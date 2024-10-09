package com.zhenshu.reward.common.library.cache.captcha;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author hong
 * @version 1.0
 * @date 2024/6/3 14:01
 * @desc 缓存Key
 */
@Component
public class CaptchaCacheKey {
    @Value("${spring.redis.cache_name}:captcha")
    public String cacheName;

    /**
     * 获取验证码缓存Key
     *
     * @param phone 手机号
     * @param type  验证码类型
     * @return 缓存Key
     */
    public String getCaptchaKey(String phone, CaptchaType type) {
        return String.format("%s:captcha:%s:%s", cacheName, type.getValue(), phone);
    }
}
