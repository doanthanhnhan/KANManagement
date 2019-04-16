/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import models.DAOCustomerBookingCheckIn;
import models.Room;
import models.RoomDAOImpl;
import models.Setting;

/**
 *
 * @author Doan Thanh Nhan
 */
public class connectDB {

    static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static String DB_URL;
    public static String AZURE_URL;
    public static String DATABASENAME;
    public static String USER;
    public static String PASS;
    public static String SMTP_SERVER;
    public static String SMTP_PORT;
    public static String EMAIL;
    public static String EMAIL_PASS;
    public static Setting SETTING;

    public connectDB() {
        SETTING = SettingXML.readXML();
        if (SETTING.getChooseServer().equals("Local")) {
            DB_URL = SETTING.getLocalServer_DB_URL();
            DATABASENAME = "databaseName=" + SETTING.getLocalServer_DB_Name() + ";";
            USER = "user=" + SETTING.getLocalServer_User() + ";";
            PASS = "password=" + SETTING.getLocalServer_Password() + ";";
            AZURE_URL = DB_URL + DATABASENAME + USER + PASS;
        } else {
            AZURE_URL = SETTING.getRemoteServer_DB_URL();
            DATABASENAME = SETTING.getRemoteServer_DB_Name();
            USER = "user=" + SETTING.getRemoteServer_User() + ";";
            PASS = "password=" + SETTING.getRemoteServer_Password() + ";";
        }
        SMTP_SERVER = SETTING.getSmtpServer();
        SMTP_PORT = SETTING.getSmtpPort();
        EMAIL = SETTING.getEmailAdress();
        EMAIL_PASS = SETTING.getEmailPassword();
    }

    /**
     * Use to connect and create new database
     *
     * @return connection
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection connectSQLServerToCreateDB() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        Connection connection = DriverManager.getConnection(DB_URL + USER + PASS);
        return connection;
    }

    /**
     * Use to connect existing database
     *
     * @return connection
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection connectSQLServer() throws ClassNotFoundException, SQLException {
        //Setting SQLServer JDBC Driver
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your SQLServer JDBC Driver?");
            //e.printStackTrace();
            //throw e;
        }
        Connection connection = DriverManager.getConnection(AZURE_URL);
        return connection;
    }

    public static boolean checkDBExists() {
        try {
            String sql = "SELECT * FROM sys.databases WHERE name LIKE 'KanManagement'";
            Connection conn = connectDB.connectSQLServerToCreateDB();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                return true;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
//            Platform.runLater(() -> {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Message");
//                alert.setHeaderText("Error Querry");
//                alert.setContentText("Can't connect to Database");
//                alert.show();
//                
//            });
        return false;
    }

    public static void createSQLDatabase() {
        File file = new File("");
        File txtFile = new File(file.getAbsolutePath() + "/src/lib/SQL/CreateDBEnCrypt.txt");
        Charset charset = Charset.forName("UTF-8");
        //String sql = "CREATE DATABASE KANManagement";
        try {
            //Reading normal txt file
            //String sql = new Scanner(txtFile).useDelimiter("\\Z").next();
            //Reading desEncrypt txt file
            String sql = DESEncryptExample.desDecrypt_File(txtFile.getAbsolutePath(), charset, "DoanThanhNhan");
            Connection conn = connectDB.connectSQLServerToCreateDB();
            PreparedStatement stmt = conn.prepareStatement(sql);
            System.out.println("Create DB Successful : " + stmt.executeUpdate());
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
//            Platform.runLater(() -> {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Message");
//                alert.setHeaderText("Error Querry");
//                alert.setContentText("Can't connect to Database");
//                alert.show();
//                
//            });

    }

    public static void createTables() {
        File file = new File("");
        File txtFile = new File(file.getAbsolutePath() + "/src/lib/SQL/CreateTablesEnCrypt.txt");
        Charset charset = Charset.forName("UTF-8");
        //String sql = "CREATE DATABASE KANManagement";
        try {
            //Reading normal txt file
            //String sql = new Scanner(txtFile).useDelimiter("\\Z").next();
            //Reading desEncrypt txt file
            String sql = DESEncryptExample.desDecrypt_File(txtFile.getAbsolutePath(), charset, "DoanThanhNhan");
            Connection conn = connectDB.connectSQLServer();
            PreparedStatement stmt = conn.prepareStatement(sql);
            System.out.println("Create Tables Successful : " + stmt.executeUpdate());
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
//            Platform.runLater(() -> {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Message");
//                alert.setHeaderText("Error Querry");
//                alert.setContentText("Can't connect to Database");
//                alert.show();
//                
//            });
    }

    public static void createViews() {
        File file = new File("");
        File txtFile1 = new File(file.getAbsolutePath() + "/src/lib/SQL/CreateViewUserRolesEnCrypt.txt");
        File txtFile2 = new File(file.getAbsolutePath() + "/src/lib/SQL/CreateViewRoomPropertiesEnCrypt.txt");
        Charset charset = Charset.forName("UTF-8");
        //String sql = "CREATE DATABASE KANManagement";
        try {
            //Reading normal txt file
            //String sql = new Scanner(txtFile).useDelimiter("\\Z").next();
            //Reading desEncrypt txt file
            String sql1 = DESEncryptExample.desDecrypt_File(txtFile1.getAbsolutePath(), charset, "DoanThanhNhan");
            String sql2 = DESEncryptExample.desDecrypt_File(txtFile2.getAbsolutePath(), charset, "DoanThanhNhan");
            Connection conn = connectDB.connectSQLServer();
            PreparedStatement stmt1 = conn.prepareStatement(sql1);
            System.out.println("Create View1 Successful : " + stmt1.executeUpdate());
            PreparedStatement stmt2 = conn.prepareStatement(sql2);
            System.out.println("Create View2 Successful : " + stmt2.executeUpdate());
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
//            Platform.runLater(() -> {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Message");
//                alert.setHeaderText("Error Querry");
//                alert.setContentText("Can't connect to Database");
//                alert.show();
//                
//            });
    }

    public static void main(String[] args) {
        connectDB cn = new connectDB();
        //createSQLDatabase();
        //createTables();
        createViews();
    }
}
