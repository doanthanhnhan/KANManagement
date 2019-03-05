/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.DAO;
import utils.Email;
import utils.MD5Encrypt;
import java.util.regex.Pattern;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import models.InfoEmployee;
import utils.FormatName;
// regex first last ^[A-Z](?:[a-z]*){2,15}+$

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class FXMLAddNewEmloyeeController implements Initializable {

    @FXML
    private JFXTextField newGmail;
    @FXML
    private JFXTextField newFirstname;
    @FXML
    private JFXTextField newMidname;
    @FXML
    private JFXTextField newLastname;
    @FXML
    private JFXComboBox newRole;
    @FXML
    private JFXTextField newId;
    @FXML
    private JFXRadioButton sexMale;
    @FXML
    private JFXRadioButton sexFemale;
    @FXML
    private JFXButton btnAddNew;
    @FXML
    private HBox HboxContent;
    private Pattern pattern;
    private Pattern patternEmail;
    private Pattern patternFLName;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            if (DAO.checkFirstLogin() == 0) {
                newRole.setValue("Admin");
                newRole.setDisable(true);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLAddNewEmloyeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        newId.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Platform.runLater(() -> {
                    HboxContent.getChildren().clear();
                    newId.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");

                });
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        btnSubmitAddNewEmployee();
                    } catch (ClassNotFoundException | SQLException | IOException ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(FXMLAddNewEmloyeeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        newGmail.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Platform.runLater(() -> {
                    HboxContent.getChildren().clear();
                    newGmail.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");

                });
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        btnSubmitAddNewEmployee();
                    } catch (ClassNotFoundException | SQLException | IOException ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(FXMLAddNewEmloyeeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        newFirstname.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Platform.runLater(() -> {
                    HboxContent.getChildren().clear();
                    newFirstname.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");

                });
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        btnSubmitAddNewEmployee();
                    } catch (ClassNotFoundException | SQLException | IOException ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(FXMLAddNewEmloyeeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        newLastname.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Platform.runLater(() -> {
                    HboxContent.getChildren().clear();
                    newLastname.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");

                });
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        btnSubmitAddNewEmployee();
                    } catch (ClassNotFoundException | SQLException | IOException ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(FXMLAddNewEmloyeeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        newMidname.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Platform.runLater(() -> {
                    HboxContent.getChildren().clear();
                    newMidname.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                });
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        btnSubmitAddNewEmployee();
                    } catch (ClassNotFoundException | SQLException | IOException ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(FXMLAddNewEmloyeeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        newRole.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        btnSubmitAddNewEmployee();
                    } catch (ClassNotFoundException | SQLException | IOException ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(FXMLAddNewEmloyeeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        newRole.valueProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null) {
                Platform.runLater(() -> {
                    HboxContent.getChildren().clear();
                    newRole.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                });
            }
        });
        ObservableList listRole = FXCollections.observableArrayList();
        try {
            listRole = DAO.getAllRole();
            newRole.setItems(listRole);
            // TODO
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLAddNewEmloyeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnSubmitAddNewEmployee() throws ClassNotFoundException, SQLException, IOException {
        btnAddNew.setDisable(true);
        // Đoạn này làm loading (đang làm chạy vô tận)

        // Khai báo stage nhìn xuyên thấu
        final Stage stage = new Stage(StageStyle.TRANSPARENT);

        // Chỗ này set khi mở cửa sổ con lên thì cha bị vô hiệu
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOpacity(0.5);

        final Label status = new Label("Sending Email");
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
                                ("Sending Email . . .".equals(statusText))
                                ? "Sending Email ."
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
                        HboxContent.getChildren().clear();
                    }
                });
                AddNewEmployee();
                return null;
            }
        };

        loadOverview.setOnSucceeded(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                System.out.println("Finished");
                Platform.runLater(() -> {
                    btnAddNew.setDisable(false);
                    timeline.stop();
                    stage.close();
                });
            }
        });

        new Thread(loadOverview).start();

    }

    private void AddNewEmployee() throws ClassNotFoundException, SQLException, IOException, Exception {
//        valided texfield
        pattern = Pattern.compile("^(?=.{4,12}$)[a-zA-Z][a-zA-Z0-9_]+$");
        patternEmail = Pattern.compile("^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$");
        patternFLName = Pattern.compile("^\\pL+[\\pL\\pZ]{1,25}$");
        if (newId.getText().equals("")) {
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(465, 35);
                label.setText("ID MUST NOT EMPTY !!!");
                newId.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
                HboxContent.setSpacing(10);
                HboxContent.setAlignment(Pos.CENTER);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
                newId.requestFocus();
            });
        } else if (newGmail.getText().equals("")) {
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(465, 35);
                label.setText("GMAIL MUST NOT EMPTY !!!");
                newGmail.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
                HboxContent.setSpacing(10);
                HboxContent.setAlignment(Pos.CENTER);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
                newGmail.requestFocus();
            });
        } else if (newFirstname.getText().equals("")) {
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(465, 35);
                label.setText("FIRST NAME MUST NOT EMPTY !!!");
                newFirstname.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
                HboxContent.setSpacing(10);
                HboxContent.setAlignment(Pos.CENTER);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
                newFirstname.requestFocus();
            });

        } else if (newLastname.getText().equals("")) {
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(465, 35);
                label.setText("LAST NAME MUST NOT EMPTY !!!");
                newLastname.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
                HboxContent.setSpacing(10);
                HboxContent.setAlignment(Pos.CENTER);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
                newLastname.requestFocus();
            });

        } else if (newRole.getValue()==null) {
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(470, 35);
                label.setText("ROLE MUST NOT EMPTY !!!");
                newRole.getStyleClass().removeAll();
                newRole.getStyleClass().add("jfx-combo-box-fault");
                HboxContent.setSpacing(10);
                HboxContent.setAlignment(Pos.CENTER);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
                newRole.requestFocus();
            });
        } else if (!pattern.matcher(newId.getText()).matches()) {
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(465, 35);
                label.setText("ID MUST 4-12 CHARACTER, NOT BEGIN NUMBER AND CHARACTER SPECIAL !!!");
                newId.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
                HboxContent.setSpacing(10);
                HboxContent.setAlignment(Pos.CENTER);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
                newId.requestFocus();
            });

        } else if (!patternEmail.matcher(newGmail.getText()).matches()) {

            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(465, 35);
                label.setText("INVALID EMAIL !!!");
                newGmail.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
                HboxContent.setSpacing(10);
                HboxContent.setAlignment(Pos.CENTER);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
                newGmail.requestFocus();
            });

        } else if (!patternFLName.matcher(newFirstname.getText()).matches()) {
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(465, 35);
                label.setText("FIRSTNAME INVALID !!! (Example: Nguyễn, John,...) ");
                newFirstname.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
                HboxContent.setSpacing(10);
                HboxContent.setAlignment(Pos.CENTER);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
                newFirstname.requestFocus();
            });

        } else if (!patternFLName.matcher(newMidname.getText()).matches() && !newMidname.getText().equals("")) {
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(465, 35);
                label.setText("MIDNAME INVALID !!!(Example: Thị, Rooney,...");
                newMidname.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
                HboxContent.setSpacing(10);
                HboxContent.setAlignment(Pos.CENTER);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
                newMidname.requestFocus();
            });

        } else if (!patternFLName.matcher(newLastname.getText()).matches()) {
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(465, 35);
                label.setText("LASTNAME INVALID !!!(Example: Toàn, Văn,...");
                newLastname.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
                HboxContent.setSpacing(10);
                HboxContent.setAlignment(Pos.CENTER);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
                newLastname.requestFocus();
            });

        } else {

            ObservableList<InfoEmployee> List_Check_Employee = FXCollections.observableArrayList();
            List_Check_Employee = FXMLLoginController.List_Employee;
            boolean check = true;
            for (InfoEmployee infoEmployee : List_Check_Employee) {
                if (newId.getText().equals(infoEmployee.getEmployee_ID())) {

                    Platform.runLater(() -> {
                        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                        icon.setSize("16");
                        icon.setStyleClass("jfx-glyhp-icon");
                        Label label = new Label();
                        label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                        label.setPrefSize(465, 35);
                        label.setText("ID ALREADY EXIST !!!");
                        newLastname.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
                        HboxContent.setSpacing(10);
                        HboxContent.setAlignment(Pos.CENTER);
                        HboxContent.getChildren().clear();
                        HboxContent.getChildren().add(icon);
                        HboxContent.getChildren().add(label);
                        newId.requestFocus();
                    });

                    check = false;
                    break;
                } else if (newGmail.getText().equals(infoEmployee.getGmail())) {

                    Platform.runLater(() -> {
                        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                        icon.setSize("16");
                        icon.setStyleClass("jfx-glyhp-icon");
                        Label label = new Label();
                        label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                        label.setPrefSize(465, 35);
                        label.setText("EMAIL ALREADY EXIST !!!");
                        newLastname.setStyle("-jfx-focus-color: #FF2625;-jfx-unfocus-color: #FF2625;");
                        HboxContent.setSpacing(10);
                        HboxContent.setAlignment(Pos.CENTER);
                        HboxContent.getChildren().clear();
                        HboxContent.getChildren().add(icon);
                        HboxContent.getChildren().add(label);
                        newGmail.requestFocus();
                    });

                    check = false;
                    break;
                }
            }
            if (check == true) {
                String Sex;
                if (sexMale.selectedProperty().getValue()) {
                    Sex = "Male";
                } else {
                    Sex = "Female";
                }
                String Id_Role;
                Id_Role = DAO.getIdRole((String) newRole.getValue());
                DAO.AddNewEmployee(newId.getText(), FormatName.format(newFirstname.getText()), FormatName.format(newMidname.getText()), FormatName.format(newLastname.getText()), Id_Role, newGmail.getText(), Sex);
                String Username = newId.getText();
                MD5Encrypt m;
                m = new MD5Encrypt();
                String Password = m.hashPass("123456");
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                Calendar cal = Calendar.getInstance();
                String logtime;
                logtime = dateFormat.format(cal.getTime());
                DAO.AddUser(newId.getText(), Username, Password, logtime);
                String content = "Username: " + newId.getText() + ", Password: 123456";
                Email.send_Email_Without_Attach("smtp.gmail.com", newGmail.getText(), "KANManagement.AP146@gmail.com",
                        "KAN@123456", "Default username and password", content);
                Platform.runLater(() -> {
                    try {
                        FXMLLoginController loginController = ConnectControllers.getfXMLLoginController();
                        loginController.List_Employee = DAO.getAllEmployee();
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(FXMLAddNewEmloyeeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    newRole.setValue(null);
                    newFirstname.setText("");
                    newMidname.setText("");
                    newLastname.setText("");
                    newGmail.setText("");
                    newId.setText("");
                    newId.requestFocus();
                });

                if (DAO.checkFirstLogin() == 1) {
                    Platform.runLater(() -> {
                        Stage stage = (Stage) btnAddNew.getScene().getWindow();
                        stage.close();
                        Stage stageEdit = new Stage();
                        stageEdit.resizableProperty().setValue(Boolean.FALSE);
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("/fxml/FXMLLogin.fxml"));
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLAddNewEmloyeeController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        stageEdit.getIcons().add(new Image("/images/iconmanagement.png"));
                        Scene scene = new Scene(root);
                        stageEdit.setTitle("KANManagement");
                        stageEdit.setScene(scene);
                        stageEdit.show();
                    });
                }
            }
        }
    }
}
