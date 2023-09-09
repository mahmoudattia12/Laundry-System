package com.example.LaundrySystem.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Employee_Holidays")
@IdClass(HolidayPrimaryKey.class)
public class EmployeeHoliday {
    @Id
    @ManyToOne
    @JoinColumn(name = "employee_user_name")
    private Employee employee;
    @Id
    private String holiday;

    public EmployeeHoliday() {}

    public EmployeeHoliday(Employee employee, String holiday) {
        this.employee = employee;
        this.holiday = holiday;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }
}
