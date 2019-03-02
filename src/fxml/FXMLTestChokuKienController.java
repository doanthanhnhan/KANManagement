/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxml;

import com.jfoenix.controls.JFXTextField;
import static controllers.FXMLAccountController.stage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import utils.QRWebCam;

/**
 * FXML Controller class
 *
 * @author Doan Thanh Nhan
 */
public class FXMLTestChokuKienController implements Initializable {

    @FXML
    private Button btnTest;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private JFXTextField txtInput;
    @FXML
    private Label labelDisplay;
    @FXML
    private Button btnWebCam;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }

    @FXML
    private void TestFunction(ActionEvent event) {
        functiona();
    }

    /**
     * Để anh cố gắng tạo 1 class chạy loading chung để lấy ra dùng nhiều lần cho nó dễ
     * Đã có loading đẹp, nhưng chưa hiểu code nên chưa ném vào đây ^_^
     */
    public void functiona() {
        System.out.println(mainPane.lookup("#btn_test"));

        Button btn = (Button) mainPane.lookup("#btn_test");
        btn.setDisable(true);

        // Đoạn này làm loading (đang làm chạy vô tận)
        
        // Khai báo stage nhìn xuyên thấu
        final Stage stage = new Stage(StageStyle.TRANSPARENT);
        stage.setOpacity(0.5);
        // Chỗ này set khi mở cửa sổ con lên thì cha bị vô hiệu
        stage.initModality(Modality.APPLICATION_MODAL);
        
        final Label status = new Label("Loading");
        final ProgressIndicator indicator = new ProgressIndicator(ProgressIndicator.INDETERMINATE_PROGRESS);
        indicator.setPrefSize(100, 100);
        final Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        String statusText = status.getText();
                        status.setText(
                                ("Loading . . .".equals(statusText))
                                ? "Loading ."
                                : statusText + " ."
                        );
                    }
                }),
                new KeyFrame(Duration.millis(1000))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        Platform.runLater(() -> {
            VBox layout = new VBox();
            layout.setAlignment(Pos.CENTER);
            layout.setSpacing(10);
            layout.getChildren().addAll(indicator, status);
            layout.setStyle("-fx-padding: 10;");
            stage.setScene(new Scene(layout, 300, 300));
            stage.show();
        });

        timeline.play();
        //Hết đoạn loading
        
        //Muốn làm loading dừng lại thì phải chạy 1 luồng khác tắt cái timelime.stop() -- Xem bên main form anh làm.
        //Khuya rồi nên làm demo vầy cho em xem rồi tính tiếp.
        //Cách dưới là 1 nhiệm vụ chạy 1 luồng, em muốn nhiều nhiệm vụ chạy cùng 1 lúc thì tạo nhiều task, và new Thread(Tên task).start()
        //để chạy luồng mới.
        //Chạy đa luồng yêu cầu em phải kiểm soát (tức là đoán được code của mình nó chạy như thế nào để debug)
        
        // Đoạn này làm đa luồng.
        Task loadOverview = new Task() {
            @Override
            protected Object call() throws Exception {
                System.out.println("Loading...");
                for (int i = 0; i < 50000; i++) {
                    System.out.println("Loading..." + i);
                }
                return null;
            }
        };

        loadOverview.setOnSucceeded(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                System.out.println("Finished");
                Platform.runLater(() -> {
                    btn.setDisable(false);
                    timeline.stop();
                    stage.close();
                });
            }
        });

        new Thread(loadOverview).start();
  
    }

    @FXML
    private void CallWebCam(ActionEvent event) {
        QRWebCam qrWebCam = new QRWebCam(); 
        qrWebCam.txtQR.addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                txtInput.setText(newValue);
            }
        });   
        
        
    }
    
    public void setTextField(String string){        
        
    }

}
