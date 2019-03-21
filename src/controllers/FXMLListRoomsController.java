/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.FXMLListServiceTypeController.serviceTypeItem;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
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
import models.DAO;
import models.RoomAction;
import models.RoomEX;
import models.RoomDAOImpl;
import utils.FormatName;
import utils.StageLoader;

/**
 * FXML Controller class
 *
 * @author Doan Thanh Nhan
 */
public class FXMLListRoomsController implements Initializable {

    ObservableList<RoomEX> listRoomEXs = FXCollections.observableArrayList();
    RoomDAOImpl roomEXDAOImpl = new RoomDAOImpl();

    public Boolean check_Edit_Action = false;
    public static RoomEX roomEXItem;

    private static final int ROWS_PER_PAGE = 20;
    private FilteredList<RoomEX> filteredData;
    private FXMLMainFormController mainFormController;

    @FXML
    private TableView<RoomEX> table_Rooms;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("List Rooms initialize...");
        ConnectControllers.setfXMLListRoomsController(this);
        mainFormController = ConnectControllers.getfXMLMainFormController();
        setColumns();
        showRoomsData();
        //ConnectControllers.setfXMLListRoomEXController(this);

        // Check item when click on table
        table_Rooms.setOnMouseClicked((MouseEvent event) -> {
            if ((event.getButton().equals(MouseButton.PRIMARY) || event.getButton().equals(MouseButton.SECONDARY))
                    && table_Rooms.getSelectionModel().getSelectedItem() != null) {
                menuItem_Edit.setDisable(false);
                menuItem_Delete.setDisable(false);
                System.out.println(table_Rooms.getSelectionModel().getSelectedItem().getRoomID());
                roomEXItem = table_Rooms.getSelectionModel().getSelectedItem();
            } else {
                menuItem_Edit.setDisable(true);
                menuItem_Delete.setDisable(true);
            }
        });
    }

    private void changeTableView(int index, int limit) {

        int fromIndex = index * limit;
        int toIndex = Math.min(fromIndex + limit, listRoomEXs.size());

        int minIndex = Math.min(toIndex, filteredData.size());

        SortedList<RoomEX> sortedData = new SortedList<>(
                FXCollections.observableArrayList(filteredData.subList(Math.min(fromIndex, minIndex), minIndex)));

        sortedData.comparatorProperty().bind(table_Rooms.comparatorProperty());

        table_Rooms.setItems(sortedData);

    }

    private void setColumns() {
        TableColumn<RoomEX, String> roomIDCol = new TableColumn<>("Room ID");
        TableColumn<RoomEX, String> roomTypeCol = new TableColumn<>("Room type");
        TableColumn<RoomEX, String> roomStatusCol = new TableColumn<>("Status");
        TableColumn<RoomEX, String> customerNameCol = new TableColumn<>("Customer name");
        TableColumn<RoomEX, Integer> remainingDaysCol = new TableColumn<>("Days");
        TableColumn<RoomEX, String> roomPhoneNumberCol = new TableColumn<>("Phone number");
        TableColumn<RoomEX, Integer> roomOnFloorCol = new TableColumn<>("Floor");
        TableColumn<RoomEX, CheckBox> roomCleanCol = new TableColumn<>("Clean");
        TableColumn<RoomEX, CheckBox> roomRepairedCol = new TableColumn<>("Repaired");
        TableColumn<RoomEX, CheckBox> roomInProgressCol = new TableColumn<>("In progress");
        TableColumn<RoomEX, RoomAction> roomActionCol = new TableColumn<>("Action");

        TableColumn numberCol = new TableColumn("#");
        numberCol.setCellValueFactory(new Callback<CellDataFeatures<RoomEX, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<RoomEX, String> p) {
                return new ReadOnlyObjectWrapper((table_Rooms.getItems().indexOf(p.getValue()) + 1) + "");
            }
        });
        numberCol.setSortable(false);

        // Định nghĩa cách để lấy dữ liệu cho mỗi ô.
        // Lấy giá trị từ các thuộc tính của RoomEX.
        roomIDCol.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        roomTypeCol.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        roomStatusCol.setCellValueFactory(new PropertyValueFactory<>("roomStatus"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        remainingDaysCol.setCellValueFactory(new PropertyValueFactory<>("dayRemaining"));
        roomPhoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("roomPhoneNumber"));
        roomOnFloorCol.setCellValueFactory(new PropertyValueFactory<>("roomOnFloor"));
        roomCleanCol.setCellValueFactory(new PropertyValueFactory<>("checkBox_Room_Clean"));
        roomRepairedCol.setCellValueFactory(new PropertyValueFactory<>("checkBox_Room_Repaired"));
        roomInProgressCol.setCellValueFactory(new PropertyValueFactory<>("checkBox_Room_In_Progress"));
        roomActionCol.setCellValueFactory(new PropertyValueFactory<>("roomAction"));

        numberCol.setStyle("-fx-alignment: CENTER-LEFT;");
        roomIDCol.setStyle("-fx-alignment: CENTER-LEFT;");
        roomTypeCol.setStyle("-fx-alignment: CENTER-LEFT;");
        roomStatusCol.setStyle("-fx-alignment: CENTER-LEFT;");
        customerNameCol.setStyle("-fx-alignment: CENTER-LEFT;");
        remainingDaysCol.setStyle("-fx-alignment: CENTER-LEFT;");
        roomPhoneNumberCol.setStyle("-fx-alignment: CENTER-LEFT;");
        roomOnFloorCol.setStyle("-fx-alignment: CENTER-LEFT;");
        roomCleanCol.setStyle("-fx-alignment: CENTER;");
        roomRepairedCol.setStyle("-fx-alignment: CENTER;");
        roomInProgressCol.setStyle("-fx-alignment: CENTER;");
        roomActionCol.setStyle("-fx-alignment: CENTER;");

        // Thêm cột vào bảng
        table_Rooms.getColumns().clear();
        table_Rooms.getColumns().addAll(numberCol, roomIDCol, roomTypeCol, roomStatusCol, customerNameCol, remainingDaysCol,
                roomPhoneNumberCol, roomOnFloorCol , roomCleanCol, roomRepairedCol, roomInProgressCol, roomActionCol);

        // Xét xắp xếp theo userName
        //userNameCol.setSortType(TableColumn.SortType.DESCENDING);
    }

    public void showRoomsData() {
        listRoomEXs = roomEXDAOImpl.getAllRoomEX();
        //table_Rooms.getItems().clear();
        table_Rooms.setItems(listRoomEXs);

        //Set filterData and Pagination
        filteredData = new FilteredList<>(listRoomEXs, list -> true);
        FXMLMainFormController mainFormController = ConnectControllers.getfXMLMainFormController();
        mainFormController.getTxt_Search().textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(
                    roomEX -> newValue == null || newValue.isEmpty()
                    || roomEX.getRoomID().toLowerCase().contains(newValue.toLowerCase())
                    || roomEX.getCustomerName().toLowerCase().contains(newValue.toLowerCase())
                    || roomEX.getCustomerName().toLowerCase().contains(newValue.toLowerCase())
                    || String.valueOf(roomEX.getDayRemaining()).contains(newValue.toLowerCase())
                    || String.valueOf(roomEX.getRoomOnFloor()).contains(newValue.toLowerCase())
                    || roomEX.getRoomType().toLowerCase().contains(newValue.toLowerCase()));
            pagination.setPageCount((int) (Math.ceil(filteredData.size() * 1.0 / ROWS_PER_PAGE)));
            changeTableView(pagination.getCurrentPageIndex(), ROWS_PER_PAGE);
        });

        int totalPage = (int) (Math.ceil(listRoomEXs.size() * 1.0 / ROWS_PER_PAGE));
        pagination.setPageCount(totalPage);
        pagination.setCurrentPageIndex(0);
        changeTableView(0, ROWS_PER_PAGE);
        pagination.currentPageIndexProperty().addListener(
                (observable, oldValue, newValue) -> changeTableView(newValue.intValue(), ROWS_PER_PAGE));
    }

    @FXML
    private void handle_MenuItem_Edit_Action(ActionEvent event) {
        check_Edit_Action = true;
        StageLoader stageLoader = new StageLoader();
        stageLoader.formLoader("/fxml/FXMLAddNewRoom.fxml", "/images/KAN Logo.png", "Edit Room Type Informations");

    }

    @FXML
    private void handle_MenuItem_Add_Action(ActionEvent event) {
        check_Edit_Action = false;
        StageLoader stageLoader = new StageLoader();
        stageLoader.formLoader("/fxml/FXMLAddNewRoom.fxml", "/images/KAN Logo.png", "Edit Room Type Informations");
    }

    @FXML
    private void handle_MenuItem_Delete_Action(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Comfirm");
        alert.setContentText("Do you want to delete this?");
        alert.showAndWait();
        System.out.println(alert.getResult());
        if (alert.getResult() == ButtonType.OK) {
            roomEXDAOImpl.deleteRoomEX(roomEXItem);
            DAO.setUserLogs_With_MAC(mainFormController.getUserRole().getEmployee_ID(), "Delete Room ID: " 
                            + FormatName.format(serviceTypeItem.getServiceID()), 
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), mainFormController.macAdress);
            System.out.println("Delete successful");
            showRoomsData();
        }
    }

    @FXML
    private void handle_MenuItem_Refresh_Action(ActionEvent event) {
        showRoomsData();
    }

}
