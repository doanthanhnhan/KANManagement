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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

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
    private JFXComboBox<?> txtSerectQuestion;
    @FXML
    private JFXPasswordField textSerectAnswer;
    @FXML
    private JFXButton btnSubmit;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList List_Question = FXCollections.observableArrayList();
        List_Question.add("What is your mother's name?");
        List_Question.add("What is your girlfriend's name?");
        List_Question.add("What is the first phone number you use?");
        List_Question.add("Which animal do you like best?");
        txtSerectQuestion.setItems(List_Question);
        // TODO
    }    

    @FXML
    private void handleForgetAction(ActionEvent event) {
        txtForgetUsername.setText("");
        txtForgetPassword.setText("");
        txtForgetConfirmPassword.setText("");
        textSerectAnswer.setText("");
        txtSerectQuestion.setValue(null);
    }
    
}
