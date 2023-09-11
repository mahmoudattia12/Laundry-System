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
    private int number;
    @Column(nullable = false)
    private double price;
    @Column
    private double discount;

    public OrderItem() {}

    public OrderItem(Order order, String type, String serviceCategory, int number, double price, double discount) {
        this.order = order;
        this.type = type;
        this.serviceCategory = serviceCategory;
        this.number = number;
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
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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
}
