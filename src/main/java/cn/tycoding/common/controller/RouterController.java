package cn.tycoding.common.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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

    @GetMapping("/doc")
    public String doc() {
        return "page/doc";
    }

    @GetMapping("/403")
    public String unAuthorized() {
        return "error/403";
    }

    /**
     * 系统管理
     */
    @GetMapping("/system/user")
    @RequiresPermissions("user:list")
    public String user() {
        return "page/system/user/index";
    }

    @GetMapping("/system/role")
    @RequiresPermissions("role:list")
    public String role() {
        return "page/system/role/index";
    }

    @GetMapping("/system/menu")
    @RequiresPermissions("menu:list")
    public String menu() {
        return "page/system/menu/index";
    }

    @GetMapping("/system/self")
    public String self() {
        return "page/system/my/index";
    }

    @GetMapping("/system/dept")
    @RequiresPermissions("dept:list")
    public String dept() {
        return "page/system/dept/index";
    }

    /**
     * 系统监控
     */
    @GetMapping("/monitor/online")
    @RequiresPermissions("online:list")
    public String online() {
        return "page/monitor/online/index";
    }

    @GetMapping("/monitor/loginlog")
    @RequiresPermissions("loginlog:list")
    public String loginLog() {
        return "page/monitor/loginlog/index";
    }

    @GetMapping("/monitor/log")
    @RequiresPermissions("log:list")
    public String log() {
        return "page/monitor/log/index";
    }

    @GetMapping("/monitor/redis/monitor")
    @RequiresPermissions("redis:list")
    public String monitor() {
        return "page/monitor/redis/index";
    }

    @GetMapping("/monitor/druid")
    @RequiresPermissions("druid:list")
    public String druid() {
        return "page/monitor/druid";
    }

    /**
     * 对象储存
     */
    @GetMapping("/storage/qiniu")
    @RequiresPermissions("qiniu:list")
    public String qiniu() {
        return "page/storage/qiniu/index";
    }

    /**
     * 网络资源
     */
    @GetMapping("/web/weather")
    @RequiresPermissions("weather:list")
    public String weather() {
        return "page/web/weather";
    }

    @GetMapping("/web/movie")
    @RequiresPermissions("movie:list")
    public String movie() {
        return "page/web/movie";
    }
}
