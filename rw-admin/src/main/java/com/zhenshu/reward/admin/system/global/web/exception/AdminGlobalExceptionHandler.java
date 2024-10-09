package com.zhenshu.reward.admin.system.global.web.exception;

import com.zhenshu.reward.common.constant.HttpStatus;
import com.zhenshu.reward.common.utils.StringUtils;
import com.zhenshu.reward.common.web.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hong
 * @version 1.0
 * @date 2023/12/5 17:34
 * @desc
 */
@Slf4j
@RestControllerAdvice
public class AdminGlobalExceptionHandler {
    /**
     * 权限校验异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Result<Object> accessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        log.error(String.format("interface %s , AccessDeniedException ", request.getRequestURI()), e);
        return new Result<>().fail(HttpStatus.UNAUTHORIZED, StringUtils.format("请求访问：{}，无权限访问，无法访问系统资源", request.getRequestURI()));
    }
}
