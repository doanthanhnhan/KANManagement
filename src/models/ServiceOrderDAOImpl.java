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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.connectDB;

/**
 *
 * @author Doan Thanh Nhan
 */
public class ServiceOrderDAOImpl implements ServiceOrderDAO {

    @Override
    public ObservableList<ServiceOrder> getAllServiceOrders() {
        String sql = "SELECT OrderID, RoomID, CustomerID, UserName, ServiceOrderDate, ServiceNote FROM ServicesOrders WHERE ACTIVE=1";
        ObservableList<ServiceOrder> listServiceOrders = FXCollections.observableArrayList();
        try {
            try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    ServiceOrder serviceOrder = new ServiceOrder();
                    serviceOrder.setCustomerID(rs.getString("CustomerID"));
                    serviceOrder.setServiceOrderID(rs.getString("OrderID"));
                    serviceOrder.setRoomID(rs.getString("RoomID"));                    
                    serviceOrder.setUserName(rs.getString("UserName"));                    
                    serviceOrder.setServiceNote(rs.getNString("ServiceNote"));                    
                    serviceOrder.setServiceOrderTime(rs.getTimestamp("ServiceOrderDate").toLocalDateTime());                   

                    listServiceOrders.add(serviceOrder);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ServiceOrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listServiceOrders;
    }

    @Override
    public void addServiceOrder(ServiceOrder serviceOrder) {
        String sql = "INSERT INTO ServicesOrders (OrderID, RoomID, CustomerID, UserName, ServiceOrderDate, ServiceNote) VALUES (?,?,?,?,?,?)";
        try {
            try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, serviceOrder.getServiceOrderID());
                stmt.setString(2, serviceOrder.getRoomID());
                stmt.setString(3, serviceOrder.getCustomerID());
                stmt.setString(4, serviceOrder.getUserName());
                stmt.setTimestamp(5, Timestamp.valueOf(serviceOrder.getServiceOrderTime()));               
                stmt.setNString(6, serviceOrder.getServiceNote());
                stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ServiceOrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editServiceOrder(ServiceOrder serviceOrder, Boolean active) {
        String sql = "UPDATE ServicesOrders SET OrderID=?, RoomID=?, CustomerID=?, "
                + "UserName=?, ServiceOrderDate=?, ServiceNote=?  "
                + "WHERE ServiceID=?";
        try {
            try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, serviceOrder.getServiceOrderID());
                stmt.setString(2, serviceOrder.getRoomID());
                stmt.setString(3, serviceOrder.getCustomerID());
                stmt.setString(4, serviceOrder.getUserName());
                stmt.setTimestamp(5, Timestamp.valueOf(serviceOrder.getServiceOrderTime()));               
                stmt.setNString(6, serviceOrder.getServiceNote());
                stmt.setString(7, serviceOrder.getServiceOrderID());
                stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ServiceOrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteServiceOrder(ServiceOrder serviceOrder) {
        String sql = "UPDATE ServicesOrders SET Active=? WHERE ServiceID=?";
        try {
            try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setBoolean(1, false);
                stmt.setString(2, serviceOrder.getServiceOrderID());
                stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ServiceOrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
