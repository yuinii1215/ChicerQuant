package AnyQuantProject.ui.favoriteUI;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import AnyQuantProject.bl.factoryBL.FavoriteBLFactory;
import AnyQuantProject.bl.factoryBL.StockListBLFactory;
import AnyQuantProject.blService.favoriteBLService.FavoriteBLService;
import AnyQuantProject.blService.stockListBLService.StockListBLService;
import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.ui.allStocksUI.AllStocksUIController.TableRowControl;
import AnyQuantProject.ui.controllerUI.Main;
import AnyQuantProject.ui.controllerUI.MainPageController;
import AnyQuantProject.ui.favoriteUI.search_method.SearchTextField;
import AnyQuantProject.ui.favoriteUI.search_method.SeachTextFieldBuilder;
import AnyQuantProject.util.method.SimpleDoubleProperty;
import AnyQuantProject.util.method.SimpleIntegerProperty;
import AnyQuantProject.util.method.SimpleLongProperty;

/**
 * 
 * @author QiHan
 *
 */
public class FavoriteUIController implements Initializable{
	
	@FXML
	private  TableView<Stock> table;
	@FXML
	private  TableColumn<Stock, String> id;
	@FXML
	private  TableColumn<Stock, String> name;
	@FXML
	private  TableColumn<Stock, Double> open;
	@FXML
	private  TableColumn<Stock, Double> high;
	@FXML
	private  TableColumn<Stock, Double> low;
	@FXML
	private  TableColumn<Stock, Double> close;
	@FXML
	private  TableColumn<Stock, Integer> volum;
	@FXML
	private  TableColumn<Stock, Double> adj_price;
	@FXML
	private  TableColumn<Stock, Long> marketvalue;
	@FXML
	private  TableColumn<Stock, Long> flow;
	@FXML
	private  TableColumn<Stock, Double> pe;
	@FXML
	private  TableColumn<Stock, Double> pb;
	@FXML
	private  TextField search;
	
	private FavoriteBLService favoriteBLService = FavoriteBLFactory.getFavoriteBLService();  
	private StockListBLService stockListBLService = StockListBLFactory.getStockListBLService();
	private String searchInput;
	private  List<Stock> myFavorList;
	private List<String> searchTipList;  
	int selectedIndex;
	static Parent root ;
	String searchName;
	private static FavoriteUIController instance = null;
	 
	/**
	 * 此处之后应该对接初始化数据的方法  
	 * @return
	 */
	public FavoriteUIController(){
		
	}
	
		public static FavoriteUIController getInstance() {
			 System.out.println("here is the instance of FavoriteUIController ");
	         FXMLLoader loader = new FXMLLoader();
			 loader.setLocation(FavoriteUIController.class.getResource("favouritePanel.fxml"));
			return instance;
	    }
	
	/**
	 * initialize the table
	 * 
	 */
    public  void init(){
    	
//    	myFavorList=favoriteBLService.getMyFavor();
//    	
//      table.setItems(FXCollections.observableArrayList(myFavorList));

        table.getItems().add(new Stock());
     
       
  
	/**
	 * initialize the tabel columns
	 */
           id.setCellValueFactory(cellData -> new SimpleStringProperty(
        		   cellData.getValue().getName()));
           name.setCellValueFactory(cellData -> new SimpleStringProperty(
        		   cellData.getValue().getName()));
           open.setCellValueFactory(cellData -> new SimpleDoubleProperty(
        		   cellData.getValue().getOpen()));
           high.setCellValueFactory(cellData -> new SimpleDoubleProperty(
        		   cellData.getValue().getHigh()));
           low.setCellValueFactory(cellData -> new SimpleDoubleProperty(
        		   cellData.getValue().getLow()));
           close.setCellValueFactory(cellData -> new SimpleDoubleProperty(
        		   cellData.getValue().getClose()));
           volum.setCellValueFactory(cellData -> new SimpleIntegerProperty(
        		   cellData.getValue().getVolume()));
           adj_price.setCellValueFactory(cellData -> new SimpleDoubleProperty(
				   cellData.getValue().getAdj_price()));
           marketvalue.setCellValueFactory(cellData -> new SimpleLongProperty(
				cellData.getValue().getMarketvalue()));
           flow.setCellValueFactory(cellData -> new SimpleLongProperty(
			    cellData.getValue().getFlow()));
           pe.setCellValueFactory(cellData -> new SimpleDoubleProperty(
				   cellData.getValue().getPe_ttm()));
           pb.setCellValueFactory(cellData -> new SimpleDoubleProperty(
				   cellData.getValue().getPb()));
           search();
      
   }

   /**
    * TO be tested
    */
    public void search(){
    	searchTipList = stockListBLService.searchPredict(searchInput);
   // 	FXCollections.observableArrayList(searchTipList);
    	SearchTextField auto=SeachTextFieldBuilder.build(search);
    	auto.setCacheDataList(searchTipList);
    	
    	search.setOnAction(new EventHandler<ActionEvent>(){     
    				@Override
    				public void handle(ActionEvent arg0){
    					searchName=search.getText();
    					System.out.println("...SearchName ..."+searchName+".......");
    				    Main.enterSingleStockInfoScene(searchName);
    				}
    	});
    	
    	
    	
    	
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
                   selectedIndex=TableRowControl.this.getIndex();
                   String stockName=name.getCellData(selectedIndex);

              //     MainPageController.getInstance().setPanel(Main.singleStockInfoPanel, "打开单只股票下部信息界面...");
                   Main.enterSingleStockInfoScene(stockName);
                   
                   
              }  
          });  

        }
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


	
