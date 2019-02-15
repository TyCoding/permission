package cn.tycoding.system.service;

import cn.tycoding.common.dto.Tree;
import cn.tycoding.common.service.BaseService;
import cn.tycoding.system.entity.Menu;
import cn.tycoding.system.entity.User;
import cn.tycoding.system.entity.UserWithRole;

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

    boolean checkName(String name, String id);

    void update(UserWithRole user);

    void delete(List<Long> keys);
}
