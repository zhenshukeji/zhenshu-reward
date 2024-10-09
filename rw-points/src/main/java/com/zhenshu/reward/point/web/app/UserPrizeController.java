package com.zhenshu.reward.point.web.app;

import com.zhenshu.reward.common.constant.annotation.LockFunction;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.point.service.domain.bo.h5.UserPrizeBO;
import com.zhenshu.reward.point.service.domain.bo.h5.UserPrizeCountBO;
import com.zhenshu.reward.point.service.domain.vo.h5.PointsExchangeReceiveVO;
import com.zhenshu.reward.point.service.domain.vo.h5.UserPrizeQueryVO;
import com.zhenshu.reward.point.service.facade.h5.UserPrizeFacade;
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
 * @date 2023/12/13 10:31
 * @desc 用户奖品
 */
@RestController
@RequestMapping("/h5/userPrize")
@Api(tags = "用户奖品", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserPrizeController {
    @Resource
    private UserPrizeFacade userPrizeFacade;

    @LoginToken
    @PostMapping("/count")
    @ApiOperation(value = "奖品数")
    public Result<UserPrizeCountBO> count() {
        UserPrizeCountBO bo = userPrizeFacade.count();
        return Result.buildSuccess(bo);
    }

    @ApiOperation(value = "奖品列表")
    @PostMapping("/list")
    @LoginToken
    public Result<List<UserPrizeBO>> exchangeRecord(@RequestBody @Validated UserPrizeQueryVO queryVO) {
        List<UserPrizeBO> page = userPrizeFacade.list(queryVO);
        return Result.buildSuccess(page);
    }

    @LoginToken
    @PostMapping("/{id}")
    @ApiOperation(value = "奖品详情", notes = "该接口不返回积分数")
    public Result<UserPrizeBO> details(@PathVariable("id") @ApiParam("id") Long id) {
        UserPrizeBO bo = userPrizeFacade.details(id);
        return Result.buildSuccess(bo);
    }

    @LockFunction(methodName = "exchangeReceive", keyName = "id")
    @ApiOperation(value = "填写收货信息")
    @PostMapping("/receive")
    @LoginToken
    public Result<Void> receive(@RequestBody @Validated PointsExchangeReceiveVO receiveVO) {
        userPrizeFacade.receive(receiveVO);
        return Result.buildSuccess();
    }

    @LockFunction(methodName = "received", isObj = false)
    @LoginToken
    @PostMapping("/received/{id}")
    @ApiOperation(value = "确认收货")
    public Result<Void> received(@PathVariable("id") @ApiParam("id") Long id) {
        userPrizeFacade.received(id);
        return Result.buildSuccess();
    }
}
