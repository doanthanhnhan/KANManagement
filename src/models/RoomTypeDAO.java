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
public interface RoomTypeDAO {
    List<RoomType> getAllRoomType();
    void addRoomType(RoomType roomType);
    void editRoomType(RoomType roomType, Boolean active);
    void deleteRoomType(RoomType roomType);
}
