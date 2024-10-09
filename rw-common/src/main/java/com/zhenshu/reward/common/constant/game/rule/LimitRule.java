package com.zhenshu.reward.common.constant.game.rule;

import com.zhenshu.reward.common.constant.Constants;
import com.zhenshu.reward.common.constant.game.rule.restriction.ChronoUnitRestriction;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

/**
 * @author hong
 * @version 1.0
 * @date 2024/5/23 16:38
 * @desc 次数限制规则
 * <p>
 * 其他规则可以通过继承{@link LimitRule}获得系统内置次数限制检查能力, 主要是通过{@link LimitRule#restrictions()}方法提供能力,
 * 因此请不要随意重写{@link LimitRule#restrictions()}方法
 * </p>
 */
@Data
public abstract class LimitRule implements PointsTaskRule {
    /**
     * 每日完成任务的次数限制
     */
    @NotNull
    @ApiModelProperty(required = true, value = "每日完成任务的次数限制")
    private Integer dailyLimit;

    /**
     * 累计完成任务的次数限制
     */
    @NotNull
    @ApiModelProperty(required = true, value = "累计完成任务的次数限制")
    private Integer totalLimit;

    @Override
    public List<Object> restrictions() {
        List<Object> restrictions = new LinkedList<>();
        if (dailyLimit >= Constants.ZERO) {
            restrictions.add(new ChronoUnitRestriction(ChronoUnit.DAYS, dailyLimit));
        }
        if (totalLimit >= Constants.ZERO) {
            restrictions.add(new ChronoUnitRestriction(ChronoUnit.FOREVER, totalLimit));
        }
        return restrictions;
    }
}
