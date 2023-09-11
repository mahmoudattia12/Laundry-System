package com.example.LaundrySystem.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer ID;
    @Column(nullable = false)
    private String currState;
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isDelivery;
    @Column(nullable = false)
    private LocalDateTime startDate;
    @Column(nullable = false)
    private LocalDateTime endDate;
    @Column(nullable = false)
    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "customerPhoneNumber", nullable = false)
    private Customer customer;
    @Column
    private String alternatePhone;

    @OneToMany(mappedBy = "order",cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<OrderItem> items = new ArrayList<>();

    @OneToMany(mappedBy = "order",cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<OrderNote> notes = new ArrayList<>();

    public Order(){}

    public Order(int ID, String currState, boolean isDelivery, LocalDateTime startDate, LocalDateTime endDate, double totalPrice, Customer customerPhone, String alternatePhone) {
        this.ID = ID;
        this.currState = currState;
        this.isDelivery = isDelivery;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.customer = customerPhone;
        this.alternatePhone = alternatePhone;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCurrState() {
        return currState;
    }

    public void setCurrState(String currState) {
        this.currState = currState;
    }

    public boolean isDelivery() {
        return isDelivery;
    }

    public void setDelivery(boolean delivery) {
        isDelivery = delivery;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public double getTotalPrice() {
        //here calculate it : totalPrice = sigma for all items(itemPrice * numberOfItems * ((100 - discount)/100))
        totalPrice = 0;
        for(OrderItem orderItem : items){
            totalPrice += (orderItem.getPrice() * orderItem.getNumber() *((100-orderItem.getDiscount())/100));
        }
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getAlternatePhone() {
        return alternatePhone;
    }

    public void setAlternatePhone(String alternatePhone) {
        this.alternatePhone = alternatePhone;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public List<OrderNote> getNotes() {
        return notes;
    }

    public void setNotes(List<OrderNote> notes) {
        this.notes = notes;
    }

    public List<String> getNotesMessages(){
        List<String> messages = new ArrayList<>();
        for (OrderNote orderNote: notes) {
            messages.add(orderNote.getNote());
        }
        return messages;
    }
}
