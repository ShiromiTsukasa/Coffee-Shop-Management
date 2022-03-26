package com.UserAsClient.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import com.helper.Crypter;
import com.helper.Parser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginSceneController {
    @FXML
    public Button btn;

    @FXML
    public TextField userName;

    @FXML
    public TextField password;

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
            } else {
                System.out.println("User not found");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }
}
