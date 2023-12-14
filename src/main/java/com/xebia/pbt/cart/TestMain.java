package com.xebia.pbt.cart;

import java.math.BigDecimal;
import java.util.Map;

public class TestMain {

    public static void main(String[] args) {

//        Map<String, Integer> items = Map.of(
//                "Item 0", 10,
//                "Item 1", 6,
//                "Item 2", 9,
//                "Item 3", 1
//        );
//
//        ShoppingCart cart = new ShoppingCart();
//        items.forEach(cart::addItem);
//        Map<String, Double> itemPrices = Map.of(
//                "Item 0", 0.01,
//                "Item 1", 64.85,
//                "Item 2", 95.49,
//                "Item 3", 53.28
//        );
//        System.out.println(cart.calculateTotalPrice(itemPrices));
//
//
//        double calculated = items.entrySet().stream()
//                .mapToDouble(entry -> itemPrices.get(entry.getKey()) * entry.getValue())
//                .sum();
//        System.out.println(calculated);
        int quantity = 6;
        double price = 64.85;
        double calculated1 = price * quantity;
        double calculated2 = BigDecimal.valueOf(price).multiply(BigDecimal.valueOf(quantity)).doubleValue();
        System.out.println(calculated1);
        System.out.println(calculated2);
    }
}
