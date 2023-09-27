package com.example.LaundrySystem.Entities;

public class ToSendCustomer {
    private String PhoneNumber, name, email, address;
    private double totalPays;
    private boolean isGold;

    public ToSendCustomer(String phoneNumber, String name, String email, String address, double totalPays, boolean isGold) {
        PhoneNumber = phoneNumber;
        this.name = name;
        this.email = email;
        this.address = address;
        this.totalPays = totalPays;
        this.isGold = isGold;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotalPays() {
        return totalPays;
    }

    public void setTotalPays(double totalPays) {
        this.totalPays = totalPays;
    }

    public boolean isGold() {
        return isGold;
    }

    public void setGold(boolean gold) {
        isGold = gold;
    }
}
