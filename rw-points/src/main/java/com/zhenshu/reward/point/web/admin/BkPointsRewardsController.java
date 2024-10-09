package com.zhenshu.reward.point.web.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.reward.common.constant.annotation.Log;
import com.zhenshu.reward.common.constant.enums.BusinessType;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.point.service.domain.bo.bk.BkPointsRewardsBO;
import com.zhenshu.reward.point.service.domain.vo.bk.*;
import com.zhenshu.reward.point.service.facade.bk.BkPointsRewardsFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author jing
 * @version 1.0
 * @desc 后台积分商品
 * @date 2023/10/24 17:07
 **/
@RestController
@PreAuthorize("@ss.hasPermi('integral:pointsRewards')")
@RequestMapping("/bk/pointsRewards")
@Api(tags = "后台-积分商品", produces = MediaType.APPLICATION_JSON_VALUE)
public class BkPointsRewardsController {

    @Resource
    private BkPointsRewardsFacade bkPointsRewardsFacade;

    @PostMapping("/list")
    @ApiOperation(value = "列表查询")
    public Result<IPage<BkPointsRewardsBO>> list(@RequestBody @Validated BkPointsRewardsQueryVO queryVO) {
        IPage<BkPointsRewardsBO> page = bkPointsRewardsFacade.list(queryVO);
        return Result.buildSuccess(page);
    }

    @Log(title = "新增积分商品", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ApiOperation(value = "新增")
    public Result<Object> add(@RequestBody @Validated BkPointsRewardsAddVO addVO) {
        bkPointsRewardsFacade.add(addVO);
        return Result.buildSuccess();
    }

    @Log(title = "修改积分商品", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    @ApiOperation(value = "修改")
    public Result<Object> update(@RequestBody @Validated BkPointsRewardsUpdateVO updateVO) {
        bkPointsRewardsFacade.update(updateVO);
        return Result.buildSuccess();
    }

    @Log(title = "删除积分商品", businessType = BusinessType.DELETE)
    @PostMapping("/delete")
    @ApiOperation(value = "删除")
    public Result<Object> delete(@RequestBody @Validated BkPointsRewardsDeleteVO deleteVO) {
        bkPointsRewardsFacade.delete(deleteVO);
        return Result.buildSuccess();
    }

    @Log(title = "积分商品上下架", businessType = BusinessType.UPDATE)
    @PostMapping("/updateStatus")
    @ApiOperation(value = "上下架")
    public Result<Object> updateStatus(@RequestBody @Validated BkPointsRewardsStatusVO statusVO) {
        bkPointsRewardsFacade.updateStatus(statusVO);
        return Result.buildSuccess();
    }

}
