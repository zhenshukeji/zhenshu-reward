package com.zhenshu.reward.common.library.core.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author hong
 * @version 1.0
 * @date 2023/6/20 21:14
 * @desc 登录用户
 */
@Data
public abstract class BaseLoginUser {
    /**
     * 登录时间, 毫秒级时间戳
     */
    private Long loginTime;

    /**
     * 过期时间, 毫秒级时间戳
     */
    private Long expireTime;

    /**
     * 登录token的唯一标识
     */
    private String uuid;

    /**
     * 获取用户标识
     *
     * @return 用户标识
     */
    @JsonIgnore
    public abstract String getUserKey();
}
