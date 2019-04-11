/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author ASUS
 */
public class BookingInfo {

    public StringProperty BookID, CusID, RoomID, User, Note, Date, DateLeave, CusName;
    public IntegerProperty NumGuest;

    public BookingInfo() {
    }

    public BookingInfo(String BookID, String CusID, String RoomID, String User, String Note, Integer NumGuest, String Date, String DateLeave, String CusName) {
        this.BookID = new SimpleStringProperty(BookID);
        this.CusID = new SimpleStringProperty(CusID);
        this.RoomID = new SimpleStringProperty(RoomID);
        this.User = new SimpleStringProperty(User);
        this.Note = new SimpleStringProperty(Note);
        this.NumGuest = new SimpleIntegerProperty(NumGuest);
        this.Date = new SimpleStringProperty(Date);
        this.DateLeave = new SimpleStringProperty(DateLeave);
        this.CusName = new SimpleStringProperty(CusName);
    }

    public String getCusName() {
        return CusName.get();
    }

    public void setCusName(String CusName) {
        this.CusName = new SimpleStringProperty(CusName);
    }

    public String getDateLeave() {
        return DateLeave.get();
    }

    public void setDateLeave(String DateLeave) {
        this.DateLeave = new SimpleStringProperty(DateLeave);
    }

    public String getBookID() {
        return BookID.get();
    }

    public void setBookID(String BookID) {
        this.BookID = new SimpleStringProperty(BookID);
    }

    public String getCusID() {
        return CusID.get();
    }

    public void setCusID(String CusID) {
        this.CusID = new SimpleStringProperty(CusID);
    }

    public String getRoomID() {
        return RoomID.get();
    }

    public void setRoomID(String RoomID) {
        this.RoomID = new SimpleStringProperty(RoomID);
    }

    public String getUser() {
        return User.get();
    }

    public void setUser(String User) {
        this.User = new SimpleStringProperty(User);
    }

    public String getNote() {
        return Note.get();
    }

    public void setNote(String Note) {
        this.Note = new SimpleStringProperty(Note);
    }

    public String getDate() {
        return Date.get();
    }

    public void setDate(String Date) {
        this.Date = new SimpleStringProperty(Date);
    }

    public Integer getNumGuest() {
        return NumGuest.get();
    }

    public void setNumGuest(Integer NumGuest) {
        this.NumGuest = new SimpleIntegerProperty(NumGuest);
    }

}
