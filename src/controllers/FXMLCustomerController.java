/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fxml.FXMLFormInforOfGuestController;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
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
    private JFXTextField txtDiscount;

    @FXML
    private JFXButton btnSubmit;

    @FXML
    private Label labelName;

    @FXML
    private HBox hboxContent;

    @FXML
    private JFXRadioButton sexMale;

    @FXML
    private ToggleGroup SexGroup;

    @FXML
    private JFXRadioButton sexFemale;

    @FXML
    private JFXTextField txtCompany;

    @FXML
    private JFXButton BookingCall;

    @FXML
    void BookingCall(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/FXMLFormInforOfGuest.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();

        Stage stage = (Stage) BookingCall.getScene().getWindow();
        stage.close();

        Stage stage1 = new Stage();
        stage1.setScene(new Scene(root1));
        stage1.show();

    }

    @FXML
    void handleSubmit(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/FXMLFormInforOfGuest.fxml"));
        FXMLFormInforOfGuestController fxmlFIGC = fxmlLoader.getController();

        FormInfo Form = new FormInfo();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        LabelContent.setStyle("-fx-text-fill: red;");
//                if (FormInfo.isString(txtLastName.getText()) && FormInfo.isString(txtFirstName.getText()) && FormInfo.validatePhoneNumber(txtPhoneNumber.getText()) && FormInfo.validateEmail(txtEmail.getText()) && FormInfo.isDateValid(dateFormat.format(dateBirthDay.getValue())))
        if (FormInfo.isString(txtLastName.getText()) && FormInfo.isString(txtFirstName.getText()) && FormInfo.validatePhoneNumber(txtPhoneNumber.getText()) && FormInfo.validateEmail(txtEmail.getText())) {
            boolean sex = sexMale.isSelected();
            System.out.println(Float.parseFloat(txtDiscount.getText()));
            Form.AddNewCustomer(txtFirstName.getText(), txtLastName.getText(), txtEmail.getText(), txtPhoneNumber.getText(), txtPassport.getText(), dateBirthDay.getValue(), txtCompany.getText(), sex, "admin", Float.parseFloat(txtDiscount.getText()));
            txtFirstName.setText("");
            txtLastName.setText("");
            txtEmail.setText("");
            txtPhoneNumber.setText("");
            txtPassport.setText("");
            dateBirthDay.setValue(null);
            txtCompany.setText("");
            txtDiscount.setText("");

            LabelContent.setStyle("-fx-text-fill: green;");
            LabelContent.setText("Success");
            Parent root1 = (Parent) fxmlLoader.load();
            
//            fxmlFIGC.AddDataFromCustomer(FormInfo.getCusID());
            Stage stage = (Stage) btnSubmit.getScene().getWindow();
            stage.close();

            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root1));
            stage1.show();

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
        } else {
            LabelContent.setText("Opps! String please!");
        }
    }
}
