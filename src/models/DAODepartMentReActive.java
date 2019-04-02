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
public class DAODepartMentReActive {

    public static void Add_New_Department(String Id, String Name, String Username) throws MalformedURLException, SQLException, ClassNotFoundException {
        Connection connection = connectDB.connectSQLServer();
        String exm = "Insert into Departments(DepartmentID,DepartmentName,Active,Username) values(?,?,?,?)";
        PreparedStatement pt = connection.prepareStatement(exm);
        pt.setString(1, Id);
        pt.setString(2, Name);
        pt.setBoolean(3, true);
        pt.setString(4, Username);
        pt.execute();
        pt.close();
        connection.close();
    }
}
