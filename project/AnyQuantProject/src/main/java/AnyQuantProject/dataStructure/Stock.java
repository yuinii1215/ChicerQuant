/**
 * 
 */
package AnyQuantProject.dataStructure;

import java.lang.reflect.Field;

/**
 * @author G
 * @param <T>
 *
 */
public class Stock<T> extends AbstractStock{
	double turnover;	//换手率
	double pe;			//市盈率
	double pb;			//市净率
	
	public double getTurnover() {
		return turnover;
	}
	public double getPe() {
		return pe;
	}
	public double getPb() {
		return pb;
	}
	public void setTurnover(double turnover) {
		this.turnover = turnover;
	}
	public void setPe(double pe) {
		this.pe = pe;
	}
	public void setPb(double pb) {
		this.pb = pb;
	}
	
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		Stock stock=new Stock();
		stock.setPe(0.6);
		stock.setClose(0.7);
		stock.setFlow(1000000067l);
		System.out.println(stock.getValueByName("flow"));
	}
}
