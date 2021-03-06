/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private StringProperty customerID;
    private StringProperty customerFullName;
    private StringProperty roomID;
    private IntegerProperty serviceQuantity;
    private BigDecimal servicePriceTotal;
    private BigDecimal serviceDiscount;
    private BooleanProperty active;
    private JFXButton serviceRemoveButton;
    private LocalDateTime serviceOrderDate;
    private boolean serviceFinish;
    private JFXCheckBox checkBox_Finish;
    private boolean serviceCheckOut;

    public ServiceOrderDetail() {
        this.active = new SimpleBooleanProperty();
        this.orderID = new SimpleStringProperty();
        this.customerID = new SimpleStringProperty();
        this.customerFullName = new SimpleStringProperty();
        this.roomID = new SimpleStringProperty();
        this.serviceQuantity = new SimpleIntegerProperty();
        this.serviceRemoveButton = new JFXButton("Remove");
        this.checkBox_Finish = new JFXCheckBox();
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

    public LocalDateTime getServiceOrderDate() {
        return serviceOrderDate;
    }

    public void setServiceOrderDate(LocalDateTime serviceOrderDate) {
        this.serviceOrderDate = serviceOrderDate;
    }

    public final String getCustomerID() {
        return customerID.get();
    }

    public final void setCustomerID(String value) {
        customerID.set(value);
    }

    public StringProperty customerIDProperty() {
        return customerID;
    }

    public final String getRoomID() {
        return roomID.get();
    }

    public final void setRoomID(String value) {
        roomID.set(value);
    }

    public StringProperty roomIDProperty() {
        return roomID;
    }

    public boolean isServiceFinish() {
        return serviceFinish;
    }

    public void setServiceFinish(boolean serviceFinish) {
        this.serviceFinish = serviceFinish;
    }

    public JFXCheckBox getCheckBox_Finish() {
        return checkBox_Finish;
    }

    public void setCheckBox_Finish(JFXCheckBox checkBox_Finish) {
        this.checkBox_Finish = checkBox_Finish;
    }

    public boolean isServiceCheckOut() {
        return serviceCheckOut;
    }

    public void setServiceCheckOut(boolean serviceCheckOut) {
        this.serviceCheckOut = serviceCheckOut;
    }

    public final String getCustomerFullName() {
        return customerFullName.get();
    }

    public final void setCustomerFullName(String value) {
        customerFullName.set(value);
    }

    public StringProperty customerFullNameProperty() {
        return customerFullName;
    }

}
