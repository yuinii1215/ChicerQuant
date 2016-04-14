/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnyQuantProject.ui.stasticsInfoUI;

import AnyQuantProject.bl.calculateBL.CalculateLineBLImpl;
import AnyQuantProject.bl.factoryBL.KLineBLFactory;
import AnyQuantProject.blService.kLineBLService.CalculateLineBLService;
import AnyQuantProject.blService.kLineBLService.StockKLineBLService;
import AnyQuantProject.dataStructure.KLineData;
import AnyQuantProject.dataStructure.KLineDataDTO;
import AnyQuantProject.ui.singleStockInfoUI.CalcuLineType;
import AnyQuantProject.util.constant.TimeType;
import AnyQuantProject.util.method.DrawKLineChart;
import AnyQuantProject.util.method.MyCrossOverlay;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
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
 * FXML Controller class
 *
 * @author GraceHan
 */
public class StasticsInfoController implements Initializable {
    
    @FXML
    Label macdTitle, kdjTitle, rsiTitle, biasTitle;
    @FXML
    TabPane macdKLineTabPane, rsiKLineTabPane, biasKLineTabPane, kdjKLineTabPane;
    @FXML
    TextArea macdText1, macdText2, macdText3, rsiText1, rsiText2, rsiText3, biasText1, biasText2, biasText3, kdjText1, kdjText2, kdjText3;
    @FXML
    Button macdMathButton, rsiMathButton, biasMathButton, kdjMathButton;
    @FXML
    Tab tab_macdDayKLine, tab_rsiDayKLine, tab_biasDayKLine, tab_kdjDayKLine;
    @FXML
    Tooltip macdMathTip, rsiMathTip, biasMathTip, kdjMathTip;
    SwingNode swingNode1, swingNode2, swingNode3, swingNode4;
    ScrollPane scroller1, scroller2, scroller3, scroller4;
    ChartPanel panel1, panel2, panel3, panel4;
    private JFreeChart macdDayChart, rsiDayChart, biasDayChart, kdjDayChart;
    private KLineData singleStockKLineDate, fiveAverageLine, tenAverageLine, thirtyAverageLine;
    private List<KLineDataDTO> singleStockKLineDataList, fiveAverageLineDataList, tenAverageLineDataList, thirtyAverageLineDataList;
    
    CalcuLineType calcuLineType;
    KLineData dayKLineData;
    List<KLineDataDTO> dayKLineList;
    Calendar minTime, maxTime;
    StockKLineBLService stockKLineImpl = KLineBLFactory.getStockKLineBLService();
    String stockName;
    List<AnyQuantProject.dataStructure.Cell> calcuList1, calcuList2, calcuList3;//MACD:DIF,DEA,MACD(bar)或者KDJ:K, D, J:
    List<AnyQuantProject.dataStructure.Cell>[] macdLineData, kdjLineData, rsiLineData, biasLineData;
    CalculateLineBLService calculateLineBlImpl;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Initializes the controller class.
     */
    private static StasticsInfoController instance;
    
    public static StasticsInfoController getInstance() {
        System.out.println("here is the instance of stasticsUIController ");
        return instance == null ? (instance = new StasticsInfoController()) : instance;
    }
    
    public StasticsInfoController() {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
        init();
    }
    
    public void init() {
        
        minTime = Calendar.getInstance();
        minTime.set(2016, 0, 1, 0, 0);
        maxTime = Calendar.getInstance();
        
        swingNode1 = new SwingNode();
        scroller1 = new ScrollPane();
        stockName = "sh601288";
        calcuLineType = CalcuLineType.TYPE_MACD;
        macdDayChart = drawDayKLine(stockName, calcuLineType);
        addChart2Tab(macdDayChart, panel1, swingNode1, scroller1, tab_macdDayKLine);
        
        swingNode2 = new SwingNode();
        scroller2 = new ScrollPane();
        stockName = "sh600030";
        calcuLineType = CalcuLineType.TYPE_RSI;
        rsiDayChart = drawDayKLine(stockName, calcuLineType);
        addChart2Tab(rsiDayChart, panel2, swingNode2, scroller2, tab_rsiDayKLine);
        
        swingNode3 = new SwingNode();
        scroller3 = new ScrollPane();
//        stockName="c";
        calcuLineType = CalcuLineType.TYPE_BIAS;
        biasDayChart = drawDayKLine(stockName, calcuLineType);
        addChart2Tab(biasDayChart, panel3, swingNode3, scroller3, tab_biasDayKLine);
        
        swingNode4 = new SwingNode();
        scroller4 = new ScrollPane();
//        stockName="d";
        calcuLineType = CalcuLineType.TYPE_KDJ;
        kdjDayChart = drawDayKLine(stockName, calcuLineType);
        addChart2Tab(kdjDayChart, panel4, swingNode4, scroller4, tab_kdjDayKLine);
        setText();
        
    }
    
    public JFreeChart drawDayKLine(String stockID, CalcuLineType calcuLineType) {
        CalcuLineChart(calcuLineType);
        dayLineChart(stockID);
        
        dayKLineData = stockKLineImpl.dayKLineChart(stockID, minTime, maxTime);
        dayKLineList = dayKLineData.geKLineDataDTOs();
        String endtime;
        if (maxTime == null) {
            endtime = null;
        } else {
            endtime = sdf.format(maxTime.getTime());
        }
        return DrawKLineChart.DayKLineChart(dayKLineList, calcuList1, calcuList2, calcuList3, calcuLineType, fiveAverageLineDataList, tenAverageLineDataList, thirtyAverageLineDataList, stockName, TimeType.DAY, endtime);
        
    }
    
    private void addChart2Tab(JFreeChart chart, ChartPanel panel, SwingNode swingNode, ScrollPane scroller, Tab tab) {
//        swingNode=new SwingNode();
//        scroller = new ScrollPane();
        panel = this.getChartPanel(chart);
        swingNode.setContent(panel);
        scroller.setContent(swingNode);
//        scroller.setFitToHeight(true);
        tab.setContent(scroller);
    }
    
    public void CalcuLineChart(CalcuLineType calcuLineType) {
        /**
         * day, week, month应该调用不同的MACD线,暂时还没有实现
         */
        calculateLineBlImpl = new CalculateLineBLImpl();
        if (calcuLineType == CalcuLineType.TYPE_MACD) {
            macdLineData = calculateLineBlImpl.drawMACD(stockName, minTime, maxTime).cells;
            //macdLineData has three list,each list is the dataList for a singleStock in the area   
            calcuList1 = macdLineData[0]; //DIF
            calcuList2 = macdLineData[1]; //DEA
            calcuList3 = macdLineData[2]; //MACD
        } else if (calcuLineType == CalcuLineType.TYPE_KDJ) {
            // TODO: 16/4/10
            kdjLineData = calculateLineBlImpl.drawKDJ(stockName, minTime, maxTime).cells;
            calcuList1 = kdjLineData[0];//"K"Line
            calcuList2 = kdjLineData[1];//"D"Line
            calcuList3 = kdjLineData[2];//"J"Line      
        } else if (calcuLineType == CalcuLineType.TYPE_RSI) {
            rsiLineData = calculateLineBlImpl.drawRSI(stockName, minTime, maxTime).cells;
            calcuList1 = rsiLineData[0];//"RSI"Line
            calcuList2 = rsiLineData[1];//"RSI2"Line
            calcuList3 = rsiLineData[2];//"RSI3"Line    
        } else if (calcuLineType == CalcuLineType.TYPE_BIAS) {
            biasLineData = calculateLineBlImpl.drawBIAS(stockName, minTime, maxTime).cells;
            calcuList1 = biasLineData[0];//"6"Line
            calcuList2 = biasLineData[1];//"12"Line
            calcuList3 = biasLineData[2];//"24"Line            
        }
    }
    
    public void dayLineChart(String stockID) {
//         StockKLineBLService stockKLineImpl=KLineBLFactory.getStockKLineBLService();
        //5日线   
        fiveAverageLine = stockKLineImpl.getDayAverageLine(stockID, minTime, maxTime, 5);
        
        fiveAverageLineDataList = fiveAverageLine.geKLineDataDTOs();
        //10日线
        tenAverageLine = stockKLineImpl.getDayAverageLine(stockName, minTime, maxTime, 10);
        tenAverageLineDataList = tenAverageLine.geKLineDataDTOs();
        //30日线
        thirtyAverageLine = stockKLineImpl.getDayAverageLine(stockName, minTime, maxTime, 30);
        thirtyAverageLineDataList = thirtyAverageLine.geKLineDataDTOs();
    }
    
    private ChartPanel getChartPanel(JFreeChart jFreeChart) {
        ChartPanel chartPanel = new ChartPanel(jFreeChart);
        chartPanel.setMinimumSize(new Dimension(800, 500));
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
    
    private void setText() {
        macdMathTip.setText("DIF:EMA(CLOSE,12)-EMA(CLOSE,26);\n"
                + "DEA:EMA(DIF,9);\n"
                + "MACD柱:(DIF-DEA)*2,COLORSTICK;");
        rsiMathTip.setText("RSI=100*RS/(1+RS)\n"+"注:其中RS表示14天内收盘价上涨数之和的平均值/14天内收盘价价下跌数之和的平均值");
        macdText1.setText("我何时该买入?\n" + "DIF由下向上突破 DEA,MACD 值由负变正");
        macdText2.setText("我何时该卖出?\n" + "DIF由上向下突破 DEA,MACD 值由正变负");
        macdText3.setText("此图表何时适用?\n" + "适用于中线趋势分析，建议与KDJ线一起使用");
        
        rsiText1.setText("我何时该买入?\n"
                + "1.W形或头肩底 当RSI在低位或底部形成W形或头肩底形时，属最佳买入时期。\n"
                + "2.当RSI运行到20以下时为超卖区，易产生返弹。\n"
                + "3.黄金交叉:当短天期的RSI向上穿越长天期的RSI时为买入信号。\n"
                + "4.牛背离:当股指或股价一波比一波低，而RSI却一波比一波高，叫牛背离，此时股指或股价很容易反转上涨.");
        rsiText2.setText("我何时该卖出?\n"
                + "1.形态M形、头肩顶形 当RSI在高位或顶部形成M形或头肩顶形时，属最佳卖出时机。\n"
                + "2.当RSI运行到80以上时进入超买区，股价很容易下跌。\n"
                + "3.顶背离当股指或股价创新高时，而RSI却不创新高，叫顶背离，将是最佳卖出时机。\n"
                + "4.当短天期RSI下穿长天期RSI时，叫死亡交叉，为卖出信号。");
        rsiText3.setText("此图表何时适用?\n"
                + "稳健型的投资可以使用交叉买入或卖出，SI短线自下而上穿越长期参数即RSI指标长线时，是一个买点，此时介入至少应当有10％左右的获利\n"
                + "空间；反之则是卖出信号，损失空间也将有10％左右；两条指标线产生"
                + "交叉后如果出现两次以上的迎合交叉，价格趋势运行按照原来方向加速"
                + "的可能性为70％；对短线而言，长线将起到阻挡或支撑作用；指标仍然"
                + "强调形态、背离等信号；两条线同方向运行，向上或向下的运行力度会"
                + "很强；对于灵敏的人来说，当短线远离长线时产生勾头，我们就作好操"
                + "作的准备");
        biasText1.setText("我何时该买？\n"
                + "一般来说，负乖离率涨降至某一百分比时，为买入信号\n"
                + "其他情况有：1、当短期BIAS曲线开始在底部向上突破长期BIAS曲线时，说明股价的弱势整理格局可能被打破，股价短期将向上运动，投资者可以考虑少量长线建仓。\n"
                + "2、当短期BIAS曲线向上突破长期BIAS曲线并迅速向上运动，同时中期BIAS曲线也向上突破长期BIAS曲线，说明股价的中长期上涨行情已经开始，投资者可以加大买入股票的力度。\n"
                + "3、当短、中、长期BIAS曲线开始摆脱前期窄幅盘整的区间并同时向上快速运动时，说明股价已经进入短线强势拉升行情，投资者应坚决持股待涨。");
        biasText2.setText("我何时该卖？\n"
                + "一般来说，正乖离率涨至某一百分比时为卖出信号。\n"
                + "其他情况有：\n"
                + "1、当短期BIAS曲线经过一段快速向上运动的过程后开始在高位向下掉头时，说明股价短期上涨过快，将开始短线调整，投资者可以短线卖出股票。\n"
                + "2、当中期BIAS曲线也开始在高位向下掉头时，说明股价的短期上涨行情可能结束，投资者应中线卖出股票。\n"
                + "3、当长期BIAS曲线也开始在高位向下掉头时，说明股价的中短期上涨行情已经结束，投资者应全部清仓离场。");
        biasText3.setText("此图表何时适用？\n"
                + "在分析和预测股价走势时，可以结合乖离率与布林线或其他数据图表进行分析。但如果仅单一适用乖离率作为研判依据，有时会出现偏差，尤其是在极端行情中，乖离率所给出的逆势操作幸好可能会丢失机会或作出错误决策。所以，通过乖离率洞察股市冷暖，符合常规思路：涨多了要跌，跌多了要涨");
        
    }
    
}
