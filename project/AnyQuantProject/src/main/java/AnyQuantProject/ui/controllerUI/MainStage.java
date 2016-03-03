package AnyQuantProject.ui.controllerUI;

import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * 
 * @author QiHan
 *
 */

public class MainStage extends Application {
	private static Stage primaryStage;   //舞台
	private static Scene primaryScene;
	private static Pane writePanel;
	private static Pane head ; //
	private static Button close;
	private static Button min;
	private static Button max;
	private static Button full; //全屏
	private static Group root;
	private static HBox hbox;
	private static javafx.geometry.Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
	private static double scrH =primaryScreenBounds.getHeight();
	private static double scrW =primaryScreenBounds.getWidth();
	
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		MainStage.primaryStage = primaryStage;
		primaryStage.setHeight(scrH*3/4/490*600);
		primaryStage.setWidth(scrW*3/4);
		primaryStage.setTitle("AnyQuant");	
		
		root = new Group();
		primaryScene = new Scene(root);  
      primaryStage.initStyle(StageStyle.DECORATED);
//		primaryStage.initStyle(StageStyle.TRANSPARENT);
	//	primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.isResizable();
		primaryStage.setScene(primaryScene);  
	//buttons();
		primaryStage.show();  
		
}  
	
 
	public void buttons(){
		  close = ButtonBuilder.create().text("close").onAction(new EventHandler<ActionEvent>(){
		        @Override public void handle(ActionEvent e){
		          Event.fireEvent(primaryStage, new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST ));
		        }
		      }).build();
		  min = ButtonBuilder.create().text("min").onAction(new EventHandler<ActionEvent>(){
		            @Override public void handle(ActionEvent e){
		            	primaryStage.setIconified(true);
		            }
		        }).build();
		    full = ButtonBuilder.create().text("full").onAction(new EventHandler<ActionEvent>(){
		            @Override public void handle(ActionEvent e){
		            	primaryStage.setFullScreen(true);
		            }
		        }).build();
		   max = ButtonBuilder.create().text("max").onAction(new EventHandler<ActionEvent>(){
		            @Override public void handle(ActionEvent e){
		                primaryStage.setX(primaryScreenBounds.getMinX());
		                primaryStage.setY(primaryScreenBounds.getMinY());
		                primaryStage.setWidth(scrW);
		                primaryStage.setHeight(scrH);
		            }
		        }).build();
		   
		  // ((Group)primaryScene.getRoot()).getChildren().addAll(full,min,max,close);
		    hbox = new HBox(-2);
		    hbox.getChildren().addAll(full,min,max,close);
		    root = new Group(hbox);
		    primaryScene = SceneBuilder.create().root(root).build();
		    primaryStage.setScene(primaryScene);
	}
	
	public static  Stage getPrimaryStage(){
		return primaryStage;
	}


	public static Scene getPrimaryScene() {
		// TODO Auto-generated method stub
		return primaryScene;
	}

//	public static void main(String[] args) {  
//		launch(args);  
//	}  
	
}
