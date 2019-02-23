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
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        comboBoxRoom.setItems(listComboBoxRoom);
        comboBoxRoom.setValue("Room");

        check1.setSelected(true);
//        if(bookFirstName.getText().isEmpty() && bookLastName.getText().isEmpty() && bookEmail.getText().isEmpty() && bookPhone.getText().isEmpty() && bookNote.getText().isEmpty() && comboBoxRoom.getValue().isEmpty() && bookDate.getValue().isEqual(local) && bookNumber.getText().isEmpty()){
//            btnSubmit.setDisable(true);
//        }
    }
    Date local;

    @FXML
    private void handleSubmitAction(ActionEvent event) throws ClassNotFoundException, SQLException, IOException, Exception {
//        local = LocalDate.now();
//        if(bookFirstName.getText().isEmpty() && bookLastName.getText().isEmpty() && bookEmail.getText().isEmpty() && bookPhone.getText().isEmpty() && bookNote.getText().isEmpty() && comboBoxRoom.getValue().isEmpty() && bookDate.getValue().isEqual(local) && bookNumber.getText().isEmpty()){
//            System.out.println("Empty");
//        }
        FormInfo Form = new FormInfo();
        boolean check = false;
        if (check1.isSelected()) {
            check = true;
        }
        System.out.println(check);
        System.out.println(txtFirstName.getText());
        System.out.println(txtLastName.getText());
        System.out.println(txtEmail.getText());
        System.out.println(txtPhoneNumber.getText());
        System.out.println(txtNote.getText());
        System.out.println(txtCompanyName.getText());
        System.out.println(comboBoxRoom.getValue());
        System.out.println(txtNumberofGuest.getText());
        System.out.println(datePickerDateArrive.getValue());
        System.out.println(txtFlightNumber.getText());
        Form.AddNewBooking(txtFirstName.getText(), txtLastName.getText(), txtEmail.getText(), txtPhoneNumber.getText(), txtNote.getText(), txtCompanyName.getText(), comboBoxRoom.getValue(), parseInt(txtNumberofGuest.getText()), datePickerDateArrive.getValue(), check, txtFlightNumber.getText());
    }

    @FXML
    private void handleSubmitAction(MouseEvent event) {
    }

}
