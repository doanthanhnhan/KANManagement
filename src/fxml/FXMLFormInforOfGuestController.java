/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxml;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import models.DAOFormInfo;
import models.formatCalender;
import models.FormInfo;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class FXMLFormInforOfGuestController implements Initializable {

    //define
    ObservableList<String> listComboBoxRoom = FXCollections.observableArrayList("Standard", "Superior", "Deluxe", "Suite");
    @FXML
    private Label labelName;
    @FXML
    private JFXTextField txtFirstName;
    @FXML
    private JFXTextField txtLastName;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXTextField txtPhoneNumber;
    @FXML
    private JFXTextField txtNote;
    @FXML
    private JFXTextField txtCompanyName;
    @FXML
    private JFXComboBox<String> comboBoxRoom;
    @FXML
    private JFXTextField txtNumberofGuest;
    @FXML
    private JFXDatePicker datePickerDateArrive;
    @FXML
    private JFXCheckBox check1;
    @FXML
    private JFXTextField txtFlightNumber;
    @FXML
    private JFXButton btnSubmit;
    @FXML
    private Label LabelContent;
    /**
     * Initializes the controller class.
     */
    LocalDate local = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        comboBoxRoom.setItems(listComboBoxRoom);

        check1.setSelected(true);
    }

    @FXML
    private void handleSubmitAction(ActionEvent event) throws ClassNotFoundException, SQLException, IOException, Exception {
//        local = LocalDate.now();
//        if(bookFirstName.getText().isEmpty() && bookLastName.getText().isEmpty() && bookEmail.getText().isEmpty() && bookPhone.getText().isEmpty() && bookNote.getText().isEmpty() && comboBoxRoom.getValue().isEmpty() && bookDate.getValue().isEqual(local) && bookNumber.getText().isEmpty()){
//            System.out.println("Empty");
//        }
        FormInfo Form = new FormInfo();
        
        LabelContent.setStyle("-fx-text-fill: red;");

        //define check
        boolean check = false;
        if (check1.isSelected()) {
            check = true;
        }
        if (Form.isString(txtFirstName.getText()) && Form.isString(txtLastName.getText()) && Form.validatePhoneNumber(txtPhoneNumber.getText()) && Form.validateEmail(txtEmail.getText()) && !comboBoxRoom.getValue().isEmpty() && FormInfo.validateNumber(txtNumberofGuest.getText()) && !datePickerDateArrive.getValue().equals(null)) {
            
            Form.AddNewBooking(txtFirstName.getText(), txtLastName.getText(), txtEmail.getText(), txtPhoneNumber.getText(), txtNote.getText(), txtCompanyName.getText(), comboBoxRoom.getValue(), parseInt(txtNumberofGuest.getText()), datePickerDateArrive.getValue(), check, txtFlightNumber.getText());
            
            txtFirstName.setText("");
            txtLastName.setText("");
            txtEmail.setText("");
            txtPhoneNumber.setText("");
            txtNote.setText("");
            txtCompanyName.setText("");
            comboBoxRoom.setValue(null);
            txtNumberofGuest.setText("");
            datePickerDateArrive.setValue(null);
            txtFlightNumber.setText("");
            
            LabelContent.setStyle("-fx-text-fill: green;");
            LabelContent.setText("Success");
        } else {
            handleValidAction();
            System.out.println("false");
            System.out.println(FormInfo.isString(txtFirstName.getText()));
            System.out.println(FormInfo.validatePhoneNumber(txtPhoneNumber.getText()));
            System.out.println(FormInfo.validateEmail(txtEmail.getText()));
            System.out.println(!comboBoxRoom.getValue().isEmpty());
            System.out.println(FormInfo.validateNumber(txtNumberofGuest.getText()));
            System.out.println(!datePickerDateArrive.getValue().equals(null));
        }

    }

    private void handleValidAction() throws ClassNotFoundException, SQLException, IOException {
        if (txtFirstName.getText().isEmpty() || !FormInfo.isString(txtFirstName.getText())) {
            LabelContent.setText("Opps! First Name has some problems!");
            txtFirstName.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            txtFirstName.requestFocus();
        } else if (txtLastName.getText().isEmpty() || !FormInfo.isString(txtLastName.getText())) {
            LabelContent.setText("Opps! Last Name has some problems!");
            txtLastName.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            txtLastName.requestFocus();
        } else if (txtEmail.getText().isEmpty() || !FormInfo.validateEmail(txtEmail.getText())) {
            LabelContent.setText("Opps! Email has some problems!");
            txtEmail.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            txtEmail.requestFocus();
        } else if (txtPhoneNumber.getText().isEmpty() || !FormInfo.validatePhoneNumber(txtPhoneNumber.getText())) {
            LabelContent.setText("Opps! Phone Number wrong!");
            txtPhoneNumber.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            txtPhoneNumber.requestFocus();
        } else if (txtNumberofGuest.getText().isEmpty() || !FormInfo.validateNumber(txtNumberofGuest.getText())){
            LabelContent.setText("Opps! Number of Guests can not be wrong!");
            txtNumberofGuest.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            txtNumberofGuest.requestFocus();
        } else if(comboBoxRoom.getValue().isEmpty()){
            LabelContent.setText("Opps! Pick a room!");
            comboBoxRoom.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            comboBoxRoom.requestFocus();
        } else if(datePickerDateArrive.getValue().equals(null)){
            LabelContent.setText("Opps! Date wrong!");
            datePickerDateArrive.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            datePickerDateArrive.requestFocus();
        } else {
            LabelContent.setText("Opps! Something went wrong!");
        }
    }
}
