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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.connectDB;

/**
 *
 * @author Admin
 */
public class DAODepartMentReActive {
// get all name department
    public static ObservableList<String> get_All_DepartmentName() throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        ObservableList<String> list_name = FXCollections.observableArrayList();
        String sql = "select DepartmentID from Departments WHERE active = 1";
        PreparedStatement pt = connection.prepareStatement(sql);

        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        ResultSet rs = pt.executeQuery();
        while (rs.next()) {
            list_name.add(rs.getString("DepartmentID"));
        }
        return list_name;
    }
// Add new Department

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
