/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author ASUS
 */
public class CheckInRoom {
    String ID, Name, Phone, Mail, Company, Note, RoomType, Flight, Drive, Num, Date;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String Mail) {
        this.Mail = Mail;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String Company) {
        this.Company = Company;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String Note) {
        this.Note = Note;
    }

    public String getRoomType() {
        return RoomType;
    }

    public void setRoomType(String RoomType) {
        this.RoomType = RoomType;
    }

    public String getFlight() {
        return Flight;
    }

    public void setFlight(String Flight) {
        this.Flight = Flight;
    }

    public String getDrive() {
        return Drive;
    }

    public void setDrive(String Drive) {
        this.Drive = Drive;
    }

    public String getNum() {
        return Num;
    }

    public void setNum(String Num) {
        this.Num = Num;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public CheckInRoom(String ID, String Name, String Phone, String Mail, String Company, String Note, String RoomType, String Flight, String Drive, String Num, String Date) {
        this.ID = ID;
        this.Name = Name;
        this.Phone = Phone;
        this.Mail = Mail;
        this.Company = Company;
        this.Note = Note;
        this.RoomType = RoomType;
        this.Flight = Flight;
        this.Drive = Drive;
        this.Num = Num;
        this.Date = Date;
    }
    
}
