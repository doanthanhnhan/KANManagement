/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Đoàn Thanh Nhân <your.name at your.org>
 */
@XmlRootElement(namespace = "https://www.google.com/", name = "setting")
@XmlAccessorType(XmlAccessType.FIELD)
public class Setting {

    private String chooseServer;
    private String smtpServer;
    private String smtpPort;
    private String emailAdress;
    private String emailPassword;
    private String localServer_DB_URL;
    private String localServer_DB_Name;
    private String localServer_User;
    private String localServer_Password;
    private String remoteServer_DB_URL;
    private String remoteServer_DB_Name;
    private String remoteServer_User;
    private String remoteServer_Password;
    private byte[] secretKey;

    public Setting() {
    }

    public Setting(String chooseServer, String smtpServer, String smtpPort, String emailAdress, String emailPassword, String localServer_DB_URL, String localServer_DB_Name, String localServer_User, String localServer_Password, String remoteServer_DB_URL, String remoteServer_DB_Name, String remoteServer_User, String remoteServer_Password, byte[] secretKey) {
        this.chooseServer = chooseServer;
        this.smtpServer = smtpServer;
        this.smtpPort = smtpPort;
        this.emailAdress = emailAdress;
        this.emailPassword = emailPassword;
        this.localServer_DB_URL = localServer_DB_URL;
        this.localServer_DB_Name = localServer_DB_Name;
        this.localServer_User = localServer_User;
        this.localServer_Password = localServer_Password;
        this.remoteServer_DB_URL = remoteServer_DB_URL;
        this.remoteServer_DB_Name = remoteServer_DB_Name;
        this.remoteServer_User = remoteServer_User;
        this.remoteServer_Password = remoteServer_Password;
        this.secretKey = secretKey;
    }

    public byte[] getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(byte[] secretKey) {
        this.secretKey = secretKey;
    }

    public String getChooseServer() {
        return chooseServer;
    }

    public void setChooseServer(String chooseServer) {
        this.chooseServer = chooseServer;
    }

    public String getSmtpServer() {
        return smtpServer;
    }

    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    public String getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(String smtpPort) {
        this.smtpPort = smtpPort;
    }

    public String getEmailAdress() {
        return emailAdress;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    public String getLocalServer_DB_URL() {
        return localServer_DB_URL;
    }

    public void setLocalServer_DB_URL(String localServer_DB_URL) {
        this.localServer_DB_URL = localServer_DB_URL;
    }

    public String getLocalServer_DB_Name() {
        return localServer_DB_Name;
    }

    public void setLocalServer_DB_Name(String localServer_DB_Name) {
        this.localServer_DB_Name = localServer_DB_Name;
    }

    public String getLocalServer_User() {
        return localServer_User;
    }

    public void setLocalServer_User(String localServer_User) {
        this.localServer_User = localServer_User;
    }

    public String getLocalServer_Password() {
        return localServer_Password;
    }

    public void setLocalServer_Password(String localServer_Password) {
        this.localServer_Password = localServer_Password;
    }

    public String getRemoteServer_DB_URL() {
        return remoteServer_DB_URL;
    }

    public void setRemoteServer_DB_URL(String remoteServer_DB_URL) {
        this.remoteServer_DB_URL = remoteServer_DB_URL;
    }

    public String getRemoteServer_DB_Name() {
        return remoteServer_DB_Name;
    }

    public void setRemoteServer_DB_Name(String remoteServer_DB_Name) {
        this.remoteServer_DB_Name = remoteServer_DB_Name;
    }

    public String getRemoteServer_User() {
        return remoteServer_User;
    }

    public void setRemoteServer_User(String remoteServer_User) {
        this.remoteServer_User = remoteServer_User;
    }

    public String getRemoteServer_Password() {
        return remoteServer_Password;
    }

    public void setRemoteServer_Password(String remoteServer_Password) {
        this.remoteServer_Password = remoteServer_Password;
    }

}
