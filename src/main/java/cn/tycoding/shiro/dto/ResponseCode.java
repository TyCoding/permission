package cn.tycoding.shiro.dto;

import cn.tycoding.shiro.enums.StatusEnums;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

/**
 * @author tycoding
 * @date 2019-01-20
 */
@Data
public class ResponseCode {

    private Integer code;
    private String msg;
    private Object data;

    public ResponseCode() {
    }

    public ResponseCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseCode(StatusEnums enums) {
        this.code = enums.getCode();
        this.msg = enums.getInfo();
    }

    public ResponseCode(StatusEnums enums, Object data) {
        this.code = enums.getCode();
        this.msg =enums.getInfo();
        this.data = data;
    }
}
