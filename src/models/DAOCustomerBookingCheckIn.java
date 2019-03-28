/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.net.MalformedURLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import utils.connectDB;

/**
 *
 * @author Admin
 */
public class DAOCustomerBookingCheckIn {
//    check da booking hay chua

    public static boolean check_BookingIdCustomer(String ID) {
        try {
            Connection connection = connectDB.connectSQLServer();
            // Tạo đối tượng Statement.
            String sql = "select * from bookinginfo where CustomerID=? And DateDiff(Day,DateBook,GetDate()) >=0";
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            PreparedStatement pt = connection.prepareStatement(sql);
            pt.setString(1, ID);
            ResultSet rs = pt.executeQuery();
            if (rs.next()) {
                pt.close();
                connection.close();
                rs.close();
                return true;
            }
            pt.close();
            connection.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomerBookingCheckIn.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAOCustomerBookingCheckIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //Get check in room infomations - Nhan edit
    public static CheckIn getCheckInRoom(String roomID) {
        String sql = "SELECT * FROM CheckInOrders WHERE RoomID IN (SELECT RoomID FROM Rooms WHERE RoomStatus = 'Occupied') "
                + "AND RoomID = '" + roomID + "'";
        CheckIn checkIn = new CheckIn();
        try {
            try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    checkIn.setCheckID(rs.getString("CheckInID"));
                    checkIn.setBookID(rs.getString("BookingID"));
                    checkIn.setCusID(rs.getString("CustomerID"));
                    checkIn.setRoomID(rs.getString("RoomID"));
                    checkIn.setUser(rs.getString("UserName"));
                    checkIn.setCheckType(rs.getString("CheckInType"));
                    checkIn.setNumberOfCustomer(rs.getInt("NumberOfCustomer"));
                    checkIn.setDateIn(rs.getString("CheckInDate"));
                    checkIn.setDateOut(rs.getString("LeaveDate"));
                    checkIn.setCusPack(rs.getString("CustomerPackage"));                    
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error Querry");
                alert.setContentText("Don't have any check in ID in Database or Can't connect to Database");
                alert.show();
            });
            Logger.getLogger(DAOCustomerBookingCheckIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return checkIn;
    }
//    get info customer

    public static Customer getAllCustomerInfo(String id) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        Customer cm = new Customer();
        String sql = "select * from Customers where CustomerID=?";
        PreparedStatement pt = connection.prepareStatement(sql);
        pt.setString(1, id);
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        ResultSet rs = pt.executeQuery();
        while (rs.next()) {
            cm.setFName(rs.getString("CustomerFirstName"));
            cm.setMName(rs.getString("CustomerMidName"));
            cm.setLName(rs.getString("CustomerLastName"));
            cm.setDate(rs.getString("CustomerBirthday"));
            cm.setPhone(rs.getString("CustomerPhoneNumber"));
            cm.setEmail(rs.getString("CustomerEmail"));
            cm.setDiscount(rs.getFloat("Discount"));
            cm.setCompany(rs.getString("Company"));
            cm.setSex(rs.getBoolean("Sex"));
        }
        return cm;
    }

//    Add new checkIn
    public static void AddNewCheckIn(CheckIn ck) throws MalformedURLException, SQLException, ClassNotFoundException {
        Connection connection = connectDB.connectSQLServer();
        String exm = "Insert into CheckInOrders values(?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pt = connection.prepareStatement(exm);
        pt.setString(1, ck.getCheckID());
        pt.setString(2, ck.getBookID());
        pt.setString(3, ck.getCusID());
        pt.setString(4, ck.getRoomID());
        pt.setString(5, ck.getUser());
        pt.setString(6, ck.getCheckType());
        pt.setInt(7, ck.getNumberOfCustomer());
        pt.setString(8, ck.getDateIn());
        pt.setString(9, ck.getDateOut());
        pt.setString(10, ck.getCusPack());
        pt.setInt(11, 1);
        pt.execute();
        pt.close();
        connection.close();
    }

    //    get Add bookingID use for checkin
    public static ObservableList<String> getAllBookingID() throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        ObservableList<String> list_Booking_ID = FXCollections.observableArrayList();
        String sql = "select Bookinginfo.BookingID from Bookinginfo WHERE Bookinginfo.BookingID NOT IN (SELECT BookingID FROM CheckInOrders)";
        PreparedStatement pt = connection.prepareStatement(sql);

        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        ResultSet rs = pt.executeQuery();
        while (rs.next()) {
            list_Booking_ID.add(rs.getString("BookingID"));
        }
        return list_Booking_ID;
    }
//    get Add info booking

    public static BookingInfo getAllBookingInfo(String bookingID) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        BookingInfo bk = new BookingInfo();
        String sql = "select * from BookingInfo where BookingID=?";
        PreparedStatement pt = connection.prepareStatement(sql);
        pt.setString(1, bookingID);
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        ResultSet rs = pt.executeQuery();
        while (rs.next()) {
            bk.setCusID(rs.getString("CustomerID"));
            bk.setRoomID(rs.getString("RoomID"));
            bk.setNumGuest(rs.getInt("NumberGuest"));
        }
        return bk;
    }
//    Add New Booking

    public static void AddNewBooking(BookingInfo bk) throws MalformedURLException, SQLException, ClassNotFoundException {
        Connection connection = connectDB.connectSQLServer();
        String exm = "Insert into Bookinginfo values(?,?,?,?,?,?,?)";
        PreparedStatement pt = connection.prepareStatement(exm);
        pt.setString(1, bk.getBookID());
        pt.setString(2, bk.getCusID());
        pt.setString(3, bk.getRoomID());
        pt.setString(4, bk.getUser());
        pt.setString(5, bk.getNote());
        pt.setInt(6, bk.getNumGuest());
        pt.setString(7, bk.getDate());
        pt.execute();
        pt.close();
        connection.close();
    }
//    Get all Id of Customer

    public static ObservableList<String> getAllIdCustomer() throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        // Tạo đối tượng Statement.
        Statement statement = connection.createStatement();
        String sql = "select CustomerID from Customers";

        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        ResultSet rs = statement.executeQuery(sql);
        ObservableList listIdUser = FXCollections.observableArrayList();
        while (rs.next()) {
            listIdUser.add(rs.getString("CustomerID"));
        }
        connection.close();
        return listIdUser;
    }
//    Add new Customer

    public static void AddNewCustomer(Customer ctm) throws MalformedURLException, SQLException, ClassNotFoundException {
        Connection connection = connectDB.connectSQLServer();
        String exm = "Insert into Customers values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pt = connection.prepareStatement(exm);
        pt.setString(1, ctm.getCusID());
        pt.setString(2, ctm.getUser());
        pt.setString(3, ctm.getFName());
        pt.setString(4, ctm.getMName());
        pt.setString(5, ctm.getLName());
        pt.setString(6, ctm.getDate());
        pt.setString(7, ctm.getPhone());
        pt.setString(8, ctm.getPassport());
        pt.setString(9, ctm.getEmail());
        pt.setFloat(10, ctm.getDiscount());
        pt.setString(11, ctm.getCompany());
        pt.setBoolean(12, ctm.getActive());
        pt.setBoolean(13, ctm.getSex());
        pt.execute();
        pt.close();
        connection.close();
    }
//    Check invalid CustomerID

    public static boolean check_IDCustomer(String ID) {
        try {
            Connection connection = connectDB.connectSQLServer();
            // Tạo đối tượng Statement.
            String sql = "select CustomerID from Customers where CustomerID = ?";
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            PreparedStatement pt = connection.prepareStatement(sql);
            pt.setString(1, ID);
            ResultSet rs = pt.executeQuery();
            if (rs.next()) {
                pt.close();
                connection.close();
                rs.close();
                return false;
            }
            pt.close();
            connection.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomerBookingCheckIn.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAOCustomerBookingCheckIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
}
