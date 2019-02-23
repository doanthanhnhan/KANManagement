/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initAddRooms();
    }

    public void initAddRooms() {
        listRooms = roomDAOImpl.getAllRoom();

        HBox hbox = new HBox();
        hbox.setSpacing(20);
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
                    label_Clean_Status.setText("N.Repair");
                    label_Clean_Status.getStyleClass().removeAll();
                    label_Clean_Status.getStyleClass().add("label-NotClean-Status");
                    icon_Clean_Status.setGlyphName("CLOSE");
                    icon_Clean_Status.getStyleClass().removeAll();
                    icon_Clean_Status.getStyleClass().add("glyph-icon-clean-repair-inprogress-status");
                }

                hbox.getChildren().add(pane);
            }
        } catch (IOException ex) {
            Logger.getLogger(FXMLMainOverViewPaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        aPane_Rooms.getChildren().add(hbox);
    }
}
