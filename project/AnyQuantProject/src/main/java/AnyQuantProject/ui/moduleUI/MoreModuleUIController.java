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
import AnyQuantProject.starter.Main;
import AnyQuantProject.ui.allStocksUI.AllStocksUIController;
import AnyQuantProject.ui.controllerUI.MainPageController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class MoreModuleUIController implements Initializable{
	
	private static MoreModuleUIController  instance = null;
	@FXML
	private AnchorPane moreModulePanel;
	private AnchorPane modulePanel;
	private ModuleUI_1Controller moduleUI_1Controller =null;
	@FXML
	private Button returnBtn,btn16,btn17,btn18,btn19,btn20,btn21,btn22,btn23,btn24,btn25,btn26,btn27,btn28,btn29;
	private int num=13;
	private Button[] buttons =new Button[13];
	private IndustryBLService industryBLService = IndustryBLFactory.getIndustryBLService();
	private List<String> allIndustryName;
	private String singleModuleName ;
	public static MoreModuleUIController  getInstance() {
		 System.out.println("here is the instance of MoreModuleUIController  ");
        FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ModuleUI_1Controller .class.getResource("moreModulePanel.fxml"));
		return instance;
  }
	
	public  void init(){
		buttons =new Button[]{btn16,btn17,btn18,btn19,btn20,btn21,btn22,btn23,btn24,btn25,btn26,btn27,btn28};
		allIndustryName = industryBLService.getAllIndustries();
		btn29.setText("...敬请期待");
		for (int i=0;i<num;i++){
			singleModuleName =allIndustryName.get(i+15);
			buttons[i].setText(singleModuleName);
			
			buttons[i].setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					Button src=(Button)event.getSource();
					int j=0;
					for(;j<num;j++){
						if (buttons[j]==src) {
							break;
						}
					}
					System.out.println("....singleModule...."+buttons[j].getText());
					Main.enterSingleModuleScene(buttons[j].getText());
				}
			});
		}
	}
	
	@FXML
	private void toReturnPane() {
		Main.returnToModuleScene();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		instance =this;
		init();
	}
}
