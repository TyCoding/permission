package cn.tycoding.shiro.service;

import cn.tycoding.shiro.dto.Tree;
import cn.tycoding.shiro.entity.Menu;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-01-19
 */
public interface MenuService extends BaseService<Menu> {

    List<Menu> findUserPerms(String username);

    List<Menu> findUserResources(String username);

    List<Menu> findAllResources(Menu menu);

    List<Menu> queryList(Menu menu);

    List<Tree<Menu>> getMenuButtonTree();

    List<Tree<Menu>> getMenuTree();

    Menu findById(Long id);

    void add(Menu menu);

    boolean checkName(String name, String id);

    void update(Menu menu);

    void delete(List<Long> ids);
}
