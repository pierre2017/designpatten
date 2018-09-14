package com.study.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

public class HttpClientUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    private static final int CONN_REQ_TIMEOUT = 10000;
    private static final int SOCKET_TIMEOUT = 10000;
    private static final int CONN_TIMEOUT = 10000;

    public static final String HTTP_OK = "200";

    /**
     * 发送Post:json请求
     *
     * @param url
     * @param json
     * @throws Exception
     */
    public static String[] sendPostJson(String url, String json) throws Exception {
        String[] result = new String[2];
        CloseableHttpClient client = wrapClient();//HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(CONN_REQ_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectTimeout(CONN_TIMEOUT).build();// 设置请求和传输超时时间
        post.setConfig(requestConfig);
        StringEntity myEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
        post.setEntity(myEntity);
        CloseableHttpResponse resp = null;
        try {
            resp = client.execute(post);
            result[0] = String.valueOf(resp.getStatusLine().getStatusCode());
            if (resp.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = resp.getEntity();
                result[1] = EntityUtils.toString(entity);
            } else {
                logger.error("访问http接口响应异常" + resp.getStatusLine() + ","
                        + resp.getStatusLine().getReasonPhrase());
                result[1] = resp.getStatusLine().getStatusCode() + "";
            }
        } finally {
            HttpClientUtils.closeQuietly(resp);
        }
        return result;
    }

    /**
     * 服务端https证书有问题时使用
     * 客户端不用导入SSL证书
     */
    public static CloseableHttpClient wrapClient() {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                        throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                        throws CertificateException {
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(ctx,
                    NoopHostnameVerifier.INSTANCE);
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(ssf).build();
            return httpclient;
        } catch (Exception ex) {
            ex.printStackTrace();
            return HttpClients.createDefault();
        }
    }

    /**
     * 下载文件保存到本地
     *
     * @param path 文件保存位置
     * @param url  文件地址
     * @throws IOException
     */
    public static boolean downloadFile(String path, String url) {
        boolean success = true;
        logger.debug("path:" + path);
        logger.debug("url:" + url);
        CloseableHttpClient client = null;

        CloseableHttpResponse response = null;
        try {
            client = HttpClients.createDefault();
            // 获得HttpGet对象
            HttpGet httpGet = getHttpGet(url, null, null);
            if (httpGet == null)
                return false;
            // 发送请求获得返回结果
            response = client.execute(httpGet);
            // 如果成功
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                byte[] result = EntityUtils.toByteArray(response.getEntity());
                BufferedOutputStream bw = null;
                try {
                    // 创建文件对象
                    File f = new File(path);
                    // 创建文件路径
                    if (!f.getParentFile().exists())
                        f.getParentFile().mkdirs();
                    // 写入文件
                    bw = new BufferedOutputStream(new FileOutputStream(path));
                    bw.write(result);
                } catch (Exception e) {
                    logger.error("保存文件错误,path=" + path + ",url=" + url, e);
                    success = false;
                } finally {
                    try {
                        if (bw != null)
                            bw.close();
                    } catch (Exception e) {
                        logger.error(
                                "finally BufferedOutputStream shutdown close",
                                e);
                    }
                }
            }
            // 如果失败
            else {
                StringBuffer errorMsg = new StringBuffer();
                errorMsg.append("httpStatus:");
                errorMsg.append(response.getStatusLine().getStatusCode());
                errorMsg.append(response.getStatusLine().getReasonPhrase());
                errorMsg.append(", Header: ");
                Header[] headers = response.getAllHeaders();
                for (Header header : headers) {
                    errorMsg.append(header.getName());
                    errorMsg.append(":");
                    errorMsg.append(header.getValue());
                }
                logger.error("[http下载文件到本地]path:{},url:{},HttpResonse Error:{}", path, url, errorMsg);
                success = false;
            }
        } catch (ClientProtocolException e) {
            logger.error("[http下载文件到本地]下载文件保存到本地,http连接异常,path=" + path + ",url=" + url, e);
            success = false;
        } catch (IOException e) {
            logger.error("[http下载文件到本地]下载文件保存到本地,文件操作异常,path=" + path + ",url=" + url, e);
            success = false;
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (client != null) {
                    client.close();
                }
            } catch (Exception e) {
                logger.error("finally HttpClient shutdown error", e);
            }
        }
        return success;
    }

    /**
     * 获得HttpGet对象
     *
     * @param url    请求地址
     * @param params 请求参数
     * @param encode 编码方式
     * @return HttpGet对象
     */
    private static HttpGet getHttpGet(String url, Map<String, String> params,
                                      String encode) {
        StringBuffer buf = new StringBuffer(url);
        if (params != null) {
            // 地址增加?或者&
            String flag = (url.indexOf('?') == -1) ? "?" : "&";
            // 添加参数
            for (String name : params.keySet()) {
                buf.append(flag);
                buf.append(name);
                buf.append("=");
                try {
                    String param = params.get(name);
                    if (param == null) {
                        param = "";
                    }
                    buf.append(URLEncoder.encode(param, encode));
                } catch (UnsupportedEncodingException e) {
                    logger.error("URLEncoder Error,encode=" + encode + ",param="
                            + params.get(name), e);
                    return null;
                }
                flag = "&";
            }
        }
        HttpGet httpGet = new HttpGet(buf.toString());
        return httpGet;
    }

    public static void main(String[] args) throws Exception {
        String url = "https://pay.ehaier.com/paycenter/json/benefit/fallback.json";
        String json = "{\"appId\":\"1029023\"}";
        String[] sendPostJson = HttpClientUtil.sendPostJson(url, json);
        System.out.println("测试https返回报文：" + JSON.toJSONString(sendPostJson));
    }
}