package com.zhenshu.reward.point.web.app;

import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.point.service.domain.bo.h5.UserPointsBO;
import com.zhenshu.reward.point.service.facade.h5.UserPointsFacade;
import com.zhenshu.reward.common.constant.annotation.LoginToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author hong
 * @version 1.0
 * @date 2023/12/12 14:06
 * @desc 用户积分
 */
@RestController
@RequestMapping("/h5/userPoints")
@Api(tags = "用户积分", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserPointsController {
    @Resource
    private UserPointsFacade userPointsFacade;

    @LoginToken
    @PostMapping
    @ApiOperation(value = "用户积分")
    public Result<UserPointsBO> userPoints() {
        UserPointsBO bo = userPointsFacade.userPoints();
        return Result.buildSuccess(bo);
    }
}
