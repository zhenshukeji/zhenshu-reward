package com.zhenshu.reward.point.lottery.app.web;

import com.zhenshu.reward.common.constant.annotation.LockFunction;
import com.zhenshu.reward.common.constant.annotation.LoginToken;
import com.zhenshu.reward.common.constant.enums.KeyDataSource;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.point.lottery.service.app.domain.bo.LotteryBO;
import com.zhenshu.reward.point.lottery.service.app.domain.bo.MyPrizeBO;
import com.zhenshu.reward.point.lottery.service.app.domain.vo.MyPrizeQueryVO;
import com.zhenshu.reward.point.lottery.service.app.domain.vo.ReceivePrizeVO;
import com.zhenshu.reward.point.lottery.service.app.facade.LotteryFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/10/11 19:54
 * @desc 抽奖相关
 */
@Slf4j
@RestController
@RequestMapping("/h5/lottery")
@Api(tags = "抽奖相关", value = "WEB - LoginController", produces = MediaType.APPLICATION_JSON_VALUE)
public class LotteryController {
    @Resource
    private LotteryFacade lotteryFacade;

    @LockFunction(methodName = "lottery", keyName = "userId", form = KeyDataSource.USER)
    @LoginToken
    @PostMapping("/{pondId}")
    @ApiOperation(value = "抽奖")
    public Result<LotteryBO> lottery(@PathVariable("pondId") @ApiParam(value = "奖池id", required = true) Long pondId) {
        return new Result<LotteryBO>().success(lotteryFacade.lottery(pondId));
    }

    @LoginToken
    @PostMapping("/receive")
    @ApiOperation(value = "领取奖品")
    public Result<Object> receivePrize(@RequestBody @Validated ReceivePrizeVO vo) {
        lotteryFacade.receivePrize(vo);
        return new Result<>().success();
    }

    @LoginToken
    @PostMapping("/my/prize")
    @ApiOperation(value = "我的奖品")
    public Result<List<MyPrizeBO>> receivePrize(@RequestBody @Validated MyPrizeQueryVO queryVO) {
        List<MyPrizeBO> list = lotteryFacade.myPrize(queryVO);
        return Result.buildSuccess(list);
    }
}
