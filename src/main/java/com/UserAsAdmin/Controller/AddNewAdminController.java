package com.UserAsAdmin.Controller;

import com.helper.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddNewAdminController {
    @FXML
    public TextField adminUsername;

    @FXML
    public PasswordField adminPassword;

    @FXML
    public PasswordField adminConfirmPassword;

    @FXML
    public Label adminStatusIndicator;

    @FXML
    public Button cancelButton;

    @FXML
    public Button confirmButton;

    public void setWrapText() {
        adminStatusIndicator.setWrapText(true);
    }

    @FXML
    public void handleCancel(ActionEvent event) {
        event.consume();

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleAdminConfirm(ActionEvent event) {
        event.consume();

        File file = new File("data/adminAccount.txt");
        Scanner sc = null;
        Boolean found = false;

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            sc = new Scanner(file);

            String username = adminUsername.getText();
            String password = adminPassword.getText();
            String confirmPassword = adminConfirmPassword.getText();

            boolean success = false;

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] lineArray = line.split(",");

                if (lineArray[0].equals(username)) {
                    found = true;
                    break;
                }
            }
            adminStatusIndicator.setStyle(null);

            if (found) {
                adminStatusIndicator.setText("Username already exists!");
                adminStatusIndicator.setStyle("-fx-text-fill: red");
            } else if (!password.equals(confirmPassword)) {
                adminStatusIndicator.setText("Passwords do not match");
                adminStatusIndicator.setStyle("-fx-text-fill: red");
            } else {
                int err = checkSecurity(username, password);
                if (err == 0) {
                    String record = Crypter.convUPWithSalt(username, password);
                    try (BufferedWriter bf = new BufferedWriter(new FileWriter(file, true))) {
                        bf.write(record + "\n");
                        adminStatusIndicator.setText("Successfully added new admin. You can close this window now.");
                        adminStatusIndicator.setStyle("-fx-text-fill: green");

                        bf.flush();

                        success = true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (!success) {
                        adminStatusIndicator.setText("Failed to add new admin. Please try again.");
                        adminStatusIndicator.setStyle("-fx-text-fill: red");
                    }
                } else {
                    ArrayList<String> statusTokens = new ArrayList<String>();
                    if ((err & 1) == 1) {
                        statusTokens.add("Username.length < 4");
                    }

                    if ((err & 2) == 2) {
                        statusTokens.add("Username contains invalid character!");
                    }

                    if ((err & 4) == 4) {
                        statusTokens.add("Password.length < 8");
                    }

                    if ((err & 8) == 8) {
                        statusTokens.add("Password must contain at least: one digit, one lowercase letter, one uppercase letter, one special character");
                    }

                    StringJoiner sj = new StringJoiner(" and ");
                    for (String token : statusTokens) {
                        sj.add(token);
                    }
                    adminStatusIndicator.setText(sj.toString());
                    adminStatusIndicator.setStyle("-fx-text-fill: red;");
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
    }

    private int checkSecurity(String username, String password) {
        int ret = 0;

        if (username.length() < 4) {
            ret |= 1;
        }

        if (!username.matches("[a-zA-Z0-9]+")) {
            ret |= 2;
        }

        if (password.length() < 8) {
            ret |= 4;
        }

        if (!password.matches("(?=(?:.*\\d.*))(?=(?:.*[A-Z].*))(?=(?:.*[a-z].*))(?=(?:.*[\\[\\-!\\\"§$%&/()=?+*~#'_:.,;\\]].*))^.{8,}$")) {
            ret |= 8;
        }

        return ret;
    }
}
