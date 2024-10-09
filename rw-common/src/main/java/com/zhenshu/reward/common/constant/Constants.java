package com.zhenshu.reward.common.constant;

import java.math.BigDecimal;

/**
 * 通用常量信息
 *
 * @author ruoyi
 */
public class Constants {
    /**
     * 一小时
     */
    public static final int ONE_HOUR = 3600;

    /**
     * 5分钟
     */
    public static final int ONE_MINUTES = 60;

    /**
     * 5分钟
     */
    public static final int FIVE_MINUTES = 300;

    /**
     * 设置过期时间一天
     */
    public final static int DAY_TIME_SECOND = 86400;

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 注册
     */
    public static final String REGISTER = "Register";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 登录唯一标识
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 登录渠道
     */
    public static final String LOGIN_PLACE_KEY = "login_place_key";

    /**
     * 登录用户分配的uuid
     */
    public static final String LOGIN_UUID = "login_uuid";

    /**
     * 用户头像
     */
    public static final String JWT_AVATAR = "avatar";

    /**
     * 创建时间
     */
    public static final String JWT_CREATED = "created";

    /**
     * 用户权限
     */
    public static final String JWT_AUTHORITIES = "authorities";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

    /**
     * RMI 远程方法调用
     */
    public static final String LOOKUP_RMI = "rmi:";

    /**
     * LDAP 远程方法调用
     */
    public static final String LOOKUP_LDAP = "ldap:";

    /**
     * LDAPS 远程方法调用
     */
    public static final String LOOKUP_LDAPS = "ldaps:";

    /**
     * 定时任务白名单配置（仅允许访问的包名，如其他需要可以自行添加）
     */
    public static final String[] JOB_WHITELIST_STR = {"com.zhenshu"};

    /**
     * 定时任务违规的字符
     */
    public static final String[] JOB_ERROR_STR = {"java.net.URL", "javax.naming.InitialContext", "org.yaml.snakeyaml",
            "org.springframework", "org.apache", "com.zhenshu.reward.common.utils.file"};

    /**
     * 导入excel最大条数
     */
    public static final int EXCEL_MAX_SIZE = 1000;

    /**
     * true
     */
    public static final Boolean TRUE = true;

    /**
     * false
     */
    public static final Boolean FALSE = false;

    /**
     * 数字零
     */
    public static final int ZERO = 0;

    /**
     * 数字一
     */
    public static final int ONE = 1;

    /**
     * 数字二
     */
    public static final int TWO = 2;

    /**
     * 数字三
     */
    public static final int THREE = 3;

    /**
     * 数字四
     */
    public static final int FOUR = 4;

    /**
     * 数字十
     */
    public static final int TEN = 10;

    /**
     * 手机号校验正则
     */
    public static final String PHONE_REGEXP = "^1[3-9]\\d{9}$";

    /**
     * 支付币种
     */
    public static final String CURRENCY = "CNY";

    /**
     * 100
     */
    public static final BigDecimal BIG_HUNDRED = new BigDecimal("100");

    /**
     * 1是0否
     */
    public static final Integer NO = 0;

    /**
     * 1是0否
     */
    public static final Integer YES = 1;

    /**
     * 一百
     */
    public static final BigDecimal HUNDRED_BIG = new BigDecimal(100);

    /**
     * -1 表示不限数量
     */
    public static final int UNLIMITED = -1;
}
