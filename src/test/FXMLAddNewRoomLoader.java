
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package test;
//
//import java.io.IOException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
///**
// *
// * @author Doan Thanh Nhan
// */
//public class FXMLAddNewServiceTypeLoader extends Application {
//
//    @Override
//    public void start(Stage stage) {
//        try {
//            System.out.println("Kiểm tra : "+ getClass().getResource("/fxml/FXMLAddNewServiceType.fxml").getPath());
//            Parent root = FXMLLoader.load(getClass().getResource("/fxml/FXMLAddNewServiceType.fxml"));
//
//            Scene scene = new Scene(root);
//
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException ex) {
//            Logger.getLogger(FXMLAddNewServiceTypeLoader.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//}

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
import javafx.stage.Stage;

/**
 *
 * @author Doan Thanh Nhan
 */
public class FXMLAddNewRoomLoader extends Application {

    @Override
    public void start(Stage stage) {
        try {
            System.out.println("Kiểm tra : "+ getClass().getResource("/fxml/FXMLAddNewRoom.fxml").getPath());
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/FXMLAddNewRoom.fxml"));

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLAddNewRoomLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}

