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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.CheckIn;
import models.CheckInRoomDAOImple;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class FXMLCheckInRoomController implements Initializable {

    @FXML
    private JFXButton serachBtn;

    @FXML
    private JFXTextField txtID;

    @FXML
    private TableView<CheckIn> TableViewData;

    @FXML
    private MenuItem DeleteBtn;

    @FXML
    private MenuItem EditBtn;

    @FXML
    private JFXButton CheckInBtn;

    @FXML
    private JFXButton BookingBtn;

    @FXML
    private JFXButton CustomerBtn;

    boolean checkIn = true, Booking = false, Customer = false;

    @FXML
    void handleDeleteMenu(ActionEvent event) throws SQLException {

//        ObservableList<CheckInRoom> cir = TableViewData.getSelectionModel().getSelectedItems();
//        for (CheckInRoom checkInRoom : cir) {
//            CheckInRoomDAOImple.deleteDataCIR(checkInRoom.getID());
//        };
//        TableViewData.setItems(CheckInRoomDAOImple.listAddTableCheckIn());

    }

    @FXML
    void handleEditMenu(ActionEvent event){
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/FXMLFormInforOfGuest.fxml"));
//            Parent root1 = (Parent) fxmlLoader.load();
//
//            FXMLFormInforOfGuestController fxmlFIGC = fxmlLoader.getController();
//            ObservableList<CheckInRoom> cir = TableViewData.getSelectionModel().getSelectedItems();
//            for (CheckInRoom checkInRoom : cir) {
//                String[] part = checkInRoom.getName().split("\\s", 2);
////                fxmlFIGC.EditBtnCheckInRoom(checkInRoom.getID(), part[0], part[1], checkInRoom.getMail(), checkInRoom.getPhone(), checkInRoom.getNote(), checkInRoom.getCompany(), checkInRoom.getRoomType(), checkInRoom.getNum(), checkInRoom.getFlight());
//            };
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root1));
//            stage.show();
//        } catch (Exception e) {
//            System.out.println("Cant load");
//        }
//
//        TableViewData.getItems().clear();
//        TableViewData.setItems(CheckInRoomDAOImple.listAddTableCheckIn());
    }

    @FXML
    void handleSearchBtn(ActionEvent event) throws SQLException {
//        ObservableList<CheckInRoom> list = CheckInRoomDAOImple.listCheckIn(CheckInRoomDAOImple.getAllDataBooking());
//        ObservableList<CheckInRoom> list1 = FXCollections.observableArrayList();
//
//        TableViewData.getItems().clear();
//
//        char[] textSearch = txtID.getText().replaceAll("\\s", "").toCharArray();
//
//        if (FormInfo.isString(txtID.getText())) {
//
//            int available[] = new int[list.size()];
//            int max = 0;
//            int count = 0;
//            boolean check = true;
//            for (CheckInRoom cir : list) {
//                if (cir.getName().contains(txtID.getText())) {
//                    list1.add(cir);
//                } else {
//
//                    //finding fullname
////                if (CheckInRoomDAOImple.removeAccent(cir.getName().toLowerCase()).replaceAll("\\s", "").equals(txtID.getText().replaceAll("\\s", ""))) {
////                    list1.add(cir);
////                } else check = false;
////                if (!check) {
////                    //finding character
//                    char[] name = CheckInRoomDAOImple.removeAccent(cir.getName().toLowerCase()).replaceAll("\\s", "").toCharArray();
//                    int i = 0;
//                    System.out.println(name);
//                    System.out.println(textSearch);
//
//                    for (char c : textSearch) {
//                        for (char d : name) {
//                            if (c == d && Character.isLetter(c) && Character.isLetter(d)) {
//                                i++;
//                                d = ' ';
//                                break;
//                            } else {
//                                check = false;
//                            }
//                        }
//                    }
//                    if (i > max) {
//                        max = i;
//                    }
//                    available[count] = i;
//                    count++;
//                }
////                }
//            }
//            count = 0;
//            for (CheckInRoom cir : list) {
//                if (available[count] == max) {
//                    list1.add(cir);
//                }
//                count++;
//            }
//
//            //show to tableview
//            System.out.println("\n List" + list);
//            System.out.println("\n List 1" + list1);
//            TableViewData.setItems(list1);
//        } else if (FormInfo.validateNumber(txtID.getText())) {
//            for (CheckInRoom cir : list) {
//
//            }
//        } else if (FormInfo.validateEmail(txtID.getText())) {
//            for (CheckInRoom cir : list) {
//
//            }
//        } else {
//            TableViewData.setItems(CheckInRoomDAOImple.listAddTableCheckIn());
//        };

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //define table 
        if (checkIn) {

            TableColumn<CheckIn, String> checkID = new TableColumn<>("Check In ID");
            TableColumn<CheckIn, String> bookID = new TableColumn<>("Book ID");
            TableColumn<CheckIn, String> cusID = new TableColumn<>("Customer ID");
            TableColumn<CheckIn, String> roomID = new TableColumn<>("Room ID");
            TableColumn<CheckIn, String> checkType = new TableColumn<>("Check In Type");
            TableColumn<CheckIn, String> cusPack = new TableColumn<>("Customer Package");
            TableColumn<CheckIn, LocalDateTime> dateIn = new TableColumn<>("Check In Date");
            TableColumn<CheckIn, LocalDateTime> dateOut = new TableColumn<>("Leave Date");
            TableColumn<CheckIn, Integer> quantity = new TableColumn<>("Quantity");

            checkID.setCellValueFactory(new PropertyValueFactory<>("CheckID"));
            bookID.setCellValueFactory(new PropertyValueFactory<>("BookID"));
            cusID.setCellValueFactory(new PropertyValueFactory<>("CusID"));
            roomID.setCellValueFactory(new PropertyValueFactory<>("RoomID"));
            checkType.setCellValueFactory(new PropertyValueFactory<>("CheckType"));
            cusPack.setCellValueFactory(new PropertyValueFactory<>("CusPack"));
            dateIn.setCellValueFactory(new PropertyValueFactory<>("DateIn"));
            dateOut.setCellValueFactory(new PropertyValueFactory<>("DateOut"));
            quantity.setCellValueFactory(new PropertyValueFactory<>("NumGuest"));

            TableViewData.getColumns().clear();
            TableViewData.getColumns().addAll(checkID, bookID, cusID, roomID, checkType, cusPack, dateIn, dateOut, quantity);

            try {
                TableViewData.setItems(CheckInRoomDAOImple.listAddTableCheckIn());
            } catch (SQLException ex) {
                Logger.getLogger(FXMLCheckInRoomController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    void BookingBtn(ActionEvent event) {
        checkIn = false;
        Customer = false;
        Booking = true;
    }

    @FXML
    void CheckInBtn(ActionEvent event) {
        Booking = false;
        Customer = false;
        checkIn = true;
    }

    @FXML
    void CustomerBtn(ActionEvent event) {
        checkIn = false;
        Booking = false;
        Customer = true;
    }
}
