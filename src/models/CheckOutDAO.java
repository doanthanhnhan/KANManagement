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
public interface CheckOutDAO {
    List<CheckOut> getAllCheckOut();
    List<String> getAllCheckOutID();
    void addCheckOut(CheckOut checkOut);
    void editCheckOut(CheckOut checkOut, Boolean active);
    void deleteCheckOut(CheckOut checkOut);
}
