/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;

/**
 *
 * @author Doan Thanh Nhan
 */
public class RoomEX extends Room {

    private CheckBox checkBox_Room_Clean;
    private CheckBox checkBox_Room_Repaired;
    private CheckBox checkBox_Room_In_Progress;
    private HBox roomAction;

    public RoomEX() {
    }

    public CheckBox getCheckBox_Room_Clean() {
        return checkBox_Room_Clean;
    }

    public void setCheckBox_Room_Clean(CheckBox checkBox_Room_Clean) {
        this.checkBox_Room_Clean = checkBox_Room_Clean;
    }

    public CheckBox getCheckBox_Room_Repaired() {
        return checkBox_Room_Repaired;
    }

    public void setCheckBox_Room_Repaired(CheckBox checkBox_Room_Repaired) {
        this.checkBox_Room_Repaired = checkBox_Room_Repaired;
    }

    public CheckBox getCheckBox_Room_In_Progress() {
        return checkBox_Room_In_Progress;
    }

    public void setCheckBox_Room_In_Progress(CheckBox checkBox_Room_In_Progress) {
        this.checkBox_Room_In_Progress = checkBox_Room_In_Progress;
    }

    public HBox getRoomAction() {
        return roomAction;
    }

    public void setRoomAction(HBox roomAction) {
        this.roomAction = roomAction;
    }

}
