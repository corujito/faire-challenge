package com.faire.challenge.client.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.faire.challenge.client.Constants;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {

    @JsonProperty("id")
    private String id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_PATTERN)
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_PATTERN)
    @JsonProperty("updated_at")
    private Date updatedAt;
    @JsonProperty("brand_id")
    private String brandId;
    @JsonProperty("short_description")
    private String shortDescription;
    @JsonProperty("description")
    private String description;
    @JsonProperty("wholesale_price_cents")
    private Integer wholesalePriceCents;
    @JsonProperty("retail_price_cents")
    private Integer retailPriceCents;
    @JsonProperty("active")
    private Boolean active;
    @JsonProperty("name")
    private String name;
    @JsonProperty("unit_multiplier")
    private Integer unitMultiplier;
    @JsonProperty("options")
    private List<Option> options = null;
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

    @JsonProperty("brand_id")
    public String getBrandId() {
        return brandId;
    }

    @JsonProperty("brand_id")
    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    @JsonProperty("short_description")
    public String getShortDescription() {
        return shortDescription;
    }

    @JsonProperty("short_description")
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("wholesale_price_cents")
    public Integer getWholesalePriceCents() {
        return wholesalePriceCents;
    }

    @JsonProperty("wholesale_price_cents")
    public void setWholesalePriceCents(Integer wholesalePriceCents) {
        this.wholesalePriceCents = wholesalePriceCents;
    }

    @JsonProperty("retail_price_cents")
    public Integer getRetailPriceCents() {
        return retailPriceCents;
    }

    @JsonProperty("retail_price_cents")
    public void setRetailPriceCents(Integer retailPriceCents) {
        this.retailPriceCents = retailPriceCents;
    }

    @JsonProperty("active")
    public Boolean getActive() {
        return active;
    }

    @JsonProperty("active")
    public void setActive(Boolean active) {
        this.active = active;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("unit_multiplier")
    public Integer getUnitMultiplier() {
        return unitMultiplier;
    }

    @JsonProperty("unit_multiplier")
    public void setUnitMultiplier(Integer unitMultiplier) {
        this.unitMultiplier = unitMultiplier;
    }

    @JsonProperty("options")
    public List<Option> getOptions() {
        return options;
    }

    @JsonProperty("options")
    public void setOptions(List<Option> options) {
        this.options = options;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", brandId=" + brandId + ", wholesalePriceCents=" + wholesalePriceCents
                + ", retailPriceCents=" + retailPriceCents + ", active=" + active + ", name=" + name
                + ", unitMultiplier=" + unitMultiplier + ", options=" + options + "]";
    }

}
