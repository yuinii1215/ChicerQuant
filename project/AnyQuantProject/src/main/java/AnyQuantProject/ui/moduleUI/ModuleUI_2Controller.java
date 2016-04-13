package AnyQuantProject.ui.moduleUI;
/**
 * @author QiHan
 */

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import AnyQuantProject.bl.factoryBL.IndustryBLFactory;
import AnyQuantProject.blService.industryBLService.IndustryBLService;
import AnyQuantProject.dataStructure.IndustryInfo;
import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.starter.Main;
import AnyQuantProject.ui.moduleUI.SingleModuleUIController.TableRowControl;
import AnyQuantProject.util.method.SimpleDoubleProperty;
import AnyQuantProject.util.method.SimpleIntegerProperty;
import AnyQuantProject.util.method.SimpleLongProperty;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.Duration;

public class ModuleUI_2Controller implements Initializable{
	private static ModuleUI_2Controller  instance = null;
	
	private AnchorPane module2Panel;
	
	@FXML
  	private TableColumn<IndustryInfo, String> IndustryNameColumn;
    @FXML
    private TableColumn<IndustryInfo, Double> ChgColumn;
    @FXML
    private TableColumn<IndustryInfo, Double> PureColumn;
    @FXML
    private TableColumn<IndustryInfo, Double> SumColumn;
    @FXML
    private TableColumn<IndustryInfo, String> LedColumn;
    @FXML
    private TableColumn<IndustryInfo, Double> SingleChgColumn;
    @FXML
    private TableColumn<IndustryInfo, Double> SinglePrizeColumn;
    @FXML
    private TableView<IndustryInfo> table;
	@FXML
	private BarChart barChart;
	@FXML
	private CategoryAxis barXAxis;
	@FXML
	private NumberAxis barYAxis;
	
	private IndustryBLService industryBLService = IndustryBLFactory.getIndustryBLService();
	private IndustryInfo  industryInfo; 
	private List<String> allIndustryName;
	private List<IndustryInfo> industryInfoList =new ArrayList<>();
    int selectedIndex;
    String industryName; 
    String singleIndustryName;
//	private 
	
	public static ModuleUI_2Controller  getInstance() {
		System.out.println("here is the instance of ModuleUI_2Controller  ");
        FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ModuleUI_2Controller .class.getResource("modulePanel2.fxml"));
		return instance;
  }
	
	public void init(){
		initTable();
		initChart();	
	}
	
	public void initTable(){	
		allIndustryName = industryBLService.getAllIndustries();
		
		    for(int i=0;i< allIndustryName.size();i++){
		    	industryInfo= industryBLService.getIndustryInfo(allIndustryName.get(i));
		    	industryInfoList.add(i, industryInfo);
		    }
		    
			table.setItems(FXCollections.observableArrayList(industryInfoList));
			//	table.getItems().add(new Stock());
				table.setRowFactory(new Callback<TableView<IndustryInfo>, TableRow<IndustryInfo>>() {
			            @Override
			            public TableRow<IndustryInfo> call(TableView<IndustryInfo> table) {
			                // TODO Auto-generated method stub
			                return new TableRowControl(table);
			            }
			       });

				 IndustryNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
			                cellData.getValue().getIndustry()));
				 ChgColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
			                cellData.getValue().getUpdown()));
				 PureColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
			                cellData.getValue().getPure()));
				 SumColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
			                cellData.getValue().getCompanySum()));
				 LedColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
			                cellData.getValue().getLeader()));
				 SingleChgColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
			                cellData.getValue().getLeaderUpdown()));
				 SinglePrizeColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
			                        cellData.getValue().getLeaderPrice()));

				 ChgColumn.setCellFactory(new Callback<TableColumn<IndustryInfo, Double>, TableCell<IndustryInfo, Double>>() {
			            @Override
			            public TableCell<IndustryInfo, Double> call(TableColumn<IndustryInfo, Double> arg0) {
			                return new TableCell<IndustryInfo, Double>() {
			                    ObservableValue ov1;

			                    @Override
			                    protected void updateItem(Double item, boolean empty) {
			                        super.updateItem(item, empty);
			                        if (this.getIndex() < industryInfoList.size()) {
			                            if (!isEmpty()) {
			                            	this.setTextFill(Color.RED);
			                            }
			                        }
			                    }
			                };
			            }
			        });
				 
				 PureColumn.setCellFactory(new Callback<TableColumn<IndustryInfo, Long>, TableCell<IndustryInfo, Long>>() {
			            @Override
			            public TableCell<IndustryInfo, Long> call(TableColumn<IndustryInfo, Long> arg0) {
			                return new TableCell<IndustryInfo, Long>() {
			                    ObservableValue ov1;

			                    @Override
			                    protected void updateItem(Long item, boolean empty) {
			                        super.updateItem(item, empty);
			                        if (this.getIndex() < industryInfoList.size()) {
			                            if (!isEmpty()) {
			                            	if(Double.parseDouble(this.getText())<0)
			                            		this.setTextFill(Color.GREEN);
			                            	}
			                            	else if(Double.parseDouble(this.getText())>0)
			                            		this.setTextFill(Color.RED);
		                            		}
			                    }
			                };
			            }
			        });
				 
				 
				 
	}
	
	
	public void initChart(){
		//x轴为20支股票 高10支 低10支
		//y轴为净额
		
		//将净额按从大到小排序
		allIndustryName = industryBLService.getAllIndustries();
		String[] industryName = new String[allIndustryName.size()]; 
		Double[] pures = new Double[allIndustryName.size()];
		for(int i=0;i<allIndustryName.size();i++){
			pures[i] = industryInfoList.get(i).getPure();
			industryName[i] = industryInfoList.get(i).getIndustry();
		}
			/**  *冒泡排序从大到小 * */ 
		      for(int i=0 ;i < pures.length ; i++) {  
		    	  for(int j=i+1 ;j < pures.length ; j++) {  
		    		  if(pures[i] < pures[j]) {
		    			  Double temp = pures[i];
		    			  pures[i] = pures[j];
		    			  pures[j] = temp;  
		    			  String temp1 = industryName[i];
		    			  industryName[i] = industryName[j];
		    			  industryName[j] = temp1;
		    			  }
		    		  }
		    	  }
		      
		      System.out.print("every industry sort up to down:");
		      for(int i = 0 ; i < pures.length ;i ++) { 
		    	  System.out.print(industryName[i]+":"+pures[i]+" ");   
		      }
		 
		//设置图
		barChart = new BarChart<String,Number>(barXAxis,barYAxis); 
		barYAxis.setLabel("净额");
		
		XYChart.Series series = new XYChart.Series();
		
		Timeline tl = new Timeline () ;
		tl.getKeyFrames().add(new KeyFrame(Duration.millis(500),
				new EventHandler <ActionEvent> () {
			@ Override
			public void handle ( ActionEvent actionEvent ) {
				for (int i=0;i<10;i++){
					series.getData().add(new XYChart.Data(industryName[i],pures[i]));
				}
				for(int i=0;i<10;i++){
					series.getData().add(new XYChart.Data(industryName[industryName.length-i-1],pures[pures.length-i-1]));
				}
			}
		
		}));

		tl.setCycleCount (1) ;
		tl.play();
		barChart.getData().addAll(series);
	}
	
	 public class TableRowControl<T> extends TableRow<T> {

	        public TableRowControl(TableView<T> tableView) {
	            super();

	            this.setOnMouseClicked(new EventHandler<MouseEvent>() {
	                @Override
	                public void handle(MouseEvent event) {
	                	 if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
	                         selectedIndex = TableRowControl.this.getIndex();
	                         singleIndustryName =IndustryNameColumn.getCellData(selectedIndex);
	                         System.out.println("......Enter :" + singleIndustryName + " panel......");
	                         Main.enterSingleModuleScene(singleIndustryName);
	                	 }
	                }});
	         }
	  }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		instance =this;
		init();
	}

}
