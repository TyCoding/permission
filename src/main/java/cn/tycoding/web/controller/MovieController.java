package cn.tycoding.web.controller;

import cn.tycoding.common.dto.ResponseCode;
import cn.tycoding.common.dto.SysConstant;
import cn.tycoding.common.exception.GlobalException;
import cn.tycoding.common.utils.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tycoding
 * @date 2019-03-14
 */
@RestController
@RequestMapping("/web/movie")
@Api(value = "MovieController", tags = {"影视资讯模块接口"})
public class MovieController {

    @GetMapping("/hot")
    public ResponseCode hot() {
        try {
            String data = HttpUtil.sendSSLPost(SysConstant.TIME_MOVIE_HOT_URL, "locationId=328");
            JSONObject jsonObject = JSON.parseObject(data);
            return ResponseCode.success(jsonObject.get("ms"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }
}
