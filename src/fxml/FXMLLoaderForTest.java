/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxml;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Doan Thanh Nhan
 */
public class FXMLLoaderForTest extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("Kiểm tra : "+ getClass().getResource("FXMLMainForm.fxml").getPath());
        Parent root = FXMLLoader.load(getClass().getResource("FXMLMainOverViewPane.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
