package com.faire.challenge.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import com.faire.challenge.client.model.BackorderInfo;
import com.faire.challenge.client.model.InventoriesUpdateInput;
import com.faire.challenge.client.model.InventoriesUpdateResponse;
import com.faire.challenge.client.model.Inventory;
import com.faire.challenge.client.model.Option;
import com.faire.challenge.client.model.Order;
import com.faire.challenge.client.model.OrdersResponse;
import com.faire.challenge.client.model.Product;
import com.faire.challenge.client.model.ProductsResponse;

public class FaireAPIClient {

    private static final String BASE_URL = "https://www.faire-stage.com/api/v1";
    private static final String TOKEN = "X-FAIRE-ACCESS-TOKEN";

    private String apiKey;
    private Client client;
    private WebTarget webTarget;

    public FaireAPIClient(String apiKey) {
        this.apiKey = apiKey;
        client = ClientBuilder.newClient();
        client.property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true); // support for PATCH
        webTarget = client.target(BASE_URL);
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        int page = 1;
        int limit = 250;
        ProductsResponse res;
        do {
            res = getProductsPaginated(page++, limit);
            products.addAll(res.getProducts());
        } while (res.getProducts().size() == limit);

        return products;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        int page = 1;
        int limit = 50;
        OrdersResponse res;
        do {
            res = getOrdersPaginated(page++, limit);
            orders.addAll(res.getOrders());
        } while (res.getOrders().size() == limit);

        return orders;
    }

    public Order acceptOrder(String orderId) {
        Response response = webTarget
                .path("/orders/" + orderId + "/processing")
                .request(MediaType.APPLICATION_JSON)
                .header(TOKEN, apiKey)
                .put(Entity.json("{}"));
        return response.readEntity(Order.class);
    }

    public Option updateProductOption(String optionId, Option option) {
        Response response = webTarget
                .path("/products/options/" + optionId)
                .request(MediaType.APPLICATION_JSON)
                .header(TOKEN, apiKey)
                .method("PATCH", Entity.entity(option, MediaType.APPLICATION_JSON));
        return response.readEntity(Option.class);
    }

    public List<Option> updateInventoryLevels(List<Inventory> inventories) {
        Response response = webTarget
                .path("/products/options/inventory-levels")
                .request(MediaType.APPLICATION_JSON)
                .header(TOKEN, apiKey)
                .method("PATCH", Entity.entity(new InventoriesUpdateInput(inventories), MediaType.APPLICATION_JSON));
        return response.readEntity(InventoriesUpdateResponse.class).getOptions();
    }

    public Order backorderItems(String orderId, Map<String, BackorderInfo> items) {
        Response response = webTarget
                .path("/orders/" + orderId + "/items/availability")
                .request(MediaType.APPLICATION_JSON)
                .header(TOKEN, apiKey)
                .post(Entity.entity(items, MediaType.APPLICATION_JSON));
        return response.readEntity(Order.class);
    }

    private ProductsResponse getProductsPaginated(int page, int limit) {
        Response response = webTarget
                .path("/products")
                .queryParam("limit", limit)
                .queryParam("page", page)
                .request(MediaType.APPLICATION_JSON)
                .header(TOKEN, apiKey)
                .get();
        return response.readEntity(ProductsResponse.class);
    }

    private OrdersResponse getOrdersPaginated(int page, int limit) {
        Response response = webTarget
                .path("/orders")
                .queryParam("limit", limit)
                .queryParam("page", page)
                .request(MediaType.APPLICATION_JSON)
                .header(TOKEN, apiKey)
                .get();
        return response.readEntity(OrdersResponse.class);
    }
}
