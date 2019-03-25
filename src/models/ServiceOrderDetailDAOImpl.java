/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.jfoenix.controls.JFXButton;
import controllers.ConnectControllers;
import controllers.FXMLAddNewServiceOrderController;
import controllers.FXMLMainFormController;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        String sql = "SELECT SOD.OrderID, SOD.ServiceQuantity, SOD.Price, SOD.Discount, ST.*, SO.ServiceOrderDate, SO.CustomerID, SO.RoomID "
                + "FROM ServicesOrderDetails SOD "
                + "INNER JOIN ServiceType ST ON SOD.ServiceID = ST.ServiceID\n"
                + "INNER JOIN ServicesOrders SO ON SOD.OrderID = SO.OrderID\n"
                + "WHERE SOD.Active=1";
        ObservableList<ServiceOrderDetail> listServiceOrderDetails = FXCollections.observableArrayList();

        try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ServiceOrderDetail serviceOrderDetail = new ServiceOrderDetail();
                serviceOrderDetail.setOrderID(rs.getString("OrderID"));
                serviceOrderDetail.setCustomerID(rs.getString("CustomerID"));
                serviceOrderDetail.setRoomID(rs.getString("RoomID"));
                serviceOrderDetail.setServiceID(rs.getString("ServiceID"));
                serviceOrderDetail.setUserName(rs.getString("UserName"));
                serviceOrderDetail.setServiceQuantity(rs.getInt("ServiceQuantity"));
                serviceOrderDetail.setServicePriceTotal(rs.getBigDecimal("Price"));
                serviceOrderDetail.setServiceDiscount(rs.getBigDecimal("Discount"));
                serviceOrderDetail.setActive(rs.getBoolean("Active"));

                serviceOrderDetail.setServiceName(rs.getNString("ServiceName"));
                serviceOrderDetail.setServiceUnit(rs.getNString("ServiceUnit"));
                serviceOrderDetail.setServicePrice(rs.getBigDecimal("ServicePrice"));
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
                serviceOrderDetail.setServiceImportQuantity(rs.getInt("ImportQuantity"));
                serviceOrderDetail.setServiceImportDate(rs.getTimestamp("ImportDate").toLocalDateTime());
                serviceOrderDetail.setServiceExportQuantity(rs.getInt("ExportQuantity"));
                serviceOrderDetail.setServiceExportDate(rs.getTimestamp("ExportDate").toLocalDateTime());
                serviceOrderDetail.setServiceOrderDate(rs.getTimestamp("ServiceOrderDate").toLocalDateTime());

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
    public ObservableList<ServiceOrderDetail> get_All_Details_In_One_Order(String serviceOrderID) {
        String sql = "SELECT SOD.OrderID, SOD.ServiceQuantity, SOD.Price, SOD.Discount, ST.* FROM ServicesOrderDetails SOD \n"
                + "INNER JOIN ServiceType ST\n"
                + "ON SOD.ServiceID = ST.ServiceID\n"
                + "WHERE SOD.OrderID='" + serviceOrderID + "'";
        ObservableList<ServiceOrderDetail> listServiceOrderDetails = FXCollections.observableArrayList();

        try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ServiceOrderDetail serviceOrderDetail = new ServiceOrderDetail();
                serviceOrderDetail.setOrderID(rs.getString("OrderID"));
                serviceOrderDetail.setServiceID(rs.getString("ServiceID"));
                serviceOrderDetail.setUserName(rs.getString("UserName"));
                serviceOrderDetail.setServiceQuantity(rs.getInt("ServiceQuantity"));
                serviceOrderDetail.setServicePriceTotal(rs.getBigDecimal("Price"));
                serviceOrderDetail.setServiceDiscount(rs.getBigDecimal("Discount"));
                serviceOrderDetail.setActive(rs.getBoolean("Active"));

                JFXButton serviceRemoveButton = new JFXButton("Remove");
                serviceRemoveButton.getStyleClass().add("btn-red-color");
                serviceRemoveButton.setOnAction((event) -> {
                    FXMLMainFormController mainFormController = new FXMLMainFormController();
                    FXMLAddNewServiceOrderController addNewServiceOrderController = new FXMLAddNewServiceOrderController();
                    mainFormController = ConnectControllers.getfXMLMainFormController();
                    addNewServiceOrderController = ConnectControllers.getfXMLAddNewServiceOrderController();

                    deleteServiceOrdersDetail(serviceOrderDetail);

                    DAO.setUserLogs_With_MAC(mainFormController.getUserRole().getEmployee_ID(), "Delete ServiceOrderDetail, ServiceName: "
                            + serviceOrderDetail.getServiceName(),
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), mainFormController.macAdress);
                    //addNewServiceOrderController.update_Service_Type_After_Remove();

                    ServiceType serviceType = new ServiceType();
                    ServiceTypeDAOImpl serviceTypeDAOImpl = new ServiceTypeDAOImpl();
                    //Data are not changed
                    serviceType.setImageView(serviceOrderDetail.getImageView());
                    serviceType.setServiceDescription(serviceOrderDetail.getServiceDescription());
                    serviceType.setServiceID(serviceOrderDetail.getServiceID());
                    serviceType.setServiceImage(serviceOrderDetail.getServiceImage());
                    serviceType.setServiceName(serviceOrderDetail.getServiceName());
                    serviceType.setServicePrice(serviceOrderDetail.getServicePrice());
                    serviceType.setServiceUnit(serviceOrderDetail.getServiceUnit());
                    serviceType.setServiceImportQuantity(serviceOrderDetail.getServiceImportQuantity());
                    serviceType.setServiceImportDate(serviceOrderDetail.getServiceImportDate());
                    //Data are changed
                    serviceType.setServiceInventory(serviceOrderDetail.getServiceInventory() + serviceOrderDetail.getServiceQuantity());
                    serviceType.setServiceExportQuantity(serviceOrderDetail.getServiceExportQuantity() - serviceOrderDetail.getServiceQuantity());
                    serviceType.setServiceExportDate(LocalDateTime.now());
                    serviceType.setUserName(mainFormController.getUserRole().getEmployee_ID());
                    serviceTypeDAOImpl.editServiceType(serviceType, true);
                    //Refresh
                    addNewServiceOrderController.refresh_After_Remove();
                });
                serviceOrderDetail.setServiceRemoveButton(serviceRemoveButton);

                serviceOrderDetail.setServiceName(rs.getNString("ServiceName"));
                serviceOrderDetail.setServiceUnit(rs.getNString("ServiceUnit"));
                serviceOrderDetail.setServicePrice(rs.getBigDecimal("ServicePrice"));
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
                serviceOrderDetail.setServiceImportQuantity(rs.getInt("ImportQuantity"));
                serviceOrderDetail.setServiceImportDate(rs.getTimestamp("ImportDate").toLocalDateTime());
                serviceOrderDetail.setServiceExportQuantity(rs.getInt("ExportQuantity"));
                serviceOrderDetail.setServiceExportDate(rs.getTimestamp("ExportDate").toLocalDateTime());

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
                stmt.setBigDecimal(5, serviceOrderDetail.getServicePriceTotal());
                stmt.setBigDecimal(6, serviceOrderDetail.getServiceDiscount());
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
                + "WHERE ServiceID=? AND OrderID=?";

        try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, serviceOrderDetail.getOrderID());
            stmt.setString(2, serviceOrderDetail.getServiceID());
            stmt.setString(3, serviceOrderDetail.getUserName());
            stmt.setInt(4, serviceOrderDetail.getServiceQuantity());
            stmt.setBigDecimal(5, serviceOrderDetail.getServicePriceTotal());
            stmt.setBigDecimal(6, serviceOrderDetail.getServiceDiscount());
            stmt.setBoolean(7, true);
            stmt.setString(8, serviceOrderDetail.getServiceID());
            stmt.setString(9, serviceOrderDetail.getOrderID());
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
