package com.zhenshu.reward.common.web.exception;

import cn.hutool.core.util.ObjectUtil;
import com.zhenshu.reward.common.constant.enums.ErrorEnums;
import com.zhenshu.reward.common.constant.exception.ServiceException;
import com.zhenshu.reward.common.library.validation.exception.BeanValidationException;
import com.zhenshu.reward.common.web.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * 全局异常处理器
 *
 * @author zhenshu
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * handleException方法中忽略的异常集合
     */
    private static final Set<Class<? extends Exception>> IGNORE_EXCEPTIONS = new HashSet<>();

    static {
        // 请求方式不正确, POST请求使用了GET
//        IGNORE_EXCEPTIONS.add(HttpRequestMethodNotSupportedException.class);
//        // 请求头不正确, Content-Type错误
//        IGNORE_EXCEPTIONS.add(HttpMediaTypeNotSupportedException.class);
//        // 请求体不可阅读的, JSON字符串无法被解析
//        IGNORE_EXCEPTIONS.add(HttpMessageNotReadableException.class);
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public Result<Object> customerException(ServiceException e, HttpServletRequest request) {
        if (e.getCause() != null) {
            log.error(String.format("interface %s , ServiceException.Cause ", request.getRequestURI()), e);
        }
        if (ObjectUtil.isNull(e.getCode())) {
            return Result.buildFail(e.getMessage());
        }
        return Result.buildFail(e);
    }

    @ExceptionHandler(Throwable.class)
    public Result<Object> handleThrowable(Throwable e, HttpServletRequest request) {
        if (IGNORE_EXCEPTIONS.contains(e.getClass())) {
            return Result.buildFail("系统异常，请稍后重试");
        }
        log.error(String.format("interface %s , Exception ", request.getRequestURI()), e);
        return Result.buildFail("系统异常，请稍后重试");
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public Result<String> validatedBindException(BindException e, HttpServletRequest request) {
        log.error(String.format("interface %s , BindException ", request.getRequestURI()), e.getMessage());
        // TODO 开发环境返回错误描述
        return Result.buildFail(ErrorEnums.BAD_REQUEST.getCode(), e.getMessage());
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BeanValidationException.class)
    public Result<String> validatedBeanValidationException(BeanValidationException e, HttpServletRequest request) {
        log.error(String.format("interface %s , BeanValidationException ", request.getRequestURI()), e.getMessage());
        // TODO 开发环境返回错误描述
        return Result.buildFail(ErrorEnums.BAD_REQUEST.getCode(), e.getMessage());
    }
}
