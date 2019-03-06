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
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
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

    final FileChooser fileChooser = new FileChooser();
    @FXML
    private JFXTextField newPhone;
    @FXML
    private HBox HboxImage;
    @FXML
    private HBox HboxHeader;

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
    private JFXButton btnInsertImage;
    @FXML
    private ImageView imgService;
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
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.png, *.jpg, *.gif, *.bmp)", "*.jpg", "*.png", "*.gif", "*.bmp");
        fileChooser.getExtensionFilters().add(extFilter);
        // Set path for fileChooser
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        fileChooser.setInitialDirectory(new File(currentPath + "/src/images"));
        validateInfoEmployee = false;
        check_delete = false;
        System.out.println("kiem tra validate Init" + validateInfoEmployee);
        list_info = FXMLLoginController.List_Employee;
        list_login = FXMLLoginController.List_EmployeeLogin;
//        boxId.setOnKeyPressed((KeyEvent event) -> {
//            if (event.getCode() == KeyCode.ENTER) {
//                btnInfoEmployee();
//            }
//        });
//        boxId.getEditor().addEventFilter(Event.ANY, e -> System.out.println("event bat ky: " + e));
        boxId.getEditor().addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            System.out.println("key: " + e.getCode());
            if (e.getCode() == KeyCode.ENTER) {
                System.out.println("Enter pressed");
                btnInfoEmployee();
            }

        });
        for (InfoEmployee infoEmployee : list_info) {
            if (infoEmployee.getUserName().equals(list_login.get(0).getUserName())) {
                list_login_update.add(infoEmployee);
            }
        }

        list_info.forEach((InfoEmployee infoEmployee) -> {
            list_User.add(infoEmployee.getUserName());
        });
        if (FXMLMainFormController.checkRegis) {
            DepartmentId.setText("");
            Job.setText("");
            Salary.setText("0");
            Bonus.setText("0");
            Comm.setText("0");
            
            FXMLMainFormController.checkRegis = false;
            System.out.println("kiem tra validate mainform click:" + validateInfoEmployee);
            if (!list_login_update.get(0).getRole().equals("Admin")) {
                hBox_Info_Parent.getChildren().remove(vBox_Info_Right);
                HboxHeader.getChildren().remove(HboxImage);
                boxId.setDisable(true);
                boxId.setValue(list_login_update.get(0).getUserName());
                newPhone.setText(list_login_update.get(0).getPhone_No());
                address.setText(list_login_update.get(0).getAddress());
                IdNumber.setText(list_login_update.get(0).getId_number());

                System.out.println("submit click");
                System.out.println("Check delete " + check_delete);
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
                    check_delete = true;
                    System.out.println("Submit khong phai Admin");
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
                    System.out.println("run");
                    FXMLMainFormController.checkRegis = true;

                    validateForm();
                    check_delete = false;
                });
            }
        }
        if (FXMLLoginController.checkLoginRegis) {
            hBox_Info_Parent.getChildren().remove(vBox_Info_Right);
            Hboxbtn.getChildren().remove(btnCancel);
            HboxHeader.getChildren().remove(HboxImage);
            boxId.setDisable(true);
            boxId.setValue(list_login.get(0).getUserName());
            FXMLLoginController.checkLoginRegis = false;
            btnInfo.setOnAction((event) -> {
                System.out.println("đã vào btn");
                check_delete = true;
                validateForm();
                check_delete = false;
                // get a handle to the stage

                if (validateInfoEmployee) {
                    try {
                        Stage stageInfo = (Stage) btnInfo.getScene().getWindow();
                        // do what you have to do
                        stageInfo.close();
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

// add enter key
        boxId.valueProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null) {
                HboxContent.getChildren().clear();
                boxId.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            }
        });
//       
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
        address.setOnKeyPressed((KeyEvent event) -> {
            address.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                btnInfoEmployee();
            }
        });
        IdNumber.setOnKeyPressed((KeyEvent event) -> {
            IdNumber.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                btnInfoEmployee();
            }
        });
        FirstName.setOnKeyPressed((KeyEvent event) -> {
            FirstName.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                btnInfoEmployee();
            }
        });
        MidName.setOnKeyPressed((KeyEvent event) -> {
            MidName.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                btnInfoEmployee();
            }
        });
        LastName.setOnKeyPressed((KeyEvent event) -> {
            LastName.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                btnInfoEmployee();
            }
        });
        Email.setOnKeyPressed((KeyEvent event) -> {
            Email.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                btnInfoEmployee();
            }
        });
        DepartmentId.setOnKeyPressed((KeyEvent event) -> {
            DepartmentId.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                btnInfoEmployee();
            }
        });
        Hiredate.valueProperty().addListener((obs, oldItem, newItem) -> {
            Hiredate.setStyle("-jfx-default-color: #6447cd;");
            HboxContent.getChildren().clear();
        });
        Hiredate.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                btnInfoEmployee();
            }
        });
        Job.setOnKeyPressed((KeyEvent event) -> {
            Job.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                btnInfoEmployee();
            }
        });
        EducatedLevel.setOnKeyPressed((KeyEvent event) -> {
            EducatedLevel.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                btnInfoEmployee();
            }
        });
        Salary.setOnKeyPressed((KeyEvent event) -> {
            Salary.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                btnInfoEmployee();
            }
        });
        Bonus.setOnKeyPressed((KeyEvent event) -> {
            Salary.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                btnInfoEmployee();
            }
        });
        Comm.setOnKeyPressed((KeyEvent event) -> {
            Salary.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                btnInfoEmployee();
            }
        });
        boxRole.setOnKeyPressed((KeyEvent event) -> {
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
    private void select_ID(ActionEvent event) {
        list_info = FXMLLoginController.List_Employee;
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
                imgService.setImage(infoEmployee.getImageView().getImage());
                break;
            }
        }
    }

    @FXML
    private void btnInfoEmployee() {
        validateForm();
    }

    private void validateForm() {

        System.out.println("kiem tra validate method : " + validateInfoEmployee);
        pattern = Pattern.compile("^([0-9][0-9]{1,19}$)|\\+84[0-9]{1,17}$");
        patternEmail = Pattern.compile("^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$");
        patternFLName = Pattern.compile("^\\pL+[\\pL\\pZ]{1,25}$");
        patternIDNumber = Pattern.compile("^(?=.{4,20}$)[a-zA-Z][a-zA-Z0-9_]+$");
        patternELevel = Pattern.compile("[\\d]{0,2}");
        patternSalary = Pattern.compile("^[\\d][\\d]*\\.?[\\d]*$");
        System.out.println("check_delete: " + check_delete);
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
        } else if (newPhone.getText() == null || newPhone.getText().equals("")) {
            System.out.println("Neu ID khong null -- Vao else if");
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(350, 35);
            label.setText("PHONE NUMBER MUST NOT EMPTY !!!");
            label.setAlignment(Pos.CENTER_LEFT);
            newPhone.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setAlignment(Pos.CENTER_LEFT);
            HboxContent.setSpacing(10);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            newPhone.requestFocus();
            System.out.println("Chay het cau newPhone");
        } else if (birthday.getValue() == null) {
            System.out.println("Neu ID khong null -- Vao else if 1");
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(350, 35);
            label.setAlignment(Pos.CENTER_LEFT);
            label.setText("BIRTHDAY MUST NOT EMPTY !!!");
            birthday.setStyle("-jfx-default-color: RED;");
            HboxContent.setAlignment(Pos.CENTER_LEFT);
            HboxContent.setSpacing(10);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            birthday.requestFocus();
        } else if (address.getText() == null || address.getText().equals("")) {
            System.out.println("Neu ID khong null -- Vao else if 2");
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(350, 35);
            label.setText("ADDRESS MUST NOT EMPTY !!!");
            label.setAlignment(Pos.CENTER_LEFT);
            address.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setAlignment(Pos.CENTER_LEFT);
            HboxContent.setSpacing(10);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            address.requestFocus();
        } else if (IdNumber.getText() == null || IdNumber.getText().equals("")) {
            System.out.println("Neu ID khong null -- Vao else if 3");
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(350, 35);
            label.setAlignment(Pos.CENTER_LEFT);
            label.setText("ID NUMBER MUST NOT EMPTY !!!");
            IdNumber.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setAlignment(Pos.CENTER_LEFT);
            HboxContent.setSpacing(10);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            IdNumber.requestFocus();
        } else if ((FirstName.getText() == null || FirstName.getText().equals("")) && !check_delete) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(350, 35);
            label.setText("FIRST NAME MUST NOT EMPTY !!!");
            label.setAlignment(Pos.CENTER_LEFT);
            FirstName.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setAlignment(Pos.CENTER_LEFT);
            HboxContent.setSpacing(10);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            FirstName.requestFocus();
        } else if ((LastName.getText() == null || LastName.getText().equals("")) && !check_delete) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(350, 35);
            label.setAlignment(Pos.CENTER_LEFT);
            label.setText("LAST NAME MUST NOT EMPTY !!!");
            LastName.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setAlignment(Pos.CENTER_LEFT);
            HboxContent.setSpacing(10);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            LastName.requestFocus();
        } else if ((Email.getText() == null || Email.getText().equals("")) && !check_delete) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(350, 35);
            label.setAlignment(Pos.CENTER_LEFT);
            label.setText("EMAIL MUST NOT EMPTY !!!");
            Email.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setAlignment(Pos.CENTER_LEFT);
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
            label.setPrefSize(350, 35);
            label.setAlignment(Pos.CENTER_LEFT);
            label.setText("PHONE NUMBER IS INCORRECT !!!");
            newPhone.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setAlignment(Pos.CENTER_LEFT);
            HboxContent.setSpacing(10);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            newPhone.requestFocus();
        } else if (!patternIDNumber.matcher(IdNumber.getText()).matches()) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(350, 35);
            label.setAlignment(Pos.CENTER_LEFT);
            label.setText("ID NUMBER IS INCORRECT !!!");
            IdNumber.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setAlignment(Pos.CENTER_LEFT);
            HboxContent.setSpacing(10);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            IdNumber.requestFocus();
        } else if (!patternFLName.matcher(FirstName.getText()).matches() && !check_delete) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(350, 35);
            label.setAlignment(Pos.CENTER_LEFT);
            label.setText("FIRSTNAME INVALID !!! (Example: Nguyễn, John,...) ");
            FirstName.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setSpacing(10);
            HboxContent.setAlignment(Pos.CENTER_LEFT);
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
            label.setPrefSize(350, 35);
            label.setAlignment(Pos.CENTER_LEFT);
            label.setText("MIDNAME INVALID !!!(Example: Thị, Rooney,...");
            MidName.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setSpacing(10);
            HboxContent.setAlignment(Pos.CENTER_LEFT);
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
            label.setPrefSize(350, 35);
            label.setText("LASTNAME INVALID !!!(Example: Toàn, Văn,...");
            label.setAlignment(Pos.CENTER_LEFT);
            LastName.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setSpacing(10);
            HboxContent.setAlignment(Pos.CENTER_LEFT);
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
            label.setPrefSize(350, 35);
            label.setText("INVALID EMAIL !!!");
            label.setAlignment(Pos.CENTER_LEFT);
            Email.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setSpacing(10);
            HboxContent.setAlignment(Pos.CENTER_LEFT);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            Email.requestFocus();
        } else if (!(DepartmentId.getText().equals("")) && !check_delete && !patternIDNumber.matcher(DepartmentId.getText()).matches()) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(350, 35);
            label.setText("DEPARTMENT ID IS INCORRECT !!!");
            label.setAlignment(Pos.CENTER_LEFT);
            DepartmentId.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setAlignment(Pos.CENTER_LEFT);
            HboxContent.setSpacing(10);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            DepartmentId.requestFocus();
        } else if (!patternFLName.matcher(Job.getText()).matches() && !check_delete && !Job.getText().equals("")) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(350, 35);
            label.setText("JOB IS INCORRECT !!!");
            label.setAlignment(Pos.CENTER_LEFT);
            Job.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setAlignment(Pos.CENTER_LEFT);
            HboxContent.setSpacing(10);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            Job.requestFocus();
        } else if (!patternELevel.matcher(EducatedLevel.getText()).matches() && !check_delete && !EducatedLevel.getText().equals("")) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(350, 35);
            label.setText("EDUCATED LEVEL IS INCORRECT !!!");
            label.setAlignment(Pos.CENTER_LEFT);
            EducatedLevel.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setAlignment(Pos.CENTER_LEFT);
            HboxContent.setSpacing(10);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            EducatedLevel.requestFocus();
        } else if (!patternSalary.matcher(Salary.getText()).matches() && !check_delete && !Salary.getText().equals("0")) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(350, 35);
            label.setText("SALARY IS INCORRECT !!!");
            label.setAlignment(Pos.CENTER_LEFT);
            Salary.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setAlignment(Pos.CENTER_LEFT);
            HboxContent.setSpacing(10);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            Salary.requestFocus();
        } else if (!patternSalary.matcher(Bonus.getText()).matches() && !check_delete && !Bonus.getText().equals("0")) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(350, 35);
            label.setText("BONUS IS INCORRECT !!!");
            label.setAlignment(Pos.CENTER_LEFT);
            Bonus.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setAlignment(Pos.CENTER_LEFT);
            HboxContent.setSpacing(10);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            Bonus.requestFocus();
        } else if (!patternSalary.matcher(Comm.getText()).matches() && !check_delete && !Comm.getText().equals("0")) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon");
            Label label = new Label();
            label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(350, 35);
            label.setText("COMM IS INCORRECT !!!");
            label.setAlignment(Pos.CENTER_LEFT);
            Comm.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
            HboxContent.setAlignment(Pos.CENTER_LEFT);
            HboxContent.setSpacing(10);
            HboxContent.getChildren().clear();
            HboxContent.getChildren().add(icon);
            HboxContent.getChildren().add(label);
            Comm.requestFocus();
        } else {
            boolean check = true;
            for (InfoEmployee infoEmployee : list_info) {
                if (Email.getText().equals(infoEmployee.getGmail()) && !check_delete && !boxId.getValue().equals(infoEmployee.getUserName())) {
                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                    icon.setSize("16");
                    icon.setStyleClass("jfx-glyhp-icon");
                    Label label = new Label();
                    label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                    label.setPrefSize(350, 35);
                    label.setText("EMAIL ALREADY EXIST !!!");
                    label.setAlignment(Pos.CENTER_LEFT);
                    Email.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
                    HboxContent.setSpacing(10);
                    HboxContent.setAlignment(Pos.CENTER_LEFT);
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
                    if (!imgService.getImage().equals(null)) {
                        BufferedImage bImage = SwingFXUtils.fromFXImage(imgService.getImage(), null);
                        byte[] res;
                        try (ByteArrayOutputStream s = new ByteArrayOutputStream()) {
                            ImageIO.write(bImage, "png", s);
                            res = s.toByteArray();
                            Blob blob = new SerialBlob(res);
                            DAO.UpdateAllInfoEmployee(
                                    boxId.getValue(), newPhone.getText(), date, address.getText(), IdNumber.getText(), FormatName.format(FirstName.getText()),
                                    FormatName.format(MidName.getText()), FormatName.format(LastName.getText()),
                                    Email.getText(), DepartmentId.getText(), date_hire, FormatName.format(Job.getText()), EducatedLevel.getText(),
                                    Double.valueOf(Salary.getText()), Double.valueOf(Bonus.getText()), Double.valueOf(Comm.getText()), Id_Role, Sex, blob
                            );
                        } catch (SQLException ex) {
                            Logger.getLogger(FXMLAddNewServiceTypeController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLInfoEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();

                        BufferedImage bImage = SwingFXUtils.fromFXImage(new Image(currentPath + "/src/images/Java.png"), null);
                        byte[] res;
                        try (ByteArrayOutputStream s = new ByteArrayOutputStream()) {
                            ImageIO.write(bImage, "png", s);
                            res = s.toByteArray();
                            Blob blob = new SerialBlob(res);
                            DAO.UpdateAllInfoEmployee(
                                    boxId.getValue(), newPhone.getText(), date, address.getText(), IdNumber.getText(), FormatName.format(FirstName.getText()),
                                    FormatName.format(MidName.getText()), FormatName.format(LastName.getText()),
                                    Email.getText(), DepartmentId.getText(), date_hire, FormatName.format(Job.getText()), EducatedLevel.getText(),
                                    Double.valueOf(Salary.getText()), Double.valueOf(Bonus.getText()), Double.valueOf(Comm.getText()), Id_Role, Sex, blob
                            );
                        } catch (SQLException ex) {
                            Logger.getLogger(FXMLAddNewServiceTypeController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLInfoEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

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
                    Salary.setText("0");
                    Comm.setText("0");
                    Bonus.setText("0");
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

    @FXML
    private void handle_Button_Insert_Image_Action(ActionEvent event) {
        File file = fileChooser.showOpenDialog(btnInsertImage.getScene().getWindow());
        fileChooser.setTitle("Choose an image for service type");

        if (file != null) {
            System.out.println(file.toURI().toString());
            Image image = new Image(file.toURI().toString());
            imgService.setImage(image);
        }
    }
}
