package AnyQuantProject.data.util;

/**
 * Created by G on 16/4/4.
 */
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.EncoderException;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Turnover {
    //创建http client
    private static CloseableHttpClient httpClient = createHttpsClient();
    private static final String ACCESS_TOKEN = "a426949d7db2b7a49da5d4a65d171eeb687ecdb8c07ab1dad6a38ffcd7818f2a";

    public static void main(String[] args) throws IOException, EncoderException {
//       System.out.println("turnovervalue"+ getTurnOverValue("sh600121"));
       System.out.println(getShares("sh600216"));
    }

    public static String getShares(String name) throws IOException{
        double totalShares = 0;
        double nonrestFloatShares = 0;
        String result = new String();
        name = name.substring(2);
        String url = "https://api.wmcloud.com:443/data/v1/api/equity/getEqu.json?field=totalShares,nonrestFloatShares&listStatusCD=&secID=&ticker="+name+"&equTypeCD=A";
        HttpGet httpGet = new HttpGet(url);
        //在header里加入 Bearer {token}，添加认证的token，并执行get请求获取json数据
        httpGet.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);
        System.out.println(body);

        //解析json格式的数据得到行业名字
        JSONObject jo = JSONObject.fromObject(body);
        JSONArray arr = JSONArray.fromObject(jo.get("data"));
        if (! arr.get(0).getClass().equals(net.sf.json.JSONNull.class)) {
            totalShares = (Integer) ((JSONObject) arr.get(0)).get("totalShares");
            nonrestFloatShares = (Integer) ((JSONObject) arr.get(0)).get("nonrestFloatShares");
        }
        result = totalShares + " " + nonrestFloatShares;
        return result;
    }


    /**
     * 由股票的代号得到股票成交金额
     * @param name
     * @return
     */
    public static Double getTurnOverValue(String name) throws IOException {
        //根据api store页面上实际的api url来发送get请求，获取数据
        double value = 0;
        name = name.substring(2);
        Calendar c = Calendar.getInstance();
        Date d = c.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
        String date = sdf.format(d.getTime());
        String url = "https://api.wmcloud.com:443/data/v1/api/market/getMktEqud.json?field=ticker,tradeDate,turnoverValue&beginDate=&endDate=&secID=&ticker="+name+"&tradeDate="+date;
        HttpGet httpGet = new HttpGet(url);
        //在header里加入 Bearer {token}，添加认证的token，并执行get请求获取json数据
        httpGet.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);
        System.out.println(body);

        //解析json格式的数据得到行业名字
        JSONObject jo = JSONObject.fromObject(body);
        JSONArray arr = JSONArray.fromObject(jo.get("data"));
        if (! arr.get(0).getClass().equals(net.sf.json.JSONNull.class)) {
            String turnvalue =  ((JSONObject) arr.get(0)).get("turnoverValue").toString();
            if (!turnvalue.contains(".")){
                turnvalue+=".0";
            }
            value = Double.parseDouble(turnvalue);
        }

        return value;
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