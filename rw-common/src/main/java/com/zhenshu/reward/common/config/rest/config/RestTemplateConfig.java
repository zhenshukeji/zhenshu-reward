package com.zhenshu.reward.common.config.rest.config;

import com.zhenshu.reward.common.config.rest.interceptors.LogInterceptors;
import com.zhenshu.reward.common.config.rest.properties.RestTemplateProperties;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * @author xyh
 * @version 1.0
 * @date 2023/2/28 9:38
 * @desc RestTemplate配置类
 */
@Configuration
@EnableConfigurationProperties(value = {RestTemplateProperties.class})
public class RestTemplateConfig {
    @Resource
    private RestTemplateProperties restTemplateProperties;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder, RestTemplateCustomizer customizer) {
        return builder
                // 添加拦截器
                .additionalInterceptors(new LogInterceptors())
                // 添加定制器, 定制器会在build的最后一步执行, 可能会覆盖某些属性
                .additionalCustomizers(customizer)
                .build();
    }

    @Bean
    public RestTemplateCustomizer restTemplateCustomizer(HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory) {
        // RestTemplate定制器, 通过回调的方式对RestTemplate进行定制化
        // 如果你需要创建多个RestTemplate, 但是希望他们都使用同一个连接池, 可以使用这种方式
        return restTemplate -> {
            // BufferingClientHttpRequestFactory包装RequestFactory用来支持多次调用ClientHttpResponse#getBody()方法
            BufferingClientHttpRequestFactory bufferingClientHttpRequestFactory =
                    new BufferingClientHttpRequestFactory(httpComponentsClientHttpRequestFactory);
            restTemplate.setRequestFactory(bufferingClientHttpRequestFactory);
        };
    }

    @Bean
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
        if (restTemplateProperties.isSsl()) {
            return new PoolingHttpClientConnectionManager();
        } else {
            // SSL证书校验关闭
            final SSLConnectionSocketFactory sslsf;
            try {
                TrustStrategy acceptingTrustStrategy = (x509Certificates, authType) -> true;
                SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
                sslsf = new SSLConnectionSocketFactory(sslContext,
                        NoopHostnameVerifier.INSTANCE);
            } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
                throw new RuntimeException(e);
            }
            final Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", new PlainConnectionSocketFactory())
                    .register("https", sslsf)
                    .build();

            //创建连接管理器
            return new PoolingHttpClientConnectionManager(registry);
        }
    }

    @Bean
    public HttpClient httpClient(PoolingHttpClientConnectionManager poolingHttpClientConnectionManager) {
        RestTemplateProperties.HttpClientProperties httpClient = restTemplateProperties.getHttpClient();
        //连接池最大连接数
        poolingHttpClientConnectionManager.setMaxTotal(httpClient.getMaxTotal());
        //单个域名的最大并发数
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(httpClient.getDefaultMaxPerRoute());

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager)
                // 禁用重试
                .disableAutomaticRetries();

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(httpClient.getConnectionRequestTimeout())
                .setConnectTimeout(httpClient.getConnectTimeout())
                .setSocketTimeout(httpClient.getSocketTimeout())
                .build();

        //创建httpClient
        return httpClientBuilder
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory(HttpClient httpClient) {
        RestTemplateProperties.HttpClientProperties httpClientProperties = restTemplateProperties.getHttpClient();
        //创建HttpComponentsClientHttpRequestFactory
        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory(httpClient);
        //客户端和服务器建立连接的timeout
        httpComponentsClientHttpRequestFactory.setConnectTimeout(httpClientProperties.getConnectTimeout());
        //客户端从服务器读取数据的timeout
        httpComponentsClientHttpRequestFactory.setReadTimeout(httpClientProperties.getSocketTimeout());
        //从连接池获取连接的timeout超出预设时间
        httpComponentsClientHttpRequestFactory.setConnectionRequestTimeout(httpClientProperties.getConnectionRequestTimeout());
        return httpComponentsClientHttpRequestFactory;
    }
}
