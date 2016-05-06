package webProject.server.daily;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import AnyQuantProject.bl.calculateBL.BIAS;
import AnyQuantProject.bl.calculateBL.DataCell;
import AnyQuantProject.bl.calculateBL.KDJ;
import AnyQuantProject.bl.calculateBL.MACD;
import AnyQuantProject.bl.calculateBL.POLY;
import AnyQuantProject.bl.calculateBL.RSI;
import AnyQuantProject.dataStructure.AbstractStock;
import AnyQuantProject.dataStructure.KLineDataDTO;
import AnyQuantProject.dataStructure.Stock;

/**
* AnyQuantProject/webProject.server.daily/Calculate.java
* @author cxworks
* 2016年5月6日 下午3:53:23
*/
public class Calculate {
	public static void cal(List<Stock> src, List<webProject.server.daily.Stock> dst){
		int len=dst.size();
		if (src.size()-dst.size()<250) {
			len=src.size()-250;
			dst=dst.subList(dst.size()-len, dst.size());
		}
		//day
		List<Stock> pma5day=calculateAver(src,5);
		pma5day=cutStock(pma5day, len);
		List<Stock> pma10day=calculateAver(src, 10);
		pma10day=cutStock(pma10day, len);
		List<Stock> pma30day=calculateAver(src, 30);
		pma30day=cutStock(pma30day, len);
		//month
//		List<Stock> month=src.stream().filter(s->Integer.parseInt(s.getDay())==1).collect(Collectors.toList());
		List<Stock> pma5month=calculateAver(src, 100);
		pma5month=cutStock(pma5month, len);
		List<Stock> pma10month=calculateAver(src, 150);
		pma10month=cutStock(pma10month, len);
		List<Stock> pma30month=calculateAver(src, 250);
		pma30month=cutStock(pma30month, len);
		//week
//		List<Stock> week=src.stream().filter(s->s.getDateInCalendar().get(Calendar.DAY_OF_WEEK)==1).collect(Collectors.toList());
		List<Stock> pma5week=calculateAver(src, 35);
		pma5week=cutStock(pma5week, len);
		List<Stock> pma10week=calculateAver(src, 70);
		pma10week=cutStock(pma10week, len);
		List<Stock> pma30week=calculateAver(src, 210);
		pma30week=cutStock(pma30week, len);
		//BIAS
		List<DataCell> bias6=BIAS.calculateBIAS(src, 6);
		bias6=cutCell(bias6, len);
		List<DataCell> bias12=BIAS.calculateBIAS(src, 12);
		bias12=cutCell(bias12, len);
		List<DataCell> bias24=BIAS.calculateBIAS(src, 24);
		bias24=cutCell(bias24, len);
		//KDJ
		List<DataCell> rsi=KDJ.calculateRSI(src, 6);
		List<DataCell> kdj=KDJ.calculateKDJ(rsi);
		kdj=cutCell(kdj, len);
		//RSI
		List<DataCell> rsi6=RSI.calculateRSI(src, 6);
		rsi6=cutCell(rsi6, len);
		List<DataCell> rsi12=RSI.calculateRSI(src, 12);
		rsi12=cutCell(rsi12, len);
		List<DataCell> rsi24=RSI.calculateRSI(src, 24);
		rsi24=cutCell(rsi24, len);
		//MACD
		List<DataCell> macd=MACD.calculateMACD(src, 12, 9, 26);
		macd=cutCell(macd, len);
		//POLY
		List<DataCell> poly=new ArrayList<>(src.size());
		for (int i = 6; i < src.size(); i++) {
			double p=POLY.calculateStockPOLY(src.subList(i-6, i));
			poly.add(new DataCell(src.get(i).getDate(), p));
		}
		poly=cutCell(poly, len);
		//merge
		for (int i = 0; i < dst.size(); i++) {
			webProject.server.daily.Stock stock=dst.get(i);
			stock.PMA5_day=pma5day.get(i).getClose();
			stock.PMA5_week=pma5week.get(i).getClose();
			stock.PMA5_month=pma5month.get(i).getClose();
			stock.PMA10_day=pma10day.get(i).getClose();
			stock.PMA10_week=pma10week.get(i).getClose();
			stock.PMA10_month=pma10month.get(i).getClose();
			stock.PMA30_day=pma30day.get(i).getClose();
			stock.PMA30_week=pma30week.get(i).getClose();
			stock.PMA30_month=pma30month.get(i).getClose();
			//bias
			stock.BIAS6=bias6.get(i).y;
			stock.BIAS12=bias12.get(i).y;
			stock.BIAS24=bias24.get(i).y;
			//kdj
			stock.K=kdj.get(i).y;
			stock.D=kdj.get(i).y2;
			stock.J=kdj.get(i).y3;
			//rsi
			stock.RSI6=rsi6.get(i).y;
			stock.RSI12=rsi12.get(i).y;
			stock.RSI24=rsi24.get(i).y;
			//macd
			stock.DIF=macd.get(i).y;
			stock.DEA=macd.get(i).y2;
			stock.MACHBar=macd.get(i).y3;
			//poly
			stock.poly=poly.get(i).y;
		}
		
		
	}
	public static List<Stock> cutStock(List<Stock> src,int len){
		return src.subList(src.size()-len, src.size());
	}
	public static List<DataCell> cutCell(List<DataCell> src,int len){
		return src.subList(src.size()-len, src.size());
	}
	private static List<Stock> calculateAver(List<Stock> ans,int aver){
		List<Stock> tar=new ArrayList<>(ans.size());
		for(int j=aver-1;j<ans.size();j++){
			//init data
			double close=0;
			int total=0;
			String date=null;
			for(int i=0;i<aver;i++){
				KLineDataDTO temp=ans.get(j-i);
				if (i==0) {
					date=((AbstractStock)temp).getDate();
				}
				if (temp.getOpen()==0) {
					continue;
				}
				
				close+=temp.getClose();
				
				total++;
			}
			//build
			//first check
			if (total==0){
				total=1;
			}
			close/=total;
			Stock stock=new Stock();
			stock.setDate(date);
			stock.setClose(close);
			tar.add(stock);
		}
		return tar;
	}
}
