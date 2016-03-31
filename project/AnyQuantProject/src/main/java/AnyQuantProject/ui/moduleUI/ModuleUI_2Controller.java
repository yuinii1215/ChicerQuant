package AnyQuantProject.ui.moduleUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import AnyQuantProject.starter.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ModuleUI_2Controller implements Initializable{
	private static ModuleUI_2Controller  instance = null;
	
	private AnchorPane module2Panel;
	
	public static ModuleUI_2Controller  getInstance() {
		 System.out.println("here is the instance of ModuleUI_2Controller  ");
        FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ModuleUI_2Controller .class.getResource("modulePanel2.fxml"));
		return instance;
  }
	public void init(){
	
	}
	
		

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		instance =this;
		init();
	}

}
