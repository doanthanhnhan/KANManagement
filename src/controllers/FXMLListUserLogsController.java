/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.ConnectControllers.fXMLMainFormController;
import static controllers.FXMLListCustomerController.ctm;
import static controllers.FXMLListEmployeeController.Emp;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.DAO;
import models.DAOCustomerBookingCheckIn;
import models.DAOcheckRole;
import models.InfoEmployee;
import models.RoleDAOImpl;
import models.UserLogs;
import models.boolDecentralizationModel;
import utils.AlertLoginAgain;
import utils.GetInetAddress;
import utils.showFXMLLogin;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class FXMLListUserLogsController implements Initializable {

    private showFXMLLogin showFormLogin = new showFXMLLogin();
    ObservableList<UserLogs> listUL = FXCollections.observableArrayList();
    public boolDecentralizationModel userRole;
    public static UserLogs UL;
    RoleDAOImpl roleDAOImpl;
    private static final int ROWS_PER_PAGE = 20;
    private FilteredList<UserLogs> filteredData;
    @FXML
    private AnchorPane main_AnchorPane;
    @FXML
    private Pagination pagination;
    @FXML
    private TableView<UserLogs> table_ListUserLogs;
    @FXML
    private ContextMenu contextMenu_Main;
    @FXML
    private MenuItem menuItem_Delete;
    @FXML
    private MenuItem menuItem_Refresh;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setColumns();
        showUsersData();
        roleDAOImpl = new RoleDAOImpl();
        // Check item when click on table
        table_ListUserLogs.setOnMouseClicked((MouseEvent event) -> {
            if ((event.getButton().equals(MouseButton.PRIMARY) || event.getButton().equals(MouseButton.SECONDARY))
                    && table_ListUserLogs.getSelectionModel().getSelectedItem() != null) {
                menuItem_Delete.setDisable(false);
                UL = table_ListUserLogs.getSelectionModel().getSelectedItem();
            } else {
                menuItem_Delete.setDisable(true);
            }
        });
        //Get user role from Mainform
        FXMLMainFormController mainFormController = ConnectControllers.getfXMLMainFormController();
        //userRole = mainFormController.getUserRole();
        userRole = roleDAOImpl.getEmployeeRole(mainFormController.userRole.getEmployee_ID());
        //11.SERVICE TYPE CRUD
        if (!userRole.ischeckEmployee_Delete()) {
            contextMenu_Main.getItems().remove(menuItem_Delete);
        }
    }

    private void changeTableView(int index, int limit) {

        int fromIndex = index * limit;
        int toIndex = Math.min(fromIndex + limit, listUL.size());

        int minIndex = Math.min(toIndex, filteredData.size());

        SortedList<UserLogs> sortedData = new SortedList<>(
                FXCollections.observableArrayList(listUL.subList(Math.min(fromIndex, minIndex), minIndex)));

        sortedData.comparatorProperty().bind(table_ListUserLogs.comparatorProperty());

        table_ListUserLogs.setItems(sortedData);

    }

    private void setColumns() {
        TableColumn<UserLogs, Integer> UlIdCol = new TableColumn<>("ID");
        TableColumn<UserLogs, String> UlUserCol = new TableColumn<>("Username");
        TableColumn<UserLogs, String> UlMacCol = new TableColumn<>("Mac Address");
        TableColumn<UserLogs, String> UlContentCol = new TableColumn<>("Log Content");
        TableColumn<UserLogs, String> UlTimeCol = new TableColumn<>("Log Time");
        TableColumn numberCol = new TableColumn("#");
        numberCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<InfoEmployee, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InfoEmployee, String> p) {
                return new ReadOnlyObjectWrapper((table_ListUserLogs.getItems().indexOf(p.getValue()) + 1) + "");
            }
        });
        numberCol.setSortable(false);

        // Định nghĩa cách để lấy dữ liệu cho mỗi ô.
        // Lấy giá trị từ các thuộc tính của ServiceType.
        UlIdCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        UlUserCol.setCellValueFactory(new PropertyValueFactory<>("UserName"));
        UlMacCol.setCellValueFactory(new PropertyValueFactory<>("MacAddress"));
        UlContentCol.setCellValueFactory(new PropertyValueFactory<>("LogContent"));
        UlTimeCol.setCellValueFactory(new PropertyValueFactory<>("LogTime"));
//        set style
        numberCol.setStyle("-fx-alignment: CENTER-LEFT;");
        UlIdCol.setStyle("-fx-alignment: CENTER-LEFT;");
        UlUserCol.setStyle("-fx-alignment: CENTER-LEFT;");
        UlMacCol.setStyle("-fx-alignment: CENTER-LEFT;");
        UlTimeCol.setStyle("-fx-alignment: CENTER-LEFT;");
        UlContentCol.setStyle("-fx-alignment: CENTER-LEFT;");

        // Thêm cột vào bảng
        table_ListUserLogs.getColumns().clear();
        table_ListUserLogs.getColumns().addAll(numberCol, UlIdCol, UlUserCol, UlMacCol, UlContentCol, UlTimeCol);
        // Xét xắp xếp theo userName
        //userNameCol.setSortType(TableColumn.SortType.DESCENDING);
    }

    public void showUsersData() {
        try {
            listUL = DAOCustomerBookingCheckIn.getAllUserLogs();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLListEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //table_ServiceType.getItems().clear();
        table_ListUserLogs.setItems(listUL);

        //Set filterData and Pagination
        filteredData = new FilteredList<>(listUL, list -> true);
        FXMLMainFormController mainFormController = ConnectControllers.getfXMLMainFormController();
        mainFormController.getTxt_Search().textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(
                    UL -> newValue == null || newValue.isEmpty()
                    || UL.getUserName().toLowerCase().contains(newValue.toLowerCase())
                    || UL.getMacAddress().toLowerCase().contains(newValue.toLowerCase())
                    || UL.getLogContent().toLowerCase().contains(newValue.toLowerCase())
                    || UL.getLogTime().toLowerCase().contains(newValue.toLowerCase()));
            pagination.setPageCount((int) (Math.ceil(filteredData.size() * 1.0 / ROWS_PER_PAGE)));
            changeTableView(pagination.getCurrentPageIndex(), ROWS_PER_PAGE);
        });

        int totalPage = (int) (Math.ceil(listUL.size() * 1.0 / ROWS_PER_PAGE));
        pagination.setPageCount(totalPage);
        pagination.setCurrentPageIndex(0);
        changeTableView(0, ROWS_PER_PAGE);
        pagination.currentPageIndexProperty().addListener(
                (observable, oldValue, newValue) -> changeTableView(newValue.intValue(), ROWS_PER_PAGE));
    }

    @FXML
    private void handle_MenuItem_Delete_Action(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
        if (!DAOcheckRole.checkRoleDecentralization(FXMLLoginController.User_Login, "UserLog_Delete")) {
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
            alert.setContentText("Do you want to delete " + UL.getID() + "?");
            alert.showAndWait();
            System.out.println(alert.getResult());
            if (alert.getResult() == ButtonType.OK) {
                try {
                    DAOCustomerBookingCheckIn.Delete_UserLogs(UL.getID());
                    DAO.setUserLogs_With_MAC(FXMLLoginController.User_Login, "Delete " + UL.getID(),
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
    private void handle_MenuItem_Refresh_Action(ActionEvent event
    ) {
        showUsersData();
    }

}
