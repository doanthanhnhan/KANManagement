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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.BookingInfo;
import models.DAO;
import models.DAOCustomerBookingCheckIn;
import models.DAOcheckRole;
import models.RoomDAOImpl;
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
    private JFXTextField BookingID;
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
    public static boolean checkInfoBooking = false;
    private FXMLMainOverViewPaneController mainOverViewPaneController;
    private boolean checkBookingAlready;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        checkBookingAlready=DAOCustomerBookingCheckIn.check_BookingIdCustomer(FXMLInfoCustomerController.CustomerIdConect);
        mainOverViewPaneController = ConnectControllers.getfXMLMainOverViewPaneController();

        btnInfo.setOnAction((event) -> {
            try {
                btnSubmitBooking();
            } catch (ClassNotFoundException | SQLException | IOException ex) {
                Logger.getLogger(FXMLInfoCustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        BookingID.setText("BK-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSSSS")));
        NumberGuest.setText("0");
        //Getting Available roomID
        roomDAOImpl = new RoomDAOImpl();
        listRoomID = roomDAOImpl.getAllStatusRoomID("Available");

        //Initialize combobox RoomID
        boxIdRoom.getItems().addAll(listRoomID);

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

        if (FXMLInfoCustomerController.checkInfoCustomer || !checkBookingAlready) {
            checkInfoBooking = true;
            boxIdCustomer.setDisable(true);
            boxIdRoom.setDisable(true);
            BookingID.setDisable(true);
            boxIdCustomer.setValue(FXMLInfoCustomerController.CustomerIdConect);
            System.out.println("RoomID = " + mainOverViewPaneController.service_Room_ID);
            boxIdRoom.setValue(mainOverViewPaneController.service_Room_ID);
            DateBook.setDisable(true);
            DateBook.setValue(LocalDate.now());
            String pattern = "dd-MM-yyyy";
            formatCalender.format(pattern, DateBook);
        }
    }

    @FXML
    private void Format_Show_Calender(ActionEvent event) {
        String pattern = "dd-MM-yyyy";
        formatCalender.format(pattern, DateBook);
    }

    @FXML
    private void Cancel(ActionEvent event) {
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
                        BookingSubmitAction();
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

    public void BookingSubmitAction() {
        if (BookingID.getText() == null || BookingID.getText().equals("")) {
            Platform.runLater(() -> {
                notificationFunction.notification(BookingID, HboxContent, "BOOKING ID MUST NOT EMPTY !!!");
            });
        } else if (boxIdCustomer.getValue() == null) {
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
        } else if (NumberGuest.getText() == null || NumberGuest.getText().equals("")) {
            Platform.runLater(() -> {
                notificationFunction.notification(NumberGuest, HboxContent, "NUMBER GUEST MUST NOT EMPTY !!!");
            });
        } else if (!PatternValided.PatternID(BookingID.getText())) {
            Platform.runLater(() -> {
                notificationFunction.notification(BookingID, HboxContent, "BOOKING ID IS INCORRECT !!!");
            });
        } else if (!PatternValided.PatternNumberGuest(NumberGuest.getText())) {
            Platform.runLater(() -> {
                notificationFunction.notification(NumberGuest, HboxContent, "NUMBER GUEST IS INCORRECT (1-8) !!!");
            });
        } else {
            System.out.println("trong else" + FXMLInfoCustomerController.checkInfoCustomer);
            Platform.runLater(() -> {
                String date = DateBook.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                BookingInfo bk = new BookingInfo();
                bk.setBookID(BookingID.getText());
                bk.setCusID(boxIdCustomer.getValue());
                bk.setRoomID(boxIdRoom.getValue());
                bk.setDate(date);
                bk.setNote(Note.getText());
                bk.setNumGuest(Integer.valueOf(NumberGuest.getText()));
                bk.setUser(Username.getText());
                try {
                    DAOCustomerBookingCheckIn.AddNewBooking(bk);
                } catch (MalformedURLException | SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
                }
//                set Userlogs
                DAO.setUserLogs_With_MAC(FXMLLoginController.User_Login, "Add Booking for " + boxIdCustomer.getValue(),
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), GetInetAddress.getMacAddress());
                if (FXMLInfoCustomerController.checkInfoCustomer || !checkBookingAlready) {               
                    bookingIdConect = BookingID.getText();
                    roomIdConect = boxIdRoom.getValue();
                    numberofcustomer = NumberGuest.getText();
                    customerIdConect = boxIdCustomer.getValue();
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
                    stageEdit.show();
                    Stage stage = (Stage) anchorPaneBooking.getScene().getWindow();
                    stage.close();
                    FXMLInfoCustomerController.checkInfoCustomer = false;
                }
                boxIdRoom.setValue(null);
                boxIdCustomer.setValue(null);
                BookingID.setText("");
                Note.setText("");
                NumberGuest.setText("0");
                DateBook.setValue(null);

            });
        }
    }
}
