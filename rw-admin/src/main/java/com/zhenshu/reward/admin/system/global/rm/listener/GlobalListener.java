package com.zhenshu.reward.admin.system.global.rm.listener;

import com.zhenshu.reward.admin.system.global.rm.domain.LoginInfo;
import com.zhenshu.reward.admin.system.system.domain.SysOperLog;
import com.zhenshu.reward.admin.system.system.service.ISysLogininforService;
import com.zhenshu.reward.admin.system.system.service.ISysOperLogService;
import com.zhenshu.reward.common.constant.Constants;
import com.zhenshu.reward.common.utils.LogUtils;
import com.zhenshu.reward.common.utils.StringUtils;
import com.zhenshu.reward.common.utils.ip.AddressUtils;
import com.zhenshu.reward.common.library.redis.mq.annotation.RmHandler;
import com.zhenshu.reward.common.library.redis.mq.annotation.RmListener;
import com.zhenshu.reward.common.library.redis.mq.enums.MessageType;
import com.zhenshu.reward.admin.system.global.rm.constant.RmConstants;
import com.zhenshu.reward.admin.system.system.domain.SysLogininfor;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author hong
 * @version 1.0
 * @date 2023/12/8 14:15
 * @desc
 */
@Slf4j
@Component
@RmListener
public class GlobalListener {
    @Resource
    private ISysLogininforService sysLogininforService;
    @Resource
    private ISysOperLogService sysOperLogService;

    /**
     * 保存用户登录记录
     *
     * @param loginInfo 登录信息
     */
    @RmHandler(value = RmConstants.RECORD_LOGIN_INFOR, type = MessageType.STREAM)
    public void recordLogininfor(LoginInfo loginInfo) {
        String ip = loginInfo.getIp();
        UserAgent userAgent = loginInfo.getUserAgent();
        String address = AddressUtils.getRealAddressByIP(ip);
        String s = LogUtils.getBlock(ip) +
                address +
                LogUtils.getBlock(loginInfo.getUsername()) +
                LogUtils.getBlock(loginInfo.getStatus()) +
                LogUtils.getBlock(loginInfo.getMessage());
        // 打印信息到日志
        log.info(s, loginInfo.getArgs());
        // 获取客户端操作系统
        String os = userAgent.getOperatingSystem().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        // 封装对象
        SysLogininfor logininfor = new SysLogininfor();
        logininfor.setUserName(loginInfo.getUsername());
        logininfor.setIpaddr(ip);
        logininfor.setLoginLocation(address);
        logininfor.setBrowser(browser);
        logininfor.setOs(os);
        logininfor.setMsg(loginInfo.getMessage());
        // 日志状态
        if (StringUtils.equalsAny(loginInfo.getStatus(), Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)) {
            logininfor.setStatus(Constants.SUCCESS);
        } else if (Constants.LOGIN_FAIL.equals(loginInfo.getStatus())) {
            logininfor.setStatus(Constants.FAIL);
        }
        // 插入数据
        sysLogininforService.insertLogininfor(logininfor);
    }

    /**
     * 保存操作记录
     *
     * @param operLog 操作记录
     */
    @RmHandler(value = RmConstants.RECORD_OPER, type = MessageType.STREAM)
    public void recordOper(SysOperLog operLog) {
        operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
        sysOperLogService.insertOperlog(operLog);
    }
}
