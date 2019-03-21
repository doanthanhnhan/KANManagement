/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.jfoenix.controls.JFXButton;
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
public class ServiceOrderDetail extends ServiceType {

    private StringProperty orderID;
    private IntegerProperty serviceQuantity;
    private FloatProperty servicePriceTotal;
    private FloatProperty serviceDiscount;
    private BooleanProperty active;
    private JFXButton serviceRemoveButton;

    public ServiceOrderDetail() {
        this.active = new SimpleBooleanProperty();
        this.orderID = new SimpleStringProperty();
        this.serviceDiscount = new SimpleFloatProperty();
        this.servicePriceTotal = new SimpleFloatProperty();
        this.serviceQuantity = new SimpleIntegerProperty();
        this.serviceRemoveButton = new JFXButton("Remove");
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

    public final float getServicePriceTotal() {
        return servicePriceTotal.get();
    }

    public final void setServicePriceTotal(float value) {
        servicePriceTotal.set(value);
    }

    public FloatProperty servicePriceTotalProperty() {
        return servicePriceTotal;
    }

    public JFXButton getServiceRemoveButton() {
        return serviceRemoveButton;
    }

    public void setServiceRemoveButton(JFXButton serviceRemoveButton) {
        this.serviceRemoveButton = serviceRemoveButton;
    }

}
