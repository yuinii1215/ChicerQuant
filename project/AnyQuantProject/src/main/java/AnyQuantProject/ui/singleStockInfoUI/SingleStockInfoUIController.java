 package AnyQuantProject.ui.singleStockInfoUI;

import AnyQuantProject.bl.factoryBL.FavoriteBLFactory;
import AnyQuantProject.bl.factoryBL.ListFilterBLFactory;
import AnyQuantProject.bl.factoryBL.SingleStockBLFactory;
import AnyQuantProject.bl.favoriteBL.FavoriteBLController;
import AnyQuantProject.bl.listFilterBL.ListFilterBLImpl;
import AnyQuantProject.blService.favoriteBLService.FavoriteBLService;
import AnyQuantProject.blService.listFilterBLService.ListFilterBLService;
import AnyQuantProject.blService.singleStockDealBLService.SingleStockDealBLService;
import AnyQuantProject.blService.singleStockInfoBLService.SingleStockInfoBLService;
import AnyQuantProject.dataStructure.OperationResult;
import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.util.method.CalendarHelper;
import AnyQuantProject.util.method.SimpleDoubleProperty;
import AnyQuantProject.util.method.SimpleIntegerProperty;
import AnyQuantProject.util.method.SimpleLongProperty;
import javafx.scene.paint.Color;
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
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableRow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import AnyQuantProject.ui.singleStockInfoUI.StockInfo2Column;

/**
 *
 * @author GraceHan
 *
 */
public class SingleStockInfoUIController implements Initializable {

    public static void getInstance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Scene singleStockUIScene;
    @FXML
    public TableView<Stock> table;
    @FXML
    public Label titleLabel;
    @FXML
    public Label nameLabel,chineseNameLabel;
    @FXML
    Pane filterConditionPane;
    @FXML
    public  TextField minRange;
    @FXML
    public TextField maxRange;
    @FXML
    public ComboBox keyWordBox;
    @FXML
    public  Button filterCancelButton;
     @FXML
    public  Button filterPerformButton;
    @FXML
    public ImageView filterImage;
    @FXML
    public Button isFavorButton;
    @FXML
    public DatePicker startDatePicker;
    @FXML
    public DatePicker endDatePicker;
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
    @FXML
    public Button helperButton;
    @FXML
    public TableView<Map.Entry<String, Double>> tableView2,titleTable;
    @FXML
    public TableColumn<Map.Entry<String, Double>, String> key_Column,key_Column2;
    @FXML
    public TableColumn<Map.Entry<String, Double>, Double> value_Column,value_Column2;
    
    int rowColorFlag=0;//flag取0,-1,1三种状态,0表示不涨不跌,1表示涨了,-1表示跌了

    Image filterButton_normal = new Image(getClass().getResourceAsStream("/images/filterButton_normal.png"));
    Image filterButton_pressed = new Image(getClass().getResourceAsStream("/images/filterButton_pressed.png"));
    Image filterButton_entered = new Image(getClass().getResourceAsStream("/images/filterButton_entered.png"));
   
    boolean[] filterFlag={false,false,false,false,false};
    Double minFilter, maxFilter, targetFilter;
    Calendar minTime, maxTime, targetTime;
    String keyWord,selectedColumnName;
    private String stockName =null;
   
    SingleStockDealBLService singleStockDealBlImpl;
    ListFilterBLService listFilterBlImpl;
    FavoriteBLService favoriteBlImpl;
    OperationResult operationResult;
    SingleStockInfoBLService singleStockBlImpl;
    CalendarHelper calendarHelper = new CalendarHelper();
    
    public List<Stock> singleStockList = new ArrayList<Stock>();
    public Stock singleStock=new Stock();

    public void laterInit(String name) {
    	this.stockName =name;
    	minTime=Calendar.getInstance();
        this.init();
    }
    /**
     * 
     * @param  TO BE EDIT
     */
    private void filterPerformAction() {
        
        if (minRange.getText().trim().length() >= 1) {
            minFilter = Double.valueOf(minRange.getText());
            filterFlag[1] = true;
        } else {
       
        }

        if (maxRange.getText().trim().length() >= 1) {
            maxFilter = Double.valueOf(maxRange.getText());
            filterFlag[2] = true;
        } else {
  
        }

        if (startDatePicker.getValue() != null) {
            filterFlag[0] = true;
            LocalDate startLocalDate = startDatePicker.getValue();
            minTime = calendarHelper.convert2Calendar(startLocalDate);
            filterFlag[3] = true;
           
        } else {
            
        }

        if (endDatePicker.getValue() != null) {
            filterFlag[0] = true;
            LocalDate endLocalDate = endDatePicker.getValue();
            maxTime = calendarHelper.convert2Calendar(endLocalDate);
            filterFlag[4] = true;
         
        } else {
            
        }
        singleStockList = filterControl(singleStockList);
        table.getItems().clear();
        table.getItems().addAll(singleStockList);
    }

    public List<Stock> filterControl(List<Stock> currentList) {
        List<Stock> filteredList = currentList;
        listFilterBlImpl =ListFilterBLFactory.getListFilterBLService();

        if (!filterFlag[0]) {
            return singleStockList;
        } else if (filterFlag[3] && filterFlag[4] && minTime.equals(maxTime)) {
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
                    currentList, selectedColumnName, minFilter, maxFilter);
            filterFlag[1] = false;
            filterFlag[2] = false;
        } else if (filterFlag[1] && (!filterFlag[2])) {
            filteredList = listFilterBlImpl.filterStocksByFieldGreater(
                    currentList, selectedColumnName, minFilter);
            filterFlag[1] = false;
        } else if ((!filterFlag[1]) && filterFlag[2]) {
            filteredList = listFilterBlImpl.filterStocksByFieldLess(
                    currentList, selectedColumnName, maxFilter);
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
          favoriteBlImpl=FavoriteBLFactory.getFavoriteBLService();

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
    @FXML
    public void quitFilterPane(ActionEvent actionEvent){
        if(filterConditionPane.getOpacity()!=0.0){
        filterConditionPane.setOpacity(0.0);
        }
}
    @FXML
    public void handleFilterAction(ActionEvent actionEvent){
        if(filterConditionPane.getOpacity()!=0.0){
        filterConditionPane.setOpacity(0.0);
        filterPerformAction();
        }
}

    public void init() { 
        helperButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {  
        filterImage.setImage(filterButton_entered);
        }); 
        helperButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {  
        filterImage.setImage(filterButton_normal);
        }); 
         helperButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
         filterImage.setImage(filterButton_pressed);
         if(filterConditionPane.getOpacity()==0.0){
         filterConditionPane.setOpacity(1.0);
         
         }else{
           filterConditionPane.setOpacity(0.0);
         }
        }); 
        /*
        get数据的方法
        */
      singleStockBlImpl=SingleStockBLFactory.getSingleStockInfoBLService();
      singleStockDealBlImpl=SingleStockBLFactory.getSingleStockDealBLService();
      singleStock=singleStockBlImpl.getSingleStockInfo(stockName);
      singleStockList=singleStockDealBlImpl.getSingleStockDeal(stockName, minTime);

        /**
         * 之后要改,调用singleStock
         */
       
        nameLabel.setText(stockName);
        //目前中文名还是空的
        chineseNameLabel.setText((singleStock.getChinese()==null)?"腾讯科技":singleStock.getChinese());
          
        /**
         * initialize the button
         */
        if (singleStock.isFavor() == true) {
            isFavorButton.setText("取消关注");
        } else {
            isFavorButton.setText("加关注");
        }
        
     
        tableView2.setItems(FXCollections.observableArrayList(new StockInfo2Column().set(singleStock)));
        StockInfo2Column.setKValue(key_Column);
	StockInfo2Column.setVValue(value_Column);
        
        titleTable.setItems(FXCollections.observableArrayList(new StockInfo2Column().set2(singleStock)));
        StockInfo2Column.setKValue(key_Column2);
	StockInfo2Column.setVValue(value_Column2);
      
        /*
          initialize the combobox
         */
        String[] options={"开盘价","收盘价","最高价","最低价","成交量","后复权价","市值","流通","换手率","市盈率","市净率"};
        String[] columnNameList={"open","close","high","low","volume","adj_price","marketvalue","flow","turnover","pe_ttm","pb"};
        ObservableList items= FXCollections.observableArrayList (options);
        keyWordBox.setItems(items);
        keyWordBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                keyWord=t1;
//                System.out.println("the selected is: " + t1);
                for(int i=0;i<=10;i++){
                if(keyWord.equals(options[i])){
                selectedColumnName=columnNameList[i];
                filterFlag[0]=true;
                }
                }
            }
        });

        /**
         * initialize the table
         */
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
        
         openColumn.setCellFactory(new Callback<TableColumn<Stock, Double>, TableCell<Stock, Double>>(){  
          @Override  
            public TableCell<Stock, Double> call(TableColumn<Stock, Double> arg0) {  
                   return new TableCell<Stock, Double>() {
                     ObservableValue ov1; 
                     @Override  
                      protected void updateItem(Double item,boolean empty) {        
                      super.updateItem(item, empty); 
                       if (!isEmpty()) {
                           /**只有开盘,最高,最低要换字体颜色
                            * 一般的讲,红色: 代表主动买入.绿色: 代表主动卖出. 白色: 代表中性盘
                            */
//                            ov1 = getTableColumn().getCellObservableValue(getIndex());
                            
//                            ObservableValue ov2;
//                            ov2=openColumn.getCellObservableValue(getIndex());
//                            System.out.println("the content is:"+ov1.getValue()+"and"+ov2.getValue()); 
//                            if((Double)ov1.getValue()>(Double)ov2.getValue()){//收盘时更高,上涨,红色
//                            this.setTextFill(Color.RED);  
//                            }
//                            else if((Double)ov1.getValue()<(Double)ov2.getValue()){//收盘时更低,下降,绿色
////                            getTableRow().setStyle("-fx-background-color:green");
//                             this.setTextFill(Color.GREENYELLOW);  
//                            }
                            double property=Math.random();
                            if(property>0.5){
                            this.setTextFill(Color.RED);  
                            }
                            else if(property<0.5){
                             this.setTextFill(Color.GREENYELLOW); 
                            }
                            setText(item+"");
                       }
            }             
          };  
       }  
});  
         
          highColumn.setCellFactory(new Callback<TableColumn<Stock, Double>, TableCell<Stock, Double>>(){  
          @Override  
            public TableCell<Stock, Double> call(TableColumn<Stock, Double> arg0) {  
                   return new TableCell<Stock, Double>() {
                     ObservableValue ov1; 
                     @Override  
                      protected void updateItem(Double item,boolean empty) {        
                      super.updateItem(item, empty); 
                       if (!isEmpty()) {                    
                            double property=Math.random();
                            if(property>0.5){
                            this.setTextFill(Color.RED);  
                            }
                            else if(property<0.5){
                             this.setTextFill(Color.GREENYELLOW); 
                            }
                            setText(item+"");
                       }
            }             
          };  
       }  
});
               lowColumn.setCellFactory(new Callback<TableColumn<Stock, Double>, TableCell<Stock, Double>>(){  
          @Override  
            public TableCell<Stock, Double> call(TableColumn<Stock, Double> arg0) {  
                   return new TableCell<Stock, Double>() {
                     ObservableValue ov1; 
                     @Override  
                      protected void updateItem(Double item,boolean empty) {        
                      super.updateItem(item, empty); 
                       if (!isEmpty()) {                 
                            double property=Math.random();
                            if(property>0.5){
                            this.setTextFill(Color.RED);  
                            }
                            else if(property<0.5){
                             this.setTextFill(Color.GREENYELLOW); 
                            }
                            setText(item+"");
                       }
            }             
          };  
       }  
});  
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    public class TableRowControl<T> extends TableRow<T> {  
      public TableRowControl(TableView<T> tableView) {
        super();  
        System.out.println(this.indexProperty().intValue());
//        this.setStyle("-fx-background-color:#FFFFFF");
        this.setOnMouseClicked(new EventHandler<MouseEvent>() { 
            public void handle(MouseEvent event) { 
                if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {  
                   
                }  
                
            }  
        });  
    }  
}  

}
