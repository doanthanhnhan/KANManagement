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
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import utils.connectDB;

/**
 *
 * @author Admin
 */
public class DAOCustomerBookingCheckIn {
//     list booking future

    public static ObservableList<BookingInfo> check_ListbookingFuture(String ID) {
        ObservableList<BookingInfo> list_Booking_Info = FXCollections.observableArrayList();
        try {
            Connection connection = connectDB.connectSQLServer();
            // Tạo đối tượng Statement.
            String sql = "select * from bookinginfo where CustomerID=? And DateDiff(Day,DateBook,GetDate()) <0 And Bookinginfo.BookingID NOT IN (SELECT BookingID FROM CheckInOrders)";
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            PreparedStatement pt = connection.prepareStatement(sql);
            pt.setString(1, ID);
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                BookingInfo bk = new BookingInfo();
                bk.setBookID(rs.getString("BookingID"));
                bk.setCusID(rs.getString("CustomerID"));
                bk.setRoomID(rs.getString("RoomID"));
                bk.setNote(rs.getString("Note"));
                bk.setNumGuest(rs.getInt("NumberGuest"));
                bk.setUser(rs.getString("UserName"));
                bk.setDate(rs.getString("DateBook"));
                list_Booking_Info.add(bk);
            }
            pt.close();
            connection.close();
            rs.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DAOCustomerBookingCheckIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list_Booking_Info;
    }
//    check bookingid have checkin

    public static boolean check_Booking(String ID) {
        try {
            Connection connection = connectDB.connectSQLServer();
            // Tạo đối tượng Statement.
            String sql = "select BookingID from CheckInOrders where BookingID = ?";
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            PreparedStatement pt = connection.prepareStatement(sql);
            pt.setString(1, ID);
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                pt.close();
                connection.close();
                rs.close();
                return true;
            }
            pt.close();
            connection.close();
            rs.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DAOCustomerBookingCheckIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
//    update roombooking

    public static void Update_RoomBooking(String id, String RoomID) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        String exp = "UPDATE BookingInfo SET RoomID = ? WHERE BookingID = ?";
        PreparedStatement pt = connection.prepareStatement(exp);
        pt.setString(1, RoomID);
        pt.setString(2, id);
        pt.execute();
        pt.close();
        connection.close();
    }

    public static void deleteBooking(String id) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        String exp = "Delete from BookingInfo WHERE BookingID = ?";
        PreparedStatement pt = connection.prepareStatement(exp);
        pt.setString(1, id);
        pt.execute();
        pt.close();
        connection.close();
    }

    //    get list booking Virtual with from date and todate
    public static ObservableList<BookingInfo> getListBookingVirtual(String FromDate, String ToDate) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        ObservableList<BookingInfo> list_Booking = FXCollections.observableArrayList();
        String sql;
        if (FromDate.equals("")) {
            sql = "select * from BookingInfo where  DateBook<=? And BookingID NOT IN (SELECT BookingID FROM CheckInOrders)";
        } else {
            sql = "select * from BookingInfo where  ?<=DateBook AND DateBook<=? And BookingID NOT IN (SELECT BookingID FROM CheckInOrders)";
        }
        PreparedStatement pt = connection.prepareStatement(sql);
        if (FromDate.equals("")) {
            pt.setString(1, ToDate);
        } else {
            pt.setString(1, FromDate);
            pt.setString(2, ToDate);
        }
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        ResultSet rs = pt.executeQuery();
        while (rs.next()) {
            BookingInfo bk = new BookingInfo();
            bk.setBookID(rs.getString("BookingID"));
            bk.setCusID(rs.getString("CustomerID"));
            bk.setRoomID(rs.getString("RoomID"));
            bk.setUser(rs.getString("UserName"));
            bk.setNote(rs.getString("Note"));
            bk.setNumGuest(rs.getInt("NumberGuest"));
            bk.setDate(LocalDate.parse(rs.getString("DateBook")).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            list_Booking.add(bk);
        }
        return list_Booking;
    }
    //    get list booking Virtual

    public static ObservableList<BookingInfo> getListBookingVirtual() throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        ObservableList<BookingInfo> list_Booking = FXCollections.observableArrayList();
        String sql = "select * from BookingInfo where  DateDiff(Day,DateBook,GetDate()) >0 And BookingID NOT IN (SELECT BookingID FROM CheckInOrders)";
        PreparedStatement pt = connection.prepareStatement(sql);
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        ResultSet rs = pt.executeQuery();
        while (rs.next()) {
            BookingInfo bk = new BookingInfo();
            bk.setBookID(rs.getString("BookingID"));
            bk.setCusID(rs.getString("CustomerID"));
            bk.setRoomID(rs.getString("RoomID"));
            bk.setUser(rs.getString("UserName"));
            bk.setNote(rs.getString("Note"));
            bk.setNumGuest(rs.getInt("NumberGuest"));
            bk.setDate(LocalDate.parse(rs.getString("DateBook")).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            list_Booking.add(bk);
        }
        return list_Booking;
    }
//    get list booking already

    public static ObservableList<BookingInfo> getListBooking() throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        ObservableList<BookingInfo> list_Booking = FXCollections.observableArrayList();
        String sql = "select * from BookingInfo where  DateDiff(Day,DateBook,GetDate()) <=0 And BookingID NOT IN (SELECT BookingID FROM CheckInOrders)";
        PreparedStatement pt = connection.prepareStatement(sql);
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        ResultSet rs = pt.executeQuery();
        while (rs.next()) {
            BookingInfo bk = new BookingInfo();
            bk.setBookID(rs.getString("BookingID"));
            bk.setCusID(rs.getString("CustomerID"));
            bk.setRoomID(rs.getString("RoomID"));
            bk.setUser(rs.getString("UserName"));
            bk.setNote(rs.getString("Note"));
            bk.setNumGuest(rs.getInt("NumberGuest"));
            bk.setDate(LocalDate.parse(rs.getString("DateBook")).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            list_Booking.add(bk);
        }
        return list_Booking;
    }
//    update numberofCustomer

    public static void Update_NumbercustomerOfBooking(String id, Integer number) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        String exp = "UPDATE BookingInfo SET NumberGuest = ? WHERE BookingID = ?";
        PreparedStatement pt = connection.prepareStatement(exp);
        pt.setInt(1, number);
        pt.setString(2, id);
        pt.execute();
        pt.close();
        connection.close();
    }
//    delete department

    public static void Delete_Department(String id) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        String exp = "Delete from Departments where DepartmentID=?";
        PreparedStatement pt = connection.prepareStatement(exp);
        pt.setString(1, id);
        pt.execute();
        pt.close();
        connection.close();
    }
//    check delete department

    public static boolean check_delete_Department(String ID) {
        try {
            Connection connection = connectDB.connectSQLServer();
            // Tạo đối tượng Statement.
            String sql = "select DepartmentID from Departments where DepartmentID = ? and DepartmentID IN (Select DepartmentID from Employees)";
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            PreparedStatement pt = connection.prepareStatement(sql);
            pt.setString(1, ID);
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                pt.close();
                connection.close();
                rs.close();
                return true;
            }
            pt.close();
            connection.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomerBookingCheckIn.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAOCustomerBookingCheckIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
//    check roleview true or false of department

    public static boolean check_RoleView_Department(String ID) {
        try {
            Connection connection = connectDB.connectSQLServer();
            // Tạo đối tượng Statement.
            String sql = "select Role_View from Departments where DepartmentID = ?";
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            PreparedStatement pt = connection.prepareStatement(sql);
            pt.setString(1, ID);
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                if (rs.getBoolean("Role_View")) {
                    pt.close();
                    connection.close();
                    rs.close();
                    return true;
                }
            }
            pt.close();
            connection.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomerBookingCheckIn.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAOCustomerBookingCheckIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
//    DAO check Role_view delete Employee 

    public static boolean check_Delete_Employee(String ID) {
        try {
            Connection connection = connectDB.connectSQLServer();
            // Tạo đối tượng Statement.
            String sql = "select EmployeeID from Role where EmployeeID = ? And Role.Role_View = 1";
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            PreparedStatement pt = connection.prepareStatement(sql);
            pt.setString(1, ID);
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                pt.close();
                connection.close();
                rs.close();
                return true;
            }
            pt.close();
            connection.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomerBookingCheckIn.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAOCustomerBookingCheckIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    //    update role_view

    public static void Update_RoleView(String id, Boolean check) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        String exp = "UPDATE Role SET Role_View = ? WHERE EmployeeID = ?";
        PreparedStatement pt = connection.prepareStatement(exp);
        pt.setBoolean(1, check);
        pt.setString(2, id);
        pt.execute();
        pt.close();
        connection.close();
    }
//    delete Userlogs

    public static void Delete_UserLogs(Integer id) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        String exp = "UPDATE UserLogs SET Active = 0 WHERE ID = ?";
        PreparedStatement pt = connection.prepareStatement(exp);
        pt.setInt(1, id);
        pt.execute();
        pt.close();
        connection.close();
    }
//    get all info UserLogs

    public static ObservableList<UserLogs> getAllUserLogs() throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        ObservableList<UserLogs> list_UserLogs = FXCollections.observableArrayList();
        String sql = "select * from UserLogs where Active = 1";
        PreparedStatement pt = connection.prepareStatement(sql);
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        ResultSet rs = pt.executeQuery();
        while (rs.next()) {
            UserLogs Ul = new UserLogs();
            Ul.setID(rs.getInt("ID"));
            Ul.setUserName(rs.getString("Username"));
            Ul.setMacAddress(rs.getString("MACAddress"));
            Ul.setLogContent(rs.getString("LogContent"));
            Ul.setLogTime(rs.getTimestamp("LogTime").toLocalDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
            list_UserLogs.add(Ul);
        }
        return list_UserLogs;
    }
//    ReActive Customer

    public static void ReActive_Customer(String id) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        String exp = "UPDATE Customers SET Active = 1 WHERE CustomerID = ?";
        PreparedStatement pt = connection.prepareStatement(exp);
        pt.setString(1, id);
        pt.execute();
        pt.close();
        connection.close();
    }
//    Check Active of customer

    public static boolean check_Active_Customer(String ID) {
        try {
            Connection connection = connectDB.connectSQLServer();
            // Tạo đối tượng Statement.
            String sql = "Select Active from Customers where CustomerID=?";
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            PreparedStatement pt = connection.prepareStatement(sql);
            pt.setString(1, ID);
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                if (rs.getBoolean("Active")) {
                    pt.close();
                    connection.close();
                    rs.close();
                    return true;
                }
            }
            pt.close();
            connection.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomerBookingCheckIn.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAOCustomerBookingCheckIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
//    check remove customer

    public static boolean check_Remove_Customer(String ID) {
        try {
            Connection connection = connectDB.connectSQLServer();
            // Tạo đối tượng Statement.
            String sql = "select CustomerID from Customers where CustomerID = ? AND\n"
                    + "(\n"
                    + "	Customers.CustomerID IN (select CustomerID from BookingInfo where  DATEDIFF(DAY,DateBook,GETDATE()) <= 0)\n"
                    + "	OR Customers.CustomerID IN (Select Bill.CustomerID from Bill)\n"
                    + "	)";
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
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomerBookingCheckIn.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAOCustomerBookingCheckIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
//    check role_view can not disable checkbox when only one

    public static Integer check_Role_View_Disable() throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        String sql = "select Count(*) as 'count' from Role where Role_View = 1 and EmployeeID IN (select EmployeeID from Employees where active = 1)";
        PreparedStatement pt = connection.prepareStatement(sql);
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        int count = 0;
        ResultSet rs = pt.executeQuery();
        while (rs.next()) {
            count = rs.getInt("Count");
        }
        return count;
    }
//    delete customer

    public static void deleteCustomer(String id) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        String exp = "UPDATE Customers SET Active = 0 WHERE CustomerID = ?";
        PreparedStatement pt = connection.prepareStatement(exp);
        pt.setString(1, id);
        pt.execute();
        pt.close();
        connection.close();
    }
//    get all customer for listCustomer

    public static ObservableList<Customer> getAllCustomer() throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        ObservableList<Customer> list_Customer = FXCollections.observableArrayList();
        String sql = "select * from Customers where Active = 1";
        PreparedStatement pt = connection.prepareStatement(sql);
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        ResultSet rs = pt.executeQuery();
        while (rs.next()) {
            Customer cm = new Customer();
            cm.setCusID(rs.getString("CustomerID"));
            cm.setUser(rs.getString("Username"));
            cm.setPassport(rs.getString("CustomerPassport"));
            cm.setFName(rs.getString("CustomerFirstName"));
            cm.setMName(rs.getString("CustomerMidName"));
            cm.setLName(rs.getString("CustomerLastName"));
            cm.setDate(rs.getString("CustomerBirthday"));
            cm.setPhone(rs.getString("CustomerPhoneNumber"));
            cm.setEmail(rs.getString("CustomerEmail"));
            cm.setDiscount(rs.getFloat("Discount"));
            cm.setCompany(rs.getString("Company"));
            cm.setSex(rs.getBoolean("Sex"));
            list_Customer.add(cm);
        }
        return list_Customer;
    }
//    get customer email for sending QRcode - Nhan edit

    public static String getCustomerEmail(String customerID) {
        String customerEmail = "";
        try {
            Connection connection = connectDB.connectSQLServer();

            String sql = "SELECT CustomerEmail FROM Customers WHERE CustomerID = '" + customerID + "'";
            PreparedStatement pt = connection.prepareStatement(sql);
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                if (rs.getString("CustomerEmail") != null) {
                    customerEmail = rs.getString("CustomerEmail");
                }
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DAOCustomerBookingCheckIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return customerEmail;
    }

    // Insert CTM-000000000 customer for add new room - Nhan edit
    public static void addCTMFree() {
        try {
            Connection connection = connectDB.connectSQLServer();

            String sql = "SELECT CustomerID FROM Customers WHERE CustomerID = 'CTM-000000000'";
            PreparedStatement pt = connection.prepareStatement(sql);
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = pt.executeQuery();
            if (!rs.next()) {
                Customer customerFREE = new Customer();
                customerFREE.setCusID("CTM-000000000");
                customerFREE.setCompany("KAN");
                customerFREE.setDate("1980-01-01");
                customerFREE.setDiscount(0f);
                customerFREE.setEmail("kanmanagment.ap146@gmail.com");
                customerFREE.setFName("Free");
                customerFREE.setMName("");
                customerFREE.setLName("Room");
                customerFREE.setPassport("000000000");
                customerFREE.setPhone("000000000");
                customerFREE.setSex(true);
                customerFREE.setUser("admin");
                customerFREE.setActive(true);

                AddNewCustomer(customerFREE);

            }
        } catch (ClassNotFoundException | SQLException | MalformedURLException ex) {
            Logger.getLogger(DAOCustomerBookingCheckIn.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
//    Update Customer

    public static void UpdateInfoCustomer(Customer ctm) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        String exp = "UPDATE Customers SET UserName = ?, CustomerFirstName = ? ,CustomerMidName = ?,CustomerLastName = ?,CustomerBirthday=?,CustomerPhoneNumber=?,"
                + "CustomerEmail=?,Discount=?,Company=?,Sex=? WHERE CustomerID = ?";
        PreparedStatement pt = connection.prepareStatement(exp);
        pt.setString(1, ctm.getUser());
        pt.setString(2, ctm.getFName());
        pt.setString(3, ctm.getMName());
        pt.setString(4, ctm.getLName());
        pt.setString(5, ctm.getDate());
        pt.setString(6, ctm.getPhone());
        pt.setString(7, ctm.getEmail());
        pt.setFloat(8, ctm.getDiscount());
        pt.setString(9, ctm.getCompany());
        pt.setBoolean(10, ctm.getSex());
        pt.setString(11, ctm.getCusID());
        pt.execute();
        pt.close();
        connection.close();
    }
//    check da booking hay chua

    public static ObservableList<BookingInfo> check_BookingIdCustomer(String ID) {
        ObservableList<BookingInfo> list_Booking_Info = FXCollections.observableArrayList();
        try {
            Connection connection = connectDB.connectSQLServer();
            // Tạo đối tượng Statement.
            String sql = "select * from bookinginfo where CustomerID=? And DateDiff(Day,DateBook,GetDate()) =0 And Bookinginfo.BookingID NOT IN (SELECT BookingID FROM CheckInOrders)";
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            PreparedStatement pt = connection.prepareStatement(sql);
            pt.setString(1, ID);
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                BookingInfo bk = new BookingInfo();
                bk.setBookID(rs.getString("BookingID"));
                bk.setCusID(rs.getString("CustomerID"));
                bk.setRoomID(rs.getString("RoomID"));
                bk.setNote(rs.getString("Note"));
                bk.setNumGuest(rs.getInt("NumberGuest"));
                bk.setUser(rs.getString("UserName"));
                bk.setDate(rs.getString("DateBook"));
                list_Booking_Info.add(bk);
            }
            pt.close();
            connection.close();
            rs.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DAOCustomerBookingCheckIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list_Booking_Info;
    }

    //Get check in room infomations - Nhan edit
    public static CheckIn getCheckInRoom(String roomID) {
        String sql = "SELECT * FROM CheckInOrders WHERE RoomID IN (SELECT RoomID FROM Rooms WHERE RoomStatus = 'Occupied') "
                + "AND RoomID = '" + roomID + "'";
        CheckIn checkIn = new CheckIn();
        try {
            try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    checkIn.setCheckID(rs.getString("CheckInID"));
                    checkIn.setBookID(rs.getString("BookingID"));
                    checkIn.setCusID(rs.getString("CustomerID"));
                    checkIn.setRoomID(rs.getString("RoomID"));
                    checkIn.setUser(rs.getString("UserName"));
                    checkIn.setCheckType(rs.getString("CheckInType"));
                    checkIn.setNumberOfCustomer(rs.getInt("NumberOfCustomer"));
                    checkIn.setDateIn(rs.getString("CheckInDate"));
                    checkIn.setDateOut(rs.getString("LeaveDate"));
                    checkIn.setCusPack(rs.getString("CustomerPackage"));
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error Querry");
                alert.setContentText("Don't have any check in ID in Database or Can't connect to Database");
                alert.show();
            });
            Logger.getLogger(DAOCustomerBookingCheckIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return checkIn;
    }
//    get info customer

    public static Customer getAllCustomerInfo(String id) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        Customer cm = new Customer();
        String sql = "select * from Customers where CustomerID=?";
        PreparedStatement pt = connection.prepareStatement(sql);
        pt.setString(1, id);
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        ResultSet rs = pt.executeQuery();
        while (rs.next()) {
            cm.setPassport(rs.getString("CustomerPassport"));
            cm.setFName(rs.getString("CustomerFirstName"));
            cm.setMName(rs.getString("CustomerMidName"));
            cm.setLName(rs.getString("CustomerLastName"));
            cm.setDate(rs.getString("CustomerBirthday"));
            cm.setPhone(rs.getString("CustomerPhoneNumber"));
            cm.setEmail(rs.getString("CustomerEmail"));
            cm.setDiscount(rs.getFloat("Discount"));
            cm.setCompany(rs.getString("Company"));
            cm.setSex(rs.getBoolean("Sex"));
        }
        return cm;
    }

//    Add new checkIn
    public static void AddNewCheckIn(CheckIn ck) throws MalformedURLException, SQLException, ClassNotFoundException {
        Connection connection = connectDB.connectSQLServer();
        String exm = "Insert into CheckInOrders values(?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pt = connection.prepareStatement(exm);
        pt.setString(1, ck.getCheckID());
        pt.setString(2, ck.getBookID());
        pt.setString(3, ck.getCusID());
        pt.setString(4, ck.getRoomID());
        pt.setString(5, ck.getUser());
        pt.setString(6, ck.getCheckType());
        pt.setInt(7, ck.getNumberOfCustomer());
        pt.setString(8, ck.getDateIn());
        pt.setString(9, ck.getDateOut());
        pt.setString(10, ck.getCusPack());
        pt.setInt(11, 1);
        pt.execute();
        pt.close();
        connection.close();
    }

    //    get Add bookingID use for checkin
    public static ObservableList<String> getAllBookingID() throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        ObservableList<String> list_Booking_ID = FXCollections.observableArrayList();
        String sql = "select Bookinginfo.BookingID from Bookinginfo WHERE Bookinginfo.BookingID NOT IN (SELECT BookingID FROM CheckInOrders)";
        PreparedStatement pt = connection.prepareStatement(sql);

        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        ResultSet rs = pt.executeQuery();
        while (rs.next()) {
            list_Booking_ID.add(rs.getString("BookingID"));
        }
        return list_Booking_ID;
    }
//    get Add info booking

    public static BookingInfo getAllBookingInfo(String bookingID) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        BookingInfo bk = new BookingInfo();
        String sql = "select * from BookingInfo where BookingID=?";
        PreparedStatement pt = connection.prepareStatement(sql);
        pt.setString(1, bookingID);
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        ResultSet rs = pt.executeQuery();
        while (rs.next()) {
            bk.setCusID(rs.getString("CustomerID"));
            bk.setRoomID(rs.getString("RoomID"));
            bk.setNumGuest(rs.getInt("NumberGuest"));
        }
        return bk;
    }
//    Add New Booking

    public static void AddNewBooking(BookingInfo bk) throws MalformedURLException, SQLException, ClassNotFoundException {
        Connection connection = connectDB.connectSQLServer();
        String exm = "Insert into Bookinginfo values(?,?,?,?,?,?,?)";
        PreparedStatement pt = connection.prepareStatement(exm);
        pt.setString(1, bk.getBookID());
        pt.setString(2, bk.getCusID());
        pt.setString(3, bk.getRoomID());
        pt.setString(4, bk.getUser());
        pt.setString(5, bk.getNote());
        pt.setInt(6, bk.getNumGuest());
        pt.setString(7, bk.getDate());
        pt.execute();
        pt.close();
        connection.close();
    }
//    Get all Id of Customer

    public static ObservableList<String> getAllIdCustomer() throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        // Tạo đối tượng Statement.
        Statement statement = connection.createStatement();
        String sql = "select CustomerID from Customers where Active = 1 And CustomerID != CTM-000000000";

        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        ResultSet rs = statement.executeQuery(sql);
        ObservableList listIdUser = FXCollections.observableArrayList();
        while (rs.next()) {
            listIdUser.add(rs.getString("CustomerID"));
        }
        connection.close();
        return listIdUser;
    }
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
//    Check invalid CustomerID

    public static boolean check_IDCustomer(String ID) {
        try {
            Connection connection = connectDB.connectSQLServer();
            // Tạo đối tượng Statement.
            String sql = "select CustomerID from Customers where CustomerID = ?";
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            PreparedStatement pt = connection.prepareStatement(sql);
            pt.setString(1, ID);
            ResultSet rs = pt.executeQuery();
            if (rs.next()) {
                pt.close();
                connection.close();
                rs.close();
                return false;
            }
            pt.close();
            connection.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomerBookingCheckIn.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAOCustomerBookingCheckIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
}
