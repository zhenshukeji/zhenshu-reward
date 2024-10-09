package com.zhenshu.reward.common.web.domain.vo;

import com.zhenshu.reward.common.library.cache.captcha.CaptchaType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author hong
 * @version 1.0
 * @date 2024/6/3 14:11
 * @desc 验证码
 */
@Data
@ApiModel(description = "验证码")
public class CaptchaVO {
    /**
     * 手机号
     */
    @NotEmpty
    @ApiModelProperty(required = true, value = "手机号")
    private String phone;

    /**
     * 类型
     */
    @NotNull
    @ApiModelProperty(required = true, value = "类型")
    private CaptchaType type;
}
