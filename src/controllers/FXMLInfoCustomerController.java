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
import static controllers.ConnectControllers.fXMLMainFormController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
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

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
    }

    @FXML
    private void Cancel(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    private void btnSubmitAddCustomer() throws ClassNotFoundException, SQLException, IOException {
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
            btnInfo.setDisable(true);
            // Đoạn này làm loading (đang làm chạy vô tận)

            // Khai báo stage nhìn xuyên thấu
            StageLoader stageLoader = new StageLoader();
            stageLoader.loadingIndicator("Loading");
            Task loadOverview = new Task() {
                @Override
                protected Object call() throws Exception {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            HboxContent.getChildren().clear();
                        }
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
        if (CustomerID.getText() == null || CustomerID.getText().equals("")) {
            Platform.runLater(() -> {
                notificationFunction.notification(CustomerID, HboxContent, "CUSTOMER ID MUST NOT EMPTY !!!");
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
        } else if (PhoneNumber.getText() == null || PhoneNumber.getText().equals("")) {
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
        } else if (!PatternValided.PatternPhoneNumber(PhoneNumber.getText())) {
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
        } else if (!DAOCustomerBookingCheckIn.check_IDCustomer(CustomerID.getText())) {
            Platform.runLater(() -> {
                notificationFunction.notification(CustomerID, HboxContent, "CUSTOMER ID ALREADY EXIST !!!");
            });
        } else {
            Platform.runLater(() -> {
                Customer ctm = new Customer();
                String date = birthday.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                ctm.setCusID(CustomerID.getText());
                ctm.setUser(Username.getText());
                ctm.setDate(date);
                ctm.setActive(true);
                ctm.setSex(Male.isSelected());
                ctm.setCompany(Company.getText());
                ctm.setDiscount(Float.valueOf(Discount.getText()));
                ctm.setEmail(Email.getText());
                ctm.setPassport(Passport.getText());
                ctm.setFName(FirstName.getText());
                ctm.setMName(MidName.getText());
                ctm.setLName(LastName.getText());
                ctm.setPhone(PhoneNumber.getText());
                try {
                    DAOCustomerBookingCheckIn.AddNewCustomer(ctm);
                } catch (MalformedURLException | SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(FXMLInfoCustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
                DAO.setUserLogs_With_MAC(FXMLLoginController.User_Login, "Create customer " + CustomerID.getText(),
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), GetInetAddress.getMacAddress());
//            messenge when add complete
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CHECK);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon-finish");
                Label label = new Label();
                label.setStyle("-fx-text-fill: #6447cd; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(300, 35);
                label.setText("Create " + CustomerID.getText() + " COMPLETE!!!");
                HboxContent.setAlignment(Pos.CENTER_LEFT);
                HboxContent.setSpacing(10);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
//            reset textfield when add complete
                CustomerID.setText("");
                FirstName.setText("");
                MidName.setText("");
                LastName.setText("");
                Email.setText("");
                birthday.setValue(null);
                PhoneNumber.setText("");
                Passport.setText("");
                Discount.setText("0");
                Company.setText("");
            });
        }
    }
}
