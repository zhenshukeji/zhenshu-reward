package com.zhenshu.reward.common.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/8/11 11:30
 * @desc
 */
@Data
@Component
@ConfigurationProperties(prefix = "mall")
public class MallConfig {
    /**
     * 搜索历史数量
     */
    private Integer searchRecordCount;

    /**
     * 订单配置
     */
    private OrderConfig orderConfig;

    @Data
    public static class OrderConfig {

        /**
         * 支付回调地址
         */
        private String payNotifyUrl;

        /**
         * 超时时间
         */
        private Duration timeOut;

        /**
         * 支付时候的描述
         */
        private String payDesc;

        /**
         * 已取消订单退款回调地址
         */
        private String cancelOrderRefundNotifyUrl;

        /**
         * 订单退款回调地址
         */
        private String orderRefundNotifyUrl;
    }
}
