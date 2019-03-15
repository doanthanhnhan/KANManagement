/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.jfoenix.controls.JFXButton;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import utils.FormatName;
import utils.connectDB;

/**
 *
 * @author Doan Thanh Nhan
 */
public class RoomDAOImpl implements RoomDAO {

    @Override
    public ObservableList<Room> getAllRoom() {
        String sql = "SELECT R.*, C.CustomerFirstName+' '+C.CustomerMidName+ ' ' +C.CustomerLastName AS 'CustomerFullName' \n"
                + "FROM Rooms R, Customers C\n"
                + "WHERE R.CustomerID = C.CustomerID";
        ObservableList<Room> listRooms = FXCollections.observableArrayList();
        try {
            try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Room room = new Room();
                    room.setRoomID(rs.getString("roomID"));
                    room.setCustomerName(FormatName.format(rs.getString("CustomerFullName")));
                    room.setUserName(rs.getString("userName"));
                    room.setRoomType(rs.getString("RoomType"));
                    room.setRoomPhoneNumber(rs.getString("PhoneNumber"));
                    room.setRoomOnFloor(rs.getInt("RoomOnFloor"));
                    room.setRoomArea(rs.getFloat("RoomArea"));
                    room.setRoomStatus(rs.getString("RoomStatus"));
                    room.setRoomClean(rs.getBoolean("Clean"));
                    room.setRoomRepaired(rs.getBoolean("Repaired"));
                    room.setRoomInProgress(rs.getBoolean("InProgress"));
                    room.setDayRemaining(rs.getInt("DayRemaining"));
                    room.setActive(rs.getBoolean("Active"));

                    listRooms.add(room);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(RoomDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listRooms;
    }

    public ObservableList<RoomEX> getAllRoomEX() {
        String sql = "SELECT R.*, C.CustomerFirstName+' '+C.CustomerMidName+ ' ' +C.CustomerLastName AS 'CustomerFullName' \n"
                + "FROM Rooms R, Customers C\n"
                + "WHERE R.CustomerID = C.CustomerID";
        ObservableList<RoomEX> listRooms = FXCollections.observableArrayList();
        try {
            try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    RoomEX room = new RoomEX();
                    room.setRoomID(rs.getString("roomID"));
                    room.setCustomerName(FormatName.format(rs.getString("CustomerFullName")));
                    room.setUserName(rs.getString("userName"));
                    room.setRoomType(rs.getString("RoomType"));
                    room.setRoomPhoneNumber(rs.getString("PhoneNumber"));
                    room.setRoomOnFloor(rs.getInt("RoomOnFloor"));
                    room.setRoomArea(rs.getFloat("RoomArea"));
                    room.setRoomStatus(rs.getString("RoomStatus"));
                    room.setRoomClean(rs.getBoolean("Clean"));
                    room.setRoomRepaired(rs.getBoolean("Repaired"));
                    room.setRoomInProgress(rs.getBoolean("InProgress"));
                    room.setDayRemaining(rs.getInt("DayRemaining"));
                    room.setActive(rs.getBoolean("Active"));
                    
                    //Setting checkboxes
                    CheckBox cb_Clean = new CheckBox("");
                    CheckBox cb_Repaired = new CheckBox("");
                    CheckBox cb_InProgress = new CheckBox("");
                    cb_Clean.setSelected(rs.getBoolean("Clean"));
                    cb_Clean.setDisable(true);
                    cb_Repaired.setSelected(rs.getBoolean("Repaired"));
                    cb_Repaired.setDisable(true);
                    cb_InProgress.setSelected(rs.getBoolean("InProgress"));
                    cb_InProgress.setDisable(true);
                    room.setCheckBox_Room_Clean(cb_Clean);
                    room.setCheckBox_Room_Repaired(cb_Repaired);
                    room.setCheckBox_Room_In_Progress(cb_InProgress);
                    
                    //Setting buttons
                    HBox roomAction = new HBox();

                    JFXButton btn_Check_In = new JFXButton("Check In");
                    btn_Check_In.getStyleClass().add("btn-green-color");                    

                    JFXButton btn_Check_Out = new JFXButton("Check Out");
                    btn_Check_Out.getStyleClass().add("btn-red-color");

                    JFXButton btn_Services = new JFXButton("Services");
                    btn_Services.getStyleClass().add("btn-warning-color");
                    
                    if(rs.getString("RoomStatus").equalsIgnoreCase("Occupied")){
                        btn_Check_In.setDisable(true);
                    } else if (rs.getString("RoomStatus").equalsIgnoreCase("Available") 
                            || rs.getString("RoomStatus").equalsIgnoreCase("Reserved")
                            || rs.getString("RoomStatus").equalsIgnoreCase("Out")){
                        btn_Check_Out.setDisable(true);
                        btn_Services.setDisable(true);
                    }

                    roomAction.setAlignment(Pos.CENTER);
                    roomAction.setSpacing(10);
                    roomAction.getChildren().addAll(btn_Check_In, btn_Services, btn_Check_Out);
                    room.setRoomAction(roomAction);
                    
                    //Add room to list
                    listRooms.add(room);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(RoomDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listRooms;
    }

    @Override
    public void addRoom(Room room) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editRoom(Room room) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteRoom(Room room) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void main(String[] args) {
        RoomDAOImpl roomDAOImpl = new RoomDAOImpl();
        ObservableList<Room> listRooms = FXCollections.observableArrayList();
        listRooms = roomDAOImpl.getAllRoom();
        for (Room listRoom : listRooms) {
            System.out.println(listRoom.toString());
        }
    }
}
