package AnyQuantProject.bl.factoryBL;

import AnyQuantProject.bl.favoriteBL.FavoriteBLController;
import AnyQuantProject.blService.favoriteBLService.FavoriteBLService;

/** 
*AnyQuantProject//AnyQuantProject.bl.factoryBL//FavoriteBLFactory.java
* @author  cxworks 
* @date 创建时间：2016年3月2日 下午11:51:49 
*/

public class FavoriteBLFactory extends FactoryBL {
	private static FavoriteBLService favoriteBLService=null;
	
	public static FavoriteBLService getFavoriteBLService(){
		if (favoriteBLService==null) {
			favoriteBLService=new FavoriteBLController();
		}
		return favoriteBLService;
	}
}
