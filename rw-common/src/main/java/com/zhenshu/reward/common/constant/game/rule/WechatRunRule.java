package com.zhenshu.reward.common.constant.game.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author hong
 * @version 1.0
 * @date 2024/6/26 9:39
 * @desc 微信运动规则
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WechatRunRule extends LimitRule {
    /**
     * 步数
     */
    @Min(1)
    @NotNull
    @ApiModelProperty(required = true, value = "步数")
    private Integer step;

    /**
     * 积分数
     */
    @Min(1)
    @NotNull
    @ApiModelProperty(required = true, value = "积分数")
    private Integer points;
}
