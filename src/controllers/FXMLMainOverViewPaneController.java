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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import models.Room;
import models.RoomDAOImpl;
import models.RoomProperty;

/**
 * FXML Controller class
 *
 * @author Doan Thanh Nhan
 */
public class FXMLMainOverViewPaneController implements Initializable {

    public static ObservableList<Room> listRooms;
    public ObservableList<RoomProperty> listRoomProperties;
    ObservableList<String> list_Status = FXCollections.observableArrayList();
    ObservableList<String> list_Type = FXCollections.observableArrayList();
    ObservableList<String> list_HouseKeeping = FXCollections.observableArrayList();
    RoomDAOImpl roomDAOImpl = new RoomDAOImpl();

    private FilteredList<Room> filteredRoom;

    FXMLMainFormController mainFormController;

    public Boolean check_Services_Button_Clicked;
    public Boolean check_Check_In_Button_Clicked;
    public Boolean check_Check_Out_Button_Clicked;
    public String service_Room_ID;
    public String service_Customer_ID;
    public String service_Customer_Full_Name;
    public LocalDateTime room_Check_In_Date;

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
    @FXML
    private VBox vBox_Filters;
    @FXML
    private JFXCheckBox checkBox_Remaining_Days;
    @FXML
    private Label label_Remaining_Days;
    @FXML
    private JFXCheckBox checkBox_Not_Repair;
    @FXML
    private Label label_Not_Repair_Rooms;
    @FXML
    private FlowPane flowPane_Rooms;
    @FXML
    private ScrollPane scrollPane_Rooms;
    @FXML
    private AnchorPane anchorPane_Main;
    @FXML
    private SplitPane splitPane_Rooms;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Main overview pane controller initialize...");

        ConnectControllers.setfXMLMainOverViewPaneController(this);
        mainFormController = ConnectControllers.getfXMLMainFormController();

        //Initialize label room property
        init_Label_Room_Property();

        check_Services_Button_Clicked = false;

        if (!roomDAOImpl.getAllRoom().isEmpty()) {
            initAddRooms();
        } else {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error");
                alert.setContentText("Don't have any rooms in Database or Can't connect to Database");
                alert.show();
            });
        }
    }

    public void initAddRooms() {
        System.out.println("Test chạy không????");
        //Disable splitPane_Room auto resize
        SplitPane.setResizableWithParent(anchorPane_Main, false);
        listRooms = roomDAOImpl.getAllRoom();
        System.out.println("listRooms size = " + listRooms.size());

        // Make all check boxes bean ticked
//        checkBox_Available.setSelected(true);
//        checkBox_Reserved.setSelected(true);
//        checkBox_Occupied.setSelected(true);
//        checkBox_Checkout.setSelected(true);
//        checkBox_Single.setSelected(true);
//        checkBox_Double.setSelected(true);
//        checkBox_Triple.setSelected(true);
//        checkBox_Family.setSelected(true);
//        checkBox_Deluxe.setSelected(true);
//        checkBox_Clean.setSelected(true);
//        checkBox_NotClean.setSelected(true);
//        checkBox_Repair.setSelected(true);
//        checkBox_InProgress.setSelected(true);
        // Test checking role and removing node
        //vBox_Filters.getChildren().remove(checkBox_InProgress.getParent());
        //Adding room properties to list and do filtering
        checkBox_Property_Listener(list_Status, checkBox_Available, "Available");
        checkBox_Property_Listener(list_Status, checkBox_Reserved, "Reserved");
        checkBox_Property_Listener(list_Status, checkBox_Occupied, "Occupied");
        checkBox_Property_Listener(list_Status, checkBox_Checkout, "Out");

        checkBox_Property_Listener(list_Type, checkBox_Single, "Single");
        checkBox_Property_Listener(list_Type, checkBox_Double, "Double");
        checkBox_Property_Listener(list_Type, checkBox_Triple, "Triple");
        checkBox_Property_Listener(list_Type, checkBox_Family, "Family");
        checkBox_Property_Listener(list_Type, checkBox_Deluxe, "Deluxe");

        checkBox_Property_Listener(list_HouseKeeping, checkBox_Clean, "Clean");
        checkBox_Property_Listener(list_HouseKeeping, checkBox_NotClean, "Not clean");
        checkBox_Property_Listener(list_HouseKeeping, checkBox_Repair, "Repaired");
        checkBox_Property_Listener(list_HouseKeeping, checkBox_Not_Repair, "Not repaired");
        checkBox_Property_Listener(list_HouseKeeping, checkBox_InProgress, "In progress");
        checkBox_Property_Listener(list_HouseKeeping, checkBox_Remaining_Days, "Remaining days");

        //Add room into form
        add_Rooms_With_Condition(listRooms, flowPane_Rooms, list_Status, list_Type, list_HouseKeeping);

        //Set filterData and Pagination
        filteredRoom = new FilteredList<>(listRooms, list -> true);
        mainFormController = ConnectControllers.getfXMLMainFormController();
        mainFormController.getTxt_Search().textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Searching...");
            filteredRoom.setPredicate(
                    room -> newValue == null || newValue.isEmpty()
                    || room.getRoomID().toLowerCase().contains(newValue.toLowerCase())
                    || room.getRoomType().toLowerCase().contains(newValue.toLowerCase())
                    || room.getUserName().toLowerCase().contains(newValue.toLowerCase())
                    || room.getRoomStatus().toLowerCase().contains(newValue.toLowerCase())
                    || room.getCustomerName().toLowerCase().contains(newValue.toLowerCase())
                    || String.valueOf(room.getRoomOnFloor()).contains(newValue.toLowerCase())
                    || String.valueOf(room.getDayRemaining()).contains(newValue.toLowerCase())
                    || String.valueOf(room.getRoomArea()).contains(newValue.toLowerCase())
                    || room.getRoomPhoneNumber().toLowerCase().contains(newValue.toLowerCase()));
            //Add room into form
            add_Rooms_With_Condition(filteredRoom, flowPane_Rooms, list_Status, list_Type, list_HouseKeeping);
        });

        //Setting floor number into comboBox
        ObservableList<String> listFromFloor = FXCollections.observableArrayList();
        //Finding max floor
        Room roomAtMaxFloor = Collections.max(listRooms, new CompareRoom());
        //Finding min floor
        Room roomAtMinFloor = Collections.min(listRooms, new CompareRoom());
        System.out.println("Max Floor: " + roomAtMaxFloor.getRoomOnFloor());

        for (int i = 1; i <= roomAtMaxFloor.getRoomOnFloor(); i++) {
            listFromFloor.add(String.valueOf(i));
        }

        comboBox_FromFloor.getItems().addAll(listFromFloor);
        comboBox_FromFloor.setValue(String.valueOf(roomAtMinFloor.getRoomOnFloor()));
        comboBox_ToFloor.getItems().addAll(listFromFloor);
        comboBox_ToFloor.setValue(String.valueOf(roomAtMaxFloor.getRoomOnFloor()));

        // Validating when comboBox change value
        comboBox_FromFloor.valueProperty().addListener((obs, oldItem, newItem) -> {
            System.out.println("Min floor changed");
            if (Integer.parseInt(comboBox_FromFloor.getValue()) > Integer.parseInt(comboBox_ToFloor.getValue())) {
                comboBox_ToFloor.setValue(newItem);
            }
            // Call method check_Filter
            check_Filter(filteredRoom);
            //Add room into form
            add_Rooms_With_Condition(filteredRoom, flowPane_Rooms, list_Status, list_Type, list_HouseKeeping);
        });
        comboBox_ToFloor.valueProperty().addListener((obs, oldItem, newItem) -> {
            if (Integer.parseInt(comboBox_FromFloor.getValue()) > Integer.parseInt(comboBox_ToFloor.getValue())) {
                comboBox_FromFloor.setValue(newItem);
            }
            // Call method check_Filter
            check_Filter(filteredRoom);
            //Add room into form
            add_Rooms_With_Condition(filteredRoom, flowPane_Rooms, list_Status, list_Type, list_HouseKeeping);
        });
    }

    @FXML
    private void submit_Loading_Overview(ActionEvent event) {
        listRooms = roomDAOImpl.getAllRoom();
        filteredRoom = new FilteredList<>(listRooms, list -> true);
        System.out.println("Button clicked.");
        Predicate<Room> roomStatus = i -> {
            for (String status : list_Status) {
                if (i.getRoomStatus().contains(status)) {
                    return i.getRoomStatus().contains(status);
                }
            }
            return false;
        };
        filteredRoom.setPredicate(roomStatus);
        add_Rooms_With_Condition(filteredRoom, flowPane_Rooms, list_Status, list_Type, list_HouseKeeping);

    }

    public void init_Label_Room_Property() {
        listRoomProperties = roomDAOImpl.getAllRoomProperties();
        for (RoomProperty roomProperty : listRoomProperties) {

            switch (roomProperty.getRoomPropertyName()) {
                case "Available":
                    label_Available_Rooms.setText(roomProperty.getRoomCount().toString());
                    break;
                case "Reserved":
                    label_Reserved_Rooms.setText(roomProperty.getRoomCount().toString());
                    break;
                case "Occupied":
                    label_Occupied_Rooms.setText(roomProperty.getRoomCount().toString());
                    break;
                case "Out":
                    label_Checkout_Rooms.setText(roomProperty.getRoomCount().toString());
                    break;
                case "Single":
                    label_Single_Rooms.setText(roomProperty.getRoomCount().toString());
                    break;
                case "Double":
                    label_Double_Rooms.setText(roomProperty.getRoomCount().toString());
                    break;
                case "Triple":
                    label_Triple_Rooms.setText(roomProperty.getRoomCount().toString());
                    break;
                case "Family":
                    label_Family_Rooms.setText(roomProperty.getRoomCount().toString());
                    break;
                case "Deluxe":
                    label_Deluxe_Rooms.setText(roomProperty.getRoomCount().toString());
                    break;
                case "Clean":
                    label_Clean_Rooms.setText(roomProperty.getRoomCount().toString());
                    break;
                case "Not Clean":
                    label_NotClean_Rooms.setText(roomProperty.getRoomCount().toString());
                    break;
                case "Repaired":
                    label_Repair_Rooms.setText(roomProperty.getRoomCount().toString());
                    break;
                case "Not Repaired":
                    label_Not_Repair_Rooms.setText(roomProperty.getRoomCount().toString());
                    break;
                case "Checking":
                    label_InProgress_Rooms.setText(roomProperty.getRoomCount().toString());
                    break;
                case "Not Checking":
                    label_Remaining_Days.setText(roomProperty.getRoomCount().toString());
                    break;

                default:
                    throw new AssertionError();
            }
        }
    }

    public void add_Rooms_With_Condition(
            ObservableList<Room> list_Rooms,
            Pane parentPane,
            ObservableList<String> roomStatus,
            ObservableList<String> roomType,
            ObservableList<String> roomHouseKeeping) {
        parentPane.getChildren().clear();
        try {
            for (Room listRoom : list_Rooms) {
                System.out.println("RoomID = " + listRoom.getRoomID());
                AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/FXMLRoomStatusForm.fxml"));
                Label label_Room_Number = (Label) pane.lookup("#label_Room_Number");
                Label label_Room_Status = (Label) pane.lookup("#label_Room_Status");
                Label label_Date_Remaining = (Label) pane.lookup("#label_Date_Remaining");
                Label label_Clean_Status = (Label) pane.lookup("#label_Clean_Status");
                Label label_Repaired_Status = (Label) pane.lookup("#label_Repaired_Status");
                Label label_Customer_Name = (Label) pane.lookup("#label_Customer_Name");
                //HBox hbox_Room_Owner_Type = (HBox) pane.lookup("#hbox_Room_Owner_Type");                
                HBox hBox_Buttons = (HBox) pane.lookup("#hBox_Buttons");
                FontAwesomeIconView icon_Room_Type = (FontAwesomeIconView) pane.lookup("#icon_Room_Type");
                FontAwesomeIconView icon_Date_Remaining = (FontAwesomeIconView) pane.lookup("#icon_Date_Remaining");
                FontAwesomeIconView icon_Clean_Status = (FontAwesomeIconView) pane.lookup("#icon_Clean_Status");
                FontAwesomeIconView icon_Repaired_Status = (FontAwesomeIconView) pane.lookup("#icon_Repaired_Status");
                //System.out.println(pane.lookup("#label_Customer_Name"));
                JFXButton btn_CheckIn = (JFXButton) pane.lookup("#btn_CheckIn");
                btn_CheckIn.setOnAction((event) -> {
                    System.out.println("Room " + label_Room_Number.getText() + " check in!");
                    check_Check_In_Button_Clicked = true;
                    service_Room_ID = label_Room_Number.getText();
                    service_Customer_ID = listRoom.getCustomerID();
                    service_Customer_Full_Name = listRoom.getCustomerName();
                    mainFormController.formLoader("/fxml/FXMLCheckIdCardCustomer.fxml", "/images/KAN Logo.png",
                            "Check Id Card Customer: " + label_Room_Number.getText());
                });
                JFXButton btn_CheckOut = (JFXButton) pane.lookup("#btn_CheckOut");
                btn_CheckOut.setOnAction((event) -> {
                    System.out.println("Room " + label_Room_Number.getText() + " check out!");
                    check_Check_Out_Button_Clicked = true;
                    service_Room_ID = label_Room_Number.getText();
                    service_Customer_ID = listRoom.getCustomerID();
                    service_Customer_Full_Name = listRoom.getCustomerName();
                    room_Check_In_Date = listRoom.getCheckInDate();
                    mainFormController.formLoader("/fxml/FXMLCheckOut.fxml", "/images/KAN Logo.png",
                            "Check out order for Room: " + label_Room_Number.getText());
                });
                JFXButton btn_Services = (JFXButton) pane.lookup("#btn_Services");
                btn_Services.setOnAction((event) -> {
                    System.out.println("Room " + label_Room_Number.getText() + " services!");
                    check_Services_Button_Clicked = true;
                    service_Room_ID = label_Room_Number.getText();
                    service_Customer_ID = listRoom.getCustomerID();
                    mainFormController.formLoader("/fxml/FXMLAddNewServiceOrder.fxml", "/images/KAN Logo.png",
                            "Add new Service Order for Room: " + label_Room_Number.getText());
                });
                label_Room_Number.setText(listRoom.getRoomID());
                if (listRoom.getRoomStatus().equals("Reserved")){
                    label_Room_Status.setText(listRoom.getRoomStatus()+" "+listRoom.getBookingDate().toLocalDate()
                            .format(DateTimeFormatter.ofPattern("dd-MM-yy")).toString());
                } else{
                    label_Room_Status.setText(listRoom.getRoomStatus());
                }
                
                label_Customer_Name.setText(listRoom.getCustomerName());
                if (listRoom.getRoomStatus().equalsIgnoreCase("Available") || listRoom.getRoomStatus().equalsIgnoreCase("Reserved")) {
                    btn_CheckOut.setDisable(true);
                    hBox_Buttons.getChildren().remove(btn_Services);
                    if (listRoom.getRoomStatus().equalsIgnoreCase("Available")) {
                        label_Date_Remaining.setText("0 day");
                        label_Room_Status.getStyleClass().removeAll();
                        label_Room_Status.getStyleClass().add("label-roomAvailable-room-status");
                    } else {
                        label_Date_Remaining.setText(listRoom.getDayBooking()+ " days");
                        label_Room_Status.getStyleClass().removeAll();
                        label_Room_Status.getStyleClass().add("label-roomReserved-room-status");
                    }
                }
                if (listRoom.getRoomStatus().equalsIgnoreCase("Out") || listRoom.getRoomStatus().equalsIgnoreCase("Occupied")) {

                    if (listRoom.getRoomStatus().equalsIgnoreCase("Out")) {
                        label_Date_Remaining.setText("0 day");
                        btn_CheckIn.setDisable(true);
                        hBox_Buttons.getChildren().remove(btn_Services);                        
                        label_Room_Status.getStyleClass().removeAll();
                        label_Room_Status.getStyleClass().add("label-roomCheckOut-room-status");
                    } else {
                        label_Date_Remaining.setText(listRoom.getDayLeave()+ " days");
                        hBox_Buttons.getChildren().remove(btn_CheckIn);
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
                if (listRoom.getRoomType().equalsIgnoreCase("Deluxe")) {
                    icon_Room_Type.setGlyphName("DIAMOND");
                }
                if (listRoom.isRoomInProgress()) {
                    label_Date_Remaining.setText("Checking");
                    label_Date_Remaining.getStyleClass().removeAll();
                    label_Date_Remaining.getStyleClass().add("label-NotClean-Status");
                    icon_Date_Remaining.setGlyphName("CLOCK_ALT");
                    icon_Date_Remaining.getStyleClass().removeAll();
                    icon_Date_Remaining.getStyleClass().add("glyph-icon-clean-repair-inprogress-status");
                } else {
                    //label_Date_Remaining.setText(listRoom.getDayRemaining() + " days");
                    label_Date_Remaining.getStyleClass().removeAll();
                    label_Date_Remaining.getStyleClass().add("label-Primary-Status");
                    icon_Date_Remaining.setGlyphName("CALENDAR_CHECK_ALT");
                    icon_Date_Remaining.getStyleClass().removeAll();
                    icon_Date_Remaining.getStyleClass().add("glyph-icon-primary");
                }

                if (!listRoom.isRoomRepaired()) {
                    label_Repaired_Status.setText("N.Repair");
                    label_Repaired_Status.getStyleClass().removeAll();
                    label_Repaired_Status.getStyleClass().add("label-NotClean-Status");
                    icon_Repaired_Status.setGlyphName("WARNING");
                    icon_Repaired_Status.getStyleClass().removeAll();
                    icon_Repaired_Status.getStyleClass().add("glyph-icon-clean-repair-inprogress-status");
                } else {
                    label_Repaired_Status.setText("Repaired");
                    label_Repaired_Status.getStyleClass().removeAll();
                    label_Repaired_Status.getStyleClass().add("label-Primary-Status");
                    icon_Repaired_Status.setGlyphName("WRENCH");
                    icon_Repaired_Status.getStyleClass().removeAll();
                    icon_Repaired_Status.getStyleClass().add("glyph-icon-primary");
                }
                if (!listRoom.isRoomClean()) {
                    label_Clean_Status.setText("N.Clean");
                    label_Clean_Status.getStyleClass().removeAll();
                    label_Clean_Status.getStyleClass().add("label-NotClean-Status");
                    icon_Clean_Status.setGlyphName("CLOSE");
                    icon_Clean_Status.getStyleClass().removeAll();
                    icon_Clean_Status.getStyleClass().add("glyph-icon-clean-repair-inprogress-status");
                } else {
                    label_Clean_Status.setText("Clean");
                    label_Clean_Status.getStyleClass().removeAll();
                    label_Clean_Status.getStyleClass().add("label-Primary-Status");
                    icon_Clean_Status.setGlyphName("CHECK");
                    icon_Clean_Status.getStyleClass().removeAll();
                    icon_Clean_Status.getStyleClass().add("glyph-icon-primary");
                }

                parentPane.getChildren().add(pane);

            }
        } catch (IOException ex) {
            Logger.getLogger(FXMLMainOverViewPaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void refreshForm() {
        //Initialize label room property
        init_Label_Room_Property();

        check_Services_Button_Clicked = false;

        if (!roomDAOImpl.getAllRoom().isEmpty()) {
            initAddRooms();
        } else {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error");
                alert.setContentText("Don't have any rooms in Database or Can't connect to Database");
                alert.show();
            });
        }
    }

    /**
     * Adding text to list and do filtering
     *
     * @param list
     * @param checkBox
     * @param checkBox_Text
     */
    private void checkBox_Property_Listener(ObservableList<String> list, JFXCheckBox checkBox, String checkBox_Text) {
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("newValue = " + newValue);
            if (newValue == true) {
                System.out.println(checkBox_Text + " = " + checkBox.isSelected());
                if (!list.contains(checkBox_Text)) {
                    list.add(checkBox_Text);
                }
            } else {
                System.out.println(checkBox_Text + " = " + checkBox.isSelected());
                if (list.contains(checkBox_Text)) {
                    list.remove(checkBox_Text);
                }
            }
            filteredRoom = new FilteredList<>(listRooms, list1 -> true);
            // Call method check_Filter
            check_Filter(filteredRoom);

            add_Rooms_With_Condition(filteredRoom, flowPane_Rooms, list_Status, list_Type, list_HouseKeeping);
        });
    }

    private FilteredList check_Filter(FilteredList filter) {
        Predicate<Room> roomStatus = i -> {
            for (String status : list_Status) {
                if (i.getRoomStatus().contains(status)) {
                    return i.getRoomStatus().contains(status);
                }
            }
            return false;
        };

        Predicate<Room> roomType = i -> {
            for (String type : list_Type) {
                if (i.getRoomType().contains(type)) {
                    return i.getRoomType().contains(type);
                }
            }
            return false;
        };

        Predicate<Room> roomHouseKeeping = i -> {
            for (String houseKeeping : list_HouseKeeping) {
                if (check_Room_Housekeeping_Status(i, houseKeeping)) {
                    return check_Room_Housekeeping_Status(i, houseKeeping);
                }
            }
            return false;
        };
        Predicate<Room> minFloor = i -> {
            return i.getRoomOnFloor() >= Integer.parseInt(comboBox_FromFloor.getValue());
        };
        Predicate<Room> maxFloor = i -> {
            return i.getRoomOnFloor() <= Integer.parseInt(comboBox_ToFloor.getValue());
        };
        if (!list_Status.isEmpty() && !list_Type.isEmpty() && !list_HouseKeeping.isEmpty()) {
            System.out.println("3 list are not empty");
            filter.setPredicate(roomStatus.and(roomType).and(roomHouseKeeping).and(minFloor).and(maxFloor).and(minFloor).and(maxFloor));
        } else if (!list_Status.isEmpty() && !list_Type.isEmpty() && list_HouseKeeping.isEmpty()) {
            System.out.println("liststatus, listtype are not empty");
            filter.setPredicate(roomStatus.and(roomType).and(minFloor).and(maxFloor));
        } else if (!list_Status.isEmpty() && list_Type.isEmpty() && !list_HouseKeeping.isEmpty()) {
            System.out.println("liststatus, listhouse are not empty");
            filter.setPredicate(roomStatus.and(roomHouseKeeping).and(minFloor).and(maxFloor));
        } else if (!list_Status.isEmpty() && list_Type.isEmpty() && list_HouseKeeping.isEmpty()) {
            System.out.println("liststatus is not empty");
            filter.setPredicate(roomStatus.and(minFloor).and(maxFloor));
        } else if (list_Status.isEmpty() && !list_Type.isEmpty() && !list_HouseKeeping.isEmpty()) {
            System.out.println("listtype, listhouse are not empty");
            filter.setPredicate(roomType.and(roomHouseKeeping).and(minFloor).and(maxFloor));
        } else if (list_Status.isEmpty() && !list_Type.isEmpty() && list_HouseKeeping.isEmpty()) {
            System.out.println("listtype is not empty");
            filter.setPredicate(roomType.and(minFloor).and(maxFloor));
        } else if (list_Status.isEmpty() && list_Type.isEmpty() && !list_HouseKeeping.isEmpty()) {
            System.out.println("listhouse is not empty");
            filter.setPredicate(roomHouseKeeping.and(minFloor).and(maxFloor));
        } else {
            System.out.println("3 list are empty");
            filter.setPredicate(minFloor.and(maxFloor));
        }
        return filter;
    }

    private Boolean check_Room_Housekeeping_Status(Room room, String compare_Str) {
        // Convert HouseKeeping to String list
        ArrayList<String> listHouseKeeping = new ArrayList<>();
        if (room.isRoomClean()) {
            listHouseKeeping.add("Clean");
        }
        if (!room.isRoomClean()) {
            listHouseKeeping.add("Not clean");
        }
        if (room.isRoomRepaired()) {
            listHouseKeeping.add("Repaired");
        }
        if (!room.isRoomRepaired()) {
            listHouseKeeping.add("Not repaired");
        }
        if (room.isRoomInProgress()) {
            listHouseKeeping.add("In progress");
        }
        if (!room.isRoomInProgress()) {
            listHouseKeeping.add("Remaining days");
        }
        for (String string : listHouseKeeping) {
            if (string.equalsIgnoreCase(compare_Str)) {
                return true;
            }
        }
        return false;
    }

    public class CompareRoom implements Comparator<Room> {

        /**
         *
         * @param r1 Room
         * @param r2 Room
         * @return: 0: = ; 1 = max ; -1 = min;
         */
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
