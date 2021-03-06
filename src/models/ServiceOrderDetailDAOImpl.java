/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
        String sql = "SELECT SOD.OrderID, SOD.ServiceQuantity, SOD.Price, SOD.Discount, SOD.Finish, SOD.CheckOut, "
                + "ST.*, SO.ServiceOrderDate, SO.CustomerID, SO.RoomID, "
                + "CASE WHEN C.CustomerMidName <> '' THEN C.CustomerFirstName+' '+C.CustomerMidName+ ' ' +C.CustomerLastName "
                + "ELSE C.CustomerFirstName+' ' +C.CustomerLastName END AS 'CustomerFullName' "
                + "FROM ServicesOrderDetails SOD  "
                + "INNER JOIN ServiceType ST ON SOD.ServiceID = ST.ServiceID "
                + "INNER JOIN ServicesOrders SO ON SOD.OrderID = SO.OrderID "
                + "INNER JOIN Customers C ON C.CustomerID = SO.CustomerID "
                + "WHERE SOD.Active=1";
        ObservableList<ServiceOrderDetail> listServiceOrderDetails = FXCollections.observableArrayList();

        try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ServiceOrderDetail serviceOrderDetail = new ServiceOrderDetail();
                serviceOrderDetail.setOrderID(rs.getString("OrderID"));
                serviceOrderDetail.setCustomerID(rs.getString("CustomerID"));
                serviceOrderDetail.setCustomerFullName(rs.getString("CustomerFullName"));
                serviceOrderDetail.setRoomID(rs.getString("RoomID"));
                serviceOrderDetail.setServiceID(rs.getString("ServiceID"));
                serviceOrderDetail.setUserName(rs.getString("UserName"));
                serviceOrderDetail.setServiceQuantity(rs.getInt("ServiceQuantity"));
                serviceOrderDetail.setServicePriceTotal(rs.getBigDecimal("Price"));
                serviceOrderDetail.setServiceDiscount(rs.getBigDecimal("Discount"));
                serviceOrderDetail.setActive(rs.getBoolean("Active"));
                serviceOrderDetail.setServiceFinish(rs.getBoolean("Finish"));
                serviceOrderDetail.setServiceCheckOut(rs.getBoolean("CheckOut"));

                JFXCheckBox checkBox = new JFXCheckBox();
                checkBox.setOnAction((event) -> {
                    updateSODFinish(!serviceOrderDetail.isServiceFinish(), serviceOrderDetail.getOrderID(), serviceOrderDetail.getServiceID());
                });
                if (serviceOrderDetail.isServiceFinish()) {
                    checkBox.setSelected(true);
                }
                if (serviceOrderDetail.isServiceCheckOut()) {
                    checkBox.setDisable(true);
                }
                serviceOrderDetail.setCheckBox_Finish(checkBox);

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
        String sql = "SELECT SOD.OrderID, SOD.ServiceQuantity, SOD.Price, SOD.Discount, SOD.Finish, SOD.CheckOut, "
                + "ST.* FROM ServicesOrderDetails SOD \n"
                + "INNER JOIN ServiceType ST\n"
                + "ON SOD.ServiceID = ST.ServiceID\n"
                + "WHERE SOD.Active=1 AND SOD.OrderID='" + serviceOrderID + "'";
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
                serviceOrderDetail.setServiceFinish(rs.getBoolean("Finish"));
                serviceOrderDetail.setServiceCheckOut(rs.getBoolean("CheckOut"));

                JFXCheckBox checkBox = new JFXCheckBox();
                checkBox.setOnAction((event) -> {
                    updateSODFinish(!serviceOrderDetail.isServiceFinish(), serviceOrderDetail.getOrderID(), serviceOrderDetail.getServiceID());
                });
                if (serviceOrderDetail.isServiceFinish()) {
                    checkBox.setSelected(true);
                }
                if (serviceOrderDetail.isServiceCheckOut()) {
                    checkBox.setDisable(true);
                }
                serviceOrderDetail.setCheckBox_Finish(checkBox);

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
    public ObservableList<ServiceOrderDetail> get_All_Details_Of_CheckInRoom(String roomID, String checkInID) {
        String sql = "SELECT SOD.OrderID, SOD.ServiceQuantity, SOD.Price, SOD.Discount, SOD.Finish , ST.*, "
                + "SO.ServiceOrderDate, SO.CustomerID, SO.RoomID, "
                + "CIO.CheckInDate, CIO.CheckInID "
                + "FROM ServicesOrderDetails SOD "
                + "INNER JOIN ServiceType ST ON SOD.ServiceID = ST.ServiceID\n"
                + "INNER JOIN ServicesOrders SO ON SOD.OrderID = SO.OrderID\n"
                + "INNER JOIN CheckInOrders CIO ON SO.RoomID = CIO.RoomID\n"
                + "WHERE CIO.checkInID='"
                + checkInID
                + "' AND CIO.RoomID='"
                + roomID
                + "' AND SOD.Active=1 AND SOD.Finish=1 AND SO.Finish=1";
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
                serviceOrderDetail.setServiceFinish(rs.getBoolean("Finish"));

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
    public ObservableList<ServiceOrderDetail> get_All_Details_Of_CheckIn_Customer(String customerID) {
        String sql = "SELECT SOD.OrderID , SOD.ServiceID, SOD.ServiceQuantity, SOD.Price, SOD.Discount, SOD.Finish, ST.*, "
                + "SO.ServiceOrderDate, SO.CustomerID, SO.RoomID, \n"
                + "	CIO.CheckInDate, CIO.CheckInID,\n"
                + "	CASE WHEN C.CustomerMidName <> '' THEN C.CustomerFirstName+' '+C.CustomerMidName+ ' ' +C.CustomerLastName\n"
                + "	ELSE C.CustomerFirstName+' ' +C.CustomerLastName END AS 'CustomerFullName'\n"
                + "FROM ServicesOrderDetails SOD\n"
                + "INNER JOIN ServiceType ST ON SOD.ServiceID = ST.ServiceID\n"
                + "INNER JOIN ServicesOrders SO ON SOD.OrderID = SO.OrderID \n"
                + "INNER JOIN CheckInOrders CIO ON SO.RoomID = CIO.RoomID\n"
                + "INNER JOIN Customers C ON C.CustomerID = CIO.CustomerID\n"
                + "WHERE CIO.CustomerID='"
                + customerID
                + "' AND SOD.Finish=1 AND SO.Finish=1 AND SOD.CheckOut=0 AND CIO.CheckInID NOT IN (SELECT CheckInID FROM CheckOutOrders)";
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
                serviceOrderDetail.setServiceFinish(rs.getBoolean("Finish"));

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
    public ObservableList<CheckIn> get_All_CheckIn_Of_Customer(String customerID) {
        String sql = "SELECT SO.RoomID, CIO.CheckInID\n"
                + "FROM ServicesOrderDetails SOD\n"
                + "INNER JOIN ServiceType ST ON SOD.ServiceID = ST.ServiceID\n"
                + "INNER JOIN ServicesOrders SO ON SOD.OrderID = SO.OrderID \n"
                + "INNER JOIN CheckInOrders CIO ON SO.RoomID = CIO.RoomID\n"
                + "INNER JOIN Customers C ON C.CustomerID = CIO.CustomerID\n"
                + "WHERE CIO.CustomerID='"
                + customerID
                //+ "' AND SOD.Finish=1 AND SOD.CheckOut=0\n" Wrong query if Customer check in 1 room
                + "' AND CIO.CheckInID NOT IN (SELECT CheckInID FROM CheckOutOrders)\n"
                + "GROUP BY SO.RoomID, CIO.CheckInID";
        ObservableList<CheckIn> listCheckIn = FXCollections.observableArrayList();

        try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                CheckIn checkIn = new CheckIn();
                checkIn.setCheckID(rs.getString("CheckInID"));
                checkIn.setRoomID(rs.getString("RoomID"));

                listCheckIn.add(checkIn);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ServiceOrderDetailDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setHeaderText("Error");
            alert.setContentText("Don't have any Service Type in Database or Can't connect to Database");
            alert.show();
        }
        return listCheckIn;
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
                + "ServiceQuantity=?, Price=?, Discount=?, Finish=?, Active=? "
                + "WHERE ServiceID=? AND OrderID=?";

        try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, serviceOrderDetail.getOrderID());
            stmt.setString(2, serviceOrderDetail.getServiceID());
            stmt.setString(3, serviceOrderDetail.getUserName());
            stmt.setInt(4, serviceOrderDetail.getServiceQuantity());
            stmt.setBigDecimal(5, serviceOrderDetail.getServicePriceTotal());
            stmt.setBigDecimal(6, serviceOrderDetail.getServiceDiscount());
            stmt.setBoolean(7, serviceOrderDetail.isServiceFinish());
            stmt.setBoolean(8, true);
            stmt.setString(9, serviceOrderDetail.getServiceID());
            stmt.setString(10, serviceOrderDetail.getOrderID());
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
    public void updateSODFinish(boolean finish, String serviceOrderID, String serviceID) {
        String sql = "UPDATE ServicesOrderDetails SET Finish=? "
                + "WHERE ServiceID=? AND OrderID=?";

        try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, finish);

            stmt.setString(2, serviceID);
            stmt.setString(3, serviceOrderID);
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ServiceOrderDetailDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error");
                alert.setContentText("Duplicated Service Type in Database or Can't connect to Database");
                alert.show();
            });
        }
    }

    @Override
    public void updateSODCheckOut(boolean checkout, String serviceOrderID, String serviceID) {
        String sql = "UPDATE ServicesOrderDetails SET CheckOut=? "
                + "WHERE ServiceID=? AND OrderID=?";

        try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, checkout);

            stmt.setString(2, serviceID);
            stmt.setString(3, serviceOrderID);
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ServiceOrderDetailDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error");
                alert.setContentText("Duplicated Service Type in Database or Can't connect to Database");
                alert.show();
            });
        }
    }

    @Override
    public void update_CheckIN_SOD_CheckOut(String checkInID) {
        String sql = "UPDATE ServicesOrderDetails SET CheckOut=1 WHERE OrderID IN (\n"
                + "SELECT SO.OrderID FROM ServicesOrders SO\n"
                + "INNER JOIN (SELECT RoomID FROM CheckInOrders WHERE CheckInID NOT IN (SELECT CheckInID FROM CheckOutOrders) \n"
                + "AND CheckInID=?) CIO \n"
                + "ON SO.RoomID = CIO.RoomID)";

        try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, checkInID);
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ServiceOrderDetailDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error");
                alert.setContentText("Duplicated Service Type in Database or Can't connect to Database");
                alert.show();
            });
        }
    }

    @Override
    public boolean check_SOD_Per_SO_Finish(String serviceOrderID) {
        String sql = "SELECT * FROM ServicesOrderDetails WHERE OrderID=? AND Finish=0";
        try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, serviceOrderID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ServiceOrderDetailDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error");
                alert.setContentText("Can't connect to Database");
                alert.show();
            });
        }
        return false;
    }

    @Override
    public void deleteServiceOrdersDetail(ServiceOrderDetail serviceOrderDetail) {
        String sql = "UPDATE ServicesOrderDetails SET Active=? WHERE ServiceID=? AND OrderID=?";

        try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, false);
            stmt.setString(2, serviceOrderDetail.getServiceID());
            stmt.setString(3, serviceOrderDetail.getOrderID());
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
