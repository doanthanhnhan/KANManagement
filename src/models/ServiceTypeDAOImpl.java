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
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import utils.connectDB;

/**
 *
 * @author Doan Thanh Nhan
 */
public class ServiceTypeDAOImpl implements ServiceTypeDAO {

    @Override
    public ObservableList<ServiceType> getAllServiceType() {
        String sql = "SELECT ServiceID, ServiceName, ServiceUnit, ServicePrice, Image, ServiceDescription, ServiceInventory, InputDate FROM ServiceType";
        ObservableList<ServiceType> listServiceTypes = FXCollections.observableArrayList();
        try {
            try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    ServiceType serviceType = new ServiceType();
                    serviceType.setServiceID(rs.getString("ServiceID"));
                    serviceType.setServiceName(rs.getNString("ServiceName"));
                    serviceType.setServiceUnit(rs.getNString("ServiceUnit"));
                    serviceType.setServicePrice(rs.getFloat("ServicePrice"));
                    if (rs.getBlob("Image") != null) {
                        serviceType.setServiceImage(rs.getBlob("Image"));
                    }
                    if(rs.getNString("ServiceDescription") != null){
                        serviceType.setServiceDescription(rs.getNString("ServiceDescription"));
                    } else {
                        serviceType.setServiceDescription("");
                    }
                    
                    if (serviceType.getServiceImage() != null) {
                        byte[] bytes = serviceType.getServiceImage().getBytes(1l, (int) serviceType.getServiceImage().length());
                        BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes));
                        Image image = SwingFXUtils.toFXImage(img, null);
                        ImageView imageView = new ImageView(image);
                        imageView.setFitHeight(50);
                        imageView.setFitWidth(50);
                        serviceType.setImageView(imageView);
                    }
                    serviceType.setServiceInventory(rs.getInt("ServiceInventory"));
                    serviceType.setServiceInputDate(rs.getTimestamp("InputDate").toLocalDateTime());
                    listServiceTypes.add(serviceType);
                }
            } catch (IOException ex) {
                Logger.getLogger(ServiceTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error");
                alert.setContentText("Don't have any Service Type in Database or Can't connect to Database");
                alert.show();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ServiceTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listServiceTypes;
    }

    @Override
    public ObservableList<String> getAllServiceTypeID() {
        String sql = "SELECT ServiceID, ServiceName, ServiceUnit, ServicePrice, Image, ServiceDescription, ServiceInventory, InputDate FROM ServiceType";
        ObservableList<String> listServiceTypes = FXCollections.observableArrayList();
        try {
            try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String serviceTypeID;
                    serviceTypeID = rs.getString("ServiceID");

                    listServiceTypes.add(serviceTypeID);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ServiceTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listServiceTypes;
    }

    @Override
    public void addServiceType(ServiceType serviceType) {
        String sql = "INSERT INTO ServiceType (ServiceID, ServiceName, ServiceUnit, ServicePrice, Image, Active, ServiceDescription, ServiceInventory, InputDate, UserName) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?)";
        try {
            try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, serviceType.getServiceID());
                stmt.setNString(2, serviceType.getServiceName());
                stmt.setNString(3, serviceType.getServiceUnit());
                stmt.setFloat(4, serviceType.getServicePrice());
                stmt.setBlob(5, serviceType.getServiceImage());
                stmt.setBoolean(6, true);
                stmt.setNString(7, serviceType.getServiceDescription());
                stmt.setInt(8, serviceType.getServiceInventory());
                //Convert LocalDateTime to Timestamp
                stmt.setTimestamp(9, Timestamp.valueOf(serviceType.getServiceInputDate()));
                stmt.setString(10, serviceType.getUserName());

                stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ServiceTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setHeaderText("Error");
            alert.setContentText("Duplicated Service Type in Database or Can't connect to Database");
            alert.show();
        }
    }

    @Override
    public void editServiceType(ServiceType serviceType, Boolean active) {
        String sql = "UPDATE ServiceType SET ServiceID=?, ServiceName=?, ServiceUnit=?, ServicePrice=?, "
                + "Image=?, Active=?, ServiceDescription=?,  "
                + "ServiceInventory=?, InputDate=?, UserName=? "
                + "WHERE ServiceID=?";
        try {
            try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, serviceType.getServiceID());
                stmt.setNString(2, serviceType.getServiceName());
                stmt.setNString(3, serviceType.getServiceUnit());
                stmt.setFloat(4, serviceType.getServicePrice());
                stmt.setBlob(5, serviceType.getServiceImage());
                stmt.setBoolean(6, active);
                stmt.setNString(7, serviceType.getServiceDescription());
                stmt.setInt(8, serviceType.getServiceInventory());
                stmt.setTimestamp(9, Timestamp.valueOf(serviceType.getServiceInputDate()));
                stmt.setString(10, serviceType.getUserName());
                stmt.setNString(11, serviceType.getServiceID());
                stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ServiceTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setHeaderText("Error");
            alert.setContentText("Duplicated Service Type in Database or Can't connect to Database");
            alert.show();
        }
    }

    @Override
    public void deleteServiceType(ServiceType serviceType) {
        String sql = "DELETE FROM ServiceType WHERE ServiceID=?";
        try {
            try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, serviceType.getServiceID());
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
