package com.example.LaundrySystem.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Order_Items")
@IdClass(ItemPrimaryKey.class)
public class OrderItem {
    @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @Id
    private String type;
    @Id
    private String serviceCategory;
    @Column(nullable = false)
    private int quantity;


    @Column(nullable = false)
    private double price;
    @Column
    private double discount;

    public OrderItem() {}

    public OrderItem(Order order, String type, String serviceCategory, int quantity, double price, double discount) {
        this.order = order;
        this.type = type;
        this.serviceCategory = serviceCategory;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
    }

    public ItemPrimaryKey getPK(){
        return new ItemPrimaryKey(order, type, serviceCategory);
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

    public int getNumber() {
        return quantity;
    }

    public void setNumber(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
