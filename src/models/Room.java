/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Doan Thanh Nhan
 */
public class Room {

    private StringProperty roomID;
    private StringProperty customerID;
    private StringProperty customerName;
    private StringProperty userName;
    private StringProperty roomType;
    private StringProperty roomPhoneNumber;
    private IntegerProperty roomOnFloor;
    private FloatProperty roomArea;
    private StringProperty roomStatus;
    private BooleanProperty roomClean;
    private BooleanProperty roomRepaired;
    private BooleanProperty roomInProgress;
    private IntegerProperty dayRemaining;
    private BooleanProperty Active;

    public Room() {
        this.roomID = new SimpleStringProperty();
        this.customerID = new SimpleStringProperty();
        this.customerName = new SimpleStringProperty();
        this.userName = new SimpleStringProperty();
        this.roomType = new SimpleStringProperty();
        this.roomPhoneNumber = new SimpleStringProperty();
        this.roomOnFloor = new SimpleIntegerProperty();
        this.roomArea = new SimpleFloatProperty();
        this.roomStatus = new SimpleStringProperty();
        this.roomClean = new SimpleBooleanProperty();
        this.roomRepaired = new SimpleBooleanProperty();
        this.roomInProgress = new SimpleBooleanProperty();
        this.dayRemaining = new SimpleIntegerProperty();
        this.Active = new SimpleBooleanProperty();
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

    public final String getCustomerID() {
        return customerID.get();
    }

    public final void setCustomerID(String value) {
        customerID.set(value);
    }

    public StringProperty customerIDProperty() {
        return customerID;
    }

    public final String getRoomType() {
        return roomType.get();
    }

    public final void setRoomType(String value) {
        roomType.set(value);
    }

    public StringProperty roomTypeProperty() {
        return roomType;
    }

    public final String getRoomPhoneNumber() {
        return roomPhoneNumber.get();
    }

    public final void setRoomPhoneNumber(String value) {
        roomPhoneNumber.set(value);
    }

    public StringProperty roomPhoneNumberProperty() {
        return roomPhoneNumber;
    }

    public final int getRoomOnFloor() {
        return roomOnFloor.get();
    }

    public final void setRoomOnFloor(int value) {
        roomOnFloor.set(value);
    }

    public IntegerProperty roomOnFloorProperty() {
        return roomOnFloor;
    }

    public final float getRoomArea() {
        return roomArea.get();
    }

    public final void setRoomArea(float value) {
        roomArea.set(value);
    }

    public FloatProperty roomAreaProperty() {
        return roomArea;
    }

    public final String getRoomStatus() {
        return roomStatus.get();
    }

    public final void setRoomStatus(String value) {
        roomStatus.set(value);
    }

    public StringProperty roomStatusProperty() {
        return roomStatus;
    }

    public final boolean isRoomClean() {
        return roomClean.get();
    }

    public final void setRoomClean(boolean value) {
        roomClean.set(value);
    }

    public BooleanProperty roomCleanProperty() {
        return roomClean;
    }

    public final boolean isRoomRepaired() {
        return roomRepaired.get();
    }

    public final void setRoomRepaired(boolean value) {
        roomRepaired.set(value);
    }

    public BooleanProperty roomRepairedProperty() {
        return roomRepaired;
    }

    public final boolean isRoomInProgress() {
        return roomInProgress.get();
    }

    public final void setRoomInProgress(boolean value) {
        roomInProgress.set(value);
    }

    public BooleanProperty roomInProgressProperty() {
        return roomInProgress;
    }

    public final String getCustomerName() {
        return customerName.get();
    }

    public final void setCustomerName(String value) {
        customerName.set(value);
    }

    public StringProperty customerNameProperty() {
        return customerName;
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

    public final boolean isActive() {
        return Active.get();
    }

    public final void setActive(boolean value) {
        Active.set(value);
    }

    public BooleanProperty ActiveProperty() {
        return Active;
    }

    public final int getDayRemaining() {
        return dayRemaining.get();
    }

    public final void setDayRemaining(int value) {
        dayRemaining.set(value);
    }

    public IntegerProperty dayRemainingProperty() {
        return dayRemaining;
    }

    @Override
    public String toString() {
        return "Room{" + "roomID=" + roomID + ", customerID=" + customerID + ", "
                + "customerName=" + customerName + ", userName=" + userName + ", roomType="
                + roomType + ", roomPhoneNumber=" + roomPhoneNumber + ", roomOnFloor="
                + roomOnFloor + ", roomArea=" + roomArea + ", roomStatus=" + roomStatus
                + ", roomClean=" + roomClean + ", roomRepaired=" + roomRepaired
                + ", roomInProgress=" + roomInProgress + ", dayRemaining="
                + dayRemaining + ", Active=" + Active + '}';
    }

}
