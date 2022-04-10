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

    @FXML
    Button logOutButton;

    public void logout(ActionEvent event) throws IOException{
        System.out.println("Successfully logout");
        root = FXMLLoader.load(Main.class.getResource("loginScene.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    private void sendToOrderCustomizeScene(Coffee coffee) {
        try {
            // TODO: Change to refer to OrderCustomizeScene.fxml once done
            System.out.println("Changing to OrderCustomizeScene: " + coffee.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void orderCustomizeAmericano(ActionEvent event) {
        sendToOrderCustomizeScene(Coffee.AMERICANO);
    }

    @FXML
    public void orderCustomizeCappuccino(ActionEvent event) {
        sendToOrderCustomizeScene(Coffee.CAPPUCCINO);
    }

    @FXML
    public void orderCustomizeEspresso(ActionEvent event) {
        sendToOrderCustomizeScene(Coffee.ESPRESSO);
    }

    @FXML
    public void orderCustomizeLatte(ActionEvent event) {
        sendToOrderCustomizeScene(Coffee.LATTE);
    }

    @FXML
    public void orderCustomizeMocha(ActionEvent event) {
        sendToOrderCustomizeScene(Coffee.MOCHA);
    }

    @FXML
    public void orderCustomizeMicchiato(ActionEvent event) {
        sendToOrderCustomizeScene(Coffee.MICCHIATO);
    }

    @FXML
    public void orderCustomizeRedEye(ActionEvent event) {
        sendToOrderCustomizeScene(Coffee.RED_EYE);
    }

    @FXML
    public void orderCustomizeBlackEye(ActionEvent event) {
        sendToOrderCustomizeScene(Coffee.BLACK_EYE);
    }

    @FXML
    public void orderCustomizeIceCoffee(ActionEvent event) {
        sendToOrderCustomizeScene(Coffee.ICE_COFFEE);
    }
}
