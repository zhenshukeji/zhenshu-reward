package com.zhenshu.reward.game.vote.app.web;

import com.zhenshu.reward.common.constant.annotation.LockFunction;
import com.zhenshu.reward.common.constant.annotation.LoginToken;
import com.zhenshu.reward.common.constant.UserConstants;
import com.zhenshu.reward.common.constant.enums.KeyDataSource;
import com.zhenshu.reward.common.utils.UserContext;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.game.vote.app.domain.bo.CompetitorBO;
import com.zhenshu.reward.game.vote.app.domain.bo.VotingBO;
import com.zhenshu.reward.game.vote.app.domain.vo.CompetitorsQueryVO;
import com.zhenshu.reward.game.vote.app.facade.VoteFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hong
 * @version 1.0
 * @date 2023/10/9 11:23
 * @desc 投票
 */
@RestController
@RequestMapping("/h5/vote")
@Api(tags = "投票", produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    @Resource
    private VoteFacade voteFacade;

    @PostMapping("/voting/competitors")
    @ApiOperation(value = "选手列表")
    public Result<List<CompetitorBO>> competitors(@RequestBody @Validated CompetitorsQueryVO queryVO) {
        List<CompetitorBO> list = voteFacade.competitors(queryVO);
        return Result.buildSuccess(list);
    }

    @LockFunction(methodName = "voting", keyName = "userId", form = KeyDataSource.USER)
    @LoginToken(roles = {UserConstants.USER_ROLE_NORMAL})
    @PostMapping("/voting/{competitorId}")
    @ApiOperation(value = "投票")
    public Result<VotingBO> voting(@PathVariable("competitorId") @ApiParam("选手Id") Long competitorId,
                                   @RequestParam(value = "taskId", required = false) @ApiParam("任务id") Long taskId) {
        List<String> roles = UserContext.getUserLoginCustomer().getRoles();
        if (!roles.contains(UserConstants.USER_ROLE_MEMBER)) {
            taskId= null;
        }
        VotingBO bo = voteFacade.voting(competitorId, taskId);
        return Result.buildSuccess(bo);
    }
}
