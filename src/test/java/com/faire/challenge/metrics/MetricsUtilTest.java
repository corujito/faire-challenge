package com.faire.challenge.metrics;

import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import com.faire.challenge.client.model.Item;
import com.faire.challenge.client.model.Order;
import com.faire.challenge.client.model.State;

public class MetricsUtilTest {

    @Test
    public void testFindTopStateOrders() {
        Order order1 = createOrder("bo_1", State.NEW);
        Order order2 = createOrder("bo_2", State.PROCESSING);
        Order order3 = createOrder("bo_3", State.PROCESSING);
        Order order4 = createOrder("bo_4", State.BACKORDERED);
        List<Order> orders = Arrays.asList(order1, order2, order3, order4);

        Assert.assertEquals(State.PROCESSING, MetricsUtil.findTopStateOrders(orders));
    }

    @Test
    public void testFindLargestOrderByAmount() {
        Order order1 = createOrder("bo_1", State.PROCESSING);
        Item item1 = createItem("po_id1", 100, 3);
        order1.setItems(Arrays.asList(item1)); // order1 amount == 300

        Order order2 = createOrder("bo_2", State.PROCESSING);
        Item item2 = createItem("po_id1", 200, 2);
        order2.setItems(Arrays.asList(item2)); // order2 amount == 400

        List<Order> orders = Arrays.asList(order1, order2);

        Assert.assertEquals(order2.getId(), MetricsUtil.findLargestOrderByDollarAmount(orders).getId());
    }

    @Test
    public void testFindBestSellingProductOption() {
        Order order1 = createOrder("bo_1", State.PROCESSING);
        Item item1 = createItem("po_id1", 100, 3);
        order1.setItems(Arrays.asList(item1));

        Order order2 = createOrder("bo_2", State.PROCESSING);
        Item item2 = createItem("po_id1", 200, 2);
        order2.setItems(Arrays.asList(item2));

        List<Order> orders = Arrays.asList(order1, order2);

        Assert.assertEquals(item1.getProductOptionId(), MetricsUtil.findBestSellingProductOption(orders));
    }

    @Test
    public void testFindBestSellingProduct() {
        Order order1 = createOrder("bo_1", State.PROCESSING);
        Item item1 = createItem("po_id1", 100, 3);
        order1.setItems(Arrays.asList(item1));

        Order order2 = createOrder("bo_2", State.PROCESSING);
        Item item2 = createItem("po_id1", 200, 2);
        order2.setItems(Arrays.asList(item2));

        List<Order> orders = Arrays.asList(order1, order2);

        Assert.assertEquals(item1.getProductId(), MetricsUtil.findBestSellingProduct(orders));
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
        item.setProductId("p_1");
        return item;
    }
}
