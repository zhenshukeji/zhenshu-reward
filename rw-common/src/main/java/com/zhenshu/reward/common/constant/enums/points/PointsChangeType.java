package com.zhenshu.reward.common.constant.enums.points;

import com.zhenshu.reward.common.constant.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hong
 * @version 1.0
 * @date 2023/12/11 11:03
 * @desc 积分变动类型
 */
@Getter
@AllArgsConstructor
public enum PointsChangeType implements IBaseEnum<String> {

    /**
     * 增加 减少
     */
    INCR("incr", "增加"),
    DECR("decr", "减少"),
    ;

    private final String value;
    private final String info;
}
