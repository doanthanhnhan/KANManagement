/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

/**
 *
 * @author Admin
 */
public class ConnectControllers {

    public static FXMLLoginController fXMLLoginController;
    public static FXMLForgetPassController fXMLForgetPassController;
    public static FXMLAddNewEmloyeeController fXMLAddNewEmloyeeController;

    public static FXMLLoginController getfXMLLoginController() {
        return fXMLLoginController;
    }

    public static void setfXMLLoginController(FXMLLoginController fXMLLoginController) {
        ConnectControllers.fXMLLoginController = fXMLLoginController;
    }

    public static FXMLForgetPassController fXMLForgetPassController() {

        return fXMLForgetPassController;
    }

    public static void setfXMLForgetPassController(FXMLForgetPassController fXMLForgetPassController) {
        ConnectControllers.fXMLForgetPassController = fXMLForgetPassController;
    }

    public static FXMLAddNewEmloyeeController fXMLAddNewEmloyeeController() {

        return fXMLAddNewEmloyeeController;
    }

    public static void setfXMLAddNewEmloyeeController(FXMLAddNewEmloyeeController fXMLAddNewEmloyeeController) {
        ConnectControllers.fXMLAddNewEmloyeeController = fXMLAddNewEmloyeeController;
    }
}
