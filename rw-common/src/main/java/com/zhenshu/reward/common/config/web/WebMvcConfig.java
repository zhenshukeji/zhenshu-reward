package com.zhenshu.reward.common.config.web;

import com.zhenshu.reward.common.config.web.interceptor.LogInterceptor;
import com.zhenshu.reward.common.user.interceptor.LoginTokenInterceptor;
import com.zhenshu.reward.common.web.interceptor.TimestampInterceptor;
import com.zhenshu.reward.common.web.knife4j.conver.IBaseEnumConverter;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author hualong
 * @version 1.0
 * @date 2022/3/25 11:56
 * @desc
 */
@Component
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private LogInterceptor logInterceptor;
    @Resource
    private LoginTokenInterceptor loginTokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginTokenInterceptor).addPathPatterns("/**");
        registry.addInterceptor(logInterceptor).addPathPatterns("/bk/**", "/wx/**", "/admin/**", "/h5/**");
        registry.addInterceptor(new TimestampInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        /**
         * 只能转换URL中的参数, 请求体中的无法转换
         */
        registry.addConverterFactory(new IBaseEnumConverter());
    }
}
