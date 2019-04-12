/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.ConnectControllers.fXMLMainFormController;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
import utils.AlertLoginAgain;
import utils.GetInetAddress;
import utils.StageLoader;
import utils.showFXMLLogin;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class FXMLListBookingController implements Initializable {

    public boolDecentralizationModel userRole;
    RoleDAOImpl roleDAOImpl;
    private showFXMLLogin showFormLogin = new showFXMLLogin();
    ObservableList<BookingInfo> listBooking = FXCollections.observableArrayList();
    @FXML
    private AnchorPane main_AnchorPane;
    @FXML
    private Pagination pagination;
    private static final int ROWS_PER_PAGE = 20;
    private FilteredList<BookingInfo> filteredData;
    @FXML
    private TableView<BookingInfo> table_ListBooking;
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
    public String new_Emp_ID;
    public int row_add_new_index;
    public boolean check_Add_New = false;
    public int pagination_index;
    public int row_index;
    public static Boolean check_formBooking_list = false;
    public static BookingInfo bk;
    private FXMLListBookingVirtualController fXMLListBookingVirtualController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        contextMenu_Main.getItems().remove(menuItem_Edit);
        check_formBooking_list = true;
        setColumns();
        showUsersData();
        ConnectControllers.setfXMLListBookingController(this);
        roleDAOImpl = new RoleDAOImpl();
        // Check item when click on table
        table_ListBooking.setOnMouseClicked((MouseEvent event) -> {
            if ((event.getButton().equals(MouseButton.PRIMARY) || event.getButton().equals(MouseButton.SECONDARY))
                    && table_ListBooking.getSelectionModel().getSelectedItem() != null) {
                menuItem_Edit.setDisable(false);
                menuItem_Delete.setDisable(false);
                bk = table_ListBooking.getSelectionModel().getSelectedItem();
                row_index = table_ListBooking.getSelectionModel().getSelectedIndex();
                pagination_index = pagination.getCurrentPageIndex();
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
        if (!userRole.ischeckBooking_Add()) {
            contextMenu_Main.getItems().remove(menuItem_Add);
        }
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

        sortedData.comparatorProperty().bind(table_ListBooking.comparatorProperty());

        table_ListBooking.setItems(sortedData);

    }

    private void setColumns() {
        TableColumn<BookingInfo, String> bkIdCol = new TableColumn<>("Booking ID");
        TableColumn<BookingInfo, String> cusIdCol = new TableColumn<>("Customer ID");
        TableColumn<BookingInfo, String> cusNameCol = new TableColumn<>("Customer Name");
        TableColumn<BookingInfo, String> roomIdCol = new TableColumn<>("Room ID");
        TableColumn<BookingInfo, String> userCol = new TableColumn<>("User Booking");
        TableColumn<BookingInfo, String> dateCol = new TableColumn<>("Date Booking");
        TableColumn<BookingInfo, String> dateLeaveCol = new TableColumn<>("Date Leave");
        TableColumn<BookingInfo, String> noteCol = new TableColumn<>("Note");
        TableColumn<BookingInfo, Integer> numberGuestCol = new TableColumn<>("Number Guest");
        TableColumn numberCol = new TableColumn("#");
        numberCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<InfoEmployee, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InfoEmployee, String> p) {
                return new ReadOnlyObjectWrapper((table_ListBooking.getItems().indexOf(p.getValue()) + 1) + "");
            }
        });
        numberCol.setSortable(false);

        // Định nghĩa cách để lấy dữ liệu cho mỗi ô.
        // Lấy giá trị từ các thuộc tính của ServiceType.
        bkIdCol.setCellValueFactory(new PropertyValueFactory<>("BookID"));
        cusIdCol.setCellValueFactory(new PropertyValueFactory<>("CusID"));
        roomIdCol.setCellValueFactory(new PropertyValueFactory<>("RoomID"));
        cusNameCol.setCellValueFactory(new PropertyValueFactory<>("CusName"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("User"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));
        dateLeaveCol.setCellValueFactory(new PropertyValueFactory<>("DateLeave"));
        noteCol.setCellValueFactory(new PropertyValueFactory<>("Note"));
        numberGuestCol.setCellValueFactory(new PropertyValueFactory<>("NumGuest"));
        bkIdCol.setPrefWidth(200);
        cusIdCol.setPrefWidth(200);
        cusNameCol.setPrefWidth(120);
        dateCol.setPrefWidth(120);
        numberGuestCol.setPrefWidth(150);
        numberCol.setStyle("-fx-alignment: CENTER;");
        bkIdCol.setStyle("-fx-alignment: CENTER;");
        cusIdCol.setStyle("-fx-alignment: CENTER;");
        roomIdCol.setStyle("-fx-alignment: CENTER;");
        userCol.setStyle("-fx-alignment: CENTER;");
        dateCol.setStyle("-fx-alignment: CENTER;");
        cusNameCol.setStyle("-fx-alignment: CENTER;");
        dateLeaveCol.setStyle("-fx-alignment: CENTER;");
        numberGuestCol.setStyle("-fx-alignment: CENTER;");
        noteCol.setStyle("-fx-alignment: CENTER;");

        // Thêm cột vào bảng
        table_ListBooking.getColumns().clear();
        table_ListBooking.getColumns().addAll(numberCol, bkIdCol, cusIdCol, cusNameCol, roomIdCol, userCol, dateCol, dateLeaveCol, noteCol, numberGuestCol);

        // Xét xắp xếp theo userName
        //userNameCol.setSortType(TableColumn.SortType.DESCENDING);
    }

    public void showUsersData() {
        try {
            listBooking = DAOCustomerBookingCheckIn.getListBooking();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLListEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //table_ServiceType.getItems().clear();
        table_ListBooking.setItems(listBooking);

        //Set filterData and Pagination
        filteredData = new FilteredList<>(listBooking, list -> true);
        FXMLMainFormController mainFormController = ConnectControllers.getfXMLMainFormController();
        mainFormController.getTxt_Search().textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(
                    bk -> newValue == null || newValue.isEmpty()
                    || bk.getBookID().toLowerCase().contains(newValue.toLowerCase())
                    || bk.getCusID().toLowerCase().contains(newValue.toLowerCase())
                    || bk.getCusName().toLowerCase().contains(newValue.toLowerCase())
                    || bk.getDateLeave().toLowerCase().contains(newValue.toLowerCase())
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
        if (check_Add_New) {
            //Setting new room index
            for (BookingInfo item : listBooking) {
                if (new_Emp_ID.equals(item.getBookID())) {
                    row_add_new_index = listBooking.indexOf(item);
                    break;
                }
            }
            Platform.runLater(() -> {
                int focusPage = (int) row_add_new_index / ROWS_PER_PAGE;
                int new_index = row_add_new_index % ROWS_PER_PAGE;
                pagination.setCurrentPageIndex(focusPage);
                changeTableView(focusPage, ROWS_PER_PAGE);
                table_ListBooking.requestFocus();
                table_ListBooking.getSelectionModel().select(new_index);
                table_ListBooking.getFocusModel().focus(new_index);

            });
            check_Add_New = false;
        } else {
            //Forcus to the editing row
            Platform.runLater(() -> {
                //int focusPage = (int) row_index / ROWS_PER_PAGE;
                //int new_index = row_index % ROWS_PER_PAGE;
                pagination.setCurrentPageIndex(pagination_index);
                changeTableView(pagination_index, ROWS_PER_PAGE);
                table_ListBooking.requestFocus();
                table_ListBooking.getSelectionModel().select(row_index);
                table_ListBooking.getFocusModel().focus(row_index);
            });
        }
    }

    @FXML
    private void handle_MenuItem_Edit_Action(ActionEvent event) {
    }

    @FXML
    private void handle_MenuItem_Add_Action(ActionEvent event) {
        StageLoader stageLoader = new StageLoader();
        stageLoader.formLoader("/fxml/FXMLBookingInfo.fxml", "/images/KAN Logo.png", "Add New Booking");
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
            alert.setContentText("Do you want to delete " + bk.getBookID() + "?");
            alert.showAndWait();
            System.out.println(alert.getResult());
            if (alert.getResult() == ButtonType.OK) {
                try {
                    DAOCustomerBookingCheckIn.deleteBookingActive(bk.getBookID());
                    DAO.setUserLogs_With_MAC(FXMLLoginController.User_Login, "Delete Active = 0 for " + bk.getBookID(),
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), GetInetAddress.getMacAddress());
                    System.out.println("Delete successful");
                    showUsersData();
                    if (FXMLListBookingVirtualController.checkformBookingVirtual) {
                        fXMLListBookingVirtualController = ConnectControllers.getfXMLListBookingVirtualController();
                        fXMLListBookingVirtualController.showUsersData();
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(FXMLListEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @FXML
    private void handle_MenuItem_Refresh_Action(ActionEvent event) {
        showUsersData();
    }

}
