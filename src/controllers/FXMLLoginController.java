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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.MD5Encrypt;

/**
 *
 * @author Admin
 */
public class FXMLLoginController implements Initializable {

    public static ObservableList<InfoEmployee> List_EmployeeLogin = FXCollections.observableArrayList();
    public static ObservableList<InfoEmployee> employeeForget = FXCollections.observableArrayList();
    public static ObservableList<InfoEmployee> List_Employee = FXCollections.observableArrayList();
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtUserName.setOnKeyPressed(event -> {
            txtUserName.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");

            hboxContent.getChildren().clear();
        });
        txtPassword.setOnKeyPressed(event -> {
            txtPassword.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");

            hboxContent.getChildren().clear();
        });
        ConnectControllers.setfXMLLoginController(this);
        try {
            List_Employee = DAO.getAllEmployee();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleLoginAction(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
        if (txtUserName.getText().equals("")) {
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
        } else if (txtPassword.getText().equals("")) {
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
        } else {
            for (InfoEmployee infoEmployee : List_Employee) {
                MD5Encrypt m = new MD5Encrypt();
                String hashPass = m.hashPass(txtPassword.getText());
                if (txtUserName.getText().equals(infoEmployee.getUserName())) {
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
                        if (hashPass.equals(infoEmployee.getPassWord())) {
                            List_EmployeeLogin.add(infoEmployee);
                            Stage stage = (Stage) btnLogin.getScene().getWindow();
                            stage.close();
                            Stage stageEdit = new Stage();
                            Parent rootAdd;
                            if (DAO.checkSetPass(txtUserName.getText()) == 0) {
                                rootAdd = FXMLLoader.load(getClass().getResource("/fxml/FXMLAccount.fxml"));
                                stageEdit.setTitle("Set Password");
                            } else {
                                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Calendar cal = Calendar.getInstance();
                                String logtime;
                                logtime = dateFormat.format(cal.getTime());
                                DAO.setUserLogs(txtUserName.getText(), "Login", logtime);
                                DAO.reset_CheckLogin(txtUserName.getText(), logtime);
                                rootAdd = FXMLLoader.load(getClass().getResource("/fxml/FXMLMainForm.fxml"));
                                stageEdit.setTitle("KANManagementLogin");
                            }

                            Scene scene1;
                            scene1 = new Scene(rootAdd);

                            stageEdit.getIcons().add(new Image("/images/iconmanagement.png"));
                            stageEdit.setScene(scene1);
                            stageEdit.show();
                            break;
                        } else if (!hashPass.equals(infoEmployee.getPassWord())) {
                            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                            icon.setSize("16");
                            icon.setStyleClass("jfx-glyhp-icon");
                            Label label = new Label();
                            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                            label.setPrefSize(300, 35);
                            label.setText("USER OR PASSWORD WRONG !!!");
                            hboxContent.setAlignment(Pos.CENTER);
                            hboxContent.setSpacing(10);
                            hboxContent.getChildren().clear();
                            hboxContent.getChildren().add(icon);
                            hboxContent.getChildren().add(label);
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Calendar cal = Calendar.getInstance();
                            String logtime;
                            logtime = dateFormat.format(cal.getTime());
                            if (!DAO.check_Time(txtUserName.getText()).equals(logtime)) {
                                DAO.reset_CheckLogin(txtUserName.getText(), logtime);
                            }
                            DAO.check_Login(txtUserName.getText(), logtime);
                        }
                    }
                } else {
                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                    icon.setSize("16");
                    icon.setStyleClass("jfx-glyhp-icon");
                    Label label = new Label();
                    label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                    label.setPrefSize(300, 35);
                    label.setText("USER OR PASSWORD WRONG !!!");
                    hboxContent.setAlignment(Pos.CENTER);
                    hboxContent.setSpacing(10);
                    hboxContent.getChildren().clear();
                    hboxContent.getChildren().add(icon);
                    hboxContent.getChildren().add(label);
                }
            }
        }
    }

    @FXML
    private void handleForgetPass() throws IOException, SQLException, ClassNotFoundException {
        if ("".equals(txtUserName.getText())) {
            System.out.println("Empty");
        } else {
            if (DAO.check_Active(txtUserName.getText()).equals(0)) {
                System.out.println("Account Blocked !!!");
            } else {
                for (int i = 0; i < List_Employee.size(); i++) {
                    if (txtUserName.getText().equals(List_Employee.get(i).getUserName())) {
                        employeeForget.add(List_Employee.get(i));
                        Stage stageForget = new Stage();
                        stageForget.initModality(Modality.APPLICATION_MODAL);
                        // make its owner the existing window:

                        Parent rootAdd = FXMLLoader.load(getClass().getResource("/fxml/FXMLForgetPass.fxml"));
                        Scene sceneForget;
                        sceneForget = new Scene(rootAdd);
                        stageForget.setTitle("Forget Password");
                        stageForget.getIcons().add(new Image("/images/iconmanagement.png"));
                        stageForget.setScene(sceneForget);
                        stageForget.show();
                        break;
                    } else {
                        System.out.println("Invalid");
                    }
                }
            }
        }

    }

    @FXML
    private void checkInputUserName(InputMethodEvent event) {

    }
}
