package AnyQuantProject.ui.moduleUI;
/**
 * @author QiHan
 */
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import AnyQuantProject.bl.factoryBL.IndustryBLFactory;
import AnyQuantProject.blService.industryBLService.IndustryBLService;
import AnyQuantProject.dataStructure.BenchMark;
import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.starter.Main;
import AnyQuantProject.ui.allStocksUI.AllStocksUIController.TableRowControl;
import AnyQuantProject.util.method.SimpleDoubleProperty;
import AnyQuantProject.util.method.SimpleIntegerProperty;
import AnyQuantProject.util.method.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class SingleModuleUIController implements Initializable{
	private static SingleModuleUIController  instance = null;
		@FXML
	  	private TableColumn<Stock, String> idColumn;
	    @FXML
	    private TableColumn<Stock, String> chineseNameColumn;
	    @FXML
	    private TableColumn<Stock, Double> highColumn;
	    @FXML
	    private TableColumn<Stock, Double> lowColumn;
	    @FXML
	    private TableColumn<Stock, Double> openColumn;
	    @FXML
	    private TableColumn<Stock, Double> closeColumn;
	    @FXML
	    private TableColumn<Stock, Double> adj_priceColumn;
	    @FXML
	    private TableColumn<Stock, Integer> volumeColumn;
	    @FXML
	    private TableColumn<Stock, Double> peColumn;
	    @FXML
	    private TableColumn<Stock, Double> pbColumn;
	    @FXML
	    private TableColumn<Stock, Long> marketValueColumn;
	    @FXML
	    private TableColumn<Stock, Long> flowColumn;
	    @FXML
	    private TableView<Stock> table;
	    @FXML
	    private Button allModuleBtn,SingleModuleBtn;
	    @FXML
	    private Label guideLabel;
	    @FXML
	    private Text moduleChineseNameLabel,moduleCodeNameLabel,openLabel,highLabel,volumeLabel,yeaterLabel,lowLabel;
		private AnchorPane modulePanel;
		private ModuleUI_1Controller moduleUI_1Controller =null;
	    int selectedIndex;
	    String industryName; 
	    String singleStockName;
	    private IndustryBLService industryBLService = IndustryBLFactory.getIndustryBLService();
		private List<Stock> singleIndustryInfoList;
		private List<String> allIndustryName;
	    
	    
	 public static SingleModuleUIController getInstance() {
		 System.out.println("here is the instance of SingleModuleUIController");
        FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ModuleUI_2Controller .class.getResource("singleModulePanel.fxml"));
		return instance;
	
	}
	  public void laterInit(String name) {
	        this.industryName = name;
	        this.init();
	  }
	  public void init(){
		  initTable();
		  InfoRect();
	  }
	  
	  public void InfoRect(){
		  allIndustryName = industryBLService.getAllIndustries();
		  guideLabel.setText("> "+industryName);
		  for(int i=0;i< allIndustryName.size();i++){
			  if(allIndustryName.get(i).equals(industryName)){
				  moduleChineseNameLabel.setText(industryName);
				  moduleCodeNameLabel.setText(null);
				  openLabel.setText("今开："+null);
				  highLabel.setText("最高："+null);
				  volumeLabel.setText("成交量："+null);
				  yeaterLabel.setText("昨收："+null);
				  lowLabel.setText("最低："+null);
			  }
		  }
		 
	  }

	  
	public void initTable(){
		System.out.println("....InitsingleModuleInfo...."+industryName);;
		singleIndustryInfoList =industryBLService.getStocksByIndustry(industryName);
		table.setItems(FXCollections.observableArrayList(singleIndustryInfoList));
	//	table.getItems().add(new Stock());
		table.setRowFactory(new Callback<TableView<Stock>, TableRow<Stock>>() {
	            @Override
	            public TableRow<Stock> call(TableView<Stock> table) {
	                // TODO Auto-generated method stub
	                return new TableRowControl(table);
	            }
	       });

		idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
	                cellData.getValue().getName()));
		chineseNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
	                cellData.getValue().getChinese()));
	    openColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
	                cellData.getValue().getOpen()));
	    closeColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
	                cellData.getValue().getClose()));
	    highColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
	                cellData.getValue().getHigh()));
	    lowColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
	                cellData.getValue().getLow()));
	    adj_priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
	                        cellData.getValue().getAdj_price()));
	    volumeColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(
	                cellData.getValue().getVolume()));
	    marketValueColumn.setCellValueFactory(cellData -> new SimpleLongProperty(
	                        cellData.getValue().getMarketvalue()));
	    flowColumn.setCellValueFactory(cellData -> new SimpleLongProperty(
	                cellData.getValue().getFlow()));
	    peColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
	                cellData.getValue().getPe_ttm()));
	    pbColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
	                cellData.getValue().getPb()));

	}
	
	
	@FXML
	private void toReturnPane() {
		Main.returnToModuleScene();
	}
	
	
	 public class TableRowControl<T> extends TableRow<T> {

	        public TableRowControl(TableView<T> tableView) {
	            super();

	            this.setOnMouseClicked(new EventHandler<MouseEvent>() {
	                @Override
	                public void handle(MouseEvent event) {
	                	 if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
	                         selectedIndex = TableRowControl.this.getIndex();
	                         singleStockName = idColumn.getCellData(selectedIndex);
	                         System.out.println("......Enter :" + singleStockName + " panel......");
	                         Main.enterSingleStockInfoScene(singleStockName);
	                	 }
	                }});
	             }
	   			}
	 
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		instance =this;
	}
}
