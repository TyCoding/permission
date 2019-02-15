package cn.tycoding.monitor.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 封装在线用户信息
 *
 * @author tycoding
 * @date 2019-02-14
 */
@Data
public class OnlineUser implements Serializable {

    private String id; //sessionId

    private String uid; //用户ID

    private String username; //用户名

    private String host; //主机地址

    private String address; //地理位置

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime; //用户开始访问时间

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime; //用户最后访问时间

    private Long timeout; //超时时间

    private String status; //状态

    public void setHost(String host) {
        this.host = host.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : host;
    }
}
