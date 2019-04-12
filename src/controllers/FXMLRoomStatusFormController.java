/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fxml.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Doan Thanh Nhan
 */
public class FXMLRoomStatusFormController implements Initializable {

    @FXML
    private Label label_Room_Number;
    @FXML
    private Label label_Room_Status;
    @FXML
    private Label label_Date_Remaining;
    @FXML
    private Label label_Clean_Status;
    @FXML
    private Label label_Repair_Status;
    @FXML
    private JFXButton btn_Check_In;
    @FXML
    private JFXButton btn_Check_Out;
    @FXML
    private Label label_Customer_Name;
    @FXML
    private FontAwesomeIconView icon_Room_Type;
    @FXML
    private AnchorPane anchorPane_Room;
    @FXML
    private JFXButton btn_Service;
    @FXML
    private HBox hBox_Buttons;
    @FXML
    private Label label_Booking;
    @FXML
    private Label label_No_Of_Guests;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void set_label_Room_Number(String text){
        label_Room_Number.setText(text);
    }
}
