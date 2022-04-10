package com.UserAsClient.Controller;

public enum Coffee {
    AMERICANO("Americano", 1.5),
    CAPPUCCINO("Cappuccino", 2.5),
    ESPRESSO("Espresso", 1.5),
    LATTE("Latte", 2.5),
    MOCHA("Mocha", 3.5),
    MICCHIATO("Micchiato", 3.5),
    RED_EYE("Red Eye", 3.5),
    BLACK_EYE("Black Eye", 3.5),
    ICE_COFFEE("Ice Coffee", 3.5);

    private String name;
    private double price;

    Coffee(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
