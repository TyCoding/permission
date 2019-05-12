package cn.tycoding.monitor.controller;

import cn.tycoding.common.dto.ResponseCode;
import cn.tycoding.monitor.service.RedisService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tycoding
 * @date 2019-03-13
 */
@RestController
@RequestMapping("/monitor/redis")
@Api(value = "RedisController", tags = {"Redis监控模块接口"})
public class RedisController {

    @Autowired
    private RedisService redisService;

    @GetMapping("/info")
    public ResponseCode info() {
        return ResponseCode.success(redisService.getRedisInfo());
    }

    @GetMapping("/dbsize")
    public ResponseCode dbsize() {
        return ResponseCode.success(redisService.getRedisDbSize());
    }

    @GetMapping("/memory")
    public ResponseCode memory() {
        return ResponseCode.success(redisService.getRedisMemory());
    }
}
