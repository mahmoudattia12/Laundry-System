package com.example.LaundrySystem.Entities;


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
    private List<EmployeeTask> tasks= new ArrayList<>();
    @OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE)
    private List<EmployeeHoliday> holidays= new ArrayList<>();

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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
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

    public ArrayList<String> getTasks() {
        return new ArrayList<>();
    }

    public ArrayList<String> getHolidays() {
        return new ArrayList<>();
    }

}
