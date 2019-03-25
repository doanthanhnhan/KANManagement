/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.LocalDateTime;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author ASUS
 */
public class Customer {

    public StringProperty CusID, User, FName, MName, LName, Email, Passport, Phone, Company, Date;
    public BooleanProperty Sex, Active;
    public FloatProperty Discount;

    public Customer() {
    }

    public Customer(String CusID, String User, String FName, String MName, String LName, String Email, String Passport, String Phone, String Company, Boolean Sex, Boolean Active, Float Discount, String Date) {
        this.CusID = new SimpleStringProperty(CusID);
        this.User = new SimpleStringProperty(User);
        this.FName = new SimpleStringProperty(FName);
        this.MName = new SimpleStringProperty(MName);
        this.LName = new SimpleStringProperty(LName);
        this.Email = new SimpleStringProperty(Email);
        this.Passport = new SimpleStringProperty(Passport);
        this.Phone = new SimpleStringProperty(Phone);
        this.Date = new SimpleStringProperty(Date);
        this.Company = new SimpleStringProperty(Company);
        this.Sex = new SimpleBooleanProperty(Sex);
        this.Active = new SimpleBooleanProperty(Active);
        this.Discount = new SimpleFloatProperty(Discount);
    }
    //    CusId

    public String getCusID() {
        return CusID.get();
    }

    public void setCusID(String CusID) {
        this.CusID = new SimpleStringProperty(CusID);
    }
//    User

    public String getUser() {
        return User.get();
    }

    public void setUser(String User) {
        this.User = new SimpleStringProperty(User);
    }
//    Fname

    public String getFName() {
        return FName.get();
    }

    public void setFName(String FName) {
        this.FName = new SimpleStringProperty(FName);
    }
// LName

    public String getLName() {
        return LName.get();
    }

    public void setLName(String LName) {
        this.LName = new SimpleStringProperty(LName);
    }
//MName

    public String getMName() {
        return MName.get();
    }

    public void setMName(String MName) {
        this.MName = new SimpleStringProperty(MName);
    }
//Email

    public String getEmail() {
        return Email.get();
    }

    public void setEmail(String Email) {
        this.Email = new SimpleStringProperty(Email);
    }
//Passport

    public String getPassport() {
        return Passport.get();
    }

    public void setPassport(String Passport) {
        this.Passport = new SimpleStringProperty(Passport);
    }
//Phone

    public String getPhone() {
        return Phone.get();
    }

    public void setPhone(String Phone) {
        this.Phone = new SimpleStringProperty(Phone);
    }
//Date

    public String getDate() {
        return Date.get();
    }

    public void setDate(String Date) {
        this.Date = new SimpleStringProperty(Date);
    }
//Company

    public String getCompany() {
        return Company.get();
    }

    public void setCompany(String Company) {
        this.Company = new SimpleStringProperty(Company);
    }
//  Discount

    public Float getDiscount() {
        return Discount.get();
    }

    public void setDiscount(Float Discount) {
        this.Discount = new SimpleFloatProperty(Discount);
    }
//    active

    public Boolean getActive() {
        return Active.get();
    }

    public void setActive(Boolean Active) {
        this.Active = new SimpleBooleanProperty(Active);
    }
//    Sex

    public Boolean getSex() {
        return Sex.get();
    }

    public void setSex(Boolean Sex) {
        this.Sex = new SimpleBooleanProperty(Sex);
    }
}
