package com.zhenshu.reward.common.constant.enums.points;

import com.zhenshu.reward.common.constant.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hong
 * @version 1.0
 * @date 2023/12/13 9:19
 * @desc 来源类型
 */
@Getter
@AllArgsConstructor
public enum PrizeSourceType implements IBaseEnum<String> {
    /**
     * 来源类型
     */
    POINTS_EXCHANGE("points_exchange", "积分兑换"),
    LOTTERY("lottery", "抽奖"),
    ;

    private final String value;
    private final String info;
}
