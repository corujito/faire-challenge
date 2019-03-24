package com.faire.challenge.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BackorderInfo {

    @JsonProperty("available_quantity")
    private Integer availableQuantity;

    private Boolean discontinued;

    @JsonProperty("backordered_until")
    private String backorderedUntil;

    public BackorderInfo(Integer quantity) {
        this.availableQuantity = quantity;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public Boolean getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(Boolean discontinued) {
        this.discontinued = discontinued;
    }

    public String getBackorderedUntil() {
        return backorderedUntil;
    }

    public void setBackorderedUntil(String backorderedUntil) {
        this.backorderedUntil = backorderedUntil;
    }
}
