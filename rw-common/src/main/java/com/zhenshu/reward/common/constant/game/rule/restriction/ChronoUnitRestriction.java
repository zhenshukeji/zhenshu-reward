package com.zhenshu.reward.common.constant.game.rule.restriction;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.temporal.ChronoUnit;

/**
 * @author hong
 * @version 1.0
 * @date 2024/5/23 13:56
 * @desc 周期完成限制
 */
@Data
@AllArgsConstructor
public class ChronoUnitRestriction {
    /**
     * 单位
     */
    @NotNull
    private ChronoUnit unit;

    /**
     * 值
     */
    @NotNull
    @Min(1)
    private Integer value;
}
