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
import static controllers.FXMLLoginController.User_Login;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.notificationFunction;
import utils.MD5Encrypt;
import utils.StageLoader;

/**
 *
 * @author Admin
 */
public class FXMLLoginController implements Initializable {

    public static String User_Login;
    private InfoEmployee Emp = new InfoEmployee();
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
    }

    @FXML
    private void handleLoginAction() throws ClassNotFoundException, SQLException, IOException {
        btnLogin.setDisable(true);
//        // Đoạn này làm loading (đang làm chạy vô tận)
//
//        // Khai báo stage nhìn xuyên thấu
//        final Stage stage = new Stage(StageStyle.TRANSPARENT);
//
//        // Chỗ này set khi mở cửa sổ con lên thì cha bị vô hiệu
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.setOpacity(0.5);
//
//        final Label status = new Label("Loading");
//        status.setStyle("-fx-text-fill: #008FC0; -fx-font-size : 20px; -fx-font-weight: bold;");
//        final ProgressIndicator indicator = new ProgressIndicator(ProgressIndicator.INDETERMINATE_PROGRESS);
//        indicator.setPrefSize(100, 100);
//        final Timeline timeline = new Timeline(
//                new KeyFrame(Duration.seconds(1), new EventHandler() {
//                    @Override
//                    public void handle(Event event) {
//                        String statusText = status.getText();
//                        status.setText(
//                                ("Loading . . .".equals(statusText))
//                                ? "Loading ."
//                                : statusText + " ."
//                        );
//                    }
//                }),
//                new KeyFrame(Duration.millis(1000))
//        );
//        timeline.setCycleCount(Timeline.INDEFINITE);
//        Platform.runLater(() -> {
//            VBox layout = new VBox();
//            layout.setAlignment(Pos.CENTER);
//            layout.setSpacing(10);
//            layout.getChildren().addAll(indicator, status);
//            layout.setStyle("-fx-padding: 10;");
//            stage.setScene(new Scene(layout, 150, 150));
//            stage.show();
//        });
//
//        timeline.play();

        StageLoader stageLoader = new StageLoader();
        stageLoader.loadingIndicator("Loading");

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
                    stageLoader.stopTimeline();
                    stageLoader.closeStage();
                });
            }
        });

        new Thread(loadOverview).start();

    }

    public void loginAction() throws ClassNotFoundException, SQLException, IOException {
        if (txtUserName.getText().equals("")) {
            Platform.runLater(() -> {
                notificationFunction.notification(txtUserName, hboxContent, "USER MUST NOT EMPTY !!!");
            });
        } else if (txtPassword.getText().equals("")) {
            Platform.runLater(() -> {
                notificationFunction.notificationPassword(txtPassword, hboxContent, "PASSWORD MUST NOT EMPTY !!!");
            });
        } else {
            try {
                MD5Encrypt m = new MD5Encrypt();
                String hashPass = m.hashPass(txtPassword.getText());
                String user = txtUserName.getText();
                Emp = DAO.getListCheckLogin(txtUserName.getText());
//      Xử lý trường hợp user ko tồn tại
                if (Emp == null) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            notificationFunction.notification(txtUserName, hboxContent, "USER OR PASSWORD WRONG!!!");
                        }
                    });
                } else {
                    //              Xử lý trường hợp User tồn tại nhưng đã bị block
                    if (!Emp.getActive()) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                notificationFunction.notification(txtUserName, hboxContent, "ACCOUNT IS LOCKED !!!");
                            }
                        });
                    } //          Xử lý trường hợp sai pass
                    else if (!hashPass.equals(Emp.getPassWord())) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    Calendar cal = Calendar.getInstance();
                                    String logtime;
                                    logtime = dateFormat.format(cal.getTime());
//                    Xử lý reset lại Check Login nếu qua 1 ngày mới
                                    if (!DAO.check_Time(user).equals(logtime)) {
                                        DAO.reset_CheckLogin(user, logtime);
                                    }
                                    notificationFunction.notification(txtUserName, hboxContent, "USER OR PASSWORD WRONG!!!");
                                    DAO.check_Login(txtUserName.getText(), logtime);
                                } catch (SQLException | ClassNotFoundException ex) {
                                    Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                    } else {
                        User_Login = txtUserName.getText();
                        Platform.runLater(new Runnable() {
                            Stage stage = (Stage) btnLogin.getScene().getWindow();

                            @Override
                            public void run() {
                                try {
                                    stage.close();
                                    Stage stageEdit = new Stage();
                                    Parent rootAdd = null;
//                                Xử lý trường hợp đăng nhập lần đầu tiên
                                    if (!DAO.checkSetPass(txtUserName.getText())) {
                                        stageEdit.resizableProperty().setValue(Boolean.FALSE);
                                        rootAdd = FXMLLoader.load(FXMLLoginController.this.getClass().getResource("/fxml/FXMLAccount.fxml"));
                                        stageEdit.setTitle("Set Password");
                                    } //     Xử lý trường hợp thông tin cơ bản của emloyees null
                                    else if (Emp.getId_number() == null || Emp.getAddress() == null
                                            || Emp.getBirthdate() == null || Emp.getPhone_No() == null) {
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

                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void handleForgetPass() throws IOException, SQLException, ClassNotFoundException {
        if ("".equals(txtUserName.getText())) {
            notificationFunction.notification(txtUserName, hboxContent, "USER MUST NOT EMPTY !!!");
        } else {
            Emp = DAO.getListCheckLogin(txtUserName.getText());
            if (Emp == null) {
                notificationFunction.notification(txtUserName, hboxContent, "ACCOUNT DOES NOT EXIST !!!");
            } else if (!Emp.getActive()) {
                notificationFunction.notification(txtUserName, hboxContent, "ACCOUNT IS LOCKED !!!");
            } else if (!DAO.checkSetPass(txtUserName.getText())) {
                notificationFunction.notification(txtUserName, hboxContent, "USER DOESN'T HAVE SECRET QUESTIONS!!!");
            } else {
                User_Login = txtUserName.getText();
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
            }
        }

    }
}
