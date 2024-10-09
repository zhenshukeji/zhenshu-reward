package com.zhenshu.reward.point.web.app;

import com.zhenshu.reward.common.constant.annotation.LoginToken;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.point.service.domain.bo.h5.PointsRewardsBO;
import com.zhenshu.reward.point.service.domain.bo.h5.PointsRewardsDetailsBO;
import com.zhenshu.reward.point.service.domain.vo.h5.PointsRewardsExchangeVO;
import com.zhenshu.reward.point.service.domain.vo.h5.PointsRewardsQueryVO;
import com.zhenshu.reward.point.service.facade.h5.PointsRewardsFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jing
 * @version 1.0
 * @desc 积分奖励控制器
 * @date 2023/10/24 17:24
 **/
@RestController
@RequestMapping("/h5/pointsRewards")
@Api(tags = "积分奖励", produces = MediaType.APPLICATION_JSON_VALUE)
public class PointsRewardsController {

    @Resource
    private PointsRewardsFacade pointsRewardsFacade;

    @ApiOperation(value = "列表查询")
    @PostMapping("/list")
    @LoginToken
    public Result<List<PointsRewardsBO>> list(@RequestBody @Validated PointsRewardsQueryVO queryVO) {
        List<PointsRewardsBO> page = pointsRewardsFacade.list(queryVO);
        return Result.buildSuccess(page);
    }

    @ApiOperation(value = "热门商品")
    @PostMapping("/hot")
    @LoginToken
    public Result<List<PointsRewardsBO>> hot() {
        List<PointsRewardsBO> page = pointsRewardsFacade.hot();
        return Result.buildSuccess(page);
    }

    @ApiOperation(value = "详情")
    @PostMapping("/details/{id}")
    @LoginToken
    public Result<PointsRewardsDetailsBO> details(@ApiParam("id") @PathVariable("id") Long id) {
        PointsRewardsDetailsBO details = pointsRewardsFacade.details(id);
        return Result.buildSuccess(details);
    }

    @ApiOperation(value = "兑换")
    @PostMapping("/exchange")
    @LoginToken
    public Result<Object> exchange(@RequestBody @Validated PointsRewardsExchangeVO exchangeVO) {
        pointsRewardsFacade.exchange(exchangeVO);
        return Result.buildSuccess();
    }
}
