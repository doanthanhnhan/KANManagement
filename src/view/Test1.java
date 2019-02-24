/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.application.Application;
import static javafx.application.Application.launch;
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
public class Test1 extends Application {

    public static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Parent root = null;
//        if(DAO.checkFirstLogin()==0){khi 
//            root = FXMLLoader.load(getClass().getResource("/fxml/FXMLAddNewEmployee.fxml"));
//            stage.setTitle("Add New Employee");
//        }
//        else{
        root = FXMLLoader.load(getClass().getResource("/fxml/FXMLFormInforOfGuest.fxml"));
//            stage.setTitle("KANManagementLogin");
//        }
//        stage.getIcons().add(new Image("/images/iconmanagement.png"));

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
