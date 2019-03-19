/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import utils.FormatName;
import utils.connectDB;

/**
 *
 * @author Doan Thanh Nhan
 */
public class RoomTypeDAOImpl implements RoomTypeDAO {

    @Override
    public ObservableList<RoomType> getAllRoomType() {
        String sql = "SELECT RoomType, Price, Discount, Active FROM RoomType WHERE Active=1";
        ObservableList<RoomType> listRoomTypes = FXCollections.observableArrayList();
        try {
            try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    RoomType roomType = new RoomType();
                    roomType.setType(rs.getString("RoomType"));
                    roomType.setDiscount(rs.getFloat("Discount"));
                    roomType.setPrice(rs.getFloat("Price"));
                    roomType.setActive(rs.getBoolean("Active"));

                    listRoomTypes.add(roomType);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setHeaderText("Error");
            alert.setContentText("Don't have any roomTypes in Database or Can't connect to Database");
            alert.show();
            Logger.getLogger(RoomTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listRoomTypes;
    }

    @Override
    public void addRoomType(RoomType roomType) {
        String sql = "INSERT INTO RoomType (RoomType, Price, Discount, Active) "
                + "VALUES (?,?,?,?)";
        try {
            try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, roomType.getType());
                stmt.setFloat(2, roomType.getPrice());
                stmt.setFloat(3, roomType.getDiscount());
                stmt.setBoolean(4, roomType.isActive());
                stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ServiceTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setHeaderText("Error");
            alert.setContentText("Duplicated RoomType in Database or Can't connect to Database");
            alert.show();
        }
    }

    @Override
    public void editRoomType(RoomType roomType, Boolean active) {
        String sql = "UPDATE RoomType SET RoomType=?, Price=?, Discount=?, Active=? "
                + "WHERE RoomType=?";
        try {
            try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, roomType.getType());
                stmt.setFloat(2, roomType.getPrice());
                stmt.setFloat(3, roomType.getDiscount());
                stmt.setBoolean(4, roomType.isActive());
                stmt.setString(5, roomType.getType());
                stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ServiceTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setHeaderText("Error");
            alert.setContentText("Duplicated RoomType in Database or Can't connect to Database");
            alert.show();
        }
    }

    @Override
    public void deleteRoomType(RoomType roomType) {
        String sql = "DELETE FROM RoomType WHERE RoomType=?";                
        try {
            try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, roomType.getType());               
                stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ServiceTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setHeaderText("Error");
            alert.setContentText("Can't connect to Database");
            alert.show();
        }
    }

}
