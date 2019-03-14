package cn.tycoding.monitor.service;

import cn.tycoding.common.service.BaseService;
import cn.tycoding.monitor.entity.LoginLog;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-03-13
 */
public interface LoginLogService extends BaseService<LoginLog> {

    List<LoginLog> findByPage(LoginLog log);

    void delete(List<Long> ids);

    void saveLog(LoginLog log);
}
