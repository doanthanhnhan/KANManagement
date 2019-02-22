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
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.DAO;
import models.InfoEmployee;
import utils.MD5Encrypt;
import view.Login;

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
    private JFXComboBox<?> newSerectQuestion;
    @FXML
    private JFXPasswordField newSerectAnswer;
    @FXML
    private JFXButton btnRegister;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList list_question = FXCollections.observableArrayList();
        ObservableList list_question_random = FXCollections.observableArrayList();
        ObservableList<InfoEmployee> list_Employee = FXCollections.observableArrayList();
        list_Employee=FXMLLoginController.List_EmployeeLogin;
        list_question.add("What is the first phone number you use?");
        list_question.add("What is your first girlfriend's name?");
        list_question.add("Which animal do you like best?");
        list_question.add("Which subject do you like best?");
        list_question.add("Which car company do you like best?");
        Random rand = new Random();
        for(int i=0;i<5;i++){
            int randomIndex = rand.nextInt(list_question.size());
            list_question_random.add(list_question.get(randomIndex));
            list_question.remove(randomIndex);
        }
        newSerectQuestion.setItems(list_question_random);
        String username = list_Employee.get(0).getUserName();
        txtUsername.setText(username);
        // TODO
    }    
    @FXML
    private void btnSubmitRegister(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
        MD5Encrypt m;
        m = new MD5Encrypt();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String logtime;
        logtime = dateFormat.format(cal.getTime());
        DAO.SetPass(txtUsername.getText(), m.hashPass(newPassword.getText()), m.hashPass((String) newSerectQuestion.getValue()) ,m.hashPass((String) newSerectAnswer.getText()));
        String Content = "Set Password";
        DAO.setUserLogs(txtUsername.getText(), Content , logtime);
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
