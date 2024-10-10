package com.zhenshu.reward.common.constant.exception;

import com.zhenshu.reward.common.constant.enums.ErrorEnums;
import lombok.Getter;

import java.text.MessageFormat;

/**
 * 业务异常
 *
 * @author zhenshu
 */
@Getter
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误提示
     */
    private final String message;

    public ServiceException(String message, Integer code) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public ServiceException(String message, Integer code, Throwable throwable) {
        super(message, throwable);
        this.message = message;
        this.code = code;
    }

    public ServiceException(String message) {
        this(message, ErrorEnums.INNER_ERROR.getCode());
    }

    public ServiceException(String message, Throwable e) {
        this(message, ErrorEnums.INNER_ERROR.getCode(), e);
    }

    public ServiceException(Error errorEnums) {
        this(errorEnums.getMsg(), errorEnums.getCode());
    }

    public ServiceException(Error errorEnums, Object... params) {
        this(MessageFormat.format(errorEnums.getMsg(), params), errorEnums.getCode());
    }

    public ServiceException(Error errorEnums, Throwable e) {
        this(errorEnums.getMsg(), errorEnums.getCode(), e);
    }

    public ServiceException(Error errorEnums, Throwable e, Object... params) {
        this(MessageFormat.format(errorEnums.getMsg(), params), errorEnums.getCode(), e);
    }
}
