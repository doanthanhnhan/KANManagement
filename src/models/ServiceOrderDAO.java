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
public interface ServiceOrderDAO {

    List<ServiceOrder> getAllServiceOrders();

    void addServiceOrder(ServiceOrder serviceOrder);

    void editServiceOrder(ServiceOrder serviceOrder, Boolean active);

    void updateServiceFinish(boolean finish, String serviceOrderID);

    void updateServiceCheckOut(boolean checkout, String serviceOrderID);

    void update_CheckIn_SO_CheckOut(String checkInID);
    
    boolean check_For_Delete_Order(String serviceOrderID);

    void deleteServiceOrder(ServiceOrder serviceOrder);
}
