/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
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
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import models.DAO;
import models.MD5Encrypt;

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
    private void btnSubmitAddNewEmployee(ActionEvent event) throws ClassNotFoundException, SQLException {
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
    }
    
}
