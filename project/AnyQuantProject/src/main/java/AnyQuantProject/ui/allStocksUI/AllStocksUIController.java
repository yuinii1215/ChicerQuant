package AnyQuantProject.ui.allStocksUI;

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
import javafx.stage.Stage;
import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.ui.controllerUI.MainPageController;
/**
 * 
 * @author GraceHan
 *
 */
public class AllStocksUIController  implements Initializable{
	Label titleLabel;
	Scene allStockUIScene;
	TableView<Stock> table;
	List<Stock> allStocksList;
	TableColumn<Stock,String> dateColumn;
	TableColumn<Stock,Double> openColumn;
	TableColumn<Stock,Double> closeColumn;
	TableColumn<Stock,Double> highColumn;
	TableColumn<Stock,Double> lowColumn;
	TableColumn<Stock,Double> adj_priceColumn;
	TableColumn<Stock,Integer> volumeColumn;
	TableColumn<Stock, Long> marketValueColumn;
	TableColumn<Stock, Long> flowColumn;
	TableColumn<Stock,Double> peColumn;
	TableColumn<Stock,Double> pbColumn;
	
	private static AllStocksUIController instance=null;

    public static AllStocksUIController getInstance() {
    	System.out.println("!!!!!!!!!!!!!!!!!");
        return instance==null?(instance=new AllStocksUIController()):instance;
    }
    
    /**
     * Initializes the controller class.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		instance = this;
	}

//	@Override
//	public void start(Stage primaryStage) throws Exception {
//		// TODO Auto-generated method stub
//		Parent root =FXMLLoader.load(getClass().getResource("allStocksPanel.fxml"));
//		Scene scene = new Scene(root, 950, 600);
//		primaryStage.setScene(scene);
//		primaryStage.show();
//	}

}
