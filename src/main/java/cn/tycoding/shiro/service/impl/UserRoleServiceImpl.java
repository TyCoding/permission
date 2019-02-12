package cn.tycoding.shiro.service.impl;

import cn.tycoding.shiro.entity.UserRole;
import cn.tycoding.shiro.mapper.UserRoleMapper;
import cn.tycoding.shiro.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-02-03
 */
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole> implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    @Transactional
    public void deleteUserRolesByUserId(List<Long> keys) {
        this.batchDelete(keys, "userId", UserRole.class);
    }

    @Override
    @Transactional
    public void deleteUserRolesByRoleId(List<Long> keys) {
        this.batchDelete(keys, "roleId", UserRole.class);
    }
}
