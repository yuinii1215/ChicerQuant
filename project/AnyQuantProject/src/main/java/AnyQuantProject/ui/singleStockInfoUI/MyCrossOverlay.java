package AnyQuantProject.ui.singleStockInfoUI;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.List;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.text.TextUtilities;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;

/** 
* AnyQuantProject//AnyQuantProject.ui.singleStockInfoUI//MyCro.java
* @author  cxworks 
* @date 创建时间：2016年3月20日 下午10:54:34 
*/

public class MyCrossOverlay extends CrosshairOverlay {
	 /** Storage for the crosshairs along the x-axis. */
    private List xCrosshairs;

    /** Storage for the crosshairs along the y-axis. */
    private List yCrosshairs;
    
    public MyCrossOverlay() {
		super();
		this.xCrosshairs = new java.util.ArrayList();
        this.yCrosshairs = new java.util.ArrayList();
	}
	@Override
	 public void paintOverlay(Graphics2D g2, ChartPanel chartPanel) {
		if (g2==null) {
			System.out.println("g2 is null");
		}
		if (chartPanel==null) {
			System.out.println("chart is null");
		}
		Paint paint=g2.getPaint();
		Shape savedClip = g2.getClip();
        Rectangle2D dataArea = chartPanel.getScreenDataArea();
        g2.clip(dataArea);
        JFreeChart chart = chartPanel.getChart();
        XYPlot plot = (XYPlot) chart.getPlot();
        ValueAxis xAxis = plot.getDomainAxis();
        RectangleEdge xAxisEdge = plot.getDomainAxisEdge();
        Iterator iterator = this.xCrosshairs.iterator();
        while (iterator.hasNext()) {
            Crosshair ch = (Crosshair) iterator.next();
            if (ch.isVisible()) {
                double x = ch.getValue();
                double xx = xAxis.valueToJava2D(x, dataArea, xAxisEdge);
                if (plot.getOrientation() == PlotOrientation.VERTICAL) {
                    drawVerticalCrosshair(g2, dataArea, xx, ch);
                }
                else {
                    drawHorizontalCrosshair(g2, dataArea, xx, ch);
                }
            }
        }
        List<Plot> plots= ((CombinedDomainXYPlot)plot).getSubplots();
        ValueAxis yAxis=((XYPlot)plots.get(0)).getRangeAxis();
        RectangleEdge yAxisEdge = ((XYPlot)plots.get(0)).getRangeAxisEdge();
        iterator = this.yCrosshairs.iterator();
        while (iterator.hasNext()) {
            Crosshair ch = (Crosshair) iterator.next();
            if (ch.isVisible()) {
                double y = ch.getValue();
                double yy = yAxis.valueToJava2D(y, dataArea, yAxisEdge);
                if (plot.getOrientation() == PlotOrientation.VERTICAL) {
                    drawHorizontalCrosshair(g2, dataArea, yy, ch);
                }
                else {
                    drawVerticalCrosshair(g2, dataArea, yy, ch);
                }
            }
        }
        g2.setClip(savedClip);
       
	}
	@Override
	public void addDomainCrosshair(Crosshair crosshair) {
		super.addDomainCrosshair(crosshair);
		this.xCrosshairs.add(crosshair);
	}
	@Override
	 public void addRangeCrosshair(Crosshair crosshair) {
		super.addRangeCrosshair(crosshair);
		this.yCrosshairs.add(crosshair);
	}
	
	
}
