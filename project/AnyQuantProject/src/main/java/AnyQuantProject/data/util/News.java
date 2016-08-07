package AnyQuantProject.data.util;

import AnyQuantProject.dataStructure.Cell;
import AnyQuantProject.util.exception.NetFailedException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by G on 16/5/15.
 */
public class News {
    //创建http client
    private static CloseableHttpClient httpClient = createHttpsClient();
    private static final String ACCESS_TOKEN = "a426949d7db2b7a49da5d4a65d171eeb687ecdb8c07ab1dad6a38ffcd7818f2a";

    public static int getDegree(String stockname) {
        int degree = 4;
        int rank = -1;
        try {
            Map<String,Double> map=getAllNewsHeatIndex();
            List<Cell> list=map.entrySet().stream().sorted((a,b)->{
                double aa=a.getValue();
                double bb=b.getValue();
                if (aa>bb)
                    return -1;
                else if(aa<bb)
                    return 1;
                else
                 return 0;
            }).map(e->new Cell(e.getKey(),e.getValue())).collect(Collectors.toList());
            for (int i = 0;i < list.size(); ++i){
                if (list.get(i).x.equals(stockname)){
                    rank = i;
                    break;
                }
            }

        } catch (NetFailedException e) {
            System.out.println("TL failed");
        }

        if (rank==-1){
            return degree;
        }else if (rank < 260){
            return 1;
        }else if (rank <520){
            return 2;
        }else {
            return 3;
        }
    }

    public static Map<String, Double> getAllNewsHeatIndex() throws NetFailedException{
        String stocknames = ChineseName.getAllStockNames();
        Map<String, Double> resultList = new HashMap<String, Double>();
        int start = 0;
        int partlen = 300;
        int len = stocknames.length();
        int end = start+7*partlen-1;
        while (end < len){
            String partnames = stocknames.substring(start,end);
            Map<String, Double> tempList = getNewsJsonString(partnames);
            resultList.putAll(tempList);
            start = end+1;
            end = start+7*partlen-1;
        }
        resultList.putAll(getNewsJsonString(stocknames.substring(start)));
        return resultList;

    }
    public static Map<String, Double> getNewsJsonString(String stocknames) throws NetFailedException {
        Map<String, Double> resultList = new HashMap<String, Double>();
        String url = "https://api.wmcloud.com:443/data/v1/api/subject/getNewsHeatIndex.json?field=&exchangeCD=&ticker="+stocknames+"&secShortName=&beginDate=20160525&endDate=20160525";
        HttpGet httpGet = new HttpGet(url);
        //在header里加入 Bearer {token}，添加认证的token，并执行get请求获取json数据
        httpGet.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        CloseableHttpResponse response = null;
        String body = new String();
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            body = EntityUtils.toString(entity);
//            System.out.println(body);

            //解析json得到各个股票heatIndex的值
            JSONObject jo = JSONObject.fromObject(body);
            JSONArray arr = JSONArray.fromObject(jo.get("data"));
            for(int i =0; i<arr.size();++i) {
                String ticker = (String) ((JSONObject) arr.get(i)).get("ticker");
                String exchangeName = (String) ((JSONObject) arr.get(i)).get("exchangeName");
                if (exchangeName.equals("深圳证券交易所")){
                    ticker = "sz"+ticker;
                }else {
                    ticker = "sh"+ticker;
                }
                Double heatIndex = (Double) ((JSONObject) arr.get(i)).get("heatIndex");
                resultList.put(ticker, heatIndex);
            }
        } catch (IOException e) {
            throw new NetFailedException("TL net connect failed");
        }

        return resultList;
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