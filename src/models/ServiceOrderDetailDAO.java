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
    void addServiceOrdersDetail(ServiceOrderDetail serviceOrderDetail);
    void editServiceOrdersDetail(ServiceOrderDetail serviceOrderDetail, Boolean active);
    void deleteServiceOrdersDetail(ServiceOrderDetail serviceOrderDetail);
}