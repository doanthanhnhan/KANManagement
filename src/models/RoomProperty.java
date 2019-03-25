/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Doan Thanh Nhan
 */
public class RoomProperty {
    private String roomPropertyName;
    private Integer roomCount;

    public RoomProperty() {
    }

    public RoomProperty(String roomPropertyName, Integer roomCount) {
        this.roomPropertyName = roomPropertyName;
        this.roomCount = roomCount;
    }

    public String getRoomPropertyName() {
        return roomPropertyName;
    }

    public void setRoomPropertyName(String roomPropertyName) {
        this.roomPropertyName = roomPropertyName;
    }

    public Integer getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(Integer roomCount) {
        this.roomCount = roomCount;
    }    
}
