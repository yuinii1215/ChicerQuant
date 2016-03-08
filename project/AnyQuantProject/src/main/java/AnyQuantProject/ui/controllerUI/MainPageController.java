package AnyQuantProject.ui.controllerUI;
 
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import AnyQuantProject.ui.allStocksUI.AllStocksUIController;
import AnyQuantProject.ui.benchMarkUI.BenchMarkUIController;
import AnyQuantProject.ui.favoriteUI.FavoriteUIController;
import AnyQuantProject.ui.guideUI.GuideUIController;
import AnyQuantProject.ui.singleStockInfoUI.OriginSingleStockInfoUIController;
import AnyQuantProject.ui.singleStockUI.SingleStockUIController;
import AnyQuantProject.ui.stockDealInfoUI.StockDealInfoUIController;
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
	private static GuideUIController guideUIController ;
	private static SingleStockUIController singleStocksUIController ;
	private static StockDealInfoUIController stockDealInfoUIController ;
	 @FXML
	private static AnchorPane titlePanel, guidePanel, centralPanel;
	private Button defaultBtn = null;
	
  
	private static MainPageController instance;

    public static MainPageController getInstance() {
        return instance;
    }
	public static void start() throws Exception{
	//	  primaryStage = MainStage.getPrimaryStage();
	      stage.start(primaryStage);
	}
    /**
     * Initializes the controller class.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		 instance = this;
		
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
//	        AllStocksUIController.getInstance(stage.allStocksPanel);
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
		        setPanel(Main.singleStockInfoPanel, "打开单只股票下部信息界面...");
		        Button btn = (Button)e.getSource();
		        if(defaultBtn != null)
		            defaultBtn.setDefaultButton(false);
		        btn.setDefaultButton(true);
		        defaultBtn = btn;
		        OriginSingleStockInfoUIController.getInstance();
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
		  @FXML
		    private void stockDealInfoBtnFired(ActionEvent e) {
		        setPanel(Main.stockDealInfoPanel, "打开股票交易??界面...");
		        Button btn = (Button)e.getSource();
		        if(defaultBtn != null)
		            defaultBtn.setDefaultButton(false);
		        btn.setDefaultButton(true);
		        defaultBtn = btn;
		        StockDealInfoUIController.getInstance();
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
//	  private static void launchByStaff(StaffTypeEnum staffTypeEnum){
//	        switch (staffTypeEnum){
//			    case ALLSTOCKS:
//			    	allStocksController= new AllStocksUIController() ;
//			    	allStocksController.lanch();
//			    	break;
//			    case BENCHMARK:
//			    	benchMarkUIController = new BenchMarkUIController();
//			    	benchMarkUIController.lanch();
//			    	break;
//			    case FAVORITE:
//			    	favoriteUIController = new FavoriteUIController(); 
//			    	favoriteUIController.lanch();
//			    	break;
//			    case GUIDE:
//			    	guideUIController = new GuideUIController();
//			    	guideUIController.lanch();
//			    	break;
//			    case SINGLESTOCK_INFO:
//			    	singleStockInfoUIController = new SingleStockInfoUIController();
//			    	singleStockInfoUIController.lanch();
//			    	break;
//			    case SINGLESTOCK:
//			    	singleStocksUIController = new SingleStockUIController();
//			    	singleStocksUIController.lanch();
//			    	break;
//			    case STOCKDEAL_INFO:
//			    	stockDealInfoUIController = new StockDealInfoUIController();
//			    	stockDealInfoUIController.lanch();
//			    	break;
//			    default:
//			    	break;
//			}
//	        }
	
		  }
}