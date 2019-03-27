/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXTabPane;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.RoleDAOImpl;
import models.boolDecentralizationModel;
import utils.FormatName;
import utils.GetInetAddress;

import utils.MyTimer;
import utils.showFXMLLogin;

/**
 * FXML Controller class
 *
 * @author Doan Thanh Nhan
 */
public class FXMLMainFormController implements Initializable {

    public static Boolean checkRegis = false;
    private Map<String, Tab> openTabs = new HashMap<>();
    FXMLLoginController fxmlLoginController;
    RoleDAOImpl roleDAOImpl;
    public boolDecentralizationModel userRole;
    public String macAdress;

    @FXML
    private MenuBar mainMenuBar;
    @FXML
    private JFXTabPane mainTabPane;
    @FXML
    private Button btn_Toolbar_Home;
    @FXML
    private Button btn_Toolbar_Booking;
    @FXML
    private Button btn_Toolbar_Checkin;
    @FXML
    private Button btn_Toolbar_Checkout;
    @FXML
    private Button btn_Toolbar_Services;
    @FXML
    private Button btn_Toolbar_Employees;
    @FXML
    private Button btn_Toolbar_Charts;
    @FXML
    private Button btn_Toolbar_Accounting;
    @FXML
    private Button btn_Toolbar_Settings;
    @FXML
    private Button btn_Toolbar_User_Logout;
    @FXML
    private ProgressBar progressBar_MainTask;
    @FXML
    private HBox hbox_Bottom;

    @FXML
    private Menu menu_File;
    @FXML
    private MenuItem menuItem_Settings;
    @FXML
    private MenuItem menuItem_Close;
    @FXML
    private Menu menu_View;
    @FXML
    private MenuItem menuItem_List_Booking;
    @FXML
    private MenuItem menuItem_List_Check_In;
    @FXML
    private MenuItem menuItem_List_Check_Out;
    @FXML
    private MenuItem menuItem_List_Customers;
    @FXML
    private MenuItem menuItem_List_Employees;
    @FXML
    private MenuItem menuItem_List_Rooms;
    @FXML
    private MenuItem menuItem_List_Service_Type;
    @FXML
    private MenuItem menuItem_List_Service_Orders;
    @FXML
    private MenuItem menuItem_List_Users;
    @FXML
    private MenuItem menuItem_List_User_Role;
    @FXML
    private Menu menu_Add;
    @FXML
    private MenuItem menuItem_Add_Booking;
    @FXML
    private MenuItem menuItem_Add_Check_In;
    @FXML
    private MenuItem menuItem_Add_Check_out;
    @FXML
    private MenuItem menuItem_Add_Customer;
    @FXML
    private MenuItem menuItem_Add_Employee;
    @FXML
    private MenuItem menuItem_Add_Room;
    @FXML
    private MenuItem menuItem_Add_Service_Type;
    @FXML
    private MenuItem menuItem_Add_Service_Order;
    @FXML
    private MenuItem menuItem_Add_User;
    @FXML
    private MenuItem menuItem_Add_User_Role;
    @FXML
    private Menu menu_Edit;
    @FXML
    private MenuItem menuItem_Edit_Booking;
    @FXML
    private MenuItem menuItem_Edit_Check_In;
    @FXML
    private MenuItem menuItem_Edit_Check_Out;
    @FXML
    private MenuItem menuItem_Edit_Customer;
    @FXML
    private MenuItem menuItem_Edit_Employee;
    @FXML
    private MenuItem menuItem_Edit_Room;
    @FXML
    private MenuItem menuItem_Edit_Service_Type;
    @FXML
    private MenuItem menuItem_Edit_Service_Order;
    @FXML
    private MenuItem menuItem_Edit_User;
    @FXML
    private MenuItem menuItem_Edit_User_Role;
    @FXML
    private Menu About;
    @FXML
    private TextField txt_Search;
    @FXML
    public AnchorPane AnchorPaneMainForm;
    @FXML
    private MenuItem menuItem_List_Department;
    @FXML
    private MenuItem menuItem_Add_Department;
    @FXML
    private MenuItem menuItem_Edit_Department;
    @FXML
    private MenuItem menuItem_List_Role;
    @FXML
    private MenuItem menuItem_Add_Role;
    @FXML
    private MenuItem menuItem_Edit_Role;
    @FXML
    private MenuItem menuItem_List_Service_Orders_Details;
    @FXML
    private MenuItem menuItem_Add_Service_Order_Details;
    @FXML
    private MenuItem menuItem_Edit_Service_Order_Details;
    @FXML
    private MenuItem menuItem_List_Users_Log;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Set FXMLMainFormController reference
        ConnectControllers.setfXMLMainFormController(this);

        //Set MACAddress
        setMacAdress(GetInetAddress.getMacAddress());

        //Initialize roleDAOImpl
        roleDAOImpl = new RoleDAOImpl();

        //Get FXMLLoginController for using in this controller
        fxmlLoginController = ConnectControllers.getfXMLLoginController();

        //Set close button for all TAB
        mainTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);

        //Initialize OverView Pane
        task_Insert_Tab_With_Indicator("/fxml/FXMLMainOverViewPane.fxml", "mainOverView_Tab", "Over view");

        //SETTING ROLE TO FORM
        //Get user role        
        userRole = new boolDecentralizationModel();
        userRole = roleDAOImpl.getEmployeeRole(fxmlLoginController.getUser_Login_Sucessful());
        System.out.println("User has logged in: " + userRole.getEmployee_ID());

        //Setting role to form
        //01.BOOKING CRUD
        if (!userRole.ischeckBooking_Add()) {
            menu_Add.getItems().remove(menuItem_Add_Booking);
        }
        if (!userRole.ischeckBooking_Delete()) {
//            menu_Add.getItems().remove(menuItem_Add_Booking);
        }
        if (!userRole.ischeckBooking_Edit()) {
            menu_Edit.getItems().remove(menuItem_Edit_Booking);
        }
        if (!userRole.ischeckBooking_View()) {
            menu_View.getItems().remove(menuItem_List_Booking);
        }
        //02.CHECK IN CRUD
        if (!userRole.ischeckCheckIn_Add()) {
            menu_Add.getItems().remove(menuItem_Add_Check_In);
        }
        if (!userRole.ischeckCheckIn_Delete()) {
            //menu_Add.getItems().remove(menuItem_Add_Booking);
        }
        if (!userRole.ischeckCheckIn_Edit()) {
            menu_Edit.getItems().remove(menuItem_Edit_Check_In);
        }
        if (!userRole.ischeckCheckIn_View()) {
            menu_View.getItems().remove(menuItem_List_Check_In);
        }
        //03.CHECK OUT CRUD
        if (!userRole.ischeckCheckOut_Add()) {
            menu_Add.getItems().remove(menuItem_Add_Check_out);
        }
        if (!userRole.ischeckCheckOut_Delete()) {
            //menu_Add.getItems().remove(menuItem_Add_Booking);
        }
        if (!userRole.ischeckCheckOut_Edit()) {
            menu_Edit.getItems().remove(menuItem_Edit_Check_Out);
        }
        if (!userRole.ischeckCheckOut_View()) {
            menu_View.getItems().remove(menuItem_List_Check_Out);
        }
        //04.CUSTOMER CRUD
        if (!userRole.ischeckCustomer_Add()) {
            menu_Add.getItems().remove(menuItem_Add_Customer);
        }
        if (!userRole.ischeckCustomer_Delete()) {
            //menu_Add.getItems().remove(menuItem_Add_Booking);
        }
        if (!userRole.ischeckCustomer_Edit()) {
            menu_Edit.getItems().remove(menuItem_Edit_Customer);
        }
        if (!userRole.ischeckCustomer_View()) {
            menu_View.getItems().remove(menuItem_List_Customers);
        }
        //05.DEPARTMENT CRUD
        if (!userRole.ischeckDepartment_Add()) {
            menu_Add.getItems().remove(menuItem_Add_Department);
        }
        if (!userRole.ischeckDepartment_Delete()) {
            //menu_Add.getItems().remove(menuItem_Add_Booking);
        }
        if (!userRole.ischeckDepartment_Edit()) {
            menu_Edit.getItems().remove(menuItem_Edit_Department);
        }
        if (!userRole.ischeckDepartment_View()) {
            menu_View.getItems().remove(menuItem_List_Department);
        }
        //06.EMPLOYEE CRUD
        if (!userRole.ischeckEmployee_Add()) {
            menu_Add.getItems().remove(menuItem_Add_Employee);
        }
        if (!userRole.ischeckEmployee_Delete()) {
            //menu_Add.getItems().remove(menuItem_Add_Booking);
        }
//        if (!userRole.ischeckEmployee_Edit()) {
//            menu_Edit.getItems().remove(menuItem_Edit_Employee);
//        }
        if (!userRole.ischeckEmployee_View()) {
            menu_View.getItems().remove(menuItem_List_Employees);
        }
        //07.ROLE CRUD
        if (!userRole.ischeckRole_Add()) {
            menu_Add.getItems().remove(menuItem_Add_Role);
        }
        if (!userRole.ischeckRole_Delete()) {
            //menu_Add.getItems().remove(menuItem_Add_Booking);
        }
        if (!userRole.ischeckRole_Edit()) {
            menu_Edit.getItems().remove(menuItem_Edit_Role);
        }
        if (!userRole.ischeckRole_View()) {
            menu_View.getItems().remove(menuItem_List_Role);
        }
        //08.ROOM CRUD
        if (!userRole.ischeckRoom_Add()) {
            menu_Add.getItems().remove(menuItem_Add_Room);
        }
        if (!userRole.ischeckRoom_Delete()) {
            //menu_Add.getItems().remove(menuItem_Add_Booking);
        }
        if (!userRole.ischeckRoom_Edit()) {
            menu_Edit.getItems().remove(menuItem_Edit_Room);
        }
        if (!userRole.ischeckRoom_View()) {
            menu_View.getItems().remove(menuItem_List_Rooms);
        }
        //09.SERVICES ORDERS CRUD
        if (!userRole.ischeckSODer_Add()) {
            menu_Add.getItems().remove(menuItem_Add_Service_Order);
        }
        if (!userRole.ischeckSODer_Delete()) {
            //menu_Add.getItems().remove(menuItem_Add_Booking);
        }
        if (!userRole.ischeckSODer_Edit()) {
            menu_Edit.getItems().remove(menuItem_Edit_Service_Order);
        }
        if (!userRole.ischeckSODer_View()) {
            menu_View.getItems().remove(menuItem_List_Service_Orders);
        }
        //10.SERVICE ORDER DETAILS CRUD
        if (!userRole.ischeckSODetail_Add()) {
            menu_Add.getItems().remove(menuItem_Add_Service_Order_Details);
        }
        if (!userRole.ischeckSODetail_Delete()) {
            //menu_Add.getItems().remove(menuItem_Add_Booking);
        }
        if (!userRole.ischeckSODetail_Edit()) {
            menu_Edit.getItems().remove(menuItem_Edit_Service_Order_Details);
        }
        if (!userRole.ischeckSODetail_View()) {
            menu_View.getItems().remove(menuItem_List_Service_Orders_Details);
        }
        //11.SERVICE TYPE CRUD
        if (!userRole.ischeckSType_Add()) {
            menu_Add.getItems().remove(menuItem_Add_Service_Type);
        }
        if (!userRole.ischeckSType_Delete()) {
            //menu_Add.getItems().remove(menuItem_Add_Booking);
        }
        if (!userRole.ischeckSType_Edit()) {
            menu_Edit.getItems().remove(menuItem_Edit_Service_Type);
        }
        if (!userRole.ischeckSType_View()) {
            menu_View.getItems().remove(menuItem_List_Service_Type);
        }
        //12.USERLOG CRUD
        if (!userRole.ischeckUserLog_Add()) {
            //menu_Add.getItems().remove(menuItem_Add_Booking);
        }
        if (!userRole.ischeckUserLog_Delete()) {
            //menu_Add.getItems().remove(menuItem_Add_Booking);
        }
        if (!userRole.ischeckUserLog_Edit()) {
            //menu_Add.getItems().remove(menuItem_Edit_Booking);
        }
        if (!userRole.ischeckUserLog_View()) {
            menu_View.getItems().remove(menuItem_List_Users_Log);
        }
        //13.USERS CRUD
        if (!userRole.ischeckUser_Add()) {
            menu_Add.getItems().remove(menuItem_Add_User);
        }
        if (!userRole.ischeckUser_Delete()) {
            //menu_Add.getItems().remove(menuItem_Add_Booking);
        }
        if (!userRole.ischeckUser_Edit()) {
            menu_Edit.getItems().remove(menuItem_Edit_User);
        }
        if (!userRole.ischeckUser_View()) {
            menu_View.getItems().remove(menuItem_List_Users);
        }
        //ENDING SETTING ROLE TO FORM

        //Setting logout button
        String userFullName = userRole.getFirst_Name() + " " + userRole.getMid_Name() + " " + userRole.getLast_Name();
        btn_Toolbar_User_Logout.setText(FormatName.format(userFullName));
    }

    public boolDecentralizationModel getUserRole() {
        return userRole;
    }

    public void setUserRole(boolDecentralizationModel userRole) {
        this.userRole = userRole;
    }

    public String getMacAdress() {
        return macAdress;
    }

    public void setMacAdress(String macAdress) {
        this.macAdress = macAdress;
    }

    private void initMenuBar() {
        MenuItem regis = new MenuItem("Registration User");
        regis.setOnAction((event) -> {
            checkRegis = true;
            System.out.println("Registration menu item clicked!");
            try {
                Stage stage = new Stage();
                stage.resizableProperty().setValue(Boolean.FALSE);
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/FXMLInfoEmployee.fxml"));
                stage.getIcons().add(new Image("/images/iconmanagement.png"));
                Scene scene = new Scene(root);
                stage.setTitle("Add New Employee");
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            } catch (IOException ex) {
                Logger.getLogger(FXMLMainFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        Menu menuRegistration = new Menu("Registration");
        menuRegistration.getItems().add(regis);
        mainMenuBar.getMenus().add(menuRegistration);

    }

    private void initTabPane() {
        try {
            // Get content from fxml file
            AnchorPane overviewPane = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/FXMLMainOverViewPane.fxml"));
            // Add fxml content to a tab
            Tab overViewTab = new Tab("Overview");
            overViewTab.setContent(overviewPane);

            mainTabPane.getTabs().add(overViewTab);
        } catch (IOException ex) {
            Logger.getLogger(FXMLMainFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void homeAction(ActionEvent event) {
        btn_Toolbar_Home.setDisable(true);
        task_Insert_Tab_With_Indicator("/fxml/FXMLMainOverViewPane.fxml", "mainOverView_Tab", "Over view");
        btn_Toolbar_Home.setDisable(false);
    }

    @FXML
    private void userLogOutAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log out confirm");
        alert.setHeaderText("Warning");
        alert.setContentText("Are you sure to log out?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            try {
                //formLoader("/fxml/FXMLLogin.fxml", "/images/KAN Logo.png", "Login");
                showFXMLLogin showLogin = new showFXMLLogin();
                showLogin.showFormLogin();
            } catch (IOException ex) {
                Logger.getLogger(FXMLMainFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Stage stage = (Stage) btn_Toolbar_User_Logout.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void handle_Toolbar_CheckOut_Action(ActionEvent event) {
        System.out.println("CheckOut clicked!");
        task_Insert_Tab_With_Indicator("/fxml/FXMLCheckOut.fxml", "checkOut_Tab", "Check Out");
    }

    @FXML
    private void handle_Menu_Item_Close_Action(ActionEvent event) {
        System.exit(0);
    }

    // =============== START VIEW ACTIONS ===============
    @FXML
    private void handle_MenuItem_List_Service_Type_Action(ActionEvent event) {
        System.out.println("List Service Type menu item clicked!");
        task_Insert_Tab_With_Indicator("/fxml/FXMLListServiceType.fxml", "listServiceType_Tab", "Service type");
    }

    @FXML
    private void handle_MenuItem_List_Employee_Action(ActionEvent event) {
        System.out.println("List Service Type menu item clicked!");
        task_Insert_Tab_With_Indicator("/fxml/FXMLListEmployee.fxml", "listEmployees_Tab", "Employees");
    }

    @FXML
    private void handle_MenuItem_List_Rooms_Action(ActionEvent event) {
        System.out.println("List rooms menu item clicked!");
        task_Insert_Tab_With_Indicator("/fxml/FXMLListRooms.fxml", "listRooms_Tab", "Rooms");
    }

    @FXML
    private void handle_MenuItem_List_Role_Action(ActionEvent event) {
        System.out.println("List Role menu item clicked!");
        task_Insert_Tab_With_Indicator("/fxml/FXMLDecentralization.fxml", "Users role", "Edit Employee Informations");
    }
    // ############### END VIEW ACTIONS ###############

    // =============== START ADD ACTIONS ===============
    @FXML
    private void handle_MenuItem_Add_Employee_Action(ActionEvent event) {
        checkRegis = true;
        System.out.println("Add new Employee menu item clicked!");
        formLoader("/fxml/FXMLAddNewEmployee.fxml", "/images/KAN Logo.png", "Add New Employee");
    }

    @FXML
    private void handle_MenuItem_CheckIn_Action(ActionEvent event) {
        checkRegis = true;
        System.out.println("Add new User menu item clicked!");
        formLoader("/fxml/FXMLCheckInOrders.fxml", "/images/KAN Logo.png", "Add CheckInOrder");
    }

    @FXML
    private void handle_MenuItem_Add_User_Action(ActionEvent event) {
        checkRegis = true;
        System.out.println("Add new User menu item clicked!");
        formLoader("/fxml/FXMLAddNewEmployee.fxml", "/images/KAN Logo.png", "Add New User");
    }

    @FXML
    private void handle_MenuItem_Add_Customer_Action(ActionEvent event) {
        checkRegis = true;
        System.out.println("Add New Customer menu item clicked!");
        formLoader("/fxml/FXMLCustomerInfo.fxml", "/images/KAN Logo.png", "Add New Customer");
    }

    @FXML
    private void handle_MenuItem_Add_Booking_Action(ActionEvent event) {
        checkRegis = true;
        System.out.println("Add new Customer menu item clicked!");
        formLoader("/fxml/FXMLBookingInfo.fxml", "/images/KAN Logo.png", "Add Booking");
    }

    @FXML
    private void handle_MenuItem_Add_Service_Type_Action(ActionEvent event) {
        System.out.println("Add new Service Type menu item clicked!");
        formLoader("/fxml/FXMLAddNewServiceType.fxml", "/images/KAN Logo.png", "Edit User Informations");
    }
    // ############### END ADD ACTIONS ###############

    // =============== EDIT ACTIONS ===============
    @FXML
    private void handle_MenuItem_Edit_Employee_Action(ActionEvent event) {
        checkRegis = true;
        FXMLListEmployeeController.check_form_list = false;
        System.out.println("Edit Employee Informations menu item clicked!");
        formLoader("/fxml/FXMLInfoEmployee.fxml", "/images/KAN Logo.png", "Edit Employee Informations");
    }

    @FXML
    private void handle_MenuItem_Edit_User_Action(ActionEvent event) {
        checkRegis = true;
        FXMLListEmployeeController.check_form_list = false;
        System.out.println("Edit User Informations menu item clicked!");
        formLoader("/fxml/FXMLInfoEmployee.fxml", "/images/KAN Logo.png", "Edit User Informations");
    }
    // ############### END VIEW ACTIONS ###############

    // =============== PUBLIC METHODS ===============
    public TextField getTxt_Search() {
        return txt_Search;
    }

    public void setTxt_Search(TextField txt_Search) {
        this.txt_Search = txt_Search;
    }

    /**
     *
     * @param fxmlPath
     * @param iconPath
     * @param title
     */
    public void formLoader(String fxmlPath, String iconPath, String title) {
        Platform.runLater(() -> {
            Label label_Task_Status = new Label();
            //Set timer and start
            MyTimer myTimer = new MyTimer();
            myTimer.create_myTimer(label_Task_Status);

            //Add label to bottom
            hbox_Bottom.getChildren().add(0, label_Task_Status);
            //Setting progressBar
            progressBar_MainTask.setVisible(true);
            progressBar_MainTask.progressProperty().unbind();
            //Create new task
            Task loadOverview = new Task() {
                @Override
                protected Object call() throws Exception {
                    System.out.println("Loading stage...");
                    Platform.runLater(() -> {
                        //Call new stage
                        try {
                            Stage stage = new Stage();
                            stage.resizableProperty().setValue(Boolean.FALSE);
                            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
                            stage.getIcons().add(new Image(iconPath));
                            Scene scene = new Scene(root);
                            stage.setTitle(title);
                            stage.setScene(scene);
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.show();
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLMainFormController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    //Stop timer
                    myTimer.stop_Timer(label_Task_Status);
                    return null;
                }
            };
            //Binding progress bar with task
            progressBar_MainTask.progressProperty().bind(loadOverview.progressProperty());

            loadOverview.setOnSucceeded(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    System.out.println("Finished");
                    Platform.runLater(() -> {
                        label_Task_Status.setText("Finished");
                        //Hide progressBar
                        progressBar_MainTask.progressProperty().unbind();
                        progressBar_MainTask.setProgress(0);
                        progressBar_MainTask.setVisible(false);
                        hbox_Bottom.getChildren().remove(label_Task_Status);
                    });
                }
            });

            new Thread(loadOverview).start();
        });
    }

    /**
     *
     * @param formPath
     * @param tabName
     */
    public void task_Insert_Tab_With_Indicator(String formPath, String tabID, String tabName) {
        Platform.runLater(() -> {
            Label label_Task_Status = new Label();
            //Set timer and start
            MyTimer myTimer = new MyTimer();
            myTimer.create_myTimer(label_Task_Status);

            //Add label to bottom
            hbox_Bottom.getChildren().add(0, label_Task_Status);
            //Setting progressBar
            progressBar_MainTask.setVisible(true);
            progressBar_MainTask.progressProperty().unbind();
            //Create new task
            Task loadOverview = new Task() {
                @Override
                protected Object call() throws Exception {
                    System.out.println("Loading tab...");

                    //Checking existing tab
                    if (openTabs.containsKey(formPath)) {
                        mainTabPane.getSelectionModel().select(openTabs.get(formPath));
                    } else {
                        try {
                            // Get content from fxml file
                            AnchorPane subPane = (AnchorPane) FXMLLoader.load(getClass().getResource(formPath));
                            // Add fxml content to a tab
                            Tab subTab = new Tab(tabName);
                            subTab.setContent(subPane);
                            subTab.setId(tabID);
                            Platform.runLater(() -> {
                                mainTabPane.getTabs().add(subTab);
                            });
                            mainTabPane.getSelectionModel().select(subTab);
                            openTabs.put(formPath, subTab);
                            subTab.setOnClosed(e -> openTabs.remove(formPath));
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLMainFormController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    //Stop timer
                    myTimer.stop_Timer(label_Task_Status);
                    return null;
                }
            };
            //Binding progress bar with task
            progressBar_MainTask.progressProperty().bind(loadOverview.progressProperty());

            loadOverview.setOnSucceeded(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    System.out.println("Finished");
                    Platform.runLater(() -> {
                        label_Task_Status.setText("Finished");
                        //Hide progressBar
                        progressBar_MainTask.progressProperty().unbind();
                        progressBar_MainTask.setProgress(0);
                        progressBar_MainTask.setVisible(false);
                        hbox_Bottom.getChildren().remove(label_Task_Status);
                    });
                }
            });

            new Thread(loadOverview).start();
        });
    }

    @FXML
    private void handle_MenuItem_Add_Service_Order_Action(ActionEvent event) {
        System.out.println("Add new Service Order menu item clicked!");
        formLoader("/fxml/FXMLAddNewServiceOrder.fxml", "/images/KAN Logo.png", "Add new Service Order");
    }

    @FXML
    private void handle_MenuItem_List_Service_Orders_Action(ActionEvent event) {
        System.out.println("List service orders item clicked!");
        task_Insert_Tab_With_Indicator("/fxml/FXMLListServiceOrder.fxml", "listServiceOrders_Tab", "Service Orders");
    }

    @FXML
    private void handle_MenuItem_List_Service_Orders_Details_Action(ActionEvent event) {
        System.out.println("List service orders details item clicked!");
        task_Insert_Tab_With_Indicator("/fxml/FXMLListServiceOrderDetail.fxml", "listServiceOrdersDetails_Tab", "Service orders details");
    }

    @FXML
    private void handle_Toolbar_CheckIn_Action(ActionEvent event) {
        System.out.println("List service orders details item clicked!");
        task_Insert_Tab_With_Indicator("/fxml/FXMLListServiceOrderDetail.fxml", "listServiceOrdersDetails_Tab", "Service orders details");
    }

}
