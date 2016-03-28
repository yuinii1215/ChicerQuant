package AnyQuantProject.ui.singleStockInfoUI;

import javafx.embed.swing.SwingNode;
import AnyQuantProject.bl.factoryBL.FavoriteBLFactory;
import AnyQuantProject.bl.factoryBL.KLineBLFactory;
import AnyQuantProject.bl.factoryBL.ListFilterBLFactory;
import AnyQuantProject.bl.factoryBL.SingleStockBLFactory;
import AnyQuantProject.bl.favoriteBL.FavoriteBLController;
import AnyQuantProject.bl.listFilterBL.ListFilterBLImpl;
import AnyQuantProject.blService.favoriteBLService.FavoriteBLService;
import AnyQuantProject.blService.kLineBLService.StockKLineBLService;
import AnyQuantProject.blService.listFilterBLService.ListFilterBLService;
import AnyQuantProject.blService.singleStockDealBLService.SingleStockDealBLService;
import AnyQuantProject.blService.singleStockInfoBLService.SingleStockInfoBLService;
import AnyQuantProject.dataStructure.KLineData;
import AnyQuantProject.dataStructure.KLineDataDTO;
import AnyQuantProject.dataStructure.OperationResult;
import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.util.constant.TimeType;
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
import java.awt.Paint;//画笔系统
import java.awt.PaintContext;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;

import org.jfree.chart.axis.*;

import javafx.scene.paint.Color;
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
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import AnyQuantProject.ui.singleStockInfoUI.StockInfo2Column;
import AnyQuantProject.util.method.DrawKLineChart;
import AnyQuantProject.util.method.MyChartMouseListener;
import AnyQuantProject.util.method.MyCrossOverlay;
import AnyQuantProject.util.method.MyKLineChartListener;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Root;

import java.awt.BasicStroke;
import java.awt.Stroke;
import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.jfree.chart.ChartPanel;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Iterator;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javax.swing.Box;
import javax.swing.BoxLayout;

import javax.swing.ImageIcon;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.labels.CrosshairLabelGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;

/**
 *
 * @author GraceHan
 *
 */
public class SingleStockInfoUIController implements Initializable {

    public static SingleStockInfoUIController getInstance() {
        return SingleStockInfoUIController.getInstance();
    }
    
    SingleStockInfoUIController singleStockInfoUIController;
    
//  public Scene singleStockUIScene;
    @FXML
    public TableView<Stock> table;
    @FXML
    public Label titleLabel;
    @FXML
    public Label nameLabel, chineseNameLabel;
    @FXML
    public Button isFavorButton;
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
    public TableView<Map.Entry<String, Double>> tableView2, titleTable;
    @FXML
    public TableColumn<Map.Entry<String, Double>, String> key_Column, key_Column2;
    @FXML
    public TableColumn<Map.Entry<String, Double>, Double> value_Column, value_Column2;
    @FXML
    public TabPane kLineTabPane;
    @FXML
    public Tab tab_dayKLine, tab_weekKLine, tab_monthKLine;
    @FXML
    public AnchorPane anchorPane;
    @FXML
    public Pane filterConditionPane;  
    @FXML
    public TextField minRange;
    @FXML
    public TextField maxRange;
    @FXML
    public ComboBox keyWordBox;
    @FXML
    public Button filterCancelButton;
    @FXML
    public Button filterPerformButton;  
    @FXML
    public ImageView filterImage;
    @FXML
    Label toLabel1,toLabel2,keyWordLabel,valueLabel,periodLabel;
    @FXML
    public DatePicker startDatePicker;
    @FXML
    public DatePicker endDatePicker;
    @FXML
    public ImageView loadImage;
    
    Group filterPaneContent;
   
    
    SwingNode swingNode1,swingNode2,swingNode3;
    ChartPanel panel1,panel2,panel3;
    
    Image filterButton_normal = new Image(getClass().getResourceAsStream("/images/filterButton_normal.png"));
    Image filterButton_pressed = new Image(getClass().getResourceAsStream("/images/filterButton_pressed.png"));
    Image filterButton_entered = new Image(getClass().getResourceAsStream("/images/filterButton_entered.png"));

    boolean[] filterFlag = {false, false, false, false, false};
    Double minFilter, maxFilter, targetFilter;
    Calendar minTime, maxTime, targetTime;
    String keyWord, selectedColumnName;
    private String stockName = null;

    SingleStockDealBLService singleStockDealBlImpl;
    ListFilterBLService listFilterBlImpl;
    FavoriteBLService favoriteBlImpl;
    OperationResult operationResult;
    SingleStockInfoBLService singleStockBlImpl;
    CalendarHelper calendarHelper = new CalendarHelper();

    public List<Stock> singleStockList = new ArrayList<Stock>();
    public Stock singleStock = new Stock();
    
    private JFreeChart dayChart,weekChart,monthChart;
            
    private  KLineData singleStockKLineDate,fiveAverageLine,tenAverageLine,thirtyAverageLine;
    private List<KLineDataDTO>  singleStockKLineDataList,fiveAverageLineDataList,tenAverageLineDataList,thirtyAverageLineDataList;

    ScrollPane scroller1,scroller2,scroller3;
    
    public void laterInit(String name) {
        this.stockName = name;
        maxTime = Calendar.getInstance();
        this.init();
    }
    
    public void endLoad(){
       loadImage.setOpacity(0);
       loadImage.setImage(null);
    }

    /**
     *do the filter job
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
        listFilterBlImpl = ListFilterBLFactory.getListFilterBLService();

        if (!filterFlag[0]) {
            return singleStockList;
        } else if (filterFlag[3] && filterFlag[4] && minTime.equals(maxTime)) {
            targetTime = minTime;
            filteredList = listFilterBlImpl.filterStocksByDateEqual(
                    currentList, targetTime);
            filterFlag[3] = false;
            filterFlag[4] = false;
        } else if (filterFlag[3] && filterFlag[4]) {
            /**
             * 默认为只可以通过区间来获得其他时间段的股票信息,不可以通过半个区间
             */
            currentList=singleStockDealBlImpl.getSingleStockDeal(stockName, minTime, maxTime);
            filteredList=currentList;
            filterFlag[3] = false;
            filterFlag[4] = false;
            dayChart=drawDayKLine();
            /*
            update the timeRange of the stock
            */
            dayChart=drawDayKLine();
            panel1 = new ChartPanel(dayChart);
            panel1.setPopupMenu(null);
            swingNode1.setContent(panel1);    
            scroller1.setContent(swingNode1);
            scroller1.setFitToHeight(true);
            tab_dayKLine.setContent(scroller1);
            
        } else if (filterFlag[3] && (!filterFlag[4])) {
            filteredList = listFilterBlImpl.filterStocksByDateGreater(
                    currentList, minTime);
            filterFlag[3] = false;
            
            /*
            update the timeRange of the stock
            */
            dayChart=drawDayKLine();
            panel1 = new ChartPanel(dayChart);
            panel1.setPopupMenu(null);
            swingNode1.setContent(panel1);    
            scroller1.setContent(swingNode1);
            scroller1.setFitToHeight(true);
            tab_dayKLine.setContent(scroller1);
        } else if (!filterFlag[3] && filterFlag[4]) {
            filteredList = listFilterBlImpl.filterStocksByDateLess(currentList,
                    maxTime);
            filterFlag[4] = false;
            
                     /*
            update the timeRange of the stock
            */
            dayChart=drawDayKLine();
            panel1 = new ChartPanel(dayChart);
            panel1.setPopupMenu(null);
            swingNode1.setContent(panel1);    
            scroller1.setContent(swingNode1);
            scroller1.setFitToHeight(true);
            tab_dayKLine.setContent(scroller1);
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
        favoriteBlImpl = FavoriteBLFactory.getFavoriteBLService();

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
    public void quitFilterPane(ActionEvent actionEvent) {
        if (filterConditionPane.getOpacity() != 0.0) {
            filterConditionPane.setOpacity(0.0);
        }
    }

    @FXML
    public void handleFilterAction(ActionEvent actionEvent) {
        if (filterConditionPane.getOpacity() != 0.0) {
            filterConditionPane.setOpacity(0.0);
            filterPerformAction();
        }
    }    
       
    
    public List<BarData> buildBars(List<KLineDataDTO> dayKLineData) {
        double previousClose = 1850;
 
        final List<BarData> bars = new ArrayList<>();
        Calendar calendar=Calendar.getInstance();
        calendar.set(2006, 1, 1);
        
        GregorianCalendar now = new GregorianCalendar();
        now.setTimeInMillis(calendar.getTimeInMillis());
        
        for (int i = 0; i < 200; i++) {     
            int year=Integer.parseInt(dayKLineData.get(i).getYear());
            int month=Integer.parseInt(dayKLineData.get(i).getMonth());
            int day=Integer.parseInt(dayKLineData.get(i).getDay());
            now.set(year, month, day);
            double open = dayKLineData.get(i).getOpen();
            double close = dayKLineData.get(i).getClose();
            double high =dayKLineData.get(i).getHigh();
            double low = dayKLineData.get(i).getLow();
            int volume=dayKLineData.get(i).getVolume();           
            BarData bar = new BarData((GregorianCalendar)now.clone(), open, high, low, close, volume);
            now.add((GregorianCalendar.DAY_OF_MONTH), 1);
            bars.add(bar);
        }
        return bars;
    }

    public JFreeChart drawDayKLine(){
        LineChart();
        StockKLineBLService stockKLineImpl=KLineBLFactory.getStockKLineBLService();
        KLineData dayKLineData=stockKLineImpl.dayKLineChart(stockName,minTime,maxTime);
        List<KLineDataDTO> dayKLineList=dayKLineData.geKLineDataDTOs();
        String endtime=null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(maxTime==null){
             endtime=null;
        }else{
             endtime=sdf.format(maxTime.getTime());
        }
    	return  DrawKLineChart.DayKLineChart (dayKLineList,fiveAverageLineDataList,tenAverageLineDataList,thirtyAverageLineDataList,stockName,TimeType.DAY,endtime);

    }

    public JFreeChart drawWeekKLine() {      
        LineChart();
        StockKLineBLService stockKLineImpl=KLineBLFactory.getStockKLineBLService();
        KLineData weekKLineData=stockKLineImpl.weekKLineChart(stockName);
        List<KLineDataDTO> weekKLineList=weekKLineData.geKLineDataDTOs();
        String endtime=null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(maxTime==null){
             endtime=null;
        }else{
             endtime=sdf.format(maxTime.getTime());
        }
    	return  DrawKLineChart.DayKLineChart (weekKLineList,fiveAverageLineDataList,tenAverageLineDataList,thirtyAverageLineDataList,stockName,TimeType.WEEK,endtime);
    }

    public JFreeChart drawMonthKLine() {
        LineChart();
        StockKLineBLService stockKLineImpl=KLineBLFactory.getStockKLineBLService();
        KLineData monthKLineData=stockKLineImpl.monthKLineChart(stockName);
        List<KLineDataDTO> monthKLineList=monthKLineData.geKLineDataDTOs();
        String endtime=null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(maxTime==null){
             endtime=null;
        }else{
             endtime=sdf.format(maxTime.getTime());
        }
    	return  DrawKLineChart.DayKLineChart (monthKLineList,fiveAverageLineDataList,tenAverageLineDataList,thirtyAverageLineDataList,stockName,TimeType.MONTH,endtime);

    }
    
     public void LineChart(){
         StockKLineBLService stockKLineImpl=KLineBLFactory.getStockKLineBLService();
    	//5日线
   	 fiveAverageLine = stockKLineImpl.getDayAverageLine(stockName, minTime, maxTime, 5);
   	 fiveAverageLineDataList = fiveAverageLine.geKLineDataDTOs();
   	//10日线
   	 tenAverageLine =stockKLineImpl.getDayAverageLine(stockName, minTime, maxTime, 10);
         tenAverageLineDataList=tenAverageLine.geKLineDataDTOs();
       //30日线
   	 thirtyAverageLine= stockKLineImpl.getDayAverageLine(stockName, minTime, maxTime, 30);
         thirtyAverageLineDataList =thirtyAverageLine.geKLineDataDTOs();
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
            if (filterConditionPane.getOpacity() == 0.0) {
                filterConditionPane.setOpacity(1.0);
                filterConditionPane.setPrefSize(370, 227);
                filterConditionPane.getChildren().add(filterPaneContent);

            } else {
                filterConditionPane.setOpacity(0.0);
                filterConditionPane.setPrefSize(0, 0);
                filterConditionPane.getChildren().clear();
            }
        });
        /*
        get数据的方法
         */
        singleStockBlImpl = SingleStockBLFactory.getSingleStockInfoBLService();
        singleStockDealBlImpl = SingleStockBLFactory.getSingleStockDealBLService();
        singleStock = singleStockBlImpl.getSingleStockInfo(stockName);
        singleStockList = singleStockDealBlImpl.getSingleStockDeal(stockName, maxTime);
   
        /**
         * 之后要改,调用singleStock
         */
        nameLabel.setText(stockName);
        //目前中文名还是空的
        chineseNameLabel.setText((singleStock.getChinese() == null) ? "腾讯科技" : singleStock.getChinese());

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
        String[] options = {"时间","开盘价", "收盘价", "最高价", "最低价", "成交量", "后复权价", "市值", "流通", "换手率", "市盈率", "市净率"};
        String[] columnNameList = {"date","open", "close", "high", "low", "volume", "adj_price", "marketvalue", "flow", "turnover", "pe_ttm", "pb"};
        ObservableList items = FXCollections.observableArrayList(options);
        keyWordBox.setItems(items);
        keyWordBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                keyWord = t1;
//                System.out.println("the selected is: " + t1);
                for (int i = 0; i <= 10; i++) {
                    if (keyWord.equals(options[i])) {
                        selectedColumnName = columnNameList[i];
                        filterFlag[0] = true;
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

        openColumn.setCellFactory(new Callback<TableColumn<Stock, Double>, TableCell<Stock, Double>>() {
            @Override
            public TableCell<Stock, Double> call(TableColumn<Stock, Double> arg0) {
                return new TableCell<Stock, Double>() {
                    ObservableValue ov1;

                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if(this.getIndex()<singleStockList.size()){ 
                        if (!isEmpty()) {
                            double property = Math.random();
                            if (property > 0.5) {
                                this.setTextFill(Color.RED);
                            } else if (property < 0.5) {
                                this.setTextFill(Color.GREENYELLOW);
                            }
                            setText(item + "");
                        }
                        }
                    }
                };
            }
        });

        highColumn.setCellFactory(new Callback<TableColumn<Stock, Double>, TableCell<Stock, Double>>() {
            @Override
            public TableCell<Stock, Double> call(TableColumn<Stock, Double> arg0) {
                return new TableCell<Stock, Double>() {
                    ObservableValue ov1;

                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty()) {
                            double property = Math.random();
                            if (property > 0.5) {
                                this.setTextFill(Color.RED);
                            } else if (property < 0.5) {
                                this.setTextFill(Color.GREENYELLOW);
                            }
                            setText(item + "");
                        }
                    }
                };
            }
        });
        lowColumn.setCellFactory(new Callback<TableColumn<Stock, Double>, TableCell<Stock, Double>>() {
            @Override
            public TableCell<Stock, Double> call(TableColumn<Stock, Double> arg0) {
                return new TableCell<Stock, Double>() {
                    ObservableValue ov1;

                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty()) {
                            double property = Math.random();
                            if (property > 0.5) {
                                this.setTextFill(Color.RED);
                            } else if (property < 0.5) {
                                this.setTextFill(Color.GREENYELLOW);
                            }
                            setText(item + "");
                        }
                    }
                };
            }
        });

        filterPaneContent=new Group();
        filterPaneContent.getChildren().addAll(minRange,maxRange,keyWordBox,filterCancelButton,filterPerformButton,toLabel1,toLabel2,keyWordLabel,valueLabel,periodLabel,startDatePicker,endDatePicker);
        filterConditionPane.setPrefSize(0, 0);
        
        /**
         * add the JFreechart into the tabpane
             */
        
        swingNode1 = new SwingNode();
        dayChart=drawDayKLine();
        panel1 = this.getChartPanel(dayChart);
        swingNode1.setContent(panel1);    
        scroller1=new ScrollPane();
        scroller1.setContent(swingNode1);
        
        scroller1.setFitToHeight(true);       
        tab_dayKLine.setContent(scroller1);

        swingNode2 = new SwingNode();
        weekChart=drawWeekKLine();
        panel2 = this.getChartPanel(weekChart);
        swingNode2.setContent(panel2);    
        scroller2=new ScrollPane();
        scroller2.setContent(swingNode2);
        scroller2.setFitToHeight(true);
        tab_weekKLine.setContent(scroller2);
       
        swingNode3 = new SwingNode();
        monthChart=drawMonthKLine();
        panel3 = this.getChartPanel(monthChart);
        swingNode3.setContent(panel3);    
        scroller3=new ScrollPane();
        scroller3.setContent(swingNode3);
        scroller3.setFitToHeight(true);
        tab_monthKLine.setContent(scroller3);

        
    }
    private ChartPanel getChartPanel(JFreeChart jFreeChart){
    	ChartPanel chartPanel=new ChartPanel(jFreeChart);
    	chartPanel.setMaximumSize(new Dimension(1000, 600));
    	chartPanel.setMouseWheelEnabled(true);
    	chartPanel.setPopupMenu(null);
    	chartPanel.setMouseZoomable(false);
    	//
    	CrosshairOverlay crosshairOverlay=new MyCrossOverlay();
    	Crosshair xCrosshair=new Crosshair(Double.NaN);
    	xCrosshair.setLabelPaint(java.awt.Color.white);
    	xCrosshair.setStroke(new BasicStroke(0f));
    	xCrosshair.setLabelGenerator(new CrosshairLabelGenerator() {
			@Override
			public String generateLabel(Crosshair crosshair) {
				Rectangle2D dataArea=chartPanel.getScreenDataArea();
				XYPlot plot = (XYPlot) chartPanel.getChart().getPlot();
		        DateAxis xAxis=(DateAxis) plot.getDomainAxis();
		        Date date1=xAxis.getMinimumDate();
		        double start=xAxis.dateToJava2D(date1, dataArea, plot.getDomainAxisEdge());
		        Date date2=xAxis.getMaximumDate();
		        double end=xAxis.dateToJava2D(date2, dataArea, plot.getDomainAxisEdge());
		        double value=xAxis.valueToJava2D(crosshair.getValue(), dataArea, plot.getDomainAxisEdge());
		        double percent=(value-start)/(end-start);
		        long time=date2.getTime()-date1.getTime();
		        long ans=Math.round(time*percent);
		        Date date=new Date(date1.getTime()+ans);
				return date.toString();
			}
		});
    	xCrosshair.setLabelVisible(true);
    	xCrosshair.setLabelAnchor(RectangleAnchor.TOP);
    	xCrosshair.setPaint(java.awt.Color.white);
    	xCrosshair.setLabelPaint(java.awt.Color.white);
    	xCrosshair.setLabelBackgroundPaint(java.awt.Color.yellow);
    	Crosshair yCrosshair=new Crosshair(Double.NaN);
    	yCrosshair.setStroke(new BasicStroke(0f));
    	yCrosshair.setLabelGenerator(new CrosshairLabelGenerator() {
			
			@Override
			public String generateLabel(Crosshair crosshair) {
				DecimalFormat decimalFormat=new DecimalFormat("#.00");
				JFreeChart chart=chartPanel.getChart();
				CombinedDomainXYPlot combinedDomainXYPlot=(CombinedDomainXYPlot) chart.getPlot();
				List<Plot> plots=combinedDomainXYPlot.getSubplots();
				double low=((XYPlot)plots.get(0)).getRangeAxis().getLowerBound();
				double high=((XYPlot)plots.get(0)).getRangeAxis().getUpperBound();
				double val=high-(high-crosshair.getValue())*1.5;
				return decimalFormat.format(val);
		        
			}
		});
    	yCrosshair.setLabelVisible(true);
//    	yCrosshair.setLabelAnchor(RectangleAnchor.RIGHT);
    	yCrosshair.setPaint(java.awt.Color.white);
    	yCrosshair.setLabelPaint(java.awt.Color.white);
    	yCrosshair.setLabelBackgroundPaint(java.awt.Color.yellow);
    	crosshairOverlay.addDomainCrosshair(xCrosshair);
    	crosshairOverlay.addRangeCrosshair(yCrosshair);
    	chartPanel.addOverlay(crosshairOverlay);
    	
    	chartPanel.addChartMouseListener(new ChartMouseListener() {
			
			@Override
			public void chartMouseMoved(ChartMouseEvent event) {
				 	Rectangle2D dataArea = chartPanel.getScreenDataArea();
			        JFreeChart chart = event.getChart();
			        Point point=new Point(event.getTrigger().getX(), event.getTrigger().getY());
			        Point2D point2d=chartPanel.translateScreenToJava2D(point);
			        XYPlot plot = (XYPlot) chart.getPlot();
			        ValueAxis xAxis = plot.getDomainAxis();
			        List<Plot> plots= ((CombinedDomainXYPlot)plot).getSubplots();
			        NumberAxis yAxis=(NumberAxis) ((XYPlot)plots.get(0)).getRangeAxis();
			        double x = xAxis.java2DToValue(point2d.getX(), dataArea, 
			                RectangleEdge.BOTTOM);
			        
			        double y = yAxis.java2DToValue(point2d.getY(), dataArea, RectangleEdge.LEFT);
			        xCrosshair.setValue(x);
			        yCrosshair.setValue(y);
			}
			
			@Override
			public void chartMouseClicked(ChartMouseEvent event) {
			}
		});
    	
    	return chartPanel;
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

    }

    public class TableRowControl<T> extends TableRow<T> {

        public TableRowControl(TableView<T> tableView) {
            super();
            System.out.println(this.indexProperty().intValue());
            this.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {

                    }

                }
            });
        }
    }

}
