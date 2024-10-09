package com.zhenshu.reward.admin.polymerization.points.domain.vo;

import com.zhenshu.reward.game.cultivate.admin.domain.vo.BkCultivateActivityAddVO;
import com.zhenshu.reward.game.simple.service.admin.domain.vo.BkSimpleActivityAddVO;
import com.zhenshu.reward.point.service.domain.vo.bk.BkPointsTaskAddVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author hong
 * @version 1.0
 * @date 2024/5/17 15:38
 * @desc 添加任务
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "添加任务")
public class BkTaskDetailsAddVO extends BkTaskDetailsHandlerVO {
    /**
     * 任务信息
     */
    @NotNull
    @Valid
    @ApiModelProperty(required = true, value = "任务信息")
    private BkPointsTaskAddVO task;

    /**
     * 接口文档特供
     */
    @ApiModelProperty(required = false, value = "简单玩法activity参数 接口文档特供")
    private BkSimpleActivityAddVO simple;

    /**
     * 接口文档特供
     */
    @ApiModelProperty(required = false, value = "养成玩法activity参数 接口文档特供")
    private BkCultivateActivityAddVO cultivate;
}
