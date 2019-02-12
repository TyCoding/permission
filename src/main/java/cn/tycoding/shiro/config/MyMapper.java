package cn.tycoding.shiro.config;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 通用Mapper
 *
 * @author tycoding
 * @date 2019-01-19
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
