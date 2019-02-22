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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.sql.rowset.CachedRowSet;
import util.connectDB;

/**
 *
 * @author Doan Thanh Nhan
 */
public class RoomDAOImpl implements RoomDAO {

    @Override
    public ObservableList<Room> getAllRoom() {
        String sql = "SELECT * FROM Rooms";
        ObservableList<Room> listRooms = FXCollections.observableArrayList();
        try {
            CachedRowSet rowSet = connectDB.connectByCachedRowSet();
            rowSet.setCommand(sql);
            rowSet.execute();
            while (rowSet.next()) {
                // Generating cursor Moved event  
                System.out.println(rowSet.getString("RoomID"));
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
        roomDAOImpl.getAllRoom();
    }
}
