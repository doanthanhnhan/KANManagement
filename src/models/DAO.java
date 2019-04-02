/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controllers.FXMLAddNewServiceTypeController;
import controllers.FXMLInfoEmployeeController;
import controllers.FXMLLoginController;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;

/**
 *
 * @author Admin
 */
public class DAO {
// reset lại số lần gõ sai theo dia chi Mac

    public static void reset_CheckMac(String MACAddress) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        String ex = "UPDATE CheckBlockMacAddress set Times = ? where MACAddress=?";
        PreparedStatement pts = connection.prepareStatement(ex);
        pts.setInt(1, 0);
        pts.setString(2, MACAddress);
        pts.execute();
        pts.close();
        connection.close();
    }
//    check sự tồn tại của địa chỉ Mac trong Database

    public static Integer check_Active_MacAddress(String MACAddress) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        String sql = "select Active from CheckBlockMacAddress where MACAddress=?";
        PreparedStatement pp = connection.prepareStatement(sql);
        pp.setString(1, MACAddress);
        ResultSet rsp;
        rsp = pp.executeQuery();
        int active = 0;
        while (rsp.next()) {
            active = rsp.getInt("Active");
        }
        pp.close();
        rsp.close();
        connection.close();
        return active;
    }
//    check Invalid Mac

    public static boolean check_invalid(String MACAddress) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        String sql = "select * from CheckBlockMacAddress where MACAddress=?";
        PreparedStatement pp = connection.prepareStatement(sql);
        pp.setString(1, MACAddress);
        ResultSet rsp;
        rsp = pp.executeQuery();
        if (rsp.next()) {
            rsp.close();
            pp.close();
            connection.close();
            return true;
        }
        rsp.close();
        pp.close();
        connection.close();
        return false;
    }
//    checkMacAddress

    public static void check_MacAddress(String Time, String MACAddress) throws SQLException, ClassNotFoundException {
        if (!check_invalid(MACAddress)) {
            Connection connection = connectDB.connectSQLServer();
            String sql = "Insert Into CheckBlockMacAddress(MACAddress,Times) values(?,?)";
            PreparedStatement pp = connection.prepareStatement(sql);
            pp.setString(1, MACAddress);
            pp.setInt(2, 0);
            pp.execute();
            connection.close();
            pp.close();
        }
        Connection connection = connectDB.connectSQLServer();
        String exp = "select Times from CheckBlockMacAddress where MACAddress = ?";
        PreparedStatement pt = connection.prepareStatement(exp);
        pt.setString(1, MACAddress);
        ResultSet rs;
        rs = pt.executeQuery();
        int Count = 0;
        while (rs.next()) {
            Count = rs.getInt("Times");
        }
        Count++;
        rs.close();
        pt.close();
        String ex = "UPDATE CheckBlockMacAddress SET Times = ? WHERE MACAddress = ?";
        PreparedStatement pts = connection.prepareStatement(ex);
        pts.setInt(1, Count);
        pts.setString(2, MACAddress);
        pts.execute();
        pts.close();
        if (Count == 7) {
            String e = "UPDATE CheckBlockMacAddress SET Active = ? WHERE MACAddress = ?";
            PreparedStatement p = connection.prepareStatement(e);
            p.setInt(1, 0);
            p.setString(2, MACAddress);
            p.execute();
            p.close();
            DAO.setUserLogs_With_MAC(MACAddress, "MacAddress is Locked",
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), MACAddress);
            p.close();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Your device has been blocked from using this program !!!");
            alert.setContentText("Please contact the administrator for reuse !!!");
            alert.showAndWait();
            Platform.exit();
        }
        connection.close();
        pts.close();
    }

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
        String sql = "select Secret_Question,Secret_Answer,Active from Users where UserName = ?";
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        PreparedStatement pt = connection.prepareStatement(sql);
        pt.setString(1, User);
        ResultSet rs = pt.executeQuery();
        if (!rs.next()) {
            pt.close();
            rs.close();
            connection.close();
            return null;
        }
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

    public static ObservableList<InfoEmployee> getAllInfoEmployee() throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        ObservableList<InfoEmployee> list = FXCollections.observableArrayList();
        String sql = "select * from Employees where active = 1";
        PreparedStatement pt = connection.prepareStatement(sql);
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        ResultSet rs = pt.executeQuery();
        while (rs.next()) {
            InfoEmployee Emp = new InfoEmployee();
            Emp.setEmployee_ID(rs.getString("EmployeeID"));
            Emp.setActive(rs.getBoolean("Active"));
            Emp.setFirst_Name(rs.getString("EmployeeFirstName"));
            if (rs.getString("EmployeeMidName") == null) {
                Emp.setMid_Name("");
            } else {
                Emp.setMid_Name(rs.getString("EmployeeMidName"));
            }
            Emp.setLast_Name(rs.getString("EmployeeLastName"));
            if (rs.getString("DepartmentID") == null) {
                Emp.setWork_Dept("");
            } else {
                Emp.setWork_Dept(rs.getString("DepartmentID"));
            }
            if (rs.getString("PhoneNumber") == null) {
                Emp.setPhone_No("");
            } else {
                Emp.setPhone_No(rs.getString("PhoneNumber"));
            }
            if (rs.getString("Job") == null) {
                Emp.setJob("");
            } else {
                Emp.setJob(rs.getString("Job"));
            }
            Emp.setEDLEVEL(rs.getInt("EducatedLevel"));
            Emp.setHiredate(rs.getString("HireDate"));

            Emp.setBirthdate(rs.getString("Birthday"));
            if (rs.getString("Salary") == null) {
                Emp.setSalary("");
            } else {
                Emp.setSalary(rs.getString("Salary"));
            }
            if (rs.getString("Bonus") == null) {
                Emp.setBonus("");
            } else {
                Emp.setBonus(rs.getString("Bonus"));
            }
            if (rs.getString("Address") == null) {
                Emp.setAddress("");
            } else {
                Emp.setAddress(rs.getString("Address"));
            }
            if (rs.getString("Comm") == null) {
                Emp.setComm("");
            } else {
                Emp.setComm(rs.getString("Comm"));
            }
            Emp.setGmail(rs.getString("Email"));
            if (rs.getString("IDNumber") == null) {
                Emp.setId_number("");
            } else {
                Emp.setId_number(rs.getString("IDNumber"));
            }
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

// Lấy ra Info của User
    public static ObservableList<InfoEmployee> getAllInfoEmployee(String user) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        ObservableList<InfoEmployee> list = FXCollections.observableArrayList();
        String sql = "select * from Employees where active = 1 and EmployeeID != ?";
        PreparedStatement pt = connection.prepareStatement(sql);
        pt.setString(1, user);
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        ResultSet rs = pt.executeQuery();
        while (rs.next()) {
            InfoEmployee Emp = new InfoEmployee();
            Emp.setEmployee_ID(rs.getString("EmployeeID"));
            Emp.setActive(rs.getBoolean("Active"));
            Emp.setFirst_Name(rs.getString("EmployeeFirstName"));
            if (rs.getString("EmployeeMidName") == null) {
                Emp.setMid_Name("");
            } else {
                Emp.setMid_Name(rs.getString("EmployeeMidName"));
            }
            Emp.setLast_Name(rs.getString("EmployeeLastName"));
            if (rs.getString("DepartmentID") == null) {
                Emp.setWork_Dept("");
            } else {
                Emp.setWork_Dept(rs.getString("DepartmentID"));
            }
            if (rs.getString("PhoneNumber") == null) {
                Emp.setPhone_No("");
            } else {
                Emp.setPhone_No(rs.getString("PhoneNumber"));
            }
            if (rs.getString("Job") == null) {
                Emp.setJob("");
            } else {
                Emp.setJob(rs.getString("Job"));
            }
            Emp.setEDLEVEL(rs.getInt("EducatedLevel"));
            Emp.setHiredate(rs.getString("HireDate"));

            Emp.setBirthdate(rs.getString("Birthday"));
            if (rs.getString("Salary") == null) {
                Emp.setSalary("");
            } else {
                Emp.setSalary(rs.getString("Salary"));
            }
            if (rs.getString("Bonus") == null) {
                Emp.setBonus("");
            } else {
                Emp.setBonus(rs.getString("Bonus"));
            }
            if (rs.getString("Address") == null) {
                Emp.setAddress("");
            } else {
                Emp.setAddress(rs.getString("Address"));
            }
            if (rs.getString("Comm") == null) {
                Emp.setComm("");
            } else {
                Emp.setComm(rs.getString("Comm"));
            }
            Emp.setGmail(rs.getString("Email"));
            if (rs.getString("IDNumber") == null) {
                Emp.setId_number("");
            } else {
                Emp.setId_number(rs.getString("IDNumber"));
            }
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
        String sql = "select Users.*,Employees.* from Users, Employees where Users.EmployeeID =? and Users.EmployeeID = Employees.EmployeeID";
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
            Emp.setSerect_Question(rs.getString("Secret_Question"));
            Emp.setSerect_Answer(rs.getString("Secret_Answer"));
            Emp.setBirthdate(rs.getString("Birthday"));
            Emp.setSalary(rs.getString("Salary"));
            Emp.setBonus(rs.getString("Bonus"));
            Emp.setComm(rs.getString("Comm"));
            Emp.setGmail(rs.getString("Email"));
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

    public static void AddNewEmployee(InfoEmployee Emp) throws MalformedURLException, SQLException, ClassNotFoundException {
        try {
            Connection connection = connectDB.connectSQLServer();
            String exm = "Insert into Employees(EmployeeID,EmployeeFirstName,EmployeeMidName,EmployeeLastName,Sex,Email,Image,DepartmentID) values(?,?,?,?,?,?,?,?)";
            PreparedStatement pt = connection.prepareStatement(exm);
            pt.setString(1, Emp.getId_number());
            pt.setString(2, Emp.getFirst_Name());
            pt.setString(3, Emp.getMid_Name());
            pt.setString(4, Emp.getLast_Name());
            pt.setBoolean(5, Emp.getSex());
            pt.setString(6, Emp.getGmail());

            String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
            File file = new File(currentPath + "/src/images/imagequestion.png");
            BufferedImage bImage;
            bImage = SwingFXUtils.fromFXImage(new Image(file.toURI().toString()), null);
            byte[] res;
            try (ByteArrayOutputStream s = new ByteArrayOutputStream()) {
                ImageIO.write(bImage, "png", s);
                res = s.toByteArray();
                Blob blob = new SerialBlob(res);
                pt.setBlob(7, blob);

            } catch (SQLException ex) {
                Logger.getLogger(FXMLAddNewServiceTypeController.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (IOException ex) {
                Logger.getLogger(FXMLInfoEmployeeController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            pt.setString(8, Emp.getWork_Dept());
            pt.execute();
            pt.close();
            connection.close();

        } catch (SQLException | ClassNotFoundException ex1) {
            Logger.getLogger(DAO.class
                    .getName()).log(Level.SEVERE, null, ex1);
        }
    }
// update active = 0

    public static void delete_Employee(String User) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        String sql = "UPDATE Users SET Active = ? WHERE EmployeeID = ?";
        PreparedStatement pts = connection.prepareStatement(sql);
        pts.setInt(1, 0);
        pts.setString(2, User);
        pts.execute();
        pts.close();

        String exp = "UPDATE Employees SET Active = ? WHERE EmployeeID = ?";
        PreparedStatement pt = connection.prepareStatement(exp);
        pt.setInt(1, 0);
        pt.setString(2, User);
        pt.execute();
        pt.close();
        connection.close();
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
        String exp = "UPDATE Users SET PassWord = ?, Secret_Question = ? ,Secret_Answer = ? WHERE UserName = ?";
        PreparedStatement pt = connection.prepareStatement(exp);
        pt.setString(1, Pass);
        pt.setString(2, Question);
        pt.setString(3, Answer);
        pt.setString(4, User);
        pt.execute();
        pt.close();
        connection.close();
    }

    //Insert to UserLogs
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

    public static void setUserLogs_With_MAC(String User, String Content, String Logtime, String macAddress) {
        try {
            Connection connection = connectDB.connectSQLServer();
            String ex = "Insert Into UserLogs(UserName,LogContent,LogTime,Active,MACAddress) Values (?,?,?,?,?)";
            PreparedStatement pts = connection.prepareStatement(ex);
            pts.setString(1, User);
            pts.setString(2, Content);
            pts.setString(3, Logtime);
            pts.setInt(4, 1);
            pts.setString(5, macAddress);
            pts.execute();
            pts.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            String DepartId, String Hiredate, String Job, String EducatedLevel, Double Salary, Double Bonus, Double Comm, Boolean Sex, Blob Image) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        String exp = "UPDATE Employees SET EmployeeFirstName=?,EmployeeMidName=?,EmployeeLastName=?,DepartmentId=?,"
                + " PhoneNumber = ?, Birthday = ? ,Address = ?,IDNumber = ?,HireDate=?,Job=?,EducatedLevel=?,Salary=?,"
                + "Bonus=?,Comm=?,Sex=?,Image=?,Email=? WHERE EmployeeID = ?";
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
        pt.setBoolean(15, Sex);
        pt.setBlob(16, Image);
        pt.setString(17, Email);
        pt.setString(18, User);
        pt.execute();
        pt.close();
        connection.close();
    }
// set Role for User

    public static void setRoleUser(String User) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        String ex = "Insert Into Role(EmployeeID) Values (?)";
        PreparedStatement pts = connection.prepareStatement(ex);
        pts.setString(1, User);
        pts.execute();
        pts.close();
        connection.close();
    }
// set Role for admin

    public static void setRoleAdmin(String User) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        String ex = "Insert Into Role Values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pts = connection.prepareStatement(ex);
        pts.setString(1, User);
        pts.setBoolean(2, true);
        pts.setBoolean(3, true);
        pts.setBoolean(4, true);
        pts.setBoolean(5, true);
        pts.setBoolean(6, true);
        pts.setBoolean(7, true);
        pts.setBoolean(8, true);
        pts.setBoolean(9, true);
        pts.setBoolean(10, true);
        pts.setBoolean(11, true);
        pts.setBoolean(12, true);
        pts.setBoolean(13, true);
        pts.setBoolean(14, true);
        pts.setBoolean(15, true);
        pts.setBoolean(16, true);
        pts.setBoolean(17, true);
        pts.setBoolean(18, true);
        pts.setBoolean(19, true);
        pts.setBoolean(20, true);
        pts.setBoolean(21, true);
        pts.setBoolean(22, true);
        pts.setBoolean(23, true);
        pts.setBoolean(24, true);
        pts.setBoolean(25, true);
        pts.setBoolean(26, true);
        pts.setBoolean(27, true);
        pts.setBoolean(28, true);
        pts.setBoolean(29, true);
        pts.setBoolean(30, true);
        pts.setBoolean(31, true);
        pts.setBoolean(32, true);
        pts.setBoolean(33, true);
        pts.setBoolean(34, true);
        pts.setBoolean(35, true);
        pts.setBoolean(36, true);
        pts.setBoolean(37, true);
        pts.setBoolean(38, true);
        pts.setBoolean(39, true);
        pts.setBoolean(40, true);
        pts.setBoolean(41, true);
        pts.setBoolean(42, true);
        pts.setBoolean(43, true);
        pts.setBoolean(44, true);
        pts.setBoolean(45, true);
        pts.setBoolean(46, true);
        pts.setBoolean(47, true);
        pts.setBoolean(48, true);
        pts.setBoolean(49, true);
        pts.setBoolean(50, true);
        pts.setBoolean(51, true);
        pts.setBoolean(52, true);
        pts.setBoolean(53, true);
        pts.execute();
        pts.close();
        connection.close();
    }
}
