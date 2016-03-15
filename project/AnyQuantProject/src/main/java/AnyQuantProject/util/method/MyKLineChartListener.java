/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnyQuantProject.util.method;
import java.awt.Cursor;
import java.awt.Shape;

import javax.swing.JPanel;

import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.data.xy.XYIntervalDataItem;
import org.jfree.data.xy.XYIntervalSeries;
import org.jfree.data.xy.XYIntervalSeriesCollection;

/**
 *
 * @author GraceHan
 */
public class MyKLineChartListener  implements MyChartMouseListener {

	@Override
	public void chartMouseClicked(ChartMouseEvent event) {
		// TODO Auto-generated method stub
/*
		JFreeChart chart = event.getChart();

		if (chart == null)
			return;
		XYItemEntity ce = (XYItemEntity) event.getEntity();
		if (ce == null)
			return;
		IntervalXYDataset my = (IntervalXYDataset) ce.getDataset();

		int sindex = ce.getSeriesIndex();
		int iindex = ce.getItem();

		System.out.println("x = " + my.getXValue(sindex, iindex));
		System.out.println("y = " + my.getYValue(sindex, iindex));
*/
	}

	@Override
	public void chartMouseMoved(ChartMouseEvent event) {
		// TODO Auto-generated method stub

	}

	XYItemEntity ce;
	XYIntervalSeriesCollection my;

	@Override
	public void chartMousePressed(ChartMouseEvent event) {
		// TODO Auto-generated method stub
		System.out.println("mouse chartMousePressed");
		JPanel src = (JPanel) event.getTrigger().getSource();

		src.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		JFreeChart chart = event.getChart();

		if (chart == null)
			return;
		ce = (XYItemEntity) event.getEntity();
		if (ce == null)
			return;
		 my = (XYIntervalSeriesCollection) ce.getDataset();
		
		int sindex = ce.getSeriesIndex();
		int iindex = ce.getItem();
		
		Shape shape = ce.getArea();
		
	
		
		System.out.println("sindex = " + sindex);
		System.out.println("iindex = " + iindex);
		System.out.println("x = " + my.getXValue(sindex, iindex));
		System.out.println("y = " + my.getYValue(sindex, iindex));

		
		
		
		XYIntervalSeries series = (XYIntervalSeries) my.getSeries(ce.getSeriesIndex());
		
		System.out.println("y1 = " + shape.getBounds2D().getCenterY());
		

		
/*		series.remove(my.getXValue(sindex, iindex));
	
		Day day1 = new Day(13, 6, 2007);
	        Day day2 = new Day(14, 6, 2007);

		XYBarChartDemo7.addItem(series,day1,day2,sindex);
*/
	
	}

	@Override
	public void chartMouseReleased(ChartMouseEvent event) {
//		// TODO Auto-generated method stub
//		System.out.println("mouse chartMouseReleased");
//		JPanel src = (MyChartPanel) event.getTrigger().getSource();
//		src.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//		
//		double cursorY = event.getTrigger().getY();
//		
//		System.out.println("y = "+ cursorY);
//		
//		int seriesIndex = my.getSeriesCount();
//
//		
//		double maxY = src.getScreenDataArea().getMaxY();
//		double minY = src.getScreenDataArea().getMinY();
//		
//		double heightY = (maxY-minY)/seriesIndex;
//		
//		for(int i=0;i<seriesIndex;i++)
//		{
//			if(cursorY >= minY && cursorY<=(i+1)*heightY)
//			{
//				XYIntervalSeries srcSeries =((XYIntervalSeriesCollection) (this.ce.getDataset())).getSeries(seriesIndex-i-1);
//				
//				XYIntervalSeriesCollection destSeriesCollection = (XYIntervalSeriesCollection) (this.ce.getDataset());
//				XYIntervalSeries destSeries =destSeriesCollection.getSeries(this.ce.getSeriesIndex());
//				XYIntervalDataItem item = (XYIntervalDataItem) destSeries.getDataItem(this.ce.getItem());			
//				
//				srcSeries.add(item.getX(), item.getXLowValue(), item.getXHighValue(), seriesIndex-i-1, seriesIndex-i-1-0.1, seriesIndex-i-1+0.3);
//				destSeries.remove(destSeriesCollection.getXValue(this.ce.getSeriesIndex(), this.ce.getItem()));
//		        break;
//
//			}
//		}
		

	}
}
 
