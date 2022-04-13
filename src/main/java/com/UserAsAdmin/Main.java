package com.UserAsAdmin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public void start(Stage stage) throws Exception {
       // System.out.println(com.UserAsClient.Main.class);
        FXMLLoader fxmlLoader = new FXMLLoader(com.UserAsAdmin.Main.class.getResource("adminLogin.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("adminLogin");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
