/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import models.DAOCustomerBookingCheckIn;
import models.Room;
import models.RoomDAOImpl;

/**
 *
 * @author Doan Thanh Nhan
 */
public class connectDB {

    static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static final String DB_URL = "jdbc:sqlserver://localhost:1433;";
    static final String AZURE_URL = "jdbc:sqlserver://nhansqlserver.database.windows.net:1433;database=KANManagement;user=doanthanhnhan@nhansqlserver;password=abc123!!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
    static final String DATABASENAME = "databaseName=KANManagement;";
    static final String USER = "user=sa;";
    static final String PASS = "password=sa";

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

    public static void createSQLDatabase() {
        String sql = "CREATE DATABASE KANManagement";
        try {
            Connection conn = connectDB.connectSQLServerToCreateDB();
            PreparedStatement stmt = conn.prepareStatement(sql);            
            System.out.println("Create DB Successful : " + stmt.executeUpdate());
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

    }

    public static void main(String[] args) {
        createSQLDatabase();
    }
}
