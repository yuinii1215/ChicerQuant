package AnyQuantProject.starter;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import AnyQuantProject.bl.factoryBL.StockListBLFactory;
import AnyQuantProject.bl.stockListBL.StockListBLController;
import AnyQuantProject.ui.controllerUI.MainPageController;
import AnyQuantProject.ui.moduleUI.ModuleUI_1Controller;
import AnyQuantProject.ui.moduleUI.SingleModuleUIController;
import AnyQuantProject.ui.singleStockInfoUI.SingleStockInfoUIController;
import AnyQuantProject.util.constant.R;
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
	public static AnchorPane singleModulePanel,moreModulePanel,modulePanel,allStocksPanel,benchMarkPanel,favouritePanel,singleStockPanel,stockDealInfoPanel,singleStockInfoPanel,stasticsPanel;

//	private static javafx.geometry.Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
//	private static double scrH =primaryScreenBounds.getHeight();
//	private static double scrW =primaryScreenBounds.getWidth();
    private static boolean move = false;
    private static Point origin = new Point();
    public static FXMLLoader fxmlLoader;
    public static SingleStockInfoUIController singleStockInfoUIController = null;
    public static SingleModuleUIController  singleModuleInfoUIController = null;
    public static ModuleUI_1Controller   moduleUI_1Controller  = null;
    static ImageCursor imageCursor;
    

    public static Main getInstance() {
        return instance;
    }


    public static Scene getFactoryScene(Parent parent) {
    	/**
    	 * change cursor
    	 */
//		if (imageCursor==null) {
//    		Image image=new Image("images/mouse cursor.png");
//    		imageCursor = new ImageCursor(image, image.getWidth()/2, image.getHeight()/2);
//		}
        Scene ans=new Scene(parent); 
//        ans.setCursor(imageCursor);
        return ans;

    }

    public static Stage getPrimaryStage() {
        // TODO Auto-generated method stub
        return primaryStage;
    }

    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        instance = this;

        this.primaryStage = primaryStage;
        
        //	mainPanel = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        headPanel = FXMLLoader.load(getClass().getResource("headPanel.fxml"));
        guidePanel = FXMLLoader.load(Main.class.getResource("guidePanel.fxml"));

        favouritePanel = FXMLLoader.load(getClass().getResource("favouritePanel.fxml"));
//		allStocksPanel = FXMLLoader.load(getClass().getResource("allStocksPanel.fxml"));

      benchMarkPanel = FXMLLoader.load(getClass().getResource("benchMarkPanel1.fxml"));
      benchMarkPanel.setId("pane");

        modulePanel = FXMLLoader.load(getClass().getResource("modulePanel0.fxml"));
        stasticsPanel=FXMLLoader.load(getClass().getResource("stasticsInfoPanel.fxml"));


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
//		primaryStage.initStyle(StageStyle.DECORATED);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.isResizable();

		//界面拖拽
//		primaryStage.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){
//
//			@Override
//			public void handle(MouseEvent e) {
//				// TODO Auto-generated method stub
//			   if(e.getY()<=(int)((double)primaryStage.getHeight()*22/490)){
//	            	move=true;
//	            	origin.x = (int) e.getX();  
//	                origin.y = (int) e.getY();
//	                }
//			   
//			}});
//		
//		primaryStage.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>(){
//
//			@Override
//			public void handle(MouseEvent e) {
//				// TODO Auto-generated method stub
//			   move =false;
//			}});
//
//		primaryStage.addEventHandler(MouseEvent.MOUSE_DRAGGED,new EventHandler<MouseEvent>(){
//			public void handle(MouseEvent e) {	
//				if(move){
//	                double x = primaryStage.getX() ;
//	                double y =primaryStage.getY();
//	
//	                primaryStage.setX(x + e.getX() - origin.x);
//	                primaryStage.setY(y + e.getY() - origin.y);
//	            	}
//	            }
//		});	


		
		
	//	  enableDragAndResize(primaryStage);
	
		
		
		
		primaryStage.setScene(getFactoryScene(vbox));
		enableDragAndResize(  primaryStage.getScene());
		primaryStage.show();  
    }

    public void endAnimation(Stage primaryStage) {
        //TODO
        h_box = new HBox(guidePanel, favouritePanel);
        vbox = new VBox(headPanel, h_box);        
        primaryStage.setScene(getFactoryScene(vbox));
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
        this.primaryStage.setScene(getFactoryScene(mainPanel));
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
        Main.getPrimaryStage().setScene(getFactoryScene(vbox));
        MainPageController.getInstance().initPanel();
    }

    public static void enterFavouriteScene() {

        h_box = new HBox(guidePanel, favouritePanel);
        vbox = new VBox(headPanel, h_box);        
        Main.getPrimaryStage().setScene(getFactoryScene(vbox));
        MainPageController.getInstance().initPanel();
        
    }

    public static void enterBenchMarkScene() {
        h_box = new HBox(guidePanel, benchMarkPanel);
        vbox = new VBox(headPanel, h_box);
        Main.getPrimaryStage().setScene(getFactoryScene(vbox));
        MainPageController.getInstance().initPanel();
    }

    public static void enterModuleScene() {
        h_box = new HBox(guidePanel, modulePanel);
        vbox = new VBox(headPanel, h_box);
        Main.getPrimaryStage().setScene(getFactoryScene(vbox));
        MainPageController.getInstance().initPanel();
    }
    public static void enterStasticsScene() {
        h_box = new HBox(guidePanel, stasticsPanel);
        vbox = new VBox(headPanel, h_box);
        Main.getPrimaryStage().setScene(getFactoryScene(vbox));
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
		Main.getPrimaryStage().setScene(getFactoryScene(vbox));
		MainPageController.getInstance().initPanel();
                                 
    
    }
    
    public  static void enterSingleModuleScene(String singleModuleName){
    	System.out.println("....entersingleModuleSuccess!!...."+singleModuleName);
    
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
		Main.getPrimaryStage().setScene(getFactoryScene(vbox));
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
			Main.getPrimaryStage().setScene(getFactoryScene(vbox));
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

    
    
    /**
     * drag
     */
    private static double calX;
    private static double calY;
    private static double oldPosX;
    private static double oldPosY;
    private static double oldWidth;
    private static double oldHeight;
    private static boolean dragging = false;
    private static boolean resizing = false;
    private static CURSOR_AREA pressedArea = CURSOR_AREA.CENTER;
    public static final double ResizePadding = 5;
    private static final double padding = ResizePadding;
    public static final double titleHeight = 625;//70
    public static final double LeftTabsWidth = 80;//179
    
    
    private static enum CURSOR_AREA{
        NORTH_WEST,
        NORTH_EAST,
        SOUTH_WEST,
        SOUTH_EAST,
        NORTH,
        SOUTH,
        WEST,
        EAST,
        TITLE,
        CENTER
    }

    private static CURSOR_AREA getCursorArea(double x, double y, double width, double height){
        if(y < padding && x < padding){
            return CURSOR_AREA.NORTH_WEST;
        }else if(y < padding && x > width - padding){
            return CURSOR_AREA.NORTH_EAST;
        }else if(y > height - padding && x < padding){
            return CURSOR_AREA.SOUTH_WEST;
        }else if(y > height - padding && x > width - padding){
            return CURSOR_AREA.SOUTH_EAST;
        }else if(y> height - padding){
            return CURSOR_AREA.SOUTH;
        }else if(x> width - padding){
            return CURSOR_AREA.EAST;
        }else if(y < padding){
            return CURSOR_AREA.NORTH;
        }else if(x < padding){
            return CURSOR_AREA.WEST;
        }else if(y < titleHeight){
            return CURSOR_AREA.TITLE;
        }else{
            return CURSOR_AREA.CENTER;
        }
    }

    private static final double minWidth = 980;
    private static final double minHeight = 625;
    private static void setBounds(Stage stage, double x, double y, double w, double h){
        if(w > minWidth){
            stage.setWidth(w);
            stage.setX(x);
            stage.setY(y);
        }else{
            stage.setWidth(minWidth);
        }
        if(h > minHeight){
            stage.setHeight(h);
            stage.setX(x);
            stage.setY(y);
        }else{
            stage.setHeight(minHeight);
        }
    }

    static double ix;
    static double iy;
    
    private static void enableDragAndResize(Scene scene){
        scene.setOnMousePressed(
                me -> {
                    pressedArea = getCursorArea(me.getX(), me.getY(), primaryStage.getWidth(), primaryStage.getHeight());
                    if (pressedArea == CURSOR_AREA.TITLE) {
                        resizing = false;
                        dragging = true;
                        calX = me.getScreenX() - primaryStage.getX();
                        calY = me.getScreenY() - primaryStage.getY();
                    } else if (pressedArea == CURSOR_AREA.CENTER) {
                        resizing = false;
                        dragging = false;
                    } else {
                        resizing = true;
                        dragging = false;
                        oldPosX = primaryStage.getX();
                        oldPosY = primaryStage.getY();
                        oldWidth = primaryStage.getWidth();
                        oldHeight = primaryStage.getHeight();
                        calX = me.getScreenX();
                        calY = me.getScreenY();
                    }
                }
        );
        scene.setOnMouseDragged(
                me -> {
                    if (dragging) {
                        primaryStage.setX(me.getScreenX() - calX);
                        primaryStage.setY(me.getScreenY() - calY);
                    } else if (resizing) {
                        double dx = me.getScreenX() - calX;
                        double dy = me.getScreenY() - calY;
                        switch (pressedArea) {
                            case NORTH_WEST:
                                setBounds(primaryStage, oldPosX + dx, oldPosY + dy, oldWidth - dx, oldHeight - dy);
                                break;
                            case NORTH_EAST:
                                setBounds(primaryStage, oldPosX, oldPosY + dy, oldWidth + dx, oldHeight - dy);
                                break;
                            case SOUTH_WEST:
                                setBounds(primaryStage, oldPosX + dx, oldPosY, oldWidth - dx, oldHeight + dy);
                                break;
                            case SOUTH_EAST:
                                setBounds(primaryStage, oldPosX, oldPosY, oldWidth + dx, oldHeight + dy);
                                break;
                            case NORTH:
                                setBounds(primaryStage, oldPosX, oldPosY + dy, oldWidth, oldHeight - dy);
                                break;
                            case SOUTH:
                                setBounds(primaryStage, oldPosX, oldPosY, oldWidth, oldHeight + dy);
                                break;
                            case WEST:
                                setBounds(primaryStage, oldPosX + dx, oldPosY, oldWidth - dx, oldHeight);
                                break;
                            case EAST:
                                setBounds(primaryStage, oldPosX, oldPosY, oldWidth + dx, oldHeight);
                                break;
                            case TITLE:
                                break;
                            case CENTER:
                                break;
                        }
                    }
                }
        );
        // change the look of the mouse when it is moved to the sides
        scene.setOnMouseMoved(
                me -> {
                    CURSOR_AREA area = getCursorArea(me.getX(), me.getY(), primaryStage.getWidth(), primaryStage.getHeight());
                    Cursor cursor = Cursor.DEFAULT;
                    switch (area) {
                        case NORTH_WEST:
                            cursor = Cursor.NW_RESIZE;
                            break;
                        case NORTH_EAST:
                            cursor = Cursor.NE_RESIZE;
                            break;
                        case SOUTH_WEST:
                            cursor = Cursor.SW_RESIZE;
                            break;
                        case SOUTH_EAST:
                            cursor = Cursor.SE_RESIZE;
                            break;
                        case NORTH:
                        case SOUTH:
                            cursor = Cursor.V_RESIZE;
                            break;
                        case WEST:
                        case EAST:
                            cursor = Cursor.H_RESIZE;
                            break;
                        case TITLE:
                        case CENTER:
                            cursor = Cursor.DEFAULT;
                            break;
                    }
                    primaryStage.getScene().setCursor(cursor);
                }
        );
    }
    

    
    public static void main(String[] args) {
        StockListBLController stockListBLController = (StockListBLController) StockListBLFactory.getStockListBLService();

        if (stockListBLController.shouldInit()) {
            Thread thread = new Thread(stockListBLController);
            thread.start();
        }
        launch(args);

    	}
	
}
