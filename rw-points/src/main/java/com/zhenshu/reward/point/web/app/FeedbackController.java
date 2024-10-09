package com.zhenshu.reward.point.web.app;

import com.zhenshu.reward.common.constant.annotation.LoginToken;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.point.service.domain.vo.h5.FeedbackVO;
import com.zhenshu.reward.point.service.facade.h5.FeedbackFacade;
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
 * @date 2024/5/24 16:03
 * @desc 意见反馈
 */
@RestController
@RequestMapping("/h5/feedback")
@Api(tags = "意见反馈", produces = MediaType.APPLICATION_JSON_VALUE)
public class FeedbackController {
    @Resource
    private FeedbackFacade feedbackFacade;

    @LoginToken
    @PostMapping
    @ApiOperation(value = "意见反馈")
    public Result<Void> feedback(@RequestBody @Validated FeedbackVO feedbackVO) {
        feedbackFacade.feedback(feedbackVO);
        return Result.buildSuccess();
    }
}
