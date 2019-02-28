/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import models.CheckInRoom;
import utils.connectDB;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class FXMLCheckInRoomController implements Initializable {

    @FXML
    private JFXButton serachBtn;

    @FXML
    private JFXTextField txtID;

    @FXML
    private TableView<CheckInRoom> TableViewData;

    @FXML
    private MenuItem DeleteBtn;

    @FXML
    private MenuItem EditBtn;

    @FXML
    void handleDeleteMenu(ActionEvent event) {

    }

    @FXML
    void handleEditMenu(ActionEvent event) {

    }

    @FXML
    void handleSearchBtn(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void handelEditMenu(ActionEvent event) {
    }

}
