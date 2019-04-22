/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
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
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.CheckIn;
import models.DAO;
import models.DAOCustomerBookingCheckIn;
import models.DAOcheckRole;
import static models.DAOcheckRole.showFormLogin;
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
public class FXMLEditCheckInController implements Initializable {

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
    private JFXTextField BookingID;
    @FXML
    private JFXTextField CustomerID;
    @FXML
    private JFXTextField RoomID;
    @FXML
    private VBox vBox_Info_Right;
    @FXML
    private JFXTextField NumberOfCustomer;
    private JFXDatePicker CheckInDate;
    private JFXDatePicker LeaveDate;
    @FXML
    private JFXTextField CheckInType;
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
    private FXMLMainFormController fXMLMainFormController;
    private FXMLListCheckInController fXMLListCheckInController;
    private CheckIn ci;
    @FXML
    private JFXTextField DateIn;
    @FXML
    private JFXTextField DateOut;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fXMLListCheckInController = ConnectControllers.getfXMLListCheckInController();
        ci = FXMLListCheckInController.ci;
        Username.setText(ci.getUser());
        CheckInID.setText(ci.getCheckID());
        BookingID.setText(ci.getBookID());
        CustomerID.setText(ci.getCusID());
        RoomID.setText(ci.getRoomID());
        NumberOfCustomer.setText(String.valueOf(ci.getNumberOfCustomer()));
        DateIn.setText((ci.getDateIn()));
        DateOut.setText(ci.getDateOut());
        CheckInType.setText(ci.getCheckType());
        CustomerPackage.setText(ci.getCusPack());
        btnInfo.setOnAction((event) -> {
            try {
                enter_Submit_Action();
            } catch (ClassNotFoundException | SQLException | IOException ex) {
                Logger.getLogger(FXMLEditCheckInController.class.getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(FXMLEditCheckInController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        NumberOfCustomer.setOnKeyPressed((KeyEvent event) -> {
            Platform.runLater(() -> {
                NumberOfCustomer.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                HboxContent.getChildren().clear();
            });
            if (event.getCode() == KeyCode.ENTER) {

                try {
                    enter_Submit_Action();
                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(FXMLEditCheckInController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void enter_Submit_Action() throws ClassNotFoundException, SQLException, IOException {
        if (!DAOcheckRole.checkRoleDecentralization(FXMLLoginController.User_Login, "CheckIn_Edit")) {
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

                    });
                    EditCheckInSubmitAction();
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

    public void EditCheckInSubmitAction() {
        if (NumberOfCustomer.getText() == null || NumberOfCustomer.getText().equals("")) {
            Platform.runLater(() -> {
                notificationFunction.notification(NumberOfCustomer, HboxContent, "NUMBER OF CUSTOMER MUST NOT EMPTY !!!");
            });
        } else if (!PatternValided.PatternNumberGuest(NumberOfCustomer.getText())) {
            Platform.runLater(() -> {
                notificationFunction.notification(NumberOfCustomer, HboxContent, "NUMBER OF CUSTOMER IS INCORRECT (1-8) !!!");
            });
        } else {
            Platform.runLater(() -> {
                try {
                    DAOCustomerBookingCheckIn.Update_BookingWithCheckInEdit(BookingID.getText(),CheckInID.getText(),NumberOfCustomer.getText(),CustomerPackage.getText());
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(FXMLEditCheckInController.class.getName()).log(Level.SEVERE, null, ex);
                }
                DAO.setUserLogs_With_MAC(FXMLLoginController.User_Login, "Update CheckIn = " + CheckInID.getText(),
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), GetInetAddress.getMacAddress());
                if (FXMLListCheckInController.checkAction) {
                    fXMLListCheckInController = ConnectControllers.getfXMLListCheckInController();
                    fXMLListCheckInController.showUsersData();
                }
                Stage stage = (Stage) anchorPaneCheckInOrders.getScene().getWindow();
                stage.close();
            });
        }
    }

    @FXML
    private void Cancel(ActionEvent event) {
        Stage stage = (Stage) anchorPaneCheckInOrders.getScene().getWindow();
        stage.close();
    }

}
