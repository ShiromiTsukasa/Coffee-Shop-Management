package com.UserAsClient.Controller;

public class CoffeeItem {
    private Coffee coffee;
    private int quantity;

    public CoffeeItem(Coffee coffee, int quantity) {
        this.coffee = coffee;
        this.quantity = quantity;
    }

    public CoffeeItem(Coffee coffee) {
        this.coffee = coffee;
        this.quantity = 0;
    }

    public Coffee getCoffee() {
        return coffee;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            this.quantity = 0;
        } else {
            this.quantity = quantity;
        }
    }

    public String getCoffeeName() {
        return coffee.getName();
    }

    public double getCoffeePrice() {
        return coffee.getPrice();
    }
}
