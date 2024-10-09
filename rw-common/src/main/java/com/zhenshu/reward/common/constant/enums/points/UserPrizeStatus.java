package com.zhenshu.reward.common.constant.enums.points;

import com.zhenshu.reward.common.constant.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hong
 * @version 1.0
 * @date 2023/12/11 11:26
 * @desc 用户奖品状态
 */
@Getter
@AllArgsConstructor
public enum UserPrizeStatus implements IBaseEnum<String> {

    /**
     * 待领取 待发货 已发货 已收货
     */
    WAIT_RECEIVE("wait_receive", "待领取"),
    WAIT_DELIVER("wait_deliver", "待发货"),
    DELIVERED("delivered", "已发货"),
    RECEIVED("received", "已收货"),
    ;

    private final String value;
    private final String info;
}
