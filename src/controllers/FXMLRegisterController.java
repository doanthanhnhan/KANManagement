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
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import models.DAO;
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
    private JFXTextField newIdEmployee;
    @FXML
    private JFXDatePicker newBirthday;
    @FXML
    private JFXButton btnRegister;
    @FXML
    private JFXRadioButton sexMale;
    @FXML
    private JFXRadioButton sexFemale;
    @FXML
    public void Format_Show_Calender(){
        String pattern = "dd-MM-yyyy";
        formatCalender.format(pattern, newBirthday);
    }
    @FXML
    public void btnSubmitRegister(){
        String User = newUsername.getText();
        String Pass = newPassword.getText();
        String ConPass = newConfirmPassword.getText();
        String Id = newIdEmployee.getText();
        String Question = (String) newSerectQuestion.getValue();
        String Answer = newSerectAnswer.getText();
        String FName = newFirstname.getText();
        String MName = newMidname.getText();
        String LName = newLastname.getText();
        LocalDate birthday = newBirthday.getValue();
        String Sex;
        if(sexMale.selectedProperty().getValue())
            Sex = sexMale.getText();
        else
            Sex = sexFemale.getText();
        DAO.newEmployee(User, Pass, Id, Question, Answer, FName, MName, LName, birthday, Sex);
        newUsername.setText("");
        newPassword.setText("");
        newConfirmPassword.setText("");
        newIdEmployee.setText("");
        newSerectAnswer.setText("");
        newFirstname.setText("");
        newLastname.setText("");
        newMidname.setText("");
        newBirthday.setValue(null);
        newBirthday.setPromptText("Select Birthday");
        newSerectQuestion.setValue(null);
        
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
