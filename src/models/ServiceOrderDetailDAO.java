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
public interface ServiceOrderDetailDAO {

    List<ServiceOrderDetail> getAllServiceOrdersDetails();

    List<ServiceOrderDetail> get_All_Details_In_One_Order(String serviceOrderID);

    List<ServiceOrderDetail> get_All_Details_Of_CheckInRoom(String roomID, String checkInDate);

    void addServiceOrdersDetail(ServiceOrderDetail serviceOrderDetail);

    void editServiceOrdersDetail(ServiceOrderDetail serviceOrderDetail, Boolean active);

    void updateSODFinish(boolean finish, String serviceOrderID, String serviceID);

    void updateSODCheckOut(boolean checkout, String serviceOrderID, String serviceID);

    void update_CheckIN_SOD_CheckOut(String checkInID);
    
    boolean check_SOD_Per_SO_Finish(String serviceOrderID);

    void deleteServiceOrdersDetail(ServiceOrderDetail serviceOrderDetail);
}
