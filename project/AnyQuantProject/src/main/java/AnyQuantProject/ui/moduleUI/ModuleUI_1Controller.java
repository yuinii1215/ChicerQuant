package AnyQuantProject.ui.moduleUI;

/**
 * @author QiHan
 */
import java.awt.Color;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ModuleUI_1Controller implements Initializable{

	private static ModuleUI_1Controller  instance = null;
	@FXML
	private AnchorPane modulePanel;
	private AnchorPane moreModulePanel,module2Panel,singeModulePanel;
	@FXML
	private GridPane gridPane;
	@FXML
	public VBox vBox; 
	@FXML
	private Label LineLabel1,LineLabel2;
	@FXML
//	private Button moreBtn,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12,btn13,btn14,btn15,
//		btn16,btn17,btn18,btn19,btn20,btn21,btn22,btn23,btn24,btn25,btn26,btn27,btn28;
	private Rectangle Rect1,Rect2,Rect3,Rect4,Rect5,Rect6,Rect7,Rect8,Rect9,Rect10,Rect11,Rect12,Rect13,Rect14,Rect15;
//		Rect16,Rect17,Rect18,Rect19,Rect20,Rect21,Rect22,Rect23,Rect24,Rect25,Rect26,Rect27,Rect28;
	@FXML
	private Text Text1,Text2,Text3,Text4,Text5,Text6,Text7,Text8,Text9,Text10,Text11,Text12,Text13,Text14,Text15;
//	Text16,Text17,Text18,Text19,Text20,Text21,Text22,Text23,Text24,Text25,Text26,Text27,Text28;
	private String singleModuleName;
	private int num=15;
	private Rectangle[] rect = new  Rectangle[num]; 
	private Text[] texts =new Text[num];
	private IndustryBLService industryBLService = IndustryBLFactory.getIndustryBLService();
	private List<String> allIndustryName;
	
	public static ModuleUI_1Controller  getInstance() {
		 System.out.println("here is the instance of ModuleUI_1Controller  ");
        FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ModuleUI_1Controller .class.getResource("modulePane0.fxml"));
		return instance;
   }

/**
* initialize the table
* 
*/
	public  void init(){
		rect = new Rectangle[]{Rect1,Rect2,Rect3,Rect4,Rect5,Rect6,Rect7,Rect8,Rect9,Rect10,Rect11,Rect12,Rect13,Rect14,Rect15};
		texts =new Text[]{Text1,Text2,Text3,Text4,Text5,Text6,Text7,Text8,Text9,Text10,Text11,Text12,Text13,Text14,Text15};			
		allIndustryName = industryBLService.getAllIndustries();
		for (int i=0;i<num;i++){
			singleModuleName = allIndustryName.get(i);
			texts[i].setText(
					singleModuleName);
			rect[i].setOnMouseClicked(me ->{
				System.out.println("...singleModuleName..."+singleModuleName);
				Main.enterSingleModuleScene(singleModuleName);
				});
//			rect[i].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//				   public void handle(MouseEvent e) {
//						System.out.println("...singleModuleName..."+singleModuleName);
//						Main.enterSingleModuleScene(singleModuleName);
//				   }
//			  });
		}
	}
	


	@FXML
	private void allModulesBtnPane(){
		LineLabel1.setStyle("-fx-background-color: #71C671;");//#71C671 #b5b5b5"-fx-background-color: #b5b5b5;"
		LineLabel2.setStyle("-fx-background-color: #b5b5b5;");
		try {
			FXMLLoader fxmlLoader=new FXMLLoader(Main.class.getResource("modulePanel1.fxml"));
			modulePanel= (AnchorPane)fxmlLoader.load(); 
			vBox.getChildren().add(1,modulePanel);   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@FXML
	private void allModuleInfoPane(){
		LineLabel2.setStyle("-fx-background-color: #71C671;");
		LineLabel1.setStyle("-fx-background-color: #b5b5b5;");
		try {
			FXMLLoader fxmlLoader=new FXMLLoader(Main.class.getResource("modulePanel2.fxml"));
			module2Panel= (AnchorPane)fxmlLoader.load(); 
			vBox.getChildren().add(1,module2Panel);   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	@FXML
	private void toDetailMoulePane(){
		
	}
	
	@FXML
	private void toMoreModulePane(){
		try {
			FXMLLoader fxmlLoader=new FXMLLoader(Main.class.getResource("moreModulePanel.fxml"));
			moreModulePanel= (AnchorPane)fxmlLoader.load(); 
			vBox.
			getChildren().add(1,moreModulePanel);   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void toReturnPane(){
		try {
			FXMLLoader fxmlLoader=new FXMLLoader(Main.class.getResource("modulePanel1.fxml"));
			modulePanel= (AnchorPane)fxmlLoader.load(); 
			vBox.getChildren().add(1,modulePanel);   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		instance =this;
		init();
	}
	
}
