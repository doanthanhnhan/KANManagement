/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import models.RoomAction;
import models.RoomEX;
import models.RoomDAOImpl;
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

    private static final int ROWS_PER_PAGE = 4;
    private FilteredList<RoomEX> filteredData;

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
        setColumns();
        showUsersData();
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
        TableColumn<RoomEX, String> customerNameCol = new TableColumn<>("Customer name");
        TableColumn<RoomEX, CheckBox> roomCleanCol = new TableColumn<>("Room clean");
        TableColumn<RoomEX, RoomAction> roomActionCol = new TableColumn<>("Room action");

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
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        roomCleanCol.setCellValueFactory(new PropertyValueFactory<>("checkBox_Room_Clean"));
        roomActionCol.setCellValueFactory(new PropertyValueFactory<>("roomAction"));

        numberCol.setStyle("-fx-alignment: CENTER-LEFT;");
        roomIDCol.setStyle("-fx-alignment: CENTER-LEFT;");
        roomTypeCol.setStyle("-fx-alignment: CENTER-LEFT;");
        customerNameCol.setStyle("-fx-alignment: CENTER-LEFT;");
        roomCleanCol.setStyle("-fx-alignment: CENTER-LEFT;");
        roomActionCol.setStyle("-fx-alignment: CENTER-LEFT;");

        // Thêm cột vào bảng
        table_Rooms.getColumns().clear();
        table_Rooms.getColumns().addAll(numberCol, roomIDCol, roomTypeCol, customerNameCol, roomCleanCol, roomActionCol);

        // Xét xắp xếp theo userName
        //userNameCol.setSortType(TableColumn.SortType.DESCENDING);
    }

    public void showUsersData() {
        listRoomEXs = roomEXDAOImpl.getAllRoomEX();
        //table_Rooms.getItems().clear();
        table_Rooms.setItems(listRoomEXs);

        //Set filterData and Pagination
//        filteredData = new FilteredList<>(listRoomEXs, list -> true);
//        FXMLMainFormController mainFormController = ConnectControllers.getfXMLMainFormController();
//        mainFormController.getTxt_Search().textProperty().addListener((observable, oldValue, newValue) -> {
//            filteredData.setPredicate(
//                    roomEX -> newValue == null || newValue.isEmpty()
//                    || roomEX.getRoomID().toLowerCase().contains(newValue.toLowerCase()));
//                    //|| roomEX.getRoomDescription().toLowerCase().contains(newValue.toLowerCase())
//                    //|| roomEX.getRoomUnit().toLowerCase().contains(newValue.toLowerCase())
//                    //|| roomEX.getRoomPrice().toString().contains(newValue.toLowerCase())
//                    //|| roomEX.getRoomName().toLowerCase().contains(newValue.toLowerCase()));
//            pagination.setPageCount((int) (Math.ceil(filteredData.size() * 1.0 / ROWS_PER_PAGE)));
//            changeTableView(pagination.getCurrentPageIndex(), ROWS_PER_PAGE);
//        });
//
//        int totalPage = (int) (Math.ceil(listRoomEXs.size() * 1.0 / ROWS_PER_PAGE));
//        pagination.setPageCount(totalPage);
//        pagination.setCurrentPageIndex(0);
//        changeTableView(0, ROWS_PER_PAGE);
//        pagination.currentPageIndexProperty().addListener(
//                (observable, oldValue, newValue) -> changeTableView(newValue.intValue(), ROWS_PER_PAGE));
    }

    @FXML
    private void handle_MenuItem_Edit_Action(ActionEvent event) {
        check_Edit_Action = true;
        StageLoader stageLoader = new StageLoader();
        stageLoader.formLoader("/fxml/FXMLAddNewRoomEX.fxml", "/images/KAN Logo.png", "Edit Room Type Informations");

    }

    @FXML
    private void handle_MenuItem_Add_Action(ActionEvent event) {
        check_Edit_Action = false;
        StageLoader stageLoader = new StageLoader();
        stageLoader.formLoader("/fxml/FXMLAddNewRoomEX.fxml", "/images/KAN Logo.png", "Edit Room Type Informations");
    }

    @FXML
    private void handle_MenuItem_Delete_Action(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Comfirm");
        alert.setContentText("Do you want to delete this?");
        alert.showAndWait();
        System.out.println(alert.getResult());
        if (alert.getResult() == ButtonType.OK) {
            //roomEXDAOImpl.deleteRoomEX(roomEXItem);
            System.out.println("Delete successful");
            showUsersData();
        }
    }

    @FXML
    private void handle_MenuItem_Refresh_Action(ActionEvent event) {
        showUsersData();
    }

}
