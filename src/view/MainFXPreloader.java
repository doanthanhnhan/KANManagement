/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.jfoenix.controls.JFXProgressBar;
import controllers.ConnectControllers;
import controllers.FXMLSplashScreenController;
import java.io.IOException;
import javafx.application.Preloader;
import javafx.application.Preloader.ProgressNotification;
import javafx.application.Preloader.StateChangeNotification;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Doan Thanh Nhan
 */
public class MainFXPreloader extends Preloader {

    FXMLSplashScreenController splashScreenController;    
    Stage stage;
    Scene scene;

    @Override
    public void init() throws Exception {        
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/FXMLSplashScreen.fxml"));
        scene = new Scene(root);
        splashScreenController = ConnectControllers.getfXMLSplashScreenController();      
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.getIcons().add(new Image("/images/KAN Logo.png"));
        stage.show();
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification scn) {
        if (scn.getType() == StateChangeNotification.Type.BEFORE_START) {
            stage.hide();
        }
    }

    @Override
    public void handleProgressNotification(ProgressNotification pn) {
        super.handleProgressNotification(pn); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void handleApplicationNotification(PreloaderNotification info) {
        if (info instanceof ProgressNotification) {
            if(((Double)((ProgressNotification) info).getProgress()).intValue() <= 70){
                splashScreenController.getLabel_loading().setText("Loading database " 
                    + ((Double)((ProgressNotification) info).getProgress()).intValue() + "%");
            } else {
                splashScreenController.getLabel_loading().setText("Preparing scene " 
                    + ((Double)((ProgressNotification) info).getProgress()).intValue() + "%");
            }
                       
            splashScreenController.getProgressBar().setProgress(((ProgressNotification) info).getProgress() / 100);
        }
    }
   
}
