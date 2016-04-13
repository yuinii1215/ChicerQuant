package AnyQuantProject.bl.calculateBL;

import java.util.List;
import java.util.stream.Collectors;

import AnyQuantProject.dataStructure.Stock;

/**
* AnyQuantProject/AnyQuantProject.bl.calculateBL/POLY.java
* @author cxworks
* 2016年4月13日 下午11:46:31
*/
public class POLY {
	public static double calculatePOLY(List<Stock> src){
		Double[] close=(Double[])src.stream().map(s->s.getClose()).collect(Collectors.toList()).toArray(new Double[src.size()]);
		int ansX=src.size()+1;
		Double[] y=new Double[5];
		y[0]=y[4]=new Double(0);
		for (int i = 1; i < 4; i++) {
			y[i]=close[i+1]+close[i-1]-2*close[i];
		}
		//
		return 0;
	}
}
