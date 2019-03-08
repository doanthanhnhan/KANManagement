/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controllers.FXMLCheckInRoomController;
import java.sql.Connection;
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
}
