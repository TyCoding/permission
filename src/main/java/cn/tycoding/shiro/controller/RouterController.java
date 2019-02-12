package cn.tycoding.shiro.controller;

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

    @GetMapping("/")
    public String i() {
        return "index";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/user")
    public String user() {
        return "page/user/user";
    }

    @GetMapping("/role")
    public String role() {
        return "page/role/role";
    }

    @GetMapping("/menu")
    public String menu() {
        return "page/menu/menu";
    }

    @GetMapping("/self")
    public String self() {
        return "page/my/self";
    }
}
