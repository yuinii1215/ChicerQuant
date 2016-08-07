package AnyQuantProject.blService.favoriteBLService;

import java.util.List;

import AnyQuantProject.dataStructure.OperationResult;
import AnyQuantProject.dataStructure.Stock;

/** 
*AnyQuantProject//AnyQuantProject.blService.favoriteBLService//FavoriteBLService.java
* @author  cxworks 
* @date 创建时间：2016年3月2日 上午11:38:32 
*/

public interface FavoriteBLService {
	/**
	 * 
	 * @return返回喜爱的股票列表
	 */
	List<Stock> getMyFavor();
	/**
	 * 关注股票
	 * @param name喜爱的股票名称
	 * @return操作结果
	 */
	OperationResult favorStock(String name);
	/**
	 * 取消关注
	 * @param name取消关注的，名称
	 * @return操作结果
	 */
	OperationResult unFavorStock(String name);
	/**
	 * 检查是否被关注，
	 * @param name
	 * @return 已被关注返回true
	 */
	boolean checkIsFavored(String name);
}
