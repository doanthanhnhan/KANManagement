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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import utils.connectDB;

/**
 *
 * @author Doan Thanh Nhan
 */
public class ServiceOrderDetailDAOImpl implements ServiceOrderDetailDAO {

    @Override
    public ObservableList<ServiceOrderDetail> getAllServiceOrdersDetails() {
        String sql = "SELECT OrderID, ServiceID, UserName, ServiceQuantity, Price, Discount, Active FROM ServicesOrderDetails WHERE Active=1";
        ObservableList<ServiceOrderDetail> listServiceOrderDetails = FXCollections.observableArrayList();

        try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ServiceOrderDetail serviceOrderDetail = new ServiceOrderDetail();
                serviceOrderDetail.setOrderID(rs.getString("OrderID"));
                serviceOrderDetail.setServiceID(rs.getString("ServiceID"));
                serviceOrderDetail.setUserName(rs.getString("UserName"));
                serviceOrderDetail.setServiceQuantity(rs.getInt("ServiceQuantity"));
                serviceOrderDetail.setServicePrice(rs.getFloat("Price"));
                serviceOrderDetail.setServiceDiscount(rs.getFloat("Discount"));
                serviceOrderDetail.setActive(rs.getBoolean("Active"));

                listServiceOrderDetails.add(serviceOrderDetail);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ServiceOrderDetailDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setHeaderText("Error");
            alert.setContentText("Don't have any Service Type in Database or Can't connect to Database");
            alert.show();
        }
        return listServiceOrderDetails;
    }

    @Override
    public void addServiceOrdersDetail(ServiceOrderDetail serviceOrderDetail) {
        String sql = "INSERT INTO ServicesOrderDetails (OrderID, ServiceID, UserName, ServiceQuantity, Price, Discount, Active) "
                + "VALUES (?,?,?,?,?,?,?)";
        try {
            try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, serviceOrderDetail.getOrderID());
                stmt.setString(2, serviceOrderDetail.getServiceID());
                stmt.setString(3, serviceOrderDetail.getUserName());
                stmt.setInt(4, serviceOrderDetail.getServiceQuantity());
                stmt.setFloat(5, serviceOrderDetail.getServicePrice());
                stmt.setFloat(6, serviceOrderDetail.getServiceDiscount());
                stmt.setBoolean(7, true);

                stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ServiceOrderDetailDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setHeaderText("Error");
            alert.setContentText("Duplicated Service Type in Database or Can't connect to Database");
            alert.show();
        }
    }

    @Override
    public void editServiceOrdersDetail(ServiceOrderDetail serviceOrderDetail, Boolean active) {
        String sql = "UPDATE ServicesOrderDetails SET OrderID=?, ServiceID=?, UserName=?, "
                + "ServiceQuantity=?, Price=?, Discount=?, Active=? "
                + "WHERE ServiceID=?";

        try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, serviceOrderDetail.getOrderID());
            stmt.setString(2, serviceOrderDetail.getServiceID());
            stmt.setString(3, serviceOrderDetail.getUserName());
            stmt.setInt(4, serviceOrderDetail.getServiceQuantity());
            stmt.setFloat(5, serviceOrderDetail.getServicePrice());
            stmt.setFloat(6, serviceOrderDetail.getServiceDiscount());
            stmt.setBoolean(7, true);
            stmt.setString(8, serviceOrderDetail.getOrderID());
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ServiceOrderDetailDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setHeaderText("Error");
            alert.setContentText("Duplicated Service Type in Database or Can't connect to Database");
            alert.show();
        }
    }

    @Override
    public void deleteServiceOrdersDetail(ServiceOrderDetail serviceOrderDetail) {
        String sql = "DELETE FROM ServicesOrderDetails WHERE ServiceID=?";

        try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, serviceOrderDetail.getServiceID());
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ServiceOrderDetailDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setHeaderText("Error");
            alert.setContentText("Can't connect to Database");
            alert.show();
        }
    }

}
