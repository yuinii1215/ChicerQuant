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

                map.put("开盘价",stock.getOpen());
		map.put("最高价", stock.getHigh());
		map.put("最低价", stock.getLow());
                map.put("收盘价",stock.getClose());
		return map.entrySet();
	}
        	
	public Set<Entry<String, Double>> set2(Stock stock) {
		Map<String, Double> map=new HashMap<>();
                map.put("后复权价",stock.getAdj_price());
                map.put("换手率",stock.getTurnover());
                map.put("市盈率",stock.getPe_ttm());
                map.put("市净率",stock.getPb());           
		//选择你想要加入显示的属性。。。
		return map.entrySet();
	}

}
