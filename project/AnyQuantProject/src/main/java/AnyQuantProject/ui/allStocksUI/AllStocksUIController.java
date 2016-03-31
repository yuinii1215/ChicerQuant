package AnyQuantProject.ui.allStocksUI;

import AnyQuantProject.bl.factoryBL.KLineBLFactory;
import AnyQuantProject.bl.factoryBL.LineChartBLFactory;
import AnyQuantProject.bl.factoryBL.StockListBLFactory;
import AnyQuantProject.bl.stockListBL.StockListBLController;
import AnyQuantProject.blService.kLineBLService.CalculateLineBLService;
import AnyQuantProject.blService.kLineBLService.StockKLineBLService;
import AnyQuantProject.blService.stockListBLService.StockListBLService;
import AnyQuantProject.dataStructure.KLineData;
import AnyQuantProject.dataStructure.KLineDataDTO;
import AnyQuantProject.dataStructure.LineChartData;

import java.io.IOException;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.starter.Main;
import AnyQuantProject.ui.controllerUI.MainPageController;
import AnyQuantProject.ui.favoriteUI.FavoriteUIController;
import AnyQuantProject.ui.graphUI.LineChartFactory;
import AnyQuantProject.ui.singleStockInfoUI.StockInfo2Column;
import AnyQuantProject.util.constant.TimeType;
import AnyQuantProject.util.method.DrawKLineChart;
import AnyQuantProject.util.method.MyCrossOverlay;
import AnyQuantProject.util.method.SimpleDoubleProperty;
import AnyQuantProject.util.method.SimpleIntegerProperty;
import AnyQuantProject.util.method.SimpleLongProperty;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TableRow;
import javafx.util.Callback;

//import AnyQuantProject.util.method.TableRowControl;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.chart.LineChart;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView.TableViewSelectionModel;
import static javafx.scene.input.KeyCode.T;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.NumberAxis;
import javafx.util.Duration;

/**
 *
 * @author GraceHan
 *
 */
public class AllStocksUIController implements Initializable {

    StockKLineBLService stockKLineImpl;
    Label titleLabel;
    Scene allStockUIScene;
    String stockName;
    List<Stock> allStocksList = new ArrayList<Stock>();
    String targetStockName;
    @FXML
    public Pane chartPane;
    @FXML
    public Label nameLabel;
    @FXML
    public TableColumn<Stock, String> chineseColumn;
    @FXML
    public TableColumn<Stock, String> nameColumn;
    @FXML
    public TableView<Stock> allStocksTableView;
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
    public TableView<Stock> table;
    @FXML
    public TableView<Map.Entry<String, Double>> titleTable, valueTable;
    @FXML
    public TableColumn<Map.Entry<String, Double>, String> key_column, key_column2;
    @FXML
    public TableColumn<Map.Entry<String, Double>, Double> value_column, value_column2;

    public LineChart<String, Number> stockLineChart;
    int selectedIndex;
    public Stock singleStock;

    private static AllStocksUIController instance;
    StockListBLService stockListImplement = StockListBLFactory.getStockListBLService();
    CalculateLineBLService calculateLineImplement=LineChartBLFactory.getCalculateLineBL();

    private XYChart.Series<Number, Number> hourDataSeries;
    private XYChart.Series<Number, Number> minuteDataSeries;
    private NumberAxis xAxis;
    private Timeline animation;

    private double hours = 0;
    private double minutes = 0;
    private double timeInHours = 0;
    private double prevY = 10;
    private double y = 10;
    LineChartData previewLineChart;

    public static AllStocksUIController getInstance() {
        System.out.println("here is the instance of AllStocksUIController ");
        return instance == null ? (instance = new AllStocksUIController()) : instance;
    }

    /**
     * Initializes the controller class.
     */
    public AllStocksUIController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        init();
    }

    public void init() {

        allStocksList = stockListImplement.getAllStocks();
        stockKLineImpl = KLineBLFactory.getStockKLineBLService();
        table.setItems(FXCollections.observableArrayList(allStocksList));
        table.setRowFactory(new Callback<TableView<Stock>, TableRow<Stock>>() {
            @Override
            public TableRow<Stock> call(TableView<Stock> table) {
                // TODO Auto-generated method stub

                return new TableRowControl(table);
            }
        });

        singleStock = allStocksList.get(0);
        stockName = singleStock.getName();
        nameLabel.setText(singleStock.getChinese());
        valueTable.setItems(FXCollections.observableArrayList(new StockInfo2Column().set(singleStock)));
        StockInfo2Column.setKValue(key_column);
        StockInfo2Column.setVValue(value_column);
        key_column.setStyle("-fx-font-size: 15pt;");
        value_column.setStyle("-fx-font-size: 15pt;");

        titleTable.setItems(FXCollections.observableArrayList(new StockInfo2Column().set2(singleStock)));
        StockInfo2Column.setKValue(key_column2);
        StockInfo2Column.setVValue(value_column2);
        key_column2.setStyle("-fx-font-size: 15pt;");
        value_column2.setStyle("-fx-font-size: 15pt;");

//         table.setSelectionModel(allStocksList.get(0));
        /**
         * initialize the tabel columns
         */
        chineseColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getChinese()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getName()));
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

        
       // final CategoryAxis xAxis = new CategoryAxis();
       //final NumberAxis yAxis = new NumberAxis();
        previewLineChart=LineChartBLFactory.getCalculateLineBL().drawPreview(stockName);     
        
        CategoryAxis xAxis=new CategoryAxis();
        NumberAxis yAxis =new NumberAxis();
        stockLineChart = new LineChart<>(xAxis, yAxis);
        stockLineChart.setTitle("2016指数");
        
        XYChart.Series series1=new XYChart.Series();
//        XYChart.Series<String,Number> series2=previewLineChart.getSeries();
//        series1.setName("Portfolio 1");  
//        for(int j=0;j<series2.size();j++){
//        series1.setData(series2.get(j));
//        }
        
        
        series1.getData().add(new XYChart.Data("Jan", 23));
        series1.getData().add(new XYChart.Data("Feb", 14));
        series1.getData().add(new XYChart.Data("Mar", 15));
        series1.getData().add(new XYChart.Data("Apr", 24));
        series1.getData().add(new XYChart.Data("May", 34));
        series1.getData().add(new XYChart.Data("Jun", 36));
        series1.getData().add(new XYChart.Data("Jul", 22));
        series1.getData().add(new XYChart.Data("Aug", 45));
        series1.getData().add(new XYChart.Data("Sep", 43));
        series1.getData().add(new XYChart.Data("Oct", 17));
        series1.getData().add(new XYChart.Data("Nov", 29));
        series1.getData().add(new XYChart.Data("Dec", 25));

        stockLineChart.getData().addAll(series1);
        stockLineChart.setLayoutX(0);
        stockLineChart.setLayoutY(0);
        stockLineChart.setPrefSize(205, 210);

//        chartPane.getChildren().add(stockLineChart);
       
        animation = new Timeline();
        animation.getKeyFrames().add(new KeyFrame(Duration.millis(1000 / 60), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for (int count = 0; count < 6; count++) {
                    nextTime();
                    plotTime();
                }
            }
        }));
        animation.setCycleCount(Animation.INDEFINITE);
        animation.setCycleCount(100);
        chartPane.getChildren().add(createChart());
        play();
    }

    /**
     * Copyright (c) 2008, 2012 Oracle and/or its affiliates. All rights
     * reserved. Use is subject to license terms.
     */
    /**
     * A simulated stock line chart.
     *
     * @see javafx.scene.chart.Chart
     * @see javafx.scene.chart.LineChart
     * @see javafx.scene.chart.NumberAxis
     * @see javafx.scene.chart.XYChart
     */
    protected LineChart<Number, Number> createChart() {
        xAxis = new NumberAxis(0, 24, 3);
        final NumberAxis yAxis = new NumberAxis(0, 100, 10);
        final LineChart<Number, Number> lc = new LineChart<Number, Number>(xAxis, yAxis);
        // setup chart
        lc.setId("lineStockDemo");
        lc.setCreateSymbols(false);
        lc.setAnimated(false);
        lc.setLegendVisible(false);
        lc.setTitle("ACME Company Stock");
        xAxis.setLabel("Time");
        xAxis.setForceZeroInRange(false);
        yAxis.setLabel("Share Price");
        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis, "$", null));
        // add starting data
        hourDataSeries = new XYChart.Series<Number, Number>();
        hourDataSeries.setName("Hourly Data");
        minuteDataSeries = new XYChart.Series<Number, Number>();
        minuteDataSeries.setName("Minute Data");
        // create some starting data
        hourDataSeries.getData().add(new XYChart.Data<Number, Number>(timeInHours, prevY));
        minuteDataSeries.getData().add(new XYChart.Data<Number, Number>(timeInHours, prevY));
        for (double m = 0; m < (60); m++) {
            nextTime();
            plotTime();
        }
        lc.getData().add(minuteDataSeries);
        lc.getData().add(hourDataSeries);
        lc.setPrefSize(200, 200);
        return lc;
    }

    private void nextTime() {
        if (minutes == 59) {
            hours++;
            minutes = 0;
        } else {
            minutes++;
        }
        timeInHours = hours + ((1d / 60d) * minutes);
    }

    private void plotTime() {
        if ((timeInHours % 1) == 0) {
            // change of hour
            double oldY = y;
            y = prevY - 10 + (Math.random() * 20);
            prevY = oldY;
            while (y < 10 || y > 90) {
                y = y - 10 + (Math.random() * 20);
            }
            hourDataSeries.getData().add(new XYChart.Data<Number, Number>(timeInHours, prevY));
            // after 25hours delete old data
            if (timeInHours > 25) {
                hourDataSeries.getData().remove(0);
            }
            // every hour after 24 move range 1 hour
            if (timeInHours > 24) {
                xAxis.setLowerBound(xAxis.getLowerBound() + 1);
                xAxis.setUpperBound(xAxis.getUpperBound() + 1);
            }
        }
        double min = (timeInHours % 1);
        double randomPickVariance = Math.random();
        if (randomPickVariance < 0.3) {
            double minY = prevY + ((y - prevY) * min) - 4 + (Math.random() * 8);
            minuteDataSeries.getData().add(new XYChart.Data<Number, Number>(timeInHours, minY));
        } else if (randomPickVariance < 0.7) {
            double minY = prevY + ((y - prevY) * min) - 6 + (Math.random() * 12);
            minuteDataSeries.getData().add(new XYChart.Data<Number, Number>(timeInHours, minY));
        } else if (randomPickVariance < 0.95) {
            double minY = prevY + ((y - prevY) * min) - 10 + (Math.random() * 20);
            minuteDataSeries.getData().add(new XYChart.Data<Number, Number>(timeInHours, minY));
        } else {
            double minY = prevY + ((y - prevY) * min) - 15 + (Math.random() * 30);
            minuteDataSeries.getData().add(new XYChart.Data<Number, Number>(timeInHours, minY));
        }
        // after 25hours delete old data
        if (timeInHours > 25) {
            minuteDataSeries.getData().remove(0);
        }
    }

    public void play() {
        animation.play();
    }

    public void stop() {
        animation.pause();
    }

    public class TableRowControl<T> extends TableRow<T> {

        public TableRowControl(TableView<T> tableView) {
            super();

            this.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1) {
                        selectedIndex = TableRowControl.this.getIndex();
                        singleStock = allStocksList.get(selectedIndex);

                        stockName = nameColumn.getCellData(selectedIndex);
                        System.out.println("......Enter :" + stockName + " panel......");

                        nameLabel.setText(chineseColumn.getCellData(selectedIndex));

                        valueTable.setItems(FXCollections.observableArrayList(new StockInfo2Column().set(singleStock)));
                        StockInfo2Column.setKValue(key_column);
                        StockInfo2Column.setVValue(value_column);

                        titleTable.setItems(FXCollections.observableArrayList(new StockInfo2Column().set2(singleStock)));
                        StockInfo2Column.setKValue(key_column2);
                        StockInfo2Column.setVValue(value_column2);

//                        dayChart = drawDayKLine();
//                        panel1 = getChartPanel(dayChart);
//                        swingNode1.setContent(panel1);
//
//                        
//                        scroller1.setContent(swingNode1);
//                        scroller1.setFitToHeight(true);
//                        scroller1.setMaxSize(250, 230);
//                        scroller1.setHvalue(1);
//                        chartPane.getChildren().clear();
//                        chartPane.getChildren().add(scroller1);
                    }
                    if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                        selectedIndex = TableRowControl.this.getIndex();
                        stockName = nameColumn.getCellData(selectedIndex);
                        System.out.println("......Enter :" + stockName + " panel......");
                        //     MainPageController.getInstance().setPanel(Main.singleStockInfoPanel, "打开单只股票下部信息界面...");
                        Main.enterSingleStockInfoScene(stockName);

                        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
                        ScheduledFuture future = service.schedule(new Callable() {
                            public String call() {
                                System.out.print("time is up");
                                Main.endSingle();
                                return "taskcancelled!";
                            }
                        }, 4, TimeUnit.SECONDS);

                        service.shutdown();

                    }

                }
            });
        }
    }
}
