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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author Admin
 */
public class FXMLLoginController implements Initializable {
    @FXML
    private AnchorPane formLogin;
    @FXML
    private JFXTextField txtUserName;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXButton btnLogin;
    @FXML
    private HBox hBoxPassword;
    public static Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MaterialIconView icon = new MaterialIconView(MaterialIcon.VPN_KEY);
        icon.setSize("35");
        icon.setId("passIcon");
        hBoxPassword.getChildren().add(0,icon);
    }

    @FXML
    private void handleLoginAction(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
        ObservableList<InfoEmployee> List_Employee = FXCollections.observableArrayList();
        List_Employee = DAO.getAllEmployee();
        for (InfoEmployee infoEmployee : List_Employee) {
            if(txtUserName.getText().equals(infoEmployee.getUserName())&&txtPassword.getText().equals(infoEmployee.getPassWord())){
                Login.stage.close();
                Stage stageEdit = new Stage();
                this.stage = stageEdit;
                Parent rootAdd = FXMLLoader.load(getClass().getResource("/fxml/FXMLMenu.fxml"));
                Scene scene1;
                scene1 = new Scene(rootAdd);
                stageEdit.setTitle("Management");
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

}
