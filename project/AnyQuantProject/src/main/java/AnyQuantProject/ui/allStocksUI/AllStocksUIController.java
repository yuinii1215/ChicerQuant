package AnyQuantProject.ui.allStocksUI;

import AnyQuantProject.bl.factoryBL.KLineBLFactory;
import AnyQuantProject.bl.factoryBL.StockListBLFactory;
import AnyQuantProject.bl.stockListBL.StockListBLController;
import AnyQuantProject.blService.kLineBLService.StockKLineBLService;
import AnyQuantProject.blService.stockListBLService.StockListBLService;
import AnyQuantProject.dataStructure.KLineData;
import AnyQuantProject.dataStructure.KLineDataDTO;

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

import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingNode;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.CrosshairLabelGenerator;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;

/**
 *
 * @author GraceHan
 *
 */
public class AllStocksUIController implements Initializable {
    StockKLineBLService stockKLineImpl;
    private JFreeChart dayChart;
    SwingNode swingNode1;
    ChartPanel panel1;
    ScrollPane scroller1;
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

    int selectedIndex;
    public Stock singleStock;

    private static AllStocksUIController instance;
    StockListBLService stockListImplement = StockListBLFactory.getStockListBLService();

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

//        swingNode1 = new SwingNode();
//        dayChart = drawDayKLine();
//        panel1 = this.getChartPanel(dayChart);
//        swingNode1.setContent(panel1);
//
//        scroller1 = new ScrollPane();
//        scroller1.setContent(swingNode1);
//        scroller1.setFitToHeight(true);
//        scroller1.setMaxSize(250, 230);
//        scroller1.setHvalue(1);
//        chartPane.getChildren().add(scroller1);

    }

    private ChartPanel getChartPanel(JFreeChart jFreeChart) {
        ChartPanel chartPanel = new ChartPanel(jFreeChart);
        chartPanel.setMinimumSize(new Dimension(210, 232));
        chartPanel.setMaximumSize(new Dimension(210, 232));
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPopupMenu(null);
        chartPanel.setMouseZoomable(false);
        //
        CrosshairOverlay crosshairOverlay = new MyCrossOverlay();
        Crosshair xCrosshair = new Crosshair(Double.NaN);
        xCrosshair.setLabelPaint(java.awt.Color.white);
        xCrosshair.setStroke(new BasicStroke(0f));
        xCrosshair.setLabelGenerator(new CrosshairLabelGenerator() {
            @Override
            public String generateLabel(Crosshair crosshair) {
                Rectangle2D dataArea = chartPanel.getScreenDataArea();
                XYPlot plot = (XYPlot) chartPanel.getChart().getPlot();
                DateAxis xAxis = (DateAxis) plot.getDomainAxis();
                Date date1 = xAxis.getMinimumDate();
                double start = xAxis.dateToJava2D(date1, dataArea, plot.getDomainAxisEdge());
                Date date2 = xAxis.getMaximumDate();
                double end = xAxis.dateToJava2D(date2, dataArea, plot.getDomainAxisEdge());
                double value = xAxis.valueToJava2D(crosshair.getValue(), dataArea, plot.getDomainAxisEdge());
                double percent = (value - start) / (end - start);
                long time = date2.getTime() - date1.getTime();
                long ans = Math.round(time * percent);
                Date date = new Date(date1.getTime() + ans);
                return date.toString();
            }
        });
        xCrosshair.setLabelVisible(true);
        xCrosshair.setLabelAnchor(RectangleAnchor.TOP);
        xCrosshair.setPaint(java.awt.Color.white);
        xCrosshair.setLabelPaint(java.awt.Color.white);
        xCrosshair.setLabelBackgroundPaint(java.awt.Color.yellow);
        Crosshair yCrosshair = new Crosshair(Double.NaN);
        yCrosshair.setStroke(new BasicStroke(0f));
        yCrosshair.setLabelGenerator(new CrosshairLabelGenerator() {

            @Override
            public String generateLabel(Crosshair crosshair) {
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                JFreeChart chart = chartPanel.getChart();
                CombinedDomainXYPlot combinedDomainXYPlot = (CombinedDomainXYPlot) chart.getPlot();
                List<Plot> plots = combinedDomainXYPlot.getSubplots();
                double low = ((XYPlot) plots.get(0)).getRangeAxis().getLowerBound();
                double high = ((XYPlot) plots.get(0)).getRangeAxis().getUpperBound();
                double val = high - (high - crosshair.getValue()) * 1.5;
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
                Point point = new Point(event.getTrigger().getX(), event.getTrigger().getY());
                Point2D point2d = chartPanel.translateScreenToJava2D(point);
                XYPlot plot = (XYPlot) chart.getPlot();
                ValueAxis xAxis = plot.getDomainAxis();
                List<Plot> plots = ((CombinedDomainXYPlot) plot).getSubplots();
                NumberAxis yAxis = (NumberAxis) ((XYPlot) plots.get(0)).getRangeAxis();
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

    public JFreeChart drawDayKLine() {
        Calendar minTime = Calendar.getInstance();
        minTime.set(2016, 1, 1, 0, 0);
        Calendar maxTime = Calendar.getInstance();

        KLineData dayKLineData = stockKLineImpl.dayKLineChart(stockName, minTime, maxTime);
        List<KLineDataDTO> dayKLineList = dayKLineData.geKLineDataDTOs();
        String endtime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return DrawKLineChart.DayKLineChart(dayKLineList, null, null, null, stockName, TimeType.DAY, endtime);

    }

    /**
     * TO be tested
     *
     * @author QiHan
     *
     * @param <T>
     */
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
