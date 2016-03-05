package AnyQuantProject.ui.singleStockUI;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import AnyQuantProject.bl.listFilterBL.ListFilterBLImpl;
import AnyQuantProject.blService.favoriteBLService.FavoriteBLService;
import AnyQuantProject.dataStructure.OperationResult;
import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.util.method.CalendarHelper;

/**
 * 
 * @author GraceHan
 *
 */
public class SingleStockUIController extends Application {


	Scene singleStockUIScene;

	TableView<Stock> table;
	
	TextField minRange;
	TextField maxRange;
	ComboBox keyWordBox;
	Label rangeLabel1;
	Label rangeLabel2;
	Button filterButton;
	DatePicker startDatePicker;
	DatePicker endDatePicker;

//	List<Stock> singleStockList;
	TableColumn<Stock, String> dateColumn;
	TableColumn<Stock, Double> openColumn;
	TableColumn<Stock, Double> closeColumn;
	TableColumn<Stock, Double> highColumn;
	TableColumn<Stock, Double> lowColumn;
	TableColumn<Stock, Double> adj_priceColumn;
	TableColumn<Stock, Integer> volumeColumn;
	TableColumn<Stock, Long> marketValueColumn;
	TableColumn<Stock, Long> flowColumn;
	TableColumn<Stock, Double> peColumn;
	TableColumn<Stock, Double> pbColumn;

	private static SingleStockUIController instance;

	public static SingleStockUIController getInstance() {
		return instance;
	}

	public static void main(String[] args) {
		launch();
	}

	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource(
				"singleStockInfoPanel.fxml"));
		Scene scene = new Scene(root, 900, 600);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	// @FXML
	// public void filterStockInfo(ActionEvent actionEvent) {
	// if (minRange.getText().trim().length() >= 1) {
	// System.out.println("the minRange is not Empty");
	// minFilter = Double.valueOf(minRange.getText());
	// filterFlag[1] = true;
	// } else {
	// System.out.println("the minRange is Empty");
	// }
	//
	// if (maxRange.getText().trim().length() >= 1) {
	// System.out.println("the maxRange is not Empty");
	// maxFilter = Double.valueOf(maxRange.getText());
	// filterFlag[2] = true;
	// } else {
	// System.out.println("the maxRange is Empty");
	// }
	//
	// CalendarHelper calendarHelper = new CalendarHelper();
	// if (startDatePicker.getValue() != null) {
	// System.out.println("the startDate is not Empty");
	// LocalDate startLocalDate = startDatePicker.getValue();
	// minTime = calendarHelper.convert2LocalDate(startLocalDate);
	// filterFlag[3] = true;
	// /**
	// * 此处缺少把minTime赋值的功能
	// */
	// } else {
	// System.out.println("the startDate is Empty");
	// }
	//
	// if (endDatePicker.getValue() != null) {
	// System.out.println("the endDate is not Empty");
	// LocalDate endLocalDate = endDatePicker.getValue();
	// maxTime = calendarHelper.convert2LocalDate(endLocalDate);
	// filterFlag[4] = true;
	// /**
	// * 此处缺少把minTime赋值的功能
	// */
	// } else {
	// System.out.println("the startDate is Empty");
	// }
	// singleStockList=filterControl(singleStockList);
	//
	// }
	// public List<Stock> filterControl(List<Stock> currentList) {
	// List<Stock> filteredList = currentList;
	// listFilterBlImpl = new ListFilterBLImpl();
	//
	// if(!filterFlag[0]){
	// return singleStockList;
	// }
	// else if(filterFlag[3]&&filterFlag[4]&&minTime==maxTime){
	// targetDate=minTime;
	// filteredList=listFilterBlImpl.filterStocksByDateEqual(currentList,
	// targetDate);
	// filterFlag[3]=false;
	// filterFlag[4]=false;
	// }
	// else if(filterFlag[3]&&filterFlag[4]){
	// filteredList=listFilterBlImpl.filterStocksByDateAmong(currentList,
	// minTime,maxTime);
	// filterFlag[3]=false;
	// filterFlag[4]=false;
	// }
	// else if(filterFlag[3]&&(!filterFlag[4])){
	// filteredList=listFilterBlImpl.filterStocksByDateGreater(currentList,minTime);
	// filterFlag[3]=false;
	// }
	// else if(!filterFlag[3]&&filterFlag[4]){
	// filteredList=listFilterBlImpl.filterStocksByDateLess(currentList,
	// maxTime);
	// filterFlag[4]=false;
	// }
	// else if(filterFlag[1]&&filterFlag[2]&&minFilter==maxFilter){
	// targetAmount=minFilter;
	// filteredList=listFilterBlImpl.filterStocksByFieldEqual(currentList,
	// keyWord, targetAmount);
	// filterFlag[1]=false;
	// filterFlag[2]=false;
	// }
	// else if(filterFlag[1]&&filterFlag[2]){
	// filteredList=listFilterBlImpl.filterStocksByFieldAmong(currentList,
	// keyWord, minFilter, maxFilter);
	// filterFlag[1]=false;
	// filterFlag[2]=false;
	// }
	// else if(filterFlag[1]&&(!filterFlag[2])){
	// filteredList=listFilterBlImpl.filterStocksByFieldGreater(currentList,
	// keyWord, minFilter);
	// filterFlag[1]=false;
	// }
	// else if((!filterFlag[1])&&filterFlag[2]){
	// filteredList=listFilterBlImpl.filterStocksByFieldLess(currentList,
	// keyWord, maxFilter);
	// filterFlag[2]=false;
	// }
	//
	// if(!(filterFlag[1])&&(!filterFlag[2])&&(!filterFlag[3])&&(!filterFlag[3])){
	// return filteredList;
	// }
	// else{
	// return filterControl(currentList);
	// }
	// }

}