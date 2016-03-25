package AnyQuantProject.bl.factoryBL;

import AnyQuantProject.bl.industryBL.IndustryBLImpl;
import AnyQuantProject.blService.industryBLService.IndustryBLService;

/** 
* AnyQuantProject//AnyQuantProject.bl.factoryBL//IndustryBLFactory.java
* @author  cxworks 
* @date 创建时间：2016年3月25日 下午3:06:13 
*/

public class IndustryBLFactory extends FactoryBL {
	private static IndustryBLService industryBLService=null;
	public static IndustryBLService getIndustryBLService(){
		if (industryBLService==null) {
			industryBLService=new IndustryBLImpl();
		}
		return industryBLService;
	}

}
