/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controllers.FXMLAddNewServiceTypeController;
import controllers.FXMLInfoEmployeeController;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.sql.Array;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.connectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
// Lấy ra thông tin của tài khoản đăng nhập

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
            Emp.setActive(rs.getBoolean("Active"));
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
// Kiểm tra nếu là lần đăng nhập đầu tiên thì bắt đặt lại password mới

    public static Boolean checkSetPass(String User) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        String exp = "select UserName from UserLogs where UserName = ?";
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
//    Xử lý qua ngày mới thì reset lại số lần gõ sai pass

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
// reset lại số lần gõ sai pass

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
// Lấy ra thông tin câu hỏi và câu trả lời bí mật của tài khoản quên mật khẩu

    public static InfoEmployee getInfoForgetPassEmployee(String User) throws SQLException, ClassNotFoundException {
        InfoEmployee Emp = new InfoEmployee();
        Connection connection = connectDB.connectSQLServer();
        // Tạo đối tượng Statement.
        String sql = "select Serect_Question,Serect_Answer,Active from Users where UserName = ?";
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        PreparedStatement pt = connection.prepareStatement(sql);
        pt.setString(1, User);
        ResultSet rs = pt.executeQuery();
        while (rs.next()) {
            Emp.setActive(rs.getBoolean("Active"));
            Emp.setSerect_Question(rs.getString("Serect_Question"));
            Emp.setSerect_Answer(rs.getString("Serect_Answer"));
        }
        pt.close();
        rs.close();
        connection.close();
        return Emp;
    }
// Kiểm tra ID đã tồn tại trong bảng hay chưa

    public static Boolean check_Id(String ID) throws SQLException, ClassNotFoundException {
        Connection connection = connectDB.connectSQLServer();
        // Tạo đối tượng Statement.
        String sql = "select EmployeeID from Employees where EmployeeID = ?";
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        PreparedStatement pt = connection.prepareStatement(sql);
        pt.setString(1, ID);
        ResultSet rs = pt.executeQuery();
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
// Kiểm tra Email đã tồn tại trong bảng hay chưa

    public static Boolean check_Email(String email) throws SQLException, ClassNotFoundException {
        Connection connection = connectDB.connectSQLServer();
        // Tạo đối tượng Statement.
        String sql = "select Email from Employees where Email = ?";
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        PreparedStatement pt = connection.prepareStatement(sql);
        pt.setString(1, email);
        ResultSet rs = pt.executeQuery();
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
// Lấy ra Position của user

    public static String get_Role(String user) throws SQLException, ClassNotFoundException {
        Connection connection = connectDB.connectSQLServer();
        // Tạo đối tượng Statement.
        String sql = "select Position from Employees,Role where EmployeeID = ? and Employees.RoleID = Role.RoleID";
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        PreparedStatement pt = connection.prepareStatement(sql);
        pt.setString(1, user);
        String Position = null;
        ResultSet rs = pt.executeQuery();
        while (rs.next()) {
            Position = rs.getString("Position");
        }
        rs.close();
        pt.close();
        connection.close();
        return Position;
    }
// Lấy ra Info của User

    public static ObservableList<InfoEmployee> getAllInfoEmployee() throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        ObservableList<InfoEmployee> list = FXCollections.observableArrayList();
        String sql = "select * from Employees";
        PreparedStatement pt = connection.prepareStatement(sql);
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        ResultSet rs = pt.executeQuery();
        while (rs.next()) {
            InfoEmployee Emp = new InfoEmployee();
            Emp.setEmployee_ID(rs.getString("EmployeeID"));
            Emp.setActive(rs.getBoolean("Active"));
            Emp.setFirst_Name(rs.getString("EmployeeFirstName"));
            Emp.setMid_Name(rs.getString("EmployeeMidName"));
            Emp.setLast_Name(rs.getString("EmployeeLastName"));
            Emp.setWork_Dept(rs.getString("DepartmentID"));
            Emp.setPhone_No(rs.getString("PhoneNumber"));
            Emp.setHiredate(rs.getString("HireDate"));
            Emp.setJob(rs.getString("Job"));
            Emp.setEDLEVEL(rs.getInt("EducatedLevel"));
            Emp.setBirthdate(rs.getString("Birthday"));
            Emp.setSalary(rs.getString("Salary"));
            Emp.setBonus(rs.getString("Bonus"));
            Emp.setComm(rs.getString("Comm"));
            Emp.setGmail(rs.getString("Email"));
            Emp.setAddress(rs.getString("Address"));
            Emp.setId_number(rs.getString("IDNumber"));
            Emp.setServiceImage(rs.getBlob("Image"));
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
            Emp.setSex(rs.getBoolean("Sex"));
            list.add(Emp);
        }
        return list;
    }

    public static InfoEmployee getInfoEmployee(String user) throws SQLException, ClassNotFoundException {
        Connection connection = connectDB.connectSQLServer();
        String sql = "select Users.*,Employees.*,[Role].* from Users, Employees,[Role] where Users.EmployeeID =? and Users.EmployeeID = Employees.EmployeeID and Employees.RoleID=[Role].RoleID";
        PreparedStatement pt = connection.prepareStatement(sql);
        pt.setString(1, user);
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        ResultSet rs = pt.executeQuery();
        InfoEmployee Emp = new InfoEmployee();
        while (rs.next()) {
            Emp.setEmployee_ID(rs.getString("EmployeeID"));
            Emp.setUserName(rs.getString("UserName"));
            Emp.setPassWord(rs.getString("PassWord"));
            Emp.setActive(rs.getBoolean("Active"));
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
            Emp.setSex(rs.getBoolean("Sex"));
        }
        rs.close();
        pt.close();
        connection.close();
        return Emp;
    }
// Xử lý insert dữ liệu Employees mới đc tạo ra

    public static void AddNewEmployee(InfoEmployee Emp, String ID_ROLE) throws MalformedURLException {
        try {
            Connection connection = connectDB.connectSQLServer();
            String exm = "Insert into Employees(EmployeeID,EmployeeFirstName,EmployeeMidName,EmployeeLastName,Sex,Email,RoleID,Image) values(?,?,?,?,?,?,?,?)";
            PreparedStatement pt = connection.prepareStatement(exm);
            pt.setString(1, Emp.getId_number());
            pt.setString(2, Emp.getFirst_Name());
            pt.setString(3, Emp.getMid_Name());
            pt.setString(4, Emp.getLast_Name());
            pt.setBoolean(5, Emp.getSex());
            pt.setString(6, Emp.getGmail());
            pt.setString(7, ID_ROLE);
            String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
            File file = new File(currentPath + "/src/images/imagequestion.png");
            BufferedImage bImage;
            bImage = SwingFXUtils.fromFXImage(new Image(file.toURI().toString()), null);
            byte[] res;
            try (ByteArrayOutputStream s = new ByteArrayOutputStream()) {
                ImageIO.write(bImage, "png", s);
                res = s.toByteArray();
                Blob blob = new SerialBlob(res);
                pt.setBlob(8, blob);

            } catch (SQLException ex) {
                Logger.getLogger(FXMLAddNewServiceTypeController.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (IOException ex) {
                Logger.getLogger(FXMLInfoEmployeeController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            pt.execute();
            pt.close();
            connection.close();

        } catch (SQLException | ClassNotFoundException ex1) {
            Logger.getLogger(DAO.class
                    .getName()).log(Level.SEVERE, null, ex1);
        }
    }

    public static ObservableList<String> getAllIdUser() throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        // Tạo đối tượng Statement.
        Statement statement = connection.createStatement();
        String sql = "select EmployeeID from Users";

        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        ResultSet rs = statement.executeQuery(sql);
        ObservableList listIdUser = FXCollections.observableArrayList();
        while (rs.next()) {
            listIdUser.add(rs.getString("EmployeeID"));
        }
        connection.close();
        return listIdUser;
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
// xử lý lấy ra ID của Role khi có position

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
// Xử lý update lại pass khi người dùng forget pass

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
// Xử lý gõ pass sai thì tăng biến check lên 1 nếu check = 5 block account

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
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            String logtime;
            logtime = dateFormat.format(cal.getTime());
            String e = "UPDATE Users SET Active = ? WHERE UserName = ?";
            PreparedStatement p = connection.prepareStatement(e);
            p.setInt(1, 0);
            p.setString(2, User);
            p.execute();
            p.close();
            setUserLogs(User, "Account is Locked", logtime);
        }
        connection.close();
    }

    public static void UpdateInfoEmployee(String User, String Phone, String Birthday, String Address, String IdNumber, Boolean Sex) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        String exp = "UPDATE Employees SET PhoneNumber = ?, Birthday = ? ,Address = ?,IDNumber = ?,Sex=? WHERE EmployeeID = ?";
        PreparedStatement pt = connection.prepareStatement(exp);
        pt.setString(1, Phone);
        pt.setString(2, Birthday);
        pt.setString(3, Address);
        pt.setString(4, IdNumber);
        pt.setBoolean(5, Sex);
        pt.setString(6, User);
        pt.execute();
        pt.close();
        connection.close();
    }

    public static void UpdateAllInfoEmployee(String User, String Phone, String Birthday, String Address, String IdNumber, String FName, String MName, String LName, String Email,
            String DepartId, String Hiredate, String Job, String EducatedLevel, Double Salary, Double Bonus, Double Comm, String Role, Boolean Sex, Blob Image) throws ClassNotFoundException, SQLException {
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
        pt.setBoolean(16, Sex);
        pt.setBlob(17, Image);
        pt.setString(18, User);
        pt.execute();
        pt.close();
        connection.close();
    }
}
