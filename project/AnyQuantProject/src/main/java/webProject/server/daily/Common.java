package webProject.server.daily;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import AnyQuantProject.util.method.CalendarHelper;

/**
* AnyQuantProject/webProject.server.daily/Common.java
* @author cxworks
* 2016年5月5日 上午9:14:38
*/
public abstract class Common {
	String stock_id;
	String stock_name;
	double open;
	double high;
	double low;
	double close;
	int volumn;
	double adj_price;
	
	double PMA5_day;
	double PMA5_week;
	double PMA5_month;
	double PMA10_day;
	double PMA10_week;
	double PMA10_month;
	double PMA30_day;
	double PMA30_week;
	double PMA30_month;
	double RSI6;
	double RSI12;
	double RSI24;
	double BIAS6;
	double BIAS12;
	double BIAS24;
	double K,D,J;
	double DIF,DEA,MACHBar;
	double poly;
	Date date;
	public String getStock_id() {
		return stock_id;
	}
	public void setStock_id(String stock_id) {
		this.stock_id = stock_id;
	}
	public String getStock_name() {
		return stock_name;
	}
	public void setStock_name(String stock_name) {
		this.stock_name = stock_name;
	}
	public double getOpen() {
		return open;
	}
	public void setOpen(double open) {
		this.open = open;
	}
	public double getHigh() {
		return high;
	}
	public void setHigh(double high) {
		this.high = high;
	}
	public double getLow() {
		return low;
	}
	public void setLow(double low) {
		this.low = low;
	}
	public double getClose() {
		return close;
	}
	public void setClose(double close) {
		this.close = close;
	}
	public int getVolumn() {
		return volumn;
	}
	public void setVolumn(int volumn) {
		this.volumn = volumn;
	}
	public double getAdj_price() {
		return adj_price;
	}
	public void setAdj_price(double adj_price) {
		this.adj_price = adj_price;
	}
	public double getPMA5_day() {
		return PMA5_day;
	}
	public void setPMA5_day(double pMA5_day) {
		PMA5_day = pMA5_day;
	}
	public double getPMA5_week() {
		return PMA5_week;
	}
	public void setPMA5_week(double pMA5_week) {
		PMA5_week = pMA5_week;
	}
	public double getPMA5_month() {
		return PMA5_month;
	}
	public void setPMA5_month(double pMA5_month) {
		PMA5_month = pMA5_month;
	}
	public double getPMA10_day() {
		return PMA10_day;
	}
	public void setPMA10_day(double pMA10_day) {
		PMA10_day = pMA10_day;
	}
	public double getPMA10_week() {
		return PMA10_week;
	}
	public void setPMA10_week(double pMA10_week) {
		PMA10_week = pMA10_week;
	}
	public double getPMA10_month() {
		return PMA10_month;
	}
	public void setPMA10_month(double pMA10_month) {
		PMA10_month = pMA10_month;
	}
	public double getPMA30_day() {
		return PMA30_day;
	}
	public void setPMA30_day(double pMA30_day) {
		PMA30_day = pMA30_day;
	}
	public double getPMA30_week() {
		return PMA30_week;
	}
	public void setPMA30_week(double pMA30_week) {
		PMA30_week = pMA30_week;
	}
	public double getPMA30_month() {
		return PMA30_month;
	}
	public void setPMA30_month(double pMA30_month) {
		PMA30_month = pMA30_month;
	}
	public double getRSI6() {
		return RSI6;
	}
	public void setRSI6(double rSI6) {
		RSI6 = rSI6;
	}
	public double getRSI12() {
		return RSI12;
	}
	public void setRSI12(double rSI12) {
		RSI12 = rSI12;
	}
	public double getRSI24() {
		return RSI24;
	}
	public void setRSI24(double rSI24) {
		RSI24 = rSI24;
	}
	public double getBIAS6() {
		return BIAS6;
	}
	public void setBIAS6(double bIAS6) {
		BIAS6 = bIAS6;
	}
	public double getBIAS12() {
		return BIAS12;
	}
	public void setBIAS12(double bIAS12) {
		BIAS12 = bIAS12;
	}
	public double getBIAS24() {
		return BIAS24;
	}
	public void setBIAS24(double bIAS24) {
		BIAS24 = bIAS24;
	}
	public double getK() {
		return K;
	}
	public void setK(double k) {
		K = k;
	}
	public double getD() {
		return D;
	}
	public void setD(double d) {
		D = d;
	}
	public double getJ() {
		return J;
	}
	public void setJ(double j) {
		J = j;
	}
	public double getDIF() {
		return DIF;
	}
	public void setDIF(double dIF) {
		DIF = dIF;
	}
	public double getDEA() {
		return DEA;
	}
	public void setDEA(double dEA) {
		DEA = dEA;
	}
	public double getMACHBar() {
		return MACHBar;
	}
	public void setMACHBar(double mACHBar) {
		MACHBar = mACHBar;
	}
	public double getPoly() {
		return poly;
	}
	public void setPoly(double poly) {
		this.poly = poly;
	}
	public void setDate(Calendar calendar){
		date=new Date(CalendarHelper.getAfterDay(calendar).getTimeInMillis());
	}
	
}
