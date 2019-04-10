/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXCheckBox;
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
import models.RoleDAOImpl;
import models.RoomEX;
import models.ServiceOrder;
import models.ServiceOrderDAOImpl;
import models.boolDecentralizationModel;
import utils.FormatName;
import utils.StageLoader;

/**
 * FXML Controller class
 *
 * @author Doan Thanh Nhan
 */
public class FXMLListServiceOrderController implements Initializable {

    ObservableList<ServiceOrder> listServiceOrders = FXCollections.observableArrayList();
    ServiceOrderDAOImpl serviceOrderDAOImpl;
    RoleDAOImpl roleDAOImpl;
    public boolDecentralizationModel userRole;

    public Boolean check_Edit_Action;
    public boolean check_Add_Action = false;
    public boolean check_Add_New = false;
    public ServiceOrder serviceOrderItem;
    public String customer_Full_Name;
    public String new_SO_ID;
    public int row_Index;
    public int row_Add_New_Index;
    public int pagination_Index;
    

    private static final int ROWS_PER_PAGE = 20;
    private FilteredList<ServiceOrder> filteredData;

    private FXMLMainFormController mainFormController;
    private FXMLMainOverViewPaneController mainOverViewPaneController;
    private FXMLListServiceOrderDetailController listServiceOrderDetailController;

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
    private ContextMenu contextMenu_Main;
    @FXML
    private TableView<ServiceOrder> table_Service_Order;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("List Service Type initialize...");
        ConnectControllers.setfXMLListServiceOrderController(this);
        mainFormController = ConnectControllers.getfXMLMainFormController();
        mainOverViewPaneController = ConnectControllers.getfXMLMainOverViewPaneController();
        listServiceOrderDetailController = ConnectControllers.getfXMLListServiceOrderDetailController();
        serviceOrderDAOImpl = new ServiceOrderDAOImpl();
        roleDAOImpl = new RoleDAOImpl();
        check_Edit_Action = false;
        setColumns();
        if (!serviceOrderDAOImpl.getAllServiceOrders().isEmpty()) {
            showUsersData();
        } else {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error");
                alert.setContentText("Don't have any Service Type in Database or Can't connect to Database");
                alert.show();
            });
        }

        // Check item when click on table
        table_Service_Order.setOnMouseClicked((MouseEvent event) -> {
            if ((event.getButton().equals(MouseButton.PRIMARY) || event.getButton().equals(MouseButton.SECONDARY))
                    && table_Service_Order.getSelectionModel().getSelectedItem() != null) {
                menuItem_Edit.setDisable(false);
                menuItem_Delete.setDisable(false);
                System.out.println(table_Service_Order.getSelectionModel().getSelectedItem().getServiceOrderID());
                serviceOrderItem = table_Service_Order.getSelectionModel().getSelectedItem();
                customer_Full_Name = table_Service_Order.getSelectionModel().getSelectedItem().getCustomerFullName();
                
                row_Index = table_Service_Order.getSelectionModel().getSelectedIndex();
                pagination_Index = pagination.getCurrentPageIndex();
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
        if (!userRole.ischeckSType_Add()) {
            contextMenu_Main.getItems().remove(menuItem_Add);
        }
        if (!userRole.ischeckSType_Delete()) {
            contextMenu_Main.getItems().remove(menuItem_Delete);
        }
        if (!userRole.ischeckSType_Edit()) {
            contextMenu_Main.getItems().remove(menuItem_Edit);
        }
        if (!userRole.ischeckSType_View()) {
//            contextMenu_Main.getItems().remove(menuItem_List_Service_Type);
        }
    }

    public Boolean getCheck_Edit_Action() {
        return check_Edit_Action;
    }

    public void setCheck_Edit_Action(Boolean check_Edit_Action) {
        this.check_Edit_Action = check_Edit_Action;
    }

    private void changeTableView(int index, int limit) {

        int fromIndex = index * limit;
        int toIndex = Math.min(fromIndex + limit, listServiceOrders.size());

        int minIndex = Math.min(toIndex, filteredData.size());

        SortedList<ServiceOrder> sortedData = new SortedList<>(
                FXCollections.observableArrayList(filteredData.subList(Math.min(fromIndex, minIndex), minIndex)));

        sortedData.comparatorProperty().bind(table_Service_Order.comparatorProperty());

        table_Service_Order.setItems(sortedData);

    }

    private void setColumns() {
        TableColumn<ServiceOrder, String> orderIDCol = new TableColumn<>("Order ID");
        TableColumn<ServiceOrder, String> customerIDCol = new TableColumn<>("Customer ID");
        TableColumn<ServiceOrder, String> customerFullNameCol = new TableColumn<>("Customer Name");
        TableColumn<ServiceOrder, String> roomIDCol = new TableColumn<>("Room ID");
        TableColumn<ServiceOrder, String> userNameCol = new TableColumn<>("User name");
        TableColumn<ServiceOrder, LocalDateTime> orderDateCol = new TableColumn<>("Order date");
        TableColumn<ServiceOrder, String> orderNoteCol = new TableColumn<>("Order note");
        TableColumn<ServiceOrder, JFXCheckBox> checkBoxFinishCol = new TableColumn<>("Finish");

        TableColumn numberCol = new TableColumn("#");
        numberCol.setCellValueFactory(new Callback<CellDataFeatures<ServiceOrder, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<ServiceOrder, String> p) {
                return new ReadOnlyObjectWrapper((table_Service_Order.getItems().indexOf(p.getValue()) + 1) + "");
            }
        });
        numberCol.setSortable(false);

        // Định nghĩa cách để lấy dữ liệu cho mỗi ô.
        // Lấy giá trị từ các thuộc tính của ServiceOrder.
        orderIDCol.setCellValueFactory(new PropertyValueFactory<>("serviceOrderID"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerFullNameCol.setCellValueFactory(new PropertyValueFactory<>("customerFullName"));
        roomIDCol.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        orderDateCol.setCellValueFactory(new PropertyValueFactory<>("serviceOrderTime"));
        orderNoteCol.setCellValueFactory(new PropertyValueFactory<>("serviceNote"));
        checkBoxFinishCol.setCellValueFactory(new PropertyValueFactory<>("checkBox_Finish"));

        numberCol.setStyle("-fx-alignment: CENTER-LEFT;");
        orderIDCol.setStyle("-fx-alignment: CENTER-LEFT;");
        customerIDCol.setStyle("-fx-alignment: CENTER-LEFT;");
        customerFullNameCol.setStyle("-fx-alignment: CENTER-LEFT;");
        roomIDCol.setStyle("-fx-alignment: CENTER-LEFT;");
        userNameCol.setStyle("-fx-alignment: CENTER-LEFT;");
        orderDateCol.setStyle("-fx-alignment: CENTER-LEFT;");
        orderNoteCol.setStyle("-fx-alignment: CENTER-LEFT;");
        checkBoxFinishCol.setStyle("-fx-alignment: CENTER;");

        // Thêm cột vào bảng
        table_Service_Order.getColumns().clear();
        table_Service_Order.getColumns().addAll(numberCol, orderIDCol, customerIDCol, customerFullNameCol, roomIDCol,
                userNameCol, orderDateCol, orderNoteCol, checkBoxFinishCol);

        // Xét xắp xếp theo userName
        //userNameCol.setSortType(TableColumn.SortType.DESCENDING);
    }

    public void showUsersData() {
        listServiceOrders = serviceOrderDAOImpl.getAllServiceOrders();
        //table_Service_Order.getItems().clear();
        table_Service_Order.setItems(listServiceOrders);

        //Set filterData and Pagination
        filteredData = new FilteredList<>(listServiceOrders, list -> true);
        FXMLMainFormController mainFormController = ConnectControllers.getfXMLMainFormController();
        mainFormController.getTxt_Search().textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(
                    serviceOrder -> newValue == null || newValue.isEmpty()
                    || serviceOrder.getServiceOrderID().toLowerCase().contains(newValue.toLowerCase())
                    || serviceOrder.getCustomerID().toLowerCase().contains(newValue.toLowerCase())
                    || serviceOrder.getCustomerFullName().toLowerCase().contains(newValue.toLowerCase())
                    || serviceOrder.getRoomID().toLowerCase().contains(newValue.toLowerCase())
                    //|| serviceOrder.getServiceNote().toLowerCase().contains(newValue.toLowerCase())
                    || serviceOrder.getServiceOrderTime().toString().contains(newValue.toLowerCase())
                    || String.valueOf(serviceOrder.getServiceQuantity()).contains(newValue.toLowerCase())
                    || serviceOrder.getUserName().toLowerCase().contains(newValue.toLowerCase()));
            pagination.setPageCount((int) (Math.ceil(filteredData.size() * 1.0 / ROWS_PER_PAGE)));
            changeTableView(pagination.getCurrentPageIndex(), ROWS_PER_PAGE);
        });

        int totalPage = (int) (Math.ceil(listServiceOrders.size() * 1.0 / ROWS_PER_PAGE));
        pagination.setPageCount(totalPage);
        pagination.setCurrentPageIndex(0);
        changeTableView(0, ROWS_PER_PAGE);
        pagination.currentPageIndexProperty().addListener(
                (observable, oldValue, newValue) -> changeTableView(newValue.intValue(), ROWS_PER_PAGE));
        
        //Focus to the new item or editing item
        if (check_Add_New) {
            //Setting new SO index
            for (ServiceOrder item : listServiceOrders) {
                if (new_SO_ID.equals(item.getServiceOrderID())) {
                    row_Add_New_Index = listServiceOrders.indexOf(item);
                    break;
                }
            }
            Platform.runLater(() -> {
                int focusPage = (int) row_Add_New_Index / ROWS_PER_PAGE;
                int new_index = row_Add_New_Index % ROWS_PER_PAGE;
                pagination.setCurrentPageIndex(focusPage);
                changeTableView(focusPage, ROWS_PER_PAGE);
                table_Service_Order.requestFocus();
                table_Service_Order.getSelectionModel().select(new_index);
                table_Service_Order.getFocusModel().focus(new_index);
            });
            check_Add_New = false;
        } else {
            //Forcus to the editing row
            Platform.runLater(() -> {
                //int focusPage = (int) row_index / ROWS_PER_PAGE;
                //int new_index = row_index % ROWS_PER_PAGE;
                pagination.setCurrentPageIndex(pagination_Index);
                changeTableView(pagination_Index, ROWS_PER_PAGE);
                table_Service_Order.requestFocus();
                table_Service_Order.getSelectionModel().select(row_Index);
                table_Service_Order.getFocusModel().focus(row_Index);
            });
        }
    }

    @FXML
    private void handle_MenuItem_Edit_Action(ActionEvent event) {        
        if (serviceOrderItem.isServiceCheckOut()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Message");
            alert.setHeaderText("Warning");
            alert.setContentText("This service had checked out so can not allow editing!");
            alert.show();
        } else {
            this.setCheck_Edit_Action(true);
            check_Add_Action = false;
            if (mainOverViewPaneController != null) {
                mainOverViewPaneController.check_Services_Button_Clicked = false;
            }
            if (listServiceOrderDetailController != null) {
                listServiceOrderDetailController.check_Edit_Action = false;
            }
            System.out.println("Edit clicked and check = " + getCheck_Edit_Action() + " adress: " + getCheck_Edit_Action().hashCode());
            StageLoader stageLoader = new StageLoader();
            stageLoader.formLoader("/fxml/FXMLAddNewServiceOrder.fxml", "/images/KAN Logo.png", "Edit Service order Informations - Customer name: " + customer_Full_Name);
        }
    }

    @FXML
    private void handle_MenuItem_Add_Action(ActionEvent event) {
        this.setCheck_Edit_Action(false);
        check_Add_Action = true;
        StageLoader stageLoader = new StageLoader();
        stageLoader.formLoader("/fxml/FXMLAddNewServiceOrder.fxml", "/images/KAN Logo.png", "Add new Service order Informations");
    }

    @FXML
    private void handle_MenuItem_Delete_Action(ActionEvent event) {
        if (serviceOrderItem.isServiceCheckOut()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setContentText("This order was checked out so can't allow deleting!");
            alert.show();
        } else if (serviceOrderDAOImpl.check_For_Delete_Order(serviceOrderItem.getServiceOrderID())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setContentText("Must remove all items of this order before deleting!");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Comfirm");
            alert.setContentText("Do you want to delete this?");
            alert.showAndWait();
            System.out.println(alert.getResult());
            if (alert.getResult() == ButtonType.OK) {
                serviceOrderDAOImpl.deleteServiceOrder(serviceOrderItem);
                DAO.setUserLogs_With_MAC(mainFormController.getUserRole().getEmployee_ID(), "Delete ServiceOrder ID: "
                        + FormatName.format(serviceOrderItem.getServiceOrderID()),
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), mainFormController.macAdress);
                System.out.println("Delete successful");
                showUsersData();
            }
        }
    }

    @FXML
    private void handle_MenuItem_Refresh_Action(ActionEvent event) {
        showUsersData();
    }

}
