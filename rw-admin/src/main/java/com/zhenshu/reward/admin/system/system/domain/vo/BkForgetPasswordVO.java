package com.zhenshu.reward.admin.system.system.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author hong
 * @version 1.0
 * @date 2024/6/3 14:56
 * @desc 忘记密码
 */
@Data
@ApiModel(description = "忘记密码")
public class BkForgetPasswordVO {
    /**
     * 手机号
     */
    @NotEmpty
    @ApiModelProperty(required = true, value = "手机号")
    private String phone;

    /**
     * 验证码
     */
    @NotEmpty
    @ApiModelProperty(required = true, name = "验证码")
    private String code;

    /**
     * 新密码
     */
    @NotEmpty
    @ApiModelProperty(required = true, name = "新密码")
    private String password;
}
