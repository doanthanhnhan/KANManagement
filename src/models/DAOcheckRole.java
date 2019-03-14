/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.connectDB;

/**
 *
 * @author Admin
 */
public class DAOcheckRole {
        public static Boolean checkRoleEditEmployee(String User) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        String exp = "select Employee_Edit from Role where EmployeeID = ?";
        PreparedStatement pt = connection.prepareStatement(exp);
        pt.setString(1, User);
        ResultSet rs;
        rs = pt.executeQuery();
        if (rs.next()) {
            pt.close();
            connection.close();
            rs.close();
            return true;
        }
        pt.close();
        connection.close();
        rs.close();
        return false;
    }
}
