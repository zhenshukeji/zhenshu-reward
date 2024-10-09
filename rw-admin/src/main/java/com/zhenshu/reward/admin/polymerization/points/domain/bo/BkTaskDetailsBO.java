package com.zhenshu.reward.admin.polymerization.points.domain.bo;

import com.zhenshu.reward.game.cultivate.admin.domain.bo.BkCultivateActivityBO;
import com.zhenshu.reward.game.simple.service.admin.domain.bo.BkSimpleActivityBO;
import com.zhenshu.reward.point.service.domain.bo.bk.BkPointsTaskBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hong
 * @version 1.0
 * @date 2024/5/21 20:03
 * @desc 任务详情
 */
@Data
@ApiModel(description = "任务详情")
public class BkTaskDetailsBO {
    /**
     * 任务数据
     */
    @ApiModelProperty(value = "任务数据")
    private BkPointsTaskBO task;

    /**
     * 活动数据
     */
    @ApiModelProperty(value = "活动数据")
    private Object activity;

    /**
     * 接口文档特供
     */
    @ApiModelProperty(value = "简单任务activity参数 接口文档特供")
    private BkSimpleActivityBO simple;

    /**
     * 接口文档特供
     */
    @ApiModelProperty(value = "养成任务activity参数 接口文档特供")
    private BkCultivateActivityBO cultivate;
}
