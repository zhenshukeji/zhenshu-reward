package com.zhenshu.reward.game.simple.web.admin;

import com.zhenshu.reward.common.constant.annotation.Log;
import com.zhenshu.reward.common.constant.enums.BusinessType;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.game.simple.service.admin.domain.bo.BkSimpleActivityBO;
import com.zhenshu.reward.game.simple.service.admin.domain.vo.BkSimpleActivityAddVO;
import com.zhenshu.reward.game.simple.service.admin.domain.vo.BkSimpleActivityEditVO;
import com.zhenshu.reward.game.simple.service.admin.facade.BkSimpleActivityFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author ruoyi
 * @version 1.0
 * @date 2023-12-14 16:41:07
 * @desc 简单活动配置
 */
@PreAuthorize("@ss.hasPermi('integral:pointsTask')")
@RestController
@RequestMapping("/bk/simpleActivity")
@Api(tags = "简单活动配置", produces = MediaType.APPLICATION_JSON_VALUE)
public class BkSimpleActivityController {
    @Resource
    private BkSimpleActivityFacade bkLinkActivityFacade;

    @PostMapping("/info/{id}")
    @ApiOperation(value = "活动信息")
    public Result<BkSimpleActivityBO> getInfo(@PathVariable("id") @ApiParam("id") Long id) {
        BkSimpleActivityBO bo = bkLinkActivityFacade.getInfo(id);
        return Result.buildSuccess(bo);
    }

    @Log(title = "添加活动", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "添加", notes = "data返回活动id")
    public Result<Long> add(@RequestBody @Validated BkSimpleActivityAddVO addVO) {
        Long actId = bkLinkActivityFacade.add(addVO);
        return Result.buildSuccess(actId);
    }

    @Log(title = "修改活动", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "修改")
    public Result<Void> update(@RequestBody @Validated BkSimpleActivityEditVO editVO) {
        bkLinkActivityFacade.update(editVO);
        return Result.buildSuccess();
    }
}
