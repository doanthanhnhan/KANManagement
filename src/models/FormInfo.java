/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.connectDB;

/**
 *
 * @author ASUS
 */
public class FormInfo implements DAOFormInfo {

    @Override
    public void AddNewBooking(String FName, String LName, String Mail, String Phone, String Note, String Company, String RoomType, int Number, LocalDate Date, boolean Check, String Flight) {
        try {
            Connection connection = connectDB.connectSQLServer();
            String exm = "Insert into BookingInfo(BookingID,FirstName,LastName,Email,Phone,Note,Company,RoomType,NumberGuest,DateBook,BookDrive,Flight) values(?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(exm);

            String ID = "BOO" + (new Random().nextInt(999)) + "test";
            ps.setString(1, ID);
            ps.setString(2, FName);
            ps.setString(3, LName);
            ps.setString(4, Mail);
            ps.setString(5, Phone);
            ps.setString(6, Note);
            ps.setString(7, Company);
            ps.setString(8, RoomType);
            ps.setInt(9, Number);
            ps.setDate(10, java.sql.Date.valueOf(Date));
            ps.setBoolean(11, Check);
            ps.setString(12, Flight);
            ps.execute();
            ps.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException ex1) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }
}
