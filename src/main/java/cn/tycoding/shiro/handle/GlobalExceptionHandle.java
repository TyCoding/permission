package cn.tycoding.shiro.handle;

import cn.tycoding.shiro.dto.ResponseCode;
import cn.tycoding.shiro.enums.StatusEnums;
import cn.tycoding.shiro.exception.GlobalException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 简单处理全局异常信息
 *
 * @author tycoding
 * @date 2019-02-13
 */
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    public ResponseCode exception(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        return new ResponseCode(StatusEnums.SYSTEM_ERROR);
    }

    @ExceptionHandler(value = GlobalException.class)
    public ResponseCode globalExceptionHandle(GlobalException e) {
        e.printStackTrace();
        return new ResponseCode(e.getEnums());
    }
}
