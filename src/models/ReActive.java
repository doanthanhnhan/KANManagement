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
public class ReActive extends ReActiveModel {

    private HBox HboxReActive;
    private CheckBox Employee_ReActive;

    public CheckBox getEmployee_ReActive() {
        return Employee_ReActive;
    }

    public void setEmployee_ReActive(CheckBox Employee_ReActive) {
        this.Employee_ReActive = Employee_ReActive;
    }

    public HBox getHboxReActive() {
        return HboxReActive;
    }

    public void setHboxReActive(HBox HboxReActive) {
        this.HboxReActive = HboxReActive;
    }
}
