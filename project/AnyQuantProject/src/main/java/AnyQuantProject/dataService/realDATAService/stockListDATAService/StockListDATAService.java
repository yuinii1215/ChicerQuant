/**
 * 
 */
package AnyQuantProject.dataService.realDATAService.stockListDATAService;

import java.util.Calendar;
import java.util.List;

import AnyQuantProject.dataStructure.Exchange;
import AnyQuantProject.util.exception.NetFailedException;

/**
 * @author G
 *
 */
public interface StockListDATAService {
	/**
	 * 由指定年份、指定交易所的信息得到股票名称列表
	 * @param date
	 * @param exchange
	 * @return
	 */
	public List<String> getAllStocks(Calendar date, Exchange exchange) throws NetFailedException;

	/**
	 * 由指定年份、指定交易所的信息得到股票名称列表, string = 英文代号 + " " + 中文名
	 * @param date
	 * @param exchange
	 * @return
	 */
	public List<String> getAllWithChinese(Calendar date, Exchange exchange) throws NetFailedException;

	/**
	 * 从缓存的文件由单只股票的代号名得到中文名
	 * @param name
	 * @return
     */
	public String getChineseName(String name) throws NetFailedException;

}
