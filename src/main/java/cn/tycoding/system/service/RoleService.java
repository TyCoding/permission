package cn.tycoding.system.service;

import cn.tycoding.common.service.BaseService;
import cn.tycoding.system.entity.Role;
import cn.tycoding.system.entity.RoleWithMenu;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-01-19
 */
public interface RoleService extends BaseService<Role> {

    List<Role> findUserRole(String username);

    List<Role> findAllRole(Role role);

    List<Role> queryList(Role role);

    RoleWithMenu findById(Long id);

    void add(RoleWithMenu role);

    boolean checkName(String name, String id);

    void update(RoleWithMenu role);

    void delete(List<Long> keys);
}
