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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    @Override
    public void AddNewCustomer(String FName, String LName, String Mail, String Phone, String Passport, LocalDate BirthDay) {
        try {
            Connection connection = connectDB.connectSQLServer();
            String exm = "Insert into Customers(CustomerID,CustomerFirstName,CustomerLastName,CustomerBirthDay,CustomerPhoneNumber,CustomerPassport,CustomerEmail,Active) values(?,?,?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(exm);

            String ID = "CUS" + Integer.toString(new Random().nextInt(999)) + Integer.toString(new Random().nextInt(99)) + "test";
            boolean Check = true;
            
            ps.setString(1, ID);
            ps.setString(2, FName);
            ps.setString(3, LName);
            ps.setDate(4, java.sql.Date.valueOf(BirthDay));
            ps.setString(5, Phone);
            ps.setString(6, Passport);
            ps.setString(7, Mail);
            ps.setBoolean(8, Check);
            ps.execute();
            ps.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException ex1) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex1);
        }  
    }
    //validate String
    public static boolean isString(String str) {
        String expression = "^[a-zA-Z\\s]+";
        return str.matches(expression);
    }
    
    //validate Number
    public static boolean validatePhoneNumber(String phoneNo) {
        //validate phone numbers of format "1234567890"
        if (phoneNo.matches("\\d{10}")) {
            return true;
        } //validating phone number with -, . or spaces
        else if (phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) {
            return true;
        } //validating phone number with extension length from 3 to 5
        else if (phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) {
            return true;
        } //validating phone number where area code is in braces ()
        else if (phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) {
            return true;
        } //return false if nothing matches the input
        else {
            return false;
        }

    }
    
    //validate Email
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}
