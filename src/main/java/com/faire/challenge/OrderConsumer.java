package com.faire.challenge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.faire.challenge.client.FaireAPIClient;
import com.faire.challenge.client.model.BackorderInfo;
import com.faire.challenge.client.model.Inventory;
import com.faire.challenge.client.model.Item;
import com.faire.challenge.client.model.Option;
import com.faire.challenge.client.model.Order;
import com.faire.challenge.client.model.State;

public class OrderConsumer {

    private FaireAPIClient client;
    private InventoryCache inventory;

    public OrderConsumer(InventoryCache inventory, FaireAPIClient client) {
        this.inventory = inventory;
        this.client = client;
    }

    public void consumeOrders(List<Order> orders) {
        for (Order order : orders) {
            consumeOrder(order);
        }
    }

    private void consumeOrder(Order order) {
        if (order.getState() != State.NEW) {
            return;
        }

        List<Item> notEnoughItems = new ArrayList<>();
        for (Item item : order.getItems()) {
            Option option = inventory.getOptions().get(item.getProductOptionId());
            // I am assuming here that a product option is not repeated inside items
            if (isNotFulfilled(item, option)) {
                notEnoughItems.add(item);
            }
        }

        if (notEnoughItems.isEmpty()) {
            // update inventory levels and move order to processing
            updateInventoryLevels(order);
            acceptsOrder(order);
        } else {
            // mark items that dont have enough inventory as backordered
            backorderItems(order, notEnoughItems);
        }
    }

    private boolean isNotFulfilled(Item item, Option option) {
        return !option.getActive() || option.getAvailableQuantity() == null
                || item.getQuantity() > option.getAvailableQuantity();
    }

    private void updateInventoryLevels(Order order) {
        List<Inventory> inventories = new ArrayList<>();
        for (Item item : order.getItems()) {
            Option option = inventory.getOptions().get(item.getProductOptionId());
            Integer newQuantity = option.getAvailableQuantity() - item.getQuantity();
            option.setAvailableQuantity(newQuantity);
            inventories.add(new Inventory(newQuantity));
        }
        client.updateInventoryLevels(inventories);
    }

    private void acceptsOrder(Order order) {
        client.acceptOrder(order.getId());
    }

    private void backorderItems(Order order, List<Item> notEnoughItems) {
        Map<String, BackorderInfo> backorderItems = new HashMap<>();
        for (Item item : notEnoughItems) {
            backorderItems.put(item.getId(), new BackorderInfo(item.getQuantity()));
        }
        client.backorderItems(order.getId(), backorderItems);
    }
}
