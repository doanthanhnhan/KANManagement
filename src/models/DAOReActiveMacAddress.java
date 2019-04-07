/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.jfoenix.controls.JFXButton;
import controllers.ConnectControllers;
import static controllers.ConnectControllers.fXMLMainFormController;
import controllers.FXMLLoginController;
import controllers.FXMLReActiveController;
import controllers.FXMLReActiveMacAddressController;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import static models.DAOReActive.Employee_ID;
import static models.DAOReActive.check_InfoEmployeeReactive;
import static models.DAOcheckRole.showFormLogin;
import utils.AlertLoginAgain;
import utils.GetInetAddress;
import utils.StageLoader;
import utils.connectDB;

/**
 *
 * @author Admin
 */
public class DAOReActiveMacAddress {

    private static FXMLReActiveMacAddressController fXMLReActiveMacAddressController;

    public static ObservableList<MacAddress> getAllReActiveMacAddress() throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        ObservableList<MacAddress> list = FXCollections.observableArrayList();
        String sql = "select ID,MACAddress,Times,Active  from CheckBlockMacAddress where Active = 0";
        PreparedStatement pt = connection.prepareStatement(sql);
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        ResultSet rs = pt.executeQuery();
        while (rs.next()) {
            MacAddress Emp = new MacAddress();
            Emp.setID(rs.getInt("ID"));
            Emp.setMACAddress(rs.getString("MACAddress"));
            Emp.setTimes(rs.getInt("Times"));
            Emp.setcheckMacAddress_ReActive(rs.getBoolean("Active"));
            CheckBox cb_Active_MacAddress = new CheckBox("");
            cb_Active_MacAddress.setSelected(false);
            cb_Active_MacAddress.setDisable(true);
            Emp.setMacAddress_ReActive(cb_Active_MacAddress);

//            set JFXButton
            HBox Action = new HBox();
            JFXButton btn_Edit = new JFXButton("Edit");
            btn_Edit.getStyleClass().add("btn-green-color");
            JFXButton btn_Update = new JFXButton("Update");
            btn_Update.getStyleClass().add("btn-warning-color-update");
            Action.setAlignment(Pos.CENTER);
            Action.setSpacing(10);
            Action.getChildren().addAll(btn_Edit, btn_Update);
            btn_Update.setDisable(true);
//            set action for btn
            btn_Edit.setOnAction((event) -> {
//                CheckIn CheckOut Department Customer Booking
                cb_Active_MacAddress.setDisable(false);
                btn_Update.setDisable(false);
                btn_Edit.setDisable(true);
            });
//            action Update            
            btn_Update.setOnAction((event) -> {
                try {
                    if (!DAOcheckRole.checkRoleDecentralization(FXMLLoginController.User_Login, "ReActive_View")) {
                        AlertLoginAgain.alertLogin();
                        fXMLMainFormController = ConnectControllers.getfXMLMainFormController();
                        Stage stageMainForm = (Stage) fXMLMainFormController.AnchorPaneMainForm.getScene().getWindow();
                        stageMainForm.close();
                        showFormLogin.showFormLogin();
                    } else {
                        cb_Active_MacAddress.setDisable(true);
                        btn_Update.setDisable(true);
                        check_InfoEmployeeReactive = true;
                        btn_Edit.setDisable(false);
                        if (cb_Active_MacAddress.isSelected()) {
                            update_MacAddress(Emp.getMACAddress());
                            DAO.setUserLogs_With_MAC(FXMLLoginController.User_Login,
                                    "ReActive for " + Emp.getMACAddress(),
                                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), GetInetAddress.getMacAddress());
                            fXMLReActiveMacAddressController = ConnectControllers.getfXMLReActiveMacAddressController();
                            fXMLReActiveMacAddressController.showUsersData();
                        }

                    }
                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(DAOcheckRole.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            Emp.setHboxReActive(Action);
            list.add(Emp);
        }
        return list;
    }
//    update macaddress

    public static void update_MacAddress(String Mac) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        String ex = "Update CheckBlockMacAddress Set Times=0,Active=1 where MACAddress=?";
        PreparedStatement pts = connection.prepareStatement(ex);
        pts.setString(1, Mac);
        pts.execute();
        pts.close();
        connection.close();
    }
}
