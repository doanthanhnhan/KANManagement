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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.DAO;
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
 * @author Doan Thanh Nhan
 */
public class FXMLListEmployeeController implements Initializable {

    public boolDecentralizationModel userRole;
    RoleDAOImpl roleDAOImpl;
    private showFXMLLogin showFormLogin = new showFXMLLogin();
    ObservableList<InfoEmployee> listEmp = FXCollections.observableArrayList();
    public static Boolean check_Edit_Action = false;
    public static InfoEmployee Emp;
    public static Boolean check_form_list = false;
    private static final int ROWS_PER_PAGE = 4;
    private FilteredList<InfoEmployee> filteredData;

    @FXML
    public TableView<InfoEmployee> table_ListEmployee;
    @FXML
    private MenuItem menuItem_Edit;
    @FXML
    private MenuItem menuItem_Add;
    @FXML
    private MenuItem menuItem_Delete;
    @FXML
    private AnchorPane main_AnchorPane;
    @FXML
    private MenuItem menuItem_Refresh;
    @FXML
    private Pagination pagination;
    @FXML
    private ContextMenu contextMenu_Main;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        check_Edit_Action = true;
        setColumns();
        showUsersData();
        ConnectControllers.setfXMLListEmployeeController(this);
        roleDAOImpl = new RoleDAOImpl();
        // Check item when click on table
        table_ListEmployee.setOnMouseClicked((MouseEvent event) -> {
            if ((event.getButton().equals(MouseButton.PRIMARY) || event.getButton().equals(MouseButton.SECONDARY))
                    && table_ListEmployee.getSelectionModel().getSelectedItem() != null) {
                menuItem_Edit.setDisable(false);
                menuItem_Delete.setDisable(false);
                Emp = table_ListEmployee.getSelectionModel().getSelectedItem();
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
        if (!userRole.ischeckSType_View()) {
//            contextMenu_Main.getItems().remove(menuItem_List_Service_Type);
        }
    }

    private void changeTableView(int index, int limit) {

        int fromIndex = index * limit;
        int toIndex = Math.min(fromIndex + limit, listEmp.size());

        int minIndex = Math.min(toIndex, filteredData.size());

        SortedList<InfoEmployee> sortedData = new SortedList<>(
                FXCollections.observableArrayList(filteredData.subList(Math.min(fromIndex, minIndex), minIndex)));

        sortedData.comparatorProperty().bind(table_ListEmployee.comparatorProperty());

        table_ListEmployee.setItems(sortedData);

    }

    private void setColumns() {
        TableColumn<InfoEmployee, String> empIDCol = new TableColumn<>("Emp ID");
        TableColumn<InfoEmployee, String> empFNameCol = new TableColumn<>("Emp FirstName");
        TableColumn<InfoEmployee, String> empMNameCol = new TableColumn<>("Emp MidName");
        TableColumn<InfoEmployee, String> empLNameCol = new TableColumn<>("Emp LastName");
        TableColumn<InfoEmployee, String> empEmailCol = new TableColumn<>("Emp Email");
        TableColumn<InfoEmployee, String> empDepartIdCol = new TableColumn<>("Emp DepartmentID");
        TableColumn<InfoEmployee, String> empPhoneCol = new TableColumn<>("Emp Phone");
        TableColumn<InfoEmployee, String> empAddressCol = new TableColumn<>("Emp Address");
        TableColumn<InfoEmployee, String> empIdNumberCol = new TableColumn<>("Emp IDNumber");
        TableColumn<InfoEmployee, String> empHireDateCol = new TableColumn<>("Emp HireDate");
        TableColumn<InfoEmployee, String> empJobCol = new TableColumn<>("Emp Job");
        TableColumn<InfoEmployee, String> empEduLevelCol = new TableColumn<>("Emp EducatedLevel");
        TableColumn<InfoEmployee, String> empSexCol = new TableColumn<>("Emp Sex");
        TableColumn<InfoEmployee, String> empBirthdayCol = new TableColumn<>("Emp Birthday");
        TableColumn<InfoEmployee, Double> empSalaryCol = new TableColumn<>("Emp Salary");
        TableColumn<InfoEmployee, Double> empBonusCol = new TableColumn<>("Emp Bonus");
        TableColumn<InfoEmployee, String> empCommCol = new TableColumn<>("Emp Comm");
        TableColumn<InfoEmployee, ImageView> empImageCol = new TableColumn<>("Emp Image");
        TableColumn numberCol = new TableColumn("#");
        numberCol.setCellValueFactory(new Callback<CellDataFeatures<InfoEmployee, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<InfoEmployee, String> p) {
                return new ReadOnlyObjectWrapper((table_ListEmployee.getItems().indexOf(p.getValue()) + 1) + "");
            }
        });
        numberCol.setSortable(false);

        // Định nghĩa cách để lấy dữ liệu cho mỗi ô.
        // Lấy giá trị từ các thuộc tính của ServiceType.
        empIDCol.setCellValueFactory(new PropertyValueFactory<>("Employee_ID"));
        empFNameCol.setCellValueFactory(new PropertyValueFactory<>("First_Name"));
        empMNameCol.setCellValueFactory(new PropertyValueFactory<>("Mid_Name"));
        empLNameCol.setCellValueFactory(new PropertyValueFactory<>("Last_Name"));
        empEmailCol.setCellValueFactory(new PropertyValueFactory<>("Gmail"));
        empDepartIdCol.setCellValueFactory(new PropertyValueFactory<>("Work_Dept"));
        empPhoneCol.setCellValueFactory(new PropertyValueFactory<>("Phone_No"));
        empAddressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
        empIdNumberCol.setCellValueFactory(new PropertyValueFactory<>("Id_number"));
        empHireDateCol.setCellValueFactory(new PropertyValueFactory<>("Hiredate"));
        empEduLevelCol.setCellValueFactory(new PropertyValueFactory<>("EDLEVEL"));
        empJobCol.setCellValueFactory(new PropertyValueFactory<>("Job"));
        empSexCol.setCellValueFactory(new Callback<CellDataFeatures<InfoEmployee, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<InfoEmployee, String> param) {

                if (param.getValue().getSex()) {
                    return new ReadOnlyObjectWrapper("Male");
                }
                return new ReadOnlyObjectWrapper("Female");
            }
        });
        empBirthdayCol.setCellValueFactory(new PropertyValueFactory<>("Birthdate"));
        empSalaryCol.setCellValueFactory(new PropertyValueFactory<>("Salary"));
        empBonusCol.setCellValueFactory(new PropertyValueFactory<>("Bonus"));
        empCommCol.setCellValueFactory(new PropertyValueFactory<>("Comm"));
        empImageCol.setCellValueFactory(new PropertyValueFactory<>("imageView"));
        numberCol.setStyle("-fx-alignment: CENTER-LEFT;");
        empIDCol.setStyle("-fx-alignment: CENTER-LEFT;");
        empFNameCol.setStyle("-fx-alignment: CENTER-LEFT;");
        empMNameCol.setStyle("-fx-alignment: CENTER-LEFT;");
        empLNameCol.setStyle("-fx-alignment: CENTER-LEFT;");
        empEmailCol.setStyle("-fx-alignment: CENTER-LEFT;");
        empEmailCol.setPrefWidth(200);
        empDepartIdCol.setStyle("-fx-alignment: CENTER;");
        empSexCol.setStyle("-fx-alignment: CENTER-LEFT;");
        empBirthdayCol.setStyle("-fx-alignment: CENTER-LEFT;");
        empSalaryCol.setStyle("-fx-alignment: CENTER-LEFT;");
        empBonusCol.setStyle("-fx-alignment: CENTER-LEFT;");
        empCommCol.setStyle("-fx-alignment: CENTER-LEFT;");
        empImageCol.setStyle("-fx-alignment: CENTER-LEFT;");
        empPhoneCol.setStyle("-fx-alignment: CENTER-LEFT;");
        empAddressCol.setStyle("-fx-alignment: CENTER-LEFT;");
        empIdNumberCol.setStyle("-fx-alignment: CENTER-LEFT;");
        empHireDateCol.setStyle("-fx-alignment: CENTER-LEFT;");
        empEduLevelCol.setStyle("-fx-alignment: CENTER-LEFT;");
        empJobCol.setStyle("-fx-alignment: CENTER-LEFT;");

        // Thêm cột vào bảng
        table_ListEmployee.getColumns().clear();
        table_ListEmployee.getColumns().addAll(numberCol, empIDCol, empFNameCol, empMNameCol, empLNameCol, empEmailCol, empDepartIdCol, empPhoneCol, empAddressCol,
                empIdNumberCol, empHireDateCol, empEduLevelCol, empJobCol, empSexCol, empBirthdayCol, empSalaryCol, empBonusCol, empCommCol, empImageCol);

        // Xét xắp xếp theo userName
        //userNameCol.setSortType(TableColumn.SortType.DESCENDING);
    }

    public void showUsersData() {
        try {
            listEmp = DAO.getAllInfoEmployee(FXMLLoginController.User_Login);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLListEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //table_ServiceType.getItems().clear();
        table_ListEmployee.setItems(listEmp);

        //Set filterData and Pagination
        filteredData = new FilteredList<>(listEmp, list -> true);
        FXMLMainFormController mainFormController = ConnectControllers.getfXMLMainFormController();
        mainFormController.getTxt_Search().textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(
                    Emp -> newValue == null || newValue.isEmpty()
                    || Emp.getEmployee_ID().toLowerCase().contains(newValue.toLowerCase())
                    || Emp.getFirst_Name().toLowerCase().contains(newValue.toLowerCase())
                    || Emp.getMid_Name().toLowerCase().contains(newValue.toLowerCase())
                    || Emp.getGmail().toLowerCase().contains(newValue.toLowerCase())
                    || Emp.getSex().equals(male_Female(newValue))
                    || Emp.getLast_Name().toLowerCase().contains(newValue.toLowerCase())
                    || Emp.getAddress().toLowerCase().contains(newValue.toLowerCase())
                    || Emp.getBonus().toLowerCase().contains(newValue.toLowerCase())
                    || Emp.getSalary().toLowerCase().contains(newValue.toLowerCase())
                    || Emp.getJob().toLowerCase().contains(newValue.toLowerCase())
                    || Emp.getWork_Dept().toLowerCase().contains(newValue.toLowerCase())
                    || Emp.getPhone_No().toLowerCase().contains(newValue.toLowerCase()));
            pagination.setPageCount((int) (Math.ceil(filteredData.size() * 1.0 / ROWS_PER_PAGE)));
            changeTableView(pagination.getCurrentPageIndex(), ROWS_PER_PAGE);
        });

        int totalPage = (int) (Math.ceil(listEmp.size() * 1.0 / ROWS_PER_PAGE));
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
        FXMLInfoEmployeeController.check_delete = false;
        check_form_list = true;
        StageLoader stageLoader = new StageLoader();
        stageLoader.formLoader("/fxml/FXMLInfoEmployee.fxml", "/images/KAN Logo.png", "Edit Service Type Informations");

    }

    @FXML
    private void handle_MenuItem_Add_Action(ActionEvent event) {
        StageLoader stageLoader = new StageLoader();
        stageLoader.formLoader("/fxml/FXMLAddNewEmployee.fxml", "/images/KAN Logo.png", "Edit Service Type Informations");
    }

    @FXML
    private void handle_MenuItem_Delete_Action(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
        if (!DAOcheckRole.checkRoleDecentralization(FXMLLoginController.User_Login, "Employee_Delete")) {
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
            alert.setContentText("Do you want to delete " + Emp.getEmployee_ID() + "?");
            alert.showAndWait();
            System.out.println(alert.getResult());
            if (alert.getResult() == ButtonType.OK) {
                try {
                    DAO.delete_Employee(Emp.getEmployee_ID());
                    DAO.setUserLogs_With_MAC(FXMLLoginController.User_Login, "Delete " + Emp.getEmployee_ID(),
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
        showUsersData();
    }

}
