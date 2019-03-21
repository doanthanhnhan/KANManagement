/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXProgressBar;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Doan Thanh Nhan
 */
public class FXMLSplashScreenController implements Initializable {

    @FXML
    private Label label_CopyRight;
    @FXML
    private Label label_loading;
    @FXML
    private JFXProgressBar progressBar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ConnectControllers.setfXMLSplashScreenController(this);
       
    }    

    public Label getLabel_CopyRight() {
        return label_CopyRight;
    }

    public void setLabel_CopyRight(Label label_CopyRight) {
        this.label_CopyRight = label_CopyRight;
    }

    public Label getLabel_loading() {
        return label_loading;
    }

    public void setLabel_loading(Label label_loading) {
        this.label_loading = label_loading;
    }

    public JFXProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(JFXProgressBar progressBar) {
        this.progressBar = progressBar;
    }
    
}
