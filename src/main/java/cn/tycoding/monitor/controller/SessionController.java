package cn.tycoding.monitor.controller;

import cn.tycoding.common.controller.BaseController;
import cn.tycoding.common.dto.ResponseCode;
import cn.tycoding.monitor.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 在线会话管理，可参看
 * 张开涛老师的博文：https://zm10.sm-tc.cn/?src=l4uLj4zF0NCVlpGRlp6RjJeWk5CRmJGWnpHRlouahprRnJCS0J2TkJjQzc%2FLyMnLzA%3D%3D&from=derive&depth=3&link_type=60&wap=false&v=1&uid=03200e6c3a76bced6b1828a8cf8d6404&restype=1
 * Mrbird博文：https://mrbird.cc/Spring-Boot-Shiro%20session.html
 *
 * @author tycoding
 * @date 2019-02-14
 */
@RestController
@RequestMapping("/online")
public class SessionController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SessionService sessionService;

    @GetMapping("/list")
    public ResponseCode list() {
        return ResponseCode.SUCCESS(sessionService.list());
    }

    @GetMapping("/forceLogout")
    public ResponseCode forceLogout(String id) {
        try {
            sessionService.forceLogout(id);
            return ResponseCode.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseCode.ERROR();
        }
    }
}
