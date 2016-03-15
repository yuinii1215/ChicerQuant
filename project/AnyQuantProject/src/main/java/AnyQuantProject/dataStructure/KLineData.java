package AnyQuantProject.dataStructure;

import java.util.List;

/** 
* AnyQuantProject//AnyQuantProject.dataStructure//KLineData.java
* @author  cxworks 
* @date 创建时间：2016年3月14日 下午11:12:26 
*/

public class KLineData extends GraphData {
	private List<KLineDataDTO> kLineDataDTOs;

	public KLineData(String graphTitle,List<KLineDataDTO> kLineDataDTOs) {
		super(graphTitle);
		this.kLineDataDTOs=kLineDataDTOs;
	}
	public List<KLineDataDTO> geKLineDataDTOs(){
		return this.kLineDataDTOs;
	}
}
