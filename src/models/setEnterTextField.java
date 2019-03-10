///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package models;
//
//import com.jfoenix.controls.JFXTextField;
//import controllers.ConnectControllers;
//import controllers.FXMLLoginController;
//import java.io.IOException;
//import java.net.URL;
//import java.sql.SQLException;
//import java.util.ResourceBundle;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javafx.application.Platform;
//import javafx.event.EventHandler;
//import javafx.fxml.Initializable;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyEvent;
//import javafx.scene.layout.HBox;
//
///**
// *
// * @author Admin
// */
//public class setEnterTextField implements Initializable {
//
//    FXMLLoginController loginController = ConnectControllers.getfXMLLoginController();
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        ConnectControllers.setSETEnterTextField(this);
//    }
//
//    public void setEnter(JFXTextField textField, HBox hboxContent) {
//        textField.setOnKeyPressed(
//                new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event
//            ) {
//                Platform.runLater(() -> {
//                    hboxContent.getChildren().clear();
//                    textField.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
//                    if (event.getCode() == KeyCode.ENTER) {
//                        try {
//                            System.out.println("User sai");
//                            functionLogin();
//                        } catch (ClassNotFoundException | SQLException | IOException ex) {
//                            Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    }
//                });
//
//            }
//        }
//        );
//    }
//
//    public void functionLogin() throws ClassNotFoundException, SQLException, IOException {
//        Platform.runLater(() -> {
//            try {
//                loginController.loginAction();
//            } catch (ClassNotFoundException | SQLException | IOException ex) {
//                Logger.getLogger(setEnterTextField.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        });
//
//    }
//
//}
