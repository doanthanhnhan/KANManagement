/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Doan Thanh Nhan
 */
public class CheckOut {

    private StringProperty checkOutID;
    private StringProperty checkInID;
    private StringProperty customerID;
    private StringProperty roomID;
    private StringProperty userName;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private StringProperty customerPayment;
    private BigDecimal customerBill;
    private BigDecimal discount;
    private BigDecimal tax;

    public CheckOut() {
        this.checkOutID = new SimpleStringProperty();
        this.checkInID = new SimpleStringProperty();
        this.customerID = new SimpleStringProperty();
        this.roomID = new SimpleStringProperty();
        this.userName = new SimpleStringProperty();
        this.customerPayment = new SimpleStringProperty();        
    }

    public final String getCheckOutID() {
        return checkOutID.get();
    }

    public final void setCheckOutID(String value) {
        checkOutID.set(value);
    }

    public StringProperty checkOutIDProperty() {
        return checkOutID;
    }

    public final String getCheckInID() {
        return checkInID.get();
    }

    public final void setCheckInID(String value) {
        checkInID.set(value);
    }

    public StringProperty checkInIDProperty() {
        return checkInID;
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

    public final String getUserName() {
        return userName.get();
    }

    public final void setUserName(String value) {
        userName.set(value);
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public final String getCustomerPayment() {
        return customerPayment.get();
    }

    public final void setCustomerPayment(String value) {
        customerPayment.set(value);
    }

    public StringProperty customerPaymentProperty() {
        return customerPayment;
    }

    public LocalDateTime getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDateTime checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDateTime getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDateTime checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public BigDecimal getCustomerBill() {
        return customerBill;
    }

    public void setCustomerBill(BigDecimal customerBill) {
        this.customerBill = customerBill;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

}
