/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import models.DAOReActive;

/**
 *
 * @author Admin
 */
public class ConnectControllers {

    public static FXMLLoginController fXMLLoginController;
    public static FXMLMainFormController fXMLMainFormController;
    public static FXMLMainOverViewPaneController fXMLMainOverViewPaneController;
    public static FXMLForgetPassController fXMLForgetPassController;
    public static FXMLAddNewEmloyeeController fXMLAddNewEmloyeeController;
    public static FXMLAddNewRoomController fXMLAddNewRoomController;
    public static FXMLAddNewServiceTypeController fXMLAddNewServiceTypeController;
    public static FXMLAddNewServiceOrderController fXMLAddNewServiceOrderController;
    public static FXMLListCheckOutController fXMLListCheckOutController;
    public static FXMLListServiceTypeController fXMLListServiceTypeController;
    public static FXMLListServiceOrderController fXMLListServiceOrderController;
    public static FXMLListServiceOrderDetailController fXMLListServiceOrderDetailController;
    public static FXMLListEmployeeController fXMLListEmployeeController;
    public static FXMLListRoomTypeController fXMLListRoomTypeController;
    public static FXMLListRoomsController fXMLListRoomsController;
    public static FXMLInfoEmployeeController fXMLInfoEmployeeController;
    public static FXMLSplashScreenController fXMLSplashScreenController;
    public static FXMLDecentralizationController fXMLDecentralizationController;
    public static FXMLListDepartmentController fXMLListDepartmentController;
    public static FXMLListCustomerController fXMLListCustomerController;
    public static FXMLEditCustomerController editCustomerController;
    public static FXMLReActiveController fXMLReActiveController;
    public static DAOReActive reActive;
    public static FXMLReActiveMacAddressController fXMLReActiveMacAddressControllerController;
    public static FXMLListBookingController fXMLListBookingController;
    public static FXMLListBookingVirtualController fXMLListBookingVirtualController;
    public static FXMLInfoBookingVirtualController fXMLInfoBookingVirtualController;
    public static FXMLEditBookingController fXMLEditBookingController;
    public static FXMLRoomTypeController fXMLRoomTypeController;
    public static FXMLListCheckInController fXMLListCheckInController;
    public static FXMLEditCheckInController fXMLEditCheckInController;

    public static FXMLEditCheckInController getfXMLEditCheckInController() {
        return fXMLEditCheckInController;
    }

    public static void setfXMLEditCheckInController(FXMLEditCheckInController fXMLEditCheckInController) {
        ConnectControllers.fXMLEditCheckInController = fXMLEditCheckInController;
    }

    public static FXMLListCheckInController getfXMLListCheckInController() {
        return fXMLListCheckInController;
    }

    public static void setfXMLListCheckInController(FXMLListCheckInController fXMLListCheckInController) {
        ConnectControllers.fXMLListCheckInController = fXMLListCheckInController;
    }

    public static FXMLEditBookingController getfXMLEditBookingController() {
        return fXMLEditBookingController;
    }

    public static void setfXMLEditBookingController(FXMLEditBookingController fXMLEditBookingController) {
        ConnectControllers.fXMLEditBookingController = fXMLEditBookingController;
    }

    public static FXMLInfoBookingVirtualController getfXMLInfoBookingVirtualController() {
        return fXMLInfoBookingVirtualController;
    }

    public static void setfXMLInfoBookingVirtualController(FXMLInfoBookingVirtualController fXMLInfoBookingVirtualController) {
        ConnectControllers.fXMLInfoBookingVirtualController = fXMLInfoBookingVirtualController;
    }

    public static FXMLListBookingVirtualController getfXMLListBookingVirtualController() {
        return fXMLListBookingVirtualController;
    }

    public static void setfXMLListBookingVirtualController(FXMLListBookingVirtualController fXMLListBookingVirtualController) {
        ConnectControllers.fXMLListBookingVirtualController = fXMLListBookingVirtualController;
    }

    public static FXMLListBookingController getfXMLListBookingController() {
        return fXMLListBookingController;
    }

    public static void setfXMLListBookingController(FXMLListBookingController fXMLListBookingController) {
        ConnectControllers.fXMLListBookingController = fXMLListBookingController;
    }

    public static FXMLReActiveMacAddressController getfXMLReActiveMacAddressController() {
        return fXMLReActiveMacAddressControllerController;
    }

    public static void setfXMLReActiveMacAddressController(FXMLReActiveMacAddressController fXMLReActiveMacAddressControllerController) {
        ConnectControllers.fXMLReActiveMacAddressControllerController = fXMLReActiveMacAddressControllerController;
    }

    public static DAOReActive getreActive() {
        return reActive;
    }

    public static void setreActive(DAOReActive reActive) {
        ConnectControllers.reActive = reActive;
    }

    public static FXMLReActiveController getfXMLReActiveController() {
        return fXMLReActiveController;
    }

    public static void setfXMLReActiveController(FXMLReActiveController fXMLReActiveController) {
        ConnectControllers.fXMLReActiveController = fXMLReActiveController;
    }

    public static FXMLListDepartmentController getfXMLListDepartmentController() {
        return fXMLListDepartmentController;
    }

    public static void setfXMLListDepartmentController(FXMLListDepartmentController fXMLListDepartmentController) {
        ConnectControllers.fXMLListDepartmentController = fXMLListDepartmentController;
    }

    public static FXMLEditCustomerController geteditCustomerController() {
        return editCustomerController;
    }

    public static void seteditCustomerController(FXMLEditCustomerController editCustomerController) {
        ConnectControllers.editCustomerController = editCustomerController;
    }

    public static FXMLListCustomerController getfXMLListCustomerController() {
        return fXMLListCustomerController;
    }

    public static void setfXMLListCustomerController(FXMLListCustomerController fXMLListCustomerController) {
        ConnectControllers.fXMLListCustomerController = fXMLListCustomerController;
    }

    public static FXMLDecentralizationController getfXMLDecentralizationController() {
        return fXMLDecentralizationController;
    }

    public static void setfXMLDecentralizationController(FXMLDecentralizationController fXMLDecentralizationController) {
        ConnectControllers.fXMLDecentralizationController = fXMLDecentralizationController;
    }

    public static FXMLInfoEmployeeController getfXMLInfoEmployeeController() {
        return fXMLInfoEmployeeController;
    }

    public static void setfXMLInfoEmployeeController(FXMLInfoEmployeeController fXMLInfoEmployeeController) {
        ConnectControllers.fXMLInfoEmployeeController = fXMLInfoEmployeeController;
    }

    public static FXMLLoginController getfXMLLoginController() {
        return fXMLLoginController;
    }

    public static void setfXMLLoginController(FXMLLoginController fXMLLoginController) {
        ConnectControllers.fXMLLoginController = fXMLLoginController;
    }

    public static FXMLMainFormController getfXMLMainFormController() {
        return fXMLMainFormController;
    }

    public static void setfXMLMainFormController(FXMLMainFormController fXMLMainFormController) {
        ConnectControllers.fXMLMainFormController = fXMLMainFormController;
    }

    public static FXMLForgetPassController fXMLForgetPassController() {

        return fXMLForgetPassController;
    }

    public static void setfXMLForgetPassController(FXMLForgetPassController fXMLForgetPassController) {
        ConnectControllers.fXMLForgetPassController = fXMLForgetPassController;
    }

    public static FXMLAddNewEmloyeeController getfXMLAddNewEmloyeeController() {

        return fXMLAddNewEmloyeeController;
    }

    public static void setfXMLAddNewEmloyeeController(FXMLAddNewEmloyeeController fXMLAddNewEmloyeeController) {
        ConnectControllers.fXMLAddNewEmloyeeController = fXMLAddNewEmloyeeController;
    }

    public static FXMLListEmployeeController getfXMLListEmployeeController() {
        return fXMLListEmployeeController;
    }

    public static void setfXMLListEmployeeController(FXMLListEmployeeController fXMLListEmployeeController) {
        ConnectControllers.fXMLListEmployeeController = fXMLListEmployeeController;
    }

    public static FXMLAddNewServiceTypeController getfXMLAddNewServiceTypeController() {
        return fXMLAddNewServiceTypeController;
    }

    public static void setfXMLAddNewServiceTypeController(FXMLAddNewServiceTypeController fXMLAddNewServiceTypeController) {
        ConnectControllers.fXMLAddNewServiceTypeController = fXMLAddNewServiceTypeController;
    }

    public static FXMLListServiceTypeController getfXMLListServiceTypeController() {
        return fXMLListServiceTypeController;
    }

    public static void setfXMLListServiceTypeController(FXMLListServiceTypeController fXMLListServiceTypeController) {
        ConnectControllers.fXMLListServiceTypeController = fXMLListServiceTypeController;
    }

    public static FXMLListRoomsController getfXMLListRoomsController() {
        return fXMLListRoomsController;
    }

    public static void setfXMLListRoomsController(FXMLListRoomsController fXMLListRoomsController) {
        ConnectControllers.fXMLListRoomsController = fXMLListRoomsController;
    }

    public static FXMLSplashScreenController getfXMLSplashScreenController() {
        return fXMLSplashScreenController;
    }

    public static void setfXMLSplashScreenController(FXMLSplashScreenController fXMLSplashScreenController) {
        ConnectControllers.fXMLSplashScreenController = fXMLSplashScreenController;
    }

    public static FXMLAddNewServiceOrderController getfXMLAddNewServiceOrderController() {
        return fXMLAddNewServiceOrderController;
    }

    public static void setfXMLAddNewServiceOrderController(FXMLAddNewServiceOrderController fXMLAddNewServiceOrderController) {
        ConnectControllers.fXMLAddNewServiceOrderController = fXMLAddNewServiceOrderController;
    }

    public static FXMLListServiceOrderController getfXMLListServiceOrderController() {
        return fXMLListServiceOrderController;
    }

    public static void setfXMLListServiceOrderController(FXMLListServiceOrderController fXMLListServiceOrderController) {
        ConnectControllers.fXMLListServiceOrderController = fXMLListServiceOrderController;
    }

    public static FXMLListServiceOrderDetailController getfXMLListServiceOrderDetailController() {
        return fXMLListServiceOrderDetailController;
    }

    public static void setfXMLListServiceOrderDetailController(FXMLListServiceOrderDetailController fXMLListServiceOrderDetailController) {
        ConnectControllers.fXMLListServiceOrderDetailController = fXMLListServiceOrderDetailController;
    }

    public static FXMLMainOverViewPaneController getfXMLMainOverViewPaneController() {
        return fXMLMainOverViewPaneController;
    }

    public static void setfXMLMainOverViewPaneController(FXMLMainOverViewPaneController fXMLMainOverViewPaneController) {
        ConnectControllers.fXMLMainOverViewPaneController = fXMLMainOverViewPaneController;
    }

    public static FXMLListCheckOutController getfXMLListCheckOutController() {
        return fXMLListCheckOutController;
    }

    public static void setfXMLListCheckOutController(FXMLListCheckOutController fXMLListCheckOutController) {
        ConnectControllers.fXMLListCheckOutController = fXMLListCheckOutController;
    }

    public static FXMLEditCustomerController getEditCustomerController() {
        return editCustomerController;
    }

    public static void setEditCustomerController(FXMLEditCustomerController editCustomerController) {
        ConnectControllers.editCustomerController = editCustomerController;
    }

    public static FXMLAddNewRoomController getfXMLAddNewRoomController() {
        return fXMLAddNewRoomController;
    }

    public static void setfXMLAddNewRoomController(FXMLAddNewRoomController fXMLAddNewRoomController) {
        ConnectControllers.fXMLAddNewRoomController = fXMLAddNewRoomController;
    }

    public static FXMLListRoomTypeController getfXMLListRoomTypeController() {
        return fXMLListRoomTypeController;
    }

    public static void setfXMLListRoomTypeController(FXMLListRoomTypeController fXMLListRoomTypeController) {
        ConnectControllers.fXMLListRoomTypeController = fXMLListRoomTypeController;
    }

    public static DAOReActive getReActive() {
        return reActive;
    }

    public static void setReActive(DAOReActive reActive) {
        ConnectControllers.reActive = reActive;
    }

    public static FXMLReActiveMacAddressController getfXMLReActiveMacAddressControllerController() {
        return fXMLReActiveMacAddressControllerController;
    }

    public static void setfXMLReActiveMacAddressControllerController(FXMLReActiveMacAddressController fXMLReActiveMacAddressControllerController) {
        ConnectControllers.fXMLReActiveMacAddressControllerController = fXMLReActiveMacAddressControllerController;
    }

    public static FXMLRoomTypeController getfXMLRoomTypeController() {
        return fXMLRoomTypeController;
    }

    public static void setfXMLRoomTypeController(FXMLRoomTypeController fXMLRoomTypeController) {
        ConnectControllers.fXMLRoomTypeController = fXMLRoomTypeController;
    }

}
