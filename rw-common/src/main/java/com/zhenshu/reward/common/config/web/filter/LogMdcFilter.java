package com.zhenshu.reward.common.config.web.filter;

import cn.hutool.core.lang.UUID;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author xyh
 * @version 1.0
 * @date 2023/3/13 10:04
 * @desc
 */
@WebFilter(urlPatterns = "/*", filterName = "logMdcFilter")
@Order(1)
@Component
public class LogMdcFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            //traceId初始化
            MDC.put("trace_id", UUID.randomUUID().toString(true));
            //执行后续过滤器
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.clear();
        }
    }
}
