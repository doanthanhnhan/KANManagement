/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import models.BookingInfo;
import models.CheckIn;
import models.CheckInRoomDAOImple;
import models.Customer;
import models.boolDecentralizationModel;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class FXMLRenewCusFormCheckController implements Initializable {

    @FXML
    private JFXButton customerBtn;

    @FXML
    private JFXButton bookingBtn;

    @FXML
    private JFXButton checkinBtn;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private JFXButton searchBtn;

    @FXML
    private ContextMenu checkinMenu;
    @FXML
    private ContextMenu bookingMenu;
    @FXML
    private ContextMenu customerMenu;
    @FXML
    private Pane checkinPane;

    @FXML
    private TableView<CheckIn> TableCheckIn;

    @FXML
    private MenuItem addNewCKI;

    @FXML
    private MenuItem deleteCKI;

    @FXML
    private MenuItem editCKI;

    @FXML
    private Pane customerPane;

    @FXML
    private TableView<Customer> TableCus;

    @FXML
    private MenuItem addNewCus;

    @FXML
    private MenuItem deleteCus;

    @FXML
    private MenuItem editCus;

    @FXML
    private Pane bookingPane;

    @FXML
    private TableView<BookingInfo> TableBook;

    @FXML
    private MenuItem addNewBook;

    @FXML
    private MenuItem deleteBook;

    @FXML
    private MenuItem editBook;

    public boolDecentralizationModel userRole;

    boolean checkIn = false, booking = false, customer = true;

    @FXML
    void bookingBtnClicked(ActionEvent event) {

    }

    @FXML
    void checkinBtnClicked(ActionEvent event) {

    }

    @FXML
    void customerBtnClicked(ActionEvent event) {

    }

    @FXML
    void searchingAct(ActionEvent event) {

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if (checkIn) {
            if (!userRole.ischeckCheckIn_Add()){
                checkinMenu.getItems().remove(addNewCKI);
            }
            if (!userRole.ischeckCheckIn_Edit()){
                checkinMenu.getItems().remove(editCKI);
            }
            if (!userRole.ischeckCheckIn_Delete()){
                checkinMenu.getItems().remove(deleteCKI);
            }
            System.out.println("checkin");
            TableColumn<CheckIn, String> checkID = new TableColumn<>("Check In ID");
            checkID.setMinWidth(70);
            TableColumn<CheckIn, String> bookID = new TableColumn<>("Book ID");
            bookID.setMinWidth(70);
            TableColumn<CheckIn, String> cusID = new TableColumn<>("Customer ID");
            cusID.setMinWidth(70);
            TableColumn<CheckIn, String> roomID = new TableColumn<>("Room ID");
            roomID.setMinWidth(70);
            TableColumn<CheckIn, String> checkType = new TableColumn<>("Check In Type");
            checkType.setMinWidth(100);
            TableColumn<CheckIn, String> cusPack = new TableColumn<>("Customer Package");
            cusPack.setMinWidth(100);
            TableColumn<CheckIn, LocalDateTime> dateIn = new TableColumn<>("Check In Date");
            dateIn.setMinWidth(100);
            TableColumn<CheckIn, LocalDateTime> dateOut = new TableColumn<>("Leave Date");
            dateOut.setMinWidth(100);
            TableColumn<CheckIn, Integer> quantity = new TableColumn<>("Quantity");
            quantity.setMinWidth(50);

            checkID.setCellValueFactory(new PropertyValueFactory<>("CheckID"));
            bookID.setCellValueFactory(new PropertyValueFactory<>("BookID"));
            cusID.setCellValueFactory(new PropertyValueFactory<>("CusID"));
            roomID.setCellValueFactory(new PropertyValueFactory<>("RoomID"));
            checkType.setCellValueFactory(new PropertyValueFactory<>("CheckType"));
            cusPack.setCellValueFactory(new PropertyValueFactory<>("CusPack"));
            dateIn.setCellValueFactory(new PropertyValueFactory<>("DateIn"));
            dateOut.setCellValueFactory(new PropertyValueFactory<>("DateOut"));
            quantity.setCellValueFactory(new PropertyValueFactory<>("NumGuest"));

            TableCheckIn.getColumns().clear();
            TableCheckIn.getColumns().addAll(checkID, bookID, cusID, roomID, checkType, cusPack, dateIn, dateOut, quantity);

            try {
                TableCheckIn.setItems(CheckInRoomDAOImple.listAddTableCheckIn());
            } catch (SQLException ex) {
                Logger.getLogger(FXMLCheckInRoomController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (customer) {
            if (!userRole.ischeckCustomer_Add()){
                customerMenu.getItems().remove(addNewCus);
            }
            if (!userRole.ischeckCustomer_Edit()){
                customerMenu.getItems().remove(editCus);
            }
            if (!userRole.ischeckCustomer_Delete()){
                customerMenu.getItems().remove(deleteCus);
            }
            System.out.println("customer");
            TableColumn<Customer, String> cusID = new TableColumn<>("Customer ID");
            TableColumn<Customer, String> cusFname = new TableColumn<>("First Name");
            TableColumn<Customer, String> cusMname = new TableColumn<>("Mid Name");
            TableColumn<Customer, String> cusLname = new TableColumn<>("Last Name");
            TableColumn<Customer, String> cusMail = new TableColumn<>("Email");
            TableColumn<Customer, String> cusPassport = new TableColumn<>("Passport");
            TableColumn<Customer, String> cusCompany = new TableColumn<>("Company");
            TableColumn<Customer, LocalDateTime> cusBirth = new TableColumn<>("Birth Day");
            TableColumn<Customer, String> cusPhone = new TableColumn<>("Phone");
            TableColumn<Customer, Boolean> cusSex = new TableColumn<>("Sex");
            TableColumn<Customer, Float> cusDiscount = new TableColumn<>("Discount");

            cusID.setCellValueFactory(new PropertyValueFactory<>("CusID"));
            cusFname.setCellValueFactory(new PropertyValueFactory<>("FName"));
            cusMname.setCellValueFactory(new PropertyValueFactory<>("MName"));
            cusLname.setCellValueFactory(new PropertyValueFactory<>("LName"));
            cusMail.setCellValueFactory(new PropertyValueFactory<>("Email"));
            cusPassport.setCellValueFactory(new PropertyValueFactory<>("Passport"));
            cusCompany.setCellValueFactory(new PropertyValueFactory<>("Company"));
            cusBirth.setCellValueFactory(new PropertyValueFactory<>("Date"));
            cusPhone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
            cusSex.setCellValueFactory(new PropertyValueFactory<>("Sex"));
            cusDiscount.setCellValueFactory(new PropertyValueFactory<>("Discount"));

            TableCus.getColumns().clear();
            TableCus.getColumns().addAll(cusID, cusFname, cusMname, cusLname, cusMail, cusPhone, cusPassport, cusCompany, cusBirth, cusSex, cusDiscount);

            try {
                TableCus.setItems(CheckInRoomDAOImple.listCustomer());
            } catch (SQLException ex) {
                Logger.getLogger(FXMLCheckInRoomController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (booking) {
            if (!userRole.ischeckBooking_Add()){
                bookingMenu.getItems().remove(addNewBook);
            }
            if (!userRole.ischeckBooking_Edit()){
                bookingMenu.getItems().remove(editBook);
            }
            if (!userRole.ischeckBooking_Delete()){
                bookingMenu.getItems().remove(deleteBook);
            }
            System.out.println("booking");
            TableColumn<BookingInfo, String> bookID = new TableColumn<>("Booking ID");
            TableColumn<BookingInfo, String> cusID = new TableColumn<>("Customer ID");
            TableColumn<BookingInfo, String> roomID = new TableColumn<>("Room ID");
            TableColumn<BookingInfo, String> note = new TableColumn<>("Note");
            TableColumn<BookingInfo, String> flight = new TableColumn<>("Flight Number");
            TableColumn<BookingInfo, LocalDateTime> date = new TableColumn<>("Date");
            TableColumn<BookingInfo, Boolean> drive = new TableColumn<>("Drive");
            TableColumn<BookingInfo, Integer> numGuest = new TableColumn<>("Quantity");

            bookID.setCellValueFactory(new PropertyValueFactory<>("BookID"));
            cusID.setCellValueFactory(new PropertyValueFactory<>("CusID"));
            roomID.setCellValueFactory(new PropertyValueFactory<>("RoomID"));
            note.setCellValueFactory(new PropertyValueFactory<>("Note"));
            flight.setCellValueFactory(new PropertyValueFactory<>("Flight"));
            date.setCellValueFactory(new PropertyValueFactory<>("Date"));
            drive.setCellValueFactory(new PropertyValueFactory<>("Drive"));
            numGuest.setCellValueFactory(new PropertyValueFactory<>("NumGuest"));

            TableBook.getColumns().clear();
            TableBook.getColumns().addAll(bookID, cusID, roomID, note, flight, date, drive, numGuest);

            try {
                TableBook.setItems(CheckInRoomDAOImple.listBooking());
            } catch (SQLException ex) {
                Logger.getLogger(FXMLCheckInRoomController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("out");
        }
    }

}
