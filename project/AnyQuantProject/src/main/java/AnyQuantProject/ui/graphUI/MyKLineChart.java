package AnyQuantProject.ui.graphUI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sun.javafx.charts.Legend;
import com.sun.javafx.charts.Legend.LegendItem;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Rectangle;

/** 
*AnyQuantProject//AnyQuantProject.ui.graphUI//MyKLineChart.java
* @author  cxworks 
* @date 创建时间：2016年3月6日 下午11:28:37 
*/

public class MyKLineChart<X,Y> extends LineChart<X, Y> {

	
	public MyKLineChart(Axis<X> xAxis, Axis<Y> yAxis) {
		super(xAxis, yAxis);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void updateLegend(){
		super.updateLegend();
		Legend legend=(Legend) getLegend();
		if (legend.getItems().size()>1) {
			LegendItem first=legend.getItems().get(legend.getItems().size()-1);
			legend.getItems().clear();
			legend.getItems().add(first);
		}
		
		
	}
	
	private void parseRectangle(){
		Series<X,Y> src1 = getData().get(1);
		ObservableList<PathElement> seriesLine1 = ((Path)src1.getNode()).getElements();
		List<point> points1=new ArrayList<>(seriesLine1.size()-1);
		for(int j=1; j<seriesLine1.size();j++){
			 LineTo lineTo=(LineTo) seriesLine1.get(j);
			 point p=new point(lineTo.getX(), lineTo.getY());
			 points1.add(p);
		 }
		for (Iterator<Data<X, Y>> it = getDisplayedDataIterator(src1); it.hasNext(); ){
			Data<X, Y> item=it.next();
			item.setNode(null);
		}
		//
		Series<X,Y> src2 = getData().get(2);
		ObservableList<PathElement> seriesLine2 = ((Path)src2.getNode()).getElements();
		List<point> points2=new ArrayList<>(seriesLine2.size()-1);
		for(int j=1; j<seriesLine2.size();j++){
			 LineTo lineTo=(LineTo) seriesLine2.get(j);
			 point p=new point(lineTo.getX(), lineTo.getY());
			 points2.add(p);
		 }
		for (Iterator<Data<X, Y>> it = getDisplayedDataIterator(src2); it.hasNext(); ){
			Data<X, Y> item=it.next();
			item.setNode(null);
		}
		seriesLine1.clear();
		seriesLine2.clear();
		//
		Series<X,Y> series = getData().get(3);
		int t=0;
		for(Iterator<Data<X, Y>> it = getDisplayedDataIterator(series); it.hasNext(); ){
			Data<X, Y> item=it.next();
			Node symbol=item.getNode();
			double width=symbol.prefWidth(-1);
			point p1=points1.get(t);
			point p2=points2.get(t);
			if(p2.y<p1.y){
				double ch=p1.y;
				p1.y=p2.y;
				p2.y=ch;
			}
			double height= p2.y-p1.y;
			double y=p1.y;
			double x=p1.x-width/2;
			symbol.resizeRelocate(x, y, width, height);
			Rectangle rectangle=(Rectangle) symbol;
			rectangle.setHeight(height);
			t++;
		}
	}
	
	 @Override 
	 protected void layoutPlotChildren(){
		 super.layoutPlotChildren();
		 //
		 parseRectangle();
		 //
		Series<X, Y> series = getData().get(0);
		ObservableList<PathElement> seriesLine = ((Path) series.getNode()).getElements();
		List<point> points = new ArrayList<>(seriesLine.size());
		List<MoveTo> moveTos = new ArrayList<>();
		List<LineTo> lineTos = new ArrayList<>();

		for (int j = 1; j < seriesLine.size(); j++) {
			LineTo lineTo = (LineTo) seriesLine.get(j);
			point p = new point(lineTo.getX(), lineTo.getY());
			points.add(p);
		}

		seriesLine.clear();
		for (Iterator<point> iterator = points.iterator(); iterator.hasNext();) {
			point p1 = iterator.next();
			point p2 = iterator.next();
			//
			MoveTo moveTo = new MoveTo(p1.x, p1.y);
			LineTo lineTo1 = new LineTo(p1.x, p1.y);
			LineTo lineTo2 = new LineTo(p2.x, p2.y);
			seriesLine.add(moveTo);
			seriesLine.add(lineTo1);
			seriesLine.add(lineTo2);
		}
		
	 }
	 class point{
		 double x;
		 double y;
		 public point(double x,double y) {
			this.x=x;
			this.y=y;
		}
	 }

}
