package AnyQuantProject.bl.factoryBL;

import AnyQuantProject.bl.benchMarkBL.BenchMarkBLImpl;
import AnyQuantProject.blService.benchMarkBLService.BenchMarkBLService;

/** 
*AnyQuantProject//AnyQuantProject.bl.factoryBL//BenchMarkBLFactory.java
* @author  cxworks 
* @date 创建时间：2016年3月2日 下午11:49:58 
*/

public class BenchMarkBLFactory extends FactoryBL {
	private static BenchMarkBLService benchMarkBLService=null;
	
	public static BenchMarkBLService getBenchMarBLService(){
		if (benchMarkBLService==null) {
			benchMarkBLService=new BenchMarkBLImpl();
		}
		return benchMarkBLService;
	}
	
}
