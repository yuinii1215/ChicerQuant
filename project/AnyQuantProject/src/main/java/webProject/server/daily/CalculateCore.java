package webProject.server.daily;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import AnyQuantProject.bl.calculateBL.CalculateLineBLImpl;
import AnyQuantProject.bl.calculateBL.POLY;
import AnyQuantProject.blService.kLineBLService.CalculateLineBLService;
import AnyQuantProject.data.factoryDATA.FactoryDATA;
import AnyQuantProject.dataService.factoryDATAService.FactoryDATAService;
import AnyQuantProject.dataService.realDATAService.IndustryNameDATAService;
import AnyQuantProject.dataService.realDATAService.singleStockDATAService.SingleStockDATAService;
import AnyQuantProject.dataStructure.Cell;
import AnyQuantProject.util.constant.R;
import AnyQuantProject.util.exception.NetFailedException;
import AnyQuantProject.util.method.CalendarHelper;
import AnyQuantProject.util.method.IOHelper;

/**
* AnyQuantProject/webProject.server.daily/CalculateCore.java
* @author cxworks
* 2016年5月5日 下午9:12:38
*/



public class CalculateCore {

	static List<String> id=SetupSQL.id;
	static Map<String, String> chn;
	static Map<String, String> indu=SetupSQL.indu;
	
	public static List<Stock> initBase(String id,Calendar min,Calendar max) throws NetFailedException{
		Calendar usemin=Calendar.getInstance();
		usemin.setTimeInMillis(min.getTimeInMillis());
		usemin.add(Calendar.YEAR, -5);
		FactoryDATAService dataService=FactoryDATA.getInstance();
		SingleStockDATAService singleStockDATAService=dataService.getSingleStockDATAService();
		List<AnyQuantProject.dataStructure.Stock> src=singleStockDATAService.getStockAmongDate(id, usemin, max);
		src=src.stream().filter(s->s.getDate()!=null).collect(Collectors.toList());
		List<AnyQuantProject.dataStructure.Stock> tList=src.stream().filter(s->s.getClose()!=0).collect(Collectors.toList());
		if (tList.size()>=250) {
			List<AnyQuantProject.dataStructure.Stock> fina=tList.stream().filter(s->s.getDateInCalendar().after(min)).collect(Collectors.toList());
			//
			
			//
			List<Stock> ans=new ArrayList<>(fina.size());
			for (int i = 0; i < fina.size(); i++) {
				Stock stock=new Stock();
				stock.stock_id=id;
				stock.stock_name=chn.get(id);
				stock.industry=indu.get(id);
				trans(stock, fina.get(i));
				ans.add(stock);
			}
			Calculate.cal(tList, ans);
			return ans;
		}else {
			List<Stock> ans=new ArrayList<>(src.size());
			for (int i = 0; i < src.size(); i++) {
				Stock stock=new Stock();
				stock.stock_id=id;
				stock.stock_name=chn.get(id);
				stock.industry=indu.get(id);
				trans(stock, src.get(i));
				ans.add(stock);
			}
			return ans;
		}
		
	}
	
	public static List<Stock> calculateDaily(Calendar date){
		List<Stock> ans=new ArrayList<>(id.size());
		FactoryDATAService dataService=FactoryDATA.getInstance();
		SingleStockDATAService singleStockDATAService=dataService.getSingleStockDATAService();
		int z=0;
		for(int i=0;i<id.size();i++){
			
			
			try {
				Stock stock=new Stock();
				String tid=id.get(i);
				stock.stock_id=id.get(i);
				stock.stock_name=chn.get(tid);
				stock.industry=indu.get(tid);
				//
				AnyQuantProject.dataStructure.Stock data=singleStockDATAService.getOperation(tid, date);
				if (data.getClose()==0) {
					z++;
					if (z==100) {
						return null;
					}
				}
				trans(stock, data);
				//
				ans.add(stock);
			} catch (Exception e) {
				continue;
				
			}
			
		}
		
		
		
		return ans;
		
	}
	private static void merge(Stock dst,Calendar date,CalculateLineBLService service){
		Calendar use=Calendar.getInstance();
		use.setTimeInMillis(date.getTimeInMillis());
		use.add(Calendar.DAY_OF_MONTH, -1);
		String tid=dst.stock_id;
		List<Cell>[] cells;
		//BIAS
		cells=service.drawBIAS(tid, use, date).cells;
		dst.BIAS6=cells[0].get(cells[0].size()-1).y;
		dst.BIAS12=cells[1].get(cells[1].size()-1).y;
		dst.BIAS24=cells[2].get(cells[2].size()-1).y;
		//RSI
		cells=service.drawRSI(tid, use, date).cells;
		dst.RSI6=cells[0].get(cells[0].size()-1).y;
		dst.RSI12=cells[1].get(cells[1].size()-1).y;
		dst.RSI24=cells[2].get(cells[2].size()-1).y;
		//KDJ
		cells=service.drawKDJ(tid, use, date).cells;
		dst.K=cells[0].get(cells[0].size()-1).y;
		dst.D=cells[1].get(cells[1].size()-1).y;
		dst.J=cells[2].get(cells[2].size()-1).y;
		//MACD
		cells=service.drawMACD(tid, use, date).cells;
		dst.DIF=cells[0].get(cells[0].size()-1).y;
		dst.DEA=cells[1].get(cells[1].size()-1).y;
		dst.MACHBar=cells[2].get(cells[2].size()-1).y;
		//
		
	}
	
	
	public static void trans(Stock dst,AnyQuantProject.dataStructure.Stock src){
		dst.open=src.getOpen();
		dst.close=src.getClose();
		dst.high=src.getHigh();
		dst.low=src.getLow();
		dst.adj_price=src.getAdj_price();
		dst.pb=src.getPb();
		dst.pe_ttm=src.getPe_ttm();
		dst.volumn=src.getVolume();
		dst.setDate(src.getDateInCalendar());
		dst.stock_id=src.getName();
	}
	private static void aver(Stock dst,Calendar date,SingleStockDATAService dataService) throws NetFailedException{
		Calendar use=Calendar.getInstance();
		use.setTimeInMillis(date.getTimeInMillis());
		String tid=dst.stock_id;
		//day
		use.add(Calendar.DAY_OF_MONTH,-45);
		List<AnyQuantProject.dataStructure.Stock> day=dataService.getStockAmongDate(tid, use, date);
		day=day.stream().filter(s->s.getClose()!=0).collect(Collectors.toList());
		dst.PMA5_day=day.subList(day.size()-5, day.size()).stream().mapToDouble(s->s.getClose()).average().getAsDouble();
		dst.PMA10_day=day.subList(day.size()-10, day.size()).stream().mapToDouble(s->s.getClose()).average().getAsDouble();
		dst.PMA30_day=day.subList(day.size()-30, day.size()).stream().mapToDouble(s->s.getClose()).average().getAsDouble();
		//poly 
		List<Double> doubles=day.subList(day.size()-6, day.size()).stream().map(s->s.getClose()).collect(Collectors.toList());
		dst.poly=POLY.calculatePOLY(doubles);
		//month
		use.setTimeInMillis(date.getTimeInMillis());
		double total=0;
		for(int i=0,count=0;i<=30;i++){
			AnyQuantProject.dataStructure.Stock ts1=dataService.getOperation(tid, use);
			if (ts1.getClose()!=0) {
				total+=ts1.getClose();
				count++;
			}
			use.add(Calendar.MONTH, -1);
			switch (i) {
			case 5:
				dst.PMA5_month=total/count;
				break;
			case 10:
				dst.PMA10_month=total/count;
				break;
			case 30:
				dst.PMA30_month=total/count;
				break;
			}
		}
		//week
		use.setTimeInMillis(date.getTimeInMillis());
		total=0;
		for(int i=0,count=0;i<=30;i++){
			AnyQuantProject.dataStructure.Stock ts1=dataService.getOperation(tid, use);
			if (ts1.getClose()!=0) {
				total+=ts1.getClose();
				count++;
			}
			use.add(Calendar.DAY_OF_MONTH, -7);
			switch (i) {
			case 5:
				dst.PMA5_week=total/count;
				break;
			case 10:
				dst.PMA10_week=total/count;
				break;
			case 30:
				dst.PMA30_week=total/count;
				break;
			}
		}
	}
}
