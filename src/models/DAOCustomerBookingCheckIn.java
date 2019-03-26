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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import utils.connectDB;

/**
 *
 * @author Admin
 */
public class DAOCustomerBookingCheckIn {
//    Add new checkIn
    //    Add new Customer

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
        pt.setString(9, ck.getDateIn());
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
        String sql = "select Bookinginfo.BookingID from Bookinginfo, CheckInOrders  where Bookinginfo.BookingID != CheckInOrders.BookingID";
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
