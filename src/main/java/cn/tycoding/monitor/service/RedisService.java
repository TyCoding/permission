package cn.tycoding.monitor.service;

import cn.tycoding.monitor.entity.RedisInfo;

import java.util.List;
import java.util.Map;

/**
 * @author tycoding
 * @date 2019-03-13
 */
public interface RedisService {

    /**
     * 获取Redis基础info列表
     *
     * @return
     */
    List<RedisInfo> getRedisInfo();

    /**
     * 获取Redis内存占用信息
     *
     * @return
     */
    Map<String, Object> getRedisMemory();

    /**
     * 获取Redis key的数量 --dbsize
     *
     * @return
     */
    Map<String, Object> getRedisDbSize();
}
