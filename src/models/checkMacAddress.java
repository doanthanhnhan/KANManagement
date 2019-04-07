/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Admin
 */
public class checkMacAddress {

    private IntegerProperty ID;
    private StringProperty MACAddress;
    private IntegerProperty Times;
    private BooleanProperty checkMacAddress_ReActive;

    public boolean ischeckMacAddress_ReActive() {
        return checkMacAddress_ReActive.get();
    }

    public void setcheckMacAddress_ReActive(boolean checkMacAddress_ReActive) {
        this.checkMacAddress_ReActive = new SimpleBooleanProperty(checkMacAddress_ReActive);
    }

    public BooleanProperty checkMacAddress_ReActive() {
        return checkMacAddress_ReActive;
    }

    public Integer getTimes() {
        return Times.get();
    }

    public void setTimes(Integer Times) {
        this.Times = new SimpleIntegerProperty(Times);
    }

    public Integer getID() {
        return ID.get();
    }

    public void setID(Integer ID) {
        this.ID = new SimpleIntegerProperty(ID);
    }

    public String getMACAddress() {
        return MACAddress.get();
    }

    public void setMACAddress(String MACAddress) {
        this.MACAddress = new SimpleStringProperty(MACAddress);
    }

    public checkMacAddress() {
    }

    public checkMacAddress(Integer ID, String MACAddress, Integer Times, Boolean checkMacAddress_ReActive) {
        this.ID = new SimpleIntegerProperty(ID);
        this.MACAddress = new SimpleStringProperty(MACAddress);
        this.Times = new SimpleIntegerProperty(Times);
        this.checkMacAddress_ReActive = new SimpleBooleanProperty(checkMacAddress_ReActive);
    }
}
