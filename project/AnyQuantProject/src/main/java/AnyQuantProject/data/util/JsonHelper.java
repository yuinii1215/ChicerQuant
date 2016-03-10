/**
 * 
 */
package AnyQuantProject.data.util;

import java.io.IOException;
import java.util.Calendar;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import AnyQuantProject.util.method.CalendarHelper;

/**
 * @author G
 *
 */
public class JsonHelper {
	
	APIHelper helper = new APIHelper();
	String keyheader = "http://121.41.106.89:8010/api/";
	String key;
	
	
	public JSONArray getAll(String Pkey){
		JSONArray jarr = new JSONArray();
		JSONArray result = new JSONArray();
		try {
			jarr = helper.getAnyAPI(keyheader+Pkey);
		} catch (IOException e) {
			return new JSONArray();
		}

		for (int i = 0; i < jarr.size(); i++) {
			JSONObject jo = (JSONObject) jarr.get(i);
			result.add(jo.get("name"));			
		}
		
		return result;
	}
	
	
	public JSONArray getAllWithChinese(String Pkey){
		JSONArray jarr = new JSONArray();
		JSONArray result = new JSONArray();
		try {
			jarr = helper.getAnyAPI(keyheader+Pkey);
		} catch (IOException e) {
			return new JSONArray();
		}

		for (int i = 0; i < jarr.size(); i++) {
			JSONObject jo = (JSONObject) jarr.get(i);
			String name = (String) jo.get("name");
			String nameWithChinese = name + " " + helper.getSingleStockChineseName(name);
			result.add(nameWithChinese);
			
		}
		
		return result;
	}
	
	public JSONObject getOperation(DataType type, String name, Calendar date) {
		key = getKeyWithDate(type, name, date, date);
		JSONObject jo = new JSONObject();
		try {
		
				
				jo = helper.getAnyAPI(keyheader+key).getJSONObject(0);
			
		} catch (IOException e) {
			return new JSONObject();
		}
		
		if (jo.size() != 0) {
			JSONArray arr = jo.getJSONArray("trading_info");
			return arr.getJSONObject(0);
		}else {
			return jo;
		}
		
	}
	
	public JSONArray getAmongDate(DataType type, String name, Calendar start,
			Calendar end) {
		key = getKeyWithDate(type, name, start, end);
//		System.out.println("jhelper  key : "+key);
		JSONObject jo = new JSONObject();
		try {
			jo = helper.getAnyAPI(keyheader+key).getJSONObject(0);
		} catch (IOException e) {
			return new JSONArray();
		}
		JSONArray result = jo.getJSONArray("trading_info");
		return result;
	}
	
	public String getKeyWithDate(DataType type, String name, Calendar start, Calendar end) {
//		Calendar previousdate = CalendarHelper.getPreviousDay(start);
		String startday = CalendarHelper.getDate(start);
		Calendar afterdate = CalendarHelper.getAfterDay(end);
		String afterday = CalendarHelper.getDate(afterdate);
		switch (type) {
		case BENCHMARK:
			return "benchmark/"+name+"?start="+startday+"&end="+afterday+"&fields=open+high+low+close+volume+adj_price";
		default:
			return "stock/"+name+"/?start="+startday+"&end="+afterday+"&fields=open+high+low+close+volume+adj_price+turnover+pe_ttm+pb";
		}
	}
	
	public static void main(String[] args) {
		JsonHelper j = new JsonHelper();
//		j.getAllWithChinese("stock/sh600000/?start=2016-02-01&end=2016-02-03&fields=open+high+close");
//		j.getAll("stock/sh600000/?start=2016-02-01&end=2016-02-03&fields=open+high+close");
		
		j.getOperation(DataType.BENCHMARK, "hs300", Calendar.getInstance());
	}

}
