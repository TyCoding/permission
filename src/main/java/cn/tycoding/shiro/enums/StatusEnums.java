package cn.tycoding.shiro.enums;

import lombok.Getter;

/**
 * @author tycoding
 * @date 2019-01-19
 */
public enum StatusEnums {

    SUCCESS(200, "操作成功"),
    ACCOUNT_UNKNOWN(500, "账户不存在"),
    ACCOUNT_ERROR(500,"用户名或密码错误"),
    SYSTEM_ERROR(500, "系统繁忙"),
    PARAM_ERROR(400, "参数错误"),
    OTHER(-100, "其他错误");

    @Getter
    private int code;
    @Getter
    private String info;

    StatusEnums(int code, String info) {
        this.code = code;
        this.info = info;
    }
}
