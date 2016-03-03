/**
 * 
 */
package AnyQuantProject.data.realDATA.benchMarkDATA;

import java.util.Calendar;
import java.util.List;








import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
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
		JSONArray allBenchMarkList = JSONBenchMark.getAllBenchMark();
		@SuppressWarnings("unchecked")
		List<BenchMark> resultList = JSONArray.toList(allBenchMarkList, new BenchMark(), new JsonConfig());
		return resultList;
	}

	
	@Override
	public BenchMark getOperation(String name, Calendar date) {
		JSONObject result = JSONBenchMark.getOperation(name, date);
		BenchMark re = (BenchMark) JSONObject.toBean(result, BenchMark.class);
		return re;
	}

	
	@Override
	public List<BenchMark> getBenchMarkAmongDate(String name, Calendar start,
			Calendar end) {
		JSONArray result = JSONBenchMark.getBenchMarkAmongDate(name, start, end);
		@SuppressWarnings("unchecked")
		List<BenchMark> resultList = JSONArray.toList(result, new BenchMark(), new JsonConfig());
		return resultList;
	}

	

}
