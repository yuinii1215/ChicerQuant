package AnyQuantProject.blService.kLineBLService;
/**
* AnyQuantProject/AnyQuantProject.blService.kLineBLService/CalculateLineBLService.java
* @author cxworks
* 2016年3月29日 下午7:20:19
*/

import AnyQuantProject.dataStructure.LineChartData;


public interface CalculateLineBLService{
	/**
	 * 股票相对强弱指数，RSI（Relative Strength Index）
	 * 返回共三条折线，分别是6、12、24日的RSI
	 * X轴为String的日期，Y轴为0~100的数字，是百分数
	 * @param name 股票代号
	 * @return 
	 */
	public LineChartData drawRSI(String name);
	/**
	 * 股票乖离率，BIAS（没有找到全名）
	 * 返回共三条折线，分别是6、12、24日的乖离率
	 * X轴为String的日期，Y轴为0~100的数字，是百分数
	 * @param name 股票代号
	 * @return
	 */
	public	LineChartData drawBIAS(String name);
}