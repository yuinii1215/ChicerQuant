package AnyQuantProject.blService.industryBLService;

import java.util.List;

import AnyQuantProject.dataStructure.IndustryInfo;
import AnyQuantProject.dataStructure.IndustryPriceInfo;
import AnyQuantProject.dataStructure.Stock;

/** 
* AnyQuantProject//AnyQuantProject.blService.industryBLService//IndustryBLService.java
* @author  cxworks 
* @date 创建时间：2016年3月25日 上午11:03:28 
*/

public interface IndustryBLService {
	/**
	 * 获得所有分类行业的名称
	 * @return
	 */
	public List<String> getAllIndustries();
	/**
	 *  通过行业名称获得行业下所有股票的信息
	 * @param industry
	 * @return
	 */
	public List<Stock> getStocksByIndustry(String industry);
	/**
	 * 通过股票名称获取行业分类信息
	 * @param stockName 股票名称
	 * @return
	 */
	public String getIndustryByName(String stockName);

    /**
     * 获取行业信息
     * @param industry 行业名称
     * @return
     */
	public IndustryInfo getIndustryInfo(String industry);
	/**
	 * 返回所有行业信息列表
	 * @return
	 * @warning 信息是昨天（最新）信息
	 */
	public List<IndustryInfo> getAllIndustryInfo();
	/**
	 * get the price of the whole industry
	 * @param industry
	 * @return
	 */
	public IndustryPriceInfo getIndustryPrice(String industry);
	
	public Stock getYesterday(String name);
}
