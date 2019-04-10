/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.jfoenix.controls.JFXCheckBox;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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
public class ServiceOrderDAOImpl implements ServiceOrderDAO {

    @Override
    public ObservableList<ServiceOrder> getAllServiceOrders() {
        String sql = "SELECT SO.OrderID, SO.RoomID, SO.CustomerID, SO.UserName, SO.ServiceOrderDate, SO.ServiceNote, SO.Finish, SO.CheckOut, "
                + "CASE WHEN C.CustomerMidName <> '' THEN C.CustomerFirstName+' '+C.CustomerMidName+ ' ' +C.CustomerLastName "
                + "ELSE C.CustomerFirstName+' ' +C.CustomerLastName END AS 'CustomerFullName' "
                + "FROM ServicesOrders SO "
                + "INNER JOIN Customers C ON C.CustomerID = SO.CustomerID "
                + "WHERE SO.ACTIVE=1";
        ObservableList<ServiceOrder> listServiceOrders = FXCollections.observableArrayList();
        try {
            try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    ServiceOrder serviceOrder = new ServiceOrder();
                    serviceOrder.setCustomerID(rs.getString("CustomerID"));
                    serviceOrder.setCustomerFullName(rs.getString("CustomerFullName"));
                    serviceOrder.setServiceOrderID(rs.getString("OrderID"));
                    serviceOrder.setRoomID(rs.getString("RoomID"));
                    serviceOrder.setUserName(rs.getString("UserName"));
                    serviceOrder.setServiceNote(rs.getNString("ServiceNote"));
                    serviceOrder.setServiceOrderTime(rs.getTimestamp("ServiceOrderDate").toLocalDateTime());
                    serviceOrder.setServiceFinish(rs.getBoolean("Finish"));
                    serviceOrder.setServiceCheckOut(rs.getBoolean("CheckOut"));

                    JFXCheckBox checkBox = new JFXCheckBox();
                    checkBox.setOnAction((event) -> {
                        ServiceOrderDetailDAOImpl serviceOrderDetailDAOImpl = new ServiceOrderDetailDAOImpl();
                        if (!serviceOrderDetailDAOImpl.check_SOD_Per_SO_Finish(serviceOrder.getServiceOrderID())) {
                            updateServiceFinish(!serviceOrder.isServiceFinish(), serviceOrder.getServiceOrderID());
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Message");
                            alert.setHeaderText("Error");
                            alert.setContentText("Still having not finished items inside, so can not allow setting finish.");
                            alert.show();
                            checkBox.setSelected(false);
                        }
                    });
                    if (serviceOrder.isServiceFinish()) {
                        checkBox.setSelected(true);
                    }
                    if (serviceOrder.isServiceCheckOut()) {
                        checkBox.setDisable(true);
                    }
                    serviceOrder.setCheckBox_Finish(checkBox);

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
    public void updateServiceFinish(boolean finish, String serviceOrderID) {
        String sql = "UPDATE ServicesOrders SET Finish=? WHERE OrderID=?";
        try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, finish);
            stmt.setString(2, serviceOrderID);
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ServiceOrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateServiceCheckOut(boolean checkout, String serviceOrderID) {
        String sql = "UPDATE ServicesOrders SET CheckOut=1 WHERE OrderID=?";
        try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, checkout);
            stmt.setString(2, serviceOrderID);
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ServiceOrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editServiceOrder(ServiceOrder serviceOrder, Boolean active) {
        String sql = "UPDATE ServicesOrders SET OrderID=?, RoomID=?, CustomerID=?, "
                + "UserName=?, ServiceOrderDate=?, ServiceNote=?  "
                + "WHERE OrderID=?";

        try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, serviceOrder.getServiceOrderID());
            stmt.setString(2, serviceOrder.getRoomID());
            stmt.setString(3, serviceOrder.getCustomerID());
            stmt.setString(4, serviceOrder.getUserName());
            stmt.setTimestamp(5, Timestamp.valueOf(serviceOrder.getServiceOrderTime()));
            stmt.setNString(6, serviceOrder.getServiceNote());
            stmt.setString(7, serviceOrder.getServiceOrderID());
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ServiceOrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update_CheckIn_SO_CheckOut(String checkInID) {
        String sql = "UPDATE ServicesOrders SET CheckOut=1 WHERE OrderID IN (\n"
                + "SELECT SO.OrderID FROM ServicesOrders SO\n"
                + "INNER JOIN (SELECT RoomID FROM CheckInOrders WHERE CheckInID NOT IN (SELECT CheckInID FROM CheckOutOrders) \n"
                + "AND CheckInID=?) CIO \n"
                + "ON SO.RoomID = CIO.RoomID)";

        try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, checkInID);
            //stmt.executeUpdate();            
            System.out.println("Update row successful: " + stmt.executeUpdate());
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ServiceOrderDetailDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error");
                alert.setContentText("Duplicated Service Order in Database or Can't connect to Database");
                alert.show();
            });
        }
    }

    @Override
    public boolean check_For_Delete_Order(String serviceOrderID) {
        String sql = "SELECT * FROM ServicesOrderDetails WHERE OrderID=? AND Active=1";

        try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, serviceOrderID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ServiceOrderDetailDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error");
                alert.setContentText("Duplicated Service Order in Database or Can't connect to Database");
                alert.show();
            });
        }
        return false;
    }

    @Override
    public void deleteServiceOrder(ServiceOrder serviceOrder) {
        String sql = "UPDATE ServicesOrders SET Active=? WHERE OrderID=?";
        try {
            try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setBoolean(1, false);
                stmt.setString(2, serviceOrder.getServiceOrderID());
                stmt.executeUpdate();

            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ServiceOrderDAOImpl.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

}
