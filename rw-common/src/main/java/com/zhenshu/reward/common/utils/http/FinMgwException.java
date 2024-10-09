package com.zhenshu.reward.common.utils.http;

/**
 * @author xyh
 * @version 1.0
 * @desc
 * @date 2020/12/10 0010 15:58
 **/
public class FinMgwException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Integer code;

    private String message;

    public FinMgwException(String message) {
        this.message = message;

    }

    public FinMgwException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public FinMgwException(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    public FinMgwException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }

    public String getErrMsg() {
        return message;
    }

    public Integer getCode() {
        return code;
    }


}
