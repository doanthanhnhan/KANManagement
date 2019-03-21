/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.LocalDateTime;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author ASUS
 */
public class Customer {

    public StringProperty CusID, User, FName, MName, LName, Email, Passport, Phone, Company;
    public LocalDateTime Date;
    public BooleanProperty Sex, Active;
    public float Discount;

    public Customer() {
        this.CusID = new SimpleStringProperty();
        this.User = new SimpleStringProperty();
        this.FName = new SimpleStringProperty();
        this.MName = new SimpleStringProperty();
        this.LName = new SimpleStringProperty();
        this.Email = new SimpleStringProperty();
        this.Passport = new SimpleStringProperty();
        this.Phone = new SimpleStringProperty();
        this.Company = new SimpleStringProperty();
        this.Sex = new SimpleBooleanProperty();
        this.Active = new SimpleBooleanProperty();
    }

    public Customer(String CusID, String User, String FName, String MName, String LName, String Email, String Passport, String Phone, String Company, Boolean Sex, Boolean Active, float Discount) {
        this.CusID = new SimpleStringProperty(CusID);
        this.User = new SimpleStringProperty(User);
        this.FName = new SimpleStringProperty(FName);
        this.MName = new SimpleStringProperty(MName);
        this.LName = new SimpleStringProperty(LName);
        this.Email = new SimpleStringProperty(Email);
        this.Passport = new SimpleStringProperty(Passport);
        this.Phone = new SimpleStringProperty(Phone);
        this.Company = new SimpleStringProperty(Company);
        this.Sex = new SimpleBooleanProperty(Sex);
        this.Active = new SimpleBooleanProperty(Active);
        this.Discount = Discount;
    }

    public LocalDateTime getDate() {
        return Date;
    }

    public void setDate(LocalDateTime Date) {
        this.Date = Date;
    }

    public float getDiscount() {
        return Discount;
    }

    public void setDiscount(float Discount) {
        this.Discount = Discount;
    }

    public final String getCusID() {
        return CusID.get();
    }

    public final void setCusID(String value) {
        CusID.set(value);
    }

    public StringProperty CusIDProperty() {
        return CusID;
    }

    public final String getUser() {
        return User.get();
    }

    public final void setUser(String value) {
        User.set(value);
    }

    public StringProperty UserProperty() {
        return User;
    }

    public final String getFName() {
        return FName.get();
    }

    public final void setFName(String value) {
        FName.set(value);
    }

    public StringProperty FNameProperty() {
        return FName;
    }

    public final String getMName() {
        return MName.get();
    }

    public final void setMName(String value) {
        MName.set(value);
    }

    public StringProperty MNameProperty() {
        return MName;
    }

    public final String getLName() {
        return LName.get();
    }

    public final void setLName(String value) {
        LName.set(value);
    }

    public StringProperty LNameProperty() {
        return LName;
    }

    public final String getEmail() {
        return Email.get();
    }

    public final void setEmail(String value) {
        Email.set(value);
    }

    public StringProperty EmailProperty() {
        return Email;
    }

    public final String getPassport() {
        return Passport.get();
    }

    public final void setPassport(String value) {
        Passport.set(value);
    }

    public StringProperty PassportProperty() {
        return Passport;
    }

    public final String getPhone() {
        return Phone.get();
    }

    public final void setPhone(String value) {
        Phone.set(value);
    }

    public StringProperty PhoneProperty() {
        return Phone;
    }

    public final String getCompany() {
        return Company.get();
    }

    public final void setCompany(String value) {
        Company.set(value);
    }

    public StringProperty CompanyProperty() {
        return Company;
    }

    public final boolean isSex() {
        return Sex.get();
    }

    public final void setSex(boolean value) {
        Sex.set(value);
    }

    public BooleanProperty SexProperty() {
        return Sex;
    }

    public final boolean isActive() {
        return Active.get();
    }

    public final void setActive(boolean value) {
        Active.set(value);
    }

    public BooleanProperty ActiveProperty() {
        return Active;
    }

}
