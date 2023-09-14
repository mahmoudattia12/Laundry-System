package com.example.LaundrySystem.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jdk.jfr.DataAmount;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(
        name = "Employees",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"email"}),
                @UniqueConstraint(columnNames = {"phoneNumber"})
        }
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employee {
    @Id
    private String userName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String phoneNumber;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isManager;
    @Column
    private Double salary;
    @Column
    private LocalTime startShiftTime;
    @Column
    private LocalTime endShiftTime;
    @OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE)
    @JsonIgnore //to break the circular reference
    private List<EmployeeTask> tasks= new ArrayList<>();
    @OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE)
    @JsonIgnore //to break the circular reference
    private List<EmployeeHoliday> holidays= new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "laundry_name", nullable = false)
    private Laundry laundry;


    public Employee(){}

    public Employee(String userName, String password, String phoneNumber, String email, boolean isManager) {
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.isManager = isManager;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
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

    public List<EmployeeTask> getTasks() {
        return tasks;
    }

    public List<String> getTasksMessages(){
        List<String> messages = new ArrayList<>();
        for (EmployeeTask t: tasks) {
            messages.add(t.getTask());
        }
        return messages;
    }

    public void setTasks(List<EmployeeTask> tasks) {
        this.tasks = tasks;
    }

    public List<EmployeeHoliday> getHolidays() {
        return holidays;
    }

    public List<String> getHolidayMessages(){
        List<String> messages = new ArrayList<>();
        for (EmployeeHoliday h: holidays) {
            messages.add(h.getHoliday());
        }
        return messages;
    }
    public void setHolidays(List<EmployeeHoliday> holidays) {
        this.holidays = holidays;
    }
    public Laundry getLaundry() {
        return laundry;
    }

    public void setLaundry(Laundry laundry) {
        this.laundry = laundry;
    }
}
