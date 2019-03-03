/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Blob;
import javafx.scene.image.ImageView;

/**
 *
 * @author Doan Thanh Nhan
 */
public class ServiceType {
    private String serviceID;
    private String serviceName;
    private String serviceUnit;
    private Float servicePrice;
    private Blob serviceImage;
    private ImageView imageView;

    public ServiceType() {
    }

    public ServiceType(String serviceID, String serviceName, String serviceUnit, Float servicePrice, Blob serviceImage) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.serviceUnit = serviceUnit;
        this.servicePrice = servicePrice;
        this.serviceImage = serviceImage;
    }    

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
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
    
    
}
