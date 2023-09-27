package com.example.LaundrySystem.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.Formula;

import java.util.ArrayList;
import java.util.List;

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
//    @Column(nullable = false)
//    private boolean isGoldCustomer;
//    @Column(nullable = false, columnDefinition = "DOUBLE PRECISION DEFAULT 0.0")
//    private double totalPays;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "Customer_Laundry",
                joinColumns = {@JoinColumn(name = "customerPhone")},
                inverseJoinColumns = {@JoinColumn(name = "LaundryName")})
    @JsonIgnore
    private List<Laundry> laundries = new ArrayList<>();
//    private final double goldenPaysLimit = 500;
    public Customer(){}
    public Customer(String phoneNumber, String email, String name, String address) {
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.name = name;
        this.address = address;
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
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Laundry> getLaundries() {
        return laundries;
    }

    public void setLaundries(List<Laundry> laundries) {
        this.laundries = laundries;
    }

//    public double getTotalPays(String laundryName) {
//        double temp = 0;
//        for(Order order : this.orders){
//            if(order.getLaundry().getName().equals(laundryName)){
//                temp += order.getTotalPrice();
//            }
//        }
//        this.totalPays = temp;
//        if(totalPays >= goldenPaysLimit) isGoldCustomer = true; else isGoldCustomer = false;
//        return totalPays;
//    }
//
//    public void setTotalPays(double totalPays) {
//        this.totalPays = totalPays;
//        if(totalPays >= goldenPaysLimit) isGoldCustomer = true; else isGoldCustomer = false;
//    }
//    public boolean isGoldCustomer(String laundryName) {
//        return isGoldCustomer;
//    }
//
//    public void setGoldCustomer(boolean goldCustomer) {
//        isGoldCustomer = goldCustomer;
//    }
}
