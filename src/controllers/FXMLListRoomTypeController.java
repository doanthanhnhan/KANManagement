/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.FXMLListServiceTypeController.serviceTypeItem;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
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
import models.RoomType;
import models.RoomDAOImpl;
import models.RoomType;
import models.RoomTypeDAOImpl;
import utils.FormatName;
import utils.StageLoader;

/**
 * FXML Controller class
 *
 * @author Doan Thanh Nhan
 */
public class FXMLListRoomTypeController implements Initializable {

    ObservableList<RoomType> listRoomTypes = FXCollections.observableArrayList();
    RoomTypeDAOImpl roomTypeDAOImpl = new RoomTypeDAOImpl();

    public Boolean check_Edit_Action = false;
    public boolean check_Add_Action = false;
    public boolean check_Add_New = false;
    public RoomType roomTypeItem;
    public int row_index;
    public int row_add_new_index;
    public int pagination_index;
    public String new_Room_ID;

    private static final int ROWS_PER_PAGE = 20;
    private FilteredList<RoomType> filteredData;
    private FXMLMainFormController mainFormController;
    private FXMLRoomTypeController roomTypeController;

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
    private TableView<RoomType> table_Room_Type;
    @FXML
    private ContextMenu main_Context_Menu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("List Rooms initialize...");
        ConnectControllers.setfXMLListRoomTypeController(this);
        mainFormController = ConnectControllers.getfXMLMainFormController();
        roomTypeController = ConnectControllers.getfXMLRoomTypeController();

        listRoomTypes = roomTypeDAOImpl.getAllRoomType();
        if (listRoomTypes.isEmpty()) {
            roomTypeDAOImpl.addInitRoomType();
            setColumns();
            showRoomsData();
        } else {
            setColumns();
            showRoomsData();
        }
        // Check item when click on table
        table_Room_Type.setOnMouseClicked((MouseEvent event) -> {
            if ((event.getButton().equals(MouseButton.PRIMARY) || event.getButton().equals(MouseButton.SECONDARY))
                    && table_Room_Type.getSelectionModel().getSelectedItem() != null) {
                menuItem_Edit.setDisable(false);
                menuItem_Delete.setDisable(false);
                System.out.println(table_Room_Type.getSelectionModel().getSelectedItem().getType());
                roomTypeItem = table_Room_Type.getSelectionModel().getSelectedItem();
                row_index = table_Room_Type.getSelectionModel().getSelectedIndex();
                pagination_index = pagination.getCurrentPageIndex();
            } else {
                menuItem_Edit.setDisable(true);
                menuItem_Delete.setDisable(true);
            }
        });
        
        //Don't allow add and delete due to program logic
        main_Context_Menu.getItems().remove(menuItem_Add);
        main_Context_Menu.getItems().remove(menuItem_Delete);
    }

    private void changeTableView(int index, int limit) {

        int fromIndex = index * limit;
        int toIndex = Math.min(fromIndex + limit, listRoomTypes.size());

        int minIndex = Math.min(toIndex, filteredData.size());

        SortedList<RoomType> sortedData = new SortedList<>(
                FXCollections.observableArrayList(filteredData.subList(Math.min(fromIndex, minIndex), minIndex)));

        sortedData.comparatorProperty().bind(table_Room_Type.comparatorProperty());

        table_Room_Type.setItems(sortedData);

    }

    private void setColumns() {
        TableColumn<RoomType, String> roomTypeCol = new TableColumn<>("Room type");
        TableColumn<RoomType, BigDecimal> roomPriceCol = new TableColumn<>("Room price");
        TableColumn<RoomType, BigDecimal> roomDiscountCol = new TableColumn<>("Room discount");

        TableColumn numberCol = new TableColumn("#");
        numberCol.setCellValueFactory(new Callback<CellDataFeatures<RoomType, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(
                    CellDataFeatures<RoomType, String> p) {
                return new ReadOnlyObjectWrapper((table_Room_Type.getItems().indexOf(p.getValue()) + 1) + "");
            }
        });
        numberCol.setSortable(false);

        // Định nghĩa cách để lấy dữ liệu cho mỗi ô.
        // Lấy giá trị từ các thuộc tính của RoomType.        
        roomTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        roomPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        roomDiscountCol.setCellValueFactory(new PropertyValueFactory<>("discount"));

        numberCol.setStyle("-fx-alignment: CENTER-LEFT;");
        roomTypeCol.setStyle("-fx-alignment: CENTER-LEFT;");
        roomPriceCol.setStyle("-fx-alignment: CENTER-LEFT;");
        roomDiscountCol.setStyle("-fx-alignment: CENTER-LEFT;");

        // Thêm cột vào bảng
        table_Room_Type.getColumns().clear();
        table_Room_Type.getColumns().addAll(numberCol, roomTypeCol, roomPriceCol, roomDiscountCol);

        // Xét xắp xếp theo userName
        //userNameCol.setSortType(TableColumn.SortType.DESCENDING);
    }

    public void showRoomsData() {
        listRoomTypes = roomTypeDAOImpl.getAllRoomType();
        //table_Room_Type.getItems().clear();
        table_Room_Type.setItems(listRoomTypes);

        //Set filterData and Pagination
        filteredData = new FilteredList<>(listRoomTypes, list -> true);
        FXMLMainFormController mainFormController = ConnectControllers.getfXMLMainFormController();
        mainFormController.getTxt_Search().textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(
                    roomType -> newValue == null || newValue.isEmpty()
                    || roomType.getType().toLowerCase().contains(newValue.toLowerCase())
                    || String.valueOf(roomType.getPrice()).contains(newValue.toLowerCase())
                    || String.valueOf(roomType.getDiscount()).contains(newValue.toLowerCase())
            );
            pagination.setPageCount((int) (Math.ceil(filteredData.size() * 1.0 / ROWS_PER_PAGE)));
            changeTableView(pagination.getCurrentPageIndex(), ROWS_PER_PAGE);
        });

        int totalPage = (int) (Math.ceil(listRoomTypes.size() * 1.0 / ROWS_PER_PAGE));
        pagination.setPageCount(totalPage);
        pagination.setCurrentPageIndex(0);
        changeTableView(0, ROWS_PER_PAGE);
        pagination.currentPageIndexProperty().addListener(
                (observable, oldValue, newValue) -> changeTableView(newValue.intValue(), ROWS_PER_PAGE));

        if (check_Add_New) {
            //Setting new room index
            for (RoomType item : listRoomTypes) {
                if (new_Room_ID.equals(item.getType())) {
                    row_add_new_index = listRoomTypes.indexOf(item);
                    break;
                }
            }
            Platform.runLater(() -> {
                int focusPage = (int) row_add_new_index / ROWS_PER_PAGE;
                int new_index = row_add_new_index % ROWS_PER_PAGE;
                pagination.setCurrentPageIndex(focusPage);
                changeTableView(focusPage, ROWS_PER_PAGE);
                table_Room_Type.requestFocus();
                table_Room_Type.getSelectionModel().select(new_index);
                table_Room_Type.getFocusModel().focus(new_index);
            });
            check_Add_New = false;
        } else {
            //Forcus to the editing row
            Platform.runLater(() -> {
                //int focusPage = (int) row_index / ROWS_PER_PAGE;
                //int new_index = row_index % ROWS_PER_PAGE;
                pagination.setCurrentPageIndex(pagination_index);
                changeTableView(pagination_index, ROWS_PER_PAGE);
                table_Room_Type.requestFocus();
                table_Room_Type.getSelectionModel().select(row_index);
                table_Room_Type.getFocusModel().focus(row_index);
            });
        }
    }

    @FXML
    private void handle_MenuItem_Edit_Action(ActionEvent event) {
        check_Edit_Action = true;
        check_Add_Action = false;
        StageLoader stageLoader = new StageLoader();
        stageLoader.formLoader("/fxml/FXMLRoomType.fxml", "/images/KAN Logo.png", "Edit Room Type Informations");

    }

    @FXML
    private void handle_MenuItem_Add_Action(ActionEvent event) {
        check_Edit_Action = false;
        check_Add_Action = true;
        StageLoader stageLoader = new StageLoader();
        stageLoader.formLoader("/fxml/FXMLRoomType.fxml", "/images/KAN Logo.png", "Add new Room Type Informations");
    }

    @FXML
    private void handle_MenuItem_Delete_Action(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Comfirm");
        alert.setContentText("Do you want to delete this?");
        alert.showAndWait();
        System.out.println(alert.getResult());
        if (alert.getResult() == ButtonType.OK) {
            roomTypeDAOImpl.deleteRoomType(roomTypeItem);
            DAO.setUserLogs_With_MAC(mainFormController.getUserRole().getEmployee_ID(), "Delete Room Type: "
                    + roomTypeItem.getType(),
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
