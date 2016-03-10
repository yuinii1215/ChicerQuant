package AnyQuantProject.bl.factoryBL;

import AnyQuantProject.bl.listFilterBL.ListFilterBLImpl;
import AnyQuantProject.blService.listFilterBLService.ListFilterBLService;

/** 
*AnyQuantProject//AnyQuantProject.bl.factoryBL//ListFilterBLFactory.java
* @author  cxworks 
* @date 创建时间：2016年3月2日 下午11:52:17 
*/

public class ListFilterBLFactory extends FactoryBL {
	private static ListFilterBLService listFilterBLService=null;
	
	public static ListFilterBLService getListFilterBLService(){
		//TODO 
		if (listFilterBLService==null) {
			listFilterBLService=new ListFilterBLImpl();
		}
		return listFilterBLService;
	}
}
