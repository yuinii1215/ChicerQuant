package AnyQuantProject.dataStructure;

import java.util.StringTokenizer;

import AnyQuantProject.bl.calculateBL.DataCell;

/**
* AnyQuantProject/AnyQuantProject.dataStructure/Cell.java
* @author cxworks
* 2016年4月7日 下午9:26:58
*/
public class Cell{
	public String x;
	public double y;
	public Cell(DataCell d){
		this(d.x, d.y);
	}
	public Cell(String x,double y) {
		this.x=x;
		this.y=y;
	}
	public String getYear(){
		StringTokenizer stringTokenizer=new StringTokenizer(x, "-/");
		return stringTokenizer.nextToken();
	}
	public String getMonth(){
		StringTokenizer stringTokenizer=new StringTokenizer(x, "-/");
		String year=stringTokenizer.nextToken();
		return stringTokenizer.nextToken();
	}
	public String getDay(){
		StringTokenizer stringTokenizer=new StringTokenizer(x, "-/");
		String year=stringTokenizer.nextToken();
		String month=stringTokenizer.nextToken();
		return stringTokenizer.nextToken();
	}
}
