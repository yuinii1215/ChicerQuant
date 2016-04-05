package AnyQuantProject.starter;

import AnyQuantProject.bl.factoryBL.StockListBLFactory;
import AnyQuantProject.bl.stockListBL.StockListBLController;
import AnyQuantProject.ui.controllerUI.MainPageController;
import AnyQuantProject.ui.moduleUI.ModuleUI_1Controller;
import AnyQuantProject.ui.moduleUI.SingleModuleUIController;
import AnyQuantProject.ui.singleStockInfoUI.SingleStockInfoUIController;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

import org.eclipse.swt.widgets.Scale;

import com.hp.hpl.sparta.xpath.ParentNodeTest;

import java.net.MalformedURLException;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.ImageCursor;
import javafx.scene.SceneBuilder;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Node;
import javafx.stage.WindowEvent;
import javafx.scene.Cursor;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

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
	private static VBox vbox;	
	static AnchorPane mainPanel,guidePanel,headPanel,writePanel;
	public static AnchorPane singleModulePanel,moreModulePanel,modulePanel,allStocksPanel,benchMarkPanel,favouritePanel,singleStockPanel,stockDealInfoPanel,singleStockInfoPanel;

//	private static javafx.geometry.Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
//	private static double scrH =primaryScreenBounds.getHeight();
//	private static double scrW =primaryScreenBounds.getWidth();
    private static boolean move = false;
    private static Point origin = new Point();
    public static FXMLLoader fxmlLoader;
    public static SingleStockInfoUIController singleStockInfoUIController = null;
    public static SingleModuleUIController  singleModuleInfoUIController = null;
    public static ModuleUI_1Controller   moduleUI_1Controller  = null;

    public static Main getInstance() {
        return instance;
    }

    public static Scene getPrimaryScene() {
        // TODO Auto-generated method stub
//        Image image = new Image("images/mouse cursor.png");
//        primaryScene.setCursor(new ImageCursor(image, image.getWidth()/2, image.getHeight()/2));
        return primaryScene;
    }

    public static Stage getPrimaryStage() {
        // TODO Auto-generated method stub
        return primaryStage;
    }

    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        instance = this;

        this.primaryStage = primaryStage;
        primaryStage.setScene(getPrimaryScene());
        
        //	mainPanel = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        headPanel = FXMLLoader.load(getClass().getResource("headPanel.fxml"));
        guidePanel = FXMLLoader.load(Main.class.getResource("guidePanel.fxml"));

        favouritePanel = FXMLLoader.load(getClass().getResource("favouritePanel.fxml"));
//		allStocksPanel = FXMLLoader.load(getClass().getResource("allStocksPanel.fxml"));

        benchMarkPanel = FXMLLoader.load(getClass().getResource("benchMarkPanel1.fxml"));
        benchMarkPanel.setId("pane");

        modulePanel = FXMLLoader.load(getClass().getResource("modulePanel0.fxml"));


//		primaryStage.setHeight(636);
//		primaryStage.setWidth(992);
        primaryStage.setHeight(625);
        primaryStage.setWidth(980);
        primaryStage.setTitle("AnyQuant");

		h_box =new HBox(); 
		h_box.getChildren().addAll(guidePanel,favouritePanel);    
		h_box.setHgrow(guidePanel, Priority.ALWAYS);
		h_box.setPadding(new Insets(0,0,0,0));
		h_box.setSpacing(0);

		vbox =new VBox(); 
		vbox.getChildren().addAll(headPanel,h_box);    
		vbox.setPadding(new Insets(0,0,0,0));
		vbox.setSpacing(0);
   
//            primaryStage.setScene(new Scene(vbox));
////          primaryStage.setScene(initAnimation());
//        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
//        ScheduledFuture future = service.schedule(new Callable() {
//            @Override
//            public String call() {
//                System.out.print("time is up");
//                primaryStage.setScene(new Scene(vbox));
//                primaryStage.show();
//                return "taskcancelled!";
//            }
//        }, 10, TimeUnit.SECONDS);


                
    //     primaryStage.setScene(initAnimation());         
               
                            
	//	primaryStage.setScene(new Scene(vbox));
		primaryStage.initStyle(StageStyle.DECORATED);
//		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.isResizable();

		//界面拖拽
		primaryStage.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {
				// TODO Auto-generated method stub
			   if(e.getY()<=(int)((double)primaryStage.getHeight()*22/490)){
	            	move=true;
	            	origin.x = (int) e.getX();  
	                origin.y = (int) e.getY();
	                }
			   
			}});
		
		primaryStage.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {
				// TODO Auto-generated method stub
			   move =false;
			}});

		primaryStage.addEventHandler(MouseEvent.MOUSE_DRAGGED,new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e) {	
				if(move){
	                double x = primaryStage.getX() ;
	                double y =primaryStage.getY();
	
	                primaryStage.setX(x + e.getX() - origin.x);
	                primaryStage.setY(y + e.getY() - origin.y);
	            	}
	            }
		});	
	//  enterMainScene();
	//  buttons();
		primaryStage.setScene(new Scene(vbox));
		primaryStage.show();  
                  
//                ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
//                ScheduledFuture future = service.schedule(new Callable() {
//                        public String call() {
//                     //   	System.out.print("time is up");
//                             service.shutdown();
//                             System.out.println("???????!!!!!??:::"+service.isShutdown());
//                            
//                             return "taskcancelled!";
//                        }
//                    }, 11, TimeUnit.SECONDS);
//                     
                   
	      
//            primaryStage.setScene(new Scene(vbox));
////          primaryStage.setScene(initAnimation());
//        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
//        ScheduledFuture future = service.schedule(new Callable() {
//            @Override
//            public String call() {
//                System.out.print("time is up");
//                primaryStage.setScene(new Scene(vbox));
//                primaryStage.show();
//                return "taskcancelled!";
//            }
//        }, 10, TimeUnit.SECONDS);
//        service.shutdown();
//              
//        //	primaryStage.initStyle(StageStyle.DECORATED);
//        primaryStage.initStyle(StageStyle.UNDECORATED);
//        primaryStage.isResizable();

        //界面拖拽
        primaryStage.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent e) {
                // TODO Auto-generated method stub
                if (e.getY() <= (int) ((double) primaryStage.getHeight() * 22 / 490)) {
                    move = true;
                    origin.x = (int) e.getX();
                    origin.y = (int) e.getY();
                }

            }
        });

        primaryStage.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent e) {
                // TODO Auto-generated method stub
                move = false;
            }
        });

        primaryStage.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                if (move) {
                    double x = primaryStage.getX();
                    double y = primaryStage.getY();

                    primaryStage.setX(x + e.getX() - origin.x);
                    primaryStage.setY(y + e.getY() - origin.y);
                }
            }
        });
        //  enterMainScene();
        //  buttons();
        primaryStage.show();
    }

    public void endAnimation(Stage primaryStage) {
        //TODO
        h_box = new HBox(guidePanel, favouritePanel);
        vbox = new VBox(headPanel, h_box);        
        primaryStage.setScene(new Scene(vbox));
        primaryStage.show();
    }

//	    static double ix;
//	    static double iy;
//	    private static void enableDrag(Scene scene){
//	        scene.setOnMousePressed(
//	                event -> {
//	                    ix = event.getScreenX() - primaryStage.getX();
//	                    iy = event.getScreenY() - primaryStage.getY();
//	                }
//	        );
//	        scene.setOnMouseDragged(
//	                event -> {
//	                    primaryStage.setX(event.getScreenX() - ix);
//	                    primaryStage.setY(event.getScreenY() - iy);
//	                }
//	        );
//	    }
//	private Scene buildScene(Node node){
//		Image image = new Image(new File("images/mouse cursor.png").toURI().toString());
//		//TODO
//		ImageCursor cursor = new ImageCursor(image, image.getWidth(),image.getHeight());
//		node.setCursor(cursor);
//		return node.getScene();
//	}
    public void enterMainScene() {
        //TODO
        this.primaryStage.setScene(new Scene(mainPanel));
        MainPageController.getInstance().initPanel();
    }

    public static void enterAllStocksScene() {
        try {
            allStocksPanel = FXMLLoader.load(Main.class.getResource("allStocksPanel.fxml"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        h_box = new HBox(guidePanel, allStocksPanel);
        vbox = new VBox(headPanel, h_box);
        Main.getPrimaryStage().setScene(new Scene(vbox));
        MainPageController.getInstance().initPanel();
    }

    public static void enterFavouriteScene() {

        h_box = new HBox(guidePanel, favouritePanel);
        vbox = new VBox(headPanel, h_box);        
        Main.getPrimaryStage().setScene(new Scene(vbox));
        MainPageController.getInstance().initPanel();
        
    }

    public static void enterBenchMarkScene() {
        h_box = new HBox(guidePanel, benchMarkPanel);
        vbox = new VBox(headPanel, h_box);
        Main.getPrimaryStage().setScene(new Scene(vbox));
        MainPageController.getInstance().initPanel();
    }

    public static void enterModuleScene() {
        h_box = new HBox(guidePanel, modulePanel);
        vbox = new VBox(headPanel, h_box);
        Main.getPrimaryStage().setScene(new Scene(vbox));
        MainPageController.getInstance().initPanel();
    }
    
    public static void returnToModuleScene(){
    	try {
			FXMLLoader fxmlLoader=new FXMLLoader(Main.class.getResource("modulePanel0.fxml"));
			 modulePanel = (AnchorPane)fxmlLoader.load();
			 moduleUI_1Controller =fxmlLoader.getController();
			 moduleUI_1Controller .init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
    	
    	h_box =new HBox(guidePanel,modulePanel);
		vbox=new VBox(headPanel,h_box);    
		Main.getPrimaryStage().setScene(new Scene(vbox));
		MainPageController.getInstance().initPanel();
                                 
    
    }
    
    public  static void enterSingleModuleScene(String singleModuleName){
    
    	try {
			FXMLLoader fxmlLoader=new FXMLLoader(Main.class.getResource("singleModulePanel.fxml"));
			singleModulePanel= (AnchorPane)fxmlLoader.load();
			singleModuleInfoUIController=fxmlLoader.getController();
			singleModuleInfoUIController.laterInit(singleModuleName); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
    	
    	h_box =new HBox(guidePanel,singleModulePanel);
		vbox=new VBox(headPanel,h_box);    
		Main.getPrimaryStage().setScene(new Scene(vbox));
		MainPageController.getInstance().initPanel();
                                 
    }
     
/**
 * TO be tested
 * @param name
 */
       
       public static void endSingle() {                  
              singleStockInfoUIController.endLoad();
             
       }
 
	public static void enterSingleStockInfoScene(String name) {
		// TODO Auto-generated method stub                
		try {
			FXMLLoader fxmlLoader=new FXMLLoader(Main.class.getResource("singleStockInfoPanel.fxml"));
			singleStockInfoPanel = (AnchorPane)fxmlLoader.load();
			singleStockInfoUIController=fxmlLoader.getController();
			singleStockInfoUIController.loadImage.setImage(new Image("/images/load.gif"));
                        singleStockInfoUIController.laterInit(name);                 
               } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
                       
			h_box =new HBox(guidePanel,singleStockInfoPanel);
			vbox=new VBox(headPanel,h_box);    
			Main.getPrimaryStage().setScene(new Scene(vbox));
			MainPageController.getInstance().initPanel();
                                     
                      
                         
	}
    

    public void buttons() {
        close = ButtonBuilder.create().text("close").onAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Event.fireEvent(primaryStage, new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST));
            }
        }).build();
        min = ButtonBuilder.create().text("min").onAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                primaryStage.setIconified(true);
            }
        }).build();
        full = ButtonBuilder.create().text("full").onAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                primaryStage.setFullScreen(true);
            }
        }).build();
//		   max = ButtonBuilder.create().text("max").onAction(new EventHandler<ActionEvent>(){
//		            @Override public void handle(ActionEvent e){
//		                primaryStage.setX(primaryScreenBounds.getMinX());
//		                primaryStage.setY(primaryScreenBounds.getMinY());
//		                primaryStage.setWidth(scrW);
//		                primaryStage.setHeight(scrH);
//		            }
//		        }).build();

        // ((Group)primaryScene.getRoot()).getChildren().addAll(full,min,max,close);
        hbox = new HBox(-2);
        hbox.getChildren().addAll(full, min, max, close);
        root = new Group(hbox);
        primaryScene = SceneBuilder.create().root(root).build();
        primaryStage.setScene(primaryScene);
    }

    Player player;
    FileChooser fileChooser;
    Scene scene;
    public Scene initAnimation() {  
		MenuItem open = new MenuItem("Open");
		Menu file = new Menu("");
		//MenuBar menu = new MenuBar();
		file.getItems().add(open);
		//menu.getMenus().add(file);

		fileChooser = new FileChooser();

		open.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				player.player.pause();
				File file = fileChooser.showOpenDialog(primaryStage);
				if(file != null){
					try {
						player = new Player(file.toURI().toURL().toExternalForm());
						//player.setTop(menu);
						Scene scene = new Scene(player, 1000, 650, Color.BLACK);
						primaryStage.setScene(scene);
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});


		//replace filePath with path of your file

		String filePath =  "file://AnyQuant_Project//project//AnyQuantProject//src//main//java//AnyQuantProject//starter//StockMarket.mp4";
                player = new Player(filePath);
		Scene scene = new Scene(player, 1000, 650, Color.BLACK);	
                return scene;
    }
//=======

//		String filePath = "file:/course/Class/SE3/assignment/AnyQuant_Project/project/AnyQuantProject/src/main/java/AnyQuantProject/starter/StockMarket.mp4";
//		player = new Player(filePath);
//		//player.setTop(menu);
//		Scene scene = new Scene(player, 1000, 650, Color.BLACK);
//	//	primaryStage.setScene(scene);
//	//	primaryStage.show();
//                return scene;

//		String filePath ="/StockMarket.mp4";
//		System.out.println(filePath);
//                player = new Player(filePath);
//		Scene scene = new Scene(player, 1000, 650, Color.BLACK);	
//                return scene;
//    }


    public static void main(String[] args) {
        StockListBLController stockListBLController = (StockListBLController) StockListBLFactory.getStockListBLService();

        if (stockListBLController.shouldInit()) {
            Thread thread = new Thread(stockListBLController);
            thread.start();
        }
        launch(args);

    	}
	
}
