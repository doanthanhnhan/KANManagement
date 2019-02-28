/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import models.FormInfo;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class FXMLCustomerController implements Initializable {

    @FXML
    private JFXTextField txtFirstName;

    @FXML
    private JFXTextField txtLastName;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtPassport;

    @FXML
    private JFXTextField txtPhoneNumber;

    @FXML
    private JFXDatePicker dateBirthDay;

    @FXML
    private JFXButton btnSubmit;
    
    @FXML
    private Label labelName;
    
    @FXML
    private HBox hboxContent;

    @FXML
    void handleSubmit(ActionEvent event) {
        FormInfo Form = new FormInfo();

        //define check
        if (Form.isString(txtFirstName.getText()) && Form.isString(txtLastName.getText()) && Form.validatePhoneNumber(txtPhoneNumber.getText()) && Form.validateEmail(txtEmail.getText())) {
            Form.AddNewCustomer(txtFirstName.getText(), txtLastName.getText(), txtEmail.getText(), txtPhoneNumber.getText(), txtPassport.getText(), dateBirthDay.getValue());

        } else {
            System.out.println("false");
        }
        txtFirstName.setText("");
        txtLastName.setText("");
        txtEmail.setText("");
        txtPhoneNumber.setText("");
        txtPassport.setText("");
        dateBirthDay.setValue(null);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtFirstName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                hboxContent.getChildren().clear();
                txtFirstName.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        handleAction();
                    } catch (ClassNotFoundException | SQLException | IOException ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        txtLastName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                txtLastName.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                hboxContent.getChildren().clear();
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        handleAction();
                    } catch (ClassNotFoundException | SQLException | IOException ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        txtEmail.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                txtEmail.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                hboxContent.getChildren().clear();
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        handleAction();
                    } catch (ClassNotFoundException | SQLException | IOException ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        txtPhoneNumber.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                txtPhoneNumber.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                hboxContent.getChildren().clear();
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        handleAction();
                    } catch (ClassNotFoundException | SQLException | IOException ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    @FXML
    private void handleAction() throws ClassNotFoundException, SQLException, IOException {
        FormInfo Form = new FormInfo();
        if (Form.isString(txtFirstName.getText())) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(300, 35);
            label.setText("Opps! String please!");
            txtFirstName.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            hboxContent.setAlignment(Pos.CENTER);
            hboxContent.setSpacing(10);
            hboxContent.getChildren().clear();
            hboxContent.getChildren().add(icon);
            hboxContent.getChildren().add(label);
            txtFirstName.requestFocus();
        }else if(txtFirstName.getText().equals("")){
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(300, 35);
            label.setText("Opps! Insert please!");
            txtFirstName.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            hboxContent.setAlignment(Pos.CENTER);
            hboxContent.setSpacing(10);
            hboxContent.getChildren().clear();
            hboxContent.getChildren().add(icon);
            hboxContent.getChildren().add(label);
            txtFirstName.requestFocus();
        }else if(Form.isString(txtLastName.getText())){
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(300, 35);
            label.setText("Opps! String please!");
            txtLastName.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            hboxContent.setAlignment(Pos.CENTER);
            hboxContent.setSpacing(10);
            hboxContent.getChildren().clear();
            hboxContent.getChildren().add(icon);
            hboxContent.getChildren().add(label);
            txtLastName.requestFocus();
        }else if(txtLastName.getText().equals("")){
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(300, 35);
            label.setText("Opps! Insert please!");
            txtLastName.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            hboxContent.setAlignment(Pos.CENTER);
            hboxContent.setSpacing(10);
            hboxContent.getChildren().clear();
            hboxContent.getChildren().add(icon);
            hboxContent.getChildren().add(label);
            txtLastName.requestFocus();
        }
    }
}
