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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Customer;
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
public class FXMLListCustomerController implements Initializable {

    @FXML
    private AnchorPane main_AnchorPane;
    @FXML
    private Pagination pagination;
    @FXML
    private TableView<Customer> table_ListCustomer;
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
    public boolDecentralizationModel userRole;
    private showFXMLLogin showFormLogin = new showFXMLLogin();
    RoleDAOImpl roleDAOImpl;
    public static Customer ctm;
    ObservableList<Customer> list_Ctm = FXCollections.observableArrayList();
    private FilteredList<Customer> filteredData;
    private static final int ROWS_PER_PAGE = 10;
    public static Boolean check_Edit_Action = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        FXMLMainFormController.checkRegis = true;
        check_Edit_Action = true;
        setColumns();
        showUsersData();
        ConnectControllers.setfXMLListCustomerController(this);
        roleDAOImpl = new RoleDAOImpl();
        // Check item when click on table
        table_ListCustomer.setOnMouseClicked((MouseEvent event) -> {
            if ((event.getButton().equals(MouseButton.PRIMARY) || event.getButton().equals(MouseButton.SECONDARY))
                    && table_ListCustomer.getSelectionModel().getSelectedItem() != null) {
                menuItem_Edit.setDisable(false);
                menuItem_Delete.setDisable(false);
                ctm = table_ListCustomer.getSelectionModel().getSelectedItem();
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
        if (!userRole.ischeckEmployee_Add()) {
            contextMenu_Main.getItems().remove(menuItem_Add);
        }
        if (!userRole.ischeckEmployee_Delete()) {
            contextMenu_Main.getItems().remove(menuItem_Delete);
        }
        if (!userRole.ischeckEmployee_Edit()) {
            contextMenu_Main.getItems().remove(menuItem_Edit);
        }
    }

    private void changeTableView(int index, int limit) {
        int fromIndex = index * limit;
        int toIndex = Math.min(fromIndex + limit, list_Ctm.size());

        int minIndex = Math.min(toIndex, filteredData.size());

        SortedList<Customer> sortedData = new SortedList<>(
                FXCollections.observableArrayList(filteredData.subList(Math.min(fromIndex, minIndex), minIndex)));

        sortedData.comparatorProperty().bind(table_ListCustomer.comparatorProperty());

        table_ListCustomer.setItems(sortedData);
    }

    private void setColumns() {
        TableColumn<Customer, String> ctmIDCol = new TableColumn<>("Customer ID");
        TableColumn<Customer, String> ctmUserCol = new TableColumn<>("Username");
        TableColumn<Customer, String> ctmFNameCol = new TableColumn<>("Customer FirstName");
        TableColumn<Customer, String> ctmMNameCol = new TableColumn<>("Customer MidName");
        TableColumn<Customer, String> ctmLNameCol = new TableColumn<>("Customer LastName");
        TableColumn<Customer, String> ctmBirthdayCol = new TableColumn<>("Customer Birthday");
        TableColumn<Customer, String> ctmEmailCol = new TableColumn<>("Customer Email");
        TableColumn<Customer, String> ctmPassport = new TableColumn<>("Customer Passport");
        TableColumn<Customer, String> ctmPhoneCol = new TableColumn<>("Customer Phone");
        TableColumn<Customer, Float> ctmDiscountCol = new TableColumn<>("Customer Discount");
        TableColumn<Customer, String> ctmSexCol = new TableColumn<>("Customer Sex");
        TableColumn<Customer, String> ctmCompanyCol = new TableColumn<>("Customer Company");
        TableColumn numberCol = new TableColumn("#");
        numberCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<InfoEmployee, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InfoEmployee, String> p) {
                return new ReadOnlyObjectWrapper((table_ListCustomer.getItems().indexOf(p.getValue()) + 1) + "");
            }
        });
        numberCol.setSortable(false);

        // Định nghĩa cách để lấy dữ liệu cho mỗi ô.
        // Lấy giá trị từ các thuộc tính của ServiceType.
        ctmIDCol.setCellValueFactory(new PropertyValueFactory<>("CusID"));
        ctmUserCol.setCellValueFactory(new PropertyValueFactory<>("User"));
        ctmFNameCol.setCellValueFactory(new PropertyValueFactory<>("FName"));
        ctmMNameCol.setCellValueFactory(new PropertyValueFactory<>("MName"));
        ctmLNameCol.setCellValueFactory(new PropertyValueFactory<>("LName"));
        ctmBirthdayCol.setCellValueFactory(new PropertyValueFactory<>("Date"));
        ctmEmailCol.setCellValueFactory(new PropertyValueFactory<>("Email"));
        ctmPassport.setCellValueFactory(new PropertyValueFactory<>("Passport"));
        ctmPhoneCol.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        ctmDiscountCol.setCellValueFactory(new PropertyValueFactory<>("Discount"));
        ctmCompanyCol.setCellValueFactory(new PropertyValueFactory<>("Company"));
        ctmSexCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Customer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Customer, String> param) {

                if (param.getValue().getSex()) {
                    return new ReadOnlyObjectWrapper("Male");
                }
                return new ReadOnlyObjectWrapper("Female");
            }
        });
        numberCol.setStyle("-fx-alignment: CENTER-LEFT;");
        ctmIDCol.setStyle("-fx-alignment: CENTER-LEFT;");
        ctmUserCol.setStyle("-fx-alignment: CENTER-LEFT;");
        ctmFNameCol.setStyle("-fx-alignment: CENTER-LEFT;");
        ctmMNameCol.setStyle("-fx-alignment: CENTER-LEFT;");
        ctmLNameCol.setStyle("-fx-alignment: CENTER-LEFT;");
        ctmBirthdayCol.setStyle("-fx-alignment: CENTER;");
        ctmEmailCol.setStyle("-fx-alignment: CENTER-LEFT;");
        ctmPassport.setStyle("-fx-alignment: CENTER-LEFT;");
        ctmPhoneCol.setStyle("-fx-alignment: CENTER-LEFT;");
        ctmDiscountCol.setStyle("-fx-alignment: CENTER-LEFT;");
        ctmCompanyCol.setStyle("-fx-alignment: CENTER-LEFT;");
        ctmSexCol.setStyle("-fx-alignment: CENTER-LEFT;");

        // Thêm cột vào bảng
        table_ListCustomer.getColumns().clear();
        table_ListCustomer.getColumns().addAll(numberCol, ctmIDCol, ctmUserCol, ctmFNameCol, ctmMNameCol, ctmLNameCol, ctmBirthdayCol, ctmEmailCol, ctmPassport,
                ctmPhoneCol, ctmDiscountCol, ctmCompanyCol, ctmSexCol);
        // Xét xắp xếp theo userName
        //userNameCol.setSortType(TableColumn.SortType.DESCENDING);
    }

    public void showUsersData() {
        try {
            list_Ctm = DAOCustomerBookingCheckIn.getAllCustomer();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLListEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //table_ServiceType.getItems().clear();
        table_ListCustomer.setItems(list_Ctm);

        //Set filterData and Pagination
        filteredData = new FilteredList<>(list_Ctm, list -> true);
        FXMLMainFormController mainFormController = ConnectControllers.getfXMLMainFormController();
        mainFormController.getTxt_Search().textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(
                    ctm -> newValue == null || newValue.isEmpty()
                    || ctm.getCusID().toLowerCase().contains(newValue.toLowerCase())
                    || ctm.getFName().toLowerCase().contains(newValue.toLowerCase())
                    || ctm.getLName().toLowerCase().contains(newValue.toLowerCase())
                    || ctm.getSex().equals(male_Female(newValue))
                    //                    || ctm.getPassport().toLowerCase().contains(newValue.toLowerCase())
                    || ctm.getUser().toLowerCase().contains(newValue.toLowerCase()));
            pagination.setPageCount((int) (Math.ceil(filteredData.size() * 1.0 / ROWS_PER_PAGE)));
            changeTableView(pagination.getCurrentPageIndex(), ROWS_PER_PAGE);
        });

        int totalPage = (int) (Math.ceil(list_Ctm.size() * 1.0 / ROWS_PER_PAGE));
        pagination.setPageCount(totalPage);
        pagination.setCurrentPageIndex(0);
        changeTableView(0, ROWS_PER_PAGE);
        pagination.currentPageIndexProperty().addListener(
                (observable, oldValue, newValue) -> changeTableView(newValue.intValue(), ROWS_PER_PAGE));
    }

    public Boolean male_Female(String str) {
        if (str.matches("^[m]{1,1}[a]{0,1}[l]{0,1}[e]{0,1}.*$")) {
            return true;
        }
        if (str.matches("^[f]{1,1}[e]{0,1}[m]{0,1}[a]{0,1}[l]{0,1}[e]{0,1}.*$")) {
            return false;
        }
        return null;
    }

    @FXML
    private void handle_MenuItem_Edit_Action(ActionEvent event) {
        StageLoader stageLoader = new StageLoader();
        stageLoader.formLoader("/fxml/FXMLEditCustomer.fxml", "/images/KAN Logo.png", "Edit Customer Informations");
    }

    @FXML
    private void handle_MenuItem_Add_Action(ActionEvent event) {
        StageLoader stageLoader = new StageLoader();
        stageLoader.formLoader("/fxml/FXMLCustomerInfo.fxml", "/images/KAN Logo.png", "Add Customer Informations");
    }

    @FXML
    private void handle_MenuItem_Delete_Action(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
        if (!DAOcheckRole.checkRoleDecentralization(FXMLLoginController.User_Login, "Customer_Delete")) {
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
            alert.setContentText("Do you want to delete " + ctm.getCusID() + "?");
            alert.showAndWait();
            System.out.println(alert.getResult());
            if (alert.getResult() == ButtonType.OK) {
                System.out.println(DAOCustomerBookingCheckIn.check_Remove_Customer(ctm.getCusID()));
                if (!DAOCustomerBookingCheckIn.check_Remove_Customer(ctm.getCusID())) {
                    try {
                        DAOCustomerBookingCheckIn.deleteCustomer(ctm.getCusID());
                        DAO.setUserLogs_With_MAC(FXMLLoginController.User_Login, "Delete " + ctm.getCusID(),
                                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), GetInetAddress.getMacAddress());
                        System.out.println("Delete successful");
                        showUsersData();
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(FXMLListEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Error");
                    alert1.setHeaderText("You have no right to do this !!!");
                    alert1.setContentText("Because This customer is booking or staying in the hotel !!!");
                    alert1.showAndWait();
                }
            }
        }
    }

    @FXML
    private void handle_MenuItem_Refresh_Action(ActionEvent event) {
        showUsersData();
    }

}
