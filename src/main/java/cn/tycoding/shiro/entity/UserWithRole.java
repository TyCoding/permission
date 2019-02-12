package cn.tycoding.shiro.entity;

import lombok.Data;

import javax.persistence.Transient;
import java.util.List;

/**
 * 为什么要创建这个类？
 * 因为需要查询单个用户数据的同时查询到其角色列表，而并不能直接查询到角色集合封装到user的类似`List<Long> ids`这个属性中
 * 所以mysql会直接查询到多个user，我们应该单独将创建`roleId`字段用于接收mysql查询的多个user集合
 * 而这个`roleId`字段不是必须的，所以创建此类继承User类仅在特殊情况下使用
 *
 * @author tycoding
 * @date 2019-02-02
 */
@Data
public class UserWithRole extends User {

    @Transient
    private Long roleId;

    @Transient
    private List<Long> roleIds;
}
