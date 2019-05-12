package cn.tycoding.system.service;

import cn.tycoding.common.dto.Tree;
import cn.tycoding.common.service.BaseService;
import cn.tycoding.system.entity.Menu;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-01-19
 */
public interface MenuService extends BaseService<Menu> {

    List<Menu> findUserPerms(String username);

    List<Menu> findUserPermissions(String username);

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
