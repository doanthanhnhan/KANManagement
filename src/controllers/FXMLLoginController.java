/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import view.Login;
import models.DAO;
import models.InfoEmployee;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ConnectControllers.setfXMLLoginController(this);
        try {
            List_Employee = DAO.getAllEmployee();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static ObservableList<InfoEmployee> List_Employee = FXCollections.observableArrayList();
    @FXML
    private void handleLoginAction(ActionEvent event) throws ClassNotFoundException, SQLException, IOException { 
        for (InfoEmployee infoEmployee : List_Employee) {
            MD5Encrypt m = new MD5Encrypt();
            String hashPass = m.hashPass(txtPassword.getText());
            if(txtUserName.getText().equals(infoEmployee.getUserName())&&hashPass.equals(infoEmployee.getPassWord())){
                List_EmployeeLogin.add(infoEmployee);
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                stage.close();
                Stage stageEdit = new Stage();
                Parent rootAdd;
                if(DAO.checkSetPass(txtUserName.getText())==0){
                    rootAdd = FXMLLoader.load(getClass().getResource("/fxml/FXMLAccount.fxml"));
                    stageEdit.setTitle("Set Password");
                }
                else {
                    rootAdd = FXMLLoader.load(getClass().getResource("/fxml/FXMLMainForm.fxml"));
                    stageEdit.setTitle("KANManagementLogin");
                }
                
                Scene scene1;
                scene1 = new Scene(rootAdd);
                
                stageEdit.getIcons().add(new Image("/images/iconmanagement.png"));
                stageEdit.setScene(scene1);
                stageEdit.show();
                break;
            }
            else{
                System.out.println("False");
            }
        }
    }
    @FXML
    private void handleForgetPass() throws IOException{
        if("".equals(txtUserName.getText())){
            System.out.println("Empty");
        }
        else{
            for( int i = 0 ; i < List_Employee.size();i++){
                if(txtUserName.getText().equals(List_Employee.get(i).getUserName())){
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
                }
                else{
                    System.out.println("Invalid");
                }
            }
        }
        
    }
}
