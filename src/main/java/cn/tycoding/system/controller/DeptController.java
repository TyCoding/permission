package cn.tycoding.system.controller;

import cn.tycoding.common.controller.BaseController;
import cn.tycoding.common.dto.QueryPage;
import cn.tycoding.common.dto.ResponseCode;
import cn.tycoding.system.entity.Dept;
import cn.tycoding.common.enums.StatusEnums;
import cn.tycoding.system.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-02-02
 */
@RestController
@RequestMapping("/dept")
public class DeptController extends BaseController {

    @Autowired
    private DeptService deptService;

    @RequestMapping("/list")
    public ResponseCode queryList(QueryPage queryPage, Dept dept) {
        return new ResponseCode(StatusEnums.SUCCESS, super.selectByPageNumSize(queryPage, () -> deptService.queryList(dept)));
    }

    @RequestMapping("/tree")
    public ResponseCode tree() {
        return new ResponseCode(StatusEnums.SUCCESS, deptService.tree());
    }

    @RequestMapping("/findById")
    public ResponseCode findById(Long id) {
        return new ResponseCode(StatusEnums.SUCCESS, deptService.findById(id));
    }

    @RequestMapping("/add")
    public ResponseCode add(@RequestBody Dept dept) {
        try {
            deptService.add(dept);
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
        if (!deptService.checkName(name, id)) {
            return new ResponseCode(StatusEnums.PARAM_REPEAT);
        }
        return new ResponseCode(StatusEnums.SUCCESS);
    }

    @RequestMapping("update")
    public ResponseCode update(@RequestBody Dept dept) {
        try {
            deptService.update(dept);
            return new ResponseCode(StatusEnums.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseCode(StatusEnums.SYSTEM_ERROR);
        }
    }

    @RequestMapping("/delete")
    public ResponseCode delete(@RequestBody List<Long> ids) {
        try {
            deptService.delete(ids);
            return new ResponseCode(StatusEnums.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseCode(StatusEnums.SYSTEM_ERROR);
        }
    }
}
