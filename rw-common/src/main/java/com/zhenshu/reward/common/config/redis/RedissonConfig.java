package com.zhenshu.reward.common.config.redis;

import com.zhenshu.reward.common.config.properties.RedissonProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author hualong
 * @version 1.0
 * @date 2022/1/21 11:54
 * @desc Redisson 配置类
 */
@Configuration
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonConfig {
    @Resource
    private RedissonProperties redissonProperties;

    /**
     * 对 Redisson 的使用都是通过 RedissonClient 对象
     * 服务停止后调用 shutdown 方法。
     * 集群模式 config.useClusterServers().addNodeAddress("127.0.0.1:7004", "127.0.0.1:7001");
     *
     * @return 控制台
     */
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() {
        // 1、创建配置
        Config config = new Config();
        // 2、根据 Config 创建出 RedissonClient 示例。
        config.useSingleServer()
                .setAddress(String.format("redis://%s:%s", redissonProperties.getHost(), redissonProperties.getPort()))
                .setPassword(redissonProperties.getPassword())
                .setDatabase(redissonProperties.getDatabase());
        return Redisson.create(config);
    }
}
