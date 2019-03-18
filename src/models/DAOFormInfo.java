/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.LocalDate;

/**
 *
 * @author ASUS
 */
public interface DAOFormInfo {
    public void AddNewBooking(String Note,int Number,LocalDate Date,boolean Check,String Flight,String Cus, String Room);
    public void AddNewCustomer(String FName, String LName, String Mail, String Phone, String Passport, LocalDate BirthDay,  String Company, boolean Sex, String userName,float Discount);
}
