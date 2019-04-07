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
 * @author Admin
 */
public class MacAddress extends checkMacAddress {

    private HBox HboxReActive;
    private CheckBox MacAddress_ReActive;

    public CheckBox getMacAddress_ReActive() {
        return MacAddress_ReActive;
    }

    public void setMacAddress_ReActive(CheckBox MacAddress_ReActive) {
        this.MacAddress_ReActive = MacAddress_ReActive;
    }

    public HBox getHboxReActive() {
        return HboxReActive;
    }

    public void setHboxReActive(HBox HboxReActive) {
        this.HboxReActive = HboxReActive;
    }
}
