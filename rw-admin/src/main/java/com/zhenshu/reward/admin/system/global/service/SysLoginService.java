package com.zhenshu.reward.admin.system.global.service;

import com.zhenshu.reward.admin.system.global.rm.constant.RmConstants;
import com.zhenshu.reward.admin.system.global.rm.domain.LoginInfo;
import com.zhenshu.reward.admin.system.global.security.context.AuthenticationContextHolder;
import com.zhenshu.reward.admin.system.global.web.login.AdminTokenService;
import com.zhenshu.reward.admin.system.system.domain.vo.BkForgetPasswordVO;
import com.zhenshu.reward.admin.system.system.service.ISysConfigService;
import com.zhenshu.reward.admin.system.system.service.ISysUserService;
import com.zhenshu.reward.common.library.cache.captcha.CaptchaCacheManages;
import com.zhenshu.reward.common.library.cache.captcha.CaptchaType;
import com.zhenshu.reward.common.constant.CacheConstants;
import com.zhenshu.reward.common.constant.Constants;
import com.zhenshu.reward.common.library.core.domain.entity.SysUser;
import com.zhenshu.reward.common.library.core.domain.model.LoginUser;
import com.zhenshu.reward.common.library.core.redis.RedisCache;
import com.zhenshu.reward.common.constant.enums.LoginIdentity;
import com.zhenshu.reward.common.constant.exception.ServiceException;
import com.zhenshu.reward.common.constant.exception.user.CaptchaException;
import com.zhenshu.reward.common.constant.exception.user.CaptchaExpireException;
import com.zhenshu.reward.common.constant.exception.user.UserPasswordNotMatchException;
import com.zhenshu.reward.common.library.redis.mq.RedisMQTemplate;
import com.zhenshu.reward.common.library.redis.mq.stream.StreamMessage;
import com.zhenshu.reward.common.utils.*;
import com.zhenshu.reward.common.utils.ip.IpUtils;
import com.zhenshu.reward.common.web.login.LoginCacheManages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 登录校验方法
 *
 * @author zhenshu
 */
@Component
public class SysLoginService {
    @Autowired
    private AdminTokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysConfigService configService;

    @Resource
    private RedisMQTemplate redisMQTemplate;

    @Resource
    private CaptchaCacheManages captchaCacheManages;
    @Resource
    private LoginCacheManages loginCacheManages;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid) {
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        // 验证码开关
        if (captchaEnabled) {
            validateCaptcha(username, code, uuid);
        }
        // 用户验证
        Authentication authentication = null;
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            AuthenticationContextHolder.setContext(authenticationToken);
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                LoginInfo loginInfo = new LoginInfo();
                loginInfo.setUsername(username);
                loginInfo.setStatus(Constants.LOGIN_FAIL);
                loginInfo.setMessage(MessageUtils.message("user.password.not.match"));
                redisMQTemplate.send(StreamMessage.create(loginInfo, RmConstants.RECORD_LOGIN_INFOR));
                throw new UserPasswordNotMatchException();
            } else {
                LoginInfo loginInfo = new LoginInfo();
                loginInfo.setUsername(username);
                loginInfo.setStatus(Constants.LOGIN_FAIL);
                loginInfo.setMessage(e.getMessage());
                redisMQTemplate.send(StreamMessage.create(loginInfo, RmConstants.RECORD_LOGIN_INFOR));
                throw new ServiceException(e.getMessage());
            }
        }
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUsername(username);
        loginInfo.setStatus(Constants.LOGIN_SUCCESS);
        loginInfo.setMessage(MessageUtils.message("user.login.success"));
        redisMQTemplate.send(StreamMessage.create(loginInfo, RmConstants.RECORD_LOGIN_INFOR));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        recordLoginInfo(loginUser.getUserId());
        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setUsername(username);
            loginInfo.setStatus(Constants.LOGIN_FAIL);
            loginInfo.setMessage(MessageUtils.message("user.jcaptcha.expire"));
            redisMQTemplate.send(StreamMessage.create(loginInfo, RmConstants.RECORD_LOGIN_INFOR));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setUsername(username);
            loginInfo.setStatus(Constants.LOGIN_FAIL);
            loginInfo.setMessage(MessageUtils.message("user.jcaptcha.error"));
            redisMQTemplate.send(StreamMessage.create(loginInfo, RmConstants.RECORD_LOGIN_INFOR));
            throw new CaptchaException();
        }
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        sysUser.setLoginDate(DateUtils.getNowDate());
        userService.updateUserProfile(sysUser);
    }

    /**
     * 忘记密码
     *
     * @param forgetPasswordVO 参数
     */
    public void forgetPassword(BkForgetPasswordVO forgetPasswordVO) {
        SysUser sysUser = userService.selectUserByUserName(forgetPasswordVO.getPhone());
        if (sysUser == null) {
            throw new ServiceException("手机号未注册用户");
        }
        String captcha = captchaCacheManages.getCaptcha(forgetPasswordVO.getPhone(), CaptchaType.ADMIN_FORGET_PASSWORD);
        // TODO 测试代码
//        if (!Objects.equals(captcha, forgetPasswordVO.getCode())) {
//            throw new ServiceException(ErrorEnums.CODE_ERROR);
//        }
        String encryptedPassword = SecurityUtils.encryptPassword(forgetPasswordVO.getPassword());
        if (Objects.equals(sysUser.getPassword(), encryptedPassword)) {
            throw new ServiceException("新密码不能与旧密码一致");
        }
        sysUser.setPassword(encryptedPassword);
        userService.updateUserProfile(sysUser);
        loginCacheManages.deleteLogin(sysUser.getUserId().toString(), LoginIdentity.admin);
        captchaCacheManages.deleteCaptcha(forgetPasswordVO.getPhone(), CaptchaType.ADMIN_FORGET_PASSWORD);
    }
}
