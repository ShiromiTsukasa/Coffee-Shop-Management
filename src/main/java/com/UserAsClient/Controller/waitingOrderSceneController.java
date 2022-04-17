package com.UserAsClient.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class waitingOrderSceneController {
    @FXML
    private Label orderIDLabel;
    @FXML
    private Label finalPaymentLabel;
    public void setOrderIDLabel(String ID){
        orderIDLabel.setText(ID);
    }
    public void setFinalPaymentLabel(double price){
        finalPaymentLabel.setText("Total payment: $"+price);
    }
}
