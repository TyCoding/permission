package cn.tycoding.system.controller;

import cn.tycoding.common.controller.BaseController;
import cn.tycoding.common.dto.QueryPage;
import cn.tycoding.common.dto.ResponseCode;
import cn.tycoding.system.entity.*;
import cn.tycoding.common.enums.StatusEnums;
import cn.tycoding.system.service.DeptService;
import cn.tycoding.system.service.MenuService;
import cn.tycoding.system.service.RoleService;
import cn.tycoding.system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author tycoding
 * @date 2019-01-19
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private DeptService deptService;

    @GetMapping("/info")
    public ResponseCode info() {
        User user = super.getCurrentUser();
        logger.info("user={}", user.toString());

        //获取用户角色
        List<Role> roleList = roleService.findUserRole(user.getUsername());
        Set<String> roleSet = roleList.stream().map(Role::getName).collect(Collectors.toSet());

        //获取用户权限
        List<Menu> menuList = menuService.findUserPerms(user.getUsername());
        Set<String> permSet = menuList.stream().map(Menu::getPerms).collect(Collectors.toSet());

        //获取用户部门
        Dept dept = deptService.findById(user.getDeptId());

        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("username", user.getUsername());
        map.put("avatar", user.getAvatar());
        map.put("phone", user.getPhone());
        map.put("sex", user.getSex());
        map.put("roles", roleSet);
        map.put("perms", permSet);
        map.put("dept", dept.getName());
        map.put("description", user.getDescription());
        return new ResponseCode(StatusEnums.SUCCESS, map);
    }

    @GetMapping("/getMenus")
    public ResponseCode getMenus(String username) {
        return new ResponseCode(StatusEnums.SUCCESS, userService.getMenus(username));
    }

    @PostMapping("/list")
    public ResponseCode queryList(QueryPage queryPage, User user) {
        return new ResponseCode(StatusEnums.SUCCESS, super.selectByPageNumSize(queryPage, () -> userService.queryList(user)));
    }

    @GetMapping("findById")
    public ResponseCode findById(Long id) {
        return new ResponseCode(StatusEnums.SUCCESS, userService.findById(id));
    }

    @PostMapping("/add")
    public ResponseCode add(@RequestBody UserWithRole user) {
        try {
            userService.add(user);
            return new ResponseCode(StatusEnums.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseCode(StatusEnums.SYSTEM_ERROR);
        }
    }

    @GetMapping("/checkName")
    public ResponseCode checkName(String name, String id) {
        if (name.isEmpty()) {
            return new ResponseCode(StatusEnums.PARAM_ERROR);
        }
        if (!userService.checkName(name, id)) {
            return new ResponseCode(StatusEnums.PARAM_REPEAT);
        }
        return new ResponseCode(StatusEnums.SUCCESS);
    }

    @PostMapping("/update")
    public ResponseCode update(@RequestBody UserWithRole user) {
        try {
            userService.update(user);
            return new ResponseCode(StatusEnums.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseCode(StatusEnums.SYSTEM_ERROR);
        }
    }

    @PostMapping("/delete")
    public ResponseCode delete(@RequestBody List<Long> ids) {
        try {
            userService.delete(ids);
            return new ResponseCode(StatusEnums.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseCode(StatusEnums.SYSTEM_ERROR);
        }
    }

    @GetMapping("/changeAvatar")
    public ResponseCode changeAvatar(String url) {
        try {
            User user = getCurrentUser();
            user.setAvatar(url);
            userService.updateNotNull(user);
            return new ResponseCode(StatusEnums.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseCode(StatusEnums.SYSTEM_ERROR);
        }
    }
}
