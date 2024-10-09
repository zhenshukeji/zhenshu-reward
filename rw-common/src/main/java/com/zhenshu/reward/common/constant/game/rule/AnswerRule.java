package com.zhenshu.reward.common.constant.game.rule;

import com.zhenshu.reward.common.constant.Constants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author hong
 * @version 1.0
 * @date 2024/5/23 11:13
 * @desc 答题规则
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AnswerRule extends LimitRule {
    /**
     * 每题分数
     */
    @Min(Constants.ZERO)
    @NotNull
    @ApiModelProperty(required = true, value = "每题分数")
    private Integer questionPoints;

    /**
     * 额外分数所需正确题目数
     */
    @Min(Constants.ZERO)
    @NotNull
    @ApiModelProperty(required = true, value = "额外分数所需正确题目数")
    private Integer extraPointsRightQuestionNumber;

    /**
     * 额外分数
     */
    @Min(Constants.ZERO)
    @NotNull
    @ApiModelProperty(required = true, value = "额外分数")
    private Integer extraPoints;
}
