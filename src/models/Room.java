/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Doan Thanh Nhan
 */
public class Room {

    private StringProperty roomID;
    private StringProperty roomType;
    private StringProperty roomPhoneNumber;
    private StringProperty roomOnFloor;
    private IntegerProperty roomArea;
    private StringProperty roomStatus;
    private BooleanProperty roomClean;
    private BooleanProperty roomRepaired;
    private BooleanProperty roomInProgress;

    public Room() {
    }

    public Room(StringProperty roomID, StringProperty roomType, StringProperty roomPhoneNumber, StringProperty roomOnFloor, IntegerProperty roomArea, StringProperty roomStatus, BooleanProperty roomClean, BooleanProperty roomRepaired, BooleanProperty roomInProgress) {
        this.roomID = roomID;
        this.roomType = roomType;
        this.roomPhoneNumber = roomPhoneNumber;
        this.roomOnFloor = roomOnFloor;
        this.roomArea = roomArea;
        this.roomStatus = roomStatus;
        this.roomClean = roomClean;
        this.roomRepaired = roomRepaired;
        this.roomInProgress = roomInProgress;
    }

    public StringProperty getRoomID() {
        return roomID;
    }

    public void setRoomID(StringProperty roomID) {
        this.roomID = roomID;
    }

    public StringProperty getRoomType() {
        return roomType;
    }

    public void setRoomType(StringProperty roomType) {
        this.roomType = roomType;
    }

    public StringProperty getRoomPhoneNumber() {
        return roomPhoneNumber;
    }

    public void setRoomPhoneNumber(StringProperty roomPhoneNumber) {
        this.roomPhoneNumber = roomPhoneNumber;
    }

    public StringProperty getRoomOnFloor() {
        return roomOnFloor;
    }

    public void setRoomOnFloor(StringProperty roomOnFloor) {
        this.roomOnFloor = roomOnFloor;
    }

    public IntegerProperty getRoomArea() {
        return roomArea;
    }

    public void setRoomArea(IntegerProperty roomArea) {
        this.roomArea = roomArea;
    }

    public StringProperty getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(StringProperty roomStatus) {
        this.roomStatus = roomStatus;
    }

    public BooleanProperty getRoomClean() {
        return roomClean;
    }

    public void setRoomClean(BooleanProperty roomClean) {
        this.roomClean = roomClean;
    }

    public BooleanProperty getRoomRepaired() {
        return roomRepaired;
    }

    public void setRoomRepaired(BooleanProperty roomRepaired) {
        this.roomRepaired = roomRepaired;
    }

    public BooleanProperty getRoomInProgress() {
        return roomInProgress;
    }

    public void setRoomInProgress(BooleanProperty roomInProgress) {
        this.roomInProgress = roomInProgress;
    }

    @Override
    public String toString() {
        return "Room{" + "roomID=" + roomID + ", roomType=" + roomType + ", roomPhoneNumber=" + roomPhoneNumber + ", roomOnFloor=" + roomOnFloor + ", roomArea=" + roomArea + ", roomStatus=" + roomStatus + ", roomClean=" + roomClean + ", roomRepaired=" + roomRepaired + ", roomInProgress=" + roomInProgress + '}';
    }    

}
