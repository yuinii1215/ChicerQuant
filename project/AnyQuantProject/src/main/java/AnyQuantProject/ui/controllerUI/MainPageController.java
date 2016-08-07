package AnyQuantProject.ui.controllerUI;
 
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import AnyQuantProject.starter.Main;
import AnyQuantProject.ui.allStocksUI.AllStocksUIController;
import AnyQuantProject.ui.benchMarkUI.BenchMarkUIController;
import AnyQuantProject.ui.favoriteUI.FavoriteUIController;
import AnyQuantProject.ui.moduleUI.ModuleUI_1Controller;
import AnyQuantProject.ui.stasticsInfoUI.StasticsInfoController;
/**
 * 
 * @author QiHan
 *
 */

public class MainPageController  implements Initializable {
	private static Main stage;
	private static Stage primaryStage;
	private static AllStocksUIController allStocksController ;
	private static BenchMarkUIController benchMarkUIController ;
	private static FavoriteUIController  favoriteUIController ;
	 @FXML
	private static AnchorPane titlePanel, guidePanel, centralPanel;
	private Button defaultBtn = null;
	static AnchorPane headPanel;
	@FXML
	private Button favoriteBtn,allStocksBtn,benchMarkBtn,moduleBtn,stasticsBtn;
	@FXML
	private ImageView myFavor,BenchMark,allStocks,module,stastics;
	
	@FXML
	private Tooltip myFavorTip,allStocksTip,benchMarkTip,moduleTip,stasticsTip;
	
    Image stastics_Selected=new Image(getClass().getResourceAsStream("/images/stasticsSelected.png"));
    Image stastics_Exited=new Image(getClass().getResourceAsStream("/images/stastics.png"));
    Image myFavor_Selected = new Image(getClass().getResourceAsStream("/images/myFavorSelected.png"));
    Image allStocks_Selected = new Image(getClass().getResourceAsStream("/images/allStocksSelected.png"));
    Image benchMark_Selected = new Image(getClass().getResourceAsStream("/images/benchMarkSelected.png"));
    Image module_Selected = new Image(getClass().getResourceAsStream("/images/moduleSelected.png"));
    Image myFavor_Exited = new Image(getClass().getResourceAsStream("/images/myFavor.png"));
    Image allStocks_Exited= new Image(getClass().getResourceAsStream("/images/allStocks.png"));
    Image benchMark_Exited = new Image(getClass().getResourceAsStream("/images/benchMark.png"));
    Image module_Exited = new Image(getClass().getResourceAsStream("/images/module.png"));
    Image exit_Entered = new Image(getClass().getResourceAsStream("/images/exitEntered.png"));
    Image min_Entered = new Image(getClass().getResourceAsStream("/images/minEntered.png"));
    Image exit_Exited = new Image(getClass().getResourceAsStream("/images/exit.png"));
    Image min_Exited = new Image(getClass().getResourceAsStream("/images/min.png"));
   
    private static MainPageController instance;

    public static MainPageController getInstance() {
        return instance;
    }

    /**
     * Initializes the controller class.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		 instance = this;
		 buttons();
	}
	
	
	@SuppressWarnings("deprecation")
	private void buttons(){
		
		//pressed
		favoriteBtn.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
			myFavor.setImage(myFavor_Selected);
			allStocks.setImage(allStocks_Exited);
			BenchMark.setImage(benchMark_Exited);
			module.setImage(module_Exited );
        });
		favoriteBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			myFavor.setImage(myFavor_Selected);
			allStocks.setImage(allStocks_Exited);
			BenchMark.setImage(benchMark_Exited);
			module.setImage(module_Exited );
        });
		favoriteBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			myFavor.setImage(myFavor_Selected);
		});
		favoriteBtn.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			myFavor.setImage(myFavor_Exited);
		});
		
		allStocksBtn.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
			myFavor.setImage(myFavor_Exited);
			allStocks.setImage(allStocks_Selected);
			BenchMark.setImage(benchMark_Exited);
			module.setImage(module_Exited );
        });
		allStocksBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			allStocks.setImage(allStocks_Selected);
		});
		allStocksBtn.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			allStocks.setImage(allStocks_Exited);
		});
		
		benchMarkBtn.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
			myFavor.setImage(myFavor_Exited);
			allStocks.setImage(allStocks_Exited);
			BenchMark.setImage(benchMark_Selected);
			module.setImage(module_Exited );
        });
		benchMarkBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			BenchMark.setImage(benchMark_Selected);
		});
		benchMarkBtn.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			BenchMark.setImage(benchMark_Exited);
		});
		
		moduleBtn.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
			myFavor.setImage(myFavor_Exited);
			allStocks.setImage(allStocks_Exited);
			BenchMark.setImage(benchMark_Exited);
			module.setImage(module_Selected );
        });
		moduleBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			module.setImage(module_Selected);
		});
		moduleBtn.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			module.setImage(module_Exited);
		});
                stasticsBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			stastics.setImage(stastics_Selected);
		});
		stasticsBtn.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			stastics.setImage(stastics_Exited);
		});
		
		
//		//exit,min
//		exitBtn.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
//			exit.setImage(exit_Entered);
//			
//			Event.fireEvent(Main.getPrimaryStage(), new WindowEvent(Main.getPrimaryStage(), WindowEvent.WINDOW_CLOSE_REQUEST ));
//			System.exit(0);
//		});
//		minBtn.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
//			min.setImage(min_Entered);
//			Main.getPrimaryStage().setIconified(true);
//		});
//		
//		exitBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
//			exit.setImage(exit_Entered);
//        });
//		minBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
//			min.setImage(min_Entered);
//        });
//		exitBtn.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
//			exit.setImage(exit_Exited);
//        });
//		minBtn.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
//			min.setImage(min_Exited);
//        });
//		
//		
}
	
	
	
	
	
	  @FXML
	    private void allStocksBtnFired(ActionEvent e) {
	        setPanel(Main.allStocksPanel, "打开所有股票界面...");
	        Button btn = (Button)e.getSource();
	        if(defaultBtn != null)
	            defaultBtn.setDefaultButton(false);
	        btn.setDefaultButton(true);
	        defaultBtn = btn;
	        Main.enterAllStocksScene();
	        AllStocksUIController.getInstance();
	    }
	  @FXML
	    private void benchMarkBtnFired(ActionEvent e) {
	        setPanel(Main.benchMarkPanel, "打开大盘信息界面...");
	        Button btn = (Button)e.getSource();
	        if(defaultBtn != null)
	            defaultBtn.setDefaultButton(false);
	        btn.setDefaultButton(true);
	        defaultBtn = btn;
	        Main.enterBenchMarkScene();
	        BenchMarkUIController.getInstance().init();
	    }
	  
	  
	  	@FXML
	    private void favoriteBtnFired(ActionEvent e) {
	        setPanel(Main.favouritePanel, "打开我的关注界面...");
	        Button btn = (Button)e.getSource();
	        if(defaultBtn != null)
	            defaultBtn.setDefaultButton(false);
	        btn.setDefaultButton(true);
	        defaultBtn = btn;
	        Main.enterFavouriteScene();
			FavoriteUIController.getInstance().init();
		
	    }
		@FXML
	  	 private void moduleBtnFired(ActionEvent e) {
		        setPanel(Main.favouritePanel, "打开我的关注界面...");
		        Button btn = (Button)e.getSource();
		        if(defaultBtn != null)
		            defaultBtn.setDefaultButton(false);
		        btn.setDefaultButton(true);
		        defaultBtn = btn;
		        Main.enterModuleScene();
				ModuleUI_1Controller.getInstance().init();			
		    }
                @FXML
	  	 private void stasticsBtnFired(ActionEvent e) {
		        setPanel(Main.stasticsPanel, "打开统计分析界面...");
		        Button btn = (Button)e.getSource();
		        if(defaultBtn != null)
		            defaultBtn.setDefaultButton(false);
		        btn.setDefaultButton(true);
		        defaultBtn = btn;
		        Main.enterStasticsScene();
		        StasticsInfoController.getInstance().init();			
		    }
	  	
//		  @FXML
//		    private void guideBtnFired(ActionEvent e) {
//		        setPanel(Main.guidePanel, "打开导航界面...");
//		        Button btn = (Button)e.getSource();
//		        if(defaultBtn != null)
//		            defaultBtn.setDefaultButton(false);
//		        btn.setDefaultButton(true);
//		        defaultBtn = btn;
//		        GuideUIController.getInstance();
//		    }
		  
		  //为避免冲突
		  @FXML
		    private void singleStockInfoBtnFired(ActionEvent e) {
		       // setPanel(Main.singleStockInfoPanel, "打开单只股票下部信息界面...");
		        Button btn = (Button)e.getSource();
		        if(defaultBtn != null)
		            defaultBtn.setDefaultButton(false);
		        btn.setDefaultButton(true);
		        defaultBtn = btn; 
		        //SingleStockInfoUIController.getInstance();                         
		    }
                    
		  @FXML
		    private void singleStockBtnFired(ActionEvent e) {
		        setPanel(Main.singleStockPanel, "打开单只股票上部界面...");
		        Button btn = (Button)e.getSource();
		        if(defaultBtn != null)
		            defaultBtn.setDefaultButton(false);
		        btn.setDefaultButton(true);
		        defaultBtn = btn;
		     //   SingleStockUIController.getInstance();
		    }
		 
		  
		  
//	  
//		  public void showAnimation() {
//		     new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(guidePanel.translateXProperty(), -200), new KeyValue(guidePanel.opacityProperty(), 0)),
//		            new KeyFrame(Duration.seconds(0.3), new KeyValue(guidePanel.translateXProperty(), -200), new KeyValue(guidePanel.opacityProperty(), 1)),
//		            new KeyFrame(Duration.seconds(0.6), new KeyValue(guidePanel.translateXProperty(), 0), new KeyValue(guidePanel.opacityProperty(), 1))).play();
//		   }
	  
		  public void setPanel(final AnchorPane panel, String name) {
			  if (panel == null) {
				  return;
			  }
//			  if (centralPanel.getChildren().size() > 0 && centralPanel.getChildren().get(0) == panel) {
//	            return;
//	        }
	        EventHandler<ActionEvent> eh = new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                try {
	                    centralPanel.getChildren().clear();
	                    centralPanel.getChildren().add(panel);
	                    guidePanel.toFront();
	                    titlePanel.toFront();
	                } catch (Exception e) {
	                  System.out.println("......Fail......");
	                }
	            }
	        };
//	        EventHandler<ActionEvent> eh2 = new EventHandler<ActionEvent>() {
//	            @Override
//	            public void handle(ActionEvent event) {
//	                try{
//	                    showMessage("加载完成！");
//	                }catch(Exception e){
//	                    showErrorMessage(e.getLocalizedMessage());
//	                }
//	            }
//	        };
//	        StudentPanelController.getInstance().loadTable();
//	        if (centralPanel.getChildren().size() > 0) {
//	            if (centralPanel.getChildren().get(0) == panel) {
//	                return;
//	            }
//	            //定时器
//	            new Timeline(
//	                    new KeyFrame(Duration.seconds(0.15), new KeyValue(centralPanel.translateXProperty(), -600), new KeyValue(centralPanel.opacityProperty(), 0)),
//	                    new KeyFrame(Duration.seconds(0.16), eh),
//	                    new KeyFrame(Duration.seconds(0.3), new KeyValue(centralPanel.translateXProperty(), 0), new KeyValue(centralPanel.opacityProperty(), 1)) //,new KeyFrame(Duration.seconds(0.31), eh2)
//	                    ).play();
//	        } else {
//	            new Timeline(
//	                    new KeyFrame(Duration.seconds(0.45), new KeyValue(centralPanel.opacityProperty(), 0)),
//	                    new KeyFrame(Duration.seconds(0.46), eh),
//	                    new KeyFrame(Duration.seconds(0.6), new KeyValue(centralPanel.opacityProperty(), 1)) //,new KeyFrame(Duration.seconds(0.31), eh2)
//	                    ).play();
//	        }
	    }
		  public void initPanel() {
		        final AnchorPane panel = Main.favouritePanel;
		        
		        EventHandler<ActionEvent> eh = new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
		                try {
		                    centralPanel.getChildren().clear();
		                    centralPanel.getChildren().add(panel);
		                    guidePanel.toFront();
		                    titlePanel.toFront();
		                } catch (Exception e) {
		                  System.out.println("");
		                }
		            }
		        };
		        EventHandler<ActionEvent> eh2 = new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
		                try {
		                	favoriteUIController.getInstance();//.loadTable();
		                } catch (Exception e) {
		                	System.out.println("");
		                }
		            }
		        };

//		        final double delay = 0.60;
//		        new Timeline(
//		                new KeyFrame(Duration.seconds(delay), new KeyValue(centralPanel.translateXProperty(), -600), new KeyValue(centralPanel.opacityProperty(), 0)),
//		                new KeyFrame(Duration.seconds(delay+0.01), eh),
//		                new KeyFrame(Duration.seconds(delay+0.15), new KeyValue(centralPanel.translateXProperty(), 0), new KeyValue(centralPanel.opacityProperty(), 1)),
//		                new KeyFrame(Duration.seconds(delay+0.16), eh2)).play();
//		    }
		  }
}