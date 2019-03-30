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
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.DateCell;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.BookingInfo;
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
public class FXMLEditCustomerController implements Initializable {

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
    private JFXComboBox<String> CustomerID;
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
    private JFXTextField Email;
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
    private FontAwesomeIconView iconRefresh;
    private ObservableList<String> list_Id_Customer = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnInfo.setOnAction((event) -> {
            try {
                btnSubmitEdit();
            } catch (ClassNotFoundException | SQLException | IOException ex) {
                Logger.getLogger(FXMLInfoCustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
//        get all id of customer set combobox idcustomer   
        refreshIdCustomer();
        CustomerID.setItems(list_Id_Customer);
        birthday.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.ofYearDay(LocalDate.now().getYear() - 18, LocalDate.now().getDayOfYear());
                setDisable(empty || date.compareTo(today) > 0);
            }
        });
//        set enter for textfield
        Username.setText(FXMLLoginController.User_Login);
//   set enter for textfield
        CustomerID.setOnKeyPressed((KeyEvent event) -> {
            Platform.runLater(() -> {
                CustomerID.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                HboxContent.getChildren().clear();
            });
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    btnSubmitEdit();
                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        CustomerID.valueProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null && !newItem.equals("")) {
                HboxContent.getChildren().clear();
                CustomerID.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                Customer ctm = new Customer();
                try {
                    ctm = DAOCustomerBookingCheckIn.getAllCustomerInfo(newItem);
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(FXMLEditCustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
                birthday.setValue(LocalDate.parse(ctm.getDate()));
                String patternt = "dd-MM-yyyy";
                formatCalender.format(patternt, birthday);
                PhoneNumber.setText(ctm.getPhone());
                Passport.setText(ctm.getPassport());
                FirstName.setText(ctm.getFName());
                LastName.setText(ctm.getLName());
                MidName.setText(ctm.getMName());
                Email.setText(ctm.getEmail());
                Company.setText(ctm.getCompany());
                Discount.setText(String.valueOf(ctm.getDiscount()));
                if (ctm.getSex()) {
                    Male.setSelected(true);
                } else {
                    Female.setSelected(true);
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
                    btnSubmitEdit();
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
                    btnSubmitEdit();
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
                    btnSubmitEdit();
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
                    btnSubmitEdit();
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
                    btnSubmitEdit();
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
                    btnSubmitEdit();
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
                    btnSubmitEdit();
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
                    btnSubmitEdit();
                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(FXMLInfoCustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void btnSubmitEdit() throws ClassNotFoundException, SQLException, IOException {
        if (!DAOcheckRole.checkRoleDecentralization(FXMLLoginController.User_Login, "Customer_Edit")) {
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
        if (CustomerID.getValue() == null || CustomerID.getValue().equals("")) {
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(350, 35);
                label.setText("ID CUSTOMER MUST NOT EMPTY !!!");
                label.setAlignment(Pos.CENTER_LEFT);
                CustomerID.getStyleClass().removeAll();
                CustomerID.getStyleClass().add("jfx-combo-box-fault");
                HboxContent.setSpacing(10);
                HboxContent.setAlignment(Pos.CENTER_LEFT);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
                CustomerID.requestFocus();
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
        } else if (FirstName.getText() == null || FirstName.getText().equals("")) {
            Platform.runLater(() -> {
                notificationFunction.notification(FirstName, HboxContent, "FIRST NAME MUST NOT EMPTY !!!");
            });
        } else if (LastName.getText() == null || LastName.getText().equals("")) {
            Platform.runLater(() -> {
                notificationFunction.notification(LastName, HboxContent, "LAST NAME MUST NOT EMPTY !!!");
            });
        } else if (!PatternValided.PatternPhoneNumber(PhoneNumber.getText()) && ((PhoneNumber.getText() != null || !PhoneNumber.getText().equals("")))) {
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
        } else if (DAOCustomerBookingCheckIn.check_IDCustomer(CustomerID.getValue())) {
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(350, 35);
                label.setText("ID CUSTOMER DOES NOT EXIST!!!");
                label.setAlignment(Pos.CENTER_LEFT);
                CustomerID.getStyleClass().removeAll();
                CustomerID.getStyleClass().add("jfx-combo-box-fault");
                HboxContent.setSpacing(10);
                HboxContent.setAlignment(Pos.CENTER_LEFT);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
                CustomerID.requestFocus();
            });
        } else {
            Platform.runLater(() -> {
                Customer ctm = new Customer();
                String date = birthday.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                ctm.setCusID(CustomerID.getValue());
                ctm.setUser(Username.getText());
                ctm.setDate(date);
                ctm.setSex(Male.isSelected());
                ctm.setCompany(FormatName.format(Company.getText()));
                ctm.setDiscount(Float.valueOf(Discount.getText()));
                ctm.setEmail(Email.getText());
                ctm.setFName(FormatName.format(FirstName.getText()));
                ctm.setMName(FormatName.format(MidName.getText()));
                ctm.setLName(FormatName.format(LastName.getText()));
                ctm.setPhone(PhoneNumber.getText());
                try {
                    DAOCustomerBookingCheckIn.UpdateInfoCustomer(ctm);
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(FXMLEditCustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
                DAO.setUserLogs_With_MAC(FXMLLoginController.User_Login, "Update customer " + CustomerID.getValue(),
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), GetInetAddress.getMacAddress());
                FirstName.setText("");
                MidName.setText("");
                LastName.setText("");
                Email.setText("");
                PhoneNumber.setText("");
                Passport.setText("");
                Discount.setText("0");
                Company.setText("");
                birthday.setValue(null);
                CustomerID.setValue(null);
            });
        }
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
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    public void refreshIdCustomer() {
        try {
            list_Id_Customer = DAOCustomerBookingCheckIn.getAllIdCustomer();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLEditCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
