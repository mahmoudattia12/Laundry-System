package com.example.LaundrySystem.Entities;

import groovy.transform.EqualsAndHashCode;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class TaskPrimaryKey implements Serializable {
    @ManyToOne
    @JoinColumn(name = "employee_user_name")
    private Employee employee;
    private String task;

    public TaskPrimaryKey() {}

    public TaskPrimaryKey(Employee employee, String task) {
        this.employee = employee;
        this.task = task;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
