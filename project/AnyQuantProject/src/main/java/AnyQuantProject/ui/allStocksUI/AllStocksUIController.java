package AnyQuantProject.ui.allStocksUI;

import AnyQuantProject.bl.factoryBL.StockListBLFactory;
import AnyQuantProject.bl.stockListBL.StockListBLController;
import AnyQuantProject.blService.stockListBLService.StockListBLService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.starter.Main;
import AnyQuantProject.ui.controllerUI.MainPageController;
import AnyQuantProject.ui.favoriteUI.FavoriteUIController;
import AnyQuantProject.util.method.SimpleDoubleProperty;
import AnyQuantProject.util.method.SimpleIntegerProperty;
import AnyQuantProject.util.method.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TableRow;
import javafx.util.Callback;



//import AnyQuantProject.util.method.TableRowControl;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import static javafx.scene.input.KeyCode.T;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
/**
 * 
 * @author GraceHan
 *
 */
public class AllStocksUIController  implements Initializable{
	Label titleLabel;
	Scene allStockUIScene;
    
    List<Stock> allStocksList=new ArrayList<Stock>();
    String targetStockName;
    @FXML
    public TableColumn<Stock, String> chineseColumn;
    @FXML
    public TableColumn<Stock, String> nameColumn;
    @FXML
    public TableView<Stock>  allStocksTableView;
    @FXML
    public TableColumn<Stock, String> dateColumn;
    @FXML
    public TableColumn<Stock, Double> openColumn;
    @FXML
    public TableColumn<Stock, Double> closeColumn;
    @FXML
    public TableColumn<Stock, Double> highColumn;
    @FXML
    public TableColumn<Stock, Double> lowColumn;
    @FXML
    public TableColumn<Stock, Double> adj_priceColumn;
    @FXML
    public TableColumn<Stock, Integer> volumeColumn;
    @FXML
    public TableColumn<Stock, Long> marketValueColumn;
    @FXML
    public TableColumn<Stock, Long> flowColumn;
    @FXML
    public TableColumn<Stock, Double> peColumn;
    @FXML
    public TableColumn<Stock, Double> pbColumn;
    @FXML
    public TableView<Stock> table;
   
    int selectedIndex;
	
	private static AllStocksUIController instance;
    StockListBLService stockListImplement = StockListBLFactory.getStockListBLService();

    public static AllStocksUIController getInstance() {
    	 System.out.println("here is the instance of AllStocksUIController ");
        return instance==null?(instance=new AllStocksUIController()):instance;
    }
    
    /**
     * Initializes the controller class.
     */
    public AllStocksUIController(){
       
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
                init();
	}

   public void init(){
       
        allStocksList=stockListImplement.getAllStocks();
        
        table.setItems(FXCollections.observableArrayList(allStocksList));
        table.setRowFactory(new Callback<TableView<Stock>, TableRow<Stock>>() {
         @Override
         public TableRow<Stock> call(TableView<Stock> table) {
             // TODO Auto-generated method stub
            return new TableRowControl(table);
         }
     });
		/**
		 * initialize the tabel columns
		 */
        chineseColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getChinese()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getName()));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getDate()));
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
  /**
   * TO be tested
   * @author QiHan
   *
   * @param <T>
   */
   public class TableRowControl<T> extends TableRow<T> {
       
      public TableRowControl(TableView<T> tableView) {  
        super();
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {  
            @Override  
            public void handle(MouseEvent event) { 
                
                 if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {  
                   selectedIndex=TableRowControl.this.getIndex();
                   String stockName=nameColumn.getCellData(selectedIndex);
                   System.out.println("......Enter :"+stockName+" panel......");
            //     MainPageController.getInstance().setPanel(Main.singleStockInfoPanel, "打开单只股票下部信息界面...");
                   Main.enterSingleStockInfoScene(stockName);
                   
                   ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
                    ScheduledFuture future = service.schedule(new Callable() {
                        public String call() {
                            System.out.print("time is up");
                            Main.endSingle();                          
                            return "taskcancelled!";
                        }
                    }, 4, TimeUnit.SECONDS);
     
                    service.shutdown();
                 
                } 
                 
                 
            }  
        });  
    }  
}  
}
