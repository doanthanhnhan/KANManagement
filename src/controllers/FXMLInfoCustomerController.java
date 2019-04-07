/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Customer;
import models.DAO;
import models.DAOCustomerBookingCheckIn;
import models.DAOcheckRole;
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
public class FXMLInfoCustomerController implements Initializable {

    private showFXMLLogin showFormLogin = new showFXMLLogin();
    @FXML
    private AnchorPane anchorPaneInfoCustomer;
    @FXML
    private VBox vBox_Employee_Info;
    @FXML
    private HBox HboxHeader;
    @FXML
    private HBox hBox_Info_Parent;
    @FXML
    private VBox vBox_Info_Left;
    @FXML
    private HBox HboxBoxId;
    @FXML
    private JFXTextField Username;
    @FXML
    private JFXTextField CustomerID;
    @FXML
    private JFXDatePicker birthday;
    @FXML
    private JFXTextField PhoneNumber;
    @FXML
    private JFXTextField Passport;
    @FXML
    private JFXRadioButton Male;
    @FXML
    private ToggleGroup Sex;
    @FXML
    private JFXRadioButton Female;
    @FXML
    private VBox vBox_Info_Right;
    @FXML
    private JFXTextField FirstName;
    @FXML
    private JFXTextField MidName;
    @FXML
    private JFXTextField LastName;
    @FXML
    private JFXTextField Company;
    @FXML
    private JFXTextField Discount;
    @FXML
    private HBox HboxContent;
    @FXML
    private HBox Hboxbtn;
    @FXML
    private JFXButton btnInfo;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private JFXTextField Email;
    public static boolean checkInfoCustomer = false;
    public static boolean checkInfoCustomerAlready = false;
    public static String CustomerIdConect;
    private FXMLListCustomerController fXMLListCustomerController;
    private FXMLMainFormController fXMLMainFormController;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        set when click edit customer in mainform
        System.out.println("FXMLMainFormController.checkRegis" + FXMLMainFormController.checkRegis);
        System.out.println(FXMLCheckIdCardCustomerController.checkIdCardCustomer);
        if (!FXMLCheckIdCardCustomerController.checkIdCardCustomerAlready) {
            //Set datepicker :
            birthday.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    LocalDate today = LocalDate.ofYearDay(LocalDate.now().getYear() - 18, LocalDate.now().getDayOfYear());
                    setDisable(empty || date.compareTo(today) > 0);
                }
            });
            birthday.setValue(LocalDate.ofYearDay(LocalDate.now().getYear() - 18, LocalDate.now().getDayOfYear()));
            String pattern = "dd-MM-yyyy";
            formatCalender.format(pattern, birthday);
            birthday.getEditor().setText("Date Of Birth");
            birthday.setPromptText(null);
            birthday.getStyleClass().add("jfx-date-picker-fix");
        }

//        trường hợp check in mà customer chưa được tạo
        if (FXMLCheckIdCardCustomerController.checkIdCardCustomer) {
            checkInfoCustomer = true;
            System.out.println(FXMLCheckIdCardCustomerController.IdCardCustomer);
            Passport.setDisable(true);
            Passport.setText(FXMLCheckIdCardCustomerController.IdCardCustomer);
            CustomerID.setText("CTM-" + FXMLCheckIdCardCustomerController.IdCardCustomer);
            CustomerIdConect = CustomerID.getText();
        }
        btnInfo.setOnAction((event) -> {
            try {
                btnSubmitAddCustomer();
            } catch (ClassNotFoundException | SQLException | IOException ex) {
                Logger.getLogger(FXMLInfoCustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        //        trường hợp check in mà customer đã được tạo
        if (FXMLCheckIdCardCustomerController.checkIdCardCustomerAlready) {
            Customer ctm = new Customer();
            try {
                ctm = DAOCustomerBookingCheckIn.getAllCustomerInfo("CTM-" + FXMLCheckIdCardCustomerController.IdCardCustomer);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(FXMLInfoCustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
            checkInfoCustomerAlready = true;
            PhoneNumber.setText(ctm.getPhone());
            if (ctm.getSex()) {
                Male.isSelected();
                Male.setDisable(true);
                Female.setDisable(true);
            } else {
                Female.isSelected();
                Male.setDisable(true);
                Female.setDisable(true);
            }
            birthday.setValue(LocalDate.parse(ctm.getDate()));
            String patternt = "dd-MM-yyyy";
            formatCalender.format(patternt, birthday);
            FirstName.setText(ctm.getFName());
            LastName.setText(ctm.getLName());
            MidName.setText(ctm.getMName());
            Email.setText(ctm.getEmail());
            Company.setText(ctm.getCompany());
            Discount.setText(String.valueOf(ctm.getDiscount()));
            birthday.setDisable(true);
            PhoneNumber.setDisable(true);
            FirstName.setDisable(true);
            MidName.setDisable(true);
            LastName.setDisable(true);
            Email.setDisable(true);
            Company.setDisable(true);
            Passport.setDisable(true);
            Passport.setText(FXMLCheckIdCardCustomerController.IdCardCustomer);
            CustomerID.setText("CTM-" + FXMLCheckIdCardCustomerController.IdCardCustomer);
            CustomerIdConect = CustomerID.getText();
            btnInfo.setOnAction((event) -> {
                try {
                    btnSubmitAddCustomer();
                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(FXMLInfoCustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        btnInfo.setOnAction((event) -> {
            try {
                btnSubmitAddCustomer();
            } catch (ClassNotFoundException | SQLException | IOException ex) {
                Logger.getLogger(FXMLInfoCustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

//        set discount
        Discount.setText("0");
//        set UserName
        Username.setText(FXMLLoginController.User_Login);
//   set enter for textfield
        CustomerID.setOnKeyPressed((KeyEvent event) -> {
            Platform.runLater(() -> {
                CustomerID.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                HboxContent.getChildren().clear();
            });
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    btnSubmitAddCustomer();
                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(FXMLInfoCustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        birthday.valueProperty().addListener((obs, oldItem, newItem) -> {
            Platform.runLater(() -> {
                birthday.setStyle("-jfx-default-color: #6447cd;");
                HboxContent.getChildren().clear();
            });
        });
        birthday.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            Platform.runLater(() -> {
                HboxContent.getChildren().clear();
            });
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    btnSubmitAddCustomer();
                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(FXMLInfoCustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        PhoneNumber.setOnKeyPressed((KeyEvent event) -> {
            Platform.runLater(() -> {
                PhoneNumber.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                HboxContent.getChildren().clear();
            });
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    btnSubmitAddCustomer();
                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(FXMLInfoCustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        Email.setOnKeyPressed((KeyEvent event) -> {
            Platform.runLater(() -> {
                Email.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                HboxContent.getChildren().clear();
            });
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    btnSubmitAddCustomer();
                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(FXMLInfoCustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        Passport.setOnKeyPressed((KeyEvent event) -> {
            Platform.runLater(() -> {
                Passport.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                HboxContent.getChildren().clear();
            });
            if (event.getCode() == KeyCode.ENTER) {

                try {
                    btnSubmitAddCustomer();
                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(FXMLInfoCustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        FirstName.setOnKeyPressed((KeyEvent event) -> {
            Platform.runLater(() -> {
                FirstName.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                HboxContent.getChildren().clear();
            });
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    btnSubmitAddCustomer();
                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(FXMLInfoCustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        MidName.setOnKeyPressed((KeyEvent event) -> {
            Platform.runLater(() -> {
                MidName.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                HboxContent.getChildren().clear();
            });
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    btnSubmitAddCustomer();
                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(FXMLInfoCustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        LastName.setOnKeyPressed((KeyEvent event) -> {
            Platform.runLater(() -> {
                LastName.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                HboxContent.getChildren().clear();
            });
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    btnSubmitAddCustomer();
                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(FXMLInfoCustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        Company.setOnKeyPressed((KeyEvent event) -> {
            Platform.runLater(() -> {
                Company.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                HboxContent.getChildren().clear();
            });
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    btnSubmitAddCustomer();
                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(FXMLInfoCustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        Discount.setOnKeyPressed((KeyEvent event) -> {
            Platform.runLater(() -> {
                Discount.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                HboxContent.getChildren().clear();
            });
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    btnSubmitAddCustomer();
                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(FXMLInfoCustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    @FXML
    private void Format_Show_Calender(ActionEvent event) {
        String pattern = "dd-MM-yyyy";
        formatCalender.format(pattern, birthday);
        birthday.setPromptText("Date Of Birth");
        birthday.getStyleClass().remove("jfx-date-picker-fix");

    }

    @FXML
    private void Cancel(ActionEvent event) {
        checkInfoCustomer = false;
        checkInfoCustomerAlready = false;
        FXMLCheckIdCardCustomerController.checkIdCardCustomerAlready = false;
        FXMLCheckIdCardCustomerController.checkIdCardCustomer = false;

        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    public void btnSubmitAddCustomer() throws ClassNotFoundException, SQLException, IOException {
        if (!DAOcheckRole.checkRoleDecentralization(FXMLLoginController.User_Login, "Customer_Add")) {
            AlertLoginAgain.alertLogin();
            fXMLMainFormController = ConnectControllers.getfXMLMainFormController();
            Stage stageMainForm = (Stage) fXMLMainFormController.AnchorPaneMainForm.getScene().getWindow();
            Stage stage = (Stage) anchorPaneInfoCustomer.getScene().getWindow();
            stage.close();
            stageMainForm.close();
            try {
                showFormLogin.showFormLogin();
            } catch (IOException ex) {
                Logger.getLogger(FXMLInfoCustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (FXMLListCustomerController.check_Edit_Action) {
                fXMLListCustomerController = ConnectControllers.getfXMLListCustomerController();
                fXMLListCustomerController.check_Add_New = true;
                fXMLListCustomerController.new_Cus_ID = CustomerID.getText();
            }
            btnInfo.setDisable(true);
            // Đoạn này làm loading (đang làm chạy vô tận)

            // Khai báo stage nhìn xuyên thấu
            StageLoader stageLoader = new StageLoader();
            stageLoader.loadingIndicator("Loading");
            Task loadOverview = new Task() {
                @Override
                protected Object call() throws Exception {
                    Platform.runLater(() -> {
                        HboxContent.getChildren().clear();

                    });
                    formSubmitAction();
                    return null;
                }
            };

            loadOverview.setOnSucceeded(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    Platform.runLater(() -> {
                        btnInfo.setDisable(false);
                        stageLoader.stopTimeline();
                        stageLoader.closeStage();
                    });
                }
            });
            new Thread(loadOverview).start();
        }
    }

    public void formSubmitAction() {
        if (birthday.getValue() == null) {
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
        } else if (((PhoneNumber.getText() == null || PhoneNumber.getText().equals(""))) && !FXMLCheckIdCardCustomerController.checkIdCardCustomerAlready && !FXMLCheckIdCardCustomerController.checkIdCardCustomer) {
            System.out.println("FXMLCheckIdCardCustomerController.checkIdCardCustomerAlready= " + FXMLCheckIdCardCustomerController.checkIdCardCustomerAlready);
            System.out.println("FXMLCheckIdCardCustomerController.checkIdCardCustomer= " + FXMLCheckIdCardCustomerController.checkIdCardCustomer);
            Platform.runLater(() -> {
                notificationFunction.notification(PhoneNumber, HboxContent, "PHONE NUMBER MUST NOT EMPTY !!!");
            });
        } else if (Passport.getText() == null || Passport.getText().equals("")) {
            Platform.runLater(() -> {
                notificationFunction.notification(Passport, HboxContent, "PASSPORT MUST NOT EMPTY !!!");
            });
        } else if (FirstName.getText() == null || FirstName.getText().equals("")) {
            Platform.runLater(() -> {
                notificationFunction.notification(FirstName, HboxContent, "FIRST NAME MUST NOT EMPTY !!!");
            });
        } else if (LastName.getText() == null || LastName.getText().equals("")) {
            Platform.runLater(() -> {
                notificationFunction.notification(LastName, HboxContent, "LAST NAME MUST NOT EMPTY !!!");
            });
        } else if (!PatternValided.PatternID(CustomerID.getText())) {
            Platform.runLater(() -> {
                notificationFunction.notification(CustomerID, HboxContent, "CUSTOMER ID IS INCORRECT !!!");
            });
        } else if (!PatternValided.PatternPhoneNumber(PhoneNumber.getText()) && !FXMLCheckIdCardCustomerController.checkIdCardCustomerAlready && !FXMLCheckIdCardCustomerController.checkIdCardCustomer) {
            Platform.runLater(() -> {
                notificationFunction.notification(PhoneNumber, HboxContent, "PHONE NUMBER IS INCORRECT !!!");
            });
        } else if (!PatternValided.PatternCMND(Passport.getText())) {
            Platform.runLater(() -> {
                notificationFunction.notification(Passport, HboxContent, "PASSPORT IS INCORRECT !!!");
            });
        } else if (!PatternValided.PatternName(FirstName.getText())) {
            Platform.runLater(() -> {
                notificationFunction.notification(FirstName, HboxContent, "FIRST NAME INVALID (Example: Nguyễn, Lê,...) !!!");
            });
        } else if (!PatternValided.PatternName(MidName.getText()) && !MidName.getText().equals("")) {
            Platform.runLater(() -> {
                notificationFunction.notification(MidName, HboxContent, "MIDNAME INVALID (Example: Thị, Văn,...) !!!");
            });
        } else if (!PatternValided.PatternName(LastName.getText())) {
            Platform.runLater(() -> {
                notificationFunction.notification(LastName, HboxContent, "LASTNAME INVALID (Example: Nguyễn, Trần,...) !!!");
            });
        } else if (!PatternValided.PatternEmail(Email.getText()) && !Email.getText().equals("")) {
            Platform.runLater(() -> {
                notificationFunction.notification(Email, HboxContent, "EMAIL INVALID !!!");
            });
        } else if (!DAOCustomerBookingCheckIn.check_IDCustomer(CustomerID.getText()) && !FXMLCheckIdCardCustomerController.checkIdCardCustomerAlready) {
            Platform.runLater(() -> {
                if (DAOCustomerBookingCheckIn.check_Active_Customer(CustomerID.getText())) {
                    notificationFunction.notification(Passport, HboxContent, "CUSTOMER ID ALREADY EXIST !!!");
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Comfirm");
                    alert.setContentText("Customer Id = " + CustomerID.getText() + " Already Exist, You Must ReActive To Continue Using!!!");
                    alert.showAndWait();
                    System.out.println(alert.getResult());
                    if (alert.getResult() == ButtonType.OK) {
                        try {
                            DAOCustomerBookingCheckIn.ReActive_Customer(CustomerID.getText());
                            FirstName.setText("");
                            MidName.setText("");
                            LastName.setText("");
                            Email.setText("");
                            PhoneNumber.setText("");
                            Passport.setText("");
                            Discount.setText("0");
                            Company.setText("");
                            birthday.setValue(LocalDate.ofYearDay(LocalDate.now().getYear() - 18, LocalDate.now().getDayOfYear()));
                            String pattern = "dd-MM-yyyy";
                            formatCalender.format(pattern, birthday);
                            birthday.getEditor().setText("Date Of Birth");
                            birthday.setPromptText(null);
                            birthday.getStyleClass().add("jfx-date-picker-fix");

//            messenge when add complete
                            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CHECK);
                            icon.setSize("16");
                            icon.setStyleClass("jfx-glyhp-icon-finish");
                            Label label = new Label();
                            label.setStyle("-fx-text-fill: #6447cd; -fx-font-size : 11px;-fx-font-weight: bold;");
                            label.setPrefSize(300, 35);
                            label.setText("REACTIVE FOR " + CustomerID.getText() + " COMPLETE!!!");
                            HboxContent.setAlignment(Pos.CENTER_LEFT);
                            HboxContent.setSpacing(10);
                            HboxContent.getChildren().clear();
                            HboxContent.getChildren().add(icon);
                            HboxContent.getChildren().add(label);
                            CustomerID.setText("");
                        } catch (ClassNotFoundException | SQLException ex) {
                            Logger.getLogger(FXMLInfoCustomerController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
        } else {
            Platform.runLater(() -> {
                if (!FXMLCheckIdCardCustomerController.checkIdCardCustomerAlready) {
                    Customer ctm = new Customer();
                    String date = birthday.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    ctm.setCusID(CustomerID.getText());
                    ctm.setUser(Username.getText());
                    ctm.setDate(date);
                    ctm.setActive(true);
                    ctm.setSex(Male.isSelected());
                    ctm.setCompany(FormatName.format(Company.getText()));
                    ctm.setDiscount(Float.valueOf(Discount.getText()));
                    ctm.setEmail(Email.getText());
                    ctm.setPassport(Passport.getText());
                    ctm.setFName(FormatName.format(FirstName.getText()));
                    ctm.setMName(FormatName.format(MidName.getText()));
                    ctm.setLName(FormatName.format(LastName.getText()));
                    ctm.setPhone(PhoneNumber.getText());
                    try {
                        DAOCustomerBookingCheckIn.AddNewCustomer(ctm);
                    } catch (MalformedURLException | SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(FXMLInfoCustomerController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    DAO.setUserLogs_With_MAC(FXMLLoginController.User_Login, "Create customer " + CustomerID.getText(),
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), GetInetAddress.getMacAddress());
                    //            reset textfield when add complete

                    FirstName.setText("");
                    MidName.setText("");
                    LastName.setText("");
                    Email.setText("");
                    PhoneNumber.setText("");
                    Passport.setText("");
                    Discount.setText("0");
                    Company.setText("");
                    birthday.setValue(LocalDate.ofYearDay(LocalDate.now().getYear() - 18, LocalDate.now().getDayOfYear()));
                    String pattern = "dd-MM-yyyy";
                    formatCalender.format(pattern, birthday);
                    birthday.getEditor().setText("Date Of Birth");
                    birthday.setPromptText(null);
                    birthday.getStyleClass().add("jfx-date-picker-fix");

//            messenge when add complete
                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CHECK);
                    icon.setSize("16");
                    icon.setStyleClass("jfx-glyhp-icon-finish");
                    Label label = new Label();
                    label.setStyle("-fx-text-fill: #6447cd; -fx-font-size : 11px;-fx-font-weight: bold;");
                    label.setPrefSize(300, 35);
                    label.setText("CREATE " + CustomerID.getText() + " COMPLETE!!!");
                    HboxContent.setAlignment(Pos.CENTER_LEFT);
                    HboxContent.setSpacing(10);
                    HboxContent.getChildren().clear();
                    HboxContent.getChildren().add(icon);
                    HboxContent.getChildren().add(label);
                    CustomerID.setText("");
                }
                if (FXMLListCustomerController.check_Edit_Action) {
                    fXMLListCustomerController = ConnectControllers.getfXMLListCustomerController();
                    fXMLListCustomerController.showUsersData();
                }
// kiem tra chay theo click check in tu main form
                if (FXMLCheckIdCardCustomerController.checkIdCardCustomer || FXMLCheckIdCardCustomerController.checkIdCardCustomerAlready) {

                    checkInfoCustomer = true;
                    checkInfoCustomerAlready = true;

//                    neu customer da ton tai ma active = 0 thi update active = 1
                    if (!DAOCustomerBookingCheckIn.check_Active_Customer(CustomerID.getText()) && FXMLCheckIdCardCustomerController.checkIdCardCustomerAlready) {
                        try {
                            DAOCustomerBookingCheckIn.ReActive_Customer(CustomerID.getText());
                        } catch (ClassNotFoundException | SQLException ex) {
                            Logger.getLogger(FXMLInfoCustomerController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    Stage stageEdit = new Stage();
                    stageEdit.resizableProperty().setValue(Boolean.FALSE);
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("/fxml/FXMLBookingInfo.fxml"));
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLInfoCustomerController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    stageEdit.getIcons().add(new Image("/images/KAN Logo.png"));
                    Scene scene = new Scene(root);
                    stageEdit.setScene(scene);
                    stageEdit.setOnCloseRequest((event) -> {
                        checkInfoCustomer = false;
                        checkInfoCustomerAlready = false;
                        System.out.println("checkInfoCustomer " + checkInfoCustomer);
                        System.out.println("checkInfoCustomerAlready " + checkInfoCustomerAlready);
                    });
                    stageEdit.show();
                    Stage stage = (Stage) anchorPaneInfoCustomer.getScene().getWindow();
                    stage.close();
                    FXMLCheckIdCardCustomerController.checkIdCardCustomerAlready = false;
                    FXMLCheckIdCardCustomerController.checkIdCardCustomer = false;
                }

            });
        }
    }

    @FXML
    private void actionSetCustomerID() {
        CustomerID.setText("CTM-" + Passport.getText());
    }
}
