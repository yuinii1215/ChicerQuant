package AnyQuantProject.ui.allStocksUI;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.util.method.SimpleDoubleProperty;
import AnyQuantProject.util.method.SimpleIntegerProperty;
import AnyQuantProject.util.method.SimpleLongProperty;

/**
 * 
 * @author GraceHan
 *
 */
public class AllStocksUI extends Application {
	Label titleLabel;
	Scene allStockUIScene;
	TableView<Stock> table;
	List<Stock> allStocksList;
	
	private static javafx.geometry.Rectangle2D primaryScreenBounds = Screen
			.getPrimary().getVisualBounds();
	private static double scrH = primaryScreenBounds.getHeight();
	private static double scrW = primaryScreenBounds.getWidth();
	private static double positionX = primaryScreenBounds.getMinX();
	private static double positionY = primaryScreenBounds.getMinY();

	@Override
	public void start(Stage primaryStage){
		// TODO Auto-generated method stub
		Stock stockData1 = new Stock();
		Stock stockData2 = new Stock();
		allStocksList = new ArrayList<Stock>();
		allStocksList.add(stockData1);
		allStocksList.add(stockData2);

		allStockUIScene	= new Scene(new Group());
		allStockUIScene.getStylesheets().add(
				getClass().getResource("allStockSceneDecoration.css").toExternalForm());

		primaryStage.setHeight(scrH * 3 / 4 / 490 * 600);
		primaryStage.setWidth(scrW * 3 / 4);
		
		titleLabel = new Label("所有股票");
		titleLabel.setAlignment(Pos.CENTER);
//		titleLabel.setFont(new Font("Arial", 20));
//		titleLabel.getStyleClass().add("titleLabel");
//		titleLabel.setPrefSize(900, 25);
//		titleLabel.setLayoutX(positionX + 60);
//		titleLabel.setLayoutY(positionY - 25);
		
		table = new TableView<>();
		table.setLayoutX(positionX + 60);
		table.setLayoutY(positionY + 8);
		table.setPrefSize(900, 620);
		table.setItems(FXCollections.observableArrayList(allStocksList));

//		TableColumn<Stock, String> dateColumn = new TableColumn<>("数据日期");
//		dateColumn.setMinWidth(40);
//		dateColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell
//		 .getValue().getDate().toString()));
//
//		TableColumn<Stock, Double> openColumn = new TableColumn<>("开盘价");
//		 openColumn.setCellValueFactory(cell -> new SimpleDoubleProperty(cell
//		 .getValue().getOpen()));
//
//		TableColumn<Stock, Double> highColumn = new TableColumn<>("最高价");
//		 highColumn.setCellValueFactory(cell -> new SimpleDoubleProperty(cell
//		 .getValue().getHigh()));
//
//		TableColumn<Stock, Double> lowColumn = new TableColumn<>("最低价");
//		 lowColumn.setCellValueFactory(cell -> new SimpleDoubleProperty(cell
//		 .getValue().getLow()));
//	
//		TableColumn<Stock, Double> closeColumn = new TableColumn<>("收盘价");
//		 closeColumn.setCellValueFactory(cell -> new SimpleDoubleProperty(cell
//		 .getValue().getClose()));
//
//		TableColumn<Stock, Integer> volumnColumn = new TableColumn<>("成交量");
//		 volumnColumn.setCellValueFactory(cell -> new SimpleIntegerProperty(cell
//		 .getValue().getVolume()));
//	
//		TableColumn<Stock, Double> adj_priceColumn = new TableColumn<>("后复权价");
//		 adj_priceColumn.setCellValueFactory(cell -> new SimpleDoubleProperty(cell
//		 .getValue().getAdj_price()));

//		TableColumn<Stock, Long> marketValueColumn = new TableColumn<>("市值");
//		marketValueColumn.setCellValueFactory(cell -> new SimpleLongProperty(cell
//		 .getValue().getMarketvalue()));
//
//		TableColumn<Stock, Long> flowColumn = new TableColumn<>("流通");
//		flowColumn.setCellValueFactory(cell -> new SimpleLongProperty(cell
//				 .getValue().getFlow()));
//		
//		TableColumn<Stock, Double> turnOverColumn = new TableColumn<>("换手率");
//		turnOverColumn.setCellValueFactory(cell -> new SimpleDoubleProperty(cell
//				 .getValue().getTurnover()));

		TableColumn<Stock, Double> peColumn = new TableColumn<>("市盈率");
		peColumn.setCellValueFactory(cell -> new SimpleDoubleProperty(cell
				 .getValue().getPe()));

		TableColumn<Stock, Double> pbColumn = new TableColumn<>("市净率");
		pbColumn.setCellValueFactory(cell -> new SimpleDoubleProperty(cell
				 .getValue().getPb()));

//		table.getColumns().addAll(dateColumn, openColumn, closeColumn,
//				highColumn, lowColumn, volumnColumn, adj_priceColumn,
//				marketValueColumn, flowColumn, turnOverColumn, peColumn,
//				pbColumn);
		
		((Group) allStockUIScene.getRoot()).getChildren().addAll(table,titleLabel);

		primaryStage.setScene(allStockUIScene);
		primaryStage.show();
	}
}
