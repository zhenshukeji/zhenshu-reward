package com.zhenshu.reward.common.utils.http;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.SocketConfig;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLException;
import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.util.*;

/**
 * @author chengbing
 * @desc http 连接工具类
 * https json post 请求用 doSSlPostRsp接口
 */
@Setter
@Slf4j
@Service
public class HttpConnector {

    private static HttpClient httpClient;
    /**
     * 最大连接数
     */
    private static final int MAX_CONNECTION = 200;
    /**
     * 每个route能使用的最大连接数，一般和MAX_CONNECTION取值一样
     */
    private static final int MAX_CONCURRENT_CONNECTIONS = 200;
    /**
     * 建立连接的超时时间，单位毫秒
     */
    private static final int CONNECTION_TIME_OUT = 2000;
    /**
     * 请求超时时间，单位毫秒
     */
    private static final int REQUEST_TIME_OUT = 5000;
    /**
     * 最大失败重试次数
     */
    private static final int MAX_FAIL_RETRY_COUNT = 0;

    static {
        SocketConfig socketConfig = SocketConfig.custom()
                .setSoTimeout(REQUEST_TIME_OUT).setSoKeepAlive(true)
                .setTcpNoDelay(true).build();
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(REQUEST_TIME_OUT)
                .setConnectTimeout(CONNECTION_TIME_OUT).build();
        // 每个默认的 ClientConnectionPoolManager 实现将给每个route创建不超过 max_connection 个并发连接，最多20个连接总数。
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(MAX_CONNECTION);
        connManager.setDefaultMaxPerRoute(MAX_CONCURRENT_CONNECTIONS);
        connManager.setDefaultSocketConfig(socketConfig);
        httpClient = HttpClients.custom()
                .setConnectionManager(connManager)
                .setDefaultRequestConfig(requestConfig)
                // 添加重试处理器
                .setRetryHandler(new MyHttpRequestRetryHandler()).build();
    }


    /**
     * 发送http get 请求，带请求头参数
     *
     * @param url     接口地址
     * @param headers 参数头
     * @return 响应数据
     */
    public String doGet(String url, Map<String, String> headers, Map<String, String> params) throws IOException {
        url = addUrlParam(url, params);
        HttpGet httpGet = new HttpGet(url);
        return doRequest(headers, httpGet);
    }

    /**
     * 转拼接参数
     *
     * @param url    接口地址
     * @param params 参数
     * @return 拼接后的参数
     */
    public String addUrlParam(String url, Map<String, String> params) {
        if (MapUtils.isNotEmpty(params)) {
            StringBuilder sb = new StringBuilder(url);
            sb.append("?");
            for (String key : params.keySet()) {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
            url = sb.substring(0, sb.length() - 1);
        }
        return url;
    }


    /**
     * 发送http get 请求，带请求头参数
     *
     * @param url    接口地址
     * @param params 参数
     * @return 响应数据
     */
    public String doGet(String url, Map<String, String> params) throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-type", "application/json;charset=utf-8");
        return doGet(url, headers, params);
    }

    /**
     * 发送http get 请求，带请求头参数
     *
     * @param url 接口地址
     * @return 响应数据
     */
    public String doGet(String url) throws IOException {
        return doGet(url, null, null);
    }


    /**
     * 发送带参数和请求头的post请求
     *
     * @param url      接口地址
     * @param headers  请求头
     * @param postJson 传参
     * @return 响应数据
     */
    public String doPost(String url, Map<String, String> headers, String postJson) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        if (postJson != null) {
            httpPost.setEntity(new StringEntity(postJson, Consts.UTF_8));
        }
        return doRequest(headers, httpPost);
    }


    /**
     * 发送带参数post请求
     *
     * @param url      接口地址
     * @param postJson 传参
     * @return 响应数据
     */
    public String doPost(String url, String postJson) throws IOException {
        return doPost(url, null, postJson);
    }

    /**
     * 发送带参数post请求 表单请求头
     *
     * @param url  接口地址
     * @param data 传参
     * @return 响应数据
     */
    public String doFormPost(String url, String data) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setEntity(new StringEntity(data, Consts.UTF_8));
        Map<String, String> headers = new HashMap<>();
        return doRequest(headers, httpPost);
    }

    /**
     * 发送带参数post请求 表单请求头
     *
     * @param url   接口地址
     * @param param 传参
     * @return 响应数据
     */
    public String doFormPost(String url, Map<String, String> param) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setEntity(new StringEntity(buildQuery(param, "utf-8"), Consts.UTF_8));
        Map<String, String> headers = new HashMap<>();
        return doRequest(headers, httpPost);
    }

    public static String buildQuery(Map<String, String> params, String charset) {

        List<NameValuePair> nvps = new LinkedList<NameValuePair>();

        Set<Map.Entry<String, String>> entries = params.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return URLEncodedUtils.format(nvps, charset);
    }

    /**
     * 发送带参数post请求 表单请求头
     *
     * @param url      接口地址
     * @param formData 传参
     * @return 响应数据
     */
    public String doFormPost(String url, Map<String, String> headers, String formData) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        if (formData != null) {
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
            httpPost.setEntity(new StringEntity(formData, Consts.UTF_8));
        }
        return doRequest(headers, httpPost);
    }

    /**
     * 发送带参数post请求
     *
     * @param url 接口地址
     * @return 响应数据
     */
    public String doPost(String url) throws IOException {
        return doPost(url, null, null);
    }

    /**
     * 发送带文件和请求头的post请求
     *
     * @param url      接口地址
     * @param headers  请求头
     * @param file     文件
     * @param fileName 文件名
     * @return 响应数据
     */
    public String doPost(String url, Map<String, String> headers, File file, String fileName) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        if (file != null) {
            httpPost.setEntity(assemblyFileEntity(file, fileName));
        }
        return doRequest(headers, httpPost);
    }

    /**
     * 生成文件请求包
     *
     * @param file 文件
     * @return http 请求体
     */
    protected HttpEntity assemblyFileEntity(File file, String fileName) {
        MultipartEntityBuilder build = MultipartEntityBuilder.create();
        build.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        build.addBinaryBody("file", file);
        build.addTextBody("filename", StringUtils.isBlank(fileName) ? file.getName() : fileName);
        return build.build();
    }

    /**
     * 发送请求
     *
     * @param headers     请求头
     * @param httpRequest request
     * @return 响应数据
     */
    private String doRequest(Map<String, String> headers, HttpRequestBase httpRequest) throws IOException {
        HttpRspBO rspBO = doRequestRsp(headers, httpRequest);
        return rspBO.getBodyStr();
    }


    /**
     * 发送request 请求
     *
     * @param headers     请求头
     * @param httpRequest 注意这个方法需要自己关闭
     *                    finally {
     *                    httpRequest.releaseConnection();
     *                    }
     * @return 带请求头的响应数据封装
     */
    private HttpRspBO doRequestRsp(Map<String, String> headers, HttpRequestBase httpRequest) throws IOException {
        if (headers != null) {
            for (String key : headers.keySet()) {
                httpRequest.addHeader(key, headers.get(key));
            }
        }
        try {
            HttpResponse response = httpClient.execute(httpRequest);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpRspBO bo = new HttpRspBO();
                bo.setBodyStr(getEntity(response));
                HashMap<String, String> map = new HashMap<>();
                Header[] rspHeaders = response.getAllHeaders();
                int i = 0;
                while (i < rspHeaders.length) {
                    map.put(rspHeaders[i].getName(), rspHeaders[i].getValue());
                    i++;
                }
                bo.setHeaders(map);
                return bo;
            } else {
                String entity = getEntity(response);
                log.info("result msg " + entity);
                throw new FinMgwException("");
            }
        } finally {
            httpRequest.releaseConnection();
        }
    }


    /**
     * 解析返回请求体
     *
     * @param response 返回参数
     * @return 字符串类型的请求体
     */
    public String getEntity(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        if (entity == null) {
            throw new FinMgwException(ResultCodeEnum.HTTP_EXECUTE_EX.getCode(),
                    ResultCodeEnum.HTTP_EXECUTE_EX.getDesc() + ", http response entity is null.");
        }
        String result;
        // 去掉首尾的 ""
        result = EntityUtils.toString(entity, Consts.UTF_8);
        String delStr = "\"";
        if (result.indexOf(delStr) == 0) {
            result = result.substring(1);
        }
        if (result.lastIndexOf(delStr) == result.length() - 1) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    /**
     * 将参数转换为form data传参
     *
     * @param param map集合
     * @return form data
     */
    public String fromData(HashMap<String, String> param) {
        StringJoiner joiner = new StringJoiner("&");
        for (Map.Entry<String, String> map : param.entrySet()) {
            String data = String.format("%s=%s", map.getKey(), map.getValue());
            joiner.add(data);
        }
        return joiner.toString();
    }

    /**
     * 请求重试处理器
     *
     * @author manzhizhen
     */
    private static class MyHttpRequestRetryHandler implements
            HttpRequestRetryHandler {

        @Override
        public boolean retryRequest(IOException exception, int executionCount,
                                    HttpContext context) {
            if (executionCount >= MAX_FAIL_RETRY_COUNT) {
                return false;
            }

            if (exception instanceof InterruptedIOException) {
                // 超时
                return false;
            }
            if (exception instanceof UnknownHostException) {
                // 未知主机
                return false;
            }
            if (exception instanceof SSLException) {
                // SSL handshake exception
                return false;
            }

            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            // 如果请求被认为是幂等的，则重试
            // Retry if the request is considered idempotent
            // 如果请求类型不是HttpEntityEnclosingRequest，被认为是幂等的，那么就重试
            // HttpEntityEnclosingRequest指的是有请求体的request，比HttpRequest多一个Entity属性
            // 而常用的GET请求是没有请求体的，POST、PUT都是有请求体的
            // Rest一般用GET请求获取数据，故幂等，POST用于新增数据，故不幂等
            return !(request instanceof HttpEntityEnclosingRequest);
        }
    }

}
