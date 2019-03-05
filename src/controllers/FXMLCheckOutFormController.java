/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import utils.QRWebCam;

/**
 * FXML Controller class
 *
 * @author Doan Thanh Nhan
 */
public class FXMLCheckOutFormController implements Initializable {

    @FXML
    private JFXButton btn_QR_Code_Scanner;
    @FXML
    private JFXTextField txt_Check_In_ID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handle_QRScanner_Action(ActionEvent event) {
        QRWebCam qrWebCam = new QRWebCam();
        qrWebCam.txtQR.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txt_Check_In_ID.setText(newValue);
            }
        });
    }
    
}
