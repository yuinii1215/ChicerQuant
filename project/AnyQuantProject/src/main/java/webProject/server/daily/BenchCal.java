package webProject.server.daily;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import AnyQuantProject.dataStructure.BenchMark;
import AnyQuantProject.util.method.CalendarHelper;

/**
* AnyQuantProject/webProject.server.daily/BenchCal.java
* @author cxworks
* 2016年5月10日 下午8:44:31
*/

public class BenchCal {
	
	public static List<webProject.server.daily.BenchMark> trans(List<BenchMark> src){
		List<webProject.server.daily.BenchMark> ans=new ArrayList<>(src.size());
		for (int i = 0; i < src.size(); i++) {
			BenchMark temp=src.get(i);
			webProject.server.daily.BenchMark benchMark=new webProject.server.daily.BenchMark();
			benchMark.stock_id=temp.getName();
			benchMark.stock_name=temp.getChinese();
			benchMark.setDate(temp.getDateInCalendar());
			benchMark.open=temp.getOpen();
			benchMark.close=temp.getClose();
			benchMark.high=temp.getHigh();
			benchMark.low=temp.getLow();
			benchMark.adj_price=temp.getAdj_price();
			benchMark.transaction=temp.getTransaction();
			benchMark.volumn=temp.getVolume();
			benchMark.marketvalue=temp.getMarketvalue();
			benchMark.flow=temp.getFlow();
			ans.add(benchMark);
		}
		return ans;
	}
	//
	public static List<Stock> buildFake1(List<webProject.server.daily.BenchMark> src){
		return src.stream().map(b->new Stock()).collect(Collectors.toList());
	}
	public static List<AnyQuantProject.dataStructure.Stock> buildFake2(List<BenchMark> src){
		List<AnyQuantProject.dataStructure.Stock> ans=new ArrayList<>(src.size());
		for (int i = 0; i < src.size(); i++) {
			BenchMark temp=src.get(i);
			AnyQuantProject.dataStructure.Stock stock=new AnyQuantProject.dataStructure.Stock();
			stock.setName(temp.getName());
			stock.setChinese(temp.getChinese());
			stock.setOpen(temp.getOpen());
			stock.setHigh(temp.getHigh());
			stock.setClose(temp.getClose());
			stock.setLow(temp.getLow());
			stock.setDate(temp.getDate());
			ans.add(stock);
		}
		return ans;
	}
	
	public static void transFake(List<Stock> src,List<webProject.server.daily.BenchMark> dst){
		for (int i = 0; i < dst.size(); i++) {
			webProject.server.daily.BenchMark d=dst.get(i);
			Stock s=src.get(i);
			d.BIAS6=s.BIAS6;
			d.BIAS12=s.BIAS12;
			d.BIAS24=s.BIAS24;
			d.RSI6=s.RSI6;
			d.RSI12=s.RSI12;
			d.RSI24=s.RSI24;
			d.K=s.K;
			d.J=s.J;
			d.D=s.D;
			d.poly=s.poly;
			d.DEA=s.DEA;
			d.DIF=s.DIF;
			d.MACHBar=s.MACHBar;
			d.PMA5_day=s.PMA5_day;
			d.PMA10_day=s.PMA10_day;
			d.PMA30_day=s.PMA30_day;
			d.PMA5_week=s.PMA5_week;
			d.PMA10_week=s.PMA10_week;
			d.PMA30_week=s.PMA30_week;
			d.PMA5_month=s.PMA5_month;
			d.PMA10_month=s.PMA10_month;
			d.PMA30_month=s.PMA30_month;
		}
	}
}
