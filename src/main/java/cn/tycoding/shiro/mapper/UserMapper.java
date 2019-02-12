package cn.tycoding.shiro.mapper;

import cn.tycoding.shiro.config.MyMapper;
import cn.tycoding.shiro.entity.User;
import cn.tycoding.shiro.entity.UserWithRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-01-19
 */
@Mapper
public interface UserMapper extends MyMapper<User> {

    List<User> queryList(User user);

    List<UserWithRole> findById(Long id);
}
