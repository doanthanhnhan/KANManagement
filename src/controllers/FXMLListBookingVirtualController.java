/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import static controllers.ConnectControllers.fXMLMainFormController;
import static controllers.FXMLListBookingController.bk;
import static controllers.FXMLListCustomerController.ctm;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.DateCell;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.BookingInfo;
import models.DAO;
import models.DAOCustomerBookingCheckIn;
import models.DAOcheckRole;
import models.InfoEmployee;
import models.RoleDAOImpl;
import models.boolDecentralizationModel;
import models.formatCalender;
import utils.AlertLoginAgain;
import utils.GetInetAddress;
import utils.showFXMLLogin;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class FXMLListBookingVirtualController implements Initializable {

    private static final int ROWS_PER_PAGE = 10;
    private FilteredList<BookingInfo> filteredData;
    ObservableList<BookingInfo> listBooking = FXCollections.observableArrayList();
    public boolDecentralizationModel userRole;
    RoleDAOImpl roleDAOImpl;
    @FXML
    private AnchorPane main_AnchorPane;
    @FXML
    private Pagination pagination;
    @FXML
    private TableView<BookingInfo> table_ListBookingVirtual;
    @FXML
    private ContextMenu contextMenu_Main;
    @FXML
    private MenuItem menuItem_Edit;
    @FXML
    private MenuItem menuItem_Add;
    @FXML
    private MenuItem menuItem_Delete;
    @FXML
    private MenuItem menuItem_Refresh;
    public static BookingInfo bk;
    @FXML
    private JFXDatePicker FromDate;
    @FXML
    private JFXDatePicker ToDate;
    @FXML
    private JFXButton btnSubmit;
    private boolean checkSubmit = false;
    private showFXMLLogin showFormLogin = new showFXMLLogin();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ToDate.valueProperty().addListener((obs, oldItem, newItem) -> {
            ToDate.setStyle("-jfx-default-color: #6447cd;");
        });
        ToDate.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                Format_Show_Booking_Date_Calender();
            }
        });
        FromDate.valueProperty().addListener((obs, oldItem, newItem) -> {
            FromDate.setStyle("-jfx-default-color: #6447cd;");
        });
        FromDate.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                Format_Show_Booking_Date_Calender();
            }
        });
        btnSubmit.setOnAction((event) -> {
            Format_Show_Booking_Date_Calender();
        });
        ToDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) > -1);
            }
        });
        FromDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) > -1);
            }
        });
        contextMenu_Main.getItems().remove(menuItem_Add);
        contextMenu_Main.getItems().remove(menuItem_Edit);
        setColumns();
        showUsersData();
        roleDAOImpl = new RoleDAOImpl();
        // Check item when click on table
        table_ListBookingVirtual.setOnMouseClicked((MouseEvent event) -> {
            if ((event.getButton().equals(MouseButton.PRIMARY) || event.getButton().equals(MouseButton.SECONDARY))
                    && table_ListBookingVirtual.getSelectionModel().getSelectedItem() != null) {
                menuItem_Edit.setDisable(false);
                menuItem_Delete.setDisable(false);
                bk = table_ListBookingVirtual.getSelectionModel().getSelectedItem();
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
        if (!userRole.ischeckBooking_Delete()) {
            contextMenu_Main.getItems().remove(menuItem_Delete);
        }
    }

    private void changeTableView(int index, int limit) {

        int fromIndex = index * limit;
        int toIndex = Math.min(fromIndex + limit, listBooking.size());

        int minIndex = Math.min(toIndex, filteredData.size());

        SortedList<BookingInfo> sortedData = new SortedList<>(
                FXCollections.observableArrayList(filteredData.subList(Math.min(fromIndex, minIndex), minIndex)));

        sortedData.comparatorProperty().bind(table_ListBookingVirtual.comparatorProperty());

        table_ListBookingVirtual.setItems(sortedData);

    }

    private void setColumns() {
        TableColumn<BookingInfo, String> bkIdCol = new TableColumn<>("Booking ID");
        TableColumn<BookingInfo, String> cusIdCol = new TableColumn<>("Customer ID");
        TableColumn<BookingInfo, String> roomIdCol = new TableColumn<>("Room ID");
        TableColumn<BookingInfo, String> userCol = new TableColumn<>("User Booking");
        TableColumn<BookingInfo, String> dateCol = new TableColumn<>("Date Booking");
        TableColumn<BookingInfo, String> noteCol = new TableColumn<>("Note");
        TableColumn<BookingInfo, Integer> numberGuestCol = new TableColumn<>("Number Guest");
        TableColumn numberCol = new TableColumn("#");
        numberCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<InfoEmployee, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InfoEmployee, String> p) {
                return new ReadOnlyObjectWrapper((table_ListBookingVirtual.getItems().indexOf(p.getValue()) + 1) + "");
            }
        });
        numberCol.setSortable(false);

        // Định nghĩa cách để lấy dữ liệu cho mỗi ô.
        // Lấy giá trị từ các thuộc tính của ServiceType.
        bkIdCol.setCellValueFactory(new PropertyValueFactory<>("BookID"));
        cusIdCol.setCellValueFactory(new PropertyValueFactory<>("CusID"));
        roomIdCol.setCellValueFactory(new PropertyValueFactory<>("RoomID"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("User"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));
        noteCol.setCellValueFactory(new PropertyValueFactory<>("Note"));
        numberGuestCol.setCellValueFactory(new PropertyValueFactory<>("NumGuest"));

        numberCol.setStyle("-fx-alignment: CENTER;");
        bkIdCol.setStyle("-fx-alignment: CENTER;");
        cusIdCol.setStyle("-fx-alignment: CENTER;");
        roomIdCol.setStyle("-fx-alignment: CENTER;");
        userCol.setStyle("-fx-alignment: CENTER;");
        dateCol.setStyle("-fx-alignment: CENTER;");
        numberGuestCol.setStyle("-fx-alignment: CENTER;");
        noteCol.setStyle("-fx-alignment: CENTER;");

        // Thêm cột vào bảng
        table_ListBookingVirtual.getColumns().clear();
        table_ListBookingVirtual.getColumns().addAll(numberCol, bkIdCol, cusIdCol, roomIdCol, userCol, dateCol, noteCol, numberGuestCol);

        // Xét xắp xếp theo userName
        //userNameCol.setSortType(TableColumn.SortType.DESCENDING);
    }

    public void showUsersData() {
        try {
            if (!checkSubmit) {
                listBooking = DAOCustomerBookingCheckIn.getListBookingVirtual();
            } else {
                String fromdate = "";
                if (FromDate.getValue() != null) {
                    fromdate = String.valueOf(FromDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                }
                listBooking = DAOCustomerBookingCheckIn.getListBookingVirtual(fromdate, String.valueOf(ToDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLListEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //table_ServiceType.getItems().clear();
        table_ListBookingVirtual.setItems(listBooking);

        //Set filterData and Pagination
        filteredData = new FilteredList<>(listBooking, list -> true);
        FXMLMainFormController mainFormController = ConnectControllers.getfXMLMainFormController();
        mainFormController.getTxt_Search().textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(
                    bk -> newValue == null || newValue.isEmpty()
                    || bk.getBookID().toLowerCase().contains(newValue.toLowerCase())
                    || bk.getCusID().toLowerCase().contains(newValue.toLowerCase())
                    || bk.getDate().toLowerCase().contains(newValue.toLowerCase())
                    || bk.getNumGuest().toString().toLowerCase().contains(newValue.toLowerCase())
                    || bk.getRoomID().toLowerCase().contains(newValue.toLowerCase())
                    || bk.getUser().toLowerCase().contains(newValue.toLowerCase()));
            pagination.setPageCount((int) (Math.ceil(filteredData.size() * 1.0 / ROWS_PER_PAGE)));
            changeTableView(pagination.getCurrentPageIndex(), ROWS_PER_PAGE);
        });
        int totalPage = (int) (Math.ceil(listBooking.size() * 1.0 / ROWS_PER_PAGE));
        pagination.setPageCount(totalPage);
        pagination.setCurrentPageIndex(0);
        changeTableView(0, ROWS_PER_PAGE);
        pagination.currentPageIndexProperty().addListener(
                (observable, oldValue, newValue) -> changeTableView(newValue.intValue(), ROWS_PER_PAGE));
        checkSubmit = false;
    }

    @FXML
    private void handle_MenuItem_Edit_Action(ActionEvent event) {
    }

    @FXML
    private void handle_MenuItem_Add_Action(ActionEvent event) {
    }

    @FXML
    private void handle_MenuItem_Delete_Action(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
        if (!DAOcheckRole.checkRoleDecentralization(FXMLLoginController.User_Login, "Booking_Delete")) {
            AlertLoginAgain.alertLogin();
            fXMLMainFormController = ConnectControllers.getfXMLMainFormController();
            Stage stageMainForm = (Stage) fXMLMainFormController.AnchorPaneMainForm.getScene().getWindow();
            Stage stage = (Stage) main_AnchorPane.getScene().getWindow();
            stage.close();
            stageMainForm.close();
            showFormLogin.showFormLogin();
        } else {
            System.out.println("Kien");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Comfirm");
            alert.setContentText("Do you want to delete " + bk.getBookID()+ "?");
            alert.showAndWait();
            System.out.println(alert.getResult());
            if (alert.getResult() == ButtonType.OK) {
                try {
                    DAOCustomerBookingCheckIn.deleteBooking(bk.getBookID());
                    DAO.setUserLogs_With_MAC(FXMLLoginController.User_Login, "Delete " + bk.getBookID(),
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), GetInetAddress.getMacAddress());
                    System.out.println("Delete successful");
                    showUsersData();
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(FXMLListEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @FXML
    private void handle_MenuItem_Refresh_Action(ActionEvent event) {
        ToDate.setValue(null);
        FromDate.setValue(null);
        showUsersData();
    }

    public void Format_Show_Booking_Date_Calender() {
        boolean check_OK = true;
        if (ToDate.getValue() == null) {
            LocalDate today = LocalDate.now();
            LocalDate yesterday = today.minus(Period.ofDays(1));
            ToDate.setValue(yesterday);
            Format_Show_ToDate_Calender();
        }
        if (FromDate.getValue() != null) {
            if (ToDate.getValue().compareTo(FromDate.getValue()) < 0) {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Error");
                alert1.setHeaderText("You have no right to do this !!!");
                alert1.setContentText("Because From Date Cannot Be Bigger Than Today !!!");
                alert1.showAndWait();
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                ToDate.setStyle("-jfx-default-color: RED;");
                ToDate.requestFocus();
                check_OK = false;
            }
        }
        if (check_OK) {
            checkSubmit = true;
            showUsersData();
        }
    }

    @FXML
    private void Format_Show_ToDate_Calender() {
        String pattern = "dd-MM-yyyy";
        formatCalender.format(pattern, ToDate);
    }

    @FXML
    private void Format_Show_FromDate_Calender() {
        String pattern = "dd-MM-yyyy";
        formatCalender.format(pattern, FromDate);
    }
}
