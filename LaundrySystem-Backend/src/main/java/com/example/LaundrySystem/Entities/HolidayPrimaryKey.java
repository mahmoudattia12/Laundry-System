package com.example.LaundrySystem.Entities;

import groovy.transform.EqualsAndHashCode;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class HolidayPrimaryKey implements Serializable {
    @ManyToOne
    @JoinColumn(name = "employee_user_name")
    private Employee employee;
    private String holiday;

    public HolidayPrimaryKey() {}

    public HolidayPrimaryKey(Employee employee, String holiday) {
        this.employee = employee;
        this.holiday = holiday;
    }
    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }


    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
