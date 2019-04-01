/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.CheckOutDAOImpl;
import models.RoomDAOImpl;
import models.RoomType;
import models.RoomTypeDAOImpl;
import utils.PrintReport;
import utils.QRWebCam;
import utils.StageLoader;

/**
 * FXML Controller class
 *
 * @author Doan Thanh Nhan
 */
public class FXMLBillReportController implements Initializable {

    @FXML
    private HBox hboxHeader;
    @FXML
    private HBox hBox_Info_Parent;
    @FXML
    private VBox vBox_Info_Left;
    @FXML
    private HBox HboxBoxId;
    @FXML
    private FontAwesomeIconView iconRefresh;
    @FXML
    private HBox hBoxContent;
    @FXML
    private HBox hBoxBtn;
    @FXML
    private VBox vBox_BillReport_Info;
    @FXML
    private JFXComboBox<String> comboBox_Check_Out_ID;
    @FXML
    private JFXButton btn_QR_Code_Scanner;
    @FXML
    private JFXButton btn_Print;
    @FXML
    private JFXButton btn_Cancel;

    private FXMLMainFormController mainFormController;
    private CheckOutDAOImpl checkOutDAOImpl;
    private ObservableList<String> list_Check_Out_ID;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainFormController = ConnectControllers.getfXMLMainFormController();
        checkOutDAOImpl = new CheckOutDAOImpl();

        list_Check_Out_ID = checkOutDAOImpl.getAllCheckOutID();

        //Init comboBox_Room_Type
        if (list_Check_Out_ID.isEmpty()) {
            comboBox_Check_Out_ID.setDisable(true);

        } else {
            comboBox_Check_Out_ID.setItems(list_Check_Out_ID);
        }

    }

    @FXML
    private void refreshRoomType(MouseEvent event) {
    }

    @FXML
    private void handle_QRScanner_Action(ActionEvent event) {
        QRWebCam qrWebCam = new QRWebCam();
        qrWebCam.txtQR.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String[] string = newValue.split(";");
                System.out.println("CheckOutID = " + string[0]);
                Platform.runLater(() -> {
                    comboBox_Check_Out_ID.getSelectionModel().select(string[0]);
                    //comboBox_Check_Out_ID.setValue(string[0]);
                    handle_Save_Button_Action(event);
                });
                
            }
        });
    }

    @FXML
    private void handle_Save_Button_Action(ActionEvent event) {
        // Đoạn này làm loading (đang làm chạy vô tận)

        // Khai báo stage nhìn xuyên thấu
        StageLoader stageLoader = new StageLoader();
        stageLoader.loadingIndicator("Opening report");
        Task loadOverview = new Task() {
            @Override
            protected Object call() throws Exception {
                //Calling bill report
                PrintReport viewReport = new PrintReport();
                viewReport.showReport_Customer_Bill("/src/reports/Bill.jrxml", comboBox_Check_Out_ID.getValue());
                return null;
            }
        };

        loadOverview.setOnSucceeded(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                System.out.println("Finished");
                Platform.runLater(() -> {
                    stageLoader.stopTimeline();
                    stageLoader.closeStage();
                });
            }
        });
        new Thread(loadOverview).start();
    }

    @FXML
    private void handle_Cancel_Button_Action(ActionEvent event) {
        Stage stage = (Stage) btn_Cancel.getScene().getWindow();
        stage.close();
    }

}
