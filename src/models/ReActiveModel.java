/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Admin
 */
public class ReActiveModel {

    private StringProperty Employee_ID;
    private StringProperty First_Name;
    private StringProperty Mid_Name;
    private StringProperty Last_Name;
    private BooleanProperty checkEmployee_ReActive;

    public boolean ischeckEmployee_ReActive() {
        return checkEmployee_ReActive.get();
    }

    public void setcheckEmployee_ReActive(boolean checkEmployee_ReActive) {
        this.checkEmployee_ReActive = new SimpleBooleanProperty(checkEmployee_ReActive);
    }

    public BooleanProperty checkEmployee_ReActive() {
        return checkEmployee_ReActive;
    }

    public String getEmployee_ID() {
        return Employee_ID.get();
    }

    public void setEmployee_ID(String Employee_ID) {
        this.Employee_ID = new SimpleStringProperty(Employee_ID);
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

    public ReActiveModel() {
    }

    public ReActiveModel(String Employee_ID, String First_Name, String Mid_Name, String Last_Name, Boolean checkEmployee_ReActive) {
        this.Employee_ID = new SimpleStringProperty(Employee_ID);
        this.First_Name = new SimpleStringProperty(First_Name);
        this.Mid_Name = new SimpleStringProperty(Mid_Name);
        this.Last_Name = new SimpleStringProperty(Last_Name);
        this.checkEmployee_ReActive = new SimpleBooleanProperty(checkEmployee_ReActive);
    }
}
