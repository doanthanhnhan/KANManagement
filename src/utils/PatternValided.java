/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 *
 */
public class PatternValided {

    public static Boolean PatternPassword(String pass) {
        Pattern patternPassword = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z#$^+=!*()@%&])(?=.*\\d).{8,20}$");
        return patternPassword.matcher(pass).matches();
    }

    public static Boolean PatternID(String id) {
        Pattern patternID = Pattern.compile("^(?=.{4,90}$)[a-zA-Z][a-zA-Z0-9_-]+$");
        return patternID.matcher(id).matches();
    }

    public static Boolean PatternEmail(String email) {
        Pattern patternEmail = Pattern.compile("^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$");
        return patternEmail.matcher(email).matches();
    }

    public static Boolean PatternName(String name) {
        Pattern patternName = Pattern.compile("^[\\pL\\s]+[\\pL\\pZ]{0,25}$");
        return patternName.matcher(name).matches();
    }

    public static Boolean PatternAnswer(String answer) {
        Pattern patternAnswer = Pattern.compile("^.{4,20}$");
        return patternAnswer.matcher(answer).matches();
    }

    public static Boolean PatternPhoneNumber(String phone) {
        Pattern phoneNumber = Pattern.compile("^([0-9][0-9]{1,19}$)|\\+84[0-9]{1,17}$");
        return phoneNumber.matcher(phone).matches();
    }

    public static Boolean PatternCMND(String cmnd) {
        Pattern CMND = Pattern.compile("^(?=.{4,20}$)[a-zA-Z\\d][a-zA-Z0-9_]+$");
        return CMND.matcher(cmnd).matches();
    }

    public static Boolean PatternELevel(String elevel) {
        Pattern eLevel = Pattern.compile("[\\d]{0,2}");
        return eLevel.matcher(elevel).matches();
    }

    public static Boolean PatternSalary(String value) {
        Pattern Salary = Pattern.compile("^[\\d][\\d]*\\.?[\\d]*$");
        return Salary.matcher(value).matches();
    }

    public static Boolean PatternNumberGuest(String value) {
        Pattern Number = Pattern.compile("[1-8]");
        return Number.matcher(value).matches();
    }

    /**
     * Pattern validation for JFXTextField
     *
     * @param hbox
     * @param textField
     * @param regexString
     * @param warningEmpty
     * @param warningPattern
     * @return Boolean
     */
    public static Boolean validateTextField(HBox hbox, JFXTextField textField, String regexString, String warningEmpty, String warningPattern) {
        Boolean check;
        Pattern pattern = Pattern.compile(regexString);
        if (textField.getText().equals("")) {
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(465, 35);
                label.setText(warningEmpty);
                //textField.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
                textField.getStyleClass().add("text-field-fault");
                hbox.setSpacing(10);
                hbox.setAlignment(Pos.CENTER);
                hbox.getChildren().clear();
                hbox.getChildren().add(icon);
                hbox.getChildren().add(label);
                textField.requestFocus();
            });
            check = true;
        } else if (!pattern.matcher(textField.getText()).matches()) {
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(465, 35);
                label.setText(warningPattern);
                textField.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
                hbox.setSpacing(10);
                hbox.setAlignment(Pos.CENTER);
                hbox.getChildren().clear();
                hbox.getChildren().add(icon);
                hbox.getChildren().add(label);
                textField.requestFocus();
            });
            check = true;
        } else {
            check = false;
        }
        return check;
    }

    /**
     * Pattern validation for JFXTextArea
     *
     * @param hbox
     * @param textField
     * @param regexString
     * @param warningEmpty
     * @param warningPattern
     * @return Boolean
     */
    public static Boolean validateTextArea(HBox hbox, JFXTextArea textField, String regexString, String warningEmpty, String warningPattern) {
        Boolean check;
        Pattern pattern = Pattern.compile(regexString);
        if (!textField.getText().equalsIgnoreCase("") && !pattern.matcher(textField.getText()).matches()) {
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(465, 35);
                label.setText(warningPattern);
                textField.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
                hbox.setSpacing(10);
                hbox.setAlignment(Pos.CENTER);
                hbox.getChildren().clear();
                hbox.getChildren().add(icon);
                hbox.getChildren().add(label);
                textField.requestFocus();
            });
            check = true;
        } else {
            check = false;
        }
        return check;
    }
}
