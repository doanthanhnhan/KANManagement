/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import static controllers.ConnectControllers.fXMLMainFormController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.DAO;
import models.DAODepartMentReActive;
import models.DAOcheckRole;
import models.notificationFunction;
import utils.AlertLoginAgain;
import utils.FormatName;
import utils.GetInetAddress;
import utils.PatternValided;
import utils.StageLoader;
import utils.showFXMLLogin;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class FXMLAddDepartmentController implements Initializable {

    private showFXMLLogin showFormLogin = new showFXMLLogin();
    @FXML
    private AnchorPane anchorPaneAddDepartment;
    @FXML
    private VBox vBox_Employee_Info;
    @FXML
    private HBox HboxHeader;
    @FXML
    private HBox hBox_Info_Parent;
    @FXML
    private VBox vBox_Info_Left;
    @FXML
    private JFXTextField Username;
    @FXML
    private JFXTextField DepartmentID;
    @FXML
    private JFXTextField DepartmentName;
    @FXML
    private HBox HboxContent;
    @FXML
    private HBox Hboxbtn;
    @FXML
    private JFXButton btnInfo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnInfo.setOnAction((event) -> {
            try {
                btnSubmitAddDepartment();
            } catch (ClassNotFoundException | SQLException | IOException ex) {
                Logger.getLogger(FXMLAddDepartmentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        Username.setText(FXMLLoginController.User_Login);
        DepartmentID.setText("DPM-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSSSS")));
        DepartmentName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Platform.runLater(() -> {
                    HboxContent.getChildren().clear();
                    DepartmentName.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");

                });
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        btnSubmitAddDepartment();
                    } catch (ClassNotFoundException | SQLException | IOException ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    public void btnSubmitAddDepartment() throws ClassNotFoundException, SQLException, IOException {
        if (!DAOcheckRole.checkRoleDecentralization(FXMLLoginController.User_Login, "Department_Add")) {
            AlertLoginAgain.alertLogin();
            fXMLMainFormController = ConnectControllers.getfXMLMainFormController();
            Stage stageMainForm = (Stage) fXMLMainFormController.AnchorPaneMainForm.getScene().getWindow();
            Stage stage = (Stage) anchorPaneAddDepartment.getScene().getWindow();
            stage.close();
            stageMainForm.close();
            showFormLogin.showFormLogin();
        } else {
            btnInfo.setDisable(true);
            // Đoạn này làm loading (đang làm chạy vô tận)

            // Khai báo stage nhìn xuyên thấu
            StageLoader stageLoader = new StageLoader();
            stageLoader.loadingIndicator("Loading");
            Task loadOverview = new Task() {
                @Override
                protected Object call() throws Exception {
                    Platform.runLater(() -> {
                        HboxContent.getChildren().clear();
                        AddSubmitAction();
                    });
                    return null;
                }
            };

            loadOverview.setOnSucceeded(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    System.out.println("Finished");
                    Platform.runLater(() -> {
                        btnInfo.setDisable(false);
                        stageLoader.stopTimeline();
                        stageLoader.closeStage();
                    });
                }
            });
            new Thread(loadOverview).start();
        }
    }

    public void AddSubmitAction() {
        if (DepartmentName.getText().equals("")) {
            Platform.runLater(() -> {
                notificationFunction.notification(DepartmentName, HboxContent, "DEPARTMENT NAME MUST NOT EMPTY !!!");
            });
        } else if (!PatternValided.PatternName(DepartmentName.getText())) {
            Platform.runLater(() -> {
                notificationFunction.notification(DepartmentName, HboxContent, "DEPARTMENT NAME DOES NOT EXIST !!! ");
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            });
        } else {
            try {
                DAODepartMentReActive.Add_New_Department(DepartmentID.getText(), FormatName.format(DepartmentName.getText()), Username.getText());
            } catch (MalformedURLException | SQLException | ClassNotFoundException ex) {
                Logger.getLogger(FXMLAddDepartmentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            DAO.setUserLogs_With_MAC(FXMLLoginController.User_Login, "Add Department Id "+ DepartmentID.getText(),
                                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")),GetInetAddress.getMacAddress());
            DepartmentID.setText("DPM-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSSSS")));
            DepartmentName.setText("");
        }
    }
}
