/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import static controllers.ConnectControllers.fXMLMainFormController;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.DAO;
import models.Setting;
import utils.AlertLoginAgain;
import utils.GetInetAddress;
import utils.PatternValided;
import utils.SettingXML;
import utils.connectDB;
import static utils.connectDB.DATABASENAME;
import utils.showFXMLLogin;
import view.Login;

/**
 * FXML Controller class
 *
 * @author Đoàn Thanh Nhân
 */
public class FXMLSettingsController implements Initializable {

    private boolean check_Validate_Form;
    private SimpleStringProperty local_Server_URL;
    private showFXMLLogin showFormLogin;
    private FXMLMainFormController mainFormController;

    @FXML
    private ComboBox<String> comboBox_Choose_Server;
    @FXML
    private JFXTextField txt_SMTP_Server;
    @FXML
    private JFXTextField txt_SMTP_Port;
    @FXML
    private JFXTextField txt_Email;
    @FXML
    private JFXPasswordField txt_Email_Password;
    @FXML
    private JFXTextField txt_Local_Database_URL;
    @FXML
    private JFXTextField txt_Local_Database_Name;
    @FXML
    private JFXTextField txt_Local_Server_User;
    @FXML
    private JFXPasswordField txt_Local_Server_Password;
    @FXML
    private JFXTextField txt_Remote_Server_Database_URL;
    @FXML
    private JFXTextField txt_Remote_Server_Database_Name;
    @FXML
    private JFXTextField txt_Remote_Server_User;
    @FXML
    private JFXPasswordField txt_Remote_Server_Password;
    @FXML
    private JFXButton btn_Save;
    @FXML
    private JFXButton btn_Cancel;
    @FXML
    private Tab tab_General;
    @FXML
    private Tab tab_Local;
    @FXML
    private Tab tab_Remote;
    @FXML
    private JFXTabPane mainTabPane;
    @FXML
    private JFXButton btn_Create_Local_DB;
    @FXML
    private JFXButton btn_ReLogIn_Local;
    @FXML
    private JFXButton btn_Create_Remote_DB;
    @FXML
    private JFXButton btn_ReLogin_Remote;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        local_Server_URL = new SimpleStringProperty();
        // Initialize comboBox
        ObservableList<String> listServer = FXCollections.observableArrayList();
        listServer.addAll("Local", "Remote");
        comboBox_Choose_Server.setItems(listServer);

        // Initialize Text field
        File file = new File("");
        File fileSetting = new File(file.getAbsolutePath() + "/src/xml/settings.xml");
        if (fileSetting.exists() && !fileSetting.isDirectory()) {
            Setting setting = SettingXML.readXML();

            comboBox_Choose_Server.setValue(setting.getChooseServer());
            txt_Email.setText(setting.getEmailAdress());
            txt_Email_Password.setText(setting.getEmailPassword());
            txt_Local_Database_Name.setText(setting.getLocalServer_DB_Name());
            txt_Local_Database_URL.setText(setting.getLocalServer_DB_URL());
            txt_Local_Server_Password.setText(setting.getLocalServer_Password());
            txt_Local_Server_User.setText(setting.getLocalServer_User());
            txt_Remote_Server_Database_Name.setText(setting.getRemoteServer_DB_Name());
            txt_Remote_Server_Database_URL.setText(setting.getRemoteServer_DB_URL());
            txt_Remote_Server_Password.setText(setting.getRemoteServer_Password());
            txt_Remote_Server_User.setText(setting.getRemoteServer_User());
            txt_SMTP_Port.setText(setting.getSmtpPort());
            txt_SMTP_Server.setText(setting.getSmtpServer());

        } else {
            comboBox_Choose_Server.setValue("Local");
        }

        // Initialize Buttons
        comboBox_Choose_Server.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Local")) {
                btn_Create_Local_DB.setDisable(false);
                btn_ReLogIn_Local.setDisable(true);
                btn_Create_Remote_DB.setDisable(true);
                btn_ReLogin_Remote.setDisable(true);
            } else if (newValue.equals("Remote")) {
                btn_Create_Local_DB.setDisable(true);
                btn_ReLogIn_Local.setDisable(true);
                btn_Create_Remote_DB.setDisable(false);
                btn_ReLogin_Remote.setDisable(true);
            }
        });

        btn_ReLogIn_Local.setOnAction((event) -> {
            reLogin();
        });
        btn_ReLogin_Remote.setOnAction((event) -> {
            reLogin();
        });
    }

    public Setting get_Data_From_Form() {
        Setting setting = new Setting();
        setting.setChooseServer(comboBox_Choose_Server.getValue());
        setting.setEmailAdress(txt_Email.getText());
        setting.setEmailPassword(txt_Email_Password.getText());
        setting.setLocalServer_DB_Name(txt_Local_Database_Name.getText());
        setting.setLocalServer_DB_URL(txt_Local_Database_URL.getText());
        setting.setLocalServer_Password(txt_Local_Server_Password.getText());
        setting.setLocalServer_User(txt_Local_Server_User.getText());
        setting.setRemoteServer_DB_Name(txt_Remote_Server_Database_Name.getText());
        setting.setRemoteServer_DB_URL(txt_Remote_Server_Database_URL.getText());
        setting.setRemoteServer_Password(txt_Remote_Server_Password.getText());
        setting.setRemoteServer_User(txt_Remote_Server_User.getText());
        setting.setSmtpPort(txt_SMTP_Port.getText());
        setting.setSmtpServer(txt_SMTP_Server.getText());
        return setting;
    }

    public void validate_Form() {
        if (txt_Email.getText().isEmpty()) {
            check_Validate_Form = false;
            mainTabPane.getSelectionModel().select(tab_General);
            txt_Email.requestFocus();
            txt_Email.getStyleClass().add("text-field-fault");
        } else if (txt_Email_Password.getText().isEmpty() || PatternValided.PatternEmail(txt_Email_Password.getText())) {
            check_Validate_Form = false;
            mainTabPane.getSelectionModel().select(tab_General);
            txt_Email_Password.requestFocus();
            txt_Email_Password.getStyleClass().add("text-field-fault");
        } else if (txt_Local_Database_Name.getText().isEmpty()) {
            check_Validate_Form = false;
            mainTabPane.getSelectionModel().select(tab_Local);
            txt_Local_Database_Name.requestFocus();
            txt_Local_Database_Name.getStyleClass().add("text-field-fault");
        } else if (txt_Local_Database_URL.getText().isEmpty()) {
            check_Validate_Form = false;
            mainTabPane.getSelectionModel().select(tab_Local);
            txt_Local_Database_URL.requestFocus();
            txt_Local_Database_URL.getStyleClass().add("text-field-fault");
        } else if (txt_Local_Server_Password.getText().isEmpty()) {
            check_Validate_Form = false;
            mainTabPane.getSelectionModel().select(tab_Local);
            txt_Local_Server_Password.requestFocus();
            txt_Local_Server_Password.getStyleClass().add("text-field-fault");
        } else if (txt_Local_Server_User.getText().isEmpty()) {
            check_Validate_Form = false;
            mainTabPane.getSelectionModel().select(tab_Local);
            txt_Local_Server_User.requestFocus();
            txt_Local_Server_User.getStyleClass().add("text-field-fault");
        } else if (txt_Remote_Server_Database_Name.getText().isEmpty()) {
            check_Validate_Form = false;
            mainTabPane.getSelectionModel().select(tab_Remote);
            txt_Remote_Server_Database_Name.requestFocus();
            txt_Remote_Server_Database_Name.getStyleClass().add("text-field-fault");
        } else if (txt_Remote_Server_Database_URL.getText().isEmpty()) {
            check_Validate_Form = false;
            mainTabPane.getSelectionModel().select(tab_Remote);
            txt_Remote_Server_Database_URL.requestFocus();
            txt_Remote_Server_Database_URL.getStyleClass().add("text-field-fault");
        } else if (txt_Remote_Server_Password.getText().isEmpty()) {
            check_Validate_Form = false;
            mainTabPane.getSelectionModel().select(tab_Remote);
            txt_Remote_Server_Password.requestFocus();
            txt_Remote_Server_Password.getStyleClass().add("text-field-fault");
        } else if (txt_Remote_Server_User.getText().isEmpty()) {
            check_Validate_Form = false;
            mainTabPane.getSelectionModel().select(tab_Remote);
            txt_Remote_Server_User.requestFocus();
            txt_Remote_Server_User.getStyleClass().add("text-field-fault");
        } else if (txt_SMTP_Port.getText().isEmpty()) {
            check_Validate_Form = false;
            mainTabPane.getSelectionModel().select(tab_General);
            txt_SMTP_Port.requestFocus();
            txt_SMTP_Port.getStyleClass().add("text-field-fault");
        } else if (txt_SMTP_Server.getText().isEmpty()) {
            check_Validate_Form = false;
            mainTabPane.getSelectionModel().select(tab_General);
            txt_SMTP_Server.requestFocus();
            txt_SMTP_Server.getStyleClass().add("text-field-fault");
        } else {
            System.out.println("Validate finished!");
            check_Validate_Form = true;
        }
    }

    public void reLogin() {
        mainFormController = ConnectControllers.getfXMLMainFormController();
        Platform.runLater(() -> {
            Stage stage = new Stage();
            if (connectDB.checkDBExists()) {
                try {
                    if (DAO.check_invalid(GetInetAddress.getMacAddress()) && DAO.check_Active_MacAddress(GetInetAddress.getMacAddress()).equals(0)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("MacAddress: " + GetInetAddress.getMacAddress() + ".Your device has been blocked from using this program !!!");
                        alert.setContentText("Please contact the administrator for reuse !!!");
                        alert.showAndWait();
                    } else {
                        if (DAO.checkFirstLogin() == 0) {
                            Parent root = FXMLLoader.load(getClass().getResource("/fxml/FXMLAddNewEmployee.fxml"));
                            stage.setTitle("Add New Employee");
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                        } else {
                            Parent root = FXMLLoader.load(getClass().getResource("/fxml/FXMLLogin.fxml"));
                            stage.setTitle("Login");
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                        }
                        stage.resizableProperty().setValue(Boolean.FALSE);
                        stage.getIcons().add(new Image("/images/KAN Logo.png"));
                        stage.show();
                    }
                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(FXMLSettingsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/FXMLSettings.fxml"));
                    stage.setTitle("Settings");
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.resizableProperty().setValue(Boolean.FALSE);
                    stage.getIcons().add(new Image("/images/KAN Logo.png"));
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(FXMLSettingsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        Stage stage = (Stage) mainTabPane.getScene().getWindow();
        stage.close();
        if (mainFormController != null) {
            Stage stageMainForm = (Stage) fXMLMainFormController.AnchorPaneMainForm.getScene().getWindow();
            stageMainForm.close();
        }
    }

    @FXML
    private void Save_Action(ActionEvent event) {
        validate_Form();
        if (check_Validate_Form) {
            Setting setting = get_Data_From_Form();
            SettingXML.writeXML(setting);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText("Informations");
            alert.setContentText("Setting informations have been saved successful!");
            alert.show();
        }
    }

    @FXML
    private void Cancel_Action(ActionEvent event) {
        Stage stage = (Stage) btn_Cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void Create_Local_DB_Action(ActionEvent event) {
        if (!connectDB.checkDBExists()) {
            connectDB.createSQLDatabase();
            connectDB.createTables();
            connectDB.createViews();
            btn_Create_Local_DB.setDisable(true);
            btn_ReLogIn_Local.setDisable(false);
        } else {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText("Querry result");
                alert.setContentText("This DBName: " + DATABASENAME + " is exists.");
                alert.show();
            });
        }
    }

    @FXML
    private void Create_Remote_DB_Action(ActionEvent event) {
        if (!connectDB.checkDBExists()) {
            connectDB.createTables();
            connectDB.createViews();
            btn_Create_Remote_DB.setDisable(true);
            btn_ReLogin_Remote.setDisable(false);
        } else {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText("Querry result");
                alert.setContentText("This DBName: " + DATABASENAME + " is exists.");
                alert.show();
            });
        }
    }
}
