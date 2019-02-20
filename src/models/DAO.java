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
import static java.sql.Types.NULL;
import java.time.LocalDate;
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
    public static void newEmployee(String User,String Pass,String Id,String Question,String Answer,String FName,String MName,String LName,LocalDate birthday,String Sex) {
        try {
            Connection connection = connectDB.connectSQLServer();
        // Tạo đối tượng Statement.
            Statement statement = connection.createStatement();
            String exp="select Max(ID_User) Max from Users";
            ResultSet rs = statement.executeQuery(exp);
            int Id_User = 0;
            while(rs.next()){
                Id_User = rs.getInt("Max")+1;
            }
            String exm = "Insert into Employee(Employee_ID,FIRST_NAME,MID_NAME,LAST_NAME,SEX,BIRTHDATE) values(?,?,?,?,?,?)";
            PreparedStatement pt = connection.prepareStatement(exm);
            pt.setString(1, Id);
            pt.setString(2, FName);
            pt.setString(3, MName);
            pt.setString(4, LName);
            if("Male".equals(Sex)){
                pt.setInt( 5 , 1 );
            }
            else{
                pt.setInt(5,0);
            }
            pt.setString(6, String.valueOf(birthday));
            pt.execute();
            pt.close();
            String ex = "Insert into Users values(?,?,?,?,?,?,?)";
            PreparedStatement pst = connection.prepareStatement(ex);
            pst.setInt(1, Id_User);
            pst.setString(2, Id);
            pst.setString(3, User);
            pst.setString(4, Pass);
            pst.setInt(5, 1);
            pst.setString(6, Question);
            pst.setString(7, Answer);
            pst.execute();
            pst.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException ex1) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }
}
