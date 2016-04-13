package AnyQuantProject.ui.moduleUI;
/**
 * @author QiHan
 * 
 */
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import AnyQuantProject.bl.factoryBL.IndustryBLFactory;
import AnyQuantProject.blService.industryBLService.IndustryBLService;
import AnyQuantProject.dataStructure.BenchMark;
import AnyQuantProject.dataStructure.IndustryInfo;
import AnyQuantProject.dataStructure.IndustryPriceInfo;
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
import javafx.scene.chart.PieChart;
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
	    private TableView<Stock> table;
	    @FXML
	    private Button allModuleBtn,SingleModuleBtn;
	    @FXML
	    private Label guideLabel;
	    @FXML
	    private Text moduleChineseNameLabel,openLabel,highLabel,volumeLabel,yeaterLabel,lowLabel;
	    @FXML
	    private PieChart pieChart;
		private AnchorPane modulePanel;
		private ModuleUI_1Controller moduleUI_1Controller =null;
	    int selectedIndex;
	    String industryName; 
	    String singleStockName;
	    private IndustryBLService industryBLService = IndustryBLFactory.getIndustryBLService();
		private List<Stock> singleIndustryInfoList;
		private List<String> allIndustryName;
		private IndustryPriceInfo industryPriceInfo;
	    
	    
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
		  chart();
	  }
	  
	  public void InfoRect(){
		  allIndustryName = industryBLService.getAllIndustries();
		  guideLabel.setText("> "+industryName);
		  
		  industryPriceInfo = industryBLService.getIndustryPrice(industryName);
		  for(int i=0;i< allIndustryName.size();i++){
			  if(allIndustryName.get(i).equals(industryName)){
				  moduleChineseNameLabel.setText(industryName);
				  double open = industryPriceInfo.getOpen();
				  double max = industryPriceInfo.getMax();
				  double close = industryPriceInfo.getClose();
				  double min =industryPriceInfo.getMin();
				  openLabel.setText("今开："+ String .format("%.3f",open));
				  highLabel.setText("最高："+  String .format("%.3f",max));
				  volumeLabel.setText("成交量："+ industryPriceInfo.getVolume());
				  yeaterLabel.setText("昨收："+  String .format("%.3f",close));
				  lowLabel.setText("最低："+  String .format("%.3f",min));
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
	    peColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
	                cellData.getValue().getPe_ttm()));
	    pbColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
	                cellData.getValue().getPb()));

	}
	
	
	public void chart(){
		double[] value = new double[singleIndustryInfoList.size()];
		for(int i=0;i<singleIndustryInfoList.size();i++){
			value[i]=singleIndustryInfoList.get(i).getClose() -
					singleIndustryInfoList.get(i).getOpen();
		}
		int up=0,down=0,no=0;
		for (int i=0;i<singleIndustryInfoList.size();i++){
			if(value[i]>0){
				up++;
			}
			else if(value[i]<0){
				down++;
			}
			else{
				no++;
			}
		}
		

		pieChart.getData().add(1,new PieChart.Data("上涨", up));
		pieChart.getData().add(2,new PieChart.Data("下跌", down));
		pieChart.getData().add(3,new PieChart.Data("持平", no));
		pieChart.setId("行业个股涨跌分布");
		
		
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
