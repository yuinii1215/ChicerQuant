package AnyQuantProject.ui.moduleUI;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public class ModuleUIController implements Initializable{

	private static ModuleUIController  instance = null;
	
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
	public  void init(){}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		instance =this;
		init();
	}
	
}
