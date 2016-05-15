package AnyQuantProject.data.util;

import AnyQuantProject.util.exception.NetFailedException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * Created by G on 16/5/15.
 */
public class News {
    //创建http client
    private static CloseableHttpClient httpClient = createHttpsClient();
    private static final String ACCESS_TOKEN = "a426949d7db2b7a49da5d4a65d171eeb687ecdb8c07ab1dad6a38ffcd7818f2a";

    public static void main(String[] args)  {

        try {
            System.out.println(getNewsJsonString());
        } catch (NetFailedException e) {
            System.out.println("i'll handle it");
        }
    }

    public static String getNewsJsonString() throws NetFailedException {
        String url = "https://api.wmcloud.com:443/data/v1/api/subject/getNewsInfoByTime.json?field=&beginTime=&endTime=&newsPublishDate=20150101";
        HttpGet httpGet = new HttpGet(url);
        //在header里加入 Bearer {token}，添加认证的token，并执行get请求获取json数据
        httpGet.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        CloseableHttpResponse response = null;
        String body = new String();
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            body = EntityUtils.toString(entity);
            System.out.println(body);
        } catch (IOException e) {
            throw new NetFailedException("TL net connect failed");
        }
        return body;
    }



    //创建http client
    public static CloseableHttpClient createHttpsClient() {
        X509TrustManager x509mgr = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] xcs, String string) {
            }
            @Override
            public void checkServerTrusted(X509Certificate[] xcs, String string) {
            }
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        //因为java客户端要进行安全证书的认证，这里我们设置ALLOW_ALL_HOSTNAME_VERIFIER来跳过认证，否则将报错
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{x509mgr}, null);
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslContext,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}