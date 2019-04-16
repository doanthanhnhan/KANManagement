/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.DAO;
import utils.Email;
import utils.MD5Encrypt;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import models.DAODepartMentReActive;
import models.DAODepartmentDecentralization;
import models.DAOReActive;
import models.DAOcheckRole;
import models.InfoEmployee;
import models.notificationFunction;
import utils.AlertLoginAgain;
import utils.PatternValided;
import utils.FormatName;
import utils.GetInetAddress;
import utils.StageLoader;
import utils.showFXMLLogin;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class FXMLAddNewEmloyeeController implements Initializable {

    private showFXMLLogin showFormLogin = new showFXMLLogin();
    @FXML
    private JFXTextField newGmail;
    @FXML
    private JFXTextField newFirstname;
    @FXML
    private JFXTextField newMidname;
    @FXML
    private JFXTextField newLastname;
    @FXML
    private JFXTextField newId;
    @FXML
    private JFXRadioButton sexMale;
    @FXML
    private JFXRadioButton sexFemale;
    @FXML
    private JFXButton btnAddNew;
    @FXML
    private HBox HboxContent;
    private FXMLDecentralizationController fXMLDecentralizationController;
    private FXMLListEmployeeController fXMLListEmployeeController;
    private FXMLMainFormController fXMLMainFormController;
    @FXML
    private AnchorPane anchorPaneAddEmployee;
    @FXML
    private ToggleGroup newSexEmployee;
    @FXML
    private JFXComboBox<String> boxDepartment;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            boxDepartment.setItems(DAODepartMentReActive.get_All_DepartmentName());
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLAddNewEmloyeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (DAO.checkFirstLogin().equals(0)) {
                newId.setDisable(true);
                newId.setText("admin");
                boxDepartment.setDisable(true);
                ObservableList<String> list = FXCollections.observableArrayList();
                list.add("DPM-Admin");
                boxDepartment.setItems(list);
                boxDepartment.setValue("DPM-Admin");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLAddNewEmloyeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
//        set entersubmit
        boxDepartment.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                System.out.println("Enter pressed");
                try {
                    btnSubmitAddNewEmployee();
                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        boxDepartment.valueProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null && !newItem.equals("")) {
                HboxContent.getChildren().clear();
                boxDepartment.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            }
        });
        newId.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Platform.runLater(() -> {
                    HboxContent.getChildren().clear();
                    newId.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");

                });
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        btnSubmitAddNewEmployee();
                    } catch (ClassNotFoundException | SQLException | IOException ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        newGmail.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Platform.runLater(() -> {
                    HboxContent.getChildren().clear();
                    newGmail.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");

                });
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        btnSubmitAddNewEmployee();
                    } catch (ClassNotFoundException | SQLException | IOException ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        newFirstname.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Platform.runLater(() -> {
                    HboxContent.getChildren().clear();
                    newFirstname.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");

                });
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        btnSubmitAddNewEmployee();
                    } catch (ClassNotFoundException | SQLException | IOException ex) {
                        Logger.getLogger(FXMLAddNewEmloyeeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        newLastname.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Platform.runLater(() -> {
                    HboxContent.getChildren().clear();
                    newLastname.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");

                });
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        btnSubmitAddNewEmployee();
                    } catch (ClassNotFoundException | SQLException | IOException ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        newMidname.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Platform.runLater(() -> {
                    HboxContent.getChildren().clear();
                    newMidname.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                });
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        btnSubmitAddNewEmployee();
                    } catch (ClassNotFoundException | SQLException | IOException ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    @FXML
    private void btnSubmitAddNewEmployee() throws ClassNotFoundException, SQLException, IOException {
        if (!DAO.checkFirstLogin().equals(0) && !DAOcheckRole.checkRoleDecentralization(FXMLLoginController.User_Login, "Employee_Add")) {
            AlertLoginAgain.alertLogin();
            fXMLMainFormController = ConnectControllers.getfXMLMainFormController();
            Stage stageMainForm = (Stage) fXMLMainFormController.AnchorPaneMainForm.getScene().getWindow();
            Stage stage = (Stage) anchorPaneAddEmployee.getScene().getWindow();
            stage.close();
            stageMainForm.close();
            showFormLogin.showFormLogin();
        } else {
            if (FXMLListEmployeeController.check_Edit_Action) {
                fXMLListEmployeeController = ConnectControllers.getfXMLListEmployeeController();
                fXMLListEmployeeController.check_Add_New = true;
                fXMLListEmployeeController.new_Emp_ID = newId.getText();
            }

            btnAddNew.setDisable(true);
            // Đoạn này làm loading (đang làm chạy vô tận)

            // Khai báo stage nhìn xuyên thấu
            StageLoader stageLoader = new StageLoader();
            stageLoader.loadingIndicator("Sending Email");
            Task loadOverview = new Task() {
                @Override
                protected Object call() throws Exception {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            HboxContent.getChildren().clear();
                        }
                    });
                    AddNewEmployee();
                    return null;
                }
            };

            loadOverview.setOnSucceeded(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    System.out.println("Finished");
                    Platform.runLater(() -> {
                        btnAddNew.setDisable(false);
                        stageLoader.stopTimeline();
                        stageLoader.closeStage();
                    });
                }
            });
            new Thread(loadOverview).start();
        }
    }

    private void AddNewEmployee() throws ClassNotFoundException, SQLException, IOException, Exception {

//        valided texfield
        if (newId.getText().equals("")) {
            Platform.runLater(() -> {
                notificationFunction.notification(newId, HboxContent, "ID MUST NOT EMPTY !!!");
            });
        } else if (newGmail.getText().equals("")) {
            Platform.runLater(() -> {
                notificationFunction.notification(newGmail, HboxContent, "GMAIL MUST NOT EMPTY !!!");
            });
        } else if (newFirstname.getText().equals("")) {
            Platform.runLater(() -> {
                notificationFunction.notification(newFirstname, HboxContent, "FIRST NAME MUST NOT EMPTY !!!");
            });

        } else if (newLastname.getText().equals("")) {
            Platform.runLater(() -> {
                notificationFunction.notification(newLastname, HboxContent, "LAST NAME MUST NOT EMPTY !!!");
            });

        } else if (boxDepartment.getValue() == null) {
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(350, 35);
                label.setText("DEPARTMENT MUST NOT EMPTY !!!");
                label.setAlignment(Pos.CENTER_LEFT);
                boxDepartment.getStyleClass().removeAll();
                boxDepartment.getStyleClass().add("jfx-combo-box-fault");
                HboxContent.setSpacing(10);
                HboxContent.setAlignment(Pos.CENTER_LEFT);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
                boxDepartment.requestFocus();
            });
        } else if (!PatternValided.PatternID(newId.getText())) {
            Platform.runLater(() -> {
                notificationFunction.notification(newId, HboxContent, "ID MUST 4-12 CHARACTER, NOT BEGIN NUMBER AND CHARACTER SPECIAL !!!");
            });

        } else if (!PatternValided.PatternEmail(newGmail.getText())) {
            Platform.runLater(() -> {
                notificationFunction.notification(newGmail, HboxContent, "EMAIL DOES NOT EXIST!!!");
            });
        } else if (!PatternValided.PatternName(newFirstname.getText())) {
            Platform.runLater(() -> {
                notificationFunction.notification(newFirstname, HboxContent, "FIRSTNAME DOES NOT EXIST !!! (Example: Nguyễn, Lê,...) ");
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            });

        } else if (!PatternValided.PatternName(newMidname.getText()) && !newMidname.getText().equals("")) {
            Platform.runLater(() -> {
                notificationFunction.notification(newMidname, HboxContent, "MIDNAME DOES NOT EXIST !!!(Example: Thị,Văn,...");
            });

        } else if (!PatternValided.PatternName(newLastname.getText())) {
            Platform.runLater(() -> {
                notificationFunction.notification(newLastname, HboxContent, "LASTNAME DOES NOT EXIST !!!(Example: Toàn, Văn,...");
            });

        } else {
            if (DAO.check_Id(newId.getText()) && DAOReActive.checkActiveEmployee(newId.getText())) {
                Platform.runLater(() -> {
                    notificationFunction.notification(newId, HboxContent, "ID ALREADY EXIST !!!");
                });
            } else if (DAO.check_Id(newId.getText()) && !DAOReActive.checkActiveEmployee(newId.getText())) {
                Platform.runLater(() -> {
                    notificationFunction.notification(newId, HboxContent, "ID ALREADY EXIST !!!");
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Error");
                    alert1.setHeaderText("You cannot create this account !!!");
                    alert1.setContentText("Because the account already exist and has been locked please reactivate to use this account !!!");
                    alert1.showAndWait();
                });
            } else if (DAO.check_Email(newGmail.getText())) {
                System.out.println("vao else if gmail");
                Platform.runLater(() -> {
                    notificationFunction.notification(newGmail, HboxContent, "EMAIL ALREADY EXIST !!!");
                });
            } else {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (DAO.checkFirstLogin().equals(0)) {
                                DAODepartMentReActive.Add_New_Department("DPM-Admin", "Admin", "admin");
                                DAODepartmentDecentralization.update_DepartmentDecentralization("DPM-Admin", true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true,
                                        true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true);
                            }
                            Boolean Sex;
                            Sex = sexMale.selectedProperty().getValue();
                            InfoEmployee Emp = new InfoEmployee();
                            Emp.setId_number(newId.getText());
                            Emp.setFirst_Name(FormatName.format(newFirstname.getText()));
                            Emp.setMid_Name(FormatName.format(newMidname.getText()));
                            Emp.setLast_Name(FormatName.format(newLastname.getText()));
                            Emp.setGmail(newGmail.getText());
                            Emp.setSex(Sex);
                            Emp.setWork_Dept(boxDepartment.getValue());
                            DAO.AddNewEmployee(Emp);
                            String Username = newId.getText();
                            MD5Encrypt m;
                            m = new MD5Encrypt();
                            String Password = m.hashPass("123456");
                            DAO.AddUser(newId.getText(), Username, Password, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
                            DAO.setRoleUser(newId.getText());
                            DAO.Set_Role_Employee(Emp.getWork_Dept(), newId.getText());
                            String content = "Username: " + newId.getText() + ", Password: 123456";
                            Email.send_Email_Without_Attach(newGmail.getText(), "Default username and password", content);
                            if (!DAO.checkFirstLogin().equals(1)) {
                                DAO.setUserLogs_With_MAC(FXMLLoginController.User_Login, "Create " + newId.getText(),
                                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), GetInetAddress.getMacAddress());
                            }
                            newFirstname.setText("");
                            newMidname.setText("");
                            newLastname.setText("");
                            newGmail.setText("");
                            newId.setText("");
                            newId.requestFocus();
                            boxDepartment.setValue(null);
                            if (FXMLListEmployeeController.check_Edit_Action) {
                                fXMLListEmployeeController = ConnectControllers.getfXMLListEmployeeController();
                                fXMLListEmployeeController.showUsersData();
                            }
                            if (FXMLDecentralizationController.check_form_list) {
                                fXMLDecentralizationController = ConnectControllers.getfXMLDecentralizationController();
                                fXMLListEmployeeController.showUsersData();
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(FXMLAddNewEmloyeeController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(FXMLAddNewEmloyeeController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (Exception ex) {
                            Logger.getLogger(FXMLAddNewEmloyeeController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }
            if (DAO.checkFirstLogin().equals(0)) {
                Platform.runLater(() -> {
                    try {
                        Stage stage = (Stage) btnAddNew.getScene().getWindow();
                        stage.close();
                        showFormLogin.showFormLogin();
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLAddNewEmloyeeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        }
    }
}
