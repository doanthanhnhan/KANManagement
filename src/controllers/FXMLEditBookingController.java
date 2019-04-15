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
import models.DAO;
import models.DAOCustomerBookingCheckIn;
import models.DAOcheckRole;
import static models.DAOcheckRole.showFormLogin;
import models.formatCalender;
import models.notificationFunction;
import utils.AlertLoginAgain;
import utils.GetInetAddress;
import utils.PatternValided;
import utils.StageLoader;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class FXMLEditBookingController implements Initializable {

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
    private JFXTextField CustomerId;
    @FXML
    private JFXComboBox<String> boxIdRoom;
    @FXML
    private VBox vBox_Info_Right;
    @FXML
    private JFXDatePicker DateBook;
    @FXML
    private JFXDatePicker DateLeave;
    @FXML
    private JFXTextField CustomerName;
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
    private FXMLListBookingController fXMLListBookingController;
    private BookingInfo bk;
    ObservableList<String> listRoomID;
    private FXMLMainFormController fXMLMainFormController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        fXMLListBookingController = ConnectControllers.getfXMLListBookingController();
        bk = FXMLListBookingController.bk;
        BookingID.setText(bk.getBookID());
        DateBook.setValue(LocalDate.parse(bk.getDate()));
        DateLeave.setValue(LocalDate.parse(bk.getDateLeave()));
        try {
            listRoomID = DAOCustomerBookingCheckIn.getAllRoomBookingNoCheck(String.valueOf(DateBook.getValue()),
                    String.valueOf(DateLeave.getValue()), BookingID.getText());
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLEditBookingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        boxIdRoom.setItems(listRoomID);
        boxIdRoom.setValue(bk.getRoomID());
        Username.setText(bk.getUser());
        CustomerId.setText(bk.getCusID());
        CustomerName.setText(bk.getCusName());
        NumberGuest.setText(String.valueOf(bk.getNumGuest()));
        Note.setText(bk.getNote());
        String pattern = "dd-MM-yyyy";
        formatCalender.format(pattern, DateBook);
        formatCalender.format(pattern, DateLeave);
        // TODO
    }

    @FXML
    private void Format_Show_Calender(ActionEvent event) {
        DateBook.setPromptText("Date Book");
        String pattern = "dd-MM-yyyy";
        formatCalender.format(pattern, DateBook);
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
                listRoomID = DAOCustomerBookingCheckIn.getAllRoomBookingNoCheck(String.valueOf(DateBook.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
                        String.valueOf(DateLeave.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))), BookingID.getText());
                boxIdRoom.setItems(listRoomID);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void Format_Show_Calender_DateLeave(ActionEvent event) {
        DateLeave.setPromptText("Date Leave");
        String pattern = "dd-MM-yyyy";
        formatCalender.format(pattern, DateLeave);
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
                listRoomID = DAOCustomerBookingCheckIn.getAllRoomBookingNoCheck(String.valueOf(DateBook.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
                        String.valueOf(DateLeave.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))), BookingID.getText());
                boxIdRoom.setItems(listRoomID);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void Cancel(ActionEvent event) {
    }

    private void btnSubmitBooking() throws ClassNotFoundException, SQLException, IOException {
        if (!DAOcheckRole.checkRoleDecentralization(FXMLLoginController.User_Login, "Booking_Edit")) {
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
        if (boxIdRoom.getValue() == null) {
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
        } else if (DAOCustomerBookingCheckIn.check_Booking(BookingID.getText())) {
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
        } else if (DAOCustomerBookingCheckIn.check_RoomIdAgainBookingalready(boxIdRoom.getValue(), String.valueOf(DateBook.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
                String.valueOf(DateLeave.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))), BookingID.getText())) {
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
                            String.valueOf(DateLeave.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))), BookingID.getText()));
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(FXMLInfoBookingController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } else {
            Platform.runLater(() -> {
                try {
                    DAOCustomerBookingCheckIn.Update_EditBooking(BookingID.getText(), boxIdRoom.getValue(),
                            String.valueOf(DateBook.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
                            String.valueOf(DateLeave.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
                            Integer.parseInt(NumberGuest.getText()), Note.getText());
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(FXMLEditBookingController.class.getName()).log(Level.SEVERE, null, ex);
                }
                DAO.setUserLogs_With_MAC(FXMLLoginController.User_Login, "Update BookingID = " + BookingID.getText(),
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), GetInetAddress.getMacAddress());
                if (FXMLListBookingController.check_formBooking_list) {
                    fXMLListBookingController = ConnectControllers.getfXMLListBookingController();
                    fXMLListBookingController.showUsersData();
                }
                Stage stage = (Stage) anchorPaneBooking.getScene().getWindow();
                stage.close();
            });
        }
    }
}
