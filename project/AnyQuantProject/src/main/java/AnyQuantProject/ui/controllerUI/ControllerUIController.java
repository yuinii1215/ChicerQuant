package AnyQuantProject.ui.controllerUI;

import javafx.stage.Stage;
import AnyQuantProject.ui.SingleStockInfoUI.SingleStockInfoUIController;
import AnyQuantProject.ui.SingleStockUI.SingleStockUIController;
import AnyQuantProject.ui.allStocksUI.AllStocksUIController;
import AnyQuantProject.ui.benchMarkUI.BenchMarkUIController;
import AnyQuantProject.ui.favoriteUI.FavoriteUIController;
import AnyQuantProject.ui.guideUI.GuideUIController;
import AnyQuantProject.ui.stockDealInfoUI.StockDealInfoUIController;
/**
 * 
 * @author QiHan
 *
 */

public class ControllerUIController {
	private static MainStage stage;
	private static Stage primaryStage;
	private static AllStocksUIController allStocksController ;
	private static BenchMarkUIController benchMarkUIController ;
	private static FavoriteUIController  favoriteUIController ;
	private static GuideUIController guideUIController ;
	private static SingleStockInfoUIController singleStockInfoUIController ;
	private static SingleStockUIController singleStocksUIController ;
	private static StockDealInfoUIController stockDealInfoUIController ;
	
	
	public static void start() throws Exception{
	//	  primaryStage = MainStage.getPrimaryStage();
	      stage.start(primaryStage);
	}
	
	  private static void launchByStaff(StaffTypeEnum staffTypeEnum){
	        switch (staffTypeEnum){
			    case ALLSTOCKS:
			    	allStocksController= new AllStocksUIController() ;
			    	allStocksController.lanch();
			    	break;
			    case BENCHMARK:
			    	benchMarkUIController = new BenchMarkUIController();
			    	benchMarkUIController.lanch();
			    	break;
			    case FAVORITE:
			    	favoriteUIController = new FavoriteUIController(); 
			    	favoriteUIController.lanch();
			    	break;
			    case GUIDE:
			    	guideUIController = new GuideUIController();
			    	guideUIController.lanch();
			    	break;
			    case SINGLESTOCK_INFO:
			    	singleStockInfoUIController = new SingleStockInfoUIController();
			    	singleStockInfoUIController.lanch();
			    	break;
			    case SINGLESTOCK:
			    	singleStocksUIController = new SingleStockUIController();
			    	singleStocksUIController.lanch();
			    	break;
			    case STOCKDEAL_INFO:
			    	stockDealInfoUIController = new StockDealInfoUIController();
			    	stockDealInfoUIController.lanch();
			    	break;
			    default:
			    	break;
			}
	        }
}