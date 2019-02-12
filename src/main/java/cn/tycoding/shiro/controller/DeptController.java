package cn.tycoding.shiro.controller;

import cn.tycoding.shiro.dto.ResponseCode;
import cn.tycoding.shiro.enums.StatusEnums;
import cn.tycoding.shiro.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tycoding
 * @date 2019-02-02
 */
@RestController
@RequestMapping("/dept")
public class DeptController extends BaseController {

    @Autowired
    private DeptService deptService;

    @RequestMapping("/tree")
    public ResponseCode tree() {
        return new ResponseCode(StatusEnums.SUCCESS, deptService.tree());
    }
}
