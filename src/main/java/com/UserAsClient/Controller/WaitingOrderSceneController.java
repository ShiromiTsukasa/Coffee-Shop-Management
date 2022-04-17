package com.UserAsClient.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class WaitingOrderSceneController {
    Stage window;
    @FXML
    private Label orderID;

    @FXML
    private Label totalPaymentLabel;

    public void setTotalPaymentLabel(double price){
        totalPaymentLabel.setText(String.format("Total payment: $%.2f", price));
    }
    public void setOrderID(int orderID){
        this.orderID.setText(String.valueOf(orderID));
    }

    public void setWindow(Stage window){
        this.window = window;
    }
    @FXML
    void handleOnOkayRequest(ActionEvent event) {
        Lock lock = Lock.getInstance();
        lock.release();
        //    window = (Stage) ((Button)event.getSource()).getScene().getWindow();
        window.close();
    }
    @FXML
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
