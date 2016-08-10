<<<<<<< HEAD

/**
 * 
 */
package AnyQuantProject.dataStructure;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.StringTokenizer;

import AnyQuantProject.util.method.CalendarHelper;
import AnyQuantProject.util.method.Checker;

/**
 * @author G
 *
 */
public class AbstractStock implements Serializable,KLineDataDTO{
	String name;	//代号名
	String chinese;  //中文名 
	boolean isFavor;     //是否被关注
	String date;    // 数据日期
	double open;		//开盘价
	double high; 		//最高价
	double low;			//最低价	
	double close;		//收盘价
	int	volume;			//成交量
	double turnoverValue; //成交金额
	double adj_price;	//后复权价
	long marketvalue;	//市值
	long flow;			//流通
	
	
	public AbstractStock() {
	}
	
	public AbstractStock(String name, String chinese) {
		this.name = name;
		this.chinese = chinese;
	}
	
	public double getValueByName(String name) throws IllegalArgumentException, IllegalAccessException,ClassCastException{
		//
		Class<?> cla=this.getClass();
		while (cla!=Object.class) {
			Field[] fields=cla.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].getName().equalsIgnoreCase(name)) {
					fields[i].setAccessible(true);
					return ((Number) fields[i].get(this)).doubleValue();
				}
			}
			cla=cla.getSuperclass();
		}
		//
		return 0;
	}
	

	public void setName(String name) {
		this.name = name;
	}



	public void setFavor(boolean isFavor) {
		this.isFavor = isFavor;
	}




	public void setOpen(double open) {
		this.open = open;
	}



	public void setHigh(double high) {
		this.high = high;
	}



	public void setLow(double low) {
		this.low = low;
	}



	public void setClose(double close) {
		this.close = close;
	}



	public void setVolume(int volume) {
		this.volume = volume;
	}



	public void setAdj_price(double adj_price) {
		this.adj_price = adj_price;
	}



	public void setMarketvalue(long marketvalue) {
		this.marketvalue = marketvalue;
	}



	public String getDate() {
		return date;
	}
	public void setFlow(long flow) {
		this.flow = flow;
	}
	public Calendar getDateInCalendar(){
			return CalendarHelper.convert2Calendar(date);
		
	}


	public String getName() {
		return name;
	}
	public boolean isFavor() {
		return isFavor;
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
	public String getYear(){
		StringTokenizer stringTokenizer=new StringTokenizer(date, "-/");
		return stringTokenizer.nextToken();
	}
	public String getMonth(){
		StringTokenizer stringTokenizer=new StringTokenizer(date, "-/");
		String year=stringTokenizer.nextToken();
		return stringTokenizer.nextToken();
	}
	public String getDay(){
		StringTokenizer stringTokenizer=new StringTokenizer(date, "-/");
		String year=stringTokenizer.nextToken();
		String month=stringTokenizer.nextToken();
		return stringTokenizer.nextToken();
	}
	
	public void setDate(String date) {
		this.date = date;
	}


	public String getChinese() {
		return chinese;
	}


	public void setChinese(String chinese) {
		this.chinese = chinese;
	}

	@Override
	public boolean isCloseRed() {
		return close>yesterClose;
	}

	public double getTurnoverValue() {
		return turnoverValue;
	}

	public void setTurnoverValue(double turnoverValue) {
		this.turnoverValue = turnoverValue;
	}
	double yesterClose;
	double yesterOpen;
	double yesterHigh;
	double yesterLow;
	public void setYesterday(AbstractStock yes){
		yesterClose=yes.close;
		yesterOpen=yes.open;
		yesterHigh=yes.high;
		yesterLow=yes.low;
	}

	@Override
	public boolean isOpenRed() {
		return open>yesterOpen;
	}

	@Override
	public boolean isHighRed() {
		return high>yesterHigh;
	}

	@Override
	public boolean isLowRed() {
		return low>yesterLow;
	}
}

=======

/**
 * 
 */
package AnyQuantProject.dataStructure;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.StringTokenizer;

import AnyQuantProject.util.method.CalendarHelper;
import AnyQuantProject.util.method.Checker;

/**
 * @author G
 *
 */
public class AbstractStock implements Serializable,KLineDataDTO{
	String name;	//代号名
	String chinese;  //中文名 
	boolean isFavor;     //是否被关注
	String date;    // 数据日期
	double open;		//开盘价
	double high; 		//最高价
	double low;			//最低价	
	double close;		//收盘价
	int	volume;			//成交量
	double turnoverValue; //成交金额
	double adj_price;	//后复权价
	long marketvalue;	//市值
	long flow;			//流通
	
	
	public AbstractStock() {
	}
	
	public AbstractStock(String name, String chinese) {
		this.name = name;
		this.chinese = chinese;
	}
	
	public double getValueByName(String name) throws IllegalArgumentException, IllegalAccessException,ClassCastException{
		//
		Class<?> cla=this.getClass();
		while (cla!=Object.class) {
			Field[] fields=cla.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].getName().equalsIgnoreCase(name)) {
					fields[i].setAccessible(true);
					return ((Number) fields[i].get(this)).doubleValue();
				}
			}
			cla=cla.getSuperclass();
		}
		//
		return 0;
	}
	

	public void setName(String name) {
		this.name = name;
	}



	public void setFavor(boolean isFavor) {
		this.isFavor = isFavor;
	}




	public void setOpen(double open) {
		this.open = open;
	}



	public void setHigh(double high) {
		this.high = high;
	}



	public void setLow(double low) {
		this.low = low;
	}



	public void setClose(double close) {
		this.close = close;
	}



	public void setVolume(int volume) {
		this.volume = volume;
	}



	public void setAdj_price(double adj_price) {
		this.adj_price = adj_price;
	}



	public void setMarketvalue(long marketvalue) {
		this.marketvalue = marketvalue;
	}



	public String getDate() {
		return date;
	}
	public void setFlow(long flow) {
		this.flow = flow;
	}
	public Calendar getDateInCalendar(){
			return CalendarHelper.convert2Calendar(date);
		
	}


	public String getName() {
		return name;
	}
	public boolean isFavor() {
		return isFavor;
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
	public String getYear(){
		StringTokenizer stringTokenizer=new StringTokenizer(date, "-/");
		return stringTokenizer.nextToken();
	}
	public String getMonth(){
		StringTokenizer stringTokenizer=new StringTokenizer(date, "-/");
		String year=stringTokenizer.nextToken();
		return stringTokenizer.nextToken();
	}
	public String getDay(){
		StringTokenizer stringTokenizer=new StringTokenizer(date, "-/");
		String year=stringTokenizer.nextToken();
		String month=stringTokenizer.nextToken();
		return stringTokenizer.nextToken();
	}
	
	public void setDate(String date) {
		this.date = date;
	}


	public String getChinese() {
		return chinese;
	}


	public void setChinese(String chinese) {
		this.chinese = chinese;
	}

	@Override
	public boolean isCloseRed() {
		return close>yesterClose;
	}

	public double getTurnoverValue() {
		return turnoverValue;
	}

	public void setTurnoverValue(double turnoverValue) {
		this.turnoverValue = turnoverValue;
	}
	double yesterClose;
	double yesterOpen;
	double yesterHigh;
	double yesterLow;
	public void setYesterday(AbstractStock yes){
		yesterClose=yes.close;
		yesterOpen=yes.open;
		yesterHigh=yes.high;
		yesterLow=yes.low;
	}

	@Override
	public boolean isOpenRed() {
		return open>yesterOpen;
	}

	@Override
	public boolean isHighRed() {
		return high>yesterHigh;
	}

	@Override
	public boolean isLowRed() {
		return low>yesterLow;
	}
}

>>>>>>> 1386f407833e5c3a743fab7ea83da58cdf56ef51
