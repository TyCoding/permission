package cn.tycoding.shiro.mapper;

import cn.tycoding.shiro.config.MyMapper;
import cn.tycoding.shiro.entity.Dept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-02-02
 */
@Mapper
public interface DeptMapper extends MyMapper<Dept> {

    void changeTopNode(List<Long> ids);
}
