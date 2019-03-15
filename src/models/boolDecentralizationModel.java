/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Admin
 */
public class boolDecentralizationModel {

    private StringProperty Employee_ID;
    private StringProperty First_Name;
    private StringProperty Mid_Name;
    private StringProperty Last_Name;
    private BooleanProperty checkEmployee_View;
    private BooleanProperty checkEmployee_Add;
    private BooleanProperty checkEmployee_Edit;
    private BooleanProperty checkEmployee_Delete;
    private BooleanProperty checkUser_View;
    private BooleanProperty checkUser_Add;
    private BooleanProperty checkUser_Edit;
    private BooleanProperty checkUser_Delete;
//    Nhom Booking va CheckIn
    private BooleanProperty checkBooking_View;
    private BooleanProperty checkBooking_Add;
    private BooleanProperty checkBooking_Edit;
    private BooleanProperty checkBooking_Delete;
    private BooleanProperty checkCheckIn_View;
    private BooleanProperty checkCheckIn_Add;
    private BooleanProperty checkCheckIn_Edit;
    private BooleanProperty checkCheckIn_Delete;

//    Nhom CheckOut va Customer
    private BooleanProperty checkCheckOut_View;
    private BooleanProperty checkCheckOut_Add;
    private BooleanProperty checkCheckOut_Edit;
    private BooleanProperty checkCheckOut_Delete;
    private BooleanProperty checkCustomer_View;
    private BooleanProperty checkCustomer_Add;
    private BooleanProperty checkCustomer_Edit;
    private BooleanProperty checkCustomer_Delete;
//    Nhom Department va Role
    private BooleanProperty checkDepartment_View;
    private BooleanProperty checkDepartment_Add;
    private BooleanProperty checkDepartment_Edit;
    private BooleanProperty checkDepartment_Delete;
    private BooleanProperty checkRole_View;
    private BooleanProperty checkRole_Add;
    private BooleanProperty checkRole_Edit;
    private BooleanProperty checkRole_Delete;
//    Nhom Room va SODetail
    private BooleanProperty checkRoom_View;
    private BooleanProperty checkRoom_Add;
    private BooleanProperty checkRoom_Edit;
    private BooleanProperty checkRoom_Delete;
    private BooleanProperty checkSODetail_View;
    private BooleanProperty checkSODetail_Add;
    private BooleanProperty checkSODetail_Edit;
    private BooleanProperty checkSODetail_Delete;
//    Nhom SODer va SType
    private BooleanProperty checkSODer_View;
    private BooleanProperty checkSODer_Add;
    private BooleanProperty checkSODer_Edit;
    private BooleanProperty checkSODer_Delete;
    private BooleanProperty checkSType_View;
    private BooleanProperty checkSType_Add;
    private BooleanProperty checkSType_Edit;
    private BooleanProperty checkSType_Delete;
    private BooleanProperty checkUserLog_View;
    private BooleanProperty checkUserLog_Add;
    private BooleanProperty checkUserLog_Edit;
    private BooleanProperty checkUserLog_Delete;

    //  Nhom Booking
    public boolean ischeckBooking_Delete() {
        return checkBooking_Delete.get();
    }

    public BooleanProperty checkBooking_Delete() {
        return checkBooking_Delete;
    }

    public void setcheckBooking_Delete(boolean checkBooking_Delete) {
        this.checkBooking_Delete = new SimpleBooleanProperty(checkBooking_Delete);
    }

    public boolean ischeckBooking_Edit() {
        return checkBooking_Edit.get();
    }

    public void setcheckBooking_Edit(boolean checkBooking_Edit) {
        this.checkBooking_Edit = new SimpleBooleanProperty(checkBooking_Edit);
    }

    public BooleanProperty checkBooking_Edit() {
        return checkBooking_Edit;
    }

    public boolean ischeckBooking_Add() {
        return checkBooking_Add.get();
    }

    public BooleanProperty checkBooking_Add() {
        return checkBooking_Add;
    }

    public void setcheckBooking_Add(boolean checkBooking_Add) {
        this.checkBooking_Add = new SimpleBooleanProperty(checkBooking_Add);
    }

    public boolean ischeckBooking_View() {
        return checkBooking_View.get();
    }

    public BooleanProperty checkBooking_View() {
        return checkBooking_View;
    }

    public void setcheckBooking_View(boolean checkBooking_View) {
        this.checkBooking_View = new SimpleBooleanProperty(checkBooking_View);
    }

    //  Nhom CheckIn
    public boolean ischeckCheckIn_Delete() {
        return checkCheckIn_Delete.get();
    }

    public BooleanProperty checkCheckIn_Delete() {
        return checkCheckIn_Delete;
    }

    public void setcheckCheckIn_Delete(boolean checkCheckIn_Delete) {
        this.checkCheckIn_Delete = new SimpleBooleanProperty(checkCheckIn_Delete);
    }

    public boolean ischeckCheckIn_Edit() {
        return checkCheckIn_Edit.get();
    }

    public void setcheckCheckIn_Edit(boolean checkCheckIn_Edit) {
        this.checkCheckIn_Edit = new SimpleBooleanProperty(checkCheckIn_Edit);
    }

    public BooleanProperty checkCheckIn_Edit() {
        return checkCheckIn_Edit;
    }

    public boolean ischeckCheckIn_Add() {
        return checkCheckIn_Add.get();
    }

    public BooleanProperty checkCheckIn_Add() {
        return checkCheckIn_Add;
    }

    public void setcheckCheckIn_Add(boolean checkCheckIn_Add) {
        this.checkCheckIn_Add = new SimpleBooleanProperty(checkCheckIn_Add);
    }

    public boolean ischeckCheckIn_View() {
        return checkCheckIn_View.get();
    }

    public BooleanProperty checkCheckIn_View() {
        return checkCheckIn_View;
    }

    public void setcheckCheckIn_View(boolean checkCheckIn_View) {
        this.checkCheckIn_View = new SimpleBooleanProperty(checkCheckIn_View);
    }

    //  Nhom Customer
    public boolean ischeckCheckOut_Delete() {
        return checkCheckOut_Delete.get();
    }

    public BooleanProperty checkCheckOut_Delete() {
        return checkCheckOut_Delete;
    }

    public void setcheckCheckOut_Delete(boolean checkCheckOut_Delete) {
        this.checkCheckOut_Delete = new SimpleBooleanProperty(checkCheckOut_Delete);
    }

    public boolean ischeckCheckOut_Edit() {
        return checkCheckOut_Edit.get();
    }

    public void setcheckCheckOut_Edit(boolean checkCheckOut_Edit) {
        this.checkCheckOut_Edit = new SimpleBooleanProperty(checkCheckOut_Edit);
    }

    public BooleanProperty checkCheckOut_Edit() {
        return checkCheckOut_Edit;
    }

    public boolean ischeckCheckOut_Add() {
        return checkCheckOut_Add.get();
    }

    public BooleanProperty checkCheckOut_Add() {
        return checkCheckOut_Add;
    }

    public void setcheckCheckOut_Add(boolean checkCheckOut_Add) {
        this.checkCheckOut_Add = new SimpleBooleanProperty(checkCheckOut_Add);
    }

    public boolean ischeckCheckOut_View() {
        return checkCheckOut_View.get();
    }

    public BooleanProperty checkCheckOut_View() {
        return checkCheckOut_View;
    }

    public void setcheckCheckOut_View(boolean checkCheckOut_View) {
        this.checkCheckOut_View = new SimpleBooleanProperty(checkCheckOut_View);
    }

    //  Nhom CheckOut
    public boolean ischeckCustomer_Delete() {
        return checkCustomer_Delete.get();
    }

    public BooleanProperty checkCustomer_Delete() {
        return checkCustomer_Delete;
    }

    public void setcheckCustomer_Delete(boolean checkCustomer_Delete) {
        this.checkCustomer_Delete = new SimpleBooleanProperty(checkCustomer_Delete);
    }

    public boolean ischeckCustomer_Edit() {
        return checkCustomer_Edit.get();
    }

    public void setcheckCustomer_Edit(boolean checkCustomer_Edit) {
        this.checkCustomer_Edit = new SimpleBooleanProperty(checkCustomer_Edit);
    }

    public BooleanProperty checkCustomer_Edit() {
        return checkCustomer_Edit;
    }

    public boolean ischeckCustomer_Add() {
        return checkCustomer_Add.get();
    }

    public BooleanProperty checkCustomer_Add() {
        return checkCustomer_Add;
    }

    public void setcheckCustomer_Add(boolean checkCustomer_Add) {
        this.checkCustomer_Add = new SimpleBooleanProperty(checkCustomer_Add);
    }

    public boolean ischeckCustomer_View() {
        return checkCustomer_View.get();
    }

    public BooleanProperty checkCustomer_View() {
        return checkCustomer_View;
    }

    public void setcheckCustomer_View(boolean checkCustomer_View) {
        this.checkCustomer_View = new SimpleBooleanProperty(checkCustomer_View);
    }

    //  Nhom Department
    public boolean ischeckDepartment_Delete() {
        return checkDepartment_Delete.get();
    }

    public BooleanProperty checkDepartment_Delete() {
        return checkDepartment_Delete;
    }

    public void setcheckDepartment_Delete(boolean checkDepartment_Delete) {
        this.checkDepartment_Delete = new SimpleBooleanProperty(checkDepartment_Delete);
    }

    public boolean ischeckDepartment_Edit() {
        return checkDepartment_Edit.get();
    }

    public void setcheckDepartment_Edit(boolean checkDepartment_Edit) {
        this.checkDepartment_Edit = new SimpleBooleanProperty(checkDepartment_Edit);
    }

    public BooleanProperty checkDepartment_Edit() {
        return checkDepartment_Edit;
    }

    public boolean ischeckDepartment_Add() {
        return checkDepartment_Add.get();
    }

    public BooleanProperty checkDepartment_Add() {
        return checkDepartment_Add;
    }

    public void setcheckDepartment_Add(boolean checkDepartment_Add) {
        this.checkDepartment_Add = new SimpleBooleanProperty(checkDepartment_Add);
    }

    public boolean ischeckDepartment_View() {
        return checkDepartment_View.get();
    }

    public BooleanProperty checkDepartment_View() {
        return checkDepartment_View;
    }

    public void setcheckDepartment_View(boolean checkDepartment_View) {
        this.checkDepartment_View = new SimpleBooleanProperty(checkDepartment_View);
    }

    //  Nhom Role
    public boolean ischeckRole_Delete() {
        return checkRole_Delete.get();
    }

    public BooleanProperty checkRole_Delete() {
        return checkRole_Delete;
    }

    public void setcheckRole_Delete(boolean checkRole_Delete) {
        this.checkRole_Delete = new SimpleBooleanProperty(checkRole_Delete);
    }

    public boolean ischeckRole_Edit() {
        return checkRole_Edit.get();
    }

    public void setcheckRole_Edit(boolean checkRole_Edit) {
        this.checkRole_Edit = new SimpleBooleanProperty(checkRole_Edit);
    }

    public BooleanProperty checkRole_Edit() {
        return checkRole_Edit;
    }

    public boolean ischeckRole_Add() {
        return checkRole_Add.get();
    }

    public BooleanProperty checkRole_Add() {
        return checkRole_Add;
    }

    public void setcheckRole_Add(boolean checkRole_Add) {
        this.checkRole_Add = new SimpleBooleanProperty(checkRole_Add);
    }

    public boolean ischeckRole_View() {
        return checkRole_View.get();
    }

    public BooleanProperty checkRole_View() {
        return checkRole_View;
    }

    public void setcheckRole_View(boolean checkRole_View) {
        this.checkRole_View = new SimpleBooleanProperty(checkRole_View);
    }

    //  Nhom Room
    public boolean ischeckRoom_Delete() {
        return checkRoom_Delete.get();
    }

    public BooleanProperty checkRoom_Delete() {
        return checkRoom_Delete;
    }

    public void setcheckRoom_Delete(boolean checkRoom_Delete) {
        this.checkRoom_Delete = new SimpleBooleanProperty(checkRoom_Delete);
    }

    public boolean ischeckRoom_Edit() {
        return checkRoom_Edit.get();
    }

    public void setcheckRoom_Edit(boolean checkRoom_Edit) {
        this.checkRoom_Edit = new SimpleBooleanProperty(checkRoom_Edit);
    }

    public BooleanProperty checkRoom_Edit() {
        return checkRoom_Edit;
    }

    public boolean ischeckRoom_Add() {
        return checkRoom_Add.get();
    }

    public BooleanProperty checkRoom_Add() {
        return checkRoom_Add;
    }

    public void setcheckRoom_Add(boolean checkRoom_Add) {
        this.checkRoom_Add = new SimpleBooleanProperty(checkRoom_Add);
    }

    public boolean ischeckRoom_View() {
        return checkRoom_View.get();
    }

    public BooleanProperty checkRoom_View() {
        return checkRoom_View;
    }

    public void setcheckRoom_View(boolean checkRoom_View) {
        this.checkRoom_View = new SimpleBooleanProperty(checkRoom_View);
    }
    //  Nhom SODetail

    public boolean ischeckSODetail_Delete() {
        return checkSODer_Delete.get();
    }

    public BooleanProperty checkSODetail_Delete() {
        return checkSODetail_Delete;
    }

    public void setcheckSODetail_Delete(boolean checkSODetail_Delete) {
        this.checkSODetail_Delete = new SimpleBooleanProperty(checkSODetail_Delete);
    }

    public boolean ischeckSODetail_Edit() {
        return checkSODetail_Edit.get();
    }

    public void setcheckSODetail_Edit(boolean checkSODetail_Edit) {
        this.checkSODetail_Edit = new SimpleBooleanProperty(checkSODetail_Edit);
    }

    public BooleanProperty checkSODetail_Edit() {
        return checkSODetail_Edit;
    }

    public boolean ischeckSODetail_Add() {
        return checkSODetail_Add.get();
    }

    public BooleanProperty checkSODetail_Add() {
        return checkSODetail_Add;
    }

    public void setcheckSODetail_Add(boolean checkSODetail_Add) {
        this.checkSODetail_Add = new SimpleBooleanProperty(checkSODetail_Add);
    }

    public boolean ischeckSODetail_View() {
        return checkSODetail_View.get();
    }

    public BooleanProperty checkSODetail_View() {
        return checkSODetail_View;
    }

    public void setcheckSODetail_View(boolean checkSODetail_View) {
        this.checkSODetail_View = new SimpleBooleanProperty(checkSODetail_View);
    }
    //  Nhom SOrder 

    public boolean ischeckSODer_Delete() {
        return checkSODer_Delete.get();
    }

    public BooleanProperty checkSODer_Delete() {
        return checkSODer_Delete;
    }

    public void setcheckSODer_Delete(boolean checkSODer_Delete) {
        this.checkSODer_Delete = new SimpleBooleanProperty(checkSODer_Delete);
    }

    public boolean ischeckSODer_Edit() {
        return checkSODer_Edit.get();
    }

    public void setcheckSODer_Edit(boolean checkSODer_Edit) {
        this.checkSODer_Edit = new SimpleBooleanProperty(checkSODer_Edit);
    }

    public BooleanProperty checkcheckSODer_Edit() {
        return checkSODer_Edit;
    }

    public boolean ischeckSODer_Add() {
        return checkSODer_Add.get();
    }

    public BooleanProperty checkSODer_Add() {
        return checkSODer_Add;
    }

    public void setcheckSODer_Add(boolean checkSODer_Add) {
        this.checkSODer_Add = new SimpleBooleanProperty(checkSODer_Add);
    }

    public boolean ischeckcheckSODer_View() {
        return checkSODer_View.get();
    }

    public BooleanProperty checkSODer_View() {
        return checkSODer_View;
    }

    public void setcheckSODer_View(boolean checkSODer_View) {
        this.checkSODer_View = new SimpleBooleanProperty(checkSODer_View);
    }
    //  Nhom SType  

    public boolean ischeckSType_Delete() {
        return checkSType_Delete.get();
    }

    public BooleanProperty checkSType_Delete() {
        return checkSType_Delete;
    }

    public void setcheckSType_Delete(boolean checkSType_Delete) {
        this.checkSType_Delete = new SimpleBooleanProperty(checkSType_Delete);
    }

    public boolean ischeckSType_Edit() {
        return checkSType_Edit.get();
    }

    public void setcheckSType_Edit(boolean checkSType_Edit) {
        this.checkSType_Edit = new SimpleBooleanProperty(checkSType_Edit);
    }

    public BooleanProperty checkcheckSType_Edit() {
        return checkSType_Edit;
    }

    public boolean ischeckSType_Add() {
        return checkSType_Add.get();
    }

    public BooleanProperty checkSType_Add() {
        return checkSType_Add;
    }

    public void setcheckSType_Add(boolean checkSType_Add) {
        this.checkSType_Add = new SimpleBooleanProperty(checkSType_Add);
    }

    public boolean ischeckSType_View() {
        return checkSType_View.get();
    }

    public BooleanProperty checkSType_View() {
        return checkSType_View;
    }

    public void setcheckSType_View(boolean checkSType_View) {
        this.checkSType_View = new SimpleBooleanProperty(checkSType_View);
    }
    //  Nhom UserLog  

    public boolean ischeckUserLog_Delete() {
        return checkUserLog_Delete.get();
    }

    public BooleanProperty checkUserLog_Delete() {
        return checkUserLog_Delete;
    }

    public void setcheckUserLog_Delete(boolean checkUserLog_Delete) {
        this.checkUserLog_Delete = new SimpleBooleanProperty(checkUserLog_Delete);
    }

    public boolean ischeckUserLog_Edit() {
        return checkUserLog_Edit.get();
    }

    public void setcheckUserLog_Edit(boolean checkUserLog_Edit) {
        this.checkUserLog_Edit = new SimpleBooleanProperty(checkUserLog_Edit);
    }

    public BooleanProperty checkUserLog_Edit() {
        return checkUserLog_Edit;
    }

    public boolean ischeckUserLog_Add() {
        return checkUserLog_Add.get();
    }

    public BooleanProperty checkUserLog_Add() {
        return checkUserLog_Add;
    }

    public void setcheckUserLog_Add(boolean checkUserLog_Add) {
        this.checkUserLog_Add = new SimpleBooleanProperty(checkUserLog_Add);
    }

    public boolean ischeckUserLog_View() {
        return checkUserLog_View.get();
    }

    public BooleanProperty checkUserLog_View() {
        return checkUserLog_View;
    }

    public void setcheckUserLog_View(boolean checkUserLog_View) {
        this.checkUserLog_View = new SimpleBooleanProperty(checkUserLog_View);
    }
//  Nhom User  

    public boolean ischeckUser_Delete() {
        return checkUser_Delete.get();
    }

    public BooleanProperty checkUser_Delete() {
        return checkUser_Delete;
    }

    public void setcheckUser_Delete(boolean checkUser_Delete) {
        this.checkUser_Delete = new SimpleBooleanProperty(checkUser_Delete);
    }

    public boolean ischeckUser_Edit() {
        return checkUser_Edit.get();
    }

    public void setcheckUser_Edit(boolean checkUser_Edit) {
        this.checkUser_Edit = new SimpleBooleanProperty(checkUser_Edit);
    }

    public BooleanProperty checkUser_Edit() {
        return checkUser_Edit;
    }

    public boolean ischeckUser_Add() {
        return checkUser_Add.get();
    }

    public BooleanProperty checkUser_Add() {
        return checkUser_Add;
    }

    public void setcheckUser_Add(boolean checkUser_Add) {
        this.checkUser_Add = new SimpleBooleanProperty(checkUser_Add);
    }

    public boolean ischeckUser_View() {
        return checkUser_View.get();
    }

    public BooleanProperty checkUser_View() {
        return checkUser_View;
    }

    public void setcheckUser_View(boolean checkUser_View) {
        this.checkUser_View = new SimpleBooleanProperty(checkUser_View);
    }
//  Nhom Employee

    public boolean ischeckEmployee_Delete() {
        return checkEmployee_Delete.get();
    }

    public void setcheckEmployee_Delete(boolean checkEmployee_Delete) {
        this.checkEmployee_Delete = new SimpleBooleanProperty(checkEmployee_Delete);
    }

    public BooleanProperty checkEmployee_Delete() {
        return checkEmployee_Delete;
    }

    public boolean ischeckEmployee_Edit() {
        return checkEmployee_Edit.get();
    }

    public void setcheckEmployee_Edit(boolean checkEmployee_Edit) {
        this.checkEmployee_Edit = new SimpleBooleanProperty(checkEmployee_Edit);
    }

    public BooleanProperty checkEmployee_Edit() {
        return checkEmployee_Edit;
    }

    public boolean ischeckEmployee_Add() {
        return checkEmployee_Add.get();
    }

    public void setcheckEmployee_Add(boolean checkEmployee_Add) {
        this.checkEmployee_Add = new SimpleBooleanProperty(checkEmployee_Add);
    }

    public BooleanProperty checkEmployee_Add() {
        return checkEmployee_Add;
    }

    public boolean ischeckEmployee_View() {
        return checkEmployee_View.get();
    }

    public void setcheckEmployee_View(boolean checkEmployee_View) {
        this.checkEmployee_View = new SimpleBooleanProperty(checkEmployee_View);
    }

    public BooleanProperty checkEmployee_View() {
        return checkEmployee_View;
    }

    public String getEmployee_ID() {
        return Employee_ID.get();
    }

    public void setEmployee_ID(String Employee_ID) {
        this.Employee_ID = new SimpleStringProperty(Employee_ID);
    }

    public String getFirst_Name() {
        return First_Name.get();
    }

    public void setFirst_Name(String First_Name) {
        this.First_Name = new SimpleStringProperty(First_Name);
    }

    public String getMid_Name() {
        return Mid_Name.get();
    }

    public void setMid_Name(String Mid_Name) {
        this.Mid_Name = new SimpleStringProperty(Mid_Name);
    }

    public String getLast_Name() {
        return Last_Name.get();
    }

    public void setLast_Name(String Last_Name) {
        this.Last_Name = new SimpleStringProperty(Last_Name);
    }

    public boolDecentralizationModel() {
    }

    public boolDecentralizationModel(String Employee_ID, String First_Name, String Mid_Name, String Last_Name, Boolean checkEmployee_View, Boolean checkUser_Delete,
            Boolean checkUser_Edit, Boolean checkUser_Add, Boolean checkUser_View, Boolean checkEmployee_Add, Boolean checkEmployee_Edit, Boolean checkEmployee_Delete) {
        this.Employee_ID = new SimpleStringProperty(Employee_ID);
        this.First_Name = new SimpleStringProperty(First_Name);
        this.Mid_Name = new SimpleStringProperty(Mid_Name);
        this.Last_Name = new SimpleStringProperty(Last_Name);
        this.checkEmployee_View = new SimpleBooleanProperty(checkEmployee_View);
        this.checkUser_Delete = new SimpleBooleanProperty(checkUser_Delete);
        this.checkUser_Edit = new SimpleBooleanProperty(checkUser_Edit);
        this.checkUser_Add = new SimpleBooleanProperty(checkUser_Add);
        this.checkUser_View = new SimpleBooleanProperty(checkUser_View);
        this.checkEmployee_Add = new SimpleBooleanProperty(checkEmployee_Add);
        this.checkEmployee_Edit = new SimpleBooleanProperty(checkEmployee_Edit);
        this.checkEmployee_Delete = new SimpleBooleanProperty(checkEmployee_Delete);
    }
}
