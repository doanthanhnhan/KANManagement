/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import models.DAO;
import models.InfoEmployee;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import utils.MD5Encrypt;

/**
 *
 * @author Admin
 */
public class FXMLLoginController implements Initializable {

    public static ObservableList<InfoEmployee> List_Employee = FXCollections.observableArrayList();
    public static ObservableList<InfoEmployee> List_EmployeeLogin = FXCollections.observableArrayList();
    public static ObservableList<InfoEmployee> employeeForget = FXCollections.observableArrayList();
    private ObservableList<InfoEmployee> List_Check_Login = FXCollections.observableArrayList();
    @FXML
    private AnchorPane formLogin;
    @FXML
    private JFXTextField txtUserName;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private HBox hBoxPassword;
    @FXML
    private JFXButton btnLogin;
    @FXML
    private HBox hboxContent;

    public static Boolean checkLoginRegis = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtUserName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Platform.runLater(() -> {
                    hboxContent.getChildren().clear();
                    txtUserName.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                });
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        handleLoginAction();
                    } catch (ClassNotFoundException | SQLException | IOException ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        txtPassword.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Platform.runLater(() -> {
                    hboxContent.getChildren().clear();
                    txtPassword.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                });
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        handleLoginAction();
                    } catch (ClassNotFoundException | SQLException | IOException ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        ConnectControllers.setfXMLLoginController(this);
        try {
            List_Employee = DAO.getAllEmployee();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleLoginAction() throws ClassNotFoundException, SQLException, IOException {
        btnLogin.setDisable(true);
        // Đoạn này làm loading (đang làm chạy vô tận)

        // Khai báo stage nhìn xuyên thấu
        final Stage stage = new Stage(StageStyle.TRANSPARENT);

        // Chỗ này set khi mở cửa sổ con lên thì cha bị vô hiệu
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOpacity(0.5);

        final Label status = new Label("Loading");
        status.setStyle("-fx-text-fill: #008FC0; -fx-font-size : 20px; -fx-font-weight: bold;");
        final ProgressIndicator indicator = new ProgressIndicator(ProgressIndicator.INDETERMINATE_PROGRESS);
        indicator.setPrefSize(100, 100);
        final Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        String statusText = status.getText();
                        status.setText(
                                ("Loading . . .".equals(statusText))
                                ? "Loading ."
                                : statusText + " ."
                        );
                    }
                }),
                new KeyFrame(Duration.millis(1000))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        Platform.runLater(() -> {
            VBox layout = new VBox();
            layout.setAlignment(Pos.CENTER);
            layout.setSpacing(10);
            layout.getChildren().addAll(indicator, status);
            layout.setStyle("-fx-padding: 10;");
            stage.setScene(new Scene(layout, 150, 150));
            stage.show();
        });

        timeline.play();
        Task loadOverview = new Task() {
            @Override
            protected Object call() throws Exception {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        hboxContent.getChildren().clear();
                    }
                });
                loginAction();
                return null;
            }
        };

        loadOverview.setOnSucceeded(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                System.out.println("Finished");
                Platform.runLater(() -> {
                    btnLogin.setDisable(false);
                    timeline.stop();
                    stage.close();
                });
            }
        });

        new Thread(loadOverview).start();

    }

    public void loginAction() throws ClassNotFoundException, SQLException, IOException {
        if (txtUserName.getText().equals("")) {
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(300, 35);
                label.setText("USER MUST NOT EMPTY !!!");
                txtUserName.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
                hboxContent.setAlignment(Pos.CENTER);
                hboxContent.setSpacing(10);
                hboxContent.getChildren().clear();
                hboxContent.getChildren().add(icon);
                hboxContent.getChildren().add(label);
                txtUserName.requestFocus();
            });
        } else if (txtPassword.getText().equals("")) {
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(300, 35);
                label.setText("PASSWORD MUST NOT EMPTY !!!");
                txtPassword.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
                hboxContent.setAlignment(Pos.CENTER);
                hboxContent.setSpacing(10);
                hboxContent.getChildren().clear();
                hboxContent.getChildren().add(icon);
                hboxContent.getChildren().add(label);
                txtPassword.requestFocus();
            });
        } else {
            boolean checklogin = false;
            List_Check_Login = DAO.getAllEmployee();
            for (InfoEmployee infoEmployee : List_Check_Login) {
                MD5Encrypt m = new MD5Encrypt();
                String hashPass = m.hashPass(txtPassword.getText());
                if (txtUserName.getText().equals(infoEmployee.getUserName()) && hashPass.equals(infoEmployee.getPassWord())) {
                    if (DAO.check_Active(txtUserName.getText()).equals(0)) {
                        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                        icon.setSize("16");
                        icon.setStyleClass("jfx-glyhp-icon");
                        Label label = new Label();
                        label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                        label.setPrefSize(300, 35);
                        label.setText("ACCOUNT IS LOCKED !!!");
                        Platform.runLater(() -> {
                            hboxContent.setAlignment(Pos.CENTER);
                            hboxContent.setSpacing(10);
                            hboxContent.getChildren().clear();
                            hboxContent.getChildren().add(icon);
                            hboxContent.getChildren().add(label);
                        });
                    } else {
                        checklogin = true;
                        List_EmployeeLogin.add(infoEmployee);
                        Stage stage = (Stage) btnLogin.getScene().getWindow();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    stage.close();
                                    Stage stageEdit = new Stage();
                                    Parent rootAdd = null;
                                    if (DAO.checkSetPass(txtUserName.getText()) == 0) {
                                        stageEdit.resizableProperty().setValue(Boolean.FALSE);
                                        rootAdd = FXMLLoader.load(FXMLLoginController.this.getClass().getResource("/fxml/FXMLAccount.fxml"));
                                        stageEdit.setTitle("Set Password");
                                    } else if (infoEmployee.getId_number() == null || infoEmployee.getAddress() == null
                                            || infoEmployee.getBirthdate() == null || infoEmployee.getPhone_No() == null) {
                                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        Calendar cal = Calendar.getInstance();
                                        String logtime;
                                        logtime = dateFormat.format(cal.getTime());
                                        DAO.setUserLogs(txtUserName.getText(), "Login", logtime);
                                        DAO.reset_CheckLogin(txtUserName.getText(), logtime);
                                        checkLoginRegis = true;
                                        rootAdd = FXMLLoader.load(FXMLLoginController.this.getClass().getResource("/fxml/FXMLInfoEmployee.fxml"));
                                        stageEdit.setTitle("Add Info Employee");
                                        stageEdit.initStyle(StageStyle.UNDECORATED);

                                    } else {
                                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        Calendar cal = Calendar.getInstance();
                                        String logtime;
                                        logtime = dateFormat.format(cal.getTime());
                                        DAO.setUserLogs(txtUserName.getText(), "Login", logtime);
                                        DAO.reset_CheckLogin(txtUserName.getText(), logtime);
                                        rootAdd = FXMLLoader.load(FXMLLoginController.this.getClass().getResource("/fxml/FXMLMainForm.fxml"));
                                        stageEdit.setTitle("KANManagement");
                                    }
                                    Scene scene1;
                                    scene1 = new Scene(rootAdd);
                                    stageEdit.getIcons().add(new Image("/images/KAN Logo.png"));
                                    stageEdit.setScene(scene1);
                                    stageEdit.show();
                                } catch (ClassNotFoundException | SQLException | IOException ex) {
                                    Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                    }
                    break;
                } else if (txtUserName.getText().equals(infoEmployee.getUserName()) && !hashPass.equals(infoEmployee.getPassWord())) {
                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                    icon.setSize("16");
                    icon.setStyleClass("jfx-glyhp-icon");
                    Label label = new Label();
                    label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                    label.setPrefSize(300, 35);
                    label.setText("USER OR PASSWORD WRONG!!!");
                    Platform.runLater(() -> {
                        hboxContent.setAlignment(Pos.CENTER);
                        hboxContent.setSpacing(10);
                        hboxContent.getChildren().clear();
                        hboxContent.getChildren().add(icon);
                        hboxContent.getChildren().add(label);
                    });
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar cal = Calendar.getInstance();
                    String logtime;
                    logtime = dateFormat.format(cal.getTime());

                    if (!DAO.check_Time(txtUserName.getText()).equals(logtime)) {
                        DAO.reset_CheckLogin(txtUserName.getText(), logtime);
                    }
                    DAO.check_Login(txtUserName.getText(), logtime);
                    break;
                }
            }
            if (!checklogin) {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(300, 35);
                label.setText("USER OR PASSWORD WRONG!!!");
                Platform.runLater(() -> {
                    hboxContent.setAlignment(Pos.CENTER);
                    hboxContent.setSpacing(10);
                    hboxContent.getChildren().clear();
                    hboxContent.getChildren().add(icon);
                    hboxContent.getChildren().add(label);
                });
            }
        }
    }

    @FXML
    private void handleForgetPass() throws IOException, SQLException, ClassNotFoundException {
        if ("".equals(txtUserName.getText())) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(300, 35);
            label.setText("USER MUST NOT EMPTY !!!");
            txtUserName.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            hboxContent.setAlignment(Pos.CENTER);
            hboxContent.setSpacing(10);
            hboxContent.getChildren().clear();
            hboxContent.getChildren().add(icon);
            hboxContent.getChildren().add(label);
            txtUserName.requestFocus();
        } else if (DAO.checkSetPass(txtUserName.getText()) == 0) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(300, 35);
            label.setText("USER DOESN'T HAVE SECRET QUESTIONS!!!");
            txtUserName.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            hboxContent.setAlignment(Pos.CENTER);
            hboxContent.setSpacing(10);
            hboxContent.getChildren().clear();
            hboxContent.getChildren().add(icon);
            hboxContent.getChildren().add(label);
            txtUserName.requestFocus();
        } else {
            System.out.println(List_Employee.size());
            for (int i = 0; i < List_Employee.size(); i++) {
                if (txtUserName.getText().equals(List_Employee.get(i).getUserName())) {
                    if (DAO.check_Active(txtUserName.getText()).equals(0)) {
                        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                        icon.setSize("16");
                        icon.setStyleClass("jfx-glyhp-icon");
                        Label label = new Label();
                        label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                        label.setPrefSize(300, 35);
                        label.setText("ACCOUNT IS LOCKED !!!");
                        hboxContent.setAlignment(Pos.CENTER);
                        hboxContent.setSpacing(10);
                        hboxContent.getChildren().clear();
                        hboxContent.getChildren().add(icon);
                        hboxContent.getChildren().add(label);

                    } else {
                        employeeForget.add(List_Employee.get(i));
                        Stage stageForget = new Stage();
                        stageForget.initModality(Modality.APPLICATION_MODAL);
                        // make its owner the existing window:
                        stageForget.resizableProperty().setValue(Boolean.FALSE);
                        Parent rootAdd = FXMLLoader.load(getClass().getResource("/fxml/FXMLForgetPass.fxml"));
                        Scene sceneForget;
                        sceneForget = new Scene(rootAdd);
                        stageForget.setTitle("Forget Password");
                        stageForget.getIcons().add(new Image("/images/iconmanagement.png"));
                        stageForget.setScene(sceneForget);
                        stageForget.show();
                        break;
                    }
                } else {
                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                    icon.setSize("16");
                    icon.setStyleClass("jfx-glyhp-icon");
                    Label label = new Label();
                    label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                    label.setPrefSize(300, 35);
                    label.setText("ACCOUNT INVALID !!!");
                    hboxContent.setAlignment(Pos.CENTER);
                    hboxContent.setSpacing(10);
                    hboxContent.getChildren().clear();
                    hboxContent.getChildren().add(icon);
                    hboxContent.getChildren().add(label);
                    txtUserName.requestFocus();
                }

            }
        }

    }
}
