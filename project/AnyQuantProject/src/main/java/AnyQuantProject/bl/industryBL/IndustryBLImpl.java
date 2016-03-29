package AnyQuantProject.bl.industryBL;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import AnyQuantProject.bl.factoryBL.StockListBLFactory;
import AnyQuantProject.blService.industryBLService.IndustryBLService;
import AnyQuantProject.data.factoryDATA.FactoryDATA;
import AnyQuantProject.data.util.IndustryName;
import AnyQuantProject.dataService.factoryDATAService.FactoryDATAService;
import AnyQuantProject.dataService.realDATAService.IndustryNameDATAService;
import AnyQuantProject.dataStructure.Stock;
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
		List<Stock> ans=all.stream().filter(st->stocks.contains(st)).collect(Collectors.toList());
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

}
