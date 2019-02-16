package cn.tycoding.system.controller;

import cn.tycoding.common.controller.BaseController;
import cn.tycoding.common.dto.QueryPage;
import cn.tycoding.common.dto.ResponseCode;
import cn.tycoding.common.enums.StatusEnums;
import cn.tycoding.system.entity.Dept;
import cn.tycoding.system.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/list")
    public ResponseCode queryList(QueryPage queryPage, Dept dept) {
        return ResponseCode.SUCCESS(super.selectByPageNumSize(queryPage, () -> deptService.queryList(dept)));
    }

    @GetMapping("/tree")
    public ResponseCode tree() {
        return ResponseCode.SUCCESS(deptService.tree());
    }

    @GetMapping("/findById")
    public ResponseCode findById(Long id) {
        return ResponseCode.SUCCESS(deptService.findById(id));
    }

    @PostMapping("/add")
    public ResponseCode add(@RequestBody Dept dept) {
        try {
            deptService.add(dept);
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
        if (!deptService.checkName(name, id)) {
            return new ResponseCode(StatusEnums.PARAM_REPEAT);
        }
        return ResponseCode.SUCCESS();
    }

    @PostMapping("update")
    public ResponseCode update(@RequestBody Dept dept) {
        try {
            deptService.update(dept);
            return ResponseCode.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseCode.ERROR();
        }
    }

    @PostMapping("/delete")
    public ResponseCode delete(@RequestBody List<Long> ids) {
        try {
            deptService.delete(ids);
            return ResponseCode.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseCode.ERROR();
        }
    }
}
