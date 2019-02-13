package cn.tycoding.shiro.service.impl;

import cn.tycoding.shiro.dto.Tree;
import cn.tycoding.shiro.entity.Menu;
import cn.tycoding.shiro.mapper.MenuMapper;
import cn.tycoding.shiro.service.MenuService;
import cn.tycoding.shiro.service.RoleMenuService;
import cn.tycoding.shiro.utils.TreeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author tycoding
 * @date 2019-01-19
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements MenuService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<Menu> findUserPerms(String username) {
        return menuMapper.findUserMenus(username);
    }

    @Override
    public List<Menu> findUserResources(String username) {
        return menuMapper.findUserMenus(username);
    }

    @Override
    public List<Menu> findAllResources(Menu menu) {
        try {
            Example example = new Example(Menu.class);
            Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(menu.getName())) {
                criteria.andCondition("name=", menu.getName());
            }
            example.setOrderByClause("id");
            return this.selectByExample(example);
        } catch (Exception e) {
            logger.error("error", e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<Menu> queryList(Menu menu) {
        try {
            Example example = new Example(Menu.class);
            if (StringUtils.isNotBlank(menu.getName())) {
                example.createCriteria().andCondition("name=", menu.getName());
            }
            if (StringUtils.isNotBlank(menu.getType())) {
                example.createCriteria().andCondition("type=", menu.getType());
            }
            example.setOrderByClause("id");
            return this.selectByExample(example);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Tree<Menu>> getMenuButtonTree() {
        List<Tree<Menu>> trees = new ArrayList<>();
        List<Menu> menus = queryList(new Menu());
        buildMenuTree(trees, menus);
        return TreeUtils.build(trees);
    }

    private void buildMenuTree(List<Tree<Menu>> trees, List<Menu> menus) {
        menus.forEach(menu -> {
            Tree<Menu> tree = new Tree<>();
            tree.setId(menu.getId());
            tree.setParentId(menu.getParentId());
            tree.setName(menu.getName());
            trees.add(tree);
        });
    }

    @Override
    public List<Tree<Menu>> getMenuTree() {
        List<Tree<Menu>> trees = new ArrayList<>();
        Menu menu = new Menu();
        menu.setType("menu");
        List<Menu> menus = queryList(menu);
        buildMenuTree(trees, menus);
        return TreeUtils.build(trees);
    }

    @Override
    public Menu findById(Long id) {
        return this.selectByKey(id);
    }

    @Override
    @Transactional
    public void add(Menu menu) {
        menu.setCreateTime(new Date());
        if (menu.getParentId() == null) {
            menu.setParentId(0L);
        }
        if (Menu.TYPE_BUTTON.equals(menu.getType())) {
            menu.setIcon(null);
        }
        this.save(menu);
    }

    @Override
    public boolean checkName(String name) {
        if (name.isEmpty()) {
            return false;
        }
        Example example = new Example(Menu.class);
        example.createCriteria().andCondition("name=", name);
        List<Menu> menus = this.selectByExample(example);
        if (menus.size() > 0) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public void update(Menu menu) {
        if (menu.getParentId() == null) {
            menu.setParentId(0L);
        }
        if (Menu.TYPE_BUTTON.equals(menu.getType())) {
            menu.setIcon(null);
        }
        this.updateNotNull(menu);
    }

    @Override
    @Transactional
    public void delete(List<Long> ids) {
        this.batchDelete(ids, "id", Menu.class);
        roleMenuService.deleteRoleMenusByMenuId(ids);
        //改变父节点
        menuMapper.changeTopNode(ids);
    }


}
