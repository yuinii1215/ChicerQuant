package AnyQuantProject.dataStructure.enumInterface;

/** 
*AnyQuantProject//AnyQuantProject.dataStructure.enumInterface//EnumObservable.java
* @author  cxworks 
* @date 创建时间：2016年3月3日 下午7:15:57 
*/

public interface EnumObservable<E extends Enum<E>> {
	
	public String getChinese();
	
	public String getEnglish();
}
