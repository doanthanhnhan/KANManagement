/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javafx.scene.control.Alert;

/**
 *
 * @author Admin
 */
public class AlertLoginAgain {

    public static void alertLogin() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("You have no right to do this !!!");
        alert.setContentText("Please Login again !!!");
        alert.showAndWait();
    }
}
