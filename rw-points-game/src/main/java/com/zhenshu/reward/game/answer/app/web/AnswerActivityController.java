package com.zhenshu.reward.game.answer.app.web;

import com.zhenshu.reward.game.answer.service.app.domain.bo.AnswerActivityBO;
import com.zhenshu.reward.game.answer.service.app.facade.AnswerActivityFacade;
import com.zhenshu.reward.common.web.Result;
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
@RequestMapping("/h5/activity/answer")
@Api(tags = "答题活动", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnswerActivityController {
    @Resource
    private AnswerActivityFacade answerActivityFacade;

    @PostMapping("/{id}")
    @ApiOperation(value = "活动信息")
    public Result<AnswerActivityBO> competitors(@PathVariable("id") @ApiParam("活动id") Long id) {
        AnswerActivityBO bo = answerActivityFacade.competitors(id);
        return Result.buildSuccess(bo);
    }
}
