/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.jfoenix.controls.JFXButton;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
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
    public ObservableList<ServiceOrderDetail> get_All_Details_In_One_Order(String serviceOrderID) {
        String sql = "SELECT SOD.OrderID, SOD.ServiceQuantity, SOD.Price, SOD.Discount, ST.* FROM ServicesOrderDetails SOD \n"
                + "INNER JOIN ServiceType ST\n"
                + "ON SOD.ServiceID = ST.ServiceID\n"
                + "WHERE SOD.OrderID='" + serviceOrderID +"'";
        ObservableList<ServiceOrderDetail> listServiceOrderDetails = FXCollections.observableArrayList();

        try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ServiceOrderDetail serviceOrderDetail = new ServiceOrderDetail();
                serviceOrderDetail.setOrderID(rs.getString("OrderID"));
                serviceOrderDetail.setServiceID(rs.getString("ServiceID"));
                serviceOrderDetail.setUserName(rs.getString("UserName"));
                serviceOrderDetail.setServiceQuantity(rs.getInt("ServiceQuantity"));
                serviceOrderDetail.setServicePriceTotal(rs.getFloat("Price"));
                serviceOrderDetail.setServiceDiscount(rs.getFloat("Discount"));
                serviceOrderDetail.setActive(rs.getBoolean("Active"));

                JFXButton serviceRemoveButton = new JFXButton("Remove");
                serviceRemoveButton.getStyleClass().add("btn-red-color");
                serviceRemoveButton.setOnAction((event) -> {
                    deleteServiceOrdersDetail(serviceOrderDetail);
                });
                serviceOrderDetail.setServiceRemoveButton(serviceRemoveButton);

                serviceOrderDetail.setServiceName(rs.getNString("ServiceName"));
                serviceOrderDetail.setServiceUnit(rs.getNString("ServiceUnit"));
                serviceOrderDetail.setServicePrice(rs.getFloat("ServicePrice"));
                if (rs.getBlob("Image") != null) {
                    serviceOrderDetail.setServiceImage(rs.getBlob("Image"));
                }
                serviceOrderDetail.setServiceDescription(rs.getNString("ServiceDescription"));
                if (serviceOrderDetail.getServiceImage() != null) {
                    byte[] bytes = serviceOrderDetail.getServiceImage().getBytes(1l, (int) serviceOrderDetail.getServiceImage().length());
                    BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes));
                    Image image = SwingFXUtils.toFXImage(img, null);
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(50);
                    imageView.setFitWidth(50);
                    serviceOrderDetail.setImageView(imageView);
                }
                serviceOrderDetail.setServiceInventory(rs.getInt("ServiceInventory"));
                serviceOrderDetail.setServiceInputDate(rs.getTimestamp("InputDate").toLocalDateTime());

                listServiceOrderDetails.add(serviceOrderDetail);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ServiceOrderDetailDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setHeaderText("Error");
            alert.setContentText("Don't have any Service Type in Database or Can't connect to Database");
            alert.show();
        } catch (IOException ex) {
            Logger.getLogger(ServiceOrderDetailDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                stmt.setFloat(5, serviceOrderDetail.getServicePriceTotal());
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
        String sql = "DELETE FROM ServicesOrderDetails WHERE ServiceID=? AND OrderID=?";

        try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, serviceOrderDetail.getServiceID());
            stmt.setString(2, serviceOrderDetail.getOrderID());
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
