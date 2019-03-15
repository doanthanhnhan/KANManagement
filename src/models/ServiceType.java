/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.Calendar;
import javafx.scene.image.ImageView;

/**
 *
 * @author Doan Thanh Nhan
 */
public class ServiceType {

    private String serviceID;
    private String userName;
    private String serviceName;
    private String serviceUnit;
    private Float servicePrice;
    private Integer serviceInventory;
    private LocalDateTime serviceInputDate;
    private Blob serviceImage;
    private ImageView imageView;
    private String serviceDescription;

    public ServiceType() {
    }

    public ServiceType(String serviceID, String serviceName, String serviceUnit, Float servicePrice, Blob serviceImage, ImageView imageView, String serviceDescription) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.serviceUnit = serviceUnit;
        this.servicePrice = servicePrice;
        this.serviceImage = serviceImage;
        this.imageView = imageView;
        this.serviceDescription = serviceDescription;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceUnit() {
        return serviceUnit;
    }

    public void setServiceUnit(String serviceUnit) {
        this.serviceUnit = serviceUnit;
    }

    public Float getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(Float servicePrice) {
        this.servicePrice = servicePrice;
    }

    public Integer getServiceInventory() {
        return serviceInventory;
    }

    public void setServiceInventory(Integer serviceInventory) {
        this.serviceInventory = serviceInventory;
    }

    public LocalDateTime getServiceInputDate() {
        return serviceInputDate;
    }

    public void setServiceInputDate(LocalDateTime serviceInputDate) {
        this.serviceInputDate = serviceInputDate;
    }

    public Blob getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(Blob serviceImage) {
        this.serviceImage = serviceImage;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

}
