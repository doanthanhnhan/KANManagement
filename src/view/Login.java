/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.DAO;

/**
 *
 * @author Admin
 */
public class Login extends Application {

    public static Integer checkFirstLogin;

    @Override
    public void start(Stage stage) throws Exception {
        checkFirstLogin = DAO.checkFirstLogin();
        if (checkFirstLogin.equals(0)) {
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
