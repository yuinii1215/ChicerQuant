package AnyQuantProject.ui.net;

import java.awt.event.MouseAdapter;

import AnyQuantProject.starter.Main;
import AnyQuantProject.util.method.errorInputHint.MonologFX;
import AnyQuantProject.util.method.errorInputHint.MonologFXBuilder;
import AnyQuantProject.util.method.errorInputHint.MonologFXButton;
import AnyQuantProject.util.method.errorInputHint.MonologFXButton.Type;
import AnyQuantProject.util.method.errorInputHint.MonologFXButtonBuilder;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class TipPop {
//	static Popup netPop = new Popup();
//	static ListView<String> tips= new ListView<String>();
//	static Label tip = new Label("网络已断开，请检查！");
//	static Button ok,exit;
	static MonologFX mono;
	static MonologFXButton mlb1;
	static MonologFXButton mlb2;
	static MonologFXButton.Type type;
	
	public final static  void showTipPop(){
//		netPop.setAutoHide(true);
//		netPop.getContent().add(tip);
//		netPop.setWidth(900);
//		netPop.setHeight(60);
//		netPop.setX(80);
		//NETPop.setY(0);
		
		mlb1=MonologFXButtonBuilder.create().defaultButton(true).label("已连接").type(MonologFXButton.Type.OK).build();
		mlb2=MonologFXButtonBuilder.create().defaultButton(true).label("退出").type(MonologFXButton.Type.CANCEL).build();
		mono=MonologFXBuilder.create().modal(true).message("网络已断开，请检查！").button(mlb1).button(mlb2).buttonAlignment(MonologFX.ButtonAlignment.CENTER).build();        
		mono.initStyle(StageStyle.UNDECORATED);
		//625,980 80
		mono.setHeight(60);
		mono.setWidth(900);
		mono.setAlwaysOnTop(true);
		
	}
	public static void listeners(){
		if(type == MonologFXButton.Type.OK) {
			
		}
		
		if(type == MonologFXButton.Type.CANCEL) {
			Event.fireEvent(Main.getPrimaryStage(), new WindowEvent(Main.getPrimaryStage(), WindowEvent.WINDOW_CLOSE_REQUEST ));
			System.exit(0);
		}
	}
	
	public final static Scene getScene(){
		return Main.getPrimaryStage().getScene();
	}
	
	public final static Window getWindow(){
		return getScene().getWindow();
	}
	
	public static  void showTip(){
		showTipPop();
		listeners();
		Window window = getWindow();
		Scene scene = getScene();
		type = mono.showDialog((int)(window.getX() +window.getWidth()*3/7), (int)(window.getY() +window.getHeight()*3/7 ));

		//netPop.show(window, window.getX() + fieldPosition.getX() + scene.getX(), window.getY() + fieldPosition.getY() + scene.getY() + textField.getHeight());
		 
	}
	
}
