package AnyQuantProject.ui.favoriteUI;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.table.TableColumn;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumnBuilder;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import AnyQuantProject.ui.benchMarkUI.BenchMarkUIController;

/**
 * 
 * @author QiHan
 *
 */
public class FavoriteUIController implements Initializable{

	private static FavoriteUIController instance;

	public static FavoriteUIController getInstance() {
		return instance;
	}
	@FXML
	TableView table;
	@FXML
	TableColumn number,id,name,open,high,low,close,volume,adj_price,marketvalue,flow;
	@FXML
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		   instance = this;
	       table.getColumns().addAll(number,id,name,open,high,low,close,volume,adj_price,marketvalue,flow);
//	                TableColumnBuilder.create().text("序号").cellValueFactory(new PropertyValueFactory("number")).prefWidth(75.0).build(),
//	                TableColumnBuilder.create().text("代码").cellValueFactory(new PropertyValueFactory("id")).prefWidth(76).build(),
//	                TableColumnBuilder.create().text("名称").cellValueFactory(new PropertyValueFactory("name")).prefWidth(87).build(),
//	                TableColumnBuilder.create().text("开盘价").cellValueFactory(new PropertyValueFactory("open")).prefWidth(73).build(),
//	                TableColumnBuilder.create().text("最高价").cellValueFactory(new PropertyValueFactory("high")).prefWidth(73).build(),
//	                TableColumnBuilder.create().text("最低价").cellValueFactory(new PropertyValueFactory("low")).prefWidth(78).build(),
//	        		TableColumnBuilder.create().text("收盘价").cellValueFactory(new PropertyValueFactory("close")).prefWidth(74).build(),
//	        		TableColumnBuilder.create().text("成交量").cellValueFactory(new PropertyValueFactory("volume")).prefWidth(74).build(),
//	        		TableColumnBuilder.create().text("后复权价").cellValueFactory(new PropertyValueFactory("adj_price")).prefWidth(76).build(),
//	        		TableColumnBuilder.create().text("市值").cellValueFactory(new PropertyValueFactory("marketvalue")).prefWidth(70).build(),
//	        		TableColumnBuilder.create().text("流通量").cellValueFactory(new PropertyValueFactory("flow")).prefWidth(92.99993896484375).build(),
	
	        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	                FavoriteTableRecord record = (FavoriteTableRecord) table.getSelectionModel().getSelectedItem();
	                if (record == null) {
	                    return;
	                }

	            }
	        });
	    
	}
}
