package AnyQuantProject.ui.benchMarkUI;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

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
import AnyQuantProject.blService.benchMarkBLService.BenchMarkBLService;
import AnyQuantProject.dataStructure.BenchMark;
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
	
	private BenchMarkBLService benchMarkBLService =BenchMarkBLFactory.getBenchMarBLService();  
	private  List<BenchMark> benchMarkList;
	  
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
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        init();
    }
}
