package com.zhenshu.reward.admin.system.global.service;

import com.zhenshu.reward.admin.system.global.rm.constant.RmConstants;
import com.zhenshu.reward.admin.system.global.rm.domain.LoginInfo;
import com.zhenshu.reward.admin.system.global.security.context.AuthenticationContextHolder;
import com.zhenshu.reward.common.constant.Constants;
import com.zhenshu.reward.common.utils.MessageUtils;
import com.zhenshu.reward.common.library.redis.mq.RedisMQTemplate;
import com.zhenshu.reward.common.library.redis.mq.stream.StreamMessage;
import com.zhenshu.reward.common.constant.CacheConstants;
import com.zhenshu.reward.common.library.core.domain.entity.SysUser;
import com.zhenshu.reward.common.library.core.redis.RedisCache;
import com.zhenshu.reward.common.constant.exception.user.UserPasswordNotMatchException;
import com.zhenshu.reward.common.constant.exception.user.UserPasswordRetryLimitExceedException;
import com.zhenshu.reward.common.utils.SecurityUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 登录密码方法
 *
 * @author ruoyi
 */
@Component
public class SysPasswordService {
    @Autowired
    private RedisCache redisCache;

    @Value(value = "${user.password.maxRetryCount}")
    private int maxRetryCount;

    @Value(value = "${user.password.lockTime}")
    private int lockTime;

    @Resource
    private RedisMQTemplate redisMQTemplate;

    /**
     * 登录账户密码错误次数缓存键名
     *
     * @param username 用户名
     * @return 缓存键key
     */
    private String getCacheKey(String username) {
        return CacheConstants.PWD_ERR_CNT_KEY + username;
    }

    public void validate(SysUser user) {
        Authentication usernamePasswordAuthenticationToken = AuthenticationContextHolder.getContext();
        String username = usernamePasswordAuthenticationToken.getName();
        String password = usernamePasswordAuthenticationToken.getCredentials().toString();

        Integer retryCount = redisCache.getCacheObject(getCacheKey(username));

        if (retryCount == null) {
            retryCount = 0;
        }

        if (retryCount >= Integer.valueOf(maxRetryCount).intValue()) {
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setUsername(username);
            loginInfo.setStatus(Constants.LOGIN_FAIL);
            loginInfo.setMessage(MessageUtils.message("user.password.retry.limit.exceed"));
            loginInfo.setArgs(ArrayUtils.toArray(maxRetryCount, lockTime));
            redisMQTemplate.send(StreamMessage.create(loginInfo, RmConstants.RECORD_LOGIN_INFOR));
            throw new UserPasswordRetryLimitExceedException(maxRetryCount, lockTime);
        }

        if (!matches(user, password)) {
            retryCount = retryCount + 1;
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setUsername(username);
            loginInfo.setStatus(Constants.LOGIN_FAIL);
            loginInfo.setMessage(MessageUtils.message("user.password.retry.limit.count"));
            loginInfo.setArgs(ArrayUtils.toArray(retryCount));
            redisMQTemplate.send(StreamMessage.create(loginInfo, RmConstants.RECORD_LOGIN_INFOR));
            redisCache.setCacheObject(getCacheKey(username), retryCount, lockTime, TimeUnit.MINUTES);
            throw new UserPasswordNotMatchException();
        } else {
            clearLoginRecordCache(username);
        }
    }

    public boolean matches(SysUser user, String rawPassword) {
        return SecurityUtils.matchesPassword(rawPassword, user.getPassword());
    }

    public void clearLoginRecordCache(String loginName) {
        if (redisCache.hasKey(getCacheKey(loginName))) {
            redisCache.deleteObject(getCacheKey(loginName));
        }
    }
}
