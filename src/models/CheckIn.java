/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.LocalDateTime;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author ASUS
 */
public class CheckIn {
    public int NumGuest;
    public StringProperty CheckID, BookID, CusID, RoomID, User, CusPack, CheckType;
    public LocalDateTime DateIn, DateOut;

    public final String getCheckID() {
        return CheckID.get();
    }

    public final void setCheckID(String value) {
        CheckID.set(value);
    }

    public StringProperty CheckIDProperty() {
        return CheckID;
    }

    public final String getBookID() {
        return BookID.get();
    }

    public final void setBookID(String value) {
        BookID.set(value);
    }

    public StringProperty BookIDProperty() {
        return BookID;
    }

    public final String getCusID() {
        return CusID.get();
    }

    public final void setCusID(String value) {
        CusID.set(value);
    }

    public StringProperty CusIDProperty() {
        return CusID;
    }

    public final String getRoomID() {
        return RoomID.get();
    }

    public final void setRoomID(String value) {
        RoomID.set(value);
    }

    public StringProperty RoomIDProperty() {
        return RoomID;
    }

    public final String getUser() {
        return User.get();
    }

    public final void setUser(String value) {
        User.set(value);
    }

    public StringProperty UserProperty() {
        return User;
    }

    public final String getCusPack() {
        return CusPack.get();
    }

    public final void setCusPack(String value) {
        CusPack.set(value);
    }

    public StringProperty CusPackProperty() {
        return CusPack;
    }

    public final String getCheckType() {
        return CheckType.get();
    }

    public final void setCheckType(String value) {
        CheckType.set(value);
    }

    public StringProperty CheckTypeProperty() {
        return CheckType;
    }
    
    public int getNumGuest() {
        return NumGuest;
    }

    public void setNumGuest(int NumGuest) {
        this.NumGuest = NumGuest;
    }

    public LocalDateTime getDateIn() {
        return DateIn;
    }

    public void setDateIn(LocalDateTime DateIn) {
        this.DateIn = DateIn;
    }

    public LocalDateTime getDateOut() {
        return DateOut;
    }

    public void setDateOut(LocalDateTime DateOut) {
        this.DateOut = DateOut;
    }



    public CheckIn() {
    }

    public CheckIn(String CheckID, String BookID, String CusID, String RoomID, String User, String CusPack, String CheckType) {
        this.CheckID = new SimpleStringProperty(CheckID);
        this.BookID = new SimpleStringProperty(BookID);
        this.CusID = new SimpleStringProperty(CusID);
        this.RoomID = new SimpleStringProperty(RoomID);
        this.User = new SimpleStringProperty(User);
        this.CusPack = new SimpleStringProperty(CusPack);
        this.CheckType = new SimpleStringProperty(CheckType);
    }
}
