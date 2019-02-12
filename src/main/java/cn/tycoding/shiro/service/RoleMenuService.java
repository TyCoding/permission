package cn.tycoding.shiro.service;

import cn.tycoding.shiro.entity.RoleMenu;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-02-05
 */
public interface RoleMenuService extends BaseService<RoleMenu> {

    void deleteRoleMenusByRoleId(List<Long> keys);

    void deleteRoleMenusByMenuId(List<Long> ids);
}
