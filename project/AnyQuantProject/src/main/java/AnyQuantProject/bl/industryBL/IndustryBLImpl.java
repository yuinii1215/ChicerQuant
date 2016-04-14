package AnyQuantProject.bl.industryBL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import AnyQuantProject.bl.factoryBL.IndustryBLFactory;
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
import AnyQuantProject.util.constant.R;
import AnyQuantProject.util.method.CalendarHelper;
import AnyQuantProject.util.method.Checker;
import AnyQuantProject.util.method.IOHelper;

/** 
* AnyQuantProject//AnyQuantProject.bl.industryBL//IndustryBLImpl.java
* @author  cxworks 
* @date 创建时间：2016年3月25日 上午11:09:18 
*/

public class IndustryBLImpl implements IndustryBLService {
	
	private Map<String, Stock> yesterday;
	private Map<String, Double> turnOver;
	private Map<String, Double> shares;
	private Map<String, Double> nonRest;
	private boolean flag=false;
	private boolean turnFlag=false;
	private boolean sharesFlag=false;
	private boolean nonRestFlag=false;
	public IndustryBLImpl() {
		FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
		SingleStockDATAService singleStockDATAService=factoryDATAService.getSingleStockDATAService();
		Calendar date= Calendar.getInstance();
		date.add(Calendar.DAY_OF_MONTH,-2);
		List<Stock> stocks=null;
		try {
		
			stocks=(List<Stock>) IOHelper.read(R.CachePath, CalendarHelper.getDate(date));
			if (stocks==null) {
				throw new NullPointerException();
			}
		} catch (Exception e) {
			List<String> name=(List<String>) IOHelper.read(R.CachePath, R.StockNameFile);
			if (name!=null) {
				stocks=name.stream().map(s->singleStockDATAService.getOperation(s, date)).collect(Collectors.toList());
				IOHelper.save(R.CachePath, CalendarHelper.getDate(date), (Serializable)stocks);
			}
		}
		finally {
			if (stocks!=null) {
				flag=true;
				yesterday=stocks.stream().collect(Collectors.toMap(Stock::getName, (s)->(s)));
				
			}
		}
		//
		TurnoverDATAService turnoverDATAService=factoryDATAService.geTurnoverDATAService();
		try {
			List<String> name=(List<String>) IOHelper.read(R.CachePath, R.StockNameFile);
//			date.add(Calendar.DAY_OF_MONTH, 1);
			turnOver=(Map<String, Double>)IOHelper.read(R.CachePath, CalendarHelper.getDate(date)+R.TurnOver);
			System.out.println(turnoverDATAService.getTurnoverValue("sh600016"));
			if (turnOver==null) {
				throw new NullPointerException();
			}
		} catch (Exception e) {
			List<String> name=(List<String>) IOHelper.read(R.CachePath, R.StockNameFile);
			turnOver=new HashMap<>(name.size());
			if (name!=null) {
				name.stream().forEach(s->turnOver.put(s, turnoverDATAService.getTurnoverValue(s)));
			}
		}
		finally {
			turnFlag=true;
			IOHelper.save(R.CachePath, CalendarHelper.getDate(date)+R.TurnOver, (Serializable)turnOver);
		}
		//
		try {
			shares=(Map<String, Double>)IOHelper.read(R.CachePath, CalendarHelper.getDate(date)+R.SHARES);
			if (shares==null) {
				throw new NullPointerException();
			}
		} catch (Exception e) {
			List<String> name=(List<String>) IOHelper.read(R.CachePath, R.StockNameFile);
			shares=new HashMap<>(name.size());
			if (name!=null) {
				name.stream().forEach(s->turnOver.put(s, turnoverDATAService.getTotalShares(s)));
			}
		}
		finally {
			sharesFlag=true;
			IOHelper.save(R.CachePath, CalendarHelper.getDate(date)+R.SHARES, (Serializable)shares);
		}
		//
		try {
			nonRest=(Map<String, Double>)IOHelper.read(R.CachePath, CalendarHelper.getDate(date)+R.NonRest);
			if (nonRest==null) {
				throw new NullPointerException();
			}
		} catch (Exception e) {
			List<String> name=(List<String>) IOHelper.read(R.CachePath, R.StockNameFile);
			nonRest=new HashMap<>(name.size());
			if (name!=null) {
				name.stream().forEach(s->nonRest.put(s, turnoverDATAService.getNonrestFloatShares(s)));
			}
		}
		finally {
			nonRestFlag=true;
			IOHelper.save(R.CachePath, CalendarHelper.getDate(date)+R.NonRest, (Serializable)nonRest);
		}
	}

	
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
		ans.stream().forEach(s->System.out.println(s));
		return ans;
	}
	public static void main(String[] args) {
		IndustryBLService industryBLService=IndustryBLFactory.getIndustryBLService();
		IndustryInfo industryInfo=industryBLService.getIndustryInfo("传媒");
		System.out.println(industryInfo==null);
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
		ans.stream().forEach(st->st.setYesterday(getYesterday(st.getName())));
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
		
//		long today=todaydata.stream().mapToLong(s->s.getMarketvalue()).sum();
		double todaySum=todaydata.stream().mapToDouble(s->s.getClose()).sum();
		FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
		
		//get yesterday
		List<Double> turnover=todaydata.stream()
				.map(s->getTurnOver(s.getName()))
				.collect(Collectors.toList());
		
		List<Stock> yesterdata=todaydata.stream()
				.map(s->getYesterday(s.getName()))
				.collect(Collectors.toList());
		double yesterSum=yesterdata.stream().mapToDouble(s->s.getClose()).sum();
		double pure=0;
		for (int i = 0; i < yesterdata.size(); i++) {
			pure+=turnover.get(i)*todaydata.get(i).getClose();
		}
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
		

		int companySum=todaydata.size();

		double total=turnover.stream().mapToDouble(s->s).sum();
		//get guben
		List<Double> guben=todaydata.stream()
				.map(s->getNonRest(s.getName()))
				.collect(Collectors.toList());
		double totalGuben=guben.stream().mapToDouble(s->s).sum();
		double totalValue=0;
		for (int i = 0; i < guben.size(); i++) {
			totalValue+=guben.get(i)*todaydata.get(i).getClose();
		}
		double price=totalValue/totalGuben;
		//
		

		IndustryInfo ans=new IndustryInfo(industry);
		ans.setPure(pure);
		ans.setUpdown((double)(todaySum-yesterSum)/yesterSum);
		ans.setTotal(total);
		ans.setCompanySum(companySum);
		ans.setPrice(price);
		ans.setLeader(name);
		ans.setLeaderPrice(leaderPrice);
		ans.setLeaderUpdown(leaderUpdown);
		return ans;
	}

	@Override
	public List<IndustryInfo> getAllIndustryInfo() {
		// TODO Auto-generated method stub
		return null;
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
		
		List<Double> guben=todaydata.stream()
				.map(s->getNonRest(s.getName()))
				.collect(Collectors.toList());
		double totalGuben=guben.stream().mapToDouble(s->s).sum();
		double open=0;
		double close=0;
		double max=0;
		double min=0;
		long volume=0;
		for (int i = 0; i < guben.size(); i++) {
			double gb=guben.get(i);
			Stock stock=todaydata.get(i);
			open+=stock.getOpen()*gb;
			close+=stock.getClose()*gb;
			max+=stock.getHigh()*gb;
			min+=stock.getLow()*gb;
			volume+=stock.getVolume();
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
		ans.setVolume(volume);
		return ans;
	}
	@Override
	public Stock getYesterday(String name){
		if (flag) {
			return yesterday.get(name);
		}
		else {
			FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
			SingleStockDATAService service=factoryDATAService.getSingleStockDATAService();
			Calendar date= Calendar.getInstance();
			date.add(Calendar.DAY_OF_MONTH,-2);
			return service.getOperation(name, date);
		}
	}
	private double getTurnOver(String name){
		if (turnFlag) {
			return turnOver.get(name);
		}
		else {
			FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
			TurnoverDATAService turnoverDATAService=factoryDATAService.geTurnoverDATAService();
			return turnoverDATAService.getTurnoverValue(name);
		}
	}
	private double getShares(String name){
		if (sharesFlag) {
			return shares.get(name);
		}
		else {
			FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
			TurnoverDATAService turnoverDATAService=factoryDATAService.geTurnoverDATAService();
			return turnoverDATAService.getTotalShares(name);
		}
	}
	private double getNonRest(String name){
		if (nonRestFlag) {
			return nonRest.get(name);
		}
		else {
			FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
			TurnoverDATAService turnoverDATAService=factoryDATAService.geTurnoverDATAService();
			return turnoverDATAService.getNonrestFloatShares(name);
		}
	}
}
