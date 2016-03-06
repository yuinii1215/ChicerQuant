package AnyQuantProject.ui.allStocksUI;

import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import AnyQuantProject.dataStructure.Stock;
/**
 * 
 * @author GraceHan
 *
 */
public class AllStocksUIController extends Application {
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
	
	//用来测试的main
	public static void main(String[] args) {
	launch();
    }
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Parent root =FXMLLoader.load(getClass().getResource("allStocksPanel.fxml"));
		Scene scene = new Scene(root, 950, 600);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
