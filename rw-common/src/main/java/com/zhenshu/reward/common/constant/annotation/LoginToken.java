package com.zhenshu.reward.common.constant.annotation;

import com.zhenshu.reward.common.constant.UserConstants;

import java.lang.annotation.*;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/8/8 19:00
 * @desc 登录Token解析
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginToken {
    /**
     * 是否必须要登录, 默认是必须的
     */
    boolean authentication() default true;

    /**
     * 用户必须持有角色
     * 仅当 {@link LoginToken#authentication()} = true 时生效
     */
    String[] roles() default {UserConstants.USER_ROLE_MEMBER, UserConstants.USER_ROLE_NORMAL};
}
