package AnyQuantProject.bl.calculateBL;

import java.util.ArrayList;
import java.util.List;

import AnyQuantProject.dataStructure.Stock;

/**
* AnyQuantProject/AnyQuantProject.bl.calculateBL/KDJ.java
* @author cxworks
* 2016年4月7日 上午10:04:45
*/
public class KDJ {
	public static List<DataCell> calculateRSI(List<Stock> src,int day){
		List<DataCell> ans=new ArrayList<>(src.size());
		for(int i=day-1;i<src.size();i++){
			Stock today=src.get(i);
			String date=today.getDate();
			double close=today.getClose();
			double high=Double.MIN_VALUE;
			double low=Double.MAX_VALUE;
			for(int j=i-day+1;j<=i;j++){
				double tar=src.get(j).getClose();
				if (tar>high) {
					high=tar;
				}
				if (tar<low) {
					low=tar;
				}
			}
			double rsi=(close-low)/(high-low)*100;
			if (high-low==0) {
				rsi=0;
			}
			DataCell dataCell=new DataCell(date, rsi);
			ans.add(dataCell);
		}
		return ans;
	} 

	
	public static List<DataCell> calculateKDJ(List<DataCell> src){
		List<DataCell> ans=new ArrayList<>(src.size());
		double k=50;
		double d=50;
		double j=0;
		for (int i = 0; i < src.size(); i++) {
			DataCell dataCell=src.get(i);
			double rsi=dataCell.y;
			//
			k=2.0/3*k+1.0/3*rsi;
			d=2.0/3*d+1.0/3*k;
			j=3*k-2*d;
			DataCell temp=new DataCell(dataCell.x,k );
			temp.y2=d;
			temp.y3=j;
			ans.add(temp);
		}
		return ans;
	}
}
