package com.zhenshu.reward.common.web;

import com.zhenshu.reward.common.constant.HttpStatus;
import com.zhenshu.reward.common.constant.enums.ErrorEnums;
import com.zhenshu.reward.common.constant.exception.Error;
import com.zhenshu.reward.common.constant.exception.ServiceException;
import com.zhenshu.reward.common.utils.ServletUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Slf4j
@Data
public class Result<T> {
    /**
     * 1.status状态值：代表本次请求response的状态结果。
     */
    private Integer code;
    /**
     * 2.response描述：对本次状态码的描述。
     */
    private String msg;
    /**
     * 3.data数据：本次返回的数据。
     */
    private T data;

    private static void logFailMsg(Integer code, String msg) {
        if (Objects.equals(code, ErrorEnums.SUCCESS.getCode())) {
            return;
        }
        String uri = "";
        String body = "";
        try {
            uri = ServletUtils.getRequest().getRequestURI();
            HttpServletRequest request = ServletUtils.getRequest();
            ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
            if (wrapper != null) {
                body = new String(wrapper.getContentAsByteArray());
            }
        } catch (Exception e) {
            log.error("body获取失败", e);
        }
        log.warn(String.format("接口返回状态码非200, url: %s, code: %d , msg: %s, body: %s", uri, code, msg, body));
    }

    private Result<T> then(Integer code, String desc, T data) {
        this.setCode(code);
        this.setMsg(desc);
        this.setData(data);
        logFailMsg(this.getCode(), this.getMsg());
        return this;
    }

    /**
     * 成功，创建ResResult：有data数据
     */
    public Result<T> success(T data) {
        return this.then(ErrorEnums.SUCCESS.getCode(), ErrorEnums.SUCCESS.getMsg(), data);
    }

    /**
     * 成功，创建ResResult：有data数据
     */
    public Result<T> success() {
        return this.success(null);
    }

    /**
     * 成功，创建ResResult：有data数据
     */
    public Result<T> fail() {
        return this.fail(HttpStatus.ERROR, null);
    }

    /**
     * 失败，指定status、desc
     */
    public Result<T> fail(Integer code, String desc) {
        return this.then(code, desc, null);
    }

    /**
     * 失败，指定status、desc
     */
    public Result<T> fail(String desc) {
        return this.fail(HttpStatus.ERROR, desc);
    }

    /**
     * 构建
     */
    public static <T> Result<T> build(Integer code, String desc) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(desc);
        logFailMsg(result.getCode(), result.getMsg());
        return result;
    }

    /**
     * 构建
     */
    public static <T> Result<T> build(Error errorEnums) {
        return build(errorEnums.getCode(), errorEnums.getMsg());
    }

    /**
     * 成功，创建ResResult：有data数据
     */
    public static <T> Result<T> buildSuccess(T data) {
        Result<T> result = build(ErrorEnums.SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     * 成功，创建ResResult
     */
    public static <T> Result<T> buildSuccess() {
        return build(ErrorEnums.SUCCESS);
    }

    /**
     * 失败
     */
    public static <T> Result<T> buildFail(Error errorEnums) {
        return build(errorEnums);
    }

    /**
     * 失败
     */
    public static <T> Result<T> buildFail(Integer code, String desc) {
        return build(code, desc);
    }

    /**
     * 失败
     */
    public static <T> Result<T> buildFail(String desc) {
        return build(ErrorEnums.INNER_ERROR.getCode(), desc);
    }

    /**
     * 失败
     */
    public static <T> Result<T> buildFail(T data) {
        Result<T> result = build(ErrorEnums.INNER_ERROR);
        result.setData(data);
        return result;
    }

    /**
     * 失败
     */
    public static <T> Result<T> buildFail(ServiceException serviceException) {
        return build(serviceException.getCode(), serviceException.getMessage());
    }
}

