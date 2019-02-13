package cn.tycoding.shiro.exception;

import cn.tycoding.shiro.enums.StatusEnums;
import lombok.Getter;

/**
 * @author tycoding
 * @date 2019-02-13
 */
public class GlobalException extends RuntimeException {

    @Getter
    private StatusEnums enums = null;

    public GlobalException(String message) {
        super(message);
    }

    public GlobalException(StatusEnums enums) {
        this.enums = enums;
    }
}
