/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.connectDB;

/**
 *
 * @author Doan Thanh Nhan
 */
public class RoleDAOImpl implements RoleDAO {

    @Override
    public boolDecentralizationModel getEmployeeRole(String EmployeeID) {
        String sql = "SELECT * FROM view_UserRole WHERE EmployeeID LIKE '" + EmployeeID +"'";
        boolDecentralizationModel userRole = new boolDecentralizationModel();

        try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {                
                userRole.setEmployee_ID(rs.getString("EmployeeID"));
                userRole.setFirst_Name(rs.getNString("EmployeeFirstName"));
                userRole.setMid_Name(rs.getNString("EmployeeMidName"));
                userRole.setLast_Name(rs.getNString("EmployeeLastName"));
                //01.BOOKING
                userRole.setcheckBooking_Add(rs.getBoolean("Booking_Add"));
                userRole.setcheckBooking_Delete(rs.getBoolean("Booking_Delete"));
                userRole.setcheckBooking_Edit(rs.getBoolean("Booking_Edit"));
                userRole.setcheckBooking_View(rs.getBoolean("Booking_View"));
                //02.CHECK IN
                userRole.setcheckCheckIn_Add(rs.getBoolean("CheckIn_Add"));
                userRole.setcheckCheckIn_Delete(rs.getBoolean("CheckIn_Delete"));
                userRole.setcheckCheckIn_Edit(rs.getBoolean("CheckIn_Edit"));
                userRole.setcheckCheckIn_View(rs.getBoolean("CheckIn_View"));
                //03.CHECK OUT
                userRole.setcheckCheckOut_Add(rs.getBoolean("CheckOut_Add"));
                userRole.setcheckCheckOut_Delete(rs.getBoolean("CheckOut_Delete"));
                userRole.setcheckCheckOut_Edit(rs.getBoolean("CheckOut_Edit"));
                userRole.setcheckCheckOut_View(rs.getBoolean("CheckOut_View"));
                //04.CUSTOMER
                userRole.setcheckCustomer_Add(rs.getBoolean("Customer_Add"));
                userRole.setcheckCustomer_Delete(rs.getBoolean("Customer_Delete"));
                userRole.setcheckCustomer_Edit(rs.getBoolean("Customer_Edit"));
                userRole.setcheckCustomer_View(rs.getBoolean("Customer_View"));
                //05.DEPARTMENT
                userRole.setcheckDepartment_Add(rs.getBoolean("Department_Add"));
                userRole.setcheckDepartment_Delete(rs.getBoolean("Department_Delete"));
                userRole.setcheckDepartment_Edit(rs.getBoolean("Department_Edit"));
                userRole.setcheckDepartment_View(rs.getBoolean("Department_View"));
                //06.EMPLOYEE
                userRole.setcheckEmployee_Add(rs.getBoolean("Employee_Add"));
                userRole.setcheckEmployee_Delete(rs.getBoolean("Employee_Delete"));
                userRole.setcheckEmployee_Edit(rs.getBoolean("Employee_Edit"));
                userRole.setcheckEmployee_View(rs.getBoolean("Employee_View"));
                //07.ROLE
                userRole.setcheckRole_Add(rs.getBoolean("Role_Add"));
                userRole.setcheckRole_Delete(rs.getBoolean("Role_Delete"));
                userRole.setcheckRole_Edit(rs.getBoolean("Role_Edit"));
                userRole.setcheckRole_View(rs.getBoolean("Role_View"));
                //08.ROOM
                userRole.setcheckRoom_Add(rs.getBoolean("Room_Add"));
                userRole.setcheckRoom_Delete(rs.getBoolean("Room_Delete"));
                userRole.setcheckRoom_Edit(rs.getBoolean("Room_Edit"));
                userRole.setcheckRoom_View(rs.getBoolean("Room_View"));
                //09.SERVICES ORDERS
                userRole.setcheckSODer_Add(rs.getBoolean("SODer_Add"));
                userRole.setcheckSODer_Delete(rs.getBoolean("SODer_Delete"));
                userRole.setcheckSODer_Edit(rs.getBoolean("SODer_Edit"));
                userRole.setcheckSODer_View(rs.getBoolean("SODer_View"));
                //10.SERVICES ORDERS DETAILS
                userRole.setcheckSODetail_Add(rs.getBoolean("SODetail_Add"));
                userRole.setcheckSODetail_Delete(rs.getBoolean("SODetail_Delete"));
                userRole.setcheckSODetail_Edit(rs.getBoolean("SODetail_Edit"));
                userRole.setcheckSODetail_View(rs.getBoolean("SODetail_View"));
                //11.SEVICE TYPE
                userRole.setcheckSType_Add(rs.getBoolean("SType_Add"));
                userRole.setcheckSType_Delete(rs.getBoolean("SType_Delete"));
                userRole.setcheckSType_Edit(rs.getBoolean("SType_Edit"));
                userRole.setcheckSType_View(rs.getBoolean("SType_View"));
                //12.USER LOG
                userRole.setcheckUserLog_Add(rs.getBoolean("UserLog_Add"));
                userRole.setcheckUserLog_Delete(rs.getBoolean("UserLog_Delete"));
                userRole.setcheckUserLog_Edit(rs.getBoolean("UserLog_Edit"));
                userRole.setcheckUserLog_View(rs.getBoolean("UserLog_View"));
                //13.USER
                userRole.setcheckUser_Add(rs.getBoolean("User_Add"));
                userRole.setcheckUser_Delete(rs.getBoolean("User_Delete"));
                userRole.setcheckUser_Edit(rs.getBoolean("User_Edit"));
                userRole.setcheckUser_View(rs.getBoolean("User_View"));
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ServiceTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return userRole;
    }

}
