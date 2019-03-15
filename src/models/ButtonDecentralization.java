/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.jfoenix.controls.JFXButton;

/**
 *
 * @author Admin
 */
public class ButtonDecentralization {
    private JFXButton btn_Edit;
    private JFXButton btn_Update;
  

    public ButtonDecentralization() {
        this.btn_Edit = new JFXButton("Edit");
        this.btn_Update = new JFXButton("Update");
    }

    public ButtonDecentralization(JFXButton btn_Update, JFXButton btn_Edit) {
        this.btn_Edit = btn_Edit;
        this.btn_Update = btn_Update;
    }

    public JFXButton getbtn_Edit() {
        return btn_Edit;
    }

    public void setbtn_Edit(JFXButton btn_Edit) {
        this.btn_Edit = btn_Edit;
    }

    public JFXButton getbtn_Update() {
        return btn_Update;
    }

    public void setbtn_Update(JFXButton btn_Update) {
        this.btn_Update = btn_Update;
    }
}
