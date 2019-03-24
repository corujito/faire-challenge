package com.faire.challenge.client.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.faire.challenge.client.Constants;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Shipment {

    @JsonProperty("id")
    private String id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_PATTERN)
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_PATTERN)
    @JsonProperty("updated_at")
    private Date updatedAt;
    @JsonProperty("order_id")
    private String orderId;
    @JsonProperty("maker_cost_cents")
    private Integer makerCostCents;
    @JsonProperty("tracking_code")
    private String trackingCode;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("created_at")
    public Date getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("updated_at")
    public Date getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updated_at")
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonProperty("order_id")
    public String getOrderId() {
        return orderId;
    }

    @JsonProperty("order_id")
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @JsonProperty("maker_cost_cents")
    public Integer getMakerCostCents() {
        return makerCostCents;
    }

    @JsonProperty("maker_cost_cents")
    public void setMakerCostCents(Integer makerCostCents) {
        this.makerCostCents = makerCostCents;
    }

    @JsonProperty("tracking_code")
    public String getTrackingCode() {
        return trackingCode;
    }

    @JsonProperty("tracking_code")
    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
