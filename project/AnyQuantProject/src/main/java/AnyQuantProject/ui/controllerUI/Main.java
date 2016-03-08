package AnyQuantProject.ui.controllerUI;

import AnyQuantProject.ui.allStocksUI.AllStocksUIController;
<<<<<<< HEAD
import AnyQuantProject.ui.singleStockInfoUI.SingleStockInfoUIController;
=======
import AnyQuantProject.ui.singleStockInfoUI.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

>>>>>>> 6c5f2b3269bbff62b20b4da52da351e3c2f22ae6
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * 
 * @author QiHan
 *
 */

public class Main extends Application {
	private static Main instance; 
	private static Stage primaryStage;   //舞台
	private static Scene primaryScene;
	private static Pane head ; //
	private static Button close;
	private static Button min;
	private static Button max;
	private static Button full; //全屏
	private static Group root;
	private static HBox h_box,hbox;	
	static AnchorPane mainPanel,guidePanel,writePanel;
	static AnchorPane allStocksPanel,benchMarkPanel,favouritePanel,
				singleStockInfoPanel,singleStockPanel,stockDealInfoPanel;
	
	private static javafx.geometry.Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
	private static double scrH =primaryScreenBounds.getHeight();
	private static double scrW =primaryScreenBounds.getWidth();
	
	public static Main getInstance(){
	        return instance;
	    }
	    
	public static Scene getPrimaryScene() {
		// TODO Auto-generated method stub
		return primaryScene;
	}
    
        public static Stage getPrimaryStage() {
	// TODO Auto-generated method stub
	return primaryStage;
        }

	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		instance=this;
		this.primaryStage = primaryStage;
<<<<<<< HEAD
//		mainPanel = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
//		guidePanel= FXMLLoader.load(getClass().getResource("guidePanel.fxml"));
//		
//		favouritePanel= FXMLLoader.load(getClass().getResource("favouritePanel.fxml"));
//		allStocksPanel = FXMLLoader.load(getClass().getResource("allStocksPanel.fxml"));
//		benchMarkPanel = FXMLLoader.load(getClass().getResource("benchMarkPanel.fxml"));
=======
//                System.out.println(getClass().getResource(""));
//                File file=new File(getClass().getResource("mainPage.fxml").toString());
//                System.out.println(getClass().getResource("mainPage.fxml").toString());
//                if(file.exists()){
//                    System.out.println("error");
//                }
                
//		mainPanel = FXMLLoader.load(getClass().getResource("mainPage.fxml"));

/**
 * 以下部分是韩梦虞用来测试的部分
 */
               Parent parent;
               String num="abcd";
               SingleStockInfoUIController controller=new SingleStockInfoUIController();
//          try {
//               parent= FXMLLoader.load(getClass().getResource("/AnyQuantProject/ui/singleStockInfoUI/singleStockInfoPanel.fxml"));
//         } catch (IOException ex) {
//            Logger.getLogger(SingleStockInfoUIController.class.getName()).log(Level.SEVERE, null, ex);
//         }
               Parent r=controller.getInstance(FXMLLoader.load(getClass().getResource("singleStockInfoPanel.fxml")),num);
               primaryStage.setScene(new Scene(r,950,600));

//
////		benchMarkPanel = FXMLLoader.load(getClass().getResource("benchMarkPanel.fxml"));
//		favouritePanel= FXMLLoader.load(getClass().getResource("favouritePanel.fxml"));
//
////		titlePanel= FXMLLoader.load(getClass().getResource("titlePanel.fxml"));
//		guidePanel= FXMLLoader.load(getClass().getResource("guidePanel.fxml"));
////		allStocksPanel = FXMLLoader.load(getClass().getResource("allStocksPanel.fxml"));
>>>>>>> 6c5f2b3269bbff62b20b4da52da351e3c2f22ae6

		singleStockInfoPanel = (AnchorPane)FXMLLoader.load(getClass().getResource("singleStockInfoPanel.fxml"));
		SingleStockInfoUIController singleStockInfoUIController = new SingleStockInfoUIController();
		singleStockInfoUIController.getInstance();
		
<<<<<<< HEAD
=======

//		singleStockInfoPanel = (AnchorPane)FXMLLoader.load(getClass().getResource("/AnyQuantProject/ui/singleStockInfoUI/singleStockInfoPanel.fxml"));
//                primaryStage.setScene(new Scene(singleStockInfoPanel,950,600));
>>>>>>> 6c5f2b3269bbff62b20b4da52da351e3c2f22ae6
//		singleStockPanel = FXMLLoader.load(getClass().getResource("singleStockPanel.fxml"));
//		stockDealInfoPanel = FXMLLoader.load(getClass().getResource("stockDealInfoPanel.fxml"));

		
//		primaryStage.setHeight(600);
//		primaryStage.setWidth(950);
//		primaryStage.setTitle("AnyQuant");	
//
//		h_box =new HBox();
//                
//		h_box.getChildren().addAll(guidePanel,favouritePanel);
//		h_box.setHgrow(guidePanel, Priority.ALWAYS);
//		h_box.setPadding(new Insets(0,0,0,0));
//		h_box.setSpacing(0);

<<<<<<< HEAD
	//	primaryStage.setScene(new Scene(h_box));
		primaryStage.setScene(new Scene(singleStockInfoPanel,950,600));
		primaryStage.initStyle(StageStyle.DECORATED);
=======
//		primaryStage.setHeight(600);
//		primaryStage.setWidth(950);
//		primaryStage.setTitle("AnyQuant");	

//		h_box =new HBox();
//                
//		h_box.getChildren().addAll(guidePanel,favouritePanel);
//		h_box.setHgrow(guidePanel, Priority.ALWAYS);
//		h_box.setPadding(new Insets(0,0,0,0));
//		h_box.setSpacing(0);
////
//		primaryStage.setScene(new Scene(h_box));

//              primaryStage.initStyle(StageStyle.DECORATED);
>>>>>>> 6c5f2b3269bbff62b20b4da52da351e3c2f22ae6

		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.isResizable();
	//  enterMainScene();
	//  buttons();
		primaryStage.show();  
		
}  
	
	
    public void enterMainScene(){
    //	Main.getPrimaryStage().setScene(new Scene(guidePanel));
        this.primaryStage.setScene(new Scene(mainPanel));
    //  MainPageController.getInstance().showAnimation();
        MainPageController.getInstance().initPanel();
    }
    
    public  static void enterAllStocksScene(){
		h_box =new HBox(guidePanel,allStocksPanel);
	    Main.getPrimaryStage().setScene(new Scene(h_box));
        MainPageController.getInstance().initPanel();
    }
    
    public  static void enterFavouriteScene(){
    	h_box =new HBox(guidePanel,favouritePanel);
    	Main.getPrimaryStage().setScene(new Scene(h_box));
        MainPageController.getInstance().initPanel();
    }
    
    public  static void enterBenchMarkScene(){
    	h_box =new HBox(guidePanel,benchMarkPanel);
    	Main.getPrimaryStage().setScene(new Scene(h_box));
        MainPageController.getInstance().initPanel();
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
	
	 public static void main(String[] args) {
		 launch(args);
	   }
  
	
}
