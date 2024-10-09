package com.zhenshu.reward.game.signin.app.web;

import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.game.signin.service.app.domain.bo.SigninActivityBO;
import com.zhenshu.reward.game.signin.service.app.domain.bo.SigninBO;
import com.zhenshu.reward.game.signin.service.app.facade.SignInFacade;
import com.zhenshu.reward.common.constant.annotation.LoginToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @author hong
 * @version 1.0
 * @date 2023/12/14 17:14
 * @desc 签到
 */
@RestController
@RequestMapping("/h5/signIn")
@Api(tags = "签到", produces = MediaType.APPLICATION_JSON_VALUE)
public class SignInController {
    @Resource
    private SignInFacade signInFacade;

    @LoginToken
    @PostMapping("/info")
    @ApiOperation(value = "签到信息")
    public Result<List<SigninActivityBO>> list(@RequestBody Set<Long> activityIds) {
        List<SigninActivityBO> list = signInFacade.info(activityIds);
        return Result.buildSuccess(list);
    }

    @LoginToken
    @PostMapping("/signIn/{activityId}")
    @ApiOperation(value = "签到")
    public Result<SigninBO> signIn(@PathVariable("activityId") Long activityId,
                                   @RequestParam("taskId") Long taskId) {
        SigninBO bo = signInFacade.signIn(activityId, taskId);
        return Result.buildSuccess(bo);
    }
}
