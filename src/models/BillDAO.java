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
public interface BillDAO {
    List<Bill> getAllBills();    
    void addBill(Bill bill);
    void editBill(Bill bill, Boolean active);    
    void deleteBill(Bill bill);    
}
