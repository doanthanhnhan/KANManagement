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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.DAOCustomerBookingCheckIn;
import models.notificationFunction;
import utils.PatternValided;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class FXMLCheckIdCardCustomerController implements Initializable {

    private FXMLMainFormController fXMLMainFormController;
    @FXML
    private HBox HboxHeader;
    @FXML
    private JFXTextField IdCard;
    @FXML
    private HBox HboxContent;
    @FXML
    private HBox Hboxbtn;
    @FXML
    private JFXButton btnInfo;
    @FXML
    private JFXButton btnCancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnInfo.setOnAction((event) -> {
            check_IdCard();
        });
        IdCard.setOnKeyPressed((KeyEvent event) -> {
            IdCard.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                check_IdCard();
            }
        });
        // TODO
    }

    public void check_IdCard() {
        if (IdCard.getText() == null || IdCard.getText().equals("")) {
            notificationFunction.notification(IdCard, HboxContent, "ID CARD MUST NOT EMPTY !!!");
        } else if (!PatternValided.PatternCMND(IdCard.getText())) {
            notificationFunction.notification(IdCard, HboxContent, "ID CARD IS INCORRECT !!!");
        } else  if (DAOCustomerBookingCheckIn.check_IDCustomer(IdCard.getText())){
//            xứ lý trường hợp ID Card chưa tồn tại
            
                fXMLMainFormController.formLoader("/fxml/FXMLCustomerInfo.fxml", "/images/KAN Logo.png", "Add New Customer");
     
        }
    }

    @FXML
    private void Cancel(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

}
