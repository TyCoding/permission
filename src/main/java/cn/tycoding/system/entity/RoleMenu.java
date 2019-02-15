package cn.tycoding.system.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author tycoding
 * @date 2019-01-19
 */
@Data
@ToString
@Table(name = "tb_role_menu")
public class RoleMenu implements Serializable {

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "menu_id")
    private Long menuId;
}
