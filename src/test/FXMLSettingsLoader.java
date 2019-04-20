/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Doan Thanh Nhan
 */
public class FXMLSettingsLoader extends Application {

    @Override
    public void start(Stage stage) {
        try {
            System.out.println("Kiểm tra : "+ getClass().getResource("/fxml/FXMLSettings.fxml").getPath());
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/FXMLSettings.fxml"));

            Scene scene = new Scene(root);
            stage.setTitle("Settings");
            stage.getIcons().add(new Image("/images/KAN Logo.png"));
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLSettingsLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
