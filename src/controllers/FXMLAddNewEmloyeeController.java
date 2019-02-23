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
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.DAO;
import utils.MD5Encrypt;
import view.Login;
import static view.Login.stage;

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
    private JFXComboBox<?> newRole;
    @FXML
    private JFXTextField newId;
    @FXML
    private JFXRadioButton sexMale;
    @FXML
    private JFXRadioButton sexFemale;
    @FXML
    private JFXButton btnAddNew;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
    private void btnSubmitAddNewEmployee(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
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
        DAO.AddUser(newId.getText(), Username, Password);
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
