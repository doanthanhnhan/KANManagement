/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;

/**
 *
 * @author Admin
 */
public class DecentralizationModel extends boolDecentralizationModel {
// Nhom Employee va User

    private CheckBox Employee_View;
    private CheckBox Employee_Add;
    private CheckBox Employee_Edit;
    private CheckBox Employee_Delete;
    private CheckBox User_View;
    private CheckBox User_Add;
    private CheckBox User_Edit;
    private CheckBox User_Delete;

//    Nhom Booking va CheckIn
    private CheckBox Booking_View;
    private CheckBox Booking_Add;
    private CheckBox Booking_Edit;
    private CheckBox Booking_Delete;
    private CheckBox CheckIn_View;
    private CheckBox CheckIn_Add;
    private CheckBox CheckIn_Edit;
    private CheckBox CheckIn_Delete;
    private HBox DecentralizationAction;
//    Nhom CheckOut va Customer
    private CheckBox CheckOut_View;
    private CheckBox CheckOut_Add;
    private CheckBox CheckOut_Edit;
    private CheckBox CheckOut_Delete;
    private CheckBox Customer_View;
    private CheckBox Customer_Add;
    private CheckBox Customer_Edit;
    private CheckBox Customer_Delete;
//    Nhom Department va Role
    private CheckBox Department_View;
    private CheckBox Department_Add;
    private CheckBox Department_Edit;
    private CheckBox Department_Delete;
    private CheckBox Role_View;
    private CheckBox Role_Add;
    private CheckBox Role_Edit;
    private CheckBox Role_Delete;
//    Nhom Room va SODetail
    private CheckBox Room_View;
    private CheckBox Room_Add;
    private CheckBox Room_Edit;
    private CheckBox Room_Delete;
    private CheckBox SODetail_View;
    private CheckBox SODetail_Add;
    private CheckBox SODetail_Edit;
    private CheckBox SODetail_Delete;
//    Nhom SODer va SType
    private CheckBox SODer_View;
    private CheckBox SODer_Add;
    private CheckBox SODer_Edit;
    private CheckBox SODer_Delete;
    private CheckBox SType_View;
    private CheckBox SType_Add;
    private CheckBox SType_Edit;
    private CheckBox SType_Delete;
    private CheckBox UserLog_View;
    private CheckBox UserLog_Add;
    private CheckBox UserLog_Edit;
    private CheckBox UserLog_Delete;
// Nhom Check Out

    public CheckBox getUserLog_View() {
        return UserLog_View;
    }

    public void setUserLog_View(CheckBox UserLog_View) {
        this.UserLog_View = UserLog_View;
    }

    public CheckBox getUserLog_Add() {
        return UserLog_Add;
    }

    public void setUserLog_Add(CheckBox UserLog_Add) {
        this.UserLog_Add = UserLog_Add;
    }

    public CheckBox getUserLog_Edit() {
        return UserLog_Edit;
    }

    public void setUserLog_Edit(CheckBox UserLog_Edit) {
        this.UserLog_Edit = UserLog_Edit;
    }

    public CheckBox getUserLog_Delete() {
        return UserLog_Delete;
    }

    public void setUserLog_Delete(CheckBox UserLog_Delete) {
        this.UserLog_Delete = UserLog_Delete;
    }

    public CheckBox getSODer_View() {
        return SODer_View;
    }

    public void setSODer_View(CheckBox SODer_View) {
        this.SODer_View = SODer_View;
    }

    public CheckBox getSODer_Add() {
        return SODer_Add;
    }

    public void setSODer_Add(CheckBox SODer_Add) {
        this.SODer_Add = SODer_Add;
    }

    public CheckBox getSODer_Edit() {
        return SODer_Edit;
    }

    public void setSODer_Edit(CheckBox SODer_Edit) {
        this.SODer_Edit = SODer_Edit;
    }

    public CheckBox getSODer_Delete() {
        return SODer_Delete;
    }

    public void setSODer_Delete(CheckBox SODer_Delete) {
        this.SODer_Delete = SODer_Delete;
    }

    public CheckBox getSType_View() {
        return SType_View;
    }

    public void setSType_View(CheckBox SType_View) {
        this.SType_View = SType_View;
    }

    public CheckBox getSType_Add() {
        return SType_Add;
    }

    public void setSType_Add(CheckBox SType_Add) {
        this.SType_Add = SType_Add;
    }

    public CheckBox getSType_Edit() {
        return SType_Edit;
    }

    public void setSType_Edit(CheckBox SType_Edit) {
        this.SType_Edit = SType_Edit;
    }

    public CheckBox getSType_Delete() {
        return SType_Delete;
    }

    public void setSType_Delete(CheckBox SType_Delete) {
        this.SType_Delete = SType_Delete;
    }

    public CheckBox getRoom_View() {
        return Room_View;
    }

    public void setRoom_View(CheckBox Room_View) {
        this.Room_View = Room_View;
    }

    public CheckBox getRoom_Add() {
        return Room_Add;
    }

    public void setRoom_Add(CheckBox Room_Add) {
        this.Room_Add = Room_Add;
    }

    public CheckBox getRoom_Edit() {
        return Room_Edit;
    }

    public void setRoom_Edit(CheckBox Room_Edit) {
        this.Room_Edit = Room_Edit;
    }

    public CheckBox getRoom_Delete() {
        return Room_Delete;
    }

    public void setRoom_Delete(CheckBox Room_Delete) {
        this.Room_Delete = Room_Delete;
    }

    public CheckBox getSODetail_View() {
        return SODetail_View;
    }

    public void setSODetail_View(CheckBox SODetail_View) {
        this.SODetail_View = SODetail_View;
    }

    public CheckBox getSODetail_Add() {
        return SODetail_Add;
    }

    public void setSODetail_Add(CheckBox SODetail_Add) {
        this.SODetail_Add = SODetail_Add;
    }

    public CheckBox getSODetail_Edit() {
        return SODetail_Edit;
    }

    public void setSODetail_Edit(CheckBox SODetail_Edit) {
        this.SODetail_Edit = SODetail_Edit;
    }

    public CheckBox getSODetail_Delete() {
        return SODetail_Delete;
    }

    public void setSODetail_Delete(CheckBox SODetail_Delete) {
        this.SODetail_Delete = SODetail_Delete;
    }

    public CheckBox getDepartment_View() {
        return Department_View;
    }

    public void setDepartment_View(CheckBox Department_View) {
        this.Department_View = Department_View;
    }

    public CheckBox getDepartment_Add() {
        return Department_Add;
    }

    public void setDepartment_Add(CheckBox Department_Add) {
        this.Department_Add = Department_Add;
    }

    public CheckBox getDepartment_Edit() {
        return Department_Edit;
    }

    public void setDepartment_Edit(CheckBox Department_Edit) {
        this.Department_Edit = Department_Edit;
    }

    public CheckBox getDepartment_Delete() {
        return Department_Delete;
    }

    public void setDepartment_Delete(CheckBox Department_Delete) {
        this.Department_Delete = Department_Delete;
    }

    public CheckBox getRole_View() {
        return Role_View;
    }

    public void setRole_View(CheckBox Role_View) {
        this.Role_View = Role_View;
    }

    public CheckBox getRole_Add() {
        return Role_Add;
    }

    public void setRole_Add(CheckBox Role_Add) {
        this.Role_Add = Role_Add;
    }

    public CheckBox getRole_Edit() {
        return Role_Edit;
    }

    public void setRole_Edit(CheckBox Role_Edit) {
        this.Role_Edit = Role_Edit;
    }

    public CheckBox getRole_Delete() {
        return Role_Delete;
    }

    public void setRole_Delete(CheckBox Role_Delete) {
        this.Role_Delete = Role_Delete;
    }

    public CheckBox getCheckOut_View() {
        return CheckOut_View;
    }

    public void setCheckOut_View(CheckBox CheckOut_View) {
        this.CheckOut_View = CheckOut_View;
    }

    public CheckBox getCheckOut_Add() {
        return CheckOut_Add;
    }

    public void setCheckOut_Add(CheckBox CheckOut_Add) {
        this.CheckOut_Add = CheckOut_Add;
    }

    public CheckBox getCheckOut_Edit() {
        return CheckOut_Edit;
    }

    public void setCheckOut_Edit(CheckBox CheckOut_Edit) {
        this.CheckOut_Edit = CheckOut_Edit;
    }

    public CheckBox getCheckOut_Delete() {
        return CheckOut_Delete;
    }

    public void setCheckOut_Delete(CheckBox CheckOut_Delete) {
        this.CheckOut_Delete = CheckOut_Delete;
    }
// Nhom Customer

    public CheckBox getCustomer_View() {
        return Customer_View;
    }

    public void setCustomer_View(CheckBox Customer_View) {
        this.Customer_View = Customer_View;
    }

    public CheckBox getCustomer_Add() {
        return Customer_Add;
    }

    public void setCustomer_Add(CheckBox Customer_Add) {
        this.Customer_Add = Customer_Add;
    }

    public CheckBox getCustomer_Edit() {
        return Customer_Edit;
    }

    public void setCustomer_Edit(CheckBox Customer_Edit) {
        this.Customer_Edit = Customer_Edit;
    }

    public CheckBox getCustomer_Delete() {
        return Customer_Delete;
    }

    public void setCustomer_Delete(CheckBox Customer_Delete) {
        this.Customer_Delete = Customer_Delete;
    }

//Nhom Booking
    public CheckBox getBooking_View() {
        return Booking_View;
    }

    public void setBooking_View(CheckBox Booking_View) {
        this.Booking_View = Booking_View;
    }

    public CheckBox getBooking_Add() {
        return Booking_Add;
    }

    public void setBooking_Add(CheckBox Booking_Add) {
        this.Booking_Add = Booking_Add;
    }

    public CheckBox getBooking_Edit() {
        return Booking_Edit;
    }

    public void setBooking_Edit(CheckBox Booking_Edit) {
        this.Booking_Edit = Booking_Edit;
    }

    public CheckBox getBooking_Delete() {
        return Booking_Delete;
    }

    public void setBooking_Delete(CheckBox Booking_Delete) {
        this.Booking_Delete = Booking_Delete;
    }
//Nhom CheckIn

    public CheckBox getCheckIn_View() {
        return CheckIn_View;
    }

    public void setCheckIn_View(CheckBox CheckIn_View) {
        this.CheckIn_View = CheckIn_View;
    }

    public CheckBox getCheckIn_Add() {
        return CheckIn_Add;
    }

    public void setCheckIn_Add(CheckBox CheckIn_Add) {
        this.CheckIn_Add = CheckIn_Add;
    }

    public CheckBox getCheckIn_Edit() {
        return CheckIn_Edit;
    }

    public void setCheckIn_Edit(CheckBox CheckIn_Edit) {
        this.CheckIn_Edit = CheckIn_Edit;
    }

    public CheckBox getCheckIn_Delete() {
        return CheckIn_Delete;
    }

    public void setCheckIn_Delete(CheckBox CheckIn_Delete) {
        this.CheckIn_Delete = CheckIn_Delete;
    }

    public HBox getDecentralizationAction() {
        return DecentralizationAction;
    }

    public void setDecentralizationAction(HBox DecentralizationAction) {
        this.DecentralizationAction = DecentralizationAction;
    }

// Nhom User
    public CheckBox getUser_Delete() {
        return User_Delete;
    }

    public void setUser_Delete(CheckBox User_Delete) {
        this.User_Delete = User_Delete;
    }

    public CheckBox getUser_Edit() {
        return User_Edit;
    }

    public void setUser_Edit(CheckBox User_Edit) {
        this.User_Edit = User_Edit;
    }

    public CheckBox getUser_Add() {
        return User_Add;
    }

    public void setUser_Add(CheckBox User_Add) {
        this.User_Add = User_Add;
    }

    public CheckBox getUser_View() {
        return User_View;
    }

    public void setUser_View(CheckBox User_View) {
        this.User_View = User_View;
    }

//Nhom Employee
    public CheckBox getEmployee_Delete() {
        return Employee_Delete;
    }

    public void setEmployee_Delete(CheckBox Employee_Delete) {
        this.Employee_Delete = Employee_Delete;
    }

    public CheckBox getEmployee_Edit() {
        return Employee_Edit;
    }

    public void setEmployee_Edit(CheckBox Employee_Edit) {
        this.Employee_Edit = Employee_Edit;
    }

    public CheckBox getEmployee_Add() {
        return Employee_Add;
    }

    public void setEmployee_Add(CheckBox Employee_Add) {
        this.Employee_Add = Employee_Add;
    }

    public CheckBox getEmployee_View() {
        return Employee_View;
    }

    public void setEmployee_View(CheckBox Employee_View) {
        this.Employee_View = Employee_View;
    }

}
