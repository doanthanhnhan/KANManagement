/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Admin
 */
public class showFXMLLogin {
    public void showFormLogin() throws IOException {
        Stage stageEdit = new Stage();
        stageEdit.resizableProperty().setValue(Boolean.FALSE);
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/FXMLLogin.fxml"));
        stageEdit.getIcons().add(new Image("/images/KAN Logo.png"));
        Scene scene = new Scene(root);
        stageEdit.setScene(scene);
        stageEdit.show();
    }
}
