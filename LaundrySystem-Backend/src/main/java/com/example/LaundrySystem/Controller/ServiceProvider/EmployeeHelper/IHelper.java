package com.example.LaundrySystem.Controller.ServiceProvider.EmployeeHelper;

import com.example.LaundrySystem.Entities.Employee;
import org.springframework.stereotype.Service;

@Service
public interface IHelper {
    public String add(Employee addFor, String toAdd);
    public String update(Employee updateFor, String prev, String updateWith);
    public String delete(Employee deleteFor, String toDelete);
}
