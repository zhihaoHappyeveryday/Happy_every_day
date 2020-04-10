package com.zhihao.common.orderpay.util;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhihao
 * @Date: 29/2/2020 下午 3:58
 * @Description: HTTP请求工具类 (http走的是默认Http客户端,https走的是SSL协议)
 * @Versions 1.0
 **/
public class HttpsUtils {

    //连接池管理器
    private static PoolingHttpClientConnectionManager connMgr;
    //请求配置
    private static RequestConfig requestConfig;
    //http请求重试处理程序
    private static HttpRequestRetryHandler httpRequestRetryHandler;
    //http客户端
    private static HttpClient httpClient;
    //最大超时时间
    private static final int MAX_TIMEOUT = 10000;

    private static Logger log = LoggerFactory.getLogger(HttpsUtils.class);

    private HttpsUtils() {
    }

    static {
        //初始化
        init();
    }

    /**
     * 发送 GET请求,不带参数
     *
     * @param url 地址
     * @return java.lang.String 结果字符串,自行json解析
     */
    public static String sendGetRequest(String url) {
        return sendGetRequest(url, new HashMap<String, Object>());
    }

    /**
     * 发送GET带参数请求 ，K-V形式
     *
     * @param url
     * @param params
     * @return java.lang.String 结果字符串,自行json解析
     */
    public static String sendGetRequest(String url, Map<String, Object> params) {
        StringBuffer param = new StringBuffer();
        //先添加请求链接
        param.append(url);
        int i = 0;
        for (String key : params.keySet()) {
            if (i == 0) {
                param.append("?");
            } else {
                param.append("&");
            }
            param.append(key).append("=").append(params.get(key));
            i++;
        }
        String resultString = null;
        //如果是发送https请求使用安全ssl构建
        if (param.toString().startsWith("https")) {
            httpClient = getSSLHttpClient();
        } else {
            httpClient = HttpClients.createDefault();
        }
        try {
            HttpGet httpGet = new HttpGet(param.toString());
            httpGet.setConfig(requestConfig);
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            resultString = EntityUtils.toString(entity);
        } catch (IOException e) {
            log.error("请求异常: " + getErrorString(e));
        }
        return resultString;
    }

    /**
     * 发送 POST 请求（HTTP），不带输入数据
     *
     * @param apiUrl 地址
     * @return java.lang.String 结果字符串,自行json解析
     * @date: 27/2/2020
     */
    public static String sendPostRequest(String apiUrl) {
        return sendPostRequest(apiUrl, new HashMap<String, Object>());
    }

    /**
     * 发送 支付 POST 请求（HTTPS)
     *
     * @param apiUrl 地址
     * @return java.lang.String 结果字符串,自行json解析
     * @date: 27/2/2020
     */
    public static String sendPayPostRequest(String apiUrl,String data) {
        //重新赋值客户端
        httpClient = getSSLHttpClient();
        String resultString = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        try {
            httpPost.setConfig(requestConfig);
            httpPost.setEntity(new StringEntity(data, Charset.forName("UTF-8")));
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            resultString = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            log.error("请求异常: " + getErrorString(e));
        }
        return resultString;
    }

    /**
     * 发送POST请求，K-V形式 表单形式
     *
     * @param apiUrl API接口URL
     * @param params 参数map
     * @return java.lang.String 结果字符串,自行json解析
     */
    public static String sendPostRequest(String apiUrl, Map<String, Object> params) {
        if (apiUrl.startsWith("https")) {
            httpClient = getSSLHttpClient();
        } else {
            httpClient = HttpClients.createDefault();
        }
        String resultString = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        try {
            httpPost.setConfig(requestConfig);
            //构造请求参数
            List<NameValuePair> pairList = new ArrayList<>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            resultString = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            log.error("请求异常: " + getErrorString(e));
        }
        return resultString;
    }

    /**
     * 发送json方式POST请求
     *
     * @param json   json对象
     * @param apiUrl 地址
     * @return java.lang.String 结果字符串,自行json解析
     */
    public static String sendJsonPostRequest(String apiUrl, Object json) {
        if (apiUrl.startsWith("https")) {
            httpClient = getSSLHttpClient();
        } else {
            httpClient = HttpClients.createDefault();
        }
        String resultString = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        try {
            httpPost.setConfig(requestConfig);
            StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");// 解决中文乱码问题
            //json请求
            httpPost.addHeader("content-type", "application/json;charset=UTF-8");
            httpPost.setEntity(stringEntity);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            resultString = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            log.error("请求异常: " + getErrorString(e));
        }
        return resultString;
    }

    /**
     * <p>
     * 获取HttpClient客户端进行自定义发送请求
     * </p>
     *
     * @return org.apache.http.client.HttpClient
     * @author: zhihao
     * @date: 29/2/2020
     */
    public static HttpClient getSSLHttpClient() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(createSSLConnSocketFactory())
                .setConnectionManager(connMgr)
                .setDefaultRequestConfig(requestConfig)
                .setRetryHandler(httpRequestRetryHandler)
                .build();
        return httpClient;
    }

    /**
     * 获取请求配置
     *
     * @return org.apache.http.client.config.RequestConfig
     * @author: zhihao
     * @date: 27/2/2020
     */
    public static RequestConfig getRequestConfig() {
        return requestConfig;
    }

    /**
     * 创建SSL安全连接
     *
     * @return
     */
    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();

            sslsf = new SSLConnectionSocketFactory(sslContext, new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (GeneralSecurityException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return sslsf;
    }

    /**
     * 初始化连接参数
     *
     * @return void
     * @date: 27/2/2020
     */
    private static void init() {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(100);
        //默认的每个路由的最大连接数
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
        // Validate connections after 1 sec of inactivity
        connMgr.setValidateAfterInactivity(1000);
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        //设置Cookie规范
        configBuilder.setCookieSpec(CookieSpecs.STANDARD_STRICT);
        requestConfig = configBuilder.build();
        //http请求重试处理程序
        httpRequestRetryHandler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception,
                                        int executionCount, HttpContext context) {
                // 如果已经重试了3次，就放弃
                if (executionCount >= 3) {
                    log.error("----链接已经重连了3次未能成功，将放弃->>");
                    return false;
                }
                // 如果服务器丢掉了连接，那么就重试
                if (exception instanceof NoHttpResponseException) {
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                    log.error("----不要重试SSL握手异常->>");
                    return false;
                }
                if (exception instanceof InterruptedIOException) {// 超时
                    log.error("----超时异常->>");
                    return false;
                }
                if (exception instanceof UnknownHostException) {// 目标服务器不可达
                    log.error("----目标服务器不可达异常->>");
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                    log.error("----连接被拒绝异常->>");
                    return false;
                }
                if (exception instanceof SSLException) {// SSL握手异常
                    log.error("----SSL握手异常->>");
                    return false;
                }

                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();

                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }
                return false;
            }
        };
    }

    /**
     * 将异常转换为字符串
     *
     * @param e 异常
     * @return java.lang.String
     * @author: zhihao
     * @date: 1/3/2020
     */
    private static String getErrorString(Throwable e) {
        if (null == e) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(e.getMessage());
        StackTraceElement[] stackTrace = e.getStackTrace();
        for (StackTraceElement traceElement : stackTrace) {
            builder.append("\n");
            builder.append(traceElement.toString());
        }
        return builder.toString();
    }
}