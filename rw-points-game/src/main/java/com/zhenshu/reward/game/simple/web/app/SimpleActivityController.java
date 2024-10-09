package com.zhenshu.reward.game.simple.web.app;

import com.zhenshu.reward.common.constant.annotation.LoginToken;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.game.simple.service.app.domain.bo.CompleteSimpleActivityBO;
import com.zhenshu.reward.game.simple.service.app.domain.bo.SimpleActivityBO;
import com.zhenshu.reward.game.simple.service.app.domain.vo.CompleteSimpleVO;
import com.zhenshu.reward.game.simple.service.app.facade.SimpleActivityFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author ruoyi
 * @version 1.0
 * @date 2023-12-14 16:41:07
 * @desc 简单活动配置
 */
@RestController
@RequestMapping("/h5/simpleActivity")
@Api(tags = "简单活动配置", produces = MediaType.APPLICATION_JSON_VALUE)
public class SimpleActivityController {
    @Resource
    private SimpleActivityFacade simpleActivityFacade;

    @LoginToken
    @PostMapping("/complete")
    @ApiOperation(value = "完成任务")
    public Result<CompleteSimpleActivityBO> complete(@RequestBody @Validated CompleteSimpleVO completeSimpleVO) {
        CompleteSimpleActivityBO bo = simpleActivityFacade.complete(completeSimpleVO);
        return Result.buildSuccess(bo);
    }

    @LoginToken
    @PostMapping("/info/{id}")
    @ApiOperation(value = "任务信息")
    public Result<SimpleActivityBO> info(@PathVariable("id") @ApiParam("活动id") Long id) {
        SimpleActivityBO bo = simpleActivityFacade.info(id);
        return Result.buildSuccess(bo);
    }
}
