package AnyQuantProject.bl.benchMarkBL;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import AnyQuantProject.blService.benchMarkBLService.BenchMarkBLService;
import AnyQuantProject.data.factoryDATA.FactoryDATA;
import AnyQuantProject.dataService.factoryDATAService.FactoryDATAService;
import AnyQuantProject.dataService.realDATAService.benchMarkDATAService.BenchMarkDATAService;
import AnyQuantProject.dataStructure.BenchMark;
import AnyQuantProject.util.method.CalendarHelper;
import AnyQuantProject.util.method.Checker;

/** 
*AnyQuantProject//AnyQuantProject.bl.benchMarkBL//BenchMarkBLImpl.java
* @author  cxworks 
* @date 创建时间：2016年3月2日 下午2:45:14 
*/

public class BenchMarkBLImpl implements BenchMarkBLService {
	
	public BenchMarkBLImpl() {
	}

	@Override
	public List<BenchMark> getAllBenchMark() {
		FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
		BenchMarkDATAService benchMarkDATAService=factoryDATAService.getBenchMarkDATAService();
		//get list
		List<String> names=benchMarkDATAService.getAllBenchMark();
		//get yesterday data
		
			List<BenchMark> ans=names.stream()
					.map(bench -> 
					benchMarkDATAService.getOperation(bench, CalendarHelper.getPreviousDay(Calendar.getInstance())))
					.collect(Collectors.toList());
			ans.get(0).setChinese("沪深300指数");
			return ans;
		
		
		
	}

	@Override
	public List<BenchMark> getBenchMarkInfo(String name, Calendar year) {
		//name
		if (!Checker.checkStringNotNull(name)) {
			return new ArrayList<BenchMark>();
		}
		//year
		if (!Checker.checkCalendarBefore(year)) {
			return new ArrayList<BenchMark>();
		}
		//reset year
		year=CalendarHelper.zeroYear(year);
		try {
			FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
			BenchMarkDATAService benchMarkDATAService=factoryDATAService.getBenchMarkDATAService();
			List<BenchMark> benchMarks=benchMarkDATAService.getBenchMarkAmongDate(name, CalendarHelper.getMonthStart(year), CalendarHelper.getMonthEnd(year));
			return benchMarks;
		} catch (Exception e) {
			// TODO: handle exception
			return new ArrayList<BenchMark>();
		}
	}

}
