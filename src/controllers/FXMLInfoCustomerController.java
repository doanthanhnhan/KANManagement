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
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
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
import models.formatCalender;
import models.notificationFunction;
import utils.GetInetAddress;
import utils.PatternValided;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class FXMLInfoCustomerController implements Initializable {

    @FXML
    private AnchorPane anchorPaneInfoEmployee;
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
//        set action for btnInfo
        btnInfo.setOnAction((event) -> {
            formSubmitAction();
        });
//   set enter for textfield
        CustomerID.setOnKeyPressed((KeyEvent event) -> {
            CustomerID.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                formSubmitAction();
            }
        });
        birthday.valueProperty().addListener((obs, oldItem, newItem) -> {
            birthday.setStyle("-jfx-default-color: #6447cd;");
            HboxContent.getChildren().clear();
        });
        birthday.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                formSubmitAction();
            }
        });
        PhoneNumber.setOnKeyPressed((KeyEvent event) -> {
            PhoneNumber.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                formSubmitAction();
            }
        });
        Email.setOnKeyPressed((KeyEvent event) -> {
            Email.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                formSubmitAction();
            }
        });
        Passport.setOnKeyPressed((KeyEvent event) -> {
            Passport.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                formSubmitAction();
            }
        });
        FirstName.setOnKeyPressed((KeyEvent event) -> {
            FirstName.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                formSubmitAction();
            }
        });
        MidName.setOnKeyPressed((KeyEvent event) -> {
            MidName.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                formSubmitAction();
            }
        });
        LastName.setOnKeyPressed((KeyEvent event) -> {
            LastName.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                formSubmitAction();
            }
        });
        Company.setOnKeyPressed((KeyEvent event) -> {
            Company.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                formSubmitAction();
            }
        });
        Discount.setOnKeyPressed((KeyEvent event) -> {
            Discount.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            HboxContent.getChildren().clear();
            if (event.getCode() == KeyCode.ENTER) {
                formSubmitAction();
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

    public void formSubmitAction() {
        if (CustomerID.getText() == null || CustomerID.getText().equals("")) {
            notificationFunction.notification(CustomerID, HboxContent, "CUSTOMER ID MUST NOT EMPTY !!!");
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
        } else if (PhoneNumber.getText() == null || PhoneNumber.getText().equals("")) {
            notificationFunction.notification(PhoneNumber, HboxContent, "PHONE NUMBER MUST NOT EMPTY !!!");
        } else if (Passport.getText() == null || Passport.getText().equals("")) {
            notificationFunction.notification(Passport, HboxContent, "PASSPORT MUST NOT EMPTY !!!");
        } else if (FirstName.getText() == null || FirstName.getText().equals("")) {
            notificationFunction.notification(FirstName, HboxContent, "FIRST NAME MUST NOT EMPTY !!!");
        } else if (LastName.getText() == null || LastName.getText().equals("")) {
            notificationFunction.notification(LastName, HboxContent, "LAST NAME MUST NOT EMPTY !!!");
        } else if (!PatternValided.PatternID(CustomerID.getText())) {
            notificationFunction.notification(CustomerID, HboxContent, "CUSTOMER ID IS INCORRECT !!!");
        } else if (!PatternValided.PatternPhoneNumber(PhoneNumber.getText())) {
            notificationFunction.notification(PhoneNumber, HboxContent, "PHONE NUMBER IS INCORRECT !!!");
        } else if (!PatternValided.PatternCMND(Passport.getText())) {
            notificationFunction.notification(Passport, HboxContent, "PASSPORT IS INCORRECT !!!");
        } else if (!PatternValided.PatternName(FirstName.getText())) {
            notificationFunction.notification(FirstName, HboxContent, "FIRST NAME INVALID (Example: Nguyễn, Lê,...) !!!");
        } else if (!PatternValided.PatternName(MidName.getText()) && !MidName.getText().equals("")) {
            notificationFunction.notification(MidName, HboxContent, "MIDNAME INVALID (Example: Thị, Văn,...) !!!");
        } else if (!PatternValided.PatternName(LastName.getText())) {
            notificationFunction.notification(LastName, HboxContent, "LASTNAME INVALID (Example: Nguyễn, Trần,...) !!!");
        } else if (!PatternValided.PatternEmail(Email.getText()) && !Email.getText().equals("")) {
            notificationFunction.notification(Email, HboxContent, "EMAIL INVALID !!!");
        } else if (!DAOCustomerBookingCheckIn.check_IDCustomer(CustomerID.getText())) {
            notificationFunction.notification(CustomerID, HboxContent, "CUSTOMER ID ALREADY EXIST !!!");
        } else {
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
            DAO.setUserLogs_With_MAC(FXMLLoginController.User_Login, "Creat customer " + CustomerID.getText(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), GetInetAddress.getMacAddress());
//            messenge when add complete
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CHECK);
            icon.setSize("16");
            icon.setStyleClass("jfx-glyhp-icon-finish");
            Label label = new Label();
            label.setStyle("-fx-text-fill: #6447cd; -fx-font-size : 11px;-fx-font-weight: bold;");
            label.setPrefSize(300, 35);
            label.setText("Create " + CustomerID.getText() + " COMPLETE!!!");
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
        }
    }
}
