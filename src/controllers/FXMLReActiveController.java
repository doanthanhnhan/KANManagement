/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.FXMLDecentralizationController.BoxValue;
import models.ReActive;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import models.ButtonDecentralization;
import models.DAOReActive;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class FXMLReActiveController implements Initializable {

    ObservableList<ReActive> listEmp = FXCollections.observableArrayList();
    public static ReActive Emp;
    @FXML
    private AnchorPane main_AnchorPane;
    @FXML
    private Pagination pagination;
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
    private TableView<ReActive> table_Reactive;
    @FXML
    private JFXComboBox<String> BoxMenu;
    private String add;
    private FilteredList<ReActive> filteredData;
    private static final int ROWS_PER_PAGE = 10;
    public static Boolean check_Edit_Action = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ConnectControllers.setfXMLReActiveController(this);
        contextMenu_Main.getItems().remove(menuItem_Add);
        contextMenu_Main.getItems().remove(menuItem_Delete);
        contextMenu_Main.getItems().remove(menuItem_Edit);
        ConnectControllers.setfXMLReActiveController(this);
        check_Edit_Action = true;
        showUsersData();
        ObservableList list_menu = FXCollections.observableArrayList();
        list_menu.add("ReActive Employee");

        BoxMenu.setItems(list_menu);
        BoxMenu.setValue("ReActive Employee");
        BoxValue = BoxMenu.getValue();
        add = "Active_Employee";
        setColumns();
        BoxMenu.valueProperty().addListener((obs, oldItem, newItem) -> {
            BoxValue = BoxMenu.getValue();
            System.out.println("Kiem tra newItem: " + newItem);
            if (newItem.equals("ReActive Employee")) {
                add = "Active_Employee";
                setColumns();
            }
        });
        // Check item when click on table
    }
    private void changeTableView(int index, int limit) {

        int fromIndex = index * limit;
        int toIndex = Math.min(fromIndex + limit, listEmp.size());

        int minIndex = Math.min(toIndex, filteredData.size());

        SortedList<ReActive> sortedData = new SortedList<>(
                FXCollections.observableArrayList(filteredData.subList(Math.min(fromIndex, minIndex), minIndex)));

        sortedData.comparatorProperty().bind(table_Reactive.comparatorProperty());

        table_Reactive.setItems(sortedData);

    }

    private void setColumns() {
        TableColumn<ReActive, String> empIDCol = new TableColumn<>("Emp ID");
        TableColumn<ReActive, String> empFNameCol = new TableColumn<>("Emp FirstName");
        TableColumn<ReActive, String> empMNameCol = new TableColumn<>("Emp MidName");
        TableColumn<ReActive, String> empLNameCol = new TableColumn<>("Emp LastName");
        TableColumn<ReActive, CheckBox> empCreateCol = new TableColumn<>("Active");
        TableColumn<ReActive, ButtonDecentralization> btn_Action_Col = new TableColumn<>("Action");
        TableColumn numberCol = new TableColumn("#");
        numberCol.setCellValueFactory(new Callback<CellDataFeatures<ReActive, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<ReActive, String> p) {
                return new ReadOnlyObjectWrapper((table_Reactive.getItems().indexOf(p.getValue()) + 1) + "");
            }
        });
        numberCol.setSortable(false);

        empCreateCol.setCellValueFactory(new PropertyValueFactory<>(add));
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
        empCreateCol.setCellValueFactory(new PropertyValueFactory<>("Employee_ReActive"));
        btn_Action_Col.setCellValueFactory(new PropertyValueFactory<>("HboxReActive"));
        numberCol.setStyle("-fx-alignment: CENTER-LEFT;");
        empIDCol.setStyle("-fx-alignment: CENTER-LEFT;");
        empFNameCol.setStyle("-fx-alignment: CENTER-LEFT;");
        empMNameCol.setStyle("-fx-alignment: CENTER-LEFT;");
        empLNameCol.setStyle("-fx-alignment: CENTER-LEFT;");
        empCreateCol.setStyle("-fx-alignment: CENTER;");
        btn_Action_Col.setStyle("-fx-alignment: CENTER;");
        btn_Action_Col.setPrefWidth(120);

        // Thêm cột vào bảng
        table_Reactive.getColumns().clear();
        table_Reactive.getColumns().addAll(numberCol, empIDCol, empFNameCol, empMNameCol, empLNameCol, empCreateCol, btn_Action_Col);

        // Xét xắp xếp theo userName
        //userNameCol.setSortType(TableColumn.SortType.DESCENDING);
    }

    public void showUsersData() {
        try {
            listEmp = DAOReActive.getAllDecentralization();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLInfoEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //table_ServiceType.getItems().clear();
        table_Reactive.setItems(listEmp);

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

    @FXML
    private void handle_MenuItem_Edit_Action(ActionEvent event) {
    }

    @FXML
    private void handle_MenuItem_Add_Action(ActionEvent event) {
    }

    @FXML
    private void handle_MenuItem_Delete_Action(ActionEvent event) {
    }

    @FXML
    private void handle_MenuItem_Refresh_Action(ActionEvent event) {
        showUsersData();
    }

}
