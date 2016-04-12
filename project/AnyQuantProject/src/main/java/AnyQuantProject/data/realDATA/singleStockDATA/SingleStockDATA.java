/**
 * 
 */
package AnyQuantProject.data.realDATA.singleStockDATA;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import AnyQuantProject.data.util.Turnover;
import AnyQuantProject.dataService.realDATAService.stockListDATAService.TurnoverDATAService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import AnyQuantProject.data.jsonDATA.JSONSingleStockDATA;
import AnyQuantProject.data.util.APIHelper;
import AnyQuantProject.data.util.ChineseName;
import AnyQuantProject.dataService.jsonDATAService.JSONSingleStockDATAService;
import AnyQuantProject.dataService.realDATAService.singleStockDATAService.SingleStockDATAService;
import AnyQuantProject.dataStructure.Stock;

/**
 * @author G
 *
 */
public class SingleStockDATA implements SingleStockDATAService, TurnoverDATAService{

	APIHelper aHelper = new APIHelper();
	
	JSONSingleStockDATAService JSONSingleStock = new JSONSingleStockDATA();
	private static SingleStockDATA singleStockDATA;

	
	private SingleStockDATA() {
	}
	
	
	public static SingleStockDATA getInstance(){
		if ( singleStockDATA == null) {
			singleStockDATA = new SingleStockDATA();
		}
		return singleStockDATA;
	}
	
	
	@Override
	public Stock getOperation(String name, Calendar date) {
		JSONObject resultJsonObject = JSONSingleStock.getOperation(name, date);
		Stock result = (Stock) JSONObject.toBean(resultJsonObject,Stock.class);
		result.setName(name);
		result.setChinese(getChineseName(name));
		return result;
	}

	
	@Override
	public List<Stock> getStockAmongDate(String name, Calendar start,
			Calendar end) {
		JSONArray resultArray = JSONSingleStock.getSingleStockAmongDate(name, start, end);
		@SuppressWarnings("unchecked")
		List<Stock> resultList = JSONArray.toList(resultArray, new Stock(), new JsonConfig());
		for (int i = 0; i < resultList.size(); i++) {
			resultList.get(i).setName(name);
			resultList.get(i).setChinese(getChineseName(name));
		}
		return resultList;
	}


	private String getChineseName(String name) {

		return ChineseName.getChineseName(name);

	}
	@Override
	public double getTurnoverValue(String name) {
		double value = 0.0;
		try {
			value = Turnover.getTurnOverValue(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

	@Override
	public double getTotalShares(String name) {
		double totalShares = 0;
		try {
			String shares = Turnover.getShares(name);
			String[] strs = shares.split(" ");
			totalShares = Double.parseDouble(strs[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return totalShares;
	}

	@Override
	public double getNonrestFloatShares(String name) {
		double nonrestFloatShares = 0;
		try {
			String shares = Turnover.getShares(name);
			String[] strs = shares.split(" ");
			nonrestFloatShares = Double.parseDouble(strs[1]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nonrestFloatShares;
	}

	public static void main(String[] args) {
		SingleStockDATA s = new SingleStockDATA();

		System.out.println(s.getOperation("sh600216",Calendar.getInstance()).getVolume());
//		s.getStockAmongDate("sh600000", Calendar.getInstance(), Calendar.getInstance());
//		s.getTurnoverValue("sh600216");
//		Calendar c = Calendar.getInstance();
//		c.set(Calendar.YEAR, 2015);
//		Stock stock = s.getOperation("sh600000", c);
////		System.out.println(stock.getName());
//		System.out.println(stock.getClose());
	}


}
