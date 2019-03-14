package cn.tycoding.web.controller;

import cn.tycoding.common.dto.ResponseCode;
import cn.tycoding.common.dto.SysConstant;
import cn.tycoding.common.exception.GlobalException;
import cn.tycoding.common.utils.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-03-14
 */
@RestController
@RequestMapping("/web/weather")
public class WeatherController {

    @GetMapping("/search")
    public ResponseCode search(String cityIds) {
        try {
            String data = HttpUtil.sendGet(SysConstant.MEIZU_WEATHER_URL, "cityIds=" + cityIds);
            JSONObject jsonObject = JSON.parseObject(data);
            return ResponseCode.success(((List) jsonObject.get("value")).get(0));
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }
}
