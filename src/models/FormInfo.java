/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
    public void AddNewBooking(String Note, int Number, LocalDate Date, boolean Check, String Flight,String Cus, String Room) {
        try {
            Connection connection = connectDB.connectSQLServer();
            String exm = "Insert into BookingInfo(BookingID,Note,NumberGuest,DateBook,BookDrive,Flight,CustomerID,RoomID,UserName) values(?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(exm);

            String ID = "BOO" + Integer.toString(new Random().nextInt(999)) + Integer.toString(new Random().nextInt(99)) + "test";
            ps.setString(1, ID);
            ps.setString(2, Note);
            ps.setInt(3, Number);
            ps.setDate(4, java.sql.Date.valueOf(Date));
            ps.setBoolean(5, Check);
            ps.setString(6, Flight);
            ps.setString(7, Cus);
            ps.setString(8, Room);
            ps.setString(9, "admin");
            ps.execute();
            ps.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException ex1) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }
    public void EditBooking(String Note, int Number, LocalDate Date, boolean Check, String Flight, String ID) {
        try {
            Connection connection = connectDB.connectSQLServer();
            String exm = "UPDATE BookingInfo SET Note = ?,NumberGuest = ?,DateBook = ?,BookDrive = ?,Flight = ? WHERE BookingID = ?;";
            PreparedStatement ps = connection.prepareStatement(exm);
            BookingInfo BookingInfo = new BookingInfo();
            ps.setString(6, ID);
            ps.setString(1, Note);;
            ps.setInt(2, Number);
            ps.setTimestamp(3, Timestamp.valueOf(BookingInfo.getDate()));
            ps.setBoolean(4, Check);
            ps.setString(5, Flight);
            ps.execute();
            ps.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException ex1) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }
    
    public static String CusID;

    public static String getCusID() {
        return CusID;
    }

    public static void setCusID(String CusID) {
        FormInfo.CusID = CusID;
    }

    @Override
    public void AddNewCustomer(String FName, String LName, String Mail, String Phone, String Passport, LocalDate BirthDay, String Company, boolean Sex, String userName,float Discount) {
        try {
            try (Connection connection = connectDB.connectSQLServer()) {
                String exm = "Insert into Customers(CustomerID,CustomerFirstName,CustomerLastName,CustomerBirthDay,CustomerPhoneNumber,CustomerPassport,CustomerEmail,Active,Company,Sex,Username,Discount) values(?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement ps = connection.prepareStatement(exm);
                Customer Customer = new Customer();
                String ID = "CUS" + Integer.toString(new Random().nextInt(999)) + Integer.toString(new Random().nextInt(99)) + "test";
                boolean Check = true;
                setCusID(ID);
                ps.setString(1, ID);
                ps.setString(2, FName);
                ps.setString(3, LName);
                ps.setTimestamp(4, Timestamp.valueOf(Customer.getDate()));
                ps.setString(5, Phone);
                ps.setString(6, Passport);
                ps.setString(7, Mail);
                ps.setBoolean(8, Check);
                ps.setString(9, Company);
                ps.setBoolean(10, Sex);
                ps.setString(11, userName);
                ps.setFloat(12, Discount);
                System.out.println("Discount = " + Discount);
                ps.execute();
                ps.close();
            }
        } catch (SQLException | ClassNotFoundException ex1) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }
    
    public void AddNewCheckIn() {
        try {
            try (Connection connection = connectDB.connectSQLServer()) {
                String exm = "Insert into CheckInOrders(CheckInID,BookingID,CustomerID,RoomID,UserName,CheckInType,NumberOfCustomer,CheckInDate,LeaveDate,CustomerPackage,Active) values(?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement ps = connection.prepareStatement(exm);
                CheckIn ck = new CheckIn();
                String ID = "CKI" + Integer.toString(new Random().nextInt(999)) + Integer.toString(new Random().nextInt(99)) + "test";
                boolean Check = true;
                
                ps.setString(1, ID);
                ps.setString(2, ck.getBookID());
                ps.setString(3, ck.getCusID());
                ps.setString(4, ck.getRoomID());
                ps.setString(5, "admin");
                ps.setString(6, ck.getCheckType());
                ps.setInt(7, ck.getNumberOfCustomer());
                ps.setTimestamp(8, Timestamp.valueOf(ck.getDateIn()));
                ps.setTimestamp(9, Timestamp.valueOf(ck.getDateOut()));
                ps.setString(10, ck.getCusPack());
                ps.setBoolean(11, Check);
               
                ps.execute();
                ps.close();
            }
        } catch (SQLException | ClassNotFoundException ex1) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }

    //validate String
    public static boolean isString(String str) {
        String expression = "^\\pL+[\\pL\\pZ]{1,25}$";
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

    //validate Number
    public static boolean validateNumber(String phoneNo) {
        //validate phone numbers of format "1234567890"
        if (phoneNo.matches("\\d")) {
            return true;
        } else {
            return false;
        }

    }

    //validate Date
    public static boolean isDateValid(String date) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
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
