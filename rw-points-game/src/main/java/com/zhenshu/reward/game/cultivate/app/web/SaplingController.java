package com.zhenshu.reward.game.cultivate.app.web;

import com.zhenshu.reward.common.constant.annotation.LockFunction;
import com.zhenshu.reward.common.constant.annotation.LoginToken;
import com.zhenshu.reward.common.constant.enums.KeyDataSource;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.game.cultivate.app.domain.bo.SaplingCultivateBO;
import com.zhenshu.reward.game.cultivate.app.domain.bo.WateringBO;
import com.zhenshu.reward.game.cultivate.app.facade.SaplingFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author hong
 * @version 1.0
 * @date 2024/3/21 15:17
 * @desc 树苗
 */
@RestController
@RequestMapping("/h5/sapling")
@Api(tags = "树苗", produces = MediaType.APPLICATION_JSON_VALUE)
public class SaplingController {
    @Resource
    private SaplingFacade saplingFacade;

    @LoginToken
    @PostMapping("/newbie/{activityId}")
    @ApiOperation(value = "新手查询", notes = "data返回boolean, true为新手")
    public Result<Boolean> newbie(@PathVariable("activityId") @ApiParam("活动id") Long activityId) {
        boolean newbie = saplingFacade.newbie(activityId);
        return Result.buildSuccess(newbie);
    }

    @LockFunction(methodName = "newbieGuideline", keyName = "userId", form = KeyDataSource.USER)
    @LoginToken
    @PostMapping("/newbie/guideline/{activityId}")
    @ApiOperation(value = "完成新手指引")
    public Result<Void> newbieGuideline(@PathVariable("activityId") @ApiParam("活动id") Long activityId) {
        saplingFacade.newbieGuideline(activityId);
        return Result.buildSuccess();
    }

    @LoginToken
    @PostMapping("/current/sapling/{activityId}")
    @ApiOperation(value = "当前树苗")
    public Result<SaplingCultivateBO> sapling(@PathVariable("activityId") @ApiParam("活动id") Long activityId) {
        SaplingCultivateBO sapling = saplingFacade.sapling(activityId);
        return Result.buildSuccess(sapling);
    }

    @LockFunction(methodName = "watering", keyName = "userId", form = KeyDataSource.USER)
    @LoginToken
    @PostMapping("/watering/{activityId}/{taskId}")
    @ApiOperation(value = "浇水")
    public Result<WateringBO> watering(@PathVariable("activityId") @ApiParam("活动id") Long activityId,
                                       @PathVariable("taskId") @ApiParam("任务id") Long taskId) {
        WateringBO bo = saplingFacade.watering(taskId, activityId);
        return Result.buildSuccess(bo);
    }

    @LockFunction(methodName = "watering", keyName = "userId", form = KeyDataSource.USER)
    @LoginToken
    @PostMapping("/watering/{activityId}")
    @ApiOperation(value = "每日登录领能量", notes = "data返回领取的能量值")
    public Result<Integer> dailyLogin(@PathVariable("activityId") @ApiParam("活动id") Long activityId) {
        Integer energy = saplingFacade.dailyLogin(activityId);
        return Result.buildSuccess(energy);
    }
}
