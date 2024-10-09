package com.zhenshu.reward.point.lottery.app.web;

import com.zhenshu.reward.common.constant.annotation.LoginToken;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.point.lottery.service.app.domain.bo.PondConfigBO;
import com.zhenshu.reward.point.lottery.service.app.domain.bo.PondConfigDetailsBO;
import com.zhenshu.reward.point.lottery.service.app.facade.PondConfigFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hong
 * @version 1.0
 * @date 2024/7/2 9:44
 * @desc 奖池
 */
@RestController
@RequestMapping("/h5/lottery/pond")
@Api(tags = "抽奖相关", produces = MediaType.APPLICATION_JSON_VALUE)
public class PondConfigController {
    @Resource
    private PondConfigFacade pondConfigFacade;

    @LoginToken
    @PostMapping("/list")
    @ApiOperation(value = "奖池列表")
    public Result<List<PondConfigBO>> list() {
        List<PondConfigBO> list = pondConfigFacade.list();
        return Result.buildSuccess(list);
    }

    @LoginToken
    @PostMapping("/info/{id}")
    @ApiOperation(value = "奖池信息")
    public Result<PondConfigDetailsBO> info(@PathVariable("id") @ApiParam("奖池id") Long id) {
        PondConfigDetailsBO bo = pondConfigFacade.info(id);
        return Result.buildSuccess(bo);
    }
}
