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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
    private Label LabelContent;

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
    void handleSubmit(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
        FormInfo Form = new FormInfo();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                if (FormInfo.isString(txtLastName.getText()) && FormInfo.isString(txtFirstName.getText()) && FormInfo.validatePhoneNumber(txtPhoneNumber.getText()) && FormInfo.validateEmail(txtEmail.getText()) && FormInfo.isDateValid(dateFormat.format(dateBirthDay.getValue())))
        if (FormInfo.isString(txtLastName.getText()) && FormInfo.isString(txtFirstName.getText()) && FormInfo.validatePhoneNumber(txtPhoneNumber.getText()) && FormInfo.validateEmail(txtEmail.getText())) {
            Form.AddNewCustomer(txtFirstName.getText(), txtLastName.getText(), txtEmail.getText(), txtPhoneNumber.getText(), txtPassport.getText(), dateBirthDay.getValue());
            txtFirstName.setText("");
            txtLastName.setText("");
            txtEmail.setText("");
            txtPhoneNumber.setText("");
            txtPassport.setText("");
            dateBirthDay.setValue(null);
            
            LabelContent.setStyle("-fx-text-fill: green;");
            LabelContent.setText("Success");

        } else {
            handleValidAction();
            System.out.println("false");
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO  
    }

    private void handleValidAction() throws ClassNotFoundException, SQLException, IOException {
        if (txtFirstName.getText().equals("")) {
            LabelContent.setText("Opps! Insert please!");
            txtFirstName.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            txtFirstName.requestFocus();
        } else if (txtLastName.getText().equals("")) {
            LabelContent.setText("Opps! Insert please!");
            txtLastName.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            txtLastName.requestFocus();
        } else if (txtEmail.getText().equals("")) {
            LabelContent.setText("Opps! Insert please!");
            txtEmail.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            txtEmail.requestFocus();
        } else if (txtPhoneNumber.getText().equals("")) {
            LabelContent.setText("Opps! Insert please!");
            txtPhoneNumber.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            txtPhoneNumber.requestFocus();
        } else{
            LabelContent.setText("Opps! String please!");
        }
    }
}
