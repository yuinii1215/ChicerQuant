package AnyQuantProject.ui.moduleUI;
/**
 * @author QiHan
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import AnyQuantProject.bl.factoryBL.IndustryBLFactory;
import AnyQuantProject.blService.industryBLService.IndustryBLService;
import AnyQuantProject.dataStructure.IndustryInfo;
import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.starter.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ModuleUI_2Controller implements Initializable{
	private static ModuleUI_2Controller  instance = null;
	
	private AnchorPane module2Panel;
	
	@FXML
  	private TableColumn<Stock, String> IndustryNameColumn;
    @FXML
    private TableColumn<Stock, Double> ChgColumn;
    @FXML
    private TableColumn<Stock, Long> PureColumn;
    @FXML
    private TableColumn<Stock, Double> SumColumn;
    @FXML
    private TableColumn<Stock, String> LedColumn;
    @FXML
    private TableColumn<Stock, Double> SingleChgColumn;
    @FXML
    private TableColumn<Stock, Double> SinglePrizeColumn;
    
	
	private IndustryBLService industryBLService = IndustryBLFactory.getIndustryBLService();
	private IndustryInfo  industryInfo; 
	
	
	public static ModuleUI_2Controller  getInstance() {
		System.out.println("here is the instance of ModuleUI_2Controller  ");
        FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ModuleUI_2Controller .class.getResource("modulePanel2.fxml"));
		return instance;
  }
	public void init(){
	//	industryInfo = industryBLService.getIndustryInfo(industry)
		
		
		
	}
	
		

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		instance =this;
		init();
	}

}
