package com.zhenshu.reward.common.utils.http;


import java.util.Map;

/**
 * @author xyh
 * @version 1.0
 * @desc 返回结果封装
 * @date 2020/12/11 0011 13:57
 **/
public class HttpRspBO {

    private String bodyStr;

    private Map<String, String> headers;

    public String getBodyStr() {
        return bodyStr;
    }

    public void setBodyStr(String bodyStr) {
        this.bodyStr = bodyStr;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
