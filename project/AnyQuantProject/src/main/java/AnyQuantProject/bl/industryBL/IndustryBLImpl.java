package AnyQuantProject.bl.industryBL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import AnyQuantProject.ui.net.TipPop;
import AnyQuantProject.util.constant.R;
import AnyQuantProject.util.exception.NetFailedException;
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
				stocks=getDataYester(name, date);
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
			if (turnOver==null) {
				throw new NullPointerException();
			}
		} catch (Exception e) {
			List<String> name=(List<String>) IOHelper.read(R.CachePath, R.StockNameFile);
			turnOver=new HashMap<>(name.size());
			if (name!=null) {
				List<Double> turn=this.getTurnOverVal(name);
				for (int i = 0; i < name.size(); i++) {
					turnOver.put(name.get(i), turn.get(i));
				}
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
				List<Double> sha=getTotalSha(name);
				for (int i = 0; i < name.size(); i++) {
					shares.put(name.get(i), sha.get(i));
				}
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
				List<Double> non=this.getNonRest(name);
				for (int i = 0; i < name.size(); i++) {
					nonRest.put(name.get(i), non.get(i));
				}
			
			}
		}
		finally {
			nonRestFlag=true;
			IOHelper.save(R.CachePath, CalendarHelper.getDate(date)+R.NonRest, (Serializable)nonRest);
		}
	}
	
	private List<Double> getNonRest(List<String> name){
		List<Double> ans=new ArrayList<>(name.size());
		FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
		TurnoverDATAService turnoverDATAService=factoryDATAService.geTurnoverDATAService();
		
		try {
			for (int i = 0; i < name.size(); i++) {
				String nam=name.get(i);
				double temp=turnoverDATAService.getNonrestFloatShares(nam);
				ans.add(temp);
			}
			return ans;
		} catch (NetFailedException e) {
			TipPop.showTip();
			return getNonRest(name);
		}
	}
	
	
	private List<Double> getTotalSha(List<String> name){
		List<Double> ans=new ArrayList<>(name.size());
		FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
		TurnoverDATAService turnoverDATAService=factoryDATAService.geTurnoverDATAService();
		try {
			for (int i = 0; i < name.size(); i++) {
				String nam=name.get(i);
				double temp=turnoverDATAService.getTotalShares(nam);
				ans.add(temp);
			}
			return ans;
		}
		catch(NetFailedException e){
			//
			return getTotalSha(name);
		}
	}
	
	private List<Double> getTurnOverVal(List<String> name){
		List<Double> ans=new ArrayList<>(name.size());
		FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
		TurnoverDATAService turnoverDATAService=factoryDATAService.geTurnoverDATAService();
		try {
			for (int i = 0; i < name.size(); i++) {
				String nam=name.get(i);
				double temp=turnoverDATAService.getTurnoverValue(nam);
				ans.add(temp);
			}
			return ans;
		} catch (NetFailedException e) {
			// TODO: handle exception
			return getTurnOverVal(name);
		}
	}
	
	
	
	private List<Stock> getDataYester(List<String> name,Calendar date){
		FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
		SingleStockDATAService singleStockDATAService=factoryDATAService.getSingleStockDATAService();
		try {
			List<Stock> stocks=new ArrayList<>(name.size());
			for (int i = 0; i < name.size(); i++) {
				String s=name.get(i);
				stocks.add(singleStockDATAService.getOperation(s, date));
			}
			return stocks;
		} catch (NetFailedException e) {
			// TODO: handle exception
			return getDataYester(name, date);
		}
	}
	private Map<String, Integer> getIndustryMap(){
		try {
			FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
			IndustryNameDATAService industryNameDATAService=factoryDATAService.getIndustryDATAService();
			List<String> temp=industryNameDATAService.getAllIndustries();
			Map<String , Integer> ans=new HashMap<>(temp.size());
			for (int i = 0; i < temp.size(); i++) {
				int t=industryNameDATAService.getStockByIndustry(temp.get(i)).size();
				ans.put(temp.get(i), t);
			}
			IOHelper.save(R.CachePath, R.IndustryStock, (Serializable)ans);
			return ans;
		} catch (NetFailedException e) {
			TipPop.showTip();
			return getIndustryMap();
		}
		
	}
	
	@Override
	public List<String> getAllIndustries() {
		Map<String, Integer> map=null;
		try {
			map=(Map<String, Integer>)IOHelper.read(R.CachePath, R.IndustryStock);
			if (map==null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			
			map=getIndustryMap();
		}
		List<Entry<String, Integer>> entries=map.entrySet().stream()
				.sorted((e1,e2)->e1.getValue().compareTo(e2.getValue()))
				.collect(Collectors.toList());
		List<String> ans=entries.stream().map(e->e.getKey()).collect(Collectors.toList());
		
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
		try {
			List<String> stocks=industryNameDATAService.getStockByIndustry(industry);
			List<Stock> all=StockListBLFactory.getStockListBLService().getAllStocks();
			List<Stock> ans=all.stream().filter(st->stocks.contains(st.getName())).collect(Collectors.toList());
			ans.stream().forEach(st->st.setYesterday(getYesterday(st.getName())));
			return ans;
		} catch (NetFailedException e) {
			TipPop.showTip();
			return getStocksByIndustry(industry);
		}
		
	}

	@Override
	public String getIndustryByName(String stockName) {
		//check
		if (!Checker.checkStringNotNull(stockName)) {
			return null;
		}
		FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
		IndustryNameDATAService industryNameDATAService=factoryDATAService.getIndustryDATAService();
		try {
			return industryNameDATAService.getIndustryName(stockName);
		} catch (NetFailedException e) {
			TipPop.showTip();
			return getIndustryByName(stockName);
		}
		
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
	@Deprecated
	public List<IndustryInfo> getAllIndustryInfo() {
		return new ArrayList<>();
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
			try {
				return service.getOperation(name, date);
			} catch (NetFailedException e) {
				TipPop.showTip();
				return getYesterday(name);
			}
			
		}
	}
	private double getTurnOver(String name){
		if (turnFlag) {
			return turnOver.get(name);
		}
		else {
			FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
			TurnoverDATAService turnoverDATAService=factoryDATAService.geTurnoverDATAService();
			try {
				return turnoverDATAService.getTurnoverValue(name);
			} catch (NetFailedException e) {
				TipPop.showTip();
				return getTurnOver(name);
			}
		}
	}
	private double getShares(String name){
		if (sharesFlag) {
			return shares.get(name);
		}
		else {
			FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
			TurnoverDATAService turnoverDATAService=factoryDATAService.geTurnoverDATAService();
			try {
				return turnoverDATAService.getTotalShares(name);
			} catch (NetFailedException e) {
				TipPop.showTip();
				return getShares(name);
			}
		}
	}
	private double getNonRest(String name){
		if (nonRestFlag) {
			return nonRest.get(name);
		}
		else {
			FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
			TurnoverDATAService turnoverDATAService=factoryDATAService.geTurnoverDATAService();
			try {
				return turnoverDATAService.getNonrestFloatShares(name);
			} catch (NetFailedException e) {
				TipPop.showTip();
				return getNonRest(name);
			}
		}
	}
}
