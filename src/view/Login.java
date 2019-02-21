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

/**
 *
 * @author Admin
 */
public class Login extends Application {
    public static Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
//        if( nếu có dữ liệ trong database){
//            chạy form login
//        }
//        else{
//            chạy form regis
//        }
        this.stage=stage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/FXMLMainForm.fxml"));
        stage.getIcons().add(new Image("/images/iconmanagement.png"));
        Scene scene = new Scene(root);
        stage.setTitle("KANManagement");
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
