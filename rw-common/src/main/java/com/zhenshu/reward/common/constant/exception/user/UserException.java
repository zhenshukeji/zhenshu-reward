package com.zhenshu.reward.common.constant.exception.user;

import com.zhenshu.reward.common.constant.exception.base.BaseException;

/**
 * 用户信息异常类
 *
 * @author zhenshu
 */
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }
}
