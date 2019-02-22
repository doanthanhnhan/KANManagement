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
    List<Room> getAllRoom();
    void addRoom(Room room);
    void editRoom(Room room);
    void deleteRoom(Room room);
}
