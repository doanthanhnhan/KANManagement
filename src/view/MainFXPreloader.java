/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.application.Preloader;
import javafx.application.Preloader.ProgressNotification;
import javafx.application.Preloader.StateChangeNotification;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Simple Preloader Using the ProgressBar Control
 *
 * @author Doan Thanh Nhan
 */
public class MainFXPreloader extends Preloader {

    ProgressBar bar;
    Label label;
    Stage stage;

    private Scene createPreloaderScene() {
        bar = new ProgressBar();
        bar.setPrefSize(180, 20);
        label = new Label();
        VBox vbox = new VBox();
        BorderPane p = new BorderPane();
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(label,bar);        
        p.setCenter(vbox);
        return new Scene(p, 300, 150);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setScene(createPreloaderScene());
        stage.setTitle("Loading");
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.getIcons().add(new Image("/images/KAN Logo.png"));
        stage.show();
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification scn) {
        if (scn.getType() == StateChangeNotification.Type.BEFORE_START) {
            stage.hide();
        }
    }

    @Override
    public void handleProgressNotification(ProgressNotification pn) {
        super.handleProgressNotification(pn); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void handleApplicationNotification(PreloaderNotification info) {
        if (info instanceof ProgressNotification) {
            label.setText("Loading database " + ((ProgressNotification) info).getProgress() + "%");
            bar.setProgress(((ProgressNotification) info).getProgress() / 100);
        }
    }

}
