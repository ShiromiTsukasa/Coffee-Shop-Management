package com.UserAsClient.Controller;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestReadFromUserTemporaryData {
    public static void main(String[] args) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get("data/userAsClient/panhaGKP/temporaryOrderData.json")));
    //    System.out.println(content);
        JSONObject currentData = new JSONObject(content);
        System.out.println();
        System.out.println(currentData.toString(4));
        System.out.println();
        for(String key: currentData.keySet()){
        //S    System.out.println(key);
        //    System.out.println(currentData.get(key));
            JSONObject coffeeSizeTotalEachPriceCoffeeName = new JSONObject(currentData.get(key).toString());
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
                    //    System.out.println();
                    }

                }else if(sizeKey.equals("totalPrice")){
                //    System.out.println("$"+coffeeSizeTotalEachPriceCoffeeName.get("totalPrice"));
                }else if(sizeKey.equals("coffee")){
                //    System.out.println(coffeeSizeTotalEachPriceCoffeeName.get("coffee"));
                }
            }
         //   System.out.println();
        }
    }
}
