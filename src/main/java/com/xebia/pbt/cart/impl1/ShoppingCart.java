package com.xebia.pbt.cart.impl1;

import com.xebia.pbt.cart.ShoppingCartInterface;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart implements ShoppingCartInterface {

    private final Map<String, Integer> items;

    public ShoppingCart() {
        items = new HashMap<>();
    }

    @Override
    public void addItem(String itemName, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        items.merge(itemName, quantity, Integer::sum);
    }

    @Override
    public void removeItem(String itemName, int quantity) {
        if (!items.containsKey(itemName) || items.get(itemName) < quantity) {
            throw new IllegalArgumentException("Item not found or insufficient quantity");
        }
        items.merge(itemName, -quantity, Integer::sum);
        if (items.get(itemName) == 0) {
            items.remove(itemName);
        }
    }

    @Override
    public double calculateTotalPrice(Map<String, Double> itemPrices) {
        return items.entrySet().stream()
                .mapToDouble(entry -> itemPrices.getOrDefault(entry.getKey(), 0.0) * entry.getValue())
                .sum();
    }
}