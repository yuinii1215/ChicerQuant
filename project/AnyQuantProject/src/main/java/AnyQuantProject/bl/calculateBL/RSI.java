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
	public static List<DataCell> calculateRSI(List<Stock> src,int day){
		List<DataCell> ans=new ArrayList<>(src.size());
		for (int i = day-1; i < src.size(); i++) {
			Stock first=src.get(i);
			double close=first.getClose();
			String date=first.getDate();
			double up=0;
			double down=0;
			//
			for (int j = 1; j < day; j++) {
				Stock temp=src.get(i-j);
				if (temp.getClose()>close) {
					up+=temp.getClose()-close;
				}
				else {
					down+=close-temp.getClose();
				}
			}
			
			up/=day;
			down/=day;
			double rsi=up/down;
			rsi/=(1+rsi);
			rsi*=100;
			DataCell dataCell=new DataCell(date, rsi);
			ans.add(dataCell);
		}
		return ans;
	}
}
