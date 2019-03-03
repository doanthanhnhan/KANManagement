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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import models.ServiceType;
import models.ServiceTypeDAOImpl;

/**
 * FXML Controller class
 *
 * @author Doan Thanh Nhan
 */
public class FXMLListServiceTypeController implements Initializable {

    ObservableList<ServiceType> listServiceTypes = FXCollections.observableArrayList();
    ServiceTypeDAOImpl serviceTypeDAOImpl = new ServiceTypeDAOImpl();

    @FXML
    private TableView<ServiceType> table_ServiceType;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setColumns();
        showUsersData();
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
                return new ReadOnlyObjectWrapper((table_ServiceType.getItems().indexOf(p.getValue())+1) + "");
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
        table_ServiceType.setItems(listServiceTypes);
    }

    /**
     * Custom image class
     */
    public class CustomImage {

        private ImageView image;

        public CustomImage(ImageView img) {
            this.image = img;
        }

        public void setImage(ImageView value) {
            image = value;
        }

        public ImageView getImage() {
            return image;
        }
    }
}
