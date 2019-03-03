/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.DAO;
import models.InfoEmployee;
import models.formatCalender;
import utils.FormatName;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class FXMLInfoEmployeeController implements Initializable {

    @FXML
    private JFXTextField newPhone;
    @FXML
    private JFXDatePicker birthday;
    private JFXTextField newMidname;
    @FXML
    private HBox HboxContent;
    @FXML
    private HBox Hboxbtn;
    @FXML
    private JFXButton btnInfo;
    private Pattern pattern;
    private Pattern patternEmail;
    private Pattern patternFLName;
    private Pattern patternIDNumber;
    private Pattern patternELevel;
    private Pattern patternSalary;
    @FXML
    private VBox vBox_Employee_Info;
    @FXML
    private VBox vBox_Info_Left;
    @FXML
    private JFXComboBox<String> boxId;
    @FXML
    private JFXTextField address;
    @FXML
    private JFXTextField IdNumber;
    @FXML
    private VBox vBox_Info_Right;
    @FXML
    private JFXTextField FirstName;
    @FXML
    private JFXTextField MidName;
    @FXML
    private JFXTextField LastName;
    @FXML
    private JFXDatePicker Hiredate;
    @FXML
    private JFXTextField Job;
    @FXML
    private JFXTextField Salary;
    @FXML
    private JFXTextField Bonus;
    @FXML
    private HBox hBox_Info_Parent;
    @FXML
    private JFXRadioButton Male;
    @FXML
    private JFXRadioButton Female;
    @FXML
    private ToggleGroup Sex;
    @FXML
    private JFXTextField Email;
    @FXML
    private JFXTextField DepartmentId;
    @FXML
    private JFXTextField EducatedLevel;
    @FXML
    private JFXTextField Comm;
    @FXML
    private JFXComboBox<String> boxRole;
    @FXML
    private JFXButton btnCancel;
    private ObservableList<InfoEmployee> list_info = FXCollections.observableArrayList();
    private ObservableList<String> list_User = FXCollections.observableArrayList();
    private ObservableList<InfoEmployee> list_login = FXCollections.observableArrayList();
    private ObservableList<InfoEmployee> list_login_update = FXCollections.observableArrayList();
    private ObservableList<String> list_Role = FXCollections.observableArrayList();
    public static Boolean validateInfoEmployee = false;
    public static Boolean check_delete = false;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        list_info = FXMLLoginController.List_Employee;
        list_login = FXMLLoginController.List_EmployeeLogin;
        for (InfoEmployee infoEmployee : list_info) {
            if (infoEmployee.getUserName().equals(list_login.get(0).getUserName())) {
                list_login_update.add(infoEmployee);
            }
        }

        list_info.forEach((InfoEmployee infoEmployee) -> {
            list_User.add(infoEmployee.getUserName());
        });
        if (FXMLMainFormController.checkRegis) {
            FXMLMainFormController.checkRegis = false;
            if (!list_login_update.get(0).getRole().equals("Admin")) {
                hBox_Info_Parent.getChildren().remove(vBox_Info_Right);
                boxId.setDisable(true);
                boxId.setValue(list_login_update.get(0).getUserName());
                check_delete = true;
                newPhone.setText(list_login_update.get(0).getPhone_No());
                address.setText(list_login_update.get(0).getAddress());
                IdNumber.setText(list_login_update.get(0).getId_number());
                if (list_login_update.get(0).getBirthdate() != null) {
                    birthday.setValue(LocalDate.parse(list_login_update.get(0).getBirthdate()));
                }
                if (list_login_update.get(0).getHiredate() != null) {
                    Hiredate.setValue(LocalDate.parse(list_login_update.get(0).getBirthdate()));
                }
                if (list_login_update.get(0).getSex().equals("Male")) {
                    Male.setSelected(true);
                } else {
                    Female.setSelected(true);
                }
                btnInfo.setOnAction((event) -> {
                    validateForm();
                    check_delete = false;
                    if (validateInfoEmployee) {
                        // get a handle to the stage
                        Stage stage = (Stage) btnInfo.getScene().getWindow();
                        // do what you have to do
                        stage.hide();

                    }
                });
            } else {
                btnInfo.setOnAction((event) -> {
                    FXMLMainFormController.checkRegis = true;
                    validateForm();
                    check_delete = false;
                });
            }

        }
        if (FXMLLoginController.checkLoginRegis) {
            hBox_Info_Parent.getChildren().remove(vBox_Info_Right);
            Hboxbtn.getChildren().remove(btnCancel);
            boxId.setDisable(true);
            boxId.setValue(list_login.get(0).getUserName());
            check_delete = true;
            FXMLLoginController.checkLoginRegis = false;
            btnInfo.setOnAction((event) -> {
                validateForm();
                check_delete = false;
                // get a handle to the stage
                Stage stageInfo = (Stage) btnInfo.getScene().getWindow();
                // do what you have to do
                stageInfo.hide();
                if (validateInfoEmployee) {
                    try {
                        Stage stage = new Stage();
                        Parent root = FXMLLoader.load(getClass().getResource("/fxml/FXMLMainForm.fxml"));
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLInfoEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

        }

        if (!list_login_update.get(0).getRole().equals("Admin")) {
            hBox_Info_Parent.getChildren().remove(vBox_Info_Right);
            boxId.setDisable(true);
            boxId.setValue(list_login_update.get(0).getUserName());
            Hboxbtn.getChildren().remove(btnCancel);
        }
        try {
            list_Role = (ObservableList<String>) DAO.getAllRole();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLInfoEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        boxRole.setItems(list_Role);
        boxId.setItems(list_User);
//        hBox_Info_Parent.getChildren().remove(vBox_Info_Right);
        newPhone.setOnKeyPressed((KeyEvent event) -> {
            newPhone.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                btnInfoEmployee();
            }
        });
        birthday.valueProperty().addListener((obs, oldItem, newItem) -> {
            birthday.setStyle("-jfx-default-color: #6447cd;");
            HboxContent.getChildren().clear();
        });
        birthday.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                btnInfoEmployee();
            }
        });

        // TODO
    }

    @FXML
    private void Format_Show_Calender() {
        String pattern = "dd-MM-yyyy";
        formatCalender.format(pattern, birthday);
    }

    @FXML
    private void Format_Show_Calender_Hiredate() {
        String pattern = "dd-MM-yyyy";
        formatCalender.format(pattern, Hiredate);
    }

    @FXML
    private void Cancel() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // do what you have to do
        stage.hide();
    }

    @FXML
    private void select_Id() {
        for (InfoEmployee infoEmployee : list_info) {
            if (boxId.getValue().equals(infoEmployee.getEmployee_ID())) {
                newPhone.setText(infoEmployee.getPhone_No());
                if (infoEmployee.getBirthdate() != null) {
                    birthday.setValue(LocalDate.parse(infoEmployee.getBirthdate()));
                }
                if (infoEmployee.getHiredate() != null) {
                    Hiredate.setValue(LocalDate.parse(infoEmployee.getBirthdate()));
                }
                if (infoEmployee.getSex().equals("Male")) {
                    Male.setSelected(true);
                } else {
                    Female.setSelected(true);
                }
                address.setText(infoEmployee.getAddress());
                IdNumber.setText(infoEmployee.getId_number());
                FirstName.setText(infoEmployee.getFirst_Name());
                LastName.setText(infoEmployee.getLast_Name());
                MidName.setText(infoEmployee.getMid_Name());
                Email.setText(infoEmployee.getGmail());
                DepartmentId.setText(infoEmployee.getWork_Dept());
                Job.setText(infoEmployee.getJob());
                EducatedLevel.setText(String.valueOf(infoEmployee.getEDLEVEL()));
                Salary.setText(infoEmployee.getSalary());
                Bonus.setText(infoEmployee.getBonus());
                Comm.setText(infoEmployee.getComm());
                boxRole.setValue(infoEmployee.getRole());
                break;
            }
        }
    }

    @FXML
    private void btnInfoEmployee() {
        validateForm();
    }

    private void validateForm() {
        pattern = Pattern.compile("^([0-9][0-9]{1,19}$)|\\+84[0-9]{1,17}$");
        patternEmail = Pattern.compile("^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$");
        patternFLName = Pattern.compile("^\\pL+[\\pL\\pZ]{1,25}$");
        patternIDNumber = Pattern.compile("[\\d\\D]{0,20}");
        patternELevel = Pattern.compile("[\\d]{0,2}");
        patternSalary = Pattern.compile("[\\d,]{0,18}");
        if (boxId.getValue() == null) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(350, 35);
            label.setText("ID MUST NOT EMPTY !!!");
            label.setAlignment(Pos.CENTER_LEFT);

            boxId.getStyleClass().removeAll();
            boxId.getStyleClass().add("jfx-combo-box-fault");
            HboxContent.setSpacing(10);
            HboxContent.setAlignment(Pos.CENTER_LEFT);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            boxId.requestFocus();
        } else if (newPhone.getText().equals("")) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(465, 35);
            label.setText("PHONE NUMBER MUST NOT EMPTY !!!");
            newPhone.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setAlignment(Pos.CENTER);
            HboxContent.setSpacing(10);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            newPhone.requestFocus();
        } else if (birthday.getValue() == null) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("131"
                    + "6");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(465, 35);
            label.setText("BIRTHDAY MUST NOT EMPTY !!!");
            birthday.setStyle("-jfx-default-color: RED;");
            HboxContent.setAlignment(Pos.CENTER);
            HboxContent.setSpacing(10);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            birthday.requestFocus();
        } else if (address.getText().equals("")) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(465, 35);
            label.setText("ADDRESS MUST NOT EMPTY !!!");
            address.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setAlignment(Pos.CENTER);
            HboxContent.setSpacing(10);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            address.requestFocus();
        } else if (IdNumber.getText().equals("")) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(465, 35);
            label.setText("ID NUMBER MUST NOT EMPTY !!!");
            IdNumber.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setAlignment(Pos.CENTER);
            HboxContent.setSpacing(10);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            IdNumber.requestFocus();
        } else if (FirstName.getText().equals("") && !check_delete) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(465, 35);
            label.setText("FIRT NAME MUST NOT EMPTY !!!");
            FirstName.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setAlignment(Pos.CENTER);
            HboxContent.setSpacing(10);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            FirstName.requestFocus();
        } else if (LastName.getText().equals("") && !check_delete) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(465, 35);
            label.setText("LAST NAME MUST NOT EMPTY !!!");
            LastName.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setAlignment(Pos.CENTER);
            HboxContent.setSpacing(10);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            LastName.requestFocus();
        } else if (Email.getText().equals("") && !check_delete) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(465, 35);
            label.setText("EMAIL MUST NOT EMPTY !!!");
            Email.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setAlignment(Pos.CENTER);
            HboxContent.setSpacing(10);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            Email.requestFocus();
        } else if (!pattern.matcher(newPhone.getText()).matches()) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(465, 35);
            label.setText("PHONE NUMBER IS INCORRECT !!!");
            newPhone.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setAlignment(Pos.CENTER);
            HboxContent.setSpacing(10);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            newPhone.requestFocus();
        } else if (!patternFLName.matcher(FirstName.getText()).matches() && !check_delete) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(465, 35);
            label.setText("FIRSTNAME INVALID !!! (Example: Nguyễn, John,...) ");
            FirstName.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setSpacing(10);
            HboxContent.setAlignment(Pos.CENTER);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            FirstName.requestFocus();
        } else if (!patternFLName.matcher(MidName.getText()).matches() && !MidName.getText().equals("") && !check_delete) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(465, 35);
            label.setText("MIDNAME INVALID !!!(Example: Thị, Rooney,...");
            MidName.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setSpacing(10);
            HboxContent.setAlignment(Pos.CENTER);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            MidName.requestFocus();
        } else if (!patternFLName.matcher(LastName.getText()).matches() && !check_delete) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(465, 35);
            label.setText("LASTNAME INVALID !!!(Example: Toàn, Văn,...");
            LastName.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setSpacing(10);
            HboxContent.setAlignment(Pos.CENTER);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            LastName.requestFocus();
        } else if (!patternEmail.matcher(Email.getText()).matches() && !check_delete) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(465, 35);
            label.setText("INVALID EMAIL !!!");
            Email.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setSpacing(10);
            HboxContent.setAlignment(Pos.CENTER);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            Email.requestFocus();
        } else {
            boolean check = true;
            for (InfoEmployee infoEmployee : list_info) {
                if (Email.getText().equals(infoEmployee.getGmail()) && !check_delete && !list_login.get(0).getUserName().equals(infoEmployee.getUserName())) {
                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                    icon.setSize("16");
                    icon.setStyleClass("jfx-glyhp-icon");
                    Label label = new Label();
                    label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                    label.setPrefSize(465, 35);
                    label.setText("EMAIL ALREADY EXIST !!!");
                    Email.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
                    HboxContent.setSpacing(10);
                    HboxContent.setAlignment(Pos.CENTER);
                    HboxContent.getChildren().clear();
                    HboxContent.getChildren().add(icon);
                    HboxContent.getChildren().add(label);
                    Email.requestFocus();
                    check = false;
                    break;
                }
            }
            if (FXMLMainFormController.checkRegis && check && list_login.get(0).getRole().equals("Admin")) {
                try {
                    validateInfoEmployee = true;
                    FXMLMainFormController.checkRegis = false;
                    String date = birthday.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    String date_hire = null;
                    if (Hiredate.getValue() != null) {
                        date_hire = Hiredate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    }
                    String Sex;
                    String Id_Role;
                    Id_Role = DAO.getIdRole((String) boxRole.getValue());
                    if (Male.selectedProperty().getValue()) {
                        Sex = "Male";
                    } else {
                        Sex = "Female";
                    }
                    DAO.UpdateAllInfoEmployee(
                            boxId.getValue(), newPhone.getText(), date, address.getText(), IdNumber.getText(), FormatName.format(FirstName.getText()),
                            FormatName.format(MidName.getText()), FormatName.format(LastName.getText()),
                            Email.getText(), DepartmentId.getText(), date_hire, Job.getText(), EducatedLevel.getText(),
                            Double.valueOf(Salary.getText()), Double.valueOf(Bonus.getText()), Double.valueOf(Comm.getText()), Id_Role, Sex
                    );
                    FXMLLoginController loginController = ConnectControllers.getfXMLLoginController();
                    loginController.List_Employee = DAO.getAllEmployee();
                    newPhone.setText("");
                    boxId.setValue(null);
                    boxRole.setValue(null);
                    birthday.setValue(null);
                    Hiredate.setValue(null);
                    FirstName.setText("");
                    MidName.setText("");
                    LastName.setText("");
                    Email.setText("");
                    IdNumber.setText("");
                    address.setText("");
                    DepartmentId.setText("");
                    Job.setText("");
                    EducatedLevel.setText("");
                    Salary.setText("");
                    Comm.setText("");
                    Bonus.setText("");
                    Male.setSelected(true);
                    boxId.requestFocus();
                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CHECK);
                    icon.setSize("16");
                    icon.setStyleClass("jfx-glyhp-icon-finish");
                    Label label = new Label();
                    label.setStyle("-fx-text-fill: #6447cd; -fx-font-size : 11px;-fx-font-weight: bold;");
                    label.setPrefSize(465, 35);
                    label.setText("UPDATE INFO " + boxId.getValue() + " COMPLETE!!!");
                    HboxContent.setSpacing(10);
                    HboxContent.setAlignment(Pos.CENTER);
                    HboxContent.getChildren().clear();
                    HboxContent.getChildren().add(icon);
                    HboxContent.getChildren().add(label);
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(FXMLInfoEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                try {
                    String Sex;
                    if (Male.selectedProperty().getValue()) {
                        Sex = "Male";
                    } else {
                        Sex = "Female";
                    }
                    validateInfoEmployee = true;
                    String date = birthday.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    DAO.UpdateInfoEmployee(boxId.getValue(), newPhone.getText(), date, address.getText(), IdNumber.getText(), Sex);
                    FXMLLoginController loginController = ConnectControllers.getfXMLLoginController();
                    loginController.List_Employee = DAO.getAllEmployee();
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(FXMLInfoEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
