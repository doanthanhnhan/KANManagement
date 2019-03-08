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
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.DAO;
import models.InfoEmployee;
import utils.PatternValided;
import utils.MD5Encrypt;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class FXMLAccountController implements Initializable {

    public static Stage stage;
    @FXML
    private JFXTextField txtUsername;
    @FXML
    private JFXPasswordField newPassword;
    @FXML
    private JFXPasswordField newConfirmPassword;
    @FXML
    private JFXComboBox<String> newSerectQuestion;
    @FXML
    private JFXPasswordField newSerectAnswer;
    @FXML
    private JFXPasswordField ConfirmAnswer;
    @FXML
    private JFXButton btnRegister;
    @FXML
    private HBox HboxContent;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        newPassword.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                newPassword.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                HboxContent.getChildren().clear();
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        btnSubmitRegister();
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLAccountController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        newConfirmPassword.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                newConfirmPassword.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                HboxContent.getChildren().clear();
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        btnSubmitRegister();
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLAccountController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        newSerectAnswer.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                newSerectAnswer.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                HboxContent.getChildren().clear();
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        btnSubmitRegister();
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLAccountController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        ConfirmAnswer.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                ConfirmAnswer.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                HboxContent.getChildren().clear();
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        btnSubmitRegister();
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLAccountController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        newSerectQuestion.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                HboxContent.getChildren().clear();
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        btnSubmitRegister();
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLAccountController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        newSerectQuestion.valueProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null) {
                newSerectQuestion.setStyle("-jfx-focus-color:#6747CD; -jfx-unfocus-color:#6747CD;");
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
        newSerectQuestion.setItems(list_question_random);
        txtUsername.setText(FXMLLoginController.User_Login);
        // TODO
    }

    @FXML
    private void btnSubmitRegister() throws ClassNotFoundException, SQLException, IOException {
        if (newPassword.getText().equals("")) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(470, 35);
            label.setText("PASSWORD MUST NOT EMPTY !!!");
            newPassword.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setSpacing(10);
            HboxContent.setAlignment(Pos.CENTER);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            newPassword.requestFocus();
        } else if (newConfirmPassword.getText().equals("")) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(470, 35);
            label.setText("CONFIRM PASSWORD MUST NOT EMPTY !!!");
            newConfirmPassword.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setSpacing(10);
            HboxContent.setAlignment(Pos.CENTER);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            newConfirmPassword.requestFocus();
        } else if (newSerectQuestion.getSelectionModel().isEmpty()) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(470, 35);
            label.setText("SERECT QUESTION MUST NOT EMPTY !!!");
            newSerectQuestion.getStyleClass().removeAll();
            newSerectQuestion.getStyleClass().add("jfx-combo-box-fault");
            HboxContent.setSpacing(10);
            HboxContent.setAlignment(Pos.CENTER);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            newSerectQuestion.requestFocus();
        } else if (newSerectAnswer.getText().equals("")) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(470, 35);
            label.setText("CONFIRM PASSWORD MUST NOT EMPTY !!!");
            newSerectAnswer.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setSpacing(10);
            HboxContent.setAlignment(Pos.CENTER);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            newSerectAnswer.requestFocus();
        } else if (ConfirmAnswer.getText().equals("")) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(470, 35);
            label.setText("CONFIRM PASSWORD MUST NOT EMPTY !!!");
            ConfirmAnswer.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setSpacing(10);
            HboxContent.setAlignment(Pos.CENTER);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            ConfirmAnswer.requestFocus();
        } else if (!PatternValided.PatternPassword(newPassword.getText())) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(470, 35);
            label.setText("PASSWORD INCORRECT (EXAM:Abc12345 (6-20 CHARACTERS) !!! ");
            newPassword.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setSpacing(10);
            HboxContent.setAlignment(Pos.CENTER);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            newPassword.requestFocus();
        } else if (!newPassword.getText().equals(newConfirmPassword.getText())) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(470, 35);
            label.setText("PASSWORD AND PASSWORD CONFIRM DID NOT MATCH !!!");
            newPassword.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            newConfirmPassword.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setSpacing(10);
            HboxContent.setAlignment(Pos.CENTER);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            newPassword.requestFocus();
        } else if (!PatternValided.PatternAnswer(newSerectAnswer.getText())) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(470, 35);
            label.setText("ANSWER INCORRECT (ANSWER MUST HAVE 4-20 CHARACTER) !!!");
            newSerectAnswer.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setSpacing(10);
            HboxContent.setAlignment(Pos.CENTER);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            newSerectAnswer.requestFocus();
        } else if (!newSerectAnswer.getText().equals(ConfirmAnswer.getText())) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(470, 35);
            label.setText("ANSWER AND ANSWER CONFIRM DID NOT MATCH !!!");
            newSerectAnswer.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            ConfirmAnswer.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setSpacing(10);
            HboxContent.setAlignment(Pos.CENTER);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            newSerectAnswer.requestFocus();
        } else {
            MD5Encrypt m;
            m = new MD5Encrypt();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            String logtime;
            logtime = dateFormat.format(cal.getTime());
            DAO.SetPass(txtUsername.getText(), m.hashPass(newPassword.getText()), m.hashPass((String) newSerectQuestion.getValue()), m.hashPass((String) newSerectAnswer.getText()));
            String Content = "Set Password";
            DAO.setUserLogs(txtUsername.getText(), Content, logtime);
            Stage stage = (Stage) btnRegister.getScene().getWindow();
            // do what you have to do
            stage.close();
            Stage stageEdit = new Stage();
            this.stage = stageEdit;
            Parent rootAdd;
            rootAdd = FXMLLoader.load(getClass().getResource("/fxml/FXMLLogin.fxml"));
            Scene scene1;
            scene1 = new Scene(rootAdd);
            stageEdit.setTitle("KANManagementLogin");
            stageEdit.getIcons().add(new Image("/images/iconmanagement.png"));
            stageEdit.setScene(scene1);
            stageEdit.show();
        }
    }
}
