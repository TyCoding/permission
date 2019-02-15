package cn.tycoding.system.mapper;

import cn.tycoding.common.config.MyMapper;
import cn.tycoding.system.entity.Role;
import cn.tycoding.system.entity.RoleWithMenu;
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
