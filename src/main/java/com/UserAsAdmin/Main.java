package com.UserAsAdmin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.UserAsAdmin.Controller.AdminSetupController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public void start(Stage stage) throws Exception {
        File dataFile = new File("data");
        try {
            boolean check = dataFile.mkdir();
            System.out.println("Data Directory created: " + check);
        } catch (Exception e) {
            e.printStackTrace();
        }

        File userFile = new File("data/userAccount.txt");
        try {
            boolean check = userFile.createNewFile();
            System.out.println("User File created: " + check);
        } catch (Exception e) {
            e.printStackTrace();
        }

        File adminAccount = new File("data/adminAccount.txt");
        try {
            boolean check = adminAccount.createNewFile();
            System.out.println("Admin File created: " + check);
        } catch (Exception e) {
            e.printStackTrace();
        }

        File orderIDFile = new File("data/orderID.txt");
        try {
            boolean check = orderIDFile.createNewFile();
            System.out.println("Order ID File created: " + check);
            if (check) {
                BufferedWriter bf = new BufferedWriter(new FileWriter(orderIDFile));
                bf.write("1000");
                bf.flush();
                bf.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        File userAsAdminFolder = new File("data/userAsAdmin");
        try {
            boolean check = userAsAdminFolder.mkdir();
            System.out.println("User As Admin Folder created: " + check);
            
            if (!check) {
                throw new Exception("User As Admin Folder already exists!");
            }

            File queueOrderFile = new File("data/userAsAdmin/queueOrder.json");
            try {
                boolean check2 = queueOrderFile.createNewFile();
                System.out.println("Queue Order File created: " + check2);
                if (check) {
                    BufferedWriter bf = new BufferedWriter(new FileWriter(queueOrderFile));
                    bf.write("{}");
                    bf.flush();
                    bf.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    
            File completedOrderFile = new File("data/userAsAdmin/completeOrder.json");
            try {
                boolean check2 = completedOrderFile.createNewFile();
                System.out.println("Completed Order File created: " + check2);
                if (check) {
                    BufferedWriter bf = new BufferedWriter(new FileWriter(completedOrderFile));
                    bf.write("{}");
                    bf.flush();
                    bf.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String content = new String(Files.readAllBytes(Paths.get("data/adminAccount.txt")));
        
        if (content.equals("")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminSetup.fxml"));
            Parent root = loader.load();
            try {
                AdminSetupController controller = loader.getController();
                controller.setWrapText();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root);
            stage.setTitle("Admin Setup");
            stage.setScene(scene);
            stage.show();
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(com.UserAsAdmin.Main.class.getResource("adminLogin.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 600, 400);
            stage.setTitle("Admin Login");
            stage.setScene(scene);
            stage.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
