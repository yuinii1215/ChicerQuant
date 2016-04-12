package AnyQuantProject.dataStructure;
/**
* AnyQuantProject/AnyQuantProject.dataStructure/IndustryPriceInfo.java
* @author cxworks
* 2016年4月12日 下午10:54:56
*/
public class IndustryPriceInfo {
	String industry;
	double open;
	double close;
	double max;
	double min;
	long volume;
	@Deprecated
	public IndustryPriceInfo() {
	}
	public IndustryPriceInfo(String industry){
		this.industry=industry;
	}
	
	public void setOpen(double open) {
		this.open = open;
	}
	public void setClose(double close) {
		this.close = close;
	}
	public void setMax(double max) {
		this.max = max;
	}
	public void setMin(double min) {
		this.min = min;
	}
<<<<<<< HEAD
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public double getOpen() {
		return open;
	}
	public double getClose() {
		return close;
	}
	public double getMax() {
		return max;
	}
	public double getMin() {
		return min;
	}
	
=======
	public void setVolume(long volume) {
		this.volume = volume;
	}
>>>>>>> 3ed8033b8692f8f178a327491af7fdafe69f3c06
	
}
