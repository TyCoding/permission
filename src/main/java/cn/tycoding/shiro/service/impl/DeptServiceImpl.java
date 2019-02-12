package cn.tycoding.shiro.service.impl;

import cn.tycoding.shiro.dto.Tree;
import cn.tycoding.shiro.entity.Dept;
import cn.tycoding.shiro.mapper.DeptMapper;
import cn.tycoding.shiro.service.DeptService;
import cn.tycoding.shiro.utils.TreeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tycoding
 * @date 2019-02-02
 */
@Service
public class DeptServiceImpl extends BaseServiceImpl<Dept> implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Tree<Dept>> tree() {
        List<Dept> list = queryList(new Dept());
        List<Tree<Dept>> treeList = new ArrayList<>();
        list.forEach(dept -> {
            Tree<Dept> tree = new Tree<>();
            tree.setId(dept.getId());
            tree.setParentId(dept.getParentId());
            tree.setName(dept.getName());
            treeList.add(tree);
        });
        return TreeUtils.build(treeList);
    }

    @Override
    public List<Dept> queryList(Dept dept) {
        try {
            Example example = new Example(Dept.class);
            if (StringUtils.isNotBlank(dept.getName())) {
                example.createCriteria().andCondition("name", dept.getName());
            }
            example.setOrderByClause("create_time");
            return this.selectByExample(example);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Dept findById(Long id) {
        Example example = new Example(Dept.class);
        example.createCriteria().andCondition("id=", id);
        List<Dept> list = this.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }


}
