package AnyQuantProject.ui.benchMarkUI;

import java.awt.Color;
import java.awt.Paint;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.jfree.chart.ChartFrame;
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

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import AnyQuantProject.bl.factoryBL.BenchMarkBLFactory;
import AnyQuantProject.bl.factoryBL.KLineBLFactory;
import AnyQuantProject.blService.benchMarkBLService.BenchMarkBLService;
import AnyQuantProject.blService.kLineBLService.BenchmarkKLineBLService;
import AnyQuantProject.dataStructure.BenchMark;
import AnyQuantProject.dataStructure.KLineData;
import AnyQuantProject.dataStructure.KLineDataDTO;
import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.util.method.SimpleDoubleProperty;
import AnyQuantProject.util.method.SimpleIntegerProperty;
import AnyQuantProject.util.method.SimpleLongProperty;

/**
 * 
 * @author QiHan
 *
 */

public class BenchMarkUIController implements Initializable{

	@FXML
	private  TableView<BenchMark> table;
	@FXML
	private TableColumn<BenchMark, String> timeTable;
	@FXML
	private TableColumn<BenchMark, String> allBenchMarkID;
	@FXML
	private  TableColumn<BenchMark, Double> openTable;
	@FXML
	private  TableColumn<BenchMark, Double> highTable;
	@FXML
	private  TableColumn<BenchMark, Double> lowTable;
	@FXML
	private  TableColumn<BenchMark, Double> closeTable;
	@FXML
	private  TableColumn<BenchMark, Integer> volumTable;
	@FXML
	private  TableColumn<BenchMark, Long> turnoverTable;//成交额
	@FXML
	private  TableColumn<BenchMark, Double> adj_priceTable;
	@FXML
	private  TableColumn<BenchMark, Long> marketvalueTable;
	@FXML
	private  TableColumn<BenchMark, Long> flow;
	@FXML
	public  ComboBox benchMarkID;
	@FXML
	private Label currentTime;
	
	private Calendar calendar;
	String  benchMarkid;
	//表格数据
	private BenchMarkBLService benchMarkBLService =BenchMarkBLFactory.getBenchMarBLService();  
	private  List<BenchMark> benchMarkList;
	
	//K线数据
	private  BenchmarkKLineBLService benchmarkKLineBLService=KLineBLFactory.getBenchmarkBLService();
	private  KLineData benchMarkKLineDate;
	private List<KLineDataDTO>  benchMarkKLineDataList; 
	private static BenchMarkUIController instance = null;
	 
	/**
	 * 此处之后应该对接初始化数据的方法  
	 * @return
	 */
	public BenchMarkUIController(){
		
	}
	
	public static BenchMarkUIController getInstance() {
		System.out.println("here is the instance of BenchMarkUIController ");
	    FXMLLoader loader = new FXMLLoader();
		loader.setLocation(BenchMarkUIController.class.getResource("benchMarkPanel.fxml"));
		return instance;
	 }
	
	/**
	 * initialize the table
	 * 
	 */
    public  void init(){
    	
    	calendar=Calendar.getInstance();
    	
    	currentTime.setText(String.valueOf(calendar.getTime()));
    	System.out.println(currentTime);
    	
    	benchMarkList=benchMarkBLService.getAllBenchMark();
    	
        table.setItems(FXCollections.observableArrayList(benchMarkList));

        table.getItems().add(new BenchMark());
       

        benchMarkID.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
            	 benchMarkid = t1;
                System.out.println("the selected is: " + t1);
            }
        });
	
	/**
	 * initialize the tabel columns
	 */
        timeTable .setCellValueFactory(cellData -> new SimpleStringProperty(
        		   cellData.getValue().getDate()));
        allBenchMarkID.setCellValueFactory(cellData -> new SimpleStringProperty(
     		   cellData.getValue().getName()));
        openTable.setCellValueFactory(cellData -> new SimpleDoubleProperty(
        		   cellData.getValue().getOpen()));
        highTable.setCellValueFactory(cellData -> new SimpleDoubleProperty(
        		   cellData.getValue().getHigh()));
        lowTable.setCellValueFactory(cellData -> new SimpleDoubleProperty(
        		   cellData.getValue().getLow()));
        closeTable.setCellValueFactory(cellData -> new SimpleDoubleProperty(
        		   cellData.getValue().getClose()));
        volumTable.setCellValueFactory(cellData -> new SimpleIntegerProperty(
        		   cellData.getValue().getVolume()));
        adj_priceTable.setCellValueFactory(cellData -> new SimpleDoubleProperty(
				   cellData.getValue().getAdj_price()));
        turnoverTable.setCellValueFactory(cellData -> new SimpleLongProperty(
				   cellData.getValue().getTransaction()));
        marketvalueTable.setCellValueFactory(cellData -> new SimpleLongProperty(
				cellData.getValue().getMarketvalue()));
        flow.setCellValueFactory(cellData -> new SimpleLongProperty(
			    cellData.getValue().getFlow()));
            
   }
    
    
    
    public JFreeChart drawDayKLine() {
    	 benchMarkKLineDate=benchmarkKLineBLService.dayKLineChart(benchMarkid);
    	 benchMarkKLineDataList=benchMarkKLineDate.geKLineDataDTOs();
    	 benchMarkKLineDataList.get(0);
    	 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
	     double highValue = Double.MIN_VALUE;// 设置K线数据当中的最大值
	     double minValue = Double.MAX_VALUE;// 设置K线数据当中的最小值
	     double high2Value = Double.MIN_VALUE;// 设置成交量的最大值
	     double min2Value = Double.MAX_VALUE;// 设置成交量的最低值
	     
	     OHLCSeries series = new OHLCSeries("");// 高开低收数据序列，股票K线图的四个数据，依次是开，高，低，收
	   
	     for (int i = 0; i < benchMarkList.size(); i++) {
	            System.out.println("");
	            int date =Integer.parseInt(benchMarkKLineDataList.get(i).getDay());
	            int month =Integer.parseInt(benchMarkKLineDataList.get(i).getMonth());
	            int year =Integer.parseInt(benchMarkKLineDataList.get(i).getYear());
	            series.add(new Day(date, month, year), benchMarkKLineDataList.get(i).getOpen(),benchMarkKLineDataList.get(i).getHigh(), benchMarkKLineDataList.get(i).getLow(), benchMarkKLineDataList.get(i).getClose());
	        }
	     
	     final OHLCSeriesCollection seriesCollection = new OHLCSeriesCollection();// 保留K线数据的数据集，必须申明为final，后面要在匿名内部类里面用到
	     seriesCollection.addSeries(series);
	     TimeSeries series2=new TimeSeries("");// 对应时间成交量数据
	     for (int i = 0; i < benchMarkKLineDataList.size(); i++) {
	    	  int date =Integer.parseInt(benchMarkKLineDataList.get(i).getDay());
	            int month =Integer.parseInt(benchMarkKLineDataList.get(i).getMonth());
	            int year =Integer.parseInt(benchMarkKLineDataList.get(i).getYear());
	            series2.add(new Day(date, month, year), benchMarkKLineDataList.get(i).getFlow()/ 100);
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
	     final CandlestickRenderer candlestickRender=new CandlestickRenderer();// 设置K线图的画图器，必须申明为final，后面要在匿名内部类里面用到
	     candlestickRender.setUseOutlinePaint(true); // 设置是否使用自定义的边框线，程序自带的边框线的颜色不符合中国股票市场的习惯
	     candlestickRender.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_AVERAGE);// 设置如何对K线图的宽度进行设定
	     candlestickRender.setAutoWidthGap(0.001);// 设置各个K线图之间的间隔
	     candlestickRender.setUpPaint(Color.RED);// 设置股票上涨的K线图颜色
	     candlestickRender.setDownPaint(Color.GREEN);// 设置股票下跌的K线图颜色
	     DateAxis x1Axis=new DateAxis();// 设置x轴，也就是时间轴
	     x1Axis.setAutoRange(false);// 设置不采用自动设置时间范围
	     try{
	     x1Axis.setRange(dateFormat.parse("2007-08-20"),dateFormat.parse("2007-09-29"));// 设置时间范围，注意时间的最大值要比已有的时间最大值要多一天
	     }catch(Exception e){
	      e.printStackTrace();
	     }
	    x1Axis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());// 设置时间线显示的规则，用这个方法就摒除掉了周六和周日这些没有交易的日期(很多人都不知道有此方法)，使图形看上去连续
	     x1Axis.setAutoTickUnitSelection(false);// 设置不采用自动选择刻度值
	     x1Axis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);// 设置标记的位置
	     x1Axis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());// 设置标准的时间刻度单位
	     x1Axis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,7));// 设置时间刻度的间隔，一般以周为单位
	     x1Axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));// 设置显示时间的格式
	     NumberAxis y1Axis=new NumberAxis();// 设定y轴，就是数字轴
	     y1Axis.setAutoRange(false);// 不不使用自动设定范围
	     y1Axis.setRange(minValue*0.9, highValue*1.1);// 设定y轴值的范围，比最低值要低一些，比最大值要大一些，这样图形看起来会美观些
	     y1Axis.setTickUnit(new NumberTickUnit((highValue*1.1-minValue*0.9)/10));// 设置刻度显示的密度
	     XYPlot plot1=new XYPlot(seriesCollection,x1Axis,y1Axis,candlestickRender);// 设置画图区域对象
	 
	     XYBarRenderer xyBarRender=new XYBarRenderer(){
	     private static final long serialVersionUID = 1L;// 为了避免出现警告消息，特设定此值
	     public Paint getItemPaint(int i, int j){// 匿名内部类用来处理当日的成交量柱形图的颜色与K线图的颜色保持一致
	       if(seriesCollection.getCloseValue(i,j)>seriesCollection.getOpenValue(i,j)){// 收盘价高于开盘价，股票上涨，选用股票上涨的颜色
	        return candlestickRender.getUpPaint();
	       }else{
	        return candlestickRender.getDownPaint();
	       }
	     }};
	     xyBarRender.setMargin(0.1);// 设置柱形图之间的间隔
	     NumberAxis y2Axis=new NumberAxis();// 设置Y轴，为数值,后面的设置，参考上面的y轴设置
	     y2Axis.setAutoRange(false);
	     y2Axis.setRange(min2Value*0.9, high2Value*1.1);
	     y2Axis.setTickUnit(new NumberTickUnit((high2Value*1.1-min2Value*0.9)/4));
	     XYPlot plot2=new XYPlot(timeSeriesCollection,null,y2Axis,xyBarRender);// 建立第二个画图区域对象，主要此时的x轴设为了null值，因为要与第一个画图区域对象共享x轴
	     CombinedDomainXYPlot combineddomainxyplot = new CombinedDomainXYPlot(x1Axis);// 建立一个恰当的联合图形区域对象，以x轴为共享轴
	     combineddomainxyplot.add(plot1, 2);// 添加图形区域对象，后面的数字是计算这个区域对象应该占据多大的区域2/3
	        combineddomainxyplot.add(plot2, 1);// 添加图形区域对象，后面的数字是计算这个区域对象应该占据多大的区域1/3
	        combineddomainxyplot.setGap(10);// 设置两个图形区域对象之间的间隔空间
	        JFreeChart chart = new JFreeChart("中国联通", JFreeChart.DEFAULT_TITLE_FONT, combineddomainxyplot, false);
	        ChartFrame frame = new ChartFrame("中国联通股票", chart);
	     frame.pack();
	     frame.setVisible(true);
	     JFreeChart dayKChart = new JFreeChart(singleStock.getChinese(), JFreeChart.DEFAULT_TITLE_FONT, combineddomainxyplot, false);
	        // 设置总的背景颜色
	        dayKChart.setBackgroundPaint(java.awt.Color.BLACK);
//	        dayKChart.setBackgroundImage(icon.getImage());
//	        dayKChart.setBackgroundImageAlpha(0.3f);
	        //统一上影线，下影线
	      //Paint p = getItemPaint(series, item);
//	           Paint outlinePaint = null;
//	           if (this.useOutlinePaint) {
//	            if(yClose>yOpen){
//	             if (this.upPaint != null) {
//	              outlinePaint=this.upPaint;
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
	
	        return dayKChart;
	}

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        init();
    }
}
