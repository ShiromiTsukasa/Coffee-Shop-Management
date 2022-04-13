package com.UserAsClient.Controller;

import com.UserAsClient.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.*;

public class MainOrderSceneController implements Initializable {
    Parent root;
    Scene scene;

    @FXML
    Label userProfile;

    @FXML
    public Button americanoButton;

    @FXML
    public Button cappuccinoButton;

    @FXML
    public Button espressoButton;

    @FXML
    public Button latteButton;

    @FXML
    public Button mochaButton;

    @FXML
    public Button micchiatoButton;

    @FXML
    public Button redEyeButton;

    @FXML
    public Button blackEyeButton;

    @FXML
    public Button iceCoffeeButton;

    private Stage stage;

    public void setUserProfile(String userName){
        userProfile.setText(userName);
    }

    public void initialize(URL location, ResourceBundle resources) {
        // TODO: initialize receipt stuffs
    }

    public void setMainStage(Stage mainStage) {
        this.stage = mainStage;
    }

    public void do_init() {
        UserProfile userProfileData = (UserProfile) stage.getUserData();
        JSONObject currentOrder = userProfileData.getCurrentOrder();
        currentOrder.clear();
        currentOrder.put("name", userProfile.getText());
        currentOrder.put("orders", new JSONObject());
        userProfileData.setCurrentOrder(currentOrder);
        stage.setUserData(userProfileData);
    }

    @FXML
    Button logOutButton;

    public void logout(ActionEvent event) throws IOException{
        UserProfile userProfileData = (UserProfile) stage.getUserData();
        JSONObject currentOrder = userProfileData.getCurrentOrder();

        double superTotalPrice = 0;

        for (String keys: currentOrder.getJSONObject("orders").keySet()) {
            JSONObject order = currentOrder.getJSONObject("orders").getJSONObject(keys);
            double totalPrice = order.getDouble("totalPrice");
            superTotalPrice += totalPrice;
        }

        currentOrder.put("superTotalPrice", superTotalPrice);
        
        System.out.println(currentOrder.toString(4));
        userProfileData.setCurrentOrder(new JSONObject());

        System.out.println("Successfully logout");
        root = FXMLLoader.load(Main.class.getResource("loginScene.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();

        do_init();
    }

    private void sendToOrderCustomizeScene(Coffee coffee, Stage oldStage) {
        try {
            Lock lock = Lock.getInstance();

            if (!lock.acquire()) {
                System.out.println("Lock is not available");
                return;
            }

            // TODO: Change to refer to OrderCustomizeScene.fxml once done
            System.out.println("Changing to OrderCustomizeScene: " + coffee.getName());

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("OrderCustomizeScene.fxml"));
            root = loader.load();
            OrderCustomizeSceneController controller = loader.getController();
            controller.setCoffeeName(coffee);
            controller.setPrices(coffee.getPriceSmall(), coffee.getPriceMedium(), coffee.getPriceLarge());
            controller.setMainStage(oldStage);
            
            scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setUserData(oldStage.getUserData());
            newStage.setScene(scene);
            newStage.setTitle("Order Customize");

            controller.setOwnStage(newStage);
            controller.overrideOnCloseRequest();

            newStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void orderCustomizeAmericano(ActionEvent event) {
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        sendToOrderCustomizeScene(Coffee.AMERICANO, stage);
    }

    @FXML
    public void orderCustomizeCappuccino(ActionEvent event) {
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        sendToOrderCustomizeScene(Coffee.CAPPUCCINO, stage);
    }

    @FXML
    public void orderCustomizeEspresso(ActionEvent event) {
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        sendToOrderCustomizeScene(Coffee.ESPRESSO, stage);
    }

    @FXML
    public void orderCustomizeLatte(ActionEvent event) {
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        sendToOrderCustomizeScene(Coffee.LATTE, stage);
    }

    @FXML
    public void orderCustomizeMocha(ActionEvent event) {
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        sendToOrderCustomizeScene(Coffee.MOCHA, stage);
    }

    @FXML
    public void orderCustomizeMicchiato(ActionEvent event) {
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        sendToOrderCustomizeScene(Coffee.MICCHIATO, stage);
    }

    @FXML
    public void orderCustomizeRedEye(ActionEvent event) {
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        sendToOrderCustomizeScene(Coffee.RED_EYE, stage);
    }

    @FXML
    public void orderCustomizeBlackEye(ActionEvent event) {
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        sendToOrderCustomizeScene(Coffee.BLACK_EYE, stage);
    }

    @FXML
    public void orderCustomizeIceCoffee(ActionEvent event) {
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        sendToOrderCustomizeScene(Coffee.ICE_COFFEE, stage);
    }
}
