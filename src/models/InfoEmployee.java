/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Admin
 */
public class InfoEmployee {
    private StringProperty Employee_ID;
    private StringProperty UserName;
    private StringProperty PassWord;
    private StringProperty Active;
    private StringProperty First_Name;
    private StringProperty Mid_Name;
    private StringProperty Last_Name;
    private StringProperty Work_Dept;
    private StringProperty Phone_No;
    private StringProperty Hiredate;
    private StringProperty Job;
    private IntegerProperty EDLEVEL;
    private StringProperty Sex;
    private StringProperty Birthdate;
    private StringProperty Salary;
    private StringProperty Bonus;
    private StringProperty Comm;
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
    public String getActive() {
        return Active.get();
    }

    public void setActive(String Active) {
        this.Active = new SimpleStringProperty(Active);
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
        this.Mid_Name =new SimpleStringProperty(Mid_Name);
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

    public String getSex() {
        return Sex.get();
    }

    public void setSex(String Sex) {
        this.Sex = new SimpleStringProperty(Sex);
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

    public InfoEmployee(){}
    public InfoEmployee(String Employee_ID, String UserName, String PassWord,String Active, String First_Name, String Mid_Name, String Last_Name, String Work_Dept,String Phone_No, String Hiredate, String Job,
            Integer EDLEVEL, String Sex, String Birthdate, String Salary, String Bonus, String Comm) {
        this.Employee_ID = new SimpleStringProperty(Employee_ID);
        this.UserName = new SimpleStringProperty(UserName);
        this.PassWord = new SimpleStringProperty(PassWord);
        this.Active = new SimpleStringProperty(Active);
        this.First_Name = new SimpleStringProperty(First_Name);
        this.Mid_Name = new SimpleStringProperty(Mid_Name);
        this.Last_Name = new SimpleStringProperty(Last_Name);
        this.Work_Dept = new SimpleStringProperty(Work_Dept);
        this.Phone_No = new SimpleStringProperty(Phone_No);
        this.Hiredate = new SimpleStringProperty(Hiredate);
        this.Job = new SimpleStringProperty(Job);
        this.EDLEVEL = new SimpleIntegerProperty(EDLEVEL);
        this.Sex = new SimpleStringProperty(Sex);
        this.Birthdate = new SimpleStringProperty(Birthdate);
        this.Salary = new SimpleStringProperty(Salary);
        this.Bonus = new SimpleStringProperty(Bonus);
        this.Comm = new SimpleStringProperty(Comm);
    }
}
