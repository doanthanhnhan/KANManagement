/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.ServiceType;
import models.ServiceTypeDAOImpl;
import utils.StageLoader;

/**
 * FXML Controller class
 *
 * @author Doan Thanh Nhan
 */
public class FXMLListServiceTypeController implements Initializable {

    ObservableList<ServiceType> listServiceTypes = FXCollections.observableArrayList();
    ServiceTypeDAOImpl serviceTypeDAOImpl = new ServiceTypeDAOImpl();

    public static Boolean check_Edit_Action = false;
    public static ServiceType serviceTypeItem;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setColumns();
        showUsersData();
        ConnectControllers.setfXMLListServiceTypeController(this);
        // Check item when click on table
        table_ServiceType.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                System.out.println(table_ServiceType.getSelectionModel().getSelectedItem().getServiceID());
                serviceTypeItem = table_ServiceType.getSelectionModel().getSelectedItem();
            }
        });
    }

    private void setColumns() {
        TableColumn<ServiceType, String> serviceIDCol = new TableColumn<>("Service ID");
        TableColumn<ServiceType, String> serviceNameCol = new TableColumn<>("Service name");
        TableColumn<ServiceType, String> serviceUnitCol = new TableColumn<>("Service unit");
        TableColumn<ServiceType, Float> servicePriceCol = new TableColumn<>("Service price");
        TableColumn<ServiceType, ImageView> serviceImageCol = new TableColumn<>("Service image");

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
        serviceNameCol.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        serviceUnitCol.setCellValueFactory(new PropertyValueFactory<>("serviceUnit"));
        servicePriceCol.setCellValueFactory(new PropertyValueFactory<>("servicePrice"));
        serviceImageCol.setCellValueFactory(new PropertyValueFactory<>("imageView"));

        numberCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceIDCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceNameCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceUnitCol.setStyle("-fx-alignment: CENTER-LEFT;");
        servicePriceCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceImageCol.setStyle("-fx-alignment: CENTER;");

        // Thêm cột vào bảng
        table_ServiceType.getColumns().clear();
        table_ServiceType.getColumns().addAll(numberCol, serviceIDCol, serviceNameCol, serviceUnitCol, servicePriceCol, serviceImageCol);

        // Xét xắp xếp theo userName
        //userNameCol.setSortType(TableColumn.SortType.DESCENDING);
    }

    public void showUsersData() {
        listServiceTypes = serviceTypeDAOImpl.getAllServiceType();
        table_ServiceType.getItems().clear();
        table_ServiceType.setItems(listServiceTypes);        
    }

    @FXML
    private void handle_MenuItem_Edit_Action(ActionEvent event) {
        check_Edit_Action = true;
        FXMLAddNewServiceTypeController controller = new FXMLAddNewServiceTypeController();
        controller.check_Btn_Update_Clicked.addListener((observable, oldValue, newValue) -> {
            System.out.println(oldValue + " and " + newValue);
            if (newValue != null) {
                Platform.runLater(() -> {
                    showUsersData();
                    System.out.println("Update clicked");
                });
            }
        });
        StageLoader stageLoader = new StageLoader();
        stageLoader.formLoader("/fxml/FXMLAddNewServiceType.fxml", "/images/KAN Logo.png", "Edit Service Type Informations");        

    }

    @FXML
    private void handle_MenuItem_Add_Action(ActionEvent event) {
    }

    @FXML
    private void handle_MenuItem_Delete_Action(ActionEvent event) {
    }

    public void updateTableValue() {
        showUsersData();
    }

    @FXML
    private void handle_MenuItem_Refresh_Action(ActionEvent event) {
        showUsersData();
    }
}
