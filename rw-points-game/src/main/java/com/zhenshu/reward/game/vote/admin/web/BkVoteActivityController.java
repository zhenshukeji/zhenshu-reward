package com.zhenshu.reward.game.vote.admin.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.reward.common.constant.annotation.Log;
import com.zhenshu.reward.common.constant.enums.BusinessType;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.game.vote.admin.domain.bo.BkVoteActivityBO;
import com.zhenshu.reward.game.vote.admin.domain.bo.BkVoteActivityDetailsBO;
import com.zhenshu.reward.game.vote.admin.domain.vo.BkVoteActivityAddVO;
import com.zhenshu.reward.game.vote.admin.domain.vo.BkVoteActivityEditVO;
import com.zhenshu.reward.game.vote.admin.domain.vo.BkVoteActivityQueryVO;
import com.zhenshu.reward.game.vote.admin.facade.BkVoteActivityFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2023-10-13
 * @desc 活动
 */
@RestController
@PreAuthorize("@ss.hasPermi('vote:activity')")
@RequestMapping("/bk/activity/vote")
@Api(tags = "投票活动", produces = MediaType.APPLICATION_JSON_VALUE)
public class BkVoteActivityController {
    @Resource
    private BkVoteActivityFacade bkVoteActivityFacade;

    @PreAuthorize("@ss.hasAnyPermi('vote:activity,integral:pointsTask')")
    @PostMapping("/list")
    @ApiOperation(value = "列表查询")
    public Result<IPage<BkVoteActivityBO>> list(@RequestBody @Validated BkVoteActivityQueryVO queryVO) {
        IPage<BkVoteActivityBO> page = bkVoteActivityFacade.list(queryVO);
        return Result.buildSuccess(page);
    }

    @PreAuthorize("@ss.hasAnyPermi('integral:pointsTask,vote:activity')")
    @PostMapping("/list/all")
    @ApiOperation(value = "查询全部")
    public Result<List<BkVoteActivityBO>> listAll() {
        List<BkVoteActivityBO> list = bkVoteActivityFacade.listAll();
        return Result.buildSuccess(list);
    }

    @PostMapping("/details/{id}")
    @ApiOperation(value = "详情")
    public Result<BkVoteActivityDetailsBO> details(@PathVariable("id") @ApiParam("id") Long id) {
        BkVoteActivityDetailsBO details = bkVoteActivityFacade.details(id);
        return Result.buildSuccess(details);
    }

    @Log(title = "添加投票活动", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "添加", notes = "data返回活动Id")
    public Result<Long> add(@RequestBody @Validated BkVoteActivityAddVO addVO) {
        Long activityId = bkVoteActivityFacade.add(addVO);
        return Result.buildSuccess(activityId);
    }

    @Log(title = "修改投票活动", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "修改")
    public Result<Void> update(@RequestBody @Validated BkVoteActivityEditVO editVO) {
        bkVoteActivityFacade.update(editVO);
        return Result.buildSuccess();
    }

    @Log(title = "删除投票活动", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    public Result<Void> delete(@PathVariable("id") @ApiParam("id") Long id) {
        bkVoteActivityFacade.delete(id);
        return Result.buildSuccess();
    }

    @Log(title = "重置投票活动票数", businessType = BusinessType.UPDATE)
    @PostMapping("/reset/votes/{id}")
    @ApiOperation(value = "重置票数")
    public Result<Void> resetVotes(@PathVariable("id") @ApiParam("id") Long id) {
        bkVoteActivityFacade.resetVotes(id);
        return Result.buildSuccess();
    }
}
