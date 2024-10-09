package com.zhenshu.reward.game.signin.admin.web;

import com.zhenshu.reward.common.constant.annotation.Log;
import com.zhenshu.reward.common.constant.enums.BusinessType;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.game.signin.service.admin.domain.vo.BkSignInActivityAddVO;
import com.zhenshu.reward.game.signin.service.admin.domain.vo.BkSignInActivityEditVO;
import com.zhenshu.reward.game.signin.service.admin.facade.BkSigninActivityFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author hong
 * @version 1.0
 * @date 2023/12/14 16:31
 * @desc 签到活动
 */
@PreAuthorize("@ss.hasPermi('integral:pointsTask')")
@RestController
@RequestMapping("/bk/activity/signIn")
@Api(tags = "活动", produces = MediaType.APPLICATION_JSON_VALUE)
public class BkSigninActivityController {
    @Resource
    private BkSigninActivityFacade bkSigninActivityFacade;

    @Log(title = "添加签到活动", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "添加")
    public Result<Long> add(@RequestBody @Validated BkSignInActivityAddVO addVO) {
        Long id = bkSigninActivityFacade.add(addVO);
        return Result.buildSuccess(id);
    }

    @Log(title = "修改签到活动", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "修改")
    public Result<Void> update(@RequestBody @Validated BkSignInActivityEditVO editVO) {
        bkSigninActivityFacade.update(editVO);
        return Result.buildSuccess();
    }
}
