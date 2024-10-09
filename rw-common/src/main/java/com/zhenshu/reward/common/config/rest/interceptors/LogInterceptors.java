package com.zhenshu.reward.common.config.rest.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * @author xyh
 * @version 1.0
 * @date 2023/2/28 15:41
 * @desc 日志打印拦截器
 */
@Slf4j
public class LogInterceptors implements ClientHttpRequestInterceptor {
    /**
     * 拦截方法
     *
     * @param httpRequest                request对象, 包含请求头, 请求方法, URL等参数
     * @param bytes                      body请求体
     * @param clientHttpRequestExecution 执行目标方法对象
     * @return 返回结果
     * @throws IOException 超时异常
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        long startTime = System.currentTimeMillis();
        ClientHttpResponse response = null;
        try {
            // 往下执行
            response = clientHttpRequestExecution.execute(httpRequest, bytes);
            return response;
        } finally {
            // 打印执行时长
            long endTime = System.currentTimeMillis();
            long runTime = endTime - startTime;
            URI uri = httpRequest.getURI();
            // 获取Body参数
            String body = new String(bytes);
            // 获取Query参数
            String query = uri.getQuery();
            // 获取响应
            String responseStr = null;
            if (response != null) {
                responseStr = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
            }
            int port = uri.getPort();
            log.info(
                    "Method:{}, URI:{}, 耗时:{}ms\n\rQuery:{}\n\rBody:{}\n\rResponse:{}",
                    httpRequest.getMethod(),
                    uri.getScheme() + "://" + uri.getHost() + (port == -1 ? "" : ":" + port) + uri.getPath(),
                    runTime,
                    query,
                    body,
                    responseStr
            );
        }
    }
}
