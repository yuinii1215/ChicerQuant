package AnyQuantProject.dataStructure;

import org.python.antlr.base.boolop;

/** 
* AnyQuantProject//AnyQuantProject.dataStructure//KLineDataDTO.java
* @author  cxworks 
* @date 创建时间：2016年3月14日 下午11:06:49 
*/

public interface KLineDataDTO {
	public String getYear();
	public String getMonth();
	public String getDay();
	public double getClose();
	public double getOpen();
	public double getHigh();
	public double getLow();
	public long getFlow();
        public int getVolume();
	public boolean isRed();
}
