/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import models.Room;
import models.RoomDAOImpl;

/**
 * FXML Controller class
 *
 * @author Doan Thanh Nhan
 */
public class FXMLMainOverViewPaneController implements Initializable {

    public static ObservableList<Room> listRooms;
    RoomDAOImpl roomDAOImpl = new RoomDAOImpl();

    @FXML
    private AnchorPane aPane_Rooms;
    @FXML
    private JFXButton btn_OverView_Submit;
    @FXML
    private JFXCheckBox checkBox_Reserved;
    @FXML
    private Label label_Reserved_Rooms;
    @FXML
    private JFXCheckBox checkBox_Occupied;
    @FXML
    private Label label_Occupied_Rooms;
    @FXML
    private JFXCheckBox checkBox_Available;
    @FXML
    private Label label_Available_Rooms;
    @FXML
    private JFXCheckBox checkBox_Checkout;
    @FXML
    private Label label_Checkout_Rooms;
    @FXML
    private JFXCheckBox checkBox_Single;
    @FXML
    private Label label_Single_Rooms;
    @FXML
    private JFXCheckBox checkBox_Double;
    @FXML
    private Label label_Double_Rooms;
    @FXML
    private JFXCheckBox checkBox_Triple;
    @FXML
    private Label label_Triple_Rooms;
    @FXML
    private JFXCheckBox checkBox_Family;
    @FXML
    private Label label_Family_Rooms;
    @FXML
    private JFXCheckBox checkBox_Deluxe;
    @FXML
    private Label label_Deluxe_Rooms;
    @FXML
    private JFXCheckBox checkBox_Clean;
    @FXML
    private Label label_Clean_Rooms;
    @FXML
    private JFXCheckBox checkBox_NotClean;
    @FXML
    private Label label_NotClean_Rooms;
    @FXML
    private JFXCheckBox checkBox_InProgress;
    @FXML
    private Label label_InProgress_Rooms;
    @FXML
    private JFXCheckBox checkBox_Repair;
    @FXML
    private Label label_Repair_Rooms;
    @FXML
    private JFXComboBox<String> comboBox_FromFloor;
    @FXML
    private JFXComboBox<String> comboBox_ToFloor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initAddRooms();
    }

    public void initAddRooms() {
        listRooms = roomDAOImpl.getAllRoom();
        ArrayList<String> array_Status = new ArrayList<>(Arrays.asList("Available", "Reserved", "Occupied", "Check out"));
        ArrayList<String> array_Type = new ArrayList<>(Arrays.asList("Single", "Double", "Triple", "Family", "Deluxe"));
        ArrayList<String> array_HouseKeeping = new ArrayList<>(Arrays.asList("Clean", "Not clean", "Repair", "In progress"));
        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setVgap(10);

        add_Rooms_With_Condition(flowPane, array_Status, array_Type, array_HouseKeeping);

        aPane_Rooms.getChildren().add(flowPane);
        aPane_Rooms.setTopAnchor(flowPane, 0.0);
        aPane_Rooms.setBottomAnchor(flowPane, 0.0);
        aPane_Rooms.setLeftAnchor(flowPane, 0.0);
        aPane_Rooms.setRightAnchor(flowPane, 0.0);

        aPane_Rooms.getChildren().setAll(flowPane);
        checkBox_Available.setSelected(true);
        checkBox_Reserved.setSelected(true);
        checkBox_Occupied.setSelected(true);
        checkBox_Checkout.setSelected(true);
        checkBox_Single.setSelected(true);
        checkBox_Double.setSelected(true);
        checkBox_Triple.setSelected(true);
        checkBox_Family.setSelected(true);
        checkBox_Deluxe.setSelected(true);
        checkBox_Clean.setSelected(true);
        checkBox_NotClean.setSelected(true);
        checkBox_Repair.setSelected(true);
        checkBox_InProgress.setSelected(true);

        ObservableList<String> listFromFloor = FXCollections.observableArrayList();
        //Finding max floor
        Room roomAtMaxFloor = Collections.max(listRooms, new CompareRoom());
        System.out.println("Max Floor: " + roomAtMaxFloor.getRoomOnFloor());

        for (int i = 1; i <= roomAtMaxFloor.getRoomOnFloor(); i++) {
            listFromFloor.add(String.valueOf(i));
        }

        comboBox_FromFloor.getItems().addAll(listFromFloor);
        comboBox_ToFloor.getItems().addAll(listFromFloor);
        
        // Validating when comboBox change value
        comboBox_FromFloor.valueProperty().addListener((obs, oldItem, newItem) -> {
            System.out.println("Min floor changed");
            if (Integer.parseInt(comboBox_FromFloor.getValue()) > Integer.parseInt(comboBox_ToFloor.getValue())) {                
                
                System.out.println("Min > Max : " + comboBox_FromFloor.getStyleClass());
                comboBox_FromFloor.getStyleClass().add("jfx-combo-box-fault");
            } else {
                comboBox_FromFloor.getStyleClass().removeAll("jfx-combo-box-fault");
            }
        });
        comboBox_ToFloor.valueProperty().addListener((obs, oldItem, newItem) -> {
            System.out.println("Max floor changed");
            if (Integer.parseInt(comboBox_FromFloor.getValue()) > Integer.parseInt(comboBox_ToFloor.getValue())) {                
                //comboBox_ToFloor.getStyleClass().removeAll("jfx-combo-box");
                System.out.println("Max < Min : " + comboBox_ToFloor.getStyleClass());
                comboBox_ToFloor.getStyleClass().add("jfx-combo-box-fault");
            } else {
                comboBox_ToFloor.getStyleClass().removeAll("jfx-combo-box-fault");
            }
        });
    }

    @FXML
    private void submit_Loading_Overview(ActionEvent event) {
        listRooms = roomDAOImpl.getAllRoom();
        System.out.println("Button clicked.");
        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        ArrayList<String> listStatus = new ArrayList<>();
        if (!checkBox_Available.isSelected() && !checkBox_Reserved.isSelected() && !checkBox_Occupied.isSelected() && !checkBox_Checkout.isSelected()) {
            System.out.println("None selected type");
            listStatus = new ArrayList<>(Arrays.asList("Available", "Reserved", "Occupied", "Check out"));
        } else {
            System.out.println("Exist selected type");
            listStatus.clear();
            if (checkBox_Available.isSelected()) {
                listStatus.add("Available");
            }
            if (checkBox_Reserved.isSelected()) {
                listStatus.add("Reserved");
            }
            if (checkBox_Occupied.isSelected()) {
                listStatus.add("Occupied");
            }
            if (checkBox_Checkout.isSelected()) {
                listStatus.add("Check out");
            }
        }

        ArrayList<String> listTypes = new ArrayList<>();
        if (!checkBox_Single.isSelected() && !checkBox_Double.isSelected() && !checkBox_Triple.isSelected() && !checkBox_Family.isSelected() && !checkBox_Deluxe.isSelected()) {
            listTypes = new ArrayList<>(Arrays.asList("Single", "Double", "Triple", "Family", "Deluxe"));
        } else {
            listTypes.clear();
            if (checkBox_Single.isSelected()) {
                listTypes.add("Single");
            }
            if (checkBox_Double.isSelected()) {
                listTypes.add("Double");
            }
            if (checkBox_Triple.isSelected()) {
                listTypes.add("Triple");
            }
            if (checkBox_Family.isSelected()) {
                listTypes.add("Family");
            }
            if (checkBox_Deluxe.isSelected()) {
                listTypes.add("Deluxe");
            }
        }

        ArrayList<String> listHouseKeeping = new ArrayList<>();
        if (!checkBox_Clean.isSelected() && !checkBox_NotClean.isSelected() && !checkBox_Repair.isSelected() && !checkBox_InProgress.isSelected()) {
            listHouseKeeping = new ArrayList<>(Arrays.asList("Clean", "Not clean", "Repair", "In progress"));
        } else {
            listHouseKeeping.clear();
            if (checkBox_Clean.isSelected()) {
                listHouseKeeping.add("Clean");
            }
            if (checkBox_NotClean.isSelected()) {
                listHouseKeeping.add("Not clean");
            }
            if (checkBox_Repair.isSelected()) {
                listHouseKeeping.add("Repair");
            }
            if (checkBox_InProgress.isSelected()) {
                listHouseKeeping.add("In progress");
            }
        }
        System.out.println("List status: " + listStatus.toString());

        add_Rooms_With_Condition(flowPane, listStatus, listTypes, listHouseKeeping);

        aPane_Rooms.getChildren().add(flowPane);
        aPane_Rooms.setTopAnchor(flowPane, 0.0);
        aPane_Rooms.setBottomAnchor(flowPane, 0.0);
        aPane_Rooms.setLeftAnchor(flowPane, 0.0);
        aPane_Rooms.setRightAnchor(flowPane, 0.0);

        aPane_Rooms.getChildren().setAll(flowPane);
        System.out.println("Number of child node : " + aPane_Rooms.getChildren().size());

    }

    public void add_Rooms_With_Condition(
            Pane parentPane,
            ArrayList<String> roomStatus,
            ArrayList<String> roomType,
            ArrayList<String> roomHouseKeeping) {
        try {
            for (Room listRoom : listRooms) {
                AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/FXMLRoomStatusForm.fxml"));
                Label label_Room_Number = (Label) pane.lookup("#label_Room_Number");
                Label label_Room_Status = (Label) pane.lookup("#label_Room_Status");
                Label label_Date_Remaining = (Label) pane.lookup("#label_Date_Remaining");
                Label label_Clean_Status = (Label) pane.lookup("#label_Clean_Status");
                Label label_Repaired_Status = (Label) pane.lookup("#label_Repaired_Status");
                Label label_Customer_Name = (Label) pane.lookup("#label_Customer_Name");
                //HBox hbox_Room_Owner_Type = (HBox) pane.lookup("#hbox_Room_Owner_Type");                
                FontAwesomeIconView icon_Room_Type = (FontAwesomeIconView) pane.lookup("#icon_Room_Type");
                FontAwesomeIconView icon_Date_Remaining = (FontAwesomeIconView) pane.lookup("#icon_Date_Remaining");
                FontAwesomeIconView icon_Clean_Status = (FontAwesomeIconView) pane.lookup("#icon_Clean_Status");
                FontAwesomeIconView icon_Repaired_Status = (FontAwesomeIconView) pane.lookup("#icon_Repaired_Status");
                //System.out.println(pane.lookup("#label_Customer_Name"));
                JFXButton btn_CheckIn = (JFXButton) pane.lookup("#btn_CheckIn");
                btn_CheckIn.setOnAction((event) -> {
                    System.out.println("Room " + label_Room_Number.getText() + " check in!");
                });
                JFXButton btn_CheckOut = (JFXButton) pane.lookup("#btn_CheckOut");
                btn_CheckOut.setOnAction((event) -> {
                    System.out.println("Room " + label_Room_Number.getText() + " check out!");
                });
                label_Room_Number.setText(listRoom.getRoomID());
                label_Room_Status.setText(listRoom.getRoomStatus());
                if (listRoom.getRoomStatus().equalsIgnoreCase("Available") || listRoom.getRoomStatus().equalsIgnoreCase("Reserved")) {
                    btn_CheckOut.setDisable(true);
                    if (listRoom.getRoomStatus().equalsIgnoreCase("Available")) {
                        label_Room_Status.getStyleClass().removeAll();
                        label_Room_Status.getStyleClass().add("label-roomAvailable-room-status");
                    } else {
                        label_Room_Status.getStyleClass().removeAll();
                        label_Room_Status.getStyleClass().add("label-roomReserved-room-status");
                    }
                }
                if (listRoom.getRoomStatus().equalsIgnoreCase("Check out") || listRoom.getRoomStatus().equalsIgnoreCase("Occupied")) {
                    btn_CheckIn.setDisable(true);
                    if (listRoom.getRoomStatus().equalsIgnoreCase("Check out")) {
                        label_Room_Status.getStyleClass().removeAll();
                        label_Room_Status.getStyleClass().add("label-roomCheckOut-room-status");
                    } else {
                        label_Room_Status.getStyleClass().removeAll();
                        label_Room_Status.getStyleClass().add("label-roomOccupied-room-status");
                    }
                }
                if (listRoom.getRoomType().equalsIgnoreCase("Single")) {
                    icon_Room_Type.setGlyphName("USER_ALT");
                }
                if (listRoom.getRoomType().equalsIgnoreCase("Double")) {
                    icon_Room_Type.setGlyphName("USER");
                }
                if (listRoom.getRoomType().equalsIgnoreCase("Triple")) {
                    icon_Room_Type.setGlyphName("USERS");
                }
                if (listRoom.getRoomType().equalsIgnoreCase("Family")) {
                    icon_Room_Type.setGlyphName("USER_PLUS");
                }
                if (listRoom.getRoomType().equalsIgnoreCase("Special")) {
                    icon_Room_Type.setGlyphName("USER_SECRET");
                }
                if (listRoom.isRoomInProgress()) {
                    label_Date_Remaining.setText("Checking");
                    label_Date_Remaining.getStyleClass().removeAll();
                    label_Date_Remaining.getStyleClass().add("label-NotClean-Status");
                    icon_Date_Remaining.setGlyphName("CLOCK_ALT");
                    icon_Date_Remaining.getStyleClass().removeAll();
                    icon_Date_Remaining.getStyleClass().add("glyph-icon-clean-repair-inprogress-status");
                }
                if (!listRoom.isRoomRepaired()) {
                    label_Repaired_Status.setText("N.Repair");
                    label_Repaired_Status.getStyleClass().removeAll();
                    label_Repaired_Status.getStyleClass().add("label-NotClean-Status");
                    icon_Repaired_Status.setGlyphName("WARNING");
                    icon_Repaired_Status.getStyleClass().removeAll();
                    icon_Repaired_Status.getStyleClass().add("glyph-icon-clean-repair-inprogress-status");
                }
                if (!listRoom.isRoomClean()) {
                    label_Clean_Status.setText("N.Clean");
                    label_Clean_Status.getStyleClass().removeAll();
                    label_Clean_Status.getStyleClass().add("label-NotClean-Status");
                    icon_Clean_Status.setGlyphName("CLOSE");
                    icon_Clean_Status.getStyleClass().removeAll();
                    icon_Clean_Status.getStyleClass().add("glyph-icon-clean-repair-inprogress-status");
                }
                // Convert HouseKeeping to String list
                ArrayList<String> listHouseKeeping = new ArrayList<>();
                if (listRoom.isRoomClean()) {
                    listHouseKeeping.add("Clean");
                }
                if (!listRoom.isRoomClean()) {
                    listHouseKeeping.add("Not clean");
                }
                if (listRoom.isRoomRepaired()) {
                    listHouseKeeping.add("Repair");
                }
                if (listRoom.isRoomInProgress()) {
                    listHouseKeeping.add("In progress");
                }
                //Check room status and add to specific Pane
                Boolean checkListHK = false, checkType = false, checkHouseKeeping = false;
                for (String status : roomStatus) {
                    for (String type : roomType) {
                        for (String houseKeeping : roomHouseKeeping) {
                            for (String stringHouseKeeping : listHouseKeeping) {
                                if (status.equalsIgnoreCase(listRoom.getRoomStatus()) && type.equals(listRoom.getRoomType()) && houseKeeping.equalsIgnoreCase(stringHouseKeeping)) {
                                    parentPane.getChildren().add(pane);
                                    checkListHK = true;
                                    break;
                                }
                            }
                            if (checkListHK) {
                                checkHouseKeeping = true;
                                break;
                            }
                        }
                        // Dùng để test trường hợp 2 thuộc tính
//                        if (status.equalsIgnoreCase(listRoom.getRoomStatus()) && type.equals(listRoom.getRoomType())) {
//                            parentPane.getChildren().add(pane);
//                            checkType = true;
//                            break;
//                        }
                        if (checkHouseKeeping) {
                            checkType = true;
                            break;
                        }
                    }
                    if (checkType) {
                        break;
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(FXMLMainOverViewPaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * CompareRoom:
     *
     * @return: Max floor.
     */
    public class CompareRoom implements Comparator<Room> {

        @Override
        public int compare(Room r1, Room r2) {

            if (r1.getRoomOnFloor() == r2.getRoomOnFloor()) {
                return 0;
            } else {
                if (r1.getRoomOnFloor() > r2.getRoomOnFloor()) {
                    return 1; // highest value first
                } else {
                    return -1;
                }
            }
        }
    }
}
