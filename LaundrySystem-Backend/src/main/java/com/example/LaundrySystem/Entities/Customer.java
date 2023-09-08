package com.example.LaundrySystem.Entities;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(
        name = "Customers",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"email"})
        }
)
public class Customer {
    @Id
    private String phoneNumber;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String name;
    @Column
    private String address;
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isGoldCustomer;
    @Column(nullable = false, columnDefinition = "DOUBLE DEFAULT 0.0")
    private double totalPays;

    public Customer(){}
    public Customer(String phoneNumber, String email, String name, String address, boolean isGoldCustomer, double totalPays) {
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.name = name;
        this.address = address;
        this.isGoldCustomer = isGoldCustomer;
        this.totalPays = totalPays;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isGoldCustomer() {
        return isGoldCustomer;
    }

    public void setGoldCustomer(boolean goldCustomer) {
        isGoldCustomer = goldCustomer;
    }

    public double getTotalPays() {
        return totalPays;
    }

    public void setTotalPays(double totalPays) {
        this.totalPays = totalPays;
    }

    public ArrayList<Order> getOrders(){
        return new ArrayList<>();
    }

}
