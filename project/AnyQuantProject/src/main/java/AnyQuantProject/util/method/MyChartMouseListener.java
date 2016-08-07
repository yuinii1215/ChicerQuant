/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnyQuantProject.util.method;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;

/**
 *
 * @author GraceHan
 */


public interface MyChartMouseListener extends ChartMouseListener {
	
	public void chartMousePressed(ChartMouseEvent event);
	public void chartMouseReleased(ChartMouseEvent event);
	
}