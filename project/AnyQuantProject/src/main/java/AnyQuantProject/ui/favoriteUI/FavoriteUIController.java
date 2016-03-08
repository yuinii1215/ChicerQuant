package AnyQuantProject.ui.favoriteUI;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import AnyQuantProject.bl.factoryBL.FavoriteBLFactory;
import AnyQuantProject.blService.favoriteBLService.FavoriteBLService;
import AnyQuantProject.dataStructure.Stock;
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
	private TableColumn<Stock, Integer> number;
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

	
	private FavoriteBLService favoriteBLService =FavoriteBLFactory.getFavoriteBLService();  
	private  List<Stock> myFavorList;
	  
	static Parent root ;
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
//        table.setItems(FXCollections.observableArrayList(myFavorList));
//
//        table.getItems().add(new Stock());
         /**

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
