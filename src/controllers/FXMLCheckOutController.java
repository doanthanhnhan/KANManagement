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
import javafx.beans.value.ObservableListValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
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

    public Double total_Service = 0.0;
    public Double total_Service_Discount = 0.0;
    public Double total_Room = 0.0;
    public Double total_Room_Discount = 0.0;
    public Double charge_Room_1st_Day = 0.0;
    public Double charge_Room_Last_Day = 0.0;

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
    private JFXComboBox<String> comboBox_Payment_Method;
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
    @FXML
    private WebView webView_ToolTip;

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

        //Init tooltip display
        WebEngine webEngine = webView_ToolTip.getEngine();
        webEngine.loadContent(
                "<h3>Note:</h3> This is calculating room charge method <br>"
                + "<b>Check in time:</b> <br>"
                + "     + From 5h00 - 9h00: 50% room price<br>"
                + "     + From 9h00 - 14h00: 30% room price<br>"
                + "     + After 14h00: 100% room price<br>"
                + "<b>Check out time:</b> <br>"
                + "     + From 12h00 - 15h00: Surcharge 30% room price<br>"
                + "     + From 15h00 - 18h00: Surcharge 50% room price<br>"
                + "     + After 18h00 : Surcharge 100% room price<br>"
        );

        //Init combobox
        ObservableList<String> listPayments = FXCollections.observableArrayList();
        listPayments.addAll("Cash", "Credit and Debit card", "Direct debit", "EFTPOS",
                "Online Payment", "Cheque", "Gift cards and vouchers", "Bitcoin and digital currencies");
        comboBox_Payment_Method.getItems().addAll(listPayments);
        // Init stringbuilder     
        string_Total_Bill = new StringBuilder();

        //Init textfield
        txt_Check_In_ID.setText(checkInRoom.getCheckID());
        txt_Check_Out_ID.setText("CO-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")));
        txt_Customer_ID.setText(checkInRoom.getCusID());
        txt_Total_Guests.setText(checkInRoom.getNumberOfCustomer().toString());
        txt_Full_Name.setText(mainOverViewPaneController.service_Customer_Full_Name);
        //txt_Discount.setText();

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
            txt_Room_ID.setDisable(true);
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
            total_Service = total_Service + list_Service_Order_Detail.getServicePriceTotal().doubleValue();
            total_Service_Discount = total_Service_Discount + list_Service_Order_Detail.getServicePriceTotal().doubleValue()
                    * list_Service_Order_Detail.getServiceDiscount().doubleValue();
            System.out.println("Total service = " + total_Service);
        }
        //Calculate room charge
        int check_In_Hour = roomCheckOut.getCheckInDate().getHour();
        int check_Out_Hour = LocalDateTime.now().getHour();
        Long hours_Stay = ChronoUnit.HOURS.between(roomCheckOut.getCheckInDate(), LocalDateTime.now());
        Long days_Stay = ChronoUnit.DAYS.between(roomCheckOut.getCheckInDate(), LocalDateTime.now());
        Double total_Room_Normal = 0.0;
        //Check checkin time
        if (check_In_Hour >= 5 && check_In_Hour < 9) {
            charge_Room_1st_Day = charge_Room_1st_Day + roomCheckOut.getRoomPrice().doubleValue() * 0.5;
        } else if (check_In_Hour >= 9 && check_In_Hour < 14) {
            charge_Room_1st_Day = charge_Room_1st_Day + roomCheckOut.getRoomPrice().doubleValue() * 0.3;
        } else {
            charge_Room_1st_Day = charge_Room_1st_Day + roomCheckOut.getRoomPrice().doubleValue();
        }
        //Check checkout time
        if (check_Out_Hour >= 12 && check_Out_Hour < 15 && !roomCheckOut.getCheckInDate().toLocalDate().equals(LocalDate.now())) {
            charge_Room_Last_Day = charge_Room_Last_Day + roomCheckOut.getRoomPrice().doubleValue() * 0.3;
        } else if (check_Out_Hour >= 15 && check_Out_Hour < 18 && !roomCheckOut.getCheckInDate().toLocalDate().equals(LocalDate.now())) {
            charge_Room_Last_Day = charge_Room_Last_Day + roomCheckOut.getRoomPrice().doubleValue() * 0.5;
        } else if (check_Out_Hour >= 18 && !roomCheckOut.getCheckInDate().toLocalDate().equals(LocalDate.now())) {
            charge_Room_Last_Day = charge_Room_Last_Day + roomCheckOut.getRoomPrice().doubleValue();
        } else {
            charge_Room_Last_Day = 0.0;
        }
        //Charge left days
        int day_Stay_Print = 0;
        if (hours_Stay >= 24) {
            total_Room_Normal = days_Stay * roomCheckOut.getRoomPrice().doubleValue();
            day_Stay_Print = days_Stay.intValue() + 2;
            total_Room = total_Room_Normal + charge_Room_1st_Day + charge_Room_Last_Day;
            total_Room_Discount = total_Room * roomCheckOut.getRoomDiscount().doubleValue();
        } else if (charge_Room_Last_Day != 0) {
            day_Stay_Print = 2;
            days_Stay = Long.valueOf(0);
            total_Room = charge_Room_1st_Day + charge_Room_Last_Day;
            total_Room_Discount = total_Room * roomCheckOut.getRoomDiscount().doubleValue();
        } else {
            day_Stay_Print = 1;
            days_Stay = Long.valueOf(0);
            total_Room = charge_Room_1st_Day;
            total_Room_Discount = total_Room * roomCheckOut.getRoomDiscount().doubleValue();
        }

        Double total_Without_CusDiscount = total_Room + total_Service;
        Double total_With_Customer_Discount = total_Without_CusDiscount * roomCheckOut.getCusDiscount().doubleValue();
        Double total = total_Without_CusDiscount - total_With_Customer_Discount - total_Room_Discount - total_Service_Discount;
        Double total_VAT = total * 0.1;

        //IMPORTANT NOTE: To display string.format, setting TextArea with: -fx-font-family: monospace
        string_Total_Bill.append(String.format("---------------------------------------------------%n"));
        string_Total_Bill.append(String.format("| %-25s | %19s |%n", "Check in time", roomCheckOut.getCheckInDate()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm:ss"))));
        string_Total_Bill.append(String.format("| %-25s | %19s |%n", "Check out time", LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm:ss"))));
        string_Total_Bill.append(String.format("| %-25s | %19d |%n", "No. Of Days", day_Stay_Print));
        string_Total_Bill.append(String.format("| %-25s | %19.3f |%n", "Rent per day", roomCheckOut.getRoomPrice()));
        string_Total_Bill.append(String.format("---------------------------------------------------%n"));
        string_Total_Bill.append(String.format("| %-25s | %19.3f |%n", "Room charge 1st day", charge_Room_1st_Day));
        string_Total_Bill.append(String.format("| %-25s | %19.3f |%n", "Room charge x " + days_Stay + " f.days", total_Room_Normal));
        string_Total_Bill.append(String.format("| %-25s | %19.3f |%n", "Room charge last day", charge_Room_Last_Day));
        string_Total_Bill.append(String.format("---------------------------------------------------%n"));
        string_Total_Bill.append(String.format("| %-25s | %19.3f |%n", "Total Room charge", total_Room));
        string_Total_Bill.append(String.format("| %-25s | %19.3f |%n", "Services charge", total_Service));
        string_Total_Bill.append(String.format("| %-25s | %19.3f |%n", "(-) Room discount", total_Room_Discount));
        string_Total_Bill.append(String.format("| %-25s | %19.3f |%n", "(-) Services discount", total_Service_Discount));
        string_Total_Bill.append(String.format("| %-25s | %19.3f |%n", "(-) Customer discount", total_With_Customer_Discount));
        string_Total_Bill.append(String.format("---------------------------------------------------%n"));
        string_Total_Bill.append(String.format("| %-25s | %19.3f |%n", "Total bill amount", total));
        string_Total_Bill.append(String.format("| %-25s | %19.3f |%n", "(+) VAT (10%)", total_VAT));
        string_Total_Bill.append(String.format("| %-25s | %19.3f |%n", "Payable amount", total + total_VAT));
        string_Total_Bill.append(String.format("---------------------------------------------------%n"));

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

    @FXML
    private void handle_Save_Button_Action(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message");
        alert.setHeaderText("Check out confirmation");
        alert.setContentText("Do you want to check out room: " + txt_Room_ID.getText() + " ?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {

        }
    }

    @FXML
    private void handle_Cancel_Button_Action(ActionEvent event) {
        Stage stage = (Stage) btn_Cancel.getScene().getWindow();
        stage.close();
    }

}
