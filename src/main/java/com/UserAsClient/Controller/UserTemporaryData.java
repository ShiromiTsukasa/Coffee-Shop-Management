package com.UserAsClient.Controller;

public class UserTemporaryData {
    public String coffeeName;
    public String size;
    public int qty;
    public double price;

    public UserTemporaryData(String coffeeName, String size, int qty, double price) {
        this.coffeeName = coffeeName;
        this.size = size;
        this.qty = qty;
        this.price = price;
    }
    public String toString(){
        return coffeeName+","+size+","+qty+","+price;
    }
}
