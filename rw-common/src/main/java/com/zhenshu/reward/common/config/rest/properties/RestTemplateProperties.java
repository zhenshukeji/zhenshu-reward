package com.zhenshu.reward.common.config.rest.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author hong
 * @version 1.0
 * @date 2024/1/30 10:48
 * @desc 配置
 */
@Data
@ConfigurationProperties(value = "spring.rest-template")
public class RestTemplateProperties {
    /**
     * 是否开启ssl检查
     */
    private boolean ssl = true;

    private HttpClientProperties httpClient = new HttpClientProperties();

    @Data
    public static class HttpClientProperties{
        /**
         * 连接池最大连接数
         */
        private int maxTotal = 200;

        /**
         * 单个域名的最大并发数
         */
        private int defaultMaxPerRoute = 200;

        /**
         * 从连接池获取连接的超时时间
         */
        private int connectionRequestTimeout = 2000;

        /**
         * 连接建立时间
         */
        private int connectTimeout = 3000;

        /**
         * 连接建立后，数据传输过程中数据包之间间隔的最大时间
         */
        private int socketTimeout = 3000;
    }
}
