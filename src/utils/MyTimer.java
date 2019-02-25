/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 *
 * @author Doan Thanh Nhan
 */
public class MyTimer {

    private static Timeline myTimer;
    private final IntegerProperty timeSeconds = new SimpleIntegerProperty(0);

    public MyTimer() {
    }

    public void updateTime() {
        // increment seconds
        int seconds = timeSeconds.get();
        timeSeconds.set(seconds + 1);
    }

    public void create_myTimer(Label timerLabel) {
        myTimer = new Timeline(new KeyFrame(Duration.seconds(1), evt -> updateTime()));
        myTimer.setCycleCount(Animation.INDEFINITE); // repeat over and over again
        timeSeconds.set(1);
        myTimer.play();

        timerLabel.textProperty().unbind();
        timerLabel.textProperty().bind(timeSeconds.asString().concat(" s Loading..."));

        //Vẫn chưa có tác dụng...
        System.out.println("Old Style class name: " + timerLabel.getStyleClass());
//        int indexOf = timerLabel.getStyleClass().indexOf("label");
//        if (indexOf != -1) {
//            timerLabel.getStyleClass().remove(indexOf);
//        }
        timerLabel.getStyleClass().removeAll("label");
        timerLabel.getStyleClass().add("label-task-status");
        System.out.println("New Style class name: " + timerLabel.getStyleClass());
        //timerLabel.setStyle("-fx-text-fill: -fx-primarycolor;");
    }

    public void stop_Timer(Label timerLabel) {
        timerLabel.textProperty().unbind();
        myTimer.stop();
    }

}
