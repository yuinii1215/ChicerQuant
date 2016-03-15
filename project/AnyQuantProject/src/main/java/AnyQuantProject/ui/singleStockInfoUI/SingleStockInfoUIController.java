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
import java.awt.GradientPaint;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
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
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.jfree.chart.ChartColor;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;

/**
 *
 * @author GraceHan
 *
 */
public class SingleStockInfoUIController implements Initializable {

    public static void getInstance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    public Scene singleStockUIScene;
    @FXML
    public TableView<Stock> table;
    @FXML
    public Label titleLabel;
    @FXML
    public Label nameLabel, chineseNameLabel;
    @FXML
    Pane filterConditionPane;
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
    AnchorPane anchorPane;

    int rowColorFlag = 0;//flag取0,-1,1三种状态,0表示不涨不跌,1表示涨了,-1表示跌了

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

    public void laterInit(String name) {
        this.stockName = name;
        minTime = Calendar.getInstance();
        this.init();
    }

    /**
     *
     * @param TO BE EDIT
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

    public JFreeChart drawDayKLine() {
        /**
         * first get the kline data from the StockKLineBLService,the later singleStockList should be replaced by the klineData
         * 
         */
        StockKLineBLService stockKLineImpl=KLineBLFactory.getStockKLineBLService();
        KLineData dayKLineData=stockKLineImpl.dayKLineChart(stockName);
        List<KLineDataDTO> dayKLineList=dayKLineData.geKLineDataDTOs();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
        double highValue = 50.0;// 设置K线数据当中的最大值
        double minValue = 10.0;// 设置K线数据当中的最小值
        double high2Value = 110.0;// 设置成交量的最大值
        double min2Value =50.0;// 设置成交量的最低值

        /**
         * 在测试阶段直接进行对singleStockList进行转换,但是之后希望能够提供相应的方法
         */
        OHLCSeries series = new OHLCSeries("");// 高开低收数据序列，股票K线图的四个数据，依次是开，高，低，收
        System.out.println(dayKLineList.size());
        for (int i = 0; i < dayKLineList.size(); i++) {
            
            int date = Integer.parseInt(dayKLineList.get(i).getDay());
            int month =  Integer.parseInt(dayKLineList.get(i).getMonth());
            int year =Integer.parseInt(dayKLineList.get(i).getYear());
            series.add(new Day(date, month, year), dayKLineList.get(i).getOpen(), dayKLineList.get(i).getHigh(), dayKLineList.get(i).getLow(), dayKLineList.get(i).getClose());
        }
        final OHLCSeriesCollection seriesCollection = new OHLCSeriesCollection();
        seriesCollection.addSeries(series);

        TimeSeries series2 = new TimeSeries("");//对应于时间成交量
        for (int i = 0; i < singleStockList.size(); i++) {
            int date = singleStockList.get(i).getDateInCalendar().get(Calendar.DAY_OF_MONTH);
            int month = singleStockList.get(i).getDateInCalendar().get(Calendar.MONTH) + 1;
            int year = singleStockList.get(i).getDateInCalendar().get(Calendar.YEAR);
            series2.add(new Day(date, month, year), singleStockList.get(i).getVolume() / 100);
        }
        // 保留成交量数据的集合
        TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();
        timeSeriesCollection.addSeries(series2);

        // 获取K线数据的最高值和最低值
        int seriesCount = seriesCollection.getSeriesCount();// 一共有多少个序列，目前为一个
        for (int i = 0; i < seriesCount; i++) {
            int itemCount = seriesCollection.getItemCount(i);// 每一个序列有多少个数据项
            for (int j = 0; j < itemCount; j++) {
                if (highValue < seriesCollection.getHighValue(i, j)) {// 取第i个序列中的第j个数据项的最大值
                    highValue = seriesCollection.getHighValue(i, j);
                }
                if (minValue > seriesCollection.getLowValue(i, j)) {// 取第i个序列中的第j个数据项的最小值
                    minValue = seriesCollection.getLowValue(i, j);
                }
            }
        }
        // 获取最高值和最低值
        int seriesCount2 = timeSeriesCollection.getSeriesCount();// 一共有多少个序列，目前为一个
        for (int i = 0; i < seriesCount2; i++) {
            int itemCount = timeSeriesCollection.getItemCount(i);// 每一个序列有多少个数据项
            for (int j = 0; j < itemCount; j++) {
                if (high2Value < timeSeriesCollection.getYValue(i, j)) {// 取第i个序列中的第j个数据项的值
                    high2Value = timeSeriesCollection.getYValue(i, j);
                }
                if (min2Value > timeSeriesCollection.getYValue(i, j)) {// 取第i个序列中的第j个数据项的值
                    min2Value = timeSeriesCollection.getYValue(i, j);
                }
            }
        }

        /**
         * 设置K线图的画图器，必须申明为final，后面要在匿名内部类里面用到
         */
        final CandlestickRenderer candlestickRender = new CandlestickRenderer();
        candlestickRender.setUseOutlinePaint(true); // 设置是否使用自定义的边框线，程序自带的边框线的颜色不符合中国股票市场的习惯
        candlestickRender.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_AVERAGE);// 设置如何对K线图的宽度进行设定
        candlestickRender.setAutoWidthGap(0.001);// 设置各个K线图之间的间隔
        
        java.awt.Color awtColorRed = java.awt.Color.getColor("#FF69B4");
        java.awt.Color awtColorGreen = java.awt.Color.GREEN;

        candlestickRender.setUpPaint(awtColorRed);// 设置股票上涨的K线图颜色
        candlestickRender.setDownPaint(awtColorGreen);// 设置股票下跌的K线图颜色

        // 设置x轴，也就是时间轴
        DateAxis x1Axis = new DateAxis();
        x1Axis.setAutoRange(false);// 设置不采用自动设置时间范围
        //x轴坐标值设置颜色
        x1Axis.setTickLabelPaint(java.awt.Color.WHITE);
        try {
            x1Axis.setRange(dateFormat.parse("2016-03-01"), dateFormat.parse("2016-03-14"));// 设置时间范围，注意时间的最大值要比已有的时间最大值要多一天
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 设置时间线显示的规则，用这个方法就摒除掉了周六和周日这些没有交易的日期(很多人都不知道有此方法)，使图形看上去连续
        x1Axis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());
//      x1Axis.setAutoTickUnitSelection(false);// 设置不采用自动选择刻度值
        x1Axis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);// 设置标记的位置
        x1Axis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());// 设置标准的时间刻度单位
        x1Axis.setTickUnit(new DateTickUnit(DateTickUnit.DAY, 1));// 设置时间刻度的间隔，一般以周为单位
        x1Axis.setDateFormatOverride(new SimpleDateFormat("MM-dd"));// 设置显示时间的格式
        NumberAxis y1Axis = new NumberAxis();// 设定y轴，就是数字轴
       
        y1Axis.setAutoRange(false);// 不不使用自动设定范围
        y1Axis.setRange(minValue * 0.9, highValue * 1.1);// 设定y轴值的范围，比最低值要低一些，比最大值要大一些，这样图形看起来会美观些
        y1Axis.setTickUnit(new NumberTickUnit((highValue * 1.1 - minValue * 0.9) / 10));// 设置刻度显示的密度
        //y轴坐标值设置颜色
        y1Axis.setTickLabelPaint(java.awt.Color.WHITE);
        XYPlot plot1 = new XYPlot(seriesCollection, x1Axis, y1Axis, candlestickRender);// 设置画图区域对象

        XYBarRenderer xyBarRender = new XYBarRenderer() {
            private static final long serialVersionUID = 1L;// 为了避免出现警告消息，特设定此值

            public Paint getItemPaint(int i, int j) {// 匿名内部类用来处理当日的成交量柱形图的颜色与K线图的颜色保持一致
                if (seriesCollection.getCloseValue(i, j) > seriesCollection.getOpenValue(i, j)) {// 收盘价高于开盘价，股票上涨，选用股票上涨的颜色
                    return candlestickRender.getUpPaint();
                } else {
                    return candlestickRender.getDownPaint();
                }
            }
        };

        xyBarRender.setMargin(0.1);// 设置柱形图之间的间隔
        NumberAxis y2Axis = new NumberAxis();// 设置Y轴，为数值,后面的设置，参考上面的y轴设置
        y2Axis.setAutoRange(false);
        y2Axis.setRange(min2Value * 0.9, high2Value * 1.1);
        y2Axis.setTickUnit(new NumberTickUnit((high2Value * 1.1 - min2Value * 0.9) / 4));
        XYPlot plot2 = new XYPlot(timeSeriesCollection, null, y2Axis, xyBarRender);// 建立第二个画图区域对象，主要此时的x轴设为了null值，因为要与第一个画图区域对象共享x轴
       
        ImageIcon icon=new ImageIcon("/images/chart_background.jpg");
        plot1.setOutlinePaint(java.awt.Color.LIGHT_GRAY);
        plot1.setBackgroundImage(icon.getImage());
        plot1.setBackgroundAlpha(0.3f);
        
        plot2.setOutlinePaint(java.awt.Color.LIGHT_GRAY);
        plot2.setBackgroundImage(icon.getImage());
        plot2.setBackgroundAlpha(0.3f);
         
        CombinedDomainXYPlot combineddomainxyplot = new CombinedDomainXYPlot(x1Axis);// 建立一个恰当的联合图形区域对象，以x轴为共享轴
        combineddomainxyplot.add(plot1, 2);// 添加图形区域对象，后面的数字是计算这个区域对象应该占据多大的区域2/3
        combineddomainxyplot.add(plot2, 1);// 添加图形区域对象，后面的数字是计算这个区域对象应该占据多大的区域1/3
        combineddomainxyplot.setGap(10);// 设置两个图形区域对象之间的间隔空间

                
        JFreeChart dayKChart = new JFreeChart(singleStock.getChinese(), JFreeChart.DEFAULT_TITLE_FONT, combineddomainxyplot, false);
        // 设置总的背景颜色
        dayKChart.setBackgroundPaint(java.awt.Color.BLACK);
//        dayKChart.setBackgroundImage(icon.getImage());
//        dayKChart.setBackgroundImageAlpha(0.3f);


        return dayKChart;
    }

    public void drawWeekKLine() {

    }

    public void drawMonthKLine() {

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

            } else {
                filterConditionPane.setOpacity(0.0);
            }
        });
        /*
        get数据的方法
         */
        singleStockBlImpl = SingleStockBLFactory.getSingleStockInfoBLService();
        singleStockDealBlImpl = SingleStockBLFactory.getSingleStockDealBLService();
        singleStock = singleStockBlImpl.getSingleStockInfo(stockName);
        singleStockList = singleStockDealBlImpl.getSingleStockDeal(stockName, minTime);

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
        String[] options = {"开盘价", "收盘价", "最高价", "最低价", "成交量", "后复权价", "市值", "流通", "换手率", "市盈率", "市净率"};
        String[] columnNameList = {"open", "close", "high", "low", "volume", "adj_price", "marketvalue", "flow", "turnover", "pe_ttm", "pb"};
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

        ChartPanel panel = new ChartPanel(drawDayKLine());
        panel.setOpaque(false);
        SwingNode swingNode = new SwingNode();
        swingNode.setContent(panel);
        tab_dayKLine.setContent(swingNode);
        /**
         * 没有实现吧JFreeChart加到JAVAFX之中
         */

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
