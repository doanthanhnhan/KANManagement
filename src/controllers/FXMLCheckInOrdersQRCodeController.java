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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
import javafx.scene.control.Alert;
import javafx.scene.control.DateCell;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.BookingInfo;
import models.CheckIn;
import models.DAO;
import models.DAOCustomerBookingCheckIn;
import models.DAOcheckRole;
import models.Room;
import models.RoomDAOImpl;
import models.formatCalender;
import models.notificationFunction;
import utils.AlertLoginAgain;
import utils.GetInetAddress;
import utils.PatternValided;
import utils.QRWebCam;
import utils.StageLoader;
import utils.showFXMLLogin;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class FXMLCheckInOrdersQRCodeController implements Initializable {

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
    private JFXComboBox<String> boxRoomID;
    @FXML
    private VBox vBox_Info_Right;
    @FXML
    private JFXTextField NumberOfCustomer;
    @FXML
    private JFXDatePicker CheckInDate;
    @FXML
    private JFXDatePicker LeaveDate;
    @FXML
    private JFXComboBox<String> boxCheckInType;
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
    private JFXButton btnQRCodeScanner;
    @FXML
    private JFXTextField CustomerID;
    @FXML
    private FontAwesomeIconView iconRefresh;

    private showFXMLLogin showFormLogin = new showFXMLLogin();
    private RoomDAOImpl roomDAOImpl;
    ObservableList<String> listRoomID;

    private FXMLMainOverViewPaneController mainOverViewPaneController;
    private Integer numberCustomer;
    private String RoomIDBooking;
    private String LeaveDateBooking;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Setting event mouseclick for iconrefresh
        iconRefresh.setOnMouseClicked((event) -> {
            refreshIdUser();
        });
        //        set box booking id 
        refreshIdUser();
        mainOverViewPaneController = ConnectControllers.getfXMLMainOverViewPaneController();
        roomDAOImpl = new RoomDAOImpl();
        if (FXMLInfoBookingController.checkInfoBooking) {
            System.out.println("check info booking " + FXMLInfoBookingController.checkInfoBooking);
            System.out.println("booking info conect " + FXMLInfoBookingController.bookingIdConect);
            CustomerID.setText(FXMLInfoBookingController.customerIdConect);
            boxBookingID.setDisable(true);
            boxBookingID.setValue(FXMLInfoBookingController.bookingIdConect);
            System.out.println("booking info conect " + boxBookingID.getValue());
            System.out.println("booking info conect " + FXMLInfoBookingController.bookingIdConect);
            boxRoomID.setValue(FXMLInfoBookingController.roomIdConect);
            CheckInID.setText("CI-" + FXMLInfoBookingController.bookingIdConect);
            NumberOfCustomer.setDisable(true);
            LeaveDate.setDisable(false);
            LeaveDate.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    LocalDate today = LocalDate.now();
                    setDisable(empty || date.compareTo(today) < 0);
                }
            });
            CheckInDate.setValue(LocalDate.now());
            HboxBoxId.getChildren().remove(iconRefresh);
            String pattern = "dd-MM-yyyy";
            formatCalender.format(pattern, CheckInDate);
            NumberOfCustomer.setText(FXMLInfoBookingController.numberofcustomer);
            btnInfo.setOnAction((event) -> {
                try {
                    enter_Submit_Action();
                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(FXMLInfoCustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
        } else {
            btnInfo.setOnAction((event) -> {
                try {
                    enter_Submit_Action();
                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(FXMLInfoCustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
//        set value for box checkin type
        ObservableList<String> list_checkintype = FXCollections.observableArrayList();
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
                    Logger.getLogger(FXMLCheckInOrdersQRCodeController.class.getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(FXMLCheckInOrdersQRCodeController.class.getName()).log(Level.SEVERE, null, ex);
                }
                CustomerID.setText(bk.getCusID());
                NumberOfCustomer.setText(String.valueOf(bk.getNumGuest()));
                boxRoomID.setValue(bk.getRoomID());
                CheckInID.setText("CI-" + newItem);
                CheckInDate.setValue(LocalDate.now());
                LeaveDate.setValue(LocalDate.parse(bk.getDateLeave()));
                boxCheckInType.setValue("Reception Desk");
                String pattern = "dd-MM-yyyy";
                formatCalender.format(pattern, CheckInDate);
                formatCalender.format(pattern, LeaveDate);
            }
            HboxContent.getChildren().clear();
            boxBookingID.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
        });
        // TODO        
    }

    @FXML
    private void Format_Show_Calender_Hiredate(ActionEvent event) {
        boxRoomID.setDisable(false);
        LeaveDate.setPromptText("Date Leave");
        String pattern = "dd-MM-yyyy";
        formatCalender.format(pattern, LeaveDate);
        if (LeaveDate.getValue() != null) {
            boolean check_OK = true;
            if (CheckInDate.getValue().compareTo(LeaveDate.getValue()) > 0) {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Error");
                alert1.setHeaderText("You have no right to do this !!!");
                alert1.setContentText("Because Date Leave Cannot Be Bigger Than Date Book !!!");
                alert1.showAndWait();
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                LeaveDate.setStyle("-jfx-default-color: RED;");
                LeaveDate.requestFocus();
                check_OK = false;
            }
            if (check_OK) {
                boxRoomID.setItems(null);
                boxRoomID.setDisable(false);
                try {
                    listRoomID = DAOCustomerBookingCheckIn.getAllRoomBookingNoCheck(String.valueOf(CheckInDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
                            String.valueOf(LeaveDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))), boxBookingID.getValue());
                    boxRoomID.setItems(listRoomID);
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @FXML
    private void Cancel(ActionEvent event) throws ClassNotFoundException, SQLException {
//        if (FXMLInfoBookingController.checkDeleteBookingID.equals(0)) {
//            DAOCustomerBookingCheckIn.deleteBooking(boxBookingID.getValue());
//        }
//        FXMLInfoBookingController.checkInfoBooking = false;
//        FXMLInfoBookingVirtualController.checkInfoBookingVirtual = false;
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
        } else if (NumberOfCustomer.getText() == null || NumberOfCustomer.getText().equals("")) {
            Platform.runLater(() -> {
                notificationFunction.notification(NumberOfCustomer, HboxContent, "NUMBER OF CUSTOMER MUST NOT EMPTY !!!");
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
        } else if (boxRoomID.getValue() == null) {
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(350, 35);
                label.setText("ID ROOM MUST NOT EMPTY !!!");
                label.setAlignment(Pos.CENTER_LEFT);
                boxRoomID.getStyleClass().removeAll();
                boxRoomID.getStyleClass().add("jfx-combo-box-fault");
                HboxContent.setSpacing(10);
                HboxContent.setAlignment(Pos.CENTER_LEFT);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
                boxRoomID.requestFocus();
            });
        } else if (LeaveDate.getValue() == null) {
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(350, 35);
                label.setText("LEAVE DATE MUST NOT EMPTY !!!");
                label.setAlignment(Pos.CENTER_LEFT);
                HboxContent.setSpacing(10);
                HboxContent.setAlignment(Pos.CENTER_LEFT);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
                LeaveDate.requestFocus();
            });
        } else if (CheckInDate.getValue().compareTo(LeaveDate.getValue()) > 0) {
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(350, 35);
                label.setAlignment(Pos.CENTER_LEFT);
                label.setText("Date Leave Cannot Be Bigger Than Date Book !!!");
                LeaveDate.setStyle("-jfx-default-color: RED;");
                HboxContent.setAlignment(Pos.CENTER_LEFT);
                HboxContent.setSpacing(10);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
                LeaveDate.requestFocus();
            });
        } else if (NumberOfCustomer.getText() == null || NumberOfCustomer.getText().equals("")) {
            Platform.runLater(() -> {
                notificationFunction.notification(NumberOfCustomer, HboxContent, "NUMBER OF CUSTOMER MUST NOT EMPTY !!!");
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
                boxBookingID.getStyleClass().add("jfx-combo-box-fault");
                HboxContent.setSpacing(10);
                HboxContent.setAlignment(Pos.CENTER_LEFT);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
                boxCheckInType.requestFocus();
            });
        } else if (!PatternValided.PatternNumberGuest(NumberOfCustomer.getText())) {
            Platform.runLater(() -> {
                notificationFunction.notification(NumberOfCustomer, HboxContent, "NUMBER OF CUSTOMER INCORRECT (1-8) !!!");
            });
        } else if (DAOCustomerBookingCheckIn.check_Booking(boxBookingID.getValue())) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error");
            alert1.setHeaderText("You have no right to do this !!!");
            alert1.setContentText("Because BookingID has been check in !!!");
            alert1.showAndWait();
        } else if (DAOCustomerBookingCheckIn.check_RoomIdAgainBookingalready(boxRoomID.getValue(), String.valueOf(CheckInDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
                String.valueOf(LeaveDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))), boxBookingID.getValue())) {
            Platform.runLater(() -> {
                System.out.println("da vao chekc trung roi");
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(350, 35);
                label.setAlignment(Pos.CENTER_LEFT);
                label.setText("This RoomID Can Not Booking Please Choose Again !!!");
                boxRoomID.setStyle("-jfx-default-color: RED;");
                HboxContent.setAlignment(Pos.CENTER_LEFT);
                HboxContent.setSpacing(10);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
                boxRoomID.requestFocus();
                try {
                    boxRoomID.setItems(DAOCustomerBookingCheckIn.getAllRoomBookingNoCheck(String.valueOf(CheckInDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
                            String.valueOf(LeaveDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))), boxBookingID.getValue()));
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } else if (!String.valueOf(CheckInDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).equals(DAOCustomerBookingCheckIn.DateBookCheck(boxBookingID.getValue()))) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error");
            alert1.setHeaderText("You have no right to do this !!!");
            alert1.setContentText("Because BookingID has been change DateBooking. Please Check Again !!!");
            alert1.showAndWait();
            Stage stage = (Stage) anchorPaneCheckInOrders.getScene().getWindow();
            stage.close();
            FXMLInfoBookingController.checkInfoBooking = false;
        } else {
            Platform.runLater(() -> {
                if (!String.valueOf(LeaveDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).equals(LeaveDateBooking)
                        || !RoomIDBooking.equals(boxRoomID.getValue())) {
                    //DAOCustomerBookingCheckIn.Update_BookingLeave(boxBookingID.getValue(), boxRoomID.getValue(), String.valueOf(LeaveDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                    DAO.setUserLogs_With_MAC(FXMLLoginController.User_Login, "CHECK IN !!! Change InfoBooking booking id = " + boxBookingID.getValue(),
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), GetInetAddress.getMacAddress());
                }
                String dateOut = LeaveDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                CheckIn ck = new CheckIn();
                ck.setBookID(boxBookingID.getValue());
                ck.setUser(Username.getText());
                ck.setCheckID(CheckInID.getText());
                ck.setCusID(CustomerID.getText());
                ck.setRoomID(boxRoomID.getValue());
                ck.setNumberOfCustomer(Integer.valueOf(NumberOfCustomer.getText()));
                ck.setDateIn(String.valueOf(LocalDateTime.now()));
                ck.setDateOut(dateOut);
                ck.setCusPack(CustomerPackage.getText());
                ck.setCheckType((String) boxCheckInType.getValue());
                try {
                    DAOCustomerBookingCheckIn.AddNewCheckIn(ck);
                } catch (MalformedURLException | SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(FXMLCheckInOrdersController.class.getName()).log(Level.SEVERE, null, ex);
                }
                DAO.setUserLogs_With_MAC(FXMLLoginController.User_Login, "Add Check In for booking id = " + boxBookingID.getValue(),
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), GetInetAddress.getMacAddress());
                if (!numberCustomer.equals(NumberOfCustomer.getText())) {
                    try {
                        DAOCustomerBookingCheckIn.Update_NumbercustomerOfBooking(boxBookingID.getValue(), Integer.parseInt(NumberOfCustomer.getText()));
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(FXMLCheckInOrdersController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //Getting Room infomations to update room Status after checking in - Nhan edit here
                Room room = new Room();
                room.setRoomID(boxRoomID.getValue());
                room.setCustomerID(CustomerID.getText());
                room.setUserName(Username.getText());
                room.setRoomStatus("Occupied");
                room.setCheckInDate(LocalDateTime.now());
                room.setLeaveDate(LocalDateTime.of(LeaveDate.getValue(), LocalTime.now()));
                room.setDayRemaining((int) ChronoUnit.DAYS.between(room.getCheckInDate(), room.getLeaveDate()));
                room.setNoOfGuests(Integer.valueOf(NumberOfCustomer.getText()));
                roomDAOImpl.editCheckInRoom(room, true);
                mainOverViewPaneController.refreshForm();

                if (FXMLInfoBookingController.checkInfoBooking) {
                    Stage stage = (Stage) anchorPaneCheckInOrders.getScene().getWindow();
                    stage.close();
                    FXMLInfoBookingController.checkInfoBooking = false;
                } else {
                    CheckInID.setText("");
                    boxBookingID.setValue(null);
                    CustomerID.setText("");
                    NumberOfCustomer.setText("");
                    CheckInDate.setValue(null);
                    LeaveDate.setValue(null);
                    boxCheckInType.setValue(null);
                    CustomerPackage.setText("");
                    refreshIdUser();
                }
            });
        }
    }

    @FXML
    public void refreshIdUser() {
        ObservableList list_Booking_Id = FXCollections.observableArrayList();
        try {
            //        set add booking id for box booking id
            boxBookingID.setItems(DAOCustomerBookingCheckIn.getAllBookingID());
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLCheckInOrdersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void QRCodeScanner_Action(ActionEvent event) {
        QRWebCam qrWebCam = new QRWebCam();
        qrWebCam.txtQR.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Platform.runLater(() -> {
                    boxBookingID.getSelectionModel().select(newValue);
                    try {
                        //Lọc RoomID --- Lấy khoảng ngày hiện tại và +30 ngày
                        listRoomID = DAOCustomerBookingCheckIn.getAllRoomBookingNoCheck(String.valueOf((CheckInDate.getValue()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
                                String.valueOf((LeaveDate.getValue()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))), newValue);
                        boxRoomID.setItems(listRoomID);
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(FXMLCheckInOrdersController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    BookingInfo bk = new BookingInfo();
                    try {
                        bk = DAOCustomerBookingCheckIn.getAllBookingInfo(newValue);
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(FXMLCheckInOrdersQRCodeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    CustomerID.setText(bk.getCusID());
                    NumberOfCustomer.setText(String.valueOf(bk.getNumGuest()));
                    boxRoomID.setValue(bk.getRoomID());
                    CheckInID.setText("CI-" + newValue);
                    CheckInDate.setValue(LocalDate.now());
                    LeaveDate.setValue(LocalDate.parse(bk.getDateLeave()));
                    boxCheckInType.setValue("Reception Desk");
                    String pattern = "dd-MM-yyyy";
                    formatCalender.format(pattern, CheckInDate);
                    formatCalender.format(pattern, LeaveDate);

                    numberCustomer = Integer.parseInt(NumberOfCustomer.getText());
                    RoomIDBooking = boxRoomID.getValue();
                    LeaveDateBooking = String.valueOf(LeaveDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

                    try {
                        //comboBox_Check_Out_ID.setValue(string[0]);
                        enter_Submit_Action();
                    } catch (ClassNotFoundException | SQLException | IOException ex) {
                        Logger.getLogger(FXMLCheckInOrdersQRCodeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        });
    }

}
