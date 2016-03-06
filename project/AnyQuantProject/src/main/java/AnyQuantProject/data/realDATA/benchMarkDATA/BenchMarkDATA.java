/**
 * 
 */
package AnyQuantProject.data.realDATA.benchMarkDATA;

import java.util.Calendar;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import AnyQuantProject.data.jsonDATA.JSONBenchMarkDATA;
import AnyQuantProject.dataService.jsonDATAService.JSONBenchMarkDATAService;
import AnyQuantProject.dataService.realDATAService.benchMarkDATAService.BenchMarkDATAService;
import AnyQuantProject.dataStructure.BenchMark;

/**
 * @author G
 *
 */
public class BenchMarkDATA implements BenchMarkDATAService{


	
	JSONBenchMarkDATAService JSONBenchMark = new JSONBenchMarkDATA();
	private static BenchMarkDATA benchMarkDATA;
	
	private BenchMarkDATA() {
	}
	
	public static BenchMarkDATA getInstance(){
		if ( benchMarkDATA == null) {
			benchMarkDATA = new BenchMarkDATA();
		}
		return benchMarkDATA;
	}
	
	@Override
	public List<String> getAllBenchMark() {
		JSONArray allBenchMarkList = JSONBenchMark.getAllBenchMark();
		@SuppressWarnings("unchecked")
		List<String> resultList = JSONArray.toList(allBenchMarkList, new String(), new JsonConfig());
		return resultList;
	}

	
	@Override
	public BenchMark getOperation(String name, Calendar date) {
		JSONObject result = JSONBenchMark.getOperation(name, date);
//		//滤去date属性
//		JsonConfig config = getJsonConfig();    
//	    result = JSONObject.fromObject(result,config); 
		BenchMark re = (BenchMark) JSONObject.toBean(result, BenchMark.class);
		re.setName(name);
		return re;
	}

	
	@Override
	public List<BenchMark> getBenchMarkAmongDate(String name, Calendar start,
			Calendar end) {
		JSONArray result = JSONBenchMark.getBenchMarkAmongDate(name, start, end);
		@SuppressWarnings("unchecked")
		List<BenchMark> resultList = JSONArray.toList(result, new BenchMark(), getJsonConfig());
		for (int i = 0; i < resultList.size(); i++) {
			resultList.get(i).setName(name);
		}
		return resultList;
	}

	

	private JsonConfig getJsonConfig() {
		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false);       
	    config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);     
	    config.setExcludes(new String[]{"date"});  
	    return config;
	}

	public static void main(String[] args) {
		BenchMarkDATA b = BenchMarkDATA.getInstance();
		List<String> list = b.getAllBenchMark();
		System.out.println("list  : " + list.size());
		System.out.println("list 0 :" + list.get(0));
//		b.getBenchMarkAmongDate("hs300", Calendar.getInstance(), Calendar.getInstance());
//		System.out.println("ben : "+ben.getOpen()+" "+ben.getClose());
	}
}
