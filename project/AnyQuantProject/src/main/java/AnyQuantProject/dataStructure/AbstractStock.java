/**
 * 
 */
package AnyQuantProject.dataStructure;

import java.util.Calendar;

/**
 * @author G
 *
 */
public class AbstractStock {
	String name;	//
	boolean isFavor;     //是否被关注
	Calendar   date;      // 数据日期
	double open;		//开盘价
	double high; 		//最高价
	double low;			//最低价
	double close;		//收盘价
	int	volume;			//成交量
	double adj_price;	//后复权价
	long marketvalue;	//市值
	long flow;			//流通
	
	/**
	 * 
	 */
	public AbstractStock() {
	}
	
	
	
	public String getName() {
		return name;
	}
	public boolean isFavor() {
		return isFavor;
	}
	public Calendar getDate() {
		return date;
	}
	public double getOpen() {
		return open;
	}
	public double getHigh() {
		return high;
	}
	public double getLow() {
		return low;
	}
	public double getClose() {
		return close;
	}
	public int getVolume() {
		return volume;
	}
	public double getAdj_price() {
		return adj_price;
	}
	public long getMarketvalue() {
		return marketvalue;
	}
	public long getFlow() {
		return flow;
	}

	
}
