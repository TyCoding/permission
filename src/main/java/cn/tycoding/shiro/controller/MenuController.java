package cn.tycoding.shiro.controller;

import cn.tycoding.shiro.dto.QueryPage;
import cn.tycoding.shiro.dto.ResponseCode;
import cn.tycoding.shiro.entity.Menu;
import cn.tycoding.shiro.enums.StatusEnums;
import cn.tycoding.shiro.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-01-31
 */
@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    @PostMapping("/list")
    public ResponseCode queryList(QueryPage queryPage, Menu menu) {
        return new ResponseCode(StatusEnums.SUCCESS, super.selectByPageNumSize(queryPage, () -> menuService.queryList(menu)));
    }

    @GetMapping("/menuButtonTree")
    public ResponseCode getMenuButtonTree() {
        try {
            return new ResponseCode(StatusEnums.SUCCESS, menuService.getMenuButtonTree());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseCode(StatusEnums.SYSTEM_ERROR);
        }
    }

    @GetMapping("/menuTree")
    public ResponseCode getMenuTree() {
        try {
            return new ResponseCode(StatusEnums.SUCCESS, menuService.getMenuTree());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseCode(StatusEnums.SYSTEM_ERROR);
        }
    }

    @GetMapping("/findById")
    public ResponseCode findById(Long id) {
        try {
            return new ResponseCode(StatusEnums.SUCCESS, menuService.findById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseCode(StatusEnums.SYSTEM_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseCode add(Menu menu) {
        try {
            menuService.add(menu);
            return new ResponseCode(StatusEnums.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseCode(StatusEnums.SYSTEM_ERROR);
        }
    }

    @PostMapping("/update")
    public ResponseCode update(Menu menu) {
        try {
            menuService.update(menu);
            return new ResponseCode(StatusEnums.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseCode(StatusEnums.SYSTEM_ERROR);
        }
    }

    @PostMapping("/delete")
    public ResponseCode delete(List<Long> ids) {
        try {
            menuService.delete(ids);
            return new ResponseCode(StatusEnums.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseCode(StatusEnums.SYSTEM_ERROR);
        }
    }
}
