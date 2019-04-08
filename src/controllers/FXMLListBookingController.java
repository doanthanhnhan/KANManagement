/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.FXMLListEmployeeController.Emp;
import static controllers.FXMLListEmployeeController.check_Edit_Action;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import models.BookingInfo;
import models.DAO;
import models.DAOCustomerBookingCheckIn;
import models.InfoEmployee;
import models.RoleDAOImpl;
import models.boolDecentralizationModel;
import utils.StageLoader;
import utils.showFXMLLogin;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class FXMLListBookingController implements Initializable {

    public boolDecentralizationModel userRole;
    RoleDAOImpl roleDAOImpl;
    private showFXMLLogin showFormLogin = new showFXMLLogin();
    ObservableList<BookingInfo> listBooking = FXCollections.observableArrayList();
    @FXML
    private AnchorPane main_AnchorPane;
    @FXML
    private Pagination pagination;
    private static final int ROWS_PER_PAGE = 10;
    private FilteredList<BookingInfo> filteredData;
    @FXML
    private TableView<BookingInfo> table_ListBooking;
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
    public String new_Emp_ID;
    public int row_add_new_index;
    public boolean check_Add_New = false;
    public int pagination_index;
    public int row_index;
    public static Boolean check_formBooking_list = false;
    public static BookingInfo bk;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        check_formBooking_list=true;
        setColumns();
        showUsersData();
        ConnectControllers.setfXMLListBookingController(this);
        roleDAOImpl = new RoleDAOImpl();
        // Check item when click on table
        table_ListBooking.setOnMouseClicked((MouseEvent event) -> {
            if ((event.getButton().equals(MouseButton.PRIMARY) || event.getButton().equals(MouseButton.SECONDARY))
                    && table_ListBooking.getSelectionModel().getSelectedItem() != null) {
                menuItem_Edit.setDisable(false);
                menuItem_Delete.setDisable(false);
                bk = table_ListBooking.getSelectionModel().getSelectedItem();
                row_index = table_ListBooking.getSelectionModel().getSelectedIndex();
                pagination_index = pagination.getCurrentPageIndex();
            } else {
                menuItem_Edit.setDisable(true);
                menuItem_Delete.setDisable(true);
            }
        });
        //Get user role from Mainform
        FXMLMainFormController mainFormController = ConnectControllers.getfXMLMainFormController();
        //userRole = mainFormController.getUserRole();
        userRole = roleDAOImpl.getEmployeeRole(mainFormController.userRole.getEmployee_ID());
        //11.SERVICE TYPE CRUD
        if (!userRole.ischeckEmployee_Add()) {
            contextMenu_Main.getItems().remove(menuItem_Add);
        }
        if (!userRole.ischeckEmployee_Delete()) {
            contextMenu_Main.getItems().remove(menuItem_Delete);
        }
        if (!userRole.ischeckEmployee_Edit()) {
            contextMenu_Main.getItems().remove(menuItem_Edit);
        }
        if (!userRole.ischeckSType_View()) {
//            contextMenu_Main.getItems().remove(menuItem_List_Service_Type);
        }
    }

    private void changeTableView(int index, int limit) {

        int fromIndex = index * limit;
        int toIndex = Math.min(fromIndex + limit, listBooking.size());

        int minIndex = Math.min(toIndex, filteredData.size());

        SortedList<BookingInfo> sortedData = new SortedList<>(
                FXCollections.observableArrayList(filteredData.subList(Math.min(fromIndex, minIndex), minIndex)));

        sortedData.comparatorProperty().bind(table_ListBooking.comparatorProperty());

        table_ListBooking.setItems(sortedData);

    }

    private void setColumns() {
        TableColumn<BookingInfo, String> bkIdCol = new TableColumn<>("Booking ID");
        TableColumn<BookingInfo, String> cusIdCol = new TableColumn<>("Customer ID");
        TableColumn<BookingInfo, String> roomIdCol = new TableColumn<>("Room ID");
        TableColumn<BookingInfo, String> userCol = new TableColumn<>("User Booking");
        TableColumn<BookingInfo, String> dateCol = new TableColumn<>("Date Booking");
        TableColumn<BookingInfo, String> noteCol = new TableColumn<>("Note");
        TableColumn<BookingInfo, Integer> numberGuestCol = new TableColumn<>("Number Guest");
        TableColumn numberCol = new TableColumn("#");
        numberCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<InfoEmployee, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InfoEmployee, String> p) {
                return new ReadOnlyObjectWrapper((table_ListBooking.getItems().indexOf(p.getValue()) + 1) + "");
            }
        });
        numberCol.setSortable(false);

        // Định nghĩa cách để lấy dữ liệu cho mỗi ô.
        // Lấy giá trị từ các thuộc tính của ServiceType.
        bkIdCol.setCellValueFactory(new PropertyValueFactory<>("BookID"));
        cusIdCol.setCellValueFactory(new PropertyValueFactory<>("CusID"));
        roomIdCol.setCellValueFactory(new PropertyValueFactory<>("RoomID"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("User"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));
        noteCol.setCellValueFactory(new PropertyValueFactory<>("Note"));
        numberGuestCol.setCellValueFactory(new PropertyValueFactory<>("NumGuest"));

        numberCol.setStyle("-fx-alignment: CENTER;");
        bkIdCol.setStyle("-fx-alignment: CENTER;");
        cusIdCol.setStyle("-fx-alignment: CENTER;");
        roomIdCol.setStyle("-fx-alignment: CENTER;");
        userCol.setStyle("-fx-alignment: CENTER;");
        dateCol.setStyle("-fx-alignment: CENTER;");
        numberGuestCol.setStyle("-fx-alignment: CENTER;");
        noteCol.setStyle("-fx-alignment: CENTER;");

        // Thêm cột vào bảng
        table_ListBooking.getColumns().clear();
        table_ListBooking.getColumns().addAll(numberCol, bkIdCol, cusIdCol, roomIdCol, userCol, dateCol, noteCol, numberGuestCol);

        // Xét xắp xếp theo userName
        //userNameCol.setSortType(TableColumn.SortType.DESCENDING);
    }

    public void showUsersData() {
        try {
            listBooking = DAOCustomerBookingCheckIn.getListBooking();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLListEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //table_ServiceType.getItems().clear();
        table_ListBooking.setItems(listBooking);

        //Set filterData and Pagination
        filteredData = new FilteredList<>(listBooking, list -> true);
        FXMLMainFormController mainFormController = ConnectControllers.getfXMLMainFormController();
        mainFormController.getTxt_Search().textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(
                    bk -> newValue == null || newValue.isEmpty()
                    || bk.getBookID().toLowerCase().contains(newValue.toLowerCase())
                    || bk.getCusID().toLowerCase().contains(newValue.toLowerCase())
                    || bk.getDate().toLowerCase().contains(newValue.toLowerCase())
                    || bk.getNumGuest().toString().toLowerCase().contains(newValue.toLowerCase())
                    || bk.getRoomID().toLowerCase().contains(newValue.toLowerCase())
                    || bk.getUser().toLowerCase().contains(newValue.toLowerCase()));
            pagination.setPageCount((int) (Math.ceil(filteredData.size() * 1.0 / ROWS_PER_PAGE)));
            changeTableView(pagination.getCurrentPageIndex(), ROWS_PER_PAGE);
        });
        int totalPage = (int) (Math.ceil(listBooking.size() * 1.0 / ROWS_PER_PAGE));
        pagination.setPageCount(totalPage);
        pagination.setCurrentPageIndex(0);
        changeTableView(0, ROWS_PER_PAGE);
        pagination.currentPageIndexProperty().addListener(
                (observable, oldValue, newValue) -> changeTableView(newValue.intValue(), ROWS_PER_PAGE));
        if (check_Add_New) {
            //Setting new room index
            for (BookingInfo item : listBooking) {
                if (new_Emp_ID.equals(item.getBookID())) {
                    row_add_new_index = listBooking.indexOf(item);
                    break;
                }
            }
            Platform.runLater(() -> {
                int focusPage = (int) row_add_new_index / ROWS_PER_PAGE;
                int new_index = row_add_new_index % ROWS_PER_PAGE;
                pagination.setCurrentPageIndex(focusPage);
                changeTableView(focusPage, ROWS_PER_PAGE);
                table_ListBooking.requestFocus();
                table_ListBooking.getSelectionModel().select(new_index);
                table_ListBooking.getFocusModel().focus(new_index);

            });
            check_Add_New = false;
        } else {
            //Forcus to the editing row
            Platform.runLater(() -> {
                //int focusPage = (int) row_index / ROWS_PER_PAGE;
                //int new_index = row_index % ROWS_PER_PAGE;
                pagination.setCurrentPageIndex(pagination_index);
                changeTableView(pagination_index, ROWS_PER_PAGE);
                table_ListBooking.requestFocus();
                table_ListBooking.getSelectionModel().select(row_index);
                table_ListBooking.getFocusModel().focus(row_index);
            });
        }
    }

    @FXML
    private void handle_MenuItem_Edit_Action(ActionEvent event) {
    }

    @FXML
    private void handle_MenuItem_Add_Action(ActionEvent event) {
        StageLoader stageLoader = new StageLoader();
        stageLoader.formLoader("/fxml/FXMLBookingInfo.fxml", "/images/KAN Logo.png", "Add New Booking");
    }

    @FXML
    private void handle_MenuItem_Delete_Action(ActionEvent event) {
    }

    @FXML
    private void handle_MenuItem_Refresh_Action(ActionEvent event) {
        showUsersData();
    }

}
