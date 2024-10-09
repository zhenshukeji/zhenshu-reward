package com.zhenshu.reward.admin.system.global.web.login;

import com.zhenshu.reward.common.constant.Constants;
import com.zhenshu.reward.common.constant.enums.LoginIdentity;
import com.zhenshu.reward.common.utils.ServletUtils;
import com.zhenshu.reward.common.utils.StringUtils;
import com.zhenshu.reward.common.utils.ip.AddressUtils;
import com.zhenshu.reward.common.utils.ip.IpUtils;
import com.zhenshu.reward.common.web.login.LoginCacheManages;
import com.zhenshu.reward.common.web.login.TokenService;
import com.zhenshu.reward.common.web.login.properties.TokenProperties;
import com.zhenshu.reward.common.library.core.domain.model.LoginUser;
import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 租户端 token 验证处理
 *
 * @author ruoyi
 */
@Component
public class AdminTokenService extends TokenService<LoginUser> {

    @Autowired
    public AdminTokenService(TokenProperties tokenProperties, LoginCacheManages loginCacheManages) {
        super(tokenProperties.getItems().get(LoginIdentity.admin), loginCacheManages);
    }

    @Override
    protected Class<LoginUser> getUserClass() {
        return LoginUser.class;
    }

    @Override
    protected String getUserKey(Claims claims) {
        return claims.get(Constants.LOGIN_USER_KEY, String.class);
    }

    @Override
    public LoginIdentity getIdentity() {
        return LoginIdentity.admin;
    }

    @Override
    protected Map<String, Object> getClaims(LoginUser user) {
        Map<String, Object> claims = new HashMap<>(4);
        claims.put(Constants.LOGIN_USER_KEY, user.getUserKey());
        return claims;
    }

    /**
     * 是否允许多设备同时在线
     *
     * @return 结果
     */
    @Override
    protected boolean isMultiDeviceAllowed() {
        return true;
    }

    public void setLoginUser(LoginUser loginUser) {
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNotEmpty(loginUser.getUuid())) {
            super.refreshToken(loginUser);
        }
    }

    @Override
    public String createToken(LoginUser user) {
        this.setUserAgent(user);
        return super.createToken(user);
    }

    /**
     * 设置用户代理信息
     *
     * @param loginUser 登录信息
     */
    public void setUserAgent(LoginUser loginUser) {
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        String ip = IpUtils.getIpAddr();
        loginUser.setIpaddr(ip);
        loginUser.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
        loginUser.setBrowser(userAgent.getBrowser().getName());
        loginUser.setOs(userAgent.getOperatingSystem().getName());
    }
}
