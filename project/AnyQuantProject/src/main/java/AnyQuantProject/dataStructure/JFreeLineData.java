package AnyQuantProject.dataStructure;

import java.util.List;

/**
* AnyQuantProject/AnyQuantProject.dataStructure/JFreeLineData.java
* @author cxworks
* 2016年4月7日 下午9:06:47
*/
public class JFreeLineData extends GraphData {
	
	public List<Cell>[] cells;
	public JFreeLineData(String graphTitle,List<Cell>...cells) {
		super(graphTitle);
		this.cells=cells;
	}
	@Deprecated
	public JFreeLineData() {
		super(null);
	}
}
