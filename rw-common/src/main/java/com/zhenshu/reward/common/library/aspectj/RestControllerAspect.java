package com.zhenshu.reward.common.library.aspectj;

import com.zhenshu.reward.common.utils.json.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/8 16:55
 * @desc Controller日志切面
 */
@Slf4j
@Aspect
@Order(2)
@Component
public class RestControllerAspect {
    @Around(value = "@within(restController)")
    public Object doAround(ProceedingJoinPoint joinPoint, RestController restController) throws Throwable {
        Object[] args = joinPoint.getArgs();
        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed(args);
        } catch (Throwable e) {
            try {
                ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                HttpServletRequest request = servletRequestAttributes.getRequest();
                log.error("请求URI: {}; useTime: {}ms; 入参: {}; 异常描述: {};", request.getRequestURI(), System.currentTimeMillis() - start, JsonUtil.toJson(args), e.getMessage());
            } catch (Exception ex) {
                log.error("RestControllerAspect切面日志打印异常, 原因: {};", ex.getMessage());
            }
            throw e;
        }
    }
}
