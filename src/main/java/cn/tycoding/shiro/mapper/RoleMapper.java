package cn.tycoding.shiro.mapper;

import cn.tycoding.shiro.config.MyMapper;
import cn.tycoding.shiro.entity.Role;
import cn.tycoding.shiro.entity.RoleWithMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-01-19
 */
@Mapper
public interface RoleMapper extends MyMapper<Role> {

    List<Role> findUserRole(String username);

    List<RoleWithMenu> findById(Long id);
}
