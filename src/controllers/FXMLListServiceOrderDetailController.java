/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXCheckBox;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import models.DAO;
import models.RoleDAOImpl;
import models.ServiceOrderDetail;
import models.ServiceOrderDetailDAOImpl;
import models.boolDecentralizationModel;
import utils.FormatName;
import utils.StageLoader;

/**
 * FXML Controller class
 *
 * @author Doan Thanh Nhan
 */
public class FXMLListServiceOrderDetailController implements Initializable {

    ObservableList<ServiceOrderDetail> listServiceOrderDetails = FXCollections.observableArrayList();
    ServiceOrderDetailDAOImpl serviceOrderDetailDAOImpl;
    RoleDAOImpl roleDAOImpl;
    public boolDecentralizationModel userRole;

    public Boolean check_Edit_Action;
    public ServiceOrderDetail serviceOrderDetailItem;

    private static final int ROWS_PER_PAGE = 20;
    private FilteredList<ServiceOrderDetail> filteredData;

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
    private TableView<ServiceOrderDetail> table_Service_Order_Detail;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("List Service order details initialize...");
        ConnectControllers.setfXMLListServiceOrderDetailController(this);
        mainFormController = ConnectControllers.getfXMLMainFormController();
        serviceOrderDetailDAOImpl = new ServiceOrderDetailDAOImpl();
        roleDAOImpl = new RoleDAOImpl();
        check_Edit_Action = false;
        setColumns();
        if (!serviceOrderDetailDAOImpl.getAllServiceOrdersDetails().isEmpty()) {
            showUsersData();
        } else {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText("Error");
                alert.setContentText("Don't have any Service order details in Database or Can't connect to Database");
                alert.show();
            });
        }

        // Check item when click on table
        table_Service_Order_Detail.setOnMouseClicked((MouseEvent event) -> {
            if ((event.getButton().equals(MouseButton.PRIMARY) || event.getButton().equals(MouseButton.SECONDARY))
                    && table_Service_Order_Detail.getSelectionModel().getSelectedItem() != null) {
                menuItem_Edit.setDisable(false);
                menuItem_Delete.setDisable(false);
                System.out.println(table_Service_Order_Detail.getSelectionModel().getSelectedItem().getServiceID());
                serviceOrderDetailItem = table_Service_Order_Detail.getSelectionModel().getSelectedItem();
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
        int toIndex = Math.min(fromIndex + limit, listServiceOrderDetails.size());

        int minIndex = Math.min(toIndex, filteredData.size());

        SortedList<ServiceOrderDetail> sortedData = new SortedList<>(
                FXCollections.observableArrayList(filteredData.subList(Math.min(fromIndex, minIndex), minIndex)));

        sortedData.comparatorProperty().bind(table_Service_Order_Detail.comparatorProperty());

        table_Service_Order_Detail.setItems(sortedData);

    }

    private void setColumns() {
        TableColumn<ServiceOrderDetail, String> orderIDCol = new TableColumn<>("Order ID");
        TableColumn<ServiceOrderDetail, String> customerIDCol = new TableColumn<>("Customer ID");
        TableColumn<ServiceOrderDetail, String> roomIDCol = new TableColumn<>("Room ID");
        TableColumn<ServiceOrderDetail, String> serviceIDCol = new TableColumn<>("Service ID");
        TableColumn<ServiceOrderDetail, String> userNameCol = new TableColumn<>("User name");
        TableColumn<ServiceOrderDetail, String> serviceNameCol = new TableColumn<>("Service name");
        TableColumn<ServiceOrderDetail, String> serviceUnitCol = new TableColumn<>("Service unit");
        TableColumn<ServiceOrderDetail, BigDecimal> servicePriceCol = new TableColumn<>("Service price");
        TableColumn<ServiceOrderDetail, Integer> serviceInventoryCol = new TableColumn<>("Inventory");
        TableColumn<ServiceOrderDetail, Integer> serviceQuantityCol = new TableColumn<>("Order quantity");
        TableColumn<ServiceOrderDetail, LocalDateTime> serviceOrderDateCol = new TableColumn<>("Order Date");
        TableColumn<ServiceOrderDetail, BigDecimal> serviceTotalPriceCol = new TableColumn<>("Total");
        TableColumn<ServiceOrderDetail, BigDecimal> serviceDiscountCol = new TableColumn<>("Discount");
        TableColumn<ServiceOrderDetail, ImageView> serviceImageCol = new TableColumn<>("Service image");
        TableColumn<ServiceOrderDetail, String> serviceDescriptionCol = new TableColumn<>("Service description");
        TableColumn<ServiceOrderDetail, JFXCheckBox> checkBoxFinishCol = new TableColumn<>("Finish");

        TableColumn numberCol = new TableColumn("#");
        numberCol.setCellValueFactory(new Callback<CellDataFeatures<ServiceOrderDetail, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<ServiceOrderDetail, String> p) {
                return new ReadOnlyObjectWrapper((table_Service_Order_Detail.getItems().indexOf(p.getValue()) + 1) + "");
            }
        });
        numberCol.setSortable(false);

        // Định nghĩa cách để lấy dữ liệu cho mỗi ô.
        // Lấy giá trị từ các thuộc tính của ServiceOrderDetail.
        orderIDCol.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        roomIDCol.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        serviceIDCol.setCellValueFactory(new PropertyValueFactory<>("serviceID"));
        userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        serviceNameCol.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        serviceUnitCol.setCellValueFactory(new PropertyValueFactory<>("serviceUnit"));
        servicePriceCol.setCellValueFactory(new PropertyValueFactory<>("servicePrice"));
        serviceInventoryCol.setCellValueFactory(new PropertyValueFactory<>("serviceInventory"));
        serviceQuantityCol.setCellValueFactory(new PropertyValueFactory<>("serviceQuantity"));
        serviceOrderDateCol.setCellValueFactory(new PropertyValueFactory<>("serviceOrderDate"));
        serviceTotalPriceCol.setCellValueFactory(new PropertyValueFactory<>("servicePriceTotal"));
        serviceDiscountCol.setCellValueFactory(new PropertyValueFactory<>("serviceDiscount"));
        serviceImageCol.setCellValueFactory(new PropertyValueFactory<>("imageView"));
        serviceDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("serviceDescription"));
        checkBoxFinishCol.setCellValueFactory(new PropertyValueFactory<>("checkBox_Finish"));

        numberCol.setStyle("-fx-alignment: CENTER-LEFT;");
        orderIDCol.setStyle("-fx-alignment: CENTER-LEFT;");
        customerIDCol.setStyle("-fx-alignment: CENTER-LEFT;");
        roomIDCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceIDCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceNameCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceUnitCol.setStyle("-fx-alignment: CENTER-LEFT;");
        servicePriceCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceInventoryCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceQuantityCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceOrderDateCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceTotalPriceCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceDiscountCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceDescriptionCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceDescriptionCol.setPrefWidth(200);
        serviceImageCol.setStyle("-fx-alignment: CENTER;");
        checkBoxFinishCol.setStyle("-fx-alignment: CENTER;");

        // Thêm cột vào bảng
        table_Service_Order_Detail.getColumns().clear();
        table_Service_Order_Detail.getColumns().addAll(numberCol, orderIDCol, customerIDCol, roomIDCol, serviceIDCol, serviceNameCol, serviceUnitCol,
                servicePriceCol, serviceInventoryCol, serviceQuantityCol, serviceOrderDateCol, serviceTotalPriceCol, serviceDiscountCol,
                serviceDescriptionCol, serviceImageCol, checkBoxFinishCol);

        // Xét xắp xếp theo userName
        //userNameCol.setSortType(TableColumn.SortType.DESCENDING);
    }

    public void showUsersData() {
        listServiceOrderDetails = serviceOrderDetailDAOImpl.getAllServiceOrdersDetails();
        //table_ServiceOrderDetail.getItems().clear();
        table_Service_Order_Detail.setItems(listServiceOrderDetails);

        //Set filterData and Pagination
        filteredData = new FilteredList<>(listServiceOrderDetails, list -> true);
        FXMLMainFormController mainFormController = ConnectControllers.getfXMLMainFormController();
        mainFormController.getTxt_Search().textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(
                    serviceOrderDetail -> newValue == null || newValue.isEmpty()
                    || serviceOrderDetail.getServiceID().toLowerCase().contains(newValue.toLowerCase())
                    //|| serviceOrderDetail.getServiceDescription().toLowerCase().contains(newValue.toLowerCase())
                    || serviceOrderDetail.getServiceUnit().toLowerCase().contains(newValue.toLowerCase())
                    || serviceOrderDetail.getServicePrice().toString().contains(newValue.toLowerCase())
                    || serviceOrderDetail.getServiceInventory().toString().contains(newValue.toLowerCase())
                    //|| serviceOrderDetail.getServiceImportDate().toString().contains(newValue.toLowerCase())
                    || serviceOrderDetail.getServiceName().toLowerCase().contains(newValue.toLowerCase()));
            pagination.setPageCount((int) (Math.ceil(filteredData.size() * 1.0 / ROWS_PER_PAGE)));
            changeTableView(pagination.getCurrentPageIndex(), ROWS_PER_PAGE);
        });

        int totalPage = (int) (Math.ceil(listServiceOrderDetails.size() * 1.0 / ROWS_PER_PAGE));
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
        stageLoader.formLoader("/fxml/FXMLAddNewServiceOrder.fxml", "/images/KAN Logo.png", "Edit Service Order Informations");

    }

    @FXML
    private void handle_MenuItem_Add_Action(ActionEvent event) {
        this.setCheck_Edit_Action(false);
        StageLoader stageLoader = new StageLoader();
        stageLoader.formLoader("/fxml/FXMLAddNewServiceOrder.fxml", "/images/KAN Logo.png", "Add new Service Order Informations");
    }

    @FXML
    private void handle_MenuItem_Delete_Action(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Comfirm");
        alert.setContentText("Do you want to delete this?");
        alert.showAndWait();
        System.out.println(alert.getResult());
        if (alert.getResult() == ButtonType.OK) {
            serviceOrderDetailDAOImpl.deleteServiceOrdersDetail(serviceOrderDetailItem);
            DAO.setUserLogs_With_MAC(mainFormController.getUserRole().getEmployee_ID(), "Delete ServiceOrderDetail ID: "
                    + FormatName.format(serviceOrderDetailItem.getServiceID()),
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
