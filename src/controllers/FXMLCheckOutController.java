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
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
import javafx.util.Callback;
import models.CheckIn;
import models.CheckOutDAOImpl;
import models.DAOCustomerBookingCheckIn;
import models.Room;
import models.RoomDAOImpl;
import models.ServiceOrderDetail;
import models.ServiceOrderDetailDAOImpl;
import utils.QRWebCam;
import utils.formatCalender;

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
    private RoomDAOImpl roomDAOImpl;
    private ServiceOrderDetailDAOImpl serviceOrderDetailDAOImpl;
    private StringBuilder string_Total_Bill;
    private Room roomCheckOut;
    private CheckIn checkInRoom;

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
        roomDAOImpl = new RoomDAOImpl();
        checkInRoom = DAOCustomerBookingCheckIn.getCheckInRoom(mainOverViewPaneController.service_Room_ID);

        // Init bigdecimal variable
        total_Service = new BigDecimal(BigInteger.ZERO);
        total_Room = new BigDecimal(BigInteger.ZERO);
        string_Total_Bill = new StringBuilder();

        //Init textfield
        txt_Check_In_ID.setText(checkInRoom.getCheckID());
        txt_Check_Out_ID.setText("CO-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")).toString());
        txt_Customer_ID.setText(checkInRoom.getCusID());
        txt_Total_Guests.setText(checkInRoom.getNumberOfCustomer().toString());
        txt_Full_Name.setText(mainOverViewPaneController.service_Customer_Full_Name);

        txt_Check_In_ID.setDisable(true);
        txt_Check_Out_ID.setDisable(true);
        txt_Customer_ID.setDisable(true);
        txt_Total_Guests.setDisable(true);
        txt_Full_Name.setDisable(true);

        //Format calender picker display
        formatCalender.format("dd-MM-yyyy", datePicker_Check_In);
        formatCalender.format("dd-MM-yyyy", datePicker_Check_Out);
        datePicker_Check_In.setValue(mainOverViewPaneController.room_Check_In_Date.toLocalDate());
        datePicker_Check_Out.setValue(LocalDate.now());
        datePicker_Check_In.setDisable(true);
        datePicker_Check_Out.setDisable(true);

        //CHECKING FORM IS CALLED
        if (mainOverViewPaneController.check_Check_Out_Button_Clicked) {
            roomCheckOut = roomDAOImpl.getRoom(mainOverViewPaneController.service_Room_ID);
            txt_Room_ID.setText(mainOverViewPaneController.service_Room_ID);
            datePicker_Check_In.setValue(roomCheckOut.getCheckInDate().toLocalDate());
            datePicker_Check_Out.setValue(LocalDate.now());
            datePicker_Check_In.setDisable(true);
            datePicker_Check_Out.setDisable(true);
        } else {
            txt_Room_ID.setText("R0103");
            datePicker_Check_In.setValue(LocalDate.parse("2019-03-21"));
        }

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
        list_Service_Order_Details = serviceOrderDetailDAOImpl.get_All_Details_Of_CheckInRoom(txt_Room_ID.getText(), txt_Check_In_ID.getText());
        //table_ServiceType.getItems().clear();
        tableView_Service_Order.setItems(list_Service_Order_Details);
        for (ServiceOrderDetail list_Service_Order_Detail : list_Service_Order_Details) {
            total_Service = total_Service.add(list_Service_Order_Detail.getServicePriceTotal()
                    .multiply(BigDecimal.ONE.subtract(list_Service_Order_Detail.getServiceDiscount())));
            System.out.println("Total service = " + total_Service);
        }
        Long hours_Stay = ChronoUnit.HOURS.between(roomCheckOut.getCheckInDate(), LocalDateTime.now());
        Long days_Stay = (long) ((float) (hours_Stay / 24));
        Long total_Day_Bill = days_Stay * roomCheckOut.getRoomPrice().longValue();
        Long total_Discount = total_Day_Bill * (1 - roomCheckOut.getRoomDiscount().longValue());
        total_Room = total_Room.add(BigDecimal.valueOf(total_Discount));
        Double total = total_Room.doubleValue() + total_Service.doubleValue();
        //IMPORTANT NOTE: To display string.format, setting TextArea with: -fx-font-family: monospace
        string_Total_Bill.append(String.format("-----------------------------------------------%n"));
        string_Total_Bill.append(String.format("| %-25s | %15s |%n", "Services charge", total_Service.toString()));
        string_Total_Bill.append(String.format("| %-25s | %15s |%n", "Room charge", total_Room.toString()));
        string_Total_Bill.append(String.format("| %-25s | %15s |%n", "(+) VAT", "10%"));        
        string_Total_Bill.append(String.format("| %-25s | %15s |%n", "(-) Room discount", "0"));
        string_Total_Bill.append(String.format("| %-25s | %15s |%n", "(-) Services discount", "0"));
        string_Total_Bill.append(String.format("| %-25s | %15s |%n", "(-) Customer discount", "0"));
        string_Total_Bill.append(String.format("-----------------------------------------------%n"));
        string_Total_Bill.append(String.format("| %-25s | %15s |%n", "Payable amount", total.toString()));
        string_Total_Bill.append(String.format("-----------------------------------------------%n"));
        //string_Total_Bill.append("Total service amount = ").append(total_Service.toString()).append("\n");
        //string_Total_Bill.append("Total room price = ").append(total_Room.toString()).append("\n");
        System.out.println(string_Total_Bill);
        txt_Area_Total_Bill.setText(string_Total_Bill.toString());
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
