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
    CalculateLineBLService calculateLineImplement = LineChartBLFactory.getCalculateLineBL();


    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private XYChart.Series<String, Number> series;

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
        key_column.setStyle("-fx-font-size: 13pt;");
        value_column.setStyle("-fx-font-size: 13pt;");

        titleTable.setItems(FXCollections.observableArrayList(new StockInfo2Column().set2(singleStock)));
        StockInfo2Column.setKValue(key_column2);
        StockInfo2Column.setVValue(value_column2);
        key_column2.setStyle("-fx-font-size: 13pt;");
        value_column2.setStyle("-fx-font-size: 13pt;");


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

        previewLineChart = LineChartBLFactory.getCalculateLineBL().drawPreview(stockName);
        xAxis = new CategoryAxis();
        xAxis = (CategoryAxis) previewLineChart.getxAxis();
        xAxis.setTickLabelFill(Color.WHITE);
        yAxis = new NumberAxis();
        yAxis = (NumberAxis) previewLineChart.getyAxis();
        yAxis.setTickLabelFill(Color.WHITE);
        yAxis.setLowerBound(yAxis.getLowerBound() - 1);
        yAxis.setUpperBound(yAxis.getUpperBound() + 1);
        yAxis.setTickUnit((yAxis.getUpperBound() - yAxis.getLowerBound()) / 10);
        stockLineChart = (LineChart<String, Number>) new LineChart<>(xAxis, yAxis);
        stockLineChart.setTitle(previewLineChart.getTitle());

        series = new XYChart.Series<>();
        series.setName("chart_series");
        series = (XYChart.Series<String, Number>) previewLineChart.getSeries().get(0);

        stockLineChart.getData().add(series);
        stockLineChart.setPrefSize(190, 200);

//       stockLineChart.getData().add((XYChart.Series<String, Number>) previewLineChart.getSeries().get(0));
        chartPane.getChildren().add(stockLineChart);

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

                        previewLineChart = LineChartBLFactory.getCalculateLineBL().drawPreview(stockName);
                        xAxis = (CategoryAxis) previewLineChart.getxAxis();
                        yAxis = (NumberAxis) previewLineChart.getyAxis();
                        yAxis.setLowerBound(yAxis.getLowerBound() - 1);
                        yAxis.setUpperBound(yAxis.getUpperBound() + 1);
                        yAxis.setTickUnit((yAxis.getUpperBound() - yAxis.getLowerBound()) / 10);
                        stockLineChart = (LineChart<String, Number>) new LineChart<>(xAxis, yAxis);
                        stockLineChart.setTitle(previewLineChart.getTitle());

                        series = new XYChart.Series<>();
                        series.setName("chart_series");
                        series = (XYChart.Series<String, Number>) previewLineChart.getSeries().get(0);
                        
                        stockLineChart.getData().add(series);
                        stockLineChart.setPrefSize(200, 200);
                        chartPane.getChildren().clear();
                        chartPane.getChildren().add(stockLineChart);
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
