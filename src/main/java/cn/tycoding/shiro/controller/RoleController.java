package cn.tycoding.shiro.controller;

import cn.tycoding.shiro.dto.QueryPage;
import cn.tycoding.shiro.dto.ResponseCode;
import cn.tycoding.shiro.entity.Role;
import cn.tycoding.shiro.entity.RoleWithMenu;
import cn.tycoding.shiro.enums.StatusEnums;
import cn.tycoding.shiro.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-01-31
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/list")
    public ResponseCode queryList(QueryPage queryPage, Role role) {
        return new ResponseCode(StatusEnums.SUCCESS, super.selectByPageNumSize(queryPage, () -> roleService.queryList(role)));
    }

    @RequestMapping("/findById")
    public ResponseCode findById(Long id) {
        return new ResponseCode(StatusEnums.SUCCESS, roleService.findById(id));
    }

    @RequestMapping("/add")
    public ResponseCode add(@RequestBody RoleWithMenu role) {
        try {
            roleService.add(role);
            return new ResponseCode(StatusEnums.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseCode(StatusEnums.SYSTEM_ERROR);
        }
    }

    @RequestMapping("update")
    public ResponseCode update(@RequestBody RoleWithMenu role) {
        try {
            roleService.update(role);
            return new ResponseCode(StatusEnums.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseCode(StatusEnums.SYSTEM_ERROR);
        }
    }

    @RequestMapping("/delete")
    public ResponseCode delete(@RequestBody List<Long> ids) {
        try {
            roleService.delete(ids);
            return new ResponseCode(StatusEnums.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseCode(StatusEnums.SYSTEM_ERROR);
        }
    }
}
