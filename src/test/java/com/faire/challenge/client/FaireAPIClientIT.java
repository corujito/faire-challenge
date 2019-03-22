package com.faire.challenge.client;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
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
        System.out.println(products.size());
    }

    @Test
    public void testGetAllOrders() {
        List<Order> orders = client.getAllOrders();
        System.out.println(orders.size());
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
        Assert.assertEquals(quantity + 1, updatedOption.getAvailableQuantity().intValue());
    }
}
