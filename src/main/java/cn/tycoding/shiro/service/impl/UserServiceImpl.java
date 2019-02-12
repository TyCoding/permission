package cn.tycoding.shiro.service.impl;

import cn.tycoding.shiro.dto.Tree;
import cn.tycoding.shiro.entity.Menu;
import cn.tycoding.shiro.entity.User;
import cn.tycoding.shiro.entity.UserRole;
import cn.tycoding.shiro.entity.UserWithRole;
import cn.tycoding.shiro.mapper.MenuMapper;
import cn.tycoding.shiro.mapper.RoleMapper;
import cn.tycoding.shiro.mapper.UserMapper;
import cn.tycoding.shiro.mapper.UserRoleMapper;
import cn.tycoding.shiro.service.UserRoleService;
import cn.tycoding.shiro.service.UserService;
import cn.tycoding.shiro.utils.TreeUtils;
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
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public User findByName(String username) {
        Example example = new Example(User.class);
        example.createCriteria().andCondition("username=", username);
        List<User> list = this.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public UserWithRole findById(Long id) {
        List<UserWithRole> list = userMapper.findById(id);
        if (list.isEmpty()) {
            return null;
        }
        List<Long> roleIds = list.stream().map(UserWithRole::getRoleId).collect(Collectors.toList());
        UserWithRole userWithRole = list.get(0);
        userWithRole.setRoleIds(roleIds);
        return userWithRole;
    }

    @Override
    public List<Tree<Menu>> getMenus(String username) {

        //获取用户资源列表和角色列表
        List<Menu> menus = menuMapper.findUserMenus(username);

        List<Tree<Menu>> treeList = new ArrayList<>();
        menus.forEach(menu -> {
            Tree<Menu> tree = new Tree<>();
            tree.setId(menu.getId());
            tree.setParentId(menu.getParentId());
            tree.setName(menu.getName());
            tree.setUrl(menu.getUrl());
            tree.setIcon(menu.getIcon());
            treeList.add(tree);
        });

        return TreeUtils.build(treeList);
    }

    @Override
    public List<User> queryList(User user) {
        try {
            return userMapper.queryList(user);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public void add(UserWithRole user) {
        user.setCreateTime(new Date());
        // @TODO
        user.setSalt("xxx");
        this.save(user);
        saveUserRole(user);
    }

    private void saveUserRole(UserWithRole user) {
        user.getRoleIds().forEach(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(roleId);
            userRoleMapper.insert(userRole);
        });
    }

    @Override
    @Transactional
    public void update(UserWithRole user) {
        user.setPassword(null);
        user.setModifyTime(new Date());
        // @TODO
        user.setSalt("xxx");
        this.updateNotNull(user);
        Example example = new Example(UserRole.class);
        example.createCriteria().andCondition("user_id=", user.getId());
        userRoleMapper.deleteByExample(example);
        saveUserRole(user);
    }

    @Override
    public void delete(List<Long> keys) {
        this.batchDelete(keys, "id", User.class);
        userRoleService.deleteUserRolesByUserId(keys);
    }


}
