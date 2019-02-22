/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Doan Thanh Nhan
 */
public class RoomDAOImpl implements RoomDAO {

    @Override
    public ObservableList<Room> getAllRoom() {
        String sql = "SELECT * FROM Rooms";
        ObservableList<Room> listRooms = FXCollections.observableArrayList();
//        try {
//            try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
//                while (rs.next()) {
////                    listRooms.add(new Room(rs.getString("roomID"), rs.getString("RoomType"), rs.getString("PhoneNumber"), rs.getInt("RoomOnFloor"), rs.getDouble("RoomArea"),                        rs.getString("RoomStatus"),
////                            rs.getBoolean("Clean"),
////                            rs.getBoolean("Repaired"),
////                            rs.getBoolean("InProgress")));
//                }
//            }
//        } catch (ClassNotFoundException | SQLException ex) {
//            Logger.getLogger(RoomDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return listRooms;
    }

    @Override
    public void addRoom(Room room) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editRoom(Room room) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteRoom(Room room) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void main(String[] args) {
        RoomDAOImpl roomDAOImpl = new RoomDAOImpl();
        roomDAOImpl.getAllRoom();
    }
}
