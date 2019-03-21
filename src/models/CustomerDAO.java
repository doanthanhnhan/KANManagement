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
public interface CustomerDAO {
    List<Customer> getAllCustomers();
    List<String> getAllCustomersID();
    void addCustomer(Customer customer);
    void editCustomer(Customer customer, Boolean active);
    void deleteCustomer(Customer customer);
}
