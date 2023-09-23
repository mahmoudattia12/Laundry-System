package com.example.LaundrySystem.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class OrderItemPair {
    private Order order;
    private List<OrderItem> orderItems;

    public OrderItemPair(Order order, List<OrderItem> orderItems) {
        this.order = order;
        this.orderItems = orderItems;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

}
