package AnyQuantProject.ui.singleStockInfoUI;

import java.util.Map.Entry;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.util.method.AbstractStock2Column;

/** 
* AnyQuantProject//AnyQuantProject.ui.singleStockInfoUI//StockInfo2Column.java
* @author  cxworks 
* @date 创建时间：2016年3月12日 下午8:25:28 
*/

public class StockInfo2Column extends AbstractStock2Column<Stock> {

	@Override
	public Set<Entry<String, Double>> set(Stock stock) {
		Map<String, Double> map=new HashMap<>();
		map.put("最高价", stock.getHigh());
		map.put("最低价", stock.getLow());
		//选择你想要加入显示的属性。。。
		return map.entrySet();
	}

}
