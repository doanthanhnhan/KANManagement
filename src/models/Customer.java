/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author ASUS
 */
public class Customer {

    public StringProperty CusID, User, FName, LName, Email, Passport, Phone, Company, Date;
    public BooleanProperty Sex, Active;
    public float Discount;
    

    public Customer() {
    }

    public Customer(String CusID, String User, String FName, String LName, String Email, String Passport, String Phone, String Company, String Date, Boolean Sex, Boolean Active, float Discount) {
        this.CusID =  new SimpleStringProperty(CusID);
        this.User =  new SimpleStringProperty(User);
        this.FName =  new SimpleStringProperty(FName);
        this.LName =  new SimpleStringProperty(LName);
        this.Email =  new SimpleStringProperty(Email);
        this.Passport =  new SimpleStringProperty(Passport);
        this.Phone =  new SimpleStringProperty(Phone);
        this.Company =  new SimpleStringProperty(Company);
        this.Date =  new SimpleStringProperty(Date);
        this.Sex =  new SimpleBooleanProperty(Sex);
        this.Active = new SimpleBooleanProperty(Active);
        this.Discount = Discount;
    }

    public String getCusID() {
        return CusID.get();
    }

    public void setCusID(String CusID) {
        this.CusID = new SimpleStringProperty(CusID);
    }

    public String getUser() {
        return User.get();
    }

    public void setUser(String User) {
        this.User = new SimpleStringProperty(User);
    }

    public String getFName() {
        return FName.get();
    }

    public void setFName(String FName) {
        this.FName = new SimpleStringProperty(FName);
    }

    public String getLName() {
        return LName.get();
    }

    public void setLName(String LName) {
        this.LName = new SimpleStringProperty(LName);
    }

    public String getEmail() {
        return Email.get();
    }

    public void setEmail(String Email) {
        this.Email = new SimpleStringProperty(Email);
    }

    public String getPassport() {
        return Passport.get();
    }

    public void setPassport(String Passport) {
        this.Passport = new SimpleStringProperty(Passport);
    }

    public String getPhone() {
        return Phone.get();
    }

    public void setPhone(String Phone) {
        this.Phone = new SimpleStringProperty(Phone);
    }

    public String getCompany() {
        return Company.get();
    }

    public void setCompany(String Company) {
        this.Company = new SimpleStringProperty(Company);
    }

    public String getDate() {
        return Date.get();
    }

    public void setDate(String Date) {
        this.Date = new SimpleStringProperty(Date);
    }

    public Boolean getSex() {
        return Sex.get();
    }

    public void setSex(Boolean Sex) {
        this.Sex = new SimpleBooleanProperty(Sex);
    }

    public Boolean getActive() {
        return Active.get();
    }

    public void setActive(Boolean Active) {
        this.Active = new SimpleBooleanProperty(Active);
    }

    public float getDiscount() {
        return Discount;
    }

    public void setDiscount(float Discount) {
        this.Discount = Discount;
    }

}
