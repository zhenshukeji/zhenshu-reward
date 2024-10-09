package com.zhenshu.reward.common.constant.exception;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/8/22 19:19
 * @desc 错误
 * 41xxx-42xxx商城APP异常
 * 40xxx\43xxx积分商城异常
 * 44xxx用户异常
 * 45xxx活动异常
 * 46xxx养成异常
 * 47xxx微信运动异常
 * 48xxx商城后台异常
 * 60xxx答题APP异常
 * 62xxx签到异常
 * 3xxxx通用异常
 */
public interface Error {
    /**
     * 获取错误状态码
     *
     * @return 结果
     */
    Integer getCode();

    /**
     * 获取错误描述
     *
     * @return 结果
     */
    String getMsg();
}
