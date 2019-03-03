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
public interface ServiceTypeDAO {
    List<ServiceType> getAllServiceType();
    void addServiceType(ServiceType serviceType);
    void editServiceType(ServiceType serviceType);
    void deleteServiceType(ServiceType serviceType);
}
