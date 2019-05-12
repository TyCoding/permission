package cn.tycoding.system.service;

import cn.tycoding.common.service.BaseService;
import cn.tycoding.system.entity.RoleMenu;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-02-05
 */
public interface RoleMenuService extends BaseService<RoleMenu> {

    void deleteRoleMenusByRoleId(List<Long> keys);

    void deleteRoleMenusByMenuId(List<Long> ids);
}
