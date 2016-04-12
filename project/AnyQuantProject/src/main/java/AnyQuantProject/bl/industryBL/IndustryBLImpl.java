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
import AnyQuantProject.dataStructure.IndustryPriceInfo;
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
		//
		//
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
		//leader
		double max=-100;
		String name="";
		double leaderPrice=0;
		double leaderUpdown=0;
		for(int i=0;i<todaydata.size();i++){
			Stock todayy=todaydata.get(i);
			double it=(todayy.getClose()-yesterdata.get(i).getClose())/yesterdata.get(i).getClose();
			if (yesterdata.get(i).getClose()==0) {
				break;
			}
			if (it>max) {
				max=it;
				name=todayy.getChinese()+todayy.getName();
				leaderPrice=todayy.getClose();
				leaderUpdown=it;
			}
		}
		//get vol
		TurnoverDATAService turnoverDATAService=factoryDATAService.geTurnoverDATAService();
		List<Double> turnover=todaydata.stream()
				.map(s->turnoverDATAService.getTurnoverValue(s.getName()))
				.collect(Collectors.toList());

		int companySum=todaydata.size();

		double total=turnover.stream().mapToDouble(s->s).sum();
		//get guben
		List<Double> guben=todaydata.stream()
				.map(s->turnoverDATAService.getNonrestFloatShares(s.getName()))
				.collect(Collectors.toList());
		double totalGuben=guben.stream().mapToDouble(s->s).sum();
		double totalValue=0;
		for (int i = 0; i < guben.size(); i++) {
			totalValue+=guben.get(i)*todaydata.get(i).getClose();
		}
		double price=totalValue/totalGuben;
		//
		

		IndustryInfo ans=new IndustryInfo(industry);
		ans.setPure(today);
		ans.setUpdown((double)(today-yesterday)/yesterday);
		ans.setTotal(total);
		ans.setCompanySum(companySum);
		ans.setPrice(price);
		ans.setLeader(name);
		ans.setLeaderPrice(leaderPrice);
		ans.setLeaderUpdown(leaderUpdown);
		return ans;
	}

	@Override
	public IndustryPriceInfo getIndustryPrice(String industry) {
		if (!Checker.checkStringNotNull(industry)){
			return  new IndustryPriceInfo();
		}
		List<Stock> todaydata=this.getStocksByIndustry(industry);
		//
		//
		double c=todaydata.get(0).getClose();
		if(c==0)
			return new IndustryPriceInfo();
		//
		FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
		TurnoverDATAService turnoverDATAService=factoryDATAService.geTurnoverDATAService();
		List<Double> guben=todaydata.stream()
				.map(s->turnoverDATAService.getNonrestFloatShares(s.getName()))
				.collect(Collectors.toList());
		double totalGuben=guben.stream().mapToDouble(s->s).sum();
		double open=0;
		double close=0;
		double max=0;
		double min=0;
		for (int i = 0; i < guben.size(); i++) {
			double gb=guben.get(i);
			Stock stock=todaydata.get(i);
			open+=stock.getOpen()*gb;
			close+=stock.getClose()*gb;
			max+=stock.getHigh()*gb;
			min+=stock.getLow()*gb;
		}
		open/=totalGuben;
		close/=totalGuben;
		max/=totalGuben;
		min/=totalGuben;
		IndustryPriceInfo ans=new IndustryPriceInfo(industry);
		ans.setOpen(open);
		ans.setClose(close);
		ans.setMax(max);
		ans.setMin(min);
		return ans;
	}
}
