/**
 * 
 */
package AnyQuantProject.data.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * @author G
 *
 */
public class APIHelper {
	
	 public static final String DEF_CHATSET = "UTF-8";
	 public static final int DEF_CONN_TIMEOUT = 30000;
	 public static final int DEF_READ_TIMEOUT = 30000;
	 public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
	 public static final String APPKEY ="b073f394420a3c09c19d09a2e42dae70";
	
	 
	 
	public JSONArray getAnyAPI(String key) throws IOException {
		JSONArray jsonArray = null;
		
//		System.out.println("key:   "+key);
		URL url = new URL(key);
		URLConnection conn = url.openConnection();
		conn.setRequestProperty("X-Auth-Code", "2069376c8bdb54297e71a564833e2770");
		conn.connect();
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String str = br.readLine();
		JSONObject jsonObject = JSONObject.fromObject(str);
		jsonArray = JSONArray.fromObject(jsonObject.get("data".toString()));
			
		System.out.println("apihelper  anyquant :   "+str);
	
		return jsonArray;
	}
	
	
	
	/**
	 * 由单只股票的代号名得到中文名
	 * @param stockkey
	 * @return
	 * @throws Exception
	 */
	public String getSingleStockChineseName(String stockkey) {
		 String name = new String();
		 String result =null;
	     String url ="http://web.juhe.cn:8080/finance/stock/hs";//请求接口地址
	     Map params = new HashMap();//请求参数
	     params.put("gid",stockkey);//股票编号，上海股市以sh开头，深圳股市以sz开头如：sh601009
         params.put("key",APPKEY);//APP Key
	 
         try {
			result =net(url, params, "GET");
		} catch (Exception e) {
			System.out.println("juhe getname failed!");
			e.printStackTrace();
		}
	     JSONObject object = JSONObject.fromObject(result);
	     JSONArray resulto = (JSONArray) object.get("result");
	     if (resulto.size() != 0) {
	    	 JSONArray jsonArray = JSONArray.fromObject(((JSONObject) resulto.get(0)).get("data".toString()));
		     JSONObject jo = (JSONObject) jsonArray.get(0);
		     name = jo.getString("name");
		}
	     
//	     if(object.getInt("error_code")==0){
//	    	 System.out.println("success");
//	     }else{
//	         System.out.println(object.get("error_code")+":"+object.get("reason"));
//	     }
	     return name;
	   
	}
	
	public static void main(String[] args) {
		APIHelper a = new APIHelper();
		try {
			a.getSingleStockChineseName("sh601009");
			a.getAnyAPI("http://121.41.106.89:8010/api/stock/sh600000/?start=2016-03-10&end=2016-03-10&fields=open+high+close");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 /**
    *
    * @param strUrl 请求地址
    * @param params 请求参数
    * @param method 请求方法
    * @return  网络请求字符串
    * @throws Exception
    */
   private static String net(String strUrl, Map params,String method) throws Exception {
       HttpURLConnection conn = null;
       BufferedReader reader = null;
       String rs = null;
       try {
           StringBuffer sb = new StringBuffer();
           if(method==null || method.equals("GET")){
               strUrl = strUrl+"?"+urlencode(params);
           }
           URL url = new URL(strUrl);
           conn = (HttpURLConnection) url.openConnection();
           if(method==null || method.equals("GET")){
               conn.setRequestMethod("GET");
           }else{
               conn.setRequestMethod("POST");
               conn.setDoOutput(true);
           }
           conn.setRequestProperty("User-agent", userAgent);
           conn.setUseCaches(false);
           conn.setConnectTimeout(DEF_CONN_TIMEOUT);
           conn.setReadTimeout(DEF_READ_TIMEOUT);
           conn.setInstanceFollowRedirects(false);
           conn.connect();
           if (params!= null && method.equals("POST")) {
               try {
                   DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                       out.writeBytes(urlencode(params));
               } catch (Exception e) {
                   // TODO: handle exception
               }
           }
           InputStream is = conn.getInputStream();
           reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
           String strRead = null;
           while ((strRead = reader.readLine()) != null) {
               sb.append(strRead);
           }
           rs = sb.toString();
       } catch (IOException e) {
           e.printStackTrace();
       } finally {
           if (reader != null) {
               reader.close();
           }
           if (conn != null) {
               conn.disconnect();
           }
       }
       return rs;
   }

   //将map型转为请求参数型
   private static String urlencode(Map<String,Object>data) {
       StringBuilder sb = new StringBuilder();
       for (Map.Entry i : data.entrySet()) {
           try {
               sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
           } catch (UnsupportedEncodingException e) {
               e.printStackTrace();
           }
       }
       return sb.toString();
   }
	
}
