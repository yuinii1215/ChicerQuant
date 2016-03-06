package AnyQuantProject.ui.benchMarkUI;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import AnyQuantProject.ui.allStocksUI.AllStocksUIController;

/**
 * 
 * @author QiHan
 *
 */

public class BenchMarkUIController implements Initializable{


    private static BenchMarkUIController instance;

    public static BenchMarkUIController getInstance() {
      
    	return instance;
    }	
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		   instance = this;
	}
}
