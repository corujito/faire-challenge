package com.faire.challenge;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import com.faire.challenge.client.FaireAPIClient;
import com.faire.challenge.client.model.Item;
import com.faire.challenge.client.model.Option;
import com.faire.challenge.client.model.Order;
import com.faire.challenge.client.model.Product;
import com.faire.challenge.client.model.State;

@RunWith(MockitoJUnitRunner.class)
public class OrderConsumerTest {

    @Mock
    FaireAPIClient client;

    List<Product> products;
    Product product;
    Option option;

    @Before
    public void setup() {
        option = createOption("po_1");
        product = createProduct("p_1");
        product.setOptions(Arrays.asList(option));
        products = Arrays.asList(product);
    }

    @Test
    public void testConsumeOrders() {
        InventoryCache inventory = new InventoryCache(products);
        OrderConsumer consumer = new OrderConsumer(inventory, client);

        int initialQuantity = option.getAvailableQuantity();

        Order order = createOrder("bo_1", State.NEW);
        Item item = createItem(option.getId(), 100, 2);
        order.setItems(Arrays.asList(item));
        List<Order> orders = Arrays.asList(order);

        consumer.consumeOrders(orders);

        Assert.assertEquals(initialQuantity - item.getQuantity(), option.getAvailableQuantity().intValue());
        verify(client, times(1)).updateProductOption(Mockito.anyString(), Mockito.any());
        verify(client, times(1)).acceptOrder(order.getId());
    }

    private Order createOrder(String id, State state) {
        Order order = new Order();
        order.setId(id);
        order.setState(state);
        return order;
    }

    private Item createItem(String poId, Integer price, Integer quantity) {
        Item item = new Item();
        item.setProductOptionId(poId);
        item.setPriceCents(price);
        item.setQuantity(quantity);
        return item;
    }

    private Product createProduct(String id) {
        Product prod = new Product();
        prod.setId(id);
        prod.setActive(true);
        return prod;
    }

    private Option createOption(String id) {
        Option opt = new Option();
        opt.setId(id);
        opt.setActive(true);
        opt.setAvailableQuantity(100);
        return opt;
    }
}
