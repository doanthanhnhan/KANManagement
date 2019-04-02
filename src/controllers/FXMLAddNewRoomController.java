/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.DAO;
import models.RoomDAOImpl;
import models.Room;
import utils.FormatName;
import utils.PatternValided;
import utils.StageLoader;

/**
 *
 * @author Doan Thanh Nhan
 */

public class FXMLAddNewRoomController implements Initializable {

    public static ObservableList<Room> listRooms;
    RoomDAOImpl roomDAOImpl;
    private FXMLListRoomsController listRoomsController;
    private FXMLMainFormController mainFormController;
    private Boolean check_Validate;

    @FXML
    private AnchorPane anchorPaneAddEmployee;
    @FXML
    private Label label_Title;
    @FXML
    private Label label_Description;
    @FXML
    private JFXTextField txt_Room_ID;
    @FXML
    private JFXComboBox<String> comboBox_Room_Type;
    @FXML
    private JFXTextField txt_Room_Phone_Number;
    @FXML
    private JFXTextField txt_Room_On_Floor;
    @FXML
    private JFXTextField txt_Room_Area;
    @FXML
    private JFXComboBox<String> comboBox_Room_Status;
    @FXML
    private JFXCheckBox checkBox_Room_Clean;
    @FXML
    private JFXCheckBox checkBox_Room_Repaired;
    @FXML
    private JFXCheckBox checkBox_Room_In_Progress;
    @FXML
    private JFXComboBox<String> comboBox_Room_Customer_ID;
    @FXML
    private JFXTextField txt_Room_Day_Remaining;
    @FXML
    private HBox hBoxContent;
    @FXML
    private JFXButton btnAddNew;
    @FXML
    private JFXButton btnCancel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Add new Room initialize...");
        roomDAOImpl = new RoomDAOImpl();
        mainFormController = ConnectControllers.getfXMLMainFormController();
        listRoomsController = ConnectControllers.getfXMLListRoomsController();
        init_ComboBox_Value();
        //Check form was call from List Room Form
        if (listRoomsController.check_Edit_Action) {
            //Setting values for new form
            txt_Room_ID.setDisable(true);
            label_Title.setText("EDITING ROOM");
            label_Description.setText("Filling the infomations for editting room infomations");
            Room room = FXMLListRoomsController.roomEXItem;
            txt_Room_ID.setText(room.getRoomID());
            txt_Room_Area.setText(String.valueOf(room.getRoomArea()));
            txt_Room_Day_Remaining.setText(String.valueOf(room.getDayRemaining()));
            txt_Room_On_Floor.setText(String.valueOf(room.getRoomOnFloor()));
            txt_Room_Phone_Number.setText(room.getRoomPhoneNumber());
            comboBox_Room_Customer_ID.setValue(room.getCustomerID());
            //comboBox_Room_Customer_ID.setDisable(true);
            comboBox_Room_Status.setValue(room.getRoomStatus());
            //comboBox_Room_Status.setDisable(true);
            comboBox_Room_Type.setValue(room.getRoomType());
            checkBox_Room_Clean.setSelected(room.isRoomClean());
            checkBox_Room_In_Progress.setSelected(room.isRoomInProgress());
            checkBox_Room_Repaired.setSelected(room.isRoomRepaired());

            btnAddNew.setText("Update");

            //Setting Update button function
            btnAddNew.setOnAction((event) -> {
                submit_Update_Room();
            });

            // Setting keypress ENTER for updating function
            txt_Room_Area.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                txt_Room_Area.getStyleClass().remove("text-field-fault");
                hBoxContent.getChildren().clear();
                if (event.getCode() == KeyCode.ENTER) {
                    submit_Update_Room();
                }
            });
            txt_Room_Day_Remaining.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                txt_Room_Day_Remaining.getStyleClass().remove("text-field-fault");
                hBoxContent.getChildren().clear();
                if (event.getCode() == KeyCode.ENTER) {
                    submit_Update_Room();
                }
            });
        } else {
            // Setting keypress ENTER for adding function
            txt_Room_Area.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                txt_Room_Area.getStyleClass().remove("text-field-fault");
                hBoxContent.getChildren().clear();
                if (event.getCode() == KeyCode.ENTER) {
                    submit_Add_New_Room();
                }
            });
            txt_Room_Day_Remaining.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                txt_Room_Day_Remaining.getStyleClass().remove("text-field-fault");
                hBoxContent.getChildren().clear();
                if (event.getCode() == KeyCode.ENTER) {
                    submit_Add_New_Room();
                }
            });
        }
    }

    @FXML
    private void handle_Button_Save_Action(ActionEvent event) {
        submit_Add_New_Room();
    }

    @FXML
    private void handle_Button_Cancel_Action(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public void init_ComboBox_Value() {
        ObservableList<String> list_Status = FXCollections.observableArrayList();
        ObservableList<String> list_Type = FXCollections.observableArrayList();
        ObservableList<String> list_CustomerID = FXCollections.observableArrayList();
        list_Status.addAll("Available", "Reserved", "Occupied", "Out");
        list_Type.addAll("Single", "Double", "Triple", "Family", "Deluxe");        
        listRoomsController.listRoomEXs.forEach((roomEX) -> {
            list_CustomerID.add(roomEX.getCustomerID());
        });
        comboBox_Room_Status.setItems(list_Status);
        comboBox_Room_Type.setItems(list_Type);
        comboBox_Room_Customer_ID.setItems(list_CustomerID);
    }

    public void submit_Add_New_Room() {
        btnAddNew.setDisable(true);
        //Calling loading form and thread task
        StageLoader stageLoader = new StageLoader();
        stageLoader.loadingIndicator("Adding Room");
        Task loadOverview;
        loadOverview = new Task() {
            @Override
            protected Object call() throws Exception {
                System.out.println("Loading...");
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        hBoxContent.getChildren().clear();
                    }
                });
                validateForm();
                if (check_Validate) {
                    addNewRoom();
                    DAO.setUserLogs_With_MAC(mainFormController.getUserRole().getEmployee_ID(), "Add new room: " 
                            + FormatName.format(txt_Room_ID.getText()), 
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), mainFormController.macAdress);
                    System.out.println("Add successful!");
                }
                return null;
            }
        };

        loadOverview.setOnSucceeded(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                Platform.runLater(() -> {
                    btnAddNew.setDisable(false);
                    stageLoader.stopTimeline();
                    stageLoader.closeStage();
                    System.out.println("Form check validate: " + check_Validate);
                    if (check_Validate) {
                        listRoomsController.showRoomsData();
                        Stage stageUpdate = (Stage) btnAddNew.getScene().getWindow();
                        stageUpdate.close();
                    }
                });
                System.out.println("Finished");
            }
        });
        new Thread(loadOverview).start();
    }

    public void submit_Update_Room() {
        StageLoader stageLoader = new StageLoader();
        stageLoader.loadingIndicator("Updating Room");
        Task loadOverview;
        loadOverview = new Task() {
            @Override
            protected Object call() throws Exception {
                System.out.println("Loading...");
                Platform.runLater(() -> {
                    hBoxContent.getChildren().clear();
                });
                validateForm();
                if (check_Validate) {
                    Room updateRoom = getDataFromForm();
                    //Updating to DB
                    roomDAOImpl.editRoom(updateRoom, true);
                    DAO.setUserLogs_With_MAC(mainFormController.getUserRole().getEmployee_ID(), "Update room: " 
                            + FormatName.format(txt_Room_ID.getText()), 
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), mainFormController.macAdress);
                    System.out.println("Updating successful!");
                }
                return null;
            }
        };

        loadOverview.setOnSucceeded((Event event1) -> {
            Platform.runLater(() -> {
                stageLoader.stopTimeline();
                stageLoader.closeStage();
                System.out.println("Form check validate: " + check_Validate);
                if (check_Validate) {
                    listRoomsController.showRoomsData();
                    Stage stageUpdate = (Stage) btnAddNew.getScene().getWindow();
                    stageUpdate.close();
                }
            });
            System.out.println("Finished");
        });

        new Thread(loadOverview).start();
    }

    public void addNewRoom() {        
        Room room = getDataFromForm();        
        roomDAOImpl.addRoom(room);        
    }

    public Room getDataFromForm() {
        Room room = new Room();
        room.setRoomID(FormatName.format(txt_Room_ID.getText()));
        room.setCustomerID(FormatName.format(comboBox_Room_Customer_ID.getValue()));        
        room.setRoomType(FormatName.format(comboBox_Room_Type.getValue()));
        room.setRoomPhoneNumber(FormatName.format(txt_Room_Phone_Number.getText()));
        room.setDayRemaining(Integer.parseInt(FormatName.format(txt_Room_Day_Remaining.getText())));
        room.setRoomOnFloor(Integer.parseInt(FormatName.format(txt_Room_On_Floor.getText())));
        room.setRoomArea(Float.parseFloat(FormatName.format(txt_Room_Area.getText())));
        room.setRoomClean(checkBox_Room_Clean.isSelected());
        room.setRoomRepaired(checkBox_Room_Repaired.isSelected());
        room.setRoomInProgress(checkBox_Room_In_Progress.isSelected());
        room.setRoomStatus(FormatName.format(comboBox_Room_Status.getValue()));
        room.setUserName(mainFormController.userRole.getEmployee_ID());
        return room;
    }

    public void validateForm() {
        if (PatternValided.validateTextField(hBoxContent, txt_Room_Area, "^[\\d][\\d]*\\.?[\\d]*$",
                "ROOM AREA MUST NOT BE EMPTY !!!",
                "AREA MUST CONTAIN NUMBERS, \nMUST BE NOT CONTAIN CHARACTER OR CHARACTER SPECIAL !!!")) {
            System.out.println("Room area false");
            check_Validate = false;
        } else if (PatternValided.validateTextField(hBoxContent, txt_Room_Day_Remaining, "[\\d]{1,4}",
                "ROOM DAYS REMAINING MUST NOT BE EMPTY !!!",
                "DAY MUST CONTAIN 1-4 NUMBERS, \nMUST BE NOT CONTAIN CHARACTER OR CHARACTER SPECIAL !!!")) {
            System.out.println("Room day remaining false");
            check_Validate = false;
        } else if (PatternValided.validateTextField(hBoxContent, txt_Room_ID, "^(?=.{1,20}$)[a-zA-Z][a-zA-Z0-9_]+$",
                "ROOM ID MUST NOT BE EMPTY !!!",
                "ID MUST CONTAIN 1-20 CHARACTER, \nBEGINNING CHAR MUST NOT BE NUMBER OR SPECIAL CHARACTER  !!!")) {
            System.out.println("Room ID false");
            check_Validate = false;
        } else if (PatternValided.validateTextField(hBoxContent, txt_Room_On_Floor, "[\\d]{1,4}",
                "ROOM ON FLOOR MUST NOT BE EMPTY !!!",
                "FLOOR MUST CONTAIN INTEGER NUMBER \nAND MUST BE CORRECT NUMBER FORMAT !!!")) {
            System.out.println("Room on floor false");
            check_Validate = false;
        } else if (PatternValided.validateTextField(hBoxContent, txt_Room_Phone_Number, "^([0-9][0-9]{1,19}$)|\\+84[0-9]{1,17}$",
                "ROOM PHONE NUMBER MUST NOT BE EMPTY !!!",
                "PHONE NUMBER MUST CONTAIN NUMBER \nAND MUST BE CORRECT PHONE NUMBER FORMAT !!!")) {
            System.out.println("Room on floor false");
            check_Validate = false;
        } else {
            System.out.println("Validate finished");
            System.out.println("Add finished");
            check_Validate = true;
        }
    }

}
