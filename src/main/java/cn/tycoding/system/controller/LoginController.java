package cn.tycoding.system.controller;

import cn.tycoding.common.controller.BaseController;
import cn.tycoding.common.dto.ResponseCode;
import cn.tycoding.common.exception.GlobalException;
import cn.tycoding.common.utils.AddressUtil;
import cn.tycoding.common.utils.HttpContextUtil;
import cn.tycoding.common.utils.IPUtil;
import cn.tycoding.monitor.entity.LoginLog;
import cn.tycoding.monitor.service.LoginLogService;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author tycoding
 * @date 2019-01-20
 */
@RestController
public class LoginController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LoginLogService loginLogService;

    @PostMapping("/login")
    public ResponseCode login(@RequestParam("username") String username,
                              @RequestParam("password") String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            Subject subject = getSubject();
            if (subject != null) {
                subject.logout();
            }
            super.login(token);
            logger.info("是否登录==>{}", subject.isAuthenticated());

            //记录登录日志
            LoginLog log = new LoginLog();
            //获取HTTP请求
            HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
            String ip = IPUtil.getIpAddr(request);
            log.setIp(ip);
            log.setUsername(super.getCurrentUser().getUsername());
            log.setLocation(AddressUtil.getAddress(ip));
            log.setCreateTime(new Date());
            log.setDevice(request.getHeader("User-Agent"));
            loginLogService.saveLog(log);
            return ResponseCode.success(super.getToken());
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @GetMapping("/logout")
    public ResponseCode logout() {
        getSubject().logout();
        return ResponseCode.success();
    }
}
