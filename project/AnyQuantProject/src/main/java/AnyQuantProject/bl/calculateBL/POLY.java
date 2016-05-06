package AnyQuantProject.bl.calculateBL;

import java.util.List;
import java.util.stream.Collectors;

import org.jfree.data.statistics.MeanAndStandardDeviation;

import AnyQuantProject.dataStructure.Stock;

/**
* AnyQuantProject/AnyQuantProject.bl.calculateBL/POLY.java
* @author cxworks
* 2016年4月13日 下午11:46:31
*/
public class POLY {
	
	public static double calculateStockPOLY(List<Stock> src){
		List<Double> tDoubles=src.stream().map(s->s.getClose()).collect(Collectors.toList());
		return calculatePOLY(tDoubles);
	}
	public static double calculatePOLY(List<Double> src){
		Double[] close=(Double[])src.stream().collect(Collectors.toList()).toArray(new Double[src.size()]);
		int ansX=src.size()+1;
		Double[] y=new Double[5];
		y[0]=y[4]=new Double(0);
		for (int i = 1; i < 4; i++) {
			y[i]=close[i+1]+close[i-1]-2*close[i];
			y[i]*=6;
		}
		//
		Double[] m=new Double[5];
		m[0]=m[4]=new Double(0);
		m[2]=y[2]-(y[1]+y[3])/2;
		m[3]=(y[3]-m[2])/2;
		m[1]=(y[1]-m[2])/2;
		Double[][] func=new Double[4][4];
		for (int i = 0; i < func.length; i++) {
			//a
			func[i][0]=close[i];
			//b
			func[i][1]=close[i+1]-close[i]+1.0/6*m[i+1]-2.0/3*m[i];
			//c
			func[i][2]=m[i]/2;
			//d
			func[i][3]=(m[i+1]-m[i])/6;
		}
		//
		
		//
		return func[3][1]*close[4]/100+close[4];
	}
}
