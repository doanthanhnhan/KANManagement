/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.jfoenix.controls.JFXButton;
import controllers.FXMLDecentralizationController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
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
    // Lấy ra quyền của các User

    public static ObservableList<DecentralizationModel> getAllDecentralization() throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        ObservableList<DecentralizationModel> list = FXCollections.observableArrayList();
        String sql = "select Employees.EmployeeID,Employees.EmployeeFirstName,Employees.EmployeeLastName,Employees.EmployeeMidName,Role.* from Employees,Role where Employees.active = 1  and Employees.EmployeeID=Role.EmployeeID";
        PreparedStatement pt = connection.prepareStatement(sql);
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        ResultSet rs = pt.executeQuery();
        while (rs.next()) {
            DecentralizationModel Emp = new DecentralizationModel();
            Emp.setEmployee_ID(rs.getString("EmployeeID"));
            Emp.setFirst_Name(rs.getString("EmployeeFirstName"));
            if (rs.getString("EmployeeMidName") == null) {
                Emp.setMid_Name("");
            } else {
                Emp.setMid_Name(rs.getString("EmployeeMidName"));
            }
            Emp.setLast_Name(rs.getString("EmployeeLastName"));
            Emp.setcheckEmployee_Add(rs.getBoolean("Employee_Add"));
            Emp.setcheckEmployee_Delete(rs.getBoolean("Employee_Delete"));
            Emp.setcheckEmployee_Edit(rs.getBoolean("Employee_Edit"));
            Emp.setcheckEmployee_View(rs.getBoolean("Employee_View"));
            Emp.setcheckUser_Add(rs.getBoolean("User_Add"));
            Emp.setcheckUser_Delete(rs.getBoolean("User_Delete"));
            Emp.setcheckUser_View(rs.getBoolean("User_View"));
            Emp.setcheckUser_Edit(rs.getBoolean("User_Edit"));

// create check box Emp va User
            CheckBox cb_Employee_Add = new CheckBox("");
            CheckBox cb_Employee_Delete = new CheckBox("");
            CheckBox cb_Employee_View = new CheckBox("");
            CheckBox cb_Employee_Edit = new CheckBox("");
            CheckBox cb_User_Add = new CheckBox("");
            CheckBox cb_User_Delete = new CheckBox("");
            CheckBox cb_User_View = new CheckBox("");
            CheckBox cb_User_Edit = new CheckBox("");

// create check box Booking va CheckIn
            CheckBox cb_Booking_Add = new CheckBox("");
            CheckBox cb_Booking_Delete = new CheckBox("");
            CheckBox cb_Booking_View = new CheckBox("");
            CheckBox cb_Booking_Edit = new CheckBox("");
            CheckBox cb_CheckIn_Add = new CheckBox("");
            CheckBox cb_CheckIn_Delete = new CheckBox("");
            CheckBox cb_CheckIn_View = new CheckBox("");
            CheckBox cb_CheckIn_Edit = new CheckBox("");

// create check box CheckOut va Customer
            CheckBox cb_CheckOut_Add = new CheckBox("");
            CheckBox cb_CheckOut_Delete = new CheckBox("");
            CheckBox cb_CheckOut_View = new CheckBox("");
            CheckBox cb_CheckOut_Edit = new CheckBox("");
            CheckBox cb_Customer_Add = new CheckBox("");
            CheckBox cb_Customer_Delete = new CheckBox("");
            CheckBox cb_Customer_View = new CheckBox("");
            CheckBox cb_Customer_Edit = new CheckBox("");

// create check box Department va Role
            CheckBox cb_Department_Add = new CheckBox("");
            CheckBox cb_Department_Delete = new CheckBox("");
            CheckBox cb_Department_View = new CheckBox("");
            CheckBox cb_Department_Edit = new CheckBox("");
            CheckBox cb_Role_Add = new CheckBox("");
            CheckBox cb_Role_Delete = new CheckBox("");
            CheckBox cb_Role_View = new CheckBox("");
            CheckBox cb_Role_Edit = new CheckBox("");

// create check box Room va SODetail
            CheckBox cb_Room_Add = new CheckBox("");
            CheckBox cb_Room_Delete = new CheckBox("");
            CheckBox cb_Room_View = new CheckBox("");
            CheckBox cb_Room_Edit = new CheckBox("");
            CheckBox cb_SODetail_Add = new CheckBox("");
            CheckBox cb_SODetail_Delete = new CheckBox("");
            CheckBox cb_SODetail_View = new CheckBox("");
            CheckBox cb_SODetail_Edit = new CheckBox("");

// create check box SODer va SType UserLog
            CheckBox cb_SODer_Add = new CheckBox("");
            CheckBox cb_SODer_Delete = new CheckBox("");
            CheckBox cb_SODer_View = new CheckBox("");
            CheckBox cb_SODer_Edit = new CheckBox("");
            CheckBox cb_SType_Add = new CheckBox("");
            CheckBox cb_SType_Delete = new CheckBox("");
            CheckBox cb_SType_View = new CheckBox("");
            CheckBox cb_SType_Edit = new CheckBox("");
            CheckBox cb_UserLog_Add = new CheckBox("");
            CheckBox cb_UserLog_Delete = new CheckBox("");
            CheckBox cb_UserLog_View = new CheckBox("");
            CheckBox cb_UserLog_Edit = new CheckBox("");

//            get Department va CheckOut CheckIn Customer Booking
            cb_Booking_Add.setSelected(rs.getBoolean("Booking_Add"));
            cb_Booking_Add.setDisable(true);
            cb_Booking_Delete.setSelected(rs.getBoolean("Booking_Delete"));
            cb_Booking_Delete.setDisable(true);
            cb_Booking_View.setSelected(rs.getBoolean("Booking_View"));
            cb_Booking_View.setDisable(true);
            cb_Booking_Edit.setSelected(rs.getBoolean("Booking_Edit"));
            cb_Booking_Edit.setDisable(true);
            cb_Customer_Add.setSelected(rs.getBoolean("Customer_Add"));
            cb_Customer_Add.setDisable(true);
            cb_Customer_Delete.setSelected(rs.getBoolean("Customer_Delete"));
            cb_Customer_Delete.setDisable(true);
            cb_Customer_View.setSelected(rs.getBoolean("Customer_View"));
            cb_Customer_View.setDisable(true);
            cb_Customer_Edit.setSelected(rs.getBoolean("Customer_Edit"));
            cb_Customer_Edit.setDisable(true);
            cb_Department_Add.setSelected(rs.getBoolean("Department_Add"));
            cb_Department_Add.setDisable(true);
            cb_Department_Delete.setSelected(rs.getBoolean("Department_Delete"));
            cb_Department_Delete.setDisable(true);
            cb_Department_View.setSelected(rs.getBoolean("Department_View"));
            cb_Department_View.setDisable(true);
            cb_Department_Edit.setSelected(rs.getBoolean("Department_Edit"));
            cb_Department_Edit.setDisable(true);
            cb_CheckOut_Add.setSelected(rs.getBoolean("CheckOut_Add"));
            cb_CheckOut_Add.setDisable(true);
            cb_CheckOut_Delete.setSelected(rs.getBoolean("CheckOut_Delete"));
            cb_CheckOut_Delete.setDisable(true);
            cb_CheckOut_View.setSelected(rs.getBoolean("CheckOut_View"));
            cb_CheckOut_View.setDisable(true);
            cb_CheckOut_Edit.setSelected(rs.getBoolean("CheckOut_Edit"));
            cb_CheckOut_Edit.setDisable(true);
            cb_CheckIn_Add.setSelected(rs.getBoolean("CheckIn_Add"));
            cb_CheckIn_Add.setDisable(true);
            cb_CheckIn_Delete.setSelected(rs.getBoolean("CheckIn_Delete"));
            cb_CheckIn_Delete.setDisable(true);
            cb_CheckIn_View.setSelected(rs.getBoolean("CheckIn_View"));
            cb_CheckIn_View.setDisable(true);
            cb_CheckIn_Edit.setSelected(rs.getBoolean("CheckIn_Edit"));
            cb_CheckIn_Edit.setDisable(true);

//            get Room va SODetail Role
            cb_Room_Add.setSelected(rs.getBoolean("Room_Add"));
            cb_Room_Add.setDisable(true);
            cb_Room_Delete.setSelected(rs.getBoolean("Room_Delete"));
            cb_Room_Delete.setDisable(true);
            cb_Room_View.setSelected(rs.getBoolean("Room_View"));
            cb_Room_View.setDisable(true);
            cb_Room_Edit.setSelected(rs.getBoolean("Room_Edit"));
            cb_Room_Edit.setDisable(true);
            cb_SODetail_Add.setSelected(rs.getBoolean("SODetail_Add"));
            cb_SODetail_Add.setDisable(true);
            cb_SODetail_Delete.setSelected(rs.getBoolean("SODetail_Delete"));
            cb_SODetail_Delete.setDisable(true);
            cb_SODetail_View.setSelected(rs.getBoolean("SODetail_View"));
            cb_SODetail_View.setDisable(true);
            cb_SODetail_Edit.setSelected(rs.getBoolean("SODetail_Edit"));
            cb_SODetail_Edit.setDisable(true);
            cb_Role_Add.setSelected(rs.getBoolean("Role_Add"));
            cb_Role_Add.setDisable(true);
            cb_Role_Delete.setSelected(rs.getBoolean("Role_Delete"));
            cb_Role_Delete.setDisable(true);
            cb_Role_View.setSelected(rs.getBoolean("Role_View"));
            cb_Role_View.setDisable(true);
            cb_Role_Edit.setSelected(rs.getBoolean("Role_Edit"));
            cb_Role_Edit.setDisable(true);

//            get SODer va SType UserLog
            cb_SODer_Add.setSelected(rs.getBoolean("SODer_Add"));
            cb_SODer_Add.setDisable(true);
            cb_SODer_Delete.setSelected(rs.getBoolean("SODer_Delete"));
            cb_SODer_Delete.setDisable(true);
            cb_SODer_View.setSelected(rs.getBoolean("SODer_View"));
            cb_SODer_View.setDisable(true);
            cb_SODer_Edit.setSelected(rs.getBoolean("SODer_Edit"));
            cb_SODer_Edit.setDisable(true);
            cb_SType_Add.setSelected(rs.getBoolean("SType_Add"));
            cb_SType_Add.setDisable(true);
            cb_SType_Delete.setSelected(rs.getBoolean("SType_Delete"));
            cb_SType_Delete.setDisable(true);
            cb_SType_View.setSelected(rs.getBoolean("SType_View"));
            cb_SType_View.setDisable(true);
            cb_SType_Edit.setSelected(rs.getBoolean("SType_Edit"));
            cb_SType_Edit.setDisable(true);
            cb_UserLog_Add.setSelected(rs.getBoolean("UserLog_Add"));
            cb_UserLog_Add.setDisable(true);
            cb_UserLog_Delete.setSelected(rs.getBoolean("UserLog_Delete"));
            cb_UserLog_Delete.setDisable(true);
            cb_UserLog_View.setSelected(rs.getBoolean("UserLog_View"));
            cb_UserLog_View.setDisable(true);
            cb_UserLog_Edit.setSelected(rs.getBoolean("UserLog_Edit"));
            cb_UserLog_Edit.setDisable(true);
//            get value
            cb_User_Add.setSelected(rs.getBoolean("User_Add"));
            cb_User_Add.setDisable(true);
            cb_User_Delete.setSelected(rs.getBoolean("User_Delete"));
            cb_User_Delete.setDisable(true);
            cb_User_View.setSelected(rs.getBoolean("User_View"));
            cb_User_View.setDisable(true);
            cb_User_Edit.setSelected(rs.getBoolean("User_Edit"));
            cb_User_Edit.setDisable(true);
            cb_Employee_Add.setSelected(rs.getBoolean("Employee_Add"));
//            cb_Employee_Add.setOnAction((event) -> {
//                if (cb_Employee_Add.isSelected()) {
//                    System.out.println("cb true " + Emp.getEmployee_ID());
//                } else {
//                    System.out.println("cb false " + Emp.getEmployee_ID());
//                }
//            });
            cb_Employee_Add.setDisable(true);
            cb_Employee_Delete.setSelected(rs.getBoolean("Employee_Delete"));
            cb_Employee_Delete.setDisable(true);
            cb_Employee_View.setSelected(rs.getBoolean("Employee_View"));
            cb_Employee_View.setDisable(true);
            cb_Employee_Edit.setSelected(rs.getBoolean("Employee_Edit"));
            cb_Employee_Edit.setDisable(true);

//          Department va CheckOut CheckIn Customer Booking
            Emp.setDepartment_Add(cb_Department_Add);
            Emp.setDepartment_Delete(cb_Department_Delete);
            Emp.setDepartment_Edit(cb_Department_Edit);
            Emp.setDepartment_View(cb_Department_View);
            Emp.setCheckOut_Add(cb_CheckOut_Add);
            Emp.setCheckOut_Delete(cb_CheckOut_Delete);
            Emp.setCheckOut_Edit(cb_CheckOut_Edit);
            Emp.setCheckOut_View(cb_CheckOut_View);
            Emp.setCheckIn_Add(cb_CheckIn_Add);
            Emp.setCheckIn_Delete(cb_CheckIn_Delete);
            Emp.setCheckIn_Edit(cb_CheckIn_Edit);
            Emp.setCheckIn_View(cb_CheckIn_View);
            Emp.setCustomer_Add(cb_Customer_Add);
            Emp.setCustomer_Delete(cb_Customer_Delete);
            Emp.setCustomer_Edit(cb_Customer_Edit);
            Emp.setCustomer_View(cb_Customer_View);
            Emp.setBooking_Add(cb_Booking_Add);
            Emp.setBooking_Delete(cb_Booking_Delete);
            Emp.setBooking_Edit(cb_Booking_Edit);
            Emp.setBooking_View(cb_Booking_View);

//            set UserLog SType SODer Room SODetail Role
            Emp.setUserLog_Add(cb_UserLog_Add);
            Emp.setUserLog_Delete(cb_UserLog_Delete);
            Emp.setUserLog_Edit(cb_UserLog_Edit);
            Emp.setUserLog_View(cb_UserLog_View);
            Emp.setSType_Add(cb_SType_Add);
            Emp.setSType_Delete(cb_SType_Delete);
            Emp.setSType_Edit(cb_SType_Edit);
            Emp.setSType_View(cb_SType_View);
            Emp.setSODer_Add(cb_SODer_Add);
            Emp.setSODer_Delete(cb_SODer_Delete);
            Emp.setSODer_Edit(cb_SODer_Edit);
            Emp.setSODer_View(cb_SODer_View);
            Emp.setRole_Add(cb_Role_Add);
            Emp.setRole_Delete(cb_Role_Delete);
            Emp.setRole_Edit(cb_Role_Edit);
            Emp.setRole_View(cb_Role_View);
            Emp.setSODetail_Add(cb_SODetail_Add);
            Emp.setSODetail_Delete(cb_SODetail_Delete);
            Emp.setSODetail_Edit(cb_SODetail_Edit);
            Emp.setSODetail_View(cb_SODetail_View);
            Emp.setRoom_Add(cb_Room_Add);
            Emp.setRoom_Delete(cb_Room_Delete);
            Emp.setRoom_Edit(cb_Room_Edit);
            Emp.setRoom_View(cb_Room_View);

//            set Emp User
            Emp.setEmployee_Add(cb_Employee_Add);
            Emp.setEmployee_Delete(cb_Employee_Delete);
            Emp.setEmployee_Edit(cb_Employee_Edit);
            Emp.setEmployee_View(cb_Employee_View);
            Emp.setUser_Add(cb_User_Add);
            Emp.setUser_Delete(cb_User_Delete);
            Emp.setUser_Edit(cb_User_Edit);
            Emp.setUser_View(cb_User_View);
//            set JFXButton

            HBox Action = new HBox();
            JFXButton btn_Edit = new JFXButton("Edit");
            btn_Edit.getStyleClass().add("btn-green-color");
            JFXButton btn_Update = new JFXButton("Update");
            btn_Update.getStyleClass().add("btn-warning-color-update");
            Action.setAlignment(Pos.CENTER);
            Action.setSpacing(10);
            Action.getChildren().addAll(btn_Edit, btn_Update);
            btn_Update.setDisable(true);
//            set action for btn
            btn_Edit.setOnAction((event) -> {
//                CheckIn CheckOut Department Customer Booking
                cb_CheckIn_Add.setDisable(false);
                cb_CheckIn_Delete.setDisable(false);
                cb_CheckIn_Edit.setDisable(false);
                cb_CheckIn_View.setDisable(false);
                cb_CheckOut_Add.setDisable(false);
                cb_CheckOut_Delete.setDisable(false);
                cb_CheckOut_Edit.setDisable(false);
                cb_CheckOut_View.setDisable(false);
                cb_Department_Edit.setDisable(false);
                cb_Department_View.setDisable(false);
                cb_Department_Add.setDisable(false);
                cb_Department_Delete.setDisable(false);
                cb_Customer_Add.setDisable(false);
                cb_Customer_Delete.setDisable(false);
                cb_Customer_Edit.setDisable(false);
                cb_Customer_View.setDisable(false);
                cb_Booking_Edit.setDisable(false);
                cb_Booking_View.setDisable(false);
                cb_Booking_Add.setDisable(false);
                cb_Booking_Delete.setDisable(false);
//                SODer SODetail Room Role
                cb_SODer_Add.setDisable(false);
                cb_SODer_Delete.setDisable(false);
                cb_SODer_Edit.setDisable(false);
                cb_SODer_View.setDisable(false);
                cb_SODetail_Edit.setDisable(false);
                cb_SODetail_View.setDisable(false);
                cb_SODetail_Add.setDisable(false);
                cb_SODetail_Delete.setDisable(false);
                cb_Room_Add.setDisable(false);
                cb_Room_Delete.setDisable(false);
                cb_Room_Edit.setDisable(false);
                cb_Room_View.setDisable(false);
                cb_Role_Edit.setDisable(false);
                cb_Role_View.setDisable(false);
                cb_Role_Add.setDisable(false);
                cb_Role_Delete.setDisable(false);
//                set Enable userlog stype employee user
                cb_UserLog_Add.setDisable(false);
                cb_UserLog_Delete.setDisable(false);
                cb_UserLog_Edit.setDisable(false);
                cb_UserLog_View.setDisable(false);
                cb_SType_Edit.setDisable(false);
                cb_SType_View.setDisable(false);
                cb_SType_Add.setDisable(false);
                cb_SType_Delete.setDisable(false);
                cb_User_Add.setDisable(false);
                cb_User_Delete.setDisable(false);
                cb_User_Edit.setDisable(false);
                cb_User_View.setDisable(false);
                cb_Employee_Edit.setDisable(false);
                cb_Employee_View.setDisable(false);
                cb_Employee_Add.setDisable(false);
                cb_Employee_Delete.setDisable(false);
                btn_Update.setDisable(false);
                btn_Edit.setDisable(true);
            });
            
//            action Update
            btn_Update.setOnAction((event) -> {
//                CheckIn CheckOut Department Customer Booking
                cb_CheckIn_Add.setDisable(true);
                cb_CheckIn_Delete.setDisable(true);
                cb_CheckIn_Edit.setDisable(true);
                cb_CheckIn_View.setDisable(true);
                cb_CheckOut_Add.setDisable(true);
                cb_CheckOut_Delete.setDisable(true);
                cb_CheckOut_Edit.setDisable(true);
                cb_CheckOut_View.setDisable(true);
                cb_Department_Edit.setDisable(true);
                cb_Department_View.setDisable(true);
                cb_Department_Add.setDisable(true);
                cb_Department_Delete.setDisable(true);
                cb_Customer_Add.setDisable(true);
                cb_Customer_Delete.setDisable(true);
                cb_Customer_Edit.setDisable(true);
                cb_Customer_View.setDisable(true);
                cb_Booking_Edit.setDisable(true);
                cb_Booking_View.setDisable(true);
                cb_Booking_Add.setDisable(true);
                cb_Booking_Delete.setDisable(true);

//                SODer SODetail Room Role
                cb_SODer_Add.setDisable(true);
                cb_SODer_Delete.setDisable(true);
                cb_SODer_Edit.setDisable(true);
                cb_SODer_View.setDisable(true);
                cb_SODetail_Edit.setDisable(true);
                cb_SODetail_View.setDisable(true);
                cb_SODetail_Add.setDisable(true);
                cb_SODetail_Delete.setDisable(true);
                cb_Room_Add.setDisable(true);
                cb_Room_Delete.setDisable(true);
                cb_Room_Edit.setDisable(true);
                cb_Room_View.setDisable(true);
                cb_Role_Edit.setDisable(true);
                cb_Role_View.setDisable(true);
                cb_Role_Add.setDisable(true);
                cb_Role_Delete.setDisable(true);
//                set Enable userlog stype employee user
                cb_UserLog_Add.setDisable(true);
                cb_UserLog_Delete.setDisable(true);
                cb_UserLog_Edit.setDisable(true);
                cb_UserLog_View.setDisable(true);
                cb_SType_Edit.setDisable(true);
                cb_SType_View.setDisable(true);
                cb_SType_Add.setDisable(true);
                cb_SType_Delete.setDisable(true);
                cb_User_Add.setDisable(true);
                cb_User_Delete.setDisable(true);
                cb_User_Edit.setDisable(true);
                cb_User_View.setDisable(true);
                cb_Employee_Edit.setDisable(true);
                cb_Employee_View.setDisable(true);
                cb_Employee_Add.setDisable(true);
                cb_Employee_Delete.setDisable(true);
                btn_Update.setDisable(true);
        
                try {
                    update_EmployeeDecentralization(
                            Emp.getEmployee_ID(), cb_Employee_View.isSelected(), cb_Employee_Add.isSelected(), cb_Employee_Edit.isSelected(), cb_Employee_Delete.isSelected(),
                            cb_User_View.isSelected(), cb_User_Add.isSelected(), cb_User_Edit.isSelected(), cb_User_Delete.isSelected(), cb_Booking_View.isSelected(), cb_Booking_Add.isSelected(), cb_Booking_Edit.isSelected(),cb_Booking_Delete.isSelected(),
                            cb_CheckIn_View.isSelected(), cb_CheckIn_Add.isSelected(), cb_CheckIn_Edit.isSelected(), cb_CheckIn_Delete.isSelected(),cb_CheckOut_View.isSelected(), cb_CheckOut_Add.isSelected(), cb_CheckOut_Edit.isSelected(), cb_CheckOut_Delete.isSelected(),
                            cb_Customer_View.isSelected(), cb_Customer_Add.isSelected(), cb_Customer_Edit.isSelected(), cb_Customer_Delete.isSelected(),cb_Department_View.isSelected(), cb_Department_Add.isSelected(), cb_Department_Edit.isSelected(), cb_Department_Delete.isSelected(),
                            cb_Role_View.isSelected(), cb_Role_Add.isSelected(), cb_Role_Edit.isSelected(), cb_Role_Delete.isSelected(), cb_Room_View.isSelected(), cb_Room_Add.isSelected(), cb_Room_Edit.isSelected(), cb_Room_Delete.isSelected(),
                            cb_SODetail_View.isSelected(), cb_SODetail_Add.isSelected(), cb_SODetail_Edit.isSelected(), cb_SODetail_Delete.isSelected(), cb_SODer_View.isSelected(), cb_SODer_Add.isSelected(), cb_SODer_Edit.isSelected(), cb_SODer_Delete.isSelected(),
                            cb_SType_View.isSelected(), cb_SType_Add.isSelected(), cb_SType_Edit.isSelected(), cb_SType_Delete.isSelected(), cb_UserLog_View.isSelected(), cb_UserLog_Add.isSelected(), cb_UserLog_Edit.isSelected(), cb_UserLog_Delete.isSelected()
                    );
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(DAOcheckRole.class.getName()).log(Level.SEVERE, null, ex);
                }
  
                btn_Edit.setDisable(false);
            });
            Emp.setDecentralizationAction(Action);
            list.add(Emp);
        }
        return list;
    }
//    Update Decentralization Employee

    public static void update_EmployeeDecentralization(String User, Boolean EmpView, Boolean EmpAdd, Boolean EmpEdit, Boolean EmpDelete, Boolean UserView, Boolean UserAdd, Boolean Useredit, Boolean UserDelete,
            Boolean Booking_View, Boolean Booking_Add, Boolean Booking_Edit, Boolean Booking_Delete, Boolean CheckIn_View, Boolean CheckIn_Add, Boolean CheckIn_Edit, Boolean CheckIn_Delete,
            Boolean CheckOut_View, Boolean CheckOut_Add, Boolean CheckOut_Edit, Boolean CheckOut_Delete, Boolean Customer_View, Boolean Customer_Add, Boolean Customer_Edit, Boolean Customer_Delete,
            Boolean Department_View, Boolean Department_Add, Boolean Department_Edit, Boolean Department_Delete, Boolean Role_View, Boolean Role_Add, Boolean Role_Edit, Boolean Role_Delete,
            Boolean Room_View, Boolean Room_Add, Boolean Room_Edit, Boolean Room_Delete, Boolean SODetail_View, Boolean SODetail_Add, Boolean SODetail_Edit, Boolean SODetail_Delete,
            Boolean SODer_View, Boolean SODer_Add, Boolean SODer_Edit, Boolean SODer_Delete, Boolean SType_View, Boolean SType_Add, Boolean SType_Edit, Boolean SType_Delete,
            Boolean UserLog_View, Boolean UserLog_Add, Boolean UserLog_Edit, Boolean UserLog_Delete) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        String ex = "Update Role Set Employee_View=?,Employee_Add=?,Employee_Edit=?,Employee_Delete=?,User_View=?,User_Add=?,User_Edit=?,User_Delete=?,"
                + "Booking_View=?,Booking_Add=?,Booking_Edit=?,Booking_Delete=?,CheckIn_View=?,CheckIn_Add=?,CheckIn_Edit=?,CheckIn_Delete=?,"
                + "CheckOut_View=?,CheckOut_Add=?,CheckOut_Edit=?,CheckOut_Delete=?,Customer_View=?,Customer_Add=?,Customer_Edit=?,Customer_Delete=?,"
                + "Department_View=?,Department_Add=?,Department_Edit=?,Department_Delete=?,Role_View=?,Role_Add=?,Role_Edit=?,Role_Delete=?,"
                + "Room_View=?,Room_Add=?,Room_Edit=?,Room_Delete=?,SODetail_View=?,SODetail_Add=?,SODetail_Edit=?,SODetail_Delete=?,"
                + "SODer_View=?,SODer_Add=?,SODer_Edit=?,SODer_Delete=?,SType_View=?,SType_Add=?,SType_Edit=?,SType_Delete=?,"
                + "UserLog_View=?,UserLog_Add=?,UserLog_Edit=?,UserLog_Delete=? where EmployeeID=?";
        PreparedStatement pts = connection.prepareStatement(ex);
//        nhom EMP
        pts.setBoolean(1, EmpView);
        pts.setBoolean(2, EmpAdd);
        pts.setBoolean(3, EmpEdit);
        pts.setBoolean(4, EmpDelete);
//        Nhom user
        pts.setBoolean(5, UserView);
        pts.setBoolean(6, UserAdd);
        pts.setBoolean(7, Useredit);
        pts.setBoolean(8, UserDelete);
//        Nhom booking
        pts.setBoolean(9, Booking_View);
        pts.setBoolean(10, Booking_Add);
        pts.setBoolean(11, Booking_Edit);
        pts.setBoolean(12, Booking_Delete);
//       Nhom CheckIn
        pts.setBoolean(13, CheckIn_View);
        pts.setBoolean(14, CheckIn_Add);
        pts.setBoolean(15, CheckIn_Edit);
        pts.setBoolean(16, CheckIn_Delete);
//        Nhom checkOut
        pts.setBoolean(17, CheckOut_View);
        pts.setBoolean(18, CheckOut_Add);
        pts.setBoolean(19, CheckOut_Edit);
        pts.setBoolean(20, CheckOut_Delete);
//        Nhom Customer
        pts.setBoolean(21, Customer_View);
        pts.setBoolean(22, Customer_Add);
        pts.setBoolean(23, Customer_Edit);
        pts.setBoolean(24, Customer_Delete);
//        Nhom Department
        pts.setBoolean(25, Department_View);
        pts.setBoolean(26, Department_Add);
        pts.setBoolean(27, Department_Edit);
        pts.setBoolean(28, Department_Delete);
//        Nhom Role
        pts.setBoolean(29, Role_View);
        pts.setBoolean(30, Role_Add);
        pts.setBoolean(31, Role_Edit);
        pts.setBoolean(32, Role_Delete);
//        Nhom Room
        pts.setBoolean(33, Room_View);
        pts.setBoolean(34, Room_Add);
        pts.setBoolean(35, Room_Edit);
        pts.setBoolean(36, Room_Delete);
//        Nhom SODetail
        pts.setBoolean(37, SODetail_View);
        pts.setBoolean(38, SODetail_Add);
        pts.setBoolean(39, SODetail_Edit);
        pts.setBoolean(40, SODetail_Delete);
//        Nhom SODer
        pts.setBoolean(41, SODer_View);
        pts.setBoolean(42, SODer_Add);
        pts.setBoolean(43, SODer_Edit);
        pts.setBoolean(44, SODer_Delete);
//        Nhom SType
        pts.setBoolean(45, SType_View);
        pts.setBoolean(46, SType_Add);
        pts.setBoolean(47, SType_Edit);
        pts.setBoolean(48, SType_Delete);
//        Nhom Userlog
        pts.setBoolean(49, UserLog_View);
        pts.setBoolean(50, UserLog_Add);
        pts.setBoolean(51, UserLog_Edit);
        pts.setBoolean(52, UserLog_Delete);
        pts.setString(53, User);
        pts.execute();
        pts.close();
        connection.close();
    }
}