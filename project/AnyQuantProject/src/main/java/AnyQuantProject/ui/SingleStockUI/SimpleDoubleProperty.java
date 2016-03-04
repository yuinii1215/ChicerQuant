package AnyQuantProject.ui.singleStockUI;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/** 
*AnyQuantProject//AnyQuantProject.ui.SingleStockUI//SimpleDoubleProperty.java
* @author  cxworks 
* @date 创建时间：2016年3月4日 下午2:48:47 
*/

public class SimpleDoubleProperty implements ObservableValue<Double>{
	private double num;
	public SimpleDoubleProperty(double num) {
		this.num=num;
	}

	@Override
	public void addListener(InvalidationListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListener(InvalidationListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addListener(ChangeListener<? super Double> listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListener(ChangeListener<? super Double> listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Double getValue() {
		return num;
	}
	@Override
	public String toString(){
		return Double.toString(num);
	}
}
