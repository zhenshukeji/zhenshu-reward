package com.zhenshu.reward.common.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author hualong
 * @version 1.0
 * @date 2022/1/21 11:57
 * @desc Redisson配置属性
 */
@Data
@ConfigurationProperties(value = "spring.redis")
public class RedissonProperties {
    /**
     * IP
     */
    private String host;

    /**
     * 端口号
     */
    private String port;

    /**
     * 密码
     */
    private String password;

    /**
     * 数据库索引
     */
    private Integer database;
}
