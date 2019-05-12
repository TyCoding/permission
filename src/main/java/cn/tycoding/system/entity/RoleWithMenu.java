package cn.tycoding.system.entity;

import lombok.Data;

import java.util.List;

/**
 * 为什么创建这个类？
 * 请看 {@link cn.tycoding.system.entity.UserWithRole} 中的解释
 *
 * @author tycoding
 * @date 2019-02-05
 */
@Data
public class RoleWithMenu extends Role {

    private Long menuId;

    private List<Long> menuIds;
}
