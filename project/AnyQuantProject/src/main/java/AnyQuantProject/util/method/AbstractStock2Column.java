package AnyQuantProject.util.method;

import java.util.Map;
import java.util.Set;

import AnyQuantProject.dataStructure.AbstractStock;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;

/** 
* AnyQuantProject//AnyQuantProject.util.method//Stock2Column.java
* @author  cxworks 
* @date 创建时间：2016年3月12日 下午8:15:40 
*/

public abstract class AbstractStock2Column<T extends AbstractStock> {
	public abstract Set<Map.Entry<String, Double>> set(T stock);
	
	public static void setKValue(TableColumn<Map.Entry<String, Double>, String> kColumn){
		kColumn.setCellValueFactory(
				cell-> new SimpleStringProperty(cell.getValue().getKey())
				);
	}
	
	public static void setVValue(TableColumn<Map.Entry<String, Double>, Double> vColumn){
		vColumn.setCellValueFactory(
				cell-> new SimpleDoubleProperty(cell.getValue().getValue())
				);
		
	}
}
