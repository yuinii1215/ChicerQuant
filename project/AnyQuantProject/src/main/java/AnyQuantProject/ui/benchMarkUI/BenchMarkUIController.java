package AnyQuantProject.ui.benchMarkUI;

import java.awt.Color;
import java.awt.Paint;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
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
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import AnyQuantProject.bl.factoryBL.BenchMarkBLFactory;
import AnyQuantProject.bl.factoryBL.KLineBLFactory;
import AnyQuantProject.blService.benchMarkBLService.BenchMarkBLService;
import AnyQuantProject.blService.kLineBLService.BenchmarkKLineBLService;
import AnyQuantProject.dataStructure.BenchMark;
import AnyQuantProject.dataStructure.KLineData;
import AnyQuantProject.dataStructure.KLineDataDTO;
import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.util.constant.R;
import AnyQuantProject.util.method.DrawKLineChart;
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
	@FXML
	private Tab dayChatTab,monthChatTab,weekChatTab;
	
	private  ChartPanel panel ;
	
	private Calendar calendar;
	String  benchMarkid;
	//表格数据
	private BenchMarkBLService benchMarkBLService =BenchMarkBLFactory.getBenchMarBLService();  
	private  List<BenchMark> benchMarkList;
	
	//K线数据
	private  String startDate ;
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
       
        ArrayList<String> options = new  ArrayList<String>();
        for(int i=0;i<benchMarkList.size();i++){
        	options.add(benchMarkList.get(i).getName());
        }
            ObservableList items = FXCollections.observableArrayList(options);
            benchMarkID.setItems(items);
            benchMarkID.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue ov, String t, String t1) {
                	benchMarkid = t1;
                    System.out.println("the selected benchMarkID is: " + benchMarkid);
                    panel = new ChartPanel(drawDayKLine());
                    panel.setOpaque(false);
                    SwingNode swingNode = new SwingNode();
                    swingNode.setContent(panel);
                    dayChatTab.setContent(swingNode);     
                
                
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
        
      
        
//        ChartPanel panel = new ChartPanel(drawDayKLine());
//        panel.setOpaque(false);
//        SwingNode swingNode = new SwingNode();
//        swingNode.setContent(panel);
//        dayChatTab.setContent(swingNode);  
   }
    
    
    
    public JFreeChart drawDayKLine() {
    	 benchMarkKLineDate=benchmarkKLineBLService.dayKLineChart(benchMarkid);
    	 benchMarkKLineDataList=benchMarkKLineDate.geKLineDataDTOs();
    	 return  DrawKLineChart.DayKLineChart (benchMarkKLineDataList,benchMarkid);
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
