/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.RoomDAOImpl;
import models.RoomType;
import models.RoomTypeDAOImpl;

/**
 * FXML Controller class
 *
 * @author Doan Thanh Nhan
 */
public class FXMLRoomTypeController implements Initializable {

    @FXML
    private VBox vBox_RoomType_Info;
    @FXML
    private HBox hboxHeader;
    @FXML
    private HBox hBox_Info_Parent;
    @FXML
    private VBox vBox_Info_Left;
    @FXML
    private HBox HboxBoxId;
    @FXML
    private JFXComboBox<String> comboBox_Room_Type;
    @FXML
    private FontAwesomeIconView iconRefresh;
    @FXML
    private JFXTextField txt_Room_Type_Price;
    @FXML
    private JFXTextField txt_Room_Type_Discount;
    @FXML
    private HBox hBoxContent;
    @FXML
    private HBox hBoxBtn;
    @FXML
    private JFXButton btnSubmit;
    @FXML
    private JFXButton btnCancel;
    
    private FXMLMainFormController mainFormController;
    private RoomTypeDAOImpl roomTypeDAOImpl;
    private ObservableList<RoomType> list_Room_Type;
    private ObservableList<String> list_Room_Type_String;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainFormController = ConnectControllers.getfXMLMainFormController();
        roomTypeDAOImpl = new RoomTypeDAOImpl();
        list_Room_Type = roomTypeDAOImpl.getAllRoomType();
        list_Room_Type_String = roomTypeDAOImpl.getAllStringRoomType();
        
        //Init comboBox_Room_Type
        if(list_Room_Type_String.isEmpty()){
            comboBox_Room_Type.setDisable(true);
            btnSubmit.setDisable(true);
        } else {
            comboBox_Room_Type.setItems(list_Room_Type_String);
        }
        
    }    

    @FXML
    private void refreshRoomType(MouseEvent event) {
    }

    @FXML
    private void handle_Btn_Submit_Action(ActionEvent event) {
    }

    @FXML
    private void handle_Btn_Cancel_Action(ActionEvent event) {
    }
    
}
