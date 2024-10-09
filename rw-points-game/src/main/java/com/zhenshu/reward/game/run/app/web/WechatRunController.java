package com.zhenshu.reward.game.run.app.web;

import com.zhenshu.reward.common.constant.annotation.LockFunction;
import com.zhenshu.reward.common.constant.annotation.LoginToken;
import com.zhenshu.reward.common.constant.enums.KeyDataSource;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.game.run.app.domian.bo.WechatRunCompleteBO;
import com.zhenshu.reward.game.run.app.domian.bo.WechatRunInfoBO;
import com.zhenshu.reward.game.run.app.domian.vo.WeRunCompleteVO;
import com.zhenshu.reward.game.run.app.domian.vo.WeRunDataVO;
import com.zhenshu.reward.game.run.app.facade.WechatRunFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author hong
 * @version 1.0
 * @date 2024/6/26 13:43
 * @desc 微信运动
 */
@RestController
@RequestMapping("/h5/wechatRun")
@Api(tags = "微信运动", produces = MediaType.APPLICATION_JSON_VALUE)
public class WechatRunController {
    @Resource
    private WechatRunFacade wechatRunFacade;

    @LockFunction(methodName = "wechatRun", keyName = "userId", form = KeyDataSource.USER)
    @LoginToken
    @PostMapping("/complete")
    @ApiOperation(value = "完成任务")
    public Result<WechatRunCompleteBO> complete(@RequestBody @Validated WeRunCompleteVO completeVO) {
        WechatRunCompleteBO bo = wechatRunFacade.complete(completeVO);
        return Result.buildSuccess(bo);
    }

    @LoginToken
    @PostMapping("/info")
    @ApiOperation(value = "微信运动信息")
    public Result<WechatRunInfoBO> info(@RequestBody @Validated WeRunDataVO runData) {
        WechatRunInfoBO bo = wechatRunFacade.info(runData);
        return Result.buildSuccess(bo);
    }
}
