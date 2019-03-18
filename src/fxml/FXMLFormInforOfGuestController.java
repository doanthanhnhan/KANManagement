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
import controllers.FXMLCheckInRoomController;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.DAOFormInfo;
import models.formatCalender;
import models.FormInfo;
import utils.connectDB;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class FXMLFormInforOfGuestController implements Initializable {

    //define
//    ObservableList<String> listComboBoxRoom = FXCollections.observableArrayList("Standard", "Superior", "Deluxe", "Suite");
//    @FXML
//    private JFXComboBox<String> RoomIDCombo;
//
//    @FXML
//    private JFXComboBox<String> CustomerIDCombo;
//    @FXML
//    private Label labelName;
//    @FXML
//    private JFXTextField txtFirstName;
//    @FXML
//    private JFXTextField txtLastName;
//    @FXML
//    private JFXTextField txtEmail;
//    @FXML
//    private JFXTextField txtPhoneNumber;
//    @FXML
//    private JFXTextField txtNote;
//    @FXML
//    private JFXTextField txtCompanyName;
//    @FXML
//    private JFXComboBox<String> comboBoxRoom;
//    @FXML
//    private JFXTextField txtNumberofGuest;
//    @FXML
//    private JFXDatePicker datePickerDateArrive;
//    @FXML
//    private JFXCheckBox check1;
//    @FXML
//    private JFXTextField txtFlightNumber;
//    @FXML
//    private JFXButton btnSubmit;
//    @FXML
//    private Label LabelContent;
    @FXML
    private JFXComboBox<String> RoomIDCombo;

    @FXML
    private JFXComboBox<String> CustomerIDCombo;

    @FXML
    private JFXDatePicker datePickerDateArrive;

    @FXML
    private Label LabelContent;

    @FXML
    private JFXTextField txtNumberofGuest;

    @FXML
    private JFXCheckBox check1;

    @FXML
    private JFXTextField txtFlightNumber;

    @FXML
    private JFXButton btnSubmit;

    @FXML
    private JFXButton CustomerCall;

    @FXML
    private JFXTextField txtNote;

    Boolean CheckEdit = false;
    String ID;
    /**
     * Initializes the controller class.
     */
    LocalDate local = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        check1.setSelected(true);

        try {
            // TODO
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            String sql = "SELECT CustomerID FROM Customers";
            conn = connectDB.connectSQLServer();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                CustomerIDCombo.getItems().addAll(rs.getString("CustomerID"));
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLCheckInRoomController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLCheckInRoomController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            // TODO
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            String sql = "SELECT RoomID FROM Rooms";
            conn = connectDB.connectSQLServer();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                RoomIDCombo.getItems().addAll(rs.getString("RoomID"));
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLCheckInRoomController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLCheckInRoomController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleSubmitAction(ActionEvent event) throws ClassNotFoundException, SQLException, IOException, Exception {
        if (!CheckEdit) {
            FormInfo Form = new FormInfo();

            LabelContent.setStyle("-fx-text-fill: red;");

            if (!RoomIDCombo.getValue().isEmpty() && !CustomerIDCombo.getValue().isEmpty() && FormInfo.validateNumber(txtNumberofGuest.getText()) && !datePickerDateArrive.getValue().equals(null)) {

                Form.AddNewBooking(txtNote.getText(), parseInt(txtNumberofGuest.getText()), datePickerDateArrive.getValue(), check1.isSelected(), txtFlightNumber.getText(), RoomIDCombo.getValue(), CustomerIDCombo.getValue());

                txtNumberofGuest.setText("");
                datePickerDateArrive.setValue(null);
                txtFlightNumber.setText("");

                LabelContent.setStyle("-fx-text-fill: green;");
                LabelContent.setText("Success");
            } else {
                handleValidAction();
            }
        } else {
            FormInfo Form = new FormInfo();

            LabelContent.setStyle("-fx-text-fill: red;");

            if (!RoomIDCombo.getValue().isEmpty() && !CustomerIDCombo.getValue().isEmpty() && FormInfo.validateNumber(txtNumberofGuest.getText()) && !datePickerDateArrive.getValue().equals(null)) {
                String ID = "";
                Form.EditBooking(txtNote.getText(), parseInt(txtNumberofGuest.getText()), datePickerDateArrive.getValue(), check1.isSelected(), txtFlightNumber.getText(), ID);

                txtNumberofGuest.setText("");
                datePickerDateArrive.setValue(null);
                txtFlightNumber.setText("");

                LabelContent.setStyle("-fx-text-fill: green;");
                LabelContent.setText("Success");
            } else {
                handleValidAction();
            }
        }

    }

    private void handleValidAction() throws ClassNotFoundException, SQLException, IOException {
        if (datePickerDateArrive.getValue().equals(null)) {
            LabelContent.setText("Opps! Date wrong!");
            datePickerDateArrive.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            datePickerDateArrive.requestFocus();
        } else {
            LabelContent.setText("Opps! Something went wrong!");
        }
    }

    public void EditBtnCheckInRoom(String Note, String Company, String RoomType, String Number, String Flight) {
        txtNumberofGuest.setText(Number);
        datePickerDateArrive.setValue(null);
        txtFlightNumber.setText(Flight);
        this.ID = ID;
        CheckEdit = true;
    }
    public void AddDataFromCustomer(String CusID){
        CustomerIDCombo.setValue(CusID);
    }
    @FXML
    private void CustomerCall(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/FXMLCustomer.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();

        Stage stage = (Stage) CustomerCall.getScene().getWindow();
        stage.close();
        
        Stage stage1 = new Stage();
        stage1.setScene(new Scene(root1));
        stage1.show();
        
    }
}
