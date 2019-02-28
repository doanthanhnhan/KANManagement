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
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
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
    
    public void createTableCol(){
        TableColumn<CheckInRoom, String> col_ID = new TableColumn<>("ID");
            col_ID.setMinWidth(50);
            col_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
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
//            while (rs.next()) {
//
//                //Define Date
//                String Date;
//                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
//                Date = dateFormat.format(rs.getDate("CustomerBirthDay"));
//
//                checkin.add(new CheckInRoom(rs.getString("BookingID"), rs.getString("FirstName") + rs.getString("LastName"), rs.getString("Phone"), rs.getString("Email"), rs.getString("Company"), rs.getString("Note"), rs.getString("RoomType"), rs.getString("Flight"), Drive, Integer.toString(rs.getInt("NumberGuest")), Date));
//            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLCheckInRoomController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLCheckInRoomController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}