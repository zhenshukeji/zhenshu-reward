package com.zhenshu.reward.common.web.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hong
 * @version 1.0
 * @date 2024/6/3 16:14
 * @desc 时间戳
 */
public class TimestampInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.addHeader("Timestamp", String.valueOf(System.currentTimeMillis()));
        return true;
    }
}
