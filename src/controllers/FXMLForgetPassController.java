/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.DAO;
import models.InfoEmployee;
import models.notificationFunction;
import utils.GetInetAddress;
import utils.PatternValided;
import utils.MD5Encrypt;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class FXMLForgetPassController implements Initializable {

    @FXML
    private JFXTextField txtForgetUsername;
    @FXML
    private JFXPasswordField txtForgetPassword;
    @FXML
    private JFXPasswordField txtForgetConfirmPassword;
    @FXML
    private JFXComboBox<String> txtSerectQuestion;
    @FXML
    private JFXPasswordField textSerectAnswer;
    @FXML
    private JFXButton btnSubmit;
    @FXML
    private HBox HboxContent;
    private String UserForget = FXMLLoginController.User_Login;
    private InfoEmployee Emp = new InfoEmployee();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        txtForgetPassword.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                txtForgetPassword.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                HboxContent.getChildren().clear();
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        handleForgetAction();
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        txtForgetConfirmPassword.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                txtForgetConfirmPassword.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                HboxContent.getChildren().clear();
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        handleForgetAction();
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        textSerectAnswer.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                textSerectAnswer.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                HboxContent.getChildren().clear();
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        handleForgetAction();
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        txtSerectQuestion.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                textSerectAnswer.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                HboxContent.getChildren().clear();
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        handleForgetAction();
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        txtSerectQuestion.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> obs, String oldItem, String newItem) {
                if (newItem != null) {
                    txtSerectQuestion.setStyle("-jfx-focus-color:#6747CD; -jfx-unfocus-color:#6747CD;");
                }
            }
        });
        ObservableList list_question = FXCollections.observableArrayList();
        ObservableList list_question_random = FXCollections.observableArrayList();
        list_question.add("What is the first phone number you use?");
        list_question.add("What is your first girlfriend's name?");
        list_question.add("Which animal do you like best?");
        list_question.add("Which subject do you like best?");
        list_question.add("Which car company do you like best?");
        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            int randomIndex = rand.nextInt(list_question.size());
            list_question_random.add(list_question.get(randomIndex));
            list_question.remove(randomIndex);
        }
        txtSerectQuestion.setItems(list_question_random);
        txtForgetUsername.setText(UserForget);
        // TODO
    }

    @FXML
    private void handleForgetAction() throws SQLException, ClassNotFoundException {
        if (txtForgetPassword.getText().equals("")) {
            notificationFunction.notificationPassword(txtForgetPassword, HboxContent, "PASSWORD MUST NOT EMPTY !!!");
        } else if (txtForgetConfirmPassword.getText().equals("")) {
            notificationFunction.notificationPassword(txtForgetConfirmPassword, HboxContent, "CONFIRM PASSWORD MUST NOT EMPTY !!!");
        } else if (txtSerectQuestion.getSelectionModel().isEmpty()) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(300, 35);
            label.setText("SERECT QUESTION MUST NOT EMPTY !!!");
            txtSerectQuestion.getStyleClass().removeAll();
            txtSerectQuestion.getStyleClass().add("jfx-combo-box-fault");
            HboxContent.setSpacing(10);
            HboxContent.setAlignment(Pos.CENTER);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            txtSerectQuestion.requestFocus();
        } else if (textSerectAnswer.getText().equals("")) {
            notificationFunction.notificationPassword(textSerectAnswer, HboxContent, "SERECT ANSWER MUST NOT EMPTY !!!");
        } else if (!PatternValided.PatternPassword(txtForgetPassword.getText())) {
            notificationFunction.notificationPassword(txtForgetPassword, HboxContent, "PASSWORD INVALID(EXAM:Abc123 (6-20 CHARACTERS)!!!");
        } else if (!txtForgetPassword.getText().equals(txtForgetConfirmPassword.getText())) {
            notificationFunction.notificationPassword(txtForgetPassword, HboxContent, "PASSWORD CONFIRM DID NOT MATCH !!!");
        } else {
            Emp = DAO.getInfoForgetPassEmployee(UserForget);
            MD5Encrypt m = new MD5Encrypt();
//            Kiểm tra account block thì ko cho thực hiện bất kì điều gì nữa
            if(Emp == null){
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(300, 35);
                label.setText("QUESTION OR ANSWER WRONG!!!");
                HboxContent.setSpacing(10);
                HboxContent.setAlignment(Pos.CENTER);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
                txtSerectQuestion.requestFocus();
                DAO.check_MacAddress(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")),GetInetAddress.getMacAddress(),txtForgetUsername.getText());
            }
            else if (!Emp.getActive()) {
                notificationFunction.notificationPassword(txtForgetPassword, HboxContent, "ACCOUNT IS LOCKED !!!");
            } //          Xử lý  khi câu hỏi và câu trả lời chính xác
            else if (Emp.getSerect_Question().equals(m.hashPass(txtSerectQuestion.getValue()))
                    && Emp.getSerect_Answer().equals(m.hashPass(textSerectAnswer.getText()))) {

                DAO.forgetPass(txtForgetUsername.getText(), m.hashPass(txtForgetPassword.getText()));
                String content = "Change New Password";
                DAO.setUserLogs_With_MAC(txtForgetUsername.getText(), content,
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), GetInetAddress.getMacAddress());
                Stage stage = (Stage) btnSubmit.getScene().getWindow();
                stage.close();
            } else {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(300, 35);
                label.setText("QUESTION OR ANSWER WRONG!!!");
                HboxContent.setSpacing(10);
                HboxContent.setAlignment(Pos.CENTER);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
                txtSerectQuestion.requestFocus();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = Calendar.getInstance();
                String logtime;
                logtime = dateFormat.format(cal.getTime());
                if (!DAO.check_Time(txtForgetUsername.getText()).equals(logtime)) {
                    DAO.reset_CheckLogin(txtForgetUsername.getText(), logtime);
                }
                DAO.check_Login(txtForgetUsername.getText(), logtime);
                DAO.check_MacAddress(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")),GetInetAddress.getMacAddress(),txtForgetUsername.getText());
            }
        }
    }
}
