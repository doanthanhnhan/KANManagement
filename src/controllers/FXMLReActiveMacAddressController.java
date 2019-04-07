/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

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
import models.DAOReActive;
import models.DAOReActiveMacAddress;
import models.MacAddress;
import models.ReActive;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class FXMLReActiveMacAddressController implements Initializable {

    ObservableList<MacAddress> listMACAddress = FXCollections.observableArrayList();
    @FXML
    private AnchorPane main_AnchorPane;
    @FXML
    private Pagination pagination;
    @FXML
    private TableView<MacAddress> table_ReactiveMacAddress;
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
    private FilteredList<MacAddress> filteredData;
    private static final int ROWS_PER_PAGE = 10;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ConnectControllers.setfXMLReActiveMacAddressController(this);
        contextMenu_Main.getItems().remove(menuItem_Add);
        contextMenu_Main.getItems().remove(menuItem_Delete);
        contextMenu_Main.getItems().remove(menuItem_Edit);
        setColumns();
        showUsersData();
    }

    private void changeTableView(int index, int limit) {

        int fromIndex = index * limit;
        int toIndex = Math.min(fromIndex + limit, listMACAddress.size());

        int minIndex = Math.min(toIndex, filteredData.size());

        SortedList<MacAddress> sortedData = new SortedList<>(
                FXCollections.observableArrayList(filteredData.subList(Math.min(fromIndex, minIndex), minIndex)));

        sortedData.comparatorProperty().bind(table_ReactiveMacAddress.comparatorProperty());

        table_ReactiveMacAddress.setItems(sortedData);

    }

    private void setColumns() {
        TableColumn<MacAddress, Integer> IDCol = new TableColumn<>("ID");
        TableColumn<MacAddress, String> MacAddressCol = new TableColumn<>("MacAddress");
        TableColumn<MacAddress, Integer> TimesCol = new TableColumn<>("Times");
        TableColumn<MacAddress, CheckBox> empCreateCol = new TableColumn<>("Active");
        TableColumn<MacAddress, ButtonDecentralization> btn_Action_Col = new TableColumn<>("Action");
        TableColumn numberCol = new TableColumn("#");
        numberCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ReActive, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ReActive, String> p) {
                return new ReadOnlyObjectWrapper((table_ReactiveMacAddress.getItems().indexOf(p.getValue()) + 1) + "");
            }
        });
        numberCol.setSortable(false);
// else {
//            empCreateCol.setCellValueFactory(new PropertyValueFactory<>("User_Add"));
//            empUpdateCol.setCellValueFactory(new PropertyValueFactory<>("User_Edit"));
//            empReadCol.setCellValueFactory(new PropertyValueFactory<>("User_View"));
//            empDeleteCol.setCellValueFactory(new PropertyValueFactory<>("User_Delete"));
//        }
        IDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        MacAddressCol.setCellValueFactory(new PropertyValueFactory<>("MACAddress"));
        TimesCol.setCellValueFactory(new PropertyValueFactory<>("Times"));
        empCreateCol.setCellValueFactory(new PropertyValueFactory<>("MacAddress_ReActive"));
        btn_Action_Col.setCellValueFactory(new PropertyValueFactory<>("HboxReActive"));
        numberCol.setStyle("-fx-alignment: CENTER;");
        IDCol.setStyle("-fx-alignment: CENTER;");
        MacAddressCol.setStyle("-fx-alignment: CENTER;");
        TimesCol.setStyle("-fx-alignment: CENTER;");
        empCreateCol.setStyle("-fx-alignment: CENTER;");
        btn_Action_Col.setStyle("-fx-alignment: CENTER;");
        btn_Action_Col.setPrefWidth(120);
        MacAddressCol.setPrefWidth(200);
        

        // Thêm cột vào bảng
        table_ReactiveMacAddress.getColumns().clear();
        table_ReactiveMacAddress.getColumns().addAll(numberCol, IDCol, MacAddressCol, TimesCol, empCreateCol, btn_Action_Col);

        // Xét xắp xếp theo userName
        //userNameCol.setSortType(TableColumn.SortType.DESCENDING);
    }

    public void showUsersData() {
        try {
            listMACAddress = DAOReActiveMacAddress.getAllReActiveMacAddress();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLInfoEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //table_ServiceType.getItems().clear();
        table_ReactiveMacAddress.setItems(listMACAddress);

        //Set filterData and Pagination
        filteredData = new FilteredList<>(listMACAddress, list -> true);
        FXMLMainFormController mainFormController = ConnectControllers.getfXMLMainFormController();
        mainFormController.getTxt_Search().textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(
                    listMACAddress -> newValue == null || newValue.isEmpty()
                    || listMACAddress.getMACAddress().toLowerCase().contains(newValue.toLowerCase()));
            pagination.setPageCount((int) (Math.ceil(filteredData.size() * 1.0 / ROWS_PER_PAGE)));
            changeTableView(pagination.getCurrentPageIndex(), ROWS_PER_PAGE);
        });

        int totalPage = (int) (Math.ceil(listMACAddress.size() * 1.0 / ROWS_PER_PAGE));
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
