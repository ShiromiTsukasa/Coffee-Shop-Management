package com.UserAsClient.Controller;

public enum Coffee {
    AMERICANO(1, "Americano", 1.5, 2, 2.5),
    CAPPUCCINO(2, "Cappuccino", 2, 2.5, 3),
    MOCHA(3, "Mocha", 2.5, 3, 3.5),
    LATTE(4, "Latte", 2.5, 3, 3.5),
    ESPRESSO(5, "Espresso", 2, 2.5, 3),
    MICCHIATO(6, "Micchiato", 2, 2.5, 3),
    RED_EYE(7, "Red Eye", 2, 2.5, 3),
    BLACK_EYE(8, "Black Eye", 2, 2.5, 3),
    ICE_COFFEE(9, "Ice Coffee", 2, 2.5, 3);

    private int id;
    private String name;
    private double priceSmall;
    private double priceMedium;
    private double priceLarge;

    Coffee(int id, String name, double priceSmall, double priceMedium, double priceLarge) {
        this.id = id;
        this.name = name;
        this.priceSmall = priceSmall;
        this.priceMedium = priceMedium;
        this.priceLarge = priceLarge;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPriceSmall() {
        return priceSmall;
    }

    public double getPriceMedium() {
        return priceMedium;
    }

    public double getPriceLarge() {
        return priceLarge;
    }
}
