/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.LocalDateTime;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author ASUS
 */
public class BookingInfo {
     public StringProperty BookID, CusID, RoomID, User, Note, Flight;
     public LocalDateTime Date;
     public BooleanProperty Drive;
     public int NumGuest;

    public BookingInfo() {
    }

    public BookingInfo(String BookID, String CusID, String RoomID, String User, String Note, String Flight, Boolean Drive, int NumGuest) {
        this.BookID = new SimpleStringProperty(BookID);
        this.CusID = new SimpleStringProperty(CusID);
        this.RoomID = new SimpleStringProperty(RoomID);
        this.User = new SimpleStringProperty(User);
        this.Note = new SimpleStringProperty(Note);
        this.Flight = new SimpleStringProperty(Flight);
        this.Drive = new SimpleBooleanProperty(Drive);
        this.NumGuest = NumGuest;
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

    public String getFlight() {
        return Flight.get();
    }

    public void setFlight(String Flight) {
        this.Flight = new SimpleStringProperty(Flight);
    }

    public LocalDateTime getDate() {
        return Date;
    }

    public void setDate(LocalDateTime Date) {
        this.Date = Date;
    }


    public Boolean getDrive() {
        return Drive.get();
    }

    public void setDrive(Boolean Drive) {
        this.Drive = new SimpleBooleanProperty(Drive);
    }

    public int getNumGuest() {
        return NumGuest;
    }

    public void setNumGuest(int NumGuest) {
        this.NumGuest = NumGuest;
    }
     
}
