package com.zhenshu.reward.common.utils.http;

/**
 * @author xyh
 * @version 1.0
 * @desc 返回结果枚举
 * @date 2020/12/10 0010 15:53
 **/
public enum ResultCodeEnum {

    /**
     * 状态
     */
    SSL_BUILD_FAIL(0, "SSL build 失败"),
    HTTP_EXECUTE_EX(4001,"执行异常结束"),
    HTTP_STATUS_ERROR(4002,"返回的状态码非成功"),
    SOCKET_TIMEOUT_EX(4003,"socket网络连接超时"),
    CONN_TIMEOUT_EX(4005,"请求超时"),
    ;

    private int code;

    private String desc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    ResultCodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
