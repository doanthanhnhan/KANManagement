/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.math.BigDecimal;
import java.sql.Blob;
import java.time.LocalDateTime;
import javafx.scene.image.ImageView;

/**
 *
 * @author Doan Thanh Nhan
 */
public class Bill {

    private String roomID;
    private String customerID;
    private String userName;
    private String checkInID;
    private String checkOutID;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private int noOfDay;
    private BigDecimal roomPrice;
    private BigDecimal roomCharge;
    private BigDecimal serviceCharge;
    private BigDecimal roomDiscount;
    private BigDecimal serviceDiscount;
    private BigDecimal customerDiscount;
    private BigDecimal totalBillAmount;
    private BigDecimal VATAmount;
    private BigDecimal payableAmount;
    private BigDecimal CustomerGive;
    private BigDecimal CustomerChange;
    private Blob billQRCode;
    private ImageView imageView_Bill_QR_Code;

    public Bill() {
    }

    public Bill(String roomID, String customerID, String userName, String checkInID, String checkOutID, LocalDateTime checkInDate, LocalDateTime checkOutDate, int noOfDay, BigDecimal roomPrice, BigDecimal roomCharge, BigDecimal serviceCharge, BigDecimal roomDiscount, BigDecimal serviceDiscount, BigDecimal customerDiscount, BigDecimal totalBillAmount, BigDecimal VATAmount, BigDecimal payableAmount, BigDecimal CustomerGive, BigDecimal CustomerChange, Blob billQRCode, ImageView imageView_Bill_QR_Code) {
        this.roomID = roomID;
        this.customerID = customerID;
        this.userName = userName;
        this.checkInID = checkInID;
        this.checkOutID = checkOutID;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.noOfDay = noOfDay;
        this.roomPrice = roomPrice;
        this.roomCharge = roomCharge;
        this.serviceCharge = serviceCharge;
        this.roomDiscount = roomDiscount;
        this.serviceDiscount = serviceDiscount;
        this.customerDiscount = customerDiscount;
        this.totalBillAmount = totalBillAmount;
        this.VATAmount = VATAmount;
        this.payableAmount = payableAmount;
        this.CustomerGive = CustomerGive;
        this.CustomerChange = CustomerChange;
        this.billQRCode = billQRCode;
        this.imageView_Bill_QR_Code = imageView_Bill_QR_Code;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCheckInID() {
        return checkInID;
    }

    public void setCheckInID(String checkInID) {
        this.checkInID = checkInID;
    }

    public String getCheckOutID() {
        return checkOutID;
    }

    public void setCheckOutID(String checkOutID) {
        this.checkOutID = checkOutID;
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

    public int getNoOfDay() {
        return noOfDay;
    }

    public void setNoOfDay(int noOfDay) {
        this.noOfDay = noOfDay;
    }

    public BigDecimal getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(BigDecimal roomPrice) {
        this.roomPrice = roomPrice;
    }

    public BigDecimal getRoomCharge() {
        return roomCharge;
    }

    public void setRoomCharge(BigDecimal roomCharge) {
        this.roomCharge = roomCharge;
    }

    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(BigDecimal serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public BigDecimal getRoomDiscount() {
        return roomDiscount;
    }

    public void setRoomDiscount(BigDecimal roomDiscount) {
        this.roomDiscount = roomDiscount;
    }

    public BigDecimal getServiceDiscount() {
        return serviceDiscount;
    }

    public void setServiceDiscount(BigDecimal serviceDiscount) {
        this.serviceDiscount = serviceDiscount;
    }

    public BigDecimal getCustomerDiscount() {
        return customerDiscount;
    }

    public void setCustomerDiscount(BigDecimal customerDiscount) {
        this.customerDiscount = customerDiscount;
    }

    public BigDecimal getTotalBillAmount() {
        return totalBillAmount;
    }

    public void setTotalBillAmount(BigDecimal totalBillAmount) {
        this.totalBillAmount = totalBillAmount;
    }

    public BigDecimal getVATAmount() {
        return VATAmount;
    }

    public void setVATAmount(BigDecimal VATAmount) {
        this.VATAmount = VATAmount;
    }

    public BigDecimal getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(BigDecimal payableAmount) {
        this.payableAmount = payableAmount;
    }

    public BigDecimal getCustomerGive() {
        return CustomerGive;
    }

    public void setCustomerGive(BigDecimal CustomerGive) {
        this.CustomerGive = CustomerGive;
    }

    public BigDecimal getCustomerChange() {
        return CustomerChange;
    }

    public void setCustomerChange(BigDecimal CustomerChange) {
        this.CustomerChange = CustomerChange;
    }

    public Blob getBillQRCode() {
        return billQRCode;
    }

    public void setBillQRCode(Blob billQRCode) {
        this.billQRCode = billQRCode;
    }

    public ImageView getImageView_Bill_QR_Code() {
        return imageView_Bill_QR_Code;
    }

    public void setImageView_Bill_QR_Code(ImageView imageView_Bill_QR_Code) {
        this.imageView_Bill_QR_Code = imageView_Bill_QR_Code;
    }

}
