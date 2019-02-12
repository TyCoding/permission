package cn.tycoding.shiro.mapper;

import cn.tycoding.shiro.config.MyMapper;
import cn.tycoding.shiro.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-01-19
 */
@Mapper
public interface MenuMapper extends MyMapper<Menu> {

    List<Menu> findUserMenus(String username);

    void changeTopNode(List<Long> ids);
}
