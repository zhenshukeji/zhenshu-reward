package com.zhenshu.reward.admin.system.global.rm.domain;

import com.zhenshu.reward.common.utils.ServletUtils;
import com.zhenshu.reward.common.utils.ip.IpUtils;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.Data;

/**
 * @author hong
 * @version 1.0
 * @date 2023/12/8 14:09
 * @desc 登录信息记录
 */
@Data
public class LoginInfo {
    /**
     * 登录用户名
     */
    private String username;

    /**
     * 登录状态
     */
    private String status;

    /**
     * 登录描述
     */
    private String message;

    /**
     * 参数
     */
    private Object[] args;

    private UserAgent userAgent;

    private String ip;

    public LoginInfo() {
        try {
            userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
            ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        } catch (Exception ignored) {
        }
    }
}
