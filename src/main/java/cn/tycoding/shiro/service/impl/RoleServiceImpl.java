package cn.tycoding.shiro.service.impl;

import cn.tycoding.shiro.entity.Role;
import cn.tycoding.shiro.entity.RoleMenu;
import cn.tycoding.shiro.entity.RoleWithMenu;
import cn.tycoding.shiro.mapper.RoleMapper;
import cn.tycoding.shiro.mapper.RoleMenuMapper;
import cn.tycoding.shiro.service.RoleMenuService;
import cn.tycoding.shiro.service.RoleService;
import cn.tycoding.shiro.service.UserRoleService;
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
