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
 * @author Doan Thanh Nhan
 */
public class ServiceOrder {

    private StringProperty serviceOrderID;
    private StringProperty roomID;
    private StringProperty customerID;   
    private StringProperty userName;   
    private IntegerProperty serviceQuantity;
    private LocalDateTime serviceOrderTime;
    private StringProperty serviceNote;

    public ServiceOrder() {
        this.serviceOrderID = new SimpleStringProperty();        
        this.roomID = new SimpleStringProperty();
        this.customerID = new SimpleStringProperty();
        this.userName = new SimpleStringProperty();
        this.serviceNote = new SimpleStringProperty();
        this.serviceQuantity = new SimpleIntegerProperty();
    }

    public final String getServiceOrderID() {
        return serviceOrderID.get();
    }

    public final void setServiceOrderID(String value) {
        serviceOrderID.set(value);
    }

    public StringProperty serviceOrderIDProperty() {
        return serviceOrderID;
    }

    public final String getRoomID() {
        return roomID.get();
    }

    public final void setRoomID(String value) {
        roomID.set(value);
    }

    public StringProperty roomIDProperty() {
        return roomID;
    }   

    public final int getServiceQuantity() {
        return serviceQuantity.get();
    }

    public final void setServiceQuantity(int value) {
        serviceQuantity.set(value);
    }

    public IntegerProperty serviceQuantityProperty() {
        return serviceQuantity;
    }

    public final String getServiceNote() {
        return serviceNote.get();
    }

    public final void setServiceNote(String value) {
        serviceNote.set(value);
    }

    public StringProperty serviceNoteProperty() {
        return serviceNote;
    }

    public LocalDateTime getServiceOrderTime() {
        return serviceOrderTime;
    }

    public void setServiceOrderTime(LocalDateTime serviceOrderTime) {
        this.serviceOrderTime = serviceOrderTime;
    }

    public final String getCustomerID() {
        return customerID.get();
    }

    public final void setCustomerID(String value) {
        customerID.set(value);
    }

    public StringProperty customerIDProperty() {
        return customerID;
    }

    public final String getUserName() {
        return userName.get();
    }

    public final void setUserName(String value) {
        userName.set(value);
    }

    public StringProperty userNameProperty() {
        return userName;
    }

}
