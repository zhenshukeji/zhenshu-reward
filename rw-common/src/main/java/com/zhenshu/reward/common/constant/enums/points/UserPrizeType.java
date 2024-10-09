package com.zhenshu.reward.common.constant.enums.points;

import com.zhenshu.reward.common.constant.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hong
 * @version 1.0
 * @date 2023/12/13 9:14
 * @desc 奖品类型
 */
@Getter
@AllArgsConstructor
public enum UserPrizeType implements IBaseEnum<String> {
    /**
     * 奖品类型
     */
    INTEGRAL("integral", "积分"),
    PHYSICAL("physical", "实物"),
    ;

    private final String value;
    private final String info;
}
