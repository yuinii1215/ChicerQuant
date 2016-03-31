package AnyQuantProject.bl.calculateBL;

import java.util.ArrayList;
import java.util.List;

import AnyQuantProject.dataStructure.Stock;

/**
* AnyQuantProject/AnyQuantProject.bl.calculateBL/BIAS.java
* @author cxworks
* 2016年3月30日 上午8:47:20
*/
public class BIAS {
	public static List<DataCell> calculateBIAS(List<Stock> src,int day){
		List<DataCell> ans=new ArrayList<>(src.size());
		for(int i=day-1;i<src.size();i++){
			//
			Stock first=src.get(i);
			String date=first.getDate();
			double close=first.getClose();
			double all=close;
			for (int j = 1; j < day; j++) {
				all+=src.get(i-j).getClose();
			}
			double aver=all/day;
			double bias=(close-aver)/aver*100;
			DataCell biasData=new DataCell(date, bias);
			ans.add(biasData);
		}
		return ans;
	}

}
