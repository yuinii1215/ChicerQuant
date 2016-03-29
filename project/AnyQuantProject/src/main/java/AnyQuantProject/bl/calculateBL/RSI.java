package AnyQuantProject.bl.calculateBL;

import java.util.ArrayList;
import java.util.List;

import AnyQuantProject.dataStructure.Stock;

/**
* AnyQuantProject/AnyQuantProject.bl.calculateBL/RSI.java
* @author cxworks
* 2016年3月29日 下午8:19:01
*/
public class RSI {
	public static List<Stock> calculateRSI(List<Stock> src,int day){
		int total=day;
		day++;
		List<Stock> ans=new ArrayList<>(src.size());
		for (int i = 0; i < src.size()-day; i++) {
			Stock stock = new Stock();
			
			
			Stock first=src.get(i);
			double close=first.getClose();
			String date=first.getDate();
			double up=0;
			double down=0;
			//
			for (int j = 1; j < day; j++) {
				Stock temp=src.get(i+j);
				if (temp.getClose()>close) {
					up+=temp.getClose()-close;
				}
				else {
					down+=close-temp.getClose();
				}
			}
			
			up/=total;
			down/=total;
			double rsi=up/down;
			rsi/=(1+rsi);
			rsi*=100;
			stock.setDate(date);
			stock.setClose(close);
			ans.add(stock);
		}
		return ans;
	}
}
