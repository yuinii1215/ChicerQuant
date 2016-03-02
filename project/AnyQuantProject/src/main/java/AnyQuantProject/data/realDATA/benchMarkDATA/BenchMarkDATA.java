/**
 * 
 */
package AnyQuantProject.data.realDATA.benchMarkDATA;

import java.util.Calendar;
import java.util.List;

import org.json.JSONObject;

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
	
	@Override
	public List<BenchMark> getAllBenchMark() {
		// TODO Auto-generated method stub
		List<JSONObject> allBenchMarkList = JSONBenchMark.getAllBenchMark();
		List<BenchMark> resultList;
		for (JSONObject jo:allBenchMarkList) {
//			BenchMark temp = JSONObject.to
		}
		return null;
	}

	
	@Override
	public BenchMark getOperation(String name, Calendar date) {
		// TODO Auto-generated method stub
		JSONObject result = JSONBenchMark.getOperation(name, date);
		return null;
	}

	
	@Override
	public List<BenchMark> getBenchMarkAmongDate(String name, Calendar start,
			Calendar end) {
		// TODO Auto-generated method stub
		List<JSONObject> resultList = JSONBenchMark.getBenchMarkAmongDate(name, start, end);
		return null;
	}

	

}
