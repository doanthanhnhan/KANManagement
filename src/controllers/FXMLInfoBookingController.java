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
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DateCell;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.mail.MessagingException;
import models.BookingInfo;
import models.DAO;
import models.DAOCustomerBookingCheckIn;
import models.DAOcheckRole;
import models.Room;
import models.RoomDAOImpl;
import models.formatCalender;
import models.notificationFunction;
import utils.AlertLoginAgain;
import utils.Email;
import utils.GetInetAddress;
import utils.PatternValided;
import utils.QRCreate;
import utils.StageLoader;
import utils.showFXMLLogin;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class FXMLInfoBookingController implements Initializable {

    @FXML
    private AnchorPane anchorPaneBooking;
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
    private HBox HboxBoxId;
    @FXML
    private JFXComboBox<String> BookingID;
    @FXML
    private JFXComboBox<String> boxIdCustomer;
    @FXML
    private JFXComboBox<String> boxIdRoom;
    @FXML
    private VBox vBox_Info_Right;
    @FXML
    private JFXDatePicker DateBook;
    @FXML
    private JFXTextField NumberGuest;
    @FXML
    private JFXTextField Note;
    @FXML
    private HBox HboxContent;
    @FXML
    private HBox Hboxbtn;
    @FXML
    private JFXButton btnInfo;
    @FXML
    private JFXButton btnCancel;
    private showFXMLLogin showFormLogin = new showFXMLLogin();
    private FXMLMainFormController fXMLMainFormController;
    ObservableList<String> listRoomID;
    RoomDAOImpl roomDAOImpl;
    public static String bookingIdConect;
    public static String roomIdConect;
    public static String numberofcustomer;
    public static String customerIdConect;
    public static LocalDate dateBookingConect;
    public static LocalDate dateLeaveConnect;
    public static boolean checkInfoBooking = false;
    private FXMLMainOverViewPaneController mainOverViewPaneController;
    private ObservableList<BookingInfo> list_Booking_Info = FXCollections.observableArrayList();
    private FXMLListBookingController fXMLListBookingController;
    private String dateLeave;
    public static String CusID_BookingFuture;
    public static Integer checkDeleteBookingID;
    @FXML
    private JFXDatePicker DateLeave;
    String RoomIDBooking;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DateBook.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date,
                    boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0);
            }
        });
        DateLeave.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0);
            }
        });
        if (FXMLInfoCustomerController.checkInfoCustomerAlready) {
            list_Booking_Info = DAOCustomerBookingCheckIn.check_BookingIdCustomer(FXMLInfoCustomerController.CustomerIdConect);
            System.out.println("Customer conect= " + FXMLInfoCustomerController.CustomerIdConect);
            System.out.println("size lis booking= " + list_Booking_Info.size());
        }
        mainOverViewPaneController = ConnectControllers.getfXMLMainOverViewPaneController();

        btnInfo.setOnAction((event) -> {
            try {
                btnSubmitBooking();
            } catch (ClassNotFoundException | SQLException | IOException ex) {
                Logger.getLogger(FXMLInfoCustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        if (list_Booking_Info.isEmpty()) {
            ObservableList<String> id_booking = FXCollections.observableArrayList();
            id_booking.add("BK-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSSSS")));
            BookingID.setItems(id_booking);
            System.out.println("list_Booking_Info is empty");
            BookingID.setValue(id_booking.get(0));
            System.out.println(BookingID.getValue());
        }
        NumberGuest.setText("0");
        Username.setText(FXMLLoginController.User_Login);
        BookingID.setOnKeyPressed((KeyEvent event) -> {
            Platform.runLater(() -> {
                BookingID.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                HboxContent.getChildren().clear();
            });
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    btnSubmitBooking();
                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        DateBook.valueProperty().addListener((obs, oldItem, newItem) -> {
            Platform.runLater(() -> {
                DateBook.setStyle("-jfx-default-color: #6447cd;");
                HboxContent.getChildren().clear();
            });
        });
        DateBook.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            Platform.runLater(() -> {
                HboxContent.getChildren().clear();
            });
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    btnSubmitBooking();
                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        DateLeave.valueProperty().addListener((obs, oldItem, newItem) -> {
            Platform.runLater(() -> {
                DateLeave.setStyle("-jfx-default-color: #6447cd;");
                HboxContent.getChildren().clear();
            });
        });
        DateLeave.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            Platform.runLater(() -> {
                HboxContent.getChildren().clear();
            });
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    btnSubmitBooking();
                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        NumberGuest.setOnKeyPressed((KeyEvent event) -> {
            Platform.runLater(() -> {
                NumberGuest.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                HboxContent.getChildren().clear();
            });
            if (event.getCode() == KeyCode.ENTER) {

                try {
                    btnSubmitBooking();
                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        Note.setOnKeyPressed((KeyEvent event) -> {
            Platform.runLater(() -> {
                Note.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                HboxContent.getChildren().clear();
            });
            if (event.getCode() == KeyCode.ENTER) {

                try {
                    btnSubmitBooking();
                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        boxIdCustomer.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                System.out.println("Enter pressed");
                try {
                    btnSubmitBooking();
                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        boxIdCustomer.valueProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null && !newItem.equals("")) {
                HboxContent.getChildren().clear();
                boxIdCustomer.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            }
        });
        boxIdRoom.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                System.out.println("Enter pressed");
                try {
                    btnSubmitBooking();
                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        boxIdRoom.valueProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null && !newItem.equals("")) {
                HboxContent.getChildren().clear();
                boxIdRoom.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
            }
        });

//        set Item for box Id customer
        try {
            boxIdCustomer.setItems(DAOCustomerBookingCheckIn.getAllIdCustomer());
            // TODO
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
        }
//      set value when customer already but not booking and when cutstomer not already
        if (FXMLInfoCustomerController.checkInfoCustomer || (list_Booking_Info.isEmpty() && FXMLInfoCustomerController.checkInfoCustomerAlready)) {
            checkInfoBooking = true;
            boxIdCustomer.setDisable(true);
            BookingID.setDisable(true);
            boxIdCustomer.setValue(FXMLInfoCustomerController.CustomerIdConect);
            System.out.println("RoomID = " + mainOverViewPaneController.service_Room_ID);
            DateBook.setDisable(true);
            DateBook.setValue(LocalDate.now());
            String pattern = "dd-MM-yyyy";
            formatCalender.format(pattern, DateBook);
        }
        if (FXMLInfoCustomerController.checkInfoCustomerAlready && !list_Booking_Info.isEmpty()) {
            ObservableList<String> list_booking_id = FXCollections.observableArrayList();
            for (BookingInfo bk : list_Booking_Info) {
                list_booking_id.add(bk.getBookID());
            }
            BookingID.setItems(list_booking_id);
//            set value for text field when list booking info have size = 1
            for (BookingInfo bk : list_Booking_Info) {
                try {
                    listRoomID = DAOCustomerBookingCheckIn.getAllRoomBookingNoCheck(String.valueOf(LocalDate.parse(bk.getDate()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
                            String.valueOf(LocalDate.parse(bk.getDateLeave()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))), bk.getBookID());
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
                }
                boxIdRoom.setItems(listRoomID);
                checkInfoBooking = true;
                boxIdCustomer.setDisable(true);
                BookingID.setDisable(false);
                BookingID.setValue(bk.getBookID());
                boxIdRoom.setValue(bk.getRoomID());
                dateLeave = String.valueOf(DateLeave.getValue());
                DateBook.setDisable(true);
                DateBook.setValue(LocalDate.parse(bk.getDate()));
                DateLeave.setValue(LocalDate.parse(bk.getDateLeave()));
                boxIdCustomer.setValue(FXMLInfoCustomerController.CustomerIdConect);
                NumberGuest.setText(String.valueOf(bk.getNumGuest()));
                Note.setText(bk.getNote());
                NumberGuest.setDisable(true);
                Note.setDisable(true);
                String pattern = "dd-MM-yyyy";
                formatCalender.format(pattern, DateBook);
                formatCalender.format(pattern, DateLeave);
                break;
            }
            if (list_Booking_Info.size() > 1) {
                BookingID.valueProperty().addListener((obs, oldItem, newItem) -> {
                    if (newItem != null && !newItem.equals("")) {
                        int i;
                        for (i = 0; i < list_Booking_Info.size(); i++) {
                            if (newItem.equals(list_Booking_Info.get(i).getBookID())) {
                                try {
                                    listRoomID = DAOCustomerBookingCheckIn.getAllRoomBookingNoCheck(String.valueOf(LocalDate.parse(list_Booking_Info.get(i).getDate()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
                                            String.valueOf(LocalDate.parse(list_Booking_Info.get(i).getDateLeave()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))), list_Booking_Info.get(i).getBookID());
                                } catch (SQLException | ClassNotFoundException ex) {
                                    Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                boxIdRoom.setItems(listRoomID);
                                checkInfoBooking = true;
                                boxIdCustomer.setDisable(true);
                                BookingID.setDisable(false);
                                BookingID.setValue(list_Booking_Info.get(i).getBookID());
                                boxIdRoom.setValue(list_Booking_Info.get(i).getRoomID());
                                dateLeave = String.valueOf(DateLeave.getValue());
                                DateBook.setDisable(true);
                                DateBook.setValue(LocalDate.parse(list_Booking_Info.get(i).getDate()));
                                DateLeave.setValue(LocalDate.parse(list_Booking_Info.get(i).getDateLeave()));
                                boxIdCustomer.setValue(FXMLInfoCustomerController.CustomerIdConect);
                                NumberGuest.setText(String.valueOf(list_Booking_Info.get(i).getNumGuest()));
                                Note.setText(list_Booking_Info.get(i).getNote());
                                NumberGuest.setDisable(true);
                                Note.setDisable(true);
                                String pattern = "dd-MM-yyyy";
                                formatCalender.format(pattern, DateBook);
                                formatCalender.format(pattern, DateLeave);
                                break;
                            }
                        }
                    }
                });
            }
        }
        dateLeave = String.valueOf(DateLeave.getValue());
        checkDeleteBookingID = list_Booking_Info.size();
        RoomIDBooking = boxIdRoom.getValue();
    }

    @FXML
    private void Format_Show_Calender(ActionEvent event) {
        String pattern = "dd-MM-yyyy";
        formatCalender.format(pattern, DateBook);
        if (DateBook.getValue() != null && DateLeave.getValue() != null) {
            boolean check_OK = true;
            if (DateBook.getValue().compareTo(DateLeave.getValue()) > 0) {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Error");
                alert1.setHeaderText("You have no right to do this !!!");
                alert1.setContentText("Because Date Leave Cannot Be Bigger Than Date Book !!!");
                alert1.showAndWait();
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                DateBook.setStyle("-jfx-default-color: RED;");
                DateBook.requestFocus();
                check_OK = false;
            }
            if (check_OK) {
                boxIdRoom.setItems(null);
                boxIdRoom.setDisable(false);
                try {
                    listRoomID = DAOCustomerBookingCheckIn.getAllRoomBooking(String.valueOf(DateBook.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
                            String.valueOf(DateLeave.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                    boxIdRoom.setItems(listRoomID);
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        DateBook.setPromptText("Date Book");
        DateLeave.setPromptText("Date Leave");
    }

    @FXML
    private void Format_Show_Calender_DateLeave(ActionEvent event) {
        String pattern = "dd-MM-yyyy";
        formatCalender.format(pattern, DateLeave);
        if (DateBook.getValue() != null && DateLeave.getValue() != null) {
            boolean check_OK = true;
            if (DateBook.getValue().compareTo(DateLeave.getValue()) > 0) {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Error");
                alert1.setHeaderText("You have no right to do this !!!");
                alert1.setContentText("Because Date Leave Cannot Be Bigger Than Date Book !!!");
                alert1.showAndWait();
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                DateBook.setStyle("-jfx-default-color: RED;");
                DateBook.requestFocus();
                check_OK = false;
            }
            if (check_OK) {
                boxIdRoom.setItems(null);
                boxIdRoom.setDisable(false);
                try {
                    if (FXMLInfoCustomerController.checkInfoCustomerAlready && !list_Booking_Info.isEmpty()) {
                        listRoomID = DAOCustomerBookingCheckIn.getAllRoomBookingNoCheck(String.valueOf(DateBook.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
                                String.valueOf(DateLeave.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))), BookingID.getValue());
                    } else {
                        listRoomID = DAOCustomerBookingCheckIn.getAllRoomBooking(String.valueOf(DateBook.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
                                String.valueOf(DateLeave.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                    }
                    boxIdRoom.setItems(listRoomID);
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        DateBook.setPromptText("Date Book");
        DateLeave.setPromptText("Date Leave");
    }

    @FXML
    private void Cancel(ActionEvent event) {
        FXMLInfoCustomerController.checkInfoCustomer = false;
        FXMLInfoCustomerController.checkInfoCustomerAlready = false;
        checkInfoBooking = false;
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    private void btnSubmitBooking() throws ClassNotFoundException, SQLException, IOException {
        if (!DAOcheckRole.checkRoleDecentralization(FXMLLoginController.User_Login, "Booking_Add")) {
            AlertLoginAgain.alertLogin();
            fXMLMainFormController = ConnectControllers.getfXMLMainFormController();
            Stage stageMainForm = (Stage) fXMLMainFormController.AnchorPaneMainForm.getScene().getWindow();
            Stage stage = (Stage) anchorPaneBooking.getScene().getWindow();
            stage.close();
            stageMainForm.close();
            showFormLogin.showFormLogin();
        } else {
            if (FXMLListBookingController.check_formBooking_list) {
                fXMLListBookingController = ConnectControllers.getfXMLListBookingController();
                fXMLListBookingController.check_Add_New = true;
                fXMLListBookingController.new_Emp_ID = BookingID.getValue();
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
                    BookingSubmitAction();
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

    public void BookingSubmitAction() {
        if (boxIdCustomer.getValue() == null) {
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(350, 35);
                label.setText("ID CUSTOMER MUST NOT EMPTY !!!");
                label.setAlignment(Pos.CENTER_LEFT);
                boxIdCustomer.getStyleClass().removeAll();
                boxIdCustomer.getStyleClass().add("jfx-combo-box-fault");
                HboxContent.setSpacing(10);
                HboxContent.setAlignment(Pos.CENTER_LEFT);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
                boxIdCustomer.requestFocus();
            });
        } else if (boxIdRoom.getValue() == null) {
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(350, 35);
                label.setText("ID ROOM MUST NOT EMPTY !!!");
                label.setAlignment(Pos.CENTER_LEFT);
                boxIdRoom.getStyleClass().removeAll();
                boxIdRoom.getStyleClass().add("jfx-combo-box-fault");
                HboxContent.setSpacing(10);
                HboxContent.setAlignment(Pos.CENTER_LEFT);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
                boxIdRoom.requestFocus();
            });
        } else if (DateBook.getValue() == null) {
            System.out.println("trong datebook null: " + FXMLInfoCustomerController.checkInfoCustomer);
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(350, 35);
                label.setAlignment(Pos.CENTER_LEFT);
                label.setText("DATE BOOK MUST NOT EMPTY !!!");
                DateBook.setStyle("-jfx-default-color: RED;");
                HboxContent.setAlignment(Pos.CENTER_LEFT);
                HboxContent.setSpacing(10);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
                DateBook.requestFocus();
            });
        } else if (DateLeave.getValue() == null) {
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(350, 35);
                label.setAlignment(Pos.CENTER_LEFT);
                label.setText("DATE LEAVE MUST NOT EMPTY !!!");
                DateLeave.setStyle("-jfx-default-color: RED;");
                HboxContent.setAlignment(Pos.CENTER_LEFT);
                HboxContent.setSpacing(10);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
                DateLeave.requestFocus();
            });
        } else if (NumberGuest.getText() == null || NumberGuest.getText().equals("")) {
            Platform.runLater(() -> {
                notificationFunction.notification(NumberGuest, HboxContent, "NUMBER GUEST MUST NOT EMPTY !!!");
            });
        } else if (!PatternValided.PatternNumberGuest(NumberGuest.getText())) {
            Platform.runLater(() -> {
                notificationFunction.notification(NumberGuest, HboxContent, "NUMBER GUEST IS INCORRECT (1-8) !!!");
            });
        } else if (DAOCustomerBookingCheckIn.check_Booking(BookingID.getValue())) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error");
            alert1.setHeaderText("You have no right to do this !!!");
            alert1.setContentText("Because BookingID has been check in !!!");
            alert1.showAndWait();
        } else if (DateBook.getValue().compareTo(DateLeave.getValue()) > 0) {
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(350, 35);
                label.setAlignment(Pos.CENTER_LEFT);
                label.setText("Date Leave Cannot Be Bigger Than Date Book !!!");
                DateBook.setStyle("-jfx-default-color: RED;");
                HboxContent.setAlignment(Pos.CENTER_LEFT);
                HboxContent.setSpacing(10);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
                DateBook.requestFocus();

            });
        } else if (DAOCustomerBookingCheckIn.check_RoomIdAgain(boxIdRoom.getValue(), String.valueOf(DateBook.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
                String.valueOf(DateLeave.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))) && list_Booking_Info.isEmpty()) {
            Platform.runLater(() -> {
                System.out.println("da vao chekc trung");
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(350, 35);
                label.setAlignment(Pos.CENTER_LEFT);
                label.setText("This RoomID Can Not Booking Please Choose Again !!!");
                boxIdRoom.setStyle("-jfx-default-color: RED;");
                HboxContent.setAlignment(Pos.CENTER_LEFT);
                HboxContent.setSpacing(10);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
                boxIdRoom.requestFocus();
                try {
                    boxIdRoom.setItems(DAOCustomerBookingCheckIn.getAllRoomBooking(String.valueOf(DateBook.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
                            String.valueOf(DateLeave.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))));
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } else if (DAOCustomerBookingCheckIn.check_RoomIdAgainBookingalready(boxIdRoom.getValue(), String.valueOf(DateBook.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
                String.valueOf(DateLeave.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))), BookingID.getValue()) && !list_Booking_Info.isEmpty()) {
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
                boxIdRoom.setStyle("-jfx-default-color: RED;");
                HboxContent.setAlignment(Pos.CENTER_LEFT);
                HboxContent.setSpacing(10);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
                boxIdRoom.requestFocus();
                try {
                    boxIdRoom.setItems(DAOCustomerBookingCheckIn.getAllRoomBookingNoCheck(String.valueOf(DateBook.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
                            String.valueOf(DateLeave.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))), BookingID.getValue()));
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } else {
            System.out.println("trong else" + FXMLInfoCustomerController.checkInfoCustomer);
            Platform.runLater(() -> {
                if (list_Booking_Info.isEmpty()) {
                    String date = DateBook.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    String dateleave = DateLeave.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    BookingInfo bk = new BookingInfo();
                    bk.setBookID(BookingID.getValue());
                    bk.setCusID(boxIdCustomer.getValue());
                    bk.setRoomID(boxIdRoom.getValue());
                    bk.setDate(date);
                    bk.setNote(Note.getText());
                    bk.setNumGuest(Integer.valueOf(NumberGuest.getText()));
                    bk.setUser(Username.getText());
                    bk.setDateLeave(dateleave);
                    try {
                        DAOCustomerBookingCheckIn.AddNewBooking(bk);
                    } catch (MalformedURLException | SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
                    }
//                set Userlogs
                    DAO.setUserLogs_With_MAC(FXMLLoginController.User_Login, "Add Booking for " + boxIdCustomer.getValue(),
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), GetInetAddress.getMacAddress());
                    //UPDATE INFORMATIONS FOR ROOM WHICH HAS BEEN BOOKED
                    roomDAOImpl = new RoomDAOImpl();
                    Room room = roomDAOImpl.getRoom(boxIdRoom.getValue());
                    if ((int) ChronoUnit.HOURS.between(room.getBookingDate(), LocalDateTime.of(DateBook.getValue(), LocalTime.now())) < 0
                            || (int) ChronoUnit.HOURS.between(room.getBookingDate(), LocalDateTime.now()) >= 0) {
                        room.setCustomerID(boxIdCustomer.getValue());
                        room.setUserName(Username.getText());
                        if (room.getRoomStatus().equals("Available")) {
                            room.setRoomStatus("Reserved");
                        }
                        room.setBookingDate(LocalDateTime.of(DateBook.getValue(), LocalTime.now()));
                        room.setLeaveDate(LocalDateTime.of(DateLeave.getValue(), LocalTime.now()));
                        roomDAOImpl.editBookingRoom(room, true);
                    }
                    if (mainOverViewPaneController != null) {
                        mainOverViewPaneController.refreshForm();
                    }
                    //END UNDATE ROOM
                    //CREATE QR CODE FILE AND SEND EMAIL
                    String customerEmail = DAOCustomerBookingCheckIn.getCustomerEmail(boxIdCustomer.getValue());
                    if ((!FXMLInfoCustomerController.checkInfoCustomer || !FXMLInfoCustomerController.checkInfoCustomerAlready)
                            && !customerEmail.equals("")) {
                        String content = "BookingID : " + BookingID.getValue() + ". Use the attached QRCode for checking in faster.";
                        QRCreate.create_Booking_QRCode(BookingID.getValue());
                        File file = new File("");
                        String filePath = file.getAbsolutePath() + "/src/images/BookingQRCode.png";
                        try {
                            Email.sendEmail_With_Attach(customerEmail, "Booking infomations: ID & QRCode", content, filePath);
                        } catch (MessagingException ex) {
                            Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    //ENDING SENDING BOOKING QRCODE
                }
                if (FXMLInfoCustomerController.checkInfoCustomer || FXMLInfoCustomerController.checkInfoCustomerAlready) {
                    bookingIdConect = BookingID.getValue();
                    roomIdConect = boxIdRoom.getValue();
                    numberofcustomer = NumberGuest.getText();
                    customerIdConect = boxIdCustomer.getValue();
                    dateBookingConect = DateBook.getValue();
                    dateLeaveConnect = DateLeave.getValue();
                    if (!dateLeave.equals(String.valueOf(DateLeave.getValue())) || !RoomIDBooking.equals(boxIdRoom.getValue())) {
                        try {
                            DAOCustomerBookingCheckIn.Update_RoomBooking(BookingID.getValue(), boxIdRoom.getValue(),
                                    String.valueOf(DateLeave.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                            DAO.setUserLogs_With_MAC(FXMLLoginController.User_Login, "Change DateLeave or RoomID for bookingID= " + BookingID.getValue(),
                                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), GetInetAddress.getMacAddress());
                            //UPDATE INFORMATIONS FOR ROOM WHICH HAS BEEN BOOKED
                            roomDAOImpl = new RoomDAOImpl();
                            Room room = roomDAOImpl.getRoom(boxIdRoom.getValue());
                            if ((int) ChronoUnit.HOURS.between(room.getBookingDate(), LocalDateTime.of(DateBook.getValue(), LocalTime.now())) < 0
                                    || (int) ChronoUnit.HOURS.between(room.getBookingDate(), LocalDateTime.now()) > 0) {
                                room.setCustomerID(boxIdCustomer.getValue());
                                room.setUserName(Username.getText());
                                if (room.getRoomStatus().equals("Available")) {
                                    room.setRoomStatus("Reserved");
                                }
                                room.setBookingDate(LocalDateTime.of(DateBook.getValue(), LocalTime.now()));
                                room.setLeaveDate(LocalDateTime.of(DateLeave.getValue(), LocalTime.now()));
                                roomDAOImpl.editBookingRoom(room, true);
                            }
                            if (mainOverViewPaneController != null) {
                                mainOverViewPaneController.refreshForm();
                            }
                            //END UNDATE ROOM
                        } catch (ClassNotFoundException | SQLException ex) {
                            Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (!DAOCustomerBookingCheckIn.check_ListbookingFuture(boxIdCustomer.getValue()).isEmpty() && list_Booking_Info.isEmpty()) {
                        CusID_BookingFuture = boxIdCustomer.getValue();
                        Stage stageEdit = new Stage();
                        stageEdit.resizableProperty().setValue(Boolean.FALSE);
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("/fxml/FXMLDeleteBookingVirtual.fxml"));
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        stageEdit.getIcons().add(new Image("/images/KAN Logo.png"));
                        Scene scene = new Scene(root);
                        stageEdit.setScene(scene);
                        stageEdit.initStyle(StageStyle.UNDECORATED);
                        stageEdit.show();
                    } else {
                        Stage stageEdit = new Stage();
                        stageEdit.resizableProperty().setValue(Boolean.FALSE);
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("/fxml/FXMLCheckInOrders.fxml"));
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        stageEdit.getIcons().add(new Image("/images/KAN Logo.png"));
                        Scene scene = new Scene(root);
                        stageEdit.setScene(scene);
                        stageEdit.setOnCloseRequest((event) -> {
                            if (FXMLInfoBookingController.checkDeleteBookingID.equals(0)) {
                                try {
                                    DAOCustomerBookingCheckIn.deleteBooking(BookingID.getValue());
                                } catch (ClassNotFoundException | SQLException ex) {
                                    Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            checkInfoBooking = false;
                        });
                        stageEdit.show();
                    }
                    Stage stage = (Stage) anchorPaneBooking.getScene().getWindow();
                    stage.close();
                    FXMLInfoCustomerController.checkInfoCustomer = false;
                    FXMLInfoCustomerController.checkInfoCustomerAlready = false;

                }
                if (FXMLListBookingController.check_formBooking_list) {
                    fXMLListBookingController = ConnectControllers.getfXMLListBookingController();
                    fXMLListBookingController.showUsersData();
                }
                boxIdRoom.setValue(null);
                boxIdCustomer.setValue(null);
                BookingID.setValue("BK-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSSSS")));
                Note.setText("");
                NumberGuest.setText("0");
                DateBook.setValue(null);
                DateLeave.setValue(null);
                boxIdRoom.setItems(null);
                boxIdRoom.setDisable(true);
            });
        }
    }
}
