package AnyQuantProject.ui.headUI;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import AnyQuantProject.starter.Main;
import AnyQuantProject.ui.benchMarkUI.BenchMarkUIController;

/**
 * 
 * @author QiHan
 *
 */

public class HeadUIController implements Initializable{
	@FXML
	private static AnchorPane headPanel; 
	@FXML
	private Button minBtn,exitBtn;
	@FXML
	private ImageView exit,min;
	@FXML
	private Tooltip minTip,closeTip;
	
	private Image exit_Entered = new Image(getClass().getResourceAsStream("/images/exitEntered.png"));
	private Image min_Entered = new Image(getClass().getResourceAsStream("/images/minEntered.png"));
	private Image exit_Exited = new Image(getClass().getResourceAsStream("/images/exit.png"));
	private Image min_Exited = new Image(getClass().getResourceAsStream("/images/min.png"));
	   
	private static javafx.geometry.Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
	private static double scrH =primaryScreenBounds.getHeight();
	private static double scrW =primaryScreenBounds.getWidth();

	
	private static HeadUIController instance;

	public static HeadUIController getInstance() {
	     return instance;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		buttons();
	}
	private void buttons(){
		
		//exit,min
		exitBtn.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
			exit.setImage(exit_Entered);
			Event.fireEvent(Main.getPrimaryStage(), new WindowEvent(Main.getPrimaryStage(), WindowEvent.WINDOW_CLOSE_REQUEST ));
			System.exit(0);
		});
		minBtn.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
			min.setImage(min_Entered);
			Main.getPrimaryStage().setIconified(true);
		});
		
		exitBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			exit.setImage(exit_Entered);
        });
		minBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			min.setImage(min_Entered);
        });
		exitBtn.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			exit.setImage(exit_Exited);
        });
		minBtn.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			min.setImage(min_Exited);
        });
	}
	
}
