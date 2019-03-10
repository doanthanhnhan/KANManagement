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
import models.notificationFunction;
import utils.FormatName;
import utils.PatternValided;

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
    private ObservableList<String> list_Role = FXCollections.observableArrayList();
    public static Boolean validateInfoEmployee = false;
    public static Boolean check_delete;
    private String userLogin = FXMLLoginController.User_Login;
    private InfoEmployee Emp = new InfoEmployee();
    @FXML
    private FontAwesomeIconView iconRefresh;
    @FXML
    private HBox HboxBoxId;

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
        DepartmentId.setText("");
        Job.setText("");
        Salary.setText("0");
        Bonus.setText("0");
        Comm.setText("0");
        // Set path for fileChooser
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        fileChooser.setInitialDirectory(new File(currentPath + "/src/images"));
        validateInfoEmployee = false;
        check_delete = false;
        System.out.println("kiem tra validate Init" + validateInfoEmployee);
        if (FXMLMainFormController.checkRegis) {
            try {
                FXMLMainFormController.checkRegis = false;
                System.out.println("kiem tra validate mainform click:" + validateInfoEmployee);
                if (!DAO.get_Role(userLogin).equals("Admin")) {
                    HboxBoxId.getChildren().remove(iconRefresh);
                    check_delete = true;
                    Emp = DAO.getInfoEmployee(userLogin);
                    hBox_Info_Parent.getChildren().remove(vBox_Info_Right);
                    HboxHeader.getChildren().remove(HboxImage);
                    boxId.setDisable(true);
                    boxId.setValue(Emp.getUserName());
                    newPhone.setText(Emp.getPhone_No());
                    address.setText(Emp.getAddress());
                    IdNumber.setText(Emp.getId_number());
                    System.out.println("submit click");
                    System.out.println("Check delete " + check_delete);
                    if (Emp.getBirthdate() != null) {
                        birthday.setValue(LocalDate.parse(Emp.getBirthdate()));
                    }
                    if (Emp.getHiredate() != null) {
                        Hiredate.setValue(LocalDate.parse(Emp.getBirthdate()));
                    }
                    if (Emp.getSex()) {
                        Male.setSelected(true);
                    } else {
                        Female.setSelected(true);
                    }
                    btnInfo.setOnAction((event) -> {
                        System.out.println("Submit khong phai Admin");
                        validateForm();
                        if (validateInfoEmployee) {
                            // get a handle to the stage
                            Stage stage = (Stage) btnInfo.getScene().getWindow();
                            // do what you have to do
                            stage.hide();

                        }
                    });
                } else {
                    try {
                        refreshIdUser();
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(FXMLInfoEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    boxRole.setItems(list_Role);
                    btnInfo.setOnAction((event) -> {
                        System.out.println("Event của admin tu MainForm");
                        System.out.println("run");
                        FXMLMainFormController.checkRegis = true;
                        validateForm();
                    });
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(FXMLInfoEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (FXMLLoginController.checkLoginRegis) {
            check_delete = true;
            hBox_Info_Parent.getChildren().remove(vBox_Info_Right);
            HboxBoxId.getChildren().remove(iconRefresh);
            Hboxbtn.getChildren().remove(btnCancel);
            HboxHeader.getChildren().remove(HboxImage);
            boxId.setDisable(true);
            boxId.setValue(userLogin);
            System.out.println("đã vào form info tu form login roi");
            btnInfo.setOnAction((event) -> {
                System.out.println("đã vào Btn action tu Login Form");
                validateForm();
                // get a handle to the stage
                System.out.println("Button Submit Infor Action check ValidateInfoEmployee: " + validateInfoEmployee);
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
                    FXMLLoginController.checkLoginRegis = false;
                }
            });

        }
        boxId.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                System.out.println("Enter pressed");
                btnInfoEmployee();
            }
        });

        boxId.valueProperty().addListener((obs, oldItem, newItem) -> {
            System.out.println("Kiem tra newItem: " + newItem);
            if (newItem != null && !newItem.equals("")) {
                try {
                    Emp = DAO.getInfoEmployee(boxId.getValue());
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(FXMLInfoEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (Emp.getBirthdate() != null) {
                    birthday.setValue(LocalDate.parse(Emp.getBirthdate()));
                }
                if (Emp.getHiredate() != null) {
                    Hiredate.setValue(LocalDate.parse(Emp.getBirthdate()));
                }
                if (Emp.getSex()) {
                    Male.setSelected(true);
                } else {
                    Female.setSelected(true);
                }
                if (Emp.getWork_Dept() != null) {
                    DepartmentId.setText(Emp.getWork_Dept());
                }
                if (Emp.getJob() != null) {
                    Job.setText(Emp.getWork_Dept());
                }
                if (Emp.getComm() != null) {
                    Comm.setText(Emp.getComm());
                }
                if (Emp.getSalary() != null) {
                    Salary.setText(Emp.getSalary());
                }
                if (Emp.getBonus() != null) {
                    Bonus.setText(Emp.getBonus());
                }
                if (Emp.getEDLEVEL() != null) {
                    EducatedLevel.setText(String.valueOf(Emp.getEDLEVEL()));
                }
                newPhone.setText(Emp.getPhone_No());
                address.setText(Emp.getAddress());
                IdNumber.setText(Emp.getId_number());
                FirstName.setText(Emp.getFirst_Name());
                LastName.setText(Emp.getLast_Name());
                MidName.setText(Emp.getMid_Name());
                Email.setText(Emp.getGmail());
                boxRole.setValue(Emp.getRole());
                imgService.setImage(Emp.getImageView().getImage());
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
    private void refreshIdUser() throws SQLException, ClassNotFoundException {
        boxId.setItems(DAO.getAllIdUser());
        System.out.println("refresh xong");
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
    private void btnInfoEmployee() {
        validateForm();
        if (validateInfoEmployee && FXMLLoginController.checkLoginRegis) {
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
            FXMLLoginController.checkLoginRegis = false;
        }
    }

    private void validateForm() {
        System.out.println("kiem tra validate method : " + validateInfoEmployee);
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
            notificationFunction.notification(newPhone, HboxContent, "PHONE NUMBER MUST NOT EMPTY !!!");
        } else if (birthday.getValue() == null) {
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
            notificationFunction.notification(address, HboxContent, "ADDRESS NUMBER MUST NOT EMPTY !!!");

        } else if (IdNumber.getText() == null || IdNumber.getText().equals("")) {
            notificationFunction.notification(IdNumber, HboxContent, "ID NUMBER MUST NOT EMPTY !!!");

        } else if ((FirstName.getText() == null || FirstName.getText().equals("")) && !check_delete) {
            notificationFunction.notification(IdNumber, HboxContent, "FIRST NAME MUST NOT EMPTY !!!");

        } else if ((LastName.getText() == null || LastName.getText().equals("")) && !check_delete) {
            notificationFunction.notification(LastName, HboxContent, "LAST NAME MUST NOT EMPTY !!!");

        } else if ((Email.getText() == null || Email.getText().equals("")) && !check_delete) {
            notificationFunction.notification(Email, HboxContent, "EMAIL MUST NOT EMPTY !!!");

        } else if (!PatternValided.PatternPhoneNumber(newPhone.getText())) {
            notificationFunction.notification(newPhone, HboxContent, "PHONE NUMBER IS INCORRECT !!!");

        } else if (!PatternValided.PatternCMND(IdNumber.getText())) {
            notificationFunction.notification(IdNumber, HboxContent, "ID NUMBER IS INCORRECT !!!");

        } else if (!PatternValided.PatternName(FirstName.getText()) && !check_delete) {
            notificationFunction.notification(FirstName, HboxContent, "FIRSTNAME INVALID (Example: Nguyễn, Lê,...) !!!");

        } else if (!PatternValided.PatternName(MidName.getText()) && !MidName.getText().equals("") && !check_delete) {
            notificationFunction.notification(MidName, HboxContent, "MIDNAME INVALID (Example: Thị, Văn,...) !!!");

        } else if (!PatternValided.PatternName(LastName.getText()) && !check_delete) {
            notificationFunction.notification(LastName, HboxContent, "LASTNAME INVALID (Example: Nguyễn, Trần,...) !!!");

        } else if (!PatternValided.PatternEmail(Email.getText()) && !check_delete) {
            notificationFunction.notification(Email, HboxContent, "EMAIL INVALID !!!");

        } else if (!(DepartmentId.getText().equals("")) && !check_delete && !PatternValided.PatternCMND(DepartmentId.getText())) {
            notificationFunction.notification(DepartmentId, HboxContent, "DEPARTMENT ID IS INCORRECT !!!");

        } else if (!PatternValided.PatternName(Job.getText()) && !check_delete && !Job.getText().equals("")) {
            notificationFunction.notification(Job, HboxContent, "JOB IS INCORRECT !!!");

        } else if (!PatternValided.PatternELevel(EducatedLevel.getText()) && !check_delete && !EducatedLevel.getText().equals("")) {
            notificationFunction.notification(EducatedLevel, HboxContent, "EDUCATED LEVEL IS INCORRECT !!!");

        } else if (!PatternValided.PatternSalary(Salary.getText()) && !check_delete && !Salary.getText().equals("0")) {
            notificationFunction.notification(Salary, HboxContent, "SALARY IS INCORRECT !!!");

        } else if (!PatternValided.PatternSalary(Bonus.getText()) && !check_delete && !Bonus.getText().equals("0")) {
            notificationFunction.notification(Bonus, HboxContent, "BONUS IS INCORRECT !!!");

        } else if (!PatternValided.PatternSalary(Comm.getText()) && !check_delete && !Comm.getText().equals("0")) {
            notificationFunction.notification(Comm, HboxContent, "COMM IS INCORRECT !!!");

        } else {
            try {
                if (DAO.check_Email(Email.getText()) && !check_delete && !Emp.getGmail().equals(Email.getText())) {
                    notificationFunction.notification(Email, HboxContent, "EMAIL ALREADY EXIST !!!");

                } else if (FXMLMainFormController.checkRegis && DAO.get_Role(FXMLLoginController.User_Login).equals("Admin")) {
                    FXMLMainFormController.checkRegis = false;
                    String date = birthday.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    String date_hire = null;
                    if (Hiredate.getValue() != null) {
                        date_hire = Hiredate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    }
                    Boolean Sex;
                    String Id_Role;
                    Id_Role = DAO.getIdRole((String) boxRole.getValue());
                    Sex = Male.selectedProperty().getValue();
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
                    check_delete = false;
                    validateInfoEmployee = true;
                    System.out.println("Vào chỗ submit admin");
                } else {
                    Boolean Sex;
                    Sex = Male.selectedProperty().getValue();
                    String date = birthday.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    DAO.UpdateInfoEmployee(boxId.getValue(), newPhone.getText(), date, address.getText(), IdNumber.getText(), Sex);
                    check_delete = false;
                    validateInfoEmployee = true;
                    System.out.println("Vào chỗ submit user");
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(FXMLInfoEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
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
