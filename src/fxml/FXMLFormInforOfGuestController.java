/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxml;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class FXMLFormInforOfGuestController implements Initializable {

    @FXML
    private Label labelName;
    @FXML
    private JFXComboBox<String> comboBoxRoom;

    ObservableList<String> listComboBoxRoom = FXCollections.observableArrayList("Standard", "Superior", "Deluxe", "Suite");
    
    @FXML
    private JFXCheckBox check1;

    @FXML
    private JFXButton btnSubmit;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        comboBoxRoom.setItems(listComboBoxRoom);
        comboBoxRoom.setValue("Room");

        check1.setSelected(true);
    }

    @FXML
    private void handleSubmitAction(ActionEvent event) {
    }

}
