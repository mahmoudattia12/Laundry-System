package com.example.LaundrySystem.Entities;

import groovy.transform.EqualsAndHashCode;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class ItemPrimaryKey implements Serializable {
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    private String type;
    private String serviceCategory;

    public ItemPrimaryKey() {}

    public ItemPrimaryKey(Order order, String type, String serviceCategory) {
        this.order = order;
        this.type = type;
        this.serviceCategory = serviceCategory;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(String serviceCategory) {
        this.serviceCategory = serviceCategory;
    }
}
