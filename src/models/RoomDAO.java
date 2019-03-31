/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.List;

/**
 *
 * @author Doan Thanh Nhan
 */
public interface RoomDAO {
    Room getRoom(String roomID);
    List<Room> getAllRoom();
    List<Room> getAllStatusRooms(String roomStatus);
    List<String> getAllRoomID();    
    List<String> getAll_Available_RoomID();    
    List<String> getAllStatusRoomID(String roomStatus);
    List<String> getAllCustomerID();
    List<String> getAllStatusCustomerID(String roomStatus);
    void addRoom(Room room);
    void editRoom(Room room, Boolean active);
    void editCheckInRoom(Room room, Boolean active);
    void editCheckOutRoom(Room room, Boolean active);
    void editBookingRoom(Room room, Boolean active);
    void editAfterCheckingRoom(Room room, Boolean active);
    void deleteRoom(Room room);
    void deleteRoomEX(RoomEX roomEX);
}
