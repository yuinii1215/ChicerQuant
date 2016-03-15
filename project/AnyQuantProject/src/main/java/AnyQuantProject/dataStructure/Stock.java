/**
 * 
 */
package AnyQuantProject.dataStructure;

import java.io.Serializable;
import java.util.StringTokenizer;

/**
 * @author G
 * @param <T>
 *
 */
public class Stock extends AbstractStock implements Serializable{
	double turnover;	//换手率
	double pe_ttm;			//市盈率
	double pb;			//市净率
	
	
	public double getTurnover() {
		return turnover;
	}
	public double getPe_ttm() {
		return pe_ttm;
	}
	public double getPb() {
		return pb;
	}
	public void setTurnover(double turnover) {
		this.turnover = turnover;
	}
	public void setPe_ttm(double pe) {
		this.pe_ttm = pe;
	}
	public void setPb(double pb) {
		this.pb = pb;
	}
	
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		Stock stock=new Stock();
		stock.setPe_ttm(0.6);
		stock.setClose(0.7);
		stock.setFlow(1000000067l);
		System.out.println(stock.getValueByName("flow"));
	}
}
