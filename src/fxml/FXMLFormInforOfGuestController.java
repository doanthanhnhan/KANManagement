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
    
    //validate String
    private static boolean isString(String str) {
        String expression = "^[a-zA-Z\\s]+";
        return str.matches(expression);
    }
    
    //validate Number
    private static boolean validatePhoneNumber(String phoneNo) {
        //validate phone numbers of format "1234567890"
        if (phoneNo.matches("\\d{10}")) {
            return true;
        } //validating phone number with -, . or spaces
        else if (phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) {
            return true;
        } //validating phone number with extension length from 3 to 5
        else if (phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) {
            return true;
        } //validating phone number where area code is in braces ()
        else if (phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) {
            return true;
        } //return false if nothing matches the input
        else {
            return false;
        }

    }
    
    //validate Email
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    @FXML
    private void handleSubmitAction(ActionEvent event) throws ClassNotFoundException, SQLException, IOException, Exception {
//        local = LocalDate.now();
//        if(bookFirstName.getText().isEmpty() && bookLastName.getText().isEmpty() && bookEmail.getText().isEmpty() && bookPhone.getText().isEmpty() && bookNote.getText().isEmpty() && comboBoxRoom.getValue().isEmpty() && bookDate.getValue().isEqual(local) && bookNumber.getText().isEmpty()){
//            System.out.println("Empty");
//        }
        FormInfo Form = new FormInfo();

        //define check
        boolean check = false;
        if (check1.isSelected()) {
            check = true;
        }
        if (isString(txtFirstName.getText()) && isString(txtLastName.getText()) && validatePhoneNumber(txtPhoneNumber.getText()) && validateEmail(txtEmail.getText()) && validatePhoneNumber(txtNumberofGuest.getText())) {

            Form.AddNewBooking(txtFirstName.getText(), txtLastName.getText(), txtEmail.getText(), txtPhoneNumber.getText(), txtNote.getText(), txtCompanyName.getText(), comboBoxRoom.getValue(), parseInt(txtNumberofGuest.getText()), datePickerDateArrive.getValue(), check, txtFlightNumber.getText());
        }
        else{
            System.out.println("false");
        }
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

    }

    @FXML
    private void handleSubmitAction(MouseEvent event) {
    }

}
