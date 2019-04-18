/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import static controllers.FXMLListBookingVirtualController.checkformBookingVirtual;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DateCell;
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
import javafx.util.Callback;
import models.BookingInfo;
import models.CheckIn;
import models.DAOCustomerBookingCheckIn;
import models.InfoEmployee;
import models.formatCalender;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class FXMLListCheckInController implements Initializable {

    private static final int ROWS_PER_PAGE = 20;
    private FilteredList<CheckIn> filteredData;
    ObservableList<CheckIn> listCheckIn = FXCollections.observableArrayList();
    public static CheckIn ci;
    @FXML
    private AnchorPane main_AnchorPane;
    @FXML
    private Pagination pagination;
    @FXML
    private TableView<CheckIn> table_ListCheckIn;
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
    @FXML
    private JFXDatePicker FromDate;
    @FXML
    private JFXDatePicker ToDate;
    @FXML
    private JFXButton btnSubmit;
    private boolean checkSubmit = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ConnectControllers.setfXMLListCheckInController(this);
        checkformBookingVirtual = true;
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
                setDisable(empty || date.compareTo(today) > 0);
            }
        });
        FromDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) > 0);
            }
        });
        contextMenu_Main.getItems().remove(menuItem_Add);
        contextMenu_Main.getItems().remove(menuItem_Edit);
        contextMenu_Main.getItems().remove(menuItem_Delete);
        setColumns();
        showUsersData();
        // Check item when click on table
        table_ListCheckIn.setOnMouseClicked((MouseEvent event) -> {
            if ((event.getButton().equals(MouseButton.PRIMARY) || event.getButton().equals(MouseButton.SECONDARY))
                    && table_ListCheckIn.getSelectionModel().getSelectedItem() != null) {
                menuItem_Edit.setDisable(false);
                menuItem_Delete.setDisable(false);
                ci = table_ListCheckIn.getSelectionModel().getSelectedItem();
            } else {
                menuItem_Edit.setDisable(true);
                menuItem_Delete.setDisable(true);
            }
        });
        //Get user role from Mainform
        FXMLMainFormController mainFormController = ConnectControllers.getfXMLMainFormController();
        //userRole = mainFormController.getUserRole();
//        userRole = roleDAOImpl.getEmployeeRole(mainFormController.userRole.getEmployee_ID());
//        //11.SERVICE TYPE CRUD
//        if (!userRole.ischeckBooking_Delete()) {
//            contextMenu_Main.getItems().remove(menuItem_Delete);
//        }
    }

    private void changeTableView(int index, int limit) {

        int fromIndex = index * limit;
        int toIndex = Math.min(fromIndex + limit, listCheckIn.size());

        int minIndex = Math.min(toIndex, filteredData.size());

        SortedList<CheckIn> sortedData = new SortedList<>(
                FXCollections.observableArrayList(filteredData.subList(Math.min(fromIndex, minIndex), minIndex)));

        sortedData.comparatorProperty().bind(table_ListCheckIn.comparatorProperty());

        table_ListCheckIn.setItems(sortedData);

    }

    private void setColumns() {
        TableColumn<CheckIn, String> ciIdCol = new TableColumn<>("CheckIn ID");
        TableColumn<CheckIn, String> ciIdBookCol = new TableColumn<>("Booking ID");
        TableColumn<CheckIn, String> ciIdCusCol = new TableColumn<>("Customer ID");
        TableColumn<CheckIn, String> ciroomIdCol = new TableColumn<>("Room ID");
        TableColumn<CheckIn, String> ciuserCol = new TableColumn<>("User CheckIn");
        TableColumn<CheckIn, String> cusPackCol = new TableColumn<>("Customer Package");
        TableColumn<CheckIn, String> ciCheckTypeCol = new TableColumn<>("CheckType");
        TableColumn<CheckIn, String> cidateCol = new TableColumn<>("Date In");
        TableColumn<CheckIn, String> cidateLeaveCol = new TableColumn<>("Date Out");
        TableColumn<CheckIn, Integer> cinumberGuestCol = new TableColumn<>("Number Guest");
        TableColumn numberCol = new TableColumn("#");
        numberCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<InfoEmployee, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InfoEmployee, String> p) {
                return new ReadOnlyObjectWrapper((table_ListCheckIn.getItems().indexOf(p.getValue()) + 1) + "");
            }
        });
        numberCol.setSortable(false);

        // Định nghĩa cách để lấy dữ liệu cho mỗi ô.
        // Lấy giá trị từ các thuộc tính của ServiceType.
        ciIdCol.setCellValueFactory(new PropertyValueFactory<>("CheckID"));
        ciIdBookCol.setCellValueFactory(new PropertyValueFactory<>("BookID"));
        ciIdCusCol.setCellValueFactory(new PropertyValueFactory<>("CusID"));
        ciroomIdCol.setCellValueFactory(new PropertyValueFactory<>("RoomID"));
        ciuserCol.setCellValueFactory(new PropertyValueFactory<>("User"));
        cidateCol.setCellValueFactory(new PropertyValueFactory<>("DateIn"));
        cidateLeaveCol.setCellValueFactory(new PropertyValueFactory<>("DateOut"));
        ciCheckTypeCol.setCellValueFactory(new PropertyValueFactory<>("CheckType"));
        cusPackCol.setCellValueFactory(new PropertyValueFactory<>("CusPack"));
        cinumberGuestCol.setCellValueFactory(new PropertyValueFactory<>("NumberOfCustomer"));
        ciIdCol.setPrefWidth(220);
        ciIdBookCol.setPrefWidth(190);
        ciIdCusCol.setPrefWidth(190);
        cidateCol.setPrefWidth(180);
        cidateLeaveCol.setPrefWidth(180);
        cinumberGuestCol.setPrefWidth(130);
        cusPackCol.setPrefWidth(130);
        numberCol.setStyle("-fx-alignment: CENTER;");
        ciIdCol.setStyle("-fx-alignment: CENTER;");
        ciIdBookCol.setStyle("-fx-alignment: CENTER;");
        ciIdCusCol.setStyle("-fx-alignment: CENTER;");
        ciroomIdCol.setStyle("-fx-alignment: CENTER;");
        ciuserCol.setStyle("-fx-alignment: CENTER;");
        cidateCol.setStyle("-fx-alignment: CENTER;");
        cidateLeaveCol.setStyle("-fx-alignment: CENTER;");
        ciCheckTypeCol.setStyle("-fx-alignment: CENTER;");
        cusPackCol.setStyle("-fx-alignment: CENTER;");
        cinumberGuestCol.setStyle("-fx-alignment: CENTER;");
        // Thêm cột vào bảng
        table_ListCheckIn.getColumns().clear();
        table_ListCheckIn.getColumns().addAll(numberCol, ciIdCol, ciIdBookCol, ciIdCusCol, ciuserCol, ciroomIdCol, ciCheckTypeCol, cidateCol, cidateLeaveCol, cinumberGuestCol, cusPackCol);

        // Xét xắp xếp theo userName
        //userNameCol.setSortType(TableColumn.SortType.DESCENDING);
    }

    public void showUsersData() {
        try {
            if (!checkSubmit) {
                listCheckIn = DAOCustomerBookingCheckIn.getListCheckIn();
            } else {
                String fromdate = "";
                String today = "";
                if (ToDate.getValue() == null && FromDate.getValue() == null) {
                    today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    ToDate.setValue(LocalDate.now());
                    Format_Show_ToDate_Calender();
                    listCheckIn = DAOCustomerBookingCheckIn.getListCheckInForDate(fromdate, today);
                } else if (FromDate.getValue() != null && ToDate.getValue() == null) {
                    today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    ToDate.setValue(LocalDate.now());
                    Format_Show_ToDate_Calender();
                    fromdate = String.valueOf(FromDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))) + " 00:00:00";
                    listCheckIn = DAOCustomerBookingCheckIn.getListCheckInForDate(fromdate, today);
                } else if (FromDate.getValue() != null && ToDate.getValue() != null) {
                    today = String.valueOf(ToDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))) + " 23:59:59";
                    fromdate = String.valueOf(FromDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))) + " 00:00:00";
                    listCheckIn = DAOCustomerBookingCheckIn.getListCheckInForDate(fromdate, today);
                } else if (FromDate.getValue() == null && ToDate.getValue() != null) {
                    today = String.valueOf(ToDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))) + " 23:59:59";
                    listCheckIn = DAOCustomerBookingCheckIn.getListCheckInForDate(fromdate, today);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLListCheckInController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //table_ServiceType.getItems().clear();
        table_ListCheckIn.setItems(listCheckIn);

        //Set filterData and Pagination
        filteredData = new FilteredList<>(listCheckIn, list -> true);
        FXMLMainFormController mainFormController = ConnectControllers.getfXMLMainFormController();
        mainFormController.getTxt_Search().textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(
                    ci -> newValue == null || newValue.isEmpty()
                    || ci.getBookID().toLowerCase().contains(newValue.toLowerCase())
                    || ci.getCusID().toLowerCase().contains(newValue.toLowerCase())
                    || ci.getCheckID().toLowerCase().contains(newValue.toLowerCase())
                    || ci.getDateIn().toLowerCase().contains(newValue.toLowerCase())
                    || ci.getDateOut().toLowerCase().contains(newValue.toLowerCase())
                    || ci.getNumberOfCustomer().toString().toLowerCase().contains(newValue.toLowerCase())
                    || ci.getRoomID().toLowerCase().contains(newValue.toLowerCase())
                    || ci.getCheckType().toLowerCase().contains(newValue.toLowerCase())
                    || ci.getUser().toLowerCase().contains(newValue.toLowerCase()));
            pagination.setPageCount((int) (Math.ceil(filteredData.size() * 1.0 / ROWS_PER_PAGE)));
            changeTableView(pagination.getCurrentPageIndex(), ROWS_PER_PAGE);
        });
        int totalPage = (int) (Math.ceil(listCheckIn.size() * 1.0 / ROWS_PER_PAGE));
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
    private void handle_MenuItem_Delete_Action(ActionEvent event) {
    }

    public void Format_Show_Booking_Date_Calender() {
        boolean check_OK = true;
        if (FromDate.getValue() != null && ToDate.getValue() != null) {
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
    private void handle_MenuItem_Refresh_Action(ActionEvent event) {
        ToDate.setValue(null);
        FromDate.setValue(null);
        ToDate.setPromptText("To Date");
        FromDate.setPromptText("From Date");
        showUsersData();
    }

    @FXML
    private void Format_Show_FromDate_Calender() {
        String pattern = "dd-MM-yyyy";
        formatCalender.format(pattern, FromDate);
        ToDate.setPromptText("To Date");
        FromDate.setPromptText("From Date");
    }

    @FXML
    private void Format_Show_ToDate_Calender() {
        String pattern = "dd-MM-yyyy";
        formatCalender.format(pattern, ToDate);
        ToDate.setPromptText("To Date");
        FromDate.setPromptText("From Date");
    }

}
