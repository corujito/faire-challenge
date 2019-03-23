package com.faire.challenge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.faire.challenge.client.FaireAPIClient;
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
        for (Item item : order.getItems()) {
            Option option = inventory.getOptions().get(item.getProductOptionId());
            option.setAvailableQuantity(option.getAvailableQuantity() - item.getQuantity());
            client.updateProductOption(option.getId(), option);
        }
        // TODO: update multiple options at once (client.updateInventoryLevels)
    }

    private void acceptsOrder(Order order) {
        client.acceptOrder(order.getId());
    }

    private void backorderItems(Order order, List<Item> notEnoughItems) {
        Map<String, Map<String, String>> backorderItems = new HashMap<>();
        for (Item item : notEnoughItems) {
            Map<String, String> map = new HashMap<>();
            map.put("available_quantity", item.getQuantity().toString());
            backorderItems.put(item.getId(), map);
        }
        // TODO: client.backorderItems(order.getId(), backorderItems);
    }
}
