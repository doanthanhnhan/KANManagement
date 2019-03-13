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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.DAO;
import utils.Email;
import utils.MD5Encrypt;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import models.InfoEmployee;
import models.notificationFunction;
import utils.PatternValided;
import utils.FormatName;
import utils.StageLoader;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class FXMLAddNewEmloyeeController implements Initializable {

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
    private FXMLListEmployeeController fXMLListEmployeeController;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fXMLListEmployeeController = ConnectControllers.getfXMLListEmployeeController();
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
                    } catch (Exception ex) {
                        Logger.getLogger(FXMLAddNewEmloyeeController.class.getName()).log(Level.SEVERE, null, ex);
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
                    } catch (Exception ex) {
                        Logger.getLogger(FXMLAddNewEmloyeeController.class.getName()).log(Level.SEVERE, null, ex);
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
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
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
                    } catch (Exception ex) {
                        Logger.getLogger(FXMLAddNewEmloyeeController.class.getName()).log(Level.SEVERE, null, ex);
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
                    } catch (Exception ex) {
                        Logger.getLogger(FXMLAddNewEmloyeeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        ObservableList listRole = FXCollections.observableArrayList();
    }

    @FXML
    private void btnSubmitAddNewEmployee() throws ClassNotFoundException, SQLException, IOException {
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
            if (DAO.check_Id(newId.getText())) {
                Platform.runLater(() -> {
                    notificationFunction.notification(newId, HboxContent, "ID ALREADY EXIST !!!");
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
                            Boolean Sex;
                            Sex = sexMale.selectedProperty().getValue();
                            InfoEmployee Emp = new InfoEmployee();
                            Emp.setId_number(newId.getText());
                            Emp.setFirst_Name(FormatName.format(newFirstname.getText()));
                            Emp.setMid_Name(FormatName.format(newMidname.getText()));
                            Emp.setLast_Name(FormatName.format(newLastname.getText()));
                            Emp.setGmail(newGmail.getText());
                            Emp.setSex(Sex);
                            DAO.AddNewEmployee(Emp);
                            String Username = newId.getText();
                            MD5Encrypt m;
                            m = new MD5Encrypt();
                            String Password = m.hashPass("123456");
                            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            Calendar cal = Calendar.getInstance();
                            String logtime;
                            logtime = dateFormat.format(cal.getTime());
                            DAO.AddUser(newId.getText(), Username, Password, logtime);
                            String content = "Username: " + newId.getText() + ", Password: 123456";
                            Email.send_Email_Without_Attach("smtp.gmail.com", newGmail.getText(), "KANManagement.AP146@gmail.com",
                                    "KAN@123456", "Default username and password", content);
                            if (DAO.checkFirstLogin() != 1) {
                                DAO.setUserLogs(FXMLLoginController.User_Login, "Create " + newId.getText(), logtime);
                            }
                            newFirstname.setText("");
                            newMidname.setText("");
                            newLastname.setText("");
                            newGmail.setText("");
                            newId.setText("");
                            newId.requestFocus();
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
                    Stage stage = (Stage) btnAddNew.getScene().getWindow();
                    stage.close();
                    Stage stageEdit = new Stage();
                    stageEdit.resizableProperty().setValue(Boolean.FALSE);
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("/fxml/FXMLLogin.fxml"));
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLAddNewEmloyeeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    stageEdit.getIcons().add(new Image("/images/iconmanagement.png"));
                    Scene scene = new Scene(root);
                    stageEdit.setTitle("KANManagement");
                    stageEdit.setScene(scene);
                    stageEdit.show();
                });
            }
        }
    }
}
