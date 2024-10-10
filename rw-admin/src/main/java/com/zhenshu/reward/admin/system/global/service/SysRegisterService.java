package com.zhenshu.reward.admin.system.global.service;

import com.zhenshu.reward.admin.system.global.rm.domain.LoginInfo;
import com.zhenshu.reward.admin.system.system.service.ISysConfigService;
import com.zhenshu.reward.admin.system.system.service.ISysUserService;
import com.zhenshu.reward.common.constant.Constants;
import com.zhenshu.reward.common.utils.MessageUtils;
import com.zhenshu.reward.common.utils.StringUtils;
import com.zhenshu.reward.common.library.redis.mq.RedisMQTemplate;
import com.zhenshu.reward.common.library.redis.mq.stream.StreamMessage;
import com.zhenshu.reward.common.constant.CacheConstants;
import com.zhenshu.reward.common.constant.UserConstants;
import com.zhenshu.reward.common.library.core.domain.entity.SysUser;
import com.zhenshu.reward.common.library.core.domain.model.RegisterBody;
import com.zhenshu.reward.common.library.core.redis.RedisCache;
import com.zhenshu.reward.common.constant.exception.user.CaptchaException;
import com.zhenshu.reward.common.constant.exception.user.CaptchaExpireException;
import com.zhenshu.reward.common.utils.SecurityUtils;
import com.zhenshu.reward.admin.system.global.rm.constant.RmConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 注册校验方法
 *
 * @author zhenshu
 */
@Component
public class SysRegisterService
{
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private RedisCache redisCache;

    @Resource
    private RedisMQTemplate redisMQTemplate;

    /**
     * 注册
     */
    public String register(RegisterBody registerBody)
    {
        String msg = "", username = registerBody.getUsername(), password = registerBody.getPassword();

        boolean captchaEnabled = configService.selectCaptchaEnabled();
        // 验证码开关
        if (captchaEnabled)
        {
            validateCaptcha(username, registerBody.getCode(), registerBody.getUuid());
        }

        if (StringUtils.isEmpty(username))
        {
            msg = "用户名不能为空";
        }
        else if (StringUtils.isEmpty(password))
        {
            msg = "用户密码不能为空";
        }
        else if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            msg = "账户长度必须在2到20个字符之间";
        }
        else if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            msg = "密码长度必须在5到20个字符之间";
        }
        else if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(username)))
        {
            msg = "保存用户'" + username + "'失败，注册账号已存在";
        }
        else
        {
            SysUser sysUser = new SysUser();
            sysUser.setUserName(username);
            sysUser.setNickName(username);
            sysUser.setPassword(SecurityUtils.encryptPassword(registerBody.getPassword()));
            boolean regFlag = userService.registerUser(sysUser);
            if (!regFlag)
            {
                msg = "注册失败,请联系系统管理人员";
            }
            else
            {
                LoginInfo loginInfo = new LoginInfo();
                loginInfo.setUsername(username);
                loginInfo.setStatus(Constants.REGISTER);
                loginInfo.setMessage(MessageUtils.message("user.register.success"));
                redisMQTemplate.send(StreamMessage.create(loginInfo, RmConstants.RECORD_LOGIN_INFOR));
            }
        }
        return msg;
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid)
    {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null)
        {
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha))
        {
            throw new CaptchaException();
        }
    }
}
