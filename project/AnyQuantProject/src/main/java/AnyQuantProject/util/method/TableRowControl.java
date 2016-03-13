package AnyQuantProject.util.method;

import AnyQuantProject.ui.singleStockInfoUI.*;
import javafx.event.EventHandler;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


public class TableRowControl<T> extends TableRow<T> {  
//	
      public TableRowControl(TableView<T> tableView) {  
        super();  
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {  
            @Override  
            public void handle(MouseEvent event) { 
             System.out.println("hi!! I HEAR YOU!");
                if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {  
                   
                }  
                
            }  
        });  
    }  
}  