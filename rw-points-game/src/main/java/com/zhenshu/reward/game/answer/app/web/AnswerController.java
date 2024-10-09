package com.zhenshu.reward.game.answer.app.web;

import com.zhenshu.reward.common.constant.UserConstants;
import com.zhenshu.reward.common.utils.UserContext;
import com.zhenshu.reward.game.answer.service.app.domain.bo.AnswerBO;
import com.zhenshu.reward.game.answer.service.app.domain.bo.QuestionBO;
import com.zhenshu.reward.game.answer.service.app.domain.vo.AnswerResultVO;
import com.zhenshu.reward.game.answer.service.app.facade.AnswerFacade;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.common.constant.annotation.LoginToken;
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
 * @date 2023/10/19 10:11
 * @desc 答题
 */
@RestController
@RequestMapping("/h5/answer")
@Api(tags = "答题", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnswerController {
    @Resource
    private AnswerFacade answerFacade;

    @LoginToken(roles = {UserConstants.USER_ROLE_NORMAL})
    @PostMapping("/question/{activityId}")
    @ApiOperation(value = "题目")
    public Result<QuestionBO> list(@PathVariable("activityId") @ApiParam("活动id") Long activityId,
                                   @RequestParam(value = "taskId", required = false) @ApiParam("任务id") Long taskId) {
        List<String> roles = UserContext.getUserLoginCustomer().getRoles();
        if (!roles.contains(UserConstants.USER_ROLE_MEMBER)) {
            taskId= null;
        }
        QuestionBO bo = answerFacade.question(activityId, taskId);
        return Result.buildSuccess(bo);
    }

    @LoginToken(roles = {UserConstants.USER_ROLE_NORMAL})
    @PostMapping("/submit/{answerId}")
    @ApiOperation(value = "提交答案")
    public Result<AnswerBO> submit(@PathVariable("answerId") @ApiParam(value = "答题Id") String answerId,
                                   @RequestBody @Validated AnswerResultVO result) {
        AnswerBO bo = answerFacade.submit(answerId, result);
        return Result.buildSuccess(bo);
    }
}
