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
		IndustryNameDATAService industryNameDATAService=new IndustryName();
		System.out.println(industryNameDATAService.getAllIndustries().size());
		return industryNameDATAService.getAllIndustries();
	}

	@Override
	public List<Stock> getStocksByIndustry(String industry) {
		//check
		if (!Checker.checkStringNotNull(industry)) {
			return new ArrayList<>();
		}
		IndustryNameDATAService industryNameDATAService=new IndustryName();
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
		IndustryNameDATAService industryNameDATAService=new IndustryName();
		return industryNameDATAService.getIndustryName(stockName);
	}

}
