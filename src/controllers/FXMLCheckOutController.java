/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import models.Bill;
import models.BillDAOImpl;
import models.CheckIn;
import models.CheckOut;
import models.CheckOutDAOImpl;
import models.DAO;
import models.DAOCustomerBookingCheckIn;
import models.Room;
import models.RoomDAOImpl;
import models.ServiceOrderDAOImpl;
import models.ServiceOrderDetail;
import models.ServiceOrderDetailDAOImpl;
import utils.FormatName;
import utils.PatternValided;
import utils.PrintReport;
import utils.QRCreate;
import utils.QRWebCam;
import utils.StageLoader;
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
    private ObservableList<ServiceOrderDetail> list_Service_Order_Details_All_Rooms;
    private ObservableList<CheckIn> list_Check_In_Of_Customer;

    private CheckOutDAOImpl checkOutDAOImpl;
    private RoomDAOImpl roomDAOImpl;
    private BillDAOImpl billDAOImpl;
    private ServiceOrderDAOImpl serviceOrderDAOImpl;
    private ServiceOrderDetailDAOImpl serviceOrderDetailDAOImpl;
    private StringBuilder string_Total_Bill;
    private StringBuilder all_string_Total_Bill;
    private Room roomCheckOut;
    private CheckIn checkInRoom;

    public Double total_Service = 0.0;
    public Double total_Service_Discount = 0.0;
    public Double total_Room = 0.0;
    public Double total_Room_Discount = 0.0;
    public Double charge_Room_1st_Day = 0.0;
    public Double charge_Room_Last_Day = 0.0;
    public Double total_Bill = 0.0;
    public Double total_Without_CusDiscount = 0.0;
    public Double total_With_Customer_Discount = 0.0;
    public Double total = 0.0;
    public Double total_VAT = 0.0;
    public int day_Stay_Print = 0;

    public Double all_total_Service = 0.0;
    public Double all_total_Service_Discount = 0.0;
    public Double all_total_Room = 0.0;
    public Double all_total_Room_Discount = 0.0;
    public Double all_charge_Room_1st_Day = 0.0;
    public Double all_charge_Room_Last_Day = 0.0;
    public Double all_total_Bill = 0.0;
    public Double all_total_Without_CusDiscount = 0.0;
    public Double all_total_With_Customer_Discount = 0.0;
    public Double all_total = 0.0;
    public Double all_total_VAT = 0.0;
    public int all_day_Stay_Print = 0;

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
    @FXML
    private JFXCheckBox checkBox_Allow_OverTime_CO;
    @FXML
    private JFXTextField txt_Customer_Give;
    @FXML
    private JFXTextField txt_Customer_Change;
    @FXML
    private JFXCheckBox checkBox_All_Rooms;

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

        serviceOrderDAOImpl = new ServiceOrderDAOImpl();
        serviceOrderDetailDAOImpl = new ServiceOrderDetailDAOImpl();
        roomDAOImpl = new RoomDAOImpl();
        checkOutDAOImpl = new CheckOutDAOImpl();
        billDAOImpl = new BillDAOImpl();
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
        comboBox_Payment_Method.setValue("Cash");
        // Init stringbuilder     
        string_Total_Bill = new StringBuilder();
        all_string_Total_Bill = new StringBuilder();
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
        //Calculate customer change
        txt_Customer_Give.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!newValue.isEmpty()) {
                    if (PatternValided.PatternSalary(newValue)) {
                        if (checkBox_All_Rooms.isSelected()) {
                            Double changeMoney = Double.valueOf(newValue) - all_total_Bill;
                            txt_Customer_Change.setText(String.format("%.3f", changeMoney));
                            txt_Customer_Give.getStyleClass().removeAll("text-field-fault");
                        } else {
                            Double changeMoney = Double.valueOf(newValue) - total_Bill;
                            txt_Customer_Change.setText(String.format("%.3f", changeMoney));
                            txt_Customer_Give.getStyleClass().removeAll("text-field-fault");
                        }

                    } else {
                        txt_Customer_Give.setText(oldValue);
                        txt_Customer_Give.getStyleClass().add("text-field-fault");
                    }
                } else {
                    txt_Customer_Give.setText("");
                    txt_Customer_Change.setText("");
                }
            }
        });

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
        if (!checkBox_All_Rooms.isSelected()) {
            list_Service_Order_Details = serviceOrderDetailDAOImpl.get_All_Details_Of_CheckInRoom(txt_Room_ID.getText(), txt_Check_In_ID.getText());
            //Load checkout room again to prevent Check out All Rooms case
            roomCheckOut = roomDAOImpl.getRoom(txt_Room_ID.getText());
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
            if (checkBox_Allow_OverTime_CO.isSelected()) {
                charge_Room_Last_Day = 0.0;
            } else {
                if (check_Out_Hour >= 12 && check_Out_Hour < 15 && !roomCheckOut.getCheckInDate().toLocalDate().equals(LocalDate.now())) {
                    charge_Room_Last_Day = charge_Room_Last_Day + roomCheckOut.getRoomPrice().doubleValue() * 0.3;
                } else if (check_Out_Hour >= 15 && check_Out_Hour < 18 && !roomCheckOut.getCheckInDate().toLocalDate().equals(LocalDate.now())) {
                    charge_Room_Last_Day = charge_Room_Last_Day + roomCheckOut.getRoomPrice().doubleValue() * 0.5;
                } else if (check_Out_Hour >= 18 && !roomCheckOut.getCheckInDate().toLocalDate().equals(LocalDate.now())) {
                    charge_Room_Last_Day = charge_Room_Last_Day + roomCheckOut.getRoomPrice().doubleValue();
                } else {
                    charge_Room_Last_Day = 0.0;
                }
            }
            //Charge left days
            day_Stay_Print = 0;
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

            total_Without_CusDiscount = total_Room + total_Service;
            total_With_Customer_Discount = total_Without_CusDiscount * roomCheckOut.getCusDiscount().doubleValue();
            total = total_Without_CusDiscount - total_With_Customer_Discount - total_Room_Discount - total_Service_Discount;
            total_VAT = total * 0.1;
            total_Bill = total + total_VAT;

            //IMPORTANT NOTE: To display string.format, setting TextArea with: -fx-font-family: monospace
            string_Total_Bill.append(String.format("-------------------------------------------------------------%n"));
            string_Total_Bill.append(String.format("| %-35s | %19s |%n", "Check in time", roomCheckOut.getCheckInDate()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm:ss"))));
            string_Total_Bill.append(String.format("| %-35s | %19s |%n", "Check out time", LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm:ss"))));
            string_Total_Bill.append(String.format("| %-35s | %19d |%n", "No. Of Days", day_Stay_Print));
            string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "Rent per day", roomCheckOut.getRoomPrice()));
            string_Total_Bill.append(String.format("-------------------------------------------------------------%n"));
            string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "Room charge 1st day", charge_Room_1st_Day));
            string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "Room charge x " + days_Stay + " f.days", total_Room_Normal));
            string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "Room charge last day", charge_Room_Last_Day));
            string_Total_Bill.append(String.format("-------------------------------------------------------------%n"));
            string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "Total Room charge", total_Room));
            string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "Services charge", total_Service));
            string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "(-) Room discount (" + roomCheckOut.getRoomDiscount().multiply(BigDecimal.valueOf(100)) + "%)", total_Room_Discount));
            string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "(-) Services discount", total_Service_Discount));
            string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "(-) Customer discount (" + roomCheckOut.getCusDiscount().multiply(BigDecimal.valueOf(100)) + "%)", total_With_Customer_Discount));
            string_Total_Bill.append(String.format("-------------------------------------------------------------%n"));
            string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "Total bill amount", total));
            string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "(+) VAT (10%)", total_VAT));
            string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "Payable amount", total_Bill));
            string_Total_Bill.append(String.format("-------------------------------------------------------------%n"));

            System.out.println(string_Total_Bill);
            txt_Area_Total_Bill.setText(string_Total_Bill.toString());
            txt_Total_Bill.setText(String.format("%.3f", total_Bill));
            txt_Total_Bill.setDisable(true);
            txt_Tax.setText("0.1");
            txt_Tax.setDisable(true);
            txt_Discount.setText(roomCheckOut.getCusDiscount().toString());
            txt_Discount.setDisable(true);
        } else {
            list_Service_Order_Details_All_Rooms = serviceOrderDetailDAOImpl.get_All_Details_Of_CheckIn_Customer(txt_Customer_ID.getText());
            //Setting data for tableview
            tableView_Service_Order.setItems(list_Service_Order_Details_All_Rooms);

            list_Check_In_Of_Customer = serviceOrderDetailDAOImpl.get_All_CheckIn_Of_Customer(txt_Customer_ID.getText());
            for (CheckIn check_In : list_Check_In_Of_Customer) {
                string_Total_Bill = new StringBuilder();
                total_Service = 0.0;
                total_Service_Discount = 0.0;
                total_Room = 0.0;
                total_Room_Discount = 0.0;
                charge_Room_1st_Day = 0.0;
                charge_Room_Last_Day = 0.0;
                total_Bill = 0.0;
                total_Without_CusDiscount = 0.0;
                total_With_Customer_Discount = 0.0;
                total = 0.0;
                total_VAT = 0.0;
                day_Stay_Print = 0;

                showUsersData_All_Room(check_In.getRoomID(), check_In.getCheckID());

                all_total_Service = all_total_Service + total_Service;
                all_total_Service_Discount = all_total_Service_Discount + total_Service_Discount;
                all_total_Room = all_total_Room + total_Room;
                all_total_Room_Discount = all_total_Room_Discount + total_Room_Discount;
                all_charge_Room_1st_Day = all_charge_Room_1st_Day + charge_Room_1st_Day;
                all_charge_Room_Last_Day = all_charge_Room_Last_Day + charge_Room_Last_Day;
                all_total_Bill = all_total_Bill + total_Bill;
                all_total_Without_CusDiscount = all_total_Without_CusDiscount + total_Without_CusDiscount;
                all_total_With_Customer_Discount = all_total_With_Customer_Discount + total_With_Customer_Discount;
                all_total = all_total + total;
                all_total_VAT = all_total_VAT + total_VAT;
                all_day_Stay_Print = all_day_Stay_Print + day_Stay_Print;
                all_string_Total_Bill.append(string_Total_Bill);
            }
            //IMPORTANT NOTE: To display string.format, setting TextArea with: -fx-font-family: monospace
            all_string_Total_Bill.append(String.format("-------------------------------------------------------------%n"));
            all_string_Total_Bill.append(String.format("| %-57s |%n", "TOTAL ALL ROOMS"));
            all_string_Total_Bill.append(String.format("-------------------------------------------------------------%n"));
            all_string_Total_Bill.append(String.format("| %-35s | %19s |%n", "Check in time", roomCheckOut.getCheckInDate()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm:ss"))));
            all_string_Total_Bill.append(String.format("| %-35s | %19s |%n", "Check out time", LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm:ss"))));
            all_string_Total_Bill.append(String.format("| %-35s | %19d |%n", "No. Of Days", all_day_Stay_Print));
            all_string_Total_Bill.append(String.format("-------------------------------------------------------------%n"));
//            all_string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "Room charge 1st day", charge_Room_1st_Day));
//            all_string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "Room charge x " + days_Stay + " f.days", total_Room_Normal));
//            all_string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "Room charge last day", charge_Room_Last_Day));
            all_string_Total_Bill.append(String.format("-------------------------------------------------------------%n"));
            all_string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "Total Room charge", all_total_Room));
            all_string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "Services charge", all_total_Service));
            all_string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "(-) Room discount (" + roomCheckOut.getRoomDiscount().multiply(BigDecimal.valueOf(100)) + "%)", all_total_Room_Discount));
            all_string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "(-) Services discount", all_total_Service_Discount));
            all_string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "(-) Customer discount (" + roomCheckOut.getCusDiscount().multiply(BigDecimal.valueOf(100)) + "%)", all_total_With_Customer_Discount));
            all_string_Total_Bill.append(String.format("-------------------------------------------------------------%n"));
            all_string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "Total bill amount", all_total));
            all_string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "(+) VAT (10%)", all_total_VAT));
            all_string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "Payable amount", all_total_Bill));
            all_string_Total_Bill.append(String.format("-------------------------------------------------------------%n"));

            System.out.println(all_string_Total_Bill);
            txt_Area_Total_Bill.setText(all_string_Total_Bill.toString());
            txt_Total_Bill.setText(String.format("%.3f", all_total_Bill));
            txt_Total_Bill.setDisable(true);
            txt_Tax.setText("0.1");
            txt_Tax.setDisable(true);
            txt_Discount.setText(roomCheckOut.getCusDiscount().toString());
            txt_Discount.setDisable(true);
        }
    }

    public void showUsersData_All_Room(String roomID, String checkInID) {
        list_Service_Order_Details = serviceOrderDetailDAOImpl.get_All_Details_Of_CheckInRoom(roomID, checkInID);
        roomCheckOut = roomDAOImpl.getRoom(roomID);
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
        if (checkBox_Allow_OverTime_CO.isSelected()) {
            charge_Room_Last_Day = 0.0;
        } else {
            if (check_Out_Hour >= 12 && check_Out_Hour < 15 && !roomCheckOut.getCheckInDate().toLocalDate().equals(LocalDate.now())) {
                charge_Room_Last_Day = charge_Room_Last_Day + roomCheckOut.getRoomPrice().doubleValue() * 0.3;
            } else if (check_Out_Hour >= 15 && check_Out_Hour < 18 && !roomCheckOut.getCheckInDate().toLocalDate().equals(LocalDate.now())) {
                charge_Room_Last_Day = charge_Room_Last_Day + roomCheckOut.getRoomPrice().doubleValue() * 0.5;
            } else if (check_Out_Hour >= 18 && !roomCheckOut.getCheckInDate().toLocalDate().equals(LocalDate.now())) {
                charge_Room_Last_Day = charge_Room_Last_Day + roomCheckOut.getRoomPrice().doubleValue();
            } else {
                charge_Room_Last_Day = 0.0;
            }
        }
        //Charge left days
        day_Stay_Print = 0;
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

        total_Without_CusDiscount = total_Room + total_Service;
        total_With_Customer_Discount = total_Without_CusDiscount * roomCheckOut.getCusDiscount().doubleValue();
        total = total_Without_CusDiscount - total_With_Customer_Discount - total_Room_Discount - total_Service_Discount;
        total_VAT = total * 0.1;
        total_Bill = total + total_VAT;

        //IMPORTANT NOTE: To display string.format, setting TextArea with: -fx-font-family: monospace
        string_Total_Bill.append(String.format("-------------------------------------------------------------%n"));
        string_Total_Bill.append(String.format("| %-57s |%n", "Room: " + roomID));
        string_Total_Bill.append(String.format("| %-57s |%n", "Check in ID: " + checkInID));
        string_Total_Bill.append(String.format("-------------------------------------------------------------%n"));
        string_Total_Bill.append(String.format("| %-35s | %19s |%n", "Check in time", roomCheckOut.getCheckInDate()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm:ss"))));
        string_Total_Bill.append(String.format("| %-35s | %19s |%n", "Check out time", LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm:ss"))));
        string_Total_Bill.append(String.format("| %-35s | %19d |%n", "No. Of Days", day_Stay_Print));
        string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "Rent per day", roomCheckOut.getRoomPrice()));
        string_Total_Bill.append(String.format("-------------------------------------------------------------%n"));
        string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "Room charge 1st day", charge_Room_1st_Day));
        string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "Room charge x " + days_Stay + " f.days", total_Room_Normal));
        string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "Room charge last day", charge_Room_Last_Day));
        string_Total_Bill.append(String.format("-------------------------------------------------------------%n"));
        string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "Total Room charge", total_Room));
        string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "Services charge", total_Service));
        string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "(-) Room discount (" + roomCheckOut.getRoomDiscount().multiply(BigDecimal.valueOf(100)) + "%)", total_Room_Discount));
        string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "(-) Services discount", total_Service_Discount));
        string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "(-) Customer discount (" + roomCheckOut.getCusDiscount().multiply(BigDecimal.valueOf(100)) + "%)", total_With_Customer_Discount));
        string_Total_Bill.append(String.format("-------------------------------------------------------------%n"));
        string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "Total bill amount", total));
        string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "(+) VAT (10%)", total_VAT));
        string_Total_Bill.append(String.format("| %-35s | %19.3f |%n", "Payable amount", total_Bill));
        string_Total_Bill.append(String.format("-------------------------------------------------------------%n"));

//        System.out.println(string_Total_Bill);
//        txt_Area_Total_Bill.setText(string_Total_Bill.toString());
//        txt_Total_Bill.setText(String.format("%.3f", total_Bill));
//        txt_Total_Bill.setDisable(true);
//        txt_Tax.setText("0.1");
//        txt_Tax.setDisable(true);
//        txt_Discount.setText(roomCheckOut.getCusDiscount().toString());
//        txt_Discount.setDisable(true);
    }

    private CheckOut get_Check_Out_Data() {
        CheckOut checkOut = new CheckOut();
        checkOut.setCheckInDate(LocalDateTime.of(datePicker_Check_In.getValue(), LocalTime.now()));
        checkOut.setCheckInID(txt_Check_In_ID.getText());
        checkOut.setCheckOutDate(LocalDateTime.now());
        checkOut.setCheckOutID(txt_Check_Out_ID.getText());
        checkOut.setCustomerBill(BigDecimal.valueOf(total_Bill));
        checkOut.setCustomerID(txt_Customer_ID.getText());
        checkOut.setCustomerPayment(comboBox_Payment_Method.getValue());
        checkOut.setDiscount(BigDecimal.valueOf(Double.valueOf(txt_Discount.getText())));
        checkOut.setRoomID(txt_Room_ID.getText());
        checkOut.setTax(BigDecimal.valueOf(Double.valueOf(txt_Tax.getText())));
        checkOut.setUserName(mainFormController.userRole.getEmployee_ID());
        return checkOut;
    }

    private CheckOut get_Check_Out_Data_All(String roomID, String checkInID, String checkOutID) {
        CheckOut checkOut = new CheckOut();
        checkOut.setCheckInDate(LocalDateTime.of(datePicker_Check_In.getValue(), LocalTime.now()));
        checkOut.setCheckInID(checkInID);
        checkOut.setCheckOutDate(LocalDateTime.now());
        checkOut.setCheckOutID(checkOutID);
        checkOut.setCustomerBill(BigDecimal.valueOf(all_total_Bill));
        checkOut.setCustomerID(txt_Customer_ID.getText());
        checkOut.setCustomerPayment(comboBox_Payment_Method.getValue());
        checkOut.setDiscount(BigDecimal.valueOf(Double.valueOf(txt_Discount.getText())));
        checkOut.setRoomID(roomID);
        checkOut.setTax(BigDecimal.valueOf(Double.valueOf(txt_Tax.getText())));
        checkOut.setUserName(mainFormController.userRole.getEmployee_ID());
        return checkOut;
    }

    private Bill get_Bill_Data() {
        Bill bill = new Bill();

        //Setting bill QRCode
        String qrCodeData = txt_Check_Out_ID.getText() + ";" + txt_Customer_ID.getText();
        Image imageQRCode = QRCreate.creatQRCode(qrCodeData);
        BufferedImage bImage = SwingFXUtils.fromFXImage(imageQRCode, null);
        byte[] res;
        try (ByteArrayOutputStream s = new ByteArrayOutputStream()) {
            ImageIO.write(bImage, "png", s);
            res = s.toByteArray();
            Blob blob = new SerialBlob(res);
            bill.setBillQRCode(blob);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(FXMLAddNewServiceTypeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        bill.setCheckInDate(LocalDateTime.of(datePicker_Check_In.getValue(), LocalTime.now()));
        bill.setCheckInID(txt_Check_In_ID.getText());
        bill.setCheckOutDate(LocalDateTime.now());
        bill.setCheckOutID(txt_Check_Out_ID.getText());
        bill.setCustomerChange(BigDecimal.valueOf(Double.valueOf(txt_Customer_Change.getText())));
        bill.setCustomerDiscount(BigDecimal.valueOf(total_With_Customer_Discount));
        bill.setCustomerGive(BigDecimal.valueOf(Double.valueOf(txt_Customer_Give.getText())));
        bill.setCustomerID(txt_Customer_ID.getText());
        bill.setNoOfDay(day_Stay_Print);
        bill.setPayableAmount(BigDecimal.valueOf(total_Bill));
        bill.setRoomCharge(BigDecimal.valueOf(total_Room));
        bill.setRoomDiscount(BigDecimal.valueOf(total_Room_Discount));
        bill.setRoomID(txt_Room_ID.getText());
        bill.setRoomPrice(roomCheckOut.getRoomPrice());
        bill.setServiceCharge(BigDecimal.valueOf(total_Service));
        bill.setServiceDiscount(BigDecimal.valueOf(total_Service_Discount));
        bill.setTotalBillAmount(BigDecimal.valueOf(total));
        bill.setUserName(mainFormController.userRole.getEmployee_ID());
        bill.setVATAmount(BigDecimal.valueOf(total_VAT));
        return bill;
    }

    private Bill get_Bill_Data_All(String roomID, String checkInID, String checkOutID) {
        Bill bill = new Bill();

        //Setting bill QRCode
        String qrCodeData = checkOutID + ";" + txt_Customer_ID.getText();
        Image imageQRCode = QRCreate.creatQRCode(qrCodeData);
        BufferedImage bImage = SwingFXUtils.fromFXImage(imageQRCode, null);
        byte[] res;
        try (ByteArrayOutputStream s = new ByteArrayOutputStream()) {
            ImageIO.write(bImage, "png", s);
            res = s.toByteArray();
            Blob blob = new SerialBlob(res);
            bill.setBillQRCode(blob);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(FXMLAddNewServiceTypeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        bill.setCheckInDate(LocalDateTime.of(datePicker_Check_In.getValue(), LocalTime.now()));
        bill.setCheckInID(checkInID);
        bill.setCheckOutDate(LocalDateTime.now());
        bill.setCheckOutID(checkOutID);
        bill.setCustomerChange(BigDecimal.valueOf(Double.valueOf(txt_Customer_Change.getText())));
        bill.setCustomerDiscount(BigDecimal.valueOf(all_total_With_Customer_Discount));
        bill.setCustomerGive(BigDecimal.valueOf(Double.valueOf(txt_Customer_Give.getText())));
        bill.setCustomerID(txt_Customer_ID.getText());
        bill.setNoOfDay(day_Stay_Print);
        bill.setPayableAmount(BigDecimal.valueOf(all_total_Bill));
        bill.setRoomCharge(BigDecimal.valueOf(all_total_Room));
        bill.setRoomDiscount(BigDecimal.valueOf(all_total_Room_Discount));
        bill.setRoomID(roomID);
        bill.setRoomPrice(roomCheckOut.getRoomPrice());
        bill.setServiceCharge(BigDecimal.valueOf(all_total_Service));
        bill.setServiceDiscount(BigDecimal.valueOf(all_total_Service_Discount));
        bill.setTotalBillAmount(BigDecimal.valueOf(all_total));
        bill.setUserName(mainFormController.userRole.getEmployee_ID());
        bill.setVATAmount(BigDecimal.valueOf(all_total_VAT));
        return bill;
    }

    private void submit_Check_Out() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message");
        alert.setHeaderText("Check out confirmation");
        alert.setContentText("Do you want to check out room: " + txt_Room_ID.getText() + " ?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            // Khai báo stage nhìn xuyên thấu
            StageLoader stageLoader = new StageLoader();
            stageLoader.loadingIndicator("Calculating and report");
            Task loadOverview = new Task() {
                @Override
                protected Object call() throws Exception {
                    Platform.runLater(() -> {
                        if (checkBox_All_Rooms.isSelected()) {
                            StringBuilder bill_Note = new StringBuilder("This bill for these rooms: ");
                            for (CheckIn check_In : list_Check_In_Of_Customer) {
                                bill_Note.append(check_In.getRoomID() + ";" + check_In.getCheckID() + ";");
                            }

                            for (CheckIn check_In : list_Check_In_Of_Customer) {
                                String roomID = check_In.getRoomID();
                                String checkInID = check_In.getCheckID();
                                String checkOutID = "CO-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
                                roomCheckOut = roomDAOImpl.getRoom(roomID);
                                //Update services orders
                                serviceOrderDAOImpl.update_CheckIn_SO_CheckOut(checkInID);
                                serviceOrderDetailDAOImpl.update_CheckIN_SOD_CheckOut(checkInID);

                                //Add checkout                                
                                CheckOut checkOut = get_Check_Out_Data_All(roomID, checkInID, checkOutID);
                                checkOutDAOImpl.addCheckOut(checkOut);
                                DAO.setUserLogs_With_MAC(mainFormController.getUserRole().getEmployee_ID(), "Add new CheckOutID: "
                                        + FormatName.format(checkOut.getCheckOutID()),
                                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), mainFormController.macAdress);

                                //Getting Room infomations to update room Status after checking in - Nhan edit here
                                Room room = new Room();
                                room.setRoomID(roomID);
                                room.setCustomerID("CTM-000000000");
                                room.setUserName(mainFormController.getUserRole().getEmployee_ID());
                                room.setRoomStatus("Out");
                                room.setLeaveDate(LocalDateTime.now());
                                room.setDayRemaining(0);
                                roomDAOImpl.editCheckOutRoom(room, true);
                                mainOverViewPaneController.refreshForm();
                                //Writing Bill to DB
                                Bill bill = get_Bill_Data_All(roomID, checkInID, checkOutID);
                                bill.setNote(bill_Note.toString());
                                billDAOImpl.addBill(bill);
                            }
                        } else {
                            //Update services orders
                            serviceOrderDAOImpl.update_CheckIn_SO_CheckOut(txt_Check_In_ID.getText());
                            serviceOrderDetailDAOImpl.update_CheckIN_SOD_CheckOut(txt_Check_In_ID.getText());
                            //Add checkout
                            CheckOut checkOut = get_Check_Out_Data();
                            checkOutDAOImpl.addCheckOut(checkOut);
                            DAO.setUserLogs_With_MAC(mainFormController.getUserRole().getEmployee_ID(), "Add new CheckOutID: "
                                    + FormatName.format(checkOut.getCheckOutID()),
                                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), mainFormController.macAdress);

                            //Getting Room infomations to update room Status after checking in - Nhan edit here
                            Room room = new Room();
                            room.setRoomID(txt_Room_ID.getText());
                            room.setCustomerID("CTM-000000000");
                            room.setUserName(mainFormController.getUserRole().getEmployee_ID());
                            room.setRoomStatus("Out");
                            room.setLeaveDate(LocalDateTime.now());
                            room.setDayRemaining(0);
                            roomDAOImpl.editCheckOutRoom(room, true);
                            mainOverViewPaneController.refreshForm();
                            //Writing Bill to DB
                            Bill bill = get_Bill_Data();
                            bill.setNote("This bill for only one room.");
                            billDAOImpl.addBill(bill);
                        }
                        //Calling bill report
                        PrintReport viewReport = new PrintReport();
                        viewReport.showReport_Customer_Bill("/src/reports/Bill.jrxml", txt_Check_Out_ID.getText());
                        Stage stage = (Stage) btn_Save.getScene().getWindow();
                        stage.close();
                    });
                    return null;
                }
            };

            loadOverview.setOnSucceeded(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    System.out.println("Finished");
                    Platform.runLater(() -> {
                        stageLoader.stopTimeline();
                        stageLoader.closeStage();
                    });
                }
            });
            new Thread(loadOverview).start();
        }
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
        if (txt_Customer_Give.getText().isEmpty()) {
            txt_Customer_Give.getStyleClass().add("text-field-fault");
            txt_Customer_Give.requestFocus();
        } else {
            submit_Check_Out();
        }
    }

    @FXML
    private void handle_Cancel_Button_Action(ActionEvent event) {
        Stage stage = (Stage) btn_Cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handle_Allow_OverTime_CO_Action(ActionEvent event) {
        string_Total_Bill = new StringBuilder();
        total_Service = 0.0;
        total_Service_Discount = 0.0;
        total_Room = 0.0;
        total_Room_Discount = 0.0;
        charge_Room_1st_Day = 0.0;
        charge_Room_Last_Day = 0.0;
        total_Bill = 0.0;
        total_Without_CusDiscount = 0.0;
        total_With_Customer_Discount = 0.0;
        total = 0.0;
        total_VAT = 0.0;
        day_Stay_Print = 0;
        showUsersData();
    }

    @FXML
    private void handle_All_Rooms_CO_Action(ActionEvent event) {
        string_Total_Bill = new StringBuilder();
        total_Service = 0.0;
        total_Service_Discount = 0.0;
        total_Room = 0.0;
        total_Room_Discount = 0.0;
        charge_Room_1st_Day = 0.0;
        charge_Room_Last_Day = 0.0;
        total_Bill = 0.0;
        total_Without_CusDiscount = 0.0;
        total_With_Customer_Discount = 0.0;
        total = 0.0;
        total_VAT = 0.0;
        day_Stay_Print = 0;

        all_string_Total_Bill = new StringBuilder();
        all_total_Service = 0.0;
        all_total_Service_Discount = 0.0;
        all_total_Room = 0.0;
        all_total_Room_Discount = 0.0;
        all_charge_Room_1st_Day = 0.0;
        all_charge_Room_Last_Day = 0.0;
        all_total_Bill = 0.0;
        all_total_Without_CusDiscount = 0.0;
        all_total_With_Customer_Discount = 0.0;
        all_total = 0.0;
        all_total_VAT = 0.0;
        all_day_Stay_Print = 0;

        showUsersData();
    }

}
