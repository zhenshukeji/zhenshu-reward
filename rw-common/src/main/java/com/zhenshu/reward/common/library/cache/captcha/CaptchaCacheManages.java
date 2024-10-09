package com.zhenshu.reward.common.library.cache.captcha;

import com.zhenshu.reward.common.library.cache.CacheManages;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author hong
 * @version 1.0
 * @date 2024/6/3 13:58
 * @desc 验证码
 */
@Component
public class CaptchaCacheManages extends CacheManages {
    @Resource
    private CaptchaCacheKey cacheKeyManager;

    /**
     * 验证码过期时间
     */
    private static final long CODE_EXPIRE = 300;
    private static final long ONE_MINUTE = 60;

    /**
     * 获取验证码
     *
     * @param phone 手机号
     * @param type  验证码类型
     * @return 验证码
     */
    public String getCaptcha(String phone, CaptchaType type) {
        String cacheKey = cacheKeyManager.getCaptchaKey(phone, type);
        return cache.get(cacheKey);
    }

    /**
     * 校验手机号1分钟内是否发送过验证码
     *
     * @param phone       手机号
     * @param captchaType 验证码类型
     * @return 结果
     */
    public boolean checkPhone(String phone, CaptchaType captchaType) {
        String cacheKey = cacheKeyManager.getCaptchaKey(phone, captchaType);
        Long expire = cache.getExpire(cacheKey);
        return CODE_EXPIRE - expire < ONE_MINUTE;
    }

    /**
     * 保存验证码
     *
     * @param phone   手机号
     * @param captcha 验证码
     * @param type    验证码类型
     */
    public void saveCaptcha(String phone, String captcha, CaptchaType type) {
        String cacheKey = cacheKeyManager.getCaptchaKey(phone, type);
        cache.set(cacheKey, captcha, CODE_EXPIRE);
    }

    /**
     * 删除验证码
     *
     * @param phone 手机号
     * @param type  验证码类型
     */
    public void deleteCaptcha(String phone, CaptchaType type) {
        cache.delete(cacheKeyManager.getCaptchaKey(phone, type));
    }

    /**
     * 删除验证码
     *
     * @param phone 手机号
     * @param type  验证码类型
     */
    public void deleteCaptcha(StringRedisConnection connection, String phone, CaptchaType type) {
        connection.del(cacheKeyManager.getCaptchaKey(phone, type));
    }
}
