package com.faire.challenge.client.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InventoriesUpdateInput {

    private List<Inventory> inventories;

    public InventoriesUpdateInput(List<Inventory> inventories) {
        this.inventories = inventories;
    }

    public List<Inventory> getInventories() {
        return inventories;
    }

    public void setInventories(List<Inventory> inventories) {
        this.inventories = inventories;
    }
}
