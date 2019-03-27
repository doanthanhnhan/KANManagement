/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import static controllers.ConnectControllers.fXMLMainFormController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.BookingInfo;
import models.CheckIn;
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
public class FXMLCheckInOrdersController implements Initializable {

    @FXML
    private AnchorPane anchorPaneCheckInOrders;
    @FXML
    private VBox vBox_Employee_Info;
    @FXML
    private HBox HboxHeader;
    @FXML
    private HBox hBox_Info_Parent;
    @FXML
    private VBox vBox_Info_Left;
    @FXML
    private JFXTextField Username;
    @FXML
    private JFXTextField CheckInID;
    @FXML
    private HBox HboxBoxId;
    @FXML
    private JFXComboBox<String> boxBookingID;
    @FXML
    private JFXTextField RoomID;
    @FXML
    private VBox vBox_Info_Right;
    @FXML
    private JFXTextField NumberOfCustomer;
    @FXML
    private JFXDatePicker CheckInDate;
    @FXML
    private JFXDatePicker LeaveDate;
    @FXML
    private JFXComboBox<?> boxCheckInType;
    @FXML
    private JFXTextField CustomerPackage;
    @FXML
    private HBox HboxContent;
    @FXML
    private HBox Hboxbtn;
    @FXML
    private JFXButton btnInfo;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private JFXTextField CustomerID;
    @FXML
    private FontAwesomeIconView iconRefresh;
    private showFXMLLogin showFormLogin = new showFXMLLogin();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnInfo.setOnAction((event) -> {
            try {
                enter_Submit_Action();
            } catch (ClassNotFoundException | SQLException | IOException ex) {
                Logger.getLogger(FXMLInfoCustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        LeaveDate.setDisable(true);
//        set box booking id 
        refreshIdUser();
//        set value for box checkin type
        ObservableList list_checkintype = FXCollections.observableArrayList();
        list_checkintype.add("Reception Desk");
        list_checkintype.add("Check In Online");
        list_checkintype.add("Company");
        list_checkintype.add("Booking");
        boxCheckInType.setItems(list_checkintype);
//        set value for username
        Username.setText(FXMLLoginController.User_Login);
//        set value and enter for box booking id
        boxBookingID.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                System.out.println("Enter pressed");
                try {
                    enter_Submit_Action();
                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(FXMLCheckInOrdersController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        boxCheckInType.setOnKeyPressed((KeyEvent event) -> {
            Platform.runLater(() -> {
                boxCheckInType.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                HboxContent.getChildren().clear();
            });
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    enter_Submit_Action();
                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        LeaveDate.valueProperty().addListener((obs, oldItem, newItem) -> {
            Platform.runLater(() -> {
                LeaveDate.setStyle("-jfx-default-color: #6447cd;");
                HboxContent.getChildren().clear();
            });
        });
        LeaveDate.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            Platform.runLater(() -> {
                HboxContent.getChildren().clear();
            });
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    enter_Submit_Action();
                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        CheckInID.setOnKeyPressed((KeyEvent event) -> {
            Platform.runLater(() -> {
                CheckInID.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                HboxContent.getChildren().clear();
            });
            if (event.getCode() == KeyCode.ENTER) {

                try {
                    enter_Submit_Action();
                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        CustomerPackage.setOnKeyPressed((KeyEvent event) -> {
            Platform.runLater(() -> {
                CustomerPackage.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                HboxContent.getChildren().clear();
            });
            if (event.getCode() == KeyCode.ENTER) {

                try {
                    enter_Submit_Action();
                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
//        install when click box booking id show all info booking
        boxBookingID.valueProperty().addListener((obs, oldItem, newItem) -> {
            System.out.println("Kiem tra newItem: " + newItem);
            if (newItem != null && !newItem.equals("")) {
                LeaveDate.setDisable(false);
                LeaveDate.setDayCellFactory(picker -> new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);
                        LocalDate today = LocalDate.now();
                        setDisable(empty || date.compareTo(today) < 0);
                    }
                });
                BookingInfo bk = new BookingInfo();
                try {
                    bk = DAOCustomerBookingCheckIn.getAllBookingInfo(newItem);
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(FXMLCheckInOrdersController.class.getName()).log(Level.SEVERE, null, ex);
                }
                CustomerID.setText(bk.getCusID());
                NumberOfCustomer.setText(String.valueOf(bk.getNumGuest()));
                RoomID.setText(bk.getRoomID());
                CheckInDate.setValue(LocalDate.now());
                CheckInID.setText("CI-"+newItem);
                String pattern = "dd-MM-yyyy";
                formatCalender.format(pattern, CheckInDate);
            }
            HboxContent.getChildren().clear();
            boxBookingID.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
        });
        // TODO
    }

    @FXML
    private void Format_Show_Calender_Hiredate(ActionEvent event) {
        String pattern = "dd-MM-yyyy";
        formatCalender.format(pattern, LeaveDate);
    }

    @FXML
    private void Cancel(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    private void enter_Submit_Action() throws ClassNotFoundException, SQLException, IOException {
        if (!DAOcheckRole.checkRoleDecentralization(FXMLLoginController.User_Login, "CheckIn_Add")) {
            AlertLoginAgain.alertLogin();
            fXMLMainFormController = ConnectControllers.getfXMLMainFormController();
            Stage stageMainForm = (Stage) fXMLMainFormController.AnchorPaneMainForm.getScene().getWindow();
            Stage stage = (Stage) anchorPaneCheckInOrders.getScene().getWindow();
            stage.close();
            stageMainForm.close();
            showFormLogin.showFormLogin();
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
                        CheckInSubmitAction();
                    });

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

    public void CheckInSubmitAction() {
        if (CheckInID.getText() == null || CheckInID.getText().equals("")) {
            Platform.runLater(() -> {
                notificationFunction.notification(CheckInID, HboxContent, "Check In ID MUST NOT EMPTY !!!");
            });
        } else if (boxBookingID.getValue() == null) {
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(350, 35);
                label.setText("ID BOOKING MUST NOT EMPTY !!!");
                label.setAlignment(Pos.CENTER_LEFT);
                boxBookingID.getStyleClass().removeAll();
                boxBookingID.getStyleClass().add("jfx-combo-box-fault");
                HboxContent.setSpacing(10);
                HboxContent.setAlignment(Pos.CENTER_LEFT);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
                boxBookingID.requestFocus();
            });
        } else if (boxCheckInType.getValue() == null) {
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(350, 35);
                label.setText("CHECK IN TYPE MUST NOT EMPTY !!!");
                label.setAlignment(Pos.CENTER_LEFT);
                boxCheckInType.getStyleClass().removeAll();
                boxCheckInType.getStyleClass().add("jfx-combo-box-fault");
                HboxContent.setSpacing(10);
                HboxContent.setAlignment(Pos.CENTER_LEFT);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
                boxCheckInType.requestFocus();
            });
        } else if (!PatternValided.PatternID(CheckInID.getText())) {
            Platform.runLater(() -> {
                notificationFunction.notification(CheckInID, HboxContent, "CHECK IN ID IS INCORRECT !!!");
            });
        } else {
            Platform.runLater(() -> {
                String dateIn = CheckInDate.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
                String dateOut = LeaveDate.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
                CheckIn ck = new CheckIn();
                ck.setBookID(boxBookingID.getValue());
                ck.setUser(Username.getText());
                ck.setCheckID(CheckInID.getText());
                ck.setCusID(CustomerID.getText());
                ck.setRoomID(RoomID.getText());
                ck.setNumberOfCustomer(Integer.valueOf(NumberOfCustomer.getText()));
                ck.setDateIn(dateIn);
                ck.setDateOut(dateOut);
                ck.setCusPack(CustomerPackage.getText());
                ck.setCheckType((String) boxCheckInType.getValue());
                try {
                    DAOCustomerBookingCheckIn.AddNewCheckIn(ck);
                } catch (MalformedURLException | SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(FXMLCheckInOrdersController.class.getName()).log(Level.SEVERE, null, ex);
                }
                DAO.setUserLogs_With_MAC(FXMLLoginController.User_Login, "Add Check In for booking id = " + boxBookingID.getValue(),
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), GetInetAddress.getMacAddress());
                CheckInID.setText("");
                boxBookingID.setValue(null);
                CustomerID.setText("");
                RoomID.setText("");
                NumberOfCustomer.setText("");
                CheckInDate.setValue(null);
                LeaveDate.setValue(null);
                boxCheckInType.setValue(null);
                CustomerPackage.setText("");
                refreshIdUser();
            });
        }
    }

    @FXML
    private void refreshIdUser() {
        ObservableList list_Booking_Id = FXCollections.observableArrayList();
        try {
            //        set add booking id for box booking id
            boxBookingID.setItems(DAOCustomerBookingCheckIn.getAllBookingID());
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLCheckInOrdersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}