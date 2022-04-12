package com.UserAsClient.Controller;

import org.json.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class OrderCustomizeSceneController {
    @FXML
    public Label coffeeName;

    @FXML
    public Label priceSmallLabel;

    @FXML
    public Label priceMediumLabel;

    @FXML
    public Label priceLargeLabel;

    @FXML
    public Label smallCounter;

    @FXML
    public Label mediumCounter;

    @FXML
    public Label largeCounter;

    @FXML
    public Button smallDecrement;
    
    @FXML
    public Button smallIncrement;

    @FXML
    public Button mediumDecrement;

    @FXML
    public Button mediumIncrement;

    @FXML
    public Button largeDecrement;

    @FXML
    public Button largeIncrement;

    @FXML
    public Button cancelOrder;

    @FXML
    public Button proceedOrder;

    private Stage mainStage;

    public void setCoffeeName(Coffee coffee) {
        coffeeName.setText(coffee.getName());
    }

    public void setPrices(double priceSmall, double priceMedium, double priceLarge) {
        priceSmallLabel.setText(String.format("$%.2f", priceSmall));
        priceMediumLabel.setText(String.format("$%.2f", priceMedium));
        priceLargeLabel.setText(String.format("$%.2f", priceLarge));
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @FXML
    public void handleSmallDecrement(ActionEvent event) {
        event.consume();

        int count = Integer.parseInt(smallCounter.getText());
        if (count > 0) {
            count--;
            smallCounter.setText(String.valueOf(count));
        }
    }

    @FXML
    public void handleSmallIncrement(ActionEvent event) {
        event.consume();

        int count = Integer.parseInt(smallCounter.getText());
        count++;
        smallCounter.setText(String.valueOf(count));
    }

    @FXML
    public void handleMediumDecrement(ActionEvent event) {
        event.consume();

        int count = Integer.parseInt(mediumCounter.getText());
        if (count > 0) {
            count--;
            mediumCounter.setText(String.valueOf(count));
        }
    }

    @FXML
    public void handleMediumIncrement(ActionEvent event) {
        event.consume();

        int count = Integer.parseInt(mediumCounter.getText());
        count++;
        mediumCounter.setText(String.valueOf(count));
    }

    @FXML
    public void handleLargeDecrement(ActionEvent event) {
        event.consume();

        int count = Integer.parseInt(largeCounter.getText());
        if (count > 0) {
            count--;
            largeCounter.setText(String.valueOf(count));
        }
    }

    @FXML
    public void handleLargeIncrement(ActionEvent event) {
        event.consume();

        int count = Integer.parseInt(largeCounter.getText());
        count++;
        largeCounter.setText(String.valueOf(count));
    }

    @FXML
    public void handleCancelOrder(ActionEvent event) {
        event.consume();

        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

        stage.close();
    }

    @FXML
    public void handleProceedOrder(ActionEvent event) {
        event.consume();

        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

        Coffee coffee = CoffeeMappings.getMapping(coffeeName.getText());

        int smallCount = Integer.parseInt(smallCounter.getText());
        int mediumCount = Integer.parseInt(mediumCounter.getText());
        int largeCount = Integer.parseInt(largeCounter.getText());

        if (smallCount == 0 && mediumCount == 0 && largeCount == 0) {
            System.out.println("Order failed: no coffee selected");
            return;
        }

        UserProfile userProfile = (UserProfile) stage.getUserData();

        JSONObject order = new JSONObject();

        order.put("coffee", coffeeName.getText());
        
        JSONObject sizes = new JSONObject();

        if (smallCount > 0) {
            JSONObject smallSize = new JSONObject().put("small", smallCount).put("price", coffee.getPriceSmall() * smallCount).put("qty", smallCount);
            sizes.put("small", smallSize);
        }

        if (mediumCount > 0) {
            JSONObject mediumSize = new JSONObject().put("medium", mediumCount).put("price", coffee.getPriceMedium() * mediumCount).put("qty", mediumCount);
            sizes.put("medium", mediumSize);
        }

        if (largeCount > 0) {
            JSONObject largeSize = new JSONObject().put("large", largeCount).put("price", coffee.getPriceLarge() * largeCount).put("qty", largeCount);
            sizes.put("large", largeSize);
        }

        order.put("sizes", sizes);

        order.put("totalPrice", coffee.getPriceSmall() * smallCount + coffee.getPriceLarge() * mediumCount + coffee.getPriceMedium() * largeCount);

        System.out.println(order.toString(2));

        JSONObject currentOrder = userProfile.getCurrentOrder();
        JSONArray ordersArray = currentOrder.getJSONArray("orders");

        for (int i = 0; i < ordersArray.length(); i++) {
            JSONObject orderObj = ordersArray.getJSONObject(i);

            if (orderObj.getString("coffee").equals(coffeeName.getText())) {
                ordersArray.remove(i);

                ordersArray.put(order);
                break;
            }
        }

        currentOrder.put("orders", ordersArray);
        userProfile.setCurrentOrder(currentOrder);

        stage.setUserData(userProfile);
        mainStage.setUserData(userProfile);
    }
}
