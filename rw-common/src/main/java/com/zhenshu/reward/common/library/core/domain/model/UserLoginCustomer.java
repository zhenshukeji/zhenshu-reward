package com.zhenshu.reward.common.library.core.domain.model;

import com.zhenshu.reward.common.user.enums.UserLoginType;
import com.zhenshu.reward.common.user.enums.UserPlaceType;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/8/10 15:15
 * @desc 微信登录用户
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "微信登录用户")
public class UserLoginCustomer extends BaseLoginUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long userId;

    /**
     * 手机号
     */
    private String userMobile;

    /**
     * 渠道
     */
    private UserPlaceType place;

    /**
     * 登录类型
     */
    private UserLoginType userLoginType;

    /**
     * 角色
     */
    private List<String> roles;

    /**
     * 微信小程序session_key
     * 仅微信小程序渠道登录有数据
     */
    private String wechatAppletSessionKey;

    @Override
    public String getUserKey() {
        return this.getUserId().toString() + "-" + userLoginType.getValue();
    }
}
