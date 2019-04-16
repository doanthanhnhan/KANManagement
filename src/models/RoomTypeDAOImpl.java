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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
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
                    roomType.setDiscount(rs.getBigDecimal("Discount"));
                    roomType.setPrice(rs.getBigDecimal("Price"));
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
                stmt.setBigDecimal(2, roomType.getPrice());
                stmt.setBigDecimal(3, roomType.getDiscount());
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
                stmt.setBigDecimal(2, roomType.getPrice());
                stmt.setBigDecimal(3, roomType.getDiscount());
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
        String sql = "UPDATE RoomType SET Active=? WHERE RoomType=?";
        try {
            try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setBoolean(1, false);
                stmt.setString(2, roomType.getType());
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

    public void addInitRoomType() {
        String sql = "INSERT INTO RoomType(RoomType, Price, Discount) VALUES \n"
                + "('Single', 100, 0.10),\n"
                + "('Double', 200, 0.10),\n"
                + "('Triple', 250, 0.10),\n"
                + "('Family', 300, 0.10),\n"
                + "('Deluxe', 1000, 0.10)";
        try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(RoomTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error");
                alert.setContentText("Can't connect to Database");
                alert.show();
            });
        }
    }

    public ObservableList<String> getAllStringRoomType() {
        String sql = "SELECT RoomType FROM RoomType";
        ObservableList<String> listRoomTypes = FXCollections.observableArrayList();
        try {
            try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String roomType;
                    roomType = rs.getString("RoomType");

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
}
