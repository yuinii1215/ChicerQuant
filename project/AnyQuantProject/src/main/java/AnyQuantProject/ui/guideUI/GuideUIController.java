package AnyQuantProject.ui.guideUI;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import AnyQuantProject.ui.controllerUI.MainStage;

/**
 * 
 * @author QiHan
 *
 */

public class GuideUIController {
	private MainStage stage;
	private Scene guideScene;
	private Label myFavor;
	private Label allStocks;
	private Label benchMark;
	private VBox set;
	
	public void lanch() {
		// TODO Auto-generated method stub
		myFavor =new Label("我的关注");
		allStocks = new Label("所有股票");
		benchMark = new Label("大盘走势");
		
		
		
		
		
	}

}
