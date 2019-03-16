/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import models.ServiceTypeDAOImpl;
import models.ServiceType;
import utils.FormatName;
import utils.StageLoader;
import utils.formatCalender;

/**
 * FXML Controller class
 *
 * @author Doan Thanh Nhan
 */
public class FXMLAddNewServiceTypeController implements Initializable {

    public static ObservableList<ServiceType> listServiceType;
    ServiceTypeDAOImpl serviceTypeDAOImpl = new ServiceTypeDAOImpl();
    private FXMLListServiceTypeController listServiceTypeController;
    final FileChooser fileChooser = new FileChooser();
    private Boolean check_Validate = false;

    @FXML
    private JFXTextField serviceID;
    @FXML
    private JFXTextField serviceName;
    @FXML
    private JFXTextField serviceUnit;
    @FXML
    private JFXTextField servicePrice;
    @FXML
    private JFXButton btnInsertImage;
    @FXML
    private ImageView imgService;
    @FXML
    private HBox hBoxContent;
    @FXML
    private JFXButton btnAddNew;
    @FXML
    private Label label_Title;
    @FXML
    private Label label_Description;
    @FXML
    private JFXTextArea txtArea_Service_Description;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private JFXTextField serviceInventory;
    @FXML
    private JFXDatePicker serviceImportDate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listServiceTypeController = ConnectControllers.getfXMLListServiceTypeController();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.png, *.jpg, *.gif, *.bmp)", "*.jpg", "*.png", "*.gif", "*.bmp");

        fileChooser.getExtensionFilters().add(extFilter);
        // Set path for fileChooser
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        fileChooser.setInitialDirectory(new File(currentPath + "/src/images"));

        //Check form was call from List ServiceType Form
        if (listServiceTypeController.check_Edit_Action) {
            listServiceTypeController.check_Edit_Action = false;
            //Setting values for new form
            serviceID.setDisable(true);
            label_Title.setText("EDITING SERVICE TYPE");
            label_Description.setText("Filling the infomations for editting service type");
            ServiceType serviceType = FXMLListServiceTypeController.serviceTypeItem;
            serviceID.setText(serviceType.getServiceID());
            serviceName.setText(serviceType.getServiceName());
            serviceUnit.setText(serviceType.getServiceUnit());
            servicePrice.setText(serviceType.getServicePrice().toString());
            serviceImportDate.setValue(serviceType.getServiceInputDate().toLocalDate());
            serviceInventory.setText(serviceType.getServiceInventory().toString());
            if (serviceType.getImageView() != null) {
                imgService.setImage(serviceType.getImageView().getImage());
            }
            txtArea_Service_Description.setText(serviceType.getServiceDescription());
            btnAddNew.setText("Update");

            //Setting Update button function
            btnAddNew.setOnAction((event) -> {
                submit_Update_Service_Type();
            });

            // Setting keypress ENTER for updating function
            serviceID.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                serviceID.getStyleClass().remove("text-field-fault");
                hBoxContent.getChildren().clear();
                if (event.getCode() == KeyCode.ENTER) {
                    submit_Update_Service_Type();
                }
            });
            serviceName.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                serviceName.getStyleClass().remove("text-field-fault");
                hBoxContent.getChildren().clear();
                if (event.getCode() == KeyCode.ENTER) {
                    submit_Update_Service_Type();
                }
            });
        } else {
            // Setting keypress ENTER for adding function
            serviceID.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                serviceID.getStyleClass().remove("text-field-fault");
                hBoxContent.getChildren().clear();
                if (event.getCode() == KeyCode.ENTER) {
                    submit_Add_New_Service_Type();
                }
            });
            serviceName.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                serviceName.getStyleClass().remove("text-field-fault");
                hBoxContent.getChildren().clear();
                if (event.getCode() == KeyCode.ENTER) {
                    submit_Add_New_Service_Type();
                }
            });
        }
    }

    @FXML
    private void btnSubmitAddNewEmployee(ActionEvent event) {
        submit_Add_New_Service_Type();
    }

    @FXML
    private void handle_Button_Insert_Image_Action(ActionEvent event) {
        File file = fileChooser.showOpenDialog(btnInsertImage.getScene().getWindow());
        fileChooser.setTitle("Choose an image for service type");

        //Check image size < 200kB
        if (file != null && file.length() <= (200 * 1024)) {
            System.out.println(file.toURI().toString() + " ; Size: = " + file.length() / 1024 + "kB");
            Image image = new Image(file.toURI().toString());
            imgService.setImage(image);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Your image is greater than 200kB !");
            alert.showAndWait();
        }
    }

    @FXML
    private void handle_Btn_Cancel_Action(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public void submit_Add_New_Service_Type() {
        btnAddNew.setDisable(true);
        //Calling loading form and thread task
        StageLoader stageLoader = new StageLoader();
        stageLoader.loadingIndicator("Adding Service Type");
        Task loadOverview;
        loadOverview = new Task() {
            @Override
            protected Object call() throws Exception {
                System.out.println("Loading...");
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        hBoxContent.getChildren().clear();
                    }
                });
                validateForm();
                if (check_Validate) {
                    addNewServiceType();
                    System.out.println("Add successful!");
                }
                return null;
            }
        };

        loadOverview.setOnSucceeded(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                Platform.runLater(() -> {
                    btnAddNew.setDisable(false);
                    stageLoader.stopTimeline();
                    stageLoader.closeStage();
                    System.out.println("Form check validate: " + check_Validate);
                    if (check_Validate) {
                        listServiceTypeController.showUsersData();
                        Stage stageUpdate = (Stage) btnAddNew.getScene().getWindow();
                        stageUpdate.close();
                    }
                });
                System.out.println("Finished");
            }
        });
        new Thread(loadOverview).start();
    }

    public void submit_Update_Service_Type() {
        StageLoader stageLoader = new StageLoader();
        stageLoader.loadingIndicator("Updating Service Type");
        Task loadOverview;
        loadOverview = new Task() {
            @Override
            protected Object call() throws Exception {
                System.out.println("Loading...");
                Platform.runLater(() -> {
                    hBoxContent.getChildren().clear();
                });
                validateForm();
                if (check_Validate) {
                    ServiceType updateServiceType = getDataFromForm();
                    //Updating to DB
                    serviceTypeDAOImpl.editServiceType(updateServiceType, true);
                    System.out.println("Updating successful!");
                }
                return null;
            }
        };

        loadOverview.setOnSucceeded((Event event1) -> {
            Platform.runLater(() -> {
                stageLoader.stopTimeline();
                stageLoader.closeStage();
                System.out.println("Form check validate: " + check_Validate);
                if (check_Validate) {
                    listServiceTypeController.showUsersData();
                    Stage stageUpdate = (Stage) btnAddNew.getScene().getWindow();
                    stageUpdate.close();
                }
            });
            System.out.println("Finished");
        });

        new Thread(loadOverview).start();
    }

    public void addNewServiceType() {
        ServiceType serviceType = getDataFromForm();
        serviceTypeDAOImpl.addServiceType(serviceType);
    }

    public ServiceType getDataFromForm() {
        ServiceType serviceType = new ServiceType();
        serviceType.setServiceID(FormatName.format(serviceID.getText()));
        serviceType.setServiceName(FormatName.format(serviceName.getText()));
        serviceType.setServiceName(FormatName.format(serviceName.getText()));
        serviceType.setServiceUnit(FormatName.format(serviceUnit.getText()));
        serviceType.setServiceInventory(Integer.parseInt(FormatName.format(serviceInventory.getText())));
        //Setting localdatetime (DatePicker + LocalTime.now())
        serviceType.setServiceInputDate(LocalDateTime.of(serviceImportDate.getValue(), LocalTime.now()));
        serviceType.setServiceDescription(FormatName.format(txtArea_Service_Description.getText()));
        serviceType.setServicePrice(Float.parseFloat(servicePrice.getText()));
        BufferedImage bImage = SwingFXUtils.fromFXImage(imgService.getImage(), null);
        byte[] res;
        try (ByteArrayOutputStream s = new ByteArrayOutputStream()) {
            ImageIO.write(bImage, "png", s);
            res = s.toByteArray();
            Blob blob = new SerialBlob(res);
            serviceType.setServiceImage(blob);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(FXMLAddNewServiceTypeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return serviceType;
    }

    public void validateForm() {
        if (validateTextField(serviceID, "^(?=.{4,50}$)[a-zA-Z][a-zA-Z0-9_]+$", "SERVICE ID MUST NOT BE EMPTY !!!",
                "ID MUST CONTAIN 4-50 CHARACTER, \nBEGINNING CHAR MUST BE NOT NUMBER OR CHARACTER SPECIAL !!!")) {
            System.out.println("serviceID false");
            check_Validate = false;
        } else if (validateTextField(serviceName, "^(?=.{1,100}$)[\\pL\\pZ\\pS][\\pL\\pZ\\pS\\s\\d]+$", "SERVICE NAME MUST NOT BE EMPTY !!!",
                "NAME MUST CONTAIN 1-100 CHARACTER, \nBEGINNING CHAR MUST BE NOT NUMBER OR SPECIAL CHARACTER  !!!")) {
            System.out.println("serviceName false");
            check_Validate = false;
        } else if (validateTextField(serviceUnit, "^(?=.{1,20}$)[a-zA-Z][a-zA-Z0-9_]+$", "SERVICE UNIT MUST NOT BE EMPTY !!!",
                "UNIT MUST CONTAIN 1-20 CHARACTER, \nBEGINNING CHAR MUST NOT BE NUMBER OR SPECIAL CHARACTER  !!!")) {
            System.out.println("serviceUnit false");
            check_Validate = false;
        } else if (validateTextField(servicePrice, "^[\\d][\\d]*\\.?[\\d]*$", "SERVICE PRICE MUST NOT BE EMPTY !!!",
                "PRICE MUST CONTAIN NUMBER \nAND MUST BE CORRECT NUMBER FORMAT !!!")) {
            System.out.println("servicePrice false");
            check_Validate = false;
        } else if (imgService.getImage() == null) {
            System.out.println("imgService is null");
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(465, 35);
                label.setText("IMAGE MUST NOT BE EMPTY");
                imgService.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
                hBoxContent.setSpacing(10);
                hBoxContent.setAlignment(Pos.CENTER);
                hBoxContent.getChildren().clear();
                hBoxContent.getChildren().add(icon);
                hBoxContent.getChildren().add(label);
                btnInsertImage.requestFocus();
            });
            check_Validate = false;
        } else if (validateTextArea(txtArea_Service_Description, "^(.|\\n){1,500}$", "NO EMPTY", "LENGTH MUST < 500 CHARACTERS")) {
            System.out.println("serviceDescription is too long");
            check_Validate = false;
        } else {
            System.out.println("Validate finished");
            System.out.println("Add finished");
            check_Validate = true;
        }
    }

    public Boolean validateTextField(JFXTextField textField, String regexString, String warningEmpty, String warningPattern) {
        Boolean check;
        Pattern pattern = Pattern.compile(regexString);
        if (textField.getText().equals("")) {
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(465, 35);
                label.setText(warningEmpty);
                //textField.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
                textField.getStyleClass().add("text-field-fault");
                hBoxContent.setSpacing(10);
                hBoxContent.setAlignment(Pos.CENTER);
                hBoxContent.getChildren().clear();
                hBoxContent.getChildren().add(icon);
                hBoxContent.getChildren().add(label);
                textField.requestFocus();
            });
            check = true;
        } else if (!pattern.matcher(textField.getText()).matches()) {
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(465, 35);
                label.setText(warningPattern);
                textField.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
                hBoxContent.setSpacing(10);
                hBoxContent.setAlignment(Pos.CENTER);
                hBoxContent.getChildren().clear();
                hBoxContent.getChildren().add(icon);
                hBoxContent.getChildren().add(label);
                textField.requestFocus();
            });
            check = true;
        } else {
            check = false;
        }
        return check;
    }

    public Boolean validateTextArea(JFXTextArea textField, String regexString, String warningEmpty, String warningPattern) {
        Boolean check;
        Pattern pattern = Pattern.compile(regexString);
        if (!textField.getText().equalsIgnoreCase("") && !pattern.matcher(textField.getText()).matches()) {
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(465, 35);
                label.setText(warningPattern);
                textField.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
                hBoxContent.setSpacing(10);
                hBoxContent.setAlignment(Pos.CENTER);
                hBoxContent.getChildren().clear();
                hBoxContent.getChildren().add(icon);
                hBoxContent.getChildren().add(label);
                textField.requestFocus();
            });
            check = true;
        } else {
            check = false;
        }
        return check;
    }

    @FXML
    private void formatImportDate(ActionEvent event) {
        String pattern = "dd-MM-yyyy";
        formatCalender.format(pattern, serviceImportDate);
    }
}
