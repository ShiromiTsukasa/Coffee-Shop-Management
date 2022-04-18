package com.UserAsClient.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AlertBoxSceneController {
    private Stage window;
    @FXML
    private Button noButton;

    @FXML
    private Button yesButton;

    public void setWindow(Stage window){
        this.window = window;
    }
    @FXML
    void handleOnNoRequest(ActionEvent event) {
        System.out.println("lock release");
        Lock lock = Lock.getInstance();
        lock.release();
    //    window = (Stage) ((Button)event.getSource()).getScene().getWindow();
        window.close();
    }

    @FXML
    void handleOnYesRequest(ActionEvent event) {
        mainOrderSceneController.discardOrder();
        Lock lock = Lock.getInstance();
        lock.release();
        window.close();
    }
    MainOrderSceneController mainOrderSceneController;
    public void setMainOrderSceneController(MainOrderSceneController mainOrderSceneController){
        this.mainOrderSceneController = mainOrderSceneController;
    }

    public void setOnCloseRequest(){
        window.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                window.close();
                Lock lock = Lock.getInstance();
                lock.release();
            }
        });
    }

}
