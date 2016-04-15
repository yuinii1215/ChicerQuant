package AnyQuantProject.ui.net;

import AnyQuantProject.starter.Main;
import AnyQuantProject.util.method.errorInputHint.MonologFX;
import AnyQuantProject.util.method.errorInputHint.MonologFXBuilder;
import AnyQuantProject.util.method.errorInputHint.MonologFXButton;
import AnyQuantProject.util.method.errorInputHint.MonologFXButton.Type;
import AnyQuantProject.util.method.errorInputHint.MonologFXButtonBuilder;
import javafx.event.Event;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class TipPop {
//	private Popup netPop = new Popup();
//	private String tips="网络已断开，请检查！";
//	private Button ok,exit;
	static MonologFX mono;
	static MonologFXButton mlb1;
	static MonologFXButton mlb2;
	static MonologFXButton.Type type;
	
	public final static  void showTipPop(){
	//	MonologFXButton.Type type = MonologFXUtil.confirm("你确定吗?"); 
	
		mlb1=MonologFXButtonBuilder.create().defaultButton(true).label("已连接").type(MonologFXButton.Type.OK).build();
		mlb2=MonologFXButtonBuilder.create().defaultButton(true).label("退出").type(MonologFXButton.Type.CANCEL).build();
		
		
		mono=MonologFXBuilder.create().modal(true).message("网络已断开，请检查！").button(mlb1).button(mlb2).buttonAlignment(MonologFX.ButtonAlignment.RIGHT).build();        
		mono.initStyle(StageStyle.UNDECORATED);
		//625,980 80
		mono.setHeight(60);
		mono.setWidth(900);
		
	}
	public static void listeners(){
		if(type == MonologFXButton.Type.OK) {
			
		}
		if(type == MonologFXButton.Type.CANCEL) {
			Event.fireEvent(Main.getPrimaryStage(), new WindowEvent(Main.getPrimaryStage(), WindowEvent.WINDOW_CLOSE_REQUEST ));
			System.exit(0);
		}
	}
	
	public static  void showTip(){
		showTipPop();
		listeners();
		type = mono.showDialog(80, 565);
		 
	}
}
