package com.zhenshu.reward.common.utils;

import com.zhenshu.reward.common.library.core.domain.model.BaseLoginUser;
import com.zhenshu.reward.common.library.core.domain.model.UserLoginCustomer;

import java.util.Objects;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/8/5 18:12
 * @desc 用户信息上下文
 */
public class UserContext {
    private static final ThreadLocal<BaseLoginUser> CONTEXT = new ThreadLocal<>();

    /**
     * 获取当前用户的信息
     */
    public static UserLoginCustomer getUserLoginCustomer() {
        return (UserLoginCustomer) CONTEXT.get();
    }

    public static Long getUserId() {
        return getUserLoginCustomer().getUserId();
    }

    public static void setUser(BaseLoginUser user) {
        if (Objects.nonNull(user)) {
            CONTEXT.set(user);
        }
    }

    public static void remove() {
        BaseLoginUser user = CONTEXT.get();
        if (Objects.nonNull(user)) {
            CONTEXT.remove();
        }
    }
}
