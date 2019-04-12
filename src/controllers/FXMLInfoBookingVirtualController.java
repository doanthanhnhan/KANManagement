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
import static controllers.FXMLInfoBookingController.checkInfoBooking;
import static controllers.FXMLListBookingVirtualController.bk;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.BookingInfo;
import models.DAO;
import models.DAOCustomerBookingCheckIn;
import models.formatCalender;
import utils.GetInetAddress;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class FXMLInfoBookingVirtualController implements Initializable {

    @FXML
    private AnchorPane anchorPaneBookingVirtual;
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
    private HBox HboxBoxId;
    @FXML
    private JFXComboBox<String> BookingID;
    @FXML
    private JFXTextField boxIdCustomer;
    @FXML
    private JFXTextField boxIdRoom;
    @FXML
    private VBox vBox_Info_Right;
    @FXML
    private JFXDatePicker DateBook;
    @FXML
    private JFXTextField NumberGuest;
    @FXML
    private JFXTextField Note;
    @FXML
    private HBox HboxContent;
    @FXML
    private HBox Hboxbtn;
    @FXML
    private JFXButton btnInfo;
    @FXML
    private JFXButton btnCancel;
    private ObservableList<BookingInfo> list_Booking_Info = FXCollections.observableArrayList();
    private FXMLListBookingController fXMLListBookingController;
    public static boolean checkInfoBookingVirtual = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        checkInfoBookingVirtual=true;
        btnInfo.setOnAction((event) -> {
            delete();
        });
        boxIdCustomer.setText(FXMLInfoBookingController.CusID_BookingFuture);
        list_Booking_Info = DAOCustomerBookingCheckIn.check_ListbookingFuture(FXMLInfoBookingController.CusID_BookingFuture);
        ObservableList<String> list_booking_id = FXCollections.observableArrayList();
        for (BookingInfo bk : list_Booking_Info) {
            list_booking_id.add(bk.getBookID());
        }
        BookingID.setItems(list_booking_id);
//            set value for text field when list booking info have size = 1
        for (BookingInfo bk : list_Booking_Info) {
            checkInfoBooking = true;
            BookingID.setDisable(false);
            BookingID.setValue(bk.getBookID());
            boxIdRoom.setText(bk.getRoomID());
            DateBook.setDisable(true);
            DateBook.setValue(LocalDate.parse(bk.getDate()));
            boxIdCustomer.setText(FXMLInfoBookingController.CusID_BookingFuture);;
            NumberGuest.setText(String.valueOf(bk.getNumGuest()));
            Note.setText(bk.getNote());
            NumberGuest.setDisable(true);
            Note.setDisable(true);
            String pattern = "dd-MM-yyyy";
            formatCalender.format(pattern, DateBook);
            break;
        }
        if (list_Booking_Info.size() > 1) {
            BookingID.valueProperty().addListener((obs, oldItem, newItem) -> {
                if (newItem != null && !newItem.equals("")) {
                    int i;
                    for (i = 0; i < list_Booking_Info.size(); i++) {
                        if (newItem.equals(list_Booking_Info.get(i).getBookID())) {
                            checkInfoBooking = true;
                            boxIdCustomer.setDisable(true);
                            BookingID.setDisable(false);
                            BookingID.setValue(list_Booking_Info.get(i).getBookID());
                            boxIdRoom.setText(list_Booking_Info.get(i).getRoomID());
                            DateBook.setDisable(true);
                            DateBook.setValue(LocalDate.parse(list_Booking_Info.get(i).getDate()));
                            boxIdCustomer.setText(FXMLInfoBookingController.CusID_BookingFuture);;
                            NumberGuest.setText(String.valueOf(list_Booking_Info.get(i).getNumGuest()));
                            Note.setText(list_Booking_Info.get(i).getNote());
                            NumberGuest.setDisable(true);
                            Note.setDisable(true);
                            String pattern = "dd-MM-yyyy";
                            formatCalender.format(pattern, DateBook);
                            break;
                        }
                    }
                }
            });
        }
    }

    @FXML
    private void Format_Show_Calender(ActionEvent event) {
        String pattern = "dd-MM-yyyy";
        formatCalender.format(pattern, DateBook);
    }

    @FXML
    private void Cancel() {
        Stage stage = (Stage) anchorPaneBookingVirtual.getScene().getWindow();
        stage.close();
        Stage stageEdit = new Stage();
        stageEdit.resizableProperty().setValue(Boolean.FALSE);
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/FXMLCheckInOrders.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        stageEdit.getIcons().add(new Image("/images/KAN Logo.png"));
        Scene scene = new Scene(root);
        stageEdit.setScene(scene);
        stageEdit.setOnCloseRequest((event) -> {
            checkInfoBookingVirtual=false;
            FXMLInfoBookingController.checkInfoBooking = false;
        });
        stageEdit.show();
    }

    public void delete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Comfirm");
        alert.setContentText("Do you want to delete " + BookingID.getValue() + "?");
        alert.showAndWait();
        System.out.println(alert.getResult());
        if (alert.getResult() == ButtonType.OK) {
            try {
                DAOCustomerBookingCheckIn.deleteBooking(BookingID.getValue());
                DAO.setUserLogs_With_MAC(FXMLLoginController.User_Login, "Delete " + BookingID.getValue() + " because this customerID = " + boxIdCustomer.getText() + " is checking in early",
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), GetInetAddress.getMacAddress());
                System.out.println("Delete successful");
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(FXMLListEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (FXMLListBookingController.check_formBooking_list) {
                System.out.println("vao check form");
                fXMLListBookingController = ConnectControllers.getfXMLListBookingController();
                fXMLListBookingController.showUsersData();
            }
        }
        Stage stage = (Stage) anchorPaneBookingVirtual.getScene().getWindow();
        stage.close();
        Stage stageEdit = new Stage();
        stageEdit.resizableProperty().setValue(Boolean.FALSE);
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/FXMLCheckInOrders.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        stageEdit.getIcons().add(new Image("/images/KAN Logo.png"));
        Scene scene = new Scene(root);
        stageEdit.setScene(scene);
        stageEdit.setOnCloseRequest((event) -> {
            FXMLInfoBookingController.checkInfoBooking = false;
        });

        stageEdit.show();
    }
}
