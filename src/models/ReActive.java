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

    private HBox ReActive;
    private CheckBox Employee_ReActive;

    public CheckBox getEmployee_ReActive() {
        return Employee_ReActive;
    }

    public void setEmployee_ReActive(CheckBox Employee_ReActive) {
        this.Employee_ReActive = Employee_ReActive;
    }

    public HBox getReActive() {
        return ReActive;
    }

    public void setReActive(HBox ReActive) {
        this.ReActive = ReActive;
    }
}
