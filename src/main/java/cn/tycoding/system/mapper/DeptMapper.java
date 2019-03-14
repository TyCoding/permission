package cn.tycoding.system.mapper;

import cn.tycoding.common.config.MyMapper;
import cn.tycoding.system.entity.Dept;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-02-02
 */
public interface DeptMapper extends MyMapper<Dept> {

    void changeTopNode(List<Long> ids);
}
