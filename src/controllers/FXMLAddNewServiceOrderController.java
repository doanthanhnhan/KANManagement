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
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import models.Customer;
import models.CustomerDAOImpl;
import models.RoleDAOImpl;
import models.Room;
import models.RoomDAOImpl;
import models.ServiceOrder;
import models.ServiceOrderDAOImpl;
import models.ServiceOrderDetail;
import models.ServiceOrderDetailDAOImpl;
import models.ServiceType;
import models.ServiceTypeDAOImpl;
import models.boolDecentralizationModel;
import utils.FormatName;
import utils.PatternValided;
import utils.formatCalender;

/**
 * FXML Controller class
 *
 * @author Doan Thanh Nhan
 */
public class FXMLAddNewServiceOrderController implements Initializable {

    ObservableList<ServiceType> listServiceTypes = FXCollections.observableArrayList();
    ObservableList<ServiceOrderDetail> listServiceOrderDetails = FXCollections.observableArrayList();
    ObservableList<Room> listRooms = FXCollections.observableArrayList();
    ObservableList<Customer> listCustomers = FXCollections.observableArrayList();

    ObservableList<String> listServiceTypesID = FXCollections.observableArrayList();
    ObservableList<String> listRoomsID = FXCollections.observableArrayList();
    ObservableList<String> listCustomersID = FXCollections.observableArrayList();

    FXMLMainFormController mainFormController;

    ServiceTypeDAOImpl serviceTypeDAOImpl;
    ServiceOrderDAOImpl serviceOrderDAOImpl;
    ServiceOrderDetailDAOImpl serviceOrderDetailDAOImpl;

    RoomDAOImpl roomDAOImpl;
    CustomerDAOImpl customerDAOImpl;
    RoleDAOImpl roleDAOImpl;

    public boolDecentralizationModel userRole;
    private Boolean check_Validate;

    @FXML
    private JFXTextField txt_Order_ID;
    @FXML
    private JFXComboBox<String> comboBox_Customer_ID;
    @FXML
    private JFXDatePicker datePicker_Order_Date;
    @FXML
    private JFXTextField txt_Note;
    @FXML
    private JFXComboBox<String> comboBox_Service_ID;
    @FXML
    private JFXTextField txt_Service_Name;
    @FXML
    private JFXTextField txt_Service_Unit;
    @FXML
    private JFXTextField txt_Service_Price;
    @FXML
    private JFXTextField txt_Service_Inventory;
    @FXML
    private JFXDatePicker datePicker_Import_Date;
    @FXML
    private JFXComboBox<String> comboBox_Room_ID;
    @FXML
    private JFXTextField txt_Order_Quantity;
    @FXML
    private JFXTextField txt_Discount;
    @FXML
    private JFXTextArea txtArea_Service_Description;
    @FXML
    private ImageView imageView_Service_Image;
    @FXML
    private JFXButton btn_Add_Service;
    @FXML
    private JFXButton btn_Edit_Service;
    @FXML
    private JFXButton btn_Save_Order;
    @FXML
    private HBox hBoxContent;
    @FXML
    private TableView<ServiceOrderDetail> tableView_Service_Order_Detail;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainFormController = ConnectControllers.getfXMLMainFormController();
        serviceTypeDAOImpl = new ServiceTypeDAOImpl();
        serviceOrderDAOImpl = new ServiceOrderDAOImpl();
        serviceOrderDetailDAOImpl = new ServiceOrderDetailDAOImpl();
        roomDAOImpl = new RoomDAOImpl();
        customerDAOImpl = new CustomerDAOImpl();
        roleDAOImpl = new RoleDAOImpl();

        listServiceTypes = serviceTypeDAOImpl.getAllServiceType();
        
        listServiceTypesID = serviceTypeDAOImpl.getAllServiceTypeID();
        listCustomersID = customerDAOImpl.getAllCustomersID();
        listRoomsID = roomDAOImpl.getAllRoomID();

        //Initialize comboBox
        comboBox_Customer_ID.getItems().addAll(listCustomersID);
        comboBox_Room_ID.getItems().addAll(listRoomsID);
        comboBox_Service_ID.getItems().addAll(listServiceTypesID);
    }

    public ServiceOrder get_Service_Order_Data() {
        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setCustomerID(comboBox_Customer_ID.getValue());
        serviceOrder.setRoomID(comboBox_Room_ID.getValue());
        serviceOrder.setServiceNote(FormatName.format(txt_Note.getText()));
        serviceOrder.setServiceOrderID(FormatName.format(txt_Order_ID.getText()));
        //Setting localdatetime (DatePicker + LocalTime.now())
        serviceOrder.setServiceOrderTime(LocalDateTime.of(datePicker_Order_Date.getValue(), LocalTime.now()));
        serviceOrder.setUserName(mainFormController.userRole.getEmployee_ID());
        return serviceOrder;
    }

    public ServiceOrderDetail get_Service_Order_Detail_Data() {
        ServiceOrderDetail serviceOrderDetail = new ServiceOrderDetail();
        serviceOrderDetail.setActive(true);
        serviceOrderDetail.setOrderID(FormatName.format(txt_Order_ID.getText()));
        serviceOrderDetail.setServiceDiscount(Float.valueOf(txt_Discount.getText()));
        serviceOrderDetail.setServiceID(FormatName.format(comboBox_Service_ID.getValue()));
        serviceOrderDetail.setServicePrice(Float.valueOf(txt_Service_Price.getText()));
        serviceOrderDetail.setServiceQuantity(Integer.valueOf(txt_Order_Quantity.getText()));
        serviceOrderDetail.setUserName(mainFormController.userRole.getEmployee_ID());
        return serviceOrderDetail;
    }

    public void add_New_ServiceOrder() {
        ServiceOrder serviceOrder = get_Service_Order_Data();
        serviceOrderDAOImpl.addServiceOrder(serviceOrder);
    }

    public void add_New_ServiceOrderDetail() {
        ServiceOrderDetail serviceOrderDetail = get_Service_Order_Detail_Data();
        serviceOrderDetailDAOImpl.addServiceOrdersDetail(serviceOrderDetail);
    }
    
    private void setColumns() {
        TableColumn<ServiceOrderDetail, String> serviceIDCol = new TableColumn<>("Service ID");
        TableColumn<ServiceOrderDetail, String> serviceNameCol = new TableColumn<>("Service name");
        TableColumn<ServiceOrderDetail, String> serviceUnitCol = new TableColumn<>("Service unit");
        TableColumn<ServiceOrderDetail, Float> servicePriceCol = new TableColumn<>("Service price");
        TableColumn<ServiceOrderDetail, Integer> serviceInventoryCol = new TableColumn<>("Inventory");
        TableColumn<ServiceOrderDetail, LocalDateTime> serviceInputDateCol = new TableColumn<>("Import date");
        TableColumn<ServiceOrderDetail, ImageView> serviceImageCol = new TableColumn<>("Service image");
        TableColumn<ServiceOrderDetail, String> serviceDescriptionCol = new TableColumn<>("Service description");

        TableColumn numberCol = new TableColumn("#");
        numberCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ServiceType, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ServiceType, String> p) {
                return new ReadOnlyObjectWrapper((tableView_Service_Order_Detail.getItems().indexOf(p.getValue()) + 1) + "");
            }
        });
        numberCol.setSortable(false);

        // Định nghĩa cách để lấy dữ liệu cho mỗi ô.
        // Lấy giá trị từ các thuộc tính của ServiceType.
        serviceIDCol.setCellValueFactory(new PropertyValueFactory<>("serviceID"));
        serviceNameCol.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        serviceUnitCol.setCellValueFactory(new PropertyValueFactory<>("serviceUnit"));
        servicePriceCol.setCellValueFactory(new PropertyValueFactory<>("servicePrice"));
        serviceInventoryCol.setCellValueFactory(new PropertyValueFactory<>("serviceInventory"));
        serviceInputDateCol.setCellValueFactory(new PropertyValueFactory<>("serviceInputDate"));
        serviceImageCol.setCellValueFactory(new PropertyValueFactory<>("imageView"));
        serviceDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("serviceDescription"));

        numberCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceIDCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceNameCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceUnitCol.setStyle("-fx-alignment: CENTER-LEFT;");
        servicePriceCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceInventoryCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceInputDateCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceDescriptionCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceDescriptionCol.setPrefWidth(200);
        serviceImageCol.setStyle("-fx-alignment: CENTER;");

        // Thêm cột vào bảng
        tableView_Service_Order_Detail.getColumns().clear();
        tableView_Service_Order_Detail.getColumns().addAll(numberCol, serviceIDCol, serviceNameCol, serviceUnitCol,
                servicePriceCol, serviceInventoryCol, serviceInputDateCol, serviceDescriptionCol, serviceImageCol);

        // Xét xắp xếp theo userName
        //userNameCol.setSortType(TableColumn.SortType.DESCENDING);
    }

    public void showUsersData() {
        listServiceOrderDetails = serviceOrderDetailDAOImpl.get_All_Details_In_One_Order("");
        //table_ServiceType.getItems().clear();
        tableView_Service_Order_Detail.setItems(listServiceOrderDetails);       
    }

    public void validateForm() {
        if (PatternValided.validateTextField(hBoxContent, txt_Discount, "^[\\d][\\d]*\\.?[\\d]*$",
                "DISCOUNT MUST NOT BE EMPTY !!!",
                "DISCOUNT MUST CONTAIN NUMBERS, \nMUST BE NOT CONTAIN CHARACTER OR CHARACTER SPECIAL !!!")) {
            System.out.println("Room area false");
            check_Validate = false;
        } else if (PatternValided.validateTextField(hBoxContent, txt_Service_Price, "^[\\d][\\d]*\\.?[\\d]*$",
                "PRICE MUST NOT BE EMPTY !!!",
                "PRICE MUST CONTAIN NUMBERS, \nMUST BE NOT CONTAIN CHARACTER OR CHARACTER SPECIAL !!!")) {
            System.out.println("Room area false");
            check_Validate = false;
        } else if (PatternValided.validateTextField(hBoxContent, txt_Order_Quantity, "[\\d]{1,4}",
                "QUANTITY MUST NOT BE EMPTY !!!",
                "QUANTITY MUST CONTAIN 1-4 INTEGER NUMBERS, \nMUST BE NOT CONTAIN CHARACTER OR CHARACTER SPECIAL !!!")) {
            System.out.println("Room day remaining false");
            check_Validate = false;
        } else if (PatternValided.validateTextField(hBoxContent, txt_Order_ID, "^(?=.{1,20}$)[a-zA-Z][a-zA-Z0-9_]+$",
                "ORDER ID MUST NOT BE EMPTY !!!",
                "ID MUST CONTAIN 1-20 CHARACTER, \nBEGINNING CHAR MUST NOT BE NUMBER OR SPECIAL CHARACTER  !!!")) {
            System.out.println("Room ID false");
            check_Validate = false;
        } else if (PatternValided.validateTextField(hBoxContent, txt_Service_Inventory, "[\\d]{1,4}",
                "INVENTORY MUST NOT BE EMPTY !!!",
                "INVENTORY MUST CONTAIN 1-4 INTEGER NUMBER \nAND MUST BE CORRECT NUMBER FORMAT !!!")) {
            System.out.println("Room on floor false");
            check_Validate = false;
        } else {
            System.out.println("Validate finished");
            System.out.println("Add finished");
            check_Validate = true;
        }
    }

    @FXML
    private void format_Order_Date(ActionEvent event) {
        String pattern = "dd-MM-yyyy";
        formatCalender.format(pattern, datePicker_Order_Date);
    }

    @FXML
    private void format_Import_Date(ActionEvent event) {
        String pattern = "dd-MM-yyyy";
        formatCalender.format(pattern, datePicker_Import_Date);
    }

    @FXML
    private void add_Service_Action(ActionEvent event) {
        listServiceOrderDetails = serviceOrderDetailDAOImpl.get_All_Details_In_One_Order("");
    }

    @FXML
    private void edit_Service_Action(ActionEvent event) {
    }

    @FXML
    private void save_Order_Action(ActionEvent event) {
    }
}