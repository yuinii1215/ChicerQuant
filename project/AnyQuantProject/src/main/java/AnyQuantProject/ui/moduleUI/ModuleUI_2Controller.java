package AnyQuantProject.ui.moduleUI;
/**
 * @author QiHan
 */

import java.io.IOException;
import java.net.URL;
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
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class ModuleUI_2Controller implements Initializable{
	private static ModuleUI_2Controller  instance = null;
	
	private AnchorPane module2Panel;
	
	@FXML
  	private TableColumn<IndustryInfo, String> IndustryNameColumn;
    @FXML
    private TableColumn<IndustryInfo, Double> ChgColumn;
    @FXML
    private TableColumn<IndustryInfo, Long> PureColumn;
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
	
	private IndustryBLService industryBLService = IndustryBLFactory.getIndustryBLService();
	private IndustryInfo  industryInfo; 
	private List<String> allIndustryName;
	private List<IndustryInfo> industryInfoList;
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
				 PureColumn.setCellValueFactory(cellData -> new SimpleLongProperty(
			                cellData.getValue().getPure()));
				 SumColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
			                cellData.getValue().getCompanySum()));
				 LedColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
			                cellData.getValue().getLeader()));
				 SingleChgColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
			                cellData.getValue().getLeaderUpdown()));
				 SinglePrizeColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
			                        cellData.getValue().getLeaderPrice()));

		
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
