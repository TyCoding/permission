package cn.tycoding.shiro.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author tycoding
 * @date 2019-01-19
 */
@Data
@ToString
@Table(name = "tb_menu")
public class Menu implements Serializable {

    public static final String TYPE_MENU = "menu";
    public static final String TYPE_BUTTON = "button";

    @Id
    private Long id;

    private String name;

    @Column(name = "parent_id")
    private Long parentId;

    private String url;

    private String perms;

    private String type;

    private String icon;

    private Long priority;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    private Boolean status;
}
