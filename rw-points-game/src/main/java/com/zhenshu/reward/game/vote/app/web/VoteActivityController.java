package com.zhenshu.reward.game.vote.app.web;

import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.game.vote.app.domain.bo.VoteActivityBO;
import com.zhenshu.reward.game.vote.app.facade.VoteActivityFacade;
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
 * @date 2023/10/18 10:33
 * @desc 活动
 */
@RestController
@RequestMapping("/h5/activity/vote")
@Api(tags = "投票活动", produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteActivityController {
    @Resource
    private VoteActivityFacade voteActivityFacade;

    @PostMapping("/{id}")
    @ApiOperation(value = "活动信息")
    public Result<VoteActivityBO> competitors(@PathVariable("id") @ApiParam("活动id") Long id) {
        VoteActivityBO bo = voteActivityFacade.competitors(id);
        return Result.buildSuccess(bo);
    }
}
