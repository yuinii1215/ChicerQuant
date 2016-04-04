package AnyQuantProject.dataStructure;

/**
 * Created by G on 16/4/1.
 */
public class IndustryInfo {
    String industry;
    double updown;//涨跌幅,百分数
    long pure;//净额,
    double total;
    @Deprecated
    public IndustryInfo(){}

    public IndustryInfo(String name){
        this.industry=name;
    }

    public void  setUpdown(double updown){
        this.updown=updown;
    }
    public  void  setPure(long pure){
        this.pure=pure;
    }
    public void setTotal(double total){
    	this.total=total;
    }
}
