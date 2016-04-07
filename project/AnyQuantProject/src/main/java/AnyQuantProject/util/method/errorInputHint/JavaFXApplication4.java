/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnyQuantProject.util.method.errorInputHint;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author GraceHan
 */
public class JavaFXApplication4 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        MonologFXButton mlb=MonologFXButtonBuilder.create().defaultButton(true).icon("Dialog-accept.jpg").type(MonologFXButton.Type.OK).build();
      //  MonologFXButton mlb2=MonologFXButtonBuilder.create().defaultButton(true).icon("Dialog-error.jpg").type(MonologFXButton.Type.CANCEL).build();

        MonologFX mono=MonologFXBuilder.create().modal(true).message("输入无效:起始值应小于结束值").titleText("Error Input").button(mlb).buttonAlignment(MonologFX.ButtonAlignment.CENTER).build();
        //MonologFXButton.Type retval=mono.showDialog();
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {           
            @Override
            public void handle(MouseEvent event) {
             if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1){
                 MonologFXButton.Type retval=mono.showDialog();
             }
            }      
        });  
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);       
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
