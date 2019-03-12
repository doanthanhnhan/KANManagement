/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
