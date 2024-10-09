package com.zhenshu.reward.game.run.admin.web;

import com.zhenshu.reward.common.constant.annotation.Log;
import com.zhenshu.reward.common.constant.enums.BusinessType;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.game.run.admin.facade.BkWechatRunFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author hong
 * @version 1.0
 * @date 2024/6/26 15:10
 * @desc 微信运动
 */
@PreAuthorize("@ss.hasPermi('integral:pointsTask')")
@RestController
@RequestMapping("/bk/wechatRun")
@Api(tags = "微信运动", produces = MediaType.APPLICATION_JSON_VALUE)
public class BkWechatRunController {
    @Resource
    private BkWechatRunFacade bkWechatRunFacade;

    @Log(title = "添加微信运动活动", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "添加", notes = "data返回活动id")
    public Result<Long> add() {
        Long actId = bkWechatRunFacade.add();
        return Result.buildSuccess(actId);
    }
}
