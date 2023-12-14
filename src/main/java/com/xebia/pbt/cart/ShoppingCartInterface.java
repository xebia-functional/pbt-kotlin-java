package com.xebia.pbt.cart;

import java.util.Map;

public interface ShoppingCartInterface {
    void addItem(String itemName, int quantity);

    void removeItem(String itemName, int quantity);

    double calculateTotalPrice(Map<String, Double> itemPrices);
}
