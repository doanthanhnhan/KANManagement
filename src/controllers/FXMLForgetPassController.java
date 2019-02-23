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
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import models.DAO;
import models.InfoEmployee;
import utils.MD5Encrypt;

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
    private ObservableList<InfoEmployee> employeeForget = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        employeeForget = FXMLLoginController.employeeForget;
        ObservableList list_question = FXCollections.observableArrayList();
        ObservableList list_question_random = FXCollections.observableArrayList();
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
        txtSerectQuestion.setItems(list_question_random);
        txtForgetUsername.setText(employeeForget.get(0).getUserName());
        // TODO
    }    

    @FXML
    private void handleForgetAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        MD5Encrypt m = new MD5Encrypt();
        System.out.println(employeeForget.get(0).getSerect_Question());
        System.out.println(m.hashPass((String) txtSerectQuestion.getValue()));
        if(DAO.check_Active(txtForgetUsername.getText()).equals(0)){
                    System.out.println("Account Blocked !!!");
        }
        if(employeeForget.get(0).getSerect_Question().equals(m.hashPass((String) txtSerectQuestion.getValue()))
                &&employeeForget.get(0).getSerect_Answer().equals(m.hashPass(textSerectAnswer.getText()))){
            DAO.forgetPass(txtForgetUsername.getText(), m.hashPass(txtForgetPassword.getText()));
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            String logtime;
            logtime = dateFormat.format(cal.getTime());
            String content = "Change New Password";
            DAO.setUserLogs(txtForgetUsername.getText(), content, logtime);
            FXMLLoginController loginController = ConnectControllers.getfXMLLoginController();
            loginController.List_Employee = DAO.getAllEmployee();
            Stage stage = (Stage) btnSubmit.getScene().getWindow();
            stage.close();
        }
        else{
            System.out.println("Serect Question or Serect Answer Wrong !!!");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            String logtime;
            logtime = dateFormat.format(cal.getTime());
            if(!DAO.check_Time(txtForgetUsername.getText()).equals(logtime)){
                DAO.reset_CheckLogin(txtForgetUsername.getText(), logtime);
            }
            DAO.check_Login(txtForgetUsername.getText(), logtime);
        }

    }
    
}
