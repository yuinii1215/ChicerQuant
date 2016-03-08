package AnyQuantProject.ui.singleStockInfoUI;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javafx.event.ActionEvent;
import AnyQuantProject.util.method.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import AnyQuantProject.bl.favoriteBL.FavoriteBLController;
import AnyQuantProject.bl.listFilterBL.ListFilterBLImpl;
import AnyQuantProject.blService.favoriteBLService.FavoriteBLService;
import AnyQuantProject.dataStructure.OperationResult;
import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.ui.allStocksUI.AllStocksUIController;
import AnyQuantProject.ui.singleStockUI.SingleStockUI;
import AnyQuantProject.util.method.CalendarHelper;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.TableRow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * 
 * @author GraceHan
 *
 */
public class OriginSingleStockInfoUIController implements Initializable{

	Scene singleStockUIScene;
	TableView<Stock> table;

	TextField minRange;
	TextField maxRange;
	ComboBox keyWordBox;
	Label rangeLabel1;
	Label rangeLabel2;
	Button filterButton, isFavorButton;
	DatePicker startDatePicker;
	DatePicker endDatePicker;

	List<Stock> singleStockList;
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

	boolean[] filterFlag;
	Double minFilter, maxFilter, targetFilter;
	Calendar minTime, maxTime, targetTime;
	String keyWord;
	ListFilterBLImpl listFilterBlImpl;
	FavoriteBLService favoriteBlImpl;
	OperationResult operationResult;
	CalendarHelper calendarHelper = new CalendarHelper();
//        Parent parent;
        public static AnchorPane myPane;
   
        private static OriginSingleStockInfoUIController instance;

//    	public static SingleStockInfoUIController getInstance(AnchorPane singleStockInfoPanel) {
//    	System.out.println("here is the instance of SingleStockInfoUIController");
//        myPane=singleStockInfoPanel;
//        return instance==null?(instance=new SingleStockInfoUIController()):instance;
//    }
        
        public static OriginSingleStockInfoUIController getInstance() {
    	System.out.println("here is the instance of SingleStockInfoUIController");
        return instance==null?(instance=new OriginSingleStockInfoUIController()):instance;
    }
    
        public OriginSingleStockInfoUIController(){
            initialize(null,null);
        }
    /**
     * Initializes the controller class.
     */
    
//    public SingleStockInfoUIController launch(Pane father, Pane before) throws IOException {
//        /**
//         * 使用lauch之后不用专门调用initialize
//         */
//        parent = FXMLLoader.load(getClass().getResource(
//                    "singleStockInfoPanel.fxml"));
//        instance = SingleStockInfoUIController.getInstance();
//        return instance;
//    }
        
	@FXML
	private void handleFilterAction(ActionEvent actionEvent) {
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
		singleStockList = filterControl(singleStockList);
	}

	public List<Stock> filterControl(List<Stock> currentList) {
		List<Stock> filteredList = currentList;
		listFilterBlImpl = new ListFilterBLImpl();

		if (!filterFlag[0]) {
			return singleStockList;
		} else if (filterFlag[3] && filterFlag[4] && minTime == maxTime) {
			targetTime = minTime;
			filteredList = listFilterBlImpl.filterStocksByDateEqual(
					currentList, targetTime);
			filterFlag[3] = false;
			filterFlag[4] = false;
		} else if (filterFlag[3] && filterFlag[4]) {
			filteredList = listFilterBlImpl.filterStocksByDateAmong(
					currentList, minTime, maxTime);
			filterFlag[3] = false;
			filterFlag[4] = false;
		} else if (filterFlag[3] && (!filterFlag[4])) {
			filteredList = listFilterBlImpl.filterStocksByDateGreater(
					currentList, minTime);
			filterFlag[3] = false;
		} else if (!filterFlag[3] && filterFlag[4]) {
			filteredList = listFilterBlImpl.filterStocksByDateLess(currentList,
					maxTime);
			filterFlag[4] = false;
		} else if (filterFlag[1] && filterFlag[2] && minFilter == maxFilter) {
			targetFilter = minFilter;
			filteredList = listFilterBlImpl.filterStocksByFieldEqual(
					currentList, keyWord, targetFilter);
			filterFlag[1] = false;
			filterFlag[2] = false;
		} else if (filterFlag[1] && filterFlag[2]) {
			filteredList = listFilterBlImpl.filterStocksByFieldAmong(
					currentList, keyWord, minFilter, maxFilter);
			filterFlag[1] = false;
			filterFlag[2] = false;
		} else if (filterFlag[1] && (!filterFlag[2])) {
			filteredList = listFilterBlImpl.filterStocksByFieldGreater(
					currentList, keyWord, minFilter);
			filterFlag[1] = false;
		} else if ((!filterFlag[1]) && filterFlag[2]) {
			filteredList = listFilterBlImpl.filterStocksByFieldLess(
					currentList, keyWord, maxFilter);
			filterFlag[2] = false;
		}

		if (!(filterFlag[1]) && (!filterFlag[2]) && (!filterFlag[3])
				&& (!filterFlag[3])) {
			return filteredList;
		} else {
			return filterControl(currentList);
		}
	}

	@FXML
	private void handleFavorAction(ActionEvent actionEvent) {
		if (singleStockList.get(0).isFavor() == false) {
			// change the state of the stock into being favored
			favoriteBlImpl.favorStock(singleStockList.get(0).getName());
			isFavorButton.setText("取消关注");
		} else {
			// change the state of the stock into unfavored
			favoriteBlImpl.unFavorStock(singleStockList.get(0).getName());
			isFavorButton.setText("加关注");
		}

	}
        
	@FXML
	public void handleComboboxAction(ActionEvent actionEvent){
//	keyWord = keyWordBox.selectionModelProperty().getName();
		
        keyWordBox.getSelectionModel().selectFirst(); //select the first element
        
        keyWordBox.setCellFactory(new Callback<ListView<String>,ListCell<String>>(){

            @Override
            public ListCell<String> call(ListView<String> p) {
                
                final ListCell<String> cell = new ListCell<String>(){

                    @Override
                    protected void updateItem(String t, boolean bln) {
                        super.updateItem(t, bln);
                        if(t != null){
                            keyWord=t;
                            setText(t);
                            filterFlag[0] = true;
                        }else{
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });
	}

	@FXML
	public void initialize(URL location, ResourceBundle resources) {
		/**
		 * initialize the button
		 */
                System.out.println("this is the initialize!!!!!!!!!!!!!!!!!");
                isFavorButton=(Button)myPane.lookup("#isFavorButton");
		if (singleStockList.get(0).isFavor() == true) {// 假设1的时候表示已经关注
			isFavorButton.setText("取消关注");
		} else {
			isFavorButton.setText("加关注");
		}

		/**
		 * initialize the table
		 */
                 table=(TableView)myPane.lookup("#tableView");
		 table.setItems(FXCollections.observableArrayList(singleStockList));
			
                 table.setRowFactory(new Callback<TableView<Stock>,TableRow<Stock>>(){
				@Override
				public TableRow<Stock> call(TableView<Stock> table) {
					// TODO Auto-generated method stub
					return new TableRowControl(table);
				}
            });
		table.setItems(FXCollections.observableArrayList(singleStockList));

		/**
		 * initialize the tabel columns
		 */
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

}
