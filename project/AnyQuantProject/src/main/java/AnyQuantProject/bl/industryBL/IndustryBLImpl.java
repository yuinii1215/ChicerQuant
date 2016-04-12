package AnyQuantProject.bl.industryBL;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import AnyQuantProject.bl.factoryBL.StockListBLFactory;
import AnyQuantProject.blService.industryBLService.IndustryBLService;
import AnyQuantProject.data.factoryDATA.FactoryDATA;
import AnyQuantProject.dataService.factoryDATAService.FactoryDATAService;
import AnyQuantProject.dataService.realDATAService.IndustryNameDATAService;
import AnyQuantProject.dataService.realDATAService.singleStockDATAService.SingleStockDATAService;
import AnyQuantProject.dataService.realDATAService.stockListDATAService.TurnoverDATAService;
import AnyQuantProject.dataStructure.IndustryInfo;
import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.util.method.CalendarHelper;
import AnyQuantProject.util.method.Checker;

/** 
* AnyQuantProject//AnyQuantProject.bl.industryBL//IndustryBLImpl.java
* @author  cxworks 
* @date 创建时间：2016年3月25日 上午11:09:18 
*/

public class IndustryBLImpl implements IndustryBLService {

	@Override
	public List<String> getAllIndustries() {

		FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
		IndustryNameDATAService industryNameDATAService=factoryDATAService.getIndustryDATAService();
		List<String> temp=industryNameDATAService.getAllIndustries();
		List<String> ans=temp.stream().sorted((s1,s2)->{
			int f1=industryNameDATAService.getStockByIndustry(s1).size();
			int f2=industryNameDATAService.getStockByIndustry(s2).size();
			return f2-f1;
		}).collect(Collectors.toList());
		return ans;
	}

	@Override
	public List<Stock> getStocksByIndustry(String industry) {
		//check
		if (!Checker.checkStringNotNull(industry)) {
			return new ArrayList<>();
		}
		FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
		IndustryNameDATAService industryNameDATAService=factoryDATAService.getIndustryDATAService();
	
		List<String> stocks=industryNameDATAService.getStockByIndustry(industry);
		List<Stock> all=StockListBLFactory.getStockListBLService().getAllStocks();
		List<Stock> ans=all.stream().filter(st->stocks.contains(st.getName())).collect(Collectors.toList());
		return ans;
	}

	@Override
	public String getIndustryByName(String stockName) {
		//check
		if (!Checker.checkStringNotNull(stockName)) {
			return null;
		}
		FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
		IndustryNameDATAService industryNameDATAService=factoryDATAService.getIndustryDATAService();
	
		return industryNameDATAService.getIndustryName(stockName);
	}

	@Override
	public IndustryInfo getIndustryInfo(String industry) {
		//check
		if (!Checker.checkStringNotNull(industry)){
			return  new IndustryInfo();
		}
		List<Stock> todaydata=this.getStocksByIndustry(industry);
		double c=todaydata.get(0).getClose();
		if(todaydata.get(0).getClose()==0)
			return new IndustryInfo();
		long today=todaydata.stream().mapToLong(s->s.getMarketvalue()).sum();
		FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
		//get yesterday
		SingleStockDATAService singleStockDATAService=factoryDATAService.getSingleStockDATAService();
		Calendar date= CalendarHelper.convert2Calendar(todaydata.get(0).getDate());
		date.add(Calendar.DAY_OF_MONTH,-1);
		List<Stock> yesterdata=todaydata.stream()
				.map(s->singleStockDATAService.getOperation(s.getName(),date))
				.collect(Collectors.toList());
		long yesterday=yesterdata.stream().mapToLong(s->s.getMarketvalue()).sum();
		//get vol
		TurnoverDATAService turnoverDATAService=factoryDATAService.geTurnoverDATAService();
		List<Double> turnover=todaydata.stream()
				.map(s->turnoverDATAService.getTurnoverValue(s.getName()))
				.collect(Collectors.toList());

		int companySum=todaydata.size();

		double total=turnover.stream().mapToDouble(s->s).sum();

		IndustryInfo ans=new IndustryInfo(industry);
		ans.setPure(today);
		ans.setUpdown((double)(today-yesterday)/yesterday);
		ans.setTotal(total);
		ans.setCompanySum(companySum);
		return  ans;
	}


	public static void main(String[] args) {
		IndustryBLImpl i = new IndustryBLImpl();
		System.out.println(i.getIndustryInfo("银行"));
	}
}
