/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

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
import models.CheckOut;
import models.CheckOutDAOImpl;
import models.boolDecentralizationModel;
import utils.FormatName;
import utils.StageLoader;

/**
 * FXML Controller class
 *
 * @author Doan Thanh Nhan
 */
public class FXMLListCheckOutController implements Initializable {

    ObservableList<CheckOut> listCheckOuts = FXCollections.observableArrayList();
    CheckOutDAOImpl checkOutDAOImpl;
    RoleDAOImpl roleDAOImpl;
    public boolDecentralizationModel userRole;

    public Boolean check_Edit_Action;
    public CheckOut checkOutItem;

    private static final int ROWS_PER_PAGE = 20;
    private FilteredList<CheckOut> filteredData;

    private FXMLMainFormController mainFormController;

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
    private TableView<CheckOut> table_Check_Out;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("List Service Type initialize...");
        ConnectControllers.setfXMLListCheckOutController(this);
        mainFormController = ConnectControllers.getfXMLMainFormController();
        checkOutDAOImpl = new CheckOutDAOImpl();
        roleDAOImpl = new RoleDAOImpl();
        check_Edit_Action = false;
        setColumns();
        if (!checkOutDAOImpl.getAllCheckOut().isEmpty()) {
            showUsersData();
        } else {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error");
                alert.setContentText("Don't have any Check out in Database or Can't connect to Database");
                alert.show();
            });
        }

        // Check item when click on table
        table_Check_Out.setOnMouseClicked((MouseEvent event) -> {
            if ((event.getButton().equals(MouseButton.PRIMARY) || event.getButton().equals(MouseButton.SECONDARY))
                    && table_Check_Out.getSelectionModel().getSelectedItem() != null) {
                menuItem_Edit.setDisable(false);
                menuItem_Delete.setDisable(false);
                System.out.println(table_Check_Out.getSelectionModel().getSelectedItem().getCheckOutID());
                checkOutItem = table_Check_Out.getSelectionModel().getSelectedItem();
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
        if (!userRole.ischeckCheckOut_Add()) {
            contextMenu_Main.getItems().remove(menuItem_Add);
        }
        if (!userRole.ischeckCheckOut_Delete()) {
            contextMenu_Main.getItems().remove(menuItem_Delete);
        }
        if (!userRole.ischeckCheckOut_Edit()) {
            contextMenu_Main.getItems().remove(menuItem_Edit);
        }
        if (!userRole.ischeckCheckOut_View()) {
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
        int toIndex = Math.min(fromIndex + limit, listCheckOuts.size());

        int minIndex = Math.min(toIndex, filteredData.size());

        SortedList<CheckOut> sortedData = new SortedList<>(
                FXCollections.observableArrayList(filteredData.subList(Math.min(fromIndex, minIndex), minIndex)));

        sortedData.comparatorProperty().bind(table_Check_Out.comparatorProperty());

        table_Check_Out.setItems(sortedData);

    }

    private void setColumns() {
        TableColumn<CheckOut, String> checkOutIDCol = new TableColumn<>("Check out ID");
        TableColumn<CheckOut, String> checkInIDCol = new TableColumn<>("Check in ID");
        TableColumn<CheckOut, String> customerIDCol = new TableColumn<>("Customer ID");
        TableColumn<CheckOut, String> roomIDCol = new TableColumn<>("Room ID");
        TableColumn<CheckOut, String> userNameCol = new TableColumn<>("User name");
        TableColumn<CheckOut, LocalDateTime> checkInDateCol = new TableColumn<>("Check in date");
        TableColumn<CheckOut, LocalDateTime> checkOutDateCol = new TableColumn<>("Check out date");
        TableColumn<CheckOut, String> customerPaymentCol = new TableColumn<>("Customer payment");
        TableColumn<CheckOut, BigDecimal> customerBillCol = new TableColumn<>("Bill");
        TableColumn<CheckOut, BigDecimal> customerDiscountCol = new TableColumn<>("Discount");
        TableColumn<CheckOut, BigDecimal> customerTaxCol = new TableColumn<>("Tax");

        TableColumn numberCol = new TableColumn("#");
        numberCol.setCellValueFactory(new Callback<CellDataFeatures<CheckOut, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<CheckOut, String> p) {
                return new ReadOnlyObjectWrapper((table_Check_Out.getItems().indexOf(p.getValue()) + 1) + "");
            }
        });
        numberCol.setSortable(false);

        // Định nghĩa cách để lấy dữ liệu cho mỗi ô.
        // Lấy giá trị từ các thuộc tính của CheckOut.
        checkOutIDCol.setCellValueFactory(new PropertyValueFactory<>("checkOutID"));
        checkInIDCol.setCellValueFactory(new PropertyValueFactory<>("checkInID"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        roomIDCol.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        checkInDateCol.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        checkOutDateCol.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        customerPaymentCol.setCellValueFactory(new PropertyValueFactory<>("customerPayment"));
        customerBillCol.setCellValueFactory(new PropertyValueFactory<>("customerBill"));
        customerDiscountCol.setCellValueFactory(new PropertyValueFactory<>("discount"));
        customerTaxCol.setCellValueFactory(new PropertyValueFactory<>("tax"));

        numberCol.setStyle("-fx-alignment: CENTER-LEFT;");
        checkOutIDCol.setStyle("-fx-alignment: CENTER-LEFT;");
        checkInIDCol.setStyle("-fx-alignment: CENTER-LEFT;");
        customerIDCol.setStyle("-fx-alignment: CENTER-LEFT;");
        roomIDCol.setStyle("-fx-alignment: CENTER-LEFT;");
        userNameCol.setStyle("-fx-alignment: CENTER-LEFT;");
        checkInDateCol.setStyle("-fx-alignment: CENTER-LEFT;");
        checkOutDateCol.setStyle("-fx-alignment: CENTER-LEFT;");
        customerPaymentCol.setStyle("-fx-alignment: CENTER-LEFT;");
        customerBillCol.setStyle("-fx-alignment: CENTER-LEFT;");
        customerDiscountCol.setStyle("-fx-alignment: CENTER-LEFT;");
        customerTaxCol.setStyle("-fx-alignment: CENTER-LEFT;");

        // Thêm cột vào bảng
        table_Check_Out.getColumns().clear();
        table_Check_Out.getColumns().addAll(numberCol, checkOutIDCol, checkInIDCol, customerIDCol, roomIDCol,
                userNameCol, checkInDateCol, checkOutDateCol, customerPaymentCol,customerBillCol,
                customerDiscountCol, customerTaxCol);

        // Xét xắp xếp theo userName
        //userNameCol.setSortType(TableColumn.SortType.DESCENDING);
    }

    public void showUsersData() {
        listCheckOuts = checkOutDAOImpl.getAllCheckOut();
        //table_Check_Out.getItems().clear();
        table_Check_Out.setItems(listCheckOuts);

        //Set filterData and Pagination
        filteredData = new FilteredList<>(listCheckOuts, list -> true);
        FXMLMainFormController mainFormController = ConnectControllers.getfXMLMainFormController();
        mainFormController.getTxt_Search().textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(
                    checkOut -> newValue == null || newValue.isEmpty()
                    || checkOut.getCheckOutID().toLowerCase().contains(newValue.toLowerCase())
                    || checkOut.getCustomerID().toLowerCase().contains(newValue.toLowerCase())
                    || checkOut.getRoomID().toLowerCase().contains(newValue.toLowerCase())
                    //|| checkOut.getServiceNote().toLowerCase().contains(newValue.toLowerCase())
                    || checkOut.getCheckOutDate().toString().contains(newValue.toLowerCase())
                    || String.valueOf(checkOut.getDiscount()).contains(newValue.toLowerCase())
                    || checkOut.getUserName().toLowerCase().contains(newValue.toLowerCase()));
            pagination.setPageCount((int) (Math.ceil(filteredData.size() * 1.0 / ROWS_PER_PAGE)));
            changeTableView(pagination.getCurrentPageIndex(), ROWS_PER_PAGE);
        });

        int totalPage = (int) (Math.ceil(listCheckOuts.size() * 1.0 / ROWS_PER_PAGE));
        pagination.setPageCount(totalPage);
        pagination.setCurrentPageIndex(0);
        changeTableView(0, ROWS_PER_PAGE);
        pagination.currentPageIndexProperty().addListener(
                (observable, oldValue, newValue) -> changeTableView(newValue.intValue(), ROWS_PER_PAGE));
    }

    @FXML
    private void handle_MenuItem_Edit_Action(ActionEvent event) {
        this.setCheck_Edit_Action(true);
        System.out.println("Edit clicked and check = " + getCheck_Edit_Action() + " adress: " + getCheck_Edit_Action().hashCode());
        StageLoader stageLoader = new StageLoader();
        stageLoader.formLoader("/fxml/FXMLCheckOut.fxml", "/images/KAN Logo.png", "Edit Check Out Informations");

    }

    @FXML
    private void handle_MenuItem_Add_Action(ActionEvent event) {
        this.setCheck_Edit_Action(false);
        StageLoader stageLoader = new StageLoader();
        stageLoader.formLoader("/fxml/FXMLCheckOut.fxml", "/images/KAN Logo.png", "Add Check Out Informations");
    }

    @FXML
    private void handle_MenuItem_Delete_Action(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Comfirm");
        alert.setContentText("Do you want to delete this?");
        alert.showAndWait();
        System.out.println(alert.getResult());
        if (alert.getResult() == ButtonType.OK) {
            checkOutDAOImpl.deleteCheckOut(checkOutItem);
            DAO.setUserLogs_With_MAC(mainFormController.getUserRole().getEmployee_ID(), "Delete CheckOut ID: "
                    + FormatName.format(checkOutItem.getCheckOutID()),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), mainFormController.macAdress);
            System.out.println("Delete successful");
            showUsersData();
        }
    }

    @FXML
    private void handle_MenuItem_Refresh_Action(ActionEvent event) {
        showUsersData();
    }

}
