package cn.tycoding.system.service.impl;

import cn.tycoding.common.service.impl.BaseServiceImpl;
import cn.tycoding.system.entity.Menu;
import cn.tycoding.system.entity.Role;
import cn.tycoding.system.entity.RoleMenu;
import cn.tycoding.system.entity.RoleWithMenu;
import cn.tycoding.system.mapper.RoleMapper;
import cn.tycoding.system.mapper.RoleMenuMapper;
import cn.tycoding.system.service.RoleMenuService;
import cn.tycoding.system.service.RoleService;
import cn.tycoding.system.service.UserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tycoding
 * @date 2019-01-19
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public List<Role> findUserRole(String username) {
        return roleMapper.findUserRole(username);
    }

    @Override
    public List<Role> findAllRole(Role role) {
        return null;
    }

    @Override
    public List<Role> queryList(Role role) {
        try {
            Example example = new Example(Role.class);
            if (StringUtils.isNotBlank(role.getName())) {
                example.createCriteria().andCondition("name=", role.getName());
            }
            example.setOrderByClause("create_time");
            return this.selectByExample(example);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public RoleWithMenu findById(Long id) {
        List<RoleWithMenu> list = roleMapper.findById(id);
        List<Long> menuIds = list.stream().map(RoleWithMenu::getMenuId).collect(Collectors.toList());
        if (list.isEmpty()) {
            return null;
        }
        RoleWithMenu roleWithMenu = list.get(0);
        roleWithMenu.setMenuIds(menuIds);
        return roleWithMenu;
    }

    @Override
    @Transactional
    public void add(RoleWithMenu role) {
        role.setCreateTime(new Date());
        this.save(role);
        saveRoleMenu(role);
    }

    @Override
    public boolean checkName(String name, String id) {
        if (name.isEmpty()) {
            return false;
        }
        Example example = new Example(Menu.class);
        if (!id.isEmpty()) {
            example.createCriteria().andCondition("lower(name)=", name.toLowerCase()).andNotEqualTo("id", id);
        } else {
            example.createCriteria().andCondition("lower(name)=", name.toLowerCase());
        }
        List<Role> roles = this.selectByExample(example);
        if (roles.size() > 0) {
            return false;
        }
        return true;
    }

    private void saveRoleMenu(RoleWithMenu role) {
        role.getMenuIds().forEach(menuId -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(menuId);
            roleMenu.setRoleId(role.getId());
            roleMenuMapper.insert(roleMenu);
        });
    }

    @Override
    @Transactional
    public void update(RoleWithMenu role) {
        this.updateNotNull(role);
        Example example = new Example(RoleMenu.class);
        example.createCriteria().andCondition("role_id=", role.getId());
        roleMenuMapper.deleteByExample(example);
        this.saveRoleMenu(role);
    }

    @Override
    public void delete(List<Long> keys) {
        this.batchDelete(keys, "id", Role.class);
        roleMenuService.deleteRoleMenusByRoleId(keys);
        userRoleService.deleteUserRolesByRoleId(keys);
    }
}
