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
public class ServiceTypeDAOImpl implements ServiceTypeDAO {

    @Override
    public ObservableList<ServiceType> getAllServiceType() {
        String sql = "SELECT * FROM ServiceType";
        ObservableList<ServiceType> listServiceTypes = FXCollections.observableArrayList();
        try {
            try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    ServiceType serviceType = new ServiceType();
                    serviceType.setServiceID(rs.getString("ServiceID"));
                    serviceType.setServiceName(rs.getString("ServiceName"));
                    serviceType.setServiceUnit(rs.getString("ServiceUnit"));
                    serviceType.setServicePrice(rs.getFloat("ServicePrice"));
                    serviceType.setServiceImage(rs.getBlob("Image"));

                    byte[] bytes = serviceType.getServiceImage().getBytes(1l, (int) serviceType.getServiceImage().length());
                    BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes));
                    Image image = SwingFXUtils.toFXImage(img, null);
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(50);
                    imageView.setFitWidth(50);
                    serviceType.setImageView(imageView);

                    listServiceTypes.add(serviceType);
                }
            } catch (IOException ex) {
                Logger.getLogger(ServiceTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ServiceTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listServiceTypes;
    }

    @Override
    public void addServiceType(ServiceType serviceType) {
        String sql = "INSERT INTO ServiceType (ServiceID, ServiceName, ServiceUnit, ServicePrice, Image, Active) VALUES (?,?,?,?,?,?)";
        try {
            try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, serviceType.getServiceID());
                stmt.setString(2, serviceType.getServiceName());
                stmt.setString(3, serviceType.getServiceUnit());
                stmt.setFloat(4, serviceType.getServicePrice());
                stmt.setBlob(5, serviceType.getServiceImage());
                stmt.setBoolean(6, true);
                stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ServiceTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editServiceType(ServiceType serviceType, Boolean active) {
        String sql = "UPDATE ServiceType SET ServiceID=?, ServiceName=?, ServiceUnit=?, ServicePrice=?, Image=?, Active=? WHERE ServiceID=?";
        try {
            try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, serviceType.getServiceID());
                stmt.setString(2, serviceType.getServiceName());
                stmt.setString(3, serviceType.getServiceUnit());
                stmt.setFloat(4, serviceType.getServicePrice());
                stmt.setBlob(5, serviceType.getServiceImage());
                stmt.setBoolean(6, active);
                stmt.setString(7, serviceType.getServiceID());
                stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ServiceTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
        }
    }

}
