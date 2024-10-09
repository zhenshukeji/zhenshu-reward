package com.zhenshu.reward.common.constant.game.rule;

import com.zhenshu.reward.common.constant.Constants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author jing
 * @version 1.0
 * @desc 签到规则
 * @date 2024/5/9 下午5:44
 **/
@Data
public class SignInRule implements PointsTaskRule {

    /**
     * 每天的积分，例如7天
     */
    @NotNull
    @ApiModelProperty(required = true, value = "积分")
    private Integer[] pointsPerDay;

    /**
     * 签到周期，例如7天一个轮回
     */
    @Min(Constants.ONE)
    @NotNull
    @ApiModelProperty(required = true, value = "签到周期")
    private Integer cycle;

    /**
     * 计算签到积分的方法
     *
     * @param day 当前连续签到天数
     * @return 应得的积分
     */
    public int calculatePoints(int day) {
        if (day <= Constants.ZERO) {
            throw new IllegalArgumentException("day <= 0, Param Error");
        }
        if (ArrayUtils.isEmpty(pointsPerDay)) {
            throw new IllegalArgumentException("pointsPerDay Is Empty, Param Error");
        }
        for (Integer points : pointsPerDay) {
            if (points <= Constants.ZERO) {
                throw new IllegalArgumentException("每日签到获得积分数不能小于零");
            }
        }
        return pointsPerDay[(day - Constants.ONE) % cycle];
    }

    @Override
    public boolean verify() {
        for (Integer points : pointsPerDay) {
            if (points <= Constants.ZERO) {
                throw new IllegalArgumentException("每日签到获得积分数不能小于零");
            }
        }
        return pointsPerDay.length == cycle;
    }
}
