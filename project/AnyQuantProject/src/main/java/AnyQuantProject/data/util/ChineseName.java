/**
 * 
 */
package AnyQuantProject.data.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.*;
import AnyQuantProject.dataStructure.AbstractStock;
import AnyQuantProject.dataStructure.OperationResult;
import AnyQuantProject.util.constant.R;
import AnyQuantProject.util.method.IOHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author G
 *
 */

public class ChineseName {
	
	APIHelper aHelper = new APIHelper();

	
	public static void main(String[] args) {
		ChineseName i = new ChineseName();
//		i.iniChinese();
//		System.out.println(i.getChineseName("sh601186"));
        List<String> list = getAllChineseName();
        System.out.println(list.size());
        System.out.println(list.get(0));
	}
	public OperationResult iniChinese() {
		OperationResult result = new OperationResult();
		try {
			HashMap<String, String> list = getNameArray();
			result = IOHelper.save(R.CachePath, R.ChineseNameFile, (Serializable) list);
		} catch (IOException e) {
			return result = new OperationResult(false, "JUHE IOEXCEPTION ");
		}
		
		return result;
	}
	
	
	/**
	 * 从缓存的文件由单只股票的代号名得到中文名
	 * @param name
	 * @return
	 */
	public static String getChineseName(String name){
		HashMap<String, String> list = (HashMap<String, String>) IOHelper.read(R.CachePath, R.ChineseNameFile);
        if (list == null) {
            ChineseName c = new ChineseName();
            c.iniChinese();
        }
		return list.get(name);
	}
	


	/**
	 * 得到所有股票的中英文名构成的AbstractStock对象
	 * @return
	 * @throws IOException
	 */
	public HashMap<String, String> getNameArray() throws IOException	{
		//exchange=sh
		URL url = new URL("http://121.41.106.89:8010/api/stocks/?year=2016&exchange=sh");
		URLConnection connection = url.openConnection();
		connection.setRequestProperty("X-Auth-Code", "2069376c8bdb54297e71a564833e2770");
		connection.connect();
		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"GBK"));
		String s = br.readLine();
		JSONObject jo = JSONObject.fromObject(s);
		JSONArray arr = JSONArray.fromObject(jo.get("data"));
		HashMap<String, String> resultList = new HashMap<String, String>(2598);
		for (int i = 0; i < arr.size(); i++) {
			String name = (String) ((JSONObject)arr.get(i)).get("name");
			String chinese = aHelper.getSingleStockChineseName(name);
			resultList.put(name, chinese);
		}
		
		
		//exchange=sz
		url = new URL("http://121.41.106.89:8010/api/stocks/?year=2016&exchange=sz");
		connection = url.openConnection();
		connection.setRequestProperty("X-Auth-Code", "2069376c8bdb54297e71a564833e2770");
		connection.connect();
		br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"GBK"));
		s = br.readLine();
		jo = JSONObject.fromObject(s);
		arr = JSONArray.fromObject(jo.get("data"));
		for (int i = 0; i < arr.size(); i++) {
			String name = (String) ((JSONObject)arr.get(i)).get("name");
			String chinese = aHelper.getSingleStockChineseName(name);
			resultList.put(name, chinese);
		}
		
		return resultList;
	}

	public static List<String> getAllChineseName() {
		Map<String, String> list = (HashMap<String, String>) IOHelper.read(R.CachePath, R.ChineseNameFile);
		List<String> resultList = new ArrayList<>();
		Set entries = list.entrySet();
		if (entries != null) {
			Iterator iterator = entries.iterator();
			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry) iterator.next();
                String str = (String) entry.getKey();
                str = str + " " + entry.getValue();
                resultList.add(str);
			}
		}
		return resultList;
	}


}