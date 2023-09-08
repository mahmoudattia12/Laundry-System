package com.example.LaundrySystem.Entities;


import jakarta.persistence.*;

@Entity
@Table(name = "Employee_Tasks")
public class EmployeeTask {
    @Id
    @ManyToOne
    @JoinColumn(name = "employee_user_name")
    private Employee employee;
    @Id
    private String task;

    public EmployeeTask() {}

    public EmployeeTask(Employee employee, String task) {
        this.employee = employee;
        this.task = task;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
