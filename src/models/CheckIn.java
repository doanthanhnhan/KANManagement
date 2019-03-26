/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.LocalDateTime;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author ASUS
 */
public class CheckIn {

    public IntegerProperty NumberOfCustomer;
    public StringProperty CheckID, BookID, CusID, RoomID, User, CusPack, CheckType, DateIn, DateOut;

    public Integer getNumberOfCustomer() {
        return NumberOfCustomer.get();
    }

    public void setNumberOfCustomer(Integer NumberOfCustomer) {
        this.NumberOfCustomer = new SimpleIntegerProperty(NumberOfCustomer);
    }

    public String getCheckType() {
        return CheckType.get();
    }

    public void setCheckType(String CheckType) {
        this.CheckType = new SimpleStringProperty(CheckType);
    }

    public String getDateIn() {
        return DateIn.get();
    }

    public void setDateIn(String DateIn) {
        this.DateIn = new SimpleStringProperty(DateIn);
    }

    public String getDateOut() {
        return DateOut.get();
    }

    public void setDateOut(String DateOut) {
        this.DateOut = new SimpleStringProperty(DateOut);
    }

    public String getRoomID() {
        return RoomID.get();
    }

    public void setRoomID(String RoomID) {
        this.RoomID = new SimpleStringProperty(RoomID);
    }

    public String getCheckID() {
        return CheckID.get();
    }

    public void setCheckID(String CheckID) {
        this.CheckID = new SimpleStringProperty(CheckID);
    }

    public String getCusID() {
        return CusID.get();
    }

    public void setCusID(String CusID) {
        this.CusID = new SimpleStringProperty(CusID);
    }

    public String getBookID() {
        return BookID.get();
    }

    public void setBookID(String BookID) {
        this.BookID = new SimpleStringProperty(BookID);
    }

    public String getUser() {
        return User.get();
    }

    public void setUser(String User) {
        this.User = new SimpleStringProperty(User);
    }

    public String getCusPack() {
        return CusPack.get();
    }

    public void setCusPack(String CusPack) {
        this.CusPack = new SimpleStringProperty(CusPack);
    }

    public CheckIn() {
    }

    public CheckIn(String CheckID, String BookID, String CusID, String RoomID, String User, String CusPack, String CheckType, String DateIn, String DateOut, Integer NumberOfCustomer) {
        this.CheckID = new SimpleStringProperty(CheckID);
        this.BookID = new SimpleStringProperty(BookID);
        this.CusID = new SimpleStringProperty(CusID);
        this.RoomID = new SimpleStringProperty(RoomID);
        this.User = new SimpleStringProperty(User);
        this.CusPack = new SimpleStringProperty(CusPack);
        this.CheckType = new SimpleStringProperty(CheckType);
        this.DateIn = new SimpleStringProperty(DateIn);
        this.DateOut = new SimpleStringProperty(DateOut);
        this.NumberOfCustomer = new SimpleIntegerProperty(NumberOfCustomer);
    }
}
