package AnyQuantProject.ui.benchMarkUI;

import java.awt.event.ItemEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import AnyQuantProject.bl.factoryBL.BenchMarkBLFactory;
import AnyQuantProject.bl.factoryBL.KLineBLFactory;
import AnyQuantProject.blService.benchMarkBLService.BenchMarkBLService;
import AnyQuantProject.blService.kLineBLService.BenchmarkKLineBLService;
import AnyQuantProject.dataStructure.BenchMark;
import AnyQuantProject.dataStructure.KLineData;
import AnyQuantProject.dataStructure.KLineDataDTO;
import AnyQuantProject.util.constant.TimeType;
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
	private Label today,yeaterday,max,min,volume,turnover;
	@FXML
	public  ComboBox benchMarkID;
	@FXML
	private Label currentTime;
	@FXML
	private Tab dayChatTab,monthChatTab,weekChatTab;
	
	private  ChartPanel daypanel,weekpanel,monthpanel;
	private SwingNode dayswingNode,weekswingNode,monthswingNode;
	private ScrollPane dayScroller,weekScroller,monthScroller;
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
            benchMarkID.setPromptText((String) items.get(0));
            benchMarkID.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue ov, String t, String t1) {
                	benchMarkid = t1;
               // 	simpleInfo(benchMarkid);
                    System.out.println("the selected benchMarkID is: " + benchMarkid);
                       
                    daypanel = new ChartPanel(drawDayKLine());
                    dayswingNode = new SwingNode();
                    dayswingNode .setContent(daypanel);    
           //         dayScroller=new ScrollPane();
            //        dayScroller.setFitToHeight(true);
             //       dayScroller.setContent(dayswingNode);
              //      dayChatTab.setContent(dayScroller);    
                    dayChatTab.setContent(dayswingNode);    
                    
                    weekpanel = new ChartPanel(drawWeekKLine());
                    weekpanel.setBounds(0, 0, 2000, 800);
                    weekswingNode = new SwingNode();
                    weekswingNode .setContent(weekpanel);    
                    weekScroller=new ScrollPane();
                    weekScroller.setFitToHeight(true);
                    weekScroller.setFitToWidth(true);;
                    weekScroller.setContent(weekswingNode);
                    weekChatTab.setContent(weekScroller);    
                   
                    monthpanel = new ChartPanel(drawMonthKLine());
                    monthswingNode = new SwingNode();
                    monthswingNode .setContent(monthpanel);    
                    monthScroller=new ScrollPane();
                    monthScroller.setFitToHeight(true);
                    monthScroller.setContent(monthswingNode);
                    monthChatTab.setContent(monthScroller);    
                   
                    
                   
                    
                
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
    
    private void simpleInfo(String id){
    	  for(int i=0;i<benchMarkList.size();i++){
          	if(benchMarkList.get(i).getName()==id){
          		today.setText(benchMarkList.get(i).getOpen()+"");
          		yeaterday.setText(benchMarkList.get(i).getClose()+"");
          		max.setText(benchMarkList.get(i).getHigh()+"");
          		min.setText(benchMarkList.get(i).getLow()+"");
          		volume.setText(benchMarkList.get(i).getVolume()+"");
          		turnover.setText(benchMarkList.get(i).getTransaction()+"");
          		break;
          	}
          }
    }
    

    private JFreeChart drawDayKLine() {
    	 benchMarkKLineDate=benchmarkKLineBLService.dayKLineChart(benchMarkid);
    	 benchMarkKLineDataList=benchMarkKLineDate.geKLineDataDTOs();
    	 return  DrawKLineChart.DayKLineChart (benchMarkKLineDataList,benchMarkid,TimeType.DAY);
	}
    private JFreeChart drawWeekKLine() {
   	 benchMarkKLineDate=benchmarkKLineBLService.weekKLineChart(benchMarkid);
   	 benchMarkKLineDataList=benchMarkKLineDate.geKLineDataDTOs();
   	 return  DrawKLineChart.DayKLineChart (benchMarkKLineDataList,benchMarkid,TimeType.WEEK);
	}
    private JFreeChart drawMonthKLine() {
   	 benchMarkKLineDate=benchmarkKLineBLService.monthKLineChart(benchMarkid);
   	 benchMarkKLineDataList=benchMarkKLineDate.geKLineDataDTOs();
   	 return  DrawKLineChart.DayKLineChart (benchMarkKLineDataList,benchMarkid,TimeType.MONTH);
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
