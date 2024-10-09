package com.zhenshu.reward.game.vote.admin.web;

import com.zhenshu.reward.common.constant.annotation.Log;
import com.zhenshu.reward.common.constant.enums.BusinessType;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.game.vote.admin.domain.bo.BkVoteActivityQuickBO;
import com.zhenshu.reward.game.vote.admin.domain.vo.BkVoteActivityQuickVO;
import com.zhenshu.reward.game.vote.admin.facade.BkVoteQuickFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * @author hong
 * @version 1.0
 * @date 2024/6/19 18:01
 * @desc 快速配置
 */
@RestController
@PreAuthorize("@ss.hasPermi('vote:activity')")
@RequestMapping("/bk/vote/quick")
@Api(tags = "投票活动", produces = MediaType.APPLICATION_JSON_VALUE)
public class BkVoteQuickController {
    @Resource
    private BkVoteQuickFacade bkVoteQuickFacade;

    @Log(title = "保存投票活动", businessType = BusinessType.OTHER)
    @PostMapping("/activity/save")
    @ApiOperation(value = "保存投票活动")
    public Result<BkVoteActivityQuickBO> save(@RequestBody @Validated BkVoteActivityQuickVO quickVO) {
        if (quickVO.getCompetitors() == null) {
            quickVO.setCompetitors(Collections.emptyList());
        }
        BkVoteActivityQuickBO bo = bkVoteQuickFacade.save(quickVO);
        if (CollectionUtils.isEmpty(bo.getCompetitorErrors())) {
            return Result.buildSuccess(bo);
        } else {
            return Result.buildFail(bo);
        }
    }
}
