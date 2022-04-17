package com.UserAsClient.Controller;

import com.UserAsClient.Main;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.ResourceBundle;

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
    public Label superTotalPriceLabel;

    private Stage stage;

    public void setUserProfile(String userName){
        userProfile.setText(userName);
    }
    public String getUserProfile(){
        return userProfile.getText();
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
        // TODO: display final order while logout (Thyrak)
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

            // TODO: Change to refer to OrderCustomizeScene.fxml once done
            System.out.println("Changing to OrderCustomizeScene: " + coffee.getName());

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("OrderCustomizeScene.fxml"));
            root = loader.load();
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
        return Integer.parseInt(orderIDString);
    }
    //add by Panha
    public void setNewOrderID(int currentOrderID) throws IOException {
        FileWriter newOrderId = new FileWriter("data/orderID.txt");
        newOrderId.write(String.valueOf(currentOrderID));
        newOrderId.flush();
        newOrderId.close();
    }
    public void addAddtionalOrderDataToUserDataFile(JSONObject additionalOrderData, String content, int orderID, JSONObject currentOrder, File orderDataFile) throws IOException {
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

    //TODO: Add temporary data to be displayed
    public ObservableList<UserTemporaryData> getUserTemporaryData() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get("data/userAsClient/panhaGKP/temporaryOrderData.json")));
        ObservableList<UserTemporaryData> data = FXCollections.observableArrayList();
        JSONObject currentDataFromFile = new JSONObject(content);

    //    System.out.println();
    //    System.out.println(currentDataFromFile.toString(4));
        System.out.println();
        for(String key: currentDataFromFile.keySet()){
            //S    System.out.println(key);
            //    System.out.println(currentData.get(key));
            JSONObject coffeeSizeTotalEachPriceCoffeeName = new JSONObject(currentDataFromFile.get(key).toString());
            System.out.println();
            for(String sizeKey: coffeeSizeTotalEachPriceCoffeeName.keySet()){
                //    System.out.println(sizeKey);
                if(sizeKey.equals("sizes")){
                    //    System.out.println(coffeeSizeTotalEachPriceCoffeeName.get("sizes").toString());
                    JSONObject coffeeSize = new JSONObject(coffeeSizeTotalEachPriceCoffeeName.get("sizes").toString());
                    //    System.out.println(coffeeSize.toString());
                    //    System.out.println();
                    for(String sizes: coffeeSize.keySet()){
                        //        System.out.println(sizes);
                        //    System.out.println(coffeeSize.get(sizes));
                        JSONObject priceAndQty = new JSONObject(coffeeSize.get(sizes).toString());
                        //        System.out.println(priceAndQty);
                        double price = 0;
                        int qty = 0;
                        for(String priceOrQtyKey: priceAndQty.keySet()){
                            //    System.out.println(priceOrQtyKey);
                            if(priceOrQtyKey.equals("price")){
                                price = Double.parseDouble(priceAndQty.get("price").toString());
                                //    System.out.println(price);
                            }else if(priceOrQtyKey.equals("qty")){
                                qty = Integer.parseInt(priceAndQty.get("qty").toString());
                                //    System.out.println(qty);
                            }
                        }
                        String coffeeName = coffeeSizeTotalEachPriceCoffeeName.get("coffee").toString();
                        String currentCoffeeSize = sizes;
                        UserTemporaryData temporaryData = new UserTemporaryData(coffeeName, sizes, qty, price);
                        System.out.println(temporaryData.toString());
                        data.add(temporaryData);
                    }
                }
            }
        }
        return data;
    }
    // Handle on table view
    @FXML
    TableView<UserTemporaryData> tableView;

    @FXML
    private TableColumn<UserTemporaryData, String> coffeeNameCol;

    @FXML
    private TableColumn<UserTemporaryData, Double> priceCol;

    @FXML
    private TableColumn<UserTemporaryData, Integer> qtyCol;

    @FXML
    private TableColumn<UserTemporaryData, String> sizeCol;
    ObservableList<UserTemporaryData> list;
    {
        try {
            list = getUserTemporaryData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: initialize receipt stuffs
        coffeeNameCol.setCellValueFactory(new PropertyValueFactory<>("coffeeName"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("qty"));
        tableView.setItems(list);
    }
    //------------------------------
    public void switchToWaitingOrderScene(ActionEvent event, int id, double price) throws IOException {
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("waitingOrderScene.fxml"));
        root = loader.load();
        waitingOrderSceneController controller = loader.getController();
        controller.setOrderIDLabel(String.valueOf(id));
        controller.setFinalPaymentLabel(price);

        Scene scene = new Scene(root);
        stage.setTitle("Waiting Order Scene");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void confirmOrder(ActionEvent event) throws IOException {
        event.consume();
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
            int newOrderID = orderID+1;
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
            addAddtionalOrderDataToUserDataFile(additionalOrderData, content, orderID, currentOrder, orderDataFile);

            String queueOrderFilePath = "data/userAsAdmin/queueOrder.json";
            additionalOrderDataInQueueOrderToFile(queueOrderFilePath, additionalOrderDataInQueueOrder, orderID, currentOrder);
            switchToWaitingOrderScene(event, orderID, superTotalPrice);
        }
//      System.out.println(orderDataArray.toString(4));
        userProfileData.setCurrentOrder(new JSONObject());
        do_init();
    }
}
