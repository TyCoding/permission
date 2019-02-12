package cn.tycoding.shiro.service;

import cn.tycoding.shiro.dto.Tree;
import cn.tycoding.shiro.entity.Menu;
import cn.tycoding.shiro.entity.User;
import cn.tycoding.shiro.entity.UserWithRole;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-01-19
 */
public interface UserService extends BaseService<User> {

    User findByName(String username);

    UserWithRole findById(Long id);

    List<Tree<Menu>> getMenus(String username);

    List<User> queryList(User user);

    void add(UserWithRole user);

    void update(UserWithRole user);

    void delete(List<Long> keys);
}
