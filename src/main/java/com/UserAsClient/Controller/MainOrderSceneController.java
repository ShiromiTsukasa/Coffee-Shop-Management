package com.UserAsClient.Controller;

import com.UserAsClient.Main;
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
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.util.Callback;
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

    @FXML
    public Button confirmOrderButton;

    @FXML
    private TableColumn<JSONObject, String> coffeeNameCol;

    @FXML
    private TableColumn<JSONObject, String> largeSizeQtyCol;

    @FXML
    private TableColumn<JSONObject, String> mediumSizeQtyCol;

    @FXML
    private TableColumn<JSONObject, String> smallSizeQtyCol;

    @FXML
    private TableColumn<JSONObject, String> totalPriceCol;

    @FXML
    private TableView<JSONObject> tableView;

    @FXML
    private Label totalPaymentLabel;

    @FXML
    private Button discardOrderButton;

    private Stage stage;

    private JSONObject tempoOrderObject;

    private ObservableList<JSONObject> observableList = FXCollections.observableArrayList();

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
        /*UserProfile userProfileData = (UserProfile) stage.getUserData();
        JSONObject currentOrder = userProfileData.getCurrentOrder();

        double superTotalPrice = 0;

        for (String keys: currentOrder.getJSONObject("orders").keySet()) {
            JSONObject order = currentOrder.getJSONObject("orders").getJSONObject(keys);
            double totalPrice = order.getDouble("totalPrice");
            superTotalPrice += totalPrice;
        }

        currentOrder.put("superTotalPrice", superTotalPrice);
        
        System.out.println(currentOrder.toString(4));
        userProfileData.setCurrentOrder(new JSONObject());*/

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

            System.out.println("Changing to OrderCustomizeScene: " + coffee.getName());

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("OrderCustomizeScene.fxml"));
            root = loader.load();
            try{
                OrderCustomizeSceneController orderCustomizeSceneController = loader.getController();
                orderCustomizeSceneController.setMainOrderSceneController(this);
            }catch (Exception e){
                e.printStackTrace();
            }
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
    //add by Panha
    public int readOrderId() throws IOException {
        // read OrderID from orderID.txt file
        String orderIDString = new String(Files.readAllBytes(Paths.get("data/orderID.txt")));
    //    System.out.println(orderIDString);
        int ID = Integer.parseInt(orderIDString);
        return ID;
    }
    //add by Panha
    public void setNewOrderID(int currentOrderID) throws IOException {
        FileWriter newOrderId = new FileWriter("data/orderID.txt");
        newOrderId.write(String.valueOf(currentOrderID));
        newOrderId.flush();
        newOrderId.close();
    }
    public void addAdditionalOrderDataToUserDataFile(JSONObject additionalOrderData, String content, int orderID, JSONObject currentOrder, File orderDataFile) throws IOException {
        System.out.println(orderID);
        if(!content.isEmpty()){
            JSONTokener previousOrderDataFile = new JSONTokener(new BufferedReader(new FileReader(orderDataFile)));
            additionalOrderData = new JSONObject(previousOrderDataFile);
        }

        additionalOrderData.put(String.valueOf(orderID), currentOrder);

        BufferedWriter bf = new BufferedWriter(new FileWriter(orderDataFile));
        bf.write(additionalOrderData.toString(4));
        bf.flush();
        bf.close();
    //    System.out.println("final order");
    //    System.out.println(additionalOrderData.toString(4));
    }
    public void additionalOrderDataInQueueOrderToFile(String queueOrderFilePath, JSONObject additionalOrderDataInQueueOrder, int orderID, JSONObject currentOrder) throws IOException {
        String queueOrderContent = new String(Files.readAllBytes(Paths.get(queueOrderFilePath)));
        if(!queueOrderContent.isEmpty()){
            JSONTokener previousOrderDataFile = new JSONTokener(new BufferedReader(new FileReader(queueOrderFilePath)));
            additionalOrderDataInQueueOrder = new JSONObject(previousOrderDataFile);
        }

        additionalOrderDataInQueueOrder.put(String.valueOf(orderID), currentOrder);

        BufferedWriter bf = new BufferedWriter(new FileWriter((queueOrderFilePath)));
        bf.write(additionalOrderDataInQueueOrder.toString(4));
        bf.flush();
        bf.close();
    }
    @FXML void confirmOrder(ActionEvent event) throws IOException {
        Lock lock = Lock.getInstance();
        if(!lock.acquire()){
            System.out.println("Lock is not available just don't click it");
            return;
        }
    //    event.consume();
    //    stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        System.out.println("Confirm order is click");
        UserProfile userProfileData = (UserProfile) stage.getUserData();
        JSONObject currentOrder = userProfileData.getCurrentOrder();

        double superTotalPrice = 0;

        for (String keys: currentOrder.getJSONObject("orders").keySet()) {
            JSONObject order = currentOrder.getJSONObject("orders").getJSONObject(keys);
            double totalPrice = order.getDouble("totalPrice");
            superTotalPrice += totalPrice;
        }
        currentOrder.put("superTotalPrice", superTotalPrice);
        JSONObject orderData = new JSONObject();

        //if supperTotalPrice is 0, we will not write to final order to file
        System.out.println("Current order");
        System.out.println(currentOrder.toString(4));

        if(superTotalPrice!=0){
            int orderID = readOrderId();
            orderData.put(String.valueOf(orderID), currentOrder);

            //change orderID in file by increasing 1
            int newOrderID = ++orderID;
            setNewOrderID(newOrderID);
            JSONObject additionalOrderData = new JSONObject();
            JSONObject additionalOrderDataInQueueOrder = new JSONObject();
            File orderDataFile = new File("data/userAsClient/"+userProfile.getText()+"/orderData.json");
            try{
                orderDataFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }

            String content = new String(Files.readAllBytes(Paths.get("data/userAsClient/"+userProfile.getText()+"/orderData.json")));
            addAdditionalOrderDataToUserDataFile(additionalOrderData, content, orderID, currentOrder, orderDataFile);

            String queueOrderFilePath = "data/userAsAdmin/queueOrder.json";
            additionalOrderDataInQueueOrderToFile(queueOrderFilePath, additionalOrderDataInQueueOrder, orderID, currentOrder);
            switchToWaitingOrderScene(orderID, superTotalPrice);
            discardOrder();
        }else{
            lock.release();
        }

//      System.out.println(orderDataArray.toString(4));
        userProfileData.setCurrentOrder(new JSONObject());
        do_init();
    }
    public void switchToWaitingOrderScene(int orderID, double totalPayment) throws IOException {
        Stage newStage = new Stage();
       /* Lock AlertLock = Lock.getInstance();
        if(!AlertLock.acquire()){
            System.out.println("Lock on alert is not available");
            return;
        }*/
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("waitingOrderScene.fxml"));
        Parent newRoot = loader.load();
        try{
            WaitingOrderSceneController waitingOrderSceneController =loader.getController();
            waitingOrderSceneController.setMainOrderSceneController(this);
            waitingOrderSceneController.setWindow(newStage);
            waitingOrderSceneController.setOnCloseRequest();
            waitingOrderSceneController.setOrderID(orderID);
            waitingOrderSceneController.setTotalPaymentLabel(totalPayment);
        }catch (Exception e){
            e.printStackTrace();
        }
        //    AlertBoxSceneController controller = loader.getController();
        Scene newScene = new Scene(newRoot);

        newStage.setScene(newScene);
        newStage.setTitle("Waiting Order Scene");
        newStage.show();
    }
    public void setTotalPaymentLabel(){
        UserProfile userProfileData = (UserProfile) stage.getUserData();
        JSONObject currentOrder = userProfileData.getCurrentOrder();

        double superTotalPrice = 0;

        for (String keys: currentOrder.getJSONObject("orders").keySet()) {
            JSONObject order = currentOrder.getJSONObject("orders").getJSONObject(keys);
            double totalPrice = order.getDouble("totalPrice");
            superTotalPrice += totalPrice;
        }
        totalPaymentLabel.setText(String.format("  $%.2f", superTotalPrice));
    }
    public void switchToAlertBoxStage() throws IOException {
        Stage newStage = new Stage();
        Lock AlertLock = Lock.getInstance();
        if(!AlertLock.acquire()){
            System.out.println("Lock on alert is not available");
            return;
        }
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("alertBoxScene.fxml"));
        Parent newRoot = loader.load();
        try{
            AlertBoxSceneController alertBoxSceneController =loader.getController();
            alertBoxSceneController.setMainOrderSceneController(this);
            alertBoxSceneController.setWindow(newStage);
            alertBoxSceneController.setOnCloseRequest();
        }catch (Exception e){
            e.printStackTrace();
        }
    //    AlertBoxSceneController controller = loader.getController();
        Scene newScene = new Scene(newRoot);

        newStage.setScene(newScene);
        newStage.setTitle("Alert Box");
        newStage.show();
    }
    public void discardOrder(){
        do_init();
        String filePath = "data/userAsClient/"+userProfile.getText()+"/temporaryOrderData.json";
        File temporaryOrderFile = new File(filePath);
        try{
            temporaryOrderFile.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        try{
            BufferedWriter bf = new BufferedWriter(new FileWriter((filePath)));
            bf.write("{}");
            bf.flush();
            bf.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        try{
            refresh();
        }catch (Exception e){
            e.printStackTrace();
        }

        setTotalPaymentLabel();
    }
    @FXML
     public void handleDiscardOrder(ActionEvent event) throws IOException {
        String totalPayment = totalPaymentLabel.getText();
        if(!totalPayment.equals("  $0.00")){
            switchToAlertBoxStage();
        }
    }

    public void refresh() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get("data/userAsClient/" + userProfile.getText() + "/temporaryOrderData.json")));
        tempoOrderObject = new JSONObject(content);
        System.out.println("Data from hit refresh or proceed order");
        System.out.println(tempoOrderObject.toString());
        setOBS();
        tableView.setItems(observableList);
        setCellFactoriesForTable();
    }

    public void setOBS(){

        List<JSONObject> ordersTempo = new ArrayList<JSONObject>();
        for (String key: tempoOrderObject.keySet()) {
            ordersTempo.add(tempoOrderObject.getJSONObject(key));
        }
        observableList.setAll(ordersTempo);
    }
    private void setCellFactoriesForTable(){
        coffeeNameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<JSONObject, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<JSONObject, String> c) {
                return new SimpleStringProperty(c.getValue().getString("coffee"));
            }
        });
        smallSizeQtyCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<JSONObject, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<JSONObject, String> s) {
                try{
                    return new SimpleStringProperty(String.valueOf(s.getValue().getJSONObject("sizes").getJSONObject("small").getInt("qty")));
                }catch (Exception e){
                    return new SimpleStringProperty("0");
                }
            }
        });
        mediumSizeQtyCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<JSONObject, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<JSONObject, String> m) {
                try{
                    return new SimpleStringProperty(String.valueOf(m.getValue().getJSONObject("sizes").getJSONObject("medium").getInt("qty")));
                }catch (Exception e){
                    return new SimpleStringProperty("0");
                }
            }
        });
        largeSizeQtyCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<JSONObject, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<JSONObject, String> l) {
                try{
                    return new SimpleStringProperty(String.valueOf(l.getValue().getJSONObject("sizes").getJSONObject("large").getInt("qty")));
                }catch (Exception e){
                    return new SimpleStringProperty("0");
                }
            }
        });
        totalPriceCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<JSONObject, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<JSONObject, String> p) {
                try{
                    return new SimpleStringProperty(String.format("$%.2f",p.getValue().getDouble("totalPrice")));
                }catch (Exception e){
                    return new SimpleStringProperty("$0.00");
                }
            }
        });
    }

}
