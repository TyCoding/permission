package cn.tycoding.system.controller;

import cn.tycoding.common.controller.BaseController;
import cn.tycoding.common.dto.ResponseCode;
import cn.tycoding.common.enums.StatusEnums;
import cn.tycoding.system.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tycoding
 * @date 2019-01-20
 */
@RestController
public class LoginController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

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
            return new ResponseCode(StatusEnums.SUCCESS, super.getToken());
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            return new ResponseCode(StatusEnums.ACCOUNT_UNKNOWN);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new ResponseCode(StatusEnums.ACCOUNT_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseCode.ERROR();
        }
    }

    @GetMapping("/logout")
    public ResponseCode logout() {
        getSubject().logout();
        return ResponseCode.SUCCESS();
    }
}
