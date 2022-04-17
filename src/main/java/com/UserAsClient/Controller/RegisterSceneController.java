package com.UserAsClient.Controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.StringJoiner;

import com.UserAsClient.Main;
import com.helper.Crypter;
import com.helper.Parser;

import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterSceneController {
    @FXML
    public TextField usernameTextField;

    @FXML
    public PasswordField passwordPassField;

    @FXML
    public PasswordField confirmPasswordPassField;

    @FXML
    public Label statusIndicator;

    @FXML
    public Button returnButton;

    @FXML
    public Button registerButton;

    public void setWrapText() {
        statusIndicator.setWrapText(true);
    }

    @FXML
    public void returnToLogin(ActionEvent event) {
        event.consume();

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("loginScene.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) returnButton.getScene().getWindow();
            Scene scene = new Scene(root, 600, 400);

            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void submitRegistration(ActionEvent event) {
        event.consume();

        File file = new File("data/userAccount.txt");
        Scanner sc = null;
        Boolean found = false;

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            sc = new Scanner(file);

            String username = usernameTextField.getText();
            String password = passwordPassField.getText();
            String confirmPassword = confirmPasswordPassField.getText();

            boolean success = false;

            // read each line
            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                Map<String, String> data = Parser.parseRecord(line);

                if (username.equals(data.get("username"))) {
                    found = true;
                    break;
                }
            }
            statusIndicator.setStyle(null);

            if (found) {
                statusIndicator.setText("Username already exists!");
                statusIndicator.setStyle("-fx-text-fill: red;");
            } else if (!password.equals(confirmPassword)) {
                statusIndicator.setText("Passwords do not match!");
                statusIndicator.setStyle("-fx-text-fill: red;");
            } else {
                int err = checkSecurity(username, password);
                if (err == 0) {
                    String record = Crypter.convUPWithSalt(username, password);
                    try (BufferedWriter bf = new BufferedWriter(new FileWriter(file, true))) {
                        bf.write(record + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    File dataFolder = new File("data");
                    try {
                        dataFolder.mkdir();

                        File userAsClientFolder = new File("data/userAsClient");
                        try {
                            userAsClientFolder.mkdir();

                            File userFolder = new File("data/userAsClient/" + username);
                            try {
                                userFolder.mkdir();

                                File userDataFile = new File("data/userAsClient/" + username + "/profile.json");

                                try {
                                    userDataFile.createNewFile();

                                    try (BufferedWriter bf = new BufferedWriter(new FileWriter(userDataFile, true))) {
                                        JSONObject userProfile = new JSONObject();
                                        userProfile.put("username", username);
                                        
                                        bf.write(userProfile.toString(4) + "\n");
    
                                        statusIndicator.setText("Registration successful!");
                                        statusIndicator.setStyle("-fx-text-fill: green;");
    
                                        success = true;
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            } catch (SecurityException se) {
                                se.printStackTrace();
                            }
                        } catch (SecurityException se) {
                            se.printStackTrace();
                        }
                    } catch (SecurityException se) {
                        se.printStackTrace();
                    }

                    if (!success) {
                        statusIndicator.setText("Registration failed!");
                        statusIndicator.setStyle("-fx-text-fill: red;");
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
                    statusIndicator.setText(sj.toString());
                    statusIndicator.setStyle("-fx-text-fill: red;");
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

        if (!username.matches("[a-zA-Z0-9_]+")) {
            ret |= 2;
        }

        //System.out.println(password.length());
        if (password.length() < 8) {
            ret |= 4;
        }

        if (!password.matches("(?=(?:.*\\d.*))(?=(?:.*[A-Z].*))(?=(?:.*[a-z].*))(?=(?:.*[\\[\\-!\\\"§$%&/()=?+*~#'_:.,;\\]].*))^.{8,}$")) {
            ret |= 8;
        }
        
        return ret;
    }
}
