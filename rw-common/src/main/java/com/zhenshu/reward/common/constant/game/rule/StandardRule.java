package com.zhenshu.reward.common.constant.game.rule;

import com.zhenshu.reward.common.constant.Constants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author jing
 * @version 1.0
 * @desc 标准规则
 * @date 2024/5/9 下午6:23
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class StandardRule extends LimitRule {
    /**
     * 每次完成任务的积分
     */
    @Min(Constants.ZERO)
    @NotNull
    @ApiModelProperty(required = true, value = "每次完成任务的积分")
    private Integer pointsPerCompletion;
}
