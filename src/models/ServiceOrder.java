/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Doan Thanh Nhan
 */
public class ServiceOrder {

    private final StringProperty serviceOrderID;
    private final StringProperty roomID;
    private final StringProperty serviceID;
    private final IntegerProperty serviceQuantity;
    private Calendar serviceOrderTime;
    private final StringProperty serviceNote;

    public ServiceOrder() {
        this.serviceOrderID = new SimpleStringProperty();
        this.serviceID = new SimpleStringProperty();
        this.roomID = new SimpleStringProperty();
        this.serviceNote = new SimpleStringProperty();
        this.serviceQuantity = new SimpleIntegerProperty();
        this.serviceOrderTime = new GregorianCalendar();
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

    public final String getServiceID() {
        return serviceID.get();
    }

    public final void setServiceID(String value) {
        serviceID.set(value);
    }

    public StringProperty serviceIDProperty() {
        return serviceID;
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

    public Calendar getServiceOrderTime() {
        return serviceOrderTime;
    }

    public void setServiceOrderTime(Calendar serviceOrderTime) {
        this.serviceOrderTime = serviceOrderTime;
    }

}
