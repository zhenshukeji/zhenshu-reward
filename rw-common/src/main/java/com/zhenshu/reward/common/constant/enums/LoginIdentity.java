package com.zhenshu.reward.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hong
 * @version 1.0
 * @date 2023/6/20 19:03
 * @desc 登录身份
 */
@Getter
@AllArgsConstructor
public enum LoginIdentity {
    /**
     * H5用户
     */
    user("user"),

    /**
     * 管理员
     */
    admin("admin"),
    ;

    private final String key;

}
