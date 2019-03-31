/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import utils.FormatName;
import utils.connectDB;

/**
 *
 * @author Doan Thanh Nhan
 */
public class BillDAOImpl implements BillDAO{

    @Override
    public ObservableList<Bill> getAllBills() {
        String sql = "SELECT * FROM Bill WHERE Active=1";
        ObservableList<Bill> listBills = FXCollections.observableArrayList();
        try {
            try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Bill bill = new Bill();
                    bill.setCheckInDate(rs.getTimestamp("CheckInDate").toLocalDateTime());
                    bill.setCheckInID(rs.getString("CheckInID"));
                    listBills.add(bill);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error Querry");
                alert.setContentText("Don't have any bill in Database or Can't connect to Database");
                alert.show();
            });
            Logger.getLogger(BillDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listBills;
    }

    @Override
    public void addBill(Bill bill) {
        String sql = "INSERT INTO Bill (RoomID, CustomerID, UserName, CheckInID, CheckOutID, "
                + "CheckInDate, CheckOutDate, NoOfDay, RoomPrice, RoomCharge, ServiceCharge, RoomDiscount, "
                + "ServiceDiscount, CustomerDiscount, TotalBillAmount, VATAmount, PayableAmount, "
                + "CustomerGive, CustomerChange, QRCode) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, bill.getRoomID());
                stmt.setString(2, bill.getCustomerID());
                stmt.setString(3, bill.getUserName());
                stmt.setString(4, bill.getCheckInID());
                stmt.setString(5, bill.getCheckOutID());                
                stmt.setTimestamp(6, Timestamp.valueOf(bill.getCheckInDate()));
                stmt.setTimestamp(7, Timestamp.valueOf(bill.getCheckOutDate()));
                stmt.setInt(8, bill.getNoOfDay());
                stmt.setBigDecimal(9, bill.getRoomPrice());
                stmt.setBigDecimal(10, bill.getRoomCharge());
                stmt.setBigDecimal(11, bill.getServiceCharge());
                stmt.setBigDecimal(12, bill.getRoomDiscount());
                stmt.setBigDecimal(13, bill.getServiceDiscount());
                stmt.setBigDecimal(14, bill.getCustomerDiscount());
                stmt.setBigDecimal(15, bill.getTotalBillAmount());
                stmt.setBigDecimal(16, bill.getVATAmount());
                stmt.setBigDecimal(17, bill.getPayableAmount());
                stmt.setBigDecimal(18, bill.getCustomerGive());
                stmt.setBigDecimal(19, bill.getCustomerChange());                
                stmt.setBlob(20, bill.getBillQRCode());
                
                stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ServiceTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setHeaderText("Error");
            alert.setContentText("Duplicated bill ID in Database or Can't connect to Database");
            alert.show();
        }
    }

    @Override
    public void editBill(Bill bill, Boolean active) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteBill(Bill bill) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
