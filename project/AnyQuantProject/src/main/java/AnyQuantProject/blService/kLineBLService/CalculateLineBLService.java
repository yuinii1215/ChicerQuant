package AnyQuantProject.blService.kLineBLService;
/**
* AnyQuantProject/AnyQuantProject.blService.kLineBLService/CalculateLineBLService.java
* @author cxworks
* 2016年3月29日 下午7:20:19
*/

import AnyQuantProject.dataStructure.JFreeLineData;
import AnyQuantProject.dataStructure.LineChartData;
import java.util.Calendar;


public interface CalculateLineBLService{
	/**
	 * 返回股票的价格波动情况以及涨跌幅、成交量
	 * 只有一条价格曲线，涨跌幅、成交量依次排列
	 * @param name
	 * @return
	 */
	public LineChartData drawPreview(String name);
	/**
	 * 股票相对强弱指数，RSI（Relative Strength Index）
	 * 返回共三条折线，分别是6、12、24日的RSI
	 * X轴为String的日期，Y轴为0~100的数字，是百分数
	 * @param name 股票代号
	 * @return 
	 */
	public JFreeLineData drawRSI(String name);
	/**
	 * 股票乖离率，BIAS（没有找到全名）
	 * 返回共三条折线，分别是6、12、24日的乖离率
	 * X轴为String的日期，Y轴为0~100的数字，是百分数
	 * @param name 股票代号
	 * @return
	 */
	public	JFreeLineData drawBIAS(String name,Calendar minTime,Calendar maxTime);
	/**
	 * 股票随机指标，(Stochastics)
	 * 共三条曲线，分别是KDJ的值
	 * @param name
	 * @return
	 */
	public JFreeLineData drawKDJ(String name,Calendar minTime,Calendar maxTime);
	/**
	 * 指数平滑异同平均线,(Moving Average Convergence / Divergence)
	 * DIF,DEA,MACD
	 * @param name
	 * @return
	 */
	public JFreeLineData drawMACD(String name,Calendar minTime,Calendar maxTime);
}