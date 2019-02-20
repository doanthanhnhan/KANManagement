/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.connectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Admin
 */
public class DAO {
     private static ObservableList<InfoEmployee> getListEmployee(ResultSet rs) throws SQLException, ClassNotFoundException {
        //Declare a observable List which comprises of Employee objects
        ObservableList<InfoEmployee> List = FXCollections.observableArrayList();
        while (rs.next()) {
            InfoEmployee Emp = new InfoEmployee();
            Emp.setEmployee_ID(rs.getString("Employee_ID"));
            Emp.setUserName(rs.getString("UserName"));
            Emp.setPassWord(rs.getString("PassWord"));
            Emp.setActive(rs.getString("Active"));
            Emp.setFirst_Name(rs.getString("FIRST_NAME"));
            Emp.setMid_Name(rs.getString("MID_NAME"));
            Emp.setLast_Name(rs.getString("LAST_NAME"));
            Emp.setWork_Dept(rs.getString("WORKDEPT"));
            Emp.setPhone_No(rs.getString("PHONENO"));
            Emp.setHiredate(rs.getString("HIREDATE"));
            Emp.setJob(rs.getString("JOB"));
            Emp.setEDLEVEL(rs.getInt("EDLEVEL"));
            Emp.setBirthdate(rs.getString("BIRTHDATE"));
            Emp.setSalary(rs.getString("SALARY"));
            Emp.setBonus(rs.getString("BONUS"));
            Emp.setComm(rs.getString("COMM"));
            int i = rs.getInt("SEX");
            if (i == 1) {
               Emp.setSex("Nam");
            } else {
                Emp.setSex("Nữ");
            }
            List.add(Emp);
        }
        return List;
    }
    public static ObservableList<InfoEmployee> getAllEmployee() throws SQLException, ClassNotFoundException {
        Connection connection = connectDB.connectSQLServer();
        // Tạo đối tượng Statement.
        Statement statement = connection.createStatement();

        String sql = "select Users.*,Employee.* from Users, Employee where Users.Employee_ID = Employee.Employee_ID";

        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        ResultSet rs = statement.executeQuery(sql);
        ObservableList<InfoEmployee> list_Employee = getListEmployee(rs);
        connection.close();
        return list_Employee;
    }
    public void addStudent(Students student) {
        try {
            String ex = "Insert into Student values(?,?,?,?,?)";
            Connection connection = ConnectionUtils.getMyConnection();
            PreparedStatement pst = connection.prepareStatement(ex);
            pst.setString(1, student.getStudentCode());
            pst.setString(2, student.getStudentName());
            String tam = student.getStudentGender();
            if (tam.equals("Nam")) {
                pst.setInt(3, 1);
            } else {
                pst.setInt(3, 0);
            }
            pst.setInt(4, student.getStudentAge());
            pst.setString(5, student.getStudentAddress());
            pst.execute();
            pst.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException ex1) {
            Logger.getLogger(SMSDAOImpl.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }
}
