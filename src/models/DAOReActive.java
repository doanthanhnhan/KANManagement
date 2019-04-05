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
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import static models.DAOcheckRole.showFormLogin;
import static models.DAOcheckRole.update_EmployeeDecentralization;
import utils.AlertLoginAgain;
import utils.GetInetAddress;
import utils.connectDB;

/**
 *
 * @author Admin
 */
public class DAOReActive {

    public static ObservableList<ReActive> getAllDecentralization() throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        ObservableList<ReActive> list = FXCollections.observableArrayList();
        String sql = "select Employees.EmployeeID,Employees.EmployeeFirstName,Employees.EmployeeLastName,Employees.EmployeeMidName where Active = 0";
        PreparedStatement pt = connection.prepareStatement(sql);
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        ResultSet rs = pt.executeQuery();
        while (rs.next()) {
            ReActive Emp = new ReActive();
            Emp.setEmployee_ID(rs.getString("EmployeeID"));
            Emp.setFirst_Name(rs.getString("EmployeeFirstName"));
            if (rs.getString("EmployeeMidName") == null) {
                Emp.setMid_Name("");
            } else {
                Emp.setMid_Name(rs.getString("EmployeeMidName"));
            }
            Emp.setLast_Name(rs.getString("EmployeeLastName"));
            Emp.setcheckEmployee_ReActive(rs.getBoolean("Active"));

            CheckBox cb_Active_Employee = new CheckBox("");

            cb_Active_Employee.setSelected(false);

            Emp.setEmployee_ReActive(cb_Active_Employee);

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
                cb_Active_Employee.setDisable(false);
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
//                CheckIn CheckOut Department Customer Booking
                        cb_Active_Employee.setDisable(true);
                        btn_Update.setDisable(true);
                        update_Active(
                                Emp.getEmployee_ID(),cb_Active_Employee.isSelected()
                        );
                        DAO.setUserLogs_With_MAC(FXMLLoginController.User_Login, "Update Active = "+cb_Active_Employee.isSelected()+" for " + Emp.getEmployee_ID(),
                                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), GetInetAddress.getMacAddress());
                        btn_Edit.setDisable(false);
                    }
                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(DAOcheckRole.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            Emp.setReActive(Action);
            list.add(Emp);
        }
        return list;
    }

    public static void update_Active(String User,Boolean Active) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        String ex = "Update Role Set Active=? where EmployeeID=?";
        PreparedStatement pts = connection.prepareStatement(ex);
        pts.setBoolean(1, Active);
        pts.setString(2, User);
        pts.execute();
        pts.close();
        connection.close();
    }
}
