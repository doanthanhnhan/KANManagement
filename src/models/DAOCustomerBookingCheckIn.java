/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.net.MalformedURLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import utils.connectDB;

/**
 *
 * @author Admin
 */
public class DAOCustomerBookingCheckIn {
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
}
