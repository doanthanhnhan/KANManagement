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
import java.sql.Timestamp;
import java.util.List;
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
public class CheckOutDAOImpl implements CheckOutDAO {

    @Override
    public ObservableList<CheckOut> getAllCheckOut() {
        String sql = "SELECT CheckOutID, CheckInID, CustomerID, RoomID, UserName, CheckInDate, "
                + "CheckOutDate, CustomerPayment, CustomerBill, Discount, Tax "
                + "FROM CheckOutOrders WHERE Active=1";
        ObservableList<CheckOut> listCheckOuts = FXCollections.observableArrayList();
        try {
            try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    CheckOut checkOut = new CheckOut();
                    checkOut.setCheckOutID(rs.getString("CheckOutID"));
                    checkOut.setCheckInID(rs.getString("CheckInID"));
                    checkOut.setCustomerID(rs.getString("CustomerID"));
                    checkOut.setRoomID(rs.getString("RoomID"));
                    checkOut.setUserName(rs.getString("UserName"));
                    checkOut.setCheckInDate(rs.getTimestamp("CheckInDate").toLocalDateTime());
                    checkOut.setCheckOutDate(rs.getTimestamp("CheckOutDate").toLocalDateTime());
                    checkOut.setCustomerPayment(rs.getString("CustomerPayment"));
                    checkOut.setCustomerBill(rs.getBigDecimal("CustomerBill"));
                    checkOut.setDiscount(rs.getBigDecimal("Discount"));
                    checkOut.setTax(rs.getBigDecimal("Tax"));

                    listCheckOuts.add(checkOut);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error");
                alert.setContentText("Don't have any checkOut in Database or Can't connect to Database");
                alert.show();
            });
            Logger.getLogger(CheckOutDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listCheckOuts;
    }

    @Override
    public ObservableList<String> getAllCheckOutID() {
        String sql = "SELECT CheckOutID FROM CheckOutOrders";
        ObservableList<String> listCheckOuts = FXCollections.observableArrayList();
        try {
            try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String checkOutID;
                    checkOutID = rs.getString("CheckOutID");

                    listCheckOuts.add(checkOutID);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error");
                alert.setContentText("Don't have any checkOut in Database or Can't connect to Database");
                alert.show();
            });
            Logger.getLogger(CheckOutDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listCheckOuts;
    }

    @Override
    public void addCheckOut(CheckOut checkOut) {
        String sql = "INSERT INTO CheckOutOrders (CheckOutID, CheckInID, CustomerID, RoomID, UserName, CheckInDate, "
                + "CheckOutDate, CustomerPayment, CustomerBill, Discount, Tax) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try {
            try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, checkOut.getCheckOutID());
                stmt.setString(2, checkOut.getCheckInID());
                stmt.setString(3, checkOut.getCustomerID());
                stmt.setString(4, checkOut.getRoomID());
                stmt.setString(5, checkOut.getUserName());
                stmt.setTimestamp(6, Timestamp.valueOf(checkOut.getCheckInDate()));
                stmt.setTimestamp(7, Timestamp.valueOf(checkOut.getCheckOutDate()));
                stmt.setString(8, checkOut.getCustomerPayment());
                stmt.setBigDecimal(9, checkOut.getCustomerBill());
                stmt.setBigDecimal(10, checkOut.getDiscount());
                stmt.setBigDecimal(11, checkOut.getTax());

                stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error");
                alert.setContentText("Duplicated CheckOut in Database or Can't connect to Database");
                alert.show();
            });
            Logger.getLogger(CheckOutDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editCheckOut(CheckOut checkOut, Boolean active) {
        String sql = "UPDATE CheckOutOrders SET CheckOutID=?, CheckInID=?, CustomerID=?, RoomID=?, UserName=?, CheckInDate=?, "
                + "CheckOutDate=?, CustomerPayment=?, CustomerBill=?, Discount=?, Tax=? "
                + "WHERE CheckOutID=?";
        try {
            try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, checkOut.getCheckOutID());
                stmt.setString(2, checkOut.getCheckInID());
                stmt.setString(3, checkOut.getCustomerID());
                stmt.setString(4, checkOut.getRoomID());
                stmt.setString(5, checkOut.getUserName());
                stmt.setTimestamp(6, Timestamp.valueOf(checkOut.getCheckInDate()));
                stmt.setTimestamp(7, Timestamp.valueOf(checkOut.getCheckOutDate()));
                stmt.setString(8, checkOut.getCustomerPayment());
                stmt.setBigDecimal(9, checkOut.getCustomerBill());
                stmt.setBigDecimal(10, checkOut.getDiscount());
                stmt.setBigDecimal(11, checkOut.getTax());

                stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(CheckOutDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error");
                alert.setContentText("Duplicated CheckOut in Database or Can't connect to Database");
                alert.show();
            });
        }
    }

    @Override
    public void deleteCheckOut(CheckOut checkOut) {
        String sql = "UPDATE CheckOutOrders SET Active=? WHERE CheckOutID=?";
        try {
            try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setBoolean(1, false);
                stmt.setString(2, checkOut.getCheckOutID());
                stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(CheckOutDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error");
                alert.setContentText("Can't connect to Database");
                alert.show();
            });
        }
    }
}
