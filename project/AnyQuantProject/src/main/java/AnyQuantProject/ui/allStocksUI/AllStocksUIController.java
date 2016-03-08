package AnyQuantProject.ui.allStocksUI;

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
import AnyQuantProject.ui.controllerUI.Main;
import AnyQuantProject.ui.controllerUI.MainPageController;
import AnyQuantProject.util.method.SimpleDoubleProperty;
import AnyQuantProject.util.method.SimpleIntegerProperty;
import AnyQuantProject.util.method.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TableRow;
import javafx.util.Callback;
import AnyQuantProject.util.method.TableRowControl;
/**
 * 
 * @author GraceHan
 *
 */
public class AllStocksUIController  implements Initializable{
	Label titleLabel;
	Scene allStockUIScene;
	static TableView<Stock> table;
	static List<Stock> allStocksList;
	static TableColumn<Stock,String> dateColumn;
	static TableColumn<Stock,Double> openColumn;
	static TableColumn<Stock,Double> closeColumn;
	static TableColumn<Stock,Double> highColumn;
	static TableColumn<Stock,Double> lowColumn;
	static TableColumn<Stock,Double> adj_priceColumn;
	static TableColumn<Stock,Integer> volumeColumn;
	static TableColumn<Stock, Long> marketValueColumn;
	static TableColumn<Stock, Long> flowColumn;
	static TableColumn<Stock,Double> peColumn;
	static TableColumn<Stock,Double> pbColumn;
	static AnchorPane allStocksPanel;
        static Parent root;
	
	private static AllStocksUIController instance;
        StockListBLService stockListImplement;

    	public static AllStocksUIController getInstance(Parent root) {
        AllStocksUIController.root=root;
        return instance==null?(instance=new AllStocksUIController(root)):instance;
    }
    
    /**
     * Initializes the controller class.
     */
    public AllStocksUIController(Parent root){
        this.root=root;
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
                init();
	}

   public void init(){
////        AllStocksUIController.root=root;
//        allStocksList=stockListImplement.getAllStocks();
//        
////        table = (TableView) root.lookup("#allStocksTableView");
//        table.setItems(FXCollections.observableArrayList(allStocksList));
//
//        table.setRowFactory(new Callback<TableView<Stock>, TableRow<Stock>>() {
//            @Override
//            public TableRow<Stock> call(TableView<Stock> table) {
//                // TODO Auto-generated method stub
//                return new TableRowControl(table);
//            }
//        });
////
////		/**
////		 * initialize the tabel columns
////		 */
//        dateColumn = (TableColumn<Stock, String>) table.getColumns().get(0);
//        openColumn = (TableColumn<Stock, Double>) table.getColumns().get(1);
//        closeColumn = (TableColumn<Stock, Double>) table.getColumns().get(2);
//        highColumn = (TableColumn<Stock, Double>) table.getColumns().get(3);
//        lowColumn = (TableColumn<Stock, Double>) table.getColumns().get(4);
//        adj_priceColumn = (TableColumn<Stock, Double>) table.getColumns().get(5);
//        volumeColumn = (TableColumn<Stock, Integer>) table.getColumns().get(6);
//        marketValueColumn = (TableColumn<Stock, Long>) table.getColumns().get(7);
//        flowColumn = (TableColumn<Stock, Long>) table.getColumns().get(8);
//        peColumn = (TableColumn<Stock, Double>) table.getColumns().get(9);
//        pbColumn = (TableColumn<Stock, Double>) table.getColumns().get(10);
//
//        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
//                cellData.getValue().getDate()));
//        openColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
//                cellData.getValue().getOpen()));
//        closeColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
//                cellData.getValue().getClose()));
//        highColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
//                cellData.getValue().getHigh()));
//        lowColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
//                cellData.getValue().getLow()));
//        adj_priceColumn
//                .setCellValueFactory(cellData -> new SimpleDoubleProperty(
//                        cellData.getValue().getAdj_price()));
//        volumeColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(
//                cellData.getValue().getVolume()));
//        marketValueColumn
//                .setCellValueFactory(cellData -> new SimpleLongProperty(
//                        cellData.getValue().getMarketvalue()));
//        flowColumn.setCellValueFactory(cellData -> new SimpleLongProperty(
//                cellData.getValue().getFlow()));
//        peColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
//                cellData.getValue().getPe_ttm()));
//        pbColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
//                cellData.getValue().getPb()));
//       
   }
}
