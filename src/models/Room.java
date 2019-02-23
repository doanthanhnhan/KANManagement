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
    private StringProperty roomType;
    private StringProperty roomPhoneNumber;
    private IntegerProperty roomOnFloor;
    private FloatProperty roomArea;
    private StringProperty roomStatus;
    private BooleanProperty roomClean;
    private BooleanProperty roomRepaired;
    private BooleanProperty roomInProgress;

    public Room() {
        this.roomID = new SimpleStringProperty();
        this.roomType = new SimpleStringProperty();
        this.roomPhoneNumber = new SimpleStringProperty();
        this.roomOnFloor = new SimpleIntegerProperty();
        this.roomArea = new SimpleFloatProperty();
        this.roomStatus = new SimpleStringProperty();
        this.roomClean = new SimpleBooleanProperty();
        this.roomRepaired = new SimpleBooleanProperty();
        this.roomInProgress = new SimpleBooleanProperty();                
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

    @Override
    public String toString() {
        return "Room{" + "roomID=" + roomID + ", roomType=" + roomType + ", roomPhoneNumber=" + roomPhoneNumber + ", roomOnFloor=" + roomOnFloor + ", roomArea=" + roomArea + ", roomStatus=" + roomStatus + ", roomClean=" + roomClean + ", roomRepaired=" + roomRepaired + ", roomInProgress=" + roomInProgress + '}';
    }  
    
}
