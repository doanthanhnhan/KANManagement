/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 *
 * @author Admin
 */
public class notificationFunction {

    public static void notification(JFXTextField textField, HBox hboxContent, String content) {
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
        icon.setSize("16");
        icon.setStyleClass("jfx-glyhp-icon");
        Label label = new Label();
        label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
        label.setPrefSize(300, 35);
        label.setText(content);
        textField.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
        hboxContent.setSpacing(10);
        hboxContent.getChildren().clear();
        hboxContent.getChildren().add(icon);
        hboxContent.getChildren().add(label);
        textField.requestFocus();
    }

    public static void notificationPassword(JFXPasswordField textField, HBox hboxContent, String content) {
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
        icon.setSize("16");
        icon.setStyleClass("jfx-glyhp-icon");
        Label label = new Label();
        label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
        label.setPrefSize(300, 35);
        label.setText(content);
        textField.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
        hboxContent.setSpacing(10);
        hboxContent.getChildren().clear();
        hboxContent.getChildren().add(icon);
        hboxContent.getChildren().add(label);
        textField.requestFocus();
    }
}
