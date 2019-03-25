/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import utils.connectDB;

/**
 *
 * @author Doan Thanh Nhan
 */
public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ObservableList<Customer> getAllCustomers() {
        String sql = "SELECT CustomerID, UserName, CustomerFirstName, CustomerMidName, CustomerLastName, CustomerBirthday, "
                + "CustomerPhoneNumber, CustomerPassport, CustomerEmail, Discount, Company, Sex, Active "
                + "FROM Customers";
        ObservableList<Customer> listCustomers = FXCollections.observableArrayList();
        try {
            try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Customer customer = new Customer();
                    customer.setCusID(rs.getString("CustomerID"));
                    customer.setUser(rs.getString("UserName"));
                    customer.setFName(rs.getString("CustomerFirstName"));
                    customer.setMName(rs.getString("CustomerMidName"));
                    customer.setLName(rs.getString("CustomerLastName"));
                    customer.setDate(rs.getString("CustomerBirthday"));
                    customer.setPhone(rs.getString("CustomerPhoneNumber"));
                    customer.setPassport(rs.getString("CustomerPassport"));
                    customer.setEmail(rs.getString("CustomerEmail"));
                    customer.setDiscount(rs.getFloat("Discount"));
                    customer.setCompany(rs.getString("Company"));
                    customer.setSex(rs.getBoolean("Sex"));
                    customer.setActive(rs.getBoolean("Active"));

                    listCustomers.add(customer);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setHeaderText("Error");
            alert.setContentText("Don't have any customer in Database or Can't connect to Database");
            alert.show();
            Logger.getLogger(CustomerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listCustomers;
    }

    @Override
    public ObservableList<String> getAllCustomersID() {
        String sql = "SELECT CustomerID FROM Customers";
        ObservableList<String> listCustomers = FXCollections.observableArrayList();
        try {
            try (Connection conn = connectDB.connectSQLServer(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String customerID;
                    customerID = rs.getString("CustomerID");

                    listCustomers.add(customerID);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setHeaderText("Error");
            alert.setContentText("Don't have any customer in Database or Can't connect to Database");
            alert.show();
            Logger.getLogger(CustomerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listCustomers;
    }

    @Override
    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO Customers (CustomerID, UserName, CustomerFirstName, CustomerMidName, "
                + "CustomerLastName, CustomerBirthday, CustomerPhoneNumber, CustomerPassport, "
                + "CustomerEmail, Discount, Company, Sex, Active) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, customer.getCusID());
                stmt.setString(2, customer.getUser());
                stmt.setString(3, customer.getFName());
                stmt.setString(4, customer.getMName());
                stmt.setString(5, customer.getLName());
                stmt.setTimestamp(6, Timestamp.valueOf(customer.getDate()));
                stmt.setString(7, customer.getPhone());
                stmt.setString(8, customer.getPassport());
                stmt.setString(9, customer.getEmail());
                stmt.setFloat(10, customer.getDiscount());
                stmt.setString(11, customer.getCompany());
                stmt.setBoolean(12, customer.getSex());
                stmt.setBoolean(13, customer.getActive());

                stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(CustomerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setHeaderText("Error");
            alert.setContentText("Duplicated Customer in Database or Can't connect to Database");
            alert.show();
        }
    }

    @Override
    public void editCustomer(Customer customer, Boolean active) {
        String sql = "UPDATE Customers SET CustomerID=?, UserName=?, CustomerFirstName=?, CustomerMidName=?, "
                + "CustomerLastName=?, CustomerBirthday=?, CustomerPhoneNumber=?, CustomerPassport=?, "
                + "CustomerEmail=?, Discount=?, Company=?, Sex=?, Active=? "
                + "WHERE CustomerID=?";
        try {
            try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, customer.getCusID());
                stmt.setString(2, customer.getUser());
                stmt.setString(3, customer.getFName());
                stmt.setString(4, customer.getMName());
                stmt.setString(5, customer.getLName());
                stmt.setTimestamp(6, Timestamp.valueOf(customer.getDate()));
                stmt.setString(7, customer.getPhone());
                stmt.setString(8, customer.getPassport());
                stmt.setString(9, customer.getEmail());
                stmt.setFloat(10, customer.getDiscount());
                stmt.setString(11, customer.getCompany());
                stmt.setBoolean(12, customer.getSex());
                stmt.setBoolean(13, customer.getActive());
                stmt.setString(14, customer.getCusID());

                stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(CustomerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setHeaderText("Error");
            alert.setContentText("Duplicated Customer in Database or Can't connect to Database");
            alert.show();
        }
    }

    @Override
    public void deleteCustomer(Customer customer) {
        String sql = "DELETE FROM Customers WHERE CustomerID=?";
        try {
            try (Connection conn = connectDB.connectSQLServer(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, customer.getCusID());
                stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(CustomerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setHeaderText("Error");
            alert.setContentText("Can't connect to Database");
            alert.show();
        }
    }

}
