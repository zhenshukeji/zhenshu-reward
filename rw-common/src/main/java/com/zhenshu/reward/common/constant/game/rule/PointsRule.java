package com.zhenshu.reward.common.constant.game.rule;

import com.zhenshu.reward.common.constant.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author hong
 * @version 1.0
 * @date 2024/5/27 17:08
 * @desc 积分规则
 */
@Data
@ApiModel(description = "积分规则")
public class PointsRule implements PointsTaskRule {
    /**
     * 每次完成任务的积分
     */
    @Min(Constants.ZERO)
    @NotNull
    @ApiModelProperty(required = true, value = "每次完成任务的积分")
    private Integer pointsPerCompletion;
}
