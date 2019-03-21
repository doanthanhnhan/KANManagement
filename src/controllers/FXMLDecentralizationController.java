/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXComboBox;
import java.awt.Checkbox;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import models.ButtonDecentralization;
import models.DAO;
import models.DAOcheckRole;
import models.DecentralizationModel;
import utils.StageLoader;

/**
 * FXML Controller class
 *
 * @author Doan Thanh Nhan
 */
public class FXMLDecentralizationController implements Initializable {

    ObservableList<DecentralizationModel> listEmp = FXCollections.observableArrayList();
    public Boolean check_Edit_Action = false;
    public static DecentralizationModel Emp;
    public static Boolean check_form_list = false;
    private static final int ROWS_PER_PAGE = 4;
    private FilteredList<DecentralizationModel> filteredData;

    @FXML
    public TableView<DecentralizationModel> tableDecentralization;

    @FXML
    private MenuItem menuItem_Edit;
    @FXML
    private MenuItem menuItem_Add;
    @FXML
    private MenuItem menuItem_Delete;
    @FXML
    private AnchorPane main_AnchorPane;
    @FXML
    private MenuItem menuItem_Refresh;
    @FXML
    private Pagination pagination;
    @FXML
    private JFXComboBox<String> BoxMenu;
    public static String BoxValue;
    private String add;
    private String delete;
    private String view;
    private String edit;
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ConnectControllers.setfXMLDecentralizationController(this);
        try {
            listEmp = DAOcheckRole.getAllDecentralization();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLInfoEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        FXMLMainFormController.checkRegis = true;
        check_form_list = true;
        showUsersData();
        ObservableList list_menu = FXCollections.observableArrayList();
        list_menu.add("Employees Decentralization");
        list_menu.add("Users Decentralization");
        list_menu.add("Booking Decentralization");
        list_menu.add("CheckIn Decentralization");
        list_menu.add("CheckOut Decentralization");
        list_menu.add("Customer Decentralization");
        list_menu.add("Department Decentralization");
        list_menu.add("Role Decentralization");
        list_menu.add("Room Decentralization");
        list_menu.add("SODetail Decentralization");
        list_menu.add("SODer Decentralization");
        list_menu.add("SType Decentralization");
        list_menu.add("UserLog Decentralization");

        BoxMenu.setItems(list_menu);
        BoxMenu.setValue("Employees Decentralization");
        BoxValue = BoxMenu.getValue();
        add = "Employee_Add";
        delete = "Employee_Delete";
        view = "Employee_View";
        edit = "Employee_Edit";
        setColumns();
        BoxMenu.valueProperty().addListener((obs, oldItem, newItem) -> {
            BoxValue = BoxMenu.getValue();
            System.out.println("Kiem tra newItem: " + newItem);
            if (newItem.equals("Employees Decentralization")) {
                add = "Employee_Add";
                delete = "Employee_Delete";
                view = "Employee_View";
                edit = "Employee_Edit";
                setColumns();
            } else if (newItem.equals("Users Decentralization")) {
                add = "User_Add";
                delete = "User_Delete";
                view = "User_View";
                edit = "User_Edit";
                setColumns();
            } else if (newItem.equals("Booking Decentralization")) {
                add = "Booking_Add";
                delete = "Booking_Delete";
                view = "Booking_View";
                edit = "Booking_Edit";
                setColumns();
            } else if (newItem.equals("CheckIn Decentralization")) {
                add = "CheckIn_Add";
                delete = "CheckIn_Delete";
                view = "CheckIn_View";
                edit = "CheckIn_Edit";
                setColumns();
            } else if (newItem.equals("CheckOut Decentralization")) {
                add = "CheckOut_Add";
                delete = "CheckOut_Delete";
                view = "CheckOut_View";
                edit = "CheckOut_Edit";
                setColumns();
            } else if (newItem.equals("Customer Decentralization")) {
                add = "Customer_Add";
                delete = "Customer_Delete";
                view = "Customer_View";
                edit = "Customer_Edit";
                setColumns();
            } else if (newItem.equals("Department Decentralization")) {
                add = "Department_Add";
                delete = "Department_Delete";
                view = "Department_View";
                edit = "Department_Edit";
                setColumns();
            } else if (newItem.equals("Role Decentralization")) {
                add = "Role_Add";
                delete = "Role_Delete";
                view = "Role_View";
                edit = "Role_Edit";
                setColumns();
            } else if (newItem.equals("Room Decentralization")) {
                add = "Room_Add";
                delete = "Room_Delete";
                view = "Room_View";
                edit = "Room_Edit";
                setColumns();
            } else if (newItem.equals("SODetail Decentralization")) {
                add = "SODetail_Add";
                delete = "SODetail_Delete";
                view = "SODetail_View";
                edit = "SODetail_Edit";
                setColumns();
            } else if (newItem.equals("SODer Decentralization")) {
                add = "SODer_Add";
                delete = "SODer_Delete";
                view = "SODer_View";
                edit = "SODer_Edit";
                setColumns();
            } else if (newItem.equals("SType Decentralization")) {
                add = "SType_Add";
                delete = "SType_Delete";
                view = "SType_View";
                edit = "SType_Edit";
                setColumns();
            } else if (newItem.equals("UserLog Decentralization")) {
                add = "UserLog_Add";
                delete = "UserLog_Delete";
                view = "UserLog_View";
                edit = "UserLog_Edit";
                setColumns();
            }
        });
        // Check item when click on table
        tableDecentralization.setOnMouseClicked((MouseEvent event) -> {
            if ((event.getButton().equals(MouseButton.PRIMARY) || event.getButton().equals(MouseButton.SECONDARY))
                    && tableDecentralization.getSelectionModel().getSelectedItem() != null) {
                menuItem_Edit.setDisable(false);
                menuItem_Delete.setDisable(false);
                Emp = tableDecentralization.getSelectionModel().getSelectedItem();
            } else {
                menuItem_Edit.setDisable(true);
                menuItem_Delete.setDisable(true);
            }
        });
        System.out.println("hello");
    }

    private void changeTableView(int index, int limit) {

        int fromIndex = index * limit;
        int toIndex = Math.min(fromIndex + limit, listEmp.size());

        int minIndex = Math.min(toIndex, filteredData.size());

        SortedList<DecentralizationModel> sortedData = new SortedList<>(
                FXCollections.observableArrayList(filteredData.subList(Math.min(fromIndex, minIndex), minIndex)));

        sortedData.comparatorProperty().bind(tableDecentralization.comparatorProperty());

        tableDecentralization.setItems(sortedData);

    }

    private void setColumns() {
        TableColumn<DecentralizationModel, String> empIDCol = new TableColumn<>("Emp ID");
        TableColumn<DecentralizationModel, String> empFNameCol = new TableColumn<>("Emp FirstName");
        TableColumn<DecentralizationModel, String> empMNameCol = new TableColumn<>("Emp MidName");
        TableColumn<DecentralizationModel, String> empLNameCol = new TableColumn<>("Emp LastName");
        TableColumn<DecentralizationModel, CheckBox> empCreateCol = new TableColumn<>("Create");
        TableColumn<DecentralizationModel, Checkbox> empUpdateCol = new TableColumn<>("Update");
        TableColumn<DecentralizationModel, Checkbox> empReadCol = new TableColumn<>("Read");
        TableColumn<DecentralizationModel, Checkbox> empDeleteCol = new TableColumn<>("Delete");

        TableColumn<DecentralizationModel, ButtonDecentralization> btn_Action_Col = new TableColumn<>("Action");
        TableColumn numberCol = new TableColumn("#");
        numberCol.setCellValueFactory(new Callback<CellDataFeatures<DecentralizationModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<DecentralizationModel, String> p) {
                return new ReadOnlyObjectWrapper((tableDecentralization.getItems().indexOf(p.getValue()) + 1) + "");
            }
        });
        numberCol.setSortable(false);

        empCreateCol.setCellValueFactory(new PropertyValueFactory<>(add));
        empUpdateCol.setCellValueFactory(new PropertyValueFactory<>(edit));
        empReadCol.setCellValueFactory(new PropertyValueFactory<>(view));
        empDeleteCol.setCellValueFactory(new PropertyValueFactory<>(delete));
// else {
//            empCreateCol.setCellValueFactory(new PropertyValueFactory<>("User_Add"));
//            empUpdateCol.setCellValueFactory(new PropertyValueFactory<>("User_Edit"));
//            empReadCol.setCellValueFactory(new PropertyValueFactory<>("User_View"));
//            empDeleteCol.setCellValueFactory(new PropertyValueFactory<>("User_Delete"));
//        }
        empIDCol.setCellValueFactory(new PropertyValueFactory<>("Employee_ID"));
        empFNameCol.setCellValueFactory(new PropertyValueFactory<>("First_Name"));
        empMNameCol.setCellValueFactory(new PropertyValueFactory<>("Mid_Name"));
        empLNameCol.setCellValueFactory(new PropertyValueFactory<>("Last_Name"));
        btn_Action_Col.setCellValueFactory(new PropertyValueFactory<>("DecentralizationAction"));
        numberCol.setStyle("-fx-alignment: CENTER-LEFT;");
        empIDCol.setStyle("-fx-alignment: CENTER-LEFT;");
        empFNameCol.setStyle("-fx-alignment: CENTER-LEFT;");
        empMNameCol.setStyle("-fx-alignment: CENTER-LEFT;");
        empLNameCol.setStyle("-fx-alignment: CENTER-LEFT;");
        empCreateCol.setStyle("-fx-alignment: CENTER;");
        empUpdateCol.setStyle("-fx-alignment: CENTER;");
        empReadCol.setStyle("-fx-alignment: CENTER;");
        empDeleteCol.setStyle("-fx-alignment: CENTER");

        // Thêm cột vào bảng
        tableDecentralization.getColumns().clear();
        tableDecentralization.getColumns().addAll(numberCol, empIDCol, empFNameCol, empMNameCol, empLNameCol, empCreateCol, empUpdateCol, empReadCol, empDeleteCol, btn_Action_Col);

        // Xét xắp xếp theo userName
        //userNameCol.setSortType(TableColumn.SortType.DESCENDING);
    }

    public void showUsersData() {
        //table_ServiceType.getItems().clear();
        tableDecentralization.setItems(listEmp);

        //Set filterData and Pagination
        filteredData = new FilteredList<>(listEmp, list -> true);
        FXMLMainFormController mainFormController = ConnectControllers.getfXMLMainFormController();
        mainFormController.getTxt_Search().textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(
                    Emp -> newValue == null || newValue.isEmpty()
                    || Emp.getEmployee_ID().toLowerCase().contains(newValue.toLowerCase())
                    || Emp.getFirst_Name().toLowerCase().contains(newValue.toLowerCase())
                    || Emp.getMid_Name().toLowerCase().contains(newValue.toLowerCase())
                    || Emp.getLast_Name().toLowerCase().contains(newValue.toLowerCase()));
            pagination.setPageCount((int) (Math.ceil(filteredData.size() * 1.0 / ROWS_PER_PAGE)));
            changeTableView(pagination.getCurrentPageIndex(), ROWS_PER_PAGE);
        });

        int totalPage = (int) (Math.ceil(listEmp.size() * 1.0 / ROWS_PER_PAGE));
        pagination.setPageCount(totalPage);
        pagination.setCurrentPageIndex(0);
        changeTableView(0, ROWS_PER_PAGE);
        pagination.currentPageIndexProperty().addListener(
                (observable, oldValue, newValue) -> changeTableView(newValue.intValue(), ROWS_PER_PAGE));
    }

    public Boolean male_Female(String str) {
        if (str.matches("^[m]{1,1}[a]{0,1}[l]{0,1}[e]{0,1}.*$")) {
            return true;
        }
        if (str.matches("^[f]{1,1}[e]{0,1}[m]{0,1}[a]{0,1}[l]{0,1}[e]{0,1}.*$")) {
            return false;
        }
        return null;
    }

    @FXML
    private void handle_MenuItem_Edit_Action(ActionEvent event) {
        check_Edit_Action = true;
        StageLoader stageLoader = new StageLoader();
        stageLoader.formLoader("/fxml/FXMLInfoEmployee.fxml", "/images/KAN Logo.png", "Edit Service Type Informations");

    }

    @FXML
    private void handle_MenuItem_Add_Action(ActionEvent event) {
        check_Edit_Action = false;
        StageLoader stageLoader = new StageLoader();
        stageLoader.formLoader("/fxml/FXMLAddNewEmployee.fxml", "/images/KAN Logo.png", "Edit Service Type Informations");
    }

    @FXML
    private void handle_MenuItem_Delete_Action(ActionEvent event) {
        System.out.println("Kien");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Comfirm");
        alert.setContentText("Do you want to delete " + Emp.getEmployee_ID() + "?");
        alert.showAndWait();
        System.out.println(alert.getResult());
        if (alert.getResult() == ButtonType.OK) {
            try {
                DAO.delete_Employee(Emp.getEmployee_ID());
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                String logtime;
                logtime = dateFormat.format(cal.getTime());
                DAO.setUserLogs(FXMLLoginController.User_Login, "Delete " + Emp.getEmployee_ID(), logtime);
                System.out.println("Delete successful");
                showUsersData();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(FXMLListEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void handle_MenuItem_Refresh_Action(ActionEvent event) {
        showUsersData();
    }

}
