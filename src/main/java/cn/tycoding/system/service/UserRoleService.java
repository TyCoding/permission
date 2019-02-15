package cn.tycoding.system.service;

import cn.tycoding.common.service.BaseService;
import cn.tycoding.system.entity.UserRole;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-02-03
 */
public interface UserRoleService extends BaseService<UserRole> {

    void deleteUserRolesByUserId(List<Long> keys);

    void deleteUserRolesByRoleId(List<Long> keys);
}
