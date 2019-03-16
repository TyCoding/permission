package cn.tycoding.system.service.impl;

import cn.tycoding.common.dto.Tree;
import cn.tycoding.common.service.impl.BaseServiceImpl;
import cn.tycoding.common.utils.PasswordHelper;
import cn.tycoding.common.utils.TreeUtils;
import cn.tycoding.system.entity.Menu;
import cn.tycoding.system.entity.User;
import cn.tycoding.system.entity.UserRole;
import cn.tycoding.system.entity.UserWithRole;
import cn.tycoding.system.mapper.MenuMapper;
import cn.tycoding.system.mapper.UserMapper;
import cn.tycoding.system.mapper.UserRoleMapper;
import cn.tycoding.system.service.UserRoleService;
import cn.tycoding.system.service.UserService;
import org.apache.shiro.SecurityUtils;
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
    private PasswordHelper passwordHelper;

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
        passwordHelper.encryptPassword(user);
        this.save(user);
        saveUserRole(user);
    }

    @Override
    public boolean checkName(String name, String id) {
        if (name.isEmpty()) {
            return false;
        }
        Example example = new Example(Menu.class);
        if (!id.isEmpty()) {
            example.createCriteria().andCondition("lower(username)=", name.toLowerCase()).andNotEqualTo("id", id);
        } else {
            example.createCriteria().andCondition("lower(username)=", name.toLowerCase());
        }
        List<User> users = this.selectByExample(example);
        if (users.size() > 0) {
            return false;
        }
        return true;
    }

    @Transactional
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
        this.updateNotNull(user);
        Example example = new Example(UserRole.class);
        example.createCriteria().andCondition("user_id=", user.getId());
        userRoleMapper.deleteByExample(example);
        saveUserRole(user);
    }

    @Override
    @Transactional
    public void delete(List<Long> keys) {
        this.batchDelete(keys, "id", User.class);
        userRoleService.deleteUserRolesByUserId(keys);
    }

    @Override
    @Transactional
    public void updatePassword(String password) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Example example = new Example(User.class);
        example.createCriteria().andCondition("username=", user.getUsername());
        user.setPassword(password);
        passwordHelper.encryptPassword(user);
        userMapper.updateByExampleSelective(user, example);
    }


}
