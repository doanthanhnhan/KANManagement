/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import utils.connectDB;

/**
 *
 * @author Doan Thanh Nhan
 */
public class ServiceOrderDAOImpl implements ServiceOrderDAO {

    @Override
    public ObservableList<ServiceOrder> getAllServiceOrders() {
        String sql = "SELECT OrderID, RoomID, ServiceID, ServiceQuantity, ServiceOrderTime, ServiceNote FROM ServiceOrders WHERE ACTIVE=1";
        ObservableList<ServiceOrder> listServiceOrders = FXCollections.observableArrayList();
        try {
            try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    ServiceOrder serviceOrder = new ServiceOrder();
                    serviceOrder.setServiceID(rs.getString("ServiceID"));
                    serviceOrder.setServiceOrderID(rs.getString("OrderID"));
                    serviceOrder.setRoomID(rs.getString("RoomID"));
                    serviceOrder.setServiceQuantity(rs.getInt("ServiceQuantity"));
                    serviceOrder.setServiceNote(rs.getNString("ServiceNote"));
                    
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(rs.getDate("ServiceOrderTime"));
                    serviceOrder.setServiceOrderTime(calendar);                    

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
        String sql = "INSERT INTO ServiceOrder (ServiceID, ServiceName, ServiceUnit, ServicePrice, Image, Active, ServiceDescription) VALUES (?,?,?,?,?,?,?)";
        try {
            try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
//                stmt.setString(1, serviceOrder.getServiceID());
//                stmt.setNString(2, serviceOrder.getServiceName());
//                stmt.setNString(3, serviceOrder.getServiceUnit());
//                stmt.setFloat(4, serviceOrder.getServicePrice());
//                stmt.setBlob(5, serviceOrder.getServiceImage());
//                stmt.setBoolean(6, true);
//                stmt.setNString(7, serviceOrder.getServiceDescription());
//                stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ServiceOrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editServiceOrder(ServiceOrder serviceOrder, Boolean active) {
        String sql = "UPDATE ServiceOrder SET ServiceID=?, ServiceName=?, ServiceUnit=?, ServicePrice=?, Image=?, Active=?, ServiceDescription=?  WHERE ServiceID=?";
        try {
            try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
//                stmt.setString(1, serviceOrder.getServiceID());
//                stmt.setNString(2, serviceOrder.getServiceName());
//                stmt.setNString(3, serviceOrder.getServiceUnit());
//                stmt.setFloat(4, serviceOrder.getServicePrice());
//                stmt.setBlob(5, serviceOrder.getServiceImage());
//                stmt.setBoolean(6, active);
//                stmt.setNString(7, serviceOrder.getServiceDescription());
//                stmt.setNString(8, serviceOrder.getServiceID());
//                stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ServiceOrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteServiceOrder(ServiceOrder serviceOrder) {
        String sql = "DELETE FROM ServiceOrder WHERE ServiceID=?";
        try {
            try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, serviceOrder.getServiceID());
                stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ServiceOrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
