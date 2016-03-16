package AnyQuantProject.ui.guideUI;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import AnyQuantProject.starter.Main;
import AnyQuantProject.ui.benchMarkUI.BenchMarkUIController;

/**
 * 
 * @author QiHan
 *
 */

public class GuideUIController {
	private static Stage stage;
	private static Scene guideScene;
	private static Label myFavor;
	private static Label allStocks;
	private static Label benchMark;
	private static GridPane backpanel;
	private static AnchorPane panel;
	private static VBox set;
	
	private static GuideUIController instance;

	public static GuideUIController getInstance() {
	     return instance;
	}
	
	public static void lanch() {
		// TODO Auto-generated method stub
		//stage =Main.getInstance();
		myFavor =new Label("我的关注");
		allStocks = new Label("所有股票");
		benchMark = new Label("大盘走势");
//		Image image1 = new Image(getClass().getResourceAsStream("images/myFavor.png"));  
//		myFavor.setGraphic(new ImageView(image1));  
//		Image image2 = new Image(getClass().getResourceAsStream("images/allStocks.png"));  
//		allStocks.setGraphic(new ImageView(image2));  
//		Image image3 = new Image(getClass().getResourceAsStream("images/benchMark.png"));  
//		benchMark.setGraphic(new ImageView(image3));  
		
	//	((Group)guideScene.getRoot()).getChildren().addAll(myFavor,allStocks,benchMark);
		
//		backpanel.setPrefSize(primaryStage.getWidth()/10, primaryStage.getHeight());
//		backpanel.setLayoutX(primaryStage.getX());
//		backpanel.setLayoutY(primaryStage.getY());
//		Image image4 = new Image(getClass().getResourceAsStream("images/guidebackground.png"));  
//		backpanel.setGraphic(new ImageView(image4));  
//		backpanel =new Label();
		

	//	AnchorPane.setRightAnchor(background, 5.0);
		//((Group)primaryScene.getRoot()).getChildren().add(background);
	//	panel.setPrefSize(prefWidth, prefHeight);
	//	getChildren().add(backpanel);
		
	}
//	public static void main(String[] args){
//		lanch();
//	
//	}
}
