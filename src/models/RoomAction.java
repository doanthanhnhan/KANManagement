/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.jfoenix.controls.JFXButton;

/**
 *
 * @author Doan Thanh Nhan
 */
public class RoomAction {

    private JFXButton btn_Check_In;
    private JFXButton btn_Check_Out;
    private JFXButton btn_Services;

    public RoomAction() {
        this.btn_Check_In = new JFXButton("Check In");
        this.btn_Services = new JFXButton("Services");
        this.btn_Check_Out = new JFXButton("Check Out");
    }

    public RoomAction(JFXButton btn_Check_In, JFXButton btn_Check_Out, JFXButton btn_Services) {
        this.btn_Check_In = btn_Check_In;
        this.btn_Check_Out = btn_Check_Out;
        this.btn_Services = btn_Services;
    }

    public JFXButton getBtn_Check_In() {
        return btn_Check_In;
    }

    public void setBtn_Check_In(JFXButton btn_Check_In) {
        this.btn_Check_In = btn_Check_In;
    }

    public JFXButton getBtn_Check_Out() {
        return btn_Check_Out;
    }

    public void setBtn_Check_Out(JFXButton btn_Check_Out) {
        this.btn_Check_Out = btn_Check_Out;
    }

    public JFXButton getBtn_Services() {
        return btn_Services;
    }

    public void setBtn_Services(JFXButton btn_Services) {
        this.btn_Services = btn_Services;
    }
}
