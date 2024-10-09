package com.zhenshu.reward.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户状态
 *
 * @author ruoyi
 */
@Getter
@AllArgsConstructor
public enum UserStatus implements IBaseEnum<String> {
    /**
     * 0 可用, 1 禁用, 2 注销
     */
    NORMAL("0", "可用"),
    DISABLED("1", "禁用"),
    LOGOFF("2", "注销"),
    ;

    private final String value;

    private final String info;

}
