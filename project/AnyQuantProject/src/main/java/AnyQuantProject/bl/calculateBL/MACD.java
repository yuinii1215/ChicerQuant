package AnyQuantProject.bl.calculateBL;

import java.util.ArrayList;
import java.util.List;

import AnyQuantProject.dataStructure.Stock;
/**
* AnyQuantProject/AnyQuantProject.bl.calculateBL/MACD.java
* @author cxworks
* 2016年4月7日 上午10:50:25
*/
public class MACD {
	public static List<DataCell> calculateMACD(List<Stock> src,int fast,int mid ,int slow){
		List<DataCell> ans=new ArrayList<>(src.size());
		double emaF=src.subList(slow-fast-2, slow-2).stream().mapToDouble(s->s.getClose()).average().getAsDouble();
		double emaS=src.subList(0, slow-2).stream().mapToDouble(s->s.getClose()).average().getAsDouble();
		double dea=Double.NaN;
		double dif=Double.NaN;
		double macd=Double.NaN;
		for (int i = slow-1; i < src.size(); i++) {
			Stock today=src.get(i);
			String date=today.getDate();
			double close=today.getClose();
			emaF=emaF*(fast-1)/(fast+1)+close*2/(fast+1);
			emaS=emaS*(slow-1)/(slow+1)+close*2/(slow+1);
			dif=emaF-emaS;
			if (dea==Double.NaN) {
				dea=dif;
			}
			dea=dea*(mid-1)/(mid+1)+dif*2/(mid+1);
			macd=(dif-dea)*2;
			DataCell dataCell=new DataCell(date, dif);
			dataCell.y2=dea;
			dataCell.y3=macd;
			ans.add(dataCell);
		}
		return ans;
	}
}
