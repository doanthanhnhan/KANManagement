/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXTabPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Doan Thanh Nhan
 */
public class FXMLMainFormController implements Initializable {

    @FXML
    private MenuBar mainMenuBar;
    @FXML
    private JFXTabPane mainTabPane;
    @FXML
    private Button btn_Toolbar_Home;
    @FXML
    private Button btn_Toolbar_Booking;
    @FXML
    private Button btn_Toolbar_Checkin;
    @FXML
    private Button btn_Toolbar_Checkout;
    @FXML
    private Button btn_Toolbar_Services;
    @FXML
    private Button btn_Toolbar_Employees;
    @FXML
    private Button btn_Toolbar_Charts;
    @FXML
    private Button btn_Toolbar_Accounting;
    @FXML
    private Button btn_Toolbar_Settings;
    @FXML
    private Button btn_Toolbar_User_Logout;
    @FXML
    private ProgressBar progressBar_MainTask;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private void initMenuBar() {
        MenuItem regis = new MenuItem("Registration User");
        regis.setOnAction((event) -> {
            System.out.println("Registration menu item clicked!");
            try {
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/FXMLAddNewEmployee.fxml"));
                stage.getIcons().add(new Image("/images/iconmanagement.png"));
                Scene scene = new Scene(root);
                stage.setTitle("Add New Employee");
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            } catch (IOException ex) {
                Logger.getLogger(FXMLMainFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        Menu menuRegistration = new Menu("Registration");
        menuRegistration.getItems().add(regis);
        mainMenuBar.getMenus().add(menuRegistration);
    }

    private void initTabPane() {
        try {
            // Get content from fxml file
            AnchorPane overviewPane = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/FXMLMainOverViewPane.fxml"));
            // Add fxml content to a tab
            Tab overViewTab = new Tab("Overview");
            overViewTab.setContent(overviewPane);
            mainTabPane.getTabs().add(overViewTab);
        } catch (IOException ex) {
            Logger.getLogger(FXMLMainFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void homeAction(ActionEvent event) {
//        ObservableList<InfoEmployee> list_Employee = FXCollections.observableArrayList();
//        list_Employee = FXMLLoginController.List_EmployeeLogin;
//        String userRole = list_Employee.get(0).getRole();
        String userRole = "Admin";
        if (userRole.equals("Admin")) {
            initMenuBar();
        }
//        progressBar_MainTask = new ProgressBar();
//        progressBar_MainTask.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        Task loadOverview = new Task() {
            @Override
            protected Object call() throws Exception {
                System.out.println("Loading...");
//                progressBar_MainTask.progressProperty().unbind();
//                progressBar_MainTask.setProgress(0.2);
                try {
                    // Get content from fxml file
                    AnchorPane overviewPane = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/FXMLMainOverViewPane.fxml"));

                    // Add fxml content to a tab
                    Tab overViewTab = new Tab("Overview");
                    overViewTab.setContent(overviewPane);
                    Platform.runLater(() -> {
//                        progressBar_MainTask.progressProperty().unbind();
//                        progressBar_MainTask.setProgress(0.6);
                        mainTabPane.getTabs().add(overViewTab);

                        btn_Toolbar_Home.setDisable(true);
                    });

                } catch (IOException ex) {
                    Logger.getLogger(FXMLMainFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }
        };
        progressBar_MainTask.progressProperty().unbind();
        progressBar_MainTask.progressProperty().bind(loadOverview.progressProperty());

        loadOverview.setOnSucceeded(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                System.out.println("Finished");
                Platform.runLater(() -> {
                    progressBar_MainTask.progressProperty().unbind();
                    progressBar_MainTask.setProgress(1);
                    btn_Toolbar_Home.setDisable(false);
                });
            }
        });

        new Thread(loadOverview).start();
    }

    @FXML
    private void userLogOutAction(ActionEvent event) {
        System.exit(0);
    }

}
