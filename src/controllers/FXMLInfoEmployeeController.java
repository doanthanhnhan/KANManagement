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
import static controllers.ConnectControllers.fXMLMainFormController;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
import javafx.scene.control.DateCell;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import models.DAO;
import models.DAOcheckRole;
import models.InfoEmployee;
import models.formatCalender;
import models.notificationFunction;
import utils.AlertLoginAgain;
import utils.FormatName;
import utils.GetInetAddress;
import utils.PatternValided;
import utils.StageLoader;
import utils.showFXMLLogin;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class FXMLInfoEmployeeController implements Initializable {

    private showFXMLLogin showFormLogin = new showFXMLLogin();
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
    private JFXButton btnInsertImage;
    @FXML
    private ImageView imgService;
    @FXML
    private JFXButton btnCancel;
    public static Boolean validateInfoEmployee = false;
    public static Boolean check_delete;
    private String userLogin = FXMLLoginController.User_Login;
    private InfoEmployee Emp = new InfoEmployee();
    @FXML
    private FontAwesomeIconView iconRefresh;
    @FXML
    private AnchorPane anchorPaneInfoEmployee;

    @FXML
    private HBox HboxBoxId;
    private FXMLListEmployeeController fXMLListEmployeeController;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fXMLListEmployeeController = ConnectControllers.getfXMLListEmployeeController();
        birthday.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.ofYearDay(LocalDate.now().getYear() - 16, LocalDate.now().getDayOfYear());
                setDisable(empty || date.compareTo(today) > 0);

            }
        });

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.png, *.jpg, *.gif, *.bmp)", "*.jpg", "*.png", "*.gif", "*.bmp");
        fileChooser.getExtensionFilters().add(extFilter);
        // Set path for fileChooser
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        fileChooser.setInitialDirectory(new File(currentPath + "/src/images"));
        validateInfoEmployee = false;
        check_delete = false;
        System.out.println("kiem tra validate Init" + validateInfoEmployee);
        System.out.println("mainform check regis: " + FXMLMainFormController.checkRegis);
        if (FXMLMainFormController.checkRegis) {
            try {
                System.out.println("kiem tra validate mainform click:" + validateInfoEmployee);
                HboxBoxId.getChildren().remove(iconRefresh);
                Hboxbtn.getChildren().remove(btnCancel);
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
                    enter_Submit_Action();
                });

            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(FXMLInfoEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (FXMLListEmployeeController.check_form_list) {
            System.out.println("Vao check_form_list = true");
            DepartmentId.setText("");
            Job.setText("");
            Salary.setText("0");
            Bonus.setText("0");
            Comm.setText("0");
            check_delete = false;
            Emp = FXMLListEmployeeController.Emp;
            HboxBoxId.getChildren().remove(iconRefresh);
            boxId.setDisable(true);
            boxId.setValue(Emp.getEmployee_ID());
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
            if (!Emp.getWork_Dept().isEmpty()) {
                DepartmentId.setText(Emp.getWork_Dept());
            }
            if (!Emp.getJob().isEmpty()) {
                Job.setText(Emp.getWork_Dept());
            }
            if (!Emp.getComm().isEmpty()) {
                Comm.setText(Emp.getComm());
            }
            if (!Emp.getSalary().isEmpty()) {
                Salary.setText(Emp.getSalary());
            }
            if (!Emp.getBonus().isEmpty()) {
                Bonus.setText(Emp.getBonus());
            }
            if (!Emp.getEDLEVEL().toString().isEmpty()) {
                EducatedLevel.setText(String.valueOf(Emp.getEDLEVEL()));
            }
            newPhone.setText(Emp.getPhone_No());
            address.setText(Emp.getAddress());
            IdNumber.setText(Emp.getId_number());
            FirstName.setText(Emp.getFirst_Name());
            LastName.setText(Emp.getLast_Name());
            MidName.setText(Emp.getMid_Name());
            Email.setText(Emp.getGmail());
            imgService.setImage(Emp.getImageView().getImage());
            btnInfo.setOnAction((event) -> {
                enter_Submit_Action();
            });
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
            Platform.runLater(() -> {
                btnInfo.setOnAction((event) -> {
                    System.out.println("đã vào Btn action tu Login Form");
                    try {
                        handleInfoAction();
                    } catch (ClassNotFoundException | SQLException | IOException ex) {
                        Logger.getLogger(FXMLInfoEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            });
        }
        newPhone.setOnKeyPressed((KeyEvent event) -> {
            Platform.runLater(() -> {
                newPhone.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                HboxContent.getChildren().clear();
            });
            if (event.getCode() == KeyCode.ENTER) {
                enter_Submit_Action();
            }
        });
        boxId.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                System.out.println("Enter pressed");
                enter_Submit_Action();
            }
        });

        birthday.valueProperty().addListener((obs, oldItem, newItem) -> {
            birthday.setStyle("-jfx-default-color: #6447cd;");
            HboxContent.getChildren().clear();
        });
        birthday.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                enter_Submit_Action();
            }
        });
        address.setOnKeyPressed((KeyEvent event) -> {
            address.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                enter_Submit_Action();
            }
        });
        IdNumber.setOnKeyPressed((KeyEvent event) -> {
            IdNumber.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                enter_Submit_Action();
            }
        });
        FirstName.setOnKeyPressed((KeyEvent event) -> {
            FirstName.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                enter_Submit_Action();
            }
        });
        MidName.setOnKeyPressed((KeyEvent event) -> {
            MidName.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                enter_Submit_Action();
            }
        });
        LastName.setOnKeyPressed((KeyEvent event) -> {
            LastName.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                enter_Submit_Action();
            }
        });
        Email.setOnKeyPressed((KeyEvent event) -> {
            Email.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                enter_Submit_Action();
            }
        });
        DepartmentId.setOnKeyPressed((KeyEvent event) -> {
            DepartmentId.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                enter_Submit_Action();
            }
        });
        Hiredate.valueProperty().addListener((obs, oldItem, newItem) -> {
            Hiredate.setStyle("-jfx-default-color: #6447cd;");
            HboxContent.getChildren().clear();
        });
        Hiredate.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                enter_Submit_Action();
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
                enter_Submit_Action();
            }
        });
        Salary.setOnKeyPressed((KeyEvent event) -> {
            Salary.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                enter_Submit_Action();
            }
        });
        Bonus.setOnKeyPressed((KeyEvent event) -> {
            Salary.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                enter_Submit_Action();
            }
        });
        Comm.setOnKeyPressed((KeyEvent event) -> {
            Salary.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                enter_Submit_Action();
            }
        });
// TODO
    }

    public void handleInfoAction() throws ClassNotFoundException, SQLException, IOException {
        System.out.println("click 1");
        btnInfo.setDisable(true);
        StageLoader stageLoader = new StageLoader();
        stageLoader.loadingIndicator("Loading");
        Task loadOverview = new Task() {
            @Override
            protected Object call() throws Exception {
                Platform.runLater(() -> {
                    HboxContent.getChildren().clear();
                    try {
                        validateForm();
                    } catch (ClassNotFoundException | IOException | SQLException ex) {
                        Logger.getLogger(FXMLInfoEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });

                System.out.println("click 2");
                return null;
            }
        };
        loadOverview.setOnSucceeded(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                System.out.println("Finished");
                Platform.runLater(() -> {
                    FXMLMainFormController.checkRegis = false;
                    btnInfo.setDisable(false);
                    stageLoader.stopTimeline();
                    stageLoader.closeStage();
                    try {
                        System.out.println("validateInfoEmployee = " + validateInfoEmployee);
                        if (validateInfoEmployee && !DAOcheckRole.checkRoleDecentralization(userLogin, "Employee_Edit")) {
                            // get a handle to the stage
                            Stage stage = (Stage) btnInfo.getScene().getWindow();
                            // do what you have to do
                            stage.close();
                        }
                        if (validateInfoEmployee && FXMLLoginController.checkLoginRegis) {
                            Stage stageInfo = (Stage) btnInfo.getScene().getWindow();
                            // do what you have to do
                            stageInfo.close();
                            Stage stage = new Stage();
                            Parent root = FXMLLoader.load(getClass().getResource("/fxml/FXMLMainForm.fxml"));
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();
                            FXMLLoginController.checkLoginRegis = false;
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLInfoEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(FXMLInfoEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(FXMLInfoEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        });
        new Thread(loadOverview).start();
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

    public void btnInfoEmployee() {
        Platform.runLater(() -> {
            try {
                validateForm();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FXMLInfoEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FXMLInfoEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(FXMLInfoEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        });
    }

    public void validateForm() throws ClassNotFoundException, IOException, SQLException {

        if ((FXMLMainFormController.checkRegis || FXMLListEmployeeController.check_form_list) && !check_delete && !DAOcheckRole.checkRoleDecentralization(userLogin, "Employee_Edit")) {
            Platform.runLater(() -> {
                AlertLoginAgain.alertLogin();
                fXMLMainFormController = ConnectControllers.getfXMLMainFormController();
                Stage stageMainForm = (Stage) fXMLMainFormController.AnchorPaneMainForm.getScene().getWindow();
                Stage stage = (Stage) anchorPaneInfoEmployee.getScene().getWindow();
                stage.close();
                stageMainForm.close();
                try {
                    showFormLogin.showFormLogin();
                } catch (IOException ex) {
                    Logger.getLogger(FXMLInfoEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } else {
            System.out.println("kiem tra user : " + userLogin);
            System.out.println("kiem tra validate method : " + validateInfoEmployee);
            System.out.println("check_delete: " + check_delete);
            if (boxId.getValue() == null) {
                Platform.runLater(() -> {
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
                });

            } else if (newPhone.getText() == null || newPhone.getText().equals("")) {
                Platform.runLater(() -> {
                    notificationFunction.notification(newPhone, HboxContent, "PHONE NUMBER MUST NOT EMPTY !!!");
                });

            } else if (birthday.getValue() == null) {
                Platform.runLater(() -> {
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
                });

            } else if (address.getText() == null || address.getText().equals("")) {
                Platform.runLater(() -> {
                    notificationFunction.notification(address, HboxContent, "ADDRESS NUMBER MUST NOT EMPTY !!!");
                });

            } else if (IdNumber.getText() == null || IdNumber.getText().equals("")) {
                Platform.runLater(() -> {
                    notificationFunction.notification(IdNumber, HboxContent, "ID NUMBER MUST NOT EMPTY !!!");
                });

            } else if ((FirstName.getText() == null || FirstName.getText().equals("")) && !check_delete) {
                Platform.runLater(() -> {
                    notificationFunction.notification(IdNumber, HboxContent, "FIRST NAME MUST NOT EMPTY !!!");
                });

            } else if ((LastName.getText() == null || LastName.getText().equals("")) && !check_delete) {
                Platform.runLater(() -> {
                    notificationFunction.notification(LastName, HboxContent, "LAST NAME MUST NOT EMPTY !!!");
                });

            } else if ((Email.getText() == null || Email.getText().equals("")) && !check_delete) {
                Platform.runLater(() -> {
                    notificationFunction.notification(Email, HboxContent, "EMAIL MUST NOT EMPTY !!!");
                });

            } else if (!PatternValided.PatternPhoneNumber(newPhone.getText())) {
                Platform.runLater(() -> {
                    notificationFunction.notification(newPhone, HboxContent, "PHONE NUMBER IS INCORRECT !!!");
                });

            } else if (!PatternValided.PatternCMND(IdNumber.getText())) {
                Platform.runLater(() -> {
                    notificationFunction.notification(IdNumber, HboxContent, "ID NUMBER IS INCORRECT !!!");
                });

            } else if (!PatternValided.PatternName(FirstName.getText()) && !check_delete) {
                Platform.runLater(() -> {
                    notificationFunction.notification(FirstName, HboxContent, "FIRSTNAME INVALID (Example: Nguyễn, Lê,...) !!!");
                });

            } else if (!PatternValided.PatternName(MidName.getText()) && !MidName.getText().equals("") && !check_delete) {
                Platform.runLater(() -> {
                    notificationFunction.notification(MidName, HboxContent, "MIDNAME INVALID (Example: Thị, Văn,...) !!!");
                });

            } else if (!PatternValided.PatternName(LastName.getText()) && !check_delete) {
                Platform.runLater(() -> {
                    notificationFunction.notification(LastName, HboxContent, "LASTNAME INVALID (Example: Nguyễn, Trần,...) !!!");
                });

            } else if (!PatternValided.PatternEmail(Email.getText()) && !check_delete) {
                Platform.runLater(() -> {
                    notificationFunction.notification(Email, HboxContent, "EMAIL INVALID !!!");
                });

            } else if (!(DepartmentId.getText().equals("")) && !check_delete && !PatternValided.PatternCMND(DepartmentId.getText())) {
                Platform.runLater(() -> {
                    notificationFunction.notification(DepartmentId, HboxContent, "DEPARTMENT ID IS INCORRECT !!!");
                });

            } else if (!PatternValided.PatternName(Job.getText()) && !check_delete && !Job.getText().equals("")) {
                Platform.runLater(() -> {
                    notificationFunction.notification(Job, HboxContent, "JOB IS INCORRECT !!!");
                });

            } else if (!PatternValided.PatternELevel(EducatedLevel.getText()) && !check_delete && !EducatedLevel.getText().equals("")) {
                Platform.runLater(() -> {
                    notificationFunction.notification(EducatedLevel, HboxContent, "EDUCATED LEVEL IS INCORRECT !!!");
                });

            } else if (!PatternValided.PatternSalary(Salary.getText()) && !check_delete && !Salary.getText().equals("0")) {
                Platform.runLater(() -> {
                    notificationFunction.notification(Salary, HboxContent, "SALARY IS INCORRECT !!!");
                });

            } else if (!PatternValided.PatternSalary(Bonus.getText()) && !check_delete && !Bonus.getText().equals("0")) {
                Platform.runLater(() -> {
                    notificationFunction.notification(Bonus, HboxContent, "BONUS IS INCORRECT !!!");
                });

            } else if (!PatternValided.PatternSalary(Comm.getText()) && !check_delete && !Comm.getText().equals("0")) {
                Platform.runLater(() -> {
                    notificationFunction.notification(Comm, HboxContent, "COMM IS INCORRECT !!!");
                });

            } else {
                try {
                    System.out.println("checkRegis = " + FXMLMainFormController.checkRegis);
                    System.out.println("check  decentralization= " + DAOcheckRole.checkRoleDecentralization(userLogin, "Employee_Edit"));
                    System.out.println("checkloginRegis= " + FXMLLoginController.checkLoginRegis);
                    if (DAO.check_Email(Email.getText()) && !check_delete && !Emp.getGmail().equals(Email.getText())) {
                        System.out.println("vao khu vuc check Email");
                        Platform.runLater(() -> {
                            notificationFunction.notification(Email, HboxContent, "EMAIL ALREADY EXIST !!!");
                        });
                    } else if (!FXMLMainFormController.checkRegis && DAOcheckRole.checkRoleDecentralization(userLogin, "Employee_Edit") && !FXMLLoginController.checkLoginRegis) {
                        System.out.println("vao khu vuc submit admin 1");
                        Platform.runLater(() -> {
                            System.out.println("vao khu vuc submit admin 2");
                            FXMLMainFormController.checkRegis = false;
                            String date = birthday.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            String date_hire = null;
                            if (Hiredate.getValue() != null) {
                                date_hire = Hiredate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            }
                            Boolean Sex;
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
                                        Double.valueOf(Salary.getText()), Double.valueOf(Bonus.getText()), Double.valueOf(Comm.getText()), Sex, blob
                                );
                            } catch (SQLException ex) {
                                Logger.getLogger(FXMLAddNewServiceTypeController.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException | ClassNotFoundException ex) {
                                Logger.getLogger(FXMLInfoEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            DAO.setUserLogs_With_MAC(FXMLLoginController.User_Login, "Update " + boxId.getValue(),
                                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), GetInetAddress.getMacAddress());
                            newPhone.setText("");
                            boxId.setValue(null);
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
                            check_delete = false;
                            validateInfoEmployee = true;
                            FXMLListEmployeeController.check_form_list = false;
                            fXMLListEmployeeController = ConnectControllers.getfXMLListEmployeeController();
                            fXMLListEmployeeController.showUsersData();
                            System.out.println("Vào chỗ submit admin");
                            Stage stage = (Stage) anchorPaneInfoEmployee.getScene().getWindow();
                            stage.close();
                        });
                    } else {
                        Platform.runLater(() -> {
                            Boolean Sex;
                            Sex = Male.selectedProperty().getValue();
                            String date = birthday.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            try {
                                DAO.UpdateInfoEmployee(boxId.getValue(), newPhone.getText(), date, address.getText(), IdNumber.getText(), Sex);
                            } catch (ClassNotFoundException | SQLException ex) {
                                Logger.getLogger(FXMLInfoEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            DAO.setUserLogs_With_MAC(FXMLLoginController.User_Login, "Update " + boxId.getValue(),
                                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), GetInetAddress.getMacAddress());
                            check_delete = false;
                            validateInfoEmployee = true;
                            System.out.println("Vào chỗ submit user");
                            FXMLMainFormController.checkRegis = false;
                            Stage stage = (Stage) anchorPaneInfoEmployee.getScene().getWindow();
                            stage.close();
                        });
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(FXMLInfoEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void enter_Submit_Action() {
        try {

            handleInfoAction();

        } catch (SQLException | ClassNotFoundException | IOException ex) {
            Logger.getLogger(FXMLInfoEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
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
