package cn.tycoding.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 路由地址管理
 * 此控制层主要管理页面请求跳转地址，返回String为HTML页面的名称，具体看application.yml中Thymeleaf的配置
 *
 * @author tycoding
 * @date 2019-01-27
 */
@Controller
public class RouterController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String i() {
        return "index";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    /**
     * 系统管理
     */
    @GetMapping("/user")
    public String user() {
        return "page/system/user/user";
    }

    @GetMapping("/role")
    public String role() {
        return "page/system/role/role";
    }

    @GetMapping("/menu")
    public String menu() {
        return "page/system/menu/menu";
    }

    @GetMapping("/self")
    public String self() {
        return "page/system/my/self";
    }

    @GetMapping("/dept")
    public String dept() {
        return "page/system/dept/dept";
    }

    /**
     * 系统监控
     */
    @GetMapping("/online")
    public String online() {
        return "/page/monitor/online/online";
    }
}
