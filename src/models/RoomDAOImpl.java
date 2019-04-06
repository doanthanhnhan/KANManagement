/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.jfoenix.controls.JFXButton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import utils.FormatName;
import utils.connectDB;

/**
 *
 * @author Doan Thanh Nhan
 */
public class RoomDAOImpl implements RoomDAO {

    @Override
    public Room getRoom(String roomID) {
        String sql = "SELECT R.*, C.CustomerFirstName+' '+C.CustomerMidName+ ' ' +C.CustomerLastName AS 'CustomerFullName', C.Discount AS 'CusDicount',\n"
                + "DATEDIFF(HOUR,GETDATE(),R.LeaveDate)/24 AS 'Day_Leave',\n"
                + "DATEDIFF(HOUR,R.BookingDate,GETDATE())/24 AS 'Day_Booking', \n"
                + "RT.Price, RT.Discount\n"
                + "FROM Rooms R, Customers C, RoomType RT\n"
                + "WHERE R.CustomerID = C.CustomerID AND R.Active=1 AND R.RoomType = RT.RoomType AND R.RoomID = '" + roomID + "'";
        Room room = new Room();
        try {
            try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {

                    room.setRoomID(rs.getString("RoomID"));
                    room.setCustomerID(rs.getString("CustomerID"));
                    room.setCustomerName(FormatName.format(rs.getString("CustomerFullName")));
                    room.setUserName(rs.getString("UserName"));
                    room.setRoomType(rs.getString("RoomType"));
                    room.setRoomPhoneNumber(rs.getString("PhoneNumber"));
                    room.setRoomOnFloor(rs.getInt("RoomOnFloor"));
                    room.setRoomArea(rs.getFloat("RoomArea"));
                    room.setRoomStatus(rs.getString("RoomStatus"));
                    room.setRoomClean(rs.getBoolean("Clean"));
                    room.setRoomRepaired(rs.getBoolean("Repaired"));
                    room.setRoomInProgress(rs.getBoolean("InProgress"));
                    room.setDayRemaining(rs.getInt("DayRemaining"));
                    room.setActive(rs.getBoolean("Active"));
                    room.setBookingDate(rs.getTimestamp("BookingDate").toLocalDateTime());
                    room.setCheckInDate(rs.getTimestamp("CheckInDate").toLocalDateTime());
                    room.setLeaveDate(rs.getTimestamp("LeaveDate").toLocalDateTime());
                    room.setDayLeave(rs.getInt("Day_Leave"));
                    room.setDayBooking(rs.getInt("Day_Booking"));
                    room.setRoomPrice(rs.getBigDecimal("Price"));
                    room.setRoomDiscount(rs.getBigDecimal("Discount"));
                    room.setCusDiscount(rs.getBigDecimal("CusDicount"));
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error Querry");
                alert.setContentText("Don't have any rooms in Database or Can't connect to Database");
                alert.show();
            });
            Logger.getLogger(RoomDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return room;
    }

    @Override
    public ObservableList<Room> getAllRoom() {
        String sql = "SELECT R.*, C.CustomerFirstName+' '+C.CustomerMidName+ ' ' +C.CustomerLastName AS 'CustomerFullName',\n"
                + "DATEDIFF(HOUR,GETDATE(),R.LeaveDate)/24 AS 'Day_Leave',\n"
                + "DATEDIFF(HOUR,R.BookingDate,GETDATE())/24 AS 'Day_Booking', \n"
                + "RT.Price, RT.Discount\n"
                + "FROM Rooms R, Customers C, RoomType RT\n"
                + "WHERE R.CustomerID = C.CustomerID AND R.RoomType = RT.RoomType AND R.Active=1";
        ObservableList<Room> listRooms = FXCollections.observableArrayList();
        try {
            try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Room room = new Room();
                    room.setRoomID(rs.getString("RoomID"));
                    room.setCustomerID(rs.getString("CustomerID"));
                    room.setCustomerName(FormatName.format(rs.getString("CustomerFullName")));
                    room.setUserName(rs.getString("UserName"));
                    room.setRoomType(rs.getString("RoomType"));
                    room.setRoomPhoneNumber(rs.getString("PhoneNumber"));
                    room.setRoomOnFloor(rs.getInt("RoomOnFloor"));
                    room.setRoomArea(rs.getFloat("RoomArea"));
                    room.setRoomStatus(rs.getString("RoomStatus"));
                    room.setRoomClean(rs.getBoolean("Clean"));
                    room.setRoomRepaired(rs.getBoolean("Repaired"));
                    room.setRoomInProgress(rs.getBoolean("InProgress"));
                    room.setDayRemaining(rs.getInt("DayRemaining"));
                    room.setActive(rs.getBoolean("Active"));
                    room.setBookingDate(rs.getTimestamp("BookingDate").toLocalDateTime());
                    room.setCheckInDate(rs.getTimestamp("CheckInDate").toLocalDateTime());
                    room.setLeaveDate(rs.getTimestamp("LeaveDate").toLocalDateTime());
                    room.setDayLeave(rs.getInt("Day_Leave"));
                    room.setDayBooking(rs.getInt("Day_Booking"));
                    room.setRoomPrice(rs.getBigDecimal("Price"));
                    room.setRoomDiscount(rs.getBigDecimal("Discount"));
                    listRooms.add(room);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error Querry");
                alert.setContentText("Don't have any rooms in Database or Can't connect to Database");
                alert.show();
            });
            Logger.getLogger(RoomDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listRooms;
    }

    @Override
    public ObservableList<Room> getAllStatusRooms(String roomStatus) {
        String sql = "SELECT R.*, C.CustomerFirstName+' '+C.CustomerMidName+ ' ' +C.CustomerLastName AS 'CustomerFullName' \n"
                + "FROM Rooms R, Customers C\n"
                + "WHERE R.CustomerID = C.CustomerID AND R.RoomStatus='" + roomStatus + "' AND R.Active=1";
        ObservableList<Room> listRooms = FXCollections.observableArrayList();
        try {
            try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Room room = new Room();
                    room.setRoomID(rs.getString("RoomID"));
                    room.setCustomerID(rs.getString("CustomerID"));
                    room.setCustomerName(FormatName.format(rs.getString("CustomerFullName")));
                    room.setUserName(rs.getString("UserName"));
                    room.setRoomType(rs.getString("RoomType"));
                    room.setRoomPhoneNumber(rs.getString("PhoneNumber"));
                    room.setRoomOnFloor(rs.getInt("RoomOnFloor"));
                    room.setRoomArea(rs.getFloat("RoomArea"));
                    room.setRoomStatus(rs.getString("RoomStatus"));
                    room.setRoomClean(rs.getBoolean("Clean"));
                    room.setRoomRepaired(rs.getBoolean("Repaired"));
                    room.setRoomInProgress(rs.getBoolean("InProgress"));
                    room.setDayRemaining(rs.getInt("DayRemaining"));
                    room.setActive(rs.getBoolean("Active"));

                    listRooms.add(room);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error Querry");
                alert.setContentText("Don't have any rooms in Database or Can't connect to Database");
                alert.show();
                Logger.getLogger(RoomDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            });
        }
        return listRooms;
    }

    @Override
    public ObservableList<String> getAllRoomID() {
        String sql = "SELECT RoomID FROM Rooms WHERE Active=1";
        ObservableList<String> listRooms = FXCollections.observableArrayList();
        try {
            try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String roomID;
                    roomID = rs.getString("RoomID");

                    listRooms.add(roomID);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error Querry");
                alert.setContentText("Don't have any rooms in Database or Can't connect to Database");
                alert.show();
                Logger.getLogger(RoomDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            });
        }
        return listRooms;
    }

    @Override
    public List<String> getAll_Available_RoomID() {
        String sql = "SELECT RoomID FROM Rooms WHERE RoomStatus='Avaiable' AND Active=1";
        ObservableList<String> listRooms = FXCollections.observableArrayList();
        try {
            try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String roomID;
                    roomID = rs.getString("RoomID");

                    listRooms.add(roomID);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error Querry");
                alert.setContentText("Don't have any rooms in Database or Can't connect to Database");
                alert.show();
                Logger.getLogger(RoomDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            });
        }
        return listRooms;
    }

    @Override
    public ObservableList<String> getAllStatusRoomID(String roomStatus) {
        String sql = "SELECT RoomID FROM Rooms WHERE RoomStatus='" + roomStatus + "' AND Active=1";
        ObservableList<String> listRooms = FXCollections.observableArrayList();
        try {
            try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String roomID;
                    roomID = rs.getString("RoomID");

                    listRooms.add(roomID);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error Querry");
                alert.setContentText("Don't have any rooms in Database or Can't connect to Database");
                alert.show();
                Logger.getLogger(RoomDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            });
        }
        return listRooms;
    }

    @Override
    public ObservableList<String> getAllCustomerID() {
        String sql = "SELECT CustomerID FROM Rooms";
        ObservableList<String> listRooms = FXCollections.observableArrayList();
        try {
            try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String roomID;
                    roomID = rs.getString("CustomerID");

                    listRooms.add(roomID);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error Querry");
                alert.setContentText("Don't have any rooms in Database or Can't connect to Database");
                alert.show();
                Logger.getLogger(RoomDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            });
        }
        return listRooms;
    }

    @Override
    public ObservableList<String> getAllStatusCustomerID(String roomStatus) {
        String sql = "SELECT CustomerID FROM Rooms WHERE RoomStatus='" + roomStatus + "' AND Active=1"
                + " GROUP BY CustomerID";
        ObservableList<String> listRooms = FXCollections.observableArrayList();
        try {
            try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String roomID;
                    roomID = rs.getString("CustomerID");

                    listRooms.add(roomID);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error Querry");
                alert.setContentText("Don't have any rooms in Database or Can't connect to Database");
                alert.show();
                Logger.getLogger(RoomDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            });
        }
        return listRooms;
    }

    public ObservableList<RoomEX> getAllRoomEX() {
        String sql = "SELECT R.*, C.CustomerFirstName+' '+C.CustomerMidName+ ' ' +C.CustomerLastName AS 'CustomerFullName' \n"
                + "FROM Rooms R, Customers C\n"
                + "WHERE R.CustomerID = C.CustomerID AND R.Active=1";
        ObservableList<RoomEX> listRooms = FXCollections.observableArrayList();
        try {
            try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    RoomEX room = new RoomEX();
                    room.setRoomID(rs.getString("roomID"));
                    room.setCustomerID(rs.getString("CustomerID"));
                    room.setCustomerName(FormatName.format(rs.getString("CustomerFullName")));
                    room.setUserName(rs.getString("userName"));
                    room.setRoomType(rs.getString("RoomType"));
                    room.setRoomPhoneNumber(rs.getString("PhoneNumber"));
                    room.setRoomOnFloor(rs.getInt("RoomOnFloor"));
                    room.setRoomArea(rs.getFloat("RoomArea"));
                    room.setRoomStatus(rs.getString("RoomStatus"));
                    room.setRoomClean(rs.getBoolean("Clean"));
                    room.setRoomRepaired(rs.getBoolean("Repaired"));
                    room.setRoomInProgress(rs.getBoolean("InProgress"));
                    room.setDayRemaining(rs.getInt("DayRemaining"));
                    room.setActive(rs.getBoolean("Active"));

                    //Setting checkboxes
                    CheckBox cb_Clean = new CheckBox("");
                    CheckBox cb_Repaired = new CheckBox("");
                    CheckBox cb_InProgress = new CheckBox("");
                    cb_Clean.setSelected(rs.getBoolean("Clean"));
                    cb_Clean.setDisable(true);
                    cb_Repaired.setSelected(rs.getBoolean("Repaired"));
                    cb_Repaired.setDisable(true);
                    cb_InProgress.setSelected(rs.getBoolean("InProgress"));
                    cb_InProgress.setDisable(true);
                    room.setCheckBox_Room_Clean(cb_Clean);
                    room.setCheckBox_Room_Repaired(cb_Repaired);
                    room.setCheckBox_Room_In_Progress(cb_InProgress);

                    //Setting buttons
                    HBox roomAction = new HBox();

                    JFXButton btn_Check_In = new JFXButton("Check In");
                    btn_Check_In.getStyleClass().add("btn-green-color");

                    JFXButton btn_Check_Out = new JFXButton("Check Out");
                    btn_Check_Out.getStyleClass().add("btn-red-color");

                    JFXButton btn_Services = new JFXButton("Services");
                    btn_Services.getStyleClass().add("btn-warning-color");

                    if (rs.getString("RoomStatus").equalsIgnoreCase("Occupied")) {
                        btn_Check_In.setDisable(true);
                    } else if (rs.getString("RoomStatus").equalsIgnoreCase("Available")
                            || rs.getString("RoomStatus").equalsIgnoreCase("Reserved")
                            || rs.getString("RoomStatus").equalsIgnoreCase("Out")) {
                        btn_Check_Out.setDisable(true);
                        btn_Services.setDisable(true);
                    }

                    roomAction.setAlignment(Pos.CENTER);
                    roomAction.setSpacing(10);
                    roomAction.getChildren().addAll(btn_Check_In, btn_Services, btn_Check_Out);
                    room.setRoomAction(roomAction);

                    //Add room to list
                    listRooms.add(room);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error Querry");
                alert.setContentText("Don't have any rooms in Database or Can't connect to Database");
                alert.show();
                Logger.getLogger(RoomDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            });
        }
        return listRooms;
    }

    @Override
    public void addRoom(Room room) {
        String sql = "INSERT INTO Rooms (RoomID, CustomerID, UserName, RoomType, PhoneNumber, RoomOnFloor, RoomArea, RoomStatus, DayRemaining, Clean, Repaired, InProgress) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, room.getRoomID());
                stmt.setString(2, room.getCustomerID());
                //stmt.setString(3, room.getUserName());
                stmt.setString(3, "admin");
                stmt.setString(4, room.getRoomType());
                stmt.setString(5, room.getRoomPhoneNumber());
                stmt.setInt(6, room.getRoomOnFloor());
                stmt.setFloat(7, room.getRoomArea());
                stmt.setString(8, room.getRoomStatus());
                stmt.setInt(9, room.getDayRemaining());
                stmt.setBoolean(10, room.isRoomClean());
                stmt.setBoolean(11, room.isRoomRepaired());
                stmt.setBoolean(12, room.isRoomInProgress());

                stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Platform.runLater(() -> {
                Logger.getLogger(ServiceTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error Querry");
                alert.setContentText("Duplicated RoomID in Database or Can't connect to Database");
                alert.show();
            });
        }
    }

    @Override
    public void editRoom(Room room, Boolean active) {
        String sql = "UPDATE Rooms SET RoomID=?, CustomerID=?, UserName=?, RoomType=?, "
                + "PhoneNumber=?, RoomOnFloor=?, RoomArea=?,  "
                + "RoomStatus=?, DayRemaining=?, Clean=?, "
                + "Repaired=?, InProgress=? "
                + "WHERE RoomID=?";
        try {
            try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, room.getRoomID());
                stmt.setString(2, room.getCustomerID());
                //stmt.setString(3, room.getUserName());
                stmt.setString(3, "admin");
                stmt.setString(4, room.getRoomType());
                stmt.setString(5, room.getRoomPhoneNumber());
                stmt.setInt(6, room.getRoomOnFloor());
                stmt.setFloat(7, room.getRoomArea());
                stmt.setString(8, room.getRoomStatus());
                stmt.setInt(9, room.getDayRemaining());
                stmt.setBoolean(10, room.isRoomClean());
                stmt.setBoolean(11, room.isRoomRepaired());
                stmt.setBoolean(12, room.isRoomInProgress());
                stmt.setString(13, room.getRoomID());
                stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Platform.runLater(() -> {
                Logger.getLogger(ServiceTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error Querry");
                alert.setContentText("Duplicated Room in Database or Can't connect to Database");
                alert.show();
            });
        }
    }

    @Override
    public void editCheckInRoom(Room room, Boolean active) {
        String sql = "UPDATE Rooms SET CustomerID=?, UserName=?, RoomStatus=?, "
                + "DayRemaining=?, CheckInDate=?, LeaveDate=? "
                + "WHERE RoomID=?";
        try {
            try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, room.getCustomerID());
                stmt.setString(2, room.getUserName());
                stmt.setString(3, room.getRoomStatus());
                stmt.setInt(4, room.getDayRemaining());
                stmt.setTimestamp(5, Timestamp.valueOf(room.getCheckInDate()));
                stmt.setTimestamp(6, Timestamp.valueOf(room.getLeaveDate()));
                stmt.setString(7, room.getRoomID());
                stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Platform.runLater(() -> {
                Logger.getLogger(ServiceTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error Querry");
                alert.setContentText("Duplicated Room in Database or Can't connect to Database");
                alert.show();
            });
        }
    }

    @Override
    public void editCheckOutRoom(Room room, Boolean active) {
        String sql = "UPDATE Rooms SET CustomerID=?, UserName=?, RoomStatus=?, "
                + "DayRemaining=?, LeaveDate=?, Clean=?, InProgress=? "
                + "WHERE RoomID=?";
        try {
            try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, room.getCustomerID());
                stmt.setString(2, room.getUserName());
                stmt.setString(3, room.getRoomStatus());
                stmt.setInt(4, room.getDayRemaining());
                stmt.setTimestamp(5, Timestamp.valueOf(room.getLeaveDate()));
                stmt.setBoolean(6, false);
                stmt.setBoolean(7, true);
                stmt.setString(8, room.getRoomID());
                stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Platform.runLater(() -> {
                Logger.getLogger(ServiceTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error Querry");
                alert.setContentText("Duplicated Room in Database or Can't connect to Database");
                alert.show();
            });
        }
    }

    @Override
    public void editAfterCheckingRoom(Room room, Boolean active) {
        String sql = "UPDATE Rooms SET UserName=?, RoomStatus=?, "
                + "Clean=?, InProgress=?, Repaired=? "
                + "WHERE RoomID=?";
        try {
            try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, room.getUserName());
                stmt.setString(2, room.getRoomStatus());
                stmt.setBoolean(3, true);
                stmt.setBoolean(4, false);
                stmt.setBoolean(5, true);
                stmt.setString(6, room.getRoomID());
                stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Platform.runLater(() -> {
                Logger.getLogger(ServiceTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error Querry");
                alert.setContentText("Duplicated Room in Database or Can't connect to Database");
                alert.show();
            });
        }
    }

    @Override
    public void editBookingRoom(Room room, Boolean active) {
        String sql = "UPDATE Rooms SET CustomerID=?, UserName=?, RoomStatus=?, "
                + "DayRemaining=?, BookingDate=?, LeaveDate=? "
                + "WHERE RoomID=?";
        try {
            try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, room.getCustomerID());
                stmt.setString(2, room.getUserName());
                stmt.setString(3, room.getRoomStatus());
                stmt.setInt(4, room.getDayRemaining());
                stmt.setTimestamp(5, Timestamp.valueOf(room.getBookingDate()));
                stmt.setTimestamp(6, Timestamp.valueOf(room.getLeaveDate()));
                stmt.setString(7, room.getRoomID());
                stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Platform.runLater(() -> {
                Logger.getLogger(ServiceTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error Querry");
                alert.setContentText("Duplicated Room in Database or Can't connect to Database");
                alert.show();
            });
        }
    }

    @Override
    public void deleteRoom(Room room) {
        String sql = "DELETE FROM Rooms WHERE RoomID=?";
        try {
            try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, room.getRoomID());
                stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Platform.runLater(() -> {
                Logger.getLogger(ServiceTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error Querry");
                alert.setContentText("Can't connect to Database");
                alert.show();
            });
        }
    }

    @Override
    public void deleteRoomEX(RoomEX roomEX) {
        String sql = "UPDATE Rooms SET Active=? WHERE RoomID=?";
        try {
            try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setBoolean(1, false);
                stmt.setString(2, roomEX.getRoomID());
                stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Platform.runLater(() -> {
                Logger.getLogger(ServiceTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error Querry");
                alert.setContentText("Can't connect to Database");
                alert.show();
            });
        }
    }

    public ObservableList<RoomProperty> getAllRoomProperties() {
        String sql = "SELECT * FROM view_RoomProperty";
        ObservableList<RoomProperty> listRooms = FXCollections.observableArrayList();
        try {
            try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    RoomProperty room = new RoomProperty();
                    room.setRoomPropertyName(rs.getString("PropertyName"));
                    room.setRoomCount(rs.getInt("Total"));

                    listRooms.add(room);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error Querry");
                alert.setContentText("Don't have any rooms in Database or Can't connect to Database");
                alert.show();
                Logger.getLogger(RoomDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            });
        }
        return listRooms;
    }

//public 
    public static void main(String[] args) {
        RoomDAOImpl roomDAOImpl = new RoomDAOImpl();
        ObservableList<Room> listRooms = FXCollections.observableArrayList();
        listRooms = roomDAOImpl.getAllRoom();
        for (Room listRoom : listRooms) {
            System.out.println(listRoom.toString());
        }
    }
}
