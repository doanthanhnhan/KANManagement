/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.jfoenix.controls.JFXButton;
import java.math.BigDecimal;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
    private BigDecimal servicePriceTotal;
    private BigDecimal serviceDiscount;
    private BooleanProperty active;
    private JFXButton serviceRemoveButton;

    public ServiceOrderDetail() {
        this.active = new SimpleBooleanProperty();
        this.orderID = new SimpleStringProperty();
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

    public BigDecimal getServiceDiscount() {
        return serviceDiscount;
    }

    public void setServiceDiscount(BigDecimal serviceDiscount) {
        this.serviceDiscount = serviceDiscount;
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

    public BigDecimal getServicePriceTotal() {
        return servicePriceTotal;
    }

    public void setServicePriceTotal(BigDecimal servicePriceTotal) {
        this.servicePriceTotal = servicePriceTotal;
    }

    public JFXButton getServiceRemoveButton() {
        return serviceRemoveButton;
    }

    public void setServiceRemoveButton(JFXButton serviceRemoveButton) {
        this.serviceRemoveButton = serviceRemoveButton;
    }

}
