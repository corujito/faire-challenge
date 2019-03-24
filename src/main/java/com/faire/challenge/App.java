package com.faire.challenge;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
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

    private static final Logger LOGGER = Logger.getLogger(App.class);

    public static void main(String[] args) {

        if (args.length != 1) {
            LOGGER.error("expected 1 parameter (api-key)");
            return;
        }

        // create API client
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
        LOGGER.info("== The Best Selling Product Option ==");
        LOGGER.info(option + "\n");

        Order order = MetricsUtil.findLargestOrderByDollarAmount(orders);
        LOGGER.info("== Largest Order By Dollar Amount ==");
        LOGGER.info(order + "\n");

        State s = MetricsUtil.findTopStateOrders(orders);
        LOGGER.info("== The State With The Most Orders ==");
        LOGGER.info(s + "\n");

        String p = MetricsUtil.findBestSellingProduct(orders);
        Product product = inventory.getProducts().get(p);
        LOGGER.info("== The Best Selling Product ==");
        LOGGER.info(product + "\n");

        product = MetricsUtil.findProductWithMostOptions(inventory.getProducts());
        LOGGER.info("== Product With Most Options ==");
        LOGGER.info(product + "\n");
    }
}
