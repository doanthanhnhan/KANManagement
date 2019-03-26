/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import models.CheckOutDAOImpl;
import models.Room;
import models.ServiceOrderDetail;
import models.ServiceOrderDetailDAOImpl;
import models.ServiceType;
import utils.QRWebCam;

/**
 * FXML Controller class
 *
 * @author Doan Thanh Nhan
 */
public class FXMLCheckOutController implements Initializable {

    private ObservableList<String> list_Payment_Method;
    private ObservableList<Room> list_Rooms;
    private ObservableList<ServiceOrderDetail> list_Service_Order_Details;

    private CheckOutDAOImpl checkOutDAOImpl;
    private ServiceOrderDetailDAOImpl serviceOrderDetailDAOImpl;
    BigDecimal total_Service;
    BigDecimal total_Room;

    FXMLMainFormController mainFormController;
    FXMLMainOverViewPaneController mainOverViewPaneController;

    @FXML
    private JFXButton btn_QR_Code_Scanner;
    @FXML
    private JFXButton btn_Save;
    @FXML
    private JFXButton btn_Cancel;
    @FXML
    private JFXTextField txt_Check_In_ID;
    @FXML
    private JFXTextField txt_Check_Out_ID;
    @FXML
    private JFXTextField txt_Customer_ID;
    @FXML
    private JFXTextField txt_Full_Name;
    @FXML
    private JFXTextField txt_Room_ID;
    @FXML
    private JFXTextField txt_Total_Guests;
    @FXML
    private JFXDatePicker datePicker_Check_In;
    @FXML
    private JFXDatePicker datePicker_Check_Out;
    @FXML
    private JFXComboBox<?> comboBox_Payment_Method;
    @FXML
    private JFXTextField txt_Discount;
    @FXML
    private JFXTextField txt_Tax;
    @FXML
    private JFXTextField txt_Total_Bill;
    @FXML
    private TableView<ServiceOrderDetail> tableView_Service_Order;
    @FXML
    private JFXTextArea txt_Area_Total_Bill;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        mainFormController = ConnectControllers.getfXMLMainFormController();
        mainOverViewPaneController = ConnectControllers.getfXMLMainOverViewPaneController();
        
        serviceOrderDetailDAOImpl = new ServiceOrderDetailDAOImpl();
        // Init bigdecimal variable
        total_Service = new BigDecimal(BigInteger.ZERO);
        total_Room = new BigDecimal(BigInteger.ZERO);
        
        //CHECKING FORM IS CALLED
        if (true) {
            
        }
        txt_Room_ID.setText("R0201");
        datePicker_Check_In.setValue(LocalDate.parse("2019-03-20"));
        setColumns();
        showUsersData();
    }

    private void setColumns() {
        TableColumn<ServiceOrderDetail, String> orderIDCol = new TableColumn<>("Order ID");
        TableColumn<ServiceOrderDetail, String> serviceNameCol = new TableColumn<>("Service name");
        TableColumn<ServiceOrderDetail, String> serviceUnitCol = new TableColumn<>("Service unit");
        TableColumn<ServiceOrderDetail, BigDecimal> servicePriceCol = new TableColumn<>("Service price");
        TableColumn<ServiceOrderDetail, Integer> serviceQuantityCol = new TableColumn<>("Order quantity");
        TableColumn<ServiceOrderDetail, LocalDateTime> serviceOrderDateCol = new TableColumn<>("Order Date");
        TableColumn<ServiceOrderDetail, BigDecimal> serviceTotalPriceCol = new TableColumn<>("Total");
        TableColumn<ServiceOrderDetail, BigDecimal> serviceDiscountCol = new TableColumn<>("Discount");

        TableColumn numberCol = new TableColumn("#");
        numberCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ServiceOrderDetail, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ServiceOrderDetail, String> p) {
                return new ReadOnlyObjectWrapper((tableView_Service_Order.getItems().indexOf(p.getValue()) + 1) + "");
            }
        });
        numberCol.setSortable(false);

        // Định nghĩa cách để lấy dữ liệu cho mỗi ô.
        // Lấy giá trị từ các thuộc tính của ServiceOrderDetail.
        orderIDCol.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        serviceNameCol.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        serviceUnitCol.setCellValueFactory(new PropertyValueFactory<>("serviceUnit"));
        servicePriceCol.setCellValueFactory(new PropertyValueFactory<>("servicePrice"));
        serviceQuantityCol.setCellValueFactory(new PropertyValueFactory<>("serviceQuantity"));
        serviceOrderDateCol.setCellValueFactory(new PropertyValueFactory<>("serviceOrderDate"));
        serviceTotalPriceCol.setCellValueFactory(new PropertyValueFactory<>("servicePriceTotal"));
        serviceDiscountCol.setCellValueFactory(new PropertyValueFactory<>("serviceDiscount"));

        numberCol.setStyle("-fx-alignment: CENTER-LEFT;");
        orderIDCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceNameCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceUnitCol.setStyle("-fx-alignment: CENTER-LEFT;");
        servicePriceCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceQuantityCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceOrderDateCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceTotalPriceCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceDiscountCol.setStyle("-fx-alignment: CENTER-LEFT;");

        // Thêm cột vào bảng
        tableView_Service_Order.getColumns().clear();
        tableView_Service_Order.getColumns().addAll(numberCol, orderIDCol, serviceNameCol, serviceUnitCol,
                servicePriceCol, serviceQuantityCol, serviceOrderDateCol, serviceTotalPriceCol, serviceDiscountCol
        );

        // Xét xắp xếp theo userName
        //userNameCol.setSortType(TableColumn.SortType.DESCENDING);
    }

    public void showUsersData() {
        list_Service_Order_Details = serviceOrderDetailDAOImpl.get_All_Details_Of_CheckInRoom(txt_Room_ID.getText(), datePicker_Check_In.getValue().toString());
        //table_ServiceType.getItems().clear();
        tableView_Service_Order.setItems(list_Service_Order_Details);
        for (ServiceOrderDetail list_Service_Order_Detail : list_Service_Order_Details) {
            total_Service = total_Service.add(list_Service_Order_Detail.getServicePriceTotal()
                    .multiply(BigDecimal.ONE.subtract(list_Service_Order_Detail.getServiceDiscount())));
            System.out.println("Total service = " + total_Service);
        }
        txt_Area_Total_Bill.setText("Total service amount = " + total_Service.toString());
    }

    @FXML
    private void handle_QRScanner_Action(ActionEvent event) {
        QRWebCam qrWebCam = new QRWebCam();
        qrWebCam.txtQR.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txt_Check_In_ID.setText(newValue);
            }
        });
    }

}
