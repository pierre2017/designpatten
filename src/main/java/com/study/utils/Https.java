package com.study.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

public class Https {

    private static Logger logger = LoggerFactory.getLogger(Https.class);

    public static final String HTTP_OK = "200";

    private static final int CONN_REQ_TIMEOUT = 5000;
    private static final int SOCKET_TIMEOUT = 5000;
    private static final int CONN_TIMEOUT = 5000;

    /**
     * 带证书的https post请求
     *
     * @param url
     * @throws Exception
     */
    public static String[] sendRequestWithCert(String url, String content, String encoding,
                                               String certFile, String certPwd) throws Exception {
        String[] strResult = new String[2];
        HttpClient httpClient = null;
        HttpResponse response = null;
        try {
            httpClient = getClientWithCert(certFile, certPwd);

            HttpPost httpPost = new HttpPost(url); // 创建HttpPost
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(CONN_REQ_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT)
                    .setConnectTimeout(CONN_TIMEOUT).build();// 设置请求和传输超时时间
            httpPost.setConfig(requestConfig);
            StringEntity se = new StringEntity(content, encoding);
            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/xml"));
            se.setContentEncoding(encoding);
            httpPost.setEntity(se);

            response = httpClient.execute(httpPost); // 执行POST请求
            int statusCode = response.getStatusLine().getStatusCode();
            strResult[0] = String.valueOf(statusCode);
            if (statusCode == 200) {
                // 若状态码为200 ok
                strResult[1] = EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            logger.info("请求错误：{}", e);
        } finally {
            if (httpClient != null) {
                HttpClientUtils.closeQuietly(httpClient);
            }
        }
        return strResult;

    }

    private static HttpClient getClientWithCert(String certFile, String certPwd) throws Exception {
        KeyStore clientStore = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File(certFile));
        clientStore.load(instream, certPwd.toCharArray());
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(clientStore, certPwd.toCharArray());

        KeyStore trustStore = KeyStore.getInstance("JKS");
        trustStore.load(new FileInputStream(System.getProperty("java.home") + File.separator + "lib" + File.separator + "security" + File.separator + "cacerts"), "changeit".toCharArray());
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(trustStore);
        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        SSLContext.setDefault(ctx);

        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(ctx,
                new String[]{"TLSv1", "SSLv3"}, null,
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        return httpclient;
    }


}
