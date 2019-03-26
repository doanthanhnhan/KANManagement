/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.BookingInfo;
import models.DAO;
import models.DAOCustomerBookingCheckIn;
import models.formatCalender;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class FXMLCheckInOrdersController implements Initializable {

    @FXML
    private AnchorPane anchorPaneCheckInOrders;
    @FXML
    private VBox vBox_Employee_Info;
    @FXML
    private HBox HboxHeader;
    @FXML
    private HBox hBox_Info_Parent;
    @FXML
    private VBox vBox_Info_Left;
    @FXML
    private JFXTextField Username;
    @FXML
    private JFXTextField CheckInID;
    @FXML
    private HBox HboxBoxId;
    @FXML
    private JFXComboBox<String> boxBookingID;
    @FXML
    private JFXTextField RoomID;
    @FXML
    private VBox vBox_Info_Right;
    @FXML
    private JFXTextField NumberOfCustomer;
    @FXML
    private JFXDatePicker CheckInDate;
    @FXML
    private JFXDatePicker LeaveDate;
    @FXML
    private JFXComboBox<?> boxCheckInType;
    @FXML
    private JFXTextField CustomerPackage;
    @FXML
    private HBox HboxContent;
    @FXML
    private HBox Hboxbtn;
    @FXML
    private JFXButton btnInfo;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private JFXTextField CustomerID;
    @FXML
    private FontAwesomeIconView iconRefresh;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LeaveDate.setDisable(true);
//        set box booking id 
        refreshIdUser();
//        set value for box checkin type
        ObservableList list_checkintype = FXCollections.observableArrayList();
        list_checkintype.add("Reception Desk");
        list_checkintype.add("Check In Online");
        list_checkintype.add("Company");
        list_checkintype.add("Booking");
        boxCheckInType.setItems(list_checkintype);
//        set value for username
        Username.setText(FXMLLoginController.User_Login);
//        set value and enter for box booking id
        boxBookingID.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                System.out.println("Enter pressed");
                enter_Submit_Action();
            }
        });

//        install when click box booking id show all info booking
        boxBookingID.valueProperty().addListener((obs, oldItem, newItem) -> {
            System.out.println("Kiem tra newItem: " + newItem);
            if (newItem != null && !newItem.equals("")) {
                LeaveDate.setDisable(false);
                LeaveDate.setDayCellFactory(picker -> new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);
                        LocalDate today = LocalDate.now();

                        setDisable(empty || date.compareTo(today) < 0);
                    }
                });
                BookingInfo bk = new BookingInfo();
                try {
                    bk = DAOCustomerBookingCheckIn.getAllBookingInfo(newItem);
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(FXMLCheckInOrdersController.class.getName()).log(Level.SEVERE, null, ex);
                }
                CustomerID.setText(bk.getCusID());
                NumberOfCustomer.setText(String.valueOf(bk.getNumGuest()));
                RoomID.setText(bk.getRoomID());
                CheckInDate.setValue(LocalDate.now());
                String pattern = "dd-MM-yyyy";
                formatCalender.format(pattern, CheckInDate);
            }
            HboxContent.getChildren().clear();
            boxBookingID.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
        });
        // TODO
    }

    @FXML
    private void Format_Show_Calender_Hiredate(ActionEvent event) {
        String pattern = "dd-MM-yyyy";
        formatCalender.format(pattern, LeaveDate);
    }

    @FXML
    private void Cancel(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    private void enter_Submit_Action() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void refreshIdUser() {
        ObservableList list_Booking_Id = FXCollections.observableArrayList();
        try {
            //        set add booking id for box booking id
            boxBookingID.setItems(DAOCustomerBookingCheckIn.getAllBookingID());
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLCheckInOrdersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
