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
		List<Stock> ans=new ArrayList<>(src.size());
		for (int i = 0; i < src.size()-day; i++) {
			Stock stock = new Stock();
			double close=0.0;
			int total=0;
			String date=null;
			//
			for (int j = 0; j < day; j++) {
				Stock temp=src.get(i+j);
				if (j==0) {
					date=temp.getDate();
				}
				if (temp.getClose()>0) {
					close+=temp.getClose();
					total++;
				}
			}
			if (total==0) {
				total=1;
			}
			//
			close/=total;
			stock.setDate(date);
			stock.setClose(close);
			ans.add(stock);
		}
		return ans;
	}
}
