/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.math.BigDecimal;
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
    private BigDecimal servicePrice;
    private Integer serviceInventory;
    private Integer serviceImportQuantity;
    private Integer serviceExportQuantity;
    private LocalDateTime serviceImportDate;
    private LocalDateTime serviceExportDate;
    private Blob serviceImage;
    private ImageView imageView;
    private String serviceDescription;

    public ServiceType() {
    }

    public ServiceType(ServiceType serviceType) {
        this.serviceID = serviceType.getServiceID();
        this.userName = serviceType.getUserName();
        this.serviceName = serviceType.getServiceName();
        this.serviceUnit = serviceType.getServiceID();
        this.servicePrice = serviceType.getServicePrice();
        this.serviceInventory = serviceType.getServiceInventory();
        this.serviceImportQuantity = serviceType.getServiceImportQuantity();
        this.serviceExportQuantity = serviceType.getServiceExportQuantity();
        this.serviceImportDate = serviceType.getServiceImportDate();
        this.serviceExportDate = serviceType.getServiceExportDate();
        this.serviceImage = serviceType.getServiceImage();
        this.imageView = serviceType.getImageView();
        this.serviceDescription = serviceType.getServiceDescription();
    }

    public ServiceType(String serviceID, String userName, String serviceName, String serviceUnit, BigDecimal servicePrice, Integer serviceInventory, Integer serviceImportQuantity, Integer serviceExportQuantity, LocalDateTime serviceImportDate, LocalDateTime serviceExportDate, Blob serviceImage, ImageView imageView, String serviceDescription) {
        this.serviceID = serviceID;
        this.userName = userName;
        this.serviceName = serviceName;
        this.serviceUnit = serviceUnit;
        this.servicePrice = servicePrice;
        this.serviceInventory = serviceInventory;
        this.serviceImportQuantity = serviceImportQuantity;
        this.serviceExportQuantity = serviceExportQuantity;
        this.serviceImportDate = serviceImportDate;
        this.serviceExportDate = serviceExportDate;
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

    public BigDecimal getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(BigDecimal servicePrice) {
        this.servicePrice = servicePrice;
    }

    public Integer getServiceInventory() {
        return serviceInventory;
    }

    public void setServiceInventory(Integer serviceInventory) {
        this.serviceInventory = serviceInventory;
    }

    public Integer getServiceImportQuantity() {
        return serviceImportQuantity;
    }

    public void setServiceImportQuantity(Integer serviceImportQuantity) {
        this.serviceImportQuantity = serviceImportQuantity;
    }

    public Integer getServiceExportQuantity() {
        return serviceExportQuantity;
    }

    public void setServiceExportQuantity(Integer serviceExportQuantity) {
        this.serviceExportQuantity = serviceExportQuantity;
    }

    public LocalDateTime getServiceImportDate() {
        return serviceImportDate;
    }

    public void setServiceImportDate(LocalDateTime serviceImportDate) {
        this.serviceImportDate = serviceImportDate;
    }

    public LocalDateTime getServiceExportDate() {
        return serviceExportDate;
    }

    public void setServiceExportDate(LocalDateTime serviceExportDate) {
        this.serviceExportDate = serviceExportDate;
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
