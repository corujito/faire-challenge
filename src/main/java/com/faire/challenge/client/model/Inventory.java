package com.faire.challenge.client.model;

import java.util.Date;
import com.faire.challenge.client.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Inventory {

    private String sku;

    @JsonProperty("current_quantity")
    private Integer currentQuantity;

    private Boolean discontinued;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_PATTERN)
    @JsonProperty("backordered_until")
    private Date backorderedUntil;

    public Inventory(Integer newQuantity) {
        this.currentQuantity = newQuantity;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(Integer currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public Boolean getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(Boolean discontinued) {
        this.discontinued = discontinued;
    }

    public Date getBackorderedUntil() {
        return backorderedUntil;
    }

    public void setBackorderedUntil(Date backorderedUntil) {
        this.backorderedUntil = backorderedUntil;
    }
}
