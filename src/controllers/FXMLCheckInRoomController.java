/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import fxml.FXMLFormInforOfGuestController;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.CheckInRoom;
import models.CheckInRoomDAOImple;
import models.FormInfo;

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
    private TableView<CheckInRoom> TableViewData;

    @FXML
    private MenuItem DeleteBtn;

    @FXML
    private MenuItem EditBtn;

    @FXML
    void handleDeleteMenu(ActionEvent event) throws SQLException {

        ObservableList<CheckInRoom> cir = TableViewData.getSelectionModel().getSelectedItems();
        for (CheckInRoom checkInRoom : cir) {
            CheckInRoomDAOImple.deleteDataCIR(checkInRoom.getID());
        };
        TableViewData.setItems(CheckInRoomDAOImple.listAddTableCheckIn());

    }

    @FXML
    void handleEditMenu(ActionEvent event) throws IOException, SQLException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/FXMLFormInforOfGuest.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();

            FXMLFormInforOfGuestController fxmlFIGC = fxmlLoader.getController();
            ObservableList<CheckInRoom> cir = TableViewData.getSelectionModel().getSelectedItems();
            for (CheckInRoom checkInRoom : cir) {
                String[] part = checkInRoom.getName().split("\\s", 2);
//                fxmlFIGC.EditBtnCheckInRoom(checkInRoom.getID(), part[0], part[1], checkInRoom.getMail(), checkInRoom.getPhone(), checkInRoom.getNote(), checkInRoom.getCompany(), checkInRoom.getRoomType(), checkInRoom.getNum(), checkInRoom.getFlight());
            };
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            System.out.println("Cant load");
        }

        TableViewData.getItems().clear();
        TableViewData.setItems(CheckInRoomDAOImple.listAddTableCheckIn());
    }

    @FXML
    void handleSearchBtn(ActionEvent event) throws SQLException {
        ObservableList<CheckInRoom> list = CheckInRoomDAOImple.listCheckIn(CheckInRoomDAOImple.getAllDataBooking());
        ObservableList<CheckInRoom> list1 = FXCollections.observableArrayList();

        TableViewData.getItems().clear();

        char[] textSearch = txtID.getText().replaceAll("\\s", "").toCharArray();

        if (FormInfo.isString(txtID.getText())) {

            int available[] = new int[list.size()];
            int max = 0;
            int count = 0;
            boolean check = true;
            for (CheckInRoom cir : list) {
                if (cir.getName().contains(txtID.getText())) {
                    list1.add(cir);
                } else {

                    //finding fullname
//                if (CheckInRoomDAOImple.removeAccent(cir.getName().toLowerCase()).replaceAll("\\s", "").equals(txtID.getText().replaceAll("\\s", ""))) {
//                    list1.add(cir);
//                } else check = false;
//                if (!check) {
//                    //finding character
                    char[] name = CheckInRoomDAOImple.removeAccent(cir.getName().toLowerCase()).replaceAll("\\s", "").toCharArray();
                    int i = 0;
                    System.out.println(name);
                    System.out.println(textSearch);

                    for (char c : textSearch) {
                        for (char d : name) {
                            if (c == d && Character.isLetter(c) && Character.isLetter(d)) {
                                i++;
                                d = ' ';
                                break;
                            } else {
                                check = false;
                            }
                        }
                    }
                    if (i > max) {
                        max = i;
                    }
                    available[count] = i;
                    count++;
                }
//                }
            }
            count = 0;
            for (CheckInRoom cir : list) {
                if (available[count] == max) {
                    list1.add(cir);
                }
                count++;
            }

            //show to tableview
            System.out.println("\n List" + list);
            System.out.println("\n List 1" + list1);
            TableViewData.setItems(list1);
        } else if (FormInfo.validateNumber(txtID.getText())) {
            for (CheckInRoom cir : list) {

            }
        } else if (FormInfo.validateEmail(txtID.getText())) {
            for (CheckInRoom cir : list) {

            }
        } else {
            TableViewData.setItems(CheckInRoomDAOImple.listAddTableCheckIn());
        };

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //define table 
        TableColumn<CheckInRoom, String> col_ID = new TableColumn<>("ID");
        col_ID.setMinWidth(50);
        col_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        TableColumn<CheckInRoom, String> col_name = new TableColumn<>("Name");
        col_name.setMinWidth(100);
        col_name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        TableColumn<CheckInRoom, String> col_phone = new TableColumn<>("Phone");
        col_phone.setMinWidth(100);
        col_phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        TableColumn<CheckInRoom, String> col_mail = new TableColumn<>("Mail");
        col_mail.setMinWidth(100);
        col_mail.setCellValueFactory(new PropertyValueFactory<>("Mail"));
        TableColumn<CheckInRoom, String> col_room = new TableColumn<>("Room Type");
        col_room.setMinWidth(50);
        col_room.setCellValueFactory(new PropertyValueFactory<>("RoomType"));
        TableColumn<CheckInRoom, String> col_num = new TableColumn<>("Number Gusest");
        col_num.setMinWidth(40);
        col_num.setCellValueFactory(new PropertyValueFactory<>("Num"));
        TableColumn<CheckInRoom, String> col_drive = new TableColumn<>("Drive");
        col_drive.setMinWidth(30);
        col_drive.setCellValueFactory(new PropertyValueFactory<>("Drive"));
        TableColumn<CheckInRoom, String> col_date = new TableColumn<>("Date");
        col_date.setMinWidth(100);
        col_date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        TableColumn<CheckInRoom, String> col_company = new TableColumn<>("Company");
        col_company.setMinWidth(100);
        col_company.setCellValueFactory(new PropertyValueFactory<>("Company"));
        TableColumn<CheckInRoom, String> col_flight = new TableColumn<>("Flight");
        col_flight.setMinWidth(100);
        col_flight.setCellValueFactory(new PropertyValueFactory<>("Flight"));
        TableColumn<CheckInRoom, String> col_note = new TableColumn<>("Note");
        col_note.setMinWidth(150);
        col_note.setCellValueFactory(new PropertyValueFactory<>("Note"));

        TableViewData.getColumns().clear();
        TableViewData.getColumns().addAll(col_ID, col_name, col_phone, col_mail, col_room, col_num, col_drive, col_date, col_company, col_flight, col_note);

        try {
            TableViewData.setItems(CheckInRoomDAOImple.listAddTableCheckIn());
        } catch (SQLException ex) {
            Logger.getLogger(FXMLCheckInRoomController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
