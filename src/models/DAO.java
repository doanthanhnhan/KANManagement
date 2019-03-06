/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controllers.FXMLAddNewServiceTypeController;
import controllers.FXMLInfoEmployeeController;
import static controllers.FXMLLoginController.Emp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.connectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import utils.FormatName;

/**
 *
 * @author Admin
 */
public class DAO {

    public static InfoEmployee getListCheckLogin(String User) throws SQLException, ClassNotFoundException {
        Connection connection = connectDB.connectSQLServer();
        // Tạo đối tượng Statement.

        String sql = "select UserName,Users.Active,PassWord,IdNumber,Address,Birthday,PhoneNumber from Users,Employees where UserName = ? and Employees.EmployeeID = Users.EmployeeID";
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        PreparedStatement pt = connection.prepareStatement(sql);
        pt.setString(1, User);

        ResultSet rs = pt.executeQuery();
        if (!rs.next()) {
            pt.close();
            connection.close();
            return null;
        } else {
            InfoEmployee Emp = new InfoEmployee();
            Emp.setUserName(rs.getString("UserName"));
            Emp.setActive(rs.getString("Active"));
            Emp.setPhone_No(rs.getString("PhoneNumber"));
            Emp.setBirthdate(rs.getString("Birthday"));
            Emp.setAddress(rs.getString("Address"));
            Emp.setId_number(rs.getString("IDNumber"));
            Emp.setPassWord(rs.getString("PassWord"));
            pt.close();
            connection.close();
            rs.close();
            return Emp;
        }
    }

    private static ObservableList<InfoEmployee> getListEmployee(ResultSet rs) throws SQLException, ClassNotFoundException {
        //Declare a observable List which comprises of Employee objects
        ObservableList<InfoEmployee> List = FXCollections.observableArrayList();
        while (rs.next()) {
            InfoEmployee Emp = new InfoEmployee();
            Emp.setEmployee_ID(rs.getString("EmployeeID"));
            Emp.setUserName(rs.getString("UserName"));
            Emp.setPassWord(rs.getString("PassWord"));
            Emp.setActive(rs.getString("Active"));
            Emp.setFirst_Name(rs.getString("EmployeeFirstName"));
            Emp.setMid_Name(rs.getString("EmployeeMidName"));
            Emp.setLast_Name(rs.getString("EmployeeLastName"));
            Emp.setWork_Dept(rs.getString("DepartmentID"));
            Emp.setPhone_No(rs.getString("PhoneNumber"));
            Emp.setHiredate(rs.getString("HireDate"));
            Emp.setJob(rs.getString("Job"));
            Emp.setEDLEVEL(rs.getInt("EducatedLevel"));
            Emp.setSerect_Question(rs.getString("Serect_Question"));
            Emp.setSerect_Answer(rs.getString("Serect_Answer"));
            Emp.setBirthdate(rs.getString("Birthday"));
            Emp.setSalary(rs.getString("Salary"));
            Emp.setBonus(rs.getString("Bonus"));
            Emp.setComm(rs.getString("Comm"));
            Emp.setGmail(rs.getString("Email"));
            Emp.setRole(rs.getString("Position"));
            Emp.setAddress(rs.getString("Address"));
            Emp.setId_number(rs.getString("IDNumber"));
            Emp.setServiceImage(rs.getBlob("Image"));
            System.out.println(rs.getBlob("Image"));
            if (rs.getBlob("Image") != null) {
                try {
                    byte[] bytes = Emp.getServiceImage().getBytes(1l, (int) Emp.getServiceImage().length());
                    BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes));
                    Image image = SwingFXUtils.toFXImage(img, null);
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(50);
                    imageView.setFitWidth(50);
                    Emp.setImageView(imageView);
                } catch (IOException ex) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            int i = rs.getInt("Sex");
            if (i == 1) {
                Emp.setSex("Male");
            } else {
                Emp.setSex("Female");
            }
            List.add(Emp);
        }
        return List;
    }

    public static ObservableList<InfoEmployee> getAllEmployee() throws SQLException, ClassNotFoundException {
        Connection connection = connectDB.connectSQLServer();
        // Tạo đối tượng Statement.
        Statement statement = connection.createStatement();

        String sql = "select Users.*,Employees.*,[Role].* from Users, Employees,[Role] where Users.EmployeeID = Employees.EmployeeID and Employees.RoleID=[Role].RoleID";

        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        ResultSet rs = statement.executeQuery(sql);
        ObservableList<InfoEmployee> list_Employee = getListEmployee(rs);
        connection.close();
        return list_Employee;
    }

    public static void AddNewEmployee(String Id, String FName, String MName, String LName, String Id_Role, String Gmail, String Sex) throws MalformedURLException {
        try {
            Connection connection = connectDB.connectSQLServer();
            String exm = "Insert into Employees(EmployeeID,EmployeeFirstName,EmployeeMidName,EmployeeLastName,Sex,Email,RoleID,Image) values(?,?,?,?,?,?,?,?)";
            PreparedStatement pt = connection.prepareStatement(exm);
            pt.setString(1, Id);
            pt.setString(2, FName);
            pt.setString(3, MName);
            pt.setString(4, LName);
            if ("Male".equals(Sex)) {
                pt.setInt(5, 1);
            } else {
                pt.setInt(5, 0);
            }
            pt.setString(6, Gmail);
            pt.setString(7, Id_Role);
            String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
            System.out.println(currentPath + "/src/images/Java.png");
            File file = new File(currentPath + "/src/images/Java.png");
            BufferedImage bImage;
            bImage = SwingFXUtils.fromFXImage(new Image(file.toURI().toString()), null);
            System.out.println("Buffer anh xong!");
            byte[] res;
            try (ByteArrayOutputStream s = new ByteArrayOutputStream()) {
                ImageIO.write(bImage, "png", s);
                res = s.toByteArray();
                Blob blob = new SerialBlob(res);
                pt.setBlob(8, blob);
            } catch (SQLException ex) {
                Logger.getLogger(FXMLAddNewServiceTypeController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FXMLInfoEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
            }

            pt.execute();
            pt.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException ex1) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }

    public static ObservableList<?> getAllRole() throws SQLException, ClassNotFoundException {
        Connection connection = connectDB.connectSQLServer();
        // Tạo đối tượng Statement.
        Statement statement = connection.createStatement();

        String sql = "select * from Role";

        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        ResultSet rs = statement.executeQuery(sql);
        ObservableList ListRole = FXCollections.observableArrayList();
        while (rs.next()) {
            ListRole.add(rs.getString("Position"));
        }
        connection.close();
        return ListRole;
    }

    public static String getIdRole(String Role) throws ClassNotFoundException, SQLException {
        String Id = null;
        Connection connection = connectDB.connectSQLServer();
        String exp = "select RoleID from [Role] where Position = ?";
        PreparedStatement pt = connection.prepareStatement(exp);
        pt.setString(1, Role);
        ResultSet rs;
        rs = pt.executeQuery();
        while (rs.next()) {
            Id = rs.getString("RoleID");
        }
        return Id;
    }

    public static void AddUser(String Emp_Id, String User, String Pass, String Date) throws SQLException, ClassNotFoundException {
        Connection connection = connectDB.connectSQLServer();
        String exp = "select Max (ID_User) Max from Users";
        PreparedStatement pt = connection.prepareStatement(exp);
        ResultSet rs;
        rs = pt.executeQuery();
        int Id = 0;
        while (rs.next()) {
            Id = rs.getInt("Max") + 1;
        }
        int active = 1;
        String ex = "Insert into Users(ID_User,EmployeeID,UserName,PassWord,Active,Check_Login,Check_Time) values(?,?,?,?,?,?,?)";
        PreparedStatement pts = connection.prepareStatement(ex);
        pts.setInt(1, Id);
        pts.setString(2, Emp_Id);
        pts.setString(3, User);
        pts.setString(4, Pass);
        pts.setInt(5, active);
        pts.setInt(6, 0);
        pts.setString(7, Date);
        pts.execute();
        pts.close();
        connection.close();
    }

    public static Integer checkFirstLogin() throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        String exp = "select count(*) Count from Users";
        PreparedStatement pt = connection.prepareStatement(exp);
        ResultSet rs;
        rs = pt.executeQuery();
        int Count = 0;
        while (rs.next()) {
            Count = rs.getInt("Count");
        }
        return Count;
    }

    public static Integer checkSetPass(String User) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        String exp = "select count(*) Count from UserLogs where UserName = ?";
        PreparedStatement pt = connection.prepareStatement(exp);
        pt.setString(1, User);
        ResultSet rs;
        rs = pt.executeQuery();
        int Count = 0;
        while (rs.next()) {
            Count = rs.getInt("Count");
        }
        return Count;
    }

    public static void SetPass(String User, String Pass, String Question, String Answer) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        String exp = "UPDATE Users SET PassWord = ?, Serect_Question = ? ,Serect_Answer = ? WHERE UserName = ?";
        PreparedStatement pt = connection.prepareStatement(exp);
        pt.setString(1, Pass);
        pt.setString(2, Question);
        pt.setString(3, Answer);
        pt.setString(4, User);
        pt.execute();
        pt.close();
        connection.close();
    }

    public static void setUserLogs(String User, String Content, String Logtime) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        String ex = "Insert Into UserLogs(UserName,LogContent,LogTime,Active) Values (?,?,?,?)";
        PreparedStatement pts = connection.prepareStatement(ex);
        pts.setString(1, User);
        pts.setString(2, Content);
        pts.setString(3, Logtime);
        pts.setInt(4, 1);
        pts.execute();
        pts.close();
        connection.close();

    }

    public static void forgetPass(String User, String Pass) throws SQLException, ClassNotFoundException {
        Connection connection = connectDB.connectSQLServer();
        String exp = "UPDATE Users SET PassWord = ? WHERE UserName = ?";
        PreparedStatement pt = connection.prepareStatement(exp);
        pt.setString(1, Pass);
        pt.setString(2, User);
        pt.execute();
        pt.close();
        connection.close();
    }

    public static void check_Login(String User, String Time) throws SQLException, ClassNotFoundException {
        Connection connection = connectDB.connectSQLServer();
        String exp = "select Check_Login from Users where UserName = ?";
        PreparedStatement pt = connection.prepareStatement(exp);
        pt.setString(1, User);
        ResultSet rs;
        rs = pt.executeQuery();
        int Count = 0;
        while (rs.next()) {
            Count = rs.getInt("Check_Login");
        }
        Count++;

        pt.close();
        String ex = "UPDATE Users SET Check_Login = ?,Check_Time = ? WHERE UserName = ?";
        PreparedStatement pts = connection.prepareStatement(ex);
        pts.setInt(1, Count);
        pts.setString(2, Time);
        pts.setString(3, User);
        pts.execute();
        pts.close();
        if (Count == 5) {
            String e = "UPDATE Users SET Active = ? WHERE UserName = ?";
            PreparedStatement p = connection.prepareStatement(e);
            p.setInt(1, 0);
            p.setString(2, User);
            p.execute();
            p.close();
            setUserLogs(User, "Account is Locked", Time);
        }
        connection.close();
    }

    public static Integer check_Active(String User) throws SQLException, ClassNotFoundException {
        Connection connection = connectDB.connectSQLServer();
        String exp = "select Active from Users where UserName = ?";
        PreparedStatement pt = connection.prepareStatement(exp);
        pt.setString(1, User);
        ResultSet rs;
        rs = pt.executeQuery();
        int Count = 0;
        while (rs.next()) {
            Count = rs.getInt("Active");
        }
        pt.close();
        connection.close();
        return Count;
    }

    public static String check_Time(String User) throws SQLException, ClassNotFoundException {
        Connection connection = connectDB.connectSQLServer();
        String exp = "select Check_Time from Users where UserName = ?";
        PreparedStatement pt = connection.prepareStatement(exp);
        pt.setString(1, User);
        ResultSet rs;
        rs = pt.executeQuery();
        String Count = null;
        while (rs.next()) {
            Count = rs.getString("Check_Time");
        }
        pt.close();
        connection.close();
        return Count;
    }

    public static void reset_CheckLogin(String User, String Logtime) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        String ex = "UPDATE Users SET Check_Login = ?,Check_Time = ? WHERE UserName = ?";
        PreparedStatement pts = connection.prepareStatement(ex);
        pts.setInt(1, 0);
        pts.setString(2, Logtime);
        pts.setString(3, User);
        pts.execute();
        pts.close();
        connection.close();

    }

    public static void UpdateInfoEmployee(String User, String Phone, String Birthday, String Address, String IdNumber, String Sex) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        String exp = "UPDATE Employees SET PhoneNumber = ?, Birthday = ? ,Address = ?,IDNumber = ?,Sex=? WHERE EmployeeID = ?";
        PreparedStatement pt = connection.prepareStatement(exp);
        pt.setString(1, Phone);
        pt.setString(2, Birthday);
        pt.setString(3, Address);
        pt.setString(4, IdNumber);
        if (Sex.equals("Male")) {
            pt.setInt(5, 1);
        } else {
            pt.setInt(5, 0);
        }
        pt.setString(6, User);
        pt.execute();
        pt.close();
        connection.close();
    }

    public static void UpdateAllInfoEmployee(String User, String Phone, String Birthday, String Address, String IdNumber, String FName, String MName, String LName, String Email,
            String DepartId, String Hiredate, String Job, String EducatedLevel, Double Salary, Double Bonus, Double Comm, String Role, String Sex, Blob Image) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        String exp = "UPDATE Employees SET EmployeeFirstName=?,EmployeeMidName=?,EmployeeLastName=?,DepartmentId=?,"
                + " PhoneNumber = ?, Birthday = ? ,Address = ?,IDNumber = ?,HireDate=?,Job=?,EducatedLevel=?,Salary=?,"
                + "Bonus=?,Comm=?,RoleID=?,Sex=?,Image=? WHERE EmployeeID = ?";
        PreparedStatement pt = connection.prepareStatement(exp);
        pt.setString(1, FName);
        pt.setString(2, MName);
        pt.setString(3, LName);
        pt.setString(4, DepartId);
        pt.setString(5, Phone);
        pt.setString(6, Birthday);
        pt.setString(7, Address);
        pt.setString(8, IdNumber);
        pt.setString(9, Hiredate);
        pt.setString(10, Job);
        pt.setString(11, EducatedLevel);
        pt.setDouble(12, Salary);
        pt.setDouble(13, Bonus);
        pt.setDouble(14, Comm);
        pt.setString(15, Role);
        if (Sex.equals("Male")) {
            pt.setInt(16, 1);
        } else {
            pt.setInt(16, 0);
        }
        pt.setBlob(17, Image);
        pt.setString(18, User);
        pt.execute();
        pt.close();
        connection.close();
    }
}
