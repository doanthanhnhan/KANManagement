/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import models.ServiceTypeDAOImpl;
import models.ServiceType;

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
    private void btnSubmitAddNewEmployee(ActionEvent event) throws IOException {
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
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAddNewServiceTypeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        serviceTypeDAOImpl.addServiceType(serviceType);
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

}
