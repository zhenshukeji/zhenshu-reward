package com.zhenshu.reward.common.constant.enums;

import com.zhenshu.reward.common.constant.annotation.LockFunction;
import com.zhenshu.reward.common.library.core.domain.model.UserLoginCustomer;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/12/5 13:47
 * @desc LockFunction-key-数据来源
 */
@Getter
@AllArgsConstructor
public enum KeyDataSource {
    /**
     * 登录用户
     * 数据源将固定为{@link UserLoginCustomer}
     */
    USER,
    /**
     * 方法参数
     * 从{@link LockFunction}标注方法的第一个参数作为数据源
     */
    PARAM,
    /**
     * 无数据源
     */
    NONE;
}
