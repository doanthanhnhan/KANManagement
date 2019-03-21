/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Doan Thanh Nhan
 */
public class ServiceOrderDetail {

    private StringProperty orderID;
    private StringProperty serviceID;
    private StringProperty userName;
    private IntegerProperty serviceQuantity;
    private FloatProperty servicePrice;
    private FloatProperty serviceDiscount;
    private BooleanProperty active;

    public ServiceOrderDetail() {
        this.active = new SimpleBooleanProperty();
        this.orderID = new SimpleStringProperty();
        this.serviceDiscount = new SimpleFloatProperty();
        this.serviceID = new SimpleStringProperty();
        this.servicePrice = new SimpleFloatProperty();
        this.serviceQuantity = new SimpleIntegerProperty();
        this.userName = new SimpleStringProperty();
    }

    public final String getOrderID() {
        return orderID.get();
    }

    public final void setOrderID(String value) {
        orderID.set(value);
    }

    public StringProperty orderIDProperty() {
        return orderID;
    }

    public final String getServiceID() {
        return serviceID.get();
    }

    public final void setServiceID(String value) {
        serviceID.set(value);
    }

    public StringProperty serviceIDProperty() {
        return serviceID;
    }

    public final String getUserName() {
        return userName.get();
    }

    public final void setUserName(String value) {
        userName.set(value);
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public final int getServiceQuantity() {
        return serviceQuantity.get();
    }

    public final void setServiceQuantity(int value) {
        serviceQuantity.set(value);
    }

    public IntegerProperty serviceQuantityProperty() {
        return serviceQuantity;
    }

    public final float getServiceDiscount() {
        return serviceDiscount.get();
    }

    public final void setServiceDiscount(float value) {
        serviceDiscount.set(value);
    }

    public FloatProperty serviceDiscountProperty() {
        return serviceDiscount;
    }

    public final boolean isActive() {
        return active.get();
    }

    public final void setActive(boolean value) {
        active.set(value);
    }

    public BooleanProperty activeProperty() {
        return active;
    }

    public final float getServicePrice() {
        return servicePrice.get();
    }

    public final void setServicePrice(float value) {
        servicePrice.set(value);
    }

    public FloatProperty servicePriceProperty() {
        return servicePrice;
    }

}
