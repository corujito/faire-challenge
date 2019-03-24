package com.faire.challenge.client;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import com.faire.challenge.client.model.BackorderInfo;
import com.faire.challenge.client.model.Inventory;
import com.faire.challenge.client.model.Item;
import com.faire.challenge.client.model.Option;
import com.faire.challenge.client.model.Order;
import com.faire.challenge.client.model.Product;
import com.faire.challenge.client.model.State;

public class FaireAPIClientIT {

    String apiKey =
            "HQLA9307HSLQYTC24PO2G0LITTIOHS2MJC8120PVZ83HJK4KACRZJL91QB7K01NWS2TUCFXGCHQ8HVED8WNZG0KS6XRNBFRNGY71";
    FaireAPIClient client = new FaireAPIClient(apiKey);

    @Test
    public void testGetAllProducts() {
        List<Product> products = client.getAllProducts();
        Assert.assertNotNull(products);
    }

    @Test
    public void testGetAllOrders() {
        List<Order> orders = client.getAllOrders();
        Assert.assertNotNull(orders);
    }

    @Test
    public void testAcceptOrder() {
        Order order = client.acceptOrder("bo_c7zgrit28t");
        Assert.assertEquals(State.PROCESSING, order.getState());
    }

    @Test
    public void testUpdateOptionQuantity() {
        Option option = client.getAllProducts().get(0).getOptions().get(0);

        int quantity = option.getAvailableQuantity() == null ? 0 : option.getAvailableQuantity();
        option.setAvailableQuantity(quantity + 1);

        Option updatedOption = client.updateProductOption(option.getId(), option);
        Assert.assertEquals(quantity + 1L, updatedOption.getAvailableQuantity().intValue());
    }

    @Test
    public void testUpdateInventoryLevels() {
        Option option = findValidOption();
        if (option == null) { // Couldn't find valid option. Skus are null
            return;
        }

        Inventory inventory = createInventory(option);
        Integer oldQuantity = option.getAvailableQuantity() == null ? 0 : option.getAvailableQuantity();
        inventory.setCurrentQuantity(oldQuantity + 10);

        List<Option> options = client.updateInventoryLevels(Arrays.asList(inventory));
        Assert.assertEquals(inventory.getCurrentQuantity(), options.get(0).getAvailableQuantity());
    }

    @Test
    public void testBackorderItems() {
        Order order = findValidOrder();
        if (order == null) { // Couldn't find valid order. Items are empty
            return;
        }

        Map<String, BackorderInfo> backorderItems = new HashMap<>();
        Item item = order.getItems().get(0);
        backorderItems.put(item.getId(), new BackorderInfo(item.getQuantity()));

        Order o = client.backorderItems(order.getId(), backorderItems);

        Assert.assertEquals(order.getId(), o.getId());
    }

    private Order findValidOrder() {
        return client.getAllOrders().stream()
                .filter(o -> o.getState() == State.PROCESSING && o.getItems() != null && !o.getItems().isEmpty())
                .findAny()
                .orElse(null);
    }

    private Option findValidOption() {
        for (Product product : client.getAllProducts()) {
            for (Option option : product.getOptions()) {
                if (option.getSku() != null) {
                    return option;
                }
            }
        }
        return null;
    }

    private Inventory createInventory(Option option) {
        Inventory inventory = new Inventory(option.getAvailableQuantity());
        inventory.setSku(option.getSku());
        return inventory;
    }
}
