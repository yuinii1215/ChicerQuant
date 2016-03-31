package AnyQuantProject.ui.moduleUI;

import java.net.URL;
import java.util.ResourceBundle;

import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.util.method.SimpleDoubleProperty;
import AnyQuantProject.util.method.SimpleIntegerProperty;
import AnyQuantProject.util.method.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class SingleModuleUIController implements Initializable{
	private static SingleModuleUIController  instance = null;
	  @FXML
	    public TableColumn<Stock, String> englishNameColumn;
	    @FXML
	    public TableColumn<Stock, String> chineseNameColumn;
	    @FXML
	    public TableColumn<Stock, Double> highColumn;
	    @FXML
	    public TableColumn<Stock, Double> lowColumn;
	    @FXML
	    public TableColumn<Stock, Double> openColumn;
	    @FXML
	    public TableColumn<Stock, Double> closeColumn;
	    @FXML
	    public TableColumn<Stock, Double> adj_priceColumn;
	    @FXML
	    public TableColumn<Stock, Integer> volumeColumn;
	    @FXML
	    public TableColumn<Stock, Double> peColumn;
	    @FXML
	    public TableColumn<Stock, Double> pbColumn;
	    @FXML
	    public TableColumn<Stock, Long> marketValueColumn;
	    @FXML
	    public TableColumn<Stock, Long> flowColumn;
	    @FXML
	    public TableView<Stock> table;
	
	public static SingleModuleUIController getInstance() {
		 System.out.println("here is the instance of SingleModuleUIController");
        FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ModuleUI_2Controller .class.getResource("singleModulePanel.fxml"));
		return instance;
  }
	public void init(){
	
		
		
		englishNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
	                cellData.getValue().getChinese()));
		chineseNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
	                cellData.getValue().getName()));
	        openColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
	                cellData.getValue().getOpen()));
	        closeColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
	                cellData.getValue().getClose()));
	        highColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
	                cellData.getValue().getHigh()));
	        lowColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
	                cellData.getValue().getLow()));
	        adj_priceColumn
	                .setCellValueFactory(cellData -> new SimpleDoubleProperty(
	                        cellData.getValue().getAdj_price()));
	        volumeColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(
	                cellData.getValue().getVolume()));
	        marketValueColumn
	                .setCellValueFactory(cellData -> new SimpleLongProperty(
	                        cellData.getValue().getMarketvalue()));
	        flowColumn.setCellValueFactory(cellData -> new SimpleLongProperty(
	                cellData.getValue().getFlow()));
	        peColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
	                cellData.getValue().getPe_ttm()));
	        pbColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
	                cellData.getValue().getPb()));
		
		
		
		
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		instance =this;
		init();
	}
}
