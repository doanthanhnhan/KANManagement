/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.sun.javafx.application.LauncherImpl;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.DAO;
import models.DAODepartMentReActive;
import utils.FormatName;
import utils.GetInetAddress;

/**
 *
 * @author Admin
 */
public class Login extends Application {

    private static final int COUNT_LIMIT = 50000;
    private int checkLogin;

    @Override
    public void start(Stage stage) throws Exception {
        if (DAO.check_invalid(GetInetAddress.getMacAddress()) && DAO.check_Active_MacAddress(GetInetAddress.getMacAddress()).equals(0)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Your device has been blocked from using this program !!!");
            alert.setContentText("Please contact the administrator for reuse !!!");
            alert.showAndWait();
        } else{
            if (DAO.checkFirstLogin() == 0) {
                DAODepartMentReActive.Add_New_Department("DPM-Admin", "Admin", "Admin");
                DAO.setRoleDepartmentAdmin();
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/FXMLAddNewEmployee.fxml"));
                stage.setTitle("Add New Employee");
                Scene scene = new Scene(root);
                stage.setScene(scene);
            } else {
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/FXMLLogin.fxml"));
                stage.setTitle("Login");
                Scene scene = new Scene(root);
                stage.setScene(scene);
            }
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.getIcons().add(new Image("/images/KAN Logo.png"));
            stage.show();
        }
    }

    @Override
    public void init() throws Exception {
        Thread checkDB = new Thread(() -> {
            try {
                checkLogin = DAO.checkFirstLogin();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Load DB finished! checkLogin = " + checkLogin);
        });
        checkDB.start();
        for (int i = 0; i < COUNT_LIMIT; i++) {
            double progress = (100 * i) / COUNT_LIMIT;
            LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
            //System.out.println(progress);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //launch(args);
        LauncherImpl.launchApplication(Login.class, MainFXPreloader.class, args);
    }

}
