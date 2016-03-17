package AnyQuantProject.util.method;

import java.awt.Paint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;

import AnyQuantProject.dataStructure.KLineDataDTO;
import AnyQuantProject.util.constant.TimeType;

public class DrawKLineChart {
	
	public static JFreeChart DayKLineChart (List<KLineDataDTO> dataList,String id,TimeType type ){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		
		String startDate = dataList.get(0).getYear()+"-"+dataList.get(0).getMonth()+"-"+dataList.get(0).getDay();
		System.out.println("......startDate......"+startDate);
//		if(type.equals(TimeType.DAY)){
//			startDate = dataList.get(0).getYear()+"-"+dataList.get(0).getMonth()+"-"+dataList.get(0).getDay();
//		 }
//		 else if(type.equals(TimeType.WEEK)){
//			 Calendar cal =Calendar.getInstance();
//			 Date date;
//			try {
//				date = dateFormat.parse(startDate);
//				cal.setTime(date);
//				cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
//				date=cal.getTime();
//				startDate =dateFormat.format(date);
//				System.out.println("......WeekstartDate......"+startDate);
//				
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			 
//		 }
//		 else if(type.equals(TimeType.MONTH)){
//			 startDate = dataList.get(0).getYear()+"-"+dataList.get(0).getMonth()+"-01";
//			 System.out.println("......MonthstartDate......"+startDate);
//		 }
//		
	     double highValue = Double.MIN_VALUE;// 设置K线数据当中的最大值
	     double minValue = Double.MAX_VALUE;// 设置K线数据当中的最小值
	     double high2Value = Double.MIN_VALUE;// 设置成交量的最大值
	     double min2Value = Double.MAX_VALUE;// 设置成交量的最低值
	     
	     
	     OHLCSeries series = new OHLCSeries("");// 高开低收数据序列，股票K线图的四个数据，依次是开，高，低，收
	     for (int i = 0; i < dataList.size(); i++) {
	            int date =Integer.parseInt(dataList.get(i).getDay());
	            int month =Integer.parseInt(dataList.get(i).getMonth());
	            int year =Integer.parseInt(dataList.get(i).getYear());
	            series.add(new Day(date, month, year), dataList.get(i).getOpen(),dataList.get(i).getHigh(), dataList.get(i).getLow(), dataList.get(i).getClose());
	      }
	     
	     final OHLCSeriesCollection seriesCollection = new OHLCSeriesCollection();// 保留K线数据的数据集，必须申明为final，后面要在匿名内部类里面用到
	     seriesCollection.addSeries(series);
	    
	     TimeSeries series2=new TimeSeries("");// 对应时间成交量数据
	     for (int i = 0; i < dataList.size(); i++) {
	    	  int date =Integer.parseInt(dataList.get(i).getDay());
	            int month =Integer.parseInt(dataList.get(i).getMonth());
	            int year =Integer.parseInt(dataList.get(i).getYear());
	            series2.add(new Day(date, month, year), dataList.get(i).getVolume()/ 100.0);
	        }
	     TimeSeriesCollection timeSeriesCollection=new TimeSeriesCollection();// 保留成交量数据的集合
	     timeSeriesCollection.addSeries(series2);
	
	     
	     // 获取K线数据的最高值和最低值
	     int seriesCount = seriesCollection.getSeriesCount();// 一共有多少个序列，目前为一个
	     	for (int i = 0; i < seriesCount; i++) {
	     		int itemCount = seriesCollection.getItemCount(i);// 每一个序列有多少个数据项
	     		for (int j = 0; j < itemCount; j++) {
	     			if (highValue < seriesCollection.getHighValue(i, j)) {// 取第i个序列中的第j个数据项的最大值
	     				highValue = seriesCollection.getHighValue(i, j);
	     			}
	     			if (minValue > seriesCollection.getLowValue(i, j)) {// 取第i个序列中的第j个数据项的最小值
	     				minValue = seriesCollection.getLowValue(i, j);
	     			}
	     		}
	     }
	     // 获取最高值和最低值
	     int seriesCount2 = timeSeriesCollection.getSeriesCount();// 一共有多少个序列，目前为一个
	     	for (int i = 0; i < seriesCount2; i++) {
	     		int itemCount = timeSeriesCollection.getItemCount(i);// 每一个序列有多少个数据项
	     		for (int j = 0; j < itemCount; j++) {
	     			if (high2Value < timeSeriesCollection.getYValue(i,j)) {// 取第i个序列中的第j个数据项的值
	     				high2Value = timeSeriesCollection.getYValue(i,j);
	     			}
	     			if (min2Value > timeSeriesCollection.getYValue(i, j)) {// 取第i个序列中的第j个数据项的值
	     				min2Value = timeSeriesCollection.getYValue(i, j);
	     			}
	     		}
	      }
	     	/**
	     	 * 或者当日前一天的日期 格式为 YYYY-MM-DD
	     	 */
	     	
	     	String leastmonth,leastday;
	     	Calendar calendar =Calendar.getInstance();
	     	if(calendar.get(Calendar.MONTH)+1<10){
	     		leastmonth="0"+(calendar.get(Calendar.MONTH)+1)+"";
	     	}
	     	else{
	     		leastmonth=(calendar.get(Calendar.MONTH)+1)+"";
	     	}
	    	if(calendar.get(Calendar.DATE)-1<10){
	     		leastday="0"+(calendar.get(Calendar.DATE)-1)+"";
	     	}
	    	else{
	    		leastday=(calendar.get(Calendar.DATE)-1)+"";
	    	}
	    	
	     	String leastTime=calendar.get(Calendar.YEAR)+"-"+leastmonth+"-"+leastday;
	     	System.out.println("-------leastTime----------"+leastTime);
	     /**
	      * 设置K线图的画图器
	      */
	     final CandlestickRenderer candlestickRender=new CandlestickRenderer();// 设置K线图的画图器，必须申明为final，后面要在匿名内部类里面用到
	     candlestickRender.setUseOutlinePaint(true); // 设置是否使用自定义的边框线，程序自带的边框线的颜色不符合中国股票市场的习惯
	     candlestickRender.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_AVERAGE);// 设置如何对K线图的宽度进行设定
	     candlestickRender.setAutoWidthGap(0.001);// 设置各个K线图之间的间隔
	     java.awt.Color awtColorRed = java.awt.Color.getColor("#FF69B4");
	     java.awt.Color awtColorGreen = java.awt.Color.GREEN;
	     candlestickRender.setUpPaint(awtColorRed);// 设置股票上涨的K线图颜色
	     candlestickRender.setDownPaint(awtColorGreen);// 设置股票下跌的K线图颜色
	     
	     // 设置x轴，也就是时间轴
	     DateAxis x1Axis=new DateAxis();
	     x1Axis.setAutoRange(true);// 设置不采用自动设置时间范围
	     x1Axis.setTickLabelPaint(java.awt.Color.WHITE);
	     try{
	    	 System.out.println("||||startdate||||"+startDate);
	    	 x1Axis.setRange(dateFormat.parse(startDate),dateFormat.parse(leastTime));// 设置时间范围，注意时间的最大值要比已有的时间最大值要多一天
	     }catch(Exception e){
	    	 e.printStackTrace();
	     }
	     
	     // 设置时间线显示的规则，用这个方法就摒除掉了周六和周日这些没有交易的日期
	     x1Axis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());
	     x1Axis.setAutoTickUnitSelection(false);// 设置不采用自动选择刻度值
	     x1Axis.setTickMarkPosition(DateTickMarkPosition.START);// 设置标记的位置
	     x1Axis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());// 设置标准的时间刻度单位
	 //   x1Axis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,1));// 设置时间刻度的间隔，一般以周为单位
	     if(type.equals(TimeType.DAY)){
	    	 x1Axis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,1));
	    	 System.out.println("..........day...........");
	     }
	     else if(type.equals(TimeType.WEEK)){
	    	 x1Axis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,7));
	    	 System.out.println("..........week...........");
	     }
	     else if(type.equals(TimeType.MONTH)){
	    	 x1Axis.setTickUnit(new DateTickUnit(DateTickUnit.MONTH,1));
	    	 System.out.println("..........month...........");
	     }
	     x1Axis.setDateFormatOverride(new SimpleDateFormat("YYYY-MM-dd"));// 设置显示时间的格式
	     
	     // 设定y轴，就是数字轴
	     NumberAxis y1Axis=new NumberAxis();
	     y1Axis.setAutoRange(false);
	     y1Axis.setRange(minValue*0.9, highValue*1.1);// 设定y轴值的范围，比最低值要低一些，比最大值要大一些，这样图形看起来会美观些
	     y1Axis.setTickUnit(new NumberTickUnit((highValue*1.1-minValue*0.9)/10));// 设置刻度显示的密度
	     y1Axis.setTickLabelPaint(java.awt.Color.WHITE);
	     XYPlot plot1=new XYPlot(seriesCollection,x1Axis,y1Axis,candlestickRender);// 设置画图区域对象
	 
	     XYBarRenderer xyBarRender=new XYBarRenderer(){
	    	 private static final long serialVersionUID = 1L;// 为了避免出现警告消息，特设定此值
                     @Override
	    	 public Paint getItemPaint(int i, int j){// 匿名内部类用来处理当日的成交量柱形图的颜色与K线图的颜色保持一致
	    		 if(seriesCollection.getCloseValue(i,j)>seriesCollection.getOpenValue(i,j)){// 收盘价高于开盘价，股票上涨，选用股票上涨的颜色                    
//                         java.awt.Color red = java.awt.Color.RED;
                             return java.awt.Color.RED;
	    		 }else{
	                  return awtColorGreen;
	    		 }
	     }};
	     
	     xyBarRender.setMargin(0.1);// 设置柱形图之间的间隔
	     NumberAxis y2Axis=new NumberAxis();// 设置Y轴，为数值,后面的设置，参考上面的y轴设置
	     y2Axis.setAutoRange(false);
         y2Axis.setTickLabelPaint(java.awt.Color.WHITE);
	     y2Axis.setRange(min2Value*0.9, high2Value*1.1);
	     y2Axis.setTickUnit(new NumberTickUnit((high2Value*1.1-min2Value*0.9)/4));
	     XYPlot plot2=new XYPlot(timeSeriesCollection,null,y2Axis,xyBarRender);// 建立第二个画图区域对象，主要此时的x轴设为了null值，因为要与第一个画图区域对象共享x轴

	     ImageIcon icon=new ImageIcon("/images/chart_background.jpg");
	     plot1.setOutlinePaint(java.awt.Color.LIGHT_GRAY);
	     plot1.setBackgroundImage(icon.getImage());
	     plot1.setBackgroundAlpha(0.3f);
	  //   plot1.setRangeGridlinesVisible(false);
	     plot2.setOutlinePaint(java.awt.Color.LIGHT_GRAY);
	     plot2.setBackgroundImage(icon.getImage());
	     plot2.setBackgroundAlpha(0.3f);
	        
	     CombinedDomainXYPlot combineddomainxyplot = new CombinedDomainXYPlot(x1Axis);// 建立一个恰当的联合图形区域对象，以x轴为共享轴
	     combineddomainxyplot.add(plot1, 2);// 添加图形区域对象，后面的数字是计算这个区域对象应该占据多大的区域2/3
	     combineddomainxyplot.add(plot2, 1);// 添加图形区域对象，后面的数字是计算这个区域对象应该占据多大的区域1/3
	     combineddomainxyplot.setGap(10);// 设置两个图形区域对象之间的间隔空间
	       
	     JFreeChart dayKChart = new JFreeChart(id, JFreeChart.DEFAULT_TITLE_FONT, combineddomainxyplot, false);
	     // 设置总的背景颜色
	     dayKChart.setBackgroundPaint(java.awt.Color.BLACK);
	  //     dayKChart.setBackgroundImageAlpha(1f);
	      return dayKChart;
	  
		//统一上影线，下影线
//	      Paint p = getItemPaint(series, item);
//	           Paint outlinePaint = null;
//	           if (this.useOutlinePaint) {
//	            if(yClose>yOpen){
//	             if (this.upPaint != null) {
//	              	outlinePaint=this.upPaint;
//	                   }
//	                   else {
//	                    outlinePaint=p;
//	                   }
//	            }else{
//	             if (this.downPaint != null) {
//	              outlinePaint=this.downPaint;
//	                   }
//	                   else {
//	                    outlinePaint=p;
//	                   }
//	            }
//	           }
	
	}

}
