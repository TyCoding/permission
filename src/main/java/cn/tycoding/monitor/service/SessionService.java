package cn.tycoding.monitor.service;

import cn.tycoding.monitor.entity.OnlineUser;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-02-14
 */
public interface SessionService {

    List<OnlineUser> list();

    void forceLogout(String id);
}
