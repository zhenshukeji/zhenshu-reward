package com.zhenshu.reward.admin.system.system.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhenshu.reward.common.utils.json.jackson.AllStarJsonSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author hong
 * @version 1.0
 * @date 2024/6/12 16:43
 * @desc 重置密码
 */
@Data
@ApiModel(description = "重置密码")
public class ResetPasswordVO {
    /**
     * 旧密码
     */
    @NotEmpty
    @JsonSerialize(using = AllStarJsonSerializer.class)
    @ApiModelProperty(required = true, value = "旧密码")
    private String oldPassword;

    /**
     * 新密码
     */
    @NotEmpty
    @JsonSerialize(using = AllStarJsonSerializer.class)
    @ApiModelProperty(required = true, value = "新密码")
    private String newPassword;
}
