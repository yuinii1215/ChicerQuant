package AnyQuantProject.util.method;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/** 
*AnyQuantProject//AnyQuantProject.ui.SingleStockUI//SimpleIntegerProperty.java
* @author  grace
* @date 创建时间：2016年3月4日 下午3:38:47 
*/

public class SimpleIntegerProperty implements ObservableValue<Integer>{
	private int num;
	
	public SimpleIntegerProperty(int num) {
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
	public void addListener(ChangeListener<? super Integer> listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListener(ChangeListener<? super Integer> listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getValue() {
		return num;
	}
	@Override
	public String toString(){
		if(num==0){
			return null;
		}
		return Integer.toString(num);
	}
}
