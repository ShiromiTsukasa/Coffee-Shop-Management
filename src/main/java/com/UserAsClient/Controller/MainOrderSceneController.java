package com.UserAsClient.Controller;

import com.UserAsClient.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainOrderSceneController {
    Parent root;
    Scene scene;
    @FXML
    Label userProfile;
    private Stage stage;
    public void setUserProfile(String userName){
        userProfile.setText(userName);
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
}
