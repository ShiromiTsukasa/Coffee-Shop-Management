package com.UserAsAdmin.Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.UserAsAdmin.Main;
import com.helper.CompletedOrderLock;
import com.helper.QueueOrderLock;

import org.json.JSONObject;
import org.json.JSONTokener;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class adminDashbord implements Initializable {
    @FXML
    public TableView<JSONObject> leftTable;

    @FXML
    public TableView<JSONObject> rightTable;

    @FXML
    public Label orderId;

    @FXML
    public Label orderUsername;

    @FXML
    public Label totalPrice;

    @FXML
    public TableColumn<JSONObject, String> briefOrderIdCol;

    @FXML
    public TableColumn<JSONObject, String> briefOrderUsernameCol;

    @FXML
    public TableColumn<JSONObject, String> briefContentCol;

    @FXML
    public TableColumn<JSONObject, String> orderCoffeeCol;

    @FXML
    public TableColumn<JSONObject, String> orderSmallCol;

    @FXML
    public TableColumn<JSONObject, String> orderMediumCol;

    @FXML
    public TableColumn<JSONObject, String> orderLargeCol;

    @FXML
    public Button completeOrderButton;

    private JSONObject ordersList = new JSONObject();
    private JSONObject completedOrdersList = new JSONObject();
    private ObservableList<JSONObject> briefObs = FXCollections.observableArrayList();
    private ObservableList<JSONObject> focusObs = FXCollections.observableArrayList();

    public void initialize(URL location, ResourceBundle resourceBundle) {
        refresh(new ActionEvent());
    }

    @FXML
    public void logout(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("adminLogin.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void addNewAdmin(ActionEvent event) throws IOException {
        event.consume();

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("addNewAdmin.fxml"));
        Parent root = loader.load();
        
        try {
            AddNewAdminController controller = loader.getController();
            controller.setWrapText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("Add New Admin");
        newStage.setScene(scene);
        newStage.show();
    }

    @FXML
    public void refresh(ActionEvent event) {
        event.consume();

        File queueOrder = new File("data/userAsAdmin/queueOrder.json");
        File completedOrder = new File("data/userAsAdmin/completeOrder.json");

        try {
            queueOrder.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            completedOrder.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String testQO = "";
        QueueOrderLock fileLock = QueueOrderLock.getInstance();
        try {
            while (fileLock.acquire() == false) {
                Thread.sleep(100);
            }
            BufferedReader br = new BufferedReader(new FileReader(queueOrder));
            testQO = br.readLine();
            br.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        fileLock.release();

        if (testQO != null) {
            try {
                JSONTokener tokener = new JSONTokener(new BufferedReader(new FileReader(queueOrder)));
                ordersList = new JSONObject(tokener);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        String testCO = "";
        CompletedOrderLock fileLock2 = CompletedOrderLock.getInstance();
        try {
            while (fileLock2.acquire() == false) {
                Thread.sleep(100);
            }
            BufferedReader bf = new BufferedReader(new FileReader(completedOrder));
            testCO = bf.readLine();
            bf.close();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        fileLock2.release();

        if (testCO != null) {
            try {
                JSONTokener tokener = new JSONTokener(new BufferedReader(new FileReader(completedOrder)));
                completedOrdersList = new JSONObject(tokener);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        setBriefObs();

        leftTable.setItems(briefObs);

        setCellFactoriesForLeftTable();

        setTableSelectionModel();
    }

    @FXML
    public void completeOrder(ActionEvent event) {
        event.consume();

        JSONObject order = new JSONObject();
        
        order.put("name", orderUsername.getText());
        order.put("orderId", orderId.getText());
        order.put("superTotalPrice", totalPrice.getText());

        JSONObject coffeeOrders = new JSONObject();

        ObservableList<JSONObject> coffeeOrdersList = rightTable.getItems();

        for (JSONObject coffeeOrder : coffeeOrdersList) {
            coffeeOrders.put(coffeeOrder.getString("coffee"), coffeeOrder);
        }

        order.put("orders", coffeeOrders);

        completedOrdersList.put(orderId.getText(), order);

        CompletedOrderLock completedOrderLock = CompletedOrderLock.getInstance();
        try (BufferedWriter bf = new BufferedWriter(new FileWriter(new File("data/userAsAdmin/completeOrder.json")))) {
            while (completedOrderLock.acquire() == false) {
                Thread.sleep(100);
            }
            bf.write(completedOrdersList.toString(4));
            bf.flush();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        completedOrderLock.release();

        ordersList.remove(orderId.getText());

        QueueOrderLock queueOrderLock = QueueOrderLock.getInstance();
        try (BufferedWriter bf = new BufferedWriter(new FileWriter(new File("data/userAsAdmin/queueOrder.json")))) {
            while (queueOrderLock.acquire() == false) {
                Thread.sleep(100);
            }
            bf.write(ordersList.toString(4));
            bf.flush();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        queueOrderLock.release();

        refresh(new ActionEvent());
    }

    private void setBriefObs() {
        List<JSONObject> orders = new ArrayList<JSONObject>();

        for (String key: ordersList.keySet()) {
            orders.add(ordersList.getJSONObject(key).put("orderId", key));
        }

        briefObs.setAll(orders);
    }

    private void setCellFactoriesForLeftTable() {
        briefOrderIdCol.setCellValueFactory(new Callback<CellDataFeatures<JSONObject, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<JSONObject, String> p) {
                return new SimpleStringProperty(p.getValue().getString("orderId"));
            }
        });

        briefOrderUsernameCol.setCellValueFactory(new Callback<CellDataFeatures<JSONObject, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<JSONObject, String> p) {
                return new SimpleStringProperty(p.getValue().getString("name"));
            }
        });

        briefContentCol.setCellValueFactory(new Callback<CellDataFeatures<JSONObject, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<JSONObject, String> p) {
                return new SimpleStringProperty(p.getValue().getJSONObject("orders").toString());
            }
        });
    }

    private void setRightTableSelection(JSONObject selection) {
        List<JSONObject> orders = new ArrayList<JSONObject>();

        for (String key: selection.getJSONObject("orders").keySet()) {
            orders.add(selection.getJSONObject("orders").getJSONObject(key));
        }

        focusObs.setAll(orders);

        rightTable.setItems(focusObs);

        setCellFactoriesForRightTable();
    }

    private void setCellFactoriesForRightTable() {
        orderCoffeeCol.setCellValueFactory(new Callback<CellDataFeatures<JSONObject, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<JSONObject, String> p) {
                return new SimpleStringProperty(p.getValue().getString("coffee"));
            }
        });

        orderSmallCol.setCellValueFactory(new Callback<CellDataFeatures<JSONObject, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<JSONObject, String> p) {
                try {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getJSONObject("sizes").getJSONObject("small").getInt("qty")));
                } catch (Exception e) {
                    return new SimpleStringProperty("0");
                }
            }
        });

        orderMediumCol.setCellValueFactory(new Callback<CellDataFeatures<JSONObject, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<JSONObject, String> p) {
                try {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getJSONObject("sizes").getJSONObject("medium").getInt("qty")));
                } catch (Exception e) {
                    return new SimpleStringProperty("0");
                }
            }
        });

        orderLargeCol.setCellValueFactory(new Callback<CellDataFeatures<JSONObject, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<JSONObject, String> p) {
                try {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getJSONObject("sizes").getJSONObject("large").getInt("qty")));
                } catch (Exception e) {
                    return new SimpleStringProperty("0");
                }
            }
        });
    }

    private void setTableSelectionModel() {
        leftTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                setRightTableSelection(newSelection);
                rightTable.setItems(focusObs);

                orderId.setText(newSelection.getString("orderId"));
                orderUsername.setText(newSelection.getString("name"));
                totalPrice.setText(String.format("$%.2f", newSelection.getDouble("superTotalPrice")));
            }
        });
    }
}
