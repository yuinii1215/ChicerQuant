/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnyQuantProject.ui.stasticsInfoUI;

import AnyQuantProject.ui.allStocksUI.AllStocksUIController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author GraceHan
 */
public class StasticsInfoController implements Initializable {
    @FXML
    Label maTitle,macdTitle,kdjTitle,rsiTitle,biasTitle;
    @FXML
    ImageView maImageView,macdImageView,kdjImageView,rsiImageView,biasImageView;
    @FXML
    TextArea maText,macdText,kdjText,rsiText,biasText;

    /**
     * Initializes the controller class.
     */
    private static StasticsInfoController instance;
    
    public static StasticsInfoController getInstance(){
        System.out.println("here is the instance of stasticsUIController ");
        return instance == null ? (instance = new StasticsInfoController()) : instance;
    }
    public StasticsInfoController(){
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
        init();
    }    
    public void init(){
       
    }
    
}
