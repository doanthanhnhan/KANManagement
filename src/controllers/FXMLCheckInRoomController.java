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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.CheckInRoom;
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
    private TableView<CheckInRoom> TableViewData;

    @FXML
    private MenuItem DeleteBtn;

    @FXML
    private MenuItem EditBtn;

    @FXML
    void handleDeleteMenu(ActionEvent event) {

    }

    @FXML
    void handleEditMenu(ActionEvent event) {

    }

    @FXML
    void handleSearchBtn(ActionEvent event) throws SQLException {
        ObservableList<CheckInRoom> list = CheckInRoomDAOImple.listCheckIn();
        System.out.println(list);
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
            TableViewData.setItems(CheckInRoomDAOImple.listCheckIn());
        } catch (SQLException ex) {
            Logger.getLogger(FXMLCheckInRoomController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
