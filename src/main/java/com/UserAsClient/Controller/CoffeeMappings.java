package com.UserAsClient.Controller;

public class CoffeeMappings {
    public static Coffee getMapping(String coffeeName) {
        for (Coffee coffee: Coffee.values()) {
            if (coffee.getName().equals(coffeeName)) {
                return coffee;
            }
        }
        return null;
    }
}
