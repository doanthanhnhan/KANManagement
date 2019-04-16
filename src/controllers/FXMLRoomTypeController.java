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
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.DAO;
import models.RoomType;
import models.RoomTypeDAOImpl;
import utils.FormatName;
import utils.PatternValided;

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
    private FXMLListRoomTypeController listRoomTypeController;
    private RoomTypeDAOImpl roomTypeDAOImpl;
    private ObservableList<RoomType> list_Room_Type;
    private ObservableList<String> list_Room_Type_String;
    private boolean check_Validate;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ConnectControllers.setfXMLRoomTypeController(this);
        mainFormController = ConnectControllers.getfXMLMainFormController();
        listRoomTypeController = ConnectControllers.getfXMLListRoomTypeController();
        roomTypeDAOImpl = new RoomTypeDAOImpl();
        list_Room_Type = roomTypeDAOImpl.getAllRoomType();
        list_Room_Type_String = roomTypeDAOImpl.getAllStringRoomType();        

        //Init comboBox_Room_Type
        comboBox_Room_Type.setItems(list_Room_Type_String);
        if (listRoomTypeController.check_Edit_Action) {
            comboBox_Room_Type.setValue(listRoomTypeController.roomTypeItem.getType());
            comboBox_Room_Type.setDisable(true);
            txt_Room_Type_Price.setText(String.valueOf(listRoomTypeController.roomTypeItem.getPrice()));
            txt_Room_Type_Discount.setText(String.valueOf(listRoomTypeController.roomTypeItem.getDiscount()));
        }

        //Validate text field
        txt_Room_Type_Price.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!newValue.isEmpty()) {
                    if (PatternValided.PatternSalary(newValue)) {
                        txt_Room_Type_Price.getStyleClass().removeAll("text-field-fault");
                    } else {
                        txt_Room_Type_Price.setText(oldValue);
                        txt_Room_Type_Price.getStyleClass().add("text-field-fault");
                    }
                } else {
                    txt_Room_Type_Price.getStyleClass().removeAll("text-field-fault");
                    txt_Room_Type_Price.setText("");
                }
            } else {
                txt_Room_Type_Price.setText("");
            }
        });

        txt_Room_Type_Discount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!newValue.isEmpty()) {
                    if (PatternValided.PatternRoomDiscount(newValue)) {
                        txt_Room_Type_Discount.getStyleClass().removeAll("text-field-fault");
                    } else {
                        txt_Room_Type_Discount.setText(oldValue);
                        txt_Room_Type_Discount.getStyleClass().add("text-field-fault");
                    }
                } else {
                    txt_Room_Type_Discount.getStyleClass().removeAll("text-field-fault");
                    txt_Room_Type_Discount.setText("");
                }
            } else {
                txt_Room_Type_Discount.setText("");
            }
        });

    }

    public RoomType getDataFromForm() {
        RoomType roomType = new RoomType();
        roomType.setType(comboBox_Room_Type.getValue());
        roomType.setPrice(BigDecimal.valueOf(Double.valueOf(txt_Room_Type_Price.getText())));
        roomType.setDiscount(BigDecimal.valueOf(Double.valueOf(txt_Room_Type_Discount.getText())));
        roomType.setActive(true);
        return roomType;
    }

    public void validateForm() {
        if (comboBox_Room_Type.getValue() == null || comboBox_Room_Type.getValue().isEmpty()) {
            check_Validate = true;
            comboBox_Room_Type.requestFocus();
            comboBox_Room_Type.getStyleClass().add("jfx-combo-box-fault");
        } else if (txt_Room_Type_Price.getText() == null || txt_Room_Type_Price.getText().isEmpty()) {
            check_Validate = true;
            txt_Room_Type_Price.requestFocus();
            txt_Room_Type_Price.getStyleClass().add("text-field-fault");
        } else if (txt_Room_Type_Discount.getText() == null || txt_Room_Type_Discount.getText().isEmpty()) {
            check_Validate = true;
            txt_Room_Type_Discount.requestFocus();
            txt_Room_Type_Discount.getStyleClass().add("text-field-fault");
        } else {
            System.out.println("Validate room type finished");
            check_Validate = true;
        }
    }

    @FXML
    private void refreshRoomType(MouseEvent event) {
    }

    @FXML
    private void handle_Btn_Submit_Action(ActionEvent event) {
        validateForm();
        if (check_Validate) {
            RoomType roomType = getDataFromForm();
            roomTypeDAOImpl.editRoomType(roomType, true);
            DAO.setUserLogs_With_MAC(mainFormController.getUserRole().getEmployee_ID(), "Edit RoomType: "
                    + FormatName.format(comboBox_Room_Type.getValue()),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), mainFormController.macAdress);
            Stage stage = (Stage) btnSubmit.getScene().getWindow();
            stage.close();
            listRoomTypeController.showRoomsData();
        }
    }

    @FXML
    private void handle_Btn_Cancel_Action(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

}
