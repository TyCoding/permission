package cn.tycoding.system.controller;

import cn.tycoding.common.controller.BaseController;
import cn.tycoding.common.dto.QueryPage;
import cn.tycoding.common.dto.ResponseCode;
import cn.tycoding.common.enums.StatusEnums;
import cn.tycoding.system.entity.Role;
import cn.tycoding.system.entity.RoleWithMenu;
import cn.tycoding.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/list")
    public ResponseCode queryList(QueryPage queryPage, Role role) {
        return ResponseCode.SUCCESS(super.selectByPageNumSize(queryPage, () -> roleService.queryList(role)));
    }

    @GetMapping("/findById")
    public ResponseCode findById(Long id) {
        return ResponseCode.SUCCESS(roleService.findById(id));
    }

    @PostMapping("/add")
    public ResponseCode add(@RequestBody RoleWithMenu role) {
        try {
            roleService.add(role);
            return ResponseCode.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseCode.ERROR();
        }
    }

    @GetMapping("/checkName")
    public ResponseCode checkName(String name, String id) {
        if (name.isEmpty()) {
            return new ResponseCode(StatusEnums.PARAM_ERROR);
        }
        if (!roleService.checkName(name, id)) {
            return new ResponseCode(StatusEnums.PARAM_REPEAT);
        }
        return ResponseCode.SUCCESS();
    }

    @PostMapping("update")
    public ResponseCode update(@RequestBody RoleWithMenu role) {
        try {
            roleService.update(role);
            return ResponseCode.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseCode.ERROR();
        }
    }

    @PostMapping("/delete")
    public ResponseCode delete(@RequestBody List<Long> ids) {
        try {
            roleService.delete(ids);
            return ResponseCode.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseCode.ERROR();
        }
    }
}
