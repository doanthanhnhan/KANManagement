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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import models.DAO;
import models.RoleDAOImpl;
import models.ServiceType;
import models.ServiceTypeDAOImpl;
import models.boolDecentralizationModel;
import utils.FormatName;
import utils.StageLoader;

/**
 * FXML Controller class
 *
 * @author Doan Thanh Nhan
 */
public class FXMLListServiceTypeController implements Initializable {

    ObservableList<ServiceType> listServiceTypes = FXCollections.observableArrayList();
    ServiceTypeDAOImpl serviceTypeDAOImpl;
    RoleDAOImpl roleDAOImpl;
    public boolDecentralizationModel userRole;

    public Boolean check_Edit_Action;
    public static ServiceType serviceTypeItem;

    private static final int ROWS_PER_PAGE = 20;
    private FilteredList<ServiceType> filteredData;

    private FXMLMainFormController mainFormController;

    @FXML
    private TableView<ServiceType> table_ServiceType;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("List Service Type initialize...");
        ConnectControllers.setfXMLListServiceTypeController(this);
        mainFormController = ConnectControllers.getfXMLMainFormController();
        serviceTypeDAOImpl = new ServiceTypeDAOImpl();
        roleDAOImpl = new RoleDAOImpl();
        check_Edit_Action = new Boolean(false);
        setColumns();
        if (serviceTypeDAOImpl.getAllServiceType().size() != 0) {
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
        table_ServiceType.setOnMouseClicked((MouseEvent event) -> {
            if ((event.getButton().equals(MouseButton.PRIMARY) || event.getButton().equals(MouseButton.SECONDARY))
                    && table_ServiceType.getSelectionModel().getSelectedItem() != null) {
                menuItem_Edit.setDisable(false);
                menuItem_Delete.setDisable(false);
                System.out.println(table_ServiceType.getSelectionModel().getSelectedItem().getServiceID());
                serviceTypeItem = table_ServiceType.getSelectionModel().getSelectedItem();
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
        int toIndex = Math.min(fromIndex + limit, listServiceTypes.size());

        int minIndex = Math.min(toIndex, filteredData.size());

        SortedList<ServiceType> sortedData = new SortedList<>(
                FXCollections.observableArrayList(filteredData.subList(Math.min(fromIndex, minIndex), minIndex)));

        sortedData.comparatorProperty().bind(table_ServiceType.comparatorProperty());

        table_ServiceType.setItems(sortedData);

    }

    private void setColumns() {
        TableColumn<ServiceType, String> serviceIDCol = new TableColumn<>("Service ID");
        TableColumn<ServiceType, String> userNameCol = new TableColumn<>("User name");
        TableColumn<ServiceType, String> serviceNameCol = new TableColumn<>("Service name");
        TableColumn<ServiceType, String> serviceUnitCol = new TableColumn<>("Service unit");
        TableColumn<ServiceType, BigDecimal> servicePriceCol = new TableColumn<>("Service price");
        TableColumn<ServiceType, Integer> serviceInventoryCol = new TableColumn<>("Inventory");
        TableColumn<ServiceType, Integer> serviceImportQuantityCol = new TableColumn<>("Import quantity");
        TableColumn<ServiceType, Integer> serviceExportQuantityCol = new TableColumn<>("Export quantity");
        TableColumn<ServiceType, LocalDateTime> serviceImportDateCol = new TableColumn<>("Import date");
        TableColumn<ServiceType, LocalDateTime> serviceExportDateCol = new TableColumn<>("Export date");
        TableColumn<ServiceType, ImageView> serviceImageCol = new TableColumn<>("Service image");
        TableColumn<ServiceType, String> serviceDescriptionCol = new TableColumn<>("Service description");

        TableColumn numberCol = new TableColumn("#");
        numberCol.setCellValueFactory(new Callback<CellDataFeatures<ServiceType, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<ServiceType, String> p) {
                return new ReadOnlyObjectWrapper((table_ServiceType.getItems().indexOf(p.getValue()) + 1) + "");
            }
        });
        numberCol.setSortable(false);

        // Định nghĩa cách để lấy dữ liệu cho mỗi ô.
        // Lấy giá trị từ các thuộc tính của ServiceType.
        serviceIDCol.setCellValueFactory(new PropertyValueFactory<>("serviceID"));
        userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        serviceNameCol.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        serviceUnitCol.setCellValueFactory(new PropertyValueFactory<>("serviceUnit"));
        servicePriceCol.setCellValueFactory(new PropertyValueFactory<>("servicePrice"));
        serviceInventoryCol.setCellValueFactory(new PropertyValueFactory<>("serviceInventory"));
        serviceImportQuantityCol.setCellValueFactory(new PropertyValueFactory<>("serviceImportQuantity"));
        serviceExportQuantityCol.setCellValueFactory(new PropertyValueFactory<>("serviceExportQuantity"));
        serviceImportDateCol.setCellValueFactory(new PropertyValueFactory<>("serviceImportDate"));
        serviceExportDateCol.setCellValueFactory(new PropertyValueFactory<>("serviceExportDate"));
        serviceImageCol.setCellValueFactory(new PropertyValueFactory<>("imageView"));
        serviceDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("serviceDescription"));

        numberCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceIDCol.setStyle("-fx-alignment: CENTER-LEFT;");
        userNameCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceNameCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceUnitCol.setStyle("-fx-alignment: CENTER-LEFT;");
        servicePriceCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceInventoryCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceImportQuantityCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceExportQuantityCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceImportDateCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceExportDateCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceDescriptionCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceDescriptionCol.setPrefWidth(200);
        serviceImageCol.setStyle("-fx-alignment: CENTER;");

        // Thêm cột vào bảng
        table_ServiceType.getColumns().clear();
        table_ServiceType.getColumns().addAll(numberCol, serviceIDCol, userNameCol, serviceNameCol, serviceUnitCol,
                servicePriceCol, serviceInventoryCol, serviceImportQuantityCol, serviceImportDateCol,
                serviceExportQuantityCol, serviceExportDateCol, serviceDescriptionCol, serviceImageCol);

        // Xét xắp xếp theo userName
        //userNameCol.setSortType(TableColumn.SortType.DESCENDING);
    }

    public void showUsersData() {
        listServiceTypes = serviceTypeDAOImpl.getAllServiceType();
        //table_ServiceType.getItems().clear();
        table_ServiceType.setItems(listServiceTypes);

        //Set filterData and Pagination
        filteredData = new FilteredList<>(listServiceTypes, list -> true);
        FXMLMainFormController mainFormController = ConnectControllers.getfXMLMainFormController();
        mainFormController.getTxt_Search().textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(
                    serviceType -> newValue == null || newValue.isEmpty()
                    || serviceType.getServiceID().toLowerCase().contains(newValue.toLowerCase())
                    //|| serviceType.getServiceDescription().toLowerCase().contains(newValue.toLowerCase())
                    || serviceType.getServiceUnit().toLowerCase().contains(newValue.toLowerCase())
                    || serviceType.getServicePrice().toString().contains(newValue.toLowerCase())
                    || serviceType.getServiceInventory().toString().contains(newValue.toLowerCase())
                    || serviceType.getServiceImportQuantity().toString().contains(newValue.toLowerCase())
                    || serviceType.getServiceExportQuantity().toString().contains(newValue.toLowerCase())
                    || serviceType.getServiceImportDate().toString().contains(newValue.toLowerCase())
                    || serviceType.getServiceExportDate().toString().contains(newValue.toLowerCase())
                    || serviceType.getServiceName().toLowerCase().contains(newValue.toLowerCase()));
            pagination.setPageCount((int) (Math.ceil(filteredData.size() * 1.0 / ROWS_PER_PAGE)));
            changeTableView(pagination.getCurrentPageIndex(), ROWS_PER_PAGE);
        });

        int totalPage = (int) (Math.ceil(listServiceTypes.size() * 1.0 / ROWS_PER_PAGE));
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
        stageLoader.formLoader("/fxml/FXMLAddNewServiceType.fxml", "/images/KAN Logo.png", "Edit Service Type Informations");

    }

    @FXML
    private void handle_MenuItem_Add_Action(ActionEvent event) {
        this.setCheck_Edit_Action(false);
        StageLoader stageLoader = new StageLoader();
        stageLoader.formLoader("/fxml/FXMLAddNewServiceType.fxml", "/images/KAN Logo.png", "Add new Service Type Informations");
    }

    @FXML
    private void handle_MenuItem_Delete_Action(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Comfirm");
        alert.setContentText("Do you want to delete this?");
        alert.showAndWait();
        System.out.println(alert.getResult());
        if (alert.getResult() == ButtonType.OK) {
            serviceTypeDAOImpl.deleteServiceType(serviceTypeItem);
            DAO.setUserLogs_With_MAC(mainFormController.getUserRole().getEmployee_ID(), "Delete ServiceType ID: "
                    + FormatName.format(serviceTypeItem.getServiceID()),
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
