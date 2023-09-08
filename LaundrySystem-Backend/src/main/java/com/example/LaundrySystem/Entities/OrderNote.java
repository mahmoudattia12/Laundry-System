package com.example.LaundrySystem.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Order_Notes")
@IdClass(NotePrimaryKey.class)
public class OrderNote {
    @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @Id
    private String note;

    public OrderNote() {}

    public OrderNote(Order order, String note) {
        this.order = order;
        this.note = note;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
