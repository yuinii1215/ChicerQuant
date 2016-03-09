package AnyQuantProject.ui.singleStockInfoUI;

import AnyQuantProject.bl.factoryBL.SingleStockBLFactory;
import AnyQuantProject.bl.favoriteBL.FavoriteBLController;
import AnyQuantProject.bl.listFilterBL.ListFilterBLImpl;
import AnyQuantProject.blService.favoriteBLService.FavoriteBLService;
import AnyQuantProject.blService.singleStockDealBLService.SingleStockDealBLService;
import AnyQuantProject.blService.singleStockInfoBLService.SingleStockInfoBLService;
import AnyQuantProject.dataStructure.OperationResult;
import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.util.method.CalendarHelper;
import AnyQuantProject.util.method.SimpleDoubleProperty;
import AnyQuantProject.util.method.SimpleIntegerProperty;
import AnyQuantProject.util.method.SimpleLongProperty;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javafx.event.ActionEvent;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

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
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableRow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import AnyQuantProject.util.method.TableRowControl;

/**
 *
 * @author GraceHan
 *
 */
public class SingleStockInfoUIController implements Initializable {

    public Scene singleStockUIScene;
    @FXML
    public TableView<Stock> table;
    @FXML
    public Label titleLabel;
    
    /**
     * nameLabel is the current name of a single stock and there are things to be added
     */
    @FXML
    public Label nameLabel;
    @FXML
    public  TextField minRange;
    @FXML
    public TextField maxRange;
    @FXML
    public ComboBox keyWordBox;
    @FXML
    public  Button filterButton;
    @FXML
    public Button isFavorButton;
    @FXML
    public DatePicker startDatePicker;
    @FXML
    public DatePicker endDatePicker;

    public List<Stock> singleStockList = new ArrayList<Stock>();
    public Stock singleStock=new Stock();
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

    boolean[] filterFlag;
    Double minFilter, maxFilter, targetFilter;
    Calendar minTime, maxTime, targetTime;
    String keyWord;
    static String stockName;
   
    SingleStockDealBLService singleStockDealBlImpl;
    ListFilterBLImpl listFilterBlImpl;
    FavoriteBLService favoriteBlImpl;
    OperationResult operationResult;
    SingleStockInfoBLService singleStockBlImpl;
    CalendarHelper calendarHelper = new CalendarHelper();

    private static SingleStockInfoUIController instance = null;

    public static SingleStockInfoUIController getInstance(String name) {
    	SingleStockInfoUIController.stockName =name;
        /**
         * 此处之后应该对接初始化数据的方法,但好像还是比较有问题?????
         */
        //initialize stockName;!!!!!!
        return instance==null?(instance=new SingleStockInfoUIController()):instance;
    }

    @FXML
    private void handleFilterAction(ActionEvent actionEvent) {
        /**
         * the filterFlag is an array to control the filter action
         *
         */
        filterFlag = new boolean[5];
        for (int i = 0; i < 5; i++) {
            filterFlag[i] = false;
        }
        System.out.println("this is FILTER ");
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
          favoriteBlImpl=new FavoriteBLController();

        if (singleStockList.get(0).isFavor() == false) {
            // change the state of the stock into being favored
            favoriteBlImpl.favorStock(singleStockList.get(0).getName());
            singleStockList.get(0).setFavor(true);
            isFavorButton.setText("取消关注");
        } else {
            // change the state of the stock into unfavored
            favoriteBlImpl.unFavorStock(singleStockList.get(0).getName());
            singleStockList.get(0).setFavor(false);
            isFavorButton.setText("加关注");
        }

    }


    public void init() {
        /*
        get数据的方法
        */ 
      //  
      singleStockBlImpl=SingleStockBLFactory.getSingleStockInfoBLService();
      singleStockDealBlImpl=SingleStockBLFactory.getSingleStockDealBLService();
      singleStock=singleStockBlImpl.getSingleStockInfo(stockName);
      singleStockList=singleStockDealBlImpl.getSingleStockDeal(keyWord, minTime);
      
          Stock data1=new Stock();
          Stock data2=new Stock();
          data1.setFavor(true);
          data2.setFavor(true);
          data1.setName("腾讯科技");
          singleStockList.add(data1);
          singleStockList.add(data2);
        /**
         * 之后要改,调用singleStock
         */
        nameLabel.setText(singleStock.getName());
        
        /**
         * initialize the button
         */

        if (singleStock.isFavor() == true) {
            isFavorButton.setText("取消关注");
        } else {
            isFavorButton.setText("加关注");
        }

        /*
          initialize the combobox
         */

        keyWordBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                keyWord = t1;
                System.out.println("the selected is: " + t1);
            }
        });

        /**
         * initialize the table
         */
        table.setItems(FXCollections.observableArrayList(singleStockList));

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

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

}
