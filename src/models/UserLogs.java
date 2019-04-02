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
public class UserLogs {

    private IntegerProperty ID;
    private StringProperty UserName;
    private StringProperty MacAddress;
    private StringProperty LogContent;
    private StringProperty LogTime;

    public UserLogs() {
    }

    public UserLogs(Integer ID, String UserName, String MacAddress, String LogContent, String LogTime) {
        this.ID = new SimpleIntegerProperty(ID);
        this.UserName = new SimpleStringProperty(UserName);
        this.LogContent = new SimpleStringProperty(LogContent);
        this.LogTime = new SimpleStringProperty(LogTime);
        this.MacAddress = new SimpleStringProperty(MacAddress);
    }

    public String getLogTime() {
        return LogTime.get();
    }

    public void setLogTime(String LogTime) {
        this.LogTime = new SimpleStringProperty(LogTime);
    }

    public String getLogContent() {
        return LogContent.get();
    }

    public void setLogContent(String LogContent) {
        this.LogContent = new SimpleStringProperty(LogContent);
    }

    public String getMacAddress() {
        return MacAddress.get();
    }

    public void setMacAddress(String MacAddress) {
        this.MacAddress = new SimpleStringProperty(MacAddress);
    }

    public String getUserName() {
        return UserName.get();
    }

    public void setUserName(String UserName) {
        this.UserName = new SimpleStringProperty(UserName);
    }

    public Integer getID() {
        return ID.get();
    }

    public void setID(Integer ID) {
        this.ID = new SimpleIntegerProperty(ID);
    }
}
