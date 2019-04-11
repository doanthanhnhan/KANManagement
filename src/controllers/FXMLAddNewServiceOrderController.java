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
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import models.Customer;
import models.DAO;
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
    FXMLMainOverViewPaneController mainOverViewPaneController;
    FXMLListServiceOrderController listServiceOrderController;
    FXMLListServiceOrderDetailController listServiceOrderDetailController;

    ServiceTypeDAOImpl serviceTypeDAOImpl;
    ServiceOrderDAOImpl serviceOrderDAOImpl;
    ServiceOrderDetailDAOImpl serviceOrderDetailDAOImpl;

    RoomDAOImpl roomDAOImpl;
    RoleDAOImpl roleDAOImpl;

    public boolDecentralizationModel userRole;
    private Boolean check_Validate;
    private Boolean check_ComboBox_CustomerID_Action;
    private Boolean check_ComboBox_RoomID_Action;
    private ServiceType selected_Service_Type;
    private ServiceOrderDetail selected_Service_Order_Detail;

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
        ConnectControllers.setfXMLAddNewServiceOrderController(this);
        mainFormController = ConnectControllers.getfXMLMainFormController();
        mainOverViewPaneController = ConnectControllers.getfXMLMainOverViewPaneController();
        listServiceOrderController = ConnectControllers.getfXMLListServiceOrderController();
        listServiceOrderDetailController = ConnectControllers.getfXMLListServiceOrderDetailController();

        serviceTypeDAOImpl = new ServiceTypeDAOImpl();
        serviceOrderDAOImpl = new ServiceOrderDAOImpl();
        serviceOrderDetailDAOImpl = new ServiceOrderDetailDAOImpl();
        roomDAOImpl = new RoomDAOImpl();

        roleDAOImpl = new RoleDAOImpl();

        check_Validate = false;
        check_ComboBox_CustomerID_Action = false;
        check_ComboBox_RoomID_Action = false;

        listServiceTypes = serviceTypeDAOImpl.getAllServiceType();
        listRooms = roomDAOImpl.getAllStatusRooms("Occupied");

        listServiceTypesID = serviceTypeDAOImpl.getAllServiceTypeID();
        listCustomersID = roomDAOImpl.getAllStatusCustomerID("Occupied");
        listRoomsID = roomDAOImpl.getAllStatusRoomID("Occupied");

        //Initialize comboBox
        comboBox_Customer_ID.getItems().addAll(listCustomersID);
        comboBox_Room_ID.getItems().addAll(listRoomsID);
        comboBox_Service_ID.getItems().addAll(listServiceTypesID);
        comboBox_Service_ID.setDisable(true);

        //Initialize OrderID        
        txt_Order_ID.setEditable(false);

        //Initialize Text field
        txt_Discount.setEditable(false);
        txt_Order_Quantity.setEditable(false);
        txt_Service_Inventory.setEditable(false);
        txt_Service_Name.setEditable(false);
        txt_Service_Price.setEditable(false);
        txt_Service_Unit.setEditable(false);
        txtArea_Service_Description.setEditable(false);

        //Validate text field
        txt_Order_Quantity.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!newValue.isEmpty()) {
                    if (PatternValided.PatternServiceInventory(newValue) && Integer.valueOf(newValue) <= Integer.valueOf(txt_Service_Inventory.getText())) {
                        txt_Order_Quantity.getStyleClass().removeAll("text-field-fault");
                    } else {
                        txt_Order_Quantity.setText(oldValue);
                        txt_Order_Quantity.getStyleClass().add("text-field-fault");
                    }
                } else {
                    txt_Order_Quantity.getStyleClass().removeAll("text-field-fault");
                    txt_Order_Quantity.setText("");
                }
            } else {
                txt_Order_Quantity.setText("");
            }
        });

        //Initialize DatePicker
        datePicker_Import_Date.setDisable(true);
        datePicker_Order_Date.setDisable(true);
        formatCalender.format("dd-MM-yyyy", datePicker_Order_Date);

        //Initialize Buttons
        btn_Add_Service.setDisable(true);
        btn_Edit_Service.setDisable(true);

        //Set comboBox function
        comboBox_Customer_ID.setOnAction((event) -> {
            check_ComboBox_CustomerID_Action = true;
            if (!check_ComboBox_RoomID_Action) {
                for (Room roomCustomer : listRooms) {
                    if (roomCustomer.getCustomerID().equals(comboBox_Customer_ID.getValue())) {
                        comboBox_Room_ID.setValue(roomCustomer.getRoomID());
                        break;
                    }
                }
            }
            check_ComboBox_CustomerID_Action = false;
        });
        comboBox_Room_ID.setOnAction((event) -> {
            check_ComboBox_RoomID_Action = true;
            if (!check_ComboBox_CustomerID_Action) {
                for (Room room : listRooms) {
                    if (room.getRoomID().equals(comboBox_Room_ID.getValue())) {
                        comboBox_Customer_ID.setValue(room.getCustomerID());
                        break;
                    }
                }
            }
            check_ComboBox_RoomID_Action = false;
        });

        comboBox_Service_ID.setOnAction((event) -> {
            for (ServiceType serviceType : listServiceTypes) {
                if (serviceType.getServiceID().equals(comboBox_Service_ID.getValue())) {
                    txt_Service_Name.setText(serviceType.getServiceName());
                    txt_Service_Unit.setText(serviceType.getServiceUnit());
                    txt_Service_Price.setText(serviceType.getServicePrice().toString());
                    txt_Service_Inventory.setText(serviceType.getServiceInventory().toString());
                    txt_Discount.setText("0");
                    txt_Order_Quantity.setText("1");
                    datePicker_Import_Date.setValue(serviceType.getServiceImportDate().toLocalDate());
                    txtArea_Service_Description.setText(serviceType.getServiceDescription());
                    imageView_Service_Image.setImage(serviceType.getImageView().getImage());

                    selected_Service_Type = new ServiceType(serviceType);

                    txt_Order_Quantity.requestFocus();
                    btn_Add_Service.setDisable(false);
                    btn_Edit_Service.setDisable(true);
                    break;
                }

            }
        });

        //Select object ServiceOrderDetail from table
        // Check item when click on table
        tableView_Service_Order_Detail.setOnMouseClicked((MouseEvent event) -> {
            if ((event.getButton().equals(MouseButton.PRIMARY) || event.getButton().equals(MouseButton.SECONDARY))
                    && tableView_Service_Order_Detail.getSelectionModel().getSelectedItem() != null) {
                btn_Add_Service.setDisable(true);
                btn_Edit_Service.setDisable(false);
                System.out.println(tableView_Service_Order_Detail.getSelectionModel().getSelectedItem().getServiceID());
                selected_Service_Order_Detail = tableView_Service_Order_Detail.getSelectionModel().getSelectedItem();

                txtArea_Service_Description.setText(selected_Service_Order_Detail.getServiceDescription());
                txt_Discount.setText(String.valueOf(selected_Service_Order_Detail.getServiceDiscount()));
                txt_Order_Quantity.setText(String.valueOf(selected_Service_Order_Detail.getServiceQuantity()));
                txt_Service_Inventory.setText(String.valueOf(selected_Service_Order_Detail.getServiceInventory()));
                txt_Service_Name.setText(selected_Service_Order_Detail.getServiceName());
                txt_Service_Price.setText(String.valueOf(selected_Service_Order_Detail.getServicePrice()));
                txt_Service_Unit.setText(selected_Service_Order_Detail.getServiceUnit());

                //Set double time to ignore event of comboBox
                comboBox_Service_ID.setValue(selected_Service_Order_Detail.getServiceID());
                comboBox_Service_ID.setValue(selected_Service_Order_Detail.getServiceID());
            } else {
                //menuItem_Edit.setDisable(true);
                //menuItem_Delete.setDisable(true);
            }
        });

        //CHECK IF OPEN FOR EDITING
        if (listServiceOrderController != null && listServiceOrderDetailController == null && mainOverViewPaneController == null) {
            // CASE 1: LIST SO OPENED
            if (listServiceOrderController.check_Edit_Action) {
                open_Editing_From_ListSO();
            } else {
                //Initialize OrderID
                txt_Order_ID.setText("SO-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")));
                //Order date
                datePicker_Order_Date.setValue(LocalDate.now());
            }
        } else if (listServiceOrderController == null && listServiceOrderDetailController != null && mainOverViewPaneController == null) {
            // CASE 2: LIST SOD OPENED
            if (listServiceOrderDetailController.check_Edit_Action) {
                open_Editing_From_ListSOD();
            } else {
                //Initialize OrderID
                txt_Order_ID.setText("SO-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")));
                //Order date
                datePicker_Order_Date.setValue(LocalDate.now());
            }
        } else if (listServiceOrderController != null && listServiceOrderDetailController != null && mainOverViewPaneController == null) {
            // CASE 3: LIST SO OPENED && LIST SOD OPENED
            if (listServiceOrderController.check_Edit_Action) {
                open_Editing_From_ListSOD();
            } else if (listServiceOrderDetailController.check_Edit_Action) {
                open_Editing_From_ListSOD();
            } else {
                //Initialize OrderID
                txt_Order_ID.setText("SO-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")));
                //Order date
                datePicker_Order_Date.setValue(LocalDate.now());
            }
        } else if (listServiceOrderController == null && listServiceOrderDetailController == null && mainOverViewPaneController != null) {
            // CASE 4: MAIN OVERVIEW OPENED
            if (mainOverViewPaneController.check_Services_Button_Clicked) {
                open_Add_New_From_Main_Form();
            } else {
                //Initialize OrderID
                txt_Order_ID.setText("SO-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")));
                //Order date
                datePicker_Order_Date.setValue(LocalDate.now());
            }
        } else if (listServiceOrderController != null && listServiceOrderDetailController == null && mainOverViewPaneController != null) {
            // CASE 4: MAIN OVERVIEW OPENED && LIST SO OPENED
            if (mainOverViewPaneController.check_Services_Button_Clicked) {
                open_Add_New_From_Main_Form();
            } else if (listServiceOrderController.check_Edit_Action) {
                open_Editing_From_ListSO();
            } else {
                //Initialize OrderID
                txt_Order_ID.setText("SO-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")));
                //Order date
                datePicker_Order_Date.setValue(LocalDate.now());
            }
        } else if (listServiceOrderController == null && listServiceOrderDetailController != null && mainOverViewPaneController != null) {
            // CASE 4: MAIN OVERVIEW OPENED && LIST SO OPENED
            if (mainOverViewPaneController.check_Services_Button_Clicked) {
                open_Add_New_From_Main_Form();
            } else if (listServiceOrderDetailController.check_Edit_Action) {
                open_Editing_From_ListSOD();
            } else {
                //Initialize OrderID
                txt_Order_ID.setText("SO-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")));
                //Order date
                datePicker_Order_Date.setValue(LocalDate.now());
            }
        } else if (listServiceOrderController != null && listServiceOrderDetailController != null && mainOverViewPaneController != null) {
            // CASE 4: MAIN OVERVIEW OPENED && LIST SO OPENED
            if (mainOverViewPaneController.check_Services_Button_Clicked) {
                open_Add_New_From_Main_Form();
            } else if (listServiceOrderController.check_Edit_Action) {
                open_Editing_From_ListSO();
            } else if (listServiceOrderDetailController.check_Edit_Action) {
                open_Editing_From_ListSOD();
            } else {
                //Initialize OrderID
                txt_Order_ID.setText("SO-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")));
                //Order date
                datePicker_Order_Date.setValue(LocalDate.now());
            }
        } else {
            //Initialize OrderID
            txt_Order_ID.setText("SO-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")));
            //Order date
            datePicker_Order_Date.setValue(LocalDate.now());
        }
    }

    public void open_Editing_From_ListSO() {
        //Initialize OrderID
        txt_Order_ID.setText(listServiceOrderController.serviceOrderItem.getServiceOrderID());
        //Order date
        datePicker_Order_Date.setValue(listServiceOrderController.serviceOrderItem.getServiceOrderTime().toLocalDate());
        //Initialize Button
        btn_Save_Order.setText("Edit order");
        btn_Save_Order.setDisable(true);

        //Initialize ComboBox
        comboBox_Customer_ID.setValue(listServiceOrderController.serviceOrderItem.getCustomerID());
        comboBox_Room_ID.setValue(listServiceOrderController.serviceOrderItem.getRoomID());
        //Setting after loading finished
        comboBox_Customer_ID.setDisable(true);
        comboBox_Room_ID.setDisable(true);
        comboBox_Service_ID.setDisable(false);
        comboBox_Service_ID.requestFocus();
        txt_Note.setDisable(true);

        txt_Discount.setEditable(true);
        txt_Order_Quantity.setEditable(true);
        setColumns();
        showUsersData();
    }

    public void open_Editing_From_ListSOD() {
        //Initialize OrderID
        txt_Order_ID.setText(listServiceOrderDetailController.serviceOrderDetailItem.getOrderID());
        //Order date
        datePicker_Order_Date.setValue(listServiceOrderDetailController.serviceOrderDetailItem.getServiceOrderDate().toLocalDate());
        //Initialize Button
        btn_Save_Order.setText("Edit order");
        btn_Save_Order.setDisable(true);
        //Initialize ComboBox
        comboBox_Customer_ID.setValue(listServiceOrderDetailController.serviceOrderDetailItem.getCustomerID());
        comboBox_Room_ID.setValue(listServiceOrderDetailController.serviceOrderDetailItem.getRoomID());

        //Setting after loading finished
        comboBox_Customer_ID.setDisable(true);
        comboBox_Room_ID.setDisable(true);
        comboBox_Service_ID.setDisable(false);
        comboBox_Service_ID.requestFocus();
        txt_Note.setDisable(true);

        txt_Discount.setEditable(true);
        txt_Order_Quantity.setEditable(true);
        setColumns();
        showUsersData();
    }

    public void open_Add_New_From_Main_Form() {
        //Initialize OrderID
        txt_Order_ID.setText("SO-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")));
        //Order date
        datePicker_Order_Date.setValue(LocalDate.now());

        //Initialize ComboBox
        comboBox_Customer_ID.setValue(mainOverViewPaneController.service_Customer_ID);
        comboBox_Room_ID.setValue(mainOverViewPaneController.service_Room_ID);

        //Setting after loading finished
        comboBox_Customer_ID.setDisable(true);
        comboBox_Room_ID.setDisable(true);
    }

    public void refresh_Service_Type() {
        listServiceTypes = serviceTypeDAOImpl.getAllServiceType();
    }

    public void refresh_After_Remove() {
        showUsersData();
        refresh_Service_Type();

        txtArea_Service_Description.setText("");
        txt_Discount.setText("");
        txt_Order_Quantity.setText("");
        txt_Service_Inventory.setText("");
        txt_Service_Name.setText("");
        txt_Service_Price.setText("");
        txt_Service_Unit.setText("");
        datePicker_Import_Date.setValue(null);
        comboBox_Service_ID.setValue(null);
        imageView_Service_Image.setImage(null);
    }

    public void refresh_After_Add() {
        showUsersData();
        refresh_Service_Type();

        txtArea_Service_Description.setText("");
        txt_Discount.setText("");
        txt_Order_Quantity.setText("");
        txt_Service_Inventory.setText("");
        txt_Service_Name.setText("");
        txt_Service_Price.setText("");
        txt_Service_Unit.setText("");
        datePicker_Import_Date.setValue(null);
        comboBox_Service_ID.setValue(null);
        imageView_Service_Image.setImage(null);
    }

    public ServiceType get_Service_Type_Data() {
        ServiceType serviceType = new ServiceType(selected_Service_Type);
        return serviceType;
    }

    public ServiceOrder get_Service_Order_Data() {
        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setCustomerID(comboBox_Customer_ID.getValue());
        serviceOrder.setRoomID(comboBox_Room_ID.getValue());
        serviceOrder.setServiceNote(FormatName.format_Trim_Space(txt_Note.getText()));
        serviceOrder.setServiceOrderID(txt_Order_ID.getText());
        //Setting localdatetime (DatePicker + LocalTime.now())
        serviceOrder.setServiceOrderTime(LocalDateTime.of(datePicker_Order_Date.getValue(), LocalTime.now()));
        serviceOrder.setUserName(mainFormController.userRole.getEmployee_ID());
        return serviceOrder;
    }

    public ServiceOrderDetail get_Service_Order_Detail_Data() {
        ServiceOrderDetail serviceOrderDetail = new ServiceOrderDetail();
        serviceOrderDetail.setActive(true);
        serviceOrderDetail.setOrderID(txt_Order_ID.getText());
        serviceOrderDetail.setServiceDiscount(BigDecimal.valueOf(Double.valueOf(txt_Discount.getText())));
        serviceOrderDetail.setServiceID(FormatName.format(comboBox_Service_ID.getValue()));
        serviceOrderDetail.setServicePriceTotal(BigDecimal.valueOf(Double.valueOf(txt_Service_Price.getText()) * Integer.valueOf(txt_Order_Quantity.getText())));
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

        //Update service type infomations
        selected_Service_Type.setServiceInventory(Integer.valueOf(txt_Service_Inventory.getText()) - Integer.valueOf(txt_Order_Quantity.getText()));
        selected_Service_Type.setServiceExportQuantity(selected_Service_Type.getServiceExportQuantity() + Integer.valueOf(txt_Order_Quantity.getText()));
        selected_Service_Type.setServiceExportDate(LocalDateTime.now());
        selected_Service_Type.setUserName(mainFormController.getUserRole().getEmployee_ID());
        serviceTypeDAOImpl.editServiceType(selected_Service_Type, true);

        DAO.setUserLogs_With_MAC(mainFormController.getUserRole().getEmployee_ID(), "Add new ServiceOrderDetail, ServiceName: "
                + FormatName.format(txt_Service_Name.getText()),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), mainFormController.macAdress);

        // Setting value for focusing new item
        if (listServiceOrderDetailController != null) {
            listServiceOrderDetailController.new_SO_ID = txt_Order_ID.getText();
            listServiceOrderDetailController.new_SOD_Service_ID = comboBox_Service_ID.getValue();
            listServiceOrderDetailController.check_Add_New = true;
            listServiceOrderDetailController.showUsersData();
        }

        if (listServiceOrderController != null) {
            listServiceOrderController.new_SO_ID = txt_Order_ID.getText();
            listServiceOrderController.check_Add_New = true;
            listServiceOrderController.showUsersData();
        }

        // Refresh form after adding new service type
        refresh_After_Add();

        btn_Add_Service.setDisable(true);
        comboBox_Service_ID.requestFocus();
    }

    private void setColumns() {
        TableColumn<ServiceOrderDetail, String> serviceIDCol = new TableColumn<>("Service ID");
        TableColumn<ServiceOrderDetail, String> serviceNameCol = new TableColumn<>("Service name");
        TableColumn<ServiceOrderDetail, String> serviceUnitCol = new TableColumn<>("Service unit");
        TableColumn<ServiceOrderDetail, Float> servicePriceCol = new TableColumn<>("Service price");
        TableColumn<ServiceOrderDetail, Integer> serviceQuantityCol = new TableColumn<>("Order quantity");
        TableColumn<ServiceOrderDetail, BigDecimal> serviceTotalPriceCol = new TableColumn<>("Total");
        TableColumn<ServiceOrderDetail, BigDecimal> serviceDiscountCol = new TableColumn<>("Discount");
        TableColumn<ServiceOrderDetail, ImageView> serviceImageCol = new TableColumn<>("Service image");
        TableColumn<ServiceOrderDetail, String> serviceDescriptionCol = new TableColumn<>("Service description");
        TableColumn<ServiceOrderDetail, JFXButton> serviceRemoveCol = new TableColumn<>("Action");
        TableColumn<ServiceOrderDetail, JFXButton> serviceFinishCol = new TableColumn<>("Finish");

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
        serviceQuantityCol.setCellValueFactory(new PropertyValueFactory<>("serviceQuantity"));
        serviceTotalPriceCol.setCellValueFactory(new PropertyValueFactory<>("servicePriceTotal"));
        serviceDiscountCol.setCellValueFactory(new PropertyValueFactory<>("serviceDiscount"));
        serviceImageCol.setCellValueFactory(new PropertyValueFactory<>("imageView"));
        serviceDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("serviceDescription"));
        serviceRemoveCol.setCellValueFactory(new PropertyValueFactory<>("serviceRemoveButton"));
        serviceFinishCol.setCellValueFactory(new PropertyValueFactory<>("checkBox_Finish"));

        numberCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceIDCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceNameCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceUnitCol.setStyle("-fx-alignment: CENTER-LEFT;");
        servicePriceCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceQuantityCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceTotalPriceCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceDiscountCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceDescriptionCol.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceDescriptionCol.setPrefWidth(200);
        serviceImageCol.setStyle("-fx-alignment: CENTER;");
        serviceRemoveCol.setStyle("-fx-alignment: CENTER;");
        serviceFinishCol.setStyle("-fx-alignment: CENTER;");

        // Thêm cột vào bảng
        tableView_Service_Order_Detail.getColumns().clear();
        tableView_Service_Order_Detail.getColumns().addAll(numberCol, serviceIDCol, serviceNameCol, serviceUnitCol,
                servicePriceCol, serviceQuantityCol, serviceTotalPriceCol, serviceDiscountCol,
                serviceDescriptionCol, serviceImageCol, serviceFinishCol, serviceRemoveCol);

        // Xét xắp xếp theo userName
        //userNameCol.setSortType(TableColumn.SortType.DESCENDING);
    }

    public void showUsersData() {
        listServiceOrderDetails = serviceOrderDetailDAOImpl.get_All_Details_In_One_Order(txt_Order_ID.getText());
        //table_ServiceType.getItems().clear();
        tableView_Service_Order_Detail.setItems(listServiceOrderDetails);
    }

    public void update_Service_Type_After_Remove() {
        selected_Service_Type.setServiceInventory(selected_Service_Order_Detail.getServiceInventory() + selected_Service_Order_Detail.getServiceQuantity());
        selected_Service_Type.setServiceExportQuantity(selected_Service_Order_Detail.getServiceExportQuantity() - selected_Service_Order_Detail.getServiceQuantity());
        selected_Service_Type.setServiceExportDate(LocalDateTime.now());
        selected_Service_Type.setUserName(mainFormController.getUserRole().getEmployee_ID());
        serviceTypeDAOImpl.editServiceType(selected_Service_Type, true);
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
        //Add serviceorderdetail
        ServiceOrderDetail serviceOrderDetail = get_Service_Order_Detail_Data();
        if (tableView_Service_Order_Detail.getItems().size() > 0) {
            Boolean check_serviceID = false;
            for (ServiceOrderDetail sod : tableView_Service_Order_Detail.getItems()) {
                if (sod.getServiceID().equals(serviceOrderDetail.getServiceID())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Message");
                    alert.setHeaderText("Duplicated service warning");
                    alert.setContentText("Selected service existed! If you want to change informations of this service, please use Edit Function!");
                    alert.show();
                    check_serviceID = true;
                    break;
                }
            }
            if (!check_serviceID) {
                add_New_ServiceOrderDetail();
            }
        } else {
            add_New_ServiceOrderDetail();
        }

    }

    @FXML
    private void edit_Service_Action(ActionEvent event) {
        //Add serviceorderdetail
        ServiceOrderDetail serviceOrderDetail = get_Service_Order_Detail_Data();
        serviceOrderDetailDAOImpl.editServiceOrdersDetail(serviceOrderDetail, true);
        //Update service type infomations
        selected_Service_Type.setServiceInventory(Integer.valueOf(txt_Service_Inventory.getText())
                + selected_Service_Order_Detail.getServiceQuantity()
                - Integer.valueOf(txt_Order_Quantity.getText()));
        selected_Service_Type.setServiceExportQuantity(selected_Service_Order_Detail.getServiceExportQuantity()
                - selected_Service_Order_Detail.getServiceQuantity()
                + Integer.valueOf(txt_Order_Quantity.getText()));
        selected_Service_Type.setServiceExportDate(LocalDateTime.now());
        selected_Service_Type.setUserName(mainFormController.getUserRole().getEmployee_ID());

        serviceTypeDAOImpl.editServiceType(selected_Service_Type, true);

        DAO.setUserLogs_With_MAC(mainFormController.getUserRole().getEmployee_ID(), "Edit ServiceOrderDetail, ServiceName: "
                + FormatName.format(txt_Service_Name.getText()),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), mainFormController.macAdress);

        // Setting value for focusing new item
        if (listServiceOrderDetailController != null) {
            listServiceOrderDetailController.new_SO_ID = txt_Order_ID.getText();
            listServiceOrderDetailController.new_SOD_Service_ID = comboBox_Service_ID.getValue();
            listServiceOrderDetailController.check_Add_New = true;
            listServiceOrderDetailController.showUsersData();
        }

        if (listServiceOrderController != null) {
            listServiceOrderController.new_SO_ID = txt_Order_ID.getText();
            listServiceOrderController.check_Add_New = true;
            listServiceOrderController.showUsersData();
        }

        // Refresh form after adding new service type
        refresh_After_Add();

        btn_Edit_Service.setDisable(true);
        comboBox_Service_ID.requestFocus();
    }

    @FXML
    private void save_Order_Action(ActionEvent event) {
        System.out.println("Save order button clicked.");
        ServiceOrder serviceOrder = get_Service_Order_Data();
        serviceOrderDAOImpl.addServiceOrder(serviceOrder);
        DAO.setUserLogs_With_MAC(mainFormController.getUserRole().getEmployee_ID(), "Add new ServiceOrderID: "
                + FormatName.format(txt_Order_ID.getText()),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), mainFormController.macAdress);

        btn_Save_Order.setDisable(true);

        comboBox_Customer_ID.setDisable(true);
        comboBox_Room_ID.setDisable(true);
        comboBox_Service_ID.setDisable(false);
        comboBox_Service_ID.requestFocus();
        txt_Note.setDisable(true);

        txt_Discount.setEditable(true);
        txt_Order_Quantity.setEditable(true);

        if (listServiceOrderController != null) {
            listServiceOrderController.new_SO_ID = txt_Order_ID.getText();
            listServiceOrderController.check_Add_New = true;
            listServiceOrderController.showUsersData();
        }

        setColumns();
        showUsersData();
    }
}
