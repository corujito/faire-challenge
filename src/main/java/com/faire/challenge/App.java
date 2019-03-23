package com.faire.challenge;

import java.util.List;
import java.util.stream.Collectors;
import com.faire.challenge.client.FaireAPIClient;
import com.faire.challenge.client.model.Option;
import com.faire.challenge.client.model.Order;
import com.faire.challenge.client.model.Product;
import com.faire.challenge.client.model.State;
import com.faire.challenge.metrics.MetricsUtil;

/**
 * This app tries to solve Faire Backend Challenge.
 */
public class App {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("expected 1 parameter (api-key)");
            return;
        }

        String apiKey = args[0];
        FaireAPIClient client = new FaireAPIClient(apiKey);

        // create in memory inventory (filtering by given brand)
        String brandId = "b_d2481b88";
        List<Product> products = client.getAllProducts().stream().filter(p -> brandId.equals(p.getBrandId()))
                .collect(Collectors.toList());
        InventoryCache inventory = new InventoryCache(products);

        // consume orders
        List<Order> orders = client.getAllOrders();
        OrderConsumer consumer = new OrderConsumer(inventory, client);
        consumer.consumeOrders(orders);

        // print Metrics
        printMetrics(orders, inventory);
    }

    private static void printMetrics(List<Order> orders, InventoryCache inventory) {
        String op = MetricsUtil.findBestSellingProductOption(orders);
        Option option = inventory.getOptions().get(op);
        System.out.println("== The Best Selling Product Option ==");
        System.out.println(option + "\n");

        Order order = MetricsUtil.findLargestOrderByDollarAmount(orders);
        System.out.println("== Largest Order By Dollar Amount ==");
        System.out.println(order + "\n");

        State s = MetricsUtil.findTopStateOrders(orders);
        System.out.println("== The State With The Most Orders ==");
        System.out.println(s + "\n");

        String p = MetricsUtil.findBestSellingProduct(orders);
        Product product = inventory.getProducts().get(p);
        System.out.println("== The Best Selling Product ==");
        System.out.println(product + "\n");

        product = MetricsUtil.findProductWithMostOptions(inventory.getProducts());
        System.out.println("== Product With Most Options ==");
        System.out.println(product + "\n");
    }
}
