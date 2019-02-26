/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxml;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void TestFunction(ActionEvent event) {
        functiona();
    }

    public void functiona() {
        System.out.println(mainPane.lookup("#btn_test"));

        Button btn = (Button) mainPane.lookup("#btn_test");
        btn.setDisable(true);
//        for (int i = 0; i < 100000; i++) {
//            System.out.println(i);
//        }
        //btnTest.setDisable(true);
        //btnTest.setDisable(false);

        Task loadOverview = new Task() {
            @Override
            protected Object call() throws Exception {
                //System.out.println("Loading...");
                for (int i = 0; i < 100000; i++) {
                    System.out.println("Loading..."+ i);
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

                });
            }
        });

        new Thread(loadOverview).start();
//        Platform.runLater(() -> {
//            btn.setDisable(false);
//
//        });
    }

}
