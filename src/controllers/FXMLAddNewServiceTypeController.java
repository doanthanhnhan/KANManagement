/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import models.ServiceTypeDAOImpl;
import models.ServiceType;
import utils.StageLoader;

/**
 * FXML Controller class
 *
 * @author Doan Thanh Nhan
 */
public class FXMLAddNewServiceTypeController implements Initializable {

    public static ObservableList<ServiceType> listServiceType;
    ServiceTypeDAOImpl serviceTypeDAOImpl = new ServiceTypeDAOImpl();
    final FileChooser fileChooser = new FileChooser();

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.png, *.jpg, *.gif, *.bmp)", "*.jpg", "*.png", "*.gif", "*.bmp");

        fileChooser.getExtensionFilters().add(extFilter);
        // Set path for fileChooser
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        fileChooser.setInitialDirectory(new File(currentPath + "/src/images"));
    }

    @FXML
    private void btnSubmitAddNewEmployee(ActionEvent event) {
        btnAddNew.setDisable(true);

        // Đoạn này làm loading (đang làm chạy vô tận)
        // Khai báo stage nhìn xuyên thấu
        final Stage stage = new Stage(StageStyle.TRANSPARENT);

        // Chỗ này set khi mở cửa sổ con lên thì cha bị vô hiệu
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOpacity(0.5);

        final Label status = new Label("Adding new Service Type");
        status.setStyle("-fx-text-fill: #008FC0; -fx-font-size : 20px; -fx-font-weight: bold;");
        final ProgressIndicator indicator = new ProgressIndicator(ProgressIndicator.INDETERMINATE_PROGRESS);
        indicator.setPrefSize(100, 100);
        //indicator.setProgress(-1d);
        final Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        String statusText = status.getText();
                        status.setText(
                                ("Adding Service Type . . .".equals(statusText))
                                ? "Adding Service Type ."
                                : statusText + " ."
                        );
                    }
                }),
                new KeyFrame(Duration.millis(1000))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        //Platform.runLater(() -> {
        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(10);
        layout.getChildren().addAll(indicator, status);
        layout.setStyle("-fx-padding: 10;");
        stage.setScene(new Scene(layout, 300, 150));
        stage.show();
        //});

        timeline.play();
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
                System.out.println("Add successful!");

                return null;
            }
        };

        loadOverview.setOnSucceeded(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                Platform.runLater(() -> {
                    btnAddNew.setDisable(false);
                    timeline.stop();
                    stage.close();
                });
                System.out.println("Finished");
            }
        });

        new Thread(loadOverview).start();
    }

    @FXML
    private void handle_Button_Insert_Image_Action(ActionEvent event) {
        File file = fileChooser.showOpenDialog(btnInsertImage.getScene().getWindow());
        fileChooser.setTitle("Choose an image for service type");

        if (file != null) {
            System.out.println(file.toURI().toString());
            Image image = new Image(file.toURI().toString());
            imgService.setImage(image);
        }
    }

    public void addNewServiceType() {
        ServiceType serviceType = new ServiceType();
        serviceType.setServiceID(serviceID.getText());
        serviceType.setServiceName(serviceName.getText());
        serviceType.setServiceUnit(serviceUnit.getText());
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

        serviceTypeDAOImpl.addServiceType(serviceType);
    }

    public void validateForm() {
        if (validateTextField(serviceID, "^(?=.{4,50}$)[a-zA-Z][a-zA-Z0-9_]+$", "SERVICE ID MUST NOT BE EMPTY !!!",
                "ID MUST CONTAIN 4-50 CHARACTER, \nBEGINNING CHAR MUST BE NOT NUMBER OR CHARACTER SPECIAL !!!")) {
            System.out.println("serviceID false");
        } else if (validateTextField(serviceName, "^(?=.{4,200}$)[a-zA-Z][a-zA-Z0-9_]+$", "SERVICE NAME MUST NOT BE EMPTY !!!",
                "NAME MUST CONTAIN 4-12 CHARACTER, \nBEGINNING CHAR MUST BE NOT NUMBER OR CHARACTER SPECIAL !!!")) {
            System.out.println("serviceName false");
        } else if (validateTextField(serviceUnit, "^(?=.{1,10}$)[a-zA-Z][a-zA-Z0-9_]+$", "SERVICE UNIT MUST NOT BE EMPTY !!!",
                "UNIT MUST CONTAIN 4-12 CHARACTER, \nBEGINNING CHAR MUST NOT BE NUMBER OR CHARACTER SPECIAL !!!")) {
            System.out.println("serviceUnit false");
        } else if (validateTextField(servicePrice, "^[\\d][\\d]*\\.?[\\d]*$", "SERVICE PRICE MUST NOT BE EMPTY !!!",
                "PRICE MUST CONTAIN NUMBER \nAND MUST BE CORRECT NUMBER FORMAT !!!")) {
            System.out.println("servicePrice false");
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
        } else {
            System.out.println("Validate finished");
            addNewServiceType();
            System.out.println("Add finished");
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
                textField.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
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
}
