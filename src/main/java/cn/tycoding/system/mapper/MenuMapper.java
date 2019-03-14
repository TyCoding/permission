package cn.tycoding.system.mapper;

import cn.tycoding.common.config.MyMapper;
import cn.tycoding.system.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-01-19
 */
public interface MenuMapper extends MyMapper<Menu> {

    List<Menu> findUserMenus(String username);

    void changeTopNode(List<Long> ids);
}
