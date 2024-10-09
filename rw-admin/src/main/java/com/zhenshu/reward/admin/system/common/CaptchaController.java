package com.zhenshu.reward.admin.system.common;

import com.zhenshu.reward.common.constant.annotation.Anonymous;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.common.web.domain.vo.CaptchaVO;
import com.zhenshu.reward.common.web.facade.CaptchaFacade;
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
 * @date 2024/6/3 14:09
 * @desc 验证码
 */
@RestController
@RequestMapping("/captcha")
@Api(tags = "验证码", produces = MediaType.APPLICATION_JSON_VALUE)
public class CaptchaController {
    @Resource
    private CaptchaFacade captchaFacade;

    @Anonymous
    @PostMapping
    @ApiOperation(value = "发送验证码")
    public Result<Void> captcha(@RequestBody @Validated CaptchaVO captchaVO) {
        captchaFacade.captcha(captchaVO);
        return Result.buildSuccess();
    }
}
