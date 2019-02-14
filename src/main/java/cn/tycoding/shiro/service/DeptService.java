package cn.tycoding.shiro.service;

import cn.tycoding.shiro.dto.Tree;
import cn.tycoding.shiro.entity.Dept;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-02-02
 */
public interface DeptService extends BaseService<Dept> {

    List<Tree<Dept>> tree();

    List<Dept> queryList(Dept dept);

    Dept findById(Long id);

    void add(Dept dept);

    boolean checkName(String name, String id);

    void update(Dept dept);

    void delete(List<Long> ids);
}
