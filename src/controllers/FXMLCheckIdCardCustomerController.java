/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
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
    public static boolean checkIdCardCustomer = false;
    public static boolean checkIdCardCustomerAlready = false;
    public static String IdCardCustomer;
    @FXML
    private AnchorPane anchorPaneCheckIdCard;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnInfo.setOnAction((event) -> {
            try {
                check_IdCard();
            } catch (IOException ex) {
                Logger.getLogger(FXMLCheckIdCardCustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        IdCard.setOnKeyPressed((KeyEvent event) -> {
            IdCard.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    check_IdCard();
                } catch (IOException ex) {
                    Logger.getLogger(FXMLCheckIdCardCustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        // TODO
    }

    public void check_IdCard() throws IOException {
        System.out.println("check Id Invalid: " + DAOCustomerBookingCheckIn.check_IDCustomer("CTM-" + IdCard.getText()));
        if (IdCard.getText() == null || IdCard.getText().equals("")) {
            notificationFunction.notification(IdCard, HboxContent, "ID CARD MUST NOT EMPTY !!!");
        } else if (!PatternValided.PatternCMND(IdCard.getText())) {
            notificationFunction.notification(IdCard, HboxContent, "ID CARD IS INCORRECT !!!");
        } else if (DAOCustomerBookingCheckIn.check_IDCustomer("CTM-" + IdCard.getText())) {
//            xứ lý trường hợp ID Card chưa tồn tại        
            Stage stageEdit = new Stage();
            stageEdit.resizableProperty().setValue(Boolean.FALSE);
            checkIdCardCustomer = true;
            IdCardCustomer = IdCard.getText();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/FXMLCustomerInfo.fxml"));
            stageEdit.getIcons().add(new Image("/images/KAN Logo.png"));
            Scene scene = new Scene(root);
            stageEdit.setScene(scene);
            stageEdit.show();

            Stage stage = (Stage) anchorPaneCheckIdCard.getScene().getWindow();
            stage.close();
        } else if (!DAOCustomerBookingCheckIn.check_IDCustomer("CTM-" + IdCard.getText())) {
            Stage stageEdit = new Stage();
            stageEdit.resizableProperty().setValue(Boolean.FALSE);
            checkIdCardCustomerAlready = true;
            IdCardCustomer = IdCard.getText();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/FXMLCustomerInfo.fxml"));
            stageEdit.getIcons().add(new Image("/images/KAN Logo.png"));
            Scene scene = new Scene(root);
            stageEdit.setScene(scene);
            stageEdit.show();
            Stage stage = (Stage) anchorPaneCheckIdCard.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void Cancel(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

}
