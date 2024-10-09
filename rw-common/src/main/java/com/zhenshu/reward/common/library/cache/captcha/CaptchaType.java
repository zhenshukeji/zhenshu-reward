package com.zhenshu.reward.common.library.cache.captcha;

import com.zhenshu.reward.common.constant.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hong
 * @version 1.0
 * @date 2024/6/3 14:03
 * @desc 验证码类型
 */
@Getter
@AllArgsConstructor
public enum CaptchaType implements IBaseEnum<String> {
    /**
     * 验证码类型
     * 用户端手机号登录
     * 管理端忘记密码
     */
    USER_LOGIN("user_login", "用户端手机号登录"),
    ADMIN_FORGET_PASSWORD("admin_forget_password", "管理端忘记密码")
    ;

    private final String value;
    private final String info;
}
