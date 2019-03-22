package com.faire.challenge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.faire.challenge.client.model.Option;
import com.faire.challenge.client.model.Product;

public class InventoryCache {

    Map<String, Product> products;
    Map<String, Option> options;

    public InventoryCache(List<Product> products) {
        this.options = new HashMap<>();
        this.products = new HashMap<>();

        for (Product product : products) {
            this.products.put(product.getId(), product);
            for (Option option : product.getOptions()) {
                this.options.put(option.getId(), option);
            }
        }
    }

    public Map<String, Product> getProducts() {
        return products;
    }

    public void setProducts(Map<String, Product> products) {
        this.products = products;
    }

    public Map<String, Option> getOptions() {
        return options;
    }

    public void setOptions(Map<String, Option> options) {
        this.options = options;
    }
}
