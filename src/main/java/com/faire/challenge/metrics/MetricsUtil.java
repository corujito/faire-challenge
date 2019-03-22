package com.faire.challenge.metrics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import com.faire.challenge.client.model.Item;
import com.faire.challenge.client.model.Order;
import com.faire.challenge.client.model.Product;
import com.faire.challenge.client.model.State;

public class MetricsUtil {

    private MetricsUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String findBestSellingProductOption(List<Order> orders) {
        Map<String, Integer> optionCounter = new HashMap<>();
        for (Order order : orders) {
            for (Item item : order.getItems()) {
                optionCounter.put(item.getProductOptionId(),
                        optionCounter.getOrDefault(item.getProductOptionId(), 0) + item.getQuantity());
            }
        }

        Integer max = null;
        String optionId = null;
        for (Entry<String, Integer> e : optionCounter.entrySet()) {
            if (max == null || e.getValue() > max) {
                max = e.getValue();
                optionId = e.getKey();
            }
        }
        return optionId;
    }

    public static Order findLargestOrderByDollarAmount(List<Order> orders) {
        Order order = null;
        long max = 0;
        for (Order o : orders) {
            int amount = 0;
            for (Item item : o.getItems()) {
                amount += item.getQuantity() * item.getPriceCents();
                if (item.getIncludesTester() != null && item.getIncludesTester()) {
                    amount += item.getTesterPriceCents();
                }
            }
            if (amount > max) {
                max = amount;
                order = o;
            }
        }
        return order;
    }

    public static State findTopStateOrders(List<Order> orders) {
        Map<State, Long> stateCounter =
                orders.stream().collect(Collectors.groupingBy(Order::getState, Collectors.counting()));
        State state = null;
        Long max = null;
        for (Entry<State, Long> e : stateCounter.entrySet()) {
            if (max == null || e.getValue() > max) {
                max = e.getValue();
                state = e.getKey();
            }
        }
        return state;
    }

    public static String findBestSellingProduct(List<Order> orders) {
        Map<String, Integer> productCounter = new HashMap<>();
        for (Order order : orders) {
            for (Item item : order.getItems()) {
                productCounter.put(item.getProductId(),
                        productCounter.getOrDefault(item.getProductId(), 0) + item.getQuantity());
            }
        }

        Integer max = null;
        String productId = null;
        for (Entry<String, Integer> e : productCounter.entrySet()) {
            if (max == null || e.getValue() > max) {
                max = e.getValue();
                productId = e.getKey();
            }
        }
        return productId;
    }

    public static Product findProductWithMostOptions(Map<String, Product> products) {
        Product product = null;
        int max = 0;
        for (Entry<String, Product> entry : products.entrySet()) {
            if (max < entry.getValue().getOptions().size()) {
                max = entry.getValue().getOptions().size();
                product = entry.getValue();
            }
        }
        return product;
    }
}
