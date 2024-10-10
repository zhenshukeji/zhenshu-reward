package com.zhenshu.reward.common.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 缓存的key 常量
 *
 * @author zhenshu
 */
@Component
public class CacheConstants
{
    public static String CACHE_NAME;

    @Value("${spring.redis.cache_name}:ry:")
    public void setCacheName(String cacheName){
        CacheConstants.CACHE_NAME = cacheName;
    }

    @PostConstruct
    public void init(){
        CacheConstants.LOGIN_TOKEN_KEY = CACHE_NAME + CacheConstants.LOGIN_TOKEN_KEY;
        CacheConstants.CAPTCHA_CODE_KEY = CACHE_NAME + CacheConstants.CAPTCHA_CODE_KEY;
        CacheConstants.SYS_CONFIG_KEY = CACHE_NAME + CacheConstants.SYS_CONFIG_KEY;
        CacheConstants.SYS_DICT_KEY = CACHE_NAME + CacheConstants.SYS_DICT_KEY;
        CacheConstants.REPEAT_SUBMIT_KEY = CACHE_NAME + CacheConstants.REPEAT_SUBMIT_KEY;
        CacheConstants.REPEAT_SUBMIT_KEY = CACHE_NAME + CacheConstants.REPEAT_SUBMIT_KEY;
        CacheConstants.REPEAT_SUBMIT_KEY = CACHE_NAME + CacheConstants.REPEAT_SUBMIT_KEY;
        CacheConstants.REPEAT_SUBMIT_KEY = CACHE_NAME + CacheConstants.REPEAT_SUBMIT_KEY;
        CacheConstants.PWD_ERR_CNT_KEY = CACHE_NAME + CacheConstants.PWD_ERR_CNT_KEY;
    }

    /**
     * 登录用户 redis key
     */
    public static String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 验证码 redis key
     */
    public static String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 参数管理 cache key
     */
    public static String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache key
     */
    public static String SYS_DICT_KEY = "sys_dict:";

    /**
     * 防重提交 redis key
     */
    public static String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 限流 redis key
     */
    public static final String RATE_LIMIT_KEY = "rate_limit:";

    /**
     * 登录账户密码错误次数 redis key
     */
    public static String PWD_ERR_CNT_KEY = "pwd_err_cnt:";
}
