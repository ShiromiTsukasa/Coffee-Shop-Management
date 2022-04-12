package com.UserAsClient.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import com.UserAsClient.Main;
import com.helper.Crypter;
import com.helper.Parser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoginSceneController {
    private Scene scene;
    private Parent root;
    @FXML
    public TextField userName;

    @FXML
    public TextField password;

    private Stage stage;

    public void switchToMainOrderScene(ActionEvent event) throws IOException{
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("MainOrderScene.fxml"));
        root = loader.load();
        MainOrderSceneController controller = loader.getController();
        controller.setUserProfile(userName.getText());
        controller.setMainStage(stage);
        controller.do_init();
        
        Scene scene = new Scene(root);
        stage.setTitle("Main order Screen");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void login(ActionEvent event) {
        event.consume();

        Boolean found = false;

        String user = userName.getText();
        String pass = password.getText();

        File file = new File("data/userAccount.txt");
        Scanner sc = null;

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try {
            sc = new Scanner(file);

            // read each line
            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                Map<String, String> data = Parser.parseRecord(line);

                if (Crypter.hashSHA256(user + pass + data.get("salt")).equals(data.get("hash"))) {
                    found = true;
                    break;
                }
            }

            if (found) {
                System.out.println("User found");
                System.out.println(userName.getText());
                try{
                    stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    stage.setUserData(new UserProfile(userName.getText()));

                    switchToMainOrderScene(event);
                }catch (Exception e){
                    e.printStackTrace();
                }

                /*FXMLLoader loader2 = new FXMLLoader(Main.class.getResource("MainOrderScene.fxml"));
                try{
                    System.out.println(getClass());
                 //   switchToMainOrderScene(event);
                    root  = loader2.load();
                    MainOrderSceneController mainOrderSceneController = loader2.getController();
                    mainOrderSceneController.setUserProfile(userName.getText());

                    stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }catch (IOException e){
                    e.printStackTrace();
                }*/
            } else {
                System.out.println("User not found");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }

    @FXML
    private void moveToRegister(ActionEvent event) {
        event.consume();

        System.out.println(getClass());

        stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("registerScene.fxml"));
        try {
            Parent root = loader.load();
            RegisterSceneController controller = loader.getController();
            controller.setWrapText();
            Scene scene = new Scene(root, 600, 400);

            stage.setTitle("Register");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
