package AnyQuantProject.ui.moduleUI;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.sun.javafx.robot.impl.FXRobotHelper;

import AnyQuantProject.bl.factoryBL.IndustryBLFactory;
import AnyQuantProject.blService.industryBLService.IndustryBLService;
import AnyQuantProject.starter.Main;
import AnyQuantProject.ui.controllerUI.MainPageController;
import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ModuleUIController implements Initializable{

	private static ModuleUIController  instance = null;
	@FXML
	private AnchorPane modulePanel,moreModulePanel;
	@FXML
	private Button moreBtn,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12,btn13,btn14,btn15;
		

	private int num=15;
	private Button[] buttons =new Button[15];
	private IndustryBLService industryBLService = IndustryBLFactory.getIndustryBLService();
	private List<String> allIndustryName;
	
	public static ModuleUIController  getInstance() {
		 System.out.println("here is the instance of ModuleUIController  ");
        FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ModuleUIController .class.getResource("modulePanel.fxml"));
		return instance;
   }

/**
* initialize the table
* 
*/
	public  void init(){
	
		buttons =new Button[]{btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12,btn13,btn14,btn15}; 
		allIndustryName = industryBLService.getAllIndustries();
		for (int i=0;i<num;i++){
			buttons[i].setText(
					allIndustryName.get(i));
		
		
		
		}
   
		
	}
	
	@FXML
	private void toDetailMoulePane(){
		
	}
	
	@FXML
	private void toMoreModulePane(){
		 Main.enterMoreModuleScene();
	}

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		instance =this;
		init();
	}
	
}
