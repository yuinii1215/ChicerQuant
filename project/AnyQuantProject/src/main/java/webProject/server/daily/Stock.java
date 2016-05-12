package webProject.server.daily;

/**
* AnyQuantProject/webProject.server.daily/Stock.java
* @author cxworks
* 2016年5月5日 下午7:17:45
*/
public class Stock extends Common {
	double turnover;
	double pe_ttm;
	double pb;
	String industry;
	public double getTurnover() {
		return turnover;
	}
	public void setTurnover(double turnover) {
		this.turnover = turnover;
	}
	public double getPe_ttm() {
		return pe_ttm;
	}
	public void setPe_ttm(double pe_ttm) {
		this.pe_ttm = pe_ttm;
	}
	public double getPb() {
		return pb;
	}
	public void setPb(double pb) {
		this.pb = pb;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	
}
