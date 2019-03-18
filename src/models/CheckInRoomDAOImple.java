/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controllers.FXMLCheckInRoomController;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import utils.connectDB;

/**
 *
 * @author ASUS
 */
public class CheckInRoomDAOImple {

    public static ResultSet getAllDataBooking() {
        try {
            // TODO
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            String sql = "SELECT * FROM BookingInfo";
            conn = connectDB.connectSQLServer();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            return rs;

        } catch (SQLException ex) {
            Logger.getLogger(FXMLCheckInRoomController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLCheckInRoomController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ResultSet getAllDataCustomer() {
        try {
            // TODO
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            String sql = "SELECT * FROM Customers";
            conn = connectDB.connectSQLServer();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(FXMLCheckInRoomController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLCheckInRoomController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void deleteDataCIR(String string) {
        try {
            // TODO
            Connection conn = null;
            PreparedStatement ps = null;;

            String sql = "DELETE FROM BookingInfo WHERE BookingID = ?";
            conn = connectDB.connectSQLServer();
            ps = conn.prepareStatement(sql);

            ps.setString(1, string);
            ps.execute();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(FXMLCheckInRoomController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLCheckInRoomController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ObservableList<Customer> listCustomer() throws SQLException {

        ObservableList<Customer> list = FXCollections.observableArrayList();

        ResultSet rs = getAllDataCustomer();

        while (rs.next()) {

            Date date = rs.getDate("CustomerBirthday");
            DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
            String strDate = dateFormat.format(date);

            list.add(new Customer(rs.getString("CustomerID"), rs.getString("UserName"), rs.getString("CustomerFirstName"), rs.getString("CustomerLastName"), rs.getString("CustomerEmail"), rs.getString("CustomerPassport"), rs.getString("CustomerPhoneNumber"), rs.getString("Company"), strDate, rs.getBoolean("Sex"), rs.getBoolean("Active"), rs.getFloat("Discount")));
        }

        return list;

    }

    public static ObservableList<BookingInfo> listBooking() throws SQLException {

        ObservableList<BookingInfo> list = FXCollections.observableArrayList();

        ResultSet rs = getAllDataBooking();

        while (rs.next()) {

            Date date = rs.getDate("DateBook");
            DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
            String strDate = dateFormat.format(date);

            list.add(new BookingInfo(rs.getString("BookingID"), rs.getString("CustomerID"), rs.getString("RoomID"), rs.getString("UserName"), rs.getString("Note"), rs.getString("Flight"), strDate, rs.getBoolean("BookDrive"), rs.getInt("NumberGuest")));
        }
        
        return list;

    }

    public static ObservableList<CheckInRoom> listAddTableCheckIn() throws SQLException {

        ObservableList<CheckInRoom> list = FXCollections.observableArrayList();

        ResultSet rs = getAllDataBooking();

        String check;

        while (rs.next()) {
            if (rs.getBoolean("BookDrive")) {
                check = "Yes";
            } else {
                check = "No";
            }
            Date date = rs.getDate("DateBook");
            DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
            String strDate = dateFormat.format(date);

            list.add(new CheckInRoom(rs.getString("BookingID"), rs.getString("FirstName") + " " + rs.getString("LastName"), rs.getString("Phone"), rs.getString("Email"), rs.getString("Company"), rs.getString("Note"), rs.getString("RoomType"), rs.getString("Flight"), check, Integer.toString(rs.getInt("NumberGuest")), strDate));
        }

        return list;

    }

    public static ObservableList<CheckInRoom> searchString(String string) {

        ObservableList<CheckInRoom> list = FXCollections.observableArrayList();

        return list;

    }

    public static int checkContinueString(String a, String b) {

//        if (!a.contains(b)) {
//            System.out.println("a khong chua b");
//            int size = b.length() - 1;
//            if (size != 0) {
//                char[] bb = b.toCharArray();
//                System.out.println("a.length = " + a.length());
//                outloops:
//                for (int i = 0; i < b.length() - size; i++) {
//                    System.out.println("size + i = " + (size + i));
//                    System.out.println("bb size = " + bb.length);
//                    String string = String.copyValueOf(bb, i, size + i);
//
//                    System.out.println("string = " + string);
//                    if (string.length() == 1) {
//                        continue outloops;
//                    } else {
//                        checkContinueString(a, string);
//                    }
//                    
//                    System.out.println("i = " + i);
//
//                }
//            } else {
//                System.out.println("size = 0");
//                return 0;
//            }
//        } else {
//            System.out.println("a chua b");
//            return b.length();
//        }
//        System.out.println("ket thuc method");
        int size = b.length();
        System.out.println(b.length());
        while (!a.contains(b)) {
            if (size != 1) {
                size--;
                char[] bb = b.toCharArray();

                for (int i = 0; i < b.length() - size + 1; i++) {
                    String string = String.copyValueOf(bb, i, size);
                    System.out.println(string);
                    if (a.contains(string)) {
                        return size;
                    }
                }
            } else {
                break;
            }
        }
        return size;
    }

    public static ObservableList<CheckInRoom> listCheckIn(ResultSet rs) throws SQLException {
        ObservableList<CheckInRoom> list = FXCollections.observableArrayList();

        String check;

        while (rs.next()) {
            if (rs.getBoolean("BookDrive")) {
                check = "Yes";
            } else {
                check = "No";
            }
            Date date = rs.getDate("DateBook");
            DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
            String strDate = dateFormat.format(date);

            CheckInRoom cir = new CheckInRoom();
            cir.setID(rs.getString("BookingID"));
            cir.setName(rs.getString("FirstName") + " " + rs.getString("LastName"));
            cir.setPhone(rs.getString("Phone"));
            cir.setMail(rs.getString("Email"));
            cir.setCompany(rs.getString("Company"));
            cir.setNote(rs.getString("Note"));
            cir.setRoomType(rs.getString("RoomType"));
            cir.setFlight(rs.getString("Flight"));
            cir.setDrive(check);
            cir.setNum(Integer.toString(rs.getInt("NumberGuest")));
            cir.setDate(strDate);

            list.add(cir);
        }
        return list;
    }

    public static String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

//    public static void main(String[] args) {
//        String a = "thienan";
//        String b = "zzzzz";
//        int h = CheckInRoomDAOImple.checkContinueString(a, b);
//        System.out.println(h);
//    }
}
