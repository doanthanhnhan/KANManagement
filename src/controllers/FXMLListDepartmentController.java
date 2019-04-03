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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import models.ButtonDecentralization;
import models.DAODepartmentDecentralization;
import models.DecentralizationModel;
import models.DepartMentDecentralization;
import utils.StageLoader;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class FXMLListDepartmentController implements Initializable {

    ObservableList<DepartMentDecentralization> listDPM = FXCollections.observableArrayList();
    public Boolean check_Edit_Action = false;
    public static DepartMentDecentralization DPM;
    public static Boolean CheckListDepart = false;
    private static final int ROWS_PER_PAGE = 5;
    private FilteredList<DepartMentDecentralization> filteredData;
    @FXML
    private AnchorPane main_AnchorPaneDepartment;
    @FXML
    private Pagination pagination;
    @FXML
    private TableView<DepartMentDecentralization> tableDepartment;
    @FXML
    private ContextMenu contextMenu_Main;
    @FXML
    private MenuItem menuItem_Edit;
    @FXML
    private MenuItem menuItem_Add;
    @FXML
    private MenuItem menuItem_Delete;
    @FXML
    private MenuItem menuItem_Refresh;
    @FXML
    private JFXComboBox<String> BoxMenu;
    public static String BoxValue;
    private String add;
    private String delete;
    private String view;
    private String edit;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        contextMenu_Main.getItems().remove(menuItem_Add);
        contextMenu_Main.getItems().remove(menuItem_Delete);
        contextMenu_Main.getItems().remove(menuItem_Edit);
        ConnectControllers.setfXMLListDepartmentController(this);
        try {
            listDPM = DAODepartmentDecentralization.getAllDecentralization();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLInfoEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        FXMLMainFormController.checkRegis = true;
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
    }

    private void changeTableView(int index, int limit) {
        int fromIndex = index * limit;
        int toIndex = Math.min(fromIndex + limit, listDPM.size());

        int minIndex = Math.min(toIndex, filteredData.size());

        SortedList<DepartMentDecentralization> sortedData = new SortedList<>(
                FXCollections.observableArrayList(filteredData.subList(Math.min(fromIndex, minIndex), minIndex)));

        sortedData.comparatorProperty().bind(tableDepartment.comparatorProperty());

        tableDepartment.setItems(sortedData);
    }

    private void setColumns() {
        TableColumn<DepartMentDecentralization, String> dpmIDCol = new TableColumn<>("Department ID");
        TableColumn<DepartMentDecentralization, String> dpmNameCol = new TableColumn<>("Department Name");
        TableColumn<DepartMentDecentralization, String> dpmUserNameCol = new TableColumn<>("UserName");
        TableColumn<DepartMentDecentralization, CheckBox> dpmCreateCol = new TableColumn<>("Create");
        TableColumn<DepartMentDecentralization, Checkbox> dpmUpdateCol = new TableColumn<>("Update");
        TableColumn<DepartMentDecentralization, Checkbox> dpmReadCol = new TableColumn<>("Read");
        TableColumn<DepartMentDecentralization, Checkbox> dpmDeleteCol = new TableColumn<>("Delete");
        TableColumn<DepartMentDecentralization, ButtonDecentralization> btn_Action_Col = new TableColumn<>("Action");
        TableColumn numberCol = new TableColumn("#");
        numberCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DecentralizationModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DecentralizationModel, String> p) {
                return new ReadOnlyObjectWrapper((tableDepartment.getItems().indexOf(p.getValue()) + 1) + "");
            }
        });
        numberCol.setSortable(false);

        dpmCreateCol.setCellValueFactory(new PropertyValueFactory<>(add));
        dpmUpdateCol.setCellValueFactory(new PropertyValueFactory<>(edit));
        dpmReadCol.setCellValueFactory(new PropertyValueFactory<>(view));
        dpmDeleteCol.setCellValueFactory(new PropertyValueFactory<>(delete));
// else {
//            empCreateCol.setCellValueFactory(new PropertyValueFactory<>("User_Add"));
//            empUpdateCol.setCellValueFactory(new PropertyValueFactory<>("User_Edit"));
//            empReadCol.setCellValueFactory(new PropertyValueFactory<>("User_View"));
//            empDeleteCol.setCellValueFactory(new PropertyValueFactory<>("User_Delete"));
//        }
        dpmIDCol.setCellValueFactory(new PropertyValueFactory<>("DepartmentID"));
        dpmNameCol.setCellValueFactory(new PropertyValueFactory<>("DepartmentName"));
        dpmUserNameCol.setCellValueFactory(new PropertyValueFactory<>("Username"));
        btn_Action_Col.setCellValueFactory(new PropertyValueFactory<>("DecentralizationAction"));
        numberCol.setStyle("-fx-alignment: CENTER-LEFT;");
        dpmIDCol.setStyle("-fx-alignment: CENTER-LEFT;");
        dpmNameCol.setStyle("-fx-alignment: CENTER-LEFT;");
        dpmUserNameCol.setStyle("-fx-alignment: CENTER-LEFT;");
        dpmCreateCol.setStyle("-fx-alignment: CENTER;");
        dpmUpdateCol.setStyle("-fx-alignment: CENTER;");
        dpmReadCol.setStyle("-fx-alignment: CENTER;");
        dpmDeleteCol.setStyle("-fx-alignment: CENTER");

        // Thêm cột vào bảng
        tableDepartment.getColumns().clear();
        tableDepartment.getColumns().addAll(numberCol, dpmIDCol, dpmNameCol, dpmUserNameCol, dpmCreateCol, dpmUpdateCol, dpmReadCol, dpmDeleteCol, btn_Action_Col);

        // Xét xắp xếp theo userName
        //userNameCol.setSortType(TableColumn.SortType.DESCENDING);
    }

    public void showUsersData() {
        //table_ServiceType.getItems().clear();
        tableDepartment.setItems(listDPM);

        //Set filterData and Pagination
        filteredData = new FilteredList<>(listDPM, list -> true);
        FXMLMainFormController mainFormController = ConnectControllers.getfXMLMainFormController();
        mainFormController.getTxt_Search().textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(
                    DPM -> newValue == null || newValue.isEmpty()
                    || DPM.getDepartmentID().toLowerCase().contains(newValue.toLowerCase())
                    || DPM.getDepartmentName().toLowerCase().contains(newValue.toLowerCase())
                    || DPM.getUsername().toLowerCase().contains(newValue.toLowerCase()));
            pagination.setPageCount((int) (Math.ceil(filteredData.size() * 1.0 / ROWS_PER_PAGE)));
            changeTableView(pagination.getCurrentPageIndex(), ROWS_PER_PAGE);
        });

        int totalPage = (int) (Math.ceil(listDPM.size() * 1.0 / ROWS_PER_PAGE));
        pagination.setPageCount(totalPage);
        pagination.setCurrentPageIndex(0);
        changeTableView(0, ROWS_PER_PAGE);
        pagination.currentPageIndexProperty().addListener(
                (observable, oldValue, newValue) -> changeTableView(newValue.intValue(), ROWS_PER_PAGE));
    }

    @FXML
    private void handle_MenuItem_Edit_Action(ActionEvent event) {
    }

    @FXML
    private void handle_MenuItem_Add_Action(ActionEvent event) {
        CheckListDepart=true;
        StageLoader stageLoader = new StageLoader();
        stageLoader.formLoader("/fxml/FXMLAddDepartment.fxml", "/images/KAN Logo.png", "Add Department Informations");
    }

    @FXML
    private void handle_MenuItem_Delete_Action(ActionEvent event) {
    }

    @FXML
    private void handle_MenuItem_Refresh_Action(ActionEvent event) {
        showUsersData();
    }

}