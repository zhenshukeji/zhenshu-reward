package com.zhenshu.reward.common.library.aspectj;

import com.zhenshu.reward.common.constant.annotation.LockFunction;
import com.zhenshu.reward.common.constant.enums.ErrorEnums;
import com.zhenshu.reward.common.constant.enums.KeyDataSource;
import com.zhenshu.reward.common.constant.exception.ServiceException;
import com.zhenshu.reward.common.utils.UserContext;
import com.zhenshu.reward.common.utils.json.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author hualong
 * @version 1.0
 * @desc 防重点的切片拦截
 * @date 2021/2/19 0019 11:05
 **/
@Slf4j
@Aspect
@Component
public class LockAspect {

    @Pointcut("@annotation(com.zhenshu.reward.common.constant.annotation.LockFunction)")
    private void lockMethod() {
    }

    @Resource
    private RedissonClient redisson;
    @Value("${spring.redis.cache_name}:lock")
    public String cacheName;

    /**
     * 使用@around 环绕通知=前置+目标方法执行+后置通知
     */
    @Around("lockMethod()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取注解上的传参
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        LockFunction annotation = signature.getMethod().getAnnotation(LockFunction.class);
        // 获取方法参数值数组
        Object[] args = joinPoint.getArgs();
        // 增加访问次数
        Object arg;
        if (annotation.form() == KeyDataSource.USER) {
            arg = UserContext.getUserLoginCustomer();
        }  else if (annotation.form() == KeyDataSource.PARAM) {
            arg = ArrayUtils.isNotEmpty(args) ? args[0] : null;
        } else {
            arg = null;
        }
        RLock lock = this.tryLock(annotation, arg);
        // 方法结束后，删除缓存
        Object object;
        try {
            object = joinPoint.proceed(args);
        } finally {
            lock.unlock();
        }
        return object;
    }

    /**
     * 尝试获取锁
     *
     * @param annotation 注解对象
     * @param arg        参数
     * @return 锁对象
     */
    private RLock tryLock(LockFunction annotation, Object arg) {
        String methodName = annotation.methodName();
        String key = null;
        // 判断入参是否为一个对象，是的话从json里取值
        if (annotation.isObj()) {
            if (StringUtils.isNotEmpty(annotation.keyName())) {
                String body = JsonUtil.toJson(arg);
                key = JsonUtil.parseField(body, annotation.keyName());
            }
        } else if (Objects.nonNull(arg)) {
            // 如果不是对象的话，那么入参直接转成string
            key = String.valueOf(arg);
        }
        // 判断key值是否为空，为空的话抛异常
        if (annotation.form() != KeyDataSource.NONE && StringUtils.isEmpty(key)) {
            throw new ServiceException("LockFunction-keyName,数据为空");
        }
        // 上锁
        String cacheKey;
        if (annotation.form() != KeyDataSource.NONE) {
            cacheKey = String.format("%s:lock:%s_%s", cacheName, methodName, key);
        } else {
            cacheKey = String.format("%s:lock:%s", cacheName, methodName);
        }
        RLock lock = redisson.getLock(cacheKey);
        boolean result = lock.tryLock();
        if (!result) {
            throw new ServiceException(ErrorEnums.FREQUENT_OPERATION);
        }
        return lock;
    }

}
