/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Blob;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.ImageView;

/**
 *
 * @author Admin
 */
public class InfoEmployee {

    private Blob serviceImage;
    private ImageView imageView;
    private StringProperty Employee_ID;
    private StringProperty UserName;
    private StringProperty PassWord;
    private BooleanProperty Active;
    private StringProperty Serect_Question;
    private StringProperty Serect_Answer;
    private StringProperty First_Name;
    private StringProperty Mid_Name;
    private StringProperty Last_Name;
    private StringProperty Address;
    private StringProperty Work_Dept;
    private StringProperty Phone_No;
    private StringProperty Hiredate;
    private StringProperty Job;
    private IntegerProperty EDLEVEL;
    private BooleanProperty Sex;
    private StringProperty Birthdate;
    private StringProperty Salary;
    private StringProperty Bonus;
    private StringProperty Comm;
    private StringProperty Gmail;
    private StringProperty Id_number;

    public String getGmail() {
        return Gmail.get();
    }

    public void setGmail(String Gmail) {
        this.Gmail = new SimpleStringProperty(Gmail);
    }

    public String getId_number() {
        return Id_number.get();
    }

    public void setId_number(String Id_number) {
        this.Id_number = new SimpleStringProperty(Id_number);
    }

    public String getAddress() {
        return Address.get();
    }

    public void setAddress(String Address) {
        this.Address = new SimpleStringProperty(Address);
    }

    public String getEmployee_ID() {
        return Employee_ID.get();
    }

    public void setEmployee_ID(String Employee_ID) {
        this.Employee_ID = new SimpleStringProperty(Employee_ID);
    }

    public String getUserName() {
        return UserName.get();
    }

    public void setUserName(String UserName) {
        this.UserName = new SimpleStringProperty(UserName);
    }

    public String getPassWord() {
        return PassWord.get();
    }

    public void setPassWord(String PassWord) {
        this.PassWord = new SimpleStringProperty(PassWord);
    }

    public Boolean getActive() {
        return Active.get();
    }

    public void setActive(Boolean Active) {
        this.Active = new SimpleBooleanProperty(Active);
    }

    public String getSerect_Question() {
        return Serect_Question.get();
    }

    public void setSerect_Question(String Serect_Question) {
        this.Serect_Question = new SimpleStringProperty(Serect_Question);
    }

    public String getSerect_Answer() {
        return Serect_Answer.get();
    }

    public void setSerect_Answer(String Serect_Answer) {
        this.Serect_Answer = new SimpleStringProperty(Serect_Answer);
    }

    public String getFirst_Name() {
        return First_Name.get();
    }

    public void setFirst_Name(String First_Name) {
        this.First_Name = new SimpleStringProperty(First_Name);
    }

    public String getMid_Name() {
        return Mid_Name.get();
    }

    public void setMid_Name(String Mid_Name) {
        this.Mid_Name = new SimpleStringProperty(Mid_Name);
    }

    public String getLast_Name() {
        return Last_Name.get();
    }

    public void setLast_Name(String Last_Name) {
        this.Last_Name = new SimpleStringProperty(Last_Name);
    }

    public String getWork_Dept() {
        return Work_Dept.get();
    }

    public void setWork_Dept(String Work_Dept) {
        this.Work_Dept = new SimpleStringProperty(Work_Dept);
    }

    public String getPhone_No() {
        return Phone_No.get();
    }

    public void setPhone_No(String Phone_No) {
        this.Phone_No = new SimpleStringProperty(Phone_No);
    }

    public String getHiredate() {
        return Hiredate.get();
    }

    public void setHiredate(String Hiredate) {
        this.Hiredate = new SimpleStringProperty(Hiredate);
    }

    public String getJob() {
        return Job.get();
    }

    public void setJob(String Job) {
        this.Job = new SimpleStringProperty(Job);
    }

    public Integer getEDLEVEL() {
        return EDLEVEL.get();
    }

    public void setEDLEVEL(Integer EDLEVEL) {
        this.EDLEVEL = new SimpleIntegerProperty(EDLEVEL);
    }

    public Boolean getSex() {
        return Sex.get();
    }

    public void setSex(Boolean Sex) {
        this.Sex = new SimpleBooleanProperty(Sex);
    }

    public String getBirthdate() {
        return Birthdate.get();
    }

    public void setBirthdate(String Birthdate) {
        this.Birthdate = new SimpleStringProperty(Birthdate);
    }

    public String getSalary() {
        return Salary.get();
    }

    public void setSalary(String Salary) {
        this.Salary = new SimpleStringProperty(Salary);
    }

    public String getBonus() {
        return Bonus.get();
    }

    public void setBonus(String Bonus) {
        this.Bonus = new SimpleStringProperty(Bonus);
    }

    public String getComm() {
        return Comm.get();
    }

    public void setComm(String Comm) {
        this.Comm = new SimpleStringProperty(Comm);
    }

    public Blob getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(Blob serviceImage) {
        this.serviceImage = serviceImage;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public InfoEmployee() {
    }

    public InfoEmployee(String Employee_ID, String UserName, String PassWord, String Id_number, String Address, Boolean Active, String Serect_Question, String Serect_Answer, String First_Name, String Mid_Name, String Last_Name, String Work_Dept, String Phone_No, String Hiredate, String Job,
            Integer EDLEVEL, Boolean Sex, String Birthdate, String Salary, String Bonus, String Comm, String Gmail, Blob serviceImage) {
        this.Employee_ID = new SimpleStringProperty(Employee_ID);
        this.UserName = new SimpleStringProperty(UserName);
        this.PassWord = new SimpleStringProperty(PassWord);
        this.Id_number = new SimpleStringProperty(Id_number);
        this.Address = new SimpleStringProperty(Address);
        this.Active = new SimpleBooleanProperty(Active);
        this.Serect_Question = new SimpleStringProperty(Serect_Question);
        this.Serect_Answer = new SimpleStringProperty(Serect_Answer);
        this.First_Name = new SimpleStringProperty(First_Name);
        this.Mid_Name = new SimpleStringProperty(Mid_Name);
        this.Last_Name = new SimpleStringProperty(Last_Name);
        this.Work_Dept = new SimpleStringProperty(Work_Dept);
        this.Phone_No = new SimpleStringProperty(Phone_No);
        this.Hiredate = new SimpleStringProperty(Hiredate);
        this.Job = new SimpleStringProperty(Job);
        this.EDLEVEL = new SimpleIntegerProperty(EDLEVEL);
        this.Sex = new SimpleBooleanProperty(Sex);
        this.Birthdate = new SimpleStringProperty(Birthdate);
        this.Salary = new SimpleStringProperty(Salary);
        this.Bonus = new SimpleStringProperty(Bonus);
        this.Comm = new SimpleStringProperty(Comm);
        this.Gmail = new SimpleStringProperty(Gmail);
        this.serviceImage = serviceImage;
    }
}
