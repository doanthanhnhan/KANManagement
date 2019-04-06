/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.jfoenix.controls.JFXButton;
import controllers.ConnectControllers;
import static controllers.ConnectControllers.fXMLMainFormController;
import controllers.FXMLInfoEmployeeController;
import controllers.FXMLListEmployeeController;
import controllers.FXMLLoginController;
import controllers.FXMLReActiveController;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
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
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import static models.DAOcheckRole.showFormLogin;
import utils.AlertLoginAgain;
import utils.GetInetAddress;
import utils.StageLoader;
import utils.connectDB;

/**
 *
 * @author Admin
 */
public class DAOReActive {

    public static boolean check_InfoEmployeeReactive = false;
    private static FXMLListEmployeeController fXMLListEmployeeController;
    private static FXMLReActiveController reActiveController;

    public static ObservableList<ReActive> getAllDecentralization() throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        ObservableList<ReActive> list = FXCollections.observableArrayList();
        String sql = "select Employees.EmployeeID,Employees.EmployeeFirstName,Employees.EmployeeLastName,Employees.EmployeeMidName, Employees.Active  from Employees where Active = 0";
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
            cb_Active_Employee.setDisable(true);
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
                btn_Update.setDisable(false);
                btn_Edit.setDisable(true);
            });
//            action Update            
            if(FXMLReActiveController.Emp == null){
                btn_Update.setDisable(true);
                btn_Edit.setDisable(true);
                cb_Active_Employee.setDisable(true);
            }
            btn_Update.setOnAction((event) -> {
                try {
                    if (!DAOcheckRole.checkRoleDecentralization(FXMLLoginController.User_Login, "ReActive_View")) {
                        AlertLoginAgain.alertLogin();
                        fXMLMainFormController = ConnectControllers.getfXMLMainFormController();
                        Stage stageMainForm = (Stage) fXMLMainFormController.AnchorPaneMainForm.getScene().getWindow();
                        stageMainForm.close();
                        showFormLogin.showFormLogin();
                    } else {
                        cb_Active_Employee.setDisable(true);
                        btn_Update.setDisable(true);
                        check_InfoEmployeeReactive = true;
                        btn_Edit.setDisable(false);
                        if (cb_Active_Employee.isSelected()) {
                            StageLoader stageLoader = new StageLoader();
                            stageLoader.formLoader("/fxml/FXMLInfoEmployee.fxml", "/images/KAN Logo.png", "Edit Employee Informations");
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

    public static void update_Active(String User) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        String ex = "Update Employees Set Active=? where EmployeeID=?";
        PreparedStatement pts = connection.prepareStatement(ex);
        pts.setBoolean(1, true);
        pts.setString(2, User);
        pts.execute();
        String exp = "Update Users Set Active=? where EmployeeID=?";
        PreparedStatement pt = connection.prepareStatement(exp);
        pt.setBoolean(1, true);
        pt.setString(2, User);
        pt.execute();
        pts.close();
        connection.close();
        pt.close();
    }

    public static InfoEmployee getInfoEmployeeReactive(String user) throws ClassNotFoundException, SQLException {
        Connection connection = connectDB.connectSQLServer();
        String sql = "select * from Employees where EmployeeID = ?";
        PreparedStatement pt = connection.prepareStatement(sql);
        pt.setString(1, user);
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        ResultSet rs = pt.executeQuery();
        InfoEmployee Emp = new InfoEmployee();
        while (rs.next()) {
            Emp.setEmployee_ID(rs.getString("EmployeeID"));
            Emp.setActive(rs.getBoolean("Active"));
            Emp.setFirst_Name(rs.getString("EmployeeFirstName"));
            if (rs.getString("EmployeeMidName") == null) {
                Emp.setMid_Name("");
            } else {
                Emp.setMid_Name(rs.getString("EmployeeMidName"));
            }
            Emp.setLast_Name(rs.getString("EmployeeLastName"));
            if (rs.getString("DepartmentID") == null) {
                Emp.setWork_Dept("");
            } else {
                Emp.setWork_Dept(rs.getString("DepartmentID"));
            }
            if (rs.getString("PhoneNumber") == null) {
                Emp.setPhone_No("");
            } else {
                Emp.setPhone_No(rs.getString("PhoneNumber"));
            }
            if (rs.getString("Job") == null) {
                Emp.setJob("");
            } else {
                Emp.setJob(rs.getString("Job"));
            }
            Emp.setEDLEVEL(rs.getInt("EducatedLevel"));
            Emp.setHiredate(rs.getString("HireDate"));

            Emp.setBirthdate(rs.getString("Birthday"));
            if (rs.getString("Salary") == null) {
                Emp.setSalary("");
            } else {
                Emp.setSalary(rs.getString("Salary"));
            }
            if (rs.getString("Bonus") == null) {
                Emp.setBonus("");
            } else {
                Emp.setBonus(rs.getString("Bonus"));
            }
            if (rs.getString("Address") == null) {
                Emp.setAddress("");
            } else {
                Emp.setAddress(rs.getString("Address"));
            }
            if (rs.getString("Comm") == null) {
                Emp.setComm("");
            } else {
                Emp.setComm(rs.getString("Comm"));
            }
            Emp.setGmail(rs.getString("Email"));
            if (rs.getString("IDNumber") == null) {
                Emp.setId_number("");
            } else {
                Emp.setId_number(rs.getString("IDNumber"));
            }
            Emp.setServiceImage(rs.getBlob("Image"));
            if (rs.getBlob("Image") != null) {
                try {
                    byte[] bytes = Emp.getServiceImage().getBytes(1l, (int) Emp.getServiceImage().length());
                    BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes));
                    Image image = SwingFXUtils.toFXImage(img, null);
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(50);
                    imageView.setFitWidth(50);
                    Emp.setImageView(imageView);
                } catch (IOException ex) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Emp.setSex(rs.getBoolean("Sex"));
        }
        connection.close();
        rs.close();
        pt.close();
        return Emp;
    }
}
