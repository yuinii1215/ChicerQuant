package AnyQuantProject.dataStructure;

/** 
*AnyQuantProject//AnyQuantProject.dataStructure//GraphBL.java
* @author  cxworks 
* @date 创建时间：2016年3月5日 下午2:48:37 
*/
/**
 * 所有绘图的数据包的父类，虽然目前不造有什么用
 * @author cxworks
 *
 */
public abstract class GraphData {
	String title;
	public GraphData(String graphTitle) {
		this.title=graphTitle;
	}
	public String getTitle() {
		return title;
	}
	
}
