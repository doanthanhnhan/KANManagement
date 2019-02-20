/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import models.InfoEmployee;
import models.formatCalender;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class FXMLRegisterController implements Initializable {

    @FXML
    private JFXTextField newUsername;
    @FXML
    private JFXPasswordField newPassword;
    @FXML
    private JFXPasswordField newConfirmPassword;
    @FXML
    private JFXComboBox<?> newSerectQuestion;
    @FXML
    private JFXPasswordField newSerectAnswer;
    @FXML
    private JFXTextField newFirstname;
    @FXML
    private JFXTextField newMidname;
    @FXML
    private JFXTextField newLastname;
    @FXML
    private JFXDatePicker newBirthday;
    @FXML
    private JFXButton btnRegister;
    @FXML
    public void Format_Show_Calender(){
        String pattern = "dd-MM-yyyy";
        formatCalender.format(pattern, newBirthday);
    }
    @FXML
    public void btnSubmitRegister(){
        System.out.println(newSerectQuestion.getValue());
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList List_Question = FXCollections.observableArrayList();
        List_Question.add("What is your mother's name?");
        List_Question.add("What is your girlfriend's name?");
        List_Question.add("What is the first phone number you use?");
        List_Question.add("Which animal do you like best?");
        newSerectQuestion.setItems(List_Question);
        // TODO
    }    
    
}
