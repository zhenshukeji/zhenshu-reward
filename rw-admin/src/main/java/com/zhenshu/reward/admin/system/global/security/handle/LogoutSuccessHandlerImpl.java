package com.zhenshu.reward.admin.system.global.security.handle;

import com.alibaba.fastjson2.JSON;
import com.zhenshu.reward.common.constant.Constants;
import com.zhenshu.reward.common.constant.HttpStatus;
import com.zhenshu.reward.common.library.core.domain.AjaxResult;
import com.zhenshu.reward.common.utils.ServletUtils;
import com.zhenshu.reward.common.utils.StringUtils;
import com.zhenshu.reward.common.library.redis.mq.RedisMQTemplate;
import com.zhenshu.reward.common.library.redis.mq.stream.StreamMessage;
import com.zhenshu.reward.common.library.core.domain.model.LoginUser;
import com.zhenshu.reward.admin.system.global.rm.constant.RmConstants;
import com.zhenshu.reward.admin.system.global.rm.domain.LoginInfo;
import com.zhenshu.reward.admin.system.global.web.login.AdminTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义退出处理类 返回成功
 *
 * @author zhenshu
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Autowired
    private AdminTokenService tokenService;
    @Resource
    private RedisMQTemplate redisMQTemplate;

    /**
     * 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser)) {
            String userName = loginUser.getUsername();
            // 删除用户缓存记录
            tokenService.logout();
            // 记录用户退出日志
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setUsername(userName);
            loginInfo.setStatus(Constants.LOGOUT);
            loginInfo.setMessage("退出成功");
            redisMQTemplate.send(StreamMessage.create(loginInfo, RmConstants.RECORD_LOGIN_INFOR));
        }
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.error(HttpStatus.SUCCESS, "退出成功")));
    }
}
