/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import models.Setting;

/**
 *
 * @author Đoàn Thanh Nhân <your.name at your.org>
 */
public class SettingXML {

    private static final String XML_FILE = "/src/xml/settings.xml";
    private static final URL url = ClassLoader.class.getResource(XML_FILE);

    public static void writeXML(Setting setting) {
        try {
            // Tạo đối tượng JAXB context.
            JAXBContext context = JAXBContext.newInstance(Setting.class);

            // Create desKey
            SecretKey desKey = KeyGenerator.getInstance("DES").generateKey();
            setting.setSecretKey(desKey.getEncoded());
            // Create encrypter/decrypter class
            EncrypterDecrypter encrypter = new EncrypterDecrypter(desKey, desKey.getAlgorithm());
            // Encrypt setting information
            setting.setChooseServer(encrypter.encrypt(setting.getChooseServer()));
            setting.setEmailAdress(encrypter.encrypt(setting.getEmailAdress()));
            setting.setEmailPassword(encrypter.encrypt(setting.getEmailPassword()));
            setting.setLocalServer_DB_Name(encrypter.encrypt(setting.getLocalServer_DB_Name()));
            setting.setLocalServer_DB_URL(encrypter.encrypt(setting.getLocalServer_DB_URL()));
            setting.setLocalServer_Password(encrypter.encrypt(setting.getLocalServer_Password()));
            setting.setLocalServer_User(encrypter.encrypt(setting.getLocalServer_User()));
            setting.setRemoteServer_DB_Name(encrypter.encrypt(setting.getRemoteServer_DB_Name()));
            setting.setRemoteServer_DB_URL(encrypter.encrypt(setting.getRemoteServer_DB_URL()));
            setting.setRemoteServer_Password(encrypter.encrypt(setting.getRemoteServer_Password()));
            setting.setRemoteServer_User(encrypter.encrypt(setting.getRemoteServer_User()));
            setting.setSmtpPort(encrypter.encrypt(setting.getSmtpPort()));
            setting.setSmtpServer(encrypter.encrypt(setting.getSmtpServer()));

            // (1) Marshaller : Chuyển đối tượng Java thành XML
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // Ghi đối tượng java (dept) ra màn hình Console (System.out)
            m.marshal(setting, System.out);

            // Ghi đối tượng Java (dept) ra file.
            File file = new File("");
            File outFile = new File(file.getAbsolutePath() + XML_FILE);
            m.marshal(setting, outFile);

            System.err.println("Write to file: " + outFile.getAbsolutePath());
        } catch (JAXBException | NoSuchAlgorithmException ex) {
            Logger.getLogger(SettingXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Setting readXML() {
        Setting setting = new Setting();
        try {
            // Tạo đối tượng JAXB context.
            JAXBContext context = JAXBContext.newInstance(Setting.class);

            // (2) Unmarshaller : Chuyển dữ liệu XML thành đối tượng Java.
            Unmarshaller um = context.createUnmarshaller();

            // Phân tích file XML vừa được tạo ra ở bước trước.
            File file = new File("");
            setting = (Setting) um.unmarshal(new FileReader(file.getAbsolutePath() + XML_FILE));
            // Create desKey
            SecretKey desKey = new SecretKeySpec(setting.getSecretKey(), "DES");

            // Create encrypter/decrypter class
            EncrypterDecrypter encrypter = new EncrypterDecrypter(desKey, desKey.getAlgorithm());
            // Decrypt setting information
            setting.setChooseServer(encrypter.decrypt(setting.getChooseServer()));
            setting.setEmailAdress(encrypter.decrypt(setting.getEmailAdress()));
            setting.setEmailPassword(encrypter.decrypt(setting.getEmailPassword()));
            setting.setLocalServer_DB_Name(encrypter.decrypt(setting.getLocalServer_DB_Name()));
            setting.setLocalServer_DB_URL(encrypter.decrypt(setting.getLocalServer_DB_URL()));
            setting.setLocalServer_Password(encrypter.decrypt(setting.getLocalServer_Password()));
            setting.setLocalServer_User(encrypter.decrypt(setting.getLocalServer_User()));
            setting.setRemoteServer_DB_Name(encrypter.decrypt(setting.getRemoteServer_DB_Name()));
            setting.setRemoteServer_DB_URL(encrypter.decrypt(setting.getRemoteServer_DB_URL()));
            setting.setRemoteServer_Password(encrypter.decrypt(setting.getRemoteServer_Password()));
            setting.setRemoteServer_User(encrypter.decrypt(setting.getRemoteServer_User()));
            setting.setSmtpPort(encrypter.decrypt(setting.getSmtpPort()));
            setting.setSmtpServer(encrypter.decrypt(setting.getSmtpServer()));

        } catch (JAXBException | FileNotFoundException ex) {
            Logger.getLogger(SettingXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        return setting;
    }

    public static void main(String[] args) {
        Setting setting = new Setting();
        setting.setChooseServer("Local");
        setting.setSmtpServer("smtp.gmail.com");
        setting.setSmtpPort("587");
        setting.setEmailAdress("KANManagement.AP146@gmail.com");
        setting.setEmailPassword("KAN@123456");
        setting.setLocalServer_DB_Name("local name");
        setting.setLocalServer_DB_URL("local url");
        setting.setLocalServer_Password("local pass");
        setting.setLocalServer_User("local user");
        setting.setRemoteServer_DB_Name("remote name");
        setting.setRemoteServer_DB_URL("remote url");
        setting.setRemoteServer_Password("remote pass");
        setting.setRemoteServer_User("remote user");
        SettingXML.writeXML(setting);
        System.out.println("Create XML finished");

        Setting settingDecrypt = new Setting();
        settingDecrypt = SettingXML.readXML();
        System.out.println("Reading XML file...");
        System.out.println("Choose server: " + settingDecrypt.getChooseServer());
    }
}
