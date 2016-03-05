package AnyQuantProject.ui.SingleStockInfoUI;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import AnyQuantProject.ui.SingleStockUI.SingleStockUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import AnyQuantProject.bl.favoriteBL.FavoriteBLController;
import AnyQuantProject.bl.listFilterBL.ListFilterBLImpl;
import AnyQuantProject.blService.favoriteBLService.FavoriteBLService;
import AnyQuantProject.dataStructure.OperationResult;
import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.ui.SingleStockUI.SingleStockUI;
/**
 * 
 * @author QiHan
 *
 */
import AnyQuantProject.util.method.CalendarHelper;

public class SingleStockInfoUI extends SingleStockUI {
	Label titleLabel;
	Label nameLabel;
	Button isFavorButton;
	String date;
	Double open;
	Double close;
	Double high;
	Double low;
	int volumn;
	Double adj_price;
	long marketValue;
	long flow;
	double turnOver;
	double pe;
	double pb;
	Scene singleStockUIScene;
	List<Stock> singleStockList;
	TableView<Stock> table;
	TextField minRange;
	TextField maxRange;
	ComboBox keyWordBox;
	Label keyWordLabel;
	Label rangeLabel1;
	Label rangeLabel2;
	Button filterButton;
	String keyWord;
	DatePicker startDatePicker;
	DatePicker endDatePicker;
	double minFilter;
	double maxFilter;
	double targetAmount;
	Calendar targetDate;
	Calendar minTime;
	Calendar maxTime;
	boolean filterFlag[];
	ListFilterBLImpl listFilterBlImpl;
	FavoriteBLService favoriteBlImpl;
	OperationResult operationResult;

	private static javafx.geometry.Rectangle2D primaryScreenBounds = Screen
			.getPrimary().getVisualBounds();
	private static double scrH = primaryScreenBounds.getHeight();
	private static double scrW = primaryScreenBounds.getWidth();
	private static double positionX = primaryScreenBounds.getMinX();
	private static double positionY = primaryScreenBounds.getMinY();

//	 public static void main(String[] args) {
//	 launch(args);
//	 }

	public void setStock(List<Stock> singleStock) {
		this.singleStockList = singleStock;
	}

	public void start(Stage stage) {

		for (int i = 0; i < 5; i++) {
			filterFlag[i] = false;
		}
		favoriteBlImpl = new FavoriteBLController();
		operationResult = new OperationResult();

		singleStockUIScene = new Scene(new Group());
		singleStockUIScene.getStylesheets().add(
				getClass().getResource("addFavorButton.css").toExternalForm());

		stage.setHeight(scrH * 3 / 4 / 490 * 600);
		stage.setWidth(scrW * 3 / 4);

		ObservableList<String> KeyWordOptions = FXCollections
				.observableArrayList("日期", "开盘价", "最高价", "最低价", "收盘价", "成交量",
						"后复权价", "市值", "流通量");
		keyWordBox = new ComboBox(KeyWordOptions);
		keyWordBox.setPromptText("关键字");
		keyWordBox.setPrefSize(90, 8);
		keyWordBox.setLayoutX(positionX + 80);
		keyWordBox.setLayoutY(positionY + 8);
		keyWordBox.getStyleClass().add("comBox");

		keyWordBox.setEditable(true);
		keyWordBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue ov, String t, String t1) {
				keyWord = t1;
				filterFlag[0] = true;
				System.out.println(keyWord);

			}
		});

		minRange = new TextField();
		minRange.setPromptText("起始值");
		minRange.setPrefSize(70, 2);
		minRange.setLayoutX(positionX + 195);
		minRange.setLayoutY(positionY + 8);
		minRange.getStyleClass().add("textArea");

		rangeLabel1 = new Label("—");
		rangeLabel1.setLayoutX(positionX + 266);
		rangeLabel1.setLayoutY(positionY + 15);

		maxRange = new TextField();
		maxRange.setPromptText("截止值");
		maxRange.setPrefSize(70, 2);
		maxRange.setLayoutX(positionX + 280);
		maxRange.setLayoutY(positionY + 8);
		maxRange.getStyleClass().add("textArea");

		startDatePicker = new DatePicker();
		Date promptDate = new Date(110, 10, 20);
		startDatePicker.setPromptText("起始时间");
		startDatePicker.setPrefSize(115, 10);
		startDatePicker.setLayoutX(positionX + 373);
		startDatePicker.setLayoutY(positionY + 8);
		startDatePicker.getStyleClass().add("picker");
		LocalDate date3 = promptDate.toInstant().atZone(ZoneId.systemDefault())
				.toLocalDate();
		startDatePicker.setPromptText(date3.toString());

		rangeLabel2 = new Label("—");
		rangeLabel2.setLayoutX(positionX + 487);
		rangeLabel2.setLayoutY(positionY + 15);

		endDatePicker = new DatePicker();
		endDatePicker.setPromptText("截止时间");
		endDatePicker.setPrefSize(115, 10);
		endDatePicker.setLayoutX(positionX + 500);
		endDatePicker.setLayoutY(positionY + 8);
		endDatePicker.getStyleClass().add("picker");

		// change the default date to yesterday
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
		Date d = new Date();
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(d);
		rightNow.add(Calendar.DAY_OF_YEAR, -1);
		Date dt1 = rightNow.getTime();
		LocalDate date2 = dt1.toInstant().atZone(ZoneId.systemDefault())
				.toLocalDate();
		endDatePicker.setPromptText(date2.toString());

		// add the shadow effect for the buttons
		DropShadow shadow = new DropShadow();

		// 对应于 String columnName, double min, double max, Calender min, Calender
		// max
		filterButton = new Button("搜索");
		filterButton.setPrefSize(40, 4);
		filterButton.setFont(new Font("Arial", 10));
		filterButton.setLayoutX(positionX + 624);
		filterButton.setLayoutY(positionY + 11);
		filterButton
				.setStyle("-fx-text-fill:#FFFFFF; -fx-background-color: #C67171;");

		filterButton.addEventHandler(MouseEvent.MOUSE_ENTERED,
				(MouseEvent e) -> {
					filterButton.setEffect(shadow);
				});
		// Removing the shadow when the mouse cursor is off
		filterButton.addEventHandler(MouseEvent.MOUSE_EXITED,
				(MouseEvent e) -> {
					filterButton.setEffect(null);
				});
		// change the state when the mouse cursor is off
		filterButton.addEventHandler(MouseEvent.MOUSE_PRESSED,
				(MouseEvent e) -> {
					filterButton.setEffect(null);
					// 得到以上五个值域的所有内容并且去调取bl层方法

				if (minRange.getText().trim().length() >= 1) {
					System.out.println("the minRange is not Empty");
					minFilter = Double.valueOf(minRange.getText());
					filterFlag[1] = true;
				} else {
					System.out.println("the minRange is Empty");
				}

				if (maxRange.getText().trim().length() >= 1) {
					System.out.println("the maxRange is not Empty");
					maxFilter = Double.valueOf(maxRange.getText());
					filterFlag[2] = true;
				} else {
					System.out.println("the maxRange is Empty");
				}

				CalendarHelper calendarHelper = new CalendarHelper();
				if (startDatePicker.getValue() != null) {
					System.out.println("the startDate is not Empty");
					LocalDate startLocalDate = startDatePicker.getValue();
					minTime = calendarHelper.convert2LocalDate(startLocalDate);
					filterFlag[3] = true;
					/**
					 * 此处缺少把minTime赋值的功能
					 */
				} else {
					System.out.println("the startDate is Empty");
				}

				if (endDatePicker.getValue() != null) {
					System.out.println("the endDate is not Empty");
					LocalDate endLocalDate = endDatePicker.getValue();
					maxTime = calendarHelper.convert2LocalDate(endLocalDate);
					filterFlag[4] = true;
					/**
					 * 此处缺少把minTime赋值的功能
					 */
				} else {
					System.out.println("the startDate is Empty");
				}
				
				singleStockList=filterControl(singleStockList);
			});

		titleLabel = new Label("股票详情");
		titleLabel.setAlignment(Pos.CENTER);
		titleLabel.setFont(new Font("Arial", 20));
		titleLabel.getStyleClass().add("titleLabel");
		titleLabel.setPrefSize(900, 25);
		titleLabel.setLayoutX(positionX + 60);
		titleLabel.setLayoutY(positionY - 25);

		nameLabel = new Label(singleStockList.get(0).getName());
		nameLabel.setFont(new Font("Arial", 36));
		nameLabel.setLayoutX(positionX + 715);
		nameLabel.setLayoutY(positionY + 20);

		isFavorButton = new Button();
		isFavorButton.setPrefSize(90, 28);
		isFavorButton.getStyleClass().add("button");

		if (singleStockList.get(0).isFavor() == true) {// 假设1的时候表示已经关注
			isFavorButton.setText("取消关注");
		} else {
			isFavorButton.setText("加关注");
		}
		// show the shadow when the mouse cursor is off
		isFavorButton.addEventHandler(MouseEvent.MOUSE_ENTERED,
				(MouseEvent e) -> {
					isFavorButton.setEffect(shadow);
				});
		// Removing the shadow when the mouse cursor is off
		isFavorButton.addEventHandler(MouseEvent.MOUSE_EXITED,
				(MouseEvent e) -> {
					isFavorButton.setEffect(null);
				});
		// change the state when the mouse cursor is off
		isFavorButton.addEventHandler(MouseEvent.MOUSE_PRESSED,
				(MouseEvent e) -> {
					isFavorButton.setEffect(null);
					if (singleStockList.get(0).isFavor() == false) {
						// change the state of the stock into being favored
				favoriteBlImpl.favorStock(singleStockList.get(0).getName());
				isFavorButton.setText("取消关注");
			} else {
				// change the state of the stock into unfavored
				favoriteBlImpl.unFavorStock(singleStockList.get(0).getName());
				isFavorButton.setText("加关注");
			}
		});

		isFavorButton.setFont(new Font("Arial", 20));
		isFavorButton.setLayoutX(positionX + 830);
		isFavorButton.setLayoutY(positionY + 70);

		table = new TableView<>();
		table.setLayoutX(positionX + 70);
		table.setLayoutY(positionY + 36);
		table.setPrefSize(600, 570);
		table.setItems(FXCollections.observableArrayList(singleStockList));

		TableColumn<Stock, String> dateColumn = new TableColumn<>("数据日期");
		// dateColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell
		// .getValue().getDate()));
		// table.getColumns().add(dateColumn);

		TableColumn<Stock, Double> openColumn = new TableColumn<>("开盘价");
		// openColumn.setCellValueFactory(cell -> new SimpleDoubleProperty(cell
		// .getValue().getOpen()));
		// table.getColumns().add(openColumn);

		TableColumn<Stock, Double> highColumn = new TableColumn<>("最高价");
		// highColumn.setCellValueFactory(new PropertyValueFactory<Stock,
		// Double>(
		// "high"));
		// table.getColumns().add(highColumn);

		TableColumn<Stock, Double> lowColumn = new TableColumn<>("最低价");
		// lowColumn.setCellValueFactory(new PropertyValueFactory<Stock,
		// Double>(
		// "low"));
		// table.getColumns().add(lowColumn);
		//
		TableColumn<Stock, Double> closeColumn = new TableColumn<>("收盘价");
		// closeColumn
		// .setCellValueFactory(new PropertyValueFactory<Stock, Double>(
		// "close"));
		// table.getColumns().add(closeColumn);
		//
		TableColumn<Stock, Integer> volumnColumn = new TableColumn<>("成交量");
		// volumnColumn
		// .setCellValueFactory(new PropertyValueFactory<Stock, Integer>(
		// "volumn"));
		// table.getColumns().add(volumnColumn);
		//
		TableColumn<Stock, Double> adj_priceColumn = new TableColumn<>("后复权价");
		// adj_priceColumn
		// .setCellValueFactory(new PropertyValueFactory<Stock, Double>(
		// "adj_price"));
		// table.getColumns().add(adj_priceColumn);

		TableColumn<Stock, Integer> marketValueColumn = new TableColumn<>("市值");
		// marketValueColumn
		// .setCellValueFactory(new PropertyValueFactory<Stock, Integer>(
		// "marketValue"));
		// table.getColumns().add(marketValueColumn);
		//
		TableColumn<Stock, Integer> flowColumn = new TableColumn<>("流通");
		// flowColumn
		// .setCellValueFactory(new PropertyValueFactory<Stock, Integer>(
		// "flow"));
		// table.getColumns().add(flowColumn);
		//
		TableColumn<Stock, Double> turnOverColumn = new TableColumn<>("换手率");
		// turnOverColumn
		// .setCellValueFactory(new PropertyValueFactory<Stock, Double>(
		// "turnOver"));
		// table.getColumns().add(turnOverColumn);
		//
		TableColumn<Stock, Double> peColumn = new TableColumn<>("市盈率");
		// peColumn.setCellValueFactory(new PropertyValueFactory<Stock, Double>(
		// "PE"));
		// table.getColumns().add(peColumn);
		//
		TableColumn<Stock, Double> pbColumn = new TableColumn<>("市净率");
		// pbColumn.setCellValueFactory(new PropertyValueFactory<Stock, Double>(
		// "PB"));
		// table.getColumns().add(pbColumn);

		table.getColumns().addAll(dateColumn, openColumn, closeColumn,
				highColumn, lowColumn, volumnColumn, adj_priceColumn,
				marketValueColumn, flowColumn, turnOverColumn, peColumn,
				pbColumn);
		((Group) singleStockUIScene.getRoot()).getChildren().addAll(table,
				keyWordBox, minRange, rangeLabel1, maxRange, startDatePicker,
				rangeLabel2, endDatePicker, titleLabel, nameLabel,
				isFavorButton, filterButton);

		stage.setScene(singleStockUIScene);
		stage.show();
	}

	public List<Stock> filterControl(List<Stock> currentList) {
		List<Stock> filteredList = currentList;
		listFilterBlImpl = new ListFilterBLImpl();
		
		if(!filterFlag[0]){
			return singleStockList;
		}
		else if(filterFlag[3]&&filterFlag[4]&&minTime==maxTime){
			targetDate=minTime;
			filteredList=listFilterBlImpl.filterStocksByDateEqual(currentList, targetDate);
			filterFlag[3]=false;
			filterFlag[4]=false;
		}
		else if(filterFlag[3]&&filterFlag[4]){
			filteredList=listFilterBlImpl.filterStocksByDateAmong(currentList, minTime,maxTime);
			filterFlag[3]=false;
			filterFlag[4]=false;
		}
		else if(filterFlag[3]&&(!filterFlag[4])){
			filteredList=listFilterBlImpl.filterStocksByDateGreater(currentList,minTime);
			filterFlag[3]=false;
		}
		else if(!filterFlag[3]&&filterFlag[4]){
			filteredList=listFilterBlImpl.filterStocksByDateLess(currentList, maxTime);
			filterFlag[4]=false;
		}
		else if(filterFlag[1]&&filterFlag[2]&&minFilter==maxFilter){
			targetAmount=minFilter;
			filteredList=listFilterBlImpl.filterStocksByFieldEqual(currentList, keyWord, targetAmount);
			filterFlag[1]=false;
			filterFlag[2]=false;
		}
		else if(filterFlag[1]&&filterFlag[2]){
			filteredList=listFilterBlImpl.filterStocksByFieldAmong(currentList, keyWord, minFilter, maxFilter);
			filterFlag[1]=false;
			filterFlag[2]=false;
		}
		else if(filterFlag[1]&&(!filterFlag[2])){
			filteredList=listFilterBlImpl.filterStocksByFieldGreater(currentList, keyWord, minFilter);
			filterFlag[1]=false;
		}
		else if((!filterFlag[1])&&filterFlag[2]){
			filteredList=listFilterBlImpl.filterStocksByFieldLess(currentList, keyWord, maxFilter);
			filterFlag[2]=false;
		}
		
       if(!(filterFlag[1])&&(!filterFlag[2])&&(!filterFlag[3])&&(!filterFlag[3])){
    	   return filteredList;
       }
       else{
    	 return filterControl(currentList);
       }
	}
}
