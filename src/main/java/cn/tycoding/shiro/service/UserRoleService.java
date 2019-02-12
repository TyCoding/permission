package cn.tycoding.shiro.service;

import cn.tycoding.shiro.entity.UserRole;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-02-03
 */
public interface UserRoleService extends BaseService<UserRole> {

    void deleteUserRolesByUserId(List<Long> keys);

    void deleteUserRolesByRoleId(List<Long> keys);
}
