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
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Doan Thanh Nhan
 */
public class FXMLAddNewServiceTypeController implements Initializable {

    @FXML
    private JFXTextField serviceID;
    @FXML
    private JFXTextField serviceName;
    @FXML
    private JFXTextField serviceUnit;
    @FXML
    private JFXTextField servicePrice;
    @FXML
    private JFXButton btnInsertImage;
    @FXML
    private ImageView imgService;
    @FXML
    private HBox HboxContent;
    @FXML
    private JFXButton btnAddNew;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnSubmitAddNewEmployee(ActionEvent event) {
    }
    
}
