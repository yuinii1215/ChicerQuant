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
import AnyQuantProject.ui.net.TipPop;
import AnyQuantProject.util.exception.NetFailedException;
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
		try {
			//get list
			List<String> names = benchMarkDATAService.getAllBenchMark();
			//get yesterday data

			List<BenchMark> ans=new ArrayList<>(names.size());
			
			for (int i = 0; i < names.size(); i++) {
				String name=names.get(i);
				BenchMark benchMark=benchMarkDATAService.getOperation(name, CalendarHelper.getPreviousDay(Calendar.getInstance()));
				ans.add(benchMark);
			}
			
			ans.get(0).setChinese("沪深300指数");
			return ans;
		} catch (NetFailedException e) {
			TipPop.showTip();
			return this.getAllBenchMark();
		}
		

		
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

		FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
		BenchMarkDATAService benchMarkDATAService=factoryDATAService.getBenchMarkDATAService();
		try {
			List<BenchMark> benchMarks=benchMarkDATAService.getBenchMarkAmongDate(name, CalendarHelper.getMonthStart(year), CalendarHelper.getMonthEnd(year));
			return benchMarks;
		} catch (NetFailedException e) {
			TipPop.showTip();
			return this.getBenchMarkInfo(name, year);
		}
		

	}

}
