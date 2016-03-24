package AnyQuantProject.data.util;

import AnyQuantProject.dataService.IndustryNameDATAService;
import AnyQuantProject.dataStructure.OperationResult;
import AnyQuantProject.util.constant.R;
import AnyQuantProject.util.method.IOHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
<<<<<<< HEAD
import java.util.*;
=======
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
>>>>>>> 4a7a02a56f5145edcf7e64a67f426a95818dba95

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
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Set;

/**
 * Created by G on 16/3/24.
 */
public class IndustryName implements IndustryNameDATAService{

    APIHelper aHelper = new APIHelper();


    private static CloseableHttpClient httpClient = createHttpsClient();
    private static final String ACCESS_TOKEN = "e6d6d1f9e67a41a324a81b8c973548d82de6aab175e1cc3883f6f13b60f94543";


    public static void main(String[] args) {
        IndustryName i = new IndustryName();

//		i.iniIndustry();
//        System.out.println(i.getIndustryName("sh601186"));
        try {
            i.getIndustryNum();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public OperationResult iniIndustry() {
        OperationResult result = new OperationResult();
        try {
            Map<String, String> list = getIndustryNameArray();
            result = IOHelper.save(R.CachePath, R.IndustryNameFile, (Serializable) list);
        } catch (IOException e) {
            return result = new OperationResult(false, "Industry IOEXCEPTION ");
        }

        return result;
    }


    /**
     * 从缓存的文件由单只股票的代号名得到对应的行业
     * @param name
     * @return
     */
    public String getIndustryName(String name){
        Map<String, String> list = (Map<String, String>) IOHelper.read(R.CachePath, R.IndustryNameFile);
        return list.get(name);
    }



    /**
     * 得到所有股票的代号和行业对应的map
     * @return
     * @throws IOException
     */
    public Map<String, String> getIndustryNameArray() throws IOException	{

        Map<String, String> resultList = new HashMap<String, String>();

        List<String> stockNameList = (List<String>) IOHelper.read(R.CachePath, R.StockNameFile);
        for (int i = 0; i < stockNameList.size(); i++) {
            String temp = stockNameList.get(i);
            String subName = temp.substring(2);
            String url = "https://api.wmcloud.com:443/data/v1/api/equity/getEquIndustry.json?field=&industryVersionCD=010303&industry=&secID=&ticker="+subName+"&intoDate=";
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
                String industryName = (String) ((JSONObject) arr.get(0)).get("industryName1");
                resultList.put(stockNameList.get(i), industryName);
            }
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


    /**
     * 测试有多少个行业类别
     * @throws IOException
     */
    private void getIndustryNum() throws IOException{
        List<String> stockNameList = (List<String>) IOHelper.read(R.CachePath, R.StockNameFile);
        Set<String> count = new HashSet<String>();
        for (int i = 0; i < stockNameList.size(); i++) {
            String name = getIndustryName(stockNameList.get(i));
            count.add(name);
        }
        System.out.println(count.size());

    }
}
