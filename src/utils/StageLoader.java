/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author Doan Thanh Nhan
 */
public class StageLoader {

    /**
     *
     * @param fxmlPath
     * @param iconPath
     * @param title
     */
    public void formLoader(String fxmlPath, String iconPath, String title) {
        try {
            Stage stage = new Stage();
            stage.resizableProperty().setValue(Boolean.FALSE);
            Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource(fxmlPath));
            stage.getIcons().add(new Image(iconPath));
            Scene scene = new Scene(root);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(StageLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadingIndicator(Stage stage, Timeline timeline, String title) {
        // Khai báo stage nhìn xuyên thấu
        stage = new Stage(StageStyle.TRANSPARENT);

        // Chỗ này set khi mở cửa sổ con lên thì cha bị vô hiệu
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOpacity(0.5);

        final Label status = new Label(title);
        status.setStyle("-fx-text-fill: #008FC0; -fx-font-size : 20px; -fx-font-weight: bold;");
        final ProgressIndicator indicator = new ProgressIndicator(ProgressIndicator.INDETERMINATE_PROGRESS);
        indicator.setPrefSize(100, 100);
        //indicator.setProgress(-1d);
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        String statusText = status.getText();
                        status.setText(
                                ((title + " . . .").equals(statusText))
                                ? (title + " .")
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
    }
}
