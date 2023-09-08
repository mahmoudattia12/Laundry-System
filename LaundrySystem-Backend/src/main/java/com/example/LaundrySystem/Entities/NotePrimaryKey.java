package com.example.LaundrySystem.Entities;

import groovy.transform.EqualsAndHashCode;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class NotePrimaryKey implements Serializable {
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    private String note;

    public NotePrimaryKey() {}

    public NotePrimaryKey(Order order, String note) {
        this.order = order;
        this.note = note;
    }
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
