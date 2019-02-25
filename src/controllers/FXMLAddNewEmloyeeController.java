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
import javafx.event.ActionEvent;
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
import java.util.regex.Pattern;
import models.InfoEmployee;
// regex first last ^[A-Z](?:[a-z]*){2,15}+$
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
    private JFXComboBox newRole;
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
    private Pattern pattern;
    private Pattern patternEmail;
    private Pattern patternFLName;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            if(DAO.checkFirstLogin()==0){
                newRole.setValue("Admin");
                newRole.setDisable(true);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLAddNewEmloyeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        newId.setOnKeyPressed(event -> {
            newId.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
        });
        newGmail.setOnKeyPressed(event -> {
            newGmail.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
        });
        newFirstname.setOnKeyPressed(event -> {
            newFirstname.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
        });
        newLastname.setOnKeyPressed(event -> {
            newLastname.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
        });
        ObservableList listRole = FXCollections.observableArrayList();
        try {
            listRole = DAO.getAllRole();
            newRole.setItems(listRole);
            // TODO
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLAddNewEmloyeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void btnSubmitAddNewEmployee(ActionEvent event) throws ClassNotFoundException, SQLException, IOException, Exception {
//        valided texfield
        pattern = Pattern.compile("^(?=.{4,12}$)[a-zA-Z][a-zA-Z0-9_]+$");
        patternEmail=Pattern.compile("^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$");
        patternFLName=Pattern.compile("^\\pL+[\\pL\\pZ\\pP]{2,30}$");
        if (newId.getText().equals("")) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(465, 35);
            label.setText("ID MUST NOT EMPTY !!!");
            newId.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setSpacing(10);
            HboxContent.setAlignment(Pos.CENTER);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            newId.requestFocus();
        } else if (newGmail.getText().equals("")) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(465, 35);
            label.setText("GMAIL MUST NOT EMPTY !!!");
            newGmail.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setAlignment(Pos.CENTER);
            HboxContent.setSpacing(10);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            newGmail.requestFocus();
        }else if (newFirstname.getText().equals("")) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(465, 35);
            label.setText("FIRST NAME MUST NOT EMPTY !!!");
            newFirstname.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setAlignment(Pos.CENTER);
            HboxContent.setSpacing(10);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            newFirstname.requestFocus();
        }else if (newLastname.getText().equals("")) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(465, 35);
            label.setText("LAST NAME MUST NOT EMPTY !!!");
            newLastname.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setAlignment(Pos.CENTER);
            HboxContent.setSpacing(10);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            newLastname.requestFocus();
        }
        else if(!pattern.matcher(newId.getText()).matches()){
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(465, 35);
            label.setText("ID MUST 4-12 CHARACTER, NOT BEGIN NUMBER AND CHARACTER SPECIAL !!!");
            newId.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setAlignment(Pos.CENTER);
            HboxContent.setSpacing(10);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            newId.requestFocus();
        }
        else if(!patternEmail.matcher(newGmail.getText()).matches()){
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(465, 35);
            label.setText("INVALID EMAIL !!!");
            newGmail.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setAlignment(Pos.CENTER);
            HboxContent.setSpacing(10);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            newGmail.requestFocus();
        }
        else if(!patternFLName.matcher(newFirstname.getText()).matches()){
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(465, 35);
            label.setText("FIRSTNAME INVALID !!! (Example: Alex, John,...) ");
            newFirstname.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setAlignment(Pos.CENTER);
            HboxContent.setSpacing(10);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            newFirstname.requestFocus();  
        }else if(!patternFLName.matcher(newMidname.getText()).matches()&&!newMidname.getText().equals("")){
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(465, 35);
            label.setText("MIDNAME INVALID !!!(Example: Peter, Rooney,...");
            newMidname.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setAlignment(Pos.CENTER);
            HboxContent.setSpacing(10);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            newMidname.requestFocus();
        }
        else if(!patternFLName.matcher(newLastname.getText()).matches()){
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(465, 35);
            label.setText("LASTNAME INVALID !!!(Example: Peter, Rooney,...");
            newLastname.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setAlignment(Pos.CENTER);
            HboxContent.setSpacing(10);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            newLastname.requestFocus();
        }
        else{
            ObservableList<InfoEmployee> List_Check_Employee = FXCollections.observableArrayList();
            List_Check_Employee = FXMLLoginController.List_Employee;
            boolean check = true;
            for (InfoEmployee infoEmployee : List_Check_Employee) {
                if(newId.getText().equals(infoEmployee.getEmployee_ID())){
                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                    icon.setSize("16");
                    icon.setStyleClass("jfx-glyhp-icon");
                    Label label = new Label();
                    label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                    label.setPrefSize(465, 35);
                    label.setText("ID ALREADY EXIST !!!");
                    newLastname.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
                    HboxContent.setAlignment(Pos.CENTER);
                    HboxContent.setSpacing(10);
                    HboxContent.getChildren().clear();
                    HboxContent.getChildren().add(icon);
                    HboxContent.getChildren().add(label);
                    newId.requestFocus();
                    check = false;
                    break;
                }
                else if(newGmail.getText().equals(infoEmployee.getGmail())){
                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                    icon.setSize("16");
                    icon.setStyleClass("jfx-glyhp-icon");
                    Label label = new Label();
                    label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                    label.setPrefSize(465, 35);
                    label.setText("EMAIL ALREADY EXIST !!!");
                    newLastname.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
                    HboxContent.setAlignment(Pos.CENTER);
                    HboxContent.setSpacing(10);
                    HboxContent.getChildren().clear();
                    HboxContent.getChildren().add(icon);
                    HboxContent.getChildren().add(label);
                    newGmail.requestFocus();
                    check = false;
                    break;
                }
            }
            if(check==true){
                String Sex;
                if(sexMale.selectedProperty().getValue()){
                    Sex = "Male";
                }
                else{
                    Sex = "Female";
                }
                String Id_Role;
                Id_Role = DAO.getIdRole((String) newRole.getValue());
                DAO.AddNewEmployee(newId.getText(), newFirstname.getText(), newMidname.getText(), newLastname.getText(), Id_Role, newGmail.getText(), Sex);
                String Username = newId.getText();
                MD5Encrypt m;
                m = new MD5Encrypt();
                String Password = m.hashPass("123456");
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                Calendar cal = Calendar.getInstance();
                String logtime;
                logtime = dateFormat.format(cal.getTime());
                DAO.AddUser(newId.getText(), Username, Password,logtime);
                String content = "Username: " + newId.getText() + ", Password: 123456";
                Email.send_Email_Without_Attach("smtp.gmail.com", newGmail.getText(), "KANManagement.AP146@gmail.com",
                            "KAN@123456", "Default username and password", content);
                newRole.setValue(null);
                newFirstname.setText("");
                newMidname.setText("");
                newLastname.setText("");
                newGmail.setText("");
                newId.setText("");
                if(DAO.checkFirstLogin()==1){
                    Stage stage = (Stage) btnAddNew.getScene().getWindow();
                    stage.close();
                    Stage stageEdit = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/FXMLLogin.fxml"));
                    stageEdit.getIcons().add(new Image("/images/iconmanagement.png"));
                    Scene scene = new Scene(root);
                    stageEdit.setTitle("KANManagement");
                    stageEdit.setScene(scene);
                    stageEdit.show();
                }
            }
        }
    }
}
