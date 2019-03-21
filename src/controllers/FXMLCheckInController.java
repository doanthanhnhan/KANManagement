package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import fxml.FXMLFormInforOfGuestController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.DAO;
import models.DAOcheckRole;
import utils.AlertLoginAgain;
import utils.connectDB;
import utils.showFXMLLogin;

public class FXMLCheckInController implements Initializable {

    private showFXMLLogin showFormLogin = new showFXMLLogin();

    private FXMLMainFormController fXMLMainFormController;
    @FXML
    private Label labelName;

    @FXML
    private JFXComboBox<String> RoomIDCombo;

    @FXML
    private JFXComboBox<String> CustomerIDCombo;

    @FXML
    private JFXComboBox<String> BookingIDCombo;

    @FXML
    private Label LabelContent;

    @FXML
    private JFXTextField txtCustomerPackage;

    @FXML
    private JFXComboBox<String> TypeIDCombo;

    @FXML
    private JFXDatePicker datePickerDateLeave;

    @FXML
    private JFXButton btnSubmit;

    @FXML
    private JFXButton BookingCall;

    @FXML
    private JFXButton SkipBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO  
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

        try {
            // TODO
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            String sql = "SELECT BookingID FROM BookingInfo";
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

        TypeIDCombo.getItems().addAll("Standard", "Family", "Deluxe", "Private");
    }

    @FXML
    void BookingCall(ActionEvent event) {

    }

    @FXML
    void SkipBtn(ActionEvent event) {

    }

    @FXML
    void btnSubmit(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
        if (!DAO.checkFirstLogin().equals(0) && !DAOcheckRole.checkRoleDecentralization(FXMLLoginController.User_Login, "Customer_Add")) {
            AlertLoginAgain.alertLogin();
            fXMLMainFormController = ConnectControllers.getfXMLMainFormController();
            Stage stageMainForm = (Stage) fXMLMainFormController.AnchorPaneMainForm.getScene().getWindow();
            Stage stage = (Stage) BookingCall.getScene().getWindow();
            stage.close();
            stageMainForm.close();
            showFormLogin.showFormLogin();
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/FXMLRenewCusFormCheck.fxml"));
            FXMLFormInforOfGuestController fxmlFIGC = fxmlLoader.getController();
        }

    }
}
