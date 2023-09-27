package com.example.LaundrySystem.Entities;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReceivedEmployee {
    private String userName, email, phoneNumber;
    private boolean isManager;
    private Double salary;
    private LocalTime startShiftTime, endShiftTime;
    private List<String> tasks = new ArrayList<>(), holidays = new ArrayList<>();

    public ReceivedEmployee() {}
//
//    public ReceivedEmployee(String userName, String email, String phoneNumber, boolean isManager) {
//        this.userName = userName;
//        this.email = email;
//        this.phoneNumber = phoneNumber;
//        this.isManager = isManager;
//    }

    public ReceivedEmployee(String userName, String email, String phoneNumber, boolean isManager, Double salary, LocalTime startShiftTime, LocalTime endShiftTime, List<String> tasks, List<String> holidays) {
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.isManager = isManager;
        this.salary = salary;
        this.startShiftTime = startShiftTime;
        this.endShiftTime = endShiftTime;
        this.tasks = tasks;
        this.holidays = holidays;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean getIsManager() {
        return isManager;
    }

    public void setIsManager(boolean manager) {
        isManager = manager;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public LocalTime getStartShiftTime() {
        return startShiftTime;
    }

    public void setStartShiftTime(LocalTime startShiftTime) {
        this.startShiftTime = startShiftTime;
    }

    public LocalTime getEndShiftTime() {
        return endShiftTime;
    }

    public void setEndShiftTime(LocalTime endShiftTime) {
        this.endShiftTime = endShiftTime;
    }

    public List<String> getTasks() {
        return tasks;
    }

    public void setTasks(List<String> tasks) {
        this.tasks = tasks;
    }

    public List<String> getHolidays() {
        return holidays;
    }

    public void setHolidays(List<String> holidays) {
        this.holidays = holidays;
    }
}
